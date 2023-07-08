<template>
	<view>
		<image src="../../static/login/top.png" mode="widthFix" class="top"></image>
		<image src="../../static/login/logo.png" mode="widthFix" class="logo"></image>
		<view class="desc">
			<text class="name">（乘客端）</text>
			<text class="version">Ver20XX.201</text>
		</view>
		<button class="btn" @tap="login">微信登陆</button>
		<view class="register-container">
			没有账号?
			<text class="link" @tap="register">立即注册</text>
		</view>
		<text class="remark">本小程序仅限于华夏代驾客户使用，新用户必须先注册方可在本小程序中下单。</text>
		<u-toast ref="uToast" />
	</view>
</template>

<script>
export default {
	data() {
		return {
			code: null
		};
	},
	methods: {
		register() {
		    let that = this;
		    uni.showModal({
		        title: '提示信息',
		        editable: true,
		        placeholderText: '输入手机号',
		        success: function(resp) {
		            let phone = resp.content;
		            if (resp.confirm) {
		                let bool = that.checkValidTel(phone, '手机号码');
		                if (bool) {
		                    uni.login({
		                        provider: 'weixin',
		                        success: function(resp) {
		                            let code = resp.code;
		                            that.code = code;
		                        }
		                    });
		                    uni.getUserProfile({
		                        desc: '获取用户信息',
		                        success: function(resp) {
		                            let nickname = resp.userInfo.nickName;
		                            let photo = resp.userInfo.avatarUrl;
		                            let gender = resp.userInfo.gender;
		                            let sexJson = {
		                                '0': '无',
		                                '1': '男',
		                                '2': '女'
		                            };
		                            gender = sexJson[gender+""];
		
		                            let data = {
		                                code: that.code,
		                                nickname: nickname,
		                                photo: phone,
		                                gender: gender,
		                                phone: phone
		                            };
		                            that.ajax(that.url.registerNewCustomer, 'POST', data, function(resp) {
		                                let token = resp.data.token;
		                                uni.setStorageSync('token', token);
		                                that.$refs.uToast.show({
		                                    title: '注册成功',
		                                    type: 'success',
		                                    callback: function() {
		                                        uni.switchTab({
		                                            url: '../workbench/workbench'
		                                        });
		                                    }
		                                });
		                            });
		                        }
		                    });
		                }
		            }
		        }
		    });
		},
    login() {
        let that = this;
        uni.login({
            provider: 'weixin',
            success: function(resp) {
                let code = resp.code;
                let data = {
                    code: code
                };
                that.ajax(that.url.login, 'POST', data, function(resp) {
                    if (!resp.data.hasOwnProperty('token')) {
                        that.$refs.uToast.show({
                            title: '请先注册',
                            type: 'error'
                        });
                    } else {
                        let token = resp.data.token;
                        uni.setStorageSync('token', token);
                        that.$refs.uToast.show({
                            title: '登陆成功',
                            type: 'success',
                            callback: function() {
                                uni.switchTab({
                                    url: '../workbench/workbench'
                                });
                            }
                        });
                    }
                });
            }
        });
    }


	},
	onLoad: function() {}
};
</script>

<style lang="less">
@import url('login.less');
</style>
