package com.xiaoyao.hxds.bff.driver.service;

import com.xiaoyao.hxds.bff.driver.controller.form.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface DriverService {
    /**
     * 注册司机
     */
    Map<String, Object> register(RegisterForm form);

    Map<String, Object> login(LoginForm form);

    /**
     * 司机认证
     */
    void authenticate(AuthenticateForm form);

    /**
     * 上传人脸照片
     */
    void archive(ArchiveForm form);

    /**
     * 上传认证图片
     */
    Map<String, Object> uploadAuthenticationPhoto(MultipartFile file);

    /**
     * 删除认证图片
     */
    void deleteAuthenticationPhoto(DeleteFilesForm form);

    /**
     * 退出登录
     */
    void logout();

    /**
     * 获取司机的个人信息。
     */
    Map<String, Object> profile();

    /**
     * 获取司机的工作台数据。
     */
    Map<String, Object> getWorkbenchData();

    /**
     * 搜索司机
     */
    Map<String, Object> searchDrivers(SearchDriverForm form);

    /**
     * 获取司机的认证信息。
     */
    Map<String, Object> getAuthenticationInfo();

    void updateLocation(UpdateLocationForm form);

    void deleteLocation();

    Map<String, Object> getOrderMessages();

    Map<String, Object> acceptOrder(AcceptOrderForm form);
}
