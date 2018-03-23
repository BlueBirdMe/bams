package com.pinhuba.common.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pinhuba.common.util.file.properties.SystemConfig;
import com.pinhuba.core.pojo.OaTools;
import com.pinhuba.core.pojo.SysMethodInfo;

public class ConstWords {

	public static final String ServletContext_Method = "MethodContextName";

	public static final String ServletContext_OnLineUser = "OnLineUserMap"; // 存储在线人员

	public static final Map<String, String> OnlineUserSessionIdMap = new HashMap<String, String>(); // 存储在线人员sessionId

	public static final Map<String, Boolean> mailIsAccpMap = new HashMap<String, Boolean>(); // 存储人员是否正在接受邮件

	public static final String OnLineUser_Sign = "OnLineUser_Sign";

	public static final String servletContext_MSGBOX = "MSGBOX";

	public static final String servletContext_Timer = "SchedulerTimer";

	public static final String TempCheckSession = "TempCheckSession"; // 项目切换临时交换

	public static final String TempStringMsg = "MsgSession"; // 登录临时交换

	public static final String ValidCodeTempSession = "CodeRandSession";// 存放验证码session

	public static final int CurrentProject = 1;

	public static final String ServletTypeCreate = "create";

	public static final String ServletTypeFree = "free";

	public static final String ServletTypeExit = "exit";

	public static final String ServletTypeSend = "send";

	public static final String ServletTypeCreateSend = "createSend";

	public static final String ServletTypeFreeSend = "freeSend";

	public static final String TempStringRequest = "TempStringRequest";

	public static final String TempStringRequest2 = "TempStringRequest2";

	public static final int SysMethodInfoIdLenght = 2;// 每级功能菜单扩展长度

	public static final String METHOD_INFO_ID = "mid";// 左侧功能块ID参数名

	public static final String MEMBER = "member";
	
	// 个人文件目录名称
	public static final String septor = "/";
	public static final String CompanyName = "comp";
	public static final String UserName = "person";

	public static final String Employee_Sign = "employee";

	public static final String DOWNLOADFILE = "downloadfile";

	public static final String DOWNLOADFILE_PATH = septor + DOWNLOADFILE + septor;

	public static final String USERTEMPFILE = "upload";

	public static final String USERTEMPFILE_PATH = septor + USERTEMPFILE + septor;

	public static int getProjectChangeHeight(int methodCount) throws Exception {
		int height = 60;
		int row = Integer.parseInt(SystemConfig.getParam("erp.show.projectchange") == null ? "3" : SystemConfig.getParam("erp.show.projectchange"));

		if (methodCount == 1) {
			return height;
		}

		if (methodCount > row) {
			if (methodCount % row == 0) {
				height = methodCount / row;
			} else {
				height = methodCount / row + 1;
			}

			return height * 60;
		} else {
			return height;
		}
	}

	public static String getProjectCode() throws IOException {
		return SystemConfig.getParam("erp.sys.ProjectCode");
	}

	/**
	 * 获取平台对象
	 * 
	 * @param methodsList
	 *            （所有顶级功能）
	 * @return
	 */
	public static SysMethodInfo getErpMethodInfo(List<SysMethodInfo> methodsList) {
		SysMethodInfo sysMethod = null;
		if (methodsList != null && methodsList.size() > 0) {
			for (int i = 0; i < methodsList.size(); i++) {
				SysMethodInfo tmp = methodsList.get(i);
				if (tmp.getIsDefault() != null && tmp.getIsDefault() == ConstWords.CurrentProject) {
					sysMethod = tmp;
				}
			}
		}
		return sysMethod;
	}

	public static String ERPPath = "";

	public static String Tool_Libriary_Code = "24";

	private static Map<String, ArrayList<OaTools>> toolsMap = new HashMap<String, ArrayList<OaTools>>();
	static {
		ArrayList<OaTools> tools1 = new ArrayList<OaTools>();
		tools1.add(new OaTools("各地天气预报", "tianqi.png", "http://flash.weather.com.cn/wmaps/index.swf?url1=http%3A%2F%2Fwww%2Eweather%2Ecom%2Ecn%2Fweather%2F&url2=%2Eshtml&from=cn"));
		tools1.add(new OaTools("酒店查询", "jiudian.png", "http://www.ctrip.com/"));
		tools1.add(new OaTools("公交线路查询", "gjxl.png", "http://www.bus84.com/"));
		tools1.add(new OaTools("列车时刻查询", "lcsk.png", "http://qq.ip138.com/train/"));
		tools1.add(new OaTools("航班查询", "hbcx.png", "http://flights.ctrip.com/Domestic/SearchFlights.aspx"));
		toolsMap.put("2401", tools1);

		ArrayList<OaTools> tools2 = new ArrayList<OaTools>();
		tools2.add(new OaTools("长途区号查询", "dhqh.png", "http://www.ip138.com/post/"));
		tools2.add(new OaTools("邮政编码查询", "yzbm.png", "http://www.ip138.com/post/"));
		tools2.add(new OaTools("电子地图", "zxdt.png", "http://www.edushi.com/"));
		toolsMap.put("2402", tools2);

		ArrayList<OaTools> tools3 = new ArrayList<OaTools>();
		tools3.add(new OaTools("IP所属地查询", "ipcx.png", "http://www.whatchina.com/html/sip.asp"));
		tools3.add(new OaTools("手机所属地查询", "sjsd.png", "http://www.whatchina.com/html/smp.html"));
		tools3.add(new OaTools("固定电话查询", "dhsd.png", "http://www.whatchina.com/html/stel.html"));
		tools3.add(new OaTools("身份证所属地查询", "sfzh.png", "http://qq.ip138.com/idsearch/"));
		toolsMap.put("2403", tools3);

		ArrayList<OaTools> tools4 = new ArrayList<OaTools>();
		tools4.add(new OaTools("全球时间", "qqsj.png", "http://www.hao123.com/haoserver/wotime.htm"));
		tools4.add(new OaTools("万年历", "wnl.png", "http://qq.ip138.com/day/"));
		tools4.add(new OaTools("全球节日查询", "jjr.png", "http://www.360doc.com/content/07/0915/18/44521_746777.shtml"));
		toolsMap.put("2404", tools4);

		ArrayList<OaTools> tools5 = new ArrayList<OaTools>();
		tools5.add(new OaTools("长度换算", "cdhs.png", "http://qq.ip138.com/converter8.htm"));
		tools5.add(new OaTools("面积换算", "mjhs.png", "http://qq.ip138.com/converter2.htm"));
		toolsMap.put("2405", tools5);

		ArrayList<OaTools> tools6 = new ArrayList<OaTools>();
		tools6.add(new OaTools("快递查询", "kdcx.png", "http://www.ip138.com/ems/"));
		tools6.add(new OaTools("在线翻译", "zxfy.png", "http://www.iciba.com/"));
		tools6.add(new OaTools("百度搜索", "baidu.png", "http://www.baidu.com"));
		tools6.add(new OaTools("谷歌搜索", "google.png", "http://www.google.com"));
		toolsMap.put("2406", tools6);

	}

	public static Map<String, ArrayList<OaTools>> getToolsMap() {
		return toolsMap;
	}
}
