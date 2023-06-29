package com.xiaoyao.hxds.driver.service;


import com.xiaoyao.hxds.common.dto.bff.driver.service.AuthenticateDTO;
import com.xiaoyao.hxds.common.dto.bff.driver.service.DriverRegisterDTO;
import com.xiaoyao.hxds.common.dto.driver.service.LoginDTO;

public interface DriverService {
    /**
     * 注册司机，返回司机主键值。
     */
    String register(DriverRegisterDTO dto);

    /**
     * 认证司机身份。
     */
    void authenticate(AuthenticateDTO dto);

    /**
     * 修改司机的人脸存档。
     */
    void archive(String driverID, String image);

    LoginDTO login(String code);
}
