<template>
    <div>
        <head-top head-title="订单列表" :go-back='true'></head-top>
        <section class="mt-10">
            <van-pull-refresh v-model="isRefreash" @refresh="onRefresh">
                <!-- <p>刷新次数: {{num}}</p> -->
                <van-list
                v-model:loading="isLoading"
                :offset="600"
                :finished="finished"
                finished-text="没有更多了"
                :immediate-check="false"
                @load="onLoad"
                >
                    <ul>
                        <li v-for="(item, index) in orderList" :key="index" class="bg-white my-2">
                            <section class="flex p-2">
                                <van-image width="2rem" height="2rem" :src="item.shop.imagePath"></van-image>
                                <div class="flex flex-col flex-1 divide-y">
                                    <div class="flex justify-between pl-2 pb-1">
                                        <div class="flex flex-col justify-center text-xxs">
                                            <span class="text-1xs">{{item.shop.name}}</span>
                                            <span class="text-gray-400">{{item.createTime}}</span>
                                        </div>
                                        <div class="text-xxs">
                                            {{item.orderStateStr}}
                                        </div>
                                    </div>
                                    <div class="flex justify-between text-xxs pl-2 py-2">
                                        <span class="text-gray-400">{{item.buyCartSkuList[0].name}}{{item.buyCartSkuList.length > 1 ? ' 等' + item.buyCartSkuList.length + '件商品' : ''}}</span>
                                        <span class="text-orange-500">¥{{item.totalPrice.toFixed(2)}}</span>
                                    </div>
                                    <div class="text-right pt-1">
                                        <compute-time v-if="item.orderStateStr == '未支付'" :time="item.createTime"></compute-time>
                        
                                        <router-link tag="span" :to="{path: '/shop', query: {geohash, id: item.shop.id,jsonEntities:item.buyCart.jsonEntities}}" v-else class="text-xxs border p-1 rounded text-blue-400 border-blue-400">再来一单</router-link>
                                    </div>
                                </div>
                            </section>
                        </li>
                    </ul>
                </van-list>
            </van-pull-refresh>
        </section>
    </div>
</template>

<script>
import { onMounted, toRefs, reactive, watch } from 'vue'
import { useStore } from 'vuex'
import { imgBaseUrl } from '@/config/env'
import { getOrderList } from '@/api8080/order'
import HeadTop from '@/components/HeadTop'
import ComputeTime from '@/components/ComputeTime'
export default {
    components: {
        HeadTop,
        ComputeTime
    },
    setup() {
        const store = useStore()
        const state = reactive({
            isRefreash: false,  // 下拉刷新
            isLoading: false,   // 上拉加载
            finished: false,    // 数据加载是否完成
            orderList: [],  // 订单列表
            offset: 1,  // 分页数据
            userInfo: null,
            imgBaseUrl,
            geohash: null,
            statement:{},
        })
        watch(() => store.state.userInfo, (val) => {
            if(val && val.user_id) {
                initData()
            }
        })
        onMounted(() => {
            initData()
        })
        const initData = async() => {
            state.geohash = store.state.geohash
            state.userInfo = store.state.userInfo
            if(store.state.userInfo && store.state.userInfo.id) {
                let res = await getOrderList(state.offset,20,state.userInfo.id)
                console.log("订单表数据:")
                console.log(res.data.data.rows);
                if(state.orderList.length > 0) {
                    state.orderList = [...state.orderList, ...res.data.data.rows]
                }else{
                    state.orderList = res.data.data.rows;
                }
                if(res.data.data.rows.length < 10) {
                    state.finished = true
                }
            }
            state.isLoading = false
        }
        const onRefresh = () => {
            state.orderList = []
            state.offset = 0
            state.isRefreash = false
            state.finished = false
            initData()
        }
        const onLoad = () => {
            state.offset += 10
            initData()
        }
        return {
            ...toRefs(state),
            initData,
            onRefresh,
            onLoad
        }
    }
}
</script>

<style scoped>

</style>