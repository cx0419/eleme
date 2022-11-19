package com.cx.springboot02.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cx.springboot02.mapper.ShopClassMapper;
import com.cx.springboot02.pojo.ShopClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ShopClassServiceImpl extends ServiceImpl<ShopClassMapper, ShopClass> {

    @Autowired
    ShopClassMapper shopClassMapper;


    public List<ShopClass> getAll(){
        return shopClassMapper.selectList(null);
    }

}
