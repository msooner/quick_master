package com.ron.dto;

/**
 * 封装统一的JSON返回结果格式
 *
 * @auther Ron
 * @date 2019/10/8
 */
public class ResponseResult<T> {

    private int code;
    private T data;
    private String message;

    public ResponseResult() {}

    public ResponseResult(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResponseResult{" +
                "message=" + message +
                ", data=" + data +
                ", code=" + code +
                '}';
    }
}
