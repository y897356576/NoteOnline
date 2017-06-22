package com.stone.core.exception;

/**
 * Created by 石头 on 2017/6/22.
 */
public class MyException extends RuntimeException {

    //错误代码
    private String code;

    public String getCode() {
        return code;
    }

    public MyException(String message){
        super(message);
    }

    public MyException(String code, String message){
        super(message);
        this.code = code;
    }

}
