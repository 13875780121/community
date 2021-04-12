package com.imnoob.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    RedisTemplate<String,String> redisTemplate;

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

    public boolean isthumbUp(Long commentId,Long useId){
        String key = "thumbup:"+commentId;
        String val = useId.toString();
        Boolean isthumbup = redisTemplate.opsForSet().isMember(key, val);
        if (isthumbup){
            return isthumbup;
        }else{
            redisTemplate.opsForSet().add(key, val);
            return isthumbup;
        }

    }


}
