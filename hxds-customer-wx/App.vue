<script>
export default {
    onLaunch: function() {
        //开启小程序进入前台时接收位置消息
        wx.startLocationUpdate({
          success:function(res){
            console.log('开启地位成功')
          },
          fail:function(res){
            console.log('开启定位失败')
          }
        })
        //实时地理位置变化事件的监听函数
        wx.onLocationChange(function(res){
          let {latitude,longitude } = res
          let location={latitude,longitude}
          uni.$emit('updateLocation',location)
        })
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
@import "uview-ui/index.scss";
</style>
