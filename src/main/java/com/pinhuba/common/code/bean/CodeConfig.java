package com.pinhuba.common.code.bean;

import java.io.IOException;
import java.util.Properties;

public class CodeConfig{
	
	private static CodeConfig instance;
    
    private String srcPath;
    
    private String pagePath;
    
    private CodeConfig (){}  
	
	public String getSrcPath() {
		return srcPath;
	}

	public void setSrcPath(String srcPath) {
		this.srcPath = srcPath;
	}

	public String getPagePath() {
		return pagePath;
	}

	public void setPagePath(String pagePath) {
		this.pagePath = pagePath;
	}

	public static synchronized CodeConfig getInstance() {
		if (instance == null) {
			Properties p = new Properties();
			try {
				p.load(CodeConfig.class.getResourceAsStream("/code.properties"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			instance = new CodeConfig();
			instance.setSrcPath(p.getProperty("srcPath"));
			instance.setPagePath(p.getProperty("pagePath"));
		}
		
		return instance;
	}
	
}