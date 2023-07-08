package com.xiaoyao.hxds.bff.customer.controller.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CarAddForm {
    @NotBlank(message = "carType不能为空")
    private String carType;

    @NotBlank(message = "carPlate不能为空")
    private String carPlate;
}
