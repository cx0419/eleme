package com.cx.springboot02.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Address {

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
     *
     */
    private boolean sex;


    /**
     *
     */
    private String createTime;

    private String tag;

}
