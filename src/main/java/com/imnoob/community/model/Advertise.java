package com.imnoob.community.model;

import lombok.Data;

@Data
public class Advertise {

    private Long id;
    private Long companyId;
    private Long medieId;
    private Integer playCount;
    private Integer clickCount;
    private String tag;
    private Integer status;
    private Long gmtCreate;
    private Long gmtModified;


}
