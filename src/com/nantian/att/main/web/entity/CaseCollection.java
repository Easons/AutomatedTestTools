package com.nantian.att.main.web.entity;

import java.util.List;

public class CaseCollection {

	private String id;
	private String name;
	private String busId;
	private List<Case> children;
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
	
	public String getBusId() {
		return busId;
	}
	public void setBusId(String busId) {
		this.busId = busId;
	}
	public List<Case> getChildren() {
		return children;
	}
	public void setChildren(List<Case> children) {
		this.children = children;
	}
	
		public CaseCollection(String id, String name, String busId,
			List<Case> children) {
		super();
		this.id = id;
		this.name = name;
		this.busId = busId;
		this.children = children;
	}
		public CaseCollection() {
		super();
		// TODO Auto-generated constructor stub
	}
		@Override
		public String toString() {
			return "CaseCollection [id=" + id + ", name=" + name + ", busId="
					+ busId + ", children=" + children + "]";
		}
	
	
	
}
