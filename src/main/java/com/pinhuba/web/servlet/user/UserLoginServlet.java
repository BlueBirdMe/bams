package com.pinhuba.web.servlet.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.pinhuba.common.module.SessionUser;
import com.pinhuba.common.util.ConstWords;
import com.pinhuba.common.util.EnumUtil;
import com.pinhuba.common.util.LoginContext;
import com.pinhuba.common.util.UtilWork;
import com.pinhuba.common.util.file.FileTool;
import com.pinhuba.common.util.file.properties.SystemConfig;
import com.pinhuba.core.iservice.ISysProcessService;
import com.pinhuba.core.iservice.IUserLoginService;
import com.pinhuba.core.pojo.SysCompanyInfo;
import com.pinhuba.core.pojo.SysLog;
import com.pinhuba.core.pojo.SysMethodInfo;
import com.pinhuba.core.pojo.SysUserInfo;
import com.pinhuba.web.controller.dwr.DwrOADesktopService;
import com.pinhuba.web.listener.OnlineUserBindingListener;
import com.pinhuba.web.servlet.ServletServiceController;

public class UserLoginServlet extends ServletServiceController {
	private Logger log =Logger.getLogger(this.getClass());
		
	private static final long serialVersionUID = 2560424066821933328L;
	public UserLoginServlet() {
		super();
	}

	public void destroy() {
		super.destroy(); 
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path ="login.jsp";
		response.setContentType("text/html");
		IUserLoginService userLoginservice =this.getUserLoginService();
		ISysProcessService sysProcessService =this.getSysProcessService();
		String companyCode =request.getParameter("companycode").toUpperCase();
		String userName =request.getParameter("username");
		String userPwd =request.getParameter("userpwd");
		String code = request.getParameter("code");//验证码
		
		//根据配置文件  是否对验证码 进行判断
		if(Boolean.parseBoolean(SystemConfig.getParam("erp.sys.CodeIsview"))){
			if (request.getSession().getAttribute(ConstWords.ValidCodeTempSession)==null) {
				request.setAttribute(ConstWords.TempStringMsg, "验证码失效，请重新登录！");
				request.getRequestDispatcher(path).forward(request, response);
				return;
			}
			
			String sessioncode = (String) request.getSession().getAttribute(ConstWords.ValidCodeTempSession);
			if (!sessioncode.equalsIgnoreCase(code)) {
				request.setAttribute(ConstWords.TempStringMsg, "验证码输入错误！");
				request.getRequestDispatcher(path).forward(request, response);
				return;
			}
		}
		
		//对公司码开始有效期验证
		SysCompanyInfo companyInfo = userLoginservice.vaildityCompany(companyCode);
		if (companyInfo==null) {
			request.setAttribute(ConstWords.TempStringMsg, "公司码不存在或者已过期！");
			request.getRequestDispatcher(path).forward(request, response);
		}else {
			SysUserInfo userInfo = userLoginservice.vaildityUserInfo(companyInfo, userName, userPwd);
			if (userInfo == null) {
				request.setAttribute(ConstWords.TempStringMsg, "用户名或密码输入错误！");
				request.getRequestDispatcher(path).forward(request, response);
			}else{
				//创建平台session
				SessionUser sUser = userLoginservice.packageUserInfo(companyCode, userName, ConstWords.getProjectCode());
				
				//用户是否包含权限
				if (sUser.getUserMethodsSet().size()==0) {
					request.setAttribute(ConstWords.TempStringMsg, "用户没有权限，请联系管理员！");
					request.getRequestDispatcher(path).forward(request, response);
					return;
				}
				
				LoginContext.SetSessionValueByLogin(request, sUser);
				
				//如果是超级管理员转到开发后台
				if(sUser.getCompanyInfo().getCompanyInfoType() == EnumUtil.SYS_COMPANY_TYPE.SYSTEM.value
					&& sUser.getUserInfo().getUserType() == EnumUtil.SYS_USER_TYPE.SYSTEM.value){
					response.sendRedirect("erp/system_manger/index.jsp");
					return;
				}
			
				//登录加载
				this.loginInit(sysProcessService,sUser,request);
				//进行转向
				SysMethodInfo forwardMethodInfo = sUser.getUserModuleMethods().get(0);
				SysMethodInfo defaultMethodInfo = userLoginservice.getMethodInfoByPk(ConstWords.getProjectCode());//平台页面
				String name = forwardMethodInfo.getMethodInfoName();
				response.sendRedirect(defaultMethodInfo.getDefaultPage()+"?mid="+forwardMethodInfo.getPrimaryKey());
				
				//写入在线人员
				HttpSession session  =request.getSession(true);
				session.setAttribute(ConstWords.OnLineUser_Sign, new OnlineUserBindingListener(sUser.getEmployeeInfo().getPrimaryKey() ,(int)sUser.getCompanyId(),request.getSession().getId()));
				//写入日志
				SysLog sysLog =new SysLog();
				long uid =sUser.getUserInfo().getPrimaryKey();
				sysLog.setUserId(Integer.parseInt(uid+""));
				sysLog.setCompanyId(Integer.parseInt(sUser.getCompanyId()+""));
				sysLog.setLogDate(UtilWork.getNowTime());
				sysLog.setLogDetail("登录系统:"+name);
				sysLog.setRequestAddr(request.getRemoteAddr());
				sysProcessService.saveSysLog(sysLog);
			}
		}
	}

	public void init() throws ServletException {
		super.init();
		super.setContext(this.getServletContext());
	}

	/**
	 * 登录加载内容
	 * @param sUser
	 * @param request
	 * @throws IOException
	 */
	private void loginInit(ISysProcessService sysProcessService,SessionUser sUser,HttpServletRequest request) throws IOException{
		//公司编码
		Integer companyId = (int)sUser.getCompanyId();
		
		//创建个人磁盘文件夹
		String personalPath = SystemConfig.getParam("erp.netdisk.path") + ConstWords.septor + companyId + ConstWords.septor + sUser.getUserInfo().getHrmEmployeeId();
		FileTool.checkDirAndCreate(personalPath);
		log.info("创建个人磁盘:"+personalPath);
		
		//创建个人桌面
		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		DwrOADesktopService desktopService = (DwrOADesktopService) context.getBean("dwrOADesktopService");
		desktopService.createOaDesktop(this.getServletContext(), request);
		log.info("加载个人桌面");
	}
	
}
