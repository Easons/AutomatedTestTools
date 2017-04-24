package com.nantian.att.main.web.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Business {

	private String id;
	@JsonProperty(value="value")
	private String name;
	private Date createTime;
	private String bussCollId;
	private BusinessCollection businessCollection;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getBussCollId() {
		return bussCollId;
	}
	public void setBussCollId(String bussCollId) {
		this.bussCollId = bussCollId;
	}
	public BusinessCollection getBusinessCollection() {
		return businessCollection;
	}
	public void setBusinessCollection(BusinessCollection businessCollection) {
		this.businessCollection = businessCollection;
	}
	public Business(String id, String name, Date createTime, String bussCollId,
			BusinessCollection businessCollection) {
		super();
		this.id = id;
		this.name = name;
		this.createTime = createTime;
		this.bussCollId = bussCollId;
		this.businessCollection = businessCollection;
	}
	public Business() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Business [id=" + id + ", name=" + name + ", createTime="
				+ createTime + ", bussCollId=" + bussCollId
				+ ", businessCollection=" + businessCollection + "]";
	}
	
}
