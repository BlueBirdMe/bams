package com.pinhuba.common.util.file.properties;

import java.io.IOException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

/**
 * 读取系统配置文件
 * 
 * @author peng.ning
 * 
 */
public class SystemConfig {
	private Logger log = Logger.getLogger(this.getClass());

	private static final String RESOURCE_NAME = "erp"; // 配置文件名称

	private static boolean isInitialed = false;// 是否已初始化

	private static ResourceBundle iResourceBundle = null;

	private static SystemConfig iSystemConfig = null;

	public SystemConfig() throws Exception {
		// 读取配置参数
		erpBundle();
	}

	public static synchronized SystemConfig getInstance() throws Exception {
		if (iSystemConfig == null) {
			iSystemConfig = new SystemConfig();
		}
		return iSystemConfig;
	}

	private void erpBundle() throws Exception {
		if (!isInitialed) {
			try {
				iResourceBundle = ResourceBundle.getBundle(RESOURCE_NAME);
				log.info(RESOURCE_NAME + "配置文件绑定完成..");
				isInitialed = true;
			} catch (Exception e) {
				throw new Exception("无法读取系统配置文件");
			}
		}
	}

	public static String getParam(String paramKey) throws IOException {
		if (!isInitialed) {
			throw new IOException("系统参数未初始化");
		}
		String value = null;
		if (iResourceBundle.containsKey(paramKey.trim())) {
			value = iResourceBundle.getString(paramKey.trim());
		}
		return value;
	}

}
