package com.cx.springboot02.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cx.springboot02.common.E.PayState;
import com.cx.springboot02.common.E.ShopCheckNum;
import com.cx.springboot02.common.RPage;
import com.cx.springboot02.data.vo.ShopDetailVo;
import com.cx.springboot02.dto.AddShopDto;
import com.cx.springboot02.dto.ShopDto;
import com.cx.springboot02.mapper.BusinessMapper;
import com.cx.springboot02.pojo.Business;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 陈翔
 * @since 2022-10-05
 */
@Service
public class BusinessServiceImpl extends ServiceImpl<BusinessMapper, Business>  {

    @Autowired
    BusinessMapper businessMapper;


    public Business selectBusinessById(Long id) {
        return businessMapper.selectBusinessById(id);
    }


    public Business login(String name, String password) {
        return businessMapper.checkBusiness(name,password);
    }


    /**
     * 分页获取商家
     * @param offset
     * @param size
     * @return
     */
    public RPage<Business> getBusinessListPage(int offset, int size){
        LambdaQueryWrapper<Business> userLambdaQueryWrapper = Wrappers.lambdaQuery();
        Page<Business> userPage = new Page<>(offset , size);
        IPage<Business> businessIPage = businessMapper.selectPage(userPage , userLambdaQueryWrapper);
        return new RPage<Business>(businessIPage);
    }


    public RPage<ShopDto> getBusinessByDistance(int pagenum, int size,double latitude,double longitude,Long sid,String key){
        if(size > 20) size = 20;//最大只允许查询20条
        return  new RPage<ShopDto>(pagenum,size,businessMapper.selectBusinessListByDistance(size*(pagenum-1), size, latitude, longitude,sid,key));
    }

    /**
     * 获取商家列表
     * @param pagenum
     * @param size
     * @param key
     * @param classId
     * @param checkPass
     * @return
     */
    public RPage<ShopDetailVo> getBusinessList(int pagenum, int size, String key, Long classId, Integer checkPass){
        if(size > 20) size = 20;//最大只允许查询20条
        RPage<ShopDetailVo> rPage = new RPage<>(pagenum, size, businessMapper.selectBusinessList(size * (pagenum - 1), size, key, classId, checkPass));
        for (ShopDetailVo row : rPage.getRows()) {
            /**
             * 设置评分
             */
            row.setRating(5.0f);
            /**
             * 设置销量
             */
            row.setRecentOrderNum(463);
            /**
             * 是否在学校当中
             */
            row.setInSchool(false);

            /**
             * 设置检查状态的字符串形式
             */
            row.setStateStr(ShopCheckNum.getMessageByCode(row.getCheckPass()));
        }
        rPage.SetTotalCountAndTotalPage(businessMapper.selectBusinessListCount(key, classId, checkPass));
        return  rPage;
    }

    public void saveShop(AddShopDto addShopDto){
        Business business = new Business();
        business.setId(addShopDto.getShopId());
        business.setAddress(addShopDto.getAddress());
        business.setPhone(addShopDto.getPhone());
        business.setLatitude(addShopDto.getLatitude());
        business.setLongitude(addShopDto.getLongitude());
        business.setCategory(addShopDto.getCategory());
        business.setImagePath(addShopDto.getImagePath());
        business.setFloatDeliveryFee(addShopDto.getFloatDeliveryFee());
        business.setFloatMinimumOrderAmount(addShopDto.getFloatMinimumOrderAmount());
        business.setDescription(addShopDto.getDescription());
        business.setPromotionInfo(addShopDto.getPromotionInfo());
        business.setIsPremium(addShopDto.getIsPremium());
        business.setDeliveryMode(addShopDto.getDeliveryMode());
        business.setNewShop(addShopDto.getNewShop());
        business.setBao(addShopDto.getBao());
        business.setPiao(addShopDto.getPiao());
        business.setStartTime(addShopDto.getStartTime());
        business.setEndTime(addShopDto.getEndTime());
        business.setBusinessLicenseImage(addShopDto.getBusinessLicenseImage());
        business.setCateringServiceLicenseImage(addShopDto.getCateringServiceLicenseImage());
        businessMapper.updateById(business);
    }

    /**
     * 改变商家审核状态
     * @param bid
     * @param shopCheckNum
     * @return
     */
    public boolean alterCheckState(Long bid, ShopCheckNum shopCheckNum){
        Business business = new Business();
        business.setId(bid);
        business.setCheckPass(shopCheckNum.getCode());
        businessMapper.updateById(business);
        return true;
    }

    /**
     * 获取不同审核状态的商家列表(分页)
     * @return
     */
    public RPage<Business> getCheckStateList(int pagenum, int size,ShopCheckNum shopCheckNum){
        if(size > 20) size = 20;//最大只允许查询20条
        Map<String,Object> mp = new HashMap<>();
        return  new RPage<>(pagenum,size,businessMapper.selectCheckStateShopList(size*(pagenum-1), size,shopCheckNum.getCode()));
    }


    /**
     * 获取所有的商家列表(分页)
     * @return
     */
    public RPage<Business> getAll(int pagenum, int size){
        if(size > 20) size = 20;//最大只允许查询20条
        Map<String,Object> mp = new HashMap<>();
        return  new RPage<>(pagenum,size,businessMapper.selectALl(size*(pagenum-1), size));
    }




}
