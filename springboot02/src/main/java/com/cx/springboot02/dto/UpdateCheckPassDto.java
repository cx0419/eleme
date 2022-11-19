package com.cx.springboot02.dto;

import lombok.Data;

@Data
public class UpdateCheckPassDto {
    Long shopId;
    Boolean pass;
    String auditRemark;
}
