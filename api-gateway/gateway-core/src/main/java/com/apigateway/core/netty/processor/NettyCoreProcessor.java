package com.apigateway.core.netty.processor;

import com.apigateway.common.entity.ResponseCode;
import com.apigateway.common.exception.ResponseException;
import com.apigateway.core.ConfigLoader;
import com.apigateway.core.context.GatewayContext;
import com.apigateway.core.context.HttpRequestWrapper;
import com.apigateway.core.helper.AsyncHttpHelper;
import com.apigateway.core.netty.NettyProcessor;
import com.apigateway.core.response.GatewayResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.extern.slf4j.Slf4j;
import org.asynchttpclient.Request;
import org.asynchttpclient.Response;

import java.net.ConnectException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeoutException;


@Slf4j
public class NettyCoreProcessor implements NettyProcessor {

    @Override
    public void process(HttpRequestWrapper httpRequestWrapper) {
        FullHttpRequest request = httpRequestWrapper.getRequest();
        ChannelHandlerContext ctx = httpRequestWrapper.getCtx();
    }

    private void route(GatewayContext gatewayContext) {
        Request request = gatewayContext.getRequest().build();
        CompletableFuture<Response> future = AsyncHttpHelper.getInstance().executeRequest(request);
        boolean whenComplete = ConfigLoader.getConfig().isWhenComplete();
        if (whenComplete) {
            future.whenComplete((response, throwable) -> {
                complete(request,response,throwable,gatewayContext);
            });
        } else {
            future.whenCompleteAsync((response,throwable) -> {
                complete(request,response,throwable,gatewayContext);
            });
        }
    }

    private void complete(Request request, Response response, Throwable throwable, GatewayContext gatewayContext) {
        gatewayContext.releaseRequest();
        try{
            if (Objects.nonNull(throwable)) {
                String url = request.getUrl();
                if (throwable instanceof TimeoutException) {
                    log.warn("complete time out {}", url);
                    gatewayContext.setThrowable(new ResponseException(ResponseCode.REQUEST_TIMEOUT));
                } else {
                    ConnectException connectException = new ConnectException();
                    gatewayContext.setThrowable(new ResponseException(ResponseCode.HTTP_RESPONSE_ERROR));
                }
            } else {
                gatewayContext.setResponse(GatewayResponse.buildGatewayResponse(response));
            }
        }catch (Throwable t){
            gatewayContext.setThrowable(new ResponseException(t,ResponseCode.INTERNAL_ERROR));
            log.error("complete error",t);
        }finally {
            gatewayContext.writtened();
        }

    }
}
