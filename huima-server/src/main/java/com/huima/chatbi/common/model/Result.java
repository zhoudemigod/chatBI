package com.huima.chatbi.common.model;

import com.huima.chatbi.common.enums.ResultCodeEnum;

import java.time.LocalDateTime;

public class Result<T> {

    private String code;
    private String message;
    private T data;
    private boolean success;
    private LocalDateTime timestamp;

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        result.setData(data);
        result.setSuccess(true);
        result.setTimestamp(LocalDateTime.now());
        return result;
    }

    public static <T> Result<T> failure(ResultCodeEnum resultCodeEnum, String message) {
        Result<T> result = new Result<>();
        result.setCode(resultCodeEnum.getCode());
        result.setMessage(message);
        result.setSuccess(false);
        result.setTimestamp(LocalDateTime.now());
        return result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}

