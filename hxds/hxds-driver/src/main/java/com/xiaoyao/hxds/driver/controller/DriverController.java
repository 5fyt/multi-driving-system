package com.xiaoyao.hxds.driver.controller;

import com.xiaoyao.hxds.common.dto.bff.driver.service.ArchiveDTO;
import com.xiaoyao.hxds.common.dto.bff.driver.service.AuthenticateDTO;
import com.xiaoyao.hxds.common.dto.bff.driver.service.DriverRegisterDTO;
import com.xiaoyao.hxds.common.dto.driver.service.LoginDTO;
import com.xiaoyao.hxds.common.result.R;
import com.xiaoyao.hxds.driver.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/driver")
@RequiredArgsConstructor
public class DriverController {
    private final DriverService driverService;

    @PostMapping("/register")
    public R register(@RequestBody DriverRegisterDTO dto) {
        String driverID = driverService.register(dto);
        return R.ok()
                .put("id", driverID);
    }

    @PostMapping("/authenticate")
    public R authenticate(@RequestBody AuthenticateDTO dto) {
        driverService.authenticate(dto);
        return R.ok();
    }

    @PostMapping("/archive")
    public R archive(@RequestBody ArchiveDTO dto) {
        driverService.archive(dto.getDriverID(), dto.getImage());
        return R.ok();
    }

    @PostMapping("/login")
    public R login(String code) {
        LoginDTO dto = driverService.login(code);
        return R.ok()
                .put("id", dto.getId())
                .put("status", dto.getStatus())
                .put("archive", dto.isArchive() ? 1 : 0);
    }
}
