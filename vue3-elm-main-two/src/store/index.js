import { createStore } from 'vuex'
import mutations from './mutations'
import actions from './action'


const state = {
    geohash: '31.22299,121.36025',//地址geohash值
    city_id:'1',
    city_name:'上海',
    longitude: '', // 经度
    latitude: '', // 纬度
    cartList: {},   //  加入购物车的商品列表
    skuCartList:{},//sku列表,在确认订单处使用
    shopDetail: null,   // 商铺详情信息
    shopid: null, 
    cartId: null,
    sig: null,
    chooseAddress: null,
    addressIndex: null,
    addressList:[],
    inputText:"",//备注

    userInfo: null, // 用户信息
    login: false,   // 是否登录
}

export default createStore({
    state,
    mutations,
    actions
})