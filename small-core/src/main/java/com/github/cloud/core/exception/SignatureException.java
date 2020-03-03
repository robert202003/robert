package com.github.cloud.core.exception;

public class SignatureException extends RuntimeException {

    private static final long serialVersionUID = 3655050728585279326L;

    private int code = 500;

    public SignatureException() {

    }

    public SignatureException(String msg) {
        super(msg);
    }

    public SignatureException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    public SignatureException(int code, String msg, Throwable cause) {
        super(msg, cause);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
