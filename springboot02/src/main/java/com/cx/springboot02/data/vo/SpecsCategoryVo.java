package com.cx.springboot02.data.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cx.springboot02.pojo.Food;
import com.cx.springboot02.pojo.Specs;
import lombok.Data;

import java.util.List;


@Data
public class SpecsCategoryVo {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 规格分类名称
     */
    private String name;

    /**
     * 属于哪个spu
     */
    private Long goodsId;


    private int sort;

    /**
     * 存储对应的sku列表
     */
    private List<Specs> specsList;

}
