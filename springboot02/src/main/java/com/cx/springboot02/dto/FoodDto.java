package com.cx.springboot02.dto;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cx.springboot02.pojo.Food;
import com.cx.springboot02.pojo.Specs;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * spu
 */
@Data
public class FoodDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商品id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long spuId;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 食物分类
     */
    private Long categoryId;

    /**
     * 商品图片
     */
    private String imagePath;

    /**
     * 描述
     */
    private String description;


    /**
     * 活动
     */
    private List<String> activity;

    /*
    **月售
     */
    private Integer monthSales = 0;


    /**
     * 好评率
     */
    private Float satisfyRate = 0f;

    /**
     * 最少需要多少个规格
     */
    private Integer minSpecsCount;


    /**
     * 对应的sku
     */
    private List<Food> foods;

    /**
     * 规格分类列表
     */
    private List<SpecsCategoryDto> specs;

    private boolean isSingle;




}
