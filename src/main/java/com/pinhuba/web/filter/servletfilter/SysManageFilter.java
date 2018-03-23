package com.pinhuba.web.filter.servletfilter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.pinhuba.common.module.SessionUser;
import com.pinhuba.common.util.EnumUtil;
import com.pinhuba.common.util.LoginContext;
import com.pinhuba.web.filter.springaop.ExceptionCatcherAdvice;

public class SysManageFilter implements Filter {
	private final static Logger logger = LoggerFactory.getLogger(ExceptionCatcherAdvice.class);

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		SessionUser sessionUser = (SessionUser) LoginContext.getSessionValueByLogin(httpRequest);
		boolean permissionFlag = false;
		if (sessionUser.getCompanyInfo().getCompanyInfoType() == EnumUtil.SYS_COMPANY_TYPE.SYSTEM.value && 
				sessionUser.getUserInfo().getUserType() == EnumUtil.SYS_USER_TYPE.SYSTEM.value) {
			
			permissionFlag = true;
		}

		if (permissionFlag) {
			chain.doFilter(httpRequest, httpResponse);
		} else {
			logger.error("{},IP:{},访问开发后台,没有权限....", sessionUser.getEmployeeName(), request.getRemoteAddr());
			httpResponse.sendRedirect(httpRequest.getContextPath() + "/error.jsp");
		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}
}
