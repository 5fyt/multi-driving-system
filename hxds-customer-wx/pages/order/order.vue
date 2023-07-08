<template>
	<view>
		<view class="customer-container">
			<u-avatar :src="photo" mode="square"></u-avatar>
			<view class="info">
				<view class="customer-name">{{ name }}（代驾司机）</view>
				<view class="customer-tel">Tel：{{ tel }}</view>
			</view>
		</view>
		<view class="address-container">
			<view class="from">
				<text>{{ startPlace }}</text>
			</view>
			<view class="dashed-line"></view>
			<view class="to">
				<text>{{ endPlace }}</text>
			</view>
		</view>
		<view class="order-container">
			<view>【 订单号码 】 {{ orderId }}</view>
			<view>【 下单时间 】 {{ createTime }}</view>
			<view>【 客户红包 】 {{ favourFee }}元</view>
			<view>【 系统奖励 】 {{ incentiveFee }}元</view>
			<view>【 代驾车型 】 {{ carType }}</view>
			<view>【 代驾车牌 】 {{ carPlate }}</view>
		</view>
		<view>
			<view class="setion-title">
				<image src="../../static/order/money.png" mode="widthFix"></image>
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
						<text class="item-desc">免费{{ base_minute }}分钟，超出部分每分钟{{ exceedMinutePrice }}元</text>
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
				<image src="../../static/order/money.png" mode="widthFix"></image>
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
					<view class="right">{{ otherFee }}</view>
				</view>
			</view>
		</view>
		<view>
			<view class="setion-title">
				<image src="../../static/order/money.png" mode="widthFix"></image>
				<text>总金额</text>
			</view>
			<view class="section-content">
				<view class="content-container">
					<view class="left">
						<view class="content">
							【汇总合计】
							<text>¥ {{ total }} 元</text>
						</view>
						<view class="content">
							【减免金额】
							<text>¥ {{ voucherFee }} 元</text>
						</view>
						<view class="content">
							【实付金额】
							<text class="red">¥ {{ realPay }} 元</text>
						</view>
					</view>
					<image :src="img" mode="widthFix" class="img"></image>
				</view>
			</view>
		</view>

		<view class="operate-container" v-show="status == 6">
			<button class="btn" @tap="payHandle">立即付款</button>
		</view>
		<view v-if="status >= 7">
			<view class="setion-title">
				<image src="../../static/order/rate.png" mode="widthFix"></image>
				<text>客户评价</text>
			</view>
			<view class="section-content">
				<view class="remark-container">
					<view class="remark-rate">
						<view class="photo">
							<u-avatar :src="photo" size="60" />
						</view>
						<view class="rate">
							<u-rate :count="customerComment.count" 
									v-model="customerComment.value" disabled="true" 
									active-color="#FFBB2A" size="40" />
						</view>
					</view>
					<view class="remark">{{ customerComment.remark }}</view>
				</view>
			</view>
		</view>

		<view class="contact-container">
			<view class="contact">
				<text class="label">客服电话：</text>
				<text class="tel">0411-87143331</text>
			</view>
			<view class="contact">
				<text class="label">服务监督电话：</text>
				<text class="tel">0411-87143331</text>
			</view>
		</view>
		<u-top-tips ref="uTips"></u-top-tips>
		<u-popup v-model="comment.showComment" mode="center" 
				border-radius="14" width="550rpx" height="580rpx">
			<view class="comment-title">华夏代驾服务您满意吗</view>
			<view class="comment-desc">请给司机的服务一点评价吧~</view>
			<view class="comment-rate">
				<u-rate :count="comment.count" v-model="comment.value" 
						active-color="#FFBB2A" size="40">
				</u-rate>
			</view>
			<u-input v-model="comment.remark" type="textarea" :border="false"
					:clearable="false" placeholder="说说哪里好,其他顾客想知道~" 
					:custom-style="comment.remarkStyle" />
			<u-button type="success" :custom-style="comment.btnStyle" 
						@click="insertComment">确定</u-button>
		</u-popup>
	</view>
</template>

<script>
export default {
	data() {
		return {
			orderId: null,
			customerId: null,
			driverId: null,
			photo: null,
			name: null,
			tel: null,
			startPlace: null,
			endPlace: null,
			createTime: null,
			favourFee: '暂无',
			incentiveFee: '暂无',
			carPlate: null,
			carType: null,
			status: null,
			realMileage: null,
			mileageFee: null,
			baseMileagePrice: '',
			baseMileage: '',
			exceedMileagePrice: '',
			waitingFee: '暂无',
			base_minute: '',
			waitingMinute: '--',
			exceedMinutePrice: '',
			returnFee: '暂无',
			baseReturnMileage: '',
			exceedReturnPrice: '',
			returnMileage: '--',
			parkingFee: '暂无',
			tollFee: '暂无',
			otherFee: '暂无',
			total: '--',
			voucherFee: '--',
			realPay: '--',
			img: '',
			comment: {
				showComment: false,
				count: 5,
				value: 0,
				remark: null,
				remarkStyle: {
					'background-color': '#f8f8f8',
					padding: '20rpx',
					margin: '0 40rpx'
				},
				btnStyle: {
					margin: '40rpx 40rpx 30rpx 40rpx'
				}
			},
			customerComment: {
				count: 5,
				rate: 0,
				remark: '[ 客户没有评价，系统默认为好评~ ]'
			}
		};
	},
	methods: {
		
	},
	onLoad: function(options) {
		
	}
};
</script>

<style lang="less">
@import url('order.less');
</style>
