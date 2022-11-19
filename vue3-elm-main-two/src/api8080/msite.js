import axios02 from "../utils/axios02";

//获取美食轮播
export function getFoodCarousel(){
	return axios02.get("/shopclass")
} 


//获取商铺列表
export function getShops(pagenum,size,longitude,latitude){
	console.log(longitude)
	console.log(latitude)
	return axios02.get("/business/?"+"pagenum="+pagenum+"&size="+size+"&longitude="+longitude+"&latitude="+latitude)
}

//根据商家id获取到所有的分类
export function getCategories(bid){
	return axios02.get("/category/?"+"bid="+bid)
}

//根据分类获取所有的商品
export function getGoods(cid){
	return axios02.get("/goods/?"+"cid="+cid)
}

//根据商家id获取商家
export function getShopById(id){
	return axios02.get("/business/"+id)
}

//根据分类获取到对应的食物列表
export function getFoodsByCId(cid){
	return axios02.get("/cid/{"+cid+"}");
}


//获取分类+食物
export function getMenu(bid){
	return axios02.get("/goods/menu/"+bid);
}