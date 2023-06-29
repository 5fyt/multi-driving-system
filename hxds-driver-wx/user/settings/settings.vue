<template>
	<view>
		<u-cell-group :border="false">
			<u-cell-item icon="setting-fill" title="开启听单" :arrow="false" :border-top="false">
				<u-switch
					slot="right-icon"
					v-model="listenService.status"
					:loading="listenService.loading"
					:vibrate-short="true"
					active-color="#19BE6B"
					@change="changeListenServiceHandle"
				></u-switch>
			</u-cell-item>
			<u-cell-item icon="setting-fill" title="自动抢单" :arrow="false" :border-top="false">
				<u-switch
					slot="right-icon"
					v-model="autoAccept.status"
					:loading="autoAccept.loading"
					:vibrate-short="true"
					active-color="#19BE6B"
					@change="changeAutoAcceptHandle"
				></u-switch>
			</u-cell-item>
			<u-cell-item icon="setting-fill" title="定向接单" :arrow="false">
				<u-switch
					slot="right-icon"
					v-model="orientationService.status"
					:loading="orientationService.loading"
					:vibrate-short="true"
					active-color="#19BE6B"
					@change="changeOrientationHandle"
				></u-switch>
			</u-cell-item>
			<u-cell-item icon="info-circle-fill" title="接单范围" :value="rangeDistance.label" @tap="rangeDistanceHandle()"></u-cell-item>
			<u-cell-item icon="info-circle-fill" title="代驾里程" :value="orderDistance.label" @tap="orderDistanceHandle()"></u-cell-item>
		</u-cell-group>
		<u-select v-model="rangeDistance.show" :list="rangeDistance.list" :default-value="rangeDistance.default" @confirm="confirmRangeDistanceHandle"></u-select>
		<u-select v-model="orderDistance.show" :list="orderDistance.list" :default-value="orderDistance.default" @confirm="confirmOrderDistanceHandle"></u-select>
	</view>
</template>

<script>
const chooseLocation = requirePlugin('chooseLocation');
export default {
	data() {
		return {
			listenService: {
				status: true,
				loading: true
			},
			autoAccept: {
				status: true,
				loading: true
			},
			orientationService: {
				status: false,
				loading: true,
				localtion: {
					place: '',
					latitude: 0,
					longitude: 0
				}
			},
			rangeDistance: {
				label: '5公里',
				default: [0],
				value: 0,
				show: false,
				list: [{ value: 5, label: '5公里以内' }, { value: 3, label: '3公里以内' }, { value: 1, label: '1公里以内' }]
			},
			orderDistance: {
				label: '5公里',
				default: [0],
				value: 0,
				show: false,
				list: [
					{ value: 0, label: '无限制' },
					{ value: 30, label: '30~15公里' },
					{ value: 15, label: '10~15公里' },
					{ value: 10, label: '5~10公里' },
					{ value: 5, label: '5公里以内' }
				]
			}
		};
	},
	methods: {
		changeListenServiceHandle: function() {
			let that = this;
			//更新storage中缓存数据
			let settings = uni.getStorageSync('settings');
			settings.listenService = that.listenService.status;
			uni.setStorageSync('settings', settings);
			//提交Ajax更新数据
			that.ajax(that.url.updateDriverSettings, 'POST', { listenService: that.listenService.status }, function(resp) {});
		},
		changeAutoAcceptHandle: function() {
			let that = this;
			//更新storage中缓存数据
			let settings = uni.getStorageSync('settings');
			settings.autoAccept = that.autoAccept.status;
			uni.setStorageSync('settings', settings);
			//提交Ajax更新数据
			that.ajax(that.url.updateDriverSettings, 'POST', { autoAccept: that.autoAccept.status }, function(resp) {});
		},
		changeOrientationHandle: function() {
			let that = this;
			//跳转到地图选点页面
			if (that.orientationService.status) {
				uni.showModal({
					title: '提示消息',
					content: '请设置定向接单定位',
					success: function(resp) {
						if (resp.confirm) {
							let location = uni.getStorageSync('location');
							let latitude = location.latitude;
							let longitude = location.longitude;
							let key = that.tencent.map.key; //使用在腾讯位置服务申请的key
							let referer = that.tencent.map.referer; //调用插件的app的名称
							let data = JSON.stringify({
								latitude: latitude,
								longitude: longitude
							});
							uni.navigateTo({
								url: `plugin://chooseLocation/index?key=${key}&referer=${referer}&location=${data}`
							});
						} else {
							that.orientationService.status = false;
						}
					}
				});
			} else {
				//更新storage中缓存数据
				let settings = uni.getStorageSync('settings');
				settings.orientation = '';
				uni.setStorageSync('settings', settings);
				//提交Ajax更新数据
				that.ajax(that.url.updateDriverSettings, 'POST', { orientation: '' }, function(resp) {});
			}
		},
		rangeDistanceHandle: function() {
			let that = this;
			that.rangeDistance.show = true;
		},
		orderDistanceHandle: function() {
			let that = this;
			that.orderDistance.show = true;
		},

		confirmRangeDistanceHandle: function(resp) {
			let that = this;
			let label = resp[0].label;
			let value = resp[0].value;
			//列表控件不销毁，再次打开列表还是你刚刚选中的值，所以不需要更改default选中值
			that.rangeDistance.label = label;
			that.rangeDistance.value = value;
			//更新storage中缓存的数据
			let settings = uni.getStorageSync('settings');
			settings.rangeDistance = value;
			uni.setStorageSync('settings', settings);
			//提交Ajax更新数据
			that.ajax(that.url.updateDriverSettings, 'POST', { rangeDistance: value }, function(resp) {});
		},
		confirmOrderDistanceHandle: function(resp) {
			let that = this;
			let label = resp[0].label;
			let value = resp[0].value;
			//列表控件不销毁，再次打开列表还是你刚刚选中的值，所以不需要更改default选中值
			that.orderDistance.label = label;
			that.orderDistance.value = value;
			//更新storage中缓存的数据
			let settings = uni.getStorageSync('settings');
			settings.orderDistance = value;
			uni.setStorageSync('settings', settings);
			//提交Ajax更新数据
			that.ajax(that.url.updateDriverSettings, 'POST', { orderDistance: value }, function(resp) {});
		}
	},
	onShow: function() {
		let that = this;
		//定时刷新消息
		uni.$on('message', function() {
			that.refreshMessage(that);
		});

		uni.$on('showMessageTip', function(lastRows) {
			that.$refs.uTips.show({
				title: `最新收到${lastRows}条消息通知`,
				type: 'success',
				duration: '2300'
			});
		});

		let settings = uni.getStorageSync('settings');
		let location = chooseLocation.getLocation();
		if (location != null) {
			let place = location.name;
			let latitude = location.latitude;
			let longitude = location.longitude;
			let data = {
				place: place,
				latitude: latitude,
				longitude: longitude
			};
			//更新storage中缓存数据
			settings.orientation = data;
			uni.setStorageSync('settings', settings);
			that.ajax(that.url.updateDriverSettings, 'POST', data, function(resp) {});
		} else {
			let orientation = settings.orientation;
			if (orientation != '' && orientation != null) {
				that.orientationService.status = true;
			} else {
				that.orientationService.status = false;
			}
		}
		that.orientationService.loading = false;
	},
	onLoad: function() {
		let that = this;
		let settings = uni.getStorageSync('settings');

		let listenService = settings.listenService;
		that.listenService.status = listenService;
		that.listenService.loading = false;

		let autoAccept = settings.autoAccept;
		that.autoAccept.status = autoAccept;
		that.autoAccept.loading = false;

		let orientation = settings.orientation;
		// console.log(orientation.length)
		if (orientation.length == 0) {
			that.orientationService.status = false;
			that.orientationService.loading = false;
		}
		let rangeDistance = settings.rangeDistance;
		for (let i in that.rangeDistance.list) {
			let one = that.rangeDistance.list[i];
			if (one.value == rangeDistance) {
				that.rangeDistance.default = [i];
				that.rangeDistance.label = one.label;
				break;
			}
		}
		let orderDistance = settings.orderDistance;
		for (let i in that.orderDistance.list) {
			let one = that.orderDistance.list[i];
			if (one.value == orderDistance) {
				that.orderDistance.default = [i];
				that.orderDistance.label = one.label;
				break;
			}
		}
	},
	onUnload: function() {
		// 页面卸载时设置插件选点数据为null，防止再次进入页面，geLocation返回的是上次选点结果
		chooseLocation.setLocation(null);
	},
	onHide: function() {
		uni.$off('message');
		uni.$off('showMessageTip');
	}
};
</script>

<style lang="less">
@import url('settings.less');
</style>
