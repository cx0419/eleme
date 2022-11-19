package com.cx.springboot02.data.vo;

import com.cx.springboot02.pojo.Business;
import lombok.Data;

import java.io.Serializable;


@Data
public class IdentityCheckVo<T> implements Serializable {

    /**
     * 身份
     */
    private String identity;

    /**
     * 具体细节 比如商家 管理员头像名字
     */
    private T detail;

}
