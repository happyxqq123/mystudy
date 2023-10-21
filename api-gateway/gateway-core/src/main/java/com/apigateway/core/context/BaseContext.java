package com.apigateway.core.context;

import io.netty.channel.ChannelHandlerContext;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class BaseContext implements IContext{

    /**
     * 转发的协议
     */
    protected final String protocol;

    //状态，多线程情况夏考虑使用volatile
    protected volatile int status = IContext.Running;

    //Netty 上下文
    protected  final ChannelHandlerContext nettyCtx;

    //上下文参数
    protected  final Map<String,Object> attributes = new HashMap<>();

    //请求过程中发生的异常
    protected Throwable throwable;

    //是否 保持长连接
    protected  final boolean keepAlive;

    //存放回调函数集合
    protected List<Consumer<IContext>> completedCallBacks;

    //定义是否已经释放资源
    protected  final AtomicBoolean requestReleased = new AtomicBoolean(false);

    public BaseContext(String protocol, ChannelHandlerContext nettyCtx, boolean keepAlive) {
        this.protocol = protocol;
        this.nettyCtx = nettyCtx;
        this.keepAlive = keepAlive;
    }

    @Override
    public void runned() {
        status = IContext.Running;
    }

    @Override
    public void writtened() {
        status = IContext.Written;
    }

    @Override
    public void completed() {
        status = IContext.Completed;
    }

    @Override
    public void terminted() {
        status = IContext.Terminated;
    }

    @Override
    public boolean isRunning() {
        return status == IContext.Running;
    }

    @Override
    public boolean isWrittened() {
        return status == IContext.Written;
    }

    @Override
    public boolean isCompleted() {
        return status == IContext.Completed;
    }

    @Override
    public boolean isTerminted() {
        return status == IContext.Terminated;
    }

    @Override
    public String getProtocol() {
        return this.protocol;
    }

    @Override
    public Object getRequest() {
        return null;
    }

    @Override
    public Throwable getThrowable() {
        return this.throwable;
    }

    @Override
    public Object getResponse() {
        return null;
    }

    @Override
    public void setResponse(Object response) {

    }

    @Override
    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    @Override
    public ChannelHandlerContext getNettyCtx() {
        return this.nettyCtx;
    }

    @Override
    public boolean isKeepAlive() {
        return this.keepAlive;
    }

    @Override
    public boolean releaseRequest() {
        return false;
    }

    @Override
    public void setCompletedCallBack(Consumer<IContext> consumer) {
        if(Objects.isNull(consumer)) return;
        if(completedCallBacks == null){
            completedCallBacks = new ArrayList<>();
        }
        completedCallBacks.add(consumer);
    }

    @Override
    public void invokeCompletedCallback(Consumer<IContext> consumer) {
        if(completedCallBacks != null){
            completedCallBacks.forEach(call->call.accept(this));
        }
    }
}
