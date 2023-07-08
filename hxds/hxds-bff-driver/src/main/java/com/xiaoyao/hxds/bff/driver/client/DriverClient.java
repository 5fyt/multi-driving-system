package com.xiaoyao.hxds.bff.driver.client;

import com.xiaoyao.hxds.common.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient("hxds-driver")
public interface DriverClient {
    @PostMapping("/driver/register")
    R register(@RequestBody Map<String, Object> prama);

    @PostMapping("/driver/authenticate")
    R authenticate(@RequestBody Map<String, Object> param);

    @PostMapping(value = "/driver/archive")
    R archive(@RequestBody Map<String, Object> param);

    @PostMapping("/driver/login")
    R login(@RequestParam String code);

    @GetMapping("/driver/profile")
    R profile(@RequestParam long driverID);

    @GetMapping("/driver/settings")
    R settings(@RequestParam long driverID);

    @PostMapping("/driver/search-drivers")
    R searchDrivers(@RequestBody Map<String, Object> param);

    @GetMapping("/driver/authenticate-info")
    R authenticateInfo(@RequestParam long driverID);
}
