<template>
	<view class="page">
		<view class="card">
			<view class="total">
				<view>总资产（CNY）</view>
				<view class="recharge" @tap="toPage('../recharge/recharge')">
					<image src="../static/wallet/recharge.png" mode="widthFix" class="icon"></image>
					<text>充值</text>
				</view>
			</view>
			<view class="balance">
				￥
				<text>{{ balance }}</text>
			</view>
			<view class="item-list">
				<view class="item">
					<view class="title">
						<image src="../static/wallet/icon-1.png" mode="widthFix" class="icon" />
						本月收入
					</view>
					<view class="amount">￥{{ incomeTotalInMonth }}</view>
				</view>
				<view class="item">
					<view class="title">
						<image src="../static/wallet/icon-2.png" mode="widthFix" class="icon" />
						本月支出
					</view>
					<view class="amount">￥{{ paymentTotalInMonth }}</view>
				</view>
				<view class="item">
					<view class="title">
						<image src="../static/wallet/icon-3.png" mode="widthFix" class="icon" />
						今日入账
					</view>
					<view class="amount">￥{{ incomeTotalInDay }}</view>
				</view>
			</view>
		</view>
		<view class="record-container">
			<view class="title">收支明细</view>
			<view class="summary">
				<view class="month" @tap="showListHandle()">
					<text>{{ monthLabel }}</text>
					<image src="../static/wallet/icon-4.png" mode="widthFix" class="icon" />
				</view>
				<text class="total">合计：{{ incomeTotalInMonth - paymentTotalInMonth }}元</text>
			</view>
			<view class="record-list">
				<block v-if="recordInMonth.length > 0">
					<view class="record" v-for="one in recordInMonth">
						<view class="left">
							<view class="date-time">
								<view class="date">
									<image src="../static/wallet/icon-5.png" mode="widthFix" class="date-icon" />
									<text>{{ one.date }}</text>
								</view>
								<view class="time">
									<image src="../static/wallet/icon-6.png" mode="widthFix" class="time-icon" />
									<text>{{ one.time }}</text>
								</view>
							</view>
							<view class="content">
								<view class="type">钱包{{ one.name }}</view>
								<view class="desc">{{ one.remark }}</view>
							</view>
						</view>
						<view class="right">
							<text :class="one.name == '入账' ? 'red' : 'green'">{{ one.amount }}</text>
							<text class="unit">元</text>
						</view>
					</view>
				</block>
				<block v-if="recordInMonth.length == 0">
					<view class="empty">
						<image src="../static/wallet/no-data.png" mode="widthFix" class="no-data"></image>
						<view>没有相关数据</view>
					</view>
				</block>
			</view>
			<u-select v-model="showList" :list="list" @confirm="confirmMonthHandle"></u-select>
		</view>
		<u-top-tips ref="uTips"></u-top-tips>
	</view>
</template>

<script>
let dayjs = require('dayjs');
export default {
	data() {
		return {
			showList: false,
			list: [],
			id: null,
			balance: 0,
			incomeTotalInDay: 0,
			incomeTotalInMonth: 0,
			paymentTotalInMonth: 0,
			recordInMonth: [],
			monthLabel: '',
			monthValue: ''
		};
	},
	methods: {

	},
	onShow: function() {

	},
	onHide: function() {

	}
};
</script>

<style lang="less">
@import url('wallet.less');
</style>
