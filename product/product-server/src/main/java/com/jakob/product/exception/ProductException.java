package com.jakob.product.exception;

import com.jakob.product.enums.ProductEnum;

public class ProductException extends RuntimeException {
    private Integer code;

    public ProductException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public ProductException(ProductEnum productEnum) {
        this(productEnum.getMessage(), productEnum.getCode());
    }
}
