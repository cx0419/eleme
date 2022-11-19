package com.cx.springboot02.common.E;



public enum AuthorizeType {

    /** 有没有发现写枚举类的这一部分就像是在调用构造方法 */
    CUSTOMER("customer"),
    BUSINESS("business"),
    MANAGE("manager"),
    CUSTOMER_SERVICE("customerService"),
    //上面的逗号可去可不去, 并不会导致编译错误
    ;

    private String identity;

    public final static AuthorizeType[] authorizeTypes = {
            AuthorizeType.CUSTOMER,
            AuthorizeType.BUSINESS,
            AuthorizeType.CUSTOMER_SERVICE,
            AuthorizeType.MANAGE,
    };
    //构造函数默认并且必须是private
    AuthorizeType(String identity) {
        this.identity = identity;
    }


    public String identity() {
        return this.identity;
    }


    /**
     * 根据code获取message
     *
     * @param name
     * @return
     */
    public static AuthorizeType StringToAuthorizeType(String name) {
        for (AuthorizeType ele : AuthorizeType.values()) {
            if (ele.identity().equals(name)) {
                return ele;
            }
        }
        return null;
    }
}
