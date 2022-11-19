package com.cx.springboot02.dto;

import lombok.Data;

/**
 * 更新单价商品需要的信息
 */
@Data
public class UpdateSpuSingleDto {

    /**
     * 需要更新的spuid
     */
    private Long goodsId;

    /**
     * 价格
     */
    private Float price;

    /**
     * 打包费
     */
    private Float packingFee;

    /**
     * spu的name
     */
    private String name;


    /**
     * spu的描述
     */
    private String description;

    /**
     * 图片路径
     */
    private String imagePath;

    private Long categoryId;
}
