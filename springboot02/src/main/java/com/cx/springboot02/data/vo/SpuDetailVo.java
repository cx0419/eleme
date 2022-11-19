package com.cx.springboot02.data.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cx.springboot02.pojo.Food;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class SpuDetailVo  implements Serializable {
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
    private Boolean isSingle;

    /**
     * 打包费 [如果是单价商品才会有,否则为空]
     */
    private Float packingFee;


    /**
     * 价格 [如果是单价商品才会有,否则为空]
     */
    private Float price;


    /**
     * 评分
     */
    private Float rating;

    /**
     * 月售
     */
    private int monthSales;


    /**
     *sku列表
     */
    private List<SkuDetailVo> skuList;


    /**
     * 商家id
     */
    private Long shopId;

    private String ShopName;

    private String shopAddress;

    private String categoryName;

    /**
     * 是否被删除
     */
    private Boolean isDelete;

    private String deleteState;

}
