package com.xiaoyao.hxds.bff.driver.controller.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SearchDriverForm {
    @NotNull(message = "page不能为空")
    @Min(value = 1, message = "page不能小于1")
    private Integer page;

    @NotNull(message = "size不能为空")
    @Min(value = 1, message = "size不能小于1")
    private Integer size;

    @Pattern(regexp = "\\S+", message = "name不能为null或空字符串")
    private String name;

    @Pattern(regexp = "\\S+", message = "gender不能为null或空字符串")
    private String gender;

    @Pattern(regexp = "\\S+", message = "phone不能为null或空字符串")
    private String phone;

    @Pattern(regexp = "\\S+", message = "idCardNumber不能为null或空字符串")
    private String idCardNumber;

    private Integer status;

    private Integer accountStatus;
}
