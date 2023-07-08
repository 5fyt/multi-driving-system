package com.xiaoyao.hxds.common.result;

import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

public class R extends HashMap<String, Object> {
    public static final String CODE = "code";
    public static final String MESSAGE = "message";
    public static final String DATA = "data";
    public static final int SUCCESS_CODE = 200;
    public static final String SUCCESS_MESSAGE = "操作成功";

    @NonNull
    @Override
    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public static R ok() {
        return new R()
                .put(CODE, SUCCESS_CODE)
                .put(MESSAGE, SUCCESS_MESSAGE);
    }

    public static R ok(Map<String, Object> map) {
        R r = ok();
        r.putAll(map);
        return r;
    }

    public static R fail(int code, String message) {
        return new R()
                .put(CODE, code)
                .put(MESSAGE, message);
    }

    public static R fail(E e) {
        return new R()
                .put(CODE, e.getCode())
                .put(MESSAGE, e.getMessage());
    }

    public static R error() {
        return new R()
                .put(CODE, E.ERROR.getCode())
                .put(MESSAGE, E.ERROR.getMessage());
    }
}
