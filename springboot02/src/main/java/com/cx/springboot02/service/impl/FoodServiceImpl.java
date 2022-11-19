package com.cx.springboot02.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cx.springboot02.dto.AddSkuDto;
import com.cx.springboot02.mapper.FoodMapper;
import com.cx.springboot02.mapper.GoodsMapper;
import com.cx.springboot02.pojo.Food;
import com.cx.springboot02.pojo.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class FoodServiceImpl  extends ServiceImpl<FoodMapper, Food> implements IService<Food> {
    @Autowired
    FoodMapper foodMapper;


    /**
     * 添加sku
     * @param addSkuDto
     * @return
     */
    public boolean addSku(AddSkuDto addSkuDto){
        Food food = new Food();
        food.setGoodsId(addSkuDto.getGoodsId());
        food.setPrice(addSkuDto.getPrice());
        food.setPackingFee(addSkuDto.getPackingFee());
        food.setSpecsList(addSkuDto.getSpecsList());
        food.setStock(addSkuDto.getStock());
        foodMapper.insert(food);
        return true;
    }




    /**
     * 删除sku
     * @param id
     * @return
     */
    public boolean deleteSku(Long id){
        foodMapper.deleteById(id);
        return true;
    }


    /**
     * 根据spuId获取到对应的sku列表
     * @param supId
     * @return
     */
    public List<Food>  getSkuListBySpuId(Long supId){
        Map<String,Object> mp = new HashMap<>();
        mp.put("goods_id", supId);
        return foodMapper.selectByMap(mp);
    }

}
