package com.cx.springboot02.common.E;

public enum ShopCheckNum {

    NO_CHECK(-1, "未审核"),
    CHECK_NOT_PASS(0,"审核未通过"),
    CHECK_PASS(1,"审核通过"),
    ;

    private Integer code;
    private String message;
    ShopCheckNum(Integer code, String message) {
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


    /**
     * 通过code获取msg
     * @param code
     * @return
     */
    public static String getMessageByCode(Integer code) {
        for (ShopCheckNum ele : values()) {
            if (ele.getCode().equals(code)) {
                return ele.getMessage();
            }
        }
        return null;
    }
}
