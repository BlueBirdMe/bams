package com.pinhuba.web.filter.springaop;

import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;

import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.proxy.dwr.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.ThrowsAdvice;
import com.pinhuba.common.module.SessionUser;
import com.pinhuba.common.util.EnumUtil;
import com.pinhuba.common.util.LoginContext;
import com.pinhuba.common.util.UtilWork;
import com.pinhuba.core.dao.ISysExceptionDao;
import com.pinhuba.core.pojo.HrmEmployee;
import com.pinhuba.core.pojo.SysException;

	public class ExceptionCatcherAdvice implements ThrowsAdvice {
		private final static Logger logger = LoggerFactory.getLogger(ExceptionCatcherAdvice.class);
		
		private ISysExceptionDao sysExceptionDao;
		
		public void setSysExceptionDao(ISysExceptionDao sysExceptionDao) {
			this.sysExceptionDao = sysExceptionDao;
		}
		
	    public void afterThrowing(Method method, Object[] args, Object target,
	            Exception throwable) throws Throwable {
	    	
	    	WebContext ctx = WebContextFactory.get();
	    	int userId =-1;
		    int companyId= -1;
		    String exceptionClass = throwable.fillInStackTrace().toString();//异常类型
		    String className = target.getClass().getName();
		    String methodName = method.getName();
		    String exceptionMsg = throwable.getMessage()+ "," + throwable.getCause();  
		    StringBuffer sb =new StringBuffer(); 
		    for(int i=0;i<throwable.getStackTrace().length;i++){
		    	sb.append(throwable.getStackTrace()[i]);
		    }
		    
	    	if (ctx != null) {
	    		HttpServletRequest request = ctx.getHttpServletRequest();
	    		
		    	SessionUser sessionUser = (SessionUser) LoginContext.getSessionValueByLogin(request);
				String name = "中断用户";
				HrmEmployee employee =sessionUser.getEmployeeInfo();
				if (employee != null) {
					name = employee.getHrmEmployeeName();
				}
				
		        //for(Object o:args){  
		        //    System.out.println("方法的参数：   " + o.toString());  
		        //}  
				
				if(sessionUser != null){
		        	userId = Integer.parseInt(sessionUser.getUserInfo().getPrimaryKey()+"");
		        	companyId = Integer.parseInt(sessionUser.getCompanyId()+"");
		        }
				
				Util util = new Util(ctx.getScriptSession());
				util.addScript(new ScriptBuffer("errormsg()"));
				
		        logger.error("{},IP:{},异常:{},类名:{},方法名:{}",name,request.getRemoteAddr(),exceptionClass,className,methodName);
	    	
	    	}else{
	    		logger.error("异常:{},类名:{},方法名:{}",exceptionClass,className,methodName);
	    	}
	    	
    		SysException sException = new SysException();
	        
	        sException.setUserId(userId);
	        sException.setCompanyId(companyId);
	        sException.setExceptionClass(exceptionClass);
	        sException.setExceptionDate(UtilWork.getNowTime());
	        sException.setExceptionMsg(exceptionMsg);
	        sException.setExceptionContext(sb.toString());
	        sException.setExceptionStatus(EnumUtil.SYS_EXCEPTION_STATUS.Vaild.value);
	        sysExceptionDao.save(sException);
	        
	    }
	
	}
