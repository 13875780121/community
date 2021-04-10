package com.imnoob.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
public class TagsDTO {

    private HashMap<String, List<String>> tags = new HashMap<>();

    public void addTag(String key,String tag){

        if (key == null || tag == null) return;

        if (tags.containsKey(key)){
            List<String> list = tags.get(key);
            list.add(tag);
        }else {
            ArrayList<String> list = new ArrayList<>();
            list.add(tag);
            tags.put(key, list);
        }
    }

    public void addTag(String key,List<String> list){

        if (key == null || list == null || list.isEmpty()) return;
        tags.put(key, list);
    }

}
