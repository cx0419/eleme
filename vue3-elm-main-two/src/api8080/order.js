import axios02 from "../utils/axios02";



//将购物车保存至服务器
export function saveBuyCart(shopId,entities,jsonEntities,remarks){
	return axios02.post("/buyCart/check",{
		shopId,
		entities,
		jsonEntities,
		remarks
	})
}

export function saveOrderInfo(addressId,customerId,buycartId,shopId,geohash,remarks){
	return axios02.post("/order/save",{
		addressId,
		customerId,
		buycartId,
		shopId,
		geohash,
		remarks
	})
}

//根据订单id获取到订单
export function getOrderById(id){
	return axios02.get("/order/"+id)
}


//获取订单列表
export function getOrderList(pagenum,size,cid){
	return axios02.get("/order/list/?pagenum="+pagenum+"&size="+size+"&cid="+cid)
}

//支付接口
export function payForOrder(oid){
	return axios02.put("/order/pay/",{
		oid
	})
}