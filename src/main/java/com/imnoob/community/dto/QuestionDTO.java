package com.imnoob.community.dto;

import com.imnoob.community.model.User;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class QuestionDTO {

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
    private User user;
    private Integer status;
    private String statusInfo;
}
