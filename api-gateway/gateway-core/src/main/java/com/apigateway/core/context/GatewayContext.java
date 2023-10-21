package com.apigateway.core.context;

import com.apigateway.common.rule.Rule;
import com.apigateway.common.util.AssertUtil;
import com.apigateway.core.request.GatewayRequest;
import com.apigateway.core.response.GatewayResponse;
import io.netty.channel.ChannelHandlerContext;
import lombok.AllArgsConstructor;
import lombok.Setter;

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
}
