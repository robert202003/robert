package com.github.cloud.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 参数校验异常
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ParamValidationException extends RuntimeException {

    protected int statusCode;

    public ParamValidationException(String message) {
        super(message);
    }

    public ParamValidationException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}