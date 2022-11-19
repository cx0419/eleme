package com.cx.springboot02.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AddShopDto {

    private static final long serialVersionUID = 1L;


    private Long shopId;

    /**
     * 商家名字
     */
    private String name;

    /**
     * 餐馆地址
     */
    private String address;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 食品分类
     */
    private String category;

    /**
     * 店铺图片地址
     */
    private String imagePath;

    /**
     * 运费
     */
    private Integer floatDeliveryFee;

    /**
     * 起送价
     */
    private Integer floatMinimumOrderAmount;

    /**
     * 餐馆介绍
     */
    private String description;

    /**
     * 店铺标语
     */
    private String promotionInfo;

    /**
     * 品牌商铺
     */
    private Boolean isPremium;

    /**
     * 支持蜂鸟
     */
    private Boolean deliveryMode;

    /**
     * 新开店铺
     */
    private Boolean newShop;

    /**
     * 支持保险
     */
    private Boolean bao;

    /**
     * 准时达
     */
    private Boolean zhun;

    /**
     * 开发票
     */
    private Boolean piao;

    /**
     * 营业时间
     */
    private String startTime;

    /**
     * 打烊时间
     */
    private String endTime;

    /**
     * 营业执照图片地址
     */
    private String businessLicenseImage;

    /**
     * 餐饮服务许可证图片地址
     */
    private String cateringServiceLicenseImage;
}
