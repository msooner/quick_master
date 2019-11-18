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
    private String massage;

    public ResponseResult() {}

    public ResponseResult(int code, T data, String massage) {
        this.code = code;
        this.data = data;
        this.massage = massage;
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

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    @Override
    public String toString() {
        return "ResponseResult{" +
                "massage=" + massage +
                ", data=" + data +
                ", code=" + code +
                '}';
    }
}
