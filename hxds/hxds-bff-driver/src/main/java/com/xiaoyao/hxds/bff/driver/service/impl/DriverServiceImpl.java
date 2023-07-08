package com.xiaoyao.hxds.bff.driver.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import com.xiaoyao.hxds.bff.driver.client.*;
import com.xiaoyao.hxds.bff.driver.controller.form.*;
import com.xiaoyao.hxds.bff.driver.service.DriverService;
import com.xiaoyao.hxds.common.result.Assert;
import com.xiaoyao.hxds.common.result.R;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {
    private final DriverClient driverClient;
    private final StorageClient storageClient;
    private final OrderClient orderClient;
    private final MapClient mapClient;
    private final MessageClient messageClient;
    private final CustomerClient customerClient;

    @Override
    public Map<String, Object> register(RegisterForm form) {
        R r = Assert.ok(driverClient.register(BeanUtil.beanToMap(form)));
        long driverID = MapUtil.getLong(r, "id");

        // TODO 修改登录类型，支持多账户认证。
        // TODO 获取权限列表

        StpUtil.login(driverID);

        Map<String, Object> map = new HashMap<>();
        map.put("token", StpUtil.getTokenValue());
        map.put("permissions", StpUtil.getPermissionList(driverID));
        return map;
    }

    @Override
    public Map<String, Object> login(LoginForm form) {
        R r = Assert.ok(driverClient.login(form.getCode()));

        StpUtil.login(MapUtil.getLong(r, "id"));

        Map<String, Object> map = new HashMap<>();
        map.put("token", StpUtil.getTokenValue());
        map.put("permissions", StpUtil.getPermissionList());
        map.put("status", r.get("status"));
        map.put("archive", r.get("archive"));
        return map;
    }

    @Override
    public void authenticate(AuthenticateForm form) {
        Map<String, Object> map = BeanUtil.beanToMap(form);
        map.put("driverID", StpUtil.getLoginIdAsLong());
        Assert.ok(driverClient.authenticate(map));
    }

    @Override
    public void archive(ArchiveForm form) {
        Map<String, Object> map = BeanUtil.beanToMap(form);
        map.put("driverID", StpUtil.getLoginIdAsLong());
        Assert.ok(driverClient.archive(map));
    }

    @Override
    public Map<String, Object> uploadAuthenticationPhoto(MultipartFile file) {
        R r = Assert.ok(storageClient.upload(file));
        Map<String, Object> map = new HashMap<>();
        map.put("key", r.get("key"));
        return map;
    }

    @Override
    public void deleteAuthenticationPhoto(DeleteFilesForm form) {
        Assert.ok(storageClient.delete(BeanUtil.beanToMap(form)));
    }

    @Override
    public void logout() {
        StpUtil.logout();
    }

    @Override
    public Map<String, Object> profile() {
        R r = Assert.ok(driverClient.profile(StpUtil.getLoginIdAsLong()));
        Map<String, Object> map = new HashMap<>();
        map.put("nickname", r.get("nickname"));
        map.put("photo", r.get("photo"));
        map.put("status", r.get("status"));
        map.put("createTime", r.get("createTime"));
        map.put("archive", r.get("archive"));
        map.put("level", r.get("level"));
        map.put("totalOrder", r.get("totalOrder"));
        map.put("weekOrder", r.get("weekOrder"));
        map.put("weekComment", r.get("weekComment"));
        map.put("appeal", r.get("appeal"));
        map.put("balance", r.get("balance"));
        return map;
    }

    @Override
    public Map<String, Object> getWorkbenchData() {
        long driverID = StpUtil.getLoginIdAsLong();
        R settings = Assert.ok(driverClient.settings(driverID));
        R business = Assert.ok(orderClient.driverTodayBusinessData(driverID));

        Map<String, Object> map = new HashMap<>();
        map.put("isAutoAccept", settings.get("isAutoAccept"));
        map.put("orientation", settings.get("orientation"));
        map.put("isAutoListen", settings.get("isAutoListen"));
        map.put("orderDistance", settings.get("orderDistance"));
        map.put("rangeDistance", settings.get("rangeDistance"));
        map.put("drivingTime", business.get("drivingTime"));
        map.put("income", business.get("income"));
        map.put("order", business.get("order"));
        return map;
    }

    @Override
    public Map<String, Object> searchDrivers(SearchDriverForm form) {
        R r = Assert.ok(driverClient.searchDrivers(BeanUtil.beanToMap(form)));
        Map<String, Object> map = new HashMap<>();
        map.put("data", r.get("data"));
        map.put("total", r.get("total"));
        map.put("pages", r.get("pages"));
        return map;
    }

    @Override
    public Map<String, Object> getAuthenticationInfo() {
        long driverID = StpUtil.getLoginIdAsLong();
        R r = Assert.ok(driverClient.authenticateInfo(driverID));

        // 将图片的key转换为临时访问地址
        R r1 = Assert.ok(storageClient.tempUrl(Map.of("key", MapUtil.getStr(r, "idCardFrontImage"),
                "minutes", 5)));
        R r2 = Assert.ok(storageClient.tempUrl(Map.of("key", MapUtil.getStr(r, "idCardBackImage"),
                "minutes", 5)));
        R r3 = Assert.ok(storageClient.tempUrl(Map.of("key", MapUtil.getStr(r, "idCardHoldingImage"),
                "minutes", 5)));
        R r4 = Assert.ok(storageClient.tempUrl(Map.of("key", MapUtil.getStr(r, "driverCardFrontImage"),
                "minutes", 5)));
        R r5 = Assert.ok(storageClient.tempUrl(Map.of("key", MapUtil.getStr(r, "driverCardBackImage"),
                "minutes", 5)));
        R r6 = Assert.ok(storageClient.tempUrl(Map.of("key", MapUtil.getStr(r, "driverCardHoldingImage"),
                "minutes", 5)));

        Map<String, Object> map = new HashMap<>();
        map.put("name", MapUtil.getStr(r, "name"));
        map.put("gender", MapUtil.getStr(r, "gender"));
        map.put("birthday", MapUtil.getStr(r, "birthday"));
        map.put("idCardNumber", MapUtil.getStr(r, "idCardNumber"));
        map.put("idCardAddress", MapUtil.getStr(r, "idCardAddress"));
        map.put("idCardExpireTime", MapUtil.getStr(r, "idCardExpireTime"));
        map.put("phone", MapUtil.getStr(r, "phone"));
        map.put("email", MapUtil.getStr(r, "email"));
        map.put("address", MapUtil.getStr(r, "address"));
        map.put("emergencyName", MapUtil.getStr(r, "emergencyName"));
        map.put("emergencyPhone", MapUtil.getStr(r, "emergencyPhone"));
        map.put("driverCardType", MapUtil.getStr(r, "driverCardType"));
        map.put("driverCardExpireTime", MapUtil.getStr(r, "driverCardExpireTime"));
        map.put("driverCardIssueTime", MapUtil.getStr(r, "driverCardIssueTime"));
        map.put("idCardFrontImage", MapUtil.getStr(r1, "url"));
        map.put("idCardBackImage", MapUtil.getStr(r2, "url"));
        map.put("idCardHoldingImage", MapUtil.getStr(r3, "url"));
        map.put("driverCardFrontImage", MapUtil.getStr(r4, "url"));
        map.put("driverCardBackImage", MapUtil.getStr(r5, "url"));
        map.put("driverCardHoldingImage", MapUtil.getStr(r6, "url"));
        return map;
    }

    @Override
    public void updateLocation(UpdateLocationForm form) {
        Map<String, Object> map = BeanUtil.beanToMap(form);
        map.put("driverID", StpUtil.getLoginIdAsLong());
        mapClient.updateDriverLocation(map);
    }

    @Override
    public void deleteLocation() {
        Map<String, Object> map = new HashMap<>();
        map.put("driverID", StpUtil.getLoginIdAsLong());
        Assert.ok(mapClient.deleteDriverLocation(map));
    }

    @Override
    public Map<String, Object> getOrderMessages() {
        Map<String, Object> messageMap = new HashMap<>();
        messageMap.put("driverID", StpUtil.getLoginIdAsLong());
        R messageR = Assert.ok(messageClient.getDriverOrdersMessage(messageMap));

        Map<String, Object> map = new HashMap<>();
        map.put("messages", messageR.get("messages"));
        return map;
    }

    @Override
    public Map<String, Object> acceptOrder(AcceptOrderForm form) {
        Map<String, Object> param = new HashMap<>();
        param.put("orderID", form.getOrderID());
        param.put("driverID", StpUtil.getLoginIdAsString());
        R r = Assert.ok(orderClient.acceptOrder(param));

        Map<String, Object> map = new HashMap<>();
        map.put("result", r.get("result"));
        // 抢单成功后额外返回订单的信息
        if (MapUtil.getInt(param, "result") == 1) {
            map.put("createTime", r.get("createTime"));
            map.put("expectsFee", r.get("expectsFee"));
            map.put("carPlate", r.get("carPlate"));
            map.put("carType", r.get("carType"));

            R customer = Assert.ok(customerClient.getCustomerInfoInOrder(
                    Map.of("customerID", MapUtil.getStr(r, "customerId"))));
            map.put("phone", customer.get("phone"));
            map.put("gender", customer.get("gender"));
            map.put("photo", customer.get("photo"));
        }
        return map;
    }
}
