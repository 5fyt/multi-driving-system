<template>
	<view>
		<view class="tab-container">
			<u-tabs-swiper
				ref="uTabs"
				:list="list"
				:current="tabCurrent"
				@change="tabsChange"
				:is-scroll="false"
				swiperWidth="750"
			></u-tabs-swiper>
		</view>
		<swiper :current="swiperCurrent" @transition="transition" @animationfinish="animationfinish" :style="swipperHeight">
			<swiper-item class="swiper-item">
				<scroll-view :style="swipperHeight" @scrolltolower="onreachBottom" scroll-y>
					<view class="fine-container" v-for="one in fineList">
						<view class="top">
							<text class="fine-id">罚款单号：{{ one.id }}</text>
							<text class="fine-status red" v-if="one.status == 1">【 未交款 】</text>
							<text class="fine-status green" v-if="one.status == 2">【 已交款 】</text>
						</view>
						<view class="summary">
							<view class="letter orange" v-if="one.status == 1">罚</view>
							<view class="letter gray" v-if="one.status == 2">罚</view>
							<view class="content">
								<view class="remark">{{ one.remark }}</view>
								<view class="date">{{ one.createTime }}</view>
							</view>
						</view>
						<view class="bottom">
							<view class="amount">
								<image src="../static/fine_list/money-icon.png" mode="widthFix" />
								罚金面额：
								<text>{{ one.amount }}元</text>
							</view>
							<button class="pay-btn" v-if="one.status == 1" @tap="payHandle(one.id, one.amount)">立即支付</button>
						</view>
					</view>
				</scroll-view>
			</swiper-item>
			<swiper-item class="swiper-item">
				<scroll-view :style="swipperHeight" @scrolltolower="onreachBottom" scroll-y>
					<view class="fine-container" v-for="one in fineList">
						<view class="top">
							<text class="fine-id">罚款单号：{{ one.id }}</text>
							<text class="fine-status red">【 未交款 】</text>
						</view>
						<view class="summary">
							<view class="letter orange">罚</view>
							<view class="content">
								<view class="remark">{{ one.remark }}</view>
								<view class="date">{{ one.createTime }}</view>
							</view>
						</view>
						<view class="bottom">
							<view class="amount">
								<image src="../static/fine_list/money-icon.png" mode="widthFix" />
								罚金面额：
								<text>{{ one.amount }}元</text>
							</view>
							<button class="pay-btn" @tap="payHandle(one.id, one.amount)">立即支付</button>
						</view>
					</view>
				</scroll-view>
			</swiper-item>
			<swiper-item class="swiper-item">
				<scroll-view :style="swipperHeight" @scrolltolower="onreachBottom" scroll-y>
					<view class="fine-container" v-for="one in fineList">
						<view class="top">
							<text class="fine-id">罚款单号：{{ one.id }}</text>
							<text class="fine-status green">【 已交款 】</text>
						</view>
						<view class="summary">
							<view class="letter gray">罚</view>
							<view class="content">
								<view class="remark">{{ one.remark }}</view>
								<view class="date">{{ one.createTime }}</view>
							</view>
						</view>
						<view class="bottom">
							<view class="amount">
								<image src="../static/fine_list/money-icon.png" mode="widthFix" />
								罚金面额：
								<text>{{ one.amount }}元</text>
							</view>
						</view>
					</view>
				</scroll-view>
			</swiper-item>
		</swiper>
		<u-keyboard
			default=""
			ref="uKeyboard"
			mode="number"
			:mask="true"
			:mask-close-able="false"
			:dot-enabled="false"
			v-model="showKeyboard"
			:safe-area-inset-bottom="true"
			:tooltip="false"
			@change="keyboardChangeHandle"
			@backspace="backspaceHandle"
		>
			<view>
				<view class="u-text-center u-padding-30 money">
					<text>¥ {{ amount }}</text>
					<view class="close" data-flag="false" @tap="showKeyboardHandle(false)">
						<u-icon name="close" color="#333333" size="32"></u-icon>
					</view>
				</view>
				<view class="u-flex u-row-center">
					<u-message-input
						mode="box"
						:maxlength="6"
						:dot-fill="true"
						v-model="password"
						:disabled-keyboard="true"
						@finish="finish"
					></u-message-input>
				</view>
				<view class="u-text-center u-padding-top-10 u-padding-bottom-20 tips">输入华夏代驾钱包支付密码</view>
			</view>
		</u-keyboard>
		<u-top-tips ref="uTips"></u-top-tips>
	</view>
</template>

<script>
export default {
	data() {
		return {
			swipperHeight: '',
			list: [
				{
					name: '全部罚款'
				},
				{
					name: '未缴纳'
				},
				{
					name: '已缴纳'
				}
			],
			tabCurrent: 0,
			swiperCurrent: 0,
			fineList: [],
			status: null,
			page: 1,
			length: 20,
			end: false,
			showKeyboard: false,
			amount: '',
			password: '',
			fineId: null
		};
	},
	methods: {
		// tabs通知swiper切换
		tabsChange(index) {
			this.swiperCurrent = index;
			this.page = 1;
			this.fineList = [];
			if (index == 0) {
				this.status = null;
			} else if (index == 1) {
				this.status = 1;
			} else if (index == 2) {
				this.status = 2;
			}
			this.end = false;
			this.loadFineList(this);
		},
		// swiper-item左右移动，通知tabs的滑块跟随移动
		transition(e) {
			let dx = e.detail.dx;
			this.$refs.uTabs.setDx(dx);
		},
		// 由于swiper的内部机制问题，快速切换swiper不会触发dx的连续变化，需要在结束时重置状态
		// swiper滑动结束，分别设置tabs和swiper的状态
		animationfinish(e) {
			let tabCurrent = e.detail.current;
			this.$refs.uTabs.setFinishCurrent(tabCurrent);
			this.swiperCurrent = tabCurrent;
			this.tabCurrent = tabCurrent;
		},
		// scroll-view到底部加载更多
		onreachBottom: function() {
			this.page = this.page + 1;
			this.loadFineList(this);
		},
		loadFineList: function(ref) {
			if (ref.end) {
				return;
			}
			let data = {
				status: ref.status,
				page: ref.page,
				length: ref.length
			};

			ref.ajax(ref.url.searchFineByPage, 'POST', data, function(resp) {
				let result = resp.data.result;
				// console.log(result.list)
				let list = result.list;
				if (list.length == 0) {
					ref.end = true;
					uni.showToast({
						icon: 'none',
						title: '已经到底了'
					});
				} else {
					ref.fineList = ref.fineList.concat(result.list);
				}
			});
		},
		keyboardChangeHandle: function(val) {
			let that = this;

			if (that.password.length < 6) {
				that.password += val;
			}
			if (that.password.length >= 6) {
				//支付罚款
				let data = {
					password: that.password,
					amount: that.amount,
					fineId: that.fineId
				};
				that.ajax(that.url.payFine, 'POST', data, function(resp) {
					let result = resp.data.result;
					if (result) {
						uni.showToast({
							title: '支付成功'
						});
						setTimeout(function() {
							that.page = 1;
							that.end = false;
							that.showKeyboard = false;
							that.amount = '';
							that.password = '';
							that.fineId = null;
							that.fineList.length = 0;
							that.loadFineList(that);
						}, 2000);
					} else {
						uni.showToast({
							icon: 'none',
							title: '支付失败'
						});
					}
				});
			}
		},
		backspaceHandle: function(e) {
			if (this.password.length > 0) {
				this.password = this.password.substring(0, this.password.length - 1);
			}
		},
		showKeyboardHandle: function(val) {
			this.showKeyboard = val;
			this.password = '';
		},
		payHandle: function(fineId, amount) {
			let that = this;
			//先检测司机是否能支付罚款
			that.ajax(that.url.canPayFine, 'POST', { fineId: fineId }, function(resp) {
				let result = resp.data.result;
				console.log(result);
				if (!result.hasPassword) {
					uni.showModal({
						title: '消息通知',
						content: '您还没有设置支付密码，暂时不能付款',
						cancelText: '我知道了',
						confirmText: '马上设置',
						success: function(resp) {
							if (resp.confirm) {
								uni.navigateTo({
									url: '../account/account'
								});
							}
						}
					});
				} else if (!result.canPay) {
					uni.showModal({
						title: '消息通知',
						content: '您的钱包余额不足支付本次罚款',
						cancelText: '我知道了',
						confirmText: '立即充值',
						success: function(resp) {
							if (resp.confirm) {
								uni.navigateTo({
									url: '../../wallet/recharge/recharge'
								});
							}
						}
					});
				} else {
					that.amount = amount;
					that.showKeyboard = true;
					that.fineId = fineId;
				}
			});
		}
	},
	onLoad: function() {
		let windowHeight = uni.getSystemInfoSync().windowHeight;
		let swipperHeight = 'height:' + (windowHeight - 72) + 'px';
		this.swipperHeight = swipperHeight;
		this.status = null;
		this.page = 1;
		this.loadFineList(this);
	},
	onShow: function() {
		let that = this;
		//定时刷新消息
		uni.$on('message', function() {
			that.refreshMessage(that);
		});
	
		uni.$on('showMessageTip', function(lastRows) {
			that.$refs.uTips.show({
				title: `最新收到${lastRows}条消息通知`,
				type: 'success',
				duration: '2300'
			});
		});
	},
	onHide: function() {
		uni.$off('message');
		uni.$off('showMessageTip');
	}
};
</script>

<style lang="less">
@import url('fine_list.less');
</style>
