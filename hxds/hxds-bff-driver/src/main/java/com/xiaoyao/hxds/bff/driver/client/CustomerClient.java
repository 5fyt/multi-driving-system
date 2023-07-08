package com.xiaoyao.hxds.bff.driver.client;

import com.xiaoyao.hxds.common.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient("hxds-customer")
public interface CustomerClient {
    @PostMapping("/customer/customer-info-in-order")
    R getCustomerInfoInOrder(@RequestBody Map<String, Object> param);
}
