package com.cx.springboot02.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cx.springboot02.data.vo.SkuDetailVo;
import com.cx.springboot02.pojo.Food;
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
public interface FoodMapper extends BaseMapper<Food> {

    List<Food> selectFoodListByGId_Not_Single(@Param("gid") Long gid);
    List<Food> selectFoodListByGId_Single(@Param("gid") Long gId);
    Food selectBySkuId(@Param("id") Long id);

    List<SkuDetailVo> selectSkuDetailVoList(Long id);


    Food selectSkuBySingleSpuId(@Param("gid") Long gid);
}
