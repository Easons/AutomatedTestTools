package com.nantian.att.main.web.message.impl;

import java.util.ArrayList;
import java.util.List;

import com.nantian.att.main.web.entity.Data;
import com.nantian.att.main.web.message.MessageHandler;
import com.nantian.att.main.web.util.JsonUtil;
import com.nantian.att.main.web.util.MakeTreeData2List;


public class JsonFormat implements MessageHandler<Data>{

	@Override
	public List<Data> parseMessage2Tree(String message) {
		Boolean isArray = message.startsWith("[");
		List<Data> treeList = JsonUtil.getTreeList(message, isArray, null, new ArrayList<Data>(),null);
		List<Data> res = MakeTreeData2List.jsonList2TreeList(treeList);
		return res;
	}

	@Override
	public String parseTree2Message(String treeData) {
		List<Data> list = MakeTreeData2List.TreeData2List(treeData);
		String json = JsonUtil.getJsonFromTree(list,null,null,null);
		return json;
	}


}
