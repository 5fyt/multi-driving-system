package com.xiaoyao.hxds.bff.customer.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaIgnore;
import com.xiaoyao.hxds.bff.customer.controller.form.LoginForm;
import com.xiaoyao.hxds.bff.customer.controller.form.RegisterForm;
import com.xiaoyao.hxds.bff.customer.service.CustomerService;
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
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;

    @SaIgnore
    @PostMapping("/register")
    public R register(@Valid @RequestBody RegisterForm form) {
        return R.ok(customerService.register(form));
    }

    @SaIgnore
    @PostMapping("/login")
    public R login(@Valid @RequestBody LoginForm form) {
        return R.ok(customerService.login(form));
    }

    @PostMapping("/logout")
    public R logout() {
        customerService.logout();
        return R.ok();
    }
}
