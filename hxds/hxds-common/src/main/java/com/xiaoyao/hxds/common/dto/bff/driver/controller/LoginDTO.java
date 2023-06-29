package com.xiaoyao.hxds.common.dto.bff.driver.controller;

import lombok.Data;

@Data
public class LoginDTO {
    private String token;
    private int status;
    private int archive;
}
