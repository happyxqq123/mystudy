package com.apigateway.core.context;

import cn.hutool.core.util.ReferenceUtil;
import com.apigateway.common.rule.Rule;
import com.apigateway.common.util.AssertUtil;
import com.apigateway.core.request.GatewayRequest;
import com.apigateway.core.response.GatewayResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.util.Optional;

/**
 */
public class GatewayContext extends  BaseContext{


    public GatewayRequest request;

    public GatewayResponse response;

    public Rule rule;

    public GatewayContext(String protocol, ChannelHandlerContext nettyCtx, boolean keepAlive, GatewayRequest request, Rule rule) {
        super(protocol, nettyCtx, keepAlive);
        this.request = request;
        this.rule = rule;
    }

    public static class Builder{
        private String protocol;
        private ChannelHandlerContext nettyContext;
        private GatewayRequest request;
        private Rule rule;
        private boolean keepAlive;

        public Builder(){

        }

        public Builder setProtocol(String protocol) {
            this.protocol = protocol;
            return this;
        }

        public Builder setNettyContext(ChannelHandlerContext nettyContext) {
            this.nettyContext = nettyContext;
            return this;
        }

        public Builder setRequest(GatewayRequest request) {
            this.request = request;
            return this;
        }

        public Builder setRule(Rule rule) {
            this.rule = rule;
            return this;
        }

        public Builder setKeepAlive(boolean keepAlive) {
            this.keepAlive = keepAlive;
            return this;
        }

        public GatewayContext build(){
            AssertUtil.notNull(protocol,"protocol 不能为空!");
            AssertUtil.notNull(nettyContext,"nettyContext 不能为空!");
            AssertUtil.notNull(request,"request 不能为空!");
            AssertUtil.notNull(rule,"rule 不能为空!");
            return new GatewayContext(protocol,nettyContext,keepAlive,
                    request,rule);
        }
    }

    public GatewayContext(String protocol, ChannelHandlerContext nettyCtx, boolean keepAlive) {
        super(protocol, nettyCtx, keepAlive);
    }

/*    public <T> T getRequireAttribute(String key){
    }*/

    /**
     * 获取指定key的上下文参数,如果没有就返回默认值
     * @param key
     * @param defaultValue
     * @return
     * @param <T>
     */
    public <T> T getRequireAttribute(String key, T defaultValue) {
        return (T) attributes.getOrDefault(key, defaultValue);
    }

    public Optional<Rule.FilterConfig> getFilterConfig(String filterId){
        return rule.getFilterConfigById(filterId);
    }

    /**
     * 获取服务id
     * @return
     */
    public String getUniqueId(){
        return request.getUniqueId();
    }


    /**
     * 真正的释放资源
     * @return
     */
    @Override
    public boolean releaseRequest() {
        if(requestReleased.compareAndSet(false,true)){
            ReferenceCountUtil.release(request.getFullHttpRequest());
        }
        return true;
    }

    /**
     * 获取原始请求对象
     * @return
     */
    public GatewayRequest getOriginRequest(){
        return request;
    }

    public void setRequest(GatewayRequest request) {
        this.request = request;
    }

    @Override
    public GatewayResponse getResponse() {
        return response;
    }

    @Override
    public void setResponse(Object response) {
        this.response = (GatewayResponse) response;
    }

    public Rule getRule() {
        return rule;
    }

    @Override
    public GatewayRequest getRequest() {
        return request;
    }
}
