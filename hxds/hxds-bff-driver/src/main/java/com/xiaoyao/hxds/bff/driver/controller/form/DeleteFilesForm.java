package com.xiaoyao.hxds.bff.driver.controller.form;

import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class DeleteFilesForm {
    @NotEmpty(message = "文件标识不能为空")
    private List<String> keys;
}
