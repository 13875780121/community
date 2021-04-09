package com.imnoob.community.model;

import lombok.Data;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@Data
@ToString
@Alias("User")
public class User {
    private Long id;
    private String accountId;
    private String name;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String bio;
    private String avatarUrl;
}
