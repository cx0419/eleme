package com.cx.springboot02.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cx.springboot02.data.vo.SpecsCategoryVo;
import com.cx.springboot02.pojo.SpecsCategory;
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
public interface Specs_categoryMapper extends BaseMapper<SpecsCategory> {


    List<SpecsCategory> selectByGoodsId(@Param("gid") Long gid);


    List<SpecsCategoryVo> selectSpecsListVo(@Param("gid")Long gid);
}
