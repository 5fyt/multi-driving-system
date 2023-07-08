package com.xiaoyao.hxds.bff.driver.controller.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AcceptOrderForm {
    @NotBlank(message = "orderID不能为空")
    private String orderID;
}
