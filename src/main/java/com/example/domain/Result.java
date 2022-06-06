package com.example.domain;


import lombok.Data;

@Data
public class Result {
    private Object data;
    private int code;
    private String msg;

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(Object data) {
        this.data = data;
    }

    public Result(Object data, int code) {
        this.data = data;
        this.code = code;
    }

    public Result(Object data, int code, String msg) {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    public Result(int code, Object data,String msg) {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }
}
