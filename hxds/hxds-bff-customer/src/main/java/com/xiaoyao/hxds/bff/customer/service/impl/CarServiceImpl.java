package com.xiaoyao.hxds.bff.customer.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import com.xiaoyao.hxds.bff.customer.client.CustomerClient;
import com.xiaoyao.hxds.bff.customer.controller.form.CarAddForm;
import com.xiaoyao.hxds.bff.customer.controller.form.CarDeleteForm;
import com.xiaoyao.hxds.bff.customer.service.CarService;
import com.xiaoyao.hxds.common.result.Assert;
import com.xiaoyao.hxds.common.result.R;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final CustomerClient customerClient;

    @Override
    public Map<String, Object> addCar(CarAddForm form) {
        Map<String, Object> param = BeanUtil.beanToMap(form);
        param.put("customerId", StpUtil.getLoginIdAsLong());
        R r = Assert.ok(customerClient.addCar(param));
        Map<String, Object> map = new HashMap<>();
        map.put("cars", r.get("cars"));
        return map;
    }

    @Override
    public Map<String, Object> deleteCar(CarDeleteForm form) {
        Map<String, Object> param = BeanUtil.beanToMap(form);
        param.put("customerId", StpUtil.getLoginIdAsLong());
        R r = Assert.ok(customerClient.deleteCar(param));
        Map<String, Object> map = new HashMap<>();
        map.put("cars", r.get("cars"));
        return map;
    }

    @Override
    public Map<String, Object> listCar() {
        Map<String, Object> param = new HashMap<>();
        param.put("customerId", StpUtil.getLoginIdAsLong());
        R r = Assert.ok(customerClient.listCars(param));
        Map<String, Object> map = new HashMap<>();
        map.put("cars", r.get("cars"));
        return map;
    }
}
