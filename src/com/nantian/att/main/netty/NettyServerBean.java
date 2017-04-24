package com.nantian.att.main.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyServerBean {
	private static final Logger logger = LoggerFactory.getLogger(NettyServerBean.class);
	
    private Channel channel;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    private String host;
    private int ioThreadNum;
    //内核为此套接口排队的最大连接个数，对于给定的监听套接口，内核要维护两个队列，未链接队列和已连接队列大小总和最大值
    private int backlog;
    private int port;

    /**
     * 启动
     * @throws InterruptedException
     */
    public void start() throws InterruptedException {
        logger.info("begin to start rpc server");
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup(ioThreadNum);
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class	)
                .option(ChannelOption.SO_BACKLOG, backlog)
                //注意是childOption
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                //真实数据最大字节数为Integer.MAX_VALUE，解码时自动去掉前面四个字节
                                .addLast("decoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4))
                                .addLast("encoder", new LengthFieldPrepender(4, false));
                        		
                    }
                });

        channel = serverBootstrap.bind(host,port).sync().channel();

        logger.info("Netty server listening on port " + port + " and ready for connections...");
    }

    public void stop() {
        logger.info("destroy server resources");
        if (null == channel) {
            logger.error("server channel is null");
        }
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
        channel.closeFuture().syncUninterruptibly();
        bossGroup = null;
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