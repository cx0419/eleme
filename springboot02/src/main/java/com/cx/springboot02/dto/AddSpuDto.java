package com.cx.springboot02.dto;


import lombok.Data;

@Data
public class AddSpuDto {
    /**
     * spu名称
     */
    private String name;

    /**
     * spu分类
     */
    private Long categoryId;

    /**
     * spu 图片
     */
    private String imagePath;

    /**
     * spu 描述
     */
    private String description;

    /**
     * 最少需要多少个规格
     */
    private Integer minSpecsCount;


    /**
     * 是否含有规格
     */
    private boolean isSingle;
}
