package com.cx.springboot02.dto;


import lombok.Data;

@Data
public class AddSpecsDto {
    /**
     * 规格分类的id
     */
    private Long specsCategoryId;

    /**
     * 规格的名称
     */
    private String name;

    /**
     * 规格的描述
     */
    private String description;

}
