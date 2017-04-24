package com.nantian.att.main.web.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.dom4j.Attribute;
import org.dom4j.Element;

import com.nantian.att.main.web.entity.Data;

public class XmlUtil {

	private static List<Data> data = new ArrayList<Data>();
	

	public static List<Data> getData() {
		return data;
	}

	public static void setData(List<Data> data) {
		XmlUtil.data = data;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Data> xml2Obj(Element node,String parentId){
			/*System.out.println("当前节点的名称：" + node.getName()); */
			String name = node.getName();
			Data demoData = new Data();
			demoData.setId(UUID.randomUUID().toString().replace("-", ""));
			demoData.setName(name);
			demoData.setSign(1);
			if(parentId != null){
				demoData.setParentId(parentId);
			}
	        //首先获取当前节点的所有属性节点  
	        List<Attribute> list = node.attributes();  
	        if(list.size() != 0){
	        	List<Data> children = new ArrayList<Data>();
	        	//遍历属性节点  
		        for(Attribute attribute : list){  
		            /*System.out.println("属性"+attribute.getName() +":" + attribute.getValue());  */
		            children.add(new Data(UUID.randomUUID().toString().replace("-", ""), attribute.getName(), attribute.getValue(), 2, demoData.getId()));
		        }  
		        demoData.setData(children);
	        }
	        //如果 当前节点内容不为空，则输出  
	        if(!(node.getTextTrim().equals(""))){  
	            /* System.out.println( node.getName() + "：" + node.getText());*/
	             demoData.setValue(node.getText());
	        }
	        data.add(demoData);
	        //同时迭代当前节点下面的所有子节点  
	        //使用递归  
	        Iterator<Element> iterator = node.elementIterator();
	        while(iterator.hasNext()){
	            Element e = iterator.next();  
	            xml2Obj(e,demoData.getId());  
	        }  
	        return data;
	}
	public static void json2xml(Data demoData,Element node){
		List<Data> children = demoData.getData();
		if(children != null){
			for (Data data : children) {
				if(data.getSign() == 2){
					node.addAttribute(data.getName(), data.getValue());
				}else if(data.getSign() == 1){
					Element addElementtemp = node.addElement(data.getName());;
					String value = data.getValue();
					if(value != null && !"".equals(value.trim())){
						addElementtemp.addText(data.getValue());
					}
					List<Data> temp = data.getData();
					if(temp != null){
						json2xml(data,addElementtemp);
					}
				}
			}
		}
	}
}
