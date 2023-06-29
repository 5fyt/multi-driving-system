<template>
	<view>
		<u-cell-group :border="false">
			<u-cell-item
				title="统一实名认证"
				:title-style="title"
				label="保障账号与出行安全"
				:label-style="label"
				:value="realAuth"
				:border-top="false"
				@click="this.toPage('/identity/filling/filling?mode=fill')"
			/>
			<u-cell-item
				title="修改手机号"
				:title-style="title"
				label="系统会保护您的通信隐私"
				:label-style="label"
				:value="coverTel"
				@click="updateTelHandle()"
			/>
			<u-cell-item
				title="设置支付密码"
				:title-style="title"
				label="系统会保障您钱包资金的安全"
				:label-style="label"
				@click="setPasswordHandle()"
			/>
			<u-cell-item
				title="设置回家地址"
				:title-style="title"
				label="系统将为你规划合理的回家路线"
				:label-style="label"
				@click="updateHomeHandle()"
			/>
			<u-cell-item
				title="接单设置"
				:title-style="title"
				label="系统将为你推荐符合您要求的订单"
				:label-style="label"
				@click="this.toPage('../settings/settings')"
				v-show="realAuth == '已认证'"
			/>
			<u-cell-item
				title="永久注销账号"
				:title-style="title"
				label="操作后账号信息无法恢复，该微信帐户也无法再次注册"
				:label-style="label"
				@click="disableHandle"
			/>
		</u-cell-group>
		<u-toast ref="uToast" />
		<u-top-tips ref="uTips"></u-top-tips>
		<u-keyboard
			default=""
			ref="uKeyboard"
			mode="number"
			:mask="true"
			:mask-close-able="false"
			:dot-enabled="false"
			v-model="showKeyboard"
			:safe-area-inset-bottom="true"
			:tooltip="false"
			@change="keyboardChangeHandle"
			@backspace="backspaceHandle"
		>
			<view>
				<view class="u-text-right u-padding-30">
					<view class="close" data-flag="false" @tap="showKeyboardHandle(false)">
						<u-icon name="close" color="#333333" size="32"></u-icon>
					</view>
				</view>
				<view class="u-flex u-row-center">
					<u-message-input
						mode="box"
						:maxlength="6"
						:dot-fill="true"
						v-model="password"
						:disabled-keyboard="true"
						@finish="finish"
					></u-message-input>
				</view>
				<view class="u-text-center u-padding-top-10 u-padding-bottom-20 tips">输入华夏代驾钱包支付密码</view>
			</view>
		</u-keyboard>
		<u-top-tips ref="uTips"></u-top-tips>
	</view>
</template>

<script>
const chooseLocation = requirePlugin('chooseLocation');
export default {
	data() {
		return {
			title: {
				color: '#444',
				'font-size': '34rpx',
				'font-weight': 'bold'
			},
			label: {
				'font-size': '24rpx',
				color: '#a0a0a0',
				'font-weight': 'normal'
			},
			realAuth: '',
			tel: '',
			coverTel: '',
			showKeyboard: false,
			password: ''
		};
	},
	methods: {
		
	},
	onLoad: function() {
		
	},
	onShow: function() {
		
	},
	onUnload: function() {
		
	},
	onHide: function() {
		
	}
};
</script>

<style lang="less">
@import url('account.less');
</style>
