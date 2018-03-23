package com.pinhuba.common.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.web.util.WebUtils;
/**
 * 对session操作的封装
 * @author peng.ning
 *
 */
public class LoginContext {
	private static final String Login_Session_Name ="LoginSession";
	
	/**
	 * 获取登录对象
	 * @return
	 */
	public static  Object getSessionValueByLogin(HttpServletRequest request){
		if (request != null) {
			Object obj = WebUtils.getSessionAttribute(request, Login_Session_Name);
			return obj;
		}
		return null;
	}
	/**
	 * 获取登录对象
	 * @return
	 */
	public static  Object getSessionValueByLogin(HttpSession session){
		if (session != null) {
			return session.getAttribute(Login_Session_Name);
		}
		return null;
	}
	
	/**
	 * 根据名字获取session中存放对象
	 * @param sessionName
	 * @return
	 */
	public static  Object getSessionValue(HttpServletRequest request,String sessionName){
		if (request != null) {
			return WebUtils.getSessionAttribute(request,sessionName);
		}
		return null;
	}
	
	public static  Object getSessionValue(HttpSession session,String sessionName){
		if (session != null) {
			return session.getAttribute(sessionName);
		}
		return null;
	}
	/**
	 * 将登录对象放入session
	 * @param obj
	 */
	public static void SetSessionValueByLogin(HttpServletRequest request,Object obj){
		if (request != null) {
			WebUtils.setSessionAttribute(request,Login_Session_Name,obj);
		}
	}
	
	public static void SetSessionValueByLogin(HttpSession session,Object obj){
		if (session != null) {
			session.setAttribute(Login_Session_Name,obj);
		}
	}
	
	/**
	 * 将对象放入指定session
	 * @param sessionName
	 * @param obj
	 */
	public static void setSessionValue(HttpServletRequest request,String sessionName,Object obj){
		if (request != null) {
			WebUtils.setSessionAttribute(request,sessionName,obj);
		}
	}
	
	/**
	 * 将对象放入指定session
	 * @param sessionName
	 * @param obj
	 */
	public static void setSessionValue(HttpSession session,String sessionName,Object obj){
		if (session != null) {
			session.setAttribute(sessionName,obj);
		}
	}
	/**
	 * 清除登录session
	 */
	public static  void removeSessionByLogin(HttpServletRequest request){
		if (request != null) {
			request.getSession(true).removeAttribute(Login_Session_Name);
		}
	}
	public static void removeSessionByLogin(HttpSession session){
		if (session != null) {
			session.removeAttribute(Login_Session_Name);
		}
	}
	/**
	 * 清除指定session
	 * @param sessionName
	 */
	public static void removeSession(HttpServletRequest request,String sessionName){
		if (request != null) {
			request.getSession(true).removeAttribute(sessionName);
		}
	}
	/**
	 * 清除指定session
	 * @param sessionName
	 */
	public static  void removeSession(HttpSession session,String sessionName){
		if (session != null) {
			session.removeAttribute(sessionName);
		}
	}
}
