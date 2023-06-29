<template>
	<view>
		<u-notice-bar mode="horizontal" :list="notice"></u-notice-bar>
		<view class="step"><u-steps :list="numList" mode="number" :current="0"></u-steps></view>
		<view class="title">【 相关费用 】</view>
		<u-cell-group>
			<u-cell-item icon="red-packet-fill" title="路桥费" :value="tollFee + '元'" @click="enterFee('toll')"></u-cell-item>
			<u-cell-item icon="red-packet-fill" title="停车费" :value="parkingFee + '元'" @click="enterFee('parking')"></u-cell-item>
			<u-cell-item icon="red-packet-fill" title="其他费用" :value="otherFree + '元'" @click="enterFee('other')"></u-cell-item>
		</u-cell-group>
		<button class="btn" @tap="submit">输入好了</button>
		<u-toast ref="uToast" />
		<u-top-tips ref="uTips"></u-top-tips>
	</view>
</template>

<script>
export default {
	data() {
		return {
			notice: ['请认真填写相关金额，发送账单之后则不能修改，请慎重填写费用金额，精确到小数点后两位'],
			numList: [
				{
					name: '结束代驾'
				},
				{
					name: '输入费用'
				},
				{
					name: '提交账单'
				},
				{
					name: '用户付款'
				}
			],
			orderId: null,
			tollFee: '0.00',
			parkingFee: '0.00',
			otherFree: '0.00'
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
@import url('enter_fee.less');
</style>
