package com.xiaoyao.hxds.driver.service;


import java.util.Map;

public interface DriverService {
    /**
     * 注册司机，返回司机主键值。
     */
    long register(Map<String, Object> param);

    /**
     * 提交司机的身份认证信息，司机可以在未认证的情况下提交认证信息，
     * 也可以在已认证的情况下修改认证信息。
     */
    void authenticate(Map<String, Object> param);

    /**
     * 上传司机的人脸存档，只有在上传了认证信息后才可以上传人脸存档。
     */
    void archive(Map<String, Object> param);

    /**
     * 登录
     */
    Map<String, Object> login(String code);

    /**
     * 获取司机的个人信息。
     */
    Map<String, Object> profile(long driverID);

    /**
     * 获取司机的设置信息。
     */
    Map<String, Object> settings(long driverID);

    /**
     * 根据条件搜索司机。
     */
    Map<String, Object> searchDrivers(Map<String, Object> param);

    /**
     * 获取司机的认证信息。
     */
    Map<String, Object> getAuthenticateInfo(long driverID);
}
