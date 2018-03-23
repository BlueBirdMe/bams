package com.pinhuba.web.fckeditor;

import javax.servlet.http.HttpServletRequest;

import com.pinhuba.common.util.UtilTool;

import net.fckeditor.requestcycle.impl.ContextPathBuilder;

/**
 * 用户目录管理
 * 
 * @author peng.ning
 * 
 */
public class CustomerContextPathBuilder extends ContextPathBuilder {

	@Override
	public String getUserFilesPath(HttpServletRequest request) {
		String s = super.getUserFilesPath(request) + UtilTool.getCompanyAndUserPath(request, true);
		return s;
	}

	@Override
	public String getUserFilesAbsolutePath(HttpServletRequest request) {
		String s = super.getUserFilesAbsolutePath(request) + UtilTool.getCompanyAndUserPath(request, true);
		return s;
	}

}
