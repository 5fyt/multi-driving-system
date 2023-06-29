<template>
	<view class="page">
		<view class="title">大连市</view>
		<view class="section">
			<view class="section-title">代驾基础费</view>
			<view class="item"><text class="left">起步价（分时段）</text></view>
			<view class="row">
				<text class="label">06:00 ~ 21:59</text>
				<text class="value">35元/含8公里</text>
			</view>
			<view class="row">
				<text class="label">22:00 ~ 22:59</text>
				<text class="value">50元/含8公里</text>
			</view>
			<view class="row">
				<text class="label">23:00 ~ 23:59</text>
				<text class="value">65元/含8公里</text>
			</view>
			<view class="row">
				<text class="label">00:00 ~ 05:59</text>
				<text class="value">85元/含8公里</text>
			</view>
			<view class="item">
				<text class="left">里程费</text>
				<text class="right">5元/公里</text>
			</view>
			<view class="desc">超过起步里程后开始计算。所有计费单位都按整数计算，如0.1公里记为1公里，1.1分钟记为2分钟。</view>
			<view class="item">
				<text class="left">等候费</text>
				<text class="right">1元/分钟</text>
			</view>
			<view class="desc">代驾司机到达后可以免费等待顾客10分钟，超过后每分钟收取1元，代驾司机最多等待顾客190分钟（包含等待时间）。</view>
		</view>
		<view class="section">
			<view class="section-title">计费规则</view>
			<view class="desc">收取费用 = 起步价 + 里程费 + 等候费 + 动态加价费 + 附加服务费（各项费用按照实际产生收取，不产生不收取）。</view>
		</view>
		<view class="section">
			<view class="section-title">附加计费</view>
			<view class="item"><text class="left">动态加价</text></view>
			<view class="desc">
				当雨雪天气，周围司机较少或需求过旺的情况下，为了促进承担，鼓励司机更快接单，平台会临时对订单起步价、里程费和等候费适当调价，保障您的出行，调价上限为1.50倍。
			</view>
			<view class="desc">代驾过程中产生的路桥费、停车费、住宿费等，由车主承担。</view>
			<view class="desc">若遇到费用过高或者其他异常情况，需按预估价提前支付该笔代驾费用。服务结束后，根据实际费用多退少补。</view>
			<view class="item"><text class="left">取消费</text></view>
			<view class="desc">为了保证平台公平性，因您的责任导致订单取消时，您需支付一定的费用，用于补偿司机的空驶成本</view>
		</view>
		<u-top-tips ref="uTips"></u-top-tips>
	</view>
</template>

<script>
export default {
	data() {
		return {};
	},
	methods: {},
	onShow: function() {
		
	},
	onHide: function() {
		
	}
};
</script>

<style lang="less">
@import url('bill_rule.less');
</style>
