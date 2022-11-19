import request from '@/utils/request'
import Axios  from 'axios'
export function uploadImg(data){
	return Axios({
		url: 'http://127.0.0.1:8080/boot/customer/image',
		method: 'post',
		headers: { 'content-type': 'multipart/form-data' },
		data
	  })
}