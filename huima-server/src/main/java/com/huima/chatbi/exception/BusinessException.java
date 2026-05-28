package com.huima.chatbi.exception;

import com.huima.chatbi.common.enums.ResultCodeEnum;

public class BusinessException extends RuntimeException {

    private final ResultCodeEnum resultCodeEnum;

    public BusinessException(String message) {
        super(message);
        this.resultCodeEnum = ResultCodeEnum.BUSINESS_ERROR;
    }

    public BusinessException(ResultCodeEnum resultCodeEnum, String message) {
        super(message);
        this.resultCodeEnum = resultCodeEnum;
    }

    public ResultCodeEnum getResultCodeEnum() {
        return resultCodeEnum;
    }
}

