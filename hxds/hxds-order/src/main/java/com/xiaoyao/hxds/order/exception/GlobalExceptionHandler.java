package com.xiaoyao.hxds.order.exception;

import com.xiaoyao.hxds.common.result.HxdsException;
import com.xiaoyao.hxds.common.result.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(HxdsException.class)
    public R exceptionHandler(HxdsException e) {
        return R.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler
    public R exceptionHandler(Exception e) {
        if (log.isWarnEnabled()) {
            log.warn("处理未知异常", e);
        }
        return R.error();
    }
}
