package com.cx.springboot02.common.utils;

import java.util.UUID;
 
public class UUIDUtils {

	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static String getUUID(Integer len) {
		return UUID.randomUUID().toString().replaceAll("-", "").substring(0, len);
	}
}