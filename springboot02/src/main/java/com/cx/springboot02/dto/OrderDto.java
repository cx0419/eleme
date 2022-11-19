package com.cx.springboot02.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class OrderDto {

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

    /**
     * 用户id
     */
    private Long customerId;


    /*
    **购物车id
     */
    private Long buycartId;


    /**
     * 商店id
     */
    private Long shopId;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 坐标
     */
    private String geohash;



    private List<CartDataDto> groups;


}
