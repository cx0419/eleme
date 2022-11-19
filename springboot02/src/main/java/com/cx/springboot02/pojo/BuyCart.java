package com.cx.springboot02.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class BuyCart {

//    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private Long customerId;


    /**
     * 会话id
     */
    private String sessionId;

    /**
     * 商店id
     */
    private Long shopId;


    /**
     * 购物车列表数据数据
     */
    private String entities;


    /**
     * 前端购物车数据结构中商品id对应的js对象
     */
    private String jsonEntities;


    /**
     * 创建时间
     */
    private String createTime;


    /**
     * 备注
     */
    private String remarks;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
