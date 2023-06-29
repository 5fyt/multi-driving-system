<template>
	<view>
		<view class="message-container" v-for="one in list" v-if="list.length > 0" @tap="viewMessageHandle(one.id, one.readFlag, one.refId)">
			<view class="top">
				<view class="title">
					<image src="../../static/message_list/email-icon-1.png" mode="widthFix" v-if="!one.readFlag"></image>
					<image src="../../static/message_list/email-icon-2.png" mode="widthFix" v-if="one.readFlag"></image>
					<text>{{ one.senderName }}</text>
				</view>
				<view class="date">{{ one.sendTime }}</view>
			</view>
			<view class="content">{{ one.msg }}</view>
			<view class="bottom">
				<text>查看详情</text>
				<u-icon name="arrow-right" color="#aaa" size="28"></u-icon>
			</view>
		</view>
		<view class="empty" v-if="list.length == 0"><u-empty text="消息列表为空" mode="list"></u-empty></view>
	</view>
</template>

<script>
export default {
	data() {
		return {
			page: 1,
			length: 50,
			list: [],
			isLastPage: false
		};
	},
	methods: {
		
	},
	
	onShow: function() {
		
	},
	onReachBottom: function() {
		
	}
};
</script>

<style lang="less">
@import url('message_list.less');
</style>
