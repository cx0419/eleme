package com.cx.springboot02.dto;


import lombok.Data;

import java.io.Serializable;

@Data
public class UpdateDeactivateDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 是否被禁用
     */
    private Boolean deactivate;

    /**
     * 商家Id
     */
    private Long shopId;


}
