package com.xiaoyao.hxds.order.controller;

import com.xiaoyao.hxds.common.result.R;
import com.xiaoyao.hxds.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/driver-today-business")
    public R driverTodayBusinessData(long driverID) {
        return R.ok(orderService.getDriverTodayBusinessData(driverID));
    }

    @PostMapping("/create")
    public R createOrder(@RequestBody Map<String, Object> param) {
        return R.ok(orderService.createOrder(param));
    }

    @PostMapping("/accept")
    public R acceptOrder(@RequestBody Map<String, Object> param) {
        return R.ok(orderService.acceptOrder(param));
    }

    @PostMapping("/is-accepted")
    public R isAccepted(@RequestBody Map<String, Object> param) {
        return R.ok(orderService.isOrderAccepted(param));
    }

    @PostMapping("/cancel")
    public R cancelOrder(@RequestBody Map<String, Object> param) {
        return R.ok(orderService.cancelOrder(param));
    }
}
