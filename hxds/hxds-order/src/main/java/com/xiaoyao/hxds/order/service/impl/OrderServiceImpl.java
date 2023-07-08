package com.xiaoyao.hxds.order.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaoyao.hxds.order.db.entity.OrderEntity;
import com.xiaoyao.hxds.order.db.mapper.OrderMapper;
import com.xiaoyao.hxds.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderMapper orderMapper;
    private final StringRedisTemplate redisTemplate;

    @Override
    public Map<String, Object> getDriverTodayBusinessData(long driverID) {
        QueryWrapper<OrderEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("start_time", "end_time", "real_fee")
                .eq("driver_id", driverID)
                .eq("pay_date", LocalDate.now());
        List<OrderEntity> orders = orderMapper.selectList(queryWrapper);

        int drivingTime = 0;
        BigDecimal income = new BigDecimal("0.00");
        for (OrderEntity order : orders) {
            drivingTime += ChronoUnit.MINUTES.between(order.getStartTime(), order.getEndTime());
            income = income.add(order.getRealFee());
        }

        Map<String, Object> map = new HashMap<>();
        map.put("drivingTime", drivingTime);
        map.put("income", income.toString());
        map.put("order", orders.size());
        return map;
    }

    @Override
    public Map<String, Object> createOrder(Map<String, Object> param) {
        OrderEntity order = BeanUtil.toBean(param, OrderEntity.class);
        order.setDate(LocalDate.now());
        order.setCreateTime(LocalDateTime.now());
        orderMapper.insert(order);

        // TODO 优化：清除过期订单的抢单缓存
        redisTemplate.opsForValue().set("order:" + order.getId(), "1");

        Map<String, Object> map = new HashMap<>();
        map.put("orderID", order.getId().toString());
        return map;
    }

    @Override
    public Map<String, Object> acceptOrder(Map<String, Object> param) {
        Boolean result = redisTemplate.delete("order:" + MapUtil.getStr(param, "orderID"));

        Map<String, Object> map = new HashMap<>();
        map.put("result", Boolean.TRUE.equals(result) ? 1 : 0);
        if (Boolean.TRUE.equals(result)) {
            OrderEntity order = new OrderEntity();
            order.setId(MapUtil.getLong(param, "orderID"));
            order.setDriverId(MapUtil.getLong(param, "driverID"));
            order.setAcceptTime(LocalDateTime.now());
            orderMapper.updateById(order);

            QueryWrapper<OrderEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.select("customer_id", "create_time", "expects_fee", "car_plate", "car_type")
                    .eq("id", order.getId());
            BeanUtil.copyProperties(orderMapper.selectMaps(queryWrapper).get(0), map, CopyOptions.create()
                    .setFieldNameEditor(StrUtil::toCamelCase));
        }
        return map;
    }

    @Override
    public Map<String, Object> isOrderAccepted(Map<String, Object> param) {
        // 如果缓存中的key不存在，说明订单已被抢。
        Boolean result = redisTemplate.hasKey("order:" + MapUtil.getStr(param, "orderID"));

        Map<String, Object> map = new HashMap<>();
        map.put("result", Boolean.TRUE.equals(result) ? 1 : 0);
        return map;
    }

    @Override
    public Map<String, Object> cancelOrder(Map<String, Object> param) {
        // 先删除数据库中的记录，如果删除成功再删除缓存中的key。
        QueryWrapper<OrderEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", MapUtil.getLong(param, "orderID"))
                .eq("customer_id", MapUtil.getLong(param, "customerID"));
        int deleted = orderMapper.delete(queryWrapper);
        if (deleted > 0) {
            redisTemplate.delete("order:" + MapUtil.getStr(param, "orderID"));
        }

        Map<String, Object> map = new HashMap<>();
        map.put("result", deleted > 0 ? 1 : 0);
        return map;
    }
}
