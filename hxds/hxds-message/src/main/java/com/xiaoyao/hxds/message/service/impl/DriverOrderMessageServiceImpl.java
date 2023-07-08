package com.xiaoyao.hxds.message.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import com.xiaoyao.hxds.message.service.DriverOrderMessageService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class DriverOrderMessageServiceImpl implements DriverOrderMessageService {
    private final static String EXCHANGE = "driver.order";
    private final static String QUEUE_PREFIX = "driver.order.";

    private final AmqpAdmin amqpAdmin;
    private final RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        amqpAdmin.declareExchange(new DirectExchange(EXCHANGE));
    }

    @Async
    @Override
    public void sendOrderToDrivers(Map<String, Object> param) {
        List<String> driverIDs = (List<String >) param.get("driverIDs");
        List<String> distances = (List<String>) param.get("distances");
        for (int i = 0; i < driverIDs.size(); i++) {
            String driverID = driverIDs.get(i);
            amqpAdmin.declareQueue(new Queue(QUEUE_PREFIX + driverID, true, false, false,
                    Map.of("x-queue-type", "quorum")));
            amqpAdmin.declareBinding(new Binding(QUEUE_PREFIX + driverID,
                    Binding.DestinationType.QUEUE, EXCHANGE, driverID, null));

            Message message = MessageBuilder.withBody("".getBytes())
                    .setHeader("orderID", MapUtil.getStr(param, "orderID"))
                    .setHeader("distance", distances.get(i))
                    .setHeader("startPlace", MapUtil.getStr(param, "startPlace"))
                    .setHeader("endPlace", MapUtil.getStr(param, "endPlace"))
                    .setHeader("expectsMileage", MapUtil.getInt(param, "expectsMileage"))
                    .setHeader("expectsTime", MapUtil.getInt(param, "expectsTime"))
                    .setHeader("expectsFee", MapUtil.getStr(param, "expectsFee"))
                    .setTimestamp(DateUtil.offsetMinute(new Date(), 15))
                    .build();
            rabbitTemplate.send(EXCHANGE, driverID, message);
        }
    }

    @Override
    public Map<String, Object> getDriverOrdersMessage(Map<String, Object> param) {
        List<Map<String, Object>> messages = new ArrayList<>();
        Message message = rabbitTemplate.receive(QUEUE_PREFIX + param.get("driverID"));
        while (message != null) {
            Map<String, Object> map = new HashMap<>();
            map.put("orderID", message.getMessageProperties().getHeaders().get("orderID"));
            map.put("distance", message.getMessageProperties().getHeaders().get("distance"));
            map.put("startPlace", message.getMessageProperties().getHeaders().get("startPlace"));
            map.put("endPlace", message.getMessageProperties().getHeaders().get("endPlace"));
            map.put("expectsMileage", message.getMessageProperties().getHeaders().get("expectsMileage"));
            map.put("expectsTime", message.getMessageProperties().getHeaders().get("expectsTime"));
            map.put("expectsFee", message.getMessageProperties().getHeaders().get("expectsFee"));
            messages.add(map);

            message = rabbitTemplate.receive(QUEUE_PREFIX + param.get("driverID"));
        }

        Map<String, Object> map = new HashMap<>();
        map.put("messages", messages);
        return map;
    }
}
