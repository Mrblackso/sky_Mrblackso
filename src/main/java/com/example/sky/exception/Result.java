package com.example.sky.exception;

import lombok.Data;

@Data
public class Result {
    private String code;
    private String message;
    private Object data;

    public static Result success(Object data) {
        Result result = success();
        result.setData(data);
        return result;
    }
    public static Result success() {
        Result result = new Result();
        result.setCode("200");
        result.setMessage("操作成功");
        result.setData(null);
        return result;
    }
    public static Result error() {
        Result result = new Result();
        result.setCode("500");
        result.setMessage("系统错误");
        return result;
    }
    public static Result error(String message) {
        Result result = new Result();
        result.setCode("500");
        result.setMessage(message);
        return result;
    }
}
