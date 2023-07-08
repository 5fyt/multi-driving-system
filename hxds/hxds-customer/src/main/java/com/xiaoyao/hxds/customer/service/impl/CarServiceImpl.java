package com.xiaoyao.hxds.customer.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaoyao.hxds.customer.db.entity.CustomerCarEntity;
import com.xiaoyao.hxds.customer.db.mapper.CustomerCarMapper;
import com.xiaoyao.hxds.customer.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final CustomerCarMapper carMapper;

    @Override
    public Map<String, Object> addCar(Map<String, Object> param) {
        carMapper.insert(BeanUtil.toBean(param, CustomerCarEntity.class));
        return listCars(param);
    }

    @Override
    public Map<String, Object> deleteCar(Map<String, Object> param) {
        QueryWrapper<CustomerCarEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("customer_id", MapUtil.getLong(param, "customerId"))
                .eq("id", MapUtil.getLong(param, "id"));
        carMapper.delete(queryWrapper);
        return listCars(param);
    }

    @Override
    public Map<String, Object> listCars(Map<String, Object> param) {
        QueryWrapper<CustomerCarEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "car_plate", "car_type")
                .eq("customer_id", MapUtil.getLong(param, "customerId"));
        List<Map<String, Object>> cars = carMapper.selectMaps(queryWrapper);
        for (int i = 0; i < cars.size(); i++) {
            Map<String, Object> car = cars.get(i);
            Map<String, Object> newCar = new HashMap<>();
            BeanUtil.copyProperties(car, newCar, CopyOptions.create()
                    .setFieldNameEditor(StrUtil::toCamelCase));
            newCar.replace("id", newCar.get("id").toString());
            cars.set(i, newCar);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("cars", cars);
        return map;
    }
}
