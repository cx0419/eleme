package com.cx.springboot02.controller;


import com.cx.springboot02.common.JsonResult;
import com.cx.springboot02.common.E.ResultCode;
import com.cx.springboot02.common.ResultTool;
import com.cx.springboot02.common.utils.Unobstructed;
import com.cx.springboot02.dto.AddressDto;
import com.cx.springboot02.pojo.Address;
import com.cx.springboot02.service.impl.AddressServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    AddressServiceImpl addressService;
    @Unobstructed
    @PostMapping("/save")
    public JsonResult<Object> save(@RequestBody Map<String,Object> mp) {
        try {
            String name = (String) mp.get("name");
            String phone = (String) mp.get("phone");
            Double lon =  Double.parseDouble((String) mp.get("lon"));
            Double lat = Double.parseDouble((String) mp.get("lat"));
            String specificAddress  = (String) mp.get("specificAddress");
            Long cid = ((Integer) mp.get("cid")).longValue();
            Address address = new Address();
            address.setName(name);
            address.setSpecificAddress(specificAddress);
            address.setPhone(phone);
            address.setLon(lon);
            address.setLat(lat);
            address.setCid(cid);
            System.out.println(address.toString());
            return ResultTool.success(addressService.saveAddress(address));
        } catch (DataIntegrityViolationException e){
            return ResultTool.fail(ResultCode.PARAM_NOT_COMPLETE);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResultTool.fail(ResultCode.PARAM_NOT_VALID);
        }
    }


    @DeleteMapping("/")
    @Unobstructed
    public JsonResult<String> delete(@PathParam("id") Long id,@PathParam("cid") Long cid) {
        try {
            addressService.deleteByIdAndCid(id,cid);
            return ResultTool.success();
        } catch (Exception exception) {
            return ResultTool.fail(ResultCode.PARAM_NOT_VALID);
        }
    }





    @PutMapping("/update")
    @Unobstructed
    public JsonResult<String> delete(@RequestBody Map<String,Object> mp) {

        try {
            String name = (String) mp.get("name");
            String phone = (String) mp.get("phone");
            Double lon = (Double) mp.get("lon");
            Double lat = (Double) mp.get("lat");
            String specificAddress  = (String) mp.get("specificAddress");

            Long cid = ((Integer) mp.get("cid")).longValue();
            Long id = ((Integer) mp.get("id")).longValue();
            Address address = new Address();
            address.setName(name);
            address.setSpecificAddress(specificAddress);
            address.setPhone(phone);
            address.setLon(lon);
            address.setLat(lat);
            address.setCid(cid);
            address.setId(id);
            addressService.updateById(address);
            return ResultTool.success();
        } catch (Exception exception) {
            return ResultTool.fail(ResultCode.PARAM_NOT_VALID);
        }
    }



    @GetMapping("/{cid}")
    @Unobstructed
    public JsonResult<List<AddressDto> > select(@PathVariable Long cid) {
        try {
            List<AddressDto> list = addressService.getAddressByCid(cid);
            return ResultTool.success(list);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResultTool.fail(ResultCode.COMMON_FAIL);
        }
    }









}
