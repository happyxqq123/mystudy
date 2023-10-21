package com.asyncHttp;

import io.netty.handler.codec.http.HttpHeaders;
import org.asynchttpclient.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.*;

import static org.asynchttpclient.Dsl.*;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {

        AsyncHttpClient asyncHttpClient = asyncHttpClient();
        Future<Integer> whenStatusCode = asyncHttpClient.prepareGet("http://www.baidu.com")
                .execute(new AsyncHandler<Integer>(){

                    private Integer status;

                    @Override
                    public State onStatusReceived(HttpResponseStatus responseStatus) throws Exception {
                        status =  responseStatus.getStatusCode();
                        return State.CONTINUE;
                    }

                    @Override
                    public State onHeadersReceived(HttpHeaders headers) throws Exception {
                        return State.CONTINUE;
                    }

                    @Override
                    public State onBodyPartReceived(HttpResponseBodyPart bodyPart) throws Exception {
                        byte[] buf =  bodyPart.getBodyPartBytes();
                        String str = new String(buf,Charset.defaultCharset());
                        System.out.println(str);
                        return State.CONTINUE;
                    }

                    @Override
                    public void onThrowable(Throwable t) {
                        t.printStackTrace();
                    }

                    @Override
                    public Integer onCompleted() throws Exception {
                        return status;
                    }
                });

        Integer statusCode = whenStatusCode.get();
        System.out.println(statusCode);

        asyncHttpClient.close();
    }
}