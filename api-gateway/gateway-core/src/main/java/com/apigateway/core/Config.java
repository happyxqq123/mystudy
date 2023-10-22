package com.apigateway.core;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Config {

    private int port = 8888;

    private String applicationName = "api-gateway";

    private String registryAddress = "127.0.0.1:8848";

    private String env = "dev";

    private int eventLoopGroupBossNum = 1;

    private int eventLoopGroupWorkerNum = Runtime.getRuntime().availableProcessors();

    //http报文最大
    private int maxContentLength = 64 * 1024 * 1024;

    //默认是单异步
    private boolean whenComplete = true;
}
