package com.imnoob.community.model;

import lombok.Data;

@Data
public class Reporter {
    private Long id;
    private Integer type;
    private Long reporterId;
    private Long reportedId;
    private Long gmtCreate;
    private Integer reportType;
    private String otherInfo;
    private Long contentId;
}
