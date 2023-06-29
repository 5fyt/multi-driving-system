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
			<!-- <cover-image class="location" src="../static/workbench/location.png" @tap="returnLocationHandle()" /> -->
			<cover-view class="information-container" v-show="infoStatus">
				<cover-image src="../static/move/close-icon.png" class="close-icon" @tap="hideHandle"></cover-image>
				<cover-view class="info">
					<cover-view class="label">剩余</cover-view>
					<cover-view class="value">{{ distance }}公里</cover-view>
					<cover-view class="label">，预计</cover-view>
					<cover-view class="value">{{ duration }}分钟</cover-view>
				</cover-view>
			</cover-view>
		</map>
	</view>
</template>

<script>
let QQMapWX = require('../../lib/qqmap-wx-jssdk.min.js');
let qqmapsdk;
export default {
	data() {
		return {
			map: null,
			mapStyle: '',
			endLatitude: 0,
			endLongitude: 0,
			latitude: 0,
			longitude: 0,
			distance: 0,
			duration: 0,
			polyline: [],
			markers: [],
			timer: null,
			infoStatus: true
		};
	},
	methods: {
		
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
