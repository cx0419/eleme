import axios02 from "../utils/axios02";

/**
 * 登录
 * @param {*} name 
 * @param {*} password 
 * @param {*} code 
 * @returns 
 */
export function login(name,password,code){
	return axios02.post("/customer/login",{
		name,
		password,
		code
	})
}


/**
 * 
 * @param {上传头像} id 
 * @param {*} url 
 * @returns 
 */
export function uploadCustomerAvatar(id,url){
	return axios02.post("/customer/login",{
		id,
		url
	})
}


/**
 * 获取登录码
 * @returns
 */
export function getLoginCode(){
	return axios02.get("/customer/verifyCode")
}

/**
 * 登出
 */
export function LoginOut(){
	localStorage.removeItem('token');
}

/**
 * 获取注册码
 * @param {} email 
 * @returns 
 */
export function getRegisterCode(email){
	return axios02.post("/customer/sendRcode",{
		email,
	})
}

/**
 * 注册
 * @param {} name 
 * @param {*} password1 
 * @param {*} password2 
 * @param {*} email 
 * @param {*} code 
 * @returns 
 */
export function register(name,password1,password2,email,code){
	return axios02.post("/customer/register",{
		name,
		password1,
		password2,
		email,
		code,
	})
} 
