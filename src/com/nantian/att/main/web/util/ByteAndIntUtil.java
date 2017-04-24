package com.nantian.att.main.web.util;


public class ByteAndIntUtil {

	private final static byte[] hex = "0123456789abcdef".getBytes(); 
	/**
	 * 用于将表示长度的byte数组转为int
	 * @param res
	 * @return
	 */
	public static int byte2int(byte[] head) {   
		int value= 0;
        for (int i = 0; i < 2; i++) {
           int shift= (2 - 1 - i) * 8;
           value +=(head[i] & 0x000000FF) << shift;//往高位游 
        }
		return value;
	}   
	/**
	 * 将返回的sop报文的byte数组转为16进制字符串
	 * @param b
	 * @return
	 */
	public static String Bytes2HexString(byte[] b) {  
	    byte[] buff = new byte[2 * b.length];  
	    for (int i = 0; i < b.length; i++) {  
	        buff[2 * i] = hex[(b[i] >> 4) & 0x0f];  
	        buff[2 * i + 1] = hex[b[i] & 0x0f];  
	    }  
	    return new String(buff);  
	}  
}
