package com.apigateway.core.netty;

import com.apigateway.core.context.HttpRequestWrapper;

public interface NettyProcessor {

    void process(HttpRequestWrapper httpRequestWrapper);

}
