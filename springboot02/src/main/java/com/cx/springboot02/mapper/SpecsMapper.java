package com.cx.springboot02.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cx.springboot02.pojo.Specs;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 陈翔
 * @since 2022-11-07
 */
@Mapper
public interface SpecsMapper extends BaseMapper<Specs> {


    List<Specs> selectAllBySpecsCategoryId_Single(@Param("id") Long id);
}
