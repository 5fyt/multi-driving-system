<template>
	<view>
		<camera device-position="back" flash="off" class="camera" @error="error" v-if="showCamera"></camera>
		<image mode="widthFix" class="image" :src="photoPath" v-if="showImage"></image>
		<view class="operate-container">
			<button type="primary" class="btn" @tap="clickBtn">{{ btnText }}</button>
			<button type="warn" class="btn" @tap="afresh">重拍</button>
		</view>
		<view class="notice-container">
			<text class="notice">注意事项</text>
			<text class="desc">
				<block v-if="type!='driverCardBack'">拍照的时候，必须把证件拍摄清晰并完整，否则影响识别结果。持证拍照的时候不许戴帽子、墨镜和面纱等遮挡五官的饰物，只拍摄上半身即可。手持证件，五官与证件必须同时拍摄到照片中。</block>
				<block v-if="type=='driverCardBack'">拍照的时候，必须把驾驶证背面拍摄的清晰完整，否则影响认证结果。</block>
			</text>
		</view>
	</view>
</template>

<script>
export default {
	data() {
		return {
			type: null,
        	photoPath: '',
        	btnText: '拍照',
        	showCamera: true,
        	showImage: false
		};
	},
	onLoad:function(options){
		this.type = options.type;
	},
	methods: {
		 clickBtn(){
       let that = this 
       if(that.btnText==='拍照'){
        const ctx = uni.createCameraContext()
        ctx.takePhoto({
          quality:'high',
          success:function(res){
            that.photoPath = res.tempImagePath
            that.showCamera = false
            that.showImage = true
            that.btnText = '提交'
          }
        })
        //当按钮变成提交时，提交上传照片
       }else{
          const pages = getCurrentPages() //第一元素是第一个页面，最后一个是最后的一个页面
          const prevPage = pages[pages.length-2] //获取上一个页面
          prevPage.$vm.updatePhoto(that.type,that.photoPath) //调用filling.vue 页面中的上传函数
          //回退到filling页面
          uni.navigateBack({
            delta:1
          })
          
       }
     },
     afresh(){
       let that=this
       that.btnText='拍照'
       that.showCamera=true
       that.showImage=false
     }
	}
};
</script>

<style lang="less">
@import url('identity_camera.less');
</style>
