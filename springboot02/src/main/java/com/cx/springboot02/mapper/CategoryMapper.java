package com.cx.springboot02.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cx.springboot02.pojo.Category;
import com.cx.springboot02.pojo.Goods;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 陈翔
 * @since 2022-11-04
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

    List<Category> selectCategoriesById(Long id);


}
