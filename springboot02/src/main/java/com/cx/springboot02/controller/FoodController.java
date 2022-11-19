package com.cx.springboot02.controller;


import com.cx.springboot02.common.JsonResult;
import com.cx.springboot02.common.ResultTool;
import com.cx.springboot02.common.utils.Unobstructed;
import com.cx.springboot02.dto.AddSkuDto;
import com.cx.springboot02.pojo.Food;
import com.cx.springboot02.service.impl.FoodServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/food")
public class FoodController {

    @Autowired
    FoodServiceImpl foodService;

    /**
     * 添加sku
     * @param addSkuDto
     * @return
     */
    @PostMapping("")
    @Unobstructed
    public JsonResult<Object> add(@RequestBody AddSkuDto addSkuDto){
        foodService.addSku(addSkuDto);
        return ResultTool.success(null);
    }


    /**
     * 删除sku
     * @param mp
     * @return
     */
    @DeleteMapping("")
    @Unobstructed
    public JsonResult<Object> delete(@RequestBody Map<String,Object> mp){
        int id = (int) mp.get("skuId");
        foodService.deleteSku((long) id);
        return ResultTool.success(null);
    }



    /**
     * 更新非规格商品
     * @param addSkuDto
     * @return
     */
    @PutMapping("/single/")
    @Unobstructed
    public JsonResult<Object> updateSingle(@RequestBody AddSkuDto addSkuDto){
        foodService.addSku(addSkuDto);
        return ResultTool.success(null);
    }


    /**
     * 获取sku列表
     * @param spuId
     * @return
     */
    @GetMapping("/{spuId}")
    @Unobstructed
    public JsonResult<Object> getSkuList(@PathVariable String spuId){
        Long id = Long.valueOf(spuId);
        List<Food> skuListBySpuId = foodService.getSkuListBySpuId(id);
        return ResultTool.success(skuListBySpuId);
    }



}
