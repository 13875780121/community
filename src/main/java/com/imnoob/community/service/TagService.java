package com.imnoob.community.service;

import com.imnoob.community.dto.TagsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    @Autowired
    private  RedisTemplate<String,String> redisTemplate;

    @Value(value = "${tagsKey}")
    private  String tagsKey;

    public  TagsDTO getAllTags(){
        String[] split = tagsKey.split(",");
        TagsDTO tagsDTO = new TagsDTO();

        for (String key : split) {
            List<String> list = redisTemplate.opsForList().range(key, 0, -1);
            tagsDTO.addTag(key,list);
        }

        return tagsDTO;
    }
}
