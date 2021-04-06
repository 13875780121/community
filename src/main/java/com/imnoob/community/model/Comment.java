package com.imnoob.community.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("Comment")
public class Comment {
    private Long id;

    private Long parentId;

    private Integer type;


    private Long userId;


    private Long gmtCreate;


    private Long gmtModified;


    private Long likeCount;


    private String content;


    private Integer commentCount;
}
