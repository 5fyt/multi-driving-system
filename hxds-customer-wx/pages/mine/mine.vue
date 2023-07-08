<template>
	<view>
		<view class="summary-container">
			<u-avatar :src="photo" size="large"></u-avatar>
			<view class="summary">
				<view class="name">{{ name }}</view>
				<view class="level">
					<u-icon name="integral-fill" color="#FFA600" size="35" class="icon"></u-icon>
					<text>${level}</text>
				</view>
			</view>
		</view>
		<u-cell-group>
			<u-cell-item icon="file-text-fill" title="订单"></u-cell-item>
			<u-cell-item icon="info-circle-fill" title="罚款" :value="fine"></u-cell-item>
			<u-cell-item icon="coupon-fill" title="代金券" :value="voucher" @click="this.toPage('../voucher_list/voucher_list')"></u-cell-item>
			<u-cell-item icon="server-fill" title="在线客服"></u-cell-item>
			<u-cell-item icon="trash-fill" :icon-style="icon" title="清理缓存" @click="clearHandle" />
			<u-cell-item icon="file-text-fill" :icon-style="icon" title="用户指南" @click="this.toPage('../../rule/user_guide/user_guide')" />
			<u-cell-item icon="setting-fill" title="设置"></u-cell-item>
		</u-cell-group>
	</view>
</template>

<script>
export default {
	data() {
		return {
			photo: '',
			name: '',
			level:'',
			fine:"0.00元",
			voucher:"0张"
		};
	},
	methods: {
	
	}
};
</script>

<style lang="less">
@import url('mine.less');
</style>
