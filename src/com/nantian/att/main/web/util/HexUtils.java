package com.nantian.att.main.web.util;

import java.io.UnsupportedEncodingException;

public class HexUtils {
	private static String hexString = "0123456789abcdef";  
	  
    public static String encodeCN(String data) {  
        byte[] bytes;  
        try {  
            bytes = data.getBytes("gbk");  
            StringBuilder sb = new StringBuilder(bytes.length * 2);  
  
            for (int i = 0; i < bytes.length; i++) {  
                sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));  
                sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));  
            }  
            return sb.toString();  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        return "";  
    }  
      
    public static String encodeStr(String data) {  
        String result = "";  
        byte[] bytes;  
        try {  
            bytes = data.getBytes("gbk");  
            for (int i = 0; i < bytes.length; i++) {  
                result += Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1);  
            }  
            return result;  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        return "";  
    }  
      
    /** 
     * 判定是否为中文汉字 
     * @param data 
     * @return 
     */  
    public static boolean isCN(String data) {  
        boolean flag = false;  
        String regex = "^[\u4e00-\u9fa5]*$";  
        if (data.matches(regex)) {  
            flag = true;  
        }  
        return flag;  
    }  
  
    public static String getHexResult(String targetStr) {  
        StringBuilder hexStr = new StringBuilder();  
        int len = targetStr.length();  
        if (len > 0) {  
            for (int i = 0; i < len; i++) {  
                char tempStr = targetStr.charAt(i);  
                String data = String.valueOf(tempStr);  
                if (isCN(data)) {  
                    hexStr.append(encodeCN(data));  
                } else {  
                    hexStr.append(encodeStr(data));  
                }  
            }  
        }  
        return hexStr.toString();  
    }  
    
    //给固定长度的数据单元补充0
    public static String addZero(int length){
    	String blank = "";
    	for (int i = 0; i < length; i++) {
			blank += "0";
		}
    	return blank;
    }
}
