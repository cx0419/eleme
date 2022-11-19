package com.cx.springboot02.service.impl;


import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cx.springboot02.mapper.CategoryMapper;
import com.cx.springboot02.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 陈翔
 * @since 2022-11-04
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements IService<Category> {

    @Autowired
    CategoryMapper categoryMapper;

    public List<Category> getCategoryByBId(Long bid){
        return categoryMapper.selectCategoriesById(bid);
    }

}
