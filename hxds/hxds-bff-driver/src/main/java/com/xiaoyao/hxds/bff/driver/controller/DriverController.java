package com.xiaoyao.hxds.bff.driver.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaIgnore;
import com.xiaoyao.hxds.bff.driver.controller.form.*;
import com.xiaoyao.hxds.bff.driver.service.DriverService;
import com.xiaoyao.hxds.common.result.R;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@SaCheckLogin
@RestController
@RequestMapping("/driver")
@RequiredArgsConstructor
public class DriverController {
    private final DriverService driverService;

    @SaIgnore
    @PostMapping("/register")
    public R register(@Valid @RequestBody RegisterForm form) {
        return R.ok(driverService.register(form));
    }

    @PostMapping("/upload-authentication-photo")
    public R uploadAuthenticationPhoto(MultipartFile file) {
        // TODO 将清理垃圾图片的任务转移到服务端
        return R.ok(driverService.uploadAuthenticationPhoto(file));
    }

    @PostMapping("/delete-authentication-photo")
    public R deleteAuthenticationPhoto(@Valid @RequestBody DeleteFilesForm form) {
        driverService.deleteAuthenticationPhoto(form);
        return R.ok();
    }

    @PostMapping("/authenticate")
    public R authenticate(@Valid @RequestBody AuthenticateForm form) {
        driverService.authenticate(form);
        return R.ok();
    }

    @PostMapping("/archive")
    public R archive(@Valid @RequestBody ArchiveForm form) {
        driverService.archive(form);
        return R.ok();
    }

    @SaIgnore
    @PostMapping("/login")
    public R login(@Valid @RequestBody LoginForm form) {
        return R.ok(driverService.login(form));
    }

    @PostMapping("/logout")
    public R logout() {
        driverService.logout();
        return R.ok();
    }

    @GetMapping("/profile")
    private R profile() {
        return R.ok(driverService.profile());
    }

    @GetMapping("/workbench")
    public R workbench() {
        return R.ok(driverService.getWorkbenchData());
    }

    @PostMapping("/search-drivers")
    public R searchDrivers(@Valid @RequestBody SearchDriverForm form) {
        return R.ok(driverService.searchDrivers(form));
    }

    @GetMapping("/authentication-info")
    public R authenticationInfo() {
        return R.ok(driverService.getAuthenticationInfo());
    }

    @PostMapping("/update-location")
    public R updateLocation(@Valid @RequestBody UpdateLocationForm form) {
        driverService.updateLocation(form);
        return R.ok();
    }

    @PostMapping("/delete-location")
    public R deleteLocation() {
        driverService.deleteLocation();
        return R.ok();
    }

    @GetMapping("/order-messages")
    public R getOrderMessages() {
        return R.ok(driverService.getOrderMessages());
    }

    @PostMapping("/accept")
    public R acceptOrder(@Valid @RequestBody AcceptOrderForm form) {
        return R.ok(driverService.acceptOrder(form));
    }
}
