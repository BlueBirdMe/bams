package com.pinhuba.common.code.bean;

import java.io.IOException;
import java.util.Properties;

public class DbConfig {

	private static DbConfig instance;
	
	private DbConfig(){}
	
	private String url;
	private String user;
	private String password;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public static DbConfig getInstance() {
		if(instance == null){
			Properties p = new Properties();
			try {
				p.load(DbConfig.class.getResourceAsStream("/proxool.properties"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			instance = new DbConfig();
			instance.setUrl(p.getProperty("sql.driver-url"));
			instance.setUser(p.getProperty("sql.user"));
			instance.setPassword(p.getProperty("sql.password"));
		}
		return instance;
	}
}