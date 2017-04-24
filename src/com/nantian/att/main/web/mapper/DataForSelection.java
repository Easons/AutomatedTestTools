package com.nantian.att.main.web.mapper;

public class DataForSelection {

	private String id;
	private String value;
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
	public DataForSelection(String id, String value) {
		super();
		this.id = id;
		this.value = value;
	}
	@Override
	public String toString() {
		return "DataForSelection [id=" + id + ", value=" + value + "]";
	}
	
}
