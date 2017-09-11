package com.stone.demo.sendDing.exception;

/**
 * Created by sunqian on 2017/3/8.
 * 发送钉钉失败
 */
public class DingException extends RuntimeException {

    public DingException(String message) {
        super(message);
    }

    public DingException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
