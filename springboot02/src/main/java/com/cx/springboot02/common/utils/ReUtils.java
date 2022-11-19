package com.cx.springboot02.common.utils;

import com.cx.springboot02.common.JsonResult;
import com.cx.springboot02.common.E.ResultCode;

public class ReUtils<Q> {
    public static  <T> JsonResult<T> success(T data){
        return new JsonResult<>(true,data);
    }
    public static  <T> JsonResult<T> fail(ResultCode resultCode){
        return new JsonResult<>(false,resultCode);
    }

    public  Q Test(Q q){
        return q;
    }

}
