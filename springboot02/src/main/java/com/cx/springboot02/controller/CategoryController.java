package com.cx.springboot02.controller;


import com.cx.springboot02.common.JsonResult;
import com.cx.springboot02.common.ResultTool;
import com.cx.springboot02.common.utils.Unobstructed;
import com.cx.springboot02.dto.AddSpecsCategoryDto;
import com.cx.springboot02.mapper.CategoryMapper;
import com.cx.springboot02.pojo.Category;
import com.cx.springboot02.service.impl.CategoryServiceImpl;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 陈翔
 * @since 2022-11-04
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
   CategoryServiceImpl categoryService;





    /**
     * 获取一个商家的所有分类
     * @param bid 商家id
     * @return
     */
    @GetMapping("/")
    @Unobstructed
    public JsonResult<List<Category>> getCategories(@PathParam("bid")Long bid){
        System.out.println("bid:"+bid);
        List<Category>  list = categoryService.getCategoryByBId(bid);
        return ResultTool.success(list);
    }




}

