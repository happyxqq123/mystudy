package com.apigateway.common.exception;

public class BaseException extends RuntimeException{

    private int code;

    public BaseException(){
        super();
    }

    public BaseException(String message,Throwable cause){
        super(message,cause);
    }

    public BaseException (String message, int code){
        super(message);
        this.code = code;
    }

    public BaseException(String message,int code,Throwable cause){
        super(message,cause);
        this.code = code;
    }


    public BaseException(String message){
        super(message);
    }

    public BaseException(Throwable cause){
        super(cause);
    }

}
