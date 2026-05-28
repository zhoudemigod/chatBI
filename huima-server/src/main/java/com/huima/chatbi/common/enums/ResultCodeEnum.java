package com.huima.chatbi.common.enums;

public enum ResultCodeEnum {

    SUCCESS("00000", "成功"),
    BAD_REQUEST("A0400", "请求参数错误"),
    BUSINESS_ERROR("B0001", "业务处理失败"),
    SYSTEM_ERROR("B9999", "系统繁忙，请稍后重试");

    private final String code;
    private final String message;

    ResultCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

