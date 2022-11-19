import request from '@/utils/request'

/**
 * 获取搜索地址
 */
export function searchplace(cityid, value) {
  return request({
    url: '/v1/pois',
    method: 'get',
    params: {
      type: 'search',
      city_id: cityid,
      keyword: value
    }
  })
}

/**
 * category 种类列表
 */

export function foodCategory(params) {
  return request({
    url: '/shopping/v2/restaurant/category',
    method: 'get'
  })
}

/**
 * 获取餐馆列表
 */

export function getResturants(pagenum,size,key,checkPass) {
  let strurl ='?pagenum='+pagenum+"&size="+size
  if(key!=null) strurl += '&key='+ key
  if(checkPass!=null) strurl += '&checkPass='+ checkPass
  return request({
    url: '/business/list'+strurl,
    method: 'get'
  })
}

/**
 * 获取餐馆数量
 */
export function getResturantsCount(params) {
  return request({
    url: '/shopping/restaurants/count',
    method: 'get'
  })
}

/**
 * 添加商铺
 */

export function addShop(params) {
  return request({
    url: '/business/register',
    method: 'post',
    data:params
  })
}

/**
 * 更新餐馆信息
 */

export function updateResturant(params) {
  return request({
    url: '/shopping/updateshop',
    method: 'post',
    params
  })
}

/**
 * 结算金额
 * @param params
 */
export function check(params) {
  return request({
    url: '/shopping/check',
    method: 'post',
    params
  })
}

/**
 * 审核通过商铺
 * @param params
 */
 export function auditResturant(shopId,pass,auditRemark) {
  return request({
    url: '/business/CheckPass',
    method: 'put',
    data: {
      shopId, 
      pass,
      auditRemark
    }
  })
}

/**
 * 停用商铺
 * @param params
 */
export function stopResturant(shopId,deactivate) {
  return request({
    url: '/business/deactivate',
    method: 'put',
    data:{
      shopId,
      deactivate
    }
  })
}
/**
 * 删除餐馆
 */

export function deleteResturant(id) {
  return request({
    url: '/shopping/restaurants/' + id,
    method: 'delete'
  })
}

/**
 * 获取餐馆详细信息
 */

export function getResturantDetail(id) {
  return request({
    url: '/shopping/restaurant/' + id,
    method: 'get'
  })
}

/**
 * 获取menu列表
 */

export function getMenu(params) {
  return request({
    url: '/shopping/v2/menu/',
    method: 'get',
    params
  })
}

/**
 * 获取menu详情
 */

export function getMenuById(category_id) {
  return request({
    url: '/shopping/v2/menu/' + category_id,
    method: 'get'
  })
}

/**
 * 获取当前店铺食品种类
 */

export function getCategory(restaurant_id) {
  return request({
    url: '/shopping/getcategory/' + restaurant_id,
    method: 'get'
  })
}

/**
 * 添加食品种类
 */

export function addCategory(params) {
  return request({
    url: '/shopping/addcategory',
    method: 'post',
    params
  })
}

/**
 * 获取所有的商家分类(shopclass)
 */
export function getAllShopCategory(){
  return request({
    url: '/shopclass',
    method: 'get'
  })
}

//根据id获取到对应的商店
export function getShopById(shopId){
  return request({
    url: '/business/'+shopId,
    method: 'get'
  })
}


/**
 * 获取所有的分类id
 * @param {*} shopId 
 * @returns 
 */
export function getShopClass(){
  return request({
    url: '/shopclass',
    method: 'get'
  })
}



