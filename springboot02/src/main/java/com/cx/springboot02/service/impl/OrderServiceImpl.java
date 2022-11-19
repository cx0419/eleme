package com.cx.springboot02.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cx.springboot02.common.E.PayState;
import com.cx.springboot02.common.RPage;
import com.cx.springboot02.dto.CartDataDto;
import com.cx.springboot02.dto.OrderListDto;
import com.cx.springboot02.mapper.*;
import com.cx.springboot02.pojo.Business;
import com.cx.springboot02.pojo.BuyCart;
import com.cx.springboot02.pojo.Order;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 陈翔
 * @since 2022-10-05
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>  {

    @Autowired
    OrderMapper orderMapper;
    @Autowired
    BusinessMapper businessMapper;
    @Autowired
    BuyCartMapper buyCartMapper;
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    FoodMapper foodMapper;

    public void saveOderInfo(Order order){
        orderMapper.insertOrder(order);
    }

    /**
     * 更新订单状态
     * @param order
     * @param payState
     */
    public void updatePayById(Order order, PayState payState){
        order.setOrderState(payState.getCode());
        orderMapper.updateOrder(order);
    }

    /**
     * 更新售后状态
     * @param order
     * @param payState
     */
    public void updateAfterSalesStatusById(Order order, PayState payState){
        order.setAfterSalesStatus(payState.getCode());
        orderMapper.updateOrder(order);
    }

    /**
     * 更新评论状态
     * @param order
     * @param payState
     */
    public void updateCommentStatusById(Order order, PayState payState){
        order.setCommentState(payState.getCode());
        orderMapper.updateOrder(order);
    }

    /**
     * 获取客户端页面的订单列表 包含了其中的商品信息 spu和sku
     * @param pagenum
     * @param size
     * @param cid
     * @param key
     * @return
     */
    public RPage<OrderListDto> getOrderListPage(Integer pagenum, Integer size, String cid, String key){
        RPage<OrderListDto> orderListDtoRPage = new RPage<>(pagenum, size, orderMapper.selectOrderListByCId(size * (pagenum - 1), size, Long.valueOf(cid), key));
        //对dto进行修饰
        for (OrderListDto row : orderListDtoRPage.getRows()) {
            Float totalPrice = 0f;
            row.setOrderStateStr(PayState.getMessage(row.getOrderState()));
            row.setShop(businessMapper.selectBusinessById(row.getShopId()));
            if(row.getBuycartId()!=null){
                BuyCart buyCart = buyCartMapper.selectById(row.getBuycartId());

                //将json对象转化为java对象 在购物车业务当中如果这个对象转化失败就不能够保存至数据库
                List<CartDataDto> cartDataDtos = JSONObject.parseArray(buyCart.getEntities(),  CartDataDto.class);
                //cartDataDtos是购物车里面的sku列表,
                //遍历这个集合 将sku和spu信息补全
                for (CartDataDto dataDto : cartDataDtos) {
                    totalPrice += dataDto.getPrice()*dataDto.getNum();
                    if(dataDto.getSkuId()!=null){
                        dataDto.setSku(foodMapper.selectBySkuId(dataDto.getSkuId()));
                    }
                    if(dataDto.getSpuId()!=null){
                        dataDto.setSpu(goodsMapper.selectById(dataDto.getSpuId()));
                    }
                }
                //设置到里面去
                row.setBuyCart(buyCart);
                row.setBuyCartSkuList(cartDataDtos);
            }
            row.setTotalPrice(totalPrice);
        }
        return  orderListDtoRPage;
    }


    /**
     * 获取指定支付状态的列表
     * @param payState
     */
    public List<Order> getPayStateOrderList(PayState payState){
        Map<String,Object> mp = new HashMap<>();
        mp.put("order_state",payState.getCode());
        List<Order> orders = orderMapper.selectByMap(mp);
        return orders;
    }



}
