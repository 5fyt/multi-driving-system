<template>
	<view class="page">
		<view class="notice">
			<u-icon name="info-circle-fill" color="#AC9146" size="30" top="3" />
			<text>提示：本页面充值不参与返券活动</text>
		</view>
		<view class="title-container">
			<text class="title">钱包余额</text>
			<text class="balance">{{ balance }}元</text>
		</view>
		<view class="recharge-container">
			<view class="title">充值金额</view>
			<view class="recharge-amount">
				<view :class="amount == 10 ? 'amount active' : 'amount'" @tap="changeHangle(10)">10元</view>
				<view :class="amount == 20 ? 'amount active' : 'amount'" @tap="changeHangle(20)">20元</view>
				<view :class="amount == 50 ? 'amount active' : 'amount'" @tap="changeHangle(50)">50元</view>
				<view :class="amount == 100 ? 'amount active' : 'amount'" @tap="changeHangle(100)">100元</view>
				<view :class="amount == 150 ? 'amount active' : 'amount'" @tap="changeHangle(150)">150元</view>
				<view :class="amount == 200 ? 'amount active' : 'amount'" @tap="changeHangle(200)">200元</view>
				<view :class="amount == 300 ? 'amount active' : 'amount'" @tap="changeHangle(300)">300元</view>
				<view :class="amount == 500 ? 'amount active' : 'amount'" @tap="changeHangle(500)">500元</view>
			</view>
			<view class="desc">点击充值，即表示您已同意“华夏代驾充值规则”</view>
			<button class="btn" @tap="rechargeHandle()">立即充值</button>
		</view>
		<view class="rule-container">
			<view class="title">华夏代驾充值规则：</view>
			<view class="rule">
				<text class="num">1.</text>
				充值金额可以用于支付代驾司机在本代驾程序中与顾客间的通话费，以及缴纳罚款等用途；
			</view>
			<view class="rule">
				<text class="num">2.</text>
				充值金额不计利息，不可提现、退款和转赠；
			</view>
			<view class="rule">
				<text class="num">3.</text>
				充值成功后，您的充值金额会实时进入到您在华夏代驾的账户中。如果遇到充值异常可以拨打400-82014447，有专属客服为您解决问题；
			</view>
			<view class="rule">
				<text class="num">4.</text>
				余额有效期：永久有效
			</view>
		</view>
		<u-top-tips ref="uTips"></u-top-tips>
	</view>
</template>

<script>
export default {
	data() {
		return {
			id: 0,
			balance: 0,
			amount: 50,
			recharge: [10, 20, 50, 100, 150, 200, 300, 500]
		};
	},
	methods: {
		
	},
	onLoad: function() {

	},
	onShow: function() {

	},
	onHide: function() {

	}
};
</script>

<style lang="less">
@import url('recharge.less');
</style>
