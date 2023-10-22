package com.apigateway.common.exception;

import com.apigateway.common.entity.ResponseCode;

public class ResponseException extends BaseException{

    public ResponseException(){
        this(ResponseCode.INTERNAL_ERROR);
    }

    public ResponseException(ResponseCode responseCode){
        super(responseCode.getMessage(),responseCode.getCode());
    }

    public ResponseException(Throwable cause, ResponseCode responseCode){
        super(responseCode.getMessage(),responseCode.getCode(),cause);
    }
}
