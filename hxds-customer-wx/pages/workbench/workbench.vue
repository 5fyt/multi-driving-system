<template>
	<view>
		<map id="map" :longitude="longitude" :latitude="latitude" :style="contentStyle" scale="15" :enable-traffic="false" :show-location="true" :enable-poi="true" class="map">
			<cover-image class="location" src="../../static/workbench/location.png" @tap="returnLocationHandle()"></cover-image>
		</map>
		<view class="panel">
			<view class="from" @tap="chooseLocationHandle('from')">
				<text>{{ from.address }}</text>
			</view>
			<view class="dashed-line"></view>
			<view class="to" @tap="chooseLocationHandle('to')">
				<text>{{ to.address }}</text>
			</view>
		</view>
	</view>
</template>

<script>
const chooseLocation = requirePlugin('chooseLocation');
let QQMapWX = require('../../lib/qqmap-wx-jssdk.min.js');

export default {
	data() {
		return {
			from: {
				address: '',
				longitude: 0,
				latitude: 0
			},
			to: {
				address: '输入你的目的地',
				longitude: 0,
				latitude: 0
			},
			longitude: 116.397505,
			latitude: 39.908675,
			contentStyle: '',
			windowHeight: 0,
			map: null,
			flag: null
		};
	},
	methods: {
		returnLocationHandle(){
      this.map.moveToLocation() //移动当前的经纬度
    },
    //地图选点服务
    chooseLocationHandle(flag){
      let that =this
      let key=that.tencent.map.key
      let referer=that.tencent.map.referer
      that.flag=flag
      let location=JSON.stringify({
        latitude:that.latitude,
        longitude:that.longitude
      })
      wx.navigateTo({
        url: `plugin://chooseLocation/index?key=${key}&referer=${referer}&location=${location}`
      });
    }
	},
	onShow: function() {
    let that = this
		//定义地图上下文 //这个 老东西没有更新
		that.map=uni.createMapContext("map",this)
		let qqMapSdk=new QQMapWX({
		  key:that.tencent.map.key
		})
		uni.$on('updateLocation',function(location){
		  if(that.flag!=null){
		    return
		  }
		  let {latitude,longitude}=location
		  that.latitude=latitude
		  that.longitude=longitude
		  that.from.latitude=latitude
		  that.from.longitude=longitude
		  //将经纬度解析成上车地点
		  qqMapSdk.reverseGeocoder({
		    location:{
		      longitude,
          latitude
		    },
		    success:function(res){
		      that.from.address=res.result.address
		    }
		  })
		})
    
      const location = chooseLocation.getLocation(); // 如果点击确认选点按钮，则返回选点结果对象，否则返回null
      if(location!=null){
        const {name,latitude,longitude}=location
        if(that.flag==='from'){
          that.from.address=name
          that.from.latitude=latitude
          that.from.longitude=longitude
        }else{
          that.to.address=name
          that.to.latitude=latitude
          that.to.longitude=longitude
          //选好目的地之后自动跳转到线路页面
          uni.setStorageSync('from',that.from)
          uni.setStorageSync('to',that.to)
          uni.navigateTo({
            url:'../create_order/create_order'
          })
        }
      }
	},
	onHide: function() {
		uni.$off('unpdateLocation')
    //清除地图选点的结果
    chooseLocation.setLocation(null)
	},
	onLoad: function() {
		let that = this;
		let windowHeight = uni.getSystemInfoSync().windowHeight;
		that.windowHeight = windowHeight;
		that.contentStyle = `height:${that.windowHeight}px;`;
	},
	onUnload: function() {
		chooseLocation.setLocation(null)
    this.flag=null
	}
};
</script>

<style lang="less">
@import url('workbench.less');
</style>
