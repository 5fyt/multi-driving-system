package com.xiaoyao.hxds.common.dto.bff.driver.service;

import lombok.Data;

import java.util.List;

@Data
public class DeleteFilesDTO {
    private List<String> keys;
}
