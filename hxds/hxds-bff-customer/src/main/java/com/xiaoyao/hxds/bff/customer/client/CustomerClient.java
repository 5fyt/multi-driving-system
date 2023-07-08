package com.xiaoyao.hxds.bff.customer.client;

import com.xiaoyao.hxds.common.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient("hxds-customer")
public interface CustomerClient {
    @PostMapping("/customer/register")
    R register(@RequestBody Map<String, Object> param);

    @PostMapping("/customer/login")
    R login(@RequestBody Map<String, Object> param);

    @PostMapping("/customer/car/add")
    R addCar(@RequestBody Map<String, Object> param);

    @PostMapping("/customer/car/delete")
    R deleteCar(@RequestBody Map<String, Object> param);

    @PostMapping("/customer/car/list")
    R listCars(@RequestBody Map<String, Object> param);
}
