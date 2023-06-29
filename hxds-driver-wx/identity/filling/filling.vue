<template>
	<view class="page">
		<view class="notice">
			<u-icon name="info-circle-fill" color="#AC9146" size="30" top="3" />
			<text>准确填写个人信息，可享受每份代驾订单人身意外险</text>
		</view>
		<view class="credentials-container">
			<view class="credentials">
				<image :src="cardBackground[0]" class="bg"></image>
				<view class="cover">
					<image src="../static/filling/card.png" mode="widthFix" class="card"></image>
					<text class="desc">身份证正面</text>
					<ocr-navigator @onSuccess="scanIdcardFront" certificateType="idCard" :opposite="false">
						<button class="camera"></button>
					</ocr-navigator>
				</view>
			</view>
			<view class="credentials">
				<image :src="cardBackground[1]" class="bg"></image>
				<view class="cover">
					<image src="../static/filling/card.png" mode="widthFix" class="card"></image>
					<text class="desc">身份证背面</text>
					<ocr-navigator @onSuccess="scanIdcardBack" certificateType="idCard" :opposite="true">
						<button class="camera"></button>
					</ocr-navigator>
				</view>
			</view>
			<view class="credentials">
				<image :src="cardBackground[2]" class="bg"></image>
				<view class="cover">
					<image src="../static/filling/card.png" mode="widthFix" class="card"></image>
					<text class="desc">手持身份证</text>
					<button class="camera" @tap="takePhoto('idcardHolding')"></button>
				</view>
			</view>
			<view class="credentials">
				<image :src="cardBackground[3]" class="bg"></image>
				<view class="cover">
					<image src="../static/filling/card.png" mode="widthFix" class="card"></image>
					<text class="desc">驾驶证正面</text>
					<ocr-navigator @onSuccess="scanDrcardFront" certificateType="driverslicense">
						<button class="camera"></button>
					</ocr-navigator>
				</view>
			</view>
			<view class="credentials">
				<image :src="cardBackground[4]" class="bg"></image>
				<view class="cover">
					<image src="../static/filling/card.png" mode="widthFix" class="card"></image>
					<text class="desc">驾驶证背面</text>
					<button class="camera" @tap="takePhoto('drcardBack')"></button>
				</view>
			</view>
			<view class="credentials">
				<image :src="cardBackground[5]" class="bg"></image>
				<view class="cover">
					<image src="../static/filling/card.png" mode="widthFix" class="card"></image>
					<text class="desc">手持驾驶证</text>
					<button class="camera" @tap="takePhoto('drcardHolding')"></button>
				</view>
			</view>
		</view>

		<view class="title">个人信息</view>
		<view class="list">
			<u-cell-group border="false">
				<u-cell-item title="真实姓名" :value="idcard.name" :value-style="style" :arrow="false" />
				<u-cell-item title="性别" :value="idcard.sex" :value-style="style" :arrow="false" />
				<u-cell-item title="生日" :value="idcard.birthday" :value-style="style" :arrow="false" />
				<u-cell-item title="身份证号" :value="idcard.pid" :value-style="style" :arrow="false" />
				<u-cell-item title="身份证地址" :value="idcard.shortAddress" :value-style="style" @click="showAddressContent" />
				<u-cell-item title="身份证有效期" :value="idcard.expiration" :value-style="style" :arrow="false" />
			</u-cell-group>
		</view>
		<view class="title">联系方式</view>
		<view class="list">
			<u-cell-group border="false">
				<u-cell-item title="手机号码" :value="contact.tel" :value-style="style" @click="enterContent('手机号码', 'tel')" />
				<u-cell-item title="电子信箱" :value="contact.shortEmail" :value-style="style" @click="enterContent('电子信箱', 'email')" />
				<u-cell-item
					title="收信地址"
					:value="contact.shortMailAddress"
					:value-style="style"
					@click="enterContent('收信地址', 'mailAddress')"
				/>
				<u-cell-item
					title="紧急联系人"
					:value="contact.contactName"
					:value-style="style"
					@click="enterContent('紧急联系人', 'contactName')"
				/>
				<u-cell-item
					title="紧急联系人电话"
					:value="contact.contactTel"
					:value-style="style"
					@click="enterContent('紧急联系人电话', 'contactTel')"
				/>
			</u-cell-group>
		</view>
		<view class="title">驾驶证</view>
		<view class="list">
			<u-cell-group border="false">
				<u-cell-item title="驾驶证类型" :value="drcard.carClass" :value-style="style" :arrow="false" />
				<u-cell-item title="驾驶证有效期" :value="drcard.validTo" :value-style="style" :arrow="false" />
				<u-cell-item title="初次领证日期" :value="drcard.issueDate" :value-style="style" :arrow="false" />
			</u-cell-group>
		</view>

		<button class="btn" @tap="save" :disabled="realAuth == 3">保存信息</button>
		<view class="remark">您每次提交实名信息之后，都需要工作人员严格审查，请等候1~3天，这期间您将无法接单，特此声明！</view>
		<u-toast ref="uToast" />
	</view>
</template>

<script>
let dayjs = require('dayjs');
export default {
	data() {
		return {
			mode: 'fill',
			style: {
				color: '#FF9900'
			},
			cardBackground: [
				'../static/filling/credentials-bg.jpg',
				'../static/filling/credentials-bg.jpg',
				'../static/filling/credentials-bg.jpg',
				'../static/filling/credentials-bg.jpg',
				'../static/filling/credentials-bg.jpg',
				'../static/filling/credentials-bg.jpg'
			],
      //身份证信息
			idcard: {
				pid: '',
				name: '',
				sex: '',
				address: '',
				shortAddress: '',
				birthday: '',
				expiration: '',
				idcardFront: '',
				idcardBack: '',
				idcardHolding: ''
			},
			contact: {
				tel: '19916022997',
				email: '2977366691@qq.com',
				shortEmail: '2977366691@qq.com',
				mailAddress: '江西省抚州市',
				shortMailAddress: '江西省抚州市临川区',
				contactName: '张大炮',
				contactTel: '19930342245'
			},
			drcard: {
				issueDate: '',
				carClass: '',
				validFrom: '',
				validTo: '',
				drcardFront: '',
				drcardBack: '',
				drcardHolding: ''
			},
      keys:[],//云存储
			currentImg: {}, //当前图片
			realAuth: uni.getStorageSync('realAuth')
		};
	},
	methods: {
    //上传时要把整个信息都要扫描到，要不返回的数据会缺失
		scanIdcardFront: function(resp) {
		    let that = this;
		    let detail = resp.detail;
		    that.idcard.pid = detail.id.text;
		    that.idcard.name = detail.name.text;
		    that.idcard.sex = detail.gender.text;
		    that.idcard.address = detail.address.text;
		    //需要缩略身份证地址，文字太长页面显示不了
		    that.idcard.shortAddress = detail.address.text.substr(0, 15) + '...';
		    that.idcard.birthday = detail.birth.text;
		    //OCR插件拍摄到的身份证正面照片存储地址
		    that.idcard.idcardFront = detail.image_path;
		    //让身份证View标签加载身份证正面照片
		    that.cardBackground[0] = detail.image_path;
		    //发送Ajax请求，上传身份证正面照片
		    that.uploadCos(that.url.uploadCosPrivateFile, detail.image_path, 'driverAuth', function(resp) {
		        let data = JSON.parse(resp.data);
		        //身份证照片的云端URL地址
            let key = data.key
		        that.currentImg['idcardFront'] = key; //页面持久层保存身份证云端URL地址
            that.keys.push(key)
		    });
		},
    //身份证背面
    scanIdcardBack(res){
       let that=this
       let {image_path,valid_date}=res.detail
       that.cardBackground[1]=image_path
       let effectiveDate=valid_date.text.split('-')[1]
       that.idcard.expiration=dayjs(effectiveDate).format('YYYY-MM-DD')
       that.uploadCos(that.url.uploadCosPrivateFile, image_path, 'driverAuth', function(resp) {
           let data = JSON.parse(resp.data);
              //身份证照片的云端URL地址
           let key = data.key
           that.currentImg['idcardBack'] = key; //页面持久层保存身份证云端URL地址
           /*
            * 本页面所有上传到云端的照片云端URL地址都保存到数组中，因为用户可以反复拍摄身份证
            * 照片，那么之前上传的照片到最后应该从云端删除掉。页面提交完整实名认证信息的时候，
            * 需要比对cosImg数组中哪些照片不需要了，让云端删除不需要的证件照片
            */
           that.keys.push(key)
       });
    },
    //驾驶证正面
    scanDrcardFront: function(resp) {
        let that = this;
        let detail = resp.detail;
        that.drcard.issueDate = detail.issue_date.text; //初次领证日期
        that.drcard.carClass = detail.car_class.text; //准驾车型
        that.drcard.validFrom = detail.valid_from.text; //驾驶证起始有效期
        that.drcard.validTo = detail.valid_to.text; //驾驶证截止有效期
        that.drcard.drcardFront = detail.image_path;
        that.cardBackground[3] = detail.image_path;
        //把驾驶证正面照片上传到云端
        that.uploadCos(that.url.uploadCosPrivateFile, detail.image_path, 'driverAuth', function(resp) {
            let data = JSON.parse(resp.data);
            let key=data.key
            that.currentImg['drcardFront'] = key;
            that.keys.push(key)
        });
    },
   //上传照片
    updatePhoto(type,path){
         let that =this
         that.uploadCos(that.url.uploadCosPrivateFile,path, 'driverAuth', function(resp) {
             let data = JSON.parse(resp.data);
             that.keys.push(data.key)
             if(type==='idcardHolding'){
               that.cardBackground[2]=path
               that.currentImg['idcardHolding'] = data.key; //页面持久层保存身份证云端URL地址
               that.idcard.idcardHolding=data.key
             /*
              * 本页面所有上传到云端的照片云端URL地址都保存到数组中，因为用户可以反复拍摄身份证
              * 照片，那么之前上传的照片到最后应该从云端删除掉。页面提交完整实名认证信息的时候，
              * 需要比对cosImg数组中哪些照片不需要了，让云端删除不需要的证件照片
              */
           }else if(type==='drcardBack'){
             that.cardBackground[4]=path
             that.currentImg['drcardBack'] = data.key; //页面持久层保存身份证云端URL地址
             that.idcard.drcardBack=data.key
           }else if(type==='drcardHolding'){
             that.cardBackground[5]=path
             that.currentImg['drcardHolding'] = data.key; //页面持久层保存身份证云端URL地址
             that.idcard.drcardHolding=data.key
           }
         });
         that.$forceUpdate()//强制重新渲染
    },
    
    //点击弹出层显示，回显数据
    enterContent(title,key){
      let that = this
      uni.showModal({
        title:title,
        editable:true,
        content:that.contact[key], //提示信息
        success:function(res){
           if(res.confirm){
             if(key==='email'){
               that.contact['shortEmail']=res.content.substr(0,25)+(res.content.length>25?'...':'') //截取超出长度email
             }else if(key==='mailAddress'){
               that.contact['shortMailAddress']=res.content.substr(0,15)+(res.content.length>15?'...':'') //截取超出长度地址
             }
             that.contact[key]=res.content //将输入框的数据全部保存到contact对象中
             
           }
        }
      })
    },
    //保存信息
    /**
     *判断上传照片是否为6张，如果不是六张，弹出错误信息；执行前端提示框里的表单数据验证；表单验证成功弹出提示框
     * 删除多余存储桶里的照片：将当前显示的照片放进一个数组里面，在比较存储桶里面所有的照片，将不匹配的照片进行发起请求删除
     * 
    */
   save() {
       let that = this;
       //判断是否设置了6张照片
       if(Object.keys(that.currentImg).length!==6){
         this.$refs.uToast.show({
           title:'上传照片不足六张，请在上传',
           type:'error'
         })
       }
       //执行前端验证
       else if (
           that.checkValidTel(that.contact.tel, '手机号码') &&
           that.checkValidEmail(that.contact.email, '电子信箱') &&
           that.checkValidAddress(that.contact.mailAddress, '收信地址') &&
           that.checkValidName(that.contact.contactName, '联系人') &&
           that.checkValidTel(that.contact.contactTel, '联系人电话')
       ) {
           uni.showModal({
               title: '提示信息',
               content: '确认提交实名资料？',
               success: function(resp) {
                   if (resp.confirm) {
                       //比较哪些照片需要删除
                      
                       let realImg = [];
                       //从JSON中获取6张证件照片的云端存储地址
                      for(const key in that.currentImg){
                        realImg.push(that.currentImg[key])
                      }
                     
                      let deleteImg=that.keys.filter(item=>!realImg.includes(item))
                      console.log(deleteImg)
                       if (deleteImg.length > 0) {
                           //删除云端文件
                           that.ajax(that.url.deleteCosPrivateFile, 'POST', JSON.stringify({ keys: deleteImg }), function()                           { 
                               console.log('文件删除成功');
                           });
                       }
                       //需要上传的实名认证数据
                       let data = {
                           idCardNumber: that.idcard.pid,
                           name: that.idcard.name,
                           gender: that.idcard.sex,
                           birthday: that.idcard.birthday,
                           phone: that.contact.tel,
                           email: that.contact.email,
                           address: that.contact.mailAddress,
                           emergencyName: that.contact.contactName,
                           emergencyPhone: that.contact.contactTel,
                           idCardAddress: that.idcard.address,
                           idCardFrontImage: that.currentImg.idcardFront,
                           idCardBackImage: that.currentImg.idcardBack,
                           idCardHoldingImage: that.currentImg.idcardHolding,
                           idCardExpireTime: that.idcard.expiration,
                           driverCardType: that.drcard.carClass,
                           driverCardExpireTime: that.drcard.validTo,
                           driverCardIssueTime: that.drcard.issueDate,
                           driverCardFrontImage: that.currentImg.drcardFront,
                           driverCardBackImage: that.currentImg.drcardBack,
                           driverCardHoldingImage: that.currentImg.drcardHolding
                       };
                       //提交Ajax请求，上传数据
                       that.ajax(that.url.updateDriverAuth, 'POST', data, function(resp) {
                           console.log('更新成功');
                           that.$refs.uToast.show({
                               title: '资料提交成功',
                               type: 'success',
                               callback: function() {
                                   uni.setStorageSync('realAuth', 3); //更新小程序Storage
                                   that.realAuth = 3; // realAuth 1代表成功注册，2表示上传了六张图片，3表示提交所有的信息
                                   if (that.mode == 'create') {
                                       //TODO 提示新注册的司机采集面部数据
                                       uni.redirectTo({
                                         url:'../face_camera/face_camera?mode=create'
                                       })
                                   }
                                   else {
                                       //跳转到工作台页面
                                       uni.switchTab({
                                           url: '../../pages/workbench/workbench'
                                       });
                                   }
                               }
                           });
                       });
                   }
               }
           });
       }
   },

    //跳转到拍照功能
    takePhoto(type){
      uni.navigateTo({
        url:'../identity_camera/identity_camera?type='+type
      })
    }
	},
	onLoad: function(options) {
		let that = this;
        that.mode = options.mode;
        console.log(options)
	}
};
</script>

<style lang="less">
@import url('filling.less');
</style>
