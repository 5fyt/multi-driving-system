package com.xiaoyao.hxds.bff.driver.service;

import com.xiaoyao.hxds.bff.driver.controller.form.AuthenticateForm;
import com.xiaoyao.hxds.bff.driver.controller.form.LoginForm;
import com.xiaoyao.hxds.bff.driver.controller.form.RegisterForm;
import com.xiaoyao.hxds.common.dto.bff.driver.controller.LoginDTO;
import com.xiaoyao.hxds.common.dto.bff.driver.controller.RegisterDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface DriverService {
    /**
     * 注册司机
     */
    RegisterDTO register(RegisterForm form);

    LoginDTO login(LoginForm form);

    /**
     * 司机认证
     */
    void authenticate(AuthenticateForm form);

    /**
     * 上传人脸照片
     */
    void archive(String image) throws IOException;

    /**
     * 上传认证图片
     */
    String uploadAuthenticationPhoto(MultipartFile file);

    /**
     * 删除认证图片
     */
    void deleteAuthenticationPhoto(List<String> keys);

    /**
     * 退出登录
     */
    void logout();
}
