package com.xiaoyao.hxds.bff.driver.controller.form;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UpdateLocationForm {
    @NotBlank(message = "longitude不能为空")
    @Pattern(regexp = "^-?([1-9]?[0-9]|1[0-7][0-9]|180)\\.?\\d{0,6}$", message = "longitude格式不正确")
    private String longitude;

    @NotBlank(message = "latitude不能为空")
    @Pattern(regexp = "^-?([1-8]?[0-9]|90)\\.?\\d{0,6}$", message = "latitude格式不正确")
    private String latitude;

    private String orientation;

    @NotNull(message = "orderDistance不能为空")
    private Integer orderDistance;

    @NotNull(message = "rangeDistance不能为空")
    @Max(value = 5, message = "rangeDistance不能大于5")
    private Integer rangeDistance;
}
