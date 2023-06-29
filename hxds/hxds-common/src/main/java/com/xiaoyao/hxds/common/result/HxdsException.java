package com.xiaoyao.hxds.common.result;

public class HxdsException extends RuntimeException {
    private final int code;

    public HxdsException(R r) {
        super((String) r.get(R.MESSAGE));
        this.code = (Integer) r.get(R.CODE);
    }

    public HxdsException(E e) {
        super(e.getMessage());
        this.code = e.getCode();
    }

    public HxdsException(String message) {
        super(message);
        this.code = 500;
    }

    public int getCode() {
        return code;
    }
}
