package com.cx.springboot02.data.vo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 非规格spu 传输给需要修改的spu列表的单项
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SingleSpuVo {

    private static final long serialVersionUID = 1L;

    /**
     * spu id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

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

    //因为是无规格商品 所有商品的sku信息直接放在这里

    private Long skuId;


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


    private Boolean isDelete;
}
