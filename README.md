## community项目总结：

> community项目是使用springboot和模板引擎实现的一个简单论坛系统，实现了论坛的一些基本功能。

### 功能模块：

- 登陆功能：集成github授权登陆（目前存在的问题，github授权访问缓慢会造成连接超时报错）

- 文章发布：数据库使用text存储，前端继承了markdown富文本编辑（图片上传为实现，确实云存储后续修复）

- 文章评论：实现了文章的评论，包括了一级评论和二级评论

- 评论点赞：利用了redis Set的数据结构，以评论id作为核心Key存储，解决重复点赞问题。

- 文章浏览：利用了redis S听的数据结构，以用户Id和文章Id作为Key存储，并且设置合适的过期时间（5hour）解决重复刷新导致浏览量增加，同时也允许用户多次浏览文章也会增加浏览量的问题

- 文章推荐：根据正在阅读的文章的tag推送相关问题。（利用sql的正则表达式进行tag匹配）

  ```sql
  SELECT * FROM t_question
          WHERE tag REGEXP #{tag,jdbcType=VARCHAR}
          order by comment_count desc
          LIMIT 0 , 10
  ## tag REGEXP 'Spring|Java'
  ```

- 热榜系统（周期榜、滚动榜）项目亮点下面细说

- 评论通知：文章或评论被评论后对应用户上线会被通知，点击通知进行跳转。

- 登陆统计



### 项目结构：

advice:全局异常处理     annotation:自定义注解      aop:切面类                       config:配置类     controller:路由类

dto:网络传输实体类	  enums:枚举类                     exception:自定义异常     filter:过滤器        interceptor:拦截器

mapper:dao层              model:实体类                      provider:服务提供类       service:服务类     task:定时任务类

Utils:自定义工具包





### 项目亮点：
**cookie自动登陆**：实现了cookie自动登陆功能，并且解决了cookie丢失导致账号异地登陆的功能，
                   实现过程：在用户github授权登陆的时候，同时把token存入数据库和redis中，并且存入cookie。在redis中以IP地址作为核心key值存入并且设置合适的过期时间。每次访问时验证请求的IP地址与redis中的存储是否一致，以此来达到cookie丢失导致的问题。

**登陆统计**：利用redis的数据结构**HyperLogLogs**实现登陆人数统计。以日期作为Key，用户Id作为Val进行每日登陆人数的记载。利用定时任务定期进行清理避免redis内存消耗过高导致频繁swap，性能下降。

**知识点**：**HyperLogLogs**存储原理，特性。

**热榜系统**：

​	-**周期榜**：比如日榜，周榜等；

​	本次项目实现的是日榜功能。Key：计算出当日在一年中的第几天作为Key的核心字段，避免Key的冲突，Val：存储文章的id+title。减    少查询数据库的次数。利用redis中的zset数据结构进行去重和排序。每次浏览就增加对应 val 的 score。定时清理：利用spring的任务调度选择在访问量少的时间（凌晨两点）进行数据的清理（清理几天前的数据）。

​    -**滚动榜**：比如连续N天榜；

​	本次项目实现的**连续两天**的滚动榜。

​	方案：同时维护三个榜单。今日展示的滚动榜，明日展示的滚动榜，今日滚动榜最后一天的日榜。

​				在对今日滚动榜读写的时候同时查询日榜，并且把今日滚动榜-日榜的数据存入明日滚动榜。对于没用访问到的Key利用离线工具				或者spring task进行数据的迁移（可以选择迁移靠前的百分之70左右数据而不必全部迁移）。

优点：较少的读写次数，以及较少的内存空间。对系统比较友好。

**限流**：

​	系统的高可用离不开限流、熔断、降级。常用的限流方案，redis计数器，**令牌桶**，漏桶算法。

​	-**全局限流**

​	方案：使用redis计数器进行限流，主要利用了redis计数和单线程读写的特性。

​				利用日期加时间戳的后五位作为Key，val主要存储数字。

​				通过过滤器拦截请求，向redis执行inc操作根据返回值判断当前时间日期、时间戳下的val表示的数量进行判断是否到达了阈值。				到达则返回，反之通过。

```java
  		//全局限流
        Boolean islimit = redisService.isLimitQPS();
        if (islimit){
            throw new CustomizeException(ExceptionEnum.LIMITE_RATE);
        }
         //使用redis 实现限流操作
        public Boolean isLimitQPS(){
            Date date = new Date();
            int day = CommonUtils.calDayInYear(date);
            String tmp = String.valueOf(System.currentTimeMillis());

            String str =tmp.substring(tmp.length()-5, tmp.length());
            String key = "QPS::"+day+"::"+str;
            Long rate = redisTemplate.opsForValue().increment(key);
            if (rate > LIMIT_RATE) return true;
            else return false;
        }
```

-**API限流**

方案：使用guava的限流类进行实现（底层是令牌桶算法）。

1. 自定义注解标记限流的Controller。
2. 配置AOP切面类，根据注解类型进行拦截。
3. 使用concurrenthashmap存储每个controller对应的RateLimite（根据注解的属性设置令牌桶的增长速率）
4. 调用tryacuire方法尝试获得令牌，成功则执行方法。否则返回错误信息

```java
@Component
@Scope
@Aspect
public class RateLimitAspect {

    //用来存放不同接口的RateLimiter(key为接口名称，value为RateLimiter)
    private ConcurrentHashMap<String, RateLimiter> map = new ConcurrentHashMap<>();
    private RateLimiter rateLimiter;
    @Pointcut("@annotation(com.imnoob.community.annotation.RateLimit)")
    public void serviceLimit() {
    }

    @Around("serviceLimit()")
    public Object around(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
        Object obj = null;
        //获取拦截的方法名
        Signature sig = joinPoint.getSignature();
        //获取拦截的方法名
        MethodSignature msig = (MethodSignature) sig;
        //返回被织入增加处理目标对象
        Object target = joinPoint.getTarget();
        //为了获取注解信息
        Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
        //获取注解信息
        RateLimit annotation = currentMethod.getAnnotation(RateLimit.class);
        double limitNum = annotation.qps();    //获取注解每秒加入桶中的token
        String functionName = msig.getName(); // 注解所在方法名区分不同的限流策略

        //获取rateLimiter
        if(map.containsKey(functionName)){
            rateLimiter = map.get(functionName);
        }else {
            map.put(functionName, RateLimiter.create(limitNum));
            rateLimiter = map.get(functionName);
        }

        try {
            if (rateLimiter.tryAcquire()) {
                //执行方法
                obj = joinPoint.proceed();
            } else {
                //拒绝了请求（服务降级）
                throw new CustomizeException(ExceptionEnum.LIMITE_RATE);

            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return obj;
    }

}
```





**学习**：redis的使用场景，以及三种高级的数据结构。对限流有基本的了解，学习了主流的限流方案以及原理。

编程方面：虽然枚举类使用的不错，但是缺少了设计模式的使用。不利于后续的扩展以及发展。设计模式有待加强。

