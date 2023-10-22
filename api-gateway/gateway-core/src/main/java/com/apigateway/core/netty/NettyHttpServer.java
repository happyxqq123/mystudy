package com.apigateway.core.netty;

import com.apigateway.core.Config;
import com.apigateway.core.LifeCycle;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

@Slf4j
public class NettyHttpServer implements LifeCycle {

    private final Config config;
    private ServerBootstrap serverBootstrap;
    private EventLoopGroup bossEventLoopGroup;
    private EventLoopGroup workerEventLoopGroup;
    private final NettyProcessor nettyProcessor;

    public NettyHttpServer(Config config, NettyProcessor nettyProcessor){
        this.config = config;
        this.nettyProcessor = nettyProcessor;
        init();
    }


    @Override
    public void init() {
        if(useEpoll()){
            this.serverBootstrap = new ServerBootstrap();
            this.bossEventLoopGroup = new EpollEventLoopGroup(config.getEventLoopGroupBossNum(),new DefaultThreadFactory("netty-boss-nio"));
            this.workerEventLoopGroup = new EpollEventLoopGroup(config.getEventLoopGroupWorkerNum(),new DefaultThreadFactory("netty-worker-nio"));
        }else{
            this.serverBootstrap = new ServerBootstrap();
            this.bossEventLoopGroup = new NioEventLoopGroup(config.getEventLoopGroupBossNum(),new DefaultThreadFactory("netty-boss-nio"));
            this.workerEventLoopGroup = new NioEventLoopGroup(config.getEventLoopGroupWorkerNum(),new DefaultThreadFactory("netty-worker-nio"));
        }

    }

    public boolean useEpoll(){
        return Epoll.isAvailable();
    }

    @Override
    public void start() {
        this.serverBootstrap
                .group(bossEventLoopGroup,workerEventLoopGroup)
                .channel(useEpoll()? EpollServerSocketChannel.class: NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(config.getPort()))
                .childHandler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        channel.pipeline().addLast(
                                new HttpServerCodec(),  //http编解码
                                new HttpObjectAggregator(config.getMaxContentLength()),
                                new NettyServerConnectManagerHandler(),
                                new NettyHttpServerHandler(nettyProcessor));
                    }
                });

        try{
            this.serverBootstrap.bind().sync();
            log.info("server startup on port {}",config.getPort());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void shutdown() {
        if(bossEventLoopGroup != null){
            bossEventLoopGroup.shutdownGracefully();
        }
        if(workerEventLoopGroup != null){
            workerEventLoopGroup.shutdownGracefully();
        }
    }
}
