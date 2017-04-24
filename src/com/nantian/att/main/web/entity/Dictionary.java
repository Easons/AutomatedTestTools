package com.nantian.att.main.web.entity;



public class Dictionary {

	private String id;
	private String value;
	private transient String parentId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	public Dictionary(String id, String value, String parentId) {
		super();
		this.id = id;
		this.value = value;
		this.parentId = parentId;
	}
	public Dictionary() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Dictionary [id=" + id + ", value=" + value + "]";
	}
	
	
}
