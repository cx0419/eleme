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
public class Food implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "sku_id", type = IdType.AUTO)
    private Long skuId;

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

    private Boolean isDelete;


}
