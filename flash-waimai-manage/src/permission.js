import router from './router'
import store from './store'
import NProgress from 'nprogress' // Progress 进度条
import 'nprogress/nprogress.css'// Progress 进度条样式
import { Message } from 'element-ui'
import { getToken } from '@/utils/auth' // 验权
import {resp} from './json'

const whiteList = ['/login','/register'] // 不重定向白名单

// permission judge function
function hasPermission(roles, permissionRoles) {
  if (roles.indexOf('admin') >= 0) return true // admin permission passed directly
  if (!permissionRoles) return true
  return roles.some(role => permissionRoles.indexOf(role) >= 0)
}

router.beforeEach((to, from, next) => {
  NProgress.start()
  if (getToken()) {
    if (to.path === '/login' || to.path === '/register') {
      console.log(to.path)
      next({ path: '/' })
      NProgress.done() // 如果当前页面是dashboard，则在每个钩子之后都不会触发，所以手动处理它
    } else {
      next()
      if (store.getters.roles === null) {       
        store.dispatch('GetInfo').then(response => { // 拉取用户信息
          const identity = response.data.data.identity
          // //根据后台返回的路由信息调用GenerateRoutes方法生成路由表
          store.dispatch('GenerateRoutes', { identity:identity}).then(() => { // 根据roles权限生成可访问的路由表
            // 添加可访问路由表
            // router.addRoutes(store.getters.addRouters)
            next({ ...to, replace: true }) // hack方法 确保addRoutes已完成 ,设置replace:true，以便导航不会留下历史记录
            // next()
          })
        //   //原始的菜单列表数据
        //   const menus = res.data.menus
        //   //后台构造好的路由
        //   const routers = res.data.routers
        //   //角色列表
        //   const roles = res.data.roles

        //   //根据后台返回的路由信息调用GenerateRoutes方法生成路由表
        //   store.dispatch('GenerateRoutes', { roles:roles,routers:routers,menus:menus }).then(() => { // 根据roles权限生成可访问的路由表
        //     // 添加可访问路由表
        //     router.addRoutes(store.getters.addRouters)
        //     next({ ...to, replace: true }) // hack方法 确保addRoutes已完成 ,设置replace:true，以便导航不会留下历史记录
        //   })
        //   // next()
        // }).catch((err) => {
        //   store.dispatch('FedLogOut').then(() => {
        //     // Message.error(err || 'Verification failed, please login again')
        //     next({ path: '/' })
        //   })
        })
      } else {
        // 没有动态改变权限的需求可直接next() 删除下方权限判断 ↓
        if (true) {
          next()
        } else {
          console.log(401)
          next({ path: '/401', replace: true, query: { noGoBack: true }})
        }
      }
    }
  } else {
    if (whiteList.indexOf(to.path) !== -1) {
      next()
    } else {
      next('/login')
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  NProgress.done() // 结束Progress
})
