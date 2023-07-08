package com.xiaoyao.hxds.bff.customer.exception;

import cn.dev33.satoken.exception.NotLoginException;
import com.xiaoyao.hxds.common.result.E;
import com.xiaoyao.hxds.common.result.HxdsException;
import com.xiaoyao.hxds.common.result.R;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NotLoginException.class)
    public R exceptionHandler(NotLoginException e) {
        return R.fail(E.NOT_LOGIN);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R exceptionHandler(MethodArgumentNotValidException e) {
        return R.fail(E.ILLEGAL_ARGUMENT)
                .put(R.MESSAGE, e.getFieldErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public R exceptionHandler(ConstraintViolationException e) {
        ConstraintViolation<?> violation = e.getConstraintViolations().stream().toList().get(0);
        return R.fail(E.ILLEGAL_ARGUMENT)
                .put(R.MESSAGE, violation.getMessage());
    }

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
