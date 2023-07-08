package com.xiaoyao.hxds.bff.customer.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import com.xiaoyao.hxds.bff.customer.client.CustomerClient;
import com.xiaoyao.hxds.bff.customer.controller.form.LoginForm;
import com.xiaoyao.hxds.bff.customer.controller.form.RegisterForm;
import com.xiaoyao.hxds.bff.customer.service.CustomerService;
import com.xiaoyao.hxds.common.result.Assert;
import com.xiaoyao.hxds.common.result.R;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerClient customerClient;

    @Override
    public Map<String, Object> register(RegisterForm form) {
        R r = Assert.ok(customerClient.register(BeanUtil.beanToMap(form)));

        StpUtil.login(MapUtil.getLong(r, "id"));

        Map<String, Object> map = new HashMap<>();
        map.put("token", StpUtil.getTokenValue());
        return map;
    }

    @Override
    public Map<String, Object> login(LoginForm form) {
        R r = Assert.ok(customerClient.login(BeanUtil.beanToMap(form)));

        StpUtil.login(MapUtil.getLong(r, "id"));

        Map<String, Object> map = new HashMap<>();
        map.put("token", StpUtil.getTokenValue());
        return map;
    }

    @Override
    public void logout() {
        StpUtil.logout();
    }
}
