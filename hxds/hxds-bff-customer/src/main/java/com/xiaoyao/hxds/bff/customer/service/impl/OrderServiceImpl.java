package com.xiaoyao.hxds.bff.customer.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.map.MapUtil;
import com.xiaoyao.hxds.bff.customer.client.MapClient;
import com.xiaoyao.hxds.bff.customer.client.MessageClient;
import com.xiaoyao.hxds.bff.customer.client.OrderClient;
import com.xiaoyao.hxds.bff.customer.controller.form.CancelOrderForm;
import com.xiaoyao.hxds.bff.customer.controller.form.CreateOrderForm;
import com.xiaoyao.hxds.bff.customer.controller.form.IsAcceptForm;
import com.xiaoyao.hxds.bff.customer.service.OrderService;
import com.xiaoyao.hxds.common.result.Assert;
import com.xiaoyao.hxds.common.result.E;
import com.xiaoyao.hxds.common.result.R;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderClient orderClient;
    private final MapClient mapClient;
    private final MessageClient messageClient;

    @Override
    public void createOrder(CreateOrderForm form) {
        // 计算订单的预计路程和时间
        Map<String, Object> calculateMap = new HashMap<>();
        calculateMap.put("from", form.getStartCoordinate());
        calculateMap.put("to", form.getEndCoordinate());
        R calculateR = Assert.ok(mapClient.calculateDrivingLine(calculateMap));
        int distance = MapUtil.getInt(calculateR, "distance");
        int duration = MapUtil.getInt(calculateR, "duration");

        // 查看附近是否有符合条件的司机
        Map<String, Object> searchMap = new HashMap<>();
        searchMap.put("fromLongitude", form.getStartCoordinate().split(",")[1]);
        searchMap.put("fromLatitude", form.getStartCoordinate().split(",")[0]);
        searchMap.put("toLongitude", form.getEndCoordinate().split(",")[1]);
        searchMap.put("toLatitude", form.getEndCoordinate().split(",")[0]);
        searchMap.put("drivingDistance", distance / 1000.0);
        R searchR = Assert.ok(mapClient.searchNearbyDriver(searchMap));
        List<Map<String, Object>> drivers = (List<Map<String, Object>>) searchR.get("drivers");
        Assert.isTrue(drivers.size() > 0, E.NO_DRIVER_NEARBY);

        // 附近有符合条件的司机，开始创建订单。
        Map<String, Object> orderMap = new HashMap<>();
        orderMap.put("customerId", StpUtil.getLoginIdAsString());
        orderMap.put("carPlate", form.getCarPlate());
        orderMap.put("carType", form.getCarType());
        orderMap.put("startCoordinate", form.getStartCoordinate().split(",")[1] + "," + form.getStartCoordinate().split(",")[0]);
        orderMap.put("startPlace", form.getStartPlace());
        orderMap.put("endCoordinate", form.getEndCoordinate().split(",")[1] + "," + form.getEndCoordinate().split(",")[0]);
        orderMap.put("endPlace", form.getEndPlace());
        orderMap.put("expectsMileage", distance);
        orderMap.put("expectsTime", duration);
        // TODO 调用规则引擎计算预计费用
        orderMap.put("expectsFee", "100.00");
        R orderR = Assert.ok(orderClient.createOrder(orderMap));
        String orderID = MapUtil.getStr(orderR, "orderID");

        // 给附近符合条件的司机发送抢单通知
        Map<String, Object> messageMap = new HashMap<>();
        List<String> driverIDs = new ArrayList<>();
        List<String> distances = new ArrayList<>();
        for (Map<String, Object> driver : drivers) {
            driverIDs.add(MapUtil.getStr(driver, "driverID"));
            distances.add(MapUtil.getStr(driver, "distance"));
        }
        messageMap.put("driverIDs", driverIDs);
        messageMap.put("distances", distances);
        messageMap.put("orderID", orderID);
        messageMap.put("startPlace", form.getStartPlace());
        messageMap.put("endPlace", form.getEndPlace());
        messageMap.put("expectsMileage", distance);
        messageMap.put("expectsTime", duration);
        messageMap.put("expectsFee", orderMap.get("expectsFee"));
        Assert.ok(messageClient.sendMessageToDrivers(messageMap));
    }

    @Override
    public Map<String, Object> cancelOrder(CancelOrderForm form) {
        Map<String, Object> param = new HashMap<>();
        param.put("orderID", form.getOrderId());
        param.put("customerId", StpUtil.getLoginIdAsString());
        R r = Assert.ok(orderClient.cancelOrder(param));

        Map<String, Object> map = new HashMap<>();
        map.put("result", r.get("result"));
        return map;
    }

    @Override
    public Map<String, Object> isAccepted(IsAcceptForm form) {
        Map<String, Object> param = new HashMap<>();
        param.put("orderID", form.getOrderId());
        R r = Assert.ok(orderClient.isAccepted(param));

        Map<String, Object> map = new HashMap<>();
        map.put("result", r.get("result"));
        return map;
    }
}
