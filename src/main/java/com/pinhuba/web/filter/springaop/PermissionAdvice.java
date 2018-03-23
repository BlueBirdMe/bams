package com.pinhuba.web.filter.springaop;

import java.io.UnsupportedEncodingException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

import com.pinhuba.common.module.SessionUser;
import com.pinhuba.common.util.LoginContext;
import com.pinhuba.core.dao.ISysLogDao;
import com.pinhuba.core.pojo.HrmEmployee;

/**
 * 
 * @author AXDPV
 * @description 用户权限认证，需要获取容器WebApplicationContext
 * @more 1，出现异常，记录日志文件，并需要定时发送邮件 2，没有异常的，进行方法的日志记录，存入数据库
 */
public class PermissionAdvice implements MethodInterceptor {
	private Logger logger = Logger.getLogger(this.getClass());
	private ISysLogDao sysLogDao;

	public void setSysLogDao(ISysLogDao sysLogDao) {
		this.sysLogDao = sysLogDao;
	}

	/**
	 * 用户权限验证
	 */
	public Object invoke(MethodInvocation invocation) throws Throwable {
		WebContext ctx = WebContextFactory.get();
		
		ServletContext context = ctx.getServletContext();
		HttpServletRequest request = ctx.getHttpServletRequest();
		HttpServletResponse response = ctx.getHttpServletResponse();

		// 改变字符编码
		this.setEncoding(request, response);
		long methodStart = System.currentTimeMillis();
		SessionUser sessionUser = (SessionUser) LoginContext.getSessionValueByLogin(request);
		String className = invocation.getThis().getClass().getSimpleName();
		String methodName = invocation.getMethod().getName();

		logger.info("["+methodStart+"开始]:class:" + className + "-method:" + methodName);
//		new MethodCollection(ctx.getServletContext());
//		
//		MethodInfo methodInfo = MethodCollection.getMethodInfo(className,
//				methodName);
//
//		// 请求方法是否被允许
//		if (methodInfo == null || methodInfo.getIsActive() != 1) {
//			throw new RuntimeException(ErrorCollection.getErrorInfo(
//					"Service-001").getErrorInfo());
//		}
//
//		// 验证帐号状态
//		//int operate_status = checkPermission(session, methodInfo);
//		if (operate_status == User_Operate_Status.NEED_LOGIN.value) {
//			throw new RuntimeException(ErrorCollection.getErrorInfo("USER-002")
//					.getErrorInfo());
//		}
//
//		logger.info("用户[" + employee.getName() + ",ip:" + request.getRemoteAddr()+ "]："
//				+ methodInfo.getOperate());
//
//		if (operate_status == User_Operate_Status.UNDER_LEVEL.value){
//			throw new RuntimeException(ErrorCollection.getErrorInfo("Level-001").getErrorInfo());
//		}
//
//		// 方法返回
//		Object methodReturn = null;
//		//权限通过，进行操作
//		if (operate_status == User_Operate_Status.CAN_USE.value) {
//			methodReturn = invocation.proceed();
//
//			// 调用方法
//			logger.info("["+methodStart+"结束]:" + "用户[" + employee.getName() + "]"
//					+ methodInfo.getOperate() + ",耗时："
//					+ (System.currentTimeMillis() - methodStart) + "毫秒");
//			
//			if(methodInfo.getMethodType() != Operation_Status.QUERY.value && methodInfo.getMethodLevel() != -9){
//				//cimsLogDao.saveLog(employee, methodInfo, (ErrorInfo)session.getAttribute("ErrorInfo"));
//			}
//			session.removeAttribute("ErrorInfo");
//		} else {
//			// 无权操作
//			throw new RuntimeException(ErrorCollection.getErrorInfo("EXC-001")
//					.getErrorInfo());
//		}

		return null;
	}

	/**
	 * 编码过滤
	 * 
	 * @param request
	 * @param response
	 */
	private void setEncoding(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("Can't change encoding to UTF-8.Exception message:"
					+ e.getMessage());
		}
	}

//	/**
//	 * 用户权限过滤
//	 * 
//	 * @param session
//	 * @param method
//	 * @return
//	 */
//	private int checkPermission(HttpSession session, MethodInfo methodInfo) {
//		Employee employee = (Employee) session.getAttribute("employee");
//
//		// session过期，需要重新登陆
//		if (employee == null) {
//			return User_Operate_Status.NEED_LOGIN.value;
//		}

//		// 帐户停用
////		if (employee.getUsers().getIsActive().intValue() == Users_Is_Active_Status.IS_NOT_ACTIVE.value) {
////			return User_Operate_Status.IS_NOT_ACTIVE.value;
////		}
//
//		//EmployeeFunction function = employee.getEmployeeFunctions().iterator().next();
//		// 用户权限为空
//		if (function.getDetail() == null || function.getDetail().length() == 0) {
//			return User_Operate_Status.IS_NOT_ACTIVE.value;
//		}
//		
//		//其他方法，任何人可以调用
//		if (methodInfo.getMethodType() == Operation_Status.Others.value || methodInfo.getMethodLevel() == Method_Level.All.value) {
//			return User_Operate_Status.CAN_USE.value;
//		}
//
//		if (function.getDetail() != null && function.getDetail().length() > 0) {
//			// 进行权限验证
//			String[] methodPk = function.getDetail().split(",");
//			
//			for(String pkStr : methodPk){
//				if(Long.parseLong(pkStr) == methodInfo.getPrimaryKey()){
//					return User_Operate_Status.CAN_USE.value;
//				}
//			}
//			
//			return User_Operate_Status.UNDER_LEVEL.value;
//		} else {
//			return User_Operate_Status.UNDER_LEVEL.value;
//		}
//	}

}
