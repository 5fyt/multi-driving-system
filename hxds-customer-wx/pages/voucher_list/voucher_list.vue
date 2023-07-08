<template>
	<view>
		<view class="tabs">
			<u-tabs-swiper
				ref="uTabs"
				:list="list"
				:current="current"
				@change="tabsChange"
				:is-scroll="false"
				:offset="[10, 50]"
				swiperWidth="750"
			/>
		</view>

		<scroll-view scroll-y :style="swipperStyle" @scrolltolower="onreachBottom" v-if="current==0">
			<view class="voucher" v-for="one in voucherList">
				<view class="left green">
					<view class="row">
						<text class="amount">{{ one.discount }}</text>
					</view>
					<view class="row">
						<text class="desc" v-if="one.withAmount > 0">满{{ one.withAmount }}元可用</text>
						<text class="desc" v-if="one.withAmount == 0">无最低消费</text>
					</view>
				</view>
				<view class="right">
					<view class="row">
						<text class="desc">{{ one.remark }}</text>
					</view>
					<view class="row">
						<text class="desc" v-if="one.timeType == 1">领券后{{ one.days }}天内有效</text>
						<text class="desc" v-if="one.timeType == 2">{{ one.startTime }} ~ {{ one.endTime }}</text>
						<text class="desc" v-if="one.timeType == null">{{ one.createTime }} ~ 2099-01-01</text>
					</view>
				</view>
			</view>
		</scroll-view>

		<scroll-view scroll-y :style="swipperStyle" @scrolltolower="onreachBottom" v-if="current==1">
			<view class="voucher" v-for="one in voucherList">
				<view class="left orange">
					<view class="row">
						<text class="amount">{{ one.discount }}</text>
					</view>
					<view class="row">
						<text class="desc" v-if="one.withAmount > 0">满{{ one.withAmount }}元可用</text>
						<text class="desc" v-if="one.withAmount == 0">无最低消费</text>
					</view>
				</view>
				<view class="right">
					<view class="row">
						<text class="desc">{{ one.remark }}</text>
					</view>
					<view class="row">
						<text class="desc" v-if="one.timeType == 1">领券后{{ one.days }}天内有效</text>
						<text class="desc" v-if="one.timeType == 2">{{ one.startTime }} ~ {{ one.endTime }}</text>
						<text class="desc" v-if="one.timeType == null">{{ one.createTime }} ~ 2099-01-01</text>
					</view>
				</view>
			</view>
		</scroll-view>

		<scroll-view scroll-y :style="swipperStyle" @scrolltolower="onreachBottom" v-if="current==2">
			<view class="voucher" v-for="one in voucherList" @tap="takeVoucher(one.id, one.uuid)">
				<view class="left red">
					<view class="row">
						<text class="amount">{{ one.discount }}</text>
					</view>
					<view class="row">
						<text class="desc" v-if="one.withAmount > 0">满{{ one.withAmount }}元可用</text>
						<text class="desc" v-if="one.withAmount == 0">无最低消费</text>
					</view>
				</view>
				<view class="right">
					<view class="row">
						<text class="desc">{{ one.remark }}</text>
					</view>
					<view class="row">
						<text class="desc" v-if="one.timeType == 1">领券后{{ one.days }}天内有效</text>
						<text class="desc" v-if="one.timeType == 2">{{ one.startTime }} ~ {{ one.endTime }}</text>
						<text class="desc" v-if="one.timeType == null">{{ one.createTime }} ~ 2099-01-01</text>
					</view>
				</view>
			</view>
		</scroll-view>
	</view>
</template>

<script>
export default {
	data() {
		return {
			list: [
				{
					name: '未使用',
					count: 0
				},
				{
					name: '已使用',
					count: 0
				},
				{
					name: '待领取',
					count: 0
				}
			],
			current: 0, // tabs组件的current值，表示当前活动的tab选项
			swiperCurrent: 0, // swiper组件的current值，表示当前那个swiper-item是活动的
			swipperStyle: '',
			page: 1,
			length: 10,
			voucherList: [],
			isLastPage: false
		};
	},
	methods: {
		
	},
	onLoad: function() {

	}
};
</script>

<style lang="less">
@import url('voucher_list.less');
</style>
