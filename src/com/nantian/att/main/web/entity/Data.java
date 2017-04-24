package com.nantian.att.main.web.entity;

import java.util.List;

public class Data {

	private String id;
	private String name;
	private String value;
	private Integer sign;
	private String parentId;
	private List<Data> data;
	
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
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Integer getSign() {
		return sign;
	}
	public void setSign(Integer sign) {
		this.sign = sign;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	
	public List<Data> getData() {
		return data;
	}
	public void setData(List<Data> data) {
		this.data = data;
	}
	
	public Data(String id, String name, String value, Integer sign,
			String parentId) {
		super();
		this.id = id;
		this.name = name;
		this.value = value;
		this.sign = sign;
		this.parentId = parentId;
	}
	public Data(String id, String name, String value, Integer sign,
			String parentId, List<Data> data) {
		super();
		this.id = id;
		this.name = name;
		this.value = value;
		this.sign = sign;
		this.parentId = parentId;
		this.data = data;
	}
	
	public Data(String id, String name, String value, String parentId) {
		super();
		this.id = id;
		this.name = name;
		this.value = value;
		this.parentId = parentId;
	}
	public Data(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}
	public Data(String id, String name, String value) {
		super();
		this.id = id;
		this.name = name;
		this.value = value;
	}
	
	public Data(String id) {
		super();
		this.id = id;
	}
	public Data() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Data [id=" + id + ", name=" + name + ", value=" + value
				+ ", sign=" + sign + ", parentId=" + parentId + ", data="
				+ data + "]";
	}
	
	
	
	
}
