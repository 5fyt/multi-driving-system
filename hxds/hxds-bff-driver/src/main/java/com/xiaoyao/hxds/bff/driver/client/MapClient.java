package com.xiaoyao.hxds.bff.driver.client;

import com.xiaoyao.hxds.common.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient("hxds-map")
public interface MapClient {
    @PostMapping("/map/update-driver-location")
    R updateDriverLocation(@RequestBody Map<String, Object> param);

    @PostMapping("/map/delete-driver-location")
    R deleteDriverLocation(@RequestBody Map<String, Object> param);
}
