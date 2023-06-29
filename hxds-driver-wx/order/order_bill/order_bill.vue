<template>
	<view>
		<view>
			<view class="setion-title">
				<image src="../static/order/money.png" mode="widthFix"></image>
				<text>基础收费</text>
			</view>
			<view class="section-content">
				<view class="item">
					<view class="left">
						<text class="item-title">里程费（{{ realMileage }}公里）</text>
						<text class="item-desc">时段收费（{{ baseMileagePrice }}元{{ baseMileage }}公里，超出每公里{{ exceedMileagePrice }}元）</text>
					</view>
					<view class="right">{{ mileageFee }}</view>
				</view>
				<view class="item">
					<view class="left">
						<text class="item-title">时长费（{{ waitingMinute }}分钟）</text>
						<text class="item-desc">免费{{ baseMinute }}分钟，超出部分每分钟{{ exceedMinutePrice }}元</text>
					</view>
					<view class="right">{{ waitingFee }}</view>
				</view>
				<view class="item">
					<view class="left">
						<text class="item-title">返程费（{{ returnMileage }}公里）</text>
						<text class="item-desc">总里程超过{{ baseReturnMileage }}公里，每公里{{ exceedReturnPrice }}元</text>
					</view>
					<view class="right">{{ returnFee }}</view>
				</view>
			</view>
		</view>
		<view>
			<view class="setion-title">
				<image src="../static/order/money.png" mode="widthFix"></image>
				<text>额外收费</text>
			</view>
			<view class="section-content">
				<view class="item">
					<view class="left">
						<text class="item-title">停车费</text>
						<text class="item-desc">如果代驾司机预付停车费，该费用将计入订单费用</text>
					</view>
					<view class="right">{{ parkingFee }}</view>
				</view>
				<view class="item">
					<view class="left">
						<text class="item-title">路桥费</text>
						<text class="item-desc">如果代驾司机预付停车费，该费用将计入订单费用</text>
					</view>
					<view class="right">{{ tollFee }}</view>
				</view>
				<view class="item">
					<view class="left">
						<text class="item-title">其他费用</text>
						<text class="item-desc">代驾过程中产生的其他费用</text>
					</view>
					<view class="right">{{ otherFree }}</view>
				</view>
			</view>
		</view>
		<view>
			<view class="setion-title">
				<image src="../static/order/money.png" mode="widthFix"></image>
				<text>奖励费</text>
			</view>
			<view class="section-content">
				<view class="item">
					<view class="left">
						<text class="item-title">客户好处费</text>
						<text class="item-desc">代驾客户赠与的红包奖励</text>
					</view>
					<view class="right">{{ favourFee }}</view>
				</view>
				<view class="item">
					<view class="left">
						<text class="item-title">系统奖励费</text>
						<text class="item-desc">华夏代驾系统激励代驾司机的奖励</text>
					</view>
					<view class="right">{{ incentiveFee }}</view>
				</view>
			</view>
		</view>
		<view>
			<view class="setion-title">
				<image src="../static/order/money.png" mode="widthFix"></image>
				<text>总金额</text>
			</view>
			<view class="section-content">
				<view class="content-container">
					<view class="left">
						<view class="content big">
							【汇总合计】
							<text class="red">¥ {{ total }} 元</text>
						</view>
						<view class="content big">
							【代缴个税】
							<text class="red">¥ {{ taxFee }} 元</text>
						</view>
						<view class="content big">
							【实际收入】
							<text class="red">¥ {{ driverIncome }} 元</text>
						</view>
					</view>
					<image :src="img" mode="widthFix" class="img"></image>
				</view>
			</view>
			<button class="btn" @tap="sendOrderBill">发送账单</button>
		</view>
		<u-top-tips ref="uTips"></u-top-tips>
	</view>
</template>

<script>
export default {
	data() {
		return {
			orderId: null,
			customerId: null,
			favourFee: '暂无',
			incentiveFee: '暂无',
			realMileage: '--',
			mileageFee: '暂无',
			baseMileagePrice: '',
			baseMileage: '',
			exceedMileagePrice: '',

			waitingFee: '暂无',
			baseMinute: '',
			waitingMinute: '--',
			exceedMinutePrice: '',
			returnFee: '暂无',
			baseReturnMileage: '',
			exceedReturnPrice: '',
			returnMileage: '--',
			parkingFee: '暂无',
			tollFee: '暂无',
			otherFree: '暂无',
			total: '--',
			taxFee: '--',
			driverIncome: '--'
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
@import url('order_bill.less');
</style>
