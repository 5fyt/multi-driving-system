package com.xiaoyao.hxds.bff.customer.controller.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreateOrderForm {
    @NotBlank(message = "carPlate不能为空")
    private String carPlate;

    @NotBlank(message = "carType不能为空")
    private String carType;

    @NotBlank(message = "startCoordinate不能为空")
    @Pattern(regexp = "^-?([1-8]?[0-9]|90)\\.?\\d{0,6},-?([1-9]?[0-9]|1[0-7][0-9]|180)\\.?\\d{0,6}$",
            message = "startCoordinate格式不正确")
    private String startCoordinate;

    @NotBlank(message = "startPlace不能为空")
    private String startPlace;

    @NotBlank(message = "endCoordinate不能为空")
    @Pattern(regexp = "^-?([1-8]?[0-9]|90)\\.?\\d{0,6},-?([1-9]?[0-9]|1[0-7][0-9]|180)\\.?\\d{0,6}$",
            message = "startCoordinate格式不正确")
    private String endCoordinate;

    @NotBlank(message = "endPlace不能为空")
    private String endPlace;

}
