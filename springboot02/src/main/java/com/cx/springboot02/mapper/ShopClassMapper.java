package com.cx.springboot02.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cx.springboot02.pojo.Order;
import com.cx.springboot02.pojo.ShopClass;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShopClassMapper extends BaseMapper<ShopClass> {
}
