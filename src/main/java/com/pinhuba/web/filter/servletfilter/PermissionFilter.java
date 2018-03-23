package com.pinhuba.web.filter.servletfilter;

import java.io.IOException;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.pinhuba.common.module.SessionUser;
import com.pinhuba.common.util.LoginContext;
import com.pinhuba.common.util.StringTool;
import com.pinhuba.core.pojo.SysMethodInfo;
import com.pinhuba.web.controller.dwr.DwrSysProcessService;
import com.pinhuba.web.filter.springaop.ExceptionCatcherAdvice;

public class PermissionFilter implements Filter {
	private final static Logger logger = LoggerFactory.getLogger(ExceptionCatcherAdvice.class);

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		SessionUser sessionUser =  (SessionUser) LoginContext.getSessionValueByLogin(httpRequest);
	
		if(sessionUser == null){
			logger.info("系统尚未登录....");
			httpResponse.sendRedirect(httpRequest.getContextPath()+"/centerSend.jsp");
			return;
		}
		
		//获取当前访问的页面名称
		String tmpUrl = httpRequest.getRequestURL().toString();
		String page = StringTool.getPageName(tmpUrl);
		ServletContext context = httpRequest.getSession().getServletContext();
		WebApplicationContext webctx = WebApplicationContextUtils.getWebApplicationContext(context);
		DwrSysProcessService service = (DwrSysProcessService)webctx.getBean("dwrSysProcessService");
		//根据页面名称获取相关功能菜单
		List<SysMethodInfo> list = service.listSysmethodInfoByPage(context,httpRequest,page);
		
		boolean permissionFlag = false;
		
		//如果没有页面相关的功能菜单，可以访问。
		if(list.size() == 0){
			permissionFlag = true;
		}
		//如果用户有权限的功能菜单包含访问页面相关的功能菜单，可以访问。
		for (SysMethodInfo sysMethodInfo : list) {
			if(sessionUser.getUserMethodsSet().contains(sysMethodInfo.getPrimaryKey())){
				permissionFlag = true;
			}
		}

		if(permissionFlag){
			chain.doFilter(httpRequest, httpResponse);
		}else{
			logger.error("{},IP:{},访问页面:{},没有权限....",sessionUser.getEmployeeName(),request.getRemoteAddr(),page);
			httpResponse.sendRedirect(httpRequest.getContextPath()+"/error.jsp");
		}
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}
}