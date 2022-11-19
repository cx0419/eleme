package com.cx.springboot02.controller;


import com.alibaba.fastjson.JSONObject;
import com.cx.springboot02.common.E.PayState;
import com.cx.springboot02.common.JsonResult;
import com.cx.springboot02.common.E.ResultCode;
import com.cx.springboot02.common.RPage;
import com.cx.springboot02.common.ResultTool;
import com.cx.springboot02.common.mq.ProducerSchedule;
import com.cx.springboot02.common.utils.DateUtils;
import com.cx.springboot02.common.utils.Unobstructed;
import com.cx.springboot02.dto.OrderListDto;
import com.cx.springboot02.dto.ShopDto;
import com.cx.springboot02.pojo.Order;
import com.cx.springboot02.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 陈翔
 * @since 2022-10-05
 */
@RestController
@RequestMapping("/order")
public class OrderController {


    @Autowired
    OrderServiceImpl orderService;


    @Autowired
    private ProducerSchedule producerSchedule;

    @PostMapping("/save")
    @Unobstructed
    public JsonResult<Object> saveOrder(@RequestBody Map<String,Object> mp){
        try {
            Long addressId = Long.valueOf((Integer)mp.get("addressId"));
            Long customer = Long.valueOf((Integer)mp.get("customerId"));
            Long buycartId = Long.valueOf((Integer)mp.get("buycartId"));
            Long shopId = Long.valueOf((Integer)mp.get("shopId"));
            String geohash = (String) mp.get("geohash");
            String remarks = (String) mp.get("remarks");
            Order order = new Order();
            order.setAddressId(addressId);
            order.setBuycartId(buycartId);
            order.setShopId(shopId);
            order.setGeohash(geohash);
            order.setRemarks(remarks);
            order.setCreateTime(DateUtils.getCurrentTime());
            //设置为未支付
            order.setOrderState(PayState.UNPAID.getCode());
            order.setCustomerId(customer);
            orderService.saveOderInfo(order);
            //生产者生产推送消息
            producerSchedule.send("Topic", JSONObject.toJSONString(order));
            return ResultTool.success(order);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResultTool.fail(ResultCode.PARAM_NOT_VALID);
        }
    }


    /**
     * 支付接口
     * @param mp
     * @return
     */
    @PutMapping("/pay")
    @Unobstructed
    public JsonResult<Order> pay(@RequestBody Map<String,Object> mp){
        try {
            Long id =Long.valueOf( ((Integer)mp.get("oid")));
            Order order = new Order();
            order.setId(id);
            orderService.updatePayById(order,PayState.PAID);
            return ResultTool.success(order);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResultTool.fail(ResultCode.PARAM_NOT_VALID);
        }
    }



    /**
     * 根据订单id获取到某个订单
     * @param mp
     * @return
     */
    @GetMapping("/{id}")
    @Unobstructed
    public JsonResult<Object> gettime(@RequestBody Map<String, Object> mp, @PathVariable String id){
        Long oid =Long.valueOf(id);
        Order order = orderService.getById(oid);
        return ResultTool.success(order);
    }

    /**
     * 根据用户id获取到订单列表 分页处理
     * @param pagenum 第几页
     * @param size 每页多少条数据
     * @param key 关键词 可以为空
     * @param cid   用户id 可以为空
     * @return
     */
    @GetMapping("/list/")
    @Unobstructed
    public JsonResult<RPage<OrderListDto>> getList(@PathParam("pagenum")Integer pagenum,@PathParam("size")Integer size,@PathParam("key")String key,@PathParam("cid") String cid){
        if(pagenum==null || size==null || cid==null) return ResultTool.fail(ResultCode.FIELD_IS_EMPTY);
        RPage<OrderListDto> orderListPage = orderService.getOrderListPage(pagenum,size,cid,key);
        return ResultTool.success(orderListPage);
    }


    /**
     * 获取已支付的订单列表
     * @return
     */
    @GetMapping("/payState/havePay")
    @Unobstructed
    public JsonResult<List<Order>> getHavePayOrder(){
        return ResultTool.success(orderService.getPayStateOrderList(PayState.PAID));
    }


    /**
     * 获取正在派送的订单列表
     * @return
     */
    @GetMapping("/payState/Dispatch")
    @Unobstructed
    public JsonResult<List<Order>> getDispatchOrder(){
        return ResultTool.success(orderService.getPayStateOrderList(PayState.PAID));
    }


    /**
     * 获取已完成的订单列表
     * @return
     */
    @GetMapping("/payState/finish")
    @Unobstructed
    public JsonResult<List<Order>> getFinishorderList(){
        return ResultTool.success(orderService.getPayStateOrderList(PayState.TRANSACTION_COMPLETION));
    }




    /**
     * 商家接单 将状态从 已支付状态 => 正在派送状态
     * @return
     */
    @PutMapping("/orderReceiving")
    @Unobstructed
    public JsonResult<Object> orderReceiving(@RequestBody Map<String,Object> mp){
        int id = (int) mp.get("oid");
        Order order = new Order();
        order.setId((long) id);
        orderService.updatePayById(order,PayState.DISPATCH);
        return ResultTool.success();
    }

    /**
     * 商家取消订单 将订单状态:已支付状态 => 订单取消 售后状态:退款成功
     * @return
     */
    @PutMapping("/CancelOrderReceiving")
    @Unobstructed
    public JsonResult<Object> CancelOrderReceiving(@RequestBody Map<String,Object> mp){
        int id = (int) mp.get("oid");
        Order order = new Order();
        order.setId((long) id);
        orderService.updateAfterSalesStatusById(order,PayState.REFUND_COMPLETED);
        return ResultTool.success(null);
    }


    /**
     * 商家接单 将状态从 正在派送状态 => 订单完成
     * @return
     */
    @PutMapping("/orderFinish")
    @Unobstructed
    public JsonResult<Object> orderFinish(@RequestBody Map<String,Object> mp){
        int id = (int) mp.get("oid");
        Order order = new Order();
        order.setId((long) id);
        orderService.updatePayById(order,PayState.TRANSACTION_COMPLETION);
        return ResultTool.success();
    }



    /**
     * 更新评论状态 从未评论到已评论
     * @return
     */
    @PutMapping("/updateCommentState")
    @Unobstructed
    public JsonResult<Object> updateCommentState(@RequestBody Map<String,Object> mp){
        int id = (int) mp.get("oid");
        Order order = new Order();
        order.setId((long) id);
        orderService.updateCommentStatusById(order,PayState.EVALUATED);
        return ResultTool.success(null);
    }


}

