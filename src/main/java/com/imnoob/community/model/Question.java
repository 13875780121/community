package com.imnoob.community.model;

import lombok.Data;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@Alias("Question")
@Data
@ToString
public class Question {
    private Long id;


    private String title;


    private Long gmtCreate;


    private Long gmtModified;


    private Long creator;


    private Integer commentCount;


    private Integer viewCount;


    private Integer likeCount;


    private String tag;


    private Integer sticky;


    private String description;
}
