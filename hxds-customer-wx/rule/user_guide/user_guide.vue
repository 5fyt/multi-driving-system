<template>
	<view>
		<u-cell-group :border="false">
			<u-cell-item icon="rmb-circle-fill" :icon-style="icon" title="计费规则" :border-top="false" @click="this.toPage('../bill_rule/bill_rule')" />
			<u-cell-item icon="account-fill" :icon-style="icon" title="用户规则" @click="this.toPage('../user_rule/user_rule')" />
			<u-cell-item icon="error-circle-fill" :icon-style="icon" title="取消规则" @click="this.toPage('../cancel_rule/cancel_rule')" />
		</u-cell-group>
		<u-top-tips ref="uTips"></u-top-tips>
	</view>
</template>

<script>
export default {
	data() {
		return {
			icon: {
				color: '#a0a0a0',
				'margin-top': '-1rpx'
			}
		};
	},
	methods: {},
	onShow: function() {
		
	},
	onHide: function() {
		
	}
};
</script>

<style lang="less">
@import url('user_guide.less');
</style>
