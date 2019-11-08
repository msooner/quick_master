package com.ron.exception;

/**
 * 秒杀相关的异常
 *
 * @auther Ron
 * @date 2019/10/8
 */
public class SeckillException extends RuntimeException {

    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
