package com.nantian.att.main.web.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BusinessCollection {

	private String id;
	@JsonProperty(value="value")
	private String name;
	private String icon;
	@JsonProperty(value="data")
	private List<Business> children;
	
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
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public List<Business> getChildren() {
		return children;
	}
	public void setChildren(List<Business> children) {
		this.children = children;
	}
	@Override
	public String toString() {
		return "BusinessCollection [id=" + id + ", name=" + name + ", icon="
				+ icon + ", children=" + children + "]";
	}
	public BusinessCollection(String id, String name, String icon,
			List<Business> children) {
		super();
		this.id = id;
		this.name = name;
		this.icon = icon;
		this.children = children;
	}
	public BusinessCollection() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
