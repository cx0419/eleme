import request from '@/utils/request'

export function login(userType,username, password) {
  return request({
    url: '/manager/login',
    method: 'post',
    data:{
      username,
      password,
      userType
    }
  })
}

export function getInfo(token) {
  return request({
    url: '/manager/check',
    method: 'post',
    data:{
      token
    }
  })
}

export function logout() {
  console.log('logout')
  return request({
    url: '/account/logout',
    method: 'post'
  })
}

export function updatePwd(params) {
  return request({
    url: '/account/updatePwd',
    method: 'post',
    params
  })
}
