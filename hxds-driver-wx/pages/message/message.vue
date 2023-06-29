<template>
	<view>
		<view class="message">
			<view class="header">
				<view class="desc">{{ sendTime }}</view>
				<view class="opt" @tap="deleteMsg()">删除</view>
			</view>
			<view class="content">
				<view class="sender">
					<image :src="senderPhoto" mode="widthFix" class="photo" />
					<text>{{ senderName }}</text>
				</view>
				<view class="msg">{{ msg }}</view>
			</view>
		</view>
	</view>
</template>

<script>
export default {
	data() {
		return {
			id: null,
			readFlag: null,
			refId: null,
			senderName: '',
			sendTime: '',
			senderPhoto: '',
			msg: ''
		};
	},
	onShow: function() {
		
	},
	onLoad: function(options) {
		
	},
	methods: {
		
	}
};
</script>

<style lang="less">
@import url('message.less');
</style>
