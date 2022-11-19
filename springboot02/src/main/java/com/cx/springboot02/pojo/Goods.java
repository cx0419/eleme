package com.cx.springboot02.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author 陈翔
 * @since 2022-11-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Goods implements Serializable {

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


}
