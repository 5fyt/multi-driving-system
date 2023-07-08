package com.xiaoyao.hxds.bff.customer.service;

import com.xiaoyao.hxds.bff.customer.controller.form.CancelOrderForm;
import com.xiaoyao.hxds.bff.customer.controller.form.CreateOrderForm;
import com.xiaoyao.hxds.bff.customer.controller.form.IsAcceptForm;

import java.util.Map;

public interface OrderService {
    void createOrder(CreateOrderForm form);

    Map<String, Object> cancelOrder(CancelOrderForm form);

    Map<String, Object> isAccepted(IsAcceptForm form);
}
