package com.cx.springboot02.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cx.springboot02.dto.AddressDto;
import com.cx.springboot02.mapper.AddressMapper;
import com.cx.springboot02.pojo.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> {

    @Autowired
    AddressMapper addressMapper;


    public int saveAddress(Address address) {
    return addressMapper.insert(address);
    }

    public List<AddressDto> getAddressByCid(Long cid){
        List<AddressDto> addressDtos = new ArrayList<>();
        List<Address> list = addressMapper.selectAddressByCid(cid);
        for (Address address : list) {
            AddressDto addressDto = new AddressDto();
            addressDto.setId(address.getId());
            addressDto.setLat(address.getLat());
            addressDto.setLon(address.getLon());
            addressDto.setCid(address.getCid());
            addressDto.setName(address.getName());
            addressDto.setCreateTime(address.getCreateTime());
            addressDto.setDeliverable(true);
            addressDto.setPhone(address.getPhone());
            addressDto.setSex(address.isSex());
            addressDto.setSpecificAddress(address.getSpecificAddress());
            addressDto.setTag(address.getTag());
            addressDtos.add(addressDto);
        }
        return addressDtos;
    }

    public boolean deleteByIdAndCid(Long id,Long cid){
        addressMapper.deleteByIdAndCid(id,cid);
        return true;
    }




}
