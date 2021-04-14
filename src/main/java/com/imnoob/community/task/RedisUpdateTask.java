package com.imnoob.community.task;

import com.imnoob.community.Utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;

@Component
public class RedisUpdateTask {
    @Autowired
    RedisTemplate<String,String> redisTemplate;
    //可以抽取变量
    final static String DayBOARD_KEY_PREFIX = "dayboard::";
    final static String SCROLL_BOARD_KEY_PREFIX = "scroll::";
    final static Integer OFFSET = 2;        //滚动榜单的跨度  2 天

    @Scheduled(cron = "0 0 3 ? * *")
    public void fixScrollboard(){
        //
        Date date = new Date();
        int now = CommonUtils.calDayInYear(date);
        String key = SCROLL_BOARD_KEY_PREFIX + now;
        Set<String> nset = redisTemplate.opsForZSet().range(key, 1, -1);

        int delday = now  - 1;
        if (delday < 1) delday = 365 + delday;
        String key1 = SCROLL_BOARD_KEY_PREFIX + delday;
        Set<String> all = redisTemplate.opsForZSet().range(key1, 1, -1);

        int delday1 = now - OFFSET - 1;
        if (delday1 < 1) delday1 = 365 + delday1;
        String key2 = DayBOARD_KEY_PREFIX+delday1;
        for (String s : all) {
            if (!nset.contains(s)){
                Double sc1 = redisTemplate.opsForZSet().score(key1, s);
                Double sc2 = redisTemplate.opsForZSet().score(key2, s);
                redisTemplate.opsForZSet().add(key, s, sc2-sc1);
            }
        }
        redisTemplate.delete(key1);
    }

    /**
     * 计划凌晨两点对redis进行操作
     */
    @Scheduled(cron = "0 0 3 ? * *")
    public void deldayboard(){
        //删除过期的日榜
        Date date = new Date();
        int now = CommonUtils.calDayInYear(date);
        int delday = now - OFFSET - 1;
        if (delday < 1) delday = 365 + delday;
        String key = DayBOARD_KEY_PREFIX + delday;
        redisTemplate.delete(key);
    }
}
