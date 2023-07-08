package com.xiaoyao.hxds.bff.customer.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.xiaoyao.hxds.bff.customer.controller.form.CancelOrderForm;
import com.xiaoyao.hxds.bff.customer.controller.form.CreateOrderForm;
import com.xiaoyao.hxds.bff.customer.controller.form.IsAcceptForm;
import com.xiaoyao.hxds.bff.customer.service.OrderService;
import com.xiaoyao.hxds.common.result.R;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SaCheckLogin
@RestController
@RequiredArgsConstructor
@RequestMapping("/customer/order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/create")
    public R createOrder(@Valid @RequestBody CreateOrderForm form) {
        orderService.createOrder(form);
        return R.ok();
    }

    @PostMapping("/cancel")
    public R cancelOrder(@Valid @RequestBody CancelOrderForm form) {
        return R.ok(orderService.cancelOrder(form));
    }

    @PostMapping("/is-accepted")
    public R isAccepted(@Valid @RequestBody IsAcceptForm form) {
        return R.ok(orderService.isAccepted(form));
    }
}
