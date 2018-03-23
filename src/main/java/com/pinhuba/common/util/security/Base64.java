/**
 * @author 
 * @version 1.0 
 * 
 * Copyright 
 */
package com.pinhuba.common.util.security;

import sun.misc.BASE64Decoder;

public class Base64 {
	
	/*
	public static String getBase64FromString(String s) {
		if (s == null) {
			return null;
		}
		String tmp = (new sun.misc.BASE64Encoder()).encode(s.getBytes());
		tmp = tmp.replaceAll("\\+", "@1").replaceAll("\\-", "@2").replaceAll("\\=", "@3");

		return tmp;
	}*/
	
	/**
	 * Base64将字符串进行加密
	 */
	public static String getBase64FromString(String s) {
		if (s == null) {
			return null;
		}
		String tmp = (new Base64Encode()).encode(s.getBytes());
		tmp = tmp.replaceAll("\\+", "@1").replaceAll("\\-", "@2").replaceAll("\\=", "@3");
		
		return tmp;
	}

	/**
	 * Base64对字符串进行解密
	 * @param s
	 * @return
	 */
	public static String getStringFromBase64(String s) {
		if (s == null) {
			return null;
		}
		s = s.replaceAll("@1", "\\+").replaceAll("@2", "\\-").replaceAll("@3", "\\=");
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			byte[] b = decoder.decodeBuffer(s);
			String tmp = new String(b);
			return tmp;
		} catch (Exception e) {
			return null;
		}
	}

	public static void main(String[] args) {
		long t1 = System.currentTimeMillis();
		String code = Base64.getBase64FromString("http://localhost:8080/cims/userlogin");
		System.out.println(code);
		String p = Base64.getStringFromBase64("MDAtMjQtODEtNkItMzAtNA@3@3");
		System.out.println(p);
	}
}
