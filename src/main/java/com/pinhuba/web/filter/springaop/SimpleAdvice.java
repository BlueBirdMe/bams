package com.pinhuba.web.filter.springaop;

import javax.servlet.http.HttpServletRequest;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.pinhuba.common.module.SessionUser;
import com.pinhuba.common.util.LoginContext;
import com.pinhuba.core.pojo.HrmEmployee;

/**
 * 
 * @author Frin
 * @description
 * @more
 */
public class SimpleAdvice implements MethodInterceptor {
	private final static Logger logger = LoggerFactory.getLogger(SimpleAdvice.class);

	/**
	 * 对方法进行全面跟踪
	 */
	public Object invoke(MethodInvocation invocation) throws Throwable {
		WebContext ctx = WebContextFactory.get();

		if (ctx != null) {
			HttpServletRequest request = ctx.getHttpServletRequest();

			long methodStart = System.currentTimeMillis();
			
			SessionUser sessionUser = (SessionUser) LoginContext.getSessionValueByLogin(request);
			String className = invocation.getThis().getClass().getSimpleName();
			String methodName = invocation.getMethod().getName();

			String name = "中断用户";
			HrmEmployee employee =sessionUser.getEmployeeInfo();
			if (employee != null) {
				name = employee.getHrmEmployeeName();
			}
			logger.info("{} 开始,IP:{},类名:{},方法名:{}",name,request.getRemoteAddr(),className,methodName);

			Object methodReturn = invocation.proceed();

			//logger.info("{},IP:{},类名:{},方法名:{},耗时:{}毫秒",name,request.getRemoteAddr(),className,methodName,System.currentTimeMillis() - methodStart);
			logger.info("{} 结束,耗时:{}毫秒\n",name,System.currentTimeMillis() - methodStart);
			
			return methodReturn;
		} else {
			String className = invocation.getThis().getClass().getSimpleName();
			String methodName = invocation.getMethod().getName();
			
			long methodStart = System.currentTimeMillis();
			logger.info("类名:{},方法名:{}",className,methodName);

			Object methodReturn = invocation.proceed();

			logger.info("耗时:{}毫秒\n",System.currentTimeMillis() - methodStart);
			return methodReturn;
		}
	}
}
