package com.xiaoyao.hxds.bff.driver.controller.form;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
public class RegisterForm {
    @NotBlank(message = "授权码不能为空")
    private String code;

    @NotBlank(message = "昵称不能为空")
    private String nickname;

    @NotBlank(message = "头像不能为空")
    private String photo;
}
