<template>
	<view class="page">
		<image src="../../static/register/top.jpg" mode="widthFix" class="top" />
		<view class="location-container">
			<view class="left">
				<image src="../../static/register/location.png" mode="widthFix" class="location"></image>
				<text>代驾服务地点</text>
			</view>
			<view class="right">江西省南昌县</view>
		</view>
		<view class="info-container">
			<view class="title-container">
				<image src="../../static/register/title-bg.png" mode="widthFix" class="title-bg"></image>
				<text class="title">基本要求</text>
			</view>
			<view class="list">
				<view class="item">
					<text>1.</text>
					三年安全驾驶经验；
				</view>
				<view class="item">
					<text>2.</text>
					年龄23~55周岁；
				</view>
				<view class="item">
					<text>3.</text>
					无违法犯罪记录、无精神病史、无吸毒史，以及平台认为不适合代驾的其他历史证明；
				</view>
				<view class="item">
					<text>4.</text>
					有熟练驾车经验；
				</view>
				<view class="item">
					<text>5.</text>
					身体健康，无肢体残疾和大面积纹身；
				</view>
				<view class="item">
					<text>6.</text>
					需要提供身份证、驾驶证、直系亲属联系方式，并保存前述材料的真实合法性；
				</view>
			</view>
		</view>
		<view class="info-container">
			<view class="title-container">
				<image src="../../static/register/title-bg.png" mode="widthFix" class="title-bg"></image>
				<text class="title">基本要求</text>
			</view>
			<view class="list">
				<view class="complex-item">
					<view class="left">01</view>
					<view class="right">
						<text class="item-title">在线注册</text>
						<text class="item-desc">在小程序上完成注册</text>
					</view>
				</view>
				<view class="complex-item">
					<view class="left">02</view>
					<view class="right">
						<text class="item-title">基本信息提交</text>
						<text class="item-desc">按照流程提交本人身份证、驾驶证</text>
					</view>
				</view>
				<view class="complex-item">
					<view class="left">03</view>
					<view class="right">
						<text class="item-title">信息审核</text>
						<text class="item-desc">对提交信息以及证件进行审核</text>
					</view>
				</view>
				<view class="complex-item">
					<view class="left">04</view>
					<view class="right">
						<text class="item-title">签署合同</text>
						<text class="item-desc">收到邮寄的合同后必须本人签署</text>
					</view>
				</view>
			</view>
		</view>
		<button class="btn" open-type="getUserInfo" @tap="register()">立即注册</button>
		<u-toast ref="uToast" />
	</view>
</template>

<script>
export default {
	data() {
		return {
			code: null //用户登入凭证
		};
	},
	methods: {
		register:function(){
      let that = this;
       uni.login({
         provider:'weixin',
         success:function(res) {
           let code = res.code
           that.code=code //保存code要将code传给注册接口
         }
       })
       uni.getUserProfile({
         desc:'获取用户头像',
         success:function(res) {
           let nickName = res.userInfo.nickName;
           let avatorUrl = res.userInfo.avatarUrl;
           let data = {
             code:that.code,
             nickname:nickName,
             photo:avatorUrl
           }
           that.ajax(that.url.registerNewDriver,'POST',data,function(res){
             
               const token=res.data.token
               uni.setStorageSync('token',token)
               uni.setStorageSync('realAuth',1) 
               //请求成功后执行的回调函数
               that.$refs.uToast.show({
               					title: '注册成功',
               					type: 'success',
               					callback:function(){
                         console.log(1)
                          uni.redirectTo({
                            url:'../../identity/filling/filling?mode=create' 
                            //下个页面可以接收mode=create这个参数
                          })
                        }
               				})
           })
         }
       })
    }
	}
};
</script>

<style lang="less">
@import url('register.less');
</style>
