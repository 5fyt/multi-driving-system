package com.xiaoyao.hxds.bff.driver.controller.form;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
public class ArchiveForm {
    @NotBlank(message = "image不能为空")
    private String image;
}
