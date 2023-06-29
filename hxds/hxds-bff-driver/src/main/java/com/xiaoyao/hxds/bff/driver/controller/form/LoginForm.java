package com.xiaoyao.hxds.bff.driver.controller.form;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
public class LoginForm {
    @NotBlank(message = "code不能为空")
    private String code;
}
