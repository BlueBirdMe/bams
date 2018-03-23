package com.pinhuba.web.listener;

import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.pinhuba.common.util.ConstWords;
import com.pinhuba.common.util.file.FileTool;
import com.pinhuba.common.util.file.properties.SystemConfig;
import com.pinhuba.core.iservice.IMoblieSmsService;
import com.pinhuba.core.iservice.IUserLoginService;
import com.pinhuba.core.pojo.SysMethodInfo;
import com.pinhuba.core.scheduler.TimedScheduler;

public class InitErp implements ServletContextListener {
	private Logger logger = Logger.getLogger(this.getClass());

	public void contextDestroyed(ServletContextEvent sce) {
		logger.info("系统被关闭。。。");
	}

	public void contextInitialized(ServletContextEvent event) {
		try {
			logger.info("=======初始化ERP系统(InitErp)==================");
			logger.info("系统参数");
			Properties tSysProperties = System.getProperties();
			Enumeration tSysPropKeys = tSysProperties.keys();
			while (tSysPropKeys.hasMoreElements()) {
				String tSysPropKey = (String) tSysPropKeys.nextElement();
				logger.info("  " + tSysPropKey + " =[ " + tSysProperties.getProperty(tSysPropKey) + " ]");
			}
			logger.info("系统初始化开始");
			logger.info("加载配置文件......");
			SystemConfig tCimsSystemConfig = SystemConfig.getInstance();
			if (tCimsSystemConfig == null) {
				logger.error("====初始化 [配置文件] 失败=======");
			}
			logger.info("加载功能菜单模块....");
			this.loadSysMethodInfo(event.getServletContext());
			logger.info("创建系统所需目录及文件....");
			this.createDirAndFile(event.getServletContext());
			logger.info("创建短消息全部存储对象....");
			this.createSmsBox(event.getServletContext());

			/*********** 定时提醒 ***************/
			TimedScheduler timeSche = new TimedScheduler();
			timeSche.run(event.getServletContext());

			logger.info("系统初始化结束");
			logger.info("============初始化ERP系统成功==================");
		} catch (Exception e) {
			logger.error("[init system error]:" + e.getMessage());
			e.printStackTrace();
		}

		logger.info("[success]Project init has been finished...");
	}

	private void loadSysMethodInfo(javax.servlet.ServletContext servletContext) {
		WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		IUserLoginService userLoginService = (IUserLoginService) webAppContext.getBean("userLoginService");
		List<SysMethodInfo> methodInfoList = userLoginService.getAllMethodInfoByLevel();
		SysMethodInfo method = ConstWords.getErpMethodInfo(methodInfoList);
		ConstWords.ERPPath = method == null ? "" : method.getMethodUri();
		servletContext.setAttribute(ConstWords.ServletContext_Method, methodInfoList);
	}

	private void createDirAndFile(javax.servlet.ServletContext servletContext) throws Exception {
		boolean bl = FileTool.checkDirAndCreate(SystemConfig.getParam("erp.upload.fileSavePath"));
		if (bl) {
			logger.info("创建:" + SystemConfig.getParam("erp.upload.fileSavePath"));
		}

		bl = FileTool.checkDirAndCreate(SystemConfig.getParam("erp.upload.imgSavePath"));
		if (bl) {
			logger.info("创建:" + SystemConfig.getParam("erp.upload.imgSavePath"));
		}

		bl = FileTool.checkDirAndCreate(SystemConfig.getParam("erp.mail.filePath"));
		if (bl) {
			logger.info("创建:" + SystemConfig.getParam("erp.mail.filePath"));
		}

		bl = FileTool.checkDirAndCreate(SystemConfig.getParam("erp.netdisk.path"));
		if (bl) {
			logger.info("创建个人磁盘目录:" + SystemConfig.getParam("erp.netdisk.path"));
		}

		String defaultNoImgPath = SystemConfig.getParam("erp.upload.imgSavePath") + "noimg.gif";
		if (!FileTool.getIsFile(defaultNoImgPath)) {
			String systemImg = servletContext.getRealPath("/images/noimg.gif");
			FileTool.saveFileToFile(systemImg, defaultNoImgPath);
			logger.info("复制暂无图片文件...");
		}
	}

	// 存放未读短消息
	private void createSmsBox(javax.servlet.ServletContext servletContext) {
		WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		IMoblieSmsService moblieSmsService = (IMoblieSmsService) webAppContext.getBean("moblieSmsService");
		Map<String, Integer> map = moblieSmsService.getNoReadSmsCountGroupEmp();
		servletContext.setAttribute(ConstWords.servletContext_MSGBOX, map);
	}
}
