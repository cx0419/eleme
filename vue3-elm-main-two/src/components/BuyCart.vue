<template>
    <div>
        <!-- 单价商品 -->
        <div v-if="foods.single" class="flex place-content-center">
            <!-- 移除商品按钮 -->
            <span v-if="foodNum" @click="removeOutCart(foods.categoryId, foods.spuId, foods.foods[0].skuId)">
                <svg class="w-4 h-4" style="fill: #3190e8;">
                    <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#cart-minus"></use>
                </svg>
            </span>
            <span v-if="foodNum" class="text-base mx-1 min-w-8 text-center">{{ foodNum }}</span>
            <!-- 添加商品按钮 -->
            
            <div v-if="foods.foods[0]">
                <span
                    @click="addToCart(foods.categoryId, foods.spuId, foods.foods[0].skuId, foods.name, foods.foods[0].price, '无规格商品',null, foods.foods[0].packingFee)">
                    <svg class="w-4 h-4" style="fill: #3190e8;">
                        <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#cart-add"></use>
                    </svg>
                </span>
            </div>
            <span v-else>不售卖商品</span>
        </div>
        <!-- 多规格商品 -->
        <div v-else>
            <van-button type="primary" size="mini" @click="showCheckModal(foods)">选规格</van-button>
        </div>
    </div>
</template>

<script>
import { reactive, toRefs, onMounted, computed } from 'vue'
import { useStore } from 'vuex'
export default {
    name: 'BuyCart',
    props: ['foods', 'shopId', 'shopCart'],
    setup(props, content) {
        const store = useStore()
        const useMutation = store._mutations
        const foodNum = computed(() => {    // 计算当前商品购买数量
            let categoryId = props.foods.categoryId
            let spuId = props.foods.spuId
            // 若购物车中已存在商品，计算商品总数
            if (props.shopCart && props.shopCart[categoryId] && props.shopCart[categoryId][spuId]) {
                let num = 0;
                Object.values(props.shopCart[categoryId][spuId]).forEach((spu) => {
                    num += spu.num;
                })
                return num;
            } else {
                // 购物车为空，商品为0
                return 0;
            }
        })
        const state = reactive({
            foods: null
        })
        onMounted(() => {
            // console.log(props.shopCart)
        })
        // 移除商品
        const removeOutCart = (categoryId, spuId, skuId) => {
            if (foodNum.value > 0) {
                useMutation.REDUCE_CART[0]({ shopId: props.shopId, categoryId, spuId, skuId })
            }
        }
        // 添加商品
        const addToCart = (categoryId, spuId, skuId, name, price, specsList,specsIdList, packingFee) => {
            useMutation.ADD_CART[0]({ shopId: props.shopId, categoryId, spuId, skuId, name, price, specsList,specsIdList, packingFee })
        }
        // 多规格商品弹出选择弹窗
        const showCheckModal = (foods) => {
            content.emit('showCheckModal', foods)
        }
        return {
            ...toRefs(state),
            foodNum,
            removeOutCart,
            addToCart,
            showCheckModal
        }
    }
}
</script>

<style scoped>

</style>