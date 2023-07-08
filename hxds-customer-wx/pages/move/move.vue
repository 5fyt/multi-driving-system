<template>
	<view>
		<map
			id="map"
			scale="15"
			:longitude="longitude"
			:latitude="latitude"
			:enable-poi="true"
			class="map"
			:style="mapStyle"
			:polyline="polyline"
			:markers="markers"
			@longpress="showHandle"
		>
			<!-- <image class="location" src="../static/workbench/location.png" @tap="returnLocationHandle()" /> -->
		</map>
		<view class="panel" v-show="infoStatus">
			<view class="info">
				<view class="label">剩余</view>
				<view class="value">{{ distance }}公里</view>
				<view class="label">，预计</view>
				<view class="value">{{ duration }}分钟</view>
				<view class="more" @tap="moreHandle">订单详情</view>
			</view>
			<view class="opt">
				<button class="cancel-btn" :style="cancelStyle">取消订单</button>
				<button class="confirm-btn" v-if="status == 2||status == 3" @tap="driverArriviedHandle">司机到达</button>
			</view>
		</view>
	</view>
</template>

<script>
let QQMapWX = require('../../lib/qqmap-wx-jssdk.min.js');
let qqmapsdk;
export default {
	data() {
		return {
			orderId: null,
			status: null,
			mode: null,
			cancelStyle: '',
			map: null,
			mapStyle: '',
			startLatitude: 0,
			startLongitude: 0,
			endLatitude: 0,
			endLongitude: 0,
			latitude: 0,
			longitude: 0,
			targetLatitude: 0,
			targetLongitude: 0,
			distance: 0,
			duration: 0,
			polyline: [],
			markers: [],
			timer: null,
			infoStatus: true
		};
	},
	methods: {
		formatPolyline(polyline) {
			let coors = polyline;
			let pl = [];
			//坐标解压（返回的点串坐标，通过前向差分进行压缩）
			const kr = 1000000;
			for (let i = 2; i < coors.length; i++) {
				coors[i] = Number(coors[i - 2]) + Number(coors[i]) / kr;
			}
			//将解压后的坐标放入点串数组pl中
			for (let i = 0; i < coors.length; i += 2) {
				pl.push({
					longitude: coors[i + 1],
					latitude: coors[i]
				});
			}
			return pl;
		},
		
	},
	onLoad: function(options) {
		
	},
	onShow: function() {
		
	},
	onHide: function() {
		
	}
};
</script>

<style lang="less">
@import url('move.less');
</style>
