package com.jakob.product.VO;

import lombok.Data;

/**
 * http请求返回的对象
 *
 * @param <T>
 */
@Data
public class ResultVO<T> {

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 具体内容
     */
    private T data;

    public ResultVO(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResultVO(Integer code, String msg) {
        this(code, msg, null);
    }

    public ResultVO(T data) {
        this(0, "成功", data);
    }

    public ResultVO() {
        this(0, "成功", null);
    }

    public static <T> ResultVO<T> success(T data) {
        return new ResultVO<>(data);
    }
}
