<template>
	<view>
		<view class="address-container">
			<view class="from">
				<text>{{ from.address }}</text>
			</view>
			<view class="dashed-line"></view>
			<view class="to">
				<text>{{ to.address }}</text>
			</view>
		</view>
		<map
			id="map"
			:longitude="from.longitude"
			:latitude="from.latitude"
			:style="contentStyle"
			scale="12"
			:enable-traffic="false"
			:show-location="true"
			class="map"
			:polyline="polyline"
			:markers="markers"
		></map>

		<view class="panel">
			<view class="row">
				<view class="info">
					<view class="label">全程</view>
					<view class="value">
						<text>{{ distance }}</text>
						公里
					</view>
					<view class="label">，预计</view>
					<view class="value">
						<text>{{ duration }}</text>
						分钟
					</view>
				</view>
				<view class="opt" @tap="chooseCarHandle" v-if="!showCar">选择车辆</view>
				<view class="opt" @tap="chooseCarHandle" v-if="showCar">{{ carPlate }}</view>
			</view>
			<button class="btn" @tap="createOrderHandle">确定下单</button>
		</view>
		<u-popup v-model="showPopup" mode="center" width="600" height="350" :mask-close-able="false">
			<view class="popup-title">您的订单正在等待司机接单</view>
			<view class="count-down">
				<u-count-down
					ref="uCountDown"
					:timestamp="timestamp"
					:autoplay="false"
					separator="zh"
					:show-hours="false"
					:show-border="true"
					bg-color="#DDF0FF"
					separator-color="#0083F3"
					border-color="#0D90FF"
					color="#0D90FF"
					font-size="32"
					@end="countEndHandle"
					@change="countChangeHandle"
				></u-count-down>
			</view>
			<button class="btn" @tap="cancelHandle">取消订单</button>
		</u-popup>
	</view>
</template>

<script>
let QQMapWX = require('../../lib/qqmap-wx-jssdk.min.js');
let qqmapsdk;
export default {
	data() {
		return {
			from: {
				address: '',
				longitude: 0,
				latitude: 0
			},
			to: {
				address: '',
				longitude: 0,
				latitude: 0
			},
			contentStyle: '',
			windowHeight: 0,
			distance: 0,
			duration: 0,
			polyline: [],
			markers: [],
			infoStatus: true,
			carId: null,
			carPlate: null,
			carType: null,
			showCar: false,
			showPopup: false,
			timestamp: 15 * 60,
			orderId: null
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
		caculateLine(that){
      console.log('vue',that)
      qqmapsdk.direction({
        mode:'driving',
        from:{
          latitude:that.from.latitude,
          longitude:that.from.longitude
        },
        to:{
          latitude:that.to.latitude,
          longitude:that.to.longitude
        },
        success:function(res){
          if(res.status!==0){
            uni.showToast({
              title:res.message,
              icon:'error'
            })
            return
          }
          let route=res.result.routes[0]
          that.distance=Math.ceil((route.distance/1000)*10)/10 //计算公里数
          that.duration=route.duration
          let polyline=route.polyline
          let points=that.formatPolyline(polyline)
          //指定一系列坐标点，从数组第一项连线至最后一项
          that.polyline=[{
            points:points,
            color:'#05B473',
            arrowLine:true,
            width:6
          }]
          //标记点用于在地图上显示标记的位置
          that.markers=[
            {
              id:1,
              latitude:that.from.latitude,
              longitude:that.from.longitude,
              width:25,
              height:35,
              anchor:{
                x:0.5,
                y:0.5
              },
              iconPath:'https://mapapi.qq.com/web/lbs/javascriptGL/demo/img/start.png'
            },
            {
              id:2,
              latitude:that.to.latitude,
              longitude:that.to.longitude,
              width:25,
              height:35,
              anchor:{
                x:0.5,
                y:0.5
              },
              iconPath:'https://mapapi.qq.com/web/lbs/javascriptGL/demo/img/end.png'
            }
          ]
        }
      })
    }
		
	},
	onLoad: function(options) {
		let that = this;
		//设置地图控件的高度适配屏幕高度
		let windowHeight = uni.getSystemInfoSync().windowHeight;
		that.windowHeight = windowHeight;
		that.contentStyle = `height:${that.windowHeight}px;`;
    qqmapsdk = new QQMapWX({
      key:that.tencent.map.key
    })
    //初始化地图
    that.map=uni.createMapContext('map')
        
	},
	onShow: function() {
    let that = this 
    let from=uni.getStorageSync('from')
    let to=uni.getStorageSync('to')
    that.from=from
    that.to=to
    that.caculateLine(that)
	}
};
</script>

<style lang="less">
@import url('create_order.less');
</style>
