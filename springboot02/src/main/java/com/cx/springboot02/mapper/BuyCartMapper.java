package com.cx.springboot02.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cx.springboot02.dto.CartDataDto;
import com.cx.springboot02.pojo.BrowseHistory;
import com.cx.springboot02.pojo.BuyCart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BuyCartMapper extends BaseMapper<BuyCart> {


    Long insertBuyCart(BuyCart buyCart);


    CartDataDto selectCartDataDtoById(@Param("id") Long id);
}
