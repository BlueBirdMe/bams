package com.pinhuba.web.fckeditor;

import javax.servlet.http.HttpServletRequest;

import net.fckeditor.requestcycle.UserAction;

/**
 * 用户文件操作权限
 * 
 * @author peng.ning
 * 
 */
public class CustomerUserAction implements UserAction {

	// 权限--新建文件夹
	public boolean isCreateFolderEnabled(HttpServletRequest request) {
		return true;
	}

	// 权限--浏览服务器上的文件列表
	public boolean isEnabledForFileBrowsing(HttpServletRequest request) {
		return true;
	}

	// 权限--上传文件
	public boolean isEnabledForFileUpload(HttpServletRequest request) {
		return true;
	}

}
