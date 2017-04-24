package com.nantian.att.main.web.entity;

import java.util.Date;

public class Case {

	private String id;
	private String name;
	private String descp;
	private String protocol;
	private String dataFormat;
	private String dataId;
	private String result;
	private Date createTime;
	private Date modifyTime;
	private String caseCollId;
	private String treeData;
	private String communicationId;
	private String messageLogUrl;
	private String remark;
	private String isTest;
	private CaseCollection caseCollection;
	
	
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


	public String getDescp() {
		return descp;
	}


	public void setDescp(String descp) {
		this.descp = descp;
	}


	public String getProtocol() {
		return protocol;
	}


	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}


	public String getDataFormat() {
		return dataFormat;
	}


	public void setDataFormat(String dataFormat) {
		this.dataFormat = dataFormat;
	}


	public String getDataId() {
		return dataId;
	}


	public void setDataId(String dataId) {
		this.dataId = dataId;
	}


	public String getResult() {
		return result;
	}


	public void setResult(String result) {
		this.result = result;
	}


	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public Date getModifyTime() {
		return modifyTime;
	}


	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}


	public String getCaseCollId() {
		return caseCollId;
	}


	public void setCaseCollId(String caseCollId) {
		this.caseCollId = caseCollId;
	}


	public CaseCollection getCaseCollection() {
		return caseCollection;
	}


	public void setCaseCollection(CaseCollection caseCollection) {
		this.caseCollection = caseCollection;
	}
	

	public String getTreeData() {
		return treeData;
	}


	public void setTreeData(String treeData) {
		this.treeData = treeData;
	}


	public String getCommunicationId() {
		return communicationId;
	}


	public void setCommunicationId(String communicationId) {
		this.communicationId = communicationId;
	}


	public String getMessageLogUrl() {
		return messageLogUrl;
	}


	public void setMessageLogUrl(String messageLogUrl) {
		this.messageLogUrl = messageLogUrl;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getIsTest() {
		return isTest;
	}


	public void setIsTest(String isTest) {
		this.isTest = isTest;
	}


	public Case(String id, String name, String descp, String protocol,
			String dataFormat, String dataId, String result, Date createTime,
			Date modifyTime, String caseCollId, CaseCollection caseCollection) {
		super();
		this.id = id;
		this.name = name;
		this.descp = descp;
		this.protocol = protocol;
		this.dataFormat = dataFormat;
		this.dataId = dataId;
		this.result = result;
		this.createTime = createTime;
		this.modifyTime = modifyTime;
		this.caseCollId = caseCollId;
		this.caseCollection = caseCollection;
	}
	

	public Case(String id, String name, String descp, String protocol,
			String dataFormat, String dataId, String result, Date createTime,
			Date modifyTime, String caseCollId, String treeData,
			String communicationId, String messageLogUrl, String remark,
			String isTest, CaseCollection caseCollection) {
		super();
		this.id = id;
		this.name = name;
		this.descp = descp;
		this.protocol = protocol;
		this.dataFormat = dataFormat;
		this.dataId = dataId;
		this.result = result;
		this.createTime = createTime;
		this.modifyTime = modifyTime;
		this.caseCollId = caseCollId;
		this.treeData = treeData;
		this.communicationId = communicationId;
		this.messageLogUrl = messageLogUrl;
		this.remark = remark;
		this.isTest = isTest;
		this.caseCollection = caseCollection;
	}


	public Case(String id, String name, String descp, String protocol,
			String dataFormat, String dataId, String result, Date createTime,
			Date modifyTime, String caseCollId, String treeData,
			CaseCollection caseCollection) {
		super();
		this.id = id;
		this.name = name;
		this.descp = descp;
		this.protocol = protocol;
		this.dataFormat = dataFormat;
		this.dataId = dataId;
		this.result = result;
		this.createTime = createTime;
		this.modifyTime = modifyTime;
		this.caseCollId = caseCollId;
		this.treeData = treeData;
		this.caseCollection = caseCollection;
	}

	


	public Case(String id, String name, String descp, String protocol,
			String dataFormat, String dataId, String result, Date createTime,
			Date modifyTime, String caseCollId, String treeData,
			String communicationId, String messageLogUrl, String remark,
			CaseCollection caseCollection) {
		super();
		this.id = id;
		this.name = name;
		this.descp = descp;
		this.protocol = protocol;
		this.dataFormat = dataFormat;
		this.dataId = dataId;
		this.result = result;
		this.createTime = createTime;
		this.modifyTime = modifyTime;
		this.caseCollId = caseCollId;
		this.treeData = treeData;
		this.communicationId = communicationId;
		this.messageLogUrl = messageLogUrl;
		this.remark = remark;
		this.caseCollection = caseCollection;
	}


	public Case(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Case() {
		super();
		// TODO Auto-generated constructor stub
	}


	@Override
	public String toString() {
		return "Case [id=" + id + ", name=" + name + ", descp=" + descp
				+ ", protocol=" + protocol + ", dataFormat=" + dataFormat
				+ ", dataId=" + dataId + ", result=" + result + ", createTime="
				+ createTime + ", modifyTime=" + modifyTime + ", caseCollId="
				+ caseCollId + ", treeData=" + treeData + ", communicationId="
				+ communicationId + ", messageLogUrl=" + messageLogUrl
				+ ", remark=" + remark + ", isTest=" + isTest
				+ ", caseCollection=" + caseCollection + "]";
	}

	
}
