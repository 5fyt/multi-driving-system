<template>
	<view class="page">
		<view class="item">华夏代驾取消费规则</view>
		<view class="desc">
			为保证平台公平性，顾客发单后需要取消订单的，应自行及时主动取消订单。乘客无正当理由取消订单，诱导、胁迫司机取消订单，或顾客存在下列清醒导致订单取消的，属于顾客有责消单。
		</view>
		<view class="item">乘客有责取消费场景</view>
		<view class="desc"><text class="num">1.</text>临时无代驾需求；</view>
		<view class="desc"><text class="num">2.</text>下错单（其他业务需求）、发错单（信息错误）等</view>
		<view class="desc"><text class="num">3.</text>乘车人数超过车内座位数（包括婴儿在内）；</view>
		<view class="desc"><text class="num">4.</text>携带易燃易爆危险物品或者管制器具；</view>
		<view class="desc"><text class="num">5.</text>要求线下交易或者议价；</view>
		<view class="desc"><text class="num">6.</text>上车点或目的地无法到达，但乘客要求必须到达；</view>
		<view class="desc"><text class="num">7.</text>其他乘客个人原因导致的超时取消；</view>
		<view class="item">乘客无责消单场景</view>
		<view class="desc"><text class="num">1.</text>代驾司机诱导消单，如直接要求消单，给出错误信息诱导消单、加价议价导致消单等；</view>
		<view class="desc"><text class="num">2.</text>因代驾司机自身原因无法正常提供代驾服务导致消单；</view>
		<view class="desc"><text class="num">3.</text>代驾司机没有主动联系顾客，或者顾客联系不上代驾司机导致消单；</view>
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
@import url('cancel_rule.less');
</style>
