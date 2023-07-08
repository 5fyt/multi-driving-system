package com.xiaoyao.hxds.customer.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaoyao.hxds.common.result.Assert;
import com.xiaoyao.hxds.common.result.E;
import com.xiaoyao.hxds.customer.db.entity.CustomerAccountEntity;
import com.xiaoyao.hxds.customer.db.mapper.CustomerAccountMapper;
import com.xiaoyao.hxds.customer.properties.WXProperties;
import com.xiaoyao.hxds.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final WXProperties wxProperties;
    private final CustomerAccountMapper customerAccountMapper;


    @Override
    public Map<String, Object> register(Map<String, Object> param) {
        String openid = getOpenid(MapUtil.getStr(param, "code"));

        // 确保用户未注册
        QueryWrapper<CustomerAccountEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid", openid);
        Long count = customerAccountMapper.selectCount(queryWrapper);
        Assert.isTrue(count == 0, E.OPENID_EXIST);

        CustomerAccountEntity customer = BeanUtil.toBean(param, CustomerAccountEntity.class);
        customer.setOpenid(openid);
        customer.setCreateTime(LocalDateTime.now());
        customer.setUpdateTime(LocalDateTime.now());
        customerAccountMapper.insert(customer);

        Map<String, Object> map = new HashMap<>();
        map.put("id", customer.getId());
        return map;
    }

    @Override
    public Map<String, Object> login(Map<String, Object> param) {
        String openid = getOpenid(MapUtil.getStr(param, "code"));

        // 确保用户已注册
        QueryWrapper<CustomerAccountEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id").eq("openid", openid);
        CustomerAccountEntity customer = customerAccountMapper.selectOne(queryWrapper);
        Assert.notNull(customer, E.NOT_REGISTERED);

        return BeanUtil.beanToMap(customer);
    }

    @Override
    public Map<String, Object> getCustomerInfoInOrder(Map<String, Object> param) {
        QueryWrapper<CustomerAccountEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("phone", "gender", "photo")
                .eq("id", MapUtil.getStr(param, "customerID"));
        return BeanUtil.beanToMap(customerAccountMapper.selectOne(queryWrapper));
    }

    private String getOpenid(String code) {
        String response = HttpUtil.get("https://api.weixin.qq.com/sns/jscode2session", Map.of(
                "appid", wxProperties.getAppid(),
                "secret", wxProperties.getSecret(),
                "js_code", code,
                "grant_type", "authorization_code"));
        JSONObject jsonObject = JSONUtil.parseObj(response);
        return jsonObject.getStr("openid");
        //        return "code-openid";
    }
}
