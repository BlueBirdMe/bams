package com.pinhuba.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.pinhuba.common.util.ConstWords;
import com.pinhuba.core.iservice.IHrmEmployeeService;
import com.pinhuba.core.pojo.HrmEmployee;
import com.pinhuba.common.module.ApproveProcessBean;
import com.pinhuba.common.module.ResultBean;
import com.pinhuba.common.module.SessionUser;
import com.pinhuba.common.pages.Pager;
import com.pinhuba.core.iservice.IMoblieSmsService;
import com.pinhuba.core.pojo.HrmContractType;
import com.pinhuba.core.pojo.HrmDepartment;
import com.pinhuba.core.pojo.HrmWorkarea;
import com.pinhuba.core.pojo.OaAlbum;
import com.pinhuba.core.pojo.OaBoardroom;
import com.pinhuba.core.pojo.OaBookType;
import com.pinhuba.core.pojo.OaChatGroups;
import com.pinhuba.core.pojo.OaDesktopSet;
import com.pinhuba.core.pojo.OaForums;
import com.pinhuba.core.pojo.OaJournalsType;
import com.pinhuba.core.pojo.OaNetmailSet;
import com.pinhuba.core.pojo.OaSmsInbox;
import com.pinhuba.core.pojo.OaWareType;
import com.pinhuba.core.pojo.SysAttachmentInfo;
import com.pinhuba.core.pojo.SysColumnControl;
import com.pinhuba.core.pojo.SysConfig;
import com.pinhuba.core.pojo.SysImageInfo;
import com.pinhuba.core.pojo.SysLibraryInfo;
import com.pinhuba.core.pojo.SysMethodBtn;
import com.pinhuba.core.pojo.SysMethodHelp;
import com.pinhuba.core.pojo.SysMethodInfo;
import com.pinhuba.core.pojo.SysProcessType;
import com.pinhuba.core.pojo.SysRoleBtn;
import com.pinhuba.core.pojo.SysRoleDetail;
import com.pinhuba.core.pojo.SysUserBtns;
import com.pinhuba.core.pojo.SysUserInfo;
import com.pinhuba.core.pojo.SysUserMethods;
import com.pinhuba.web.controller.dwr.DwrApproveProcessService;
import com.pinhuba.web.controller.dwr.DwrCommonService;
import com.pinhuba.web.controller.dwr.DwrHrmContractService;
import com.pinhuba.web.controller.dwr.DwrHrmEmployeeService;
import com.pinhuba.web.controller.dwr.DwrMailService;
import com.pinhuba.web.controller.dwr.DwrOACompanyResourcesService;
import com.pinhuba.web.controller.dwr.DwrOADesktopService;
import com.pinhuba.web.controller.dwr.DwrOaCommunicationService;
import com.pinhuba.web.controller.dwr.DwrOfficeResourcesService;
import com.pinhuba.web.controller.dwr.DwrSysProcessService;
import com.pinhuba.web.taglib.table.SysGridBtnBean;
import com.pinhuba.web.taglib.table.SysGridColumnBean;
import com.pinhuba.web.taglib.table.SysGridTitleBean;
import com.pinhuba.web.taglib.table.cloumntype.TextType;

public class UtilTool {
	Logger log = Logger.getLogger(UtilTool.class);

	public static String getProjectPath(Class cls) {
		URL result = null;
		String path = null;
		ProtectionDomain pd = cls.getProtectionDomain();
		CodeSource cs = pd.getCodeSource();

		if (cs != null) {
			result = cs.getLocation();
		}

		if (result == null) {
			String filePath = cls.getName().replace('.', '/').concat(".class");
			ClassLoader loader = cls.getClassLoader();
			result = loader != null ? loader.getResource(filePath) : ClassLoader.getSystemResource(filePath);
		}
		try {
			path = new File(result.getFile()).getCanonicalPath();
		} catch (IOException e) {
			path = e.getMessage();
		}
		path = path.substring(0, path.indexOf("\\class"));
		return path;
	}

	@SuppressWarnings("unchecked")
	public static List getListWithPage(List list, Pager page) {
		List result = new ArrayList();

		if (list.size() > 0) {
			Object[] temp = list.toArray();

			int endRow = (temp.length >= (page.getStartRow() + page.getPageSize()) ? page.getStartRow() + page.getPageSize() : temp.length);

			for (int i = page.getStartRow(); i < endRow; i++) {
				result.add(temp[i]);
			}

			return result;
		} else {
			return list;
		}
	}

	public String readInformation(HttpServletRequest request) {
		StringBuffer reqXml = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				reqXml.append(line);
			}
		} catch (IOException er) {
			log.error("CheckUser-readInformation::" + er.getMessage());
		}
		return reqXml.toString();
	}

	public static List<SysColumnControl> getColumnShow(ServletContext servletContext, String tableName) {
		WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		DwrSysProcessService sysProcessService = (DwrSysProcessService) webAppContext.getBean("dwrSysProcessService");
		SysColumnControl columnControl = new SysColumnControl();
		columnControl.setTableName(tableName);

		List<SysColumnControl> resultList = sysProcessService.listColumn(columnControl);
		return resultList;
	}

	public static ArrayList<SysGridColumnBean> getGridColumnList(List<SysColumnControl> sysList) {
		ArrayList<SysGridColumnBean> bcList = new ArrayList<SysGridColumnBean>();
		for (SysColumnControl sysColumnControl : sysList) {
			SysGridColumnBean bc = new SysGridColumnBean();
			bc.setDataName(sysColumnControl.getColumnCode());
			bc.setShowName(sysColumnControl.getColumnName());
			if (sysColumnControl.getIsShow() == EnumUtil.SYS_ISACTION.Vaild.value) {
				bc.setShowColumn(true);
			} else {
				bc.setShowColumn(false);
			}
			if (sysColumnControl.getIsshowSimple() == EnumUtil.SYS_ISACTION.Vaild.value) {
				bc.setShowQuerySelsect(true);
			} else {
				bc.setShowQuerySelsect(false);
			}
			if (sysColumnControl.getIsshowAdvanced() == EnumUtil.SYS_ISACTION.Vaild.value) {
				bc.setShowAdvanced(true);
			} else {
				bc.setShowAdvanced(false);
			}
			bc.setColumnTypeClass(new TextType());
			bc.setColumnReplace("null");
			bc.setColumnStrCount(sysColumnControl.getColumnStrcount());
			bc.setColumnToObject(true);
			bcList.add(bc);
		}
		return bcList;
	}

	public static SysConfig getSysConfigByCode(ServletContext context, String code) {
		WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(context);
		DwrSysProcessService sysProcessService = (DwrSysProcessService) webAppContext.getBean("dwrSysProcessService");
		return sysProcessService.getSysconfigByCode(code);
	}

	public static SysMethodInfo getSysMethodInfoByPk(ServletContext context, String pk) {
		WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(context);
		DwrSysProcessService sysProcessService = (DwrSysProcessService) webAppContext.getBean("dwrSysProcessService");
		return sysProcessService.getMethodInfoByPk(pk);
	}

	/**
	 * 根据上级编码获取下级编码
	 * 
	 * @param context
	 * @param request
	 * @param upcode
	 *            上级编码
	 * @param table
	 *            表名称
	 * @param colname
	 *            编码列名
	 * @param upcol
	 *            上级编码列名
	 * @return
	 */
	public static String getCodeByUpCode(ServletContext context, HttpServletRequest request, String upcode, String table, String colname, String upcol) {
		WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(context);
		DwrCommonService commonService = (DwrCommonService) webAppContext.getBean("dwrCommonService");
		return commonService.getCodeByTable(context, request, upcode, table, colname, upcol);
	}

	public static int getCompanyId(HttpServletRequest request) {
		SessionUser user = (SessionUser) LoginContext.getSessionValueByLogin(request);
		return (int) user.getCompanyId();
	}

	public static String getEmployeeId(HttpServletRequest request) {
		SessionUser user = (SessionUser) LoginContext.getSessionValueByLogin(request);
		return user.getEmployeeInfo().getPrimaryKey();
	}
	
	public static HrmEmployee getEmployee(String sessionId, HttpServletRequest request) {
		WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
		IHrmEmployeeService employeeService = (IHrmEmployeeService) webAppContext.getBean("hrmEmployeeService");
		String empId = "";
		Map<String, String> map = ConstWords.OnlineUserSessionIdMap;
		for (Map.Entry<String, String> entry : map.entrySet()) {
			if(entry.getValue().equals(sessionId)){
				empId = entry.getKey();
				break;
			}
		}
		
		HrmEmployee employee = null;
		if(StringUtils.isNotBlank(empId)){
			employee = employeeService.getEmployeeByPK(empId);
		}
		return employee;
	}

	public static long getDeptId(HttpServletRequest request) {
		SessionUser user = (SessionUser) LoginContext.getSessionValueByLogin(request);
		return user.getDepartmentInfo().getPrimaryKey();
	}

	public static void writeTextXml(HttpServletResponse response, String xml) throws Exception {
		if (xml.length() > 0) {
			response.setContentType("text/xml; charset=UTF-8");
			PrintWriter pw = response.getWriter();
			pw.print(xml.replaceAll("&", "&amp;"));
			pw.flush();
			pw.close();
		}
	}

	/**
	 * 将字符串转为select
	 * 
	 * @param enumString
	 * @return
	 */
	public static String getSelectOptionsByEnum(String enumString) {
		StringBuffer tmp = new StringBuffer();
		if (enumString != null && enumString.length() > 0) {
			String[] options = enumString.split("\\|");
			for (int j = 0; j < options.length; j++) {
				String[] tmpstr = options[j].split(",");
				String ck = "";
				tmp.append("<option value ='" + tmpstr[0] + "' " + ck + ">" + tmpstr[1] + "</option>\n");
			}
		}
		return tmp.toString();
	}
	
	/**
	 * 将字符串转为select option标签不换行
	 * 
	 * @param enumString
	 * @return
	 */
	public static String getSelectOptionsByEnumNo(String enumString) {
		StringBuffer tmp = new StringBuffer();
		if (enumString != null && enumString.length() > 0) {
			String[] options = enumString.split("\\|");
			for (int j = 0; j < options.length; j++) {
				String[] tmpstr = options[j].split(",");
				String ck = "";
				tmp.append("<option value ='" + tmpstr[0] + "' " + ck + ">" + tmpstr[1] + "</option>");
			}
		}
		return tmp.toString();
	}
	
	/**
	 * 将字符串转为select
	 * 
	 * @param enumString
	 * @return
	 */
	public static String getSelectOptionsByEnum(String[] enumString) {
		StringBuffer tmp = new StringBuffer();
		if (enumString != null && enumString.length > 0) {
			if(enumString[0]!=null&&enumString[0].length()>0){
				String[] options = enumString[0].split("\\|");
				String pk = enumString[1];
				for (int j = 0; j < options.length; j++) {
					String[] tmpstr = options[j].split(",");
					if(tmpstr[0].trim().equalsIgnoreCase(pk)){
						tmp.append("<option value ='" + tmpstr[0] + "' selected='selected'>" + tmpstr[1] + "</option>\n");
					}else{
						tmp.append("<option value ='" + tmpstr[0] + "'>" + tmpstr[1] + "</option>\n");
					}
				}
			}
		}
		return tmp.toString();
	}
	
	/**
	 * 将字符串转为radio
	 * 
	 * @param enumString
	 * @return
	 */
	public static String getRadioOptionsByEnum(String enumString , String nameStr) {
		StringBuffer tmp = new StringBuffer();
		if (enumString != null && enumString.length() > 0) {
			String[] options = enumString.split("\\|");
			for (int j = 0; j < options.length; j++) {
				String[] tmpstr = options[j].split(",");
				if(j == 0){
					//第一个选项，设置为默认
					tmp.append("<input type=\"radio\" id='" + tmpstr[1] + "' value ='" + tmpstr[0] + "' name ='" + nameStr + "' checked='checked'>");
				}else{
					tmp.append("<input type=\"radio\" id='" + tmpstr[1] + "' value ='" + tmpstr[0] + "' name ='" + nameStr + "'>");
				}
				tmp.append("<label for='" + tmpstr[1] + "' style='padding-right:10px'>" + tmpstr[1] + "</label>");
			}
		}
		return tmp.toString();
	}
	
	/**
	 * 将字符串转为checkbox
	 * 
	 * @param enumString
	 * @return
	 */
	public static String getCheckboxOptionsByEnum(String enumString , String nameStr) {
		StringBuffer tmp = new StringBuffer();
		if (enumString != null && enumString.length() > 0) {
			String[] options = enumString.split("\\|");
			for (int j = 0; j < options.length; j++) {
				String[] tmpstr = options[j].split(",");
				tmp.append("<input type=\"checkbox\" id='" + tmpstr[1] + "' value ='" + tmpstr[0] + "' name ='" + nameStr + "'>");
				tmp.append("<label for='" + tmpstr[1] + "' style='padding-right:10px'>" + tmpstr[1] + "</label>");
			}
		}
		return tmp.toString();
	}


	/**
	 * 加载字典表为select jsp页面直接使用 通讯手册分组信息
	 * 
	 * @param context
	 * @param request
	 * @param andtxt
	 *            追加的字符串
	 * @param code
	 *            上级编码
	 * @return
	 */
	public static String getCommunSelectOptions(ServletContext context, HttpServletRequest request, String andtxt) {
		String tmp = getChatGroupInfoList(context, request, andtxt);
		return getSelectOptionsByEnum(tmp);
	}
	
	/**
	 * 加载字典表为select jsp页面直接使用 图书类型信息
	 * 
	 * @param context
	 * @param request
	 * @param andtxt
	 *            追加的字符串
	 *
	 * @return
	 */
	public static String getBookTypeSelectOptions(ServletContext context, HttpServletRequest request, String andtxt) {
		String tmp = getBookTypeInfoList(context, request, andtxt);
		return getSelectOptionsByEnum(tmp);
	}
	
	/**
	 * 加载字典表为select jsp页面直接使用 会议室管理用
	 * 
	 * @param context
	 * @param request
	 * @param andtxt
	 *            追加的字符串
	 * @return
	 */
	public static String getBoardroomSelectOptions(ServletContext context, HttpServletRequest request,OaBoardroom oaBoardroom,Pager pager,String andtxt) {
		String tmp = getBoardroomList(context, request, oaBoardroom,pager,andtxt);
		return getSelectOptionsByEnum(tmp);
	}
	
	
	/**
	 * 加载字典表为select jsp页面直接使用 论坛版块
	 * 
	 * @param context
	 * @param request
	 * @param andtxt
	 *            追加的字符串
	 * @param code
	 *            上级编码
	 * @return
	 */
	public static String getForumsSelectOptions(ServletContext context, HttpServletRequest request, String andtxt) {
		String tmp = getForumsInfoList(context, request, andtxt);
		return getSelectOptionsByEnum(tmp);
	}

	/**
	 * 加载字典表为select jsp页面直接使用（公司已自动传入）
	 * 
	 * @param context
	 * @param request
	 * @param andtxt
	 *            追加的字符串
	 * @param code
	 *            上级编码
	 * @return
	 */
	public static String getSelectOptions(ServletContext context, HttpServletRequest request, String andtxt, String code) {
		String tmp = getLibraryInfoList(context, request, andtxt, code);
		return getSelectOptionsByEnum(tmp);
	}
	
	/**
	 * 加载字典表为checkbox jsp页面直接使用
	 * 
	 * @param context
	 * @param request
	 * @param andtxt
	 *            追加的字符串
	 * @param code
	 *            上级编码
	 * @return
	 */
	public static String getCheckboxOptions(ServletContext context, HttpServletRequest request, String andtxt, String code, String nameStr) {
		String tmp = getLibraryInfoList(context, request, andtxt, code);
		return getCheckboxOptionsByEnum(tmp, nameStr);
	}
	

	/**
	 * 列表高级查询时生成下拉列表（公司已自动传入）
	 * 
	 * @param context
	 * @param request
	 * @param andtxt
	 *            追加的字符串
	 * @param code
	 *            上级编码
	 * @return
	 */
	public static String getLibraryInfoList(ServletContext context, HttpServletRequest request, String andtxt, String code) {
		String tmp = "";
		if (andtxt != null && andtxt.length() > 0) {
			tmp = andtxt + "|";
		}
		WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(context);
		DwrCommonService commonService = (DwrCommonService) webAppContext.getBean("dwrCommonService");
		List<SysLibraryInfo> list = commonService.getLibraryInfoByCode(context, request, code);
		for (SysLibraryInfo sysLibraryInfo : list) {
			tmp += sysLibraryInfo.getPrimaryKey() + "," + sysLibraryInfo.getLibraryInfoName() + "|";
		}
		if (tmp != null && tmp.length() > 0)
			return tmp.substring(0, tmp.length() - 1);
		else
			return tmp;
	}

	/**
	 * 根据编码获取字典对象(公司已自动传入)
	 * 
	 * @param context
	 * @param request
	 * @param code
	 * @return
	 */
	public static SysLibraryInfo getLibraryInfoByCode(ServletContext context, HttpServletRequest request, String code) {
		WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(context);
		DwrCommonService commonService = (DwrCommonService) webAppContext.getBean("dwrCommonService");
		return commonService.getLibraryInfoByCodeAndCompanyId(context, request, code);
	}

	/**
	 * 根据主键获取字典对象
	 * 
	 * @param context
	 * @param request
	 * @param pk
	 * @return
	 */
	public static SysLibraryInfo getLibraryInfoByPk(ServletContext context, HttpServletRequest request, long pk) {
		WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(context);
		DwrCommonService commonService = (DwrCommonService) webAppContext.getBean("dwrCommonService");
		return commonService.getLibraryInfoByPk(context, request, pk);
	}

	// ============知识仓库生成select============
	public static String getWareTypeString(ServletContext context, HttpServletRequest request, String andtxt, int type) {
		String tmp = "";
		if (andtxt != null && andtxt.length() > 0) {
			tmp = andtxt + "|";
		}
		WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(context);
		DwrOACompanyResourcesService oaCompanyService = (DwrOACompanyResourcesService) webAppContext.getBean("dwrOACompanyResourcesService");
		ResultBean bean = oaCompanyService.getWareTypeByType(context, request, type);
		List<OaWareType> list = bean.getResultList();
		for (OaWareType oaWareType : list) {
			tmp += oaWareType.getPrimaryKey() + "," + oaWareType.getOaTypeName() + "|";
		}
		if (tmp != null && tmp.length() > 0)
			tmp = tmp.substring(0, tmp.length() - 1);
		return tmp;
	}

	public static String getWareTypeOptions(ServletContext context, HttpServletRequest request, String andtxt, int type) {
		String tmp = getWareTypeString(context, request, andtxt, type);
		return getSelectOptionsByEnum(tmp);
	}

	//=====================外部邮箱==============================
	public static String[] getNetMailString(ServletContext context, HttpServletRequest request, String andtxt, int type){
		String tmp = "";
		if (andtxt != null && andtxt.length() > 0) {
			tmp = andtxt + "|";
		}
		String defaultsel="";
		WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(context);
		DwrMailService mailService = (DwrMailService) webAppContext.getBean("dwrMailService");
		ResultBean bean = mailService.getOaNetMailSetList(context, request);
		List<OaNetmailSet> list = bean.getResultList();
		if (type==EnumUtil.Net_Mail_Type.Send.value) {
			for (OaNetmailSet oaNetmailSet : list) {
				if (oaNetmailSet.getOaNetmailIssend()==EnumUtil.SYS_ISACTION.Vaild.value) {
					tmp += oaNetmailSet.getPrimaryKey() + "," + oaNetmailSet.getOaNetmailFrom() + "|";
				}
				if (oaNetmailSet.getOaNetmailIsDefault()!=null&&oaNetmailSet.getOaNetmailIsDefault()==EnumUtil.SYS_ISACTION.Vaild.value) {
					defaultsel = String.valueOf(oaNetmailSet.getPrimaryKey());
				}
			}
		}else if (type==EnumUtil.Net_Mail_Type.Accp.value) {
			for (OaNetmailSet oaNetmailSet : list) {
				if (oaNetmailSet.getOaNetmailIsaccp()==EnumUtil.SYS_ISACTION.Vaild.value) {
					tmp += oaNetmailSet.getPrimaryKey() + "," + oaNetmailSet.getOaNetmailFrom() + "|";
				}
				if (oaNetmailSet.getOaNetmailIsDefault()!=null&&oaNetmailSet.getOaNetmailIsDefault()==EnumUtil.SYS_ISACTION.Vaild.value) {
					defaultsel = String.valueOf(oaNetmailSet.getPrimaryKey());
				}
			}
		}
		if (tmp != null && tmp.length() > 0){
			tmp = tmp.substring(0, tmp.length() - 1);
		}
		
		String[] str = new String[2];
		str[0]= tmp;
		str[1] = defaultsel;
		return str;
	}
	
	/**
	 * 加载默认邮箱
	 * @param context
	 * @param request
	 * @return
	 */
	public static OaNetmailSet getNetMailByDefault(ServletContext context, HttpServletRequest request){
		WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(context);
		DwrMailService mailService = (DwrMailService) webAppContext.getBean("dwrMailService");
		ResultBean bean = mailService.getOaNetMailSetList(context, request);
		List<OaNetmailSet> list = bean.getResultList();
		List<OaNetmailSet> tmplist = new ArrayList<OaNetmailSet>();
		OaNetmailSet tmp=null;
		for (OaNetmailSet setmail : list) {
			if (setmail.getOaNetmailIsaccp()==EnumUtil.SYS_ISACTION.Vaild.value) {
				tmplist.add(setmail);
				if (setmail.getOaNetmailIsDefault()!=null&&setmail.getOaNetmailIsDefault()==EnumUtil.SYS_ISACTION.Vaild.value) {
					tmp = setmail;
				}
			}
		}
		if (tmp==null&&tmplist.size()>0) {
			tmp = tmplist.get(0);
		}
		return tmp;
	}
	
	public static String getNetMailOptions(ServletContext context, HttpServletRequest request, String andtxt, int type) {
		String[] tmp = getNetMailString(context, request, andtxt, type);
		return getSelectOptionsByEnum(tmp);
	}
	
	
	// ============期刊===============
	public static String getJournalsTypeString(ServletContext context, HttpServletRequest request, String andtxt) {
		String tmp = "";
		if (andtxt != null && andtxt.length() > 0) {
			tmp = andtxt + "|";
		}
		WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(context);
		DwrOACompanyResourcesService oaCompanyService = (DwrOACompanyResourcesService) webAppContext.getBean("dwrOACompanyResourcesService");
		ResultBean bean = oaCompanyService.getJournalsTypeList(context, request);
		List<OaJournalsType> list = bean.getResultList();
		for (OaJournalsType type : list) {
			tmp += type.getPrimaryKey() + "," + type.getJournalsTypeName() + "|";
		}
		if (tmp != null && tmp.length() > 0)
			tmp = tmp.substring(0, tmp.length() - 1);
		return tmp;
	}

	public static String getJournalsTypeOptions(ServletContext context, HttpServletRequest request, String andtxt) {
		String tmp = getJournalsTypeString(context, request, andtxt);
		return getSelectOptionsByEnum(tmp);
	}

	// ============相册生成select=======================
	public static String getAlbumOptions(ServletContext context, HttpServletRequest request, String andtxt) {
		String tmp = "";
		if (andtxt != null && andtxt.length() > 0) {
			tmp = andtxt + "|";
		}
		WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(context);
		DwrOACompanyResourcesService oaCompanyService = (DwrOACompanyResourcesService) webAppContext.getBean("dwrOACompanyResourcesService");
		ResultBean bean = oaCompanyService.getAllAlbumList(context, request);
		List<OaAlbum> list = bean.getResultList();
		for (OaAlbum am : list) {
			tmp += am.getPrimaryKey() + "," + am.getAlbumName() + "|";
		}
		if (tmp != null && tmp.length() > 0)
			tmp = tmp.substring(0, tmp.length() - 1);
		return getSelectOptionsByEnum(tmp);
	}
	
	
	
	
	//============工作地区===============
	public static String getWorkareaString(ServletContext context, HttpServletRequest request, String andtxt) {
		String tmp = "";
		if (andtxt != null && andtxt.length() > 0) {
			tmp = andtxt + "|";
		}
		WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(context);
		DwrHrmEmployeeService employeeService = (DwrHrmEmployeeService) webAppContext.getBean("dwrHrmEmployeeService");
		ResultBean bean = employeeService.getWorkareaList(context, request);
		List<HrmWorkarea> list = bean.getResultList();
		for (HrmWorkarea type : list) {
			tmp += type.getPrimaryKey() + "," + type.getHrmAreaName() + "|";
		}
		if (tmp != null && tmp.length() > 0)
			tmp = tmp.substring(0, tmp.length() - 1);
		return tmp;
	}

	public static String getWorkareaOptions(ServletContext context, HttpServletRequest request, String andtxt) {
		String tmp = getWorkareaString(context, request, andtxt);
		return getSelectOptionsByEnum(tmp);
	}
	
	//============合同类别===============
	public static String getContractTypeString(ServletContext context, HttpServletRequest request, String andtxt) {
		String tmp = "";
		if (andtxt != null && andtxt.length() > 0) {
			tmp = andtxt + "|";
		}
		WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(context);
		DwrHrmContractService employeeService = (DwrHrmContractService) webAppContext.getBean("dwrHrmContractService");
		ResultBean bean = employeeService.listHrmContractTypeAll(context, request);
		List<HrmContractType> list = bean.getResultList();
		for (HrmContractType type : list) {
			tmp += type.getPrimaryKey() + "," + type.getTypeName() + "|";
		}
		if (tmp != null && tmp.length() > 0)
			tmp = tmp.substring(0, tmp.length() - 1);
		return tmp;
	}
	
	public static String getContractTypeOptions(ServletContext context, HttpServletRequest request, String andtxt) {
		String tmp = getContractTypeString(context, request, andtxt);
		return getSelectOptionsByEnum(tmp);
	}
	
	
	//===================流程相关===============
	public static String getProcessTypeString(ServletContext context, HttpServletRequest request, String andtxt) {
		String tmp = "";
		if (andtxt != null && andtxt.length() > 0) {
			tmp = andtxt + "|";
		}
		WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(context);
		DwrApproveProcessService perService = (DwrApproveProcessService) webAppContext.getBean("dwrApproveProcessService");
		List<ApproveProcessBean> processList = perService.listSysApproveProcessAll(context, request);
		for (ApproveProcessBean processBean : processList) {
			tmp += processBean.getProcessDefinition().getKey() + "," + processBean.getProcessDefinition().getName() + "|";
		}
		if (tmp != null && tmp.length() > 0)
			tmp = tmp.substring(0, tmp.length() - 1);
		return tmp;
	}
	
	public static String getProTypeString(ServletContext context, HttpServletRequest request, String andtxt) {
		String tmp = "";
		if (andtxt != null && andtxt.length() > 0) {
			tmp = andtxt + "|";
		}
		WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(context);
		DwrApproveProcessService service = (DwrApproveProcessService) webAppContext.getBean("dwrApproveProcessService");
		ResultBean bean = service.listSysProcessTypeAllForSelect(context, request);
		List<SysProcessType> list = bean.getResultList();
		for (SysProcessType type : list) {
			tmp += type.getPrimaryKey() + "," + type.getTypeName() + "|";
		}
		if (tmp != null && tmp.length() > 0)
			tmp = tmp.substring(0, tmp.length() - 1);
		return tmp;
	}
	
	public static String getProcessTypeOptions(ServletContext context, HttpServletRequest request, String andtxt) {
		String tmp = getProTypeString(context, request, andtxt);
		return getSelectOptionsByEnum(tmp);
	}
	
	public static String getProcessGroups(ServletContext context, HttpServletRequest request, String namestr) {
		String tmp = getProcessGroupsString(context, request);
		return getCheckboxOptionsByEnum(tmp,namestr);
	}
	
	public static String getProcessGroupsString(ServletContext context, HttpServletRequest request) {
		String tmp = "";
		WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(context);
		IdentityService identityService = (IdentityService) webAppContext.getBean("identityService");
		List<Group> list = identityService.createGroupQuery().list();
		for (Group type : list) {
			tmp += type.getId() + "," + type.getName() + "|";
		}
		if (tmp != null && tmp.length() > 0)
			tmp = tmp.substring(0, tmp.length() - 1);
		return tmp;
	}

	//==========================附件、图片操作====================
	/**
	 * 保存附件并返回附件id,多个以逗号分隔
	 */
	public static String saveAttachments(ServletContext context, HttpServletRequest request, String fileNames) {
		WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(context);
		DwrCommonService commonService = (DwrCommonService) webAppContext.getBean("dwrCommonService");
		List<SysAttachmentInfo> list = commonService.saveAttachmentInfo(request, fileNames);
		String ids = "";
		for (SysAttachmentInfo sysAttachmentInfo : list) {
			ids += sysAttachmentInfo.getPrimaryKey() + ",";
		}
		if (ids.length() > 0) {
			ids = ids.substring(0, ids.length() - 1);
		}
		return ids;
	}

	/**
	 * 保存图片并返回图片id,多个以逗号分隔
	 */
	public static String saveImages(ServletContext context, HttpServletRequest request, String fileNames) {
		WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(context);
		DwrCommonService commonService = (DwrCommonService) webAppContext.getBean("dwrCommonService");
		List<SysImageInfo> list = commonService.saveImageInfo(context, request, fileNames);
		String ids = "";
		for (SysImageInfo sysImageInfo : list) {
			ids += sysImageInfo.getPrimaryKey() + ",";
		}
		if (ids.length() > 0) {
			ids = ids.substring(0, ids.length() - 1);
		}
		return ids;
	}

	/**
	 * 根据id获取附件对象，并转化为[名称|路径,名称|路径]形式
	 * 
	 * @param context
	 * @param request
	 * @param ids
	 * @return
	 */
	public static String getAttachments(ServletContext context, HttpServletRequest request, String ids) {
		WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(context);
		DwrCommonService commonService = (DwrCommonService) webAppContext.getBean("dwrCommonService");
		String names = commonService.getAttachmentInfoListToString(context, request, ids);
		return names;
	}

	/**
	 * 根据id获取图片对象，并转化为[名称|路径,名称|路径]形式
	 * 
	 * @param context
	 * @param request
	 * @param ids
	 * @return
	 */
	public static String getImages(ServletContext context, HttpServletRequest request, String ids) {
		WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(context);
		DwrCommonService commonService = (DwrCommonService) webAppContext.getBean("dwrCommonService");
		String names = commonService.getImageInfoListToString(context, request, ids);
		return names;
	}

	/**
	 * 删除附件记录及文件
	 * 
	 * @param context
	 * @param request
	 * @param ids
	 */
	public static void deleteAttachmentsAndFile(ServletContext context, HttpServletRequest request, String ids) {
		WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(context);
		DwrCommonService commonService = (DwrCommonService) webAppContext.getBean("dwrCommonService");
		commonService.deleteAttachmentInfoByIds(context, request, ids);
	}

	/**
	 * 删除图片记录及文件
	 * 
	 * @param context
	 * @param request
	 * @param ids
	 */
	public static void deleteImagesAndFile(ServletContext context, HttpServletRequest request, String ids) {
		WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(context);
		DwrCommonService commonService = (DwrCommonService) webAppContext.getBean("dwrCommonService");
		commonService.deleteImageInfo(context, request, ids);
	}

	/**
	 * 删除附件记录
	 * 
	 * @param context
	 * @param request
	 * @param ids
	 */
	public static void deleteAttachmentsNoFile(ServletContext context, HttpServletRequest request, String ids) {
		WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(context);
		DwrCommonService commonService = (DwrCommonService) webAppContext.getBean("dwrCommonService");
		commonService.deleteAttachmentInfoByIdsNoFile(context, request, ids);
	}

	/**
	 * 删除图片记录
	 * 
	 * @param context
	 * @param request
	 * @param ids
	 */
	public static void deleteImagesNoFile(ServletContext context, HttpServletRequest request, String ids) {
		WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(context);
		DwrCommonService commonService = (DwrCommonService) webAppContext.getBean("dwrCommonService");
		commonService.deleteImageInfoNoFile(context, request, ids);
	}

	/**
	 * 列表高级查询时生成下拉列表 通讯手册分组
	 * 
	 * @param context
	 * @param request
	 * @param andtxt
	 *            追加的字符串
	 * @return
	 */
	public static String getChatGroupInfoList(ServletContext context, HttpServletRequest request, String andtxt) {
		String tmp = "";
		if (andtxt != null && andtxt.length() > 0) {
			tmp = andtxt + "|";
		}
		WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(context);
		DwrOaCommunicationService communService = (DwrOaCommunicationService) webAppContext.getBean("dwrOaCommunicationService");
		ResultBean bean = communService.getAllChatGroup(context, request, UtilTool.getEmployeeId(request), UtilTool.getCompanyId(request));
		List<OaChatGroups> list = bean.getResultList();
		//如果没有分组，则加载默认分组！
		if(list.isEmpty() == true){
			OaChatGroups group = new OaChatGroups();
			group.setCompanyId(getCompanyId(request));
			group.setLastmodiDate(UtilWork.getNowTime());
			group.setLastmodiId(getEmployeeId(request));
			group.setOaChatgpDetail("通讯分组默认分组！");
			group.setOaChatgpName("我的好友");
			group.setRecordDate(UtilWork.getNowTime());
			group.setRecordId(getEmployeeId(request));
			OaChatGroups backGroup = communService.newChatGroup(context, request, group);
			list.add(backGroup);
		}
		for (OaChatGroups group : list) {
			tmp += group.getPrimaryKey() + "," + group.getOaChatgpName() + "|";
		}
		if (tmp != null && tmp.length() > 0)
			return tmp.substring(0, tmp.length() - 1);
		else
			return tmp;
	}
	
	/**会议室列表
	 * 
	 * @param context
	 * @param request
	 * @param oaBoardroom
	 * @param pager
	 * @param andtxt
	 * @return
	 */
	public static String getBoardroomList(ServletContext context, HttpServletRequest request, OaBoardroom oaBoardroom,Pager pager,String andtxt) {
		String tmp = "";
		if (andtxt != null && andtxt.length() > 0) {
			tmp = andtxt + "|";
		}
		WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(context);
		DwrOfficeResourcesService officeService = (DwrOfficeResourcesService) webAppContext.getBean("dwrOfficeResourcesService");
		ResultBean bean = officeService.listBoadrooms(context, request, oaBoardroom, pager);
		List<OaBoardroom> list = bean.getResultList();
		for (OaBoardroom group : list) {
			tmp += group.getPrimaryKey() + "," + group.getOaBoardroomName() + "|";
		}
		if (tmp != null && tmp.length() > 0)
			return tmp.substring(0, tmp.length() - 1);
		else
			return tmp;
	}
	
	
	
	
	/**
	 * 列表高级查询时生成下拉列表 论坛版块
	 * 
	 * @param context
	 * @param request
	 * @param andtxt
	 *            追加的字符串
	 * @return
	 */
	public static String getForumsInfoList(ServletContext context, HttpServletRequest request, String andtxt) {
		String tmp = "";
		if (andtxt != null && andtxt.length() > 0) {
			tmp = andtxt + "|";
		}
		WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(context);
		DwrOaCommunicationService communService = (DwrOaCommunicationService) webAppContext.getBean("dwrOaCommunicationService");
		ResultBean bean = communService.getAllForums(context, request, UtilTool.getCompanyId(request));
		List<OaForums> list = bean.getResultList();
		for (OaForums forum : list) {
			tmp += forum.getPrimaryKey() + "," + forum.getOaForumName() + "|";
		}
		if (tmp != null && tmp.length() > 0)
			return tmp.substring(0, tmp.length() - 1);
		else
			return tmp;
	}

	
	/**
	 * 列表高级查询时生成下拉列表 图书类型
	 * 
	 * @param context
	 * @param request
	 * @param andtxt
	 *            追加的字符串
	 * @return
	 */
	public static String getBookTypeInfoList(ServletContext context, HttpServletRequest request, String andtxt) {
		String tmp = "";
		if (andtxt != null && andtxt.length() > 0) {
			tmp = andtxt + "|";
		}
		WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(context);
		DwrOfficeResourcesService officeService = (DwrOfficeResourcesService) webAppContext.getBean("dwrOfficeResourcesService");
		ResultBean bean = officeService.getAllBookType(context, request, UtilTool.getCompanyId(request));
		List<OaBookType> list = bean.getResultList();
		for (OaBookType type : list) {
			tmp += type.getPrimaryKey() + "," + type.getOaBooktypeName() + "|";
		}
		if (tmp != null && tmp.length() > 0)
			return tmp.substring(0, tmp.length() - 1);
		else
			return tmp;
	}
	
	/**
	 * 列表高级查询时生成下拉列表 部门
	 * 
	 * @param context
	 * @param request
	 * @param andtxt
	 *            追加的字符串
	 * @return
	 */
	public static String getDepInfoList(ServletContext context, HttpServletRequest request, String andtxt) {
		String tmp = "";
		if (andtxt != null && andtxt.length() > 0) {
			tmp = andtxt + "|";
		}
		WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(context);
		DwrHrmEmployeeService depService = (DwrHrmEmployeeService) webAppContext.getBean("dwrHrmEmployeeService");
		ResultBean bean = depService.getAlldepartmentNopager(context, request, UtilTool.getCompanyId(request));
		List<HrmDepartment> list = bean.getResultList();
		for (HrmDepartment dep : list) {
			tmp += dep.getPrimaryKey() + "," + dep.getHrmDepName() + "|";
		}
		if (tmp != null && tmp.length() > 0)
			return tmp.substring(0, tmp.length() - 1);
		else
			return tmp;
	}
	
	// ==================================end==================

	@SuppressWarnings("unchecked")
	public static List<SysRoleDetail> getSysRoleDetailList(ServletContext context, HttpServletRequest request, Integer roleId) {
		WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(context);
		DwrSysProcessService sysProcessService = (DwrSysProcessService) webAppContext.getBean("dwrSysProcessService");
		List<SysRoleDetail> detailList = sysProcessService.listSysRoledetailByRoleId(roleId).getResultList();
		return detailList;
	}
	
	
	public static List<SysRoleBtn> getSysRoleBtnList(ServletContext context, HttpServletRequest request, Integer roleId) {
		WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(context);
		DwrSysProcessService sysProcessService = (DwrSysProcessService) webAppContext.getBean("dwrSysProcessService");
		List<SysRoleBtn> btnList = sysProcessService.listSysRoleBtnByRoleId(roleId).getResultList();
		return btnList;
	}

	// 权限设置封装
	@SuppressWarnings("unchecked")
	public static List<SysMethodInfo> getSysMethodMap(ServletContext context, HttpServletRequest request, String code, List<SysRoleDetail> detailList, int level) {
		WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(context);
		DwrSysProcessService sysProcessService = (DwrSysProcessService) webAppContext.getBean("dwrSysProcessService");
		List<SysMethodInfo> methodlist = sysProcessService.getSysMethodInfoList(context, request, code, level).getResultList();
		if (detailList != null && detailList.size() > 0) {
			// 进行是否选择绑定
			for (int i = 0; i < detailList.size(); i++) {
				SysRoleDetail detail = detailList.get(i);
				for (SysMethodInfo sysMethodInfo : methodlist) {
					if (detail.getMethodId().equalsIgnoreCase(sysMethodInfo.getPrimaryKey())) {
						sysMethodInfo.setIschecked(true);
						break;
					}
				}
			}
		}
		return methodlist;
	}

	public static boolean sysMethodIsCheck(String methodId, List<SysRoleDetail> detailList) {
		boolean bl = false;
		if (detailList != null && detailList.size() > 0) {
			for (int i = 0; i < detailList.size(); i++) {
				SysRoleDetail detail = detailList.get(i);
				if (detail.getMethodId().equalsIgnoreCase(methodId)) {
					bl = true;
					break;
				}
			}
		}
		return bl;
	}

	public static SysUserMethods getSysUserMethodByUid(ServletContext context, HttpServletRequest request, String uids) {
		WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(context);
		DwrSysProcessService sysProcessService = (DwrSysProcessService) webAppContext.getBean("dwrSysProcessService");
		SysUserMethods methods = sysProcessService.getSysUserMethodsByUserId(context, request, uids);
		return methods;
	}
	
	public static SysUserBtns getSysUserBtnByUid(ServletContext context, HttpServletRequest request, String uids) {
		WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(context);
		DwrSysProcessService sysProcessService = (DwrSysProcessService) webAppContext.getBean("dwrSysProcessService");
		SysUserBtns btns = sysProcessService.getSysUserBtnsByUserId(context, request, uids);
		return btns;
	}
	
	public static SysMethodBtn setBtnChecked(SysMethodBtn btn, SysUserBtns btns) {
		if(btns != null && StringUtils.isNotBlank(btns.getUserBtnDetail())){
			String[] tmps = btns.getUserBtnDetail().split(",");
			for (int i = 0; i < tmps.length; i++) {
				if (Integer.valueOf(tmps[i]) == btn.getPrimaryKey()) {
					btn.setChecked(true);
				}
			}
		}
		return btn;
	}
	
	public static SysMethodBtn setBtnChecked(SysMethodBtn btn, List<SysRoleBtn> btns) {
		if(btns != null){
			for (SysRoleBtn sysRoleBtn : btns) {
				if(btn.getPrimaryKey() == sysRoleBtn.getBtnId()){
					btn.setChecked(true);
				}
			}
		}
		return btn;
	}

	public static Set<String> getSysUserMethodAllByUid(ServletContext context, HttpServletRequest request, int uids, String show) {
		Set<String> methodset = null;
		WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(context);
		DwrSysProcessService sysProcessService = (DwrSysProcessService) webAppContext.getBean("dwrSysProcessService");
		methodset = sysProcessService.getSysUserMethodsAllByUserId(context, request, uids, show);
		return methodset;
	}

	public static boolean sysMethodIsCheckByUserMethods(String methodId, SysUserMethods methods) {
		boolean bl = false;
		if (methods != null && methods.getUserMethodDetail() != null && methods.getUserMethodDetail().length() > 0) {
			String[] tmps = methods.getUserMethodDetail().split(",");
			for (int i = 0; i < tmps.length; i++) {
				if (tmps[i].equalsIgnoreCase(methodId)) {
					bl = true;
					break;
				}
			}
		}
		return bl;
	}

	public static boolean sysMethodIsCheckByMethodSet(String methodId, Set<String> mset) {
		boolean bl = false;
		if (mset != null && mset.size() > 0) {
			Iterator<String> it = mset.iterator();
			while (it.hasNext()) {
				String elem = (String) it.next();
				if (elem.equalsIgnoreCase(methodId)) {
					bl = true;
					break;
				}
			}
		}
		return bl;
	}

	public static List<SysMethodInfo> getSysMethodMapByUserMethods(ServletContext context, HttpServletRequest request, String code, SysUserMethods usermethods, int level) {
		WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(context);
		DwrSysProcessService sysProcessService = (DwrSysProcessService) webAppContext.getBean("dwrSysProcessService");
		List<SysMethodInfo> methodlist = sysProcessService.getSysMethodInfoList(context, request, code, level).getResultList();
		if (usermethods != null && usermethods.getUserMethodDetail() != null && usermethods.getUserMethodDetail().length() > 0) {
			String[] tmps = usermethods.getUserMethodDetail().split(",");
			// 进行是否选择绑定
			for (int i = 0; i < tmps.length; i++) {
				for (SysMethodInfo sysMethodInfo : methodlist) {
					if (tmps[i].equalsIgnoreCase(sysMethodInfo.getPrimaryKey())) {
						sysMethodInfo.setIschecked(true);
						break;
					}
				}
			}
		}
		return methodlist;
	}

	public static List<SysMethodInfo> getSysMethodMapByMethodSet(ServletContext context, HttpServletRequest request, String code, Set<String> mset, int level) {
		WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(context);
		DwrSysProcessService sysProcessService = (DwrSysProcessService) webAppContext.getBean("dwrSysProcessService");
		List<SysMethodInfo> methodlist = sysProcessService.getSysMethodInfoList(context, request, code, level).getResultList();
		if (mset != null && mset.size() > 0) {
			Iterator<String> it = mset.iterator();
			// 进行是否选择绑定
			while (it.hasNext()) {
				String elem = (String) it.next();
				for (SysMethodInfo sysMethodInfo : methodlist) {
					if (elem.equalsIgnoreCase(sysMethodInfo.getPrimaryKey())) {
						sysMethodInfo.setIschecked(true);
						break;
					}
				}
			}
		}
		return methodlist;
	}
	
	
	public static boolean isCheckedCompanyMethods(SysMethodInfo method,List<SysMethodInfo> companyMethods){
		boolean bl = false;
		for (SysMethodInfo sysMethodInfo : companyMethods) {
			if(sysMethodInfo.getPrimaryKey().equalsIgnoreCase(method.getPrimaryKey())){
				bl = true;
				break;
			}
		}
		return bl;
	}
	/**
	 * 获取参数
	 * @param request
	 * @param index
	 * @return
	 */
	public static String getSysParamByIndex(HttpServletRequest request,String index){
		String value ="";
		SessionUser user = (SessionUser) LoginContext.getSessionValueByLogin(request);
		Map<String, String> paramMap = user.getParamMap();
		if (paramMap!=null&&paramMap.size()>0) {
			if(paramMap.containsKey(index)){
				value = paramMap.get(index);
			}
		}
		return value;
	}
	
	/**
	 * 将set结合转为字符串并用于主键为varchar类型in查询
	 * @param approvIds
	 * @return
	 */
	public static String getStringFormSetIsString(Set<String> approvIds){
		String arIds ="";
		if (approvIds!=null&&approvIds.size()>0) {
			Iterator<String> it = approvIds.iterator();
			while (it.hasNext()) {
				String elem = (String) it.next();
				arIds+="'"+elem+"',";
			}
			if (arIds != null && arIds.length()>0) {
				arIds =arIds.substring(0,arIds.length()-1);
			}
		}
		return arIds;
	}
	
	/**
	 * 将set结合转为字符串并用于主键为int/long类型in查询
	 * @param approvIds
	 * @return
	 */
	public static String getStringFormSetIsNum(Set<String> approvIds){
		String arIds ="";
		if (approvIds!=null&&approvIds.size()>0) {
			Iterator<String> it = approvIds.iterator();
			while (it.hasNext()) {
				String elem = (String) it.next();
				arIds+=elem+",";
			}
			if (arIds != null && arIds.length()>0) {
				arIds =arIds.substring(0,arIds.length()-1);
			}
		}
		return arIds;
	}
	/**
	 * 验证桌面是否显示该窗体
	 * @param context
	 * @param request
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean isShowDeskTop(ServletContext context,HttpServletRequest request,int type){
		boolean bl = false;
		WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(context);
		DwrOADesktopService dwrOaDesktopService  = (DwrOADesktopService) webAppContext.getBean("dwrOADesktopService");
		List<OaDesktopSet> list = dwrOaDesktopService.getOaDeskTopList(context, request).getResultList();
		OaDesktopSet temp = null;
		for (OaDesktopSet tmp : list) {
			if(tmp.getOaDesktopType().intValue() == type){
				temp = tmp;
				break;
			}
		}
		if (temp!=null&& temp.getOaDesktopIsshow() == EnumUtil.SYS_ISACTION.Vaild.value) {
			bl = true;
		}
		return bl;
	}
	
	/**
	 * 验证桌面是否显示该窗体
	 * @param context
	 * @param request
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static OaDesktopSet getDeskTopByType(ServletContext context,HttpServletRequest request,int type){
		WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(context);
		DwrOADesktopService dwrOaDesktopService  = (DwrOADesktopService) webAppContext.getBean("dwrOADesktopService");
		List<OaDesktopSet> list = dwrOaDesktopService.getOaDeskTopList(context, request).getResultList();
		OaDesktopSet temp = null;
		for (OaDesktopSet tmp : list) {
			if(tmp.getOaDesktopType().intValue() == type){
				temp = tmp;
				break;
			}
		}
		return temp;
	}
	
	/**
	 * 验证用户是否有该功能权限
	 * @param request
	 * @param pk
	 * @return
	 */
	public static boolean ishashMethodByCode(HttpServletRequest request,String pk){
		SessionUser user = (SessionUser) LoginContext.getSessionValueByLogin(request);
		boolean bl = false;
		if(user!=null){
			Set<String> methodSet =  user.getUserMethodsSet();
			bl = methodSet.contains(pk);
		}
		return bl;
	}
	
	/**
	 * 发送提醒短信
	 * @param context
	 * @param empIds	收件人id数组
	 * @param smsContent 内容
	 */
	public static void sendSms(ServletContext context,int companyId,String[] empIds,String smsContent){
		WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(context);
		IMoblieSmsService moblieService  = (IMoblieSmsService) webAppContext.getBean("moblieSmsService");
		ArrayList<OaSmsInbox> list = new ArrayList<OaSmsInbox>();
		for (int i = 0; i < empIds.length; i++) {
			OaSmsInbox oainbox = new OaSmsInbox();
			oainbox.setOaSmsInboxEmp(empIds[i]);
			oainbox.setCompanyId(companyId);
			oainbox.setOaSmsInboxContent(smsContent);
			oainbox.setOaSmsInboxIsread(EnumUtil.OA_SMS_INBOX_ISREAD.two.value);
			oainbox.setOaSmsInboxSenderid("-1");
			oainbox.setOaSmsInboxSendtime(UtilWork.getNowTime());
			oainbox.setOaSmsType(EnumUtil.OA_SMS_TYPE.two.value);
			oainbox.setOaSmsInboxSenderName("系统管理员");
			oainbox.setRecordDate(UtilWork.getNowTime());
			oainbox.setRecordId("-1");
			list.add(oainbox);
		}
		int c = moblieService.saveOaSmsInbox(list);
		if (c>0) {
			for (int i = 0; i < empIds.length; i++) {
				if (context.getAttribute(ConstWords.servletContext_MSGBOX)!=null) {
					Map<String,Integer> map =(Map)context.getAttribute(ConstWords.servletContext_MSGBOX);
					String userid = empIds[i];
					if(map.containsKey(userid)){
						int count = map.get(userid)+1;
						map.put(userid, count);
					}else{
						map.put(userid, 1);
					}
				}
			}
		}
	}
	
	public static void sendSmsMore(ServletContext context,int companyId,String[] empIds,String smsContents[]){
		WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(context);
		IMoblieSmsService moblieService  = (IMoblieSmsService) webAppContext.getBean("moblieSmsService");
		ArrayList<OaSmsInbox> list = new ArrayList<OaSmsInbox>();
		for (int i = 0; i < empIds.length; i++) {
			OaSmsInbox oainbox = new OaSmsInbox();
			oainbox.setOaSmsInboxEmp(empIds[i]);
			oainbox.setCompanyId(companyId);
			oainbox.setOaSmsInboxContent(smsContents[i]);
			oainbox.setOaSmsInboxIsread(EnumUtil.OA_SMS_INBOX_ISREAD.two.value);
			oainbox.setOaSmsInboxSenderid("-1");
			oainbox.setOaSmsInboxSendtime(UtilWork.getNowTime());
			oainbox.setOaSmsType(EnumUtil.OA_SMS_TYPE.two.value);
			oainbox.setOaSmsInboxSenderName("系统管理员");
			oainbox.setRecordDate(UtilWork.getNowTime());
			oainbox.setRecordId("-1");
			list.add(oainbox);
		}
		int c = moblieService.saveOaSmsInbox(list);
		if (c>0) {
			for (int i = 0; i < empIds.length; i++) {
				if (context.getAttribute(ConstWords.servletContext_MSGBOX)!=null) {
					Map<String,Integer> map =(Map)context.getAttribute(ConstWords.servletContext_MSGBOX);
					String userid = empIds[i];
					if(map.containsKey(userid)){
						int count = map.get(userid)+1;
						map.put(userid, count);
					}else{
						map.put(userid, 1);
					}
				}
			}
		}
	}
	
	/**
	 * 定时提醒标志式转换
	 * @param type
	 * @param timedDate
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String getDateToQuartzStr(int type,String timedDate){
		Date date = null;
    	if(type==EnumUtil.TIMED_TYPE.Vaild.value) {
			date = DateTimeTool.getDateFromString(timedDate, "HH:mm");
		} else if(type==EnumUtil.TIMED_TYPE.No_Vaild.value) {
			date = DateTimeTool.getDateFromString(timedDate, "yyyy-MM-dd HH:mm");
		}
		StringBuffer cronStr = new StringBuffer();
    	cronStr.append("0 ");
    	cronStr.append(date.getMinutes());
    	cronStr.append(" ");
    	cronStr.append(date.getHours());
    	cronStr.append(" ");
    	if(type == EnumUtil.TIMED_TYPE.No_Vaild.value) {
	    	cronStr.append(date.getDate());
	    	cronStr.append(" ");
	    	cronStr.append(date.getMonth()+1);
	    	cronStr.append(" ?");
	    	cronStr.append(" ");
	    	cronStr.append(timedDate.substring(0, 4));
    	} else if (type == EnumUtil.TIMED_TYPE.Vaild.value) {
    		cronStr.append("* * ?");
    	}
    	return cronStr.toString();
	}
	
	
	/**
	 * 自定义方法，获取公司主键及用户主键并转为路径
	 * 
	 * @param request
	 * @return
	 */
	public static String getCompanyAndUserPath(HttpServletRequest request,boolean appSeptor) {
		SessionUser suser =(SessionUser) LoginContext.getSessionValueByLogin(request);
		StringBuffer path = new StringBuffer();
		if (appSeptor) {
			path.append(ConstWords.septor);
		}
		if(suser != null){
			path.append(ConstWords.CompanyName+suser.getCompanyId());
			path.append(ConstWords.septor);
			path.append(ConstWords.UserName+suser.getUserInfo().getPrimaryKey());
		}else{
			path.append(ConstWords.MEMBER);
		}
		return path.toString();
	}
	
	public static String getProjectPath(HttpServletRequest request){
		String url = "login.jsp";
		SessionUser sessionUser = (SessionUser)LoginContext.getSessionValueByLogin(request);
		
		if(sessionUser != null){
			List<SysMethodInfo> companyList = sessionUser.getCompanyMethodsList();
			if (companyList.size()==1) {
				url = request.getContextPath()+"/login.jsp";
			}
		}
		return url;
	}

	/**
	 * 格式化银行账号
	 * @param account
	 * @param len
	 * @param split
	 * @return
	 */
	public static String formatAccount(String account,int len,String split){
		if (account==null||account.length()==0) {
			return account;
		}
		StringBuffer result = new StringBuffer();
		int a = 1;
		for (int i = 0; i <account.length(); i++) {
			result.append(account.charAt(i));
			if (i+1==len*a) {
				result.append(split);
				a++;
			}
		}
		return result.toString();
	}
	
	public static String getStrByDouble(Double val,DecimalFormat df){
		String str="&nbsp;";
		if (val!=null&&val!=0) {
			str = df.format(val);
		}
		return str;
	}
	/**
	 * 日期set集合排序
	 * @param dateSet
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<String> compDateBySet(Set<String> dateSet){
		List<String> dateList = new ArrayList<String>();
		Iterator<String> it = dateSet.iterator();
		while (it.hasNext()) {
			String elem = (String) it.next();
			dateList.add(elem);
		}
		CaseInsensitiveComparator comp = new CaseInsensitiveComparator();
		Collections.sort(dateList,comp);
		return dateList;
	}
	
	// 日期排序
	@SuppressWarnings("unchecked")
	static class CaseInsensitiveComparator implements Comparator {

		public int compare(Object arg0, Object arg1) {
			String d1 = (String) arg0;
			String d2 = (String) arg1;
			boolean bl = UtilWork.checkDay(d2, d1);
			if (bl) {
				return -1;
			} else {
				return 1;
			}
		}
	}
	
	
	/**
	 * 根据empId获取用户名  2014-02-06 JC
	 * @param context
	 * @param request
	 * @param empId
	 * @return
	 */
	public static String getUserName(ServletContext context, HttpServletRequest request, String empId) {
		WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(context);
		DwrSysProcessService sysProcessService = (DwrSysProcessService) webAppContext.getBean("dwrSysProcessService");
		SysUserInfo sysUserInfo = sysProcessService.getSysUserInfoByEmpId(context, request, empId);
		return sysUserInfo.getUserName();
	}
	
	public static String getUserName(HttpServletRequest request) {
		SessionUser user = (SessionUser) LoginContext.getSessionValueByLogin(request);
		return user.getUserInfo().getUserName();
	}
	
	/**
	 * 获得操作提示
	 * @param context
	 * @param request
	 * @return
	 */
	public static ArrayList<SysGridTitleBean> getGridTitleList(ServletContext context, HttpServletRequest request) {
		WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(context);
		DwrSysProcessService sysProcessService = (DwrSysProcessService) webAppContext.getBean("dwrSysProcessService");
		ArrayList<SysGridTitleBean> list = new ArrayList<SysGridTitleBean>();
		
		String methodId = request.getParameter(ConstWords.METHOD_INFO_ID);
		
		List<SysMethodHelp> resultList = sysProcessService.listSysMethodHelpByMethodId(context, request, methodId);
		
		for (SysMethodHelp sysMethodHelp : resultList) {
			SysGridTitleBean bean = new SysGridTitleBean(sysMethodHelp.getHelpImg(),sysMethodHelp.getHelpDesc());
			list.add(bean);
		}
		
		return list;
	}
	
	
	/**
	 * 获得功能按钮
	 * @param context
	 * @param request
	 * @return
	 */
	public static ArrayList<SysGridBtnBean> getGridBtnList(ServletContext context, HttpServletRequest request) {
		SessionUser user = (SessionUser) LoginContext.getSessionValueByLogin(request);
		
		
		WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(context);
		DwrSysProcessService sysProcessService = (DwrSysProcessService) webAppContext.getBean("dwrSysProcessService");
		ArrayList<SysGridBtnBean> list = new ArrayList<SysGridBtnBean>();
		
		String methodId = request.getParameter(ConstWords.METHOD_INFO_ID);
		
		List<SysMethodBtn> resultList = sysProcessService.listSysMethodBtnByMethodId(context, request, methodId);
		
		for (SysMethodBtn sysMethodBtn : resultList) {
			
			if(user.getUserBtnsSet()!=null && user.getUserBtnsSet().size()>0){
				Iterator<Integer> it = user.getUserBtnsSet().iterator();
				while(it.hasNext()){
					Integer id = it.next();
					if((int)sysMethodBtn.getPrimaryKey() == id){
						SysGridBtnBean bean = new SysGridBtnBean(sysMethodBtn.getBtnName(),sysMethodBtn.getBtnFun(),sysMethodBtn.getBtnImg());
						list.add(bean);
					}
				}
			}
		}
		
		return list;
	}
}
