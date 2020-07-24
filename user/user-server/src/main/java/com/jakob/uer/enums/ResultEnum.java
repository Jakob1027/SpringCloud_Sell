package com.jakob.uer.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    LOGIN_FAILED(1, "登录失败"),
    ROLE_ERROR(2, "角色错误");

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
