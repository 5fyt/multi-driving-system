package com.xiaoyao.hxds.common.dto.bff.driver.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RegisterDTO {
    /**
     * 登录token
     */
    private String token;

    /**
     * 权限列表
     */
    private List<String> permissions;
}
