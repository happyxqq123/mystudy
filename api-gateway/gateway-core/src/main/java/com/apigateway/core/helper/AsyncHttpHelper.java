package com.apigateway.core.helper;

import org.asynchttpclient.*;

import java.util.concurrent.CompletableFuture;

public class AsyncHttpHelper {

    private  AsyncHttpClient asyncHttpClient;

    private static AsyncHttpHelper asyncHttpHelper = new AsyncHttpHelper();

    private AsyncHttpHelper(){
        initialized(new DefaultAsyncHttpClient());
    }

    public static AsyncHttpHelper getInstance(){
        return asyncHttpHelper;
    }

    public void initialized(AsyncHttpClient asyncHttpClient){
        this.asyncHttpClient = asyncHttpClient;
    }

    public CompletableFuture<Response> executeRequest(Request request){
        ListenableFuture<Response> future = asyncHttpClient.executeRequest(request);
        return future.toCompletableFuture();
    }

    public <T> CompletableFuture<T> executeRequest(Request request, AsyncHandler<T> handler){
        ListenableFuture<T> future = asyncHttpClient.executeRequest(request,handler);
        return future.toCompletableFuture();
    }
}
