package com.imnoob.community.entities;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class GithubUser {

    private String name;
    private Long id;
    private String bio;
    private String avatarUrl;
}
