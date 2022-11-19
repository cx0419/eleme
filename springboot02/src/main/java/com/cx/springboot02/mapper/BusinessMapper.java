package com.cx.springboot02.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cx.springboot02.data.vo.ShopDetailVo;
import com.cx.springboot02.dto.ShopDto;
import com.cx.springboot02.pojo.Business;
import com.cx.springboot02.pojo.Customer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 陈翔
 * @since 2022-10-05
 */
@Mapper
public interface BusinessMapper extends BaseMapper<Business> {
    Business checkBusiness(@Param("name") String name, @Param("password")String password);

    Business selectBusinessById(Long id);

    List<ShopDto> selectBusinessListByDistance(@Param("offset")int offset, @Param("pagesize")int pagesize, @Param("latitude") double latitude, @Param("longitude")double longitude,@Param("sid")Long sid,@Param("key")String key);


    List<ShopDetailVo> selectBusinessList(@Param("offset")int offset, @Param("pagesize")int pagesize, @Param("key")String key, @Param("classId")Long classId, @Param("checkPass")Integer checkPass);
    int selectBusinessListCount(@Param("key")String key,@Param("classId")Long classId,@Param("checkPass")Integer checkPass);

    List<Business> selectCheckStateShopList(int offset,int size,int state);

    List<Business> selectALl(int offset,int size);
}
