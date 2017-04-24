package com.nantian.att.main.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpVersion;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nantian.att.main.netty.handler.HttpClientInboundHandler;

public class NettyClientBean {
	private static final Logger logger = LoggerFactory.getLogger(NettyClientBean.class);
	
    private Channel channel;
    private EventLoopGroup workerGroup;

    private String host;
    private int ioThreadNum;
    //内核为此套接口排队的最大连接个数，对于给定的监听套接口，内核要维护两个队列，未链接队列和已连接队列大小总和最大值
    private int backlog;
    private int port;

    /**
     * 启动
     * @throws InterruptedException
     * @throws URISyntaxException 
     * @throws UnsupportedEncodingException 
     */
    public void start() throws InterruptedException, URISyntaxException, UnsupportedEncodingException {
        logger.info("begin to start rpc server");
        workerGroup = new NioEventLoopGroup(ioThreadNum);
        try {
	        Bootstrap clientBootstrap = new Bootstrap();
	        clientBootstrap.group(workerGroup)
	                .channel(NioSocketChannel.class	)
	                .option(ChannelOption.SO_BACKLOG, backlog)
	                .option(ChannelOption.SO_KEEPALIVE, true)
	                .handler(new ChannelInitializer<SocketChannel>() {
	                    @Override
	                    protected void initChannel(SocketChannel socketChannel) throws Exception {
	                    	// 客户端接收到的是httpResponse响应，所以要使用HttpResponseDecoder进行解码
	                    	socketChannel.pipeline().addLast(new HttpResponseDecoder());
	                        // 客户端发送的是httprequest，所以要使用HttpRequestEncoder进行编码
	                    	socketChannel.pipeline().addLast(new HttpRequestEncoder());
	                    	socketChannel.pipeline().addLast(new HttpClientInboundHandler());
	                    }
	                });
	
	        // Start the client.
	        ChannelFuture f = clientBootstrap.connect(host, port).sync();
	        
	        URI uri = new URI("http://127.0.0.1:8844");
	        String msg = "Are you ok?";
	        DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET,
	                uri.toASCIIString(), Unpooled.wrappedBuffer(msg.getBytes("UTF-8")));
	        // 构建http请求
	        request.headers().set(HttpHeaders.Names.HOST, host);
	        request.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
	        request.headers().set(HttpHeaders.Names.CONTENT_LENGTH, request.content().readableBytes());
	        // 发送http请求
	        f.channel().write(request);
	        f.channel().flush();
	        f.channel().closeFuture().sync();
	        logger.info("Netty server listening on port " + port + " and ready for connections...");
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    public void stop() {
        logger.info("destroy server resources");
        if (null == channel) {
            logger.error("server channel is null");
        }
        workerGroup.shutdownGracefully();
        channel.closeFuture().syncUninterruptibly();
        workerGroup = null;
        channel = null;
    }

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getIoThreadNum() {
		return ioThreadNum;
	}

	public void setIoThreadNum(int ioThreadNum) {
		this.ioThreadNum = ioThreadNum;
	}

	public int getBacklog() {
		return backlog;
	}

	public void setBacklog(int backlog) {
		this.backlog = backlog;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	@Override
	public String toString() {
		return "NettyServerBean [host=" + host + ", ioThreadNum=" + ioThreadNum
				+ ", backlog=" + backlog + ", port=" + port + "]";
	}
}