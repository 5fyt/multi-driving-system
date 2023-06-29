package com.xiaoyao.hxds.common.result;

public class Assert {
    public static void notNull(Object o, E e) {
        if (o == null) {
            throw new HxdsException(e);
        }
    }

    public static void isTrue(boolean expression, E e) {
        if (!expression) {
            throw new HxdsException(e);
        }
    }

    public static R ok(R r) {
        Integer code = (Integer) r.get(R.CODE);
        if (code == null || code != R.SUCCESS_CODE) {
            throw new HxdsException(r);
        }
        return r;
    }

    public static void notBlank(String str, E e) {
        if (str == null || str.trim().length() == 0) {
            throw new HxdsException(e);
        }
    }
}
