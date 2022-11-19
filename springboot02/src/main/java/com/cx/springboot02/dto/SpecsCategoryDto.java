package com.cx.springboot02.dto;

import com.cx.springboot02.pojo.Specs;
import lombok.Data;

import java.util.List;

/**
 * 用于存储对应的规格列表
 */
@Data
public class SpecsCategoryDto {

    /**
     * 分类的主键
     */
    private  Long id;

    /**
     * 名字
     */
    private String name;

    /**
     * 排序
     */
    private int sort;

    /**
     * 存储的规格
     */
    private List<Specs> specsList;
}
