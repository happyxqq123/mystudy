package com.apigateway.core.context;

import io.netty.channel.ChannelHandlerContext;

import java.util.function.Consumer;

/**
 *
 */
public interface IContext {

    /**
     * 上下文生命周期,运行中
     */
    int Running = 1;

    /**
     * 运行过程中发生错误，对其进行标记，告诉我们请求已经结束，需要返回客户端
     */
    int Written = 0;

    /**
     * 标记写回成功，防止并发情况夏的多次写回
     */
    int Completed = 1;

    /**
     * 表示网关请求结束
     */
    int Terminated = 2;

    /**
     * 设置上下文状态为运行中
     */
    void runned();

    /**
     * 设置上下文状态为标记写回
     */
    void writtened();

    /**
     * 设置上下文状态为标记写回成功
     */
    void completed();

    /**
     * 设置上下文状态为请求结束
     */
    void terminted();

    /**
     * 判断网关的状态
     * @return
     */
    boolean isRunning();

    boolean isWrittened();

    boolean isCompleted();
    boolean isTerminted();

    /**
     * 获取协议
     * @return
     */
    String getProtocol();

    /**
     * 获取请求对象
     * @return
     */
    Object getRequest();

    Throwable getThrowable();

    /**
     * 获取返回对象
     * @return
     */
    Object getResponse();

    /**
     * 设置返回对象
     */
    void setResponse(Object response);

    /***
     * 设置异常对象
     */
    void setThrowable(Throwable throwable);

    /***
     * 获取netty上下文
     * @return
     */
    ChannelHandlerContext getNettyCtx();

    /**
     * 是否长连接
     * @return
     */
    boolean isKeepAlive();

    /**
     * 释放请求资源
     * @return
     */
    boolean releaseRequest();


    /**
     * 写回接收回调函数
     * @param consumer
     */
    void setCompletedCallBack(Consumer<IContext> consumer);

    public void invokeCompletedCallback(Consumer<IContext> consumer);

}
