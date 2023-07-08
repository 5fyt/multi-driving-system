package com.xiaoyao.hxds.message.controller;

import com.xiaoyao.hxds.common.result.R;
import com.xiaoyao.hxds.message.service.DriverOrderMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/message")
public class MessageController {
    private final DriverOrderMessageService driverOrderMessageService;

    @PostMapping("/send-message-to-drivers")
    public R sendMessageToDrivers(@RequestBody Map<String, Object> param) {
        driverOrderMessageService.sendOrderToDrivers(param);
        return R.ok();
    }

    @PostMapping("/get-driver-orders-message")
    public R getDriverOrdersMessage(@RequestBody Map<String, Object> param) {
        return R.ok(driverOrderMessageService.getDriverOrdersMessage(param));
    }
}
