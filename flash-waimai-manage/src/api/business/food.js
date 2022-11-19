import request from '@/utils/request'

/**
 * 添加食品
 */

export function addFood(params) {
  return request({
    url: '/shopping/addfood',
    method: 'post',
    params
  })
}
/**
 * 获取食品列表
 */

export function getFoods(pagenum,size,key,categoryId,id,shopId) {
  let str = "?pagenum="+pagenum+"&size="+size
  if(key != null) str += "&key=" + key
  if(categoryId != null) str += "&categoryId=" + categoryId
  if(id != null) str += "&id=" + id
  if(shopId != null) str += "&shopId=" + shopId
  return request({
    url: '/goods/list/'+str,
    method: 'get'
  })
}

/**
 * 更新食品信息
 */

export function updateFood(params) {
  return request({
    url: '/shopping/v2/updatefood',
    method: 'post',
    params
  })
}

/**
 * 删除食品
 */

export function deleteFood(id) {
  return request({
    url: '/shopping/v2/food/' + id,
    method: 'delete'
  })
}

/**
 * 审核商铺
 * @param params
 */
export function auditFood(params) {
  return request({
    url: '/shopping/auditFood',
    method: 'post',
    params
  })
}

//获取一个商家的菜单分类列表
export function getShopMenu(shopId){
  return request({
    url: '/category/?bid='+shopId,
    method: 'get',
  })
}

export function updateSinlge(data){
  return request({
    url: '/goods/single',
    method: 'put',
    data:data
  })
}

//添加规格分类
export function addSpecsCategory(data){
  return request({
    url: '/specs/addCategories',
    method: 'post',
    data:data
  })
}


//添加规格分类下的规格
export function addspecs(data){
  return request({
    url: '/specs/addSpecs',
    method: 'post',
    data:data
  })
}


//根据商品id获取到这个spu的所有规格分类
export function getSpecsCategoryList(id){
  return request({
    url: '/specs/list/'+id,
    method: 'get',
  })
}

//获取规格分类及其下的规格
export function getTreeSpecs(gid){
  return request({
    url: '/specs/categoryAndSpecsList/'+gid,
    method: 'get',
  })
}

//添加sku
export function addSku(sku){
  return request({
    url: '/food',
    method: 'post',
    data:sku
  })
}

//获取sku列表 [spuId]
export function getSkuList(spuId){
  return request({
    url: '/goods/'+spuId,
    method: 'get',
  })
}

//删除sku
export function removeSku(skuId){
  return request({
    url: '/food',
    method: 'delete',
    data:skuId
  })
}

//删除spu
export function removeSpu(gid){
  return request({
    url: '/goods',
    method: 'delete',
    data:{
      gid
    }
    
  })
}