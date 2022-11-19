<template>
    <div class="bg-gray-50">
        <!-- 商铺列表 -->
        <!-- 引入骨架屏 优化用户体验 -->
        <div v-if="loading">
            <van-skeleton v-for="(item, index) in 20" :key="index"  class="flex items-center" title avatar avatar-shape="square" :avatar-size="64" :row="2" :loading="loading"></van-skeleton>
        </div>
        <div v-else>
            <!-- 下拉刷新 上拉加载 -->
            <van-pull-refresh v-model="isRefreash" @refresh="onRefresh">
            <van-list
            v-model:loading="isLoading"
            :offset="600"
            :finished="finished"
            finished-text="没有更多了"
            @load="onLoad"
            >
                <!-- <div v-for="item in shopList" :key="item">{{item.name}}</div> -->
                <div v-for="item in shopList" :key="item.id" class="flex border-b-2 px-2" @click="gotoShop(item)">
                    <!-- 左侧 图片栏 -->
                    <div id="shop_item_left" class="flex items-center mr-2">
                        <van-image width="64" height="64" :src="item.imagePath"></van-image>
                    </div>
                    <!-- 右侧 详情栏 -->
                    <div id="shop_item_right" class="flex-1">
                        <header class="flex justify-between items-center">
                            <div class="truncate ... w-36">
                                <van-tag v-if="item.isPremium" color="#ffd930" text-color="black">品牌</van-tag>
                                <span class="ml-2 text-xxs font-bold">{{item.name}}</span>
                            </div>
                            <ul class="flex text-xxs">
                                <li v-if="item.bao" class="border border-gray-200 text-gray-400">{{"保"}}</li>
                                <li v-if="item.zhun" class="border border-gray-200 text-gray-400">{{"准"}}</li>
                                <li v-if="item.piao" class="border border-gray-200 text-gray-400">{{"票"}}</li>
                            </ul>
                        </header>
                        <section>
                            <div class="flex flex-wrap justify-between items-center">
                                <!-- 评分 -->
                                <div>
                                    <van-rate :size="8" allow-half readonly color="#ff9a0d" v-model="item.rating" />
                                    <span class="ml-2 text-3xs font-bold" style="color:#ff9a0d">{{item.rating}}</span>
                                    <!-- 月销 -->
                                    <span class="ml-1 text-3xs">月售{{item.recentOrderNum}}单</span>
                                </div>
                                <!-- 提供服务 -->
                                <div>
                                    <van-tag v-if="item.deliveryMode" color="#3190e8">{{"蜂鸟专送"}}</van-tag>
                                    <van-tag v-else color="#3190e8">{{"商家配送"}}</van-tag>

                                    <van-tag v-if="item.zhun" plain color="#3190e8">准时达</van-tag>
                                </div>
                            </div>
                            <div class="flex flex-wrap justify-between items-center text-gray-500">
                                <div>
                                    <span class="text-3xs">¥{{item.floatMinimumOrderAmount}}起送&nbsp;/&nbsp;{{"配送费约¥"+item.floatDeliveryFee}}</span>
                                </div>
                                <div>
                                    <span v-if="Number(item.distance)" class="text-3xs">{{item.distance > 1000 ? (item.distance/1000).toFixed(2) + 'km' : item.distance + 'm'}} /&nbsp;</span>
                                    <span v-else class="text-3xs">{{item.distance}}&nbsp;/&nbsp;</span>
                                    <span class="text-blue-400 text-3xs">{{"40分钟"}}</span>
                                </div>
                            </div>
                        </section>
                    </div>
                </div>
                <!-- 加载提示 -->
                <template #loading>
                    <div class="bg-gray-200">
                        <span>加载中...</span>
                    </div>
                </template>
                <!-- 触底提示 -->
                <template #finished>
                    <div class="bg-gray-200">
                        <span>没有更多了</span>
                    </div>
                </template>
            </van-list>
            </van-pull-refresh>
        </div>
        <!-- loading加载动画 -->
        <transition name="loading">
            <loading v-if="loading"></loading>
        </transition>
    </div>
</template>

<script>
import { onMounted, toRefs, reactive, watch, onUnmounted } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import { imgBaseUrl } from '@/config/env'
import { getShopList } from '@/api/msite/msite'
import { getShops } from '@/api8080/msite'
import Loading from '@/components/Loading'
export default {
    name: 'ShopList',
    components: {
        Loading
    },
    props: ['geohash', 'restaurantCategoryId', 'restaurantCategoryIds', 'sortByType', 'deliveryMode', 'supportIds', 'confirmSelect'],
    setup(props) {
        const store = useStore()
        const router = useRouter()
        const state = reactive({
            filterStatus: false,  // 是否开启筛选条件
            offset: 1,  // 数据分页加载
            shopList: [],   // 店铺列表
            imgBaseUrl,
            loading: true,  // 加载中
            isRefreash: false,  // 下拉刷新
            isLoading: false,   // 分页数据加载
            finished: false,    // 加载是否完成
            timer: []
        });
        // 监听下拉菜单数据变化实时更新列表数据
        watch(() => [props.restaurantCategoryIds, props.sortByType, props.confirmSelect], ([val1, val2, val3]) => {
            if(val1 || val2 || val3) {
                state.filterStatus = true
            }
            listenDataChange()
        })
        onMounted(() => {
            // 通过添加500ms延迟解决页面刷新vuex数据丢失问题
            state.timer[0] = setTimeout(() => {
                initData()
            }, 500)
        })
        onUnmounted(() => { // 清除定时器
            clearTimeout(state.timer[0])
            clearTimeout(state.timer[1])
            clearTimeout(state.timer[2])
            state.timer[0] = null
            state.timer[1] = null
            state.timer[2] = null
        })
        // 初始化数据
        const initData = async() => {
            let res
            // 判断是否存在筛选条件，分别渲染列表数据
            if(state.filterStatus) {
                res = await getShopList(store.state.latitude, store.state.longitude, state.offset, '', props.restaurantCategoryIds, props.sortByType, props.deliveryMode, props.supportIds)
            }else{
                res =  await getShops(state.offset,20,store.state.longitude,store.state.latitude)
                // res = await getShopList(store.state.latitude, store.state.longitude, state.offset)
            }
            console.log(res)
            let arr = [...res.data.data.rows]
            console.log(arr)
            // 合并数组
            state.shopList.push.apply(state.shopList, arr)
            console.log(state.shopList)
            // 移除骨架屏，展示真实数据
            state.loading = false
            state.isLoading = false;
            // 数据全部加载完成后禁用上拉加载
            if(arr.length < 20) {
                state.finished = true
            }
        }
        const onRefresh = () => {
            state.loading = true
            state.finished = false
            state.shopList = []
            state.offset = 1
            state.timer[1] = setTimeout(() => {
                state.isRefreash = false
                state.isLoading = true
                initData()
            })
        }
        const onLoad = () => {
            state.offset +=20
            state.timer[2] = setTimeout(() => {
                initData()
            });
        }
        const listenDataChange = async() => {    // 筛选条件变化重新渲染数据
            state.loading = true
            state.offset = 0
            let res = await getShopList(store.state.latitude, store.state.longitude, state.offset, '', props.restaurantCategoryIds, props.sortByType, props.deliveryMode, props.supportIds)
            state.loading = false
            state.shopList = [...res.data]
        }
        const gotoShop = (item) => {    // 跳转商铺详情页
           console.log( "触发")
            router.push({path: '/shop', query: {geohash: props.geohash, id: item.id}})
        }
        return {
            ...toRefs(state),
            onRefresh,
            onLoad,
            gotoShop
        }
    }
}
</script>

<style scoped>
</style>