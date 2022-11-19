<template>
	<div class="box">
		<!-- 高德地图容器 -->
		<div id="container"></div>
	</div>
</template>

<script>
import { reactive, toRefs, onMounted, onBeforeMount } from 'vue'
import { getCaptchas, accountLogin } from '@/api/login/login'
import { login, getLoginCode } from '@/api8080/login'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import AMapLoader from "@amap/amap-jsapi-loader";
import { MapKey, MapSecretKey } from "../config/mapConfig";
var  map;
export default {
	setup() {
		const state = reactive({
			district: null,
			polygons: [],

		})
		onBeforeMount(() => {
			console.log("进入before")
			// 初始化地图函数
			const initMap = new Promise((resolve, reject) => {
				AMapLoader.load({
					key: `${MapKey}`, // 申请好的Web端开发者Key，首次调用 load 时必填
					//!!! 2.0 许多插件名与老版本不同
					version: "1.4.15", // 指定要加载的 JSAPI 的版本，缺省时默认为 1.4.15。 2.0地图加载慢，地图上下留有网格空白，本人小白找不到原因
					plugins: [
						// 需要使用的的插件列表，如比例尺'AMap.Scale'等
					],
				})
					.then((AMap) => {
						map = new AMap.Map("container", {
							//设置地图容器id
							viewMode: "2D", //是否为3D地图模式
							zoom: 7, //初始化地图级别
							center: [116.418261, 39.921984], //初始化地图中心点位置
							mapStyle: "amap://styles/93d6a789e97e2ead1bd9fee260a7ac20",
						});
						//操作成功
						resolve();
					})
					.catch((e) => {
						//操作失败
						reject(e);
					});
			});
			window._AMapSecurityConfig = {
				securityJsCode: `${MapSecretKey}`,
			};
			initMap
				.then((ok) => {
					//加载行政区查询插件
					//行政区查询插件加载
					function initDistrictSearch() {
						AMap.plugin(["AMap.DistrictSearch"], function () {
							//加载行政区划插件
							if (!district) {
								//实例化DistrictSearch
								let opts = {
									subdistrict: 0, //获取边界不需要返回下级行政区
									extensions: "all", //返回行政区边界坐标组等具体信息
									level: "district", //查询行政级别为 区
								};
								district = new AMap.DistrictSearch(opts);
							}
						});
					}
					initDistrictSearch();
					//绘制默认地区边界
					// searchAndBounds("河北省");
				})
				.catch((err) => {
					console.log(err);
				});
		})
		return {
			...toRefs(state),
			initDistrictSearch,
			initMap,
		}
	}
}
</script>

<style>
.box {
	width: 375px;
	margin-left: 20%;
	height: 667px;
}

#container {
	width: 100%;
	height: 100%;
}

:deep(.custom-content-marker) {
	word-break: keep-all;
	color: #2ddcce;
	font-size: 16px;
	position: relative;
	padding-left: 25px;
	font-weight: 900;
}

:deep(.custom-content-marker::before) {
	content: ".";
	width: 6px;
	height: 6px;
	position: absolute;
	text-indent: -9999px;
	background: #fff;
	display: block;
	top: 3px;
	left: 0;
	border-radius: 10px;
	border: 6px solid #00deff;
	-webkit-box-shadow: 0px 0px 12px #fff;
	box-shadow: 0px 0px 12px #fff;
}

#controller {
	position: absolute;
	z-index: 99;
	top: 20px;
	left: 100px;
	background: white;
	list-style-type: none;
	width: 120px;
}
</style>