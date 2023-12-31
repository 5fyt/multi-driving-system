 import App from './App'


// #ifndef VUE3
import Vue from 'vue'
Vue.config.productionTip = false
App.mpType = 'app'
const app = new Vue({
	...App
})

//注册uView组件
import uView from 'uview-ui';
Vue.use(uView);


app.$mount() 
// #endif

// #ifdef VUE3
import {
	createSSRApp
} from 'vue'
export function createApp() {
	const app = createSSRApp(App)
	return {
		app
	}
}
// #endif


let baseUrl = "http://192.168.0.7:9000"

Vue.prototype.url = {
    registerNewDriver: `${baseUrl}/driver/register`,
    uploadCosPrivateFile:`${baseUrl}/driver/upload-authentication-photo`,
    deleteCosPrivateFile:`${baseUrl}/driver/delete-authentication-photo`,
    updateDriverAuth:`${baseUrl}/driver/authenticate `,
    createDriverFaceModal:`${baseUrl}/driver/archive `,//创建人脸模型
    login:`${baseUrl}/driver/login`,
    driverBaseInfo:`${baseUrl}/driver/profile`,//司机的基本信息
    workbenchData:`${baseUrl}/driver/workbench `,//工作台信息
    driverAuthInfo:`${baseUrl}/driver/authentication-info`,//上传司机的工作台信息
    searchOrderMessage:`${baseUrl}/driver/order-messages`,//查询订单消息
    acceptOrder:`${baseUrl}/driver/accept`,//抢单
    startAcceptOrder:`${baseUrl}/driver/refresh-working-data`,//开始接单且将司机的位置信息和返回的订单列表
    startOrder:`${baseUrl}/driver/start-work`,//开始接单扫描附近的单
    stopOrder:`${baseUrl}/driver/stop-work ` //停止接单
}
Vue.prototype.tencent = {
map: {
		referer: "华夏代驾",
		key: "XY6BZ-GI4L7-ETKXJ-HE4QX-WDDW7-YJFAC"
	}
}


Vue.prototype.ajax = function(url, method, data, fun, load) {
	let timer = null
  //没有传load参数，加载进度条显示，等到请求成功后又自动清除
	if (load == true || load == undefined) {
		uni.showLoading({
			title: "执行中"
		})
		timer = setTimeout(function() {
			uni.hideLoading()
		}, 60 * 1000)
	}
//定义ajax请求，封装相关项
	uni.request({
		"url": url,
		"method": method,
		"header": {
			token: uni.getStorageSync("token"),
		},
		"data": data,
		success: function(resp) {
      //成功后如果没有传load参数就将定时器清空，清除加载进度条
			if (load == true || load == undefined) {
				clearTimeout(timer)
				uni.hideLoading()
			}
      //请求错误返回登入页面
			if (resp.statusCode == 401) {
				uni.redirectTo({
					url: "/pages/login/login.vue"
				})
			} else if (resp.statusCode == 200 && resp.data.code == 200) {
				let data = resp.data
        //已经请求成功，如果有token，请求成功后将返回过来的token再次更新
				if (data.hasOwnProperty("token")) {
					let token = data.token
					uni.setStorageSync("token", token)
				}
        //调用回调函数
				fun(resp)
			} else if (resp.data.error=="该微信无法注册") {
				uni.showToast({
					icon:"none",
					title:"该微信无法注册"
				})
			} else {
				uni.showToast({
					icon: "error",
					title: `${resp.data.message}`
				})
				console.error(resp.data)
			}
		},
		fail: function(error) {
			if (load == true || load == undefined) {
				clearTimeout(timer)
				uni.hideLoading()
			}
		}
	})
}


Vue.prototype.refreshMessage = function(that) {
	uni.request({
		"url": that.url.refreshMessage,
		"method": "POST",
		"header": {
			token: uni.getStorageSync("token")
		},
		"data": {
			identity: 'driver'
		},
		success: function(resp) {

			if (resp.statusCode == 401) {
				uni.redirectTo({
					url: "/pages/login/login.vue"
				})
			} else if (resp.statusCode == 200 && resp.data.code == 200) {
				uni.$emit("updateMessageService", true)
				let result = resp.data.result
				let lastRows = result.lastRows
				let unreadRows = result.unreadRows
				if (lastRows > 0) {
					uni.$emit("showMessageTip", lastRows)
				}
			} else {
				console.error(resp.data)
				//在工作台页面触发更新消息服务状态，显示服务可用或者不可用
				uni.$emit("updateMessageService", false)
			}
			console.log("刷新消息")
		},
		fail: function(error) {
			//在工作台页面触发更新消息服务状态，显示服务可用或者不可用
			uni.$emit("updateMessageService", false)
		}
	})
}

Vue.prototype.uploadCos = function(url, path, module, fun) {
	uni.uploadFile({
		url: url,
		filePath: path,
		name: "file",
		header: {
			token: uni.getStorageSync("token")
		},
		formData: {
			"module": module
		},
		success: function(resp) {
			let data = JSON.parse(resp.data)
			if (resp.statusCode == 401) {
				uni.redirectTo({
					url: "/pages/login/login.vue"
				})
			} else if (resp.statusCode == 200 && data.code == 200) {
				fun(resp)
			} else {
				uni.showToast({
					icon: "none",
					title: data.error
				})
			}
		}
	})
}

Vue.prototype.upload = function(url, path, data, fun) {
	uni.uploadFile({
		url: url,
		filePath: path,
		name: "file",
		header: {
			token: uni.getStorageSync("token")
		},
		formData: data,
		success: function(resp) {
			let data = JSON.parse(resp.data)
			if (resp.statusCode == 401) {
				uni.redirectTo({
					url: "/pages/login/login.vue"
				})
			} else if (resp.statusCode == 200 && data.code == 200) {
				fun(resp)
			} else {
				uni.showToast({
					icon: "none",
					title: data.error
				})
			}
		}
	})
}

Vue.prototype.toPage = function(url) {
	uni.navigateTo({
		url: url
	})
}


Vue.prototype.checkNull = function(data, name) {
	if (data == null) {
		this.$refs.uToast.show({
			title: name + "不能为空",
			type: 'error'
		})
		return true
	}
	return false
}

Vue.prototype.checkBlank = function(data, name) {
	if (data == null || data == "") {
		this.$refs.uToast.show({
			title: name + "不能为空",
			type: 'error'
		})
		return true
	}
	return false
}

Vue.prototype.checkValidName = function(data, name) {
	if (data == null || data == "") {
		this.$refs.uToast.show({
			title: name + "不能为空",
			type: 'error'
		})
		return false
	} else if (!/^[\u4e00-\u9fa5]{2,15}$/.test(data)) {
		this.$refs.uToast.show({
			title: name + "不正确",
			type: 'error'
		})
		return false
	}
	return true
}
Vue.prototype.checkValidTel = function(data, name) {
	if (data == null || data == "") {
		this.$refs.uToast.show({
			title: name + "不能为空",
			type: 'error'
		})
		return false
	} else if (!/^1[0-9]{10}$/.test(data)) {
		this.$refs.uToast.show({
			title: name + "不正确",
			type: 'error'
		})
		return false
	}
	return true
}
Vue.prototype.checkValidEmail = function(data, name) {
	if (data == null || data == "") {
		this.$refs.uToast.show({
			title: name + "不能为空",
			type: 'error'
		})
		return false
	} else if (!/^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/.test(data)) {
		this.$refs.uToast.show({
			title: name + "不正确",
			type: 'error'
		})
		return false
	}
	return true
}

Vue.prototype.checkValidAddress = function(data, name) {

	if (data == null || data == "") {
		this.$refs.uToast.show({
			title: name + "不能为空",
			type: 'error'
		})
		return false
	} else if (!/^[0-9a-zA-Z\u4e00-\u9fa5\-]{6,50}$/.test(data)) {
		this.$refs.uToast.show({
			title: name + "不正确",
			type: 'error'
		})
		return false
	}
	return true
}

Vue.prototype.checkValidFee = function(data, name) {

	if (data == null || data == "") {
		this.$refs.uToast.show({
			title: name + "不能为空",
			type: 'error'
		})
		return false
	} else if (!/^[1-9]\d*\.\d{1,2}$|^0\.\d{1,2}$|^[1-9]\d*$/.test(data)) {
		this.$refs.uToast.show({
			title: name + "不正确",
			type: 'error'
		})
		return false
	}
	return true
}


Vue.prototype.changeNumber = function(value) {
	let newValue = ['', ''];
	let fr = 1000;
	const ad = 1;
	let num = 3;
	const fm = 1;
	while (value / fr >= 1) {
		fr *= 10;
		num += 1;
	}
	if (num <= 4) {
		// 千
		newValue[1] = '千';
		newValue[0] = parseInt(value / 1000) + '';
	} else if (num <= 8) {
		// 万
		const text1 = parseInt(num - 4) / 3 > 1 ? '千万' : '万';
		const fm = '万' === text1 ? 10000 : 10000000;
		newValue[1] = text1;
		newValue[0] = value / fm + '';
	} else if (num <= 16) {
		// 亿
		let text1 = (num - 8) / 3 > 1 ? '千亿' : '亿';
		text1 = (num - 8) / 4 > 1 ? '万亿' : text1;
		text1 = (num - 8) / 7 > 1 ? '千万亿' : text1;
		// tslint:disable-next-line:no-shadowed-variable
		let fm = 1;
		if ('亿' === text1) {
			fm = 100000000;
		} else if ('千亿' === text1) {
			fm = 100000000000;
		} else if ('万亿' === text1) {
			fm = 1000000000000;
		} else if ('千万亿' === text1) {
			fm = 1000000000000000;
		}
		newValue[1] = text1;
		newValue[0] = parseInt(value / fm) + '';
	}
	if (value < 1000) {
		newValue[1] = '';
		newValue[0] = value + '';
	}
	let temp = Math.floor((newValue[0]) * 100) / 100
	return temp + newValue[1];
}
