package com.asyncHttp;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Response;

import java.nio.charset.Charset;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import static org.asynchttpclient.Dsl.asyncHttpClient;

public class Test1 {

    public static void main(String[] args) {
        AsyncHttpClient asyncHttpClient = asyncHttpClient();
        CompletableFuture<Response> whenResponse = asyncHttpClient.prepareGet("http://www.baidu.com/")
                .execute()
                .toCompletableFuture()
                .exceptionally(t->{
                    System.out.println(t);
                    return null;
                })
                .thenApply(response -> {
                    System.out.println(Thread.currentThread().getName());
                    //System.out.println(response.getResponseBody(Charset.defaultCharset()));
                    return null;
                });
    }
}
