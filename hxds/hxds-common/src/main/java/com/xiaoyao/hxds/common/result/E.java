package com.xiaoyao.hxds.common.result;

public enum E {
    ERROR(40000, "操作错误"),
    ILLEGAL_ARGUMENT(40001, "非法参数"),
    OPENID_EXIST(40002, "openid已存在"),
    AUTHENTICATING(40003, "正在审核中，无法修改认证信息"),
    NOT_AUTHENTICATED(40004, "未上传认证信息，请先上传认证信息再上传人脸存档"),
    NOT_REGISTERED(40005, "未注册"),
    NOT_LOGIN(40006, "未登录"),
    NO_DRIVER_NEARBY(40007, "附近没有适合接单的司机"),
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
