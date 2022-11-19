package com.cx.springboot02.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cx.springboot02.pojo.Business;
import com.cx.springboot02.pojo.BuyCart;
import lombok.Data;

import java.util.List;


/**
 * 该dto用于订单列表展示
 */
@Data
public class OrderListDto {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 接收地址的id
     */
    private Long addressId;

    private Long customerId;

    private Float totalPrice;

    /**
     * 购物车信息
     */
    private Long buycartId;
    //购物车
    private BuyCart buyCart;
    //购物车里面商品的数据信息 解析 entites 得到
    private List<CartDataDto> BuyCartSkuList;


    /**
     * 商店信息
     */
    private Long shopId;
    private Business shop;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 坐标
     */
    private String geohash;


    /**
     * 支付状态
     */
    private int orderState;
    private String orderStateStr;


    /**
     * 创建时间
     */
    private String createTime;

    private String TimeRemaining;


}
