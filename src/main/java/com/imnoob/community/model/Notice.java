package com.imnoob.community.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("Notice")
public class Notice {
    private Long id;
    private Long noticer;
    private Long outerId;
    private Integer type;
    private Long gmtCreate;
    private Integer status;
    private Long receiver;
    private String outerName;
}
