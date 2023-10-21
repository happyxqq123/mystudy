package com.apigateway.core.response;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.apigateway.common.entity.ResponseCode;
import com.apigateway.common.util.JSONTool;
import io.netty.handler.codec.http.*;
import lombok.Data;
import org.asynchttpclient.Response;


/**
 * 网关回复消息对象
 */
@Data
public class GatewayResponse {
    /**
     * 响应头
     */
    private HttpHeaders responseHeaders = new DefaultHttpHeaders();
    /**
     * 额外的响应头信息
     */
    private HttpHeaders extraResponseHeaders = new DefaultHttpHeaders();
    /**
     * 响应内容
     */
    private String content;
    /**
     * 返回响应状态码
     */
    private HttpResponseStatus httpResponseStatus;
    /**
     * 异步返回对象
     */
    private Response futureResponse;

    public GatewayResponse(){

    }

    /**
     * 设置响应头信息
     */
    public void putHeader(CharSequence key, CharSequence val){
        responseHeaders.add(key,val);
    }

    /**
     * 构建异步相应对象
     * @param futureResponse
     * @return
     */
    public static GatewayResponse buildGatewayResponse(Response futureResponse){
        GatewayResponse response = new GatewayResponse();
        response.setFutureResponse(futureResponse);
        response.setHttpResponseStatus(HttpResponseStatus.valueOf(futureResponse.getStatusCode()));
        return response;
    }

    /**
     * 返回一个Json类型的响应信息，失败时使用
     */
    public static GatewayResponse buildGatewayResponse(ResponseCode code , Object...args){
        JSONObject jsonObject = JSONUtil.createObj();
        jsonObject.put(JSONTool.STATUS,code.getStatus().code());
        jsonObject.put(JSONTool.CODE,code.getCode());
        jsonObject.put(JSONTool.MESSAGE,code.getMessage());
        GatewayResponse response = new GatewayResponse();
        response.setHttpResponseStatus(code.getStatus());
        response.putHeader(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON+";charset=utf-8");
        response.setContent(jsonObject.toJSONString(1));
        return response;
    }

    /**
     * 返回一个Json类型的响应信息，成功时使用
     */
    public static GatewayResponse buildGatewayResponse(Object data){
        JSONObject jsonObject = JSONUtil.createObj();
        jsonObject.put(JSONTool.STATUS,ResponseCode.SUCCESS.getStatus().code());
        jsonObject.put(JSONTool.CODE,ResponseCode.SUCCESS.getCode());
        jsonObject.put(JSONTool.MESSAGE,ResponseCode.SUCCESS.getMessage());
        jsonObject.put("data",data);
        GatewayResponse response = new GatewayResponse();
        response.setHttpResponseStatus(response.getHttpResponseStatus());
        response.putHeader(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON+";charset=utf-8");
        response.setContent(jsonObject.toJSONString(1));
        return response;
    }
}
