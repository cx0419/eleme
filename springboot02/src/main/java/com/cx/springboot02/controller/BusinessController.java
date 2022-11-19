package com.cx.springboot02.controller;


import com.cx.springboot02.common.E.ShopCheckNum;
import com.cx.springboot02.common.JsonResult;
import com.cx.springboot02.common.RPage;
import com.cx.springboot02.common.E.ResultCode;
import com.cx.springboot02.common.ResultTool;
import com.cx.springboot02.common.utils.JwtUtils;
import com.cx.springboot02.common.redis.RedisOperator;
import com.cx.springboot02.common.utils.StringUtil;
import com.cx.springboot02.common.utils.Unobstructed;
import com.cx.springboot02.common.utils.WebUtil;
import com.cx.springboot02.dto.AddShopDto;
import com.cx.springboot02.dto.UpdateCheckPassDto;
import com.cx.springboot02.dto.UpdateDeactivateDto;
import com.cx.springboot02.pojo.Business;
import com.cx.springboot02.service.impl.BusinessServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
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
@RequestMapping("/business")
public class BusinessController {

    private static final Logger log = LoggerFactory.getLogger(Logger.class);
    @Autowired
    BusinessServiceImpl businessService;

    @Autowired
    RedisOperator redisOperator;

    @PostMapping("/login")
    @Unobstructed
    public JsonResult<Object> login(@RequestBody Map<String, Object> mp, HttpServletResponse response){
        String name = (String) mp.get("name");
        String password = (String) mp.get("password");
        Business business = businessService.login(name, password);
        if(name!=null && password!=null && business!=null){
            //生成token
            String token = JwtUtils.createToken(name, password,"customer");
            //存入redis
            redisOperator.set(name+":Business-token", token);
            //放入请求头
            response.addHeader("Access-Control-Expose-Headers","token");
            response.addHeader("token",token);
            System.out.println(response.getHeader("token"));
            return ResultTool.success(business);
        }else{
            return ResultTool.fail(ResultCode.USER_CREDENTIALS_ERROR);
        }
    }

    /**
     * 获取商家列表 可有条件:比如sid
     * @param pagenum
     * @param size
     * @return
     */
    @GetMapping("/list")
    @Unobstructed
    public JsonResult<Object> getBusinessList(@PathParam("pagenum")Integer pagenum,@PathParam("size")Integer size
            ,@PathParam("key")String key,@PathParam("ClassId")Long ClassId,@PathParam("checkPass") Integer checkPass){
        log.info("{} {} {}  {}",pagenum,size,key,checkPass);
        RPage rPage = null;
        try {
            rPage = businessService.getBusinessList(pagenum, size,key,ClassId,checkPass);
            return ResultTool.success(rPage);
        } catch (Exception exception) {
            exception.printStackTrace();
            return  ResultTool.fail(ResultCode.PARAM_NOT_COMPLETE);
        }
    }


    /**
     *
     * @param pagenum 第几页数据
     * @param size  每页多少条
     * @param key 关键词
     * @param longitude 经度3
     * @param latitude 纬度
     * @param sid 商品类别id
     * @return
     */
    @GetMapping(value = {"/"})
    @Unobstructed
    public JsonResult<Object> getBusinessListByDistance(@PathParam("pagenum")Integer pagenum,@PathParam("size")Integer size,@PathParam("key")String key,
    @PathParam("longitude")double longitude,@PathParam("latitude") double latitude,@PathParam("sid") Long sid){
        RPage rPage = null;
        try {
            rPage = businessService.getBusinessByDistance(pagenum, size,latitude,longitude,sid,key);
            return ResultTool.success(rPage);
        } catch (Exception exception) {
            exception.printStackTrace();
            return  ResultTool.fail(ResultCode.PARAM_NOT_COMPLETE);
        }
    }


    /**
     * 通过商家id 获取到对应的商家数据
     * @param id
     * @return
     */
    @GetMapping(value = {"/{id}"})
    @Unobstructed
    public JsonResult<Object> getBusinessListById(@PathVariable String id){
        Business business = businessService.getById(Long.valueOf(id));
        return ResultTool.success(business);
    }


    /**
     * 商家信息补充
     * @param addShopDto
     * @return
     */
    @PostMapping("/add")
    @Unobstructed
    public JsonResult addShop(@RequestBody AddShopDto addShopDto){
        businessService.saveShop(addShopDto);
        return ResultTool.success();
    }


    /**
     * 停用商家
     * @param updateDeactivateDto
     * @return
     */
    @PutMapping("/deactivate")
    @Unobstructed
    public JsonResult updateShopDeactivate(@RequestBody UpdateDeactivateDto updateDeactivateDto){
        Business business = new Business();
        business.setId(updateDeactivateDto.getShopId());
        business.setDeactivate(updateDeactivateDto.getDeactivate());
        businessService.updateById(business);
        return ResultTool.success();
    }

    /**
     * 申请通过/不通过商家
     * @param updateCheckPassDto
     * @return
     */
    @PutMapping("/CheckPass")
    @Unobstructed
    public JsonResult updateCheckPass(@RequestBody UpdateCheckPassDto updateCheckPassDto){
        Business business = new Business();
        business.setId(updateCheckPassDto.getShopId());
        int check = updateCheckPassDto.getPass()?ShopCheckNum.CHECK_PASS.getCode():ShopCheckNum.CHECK_NOT_PASS.getCode();
        business.setCheckPass(check);
        businessService.updateById(business);
        return ResultTool.success();
    }

    /**
     * 注册商家 addShopDto
     * @param
     * @return
     */
    @PostMapping("/register")
    @Unobstructed
    public JsonResult register(@RequestBody Business business){
        String num = StringUtil.getNum(6);
        business.setAccount(num);
        businessService.save(business);
        return ResultTool.success(num);
    }


    /**
     * 获取未审核的商家列表[分页处理]
     * @param pagenum
     * @param size
     * @return
     */
    @GetMapping("/list/NotCheck/")
    @Unobstructed
    public JsonResult getNoCheck(@PathParam("pagenum") int pagenum,@PathParam("size") int size){
        return ResultTool.success(businessService.getCheckStateList(pagenum,size,ShopCheckNum.NO_CHECK));
    }


    /**
     * 获取审核未通过的商家列表[分页处理]
     * @param pagenum
     * @param size
     * @return
     */
    @GetMapping("/list/CheckNotPass/")
    @Unobstructed
    public JsonResult CheckNotPass(@PathParam("pagenum") int pagenum,@PathParam("size") int size){
        return ResultTool.success(businessService.getCheckStateList(pagenum,size,ShopCheckNum.CHECK_NOT_PASS));
    }

    /**
     * 获取所有的商家列表 [分页]
     */
    @GetMapping("/list/")
    @Unobstructed
    public JsonResult getAll(@PathParam("pagenum") int pagenum,@PathParam("size") int size){
        return ResultTool.success(businessService.getAll(pagenum,size));
    }








}

