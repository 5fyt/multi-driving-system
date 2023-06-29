<template>
	<view>
		<view class="review-container" v-show="reviewAuth">
			<image src="../../static/workbench/review.png" mode="widthFix"></image>
			<view class="title">您的实名资料正在审核中</view>
			<view class="desc">这期间您将无法接单，请等待资料审核通过</view>
		</view>
		<view v-show="!reviewAuth">
			<view class="top-container">
				<view class="notice">今日数据：凌晨00:00 ~ 本日23:59</view>
				<view class="summary-container">
					<view class="item">
						<view class="title">代驾时长</view>
						<view class="value">
							<text class="number">{{ hour }}</text>
							<text class="unit">时</text>
						</view>
					</view>
					<view class="item">
						<view class="title">今日收入</view>
						<view class="value">
							<text class="number">{{ income }}</text>
							<text class="unit">元</text>
						</view>
					</view>
					<view class="item">
						<view class="title">今日成单</view>
						<view class="value">
							<text class="number">{{ orders }}</text>
							<text class="unit">单</text>
						</view>
					</view>
				</view>
				<view class="service-container">
					<view class="item" :style="service.locationStyle">
						<image :src="service.locationIcon" mode="widthFix" class="icon icon-1"></image>
						{{ service.locationText }}
					</view>
					<view class="item" :style="service.messageStyle">
						<image :src="service.messageIcon" mode="widthFix" class="icon icon-2"></image>
						{{ service.messageText }}
					</view>
					<view class="item" :style="service.listenStyle" @tap="listenHandle()">
						<image :src="service.listenIcon" mode="widthFix" class="icon icon-3"></image>
						{{ service.listenText }}
					</view>
					<view class="item" :style="service.settingStyle" @tap="settingHandle()">
						<image :src="service.settingIcon" mode="widthFix" class="icon icon-4"></image>
						{{ service.settingText }}
					</view>
				</view>
			</view>
			<view v-show="workStatus == '停止接单'">
				<map id="map" :longitude="longitude" :latitude="latitude" scale="15" :style="contentStyle" :enable-traffic="true" :show-location="true" :enable-poi="true">
					<cover-image class="location" src="../../static/workbench/location.png" @tap="returnLocationHandle()"></cover-image>
				</map>
			</view>
			<scroll-view scroll-y="true" :style="contentStyle" v-show="workStatus == '开始接单'">
				<view class="new-order">
					<view v-if="newOrder == null">
						<image src="../../static/workbench/no-order.png" mode="widthFix" class="no-order-img"></image>
						<view class="no-order-title">目前暂无订单</view>
					</view>
					<view v-if="newOrder != null">
						<view class="line-1">
							<view>
								<view class="sub-time">距离您</view>
								<view>
									<text class="number">{{ newOrder.distance }}</text>
									<text class="unit">公里</text>
								</view>
							</view>
							<view>
								<view class="sub-time">全程</view>
								<view>
									<text class="number">{{ newOrder.mileage }}</text>
									<text class="unit">公里</text>
								</view>
							</view>
							<view>
								<view class="tag">含加价{{ newOrder.favourFee }}元</view>
								<view>
									<text class="number">{{ newOrder.expectsFee }}</text>
									<text class="unit">元</text>
								</view>
							</view>
						</view>
						<view class="line-2">
							<view class="from">
								<text>{{ newOrder.from }}</text>
							</view>
							<view class="dashed-line"></view>
							<view class="to">
								<text>{{ newOrder.to }}</text>
							</view>
						</view>
						<button :class="!canAcceptOrder ? 'disabled-btn' : 'accept-btn'" @tap="acceptHandle">立即抢单</button>
					</view>
				</view>
				<view class="notice-container">
					<view class="title">【 抢单说明 】</view>
					<view class="desc">
						<text class="num">1.</text>
						当前地区可能存在多个代驾订单，但是司机端只能显示其中一条订单。这是为了保障代驾乘客订单的接单率，避免代驾司机只挑选优质订单。
					</view>
					<view class="desc">
						<text class="num">2.</text>
						某条订单如果10秒钟之内您没有抢单，系统则认定您主动放弃该订单，系统将为你推送新的订单。
					</view>
				</view>
			</scroll-view>
			<scroll-view scroll-y="true" :style="contentStyle" class="execute-order-container" v-show="['接客户', '到达代驾点', '开始代驾'].includes(workStatus)">
				<view class="call" @tap="callCustomerHandle"></view>
				<view class="customer-container">
					<u-avatar :src="executeOrder.photo" mode="square"></u-avatar>
					<view class="info">
						<view class="customer-name">代驾客户（{{ executeOrder.title }}）</view>
						<view class="customer-tel">Tel：{{ executeOrder.tel }}</view>
					</view>
				</view>
				<view class="address-container">
					<view class="from">
						<text>{{ executeOrder.startPlace }}</text>
					</view>
					<view class="dashed-line"></view>
					<view class="to">
						<text>{{ executeOrder.endPlace }}</text>
					</view>
				</view>
				<view class="order-container">
					<view>【 订单号码 】 {{ executeOrder.id }}</view>
					<view>【 下单时间 】 {{ executeOrder.createTime }}</view>
					<view>【 客户红包 】 {{ executeOrder.favourFee }}元</view>
					<view>【 代驾车型 】 {{ executeOrder.carType }}</view>
					<view>【 代驾车牌 】 {{ executeOrder.carPlate }}</view>
				</view>
				<view class="operate-container">
					<view class="item" @tap="cancelOrderHandle">
						<image src="../../static/workbench/cancel-icon.png" mode="widthFix" class="cancel-icon"></image>
						<text class="cancel-text">取消订单</text>
					</view>
					<view class="vline"></view>
					<view class="item" v-show="workStatus == '接客户'" @tap="arriveStartPlaceHandle">
						<image src="../../static/workbench/arrive-start-icon.png" mode="widthFix" class="arrive-start-icon"></image>
						<text class="arrive-start-text">到达代驾点</text>
					</view>
					<view class="item" v-show="workStatus == '到达代驾点'" @tap="startDrivingHandle">
						<image src="../../static/workbench/drive-start-icon.png" mode="widthFix" class="drive-start-icon"></image>
						<text class="drive-start-text">开始代驾</text>
					</view>
					<view class="item" v-show="workStatus == '开始代驾'" @tap="endDrivingHandle">
						<image src="../../static/workbench/drive-stop-icon.png" mode="widthFix" class="drive-stop-icon"></image>
						<text class="drive-stop-text">结束代驾</text>
					</view>
					<view class="vline"></view>
					<view class="item">
						<image src="../../static/workbench/alarm-icon.png" mode="widthFix" class="alarm-icon"></image>
						<text class="alarm-text">立即报警</text>
					</view>
				</view>
				<view class="other-container">
					<view class="item" @tap="showNavigationHandle">
						<image src="../../static/workbench/other-icon-1.png" mode="widthFix" class="location-icon"></image>
						<text class="location-text">定位导航</text>
					</view>
					<view class="item" @tap="showMoveHandle">
						<image src="../../static/workbench/other-icon-2.png" mode="widthFix" class="display-icon"></image>
						<text class="display-text">司乘同显</text>
					</view>
					<view class="item" @tap="showOrderHandle">
						<image src="../../static/workbench/other-icon-3.png" mode="widthFix" class="order-icon"></image>
						<text class="order-text">订单详情</text>
					</view>
					<view class="item" @tap="callServiceHandle">
						<image src="../../static/workbench/other-icon-4.png" mode="widthFix" class="service-icon"></image>
						<text class="service-text">客服热线</text>
					</view>
				</view>
			</scroll-view>
			<view class="bottom-container" v-show="['开始接单', '停止接单'].includes(workStatus)">
				<view class="btn" @tap="gohomeHandle()">
					<image src="../../static/workbench/btn-icon-1.png" mode="widthFix" class="btn-icon-1"></image>
					<text>回家</text>
				</view>
				<view class="btn primary">
					<text v-show="workStatus == '停止接单'" @tap="startWorkHandle()">开始接单</text>
					<text v-show="workStatus == '开始接单'" @tap="stopWorkHandle()">停止接单</text>
				</view>
				<view class="btn" @tap="rechargeHandle()">
					<image src="../../static/workbench/btn-icon-2.png" mode="widthFix" class="btn-icon-2"></image>
					<text>充值</text>
				</view>
			</view>
		</view>
		<u-top-tips ref="uTips"></u-top-tips>
		<u-toast ref="uToast" />
		<u-modal
			v-model="model.cancelConfirm.show"
			:title="model.cancelConfirm.title"
			:content="model.cancelConfirm.content"
			:show-confirm-button="true"
			:show-cancel-button="true"
			@confirm="confirmCancelOrder"
		></u-modal>
		<u-modal
			v-model="model.cancelSuccess.show"
			:title="model.cancelSuccess.title"
			:content="model.cancelSuccess.content"
			:show-confirm-button="true"
			:show-cancel-button="false"
		></u-modal>
	</view>
</template>

<script>
let plugin = requirePlugin('WechatSI');
let dayjs = require('dayjs');
export default {
	data() {
		return {
			reviewAuth: uni.getStorageSync('realAuth') == 3,
			hour: 0,
			income: 0,
			orders: 0,
			map: null,
			windowHeight: 0,
			contentStyle: '',
			latitude: 0,
			longitude: 0,
			service: {
				locationIcon: '../../static/workbench/service-icon-1.png',
				locationText: '定位正常',
				locationStyle: '',
				messageIcon: '../../static/workbench/service-icon-2.png',
				messageText: '推送正常',
				messageStyle: '',
				listenIcon: '../../static/workbench/service-icon-3.png',
				listenText: '收听订单',
				listenStyle: '',
				settingIcon: '../../static/workbench/service-icon-4.png',
				settingText: '接单设置',
				settingStyle: ''
			},
			settings: {
				listenService: true,
				autoAccept: false
			},
			workStatus: uni.getStorageSync('workStatus'),
			newOrder: null,
			newOrderList: [],
			executeOrder: {
				id: '',
				photo: '',
				title: '',
				tel: '',
				customerId: '',
				startPlace: '',
				startPlaceLocation: {},
				endPlace: '',
				endPlaceLocation: {},
				favourFee: '',
				carPlate: '',
				carType: '',
				createTime: ''
			},
			reciveNewOrderTimer: null,
			playFlag: false,
			audio: null,
			canAcceptOrder: false,
			accepting: false,
			showModel: false,
			modelTitle: '',
			modelContent: '',
			model: {
				cancelConfirm: {
					show: false,
					title: '提示消息',
					content: '如果不满足订单免费取消规则，只能有偿取消订单，并且可能被禁止接单一段时间，您确认要取消当前订单？'
				},
				cancelSuccess: {
					show: false,
					title: '提示消息',
					content: ''
				}
			},
			recordNum: 0,
			recordManager: null,
			stopRecord: false
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
@import url('workbench.less');
</style>
