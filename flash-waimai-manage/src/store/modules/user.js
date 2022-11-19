import { login, logout, getInfo } from '@/api/login'
import { getToken, setToken, removeToken } from '@/utils/auth'
import LocalStorage from '@/utils/localstorage'
const user = {
  state: {
    token: LocalStorage.getLocal('token')==null?"":LocalStorage.getLocal('token'),
    name: '',
    avatar: '',
    roles: null,
    profile: {}
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      LocalStorage.setLocal("token",token)
      state.token = token
    },
    SET_NAME: (state, name) => {
      state.name = name
    },
    SET_PROFILE: (state,profile) => {
      state.profile = profile
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    },
    SET_PERMISSIONS: (state, permissions) => {
      state.permissions = permissions
    }
  },

  actions: {
    // 登录
    Login({ commit }, userInfo) {
      const username = userInfo.username.trim()
      return new Promise((resolve, reject) => {
        login(userInfo.userType,username, userInfo.password).then(response => {
          console.log("enter user.js")
          console.log(response)
          console.log(response.headers.token)
          // setToken(data.token)
          commit('SET_TOKEN', response.headers.token)
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 获取用户信息
    GetInfo({ commit, state }) {
      return new Promise((resolve, reject) => {
        getInfo(state.token).then(response => {
          if (response.data.success) { // 验证返回的roles是否是一个非空数组
            commit('SET_ROLES',response.data.data.identity)
            commit('SET_PERMISSIONS',null)
            commit('SET_NAME', response.data.data.detail.name)
            commit('SET_AVATAR', 'https://ts2.cn.mm.bing.net/th?id=OIP-C.jabkLfYk-CeXUfU8i0VGKwAAAA&w=183&h=183&c=8&rs=1&qlt=90&o=6&dpr=1.7&pid=3.1&rm=2')
            commit('SET_PROFILE',response.data.data.detail)
            resolve(response)
          } else {
            reject('getInfo: roles must be a non-null array !')
          }
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 登出
    LogOut({ commit, state }) {
      return new Promise((resolve, reject) => {
        logout(state.token).then(() => {
          commit('SET_TOKEN', '')
          commit('SET_ROLES', [])
          removeToken()
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 前端 登出
    FedLogOut({ commit }) {
      return new Promise(resolve => {
        commit('SET_TOKEN', '')
        removeToken()
        resolve()
      })
    }
  }
}

export default user
