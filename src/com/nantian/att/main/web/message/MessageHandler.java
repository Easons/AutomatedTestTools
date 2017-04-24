package com.nantian.att.main.web.message;

import java.util.List;

public interface MessageHandler<T>{
	
	List<T> parseMessage2Tree(String message);
	String parseTree2Message(String treeData);
}
