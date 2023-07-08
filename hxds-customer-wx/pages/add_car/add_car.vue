<template>
    <view class="page">
        <u-cell-group :border="false">
            <u-cell-item icon="info-circle-fill" title="车型信息" :value="carType" @click="carTypeHandle"></u-cell-item>
            <u-cell-item icon="info-circle-fill" title="车牌号码" :value="carPlate" @click="carPlateHandle"></u-cell-item>
        </u-cell-group>
        <button class="btn" @tap="saveHandle">确定保存</button>
        <view class="desc">
            保存即代表您同意华夏代驾的
            <text>《隐私政策》</text>
        </view>
        <u-toast ref="uToast" />
    </view>
</template>

<script>
export default {
    data() {
        return {
            carType: '',
            carPlate: ''
        };
    },
    methods: {
        carTypeHandle(){
          let that = this
          uni.showModal({
            title:'添加车辆信息',
            editable:true,
            placeholderText:'例如劳斯莱斯',
            success:function(res){
              if(res.confirm){
                let carType=res.content
               if(that.checkValidCarType(carType,'车辆信息')){
                 that.carType=carType
               }
              }
            }
          })
        },
        carPlateHandle(){
          let that = this
          uni.showModal({
            title:'添加车牌号',
            editable:true,
            placeholderText:'赣F88888',
            success:function(res){
              if(res.confirm){
                let carPlate=res.content
              if(that.checkValidCarPlate(carPlate,'车牌号')){
                that.carPlate=carPlate
              }
              }
            }
          })
        },
        saveHandle(){
          let that =this 
          //校验车辆信息和车牌号是否为正确格式
          if(that.checkValidCarPlate(that.carPlate)&&that.checkValidCarType(that.carType)){
            let data={
              carPlate:that.carPlate,
              carType:that.carType
            }
            that.ajax(that.url.insertCustomerCar,'POST',data,function(res){
              uni.showToast({
                title:'添加成功',
                icon:'success',
                success:function(res){
                  uni.redirectTo({
                    url:'../car_list/car_list'
                  })
                }
              })
            })
          }
        }
    }
};
</script>

<style lang="less">
@import url('add_car.less');
</style>
