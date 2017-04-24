package com.nantian.att.main.web.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReadFile {
	private static Logger logger = LoggerFactory.getLogger(ReadFile.class);
	public static String readFile2Byte(File file) throws IOException{
		if (!file.exists()) {  
            throw new FileNotFoundException(file.getPath());  
        }  
		int len=0;
	    StringBuffer str=new StringBuffer("");
	    try {
	        FileInputStream is=new FileInputStream(file);
	        InputStreamReader isr= new InputStreamReader(is);
	        BufferedReader in= new BufferedReader(isr);
	        String line=null;
	        while( (line=in.readLine())!=null ){
	            if(len != 0){  // 处理换行符
	                str.append("\r\n"+line);
	            }else{
	                str.append(line);
	            }
	            len++;
	        }
	        in.close();
	        is.close();
	    } catch (IOException e) {
	    	logger.error("上传报文文件时出错："+e.getStackTrace().toString());
	    }
	    return str.toString();
	}
}
