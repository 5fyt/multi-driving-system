package com.xiaoyao.hxds.bff.customer.service;

import com.xiaoyao.hxds.bff.customer.controller.form.LoginForm;
import com.xiaoyao.hxds.bff.customer.controller.form.RegisterForm;

import java.util.Map;

public interface CustomerService {
    Map<String, Object> register(RegisterForm form);

    Map<String, Object> login(LoginForm form);

    void logout();
}
