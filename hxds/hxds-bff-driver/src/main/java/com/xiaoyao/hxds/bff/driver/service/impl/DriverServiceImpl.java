package com.xiaoyao.hxds.bff.driver.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import com.xiaoyao.hxds.bff.driver.client.DriverClient;
import com.xiaoyao.hxds.bff.driver.client.StorageClient;
import com.xiaoyao.hxds.bff.driver.controller.form.AuthenticateForm;
import com.xiaoyao.hxds.bff.driver.controller.form.LoginForm;
import com.xiaoyao.hxds.bff.driver.controller.form.RegisterForm;
import com.xiaoyao.hxds.bff.driver.service.DriverService;
import com.xiaoyao.hxds.common.dto.bff.driver.controller.LoginDTO;
import com.xiaoyao.hxds.common.dto.bff.driver.controller.RegisterDTO;
import com.xiaoyao.hxds.common.dto.bff.driver.service.ArchiveDTO;
import com.xiaoyao.hxds.common.dto.bff.driver.service.AuthenticateDTO;
import com.xiaoyao.hxds.common.dto.bff.driver.service.DeleteFilesDTO;
import com.xiaoyao.hxds.common.dto.bff.driver.service.DriverRegisterDTO;
import com.xiaoyao.hxds.common.result.Assert;
import com.xiaoyao.hxds.common.result.R;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {
    private final DriverClient driverClient;
    private final StorageClient storageClient;

    @Override
    public RegisterDTO register(RegisterForm form) {
        // 调用司机注册接口，获取司机的主键值。
        DriverRegisterDTO driverRegisterDTO = new DriverRegisterDTO();
        driverRegisterDTO.setCode(form.getCode());
        driverRegisterDTO.setNickname(form.getNickname());
        driverRegisterDTO.setPhoto(form.getPhoto());
        R r = Assert.ok(driverClient.register(driverRegisterDTO));
        String driverID = (String) r.get("id");

        StpUtil.login(driverID);
        // TODO 获取权限列表
        // TODO 修改登录类型，支持多账户认证。
        return new RegisterDTO(StpUtil.getTokenValue(), StpUtil.getPermissionList(driverID));
    }

    @Override
    public LoginDTO login(LoginForm form) {
        R r = Assert.ok(driverClient.login(form.getCode()));
        StpUtil.login(r.getStr("id"));

        LoginDTO dto = new LoginDTO();
        dto.setToken(StpUtil.getTokenValue());
        dto.setStatus(r.getInt("status"));
        dto.setArchive(r.getInt("archive"));
        return dto;
    }

    @Override
    public void authenticate(AuthenticateForm form) {
        AuthenticateDTO dto = new AuthenticateDTO();
        BeanUtil.copyProperties(form, dto);
        dto.setDriverID(StpUtil.getLoginIdAsLong());
        Assert.ok(driverClient.authenticate(dto));
    }

    @Override
    public void archive(String image) {
        ArchiveDTO dto = new ArchiveDTO();
        dto.setDriverID(StpUtil.getLoginIdAsString());
        dto.setImage(image);
        Assert.ok(driverClient.archive(dto));
    }

    @Override
    public String uploadAuthenticationPhoto(MultipartFile file) {
        R r = Assert.ok(storageClient.upload(file));
        return r.getStr("key");
    }

    @Override
    public void deleteAuthenticationPhoto(List<String> keys) {
        DeleteFilesDTO dto = new DeleteFilesDTO();
        dto.setKeys(keys);
        Assert.ok(storageClient.delete(dto));
    }

    @Override
    public void logout() {
        StpUtil.logout();
    }
}
