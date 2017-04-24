package com.nantian.att.main.web.util;

import java.util.UUID;

public class GetUUID {

	public static String randomId(){
		return UUID.randomUUID().toString().replace("-", "");
	}
}
