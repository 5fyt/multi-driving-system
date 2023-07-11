<script>
export default {
	onLaunch: function() {
     let gps=[]
     //手机保持常量，避免手机休眠
     wx.setKeepScreenOn({
       keepScreenOn:true
     })
     wx.startLocationUpdate({
       success:function(res){
         console.log('gps定位开启')
       },
       fail:function(res){
         console.log('gps定位开启失败')
       }
     })
     wx.onLocationChange(function(res){
    
         const { latitude,longitude,speed} =res
         let location={
           latitude,
           longitude
         }
       
         let workStatus=uni.getStorageSync('workStatus')
         workStatus='开始接单'
         // if(workStatus==='开始接单'){
         //   //因为要判断司机每隔10秒中就要上传一次自己的地址，否则就理解成下线了
         //   // let newDate=new Date()
         //   // if(newDate.getSeconds()>10){
         //   //   return
         //   // }

         //   let baseUrl = "http://192.168.211.219:9000"
         //   let settings=uni.setStorageSync('settings')
           
         //   //暂时写死,应该到时候从后端拿数据
         //   settings={
         //     orientation:'',
         //     orderDistance:0,
         //     rangeDistance:uni.getStorageSync('rangeDistance')
         //   }
           
         //   uni.request({
         //     url:`${baseUrl}/driver/update-location`,
         //     method:'POST',
         //     header:{
         //       token:uni.getStorageSync('token')
         //     },
         //     data:{
         //       longitude:longitude,
         //       latitude:latitude,
         //       orientation:"",
         //       orderDistance:settings.orderDistance,
         //       rangeDistance:settings.rangeDistance
         //     },
         //     success:(res)=>{
         //       const { token ,code} = res.data
         //       if(res.statusCode===200&&res.data.code===200){
         //         if(res.data.hasOwnProperty('token')){
         //           uni.setStorageSync('token',token)
         //           console.log('定位成功')
         //         }
         //       }else if(res.statusCode===401){
         //         uni.redirectTo({
         //           url:'pages/login/login'
         //         })
         //       }else{
         //         console.log('定位失败')
         //       }
         //     },
         //     fail:()=>{
         //       console.log('gps定位失败')
         //     }
         //   })
         // } 
         uni.$emit('updateLocation',location)
         console.log(location)
       }
     )
	},
	onShow: function() {
		console.log('App Show');
	},
	onHide: function() {
		console.log('App Hide');
	}
};
</script>

<style lang="scss">
@import 'uview-ui/index.scss';
</style>
