package com.imnoob.community.dto;

import com.imnoob.community.model.Reporter;
import lombok.Data;

@Data
public class ReportDTO {

    private Long id;
    private Integer type;
    private Long reporterId;
    private Long reportedId;
    private Long gmtCreate;
    private Integer reportType;
    private String otherInfo;
    private Long contentId;
}
