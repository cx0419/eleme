import Cookies from 'js-cookie'
import LocalStorage from '@/utils/localstorage'
const TokenKey = 'token'

export function getToken() {
  return LocalStorage.getLocal('token')
}

export function setToken(token) {
  return LocalStorage.setLocal('token', token)
}

export function removeToken() {
  return LocalStorage.delLocal('token')
}
