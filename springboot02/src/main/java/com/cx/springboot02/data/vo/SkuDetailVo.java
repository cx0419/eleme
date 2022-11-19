package com.cx.springboot02.data.vo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class SkuDetailVo  implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long skuId;

    /**
     * 规格列表 123|123123|123123|12313|123123... 形式 根据每个规格对应的排序位置确定
     */
    private String specsList;

    /**
     * 规格名称列表:[普通辣,加冰,..]
     */
    private List<String> specsValue;

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
