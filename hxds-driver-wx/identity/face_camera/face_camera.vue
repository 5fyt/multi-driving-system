<template>
	<view>
		<view class="face-container">
			<camera device-position="front" flash="off" class="camera" @error="error" v-if="showCamera">
				<cover-image src="../static/face_camera/bg.png" class="bg"></cover-image>
			</camera>
			<view class="image-container" v-if="showImage">
				<image mode="widthFix" class="photo" :src="photoPath"></image>
				<view class="cover"></view>
			</view>
		</view>
		<view class="desc">
			<block v-if="mode == 'verificate'">
				<image src="../static/face_camera/tips.png" mode="widthFix" class="tips"></image>
				<text>请把面部放在圆圈内</text>
				<text>拍摄脸部来确认身份</text>
			</block>
			<block v-if="mode == 'create'">
				<image src="../static/face_camera/face.png" mode="widthFix" class="face"></image>
				<text>请把完整面部放在圆圈内</text>
				<text>拍摄脸部来保存身份识别数据</text>
			</block>
		</view>
		<button class="btn" @tap="confirmHandle">{{ mode == 'create' ? '录入面部信息' : '身份核实' }}</button>
	</view>
</template>

<script>
let dayjs = require('dayjs');
export default {
	data() {
		return {
			mode: 'verificate',
			photoPath: '',
			showCamera: true,
			showImage: false,
			// audio: null
		};
	},
	methods: {
    /**
     *  调用摄像头上下文，保存摄像头成功回调函数的临时照片；需要将拍照图片转成base64的格式
     *    
    */
		confirmHandle(){
      let that = this
      let ctx=uni.createCameraContext()
      ctx.takePhoto({
        quality:'high',
        success:function(res){
          that.photoPath = res.tempImagePath
          that.showCamera = false
          that.showImage = true
        }
      })
      uni.getFileSystemManager().readFile({
        filePath:that.photoPath,
        encoding:'base64',
        success:function(res){
          let base64='data:image/png;base64,' + res.data
          let url
          if(that.mode==='create'){
             url=that.url.createDriverFaceModal
          }else{
            
          }
          that.ajax(url,'POST',{image:base64},function(res){
            const  {message}=res.data
            console.log('message',message)
            if(that.mode==='create'){
              if(message!=='操作成功'){
                uni.showToast({
                  title:message,
                  icon:'error'
                })
                setTimeout(function(){
                  that.showCamera=true
                  that.showImage=false
                },2000)
              }else{
                uni.showToast({
                  title:'录入面部成功',
                  icon:'success'
                })
                setTimeout(function(){
                  uni.switchTab({
                    url:'../../pages/workbench/workbench?mode=create'
                  })
                },2000)
              }
            }
          })
       }
      })
    }
	},
	onLoad: function(options) {
		let that = this 
    that.mode = options.mode
    let audio=uni.createInnerAudioContext()
    that.audio=audio
    audio.src='/static/voice/voice_5.mp3'
    audio.play()
	},
	onHide:function(){
		if(that.audio!==null){
      this.audio.stop()
    }
	}
};
</script>

<style lang="less">
@import url('face_camera.less');
</style>
