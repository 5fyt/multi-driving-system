package com.xiaoyao.hxds.bff.driver.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaIgnore;
import com.xiaoyao.hxds.bff.driver.controller.form.*;
import com.xiaoyao.hxds.bff.driver.service.DriverService;
import com.xiaoyao.hxds.common.dto.bff.driver.controller.LoginDTO;
import com.xiaoyao.hxds.common.dto.bff.driver.controller.RegisterDTO;
import com.xiaoyao.hxds.common.result.R;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@SaCheckLogin
@RestController
@RequestMapping("/driver")
@RequiredArgsConstructor
public class DriverController {
    private final DriverService driverService;

    @SaIgnore
    @PostMapping("/register")
    public R register(@Valid @RequestBody RegisterForm form) {
        RegisterDTO dto = driverService.register(form);
        return R.ok()
                .put("token", dto.getToken())
                .put("permissions", dto.getPermissions());
    }

    @PostMapping("/upload-authentication-photo")
    public R uploadAuthenticationPhoto(MultipartFile file) {
        // TODO 将清理垃圾图片的任务转移到服务端
        String key = driverService.uploadAuthenticationPhoto(file);
        return R.ok()
                .put("key", key);
    }

    @PostMapping("/delete-authentication-photo")
    public R deleteAuthenticationPhoto(@Valid @RequestBody DeleteFilesForm form) {
        driverService.deleteAuthenticationPhoto(form.getKeys());
        return R.ok();
    }

    @PostMapping("/authenticate")
    public R authenticate(@Valid @RequestBody AuthenticateForm form) {
        driverService.authenticate(form);
        return R.ok();
    }

    @PostMapping("/archive")
    public R archive(@RequestBody ArchiveForm form) throws IOException {
        driverService.archive(form.getImage());
        return R.ok();
    }

    @SaIgnore
    @PostMapping("/login")
    public R login(@RequestBody LoginForm form) {
        LoginDTO dto = driverService.login(form);
        return R.ok()
                .put("token", dto.getToken())
                .put("status", dto.getStatus())
                .put("archive", dto.getArchive());
    }

    @PostMapping("/logout")
    public R logout() {
        driverService.logout();
        return R.ok();
    }
}
