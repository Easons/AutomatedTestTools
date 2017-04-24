package com.nantian.att.main.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpResponse;

public class HttpClientInboundHandler extends ChannelInboundHandlerAdapter {
	 @Override
	    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
	        if (msg instanceof HttpResponse) 
	        {
	            HttpResponse response = (HttpResponse) msg;
	        }
	        if(msg instanceof HttpContent)
	        {
	            HttpContent content = (HttpContent)msg;
	            ByteBuf buf = content.content();
	            System.out.println(buf.toString(io.netty.util.CharsetUtil.UTF_8));
	            buf.release();
	        }
	    }
}
