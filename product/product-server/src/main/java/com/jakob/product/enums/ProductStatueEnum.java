package com.jakob.product.enums;

import lombok.Getter;

/**
 * 商品状态枚举类
 */
@Getter
public enum ProductStatueEnum {
    UP(0, "在架"), DOWN(1, "下架");

    private Integer code;

    private String message;

    ProductStatueEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
