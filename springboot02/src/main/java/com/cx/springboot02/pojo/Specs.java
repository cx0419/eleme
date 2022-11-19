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
public class Specs implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 规格主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 规格名称
     */
    private String name;

    /**
     * 规格描述
     */
    private String description;

    /**
     * 在spu对应列中的排序位置
     */
    private Integer specsListSort;

    /**
     * 在前端的排序位置
     */
    private Integer sort;

    private Long specsCategoryId;


}
