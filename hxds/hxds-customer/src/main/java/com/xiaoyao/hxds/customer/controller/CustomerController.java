package com.xiaoyao.hxds.customer.controller;

import com.xiaoyao.hxds.common.result.R;
import com.xiaoyao.hxds.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/register")
    public R register(@RequestBody Map<String, Object> param) {
        return R.ok(customerService.register(param));
    }

    @PostMapping("/login")
    public R login(@RequestBody Map<String, Object> param) {
        return R.ok(customerService.login(param));
    }

    @PostMapping("/customer-info-in-order")
    public R getCustomerInfoInOrder(@RequestBody Map<String, Object> param) {
        return R.ok(customerService.getCustomerInfoInOrder(param));
    }
}
