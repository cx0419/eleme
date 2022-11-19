import { 
    RECORD_ADDRESS,
    SAVE_GEOHASH,
    ADD_CART,
    REDUCE_CART,
    INIT_BUYCART,
    CLEAR_CART,
    SAVE_SHOPID,
    SAVE_CART_ID_SIG,
    CHOOSE_ADDRESS,
    CONFIRM_REMARK,
    SAVE_ORDER_PARAM,
    ORDER_SUCCESS,
    RECORD_SHOPDETAIL,
    RECORD_USERINFO,
    GET_USERINFO,
    OUT_LOGIN,
    RETSET_NAME,
    GET_ADDRESS,
    SAVE_ADDDETAIL,
    SAVE_ADDRESS,
    ADD_ADDRESS,
    RECORD_CITY_ID,
    RECORD_CITY_NAME,
    SAVE_SKU_LIST,
    ANOTHOR_ORDER,
} from './mutation-types'

import { setStore, getStore } from '@/config/utils'
// import { list } from 'postcss'

export default {
    //记录名字
    [RECORD_CITY_NAME] (state, name) {
        state.city_name = name
    },
    //记录城市id
    [RECORD_CITY_ID] (state, city_id) {
        state.city_id = city_id
    },
    
    // 记录当前经纬度
    [RECORD_ADDRESS] (state, {latitude,longitude}) {
        state.latitude = latitude
		state.longitude = longitude
    }
    
    ,
    // 保存geohash坐标
    [SAVE_GEOHASH] (state, geohash) {
        state.geohash = geohash
    },
    // 添加商品至购物车
    [ADD_CART] (state, {
        shopId,
        categoryId,
        spuId,
        skuId,
        name,
        price,
        specsList,
        specsIdList,
        packingFee
    }) {
        let cart = state.cartList   // 获取购物车
        // 获取购物车中当前商品数量
        // 若当前商品不存在购物车中生成对应数据结构: cart[shopid][category_id][category_id]
        let shop = cart[shopId] = (cart[shopId] || {})
        let category = shop[categoryId] = (shop[categoryId] || {})
        let spu = category[spuId] = (category[spuId] || {})
        if(spu[skuId]) {
            // 如果购物车中存在该商品，商品数量+1
            spu[skuId]['num']++
        }else{
            // 该商品未添加至购物车，添加商品并生成对应数据结构
            spu[skuId] = {
                'num': 1,
                'shopId':shopId,
                'spuId':spuId,
                'skuId': skuId,
                'name': name,
                'price': price,
                'specsList': specsList,
                'specsIdList':specsIdList,
                'packingFee': packingFee,
            }
        }
        // 保存当前购物车
        state.cartList = {...cart}
        // 写入localStorage
        setStore('buyCart', state.cartList)
    },
    //再来一单
    [ANOTHOR_ORDER](state,{
        shopId,
        jsonEntities
    }){
        state.cartList[shopId] = Object.assign({}, JSON.parse(jsonEntities))
        // 写入localStorage
        setStore('buyCart', state.cartList)        
    },
    // 商品移出购物车
    [REDUCE_CART](state, {
        shopId,
        categoryId,
        spuId,
        skuId
    }) {
        let cart = state.cartList   // 获取购物车
        // 获取购物车中当前商品数量 
        // cart[shopid][categoryId][skuId]
        let shop = cart[shopId] || {}
        let category = shop[categoryId] || {}
        let spu = category[spuId] || {}
        if(spu && spu[skuId]) {
            // 若购物车中存在该商品且数量大于0
            if(spu[skuId]['num'] > 0) {
                spu[skuId]['num']--
                if(spu[skuId]['num'] == 0) {
                    delete spu[skuId]
                }
            }else{
                // 商品数量为0，清空当前商品数据
                delete spu[skuId]
            }
        }
        state.cartList = {...cart}
        setStore('buyCart', state.cartList)
    },
    // 初始化购物车 通过读取localStorage实现持久化存储
    [INIT_BUYCART](state) {
        let initCart = getStore('buyCart')
        if(initCart) {
            state.cartList = JSON.parse(initCart)
        }
    },
    // 清空购物车
    [CLEAR_CART](state, shopid) {
        state.cartList[shopid] = null
        state.cartList = {...state.cartList}
        setStore('buyCart', state.cartList)
    },
    // 保存shopid
    [SAVE_SHOPID](state, shopid) {
        state.shopid = shopid
    },
    [SAVE_CART_ID_SIG](state, {
        cartId,
        sig
    }) {
        state.cartId = cartId
        state.sig = sig
    },
    // 当前选择地址
    [CHOOSE_ADDRESS] (state, {
        address,
        index
    }) {
        state.chooseAddress = address
        state.addressIndex = index
    },
    // 保存店铺详情
    [RECORD_SHOPDETAIL](state, detail) {
        state.shopDetail = detail
    },
    // 记录用户信息
    [RECORD_USERINFO](state, info) {
        state.userInfo = info
        state.login = true
        setStore('id', info.id)
    },
    // 保存用户信息至vuex
    [GET_USERINFO](state, info) {
        if(state.userInfo && (state.userInfo.name !== info.name)) {
            return
        }
        // if(!state.login){
        //     return
        // }
        if(!info.message) {
            state.userInfo = {...info}
            state.login = true
        }else{
            state.userInfo = null
        }
    },
    // 清除登录信息
    [OUT_LOGIN](state) {
        state.userInfo = null
        state.login = false
    },
    // 修改用户名
    [RETSET_NAME](state, username) {
        state.userInfo = Object.assign({}, state.userInfo, {username})
    },
    // 获取地址列表
    [GET_ADDRESS](state, address) {
        // 从接口获取地址列表更新vuex
        state.addressList = address
    },
    // 新增地址（地区）
    [SAVE_ADDDETAIL](state, newAddress) {
        state.addAddress = newAddress
    },
    [SAVE_ADDRESS](state, addAddressList){
        state.addressList = addAddressList;
    },
    // 新增新地址
    [ADD_ADDRESS](state, obj) {
        state.addressList = [obj, ...state.addressList]
    },
    // 记录当前选择的备注信息，回退时传递给订单确认页
    [CONFIRM_REMARK](state, {
        inputText
    }) {
        state.inputText = inputText
    },
    // 保存下单参数，用户验证页面调用
    [SAVE_ORDER_PARAM](state, orderParam) {
        state.orderParam = orderParam
    },
    // 下单成功，保存订单返回信息
    [ORDER_SUCCESS](state, order) {
        state.orderMessage = order
    },
    [SAVE_SKU_LIST](state,skuCartList){
        state.skuCartList = skuCartList
    },
}