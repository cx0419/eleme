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
public class SpecsCategory implements Serializable {

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


}
