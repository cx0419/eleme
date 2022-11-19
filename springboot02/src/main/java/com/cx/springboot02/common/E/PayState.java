package com.cx.springboot02.common.E;

import java.util.HashMap;
import java.util.Map;

public enum PayState {
    /**
     * 支付状态 [必须在等待付款后]
     */
    PAID(101,"已支付"),
    UNPAID(102,"未支付"),
    PAID_TIMEOUT(103,"支付超时"),
    /**
     * 物流状态 [必须在等待发货后]
     */
//    UNSHIPPED(201,"未发货"),
    DISPATCH(202,"正在派送"),
//    SIGNED_IN(203,"已签收"),


    /**
     * 订单状态
     */
//    WAITING_FOR_PAYMENT(301,"等待付款"),
//    WAITING_FOR_SHIPPED(302,""),
    CONFIRM_RECEIPT(303,"确认收货等待发货"),
    TRANSACTION_COMPLETION(304,"交易完成"),
    TRANSACTION_CLOSED(305,"交易关闭"),


    /**
     * 售后状态 [必须在交易关闭以后]
     */
    APPLY_FOR_AFTER_SALES_SERVICE(401,"申请售后"),
    REFUND_COMPLETED(402,"退款完成"),

    /**
     * 评价状态 [必须在交易关闭以后]
     */
    EVALUATED(501,"已评价"),
    NOT_EVALUATED(502,"未评价"),

    ;
    private Integer code;
    private String message;

    PayState(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private static Map<Integer,PayState> payStateHashMap = new HashMap<>();
    static {
        for (PayState value : PayState.values()) {
            payStateHashMap.put(value.code,value);
        }
    }

    public static String getMessage(Integer code){
        return payStateHashMap.get(code).getMessage();
    }

    public static void main(String[] args) {
    }
}
