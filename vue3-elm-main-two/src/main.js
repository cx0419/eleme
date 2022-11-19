import { createApp } from 'vue'
import App from './App.vue'
import router from './router'   // vue-router
import store from './store/index'   // vuex
import axios from '@/utils/axios'
import axios02 from '@/utils/axios02'
import './config/rem'   // rem
import './index.css'    // 引入tailwind css文件
// 按需引入vant及对应样式
import 'vant/lib/index.css'
import {
        Button, Toast, Grid, GridItem,
        Swipe, SwipeItem, Tag, Rate,
        Skeleton, PullRefresh, List, Cell,
        Empty, Icon, DropdownMenu, DropdownItem,
        TreeSelect, ConfigProvider, Checkbox, CheckboxGroup,
        Sticky, Tabs, Tab, Popup, Dialog, Image as VanImage,
        Notify,Search
        } from 'vant'
const app = createApp(App)
app.use(router)
app.use(store)
app.config.globalProperties.$http = axios02   // 全局挂载axios02
app.use(Button)
app.use(Toast)
app.use(Grid)
app.use(GridItem)
app.use(Swipe)
app.use(SwipeItem)
app.use(VanImage)
app.use(Tag)
app.use(Rate)
app.use(Skeleton)
app.use(PullRefresh)
app.use(List)
app.use(Cell)
app.use(Empty)
app.use(Icon)
app.use(DropdownMenu)
app.use(DropdownItem)
app.use(TreeSelect)
app.use(ConfigProvider)
app.use(Checkbox)
app.use(CheckboxGroup)
app.use(Sticky)
app.use(Tabs)
app.use(Tab)
app.use(Popup)
app.use(Dialog)
app.use(Notify)
app.use(Search)
app.mount('#app')