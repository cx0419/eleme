package com.cx.springboot02.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 陈翔
 * @since 2022-10-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("`Order`")
public class Order implements Serializable {
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

    private Long buycartId;

    private Long shopId;

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


    /**
     * 创建时间
     */
    private String createTime;


    /**
     * 是否已评论
     */
    private int commentState;


    /**
     * 订单到达时间
     */
    private String arrivalTime;


    /**
     * 订单预期送达时间
     */
    private String expectedTime;


    /**
     * 售后状态
     */
    private int afterSalesStatus;
}
