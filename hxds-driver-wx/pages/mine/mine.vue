<template>
	<view class="page">
		<view class="summary-container">
		<!-- 	<image :src="photo" mode="widthFix" class="photo" ></image> -->
      <button  open-type="chooseAvatar" @chooseavatar="onChooseAvatar" class="avatarBtn">
        <image class="photo" :src="photo" mode="widthFix"></image>
      </button>
     
			<view class="summary">
				<view class="row">
					<view class="name">
           <input type="nickname" class="nameInp" placeholder="请输入昵称"/>
          </view>
					<image
						:src="realAuth ? '../../static/mine/icon-1.png' : '../../static/mine/icon-2.png'"
						mode="widthFix"
						class="auth-icon"
					></image>
					<text :class="realAuth ? 'auth' : 'unauth'">{{ realAuth ? '已认证' : '未认证' }}</text>
				</view>
				<view class="row">
					<view class="attribute">
						<view class="item">代驾：{{ years }}年</view>
						<view class="item">等级：Lv{{ level }}</view>
						<view class="item">接单：{{ changeNumber(totalOrder) }}</view>
					</view>
				</view>
			</view>
		</view>
		<view class="notice-container">
			<view class="notice-title">
				<image src="../../static/mine/icon-3.png" mode="widthFix"></image>
				<text>{{ levelName }}</text>
			</view>
			<view class="desc">升级Lv{{ level + 1 }}等级还需接单{{ 50 - (totalOrder % 50) }}个，并且为顾客提供安全和高质量代驾服务</view>
		</view>
		<view class="nav-container">
			<view class="row">
				<view class="item" @tap="toPage('../../wallet/recharge/recharge')">
					<image src="../../static/mine/icon-4.png" mode="widthFix"></image>
					<view class="info">
						<text class="title">钱包</text>
						<text class="desc">总额{{ balance }}元</text>
					</view>
				</view>
				<view class="border-column"></view>
				<view class="item">
					<image src="../../static/mine/icon-5.png" mode="widthFix"></image>
					<view class="info">
						<text class="title">订单</text>
						<text class="desc">本周有{{ weekOrder }}单</text>
					</view>
				</view>
			</view>
			<view class="border-row"></view>
			<view class="row">
				<view class="item">
					<image src="../../static/mine/icon-6.png" mode="widthFix"></image>
					<view class="info">
						<text class="title">顾客评价</text>
						<text class="desc">近期{{ weekComment }}条好评</text>
					</view>
				</view>
				<view class="border-column"></view>
				<view class="item">
					<image src="../../static/mine/icon-7.png" mode="widthFix"></image>
					<view class="info">
						<text class="title">我的申诉</text>
						<text class="desc">正在申诉{{ appeal }}件</text>
					</view>
				</view>
			</view>
		</view>
		<view class="tab-container">
			<view class="item">
				<view class="ball ball-1"></view>
				<text>活动中心</text>
			</view>
			<view class="item">
				<view class="ball ball-2"></view>
				<text>积分商城</text>
			</view>
			<view class="item">
				<view class="ball ball-3"></view>
				<text>本月排名</text>
			</view>
			<view class="item">
				<view class="ball ball-4"></view>
				<text>我的数据</text>
			</view>
		</view>
		<u-cell-group :border="false">
			<u-cell-item
				icon="account-fill"
				:icon-style="icon"
				title="账号与安全"
				:border-top="false"
				@click="this.toPage('../../user/account/account')"
			/>
			<u-cell-item icon="server-fill" :icon-style="icon" title="在线客服" @click="serviceHandle" />
			<u-cell-item icon="trash-fill" :icon-style="icon" title="清理缓存" @click="clearHandle" />
			<u-cell-item
				icon="file-text-fill"
				:icon-style="icon"
				title="用户指南"
				:border-bottom="false"
				@click="this.toPage('../../rule/user_guide/user_guide')"
			/>
		</u-cell-group>
		<view class="exit"><button class="btn" @tap="logoutHandle()">退出系统</button></view>
		<u-top-tips ref="uTips"></u-top-tips>
	</view>
</template>

<script>
let dayjs = require('dayjs');
export default {
	data() {
		return {
			icon: {
				color: '#8A8B8F',
				'margin-top': '-1rpx'
			},
			name: '',
			photo: '',
      show:false,
			realAuth: false,
			years: 0,
			level: 0,
			levelName: '',
			balance: 0,
			totalOrder: 0,
			weekOrder: 0,
			weekComment: 0,
			appeal: 0
		};
	},
	methods: {
    onChooseAvatar(e){
      let that= this
      const{avatarUrl}=e.detail
      that.photo=avatarUrl      
      console.log(e.detail)
    },
    //在线客服
		serviceHandle(){
      //点击震动
      uni.vibrateShort({})
      uni.makePhoneCall({
        phoneNumber:'10086'
      })
    },
    //清理缓存
    clearHandle(){
      uni.vibrateShort({})
      uni.showModal({
        title:'提示消息',
        content:'清理缓存',
        success:function(res){
         if(res.confirm){
            uni.vibrateShort({})
            //清理缓存
            let caches=uni.getStorageInfoSync()
            for(let cache of caches.keys){
              if(cache==='token'||cache==='realAuth'){
                continue;
              }
              uni.removeStorageSync(cache)
            }
            //清理保存的临时文件
            uni.getSavedFileList({
              success:function(res){
                for(let file of res.fileList){
                  let path = file.filePath
                  uni.removeSavedFile({
                    filePath:path,
                    success:function(res){
                      console.log('清理文件成功')
                    }
                  })
                }
              }
            })
            setTimeout(function(){
              uni.showToast({
                title:'清理完成'
              })
            },600)
         }
        }
      })
    }
	},
	onShow: function() {
		let that =this 
    that.ajax(that.url.driverBaseInfo,'GET',null,function(res){
      const {nickname,photo,status,createTime,level,totalOrder,appeal,weekOrder,balance,weekComment}=res.data
      that.name=nickname
      that.photo=photo
      that.realAuth=uni.getStorageSync('realAuth')===0
      //代驾几年
      let initTime=dayjs(createTime,'YYYY-MM-DD')
      let nowTime=dayjs()
      that.years=nowTime.diff(initTime,'years')
      that.level=level
      if(that.level<10){
        that.levelName='初级代驾'
      }else if(that.level<30){
        that.levelName='中级代驾'
      }else if(that.level<50){
        that.levelName='高级代驾'
      }else{
        that.levelName='王牌代驾'
      }
      that.totalOrder=totalOrder
      that.weekComment=weekComment
      that.balance=balance
      that.appeal=appeal
    })
	},
	onHide: function() {
		
	}
};
</script>

<style lang="less">
@import url('mine.less');
</style>
