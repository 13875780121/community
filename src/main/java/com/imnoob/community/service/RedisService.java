package com.imnoob.community.service;

import com.imnoob.community.Utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    RedisTemplate<String,String> redisTemplate;

    final static String DayBOARD_KEY_PREFIX = "dayboard::";
    final static String THUMB_KEY_PREFIX = "thumbup::";
    final static String SCROLL_BOARD_KEY_PREFIX = "scroll::";

    final static Integer RANGE_INDEX = 0;
    final static Integer SIZE = 5;
    final static Integer OFFSET = 2;        //滚动榜单的跨度  2 天

    /**
     * 解决重复刷新导致浏览量增加
     * @param questionId
     * @param userId
     * @return
     */
    public boolean isReScan(Long questionId,Long userId){
        String key_prefix = "reScan" + questionId;
        String key_suffix = "usrId" + userId;
        String val = "已浏览";
        Integer expireTime = 5;
        if (redisTemplate.hasKey(key_prefix+key_suffix)) return true;
        else{
            redisTemplate.opsForValue().set(key_prefix + key_suffix, val,expireTime, TimeUnit.HOURS);
            return false;
        }
    }

    //实现点赞功能 避免同一个账号对同一评论重复点赞
    public boolean isthumbUp(Long commentId,Long useId){
        String key = THUMB_KEY_PREFIX+commentId;
        String val = useId.toString();
        Boolean isthumbup = redisTemplate.opsForSet().isMember(key, val);
        if (isthumbup){
            return isthumbup;
        }else{
            redisTemplate.opsForSet().add(key, val);
            return isthumbup;
        }

    }

    //日榜的实现
    public void adddayboard(Long questionid,String title){

        Date date = new Date();
        int day = CommonUtils.calDayInYear(date);
        String key = DayBOARD_KEY_PREFIX+day;
        String val = questionid + ":" + title;
        redisTemplate.opsForZSet().incrementScore(key, val, 1);
    }

    public Set<String> getdayboard(){
        Date date = new Date();
        int day = CommonUtils.calDayInYear(date);
        String key = DayBOARD_KEY_PREFIX+day;
        return redisTemplate.opsForZSet().reverseRange(key, RANGE_INDEX, SIZE);
    }

    //滚动榜单的实现  两日的滚动榜单

    /**
     * 思路 ： 在对现在的滚动榜操作的时候 同时 计算当前key  的 score 减去 最后一天的 score 并且存入下一个 滚动榜单
     * 对于没有访问过的key  需要 redis工具进行补充。这里使用 spring 的调度框架区操作redis
     * @param questionid
     * @param title
     */
    public void addscrollboard(Long questionid,String title){
        Date date = new Date();
        int day = CommonUtils.calDayInYear(date);
        String key = SCROLL_BOARD_KEY_PREFIX+day;
        String val = questionid + ":" + title;
        redisTemplate.opsForZSet().incrementScore(key, val, 1);
        Double score1 = redisTemplate.opsForZSet().score(key, val);

        int day1 = day - OFFSET;
        if (day1 < 1) day1 = 365 + day1;
        key = DayBOARD_KEY_PREFIX+day1;
        Double score2 = redisTemplate.opsForZSet().score(key,val);

        int day2 = day + 1;
        if (day2 > 365) day2 = day2 % 365;
        key = SCROLL_BOARD_KEY_PREFIX+day2;
        Double score = score1 - score2;
        redisTemplate.opsForZSet().add(key, val, score);
    }

    public Set<String> getscrollboard(){
        Date date = new Date();
        int day = CommonUtils.calDayInYear(date);
        String key = SCROLL_BOARD_KEY_PREFIX+day;
        return redisTemplate.opsForZSet().reverseRange(key, RANGE_INDEX, SIZE);
    }



}
