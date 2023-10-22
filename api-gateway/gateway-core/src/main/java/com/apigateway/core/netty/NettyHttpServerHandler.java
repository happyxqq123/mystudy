package com.apigateway.core.netty;

import com.apigateway.core.context.HttpRequestWrapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;

public class NettyHttpServerHandler extends ChannelInboundHandlerAdapter {

    private final NettyProcessor nettyProcessor;

    public NettyHttpServerHandler(NettyProcessor nettyProcessor) {
        this.nettyProcessor = nettyProcessor;
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpRequest fullHttpRequest = (FullHttpRequest) msg;
        HttpRequestWrapper requestWrapper = new HttpRequestWrapper();
        requestWrapper.setRequest(fullHttpRequest);
        requestWrapper.setCtx(ctx);
        nettyProcessor.process(requestWrapper);
    }
}
