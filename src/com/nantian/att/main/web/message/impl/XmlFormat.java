package com.nantian.att.main.web.message.impl;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.alibaba.fastjson.JSONObject;
import com.nantian.att.main.web.entity.Data;
import com.nantian.att.main.web.message.MessageHandler;
import com.nantian.att.main.web.util.XmlUtil;

public class XmlFormat implements MessageHandler<Data> {

	@Override
	public List<Data> parseMessage2Tree(String message) {
		List<Data> resList = null;
		try {
			Document document = DocumentHelper.parseText(message);
			  //获取根节点元素对象  
			    Element root = document.getRootElement();  
			    //遍历  
			   resList = XmlUtil.xml2Obj(root,null);
			   List<Data> removeIds = new ArrayList<Data>();
			   for (Data demoData : resList) {
					if(demoData.getParentId()!= null){
						removeIds.add(demoData);
						for (Data tempData : resList) {
							if(tempData.getId() == demoData.getParentId()){
								if(tempData.getData() != null){
									tempData.getData().add(demoData);
								}else{
									List<Data> childrenData = new ArrayList<Data>();
									childrenData.add(demoData);
									tempData.setData(childrenData);
								}
							}
						}
					}
			   }
			   for (Data rem : removeIds) {
				resList.remove(rem);
			   }
			  
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 XmlUtil.setData(new ArrayList<Data>());
		return resList;
	}

	@Override
	public String parseTree2Message(String treeData) {
		List<Data> parseArray = JSONObject.parseArray(treeData, Data.class);
		Map<String, Data> map = new HashMap<String, Data>();
		for (Data Data : parseArray) {
			if(Data.getParentId() != null){
				for (Data parentData : parseArray) {
					if(parentData.getId().equals(Data.getParentId())){
						if(parentData.getData() != null){
							parentData.getData().add(Data);
						}else{
							List<Data> children = new ArrayList<Data>();
							children.add(Data);
							parentData.setData(children);
						}
					}
				}
			}else{
				map.put(Data.getId(), Data);
			}
		}
		Set<String> keySet = map.keySet();
		Document document = DocumentHelper.createDocument();
		Element root = null;
		if(keySet.size() != 1){
			root = document.addElement("root");
		}
		for (String key : keySet) {
			Element node = null;
			Data data = map.get(key);
			String value = data.getValue();
			if(root == null){
				node = document.addElement(data.getName());
			}else{
				node = root.addElement(data.getName());
			}
			if(value != null && !"".equals(value.trim())){
				node.addText(data.getValue());
			}
			XmlUtil.json2xml(data, node);
		}
		StringWriter writer = new StringWriter();
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("utf-8");
		format.setNewlines(true); 
		format.setIndent(true); 
		try {
		XMLWriter xmlwriter = new XMLWriter(writer,format);
		xmlwriter.write(document);
		xmlwriter.flush();
		xmlwriter.close();
		} catch (Exception e) {
		e.printStackTrace();
		}
	    return writer.toString();
	}


}
