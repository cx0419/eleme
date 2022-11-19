import axios02 from "../utils/axios02";

/**
 * 保存收获地址
 * @param {} name 
 * @param {*} password 
 * @param {*} code 
 * @returns 
 */
export function saveAddress(name,phone,lon,lat,specificAddress,cid){
	return axios02.post("/address/save",{
		name,
		phone,
		lon,
		lat,
		specificAddress,
		cid,	
	})
}


/**
 * 删除收获地址
 * @param {} name 
 * @param {*} phone 
 * @param {*} lon 
 * @param {*} lat 
 * @param {*} specificAddress 
 * @param {*} cid 
 * @returns 
 */
export function deleteAddress(id){
	return axios02.delete("/address/?cid="+cid+"&id="+id)
}


/**
 * 更新
 * @param {*} name 
 * @param {*} phone 
 * @param {*} lon 
 * @param {*} lat 
 * @param {*} specificAddress 
 * @param {*} cid 
 * @returns 
 */
 export function updateAddress(name,phone,lon,lat,specificAddress,cid){
	return axios02.put("/address/update",{
		name,
		phone,
		lon,
		lat,
		specificAddress,
		cid,	
	})
}


/**
 * 通过顾客id获取到所有的收获地址
 * @param {*} cid 
 * @returns 
 */
export function getAddressByCid(cid){
	return axios02.get("/address/"+cid);
}

