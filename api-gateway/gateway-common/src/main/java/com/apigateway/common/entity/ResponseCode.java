package com.apigateway.common.entity;


import io.netty.handler.codec.http.HttpResponseStatus;
import lombok.Getter;

@Getter
public enum ResponseCode{

    SUCCESS(HttpResponseStatus.OK,0,"成功"),
    INTERNAL_ERROR(HttpResponseStatus.INTERNAL_SERVER_ERROR,1000,"网关内部错误"),

    SERVICE_UNAVAILABLE(HttpResponseStatus.SERVICE_UNAVAILABLE,2000,"服务暂时不可用"),

    REQUEST_PARSE_ERROR(HttpResponseStatus.BAD_REQUEST,10000,"请求解析错误"),
    REQUEST_PARSE_ERROR_NO_UNIQUEID(HttpResponseStatus.BAD_REQUEST,10001,"错误的请求"),
    PATH_NO_MATCHED(HttpResponseStatus.NOT_FOUND,10002,"没有找到匹配的路径"),
    SERVICE_DEFINITION_NOT_FOUND(HttpResponseStatus.NOT_FOUND,10003,"未找到对应的服务"),
    SERVICE_INVOKER_NOT_FOUND(HttpResponseStatus.NOT_FOUND,10004,"服务调用未找到"),
    SERVICE_INSTANCE_NOT_FOUND(HttpResponseStatus.NOT_FOUND,10005,"服务调用未找到"),
    FILTER_CONFIG_PARSE_ERROR(HttpResponseStatus.INTERNAL_SERVER_ERROR,10006,"服务内部错误"),
    REQUEST_TIMEOUT(HttpResponseStatus.GATEWAY_TIMEOUT,10007,"网关超时"),
    HTTP_RESPONSE_ERROR(HttpResponseStatus.INTERNAL_SERVER_ERROR,10030,"请求返回错误，内部错误");
    private HttpResponseStatus status;

    private int code;

    private String message;

    ResponseCode(HttpResponseStatus httpResponseStatus, int code, String message) {
        this.status = httpResponseStatus;
        this.code = code;
        this.message = message;
    }
}
