package com.cx.springboot02.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cx.springboot02.mapper.Customer_serviceMapper;
import com.cx.springboot02.pojo.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 陈翔
 * @since 2022-10-05
 */
@Service
public class CustomerServiceServiceImpl extends ServiceImpl<Customer_serviceMapper, CustomerService>{

    @Autowired
    Customer_serviceMapper customer_serviceMapper;


    public CustomerService login(String name, String password) {
        return customer_serviceMapper.checkCustomerService(name,password);
    }
}
