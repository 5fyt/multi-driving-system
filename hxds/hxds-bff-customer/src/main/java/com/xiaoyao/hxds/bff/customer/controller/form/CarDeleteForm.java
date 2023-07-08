package com.xiaoyao.hxds.bff.customer.controller.form;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CarDeleteForm {
    @NotNull(message = "id不能为空")
    private Long id;
}
