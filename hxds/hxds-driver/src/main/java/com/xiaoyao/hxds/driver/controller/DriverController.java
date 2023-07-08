package com.xiaoyao.hxds.driver.controller;

import com.xiaoyao.hxds.common.result.R;
import com.xiaoyao.hxds.driver.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/driver")
@RequiredArgsConstructor
public class DriverController {
    private final DriverService driverService;

    @PostMapping("/register")
    public R register(@RequestBody Map<String, Object> param) {
        long driverID = driverService.register(param);
        return R.ok().put("id", driverID);
    }

    @PostMapping("/authenticate")
    public R authenticate(@RequestBody Map<String, Object> param) {
        driverService.authenticate(param);
        return R.ok();
    }

    @PostMapping("/archive")
    public R archive(@RequestBody Map<String, Object> param) {
        driverService.archive(param);
        return R.ok();
    }

    @PostMapping("/login")
    public R login(String code) {
        return R.ok(driverService.login(code));
    }

    @GetMapping("/profile")
    public R profile(long driverID) {
        return R.ok(driverService.profile(driverID));
    }

    @GetMapping("/settings")
    public R settings(long driverID) {
        return R.ok(driverService.settings(driverID));
    }

    @PostMapping("/search-drivers")
    public R searchDriver(@RequestBody Map<String, Object> param) {
        return R.ok(driverService.searchDrivers(param));
    }

    @GetMapping("/authenticate-info")
    public R authenticateInfo(long driverID) {
        return R.ok(driverService.getAuthenticateInfo(driverID));
    }
}
