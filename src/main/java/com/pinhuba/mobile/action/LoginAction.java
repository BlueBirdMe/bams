package com.pinhuba.mobile.action;

import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import com.pinhuba.common.module.SessionUser;
import com.pinhuba.common.util.ConstWords;
import com.pinhuba.common.util.ResponseUtils;
import com.pinhuba.core.iservice.IUserLoginService;
import com.pinhuba.core.pojo.SysCompanyInfo;
import com.pinhuba.core.pojo.SysUserInfo;
import com.pinhuba.web.listener.OnlineUserBindingListener;

@Controller
@RequestMapping("/mobile/")
public class LoginAction {

	@Resource
	private IUserLoginService userLoginservice;

	@RequestMapping(value = "loginCheck.do")
	public void loginCheck(String companyCode, String username, String password, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException {

		String errorMsg = "";
		String name = "";

		JSONObject json = new JSONObject();

		// 对公司码开始有效期验证
		SysCompanyInfo companyInfo = userLoginservice.vaildityCompany(companyCode);

		if (companyInfo == null) {
			errorMsg = "公司码不存在或者已过期！";
		} else {
			SysUserInfo userInfo = userLoginservice.vaildityUserInfo(companyInfo, username, password);

			if (userInfo == null) {
				errorMsg = "用户名或密码输入错误！";
			} else {
				// 创建平台session
				SessionUser sUser = userLoginservice.packageUserInfo(companyCode, username, ConstWords.getProjectCode());

				// 用户是否包含权限
				if (sUser.getUserMethodsSet().size() == 0) {
					errorMsg = "用户没有权限，请联系管理员！";
				} else {
					name = sUser.getEmployeeInfo().getHrmEmployeeName();

					// 写入在线人员
					HttpSession session = request.getSession(true);
					session.setAttribute(ConstWords.OnLineUser_Sign, new OnlineUserBindingListener(sUser.getEmployeeInfo().getPrimaryKey(), (int) sUser.getCompanyId(), request.getSession().getId()));
				}
			}
		}
		if (StringUtils.isNotBlank(errorMsg)) {
			json.put("code", 1);
			json.put("errorMsg", errorMsg);
		} else {
			json.put("code", 0);
			json.put("name", name);
			json.put("sessionId", request.getSession().getId());
		}
		ResponseUtils.renderJson(response, json.toString());
	}
}
