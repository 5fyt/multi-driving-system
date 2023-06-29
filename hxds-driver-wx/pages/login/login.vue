<template>
	<view>
		<image src="../../static/login/top.png" mode="widthFix" class="top"></image>
		<image src="../../static/login/logo.png" mode="widthFix" class="logo"></image>
		<view class="desc">
			<text class="name">（司机端）</text>
			<text class="version">Ver20XX.201</text>
		</view>
		<button class="btn" @tap="login()">微信登陆</button>
		<view class="register-container">
			没有账号?
			<text class="link" @tap="toRegisterPage()">立即注册</text>
		</view>
		<text class="remark">本小程序仅限于华夏代驾签约司机使用，新司机必须先注册，经由实名认证之后方可在本小程序中接单运营。</text>
		<u-toast ref="uToast" />
	</view>
</template>

<script>
export default {
	data() {
		return {};
	},
	methods: {
    //登入功能
    /**
     * 调用登入api,在成功回调函数中获取到code，发送登入请求
     * 请求成功后又三种情况，一种是正常登入后，直接登入，status为1时是没有录入证件信息，archive为0时没有录入面部信息，都要跳转到对应的页面中
     * 如果已经注册就按照三种条件在进行判断，如果没有注册提示用户
     * */
    login(){
      let that =this 
      uni.login({
        provider:'weixin',
        success:function(res){
          const code=res.code
          let data={
            code:code
          }
          that.ajax(that.url.login,'POST',data,function(res){
            console.log(res)
            if(!res.data.hasOwnProperty('token')){
              this.$ref.uToast.show({
                title:'请先注册',
                type:'error'
              })
            }else{
              let {token,archive,status } =res.data
              uni.setStorageSync('token',token)
              uni.setStorageSync('status',status)
              that.$refs.uToast.show({
                title:'登入成功',
                type:'success',
                callback:function(res){
                  uni.setStorageSync('workStatus','停止接单')
                  //如果还没有上传身份证等信息就跳转到录入信息页面
                  if(status===0&&archive===0){
                    uni.redirectTo({
                      url:'../../identity/filling/filling?mode=create'
                    })
                  }else if(archive===0){
                    uni.showModal({
                      title:'提示信息',
                      content:'您需要录入脸部信息，如果不录入您将无法接单',
                      confirmText:'录入',
                      cancelText:'取消',
                      success:function(res){
                        if(res.confirm){
                          //不保留当前页面直接跳转到该页面
                          uni.redirectTo({
                            url:'../../identity/face_camera/face_camera?mode=create'
                          })
                        }else{
                          uni.switchTab({
                            url:'../workbench/workbench'
                          })
                        }
                      }
                    })
                  }else{
                    uni.switchTab({
                        url:'../workbench/workbench'
                    })
                  }
                }
              })
            }
          })
        },
      })
    },
		toRegisterPage:function(){
      uni.navigateTo({
        url:'../register/register'
      })
    }
	},
	onLoad: function() {
		
	}
};
</script>

<style lang="less">
@import url('login.less');
</style>
