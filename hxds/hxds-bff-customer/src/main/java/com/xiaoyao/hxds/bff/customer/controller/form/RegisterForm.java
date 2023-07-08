package com.xiaoyao.hxds.bff.customer.controller.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RegisterForm {
    @NotBlank(message = "code不能为空")
    private String code;

    @NotBlank(message = "nickname不能为空")
    private String nickname;

    @NotBlank(message = "gender不能为空")
    @Pattern(regexp = "^男$|^女$|^无$", message = "gender只能为男、女或无")
    private String gender;

    @NotBlank(message = "photo不能为空")
    private String photo;

    @NotBlank(message = "phone不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "phone格式不正确")
    private String phone;
}
