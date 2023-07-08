package com.xiaoyao.hxds.bff.customer.controller.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginForm {
    @NotBlank(message = "code不能为空")
    private String code;
}
