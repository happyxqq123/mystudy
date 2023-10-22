package com.apigateway.core.helper;

import com.apigateway.core.context.IContext;
import com.apigateway.core.response.GatewayResponse;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

public class ResponseHelper {


    public static void writeResponse(IContext context){
        //释放资源
        context.releaseRequest();
        if(context.isWrittened()){
            // 1: 第一步构建响应对象，并写回数据
        }


    }

    public static FullHttpResponse getHttpResponse(IContext context, HttpResponseStatus httpResponseStatus){
        Object object =  context.getResponse();
        GatewayResponse gatewayResponse = null;
        if(object instanceof GatewayResponse){
            gatewayResponse = (GatewayResponse) object;
        }else{
            return null;
        }
        gatewayResponse.getResponseHeaders()
        //FullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_0,httpResponseStatus,gatewayResponse.getContent());
        return null;
    }
}
