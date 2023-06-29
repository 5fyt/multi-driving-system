package com.xiaoyao.hxds.common.result;

public enum E {
    ERROR(40000, "操作错误"),
    ILLEGAL_ARGUMENT(40001, "非法参数"),
    OPENID_EXIST(40002, "openid已存在"),
    AUTHENTICATED(40003, "已认证"),
    NOT_AUTHENTICATED(40004, "未认证"),
    NOT_REGISTERED(40005, "未注册"),
    NOT_LOGIN(40006, "未登录"),
    ;

    private final int code;
    private final String message;

    E(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
