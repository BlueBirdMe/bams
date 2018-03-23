package com.pinhuba.common.util.net;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.ServletException;

public class ConnectionUrl {
	
	public static boolean createOrFreeSession(String servletUrl,String context) throws ServletException{
		boolean bl=false;
		try {
			URL url = new URL(servletUrl);
			URLConnection urlCon = url.openConnection();
			urlCon.setDoOutput(true);
			urlCon.setDoInput(true);
			BufferedOutputStream bos = new BufferedOutputStream(urlCon
					.getOutputStream());
			byte[] out = context.getBytes();
			bos.write(out);
			bos.flush();
			bos.close();
			String result = getInfoFromOther(urlCon.getInputStream());
			if (result.equalsIgnoreCase("true")) {
				bl=true;
			}
			urlCon = null;
		}catch (Exception e) {
			throw new ServletException("网络传输异常");
		}
		return bl;
	}
	private static String getInfoFromOther(InputStream is) throws Exception{
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuffer result = new StringBuffer();
		String str;
		while((str = reader.readLine()) != null && str.length() != 0){
			result.append(str);
		}
		return result.toString();
	}
}
