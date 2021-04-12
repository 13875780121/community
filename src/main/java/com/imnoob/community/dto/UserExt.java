package com.imnoob.community.dto;

import lombok.Data;

@Data
public class UserExt {
    private Long id;
    private String accountId;
    private String token;

    private String IpAddr;

}
