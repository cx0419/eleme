package com.cx.springboot02.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cx.springboot02.pojo.Goods;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * 该类为菜单+食物类
 */
@Data
public class MenuDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)

    /**
     * 菜单分类的id
     */
    private Long categoryId;

    /**
     * 菜单的描述
     */
    private String description;


    /**
     * 商家id
     */
    private Long businessId;

    /**
     * 名称
     */
    private String name;


    /**
     * 对应的食物列表
     */
    List<FoodDto> foodDto;


}
