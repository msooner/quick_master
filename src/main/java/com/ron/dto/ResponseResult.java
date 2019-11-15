package com.ron.dto;

/**
 * 封装统一的JSON返回结果
 *
 * @auther Ron
 * @date 2019/10/8
 */
public class ResponseResult<T> {

    private boolean success;
    private T data;
    private int code;

    public ResponseResult() {}

    public ResponseResult(boolean success, T data, int code) {
        this.success = success;
        this.data = data;
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "ResponseResult{" +
                "success=" + success +
                ", data=" + data +
                ", code=" + code +
                '}';
    }
}
