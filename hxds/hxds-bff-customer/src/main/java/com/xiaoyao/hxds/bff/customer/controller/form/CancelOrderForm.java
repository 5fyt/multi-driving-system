package com.xiaoyao.hxds.bff.customer.controller.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CancelOrderForm {
    @NotBlank(message = "orderId不能为空")
    private String orderId;
}
