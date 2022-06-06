package com.example.exception;


public class exception extends RuntimeException {

    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public exception(int code, String message) {
        super(message);
        this.code = code;
    }

    public exception(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

}
