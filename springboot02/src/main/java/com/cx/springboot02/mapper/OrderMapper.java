package com.cx.springboot02.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cx.springboot02.dto.OrderListDto;
import com.cx.springboot02.pojo.Order;
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
public interface OrderMapper extends BaseMapper<Order> {

    Long insertOrder(Order order);
    void updateOrder(Order order);
    List<OrderListDto> selectOrderListByCId(@Param("offset")int offset, @Param("pagesize")int pagesize, @Param("cid")Long cid, @Param("key")String key);
}
