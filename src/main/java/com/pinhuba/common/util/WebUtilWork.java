package com.pinhuba.common.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.pinhuba.common.module.ResultBean;
import com.pinhuba.common.pages.Pager;

/**
 * Web封装常用方法
 * 
 * @author Administrator
 * 
 */

public class WebUtilWork {
	private static final Logger logger = Logger.getLogger(WebUtilWork.class);
	
	/**
	 * ResultBean封装类，在Permission权限验证处，将ErrorInfo添加至HttpSession,然后返回给前台
	 * 
	 * @param dbResult
	 *            需要封装的对象的值，可为List或者Object
	 * @param pager
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static ResultBean WebResultPack(Object dbResult, Pager pager) {
		ResultBean resultBean = null;
		if (dbResult instanceof List && dbResult != null) {
			if (((List) dbResult).size() > 0) {
				resultBean = new ResultBean(true, (List) dbResult, pager);
			} else {
				resultBean = new ResultBean(true, "没有相关信息(No records)", new ArrayList(),pager);
			}
		} else {
			resultBean = new ResultBean(true, "操作执行成功(Success).");
		}

		return resultBean;
	}
	
	public static ResultBean WebResultPack(Object dbResult) {
		ResultBean resultBean = null;
		 if(dbResult instanceof List && dbResult != null) {
			if (((List) dbResult).size() > 0) {
				resultBean = new ResultBean(true, (List) dbResult);
			} else {
				resultBean = new ResultBean(true, "没有相关信息(No records)", new ArrayList());
			}
		} else {
			resultBean = new ResultBean(true, "操作执行成功(Success).");
		}

		return resultBean;
	}
	
	
	@SuppressWarnings("unchecked")
	public static ResultBean WebObjectPack(Object obj) {
		ResultBean resultBean = null;
		if (obj != null) {
			ArrayList list = new ArrayList();
			list.add(obj);
			resultBean = new ResultBean(true, "操作执行成功(Success).", list);
		} else {
			resultBean = new ResultBean(false, "操作执行异常(Error).");
		}

		return resultBean;
	}

	/*public static void errorWork(HttpSession session, Exception e) {		
		StackTraceElement[] stackTraceElements = e.getStackTrace();
		StringBuffer sb = new StringBuffer();
		sb.append(e.getClass().getName() + "\n");
		for (StackTraceElement element : stackTraceElements) {
			sb.append(element + "\n\t");
		}
	}*/
}
