package com.imnoob.community.model;

import lombok.Data;

@Data
public class Reporte {
    private Integer id;
    private Integer type;
    private Long reporterId;
    private Long gmtCreate;
    private Integer reportType;
    private String otherInfo;
}
