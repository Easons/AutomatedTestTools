package com.nantian.att.main.web.util;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.nantian.att.main.web.entity.Data;

public class MakeTreeData2List {

	public static String TreeData2ListJson(String treeData){
		List<Data> temp = JSONObject.parseArray(treeData, Data.class);
		List<Data> treeList = new ArrayList<Data>();
		for (Data data : temp) {
			if(data.getParentId() != null){
				for (Data parentData : temp) {
					if(parentData.getId().equals(data.getParentId())){
						if(parentData.getData() == null){
							List<Data> childrenData = new ArrayList<Data>();
							childrenData.add(data);
							parentData.setData(childrenData);
						}else{
							parentData.getData().add(data);
						}
					}
				}
			}
		}
		for (Data data : temp) {
			if(data.getParentId() == null){
				treeList.add(data);
			}
		}
		String jsonString = JSONObject.toJSONString(treeList);
		return jsonString;
	}
	public static List<Data> TreeData2List(String treeData){
		List<Data> temp = JSONObject.parseArray(treeData, Data.class);
		List<Data> treeList = new ArrayList<Data>();
		for (Data data : temp) {
			if(data.getParentId() != null){
				for (Data parentData : temp) {
					if(parentData.getId().equals(data.getParentId())){
						if(parentData.getData() == null){
							List<Data> childrenData = new ArrayList<Data>();
							childrenData.add(data);
							parentData.setData(childrenData);
						}else{
							parentData.getData().add(data);
						}
					}
				}
			}
		}
		for (Data data : temp) {
			if(data.getParentId() == null){
				treeList.add(data);
			}
		}
		return treeList;
	}
	public static List<Data> listBussinessData2TreeList(List<Data> list,String bussId){
		List<Data> res = new ArrayList<Data>();
		for (Data data : list) {
			if(data.getParentId() != null){
				for (Data parentData : list) {
					if(parentData.getId().equals(data.getParentId())){
						if(parentData.getData() == null){
							List<Data> childrenData = new ArrayList<Data>();
							childrenData.add(data);
							parentData.setData(childrenData);
						}else{
							parentData.getData().add(data);
						}
					}
				}
			}
		}
		for (Data data : list) {
			if(data.getParentId().equals(bussId)){
				res.add(data);
			}
		}
		return res;
	}
	
	public static List<Data> jsonList2TreeList(List<Data> list){
		List<Data> res = new ArrayList<Data>();
		for (Data data : list) {
			if(data.getParentId() != null){
				for (Data parentData : list) {
					if(parentData.getId().equals(data.getParentId())){
						if(parentData.getData() == null){
							List<Data> childrenData = new ArrayList<Data>();
							childrenData.add(data);
							parentData.setData(childrenData);
						}else{
							parentData.getData().add(data);
						}
					}
				}
			}
		}
		for (Data data : list) {
			if(data.getParentId() == null){
				res.add(data);
			}
		}
		return res;
	}
}
