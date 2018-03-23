package com.pinhuba.web.servlet.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pinhuba.common.util.ConstWords;
import com.pinhuba.common.util.EnumUtil;
import com.pinhuba.common.util.UtilWork;
import com.pinhuba.core.iservice.ISysProcessService;
import com.pinhuba.core.pojo.SysCompanyInfo;
import com.pinhuba.web.servlet.ServletServiceController;
/**
 * 公司注册处理
 * @author peng.ning
 * @date   May 10, 2010
 */
public class CompanyRegServlet extends ServletServiceController {


	private static final long serialVersionUID = -6069284009782165505L;


	public CompanyRegServlet() {
		super();
	}

	
	public void destroy() {
		super.destroy(); 
	}

	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		String path ="other/company_reg.jsp";
		String regOkPath = "other/company_regok.jsp";
		
		ISysProcessService sysProcessService = getSysProcessService();
		
		
		String companyname = request.getParameter("companyname");
		String companyjcname = request.getParameter("companyjcname");
		String province = request.getParameter("province");
		String city = request.getParameter("city");
		String area = request.getParameter("area");
		String companyadder = request.getParameter("companyadder");
		String companypost = request.getParameter("companypost");
		String companyperson = request.getParameter("companyperson");
		String companyjob = request.getParameter("companyjob");
		String companyphone = request.getParameter("companyphone");
		String companyfax = request.getParameter("companyfax");
		String companymail = request.getParameter("companymail");
		String methods = request.getParameter("methods");
		String code = request.getParameter("validcode");
		String codeName = request.getParameter("validcodeName");
		
		if (request.getSession().getAttribute(codeName)==null) {
			request.setAttribute(ConstWords.TempStringMsg, "验证码失效，请更换验证码！");
			request.getRequestDispatcher(path).forward(request, response);
			return;
		}
		
		String sessioncode = (String) request.getSession().getAttribute(codeName);
		if (!sessioncode.equalsIgnoreCase(code)) {
			request.setAttribute(ConstWords.TempStringMsg, "验证码输入错误！");
			request.getRequestDispatcher(path).forward(request, response);
			return;
		}
		
		SysCompanyInfo company = new SysCompanyInfo();
		company.setCompanyInfoName(companyname);
		company.setCompanyInfoShortname(companyjcname);
		company.setProvinceCode(province);
		company.setDistrictCode(city);
		company.setCompanyArea(area);
		company.setCompanyInfoAdder(companyadder);
		company.setCompanyInfoPost(companypost);
		company.setCompanyInfoEmployee(companyperson);
		company.setCompanyInfoEmployeePosition(companyjob);
		company.setCompanyInfoEmail(companymail);
		company.setCompanyInfoPhone(companyphone);
		company.setCompanyInfoFax(companyfax);
		company.setCompanyInfoStatus(EnumUtil.SYS_COMPANY_STATUS.APPROVE.value);
		company.setCompanyInfoType(EnumUtil.SYS_COMPANY_TYPE.APPROVE.value);
		company.setCompanyInfoRegDate(UtilWork.getNowTime());
		company.setCompanyInfoLastDate(UtilWork.getNowTime());
		
		String[] ms = methods.split(","); 
		
		
		SysCompanyInfo tmp = sysProcessService.saveCompanyInfo(company, ms);
		
		if (tmp!=null) {
			request.setAttribute(ConstWords.TempStringMsg, tmp);
			request.getRequestDispatcher(regOkPath).forward(request, response);
		}else{
			request.setAttribute(ConstWords.TempStringMsg, "注册公司错误,请联系管理员！");
			request.getRequestDispatcher(path).forward(request, response);
		}
	}

	public void init() throws ServletException {
		super.init();
		super.setContext(this.getServletContext());
	}

}
