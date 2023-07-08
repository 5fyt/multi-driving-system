package com.xiaoyao.hxds.bff.driver.client;

import com.xiaoyao.hxds.common.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient("hxds-order")
public interface OrderClient {
    @GetMapping("/order/driver-today-business")
    R driverTodayBusinessData(@RequestParam long driverID);

    @PostMapping("/order/accept")
    R acceptOrder(@RequestBody Map<String, Object> param);
}
