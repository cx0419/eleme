package com.cx.springboot02.config;

import com.alibaba.fastjson.JSONObject;
import com.cx.springboot02.common.E.AuthorizeType;
import com.cx.springboot02.common.E.ResultCode;
import com.cx.springboot02.common.ResultTool;
import com.cx.springboot02.common.redis.RedisOperator;
import com.cx.springboot02.common.utils.*;

import com.cx.springboot02.service.impl.BusinessServiceImpl;
import com.cx.springboot02.service.impl.CustomerServiceImpl;
import com.cx.springboot02.service.impl.CustomerServiceServiceImpl;
import com.cx.springboot02.service.impl.ManagerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * 验证token，是否登录
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    RedisOperator redisOperator;

    @Autowired
    CustomerServiceImpl iCustomerService;

    @Autowired
    BusinessServiceImpl iBusinessService;

    @Autowired
    CustomerServiceServiceImpl iCustomerServiceService;

    @Autowired
    ManagerServiceImpl iManagerService;


    /**
     * 忽略拦截的url
     */
    private final List<String> urls = Arrays.asList(
            "/customer/login",
            "/upload",
            "/error",
            "/verifyCode",
            "/image"
    );
    @Autowired
    CustomerServiceImpl customerService;
    /**
     * 进入controller层之前拦截请求
     * @param httpServletRequest
     * @param httpServletResponse
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        String uri = httpServletRequest.getRequestURI();
        System.out.println("本次准备拦截的路径:"+uri);
        httpServletResponse.setContentType("application/json; charset=utf-8");
//        httpServletResponse.setHeader("Access-Control-Allow-Credentials","true");
//        httpServletResponse.setHeader("Access-Control-Allow-Origin", httpServletRequest.getHeader("Origin"));


        if(HttpMethod.OPTIONS.toString().equals(httpServletRequest.getMethod())) {
            System.out.println("options请求.放行");
            return true;
        }

        if(!(handler instanceof HandlerMethod)){
            System.out.println("如果不是映射到方法直接通过");
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //拿到clz对象
        Class clz = method.getClass();



        //不拦截路径（登录路径等等）
        for (String url : urls) {
            if(uri.contains(url)) {
                System.out.println("不拦截的路径:"+url);
                return true;
            }
        }

        Cookie[] cookies = httpServletRequest.getCookies();
        System.out.println("cookie:");
        if(cookies!=null){
            for (Cookie cookie : cookies) {
                System.out.println(cookie.getName()+":"+cookie.getValue());
            }
        }

        //判断是否有放行注解
        Unobstructed unobstructed = method.getAnnotation(Unobstructed.class);
        if(unobstructed!=null) return true;//有就直接放行
        //2.拿到请求头里面的token（如果是第一次登录，那么是没有请求头的）
        String token = httpServletRequest.getHeader("token");
        if(token==null){
            //2.1 拦截请求并返回信息给前台 （前台后置拦截器就是根据这里面返回的json数据，来判读并跳转到登录界面）
            httpServletResponse.getWriter().print(JSONObject.toJSON(ResultTool.fail(ResultCode.USER_ACCOUNT_TOKEN_NULL)));
            return false;
        }else{
            //根据前端的token取出对应的身份(枚举类),根据前端的token取出对应的name,password
            AuthorizeType identity = AuthorizeType.StringToAuthorizeType(JwtUtils.getValue(token, "identity"));
            String name = JwtUtils.getValue(token, "name");
            String password = JwtUtils.getValue(token, "password");
            System.out.println("jwt:"+name);
            System.out.println("jwt:"+password);
            if(password==null || name==null || identity==null) {
                httpServletResponse.getWriter().print(JSONObject.toJSON(ResultTool.fail(ResultCode.USER_ACCOUNT_TOKEN_ERROR)));
                return false;
            }

            String token_redis = null;
            //判断name和身份去对应的表当中身份,看是否存在此用户,存在就继续验证,不存在就直接返回
            if(identity.equals(AuthorizeType.CUSTOMER)){
                token_redis = redisOperator.get(name+Final.CUSTOM_TOKEN);
                if(iCustomerService.login(name, password)==null){
                    httpServletResponse.getWriter().print(JSONObject.toJSON(ResultTool.fail(ResultCode.USER_ACCOUNT_TOKEN_ERROR)));
                    return false;
                }
            }else if(identity.equals(AuthorizeType.MANAGE)){
                token_redis = redisOperator.get(name+Final.MANAGE_TOKEN);
                if(iManagerService.login(name, password)==null){
                    httpServletResponse.getWriter().print(JSONObject.toJSON(ResultTool.fail(ResultCode.USER_ACCOUNT_TOKEN_ERROR)));
                    return false;
                }
            }else if(identity.equals(AuthorizeType.CUSTOMER_SERVICE)){
                token_redis = redisOperator.get(name+Final.CUSTOM_SERVICE_TOKEN);
                if(iCustomerServiceService.login(name, password)==null){
                    httpServletResponse.getWriter().print(JSONObject.toJSON(ResultTool.fail(ResultCode.USER_ACCOUNT_TOKEN_ERROR)));
                    return false;
                }
            }else if(identity.equals(AuthorizeType.BUSINESS)){
                token_redis = redisOperator.get(name+Final.BUSINESS_TOKEN);
                if(iBusinessService.login(name, password)==null){
                    httpServletResponse.getWriter().print(JSONObject.toJSON(ResultTool.fail(ResultCode.USER_ACCOUNT_TOKEN_ERROR)));
                    return false;
                }
            }else{
                //没有此身份
                httpServletResponse.getWriter().print(JSONObject.toJSON(ResultTool.fail(ResultCode.USER_ACCOUNT_TOKEN_ERROR)));
                return false;
            }

            //判断token是否正确或者过时,从redis当中根据name取出对应的token,这里redis以name+":"+"token"为key的方式去取
            //如果token不满足
            if(!(token.equals(token_redis))){
                httpServletResponse.getWriter().print(JSONObject.toJSON(ResultTool.fail(ResultCode.USER_ACCOUNT_TOKEN_ERROR)));
                return false;
            }

            //判断是否有权限发此请求
            //拿到方法上的注解,为空或者没有权限参数的方法不给访问
            PreAuthorize preAuthorize = method.getAnnotation(PreAuthorize.class);
            PreAuthorize preAuthorizeCLZ = (PreAuthorize) clz.getAnnotation(PreAuthorize.class);
            //当类上没有注解时 并且 方法上也没有注解的时候
            if((preAuthorize==null || preAuthorize.values().length==0)&& (preAuthorizeCLZ==null || preAuthorizeCLZ.values().length==0)){
                httpServletResponse.getWriter().print(JSONObject.toJSON(ResultTool.fail(ResultCode.NO_PERMISSION)));
                return false;
            }
            //拿到类上的权限列表,判断类上的注解是否符合
            AuthorizeType[] values = preAuthorizeCLZ.values();
            for (AuthorizeType authorizeType : values) {
                if(identity.equals(authorizeType)){
                    return true;
                }
            }
            //拿到方法上面的权限列表
            assert preAuthorize != null;values = preAuthorize.values();
            for (AuthorizeType authorizeType : values) {
                if(identity.equals(authorizeType)){
                    return true;
                }
            }
        }

        httpServletResponse.getWriter().print(JSONObject.toJSON(ResultTool.fail(ResultCode.COMMON_FAIL)));
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        // System.out.println("处理请求完成后视图渲染之前的处理操作");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        // System.out.println("视图渲染之后的操作");
    }
}

