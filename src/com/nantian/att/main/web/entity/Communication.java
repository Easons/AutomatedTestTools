package com.nantian.att.main.web.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 通讯信息类
 * @author sxh
 *
 */
public class Communication {

	private String id;
	private String name;
	private String ip;
	private String port;
	private String serverName;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@JsonProperty(value = "value")
	public String getName() {
		return name;
	}
	@JSONField(name="value")
	public void setName(String name) {
		this.name = name;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public Communication(String id, String name, String ip, String port,
			String serverName) {
		super();
		this.id = id;
		this.name = name;
		this.ip = ip;
		this.port = port;
		this.serverName = serverName;
	}
	public Communication() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Communication [id=" + id + ", name=" + name + ", ip=" + ip
				+ ", port=" + port + ", serverName=" + serverName + "]";
	}
	
	
}
