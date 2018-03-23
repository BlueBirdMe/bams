package com.pinhuba.web.listener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import com.pinhuba.common.util.ConstWords;
/**
 * 记录在线人员
 * @author peng.ning
 * @date   Apr 28, 2010
 */
public class OnlineUserBindingListener implements HttpSessionBindingListener {

	private String employeeId;
	private Integer companyId;
	private String sessionId;

	public OnlineUserBindingListener(String employeeId, Integer companyId,String sessionId) {
		super();
		this.employeeId = employeeId;
		this.companyId = companyId;
		this.sessionId = sessionId;
	}

	@SuppressWarnings("unchecked")
	public void valueBound(HttpSessionBindingEvent event) {
		HttpSession session = event.getSession();
		ServletContext application = session.getServletContext();
		Map<Integer, Set<String>> onlineMap = (Map<Integer, Set<String>>) application.getAttribute(ConstWords.ServletContext_OnLineUser);
		if (onlineMap==null) {
			onlineMap = new HashMap<Integer, Set<String>>();
			application.setAttribute(ConstWords.ServletContext_OnLineUser, onlineMap);
		}
		if (onlineMap.containsKey(this.companyId)) {
			Set<String> employeeIdList = onlineMap.get(this.companyId);
			employeeIdList.add(this.employeeId);
		}else{
			Set<String> employeeIdList = new HashSet<String>();
			employeeIdList.add(this.employeeId);
			onlineMap.put(this.companyId, employeeIdList);
		}
		ConstWords.OnlineUserSessionIdMap.put(this.employeeId, this.sessionId);
	}

	@SuppressWarnings("unchecked")
	public void valueUnbound(HttpSessionBindingEvent event) {
		HttpSession session = event.getSession();
		ServletContext application = session.getServletContext();
		Map<Integer, Set<String>> onlineMap = (Map<Integer, Set<String>>) application.getAttribute(ConstWords.ServletContext_OnLineUser);
		if (onlineMap!=null&&onlineMap.containsKey(this.companyId)) {
			Set<String> employeeIdList = onlineMap.get(this.companyId);
			employeeIdList.remove(this.employeeId);
		}
//		if (ConstWords.OnlineUserSessionIdMap.containsKey(this.employeeId)) {
//			ConstWords.OnlineUserSessionIdMap.remove(this.employeeId);
//		}
	}
	/**
	 * 根据公司编号取得在线人员主键集合
	 * @param companyId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Set<String> getOnlineList(ServletContext context,Integer companyId){
		Map<Integer, Set<String>> onlineMap = (Map<Integer, Set<String>>) context.getAttribute(ConstWords.ServletContext_OnLineUser);
		Set<String> empset = new HashSet<String>();
		if (onlineMap!=null&&onlineMap.containsKey(companyId)) {
			empset = onlineMap.get(companyId);
		}
		return empset;
	}
	
	/**
	 * 验证用户是否已登录
	 * @param context
	 * @param companyId
	 * @param empId
	 * @return
	 */
	public static boolean isOnlineByEmployeeId(ServletContext context,Integer companyId,String empId){
		boolean bl = false;
		Map<Integer, Set<String>> onlineMap = (Map<Integer, Set<String>>) context.getAttribute(ConstWords.ServletContext_OnLineUser);
		if (onlineMap!=null&&onlineMap.containsKey(companyId)) { 
			Set<String> empset = onlineMap.get(companyId);
			if (empset.contains(empId)) {
				bl = true;
			}
		}
		return bl;
	}
	
	/**
	 * 验证访问用户是否同一处登录用户
	 * @param context
	 * @param empId
	 * @param rqSessionId
	 * @return false 否 true 是
	 */
	public static boolean isEqualsSessionId(ServletContext context,String empId,String rqSessionId){
		boolean bl = false;
		if (ConstWords.OnlineUserSessionIdMap.containsKey(empId)) {
			String sid = ConstWords.OnlineUserSessionIdMap.get(empId);
			if (sid.equalsIgnoreCase(rqSessionId)) {
				bl = true;
			}
		}
		return bl;
	}
}
