package com.pinhuba.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class UtilPrimaryKey {
	
	public static String getPK() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	public static String getPrimaryKey(String sign){
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddwhhmmssSSS");
		return sign+sf.format(new Date());
	}
	
	public static String getPrimaryKey() {
		return UUID.randomUUID().toString().toUpperCase();
	}

}
