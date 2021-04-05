package com.imnoob.community.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccessToken {

    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}
