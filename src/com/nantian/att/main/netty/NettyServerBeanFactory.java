package com.nantian.att.main.netty;

import org.springframework.stereotype.Component;

@Component
public class NettyServerBeanFactory {
	
	public NettyServerBean getNettyServerBean(){
		return new NettyServerBean();
	}
}
