package com.cx.springboot02.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cx.springboot02.mapper.ManagerMapper;
import com.cx.springboot02.pojo.Manager;
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
public class ManagerServiceImpl extends ServiceImpl<ManagerMapper, Manager> {

    @Autowired
    ManagerMapper mapper;

    public Manager login(String name, String password) {
        return mapper.checkManage(name, password);
    }
}
