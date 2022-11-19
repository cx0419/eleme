package com.cx.springboot02.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cx.springboot02.mapper.BuyCartMapper;
import com.cx.springboot02.pojo.BuyCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BuyCartServiceImpl  extends ServiceImpl<BuyCartMapper, BuyCart> {
    @Autowired
    BuyCartMapper buyCartMapper;



    public Long InsertBuyCart(BuyCart buyCart){
        return buyCartMapper.insertBuyCart(buyCart);
    }


    /**
     * 根据商店id和用户id去查找是否存在一条购物车信息
     * @param CustomerId
     * @param ShopId
     */
    public BuyCart getByCustomerIdAndShopId(Long CustomerId,Long ShopId){
        Map<String,Object> mp = new HashMap<>();
        mp.put("Customer_id",CustomerId);
        mp.put("Shop_id",ShopId);
        List<BuyCart> buyCarts = buyCartMapper.selectByMap(mp);
        if(buyCarts.size()==0) return null;
        return  buyCarts.get(0);

    }





}
