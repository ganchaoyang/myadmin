package cn.gan.framework.facade;


import cn.gan.framework.constans.ErrorCode;

import java.io.Serializable;

/**
 * @author ganchaoyang
 */
public class BaseResponse<T> implements Serializable {

    private Integer code;

    private String message;

    private T data;

    private BaseResponse(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    private BaseResponse() {
    }

    private BaseResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
        this.data = null;
    }

    public static BaseResponse success() {
        return new BaseResponse(10000, "操作成功!");
    }

    public static <T> BaseResponse success(T data) {
        return new BaseResponse(10000, "操作成功！", data);
    }

    public static <T> BaseResponse success(T data, String message) {
        return new BaseResponse(10000, message, data);
    }

    public static BaseResponse error(Integer code, String message) {
        return new BaseResponse(code, message);
    }

    public static BaseResponse error(ErrorCode errorCode) {
        return new BaseResponse(errorCode.getCode(), errorCode.getMessage());
    }

    public static BaseResponse error(ErrorCode errorCode, String message) {
        return new BaseResponse(errorCode.getCode(), message);
    }


    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
