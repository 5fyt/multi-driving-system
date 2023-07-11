<template>
    <view class="page">
        <view v-if="list.length > 0" v-for="one in list" class="row" @tap="choseOneHandle(one.id, one.carPlate,one.carType)" @longpress="removeHandle(one.id)">
            <view>
                <u-icon name="info-circle-fill" color="#2979ff" size="35" class="icon"></u-icon>
                <text class="car-type">{{ one.carType }}</text>
            </view>
            <text class="car-plate">{{ one.carPlate }}</text>
        </view>
        <image v-if="list.length == 0" src="../../static/car_list/none.jpg" mode="widthFix" class="none"></image>
        <button class="btn" @tap="addHandle">添加车辆</button>
        <u-toast ref="uToast" />
    </view>
</template>

<script>
export default {
    data() {
        return {
            list: []
        };
    },
    methods: {
      choseOneHandle(id,carPlate,carType){
        uni.navigateTo({
          url:`../create_order/create_order?showCar=true&carId=${id}&carPlate=${carPlate}&carType=${carType}`
        })
      },
      //展示车辆数据
        showCarInfo(that){
          let list=[]
          that.ajax(that.url.searchCustomerCarList,'GET',{},function(res){
            const {cars}=res.data
            console.log('res',cars)
            cars.forEach(item=>{ 
              list.push({
                id:item.id,
                carType:item.carType,
                carPlate:item.carPlate
              })
            })
          })
          that.list=list
        },
        //添加车辆
        addHandle(){
          uni.navigateTo({
            url:'../add_car/add_car'
          })
        },
        //删除车辆
        removeHandle(id){
          let that = this
          uni.vibrateShort({})
          console.log('id',id)
          uni.showModal({
            title:'提示信息',
            content:'是否删除该车辆',
            success:function(res){
              if(res.confirm){
                let data={
                  id
                }
                that.ajax(that.url.deleteCustomerCarById,'POST',data,function(res){
                  uni.showToast({
                    title:'删除成功',
                    icon:'success'
                  })
                  that.showCarInfo(that)
                })
              }
            }
          })
        }
    },
    onLoad: function() {
      let that = this 
      that.showCarInfo(that)
    }
};
</script>

<style lang="less">
@import url('car_list.less');
</style>
