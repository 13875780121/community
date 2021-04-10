package com.imnoob.community.dto;

import com.imnoob.community.model.User;
import lombok.Data;

@Data
public class NoticeDTO {
    private Long id;
    private Long noticer;
    private Long outerId;
    private Integer type;
    private Long gmtCreate;
    private Integer status;
    private Long receiver;
    private String outerName;

    private String notifierName;
    private String typeName;

}
