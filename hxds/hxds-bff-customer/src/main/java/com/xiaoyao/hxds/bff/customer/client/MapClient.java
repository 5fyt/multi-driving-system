package com.xiaoyao.hxds.bff.customer.client;

import com.xiaoyao.hxds.common.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient("hxds-map")
public interface MapClient {
    @PostMapping("/map/calculate-driving-line")
    R calculateDrivingLine(@RequestBody Map<String, Object> param);

    @PostMapping("/map/search-nearby-driver")
    R searchNearbyDriver(@RequestBody Map<String, Object> param);
}
