package com.jakob.product.enums;

import lombok.Getter;

@Getter
public enum ProductEnum {
    PRODUCT_NOT_EXIST(1, "商品不存在"),
    PRODUCT_STOCK_ERROR(2, "库存错误"),
    ;
    private Integer code;
    private String message;

    ProductEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
