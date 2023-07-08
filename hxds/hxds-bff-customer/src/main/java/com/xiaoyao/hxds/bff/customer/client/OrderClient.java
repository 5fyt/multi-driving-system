package com.xiaoyao.hxds.bff.customer.client;

import com.xiaoyao.hxds.common.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient("hxds-order")
public interface OrderClient {
    @PostMapping("/order/create")
    R createOrder(@RequestBody Map<String, Object> param);

    @PostMapping("/order/cancel")
    R cancelOrder(@RequestBody Map<String, Object> param);

    @PostMapping("/order/is-accepted")
    R isAccepted(@RequestBody Map<String, Object> param);
}
