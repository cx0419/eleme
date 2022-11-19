package com.cx.springboot02.dto;


import lombok.Data;

@Data
public class SpecFoodsDto {

    /**
     * 商家食物分类id
     */
    private Long categoryId;

    /**
     * spu
     */
    private Long spuId;

    private Long foodId;

    private Float price;

    private Float packing_fee;



}
