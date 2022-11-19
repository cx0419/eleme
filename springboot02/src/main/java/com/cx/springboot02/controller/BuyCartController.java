package com.cx.springboot02.controller;

import com.alibaba.fastjson.JSONObject;
import com.cx.springboot02.common.JsonResult;
import com.cx.springboot02.common.E.ResultCode;
import com.cx.springboot02.common.ResultTool;
import com.cx.springboot02.common.utils.*;
import com.cx.springboot02.dto.CartDataDto;
import com.cx.springboot02.pojo.BuyCart;
import com.cx.springboot02.service.impl.BuyCartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buyCart")
public class BuyCartController {
    @Autowired
    BuyCartServiceImpl buyCartService;


    @Autowired
    JwtUtils jwtUtils;


    @PostMapping("/check")
    @Unobstructed
    public JsonResult<Object> createBuyCart(HttpServletRequest request,@RequestBody Map<String,Object> mp){
        try {
            Long shopId = Long.valueOf((Integer)mp.get("shopId"));
            String entities = (String) mp.get("entities");
            String JSONEntities = (String) mp.get("jsonEntities");
            //检查是否能够转换成java对象,否则抛出异常 被捕获
            JSONObject.parseArray(entities,  CartDataDto.class);
            String remarks = (String) mp.get("remarks");
            BuyCart buyCart = new BuyCart();
            buyCart.setShopId(shopId);
            buyCart.setEntities(entities);
            buyCart.setJsonEntities(JSONEntities);
            buyCart.setCreateTime(DateUtils.getCurrentTime());
            buyCart.setRemarks(remarks);
            String sessionId = WebUtil.getSessionId(request);
            buyCart.setSessionId(sessionId);
            buyCartService.InsertBuyCart(buyCart);
            return ResultTool.success(buyCart);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
       return ResultTool.fail(ResultCode.PARAM_NOT_VALID);
    }

}
