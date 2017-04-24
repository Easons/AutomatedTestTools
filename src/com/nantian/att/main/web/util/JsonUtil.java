package com.nantian.att.main.web.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nantian.att.main.web.entity.Data;

public class JsonUtil {

	/**
	 * 将json串解析成Liat<Data>为页面生成树做准备
	 * @param json json串
	 * @param isArray 是否为集合
	 * @param pId 父节点Id
	 * @param list 
	 * @param keyVal 当为对象和集合时，对象和集合的属性名
	 * @return
	 */
	public static List<Data> getTreeList(String json,Boolean isArray,String pId,List<Data> list,String keyVal){
		Data root = null;
		if(keyVal == null){
			keyVal = "";
		}
		if(isArray){
			if(pId == null){
				root = new Data(GetUUID.randomId(), "array", "", pId);
				list.add(root);
			}
			JSONArray parseArray = JSONObject.parseArray(json);
			for (Object object : parseArray) {
				String item = object.toString();
				Data data = null;
				if(item.startsWith("{")){
					if(root != null){
						data = new Data(GetUUID.randomId(), "object", keyVal, root.getId());
					}else{
						data = new Data(GetUUID.randomId(), "object", keyVal, pId);
					}
					list.add(data);
				}else if (item.startsWith("[")) {
					if(root != null){
						data = new Data(GetUUID.randomId(), "array", keyVal,  root.getId());
					}else {
						data = new Data(GetUUID.randomId(), "array", keyVal, pId);
					}
					list.add(data);
				}
				JSONObject jsonObject = (JSONObject) object;
				Set<Entry<String, Object>> entrySet = jsonObject.entrySet();
				for (Entry<String, Object> entry : entrySet) {
					String key = entry.getKey();
					String value = entry.getValue().toString();
					if(value.startsWith("{")){
						getTreeList(value, false, data.getId(), list,key);
					}else if(value.startsWith("[")){
						Data array = new Data(GetUUID.randomId(),"array",key,data.getId());
						list.add(array);
						getTreeList(value, true, array.getId(), list,null);
					}else{
						Data temp = new Data(GetUUID.randomId(), key, value, data.getId());
						list.add(temp);
					}
				}
			}
		}else if(!isArray){
			root = new Data(GetUUID.randomId(), "object", keyVal, pId);
			list.add(root);
			JSONObject jsonObject = JSONObject.parseObject(json);
			Set<Entry<String, Object>> entrySet = jsonObject.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				String key = entry.getKey();
				String value = entry.getValue().toString();
				if(value.startsWith("{")){
					getTreeList(value, false, root.getId(), list,key);
				}else if(value.startsWith("[")){
					Data array = new Data(GetUUID.randomId(),"array",key,root.getId());
					list.add(array);
					getTreeList(value, true, array.getId(), list,null);
				}else{
					Data temp = new Data(GetUUID.randomId(), key, value, root.getId());
					list.add(temp);
				}
			}
		}
		return list;
	}
	
	public static String getJsonFromTree(List<Data> list,List<Map<String,Object>> res,Map<String, Object> map,List<Map<String,Object>> array){
		for (Data data : list) {
			String name = data.getName();
			String value = data.getValue();
			if(value == null){
				value = "";
			}
			List<Data> child = data.getData();
			if(name.equals("array") && value.equals("")){
				res = new ArrayList<Map<String,Object>>();
				getJsonFromTree(child, res, null, null);
			}else if(name.equals("array") && !value.equals("")){
				List<Map<String,Object>> arrayTemp = new ArrayList<Map<String,Object>>();
				getJsonFromTree(child, res, map, arrayTemp);
				map.put(value, arrayTemp);
			}else if(name.equals("object") && value.equals("")){
				Map<String, Object> temp = null;
				temp = new HashMap<String, Object>();
				getJsonFromTree(child, res, temp, null);
				if(array != null){
					array.add(temp);
				}
				if(res != null && map == null){
					res.add(temp);
				}
				if(res == null){
					map = temp;
				}
			}else if(name.equals("object") && !value.equals("")){
				Map<String, Object> temp = new HashMap<String, Object>();
				getJsonFromTree(child, res, temp,null);
				map.put(value, temp);
			}else{
				map.put(name,value);
			}
		}
		String json = null;
		if(res == null){
			json = JSONObject.toJSONString(map);
		}else{
			json = JSONObject.toJSONString(res);
		}
		return json;
	}
}
