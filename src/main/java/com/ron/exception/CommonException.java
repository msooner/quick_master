package com.ron.exception;

/**
 * 运行期异常
 *
 * @auther Ron
 * @date 2019/10/8
 */
public class CommonException extends RuntimeException {

    public CommonException(String message) {
        super(message);
    }

    public CommonException(String message, Throwable cause) {
        super(message, cause);
    }
}
