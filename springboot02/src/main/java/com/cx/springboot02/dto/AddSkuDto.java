package com.cx.springboot02.dto;


import lombok.Data;

@Data
public class AddSkuDto {

    /**
     * 规格列表 123|123123|123123|12313|123123... 形式 根据每个规格对应的排序位置确定
     */
    private String specsList;

    /**
     * 价格
     */
    private Float price;

    /**
     * 打包费
     */
    private Float packingFee;

    /**
     * 库存容量
     */
    private Integer stock;

    private Long goodsId;


}
