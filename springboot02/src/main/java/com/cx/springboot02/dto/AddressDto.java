package com.cx.springboot02.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AddressDto {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 顾客id
     */
    private Long cid;

    /**
     * 经度
     */
    private Double lon;


    /**
     * 纬度
     */
    private Double lat;

    /**
     * 姓名 接收人
     */
    private String name;


    /**
     * 电话
     */
    private String phone;


    /**
     * 详细地址
     */
    private String specificAddress;

    /**
     *先生/女士
     */
    private boolean sex;


    /**
     *
     */
    private String createTime;


    private String tag;


    /**
     * 是否为有效地址
     */
    private boolean isDeliverable;



}
