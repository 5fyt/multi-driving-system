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
    <view class="pop">
      <u-popup v-model="showPopup" mode="center" width="600" height="350" :mask-close-able="false" >
        <view class="content">
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
        </view>
      	
      </u-popup>
    </view>
		
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
			timestamp: 60,
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
    chooseCarHandle: function() {
        uni.navigateTo({
            url: '../car_list/car_list'
        });
    },

		caculateLine(that){
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
    },
    //倒数计时，时间五秒发起一次是否被抢单
    countChangeHandle(time){
      let that =this
      if(time!==0&&time%5===0){
        that.showOrder(that)
      }
    },
    //倒数计时结束后取消订单，如果还没有司机接单
    countEndHandle(){
      let that=this
      that.deleteOrder(that)
    },
    //取消订单
    deleteOrder(that){
      let data={
        orderId:that.orderId
      }
      that.ajax(that.url.deleteOrder,'POST',data,function(res){
        console.log('result',res.data.result)
        if(res.data.result){
          uni.showToast({
            title:'取消订单成功',
            icon:'success'
          })
          setTimeout(function(){
            uni.redirectTo({
              url:'../workbench/workbench'
            })
          },3000)
        }else{
          that.showOrder(that)
        }
      })
    },
    //显示订单是否被接单
    showOrder(that){
      let data={
        orderId:that.orderId
      }
      that.ajax(that.url.searchOrderStatus,'POST',data,function(res){
        //如果被隐藏倒计时
        if(res.data.result){
          that.showPopup=false
          that.timestamp=null
          uni.showToast({
            title:'司机已接单',
            icon:'success'
          })
          uni.vibrateShort({})
          setTimeout(function(){
            uni.redirectTo({
              url:'../move/move?orderId='+that.orderId
            })
          },3000)
        }
      })
    },
    //创建订单
    createOrderHandle(){
      let that =this
      if(!that.carPlate||!that.carType){
        uni.showToast({
          title:'你还没设置代驾车辆',
          icon:'error'
        })
        return 
      }
      uni.showToast({
        title:'下单中请稍后',
        icon:'success'
      })
      //隐藏 加载
      setTimeout(function(){
        uni.hideLoading()
      },60000)
      let data={
        carPlate:that.carPlate,
        carType:that.carType,
        startCoordinate:that.from.latitude+','+that.from.longitude,
        startPlace:that.from.address,
        endCoordinate:that.to.latitude+','+that.to.longitude,
        endPlace:that.to.address
      }
      that.ajax(that.url.createCarOrder,'POST',data,function(res){
         console.log('data',res.data)
        uni.hideLoading()
        if(res.data.code==200){
          uni.showToast({
            title:'创建订单成功'
          })
          //orderId
        
          setTimeout(function(){
            that.orderId=res.data.orderID
            that.showPopup=true
            that.timestamp=60
            that.$refs.uCountDown.start()
          },2000)
        }else{
      
          uni.showToast({
            title:`${res.data.message}`,
            icon:'none'
          })
        }
      })
    },
    //手动取消订单
    cancelHandle(){
      let that=this
      that.deleteOrder(that)
    }
	},
	onLoad: function(options) {
		let that = this;
    const {showCar,carId,carPlate,carType}=options
    if(showCar){
      that.showCar=showCar
      that.carId=carId
      that.carType=carType
      that.carPlate=carPlate
    }
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
