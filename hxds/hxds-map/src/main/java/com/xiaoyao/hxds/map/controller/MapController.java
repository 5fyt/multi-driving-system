package com.xiaoyao.hxds.map.controller;

import com.xiaoyao.hxds.common.result.R;
import com.xiaoyao.hxds.map.service.MapService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/map")
public class MapController {
    private final MapService mapService;

    @PostMapping("/calculate-driving-line")
    public R calculateDrivingLine(@RequestBody Map<String, Object> param) {
        return R.ok(mapService.calculateDrivingLine(param));
    }

    @PostMapping("/update-driver-location")
    public R updateDriverLocation(@RequestBody Map<String, Object> param) {
        mapService.updateDriverLocation(param);
        return R.ok();
    }

    @PostMapping("/delete-driver-location")
    public R deleteDriverLocation(@RequestBody Map<String, Object> param) {
        mapService.deleteDriverLocation(param);
        return R.ok();
    }

    @PostMapping("/search-nearby-driver")
    public R searchNearbyDriver(@RequestBody Map<String, Object> param) {
        return R.ok(mapService.searchNearbyDriver(param));
    }
}
