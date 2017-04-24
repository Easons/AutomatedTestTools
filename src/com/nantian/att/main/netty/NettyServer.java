package com.nantian.att.main.netty;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NettyServer {
	@Autowired
	private NettyServerBeanFactory nettyServerBeanFactory = null;
	
	private Map<String,NettyServerBean> nsbMap = new HashMap<String,NettyServerBean>();
	 
	@PostConstruct
	public void init() throws InterruptedException{
		NettyServerBean nsb = nettyServerBeanFactory.getNettyServerBean();
		nsb.setHost("127.0.0.1");
		nsb.setPort(9091);
		nsb.setIoThreadNum(5);
		nsb.setBacklog(1024);
		nsbMap.put(nsb.getHost()+nsb.getPort(), nsb);
		System.out.println(nsb.toString());
		nsb.start();
	}
    @PreDestroy
    public void destroy(){
		for (Map.Entry<String,NettyServerBean> nsbEntry: nsbMap.entrySet()) {
			NettyServerBean nsb = nsbEntry.getValue();
			nsb.stop();
		}
	}
}
