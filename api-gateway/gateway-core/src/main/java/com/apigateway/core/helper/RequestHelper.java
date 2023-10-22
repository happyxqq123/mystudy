package com.apigateway.core.helper;

import com.apigateway.core.context.GatewayContext;
import com.apigateway.core.request.GatewayRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

public class RequestHelper {

    public static GatewayContext doContext(FullHttpRequest request, ChannelHandlerContext ctx){
        // 构建请求对象GatewayRequest
        GatewayRequest gatewayRequest =  doRequest(request,ctx);
        return null;
    }

    private static GatewayRequest doRequest(FullHttpRequest request, ChannelHandlerContext ctx) {
        return null;
    }

}
