package com.xiaoyao.hxds.bff.driver.client;

import com.xiaoyao.hxds.common.dto.bff.driver.service.ArchiveDTO;
import com.xiaoyao.hxds.common.dto.bff.driver.service.AuthenticateDTO;
import com.xiaoyao.hxds.common.dto.bff.driver.service.DriverRegisterDTO;
import com.xiaoyao.hxds.common.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("hxds-driver")
public interface DriverClient {
    @PostMapping("/driver/register")
    R register(@RequestBody DriverRegisterDTO dto);

    @PostMapping("/driver/authenticate")
    R authenticate(@RequestBody AuthenticateDTO dto);

    @PostMapping("/driver/archive")
    R archive(@RequestBody ArchiveDTO dto);

    @PostMapping("/driver/login")
    R login(@RequestParam String code);
}
