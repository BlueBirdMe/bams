package com.pinhuba.common.util.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author 
 * @version 1.0
 */
public class MD5
{
	
	public static String encrypt(String str)
	{		
		MessageDigest md = null;
		String out = null;
		try
		{
			md = MessageDigest.getInstance("MD5");
			byte[] digest = md.digest(str.getBytes());
			out = byte2hex(digest);
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();			
		}
		
		return out;
	}

	private static String byte2hex(byte[] b)
	{
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++)
		{
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
			{
				hs = hs + "0" + stmp;
			}
			else
			{
				hs = hs + stmp;
			}
		}

		return hs.toUpperCase();
	}
	
	public static void main(String[] args){
		String code = MD5.encrypt("getBai");
		System.out.println(code);
	}
}
