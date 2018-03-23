package com.pinhuba.web.controller.dwr;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import com.pinhuba.common.module.ResultBean;
import com.pinhuba.common.module.SessionUser;
import com.pinhuba.common.pages.Pager;
import com.pinhuba.common.pages.PagerHelper;
import com.pinhuba.common.util.CalenderBean;
import com.pinhuba.common.util.EnumUtil;
import com.pinhuba.common.util.LoginContext;
import com.pinhuba.common.util.PlanBook;
import com.pinhuba.common.util.UtilDate;
import com.pinhuba.common.util.UtilTool;
import com.pinhuba.common.util.UtilWork;
import com.pinhuba.common.util.WebUtilWork;
import com.pinhuba.common.util.WorkCalenderMonth;
import com.pinhuba.core.iservice.IHrmEmployeeService;
import com.pinhuba.core.iservice.IWorkArrangeService;
import com.pinhuba.core.pojo.HrmDepartment;
import com.pinhuba.core.pojo.HrmEmployee;
import com.pinhuba.core.pojo.OaCalender;
import com.pinhuba.core.pojo.OaWorkLog;

/**
 * 工作安排DwrService
 */
public class DwrWorkArrangeService {
	private final static Logger logger = Logger.getLogger(DwrWorkArrangeService.class);
	@Resource
	private IWorkArrangeService workArrangeService;
	@Resource
	private IHrmEmployeeService employeeinfoService;

	/**
	 * 初始化日期
	 * 
	 * @param context
	 * @param request
	 * @param year
	 * @param month
	 * @param index
	 * @return templist
	 */
	public ResultBean initDate(ServletContext context, HttpServletRequest request, int year, int month, int index) {
		PlanBook plan = new PlanBook(); // 生成日期类
		String caltable = new String();
		GregorianCalendar calendar = new GregorianCalendar(year, month + index, 1);// 获取当月的第一天
		int newyear = calendar.get(Calendar.YEAR);
		int newmonth = calendar.get(Calendar.MONTH);

		Map<String, Integer> map = new HashMap<String, Integer>();

		SessionUser user = (SessionUser) LoginContext.getSessionValueByLogin(request);
		OaWorkLog oaWorkLog = new OaWorkLog();
		oaWorkLog.setOaWorklogLogger(user.getEmployeeInfo().getPrimaryKey());
		oaWorkLog.setCompanyId(UtilTool.getCompanyId(request));
		String dateStart = getYearAndMonthAndDay(newyear, newmonth + 1, 1);
		String dateEnd = getYearAndMonthAndDay(newyear, newmonth + 1, 31);
		oaWorkLog.setOaWorklogDate(dateStart);
		oaWorkLog.setOaWorklogDeps(dateEnd);

		// 查出一个月的日志并放入map中
		List<Object[]> list = workArrangeService.getAllOaWorkLogByDateAndLogger(oaWorkLog);
		for (Object[] objects : list) {
			map.put(objects[0].toString(), Integer.parseInt(objects[1].toString()));
		}

		plan.setMap(map);
		// 得到带日志的日期
		caltable = plan.newCalendar(newyear, newmonth);
		List<String> templist = new ArrayList<String>();
		templist.add(caltable);
		templist.add(String.valueOf(newyear));
		templist.add(String.valueOf(newmonth));
		logger.info("初始化日期...");
		return WebUtilWork.WebResultPack(templist);
	}

	private String getYearAndMonthAndDay(int year, int month, int day) {
		String m = String.valueOf(month < 10 ? "0" + month : month);
		String d = String.valueOf(day < 10 ? "0" + day : day);
		String tmp = year + "-" + m + "-" + d;
		return tmp;
	}

	/**
	 * 显示所有日志
	 * 
	 * @param context
	 * @param request
	 * @param oaWorkLog
	 * @param pager
	 * @return list, pager
	 */
	public ResultBean listOaWorkLog(ServletContext context, HttpServletRequest request, OaWorkLog oaWorkLog, Pager pager) {
		List<OaWorkLog> list = null;
		SessionUser user = (SessionUser) LoginContext.getSessionValueByLogin(request);
		oaWorkLog.setCompanyId((int) user.getCompanyId());
		oaWorkLog.setOaWorklogLogger(user.getEmployeeInfo().getPrimaryKey());
		pager = PagerHelper.getPager(pager, workArrangeService.listOaWorkLogCount(oaWorkLog));
		list = workArrangeService.getAllOaWorkLog(oaWorkLog, pager);

		for (OaWorkLog workLog : list) {
			workLog.setLibrary(UtilTool.getLibraryInfoByPk(context, request, workLog.getOaWorklogType()));
		}

		logger.info("显示所有日志...");
		return WebUtilWork.WebResultPack(list, pager);
	}

	/**
	 * 新增工作日志
	 * 
	 * @param context
	 * @param request
	 * @param oaWorkLog
	 * @param oaWorklogAnnexid
	 * @return null
	 */
	public ResultBean saveWorkLog(ServletContext context, HttpServletRequest request, OaWorkLog oaWorkLog, String oaWorklogAnnexid) {

		String empid = UtilTool.getEmployeeId(request);
		String nowtime = UtilWork.getNowTime();
		if (oaWorkLog.getPrimaryKey() > 0) {
			OaWorkLog tmp = workArrangeService.getWorkLogByPK(oaWorkLog.getPrimaryKey());
			// 删除原附件记录
			UtilTool.deleteAttachmentsNoFile(context, request, tmp.getOaWorklogAnnexid());
			oaWorkLog.setRecordId(tmp.getRecordId());
			oaWorkLog.setRecordDate(tmp.getRecordDate());

		} else {
			oaWorkLog.setRecordId(empid);
			oaWorkLog.setRecordDate(nowtime);
		}
		// 保存附件记录
		String ids = UtilTool.saveAttachments(context, request, oaWorklogAnnexid);
		oaWorkLog.setOaWorklogAnnexid(ids);
		SessionUser user = (SessionUser) LoginContext.getSessionValueByLogin(request);
		oaWorkLog.setOaWorklogLogger(user.getEmployeeInfo().getPrimaryKey());
		oaWorkLog.setCompanyId(UtilTool.getCompanyId(request));
		oaWorkLog.setLastmodiId(empid);
		oaWorkLog.setLastmodiDate(nowtime);

		workArrangeService.saveWorkLog(oaWorkLog);
		logger.info("新增工作日志...");
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * id查找工作日志
	 * 
	 * @param context
	 * @param request
	 * @param worklogpk
	 * @return list
	 */
	public ResultBean getWorklogByPk(ServletContext context, HttpServletRequest request, long worklogpk) {

		OaWorkLog oaWorkLog = workArrangeService.getWorkLogByPK(worklogpk);
		oaWorkLog.setLibrary(UtilTool.getLibraryInfoByPk(context, request, oaWorkLog.getOaWorklogType()));

		// 写入共享人名字
		String actornames = "";
		String actorids = "";
		if (oaWorkLog.getOaWorklogEmps() != null && oaWorkLog.getOaWorklogEmps().length() > 0) {
			String actorsid[] = oaWorkLog.getOaWorklogEmps().substring(0, oaWorkLog.getOaWorklogEmps().length() - 1).split(",");
			//循环写入共享人的名字	
			for (int i = 0; i < actorsid.length; i++) {
				HrmEmployee employee = employeeinfoService.getEmployeeByPK(actorsid[i]);
				if (employee != null) {
					actornames += employee.getHrmEmployeeName() + ",";
					actorids += actorsid[i] + ",";
				}
			}
		}
		oaWorkLog.setOaWorklogEmpsName(actornames);
		oaWorkLog.setOaWorklogEmps(actorids);

		// 写入共享部门的名字
		String depsname = "";
		String depilids = "";
		if (oaWorkLog.getOaWorklogDeps() != null && oaWorkLog.getOaWorklogDeps().length() > 0) {
			String depil[] = oaWorkLog.getOaWorklogDeps().substring(0, oaWorkLog.getOaWorklogDeps().length() - 1).split(",");
			for (int i = 0; i < depil.length; i++) {
				HrmDepartment department = employeeinfoService.getDepartmentByPK(Long.parseLong(depil[i]));
				if (department != null) {
					depsname += department.getHrmDepName() + ",";
					depilids += depil[i] + ",";
				}
			}
		}
		oaWorkLog.setOaWorklogDepsName(depsname);
		oaWorkLog.setOaWorklogDeps(depilids);

		// 日志所属人
		if (oaWorkLog.getOaWorklogLogger() != null && oaWorkLog.getOaWorklogLogger().length() > 0) {
			oaWorkLog.setHrmEmployee(employeeinfoService.getEmployeeByPK(oaWorkLog.getOaWorklogLogger()));
		}

		List<OaWorkLog> list = new ArrayList<OaWorkLog>();
		list.add(oaWorkLog);
		logger.info("根据ID查找工作日志...");
		return WebUtilWork.WebResultPack(list);
	}

	/**
	 * 编辑工作日志
	 * 
	 * @param context
	 * @param request
	 * @param oaWorkLog
	 * @return null
	 */
	public ResultBean updateWorkLog(ServletContext context, HttpServletRequest request, OaWorkLog oaWorkLog) {
		SessionUser user = (SessionUser) LoginContext.getSessionValueByLogin(request);
		oaWorkLog.setCompanyId(UtilTool.getCompanyId(request));
		oaWorkLog.setLastmodiId(user.getEmployeeInfo().getPrimaryKey());
		oaWorkLog.setLastmodiDate(UtilWork.getNowTime());
		oaWorkLog.setOaWorklogLogger(user.getEmployeeInfo().getPrimaryKey());
		workArrangeService.saveWorkLog(oaWorkLog);
		logger.info("编辑工作日志...");
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 删除工作日志
	 * 
	 * @param context
	 * @param request
	 * @param worklogPKs[]
	 * @return null
	 */

	public ResultBean deleteWorkLogByPks(ServletContext context, HttpServletRequest request, long[] worklogPKs) {
		for (long l : worklogPKs) {
			OaWorkLog oaWorkLog = workArrangeService.getWorkLogByPK(l);
			if (oaWorkLog.getOaWorklogAnnexid() != null && oaWorkLog.getOaWorklogAnnexid().length() > 0) {
				// 删除附件
				UtilTool.deleteAttachmentsAndFile(context, request, oaWorkLog.getOaWorklogAnnexid());
			}
			workArrangeService.deleteworklogByPks(l);
		}
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 显示所有共享日志
	 * 
	 * @param context
	 * @param request
	 * @param oaWorkLog
	 * @param pager
	 * @return list, pager
	 */
	public ResultBean listOaShareWorkLog(ServletContext context, HttpServletRequest request, OaWorkLog oaWorkLog, Pager pager) {
		List<OaWorkLog> list = null;
		SessionUser user = (SessionUser) LoginContext.getSessionValueByLogin(request);
		oaWorkLog.setCompanyId((int) user.getCompanyId());
		oaWorkLog.setOaWorklogRange(2);
		oaWorkLog.setOaWorklogDeps(String.valueOf(user.getDepartmentInfo().getPrimaryKey()) + ",");
		oaWorkLog.setOaWorklogEmps(user.getEmployeeInfo().getPrimaryKey() + ",");
		pager = PagerHelper.getPager(pager, workArrangeService.listOaShareWorkLogCount(oaWorkLog));
		list = workArrangeService.getAllOaShareWorkLog(oaWorkLog, pager);

		for (OaWorkLog workLog : list) {
			workLog.setLibrary(UtilTool.getLibraryInfoByPk(context, request, workLog.getOaWorklogType())); //写入日志类型
			HrmEmployee employee = employeeinfoService.getEmployeeByPK(workLog.getOaWorklogLogger());      //写入日志发布人姓名
			if (employee != null) {
				workLog.setHrmEmployee(employee);
			}
		}

		logger.info("显示所有共享日志...");
		return WebUtilWork.WebResultPack(list, pager);
	}

	/**
	 * 显示日程安排（按周显示）
	 * 
	 * @param context
	 * @param request
	 * @param year
	 * @param month
	 * @param day
	 * @param index
	 * @return list
	 */
	public ResultBean listOaCalenderWeek(ServletContext context, HttpServletRequest request, int year, int month, int day, int index) {
		List<CalenderBean> list = new ArrayList<CalenderBean>();
		// 当前日期
		GregorianCalendar calendar = new GregorianCalendar(year, month, day + index);
		int newyear = calendar.get(Calendar.YEAR);
		int newmonth = calendar.get(Calendar.MONTH);
		int newday = calendar.get(Calendar.DATE);

		String newdate = getYearAndMonthAndDay(newyear, newmonth + 1, newday);
		// 日期集合
		UtilDate ud = new UtilDate(year, month, day, index);
		String[] dateOfWeeks = ud.getWeek();

		String dateStartAndend = dateOfWeeks[0] + "," + dateOfWeeks[6];

		SessionUser user = (SessionUser) LoginContext.getSessionValueByLogin(request);
		OaCalender oaCalender = new OaCalender();
		oaCalender.setCompanyId((int) user.getCompanyId());
		oaCalender.setOaCalenderEmp(user.getEmployeeInfo().getPrimaryKey());
		oaCalender.setLastmodiId("1");// 给SQL日期做判断
		oaCalender.setOaCalenderStart(dateStartAndend);

		List<OaCalender> listoaCalender = null;
		listoaCalender = workArrangeService.listOaCalender(oaCalender);

		for (OaCalender oc : listoaCalender) {
			oc.setLibrary(UtilTool.getLibraryInfoByPk(context, request, oc.getOaCalenderType())); //写入工作管理类型
		}

		Map<String, ArrayList<OaCalender>> map = new HashMap<String, ArrayList<OaCalender>>();
		if (listoaCalender != null && listoaCalender.size() > 0) {
			for (OaCalender cal : listoaCalender) {

				String key = cal.getOaCalenderStart().substring(0, 10);// 生成主键
				if (map.containsKey(key)) {// 判断键是否存在,存在拿出来放入集合，否则创建新集合
					ArrayList<OaCalender> calList = map.get(key);
					calList.add(cal);
				} else {
					ArrayList<OaCalender> tmpList = new ArrayList<OaCalender>();
					tmpList.add(cal);
					map.put(key, tmpList);
				}
			}
		}

		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		//周视图的显示
		for (int i = 0; i < dateOfWeeks.length; i++) {
			String content = "";
			if (map != null && map.size() > 0) {
				String key = dateOfWeeks[i];
				if (map.containsKey(key)) {
					ArrayList<OaCalender> tmplist = map.get(key);
					for (OaCalender cal : tmplist) {
						String img = this.getCalImg(cal.getOaCalenderLevel(), request);
						content += img + "&nbsp;";
						if (cal.getOaCalenderStatus() == EnumUtil.OA_CALENDER_STATUS.one.value) {
							content += "<a class='cal' href='javascript:void(0)' onmouseover=\"changeStatus('" + cal.getOaCalenderStatus() + "');\" id='" + cal.getPrimaryKey() + "'><font color='#00BD00'>"
									+ cal.getOaCalenderStart().substring(5, 16) + "--" + cal.getOaCalenderEnd().substring(5, 16) + "</font>&nbsp&nbsp<font color='#476074'>"
									+ cal.getLibrary().getLibraryInfoName() + ":</font>&nbsp&nbsp<font color='#0E75B7'>" + cal.getOaCalenderContent()
									+ "</font><font color='#00BD00'> (已完成)</font></a><br/>";
						} else {
							content += "<a class='cal' href='javascript:void(0)' onmouseover=\"changeStatus('" + cal.getOaCalenderStatus() + "');\" id='" + cal.getPrimaryKey() + "'><font color='#EC6907'>"
									+ cal.getOaCalenderStart().substring(5, 16) + "--" + cal.getOaCalenderEnd().substring(5, 16) + "</font>&nbsp&nbsp<font color='#476074'>"
									+ cal.getLibrary().getLibraryInfoName() + ":</font>&nbsp&nbsp<font color='#0E75B7'>" + cal.getOaCalenderContent()
									+ "</font><font color='#EC6907'> (未完成)</font></a><br/>";
						}
					}
				}
			}

			CalenderBean calbean = new CalenderBean(weekDays[i] + "<br/>" + dateOfWeeks[i], content, newdate, newyear, newmonth, newday);
			list.add(calbean);
		}

		logger.info("显示日程安排(按周显示)...");
		return WebUtilWork.WebResultPack(list);
	}

	/**
	 * 显示日程安排(按日显示)
	 * 
	 * @param context
	 * @param request
	 * @param year
	 * @param month
	 * @param day
	 * @param index
	 * @return list
	 */
	public ResultBean listOaCalender(ServletContext context, HttpServletRequest request, int year, int month, int day, int index) {
		List<CalenderBean> list = new ArrayList<CalenderBean>();

		GregorianCalendar calendar = new GregorianCalendar(year, month, day + index);
		int newyear = calendar.get(Calendar.YEAR);
		int newmonth = calendar.get(Calendar.MONTH);
		int newday = calendar.get(Calendar.DATE);

		String newdate = getYearAndMonthAndDay(newyear, newmonth + 1, newday);

		SessionUser user = (SessionUser) LoginContext.getSessionValueByLogin(request);
		OaCalender oaCalender = new OaCalender();
		oaCalender.setCompanyId((int) user.getCompanyId());
		oaCalender.setOaCalenderEmp(user.getEmployeeInfo().getPrimaryKey());
		oaCalender.setOaCalenderStart(newdate);

		List<OaCalender> listoaCalender = null;
		listoaCalender = workArrangeService.listOaCalender(oaCalender);

		for (OaCalender oc : listoaCalender) {
			oc.setLibrary(UtilTool.getLibraryInfoByPk(context, request, oc.getOaCalenderType()));
		}

		Map<String, ArrayList<OaCalender>> map = new HashMap<String, ArrayList<OaCalender>>();
		if (listoaCalender != null && listoaCalender.size() > 0) {
			for (OaCalender cal : listoaCalender) {
				String key = cal.getOaCalenderStart().substring(11, 13) + ":00";// 生成主键
				if (map.containsKey(key)) {// 判断键是否存在,存在拿出来放入集合，否则创建新集合
					ArrayList<OaCalender> calList = map.get(key);
					calList.add(cal);
				} else {
					ArrayList<OaCalender> tmpList = new ArrayList<OaCalender>();
					tmpList.add(cal);
					map.put(key, tmpList);
				}
			}
		}
		//time格式
		String[] time = new String[24];  
		for (int i = 0; i < 24; i++) {
			if (i < 10) {
				time[i] = "0" + i + ":00";
			} else {
				time[i] = i + ":00";
			}
		}

		for (int i = 0; i < time.length; i++) {
			String content = "";
			if (map != null && map.size() > 0) {
				String key = time[i];
				if (map.containsKey(key)) {
					ArrayList<OaCalender> tmplist = map.get(key);
					for (OaCalender cal : tmplist) {
						String img = this.getCalImg(cal.getOaCalenderLevel(), request);
						content += img + "&nbsp;";
						if (cal.getOaCalenderStatus() == EnumUtil.OA_CALENDER_STATUS.one.value) {
							content += "<a class='cal' href='javascript:void(0)' onmouseover=\"changeStatus('" + cal.getOaCalenderStatus() + "');\" id='" + cal.getPrimaryKey() + "'><font color='#00BD00'>"
									+ cal.getOaCalenderStart().substring(5, 16) + "--" + cal.getOaCalenderEnd().substring(5, 16) + "</font>&nbsp&nbsp<font color='#476074'>"
									+ cal.getLibrary().getLibraryInfoName() + ":</font>&nbsp&nbsp<font color='#0E75B7'>" + cal.getOaCalenderContent()
									+ "</font><font color='#00BD00'> (已完成)</font></a><br/>";
						} else {
							content += "<a class='cal' href='javascript:void(0)' onmouseover=\"changeStatus('" + cal.getOaCalenderStatus() + "');\" id='" + cal.getPrimaryKey() + "'><font color='#EC6907'>"
									+ cal.getOaCalenderStart().substring(5, 16) + "--" + cal.getOaCalenderEnd().substring(5, 16) + "</font>&nbsp&nbsp<font color='#476074'>"
									+ cal.getLibrary().getLibraryInfoName() + ":</font>&nbsp&nbsp<font color='#0E75B7'>" + cal.getOaCalenderContent()
									+ "</font><font color='#EC6907'> (未完成)</font></a><br/>";
						}
					}
				}
			}

			CalenderBean calbean = new CalenderBean(time[i], content, newdate, newyear, newmonth, newday);
			list.add(calbean);
		}

		logger.info("显示日程安排(按日显示)...");
		return WebUtilWork.WebResultPack(list);
	}
	/**工作日志重要级显示
	 * 
	 * @param level
	 * @param request
	 * @return
	 */
	private String getCalImg(int level, HttpServletRequest request) {
		String img = "";
		if (level == EnumUtil.OA_CALENDER_LEVEL.one.value) {
			img = "<img title ='重要/紧急' src='" + request.getContextPath() + "/images/grid_images/zyjj.png' border='0'/>";
		} else if (level == EnumUtil.OA_CALENDER_LEVEL.two.value) {
			img = "<img title ='重要/不紧急' src='" + request.getContextPath() + "/images/grid_images/zybjj.png' border='0'/>";
		} else if (level == EnumUtil.OA_CALENDER_LEVEL.three.value) {
			img = "<img title ='不重要/紧急' src='" + request.getContextPath() + "/images/grid_images/bzyjj.png' border='0'/>";
		} else {
			img = "<img title ='不重要/不紧急' src='" + request.getContextPath() + "/images/grid_images/bzybjj.png' border='0'/>";
		}
		return img;
	}

	/**
	 * 显示日程安排(按年显示)
	 * 
	 * @param context
	 * @param request
	 * @param year
	 * @param month
	 * @param day
	 * @param index
	 * @return list
	 */
	public ResultBean listOaCalenderYear(ServletContext context, HttpServletRequest request, int year, int month, int day, int index) {
		List<CalenderBean> list = new ArrayList<CalenderBean>();

		GregorianCalendar calendar = new GregorianCalendar(year + index, month, day);
		int newyear = calendar.get(Calendar.YEAR);
		int newmonth = calendar.get(Calendar.MONTH);
		int newday = calendar.get(Calendar.DATE);

		String newdate = getYearAndMonthAndDay(newyear, newmonth + 1, newday);

		SessionUser user = (SessionUser) LoginContext.getSessionValueByLogin(request);

		List<OaCalender> listyear = new ArrayList<OaCalender>();
		UtilDate ud = new UtilDate(year, month, day, index);
		String[] dateOfWeeks = ud.getEachMonthOfYear();

		for (int i = 0; i < 12; i++) {
			OaCalender oaCalender = new OaCalender();
			oaCalender.setCompanyId((int) user.getCompanyId());
			oaCalender.setOaCalenderEmp(user.getEmployeeInfo().getPrimaryKey());
			
			oaCalender.setOaCalenderStart(dateOfWeeks[i]);
			listyear.add(oaCalender);
		}

		Map<String, List<OaCalender>> mapyear = new HashMap<String, List<OaCalender>>();

		for (OaCalender cal : listyear) {
			List<OaCalender> tmpList = new ArrayList<OaCalender>();
			tmpList = workArrangeService.listOaCalender(cal);
			mapyear.put(cal.getOaCalenderStart(), tmpList);
		}
		 // 对现实内容进行处理
		for (int i = 0; i < dateOfWeeks.length; i++) {
			String[] time = new String[12];
			if (i < 9) {
				time[i] = "0" + (i + 1) + "月";
			} else {
				time[i] = (i + 1) + "月";
			}
			String content = "";
			String key = dateOfWeeks[i];
			List<OaCalender> tmplist = mapyear.get(key);
			for (OaCalender oc : tmplist) {
				oc.setLibrary(UtilTool.getLibraryInfoByPk(context, request, oc.getOaCalenderType()));
			}
			for (OaCalender cal : tmplist) {
				String img = this.getCalImg(cal.getOaCalenderLevel(), request);
				content += img + "&nbsp;";
				if (cal.getOaCalenderStatus() == EnumUtil.OA_CALENDER_STATUS.one.value) {
					content += "<a class='cal' href='javascript:void(0)' onmouseover=\"changeStatus('" + cal.getOaCalenderStatus() + "');\" id='" + cal.getPrimaryKey() + "'><font color='#00BD00'>" + cal.getOaCalenderStart().substring(5, 16)
							+ "--" + cal.getOaCalenderEnd().substring(5, 16) + "</font>&nbsp&nbsp<font color='#476074'>" + cal.getLibrary().getLibraryInfoName()
							+ ":</font>&nbsp&nbsp<font color='#0E75B7'>" + cal.getOaCalenderContent() + "</font><font color='#00BD00'> (已完成)</font></a><br/>";
				} else {
					content += "<a class='cal' href='javascript:void(0)' onmouseover=\"changeStatus('" + cal.getOaCalenderStatus() + "');\" id='" + cal.getPrimaryKey() + "'><font color='#EC6907'>" + cal.getOaCalenderStart().substring(5, 16)
							+ "--" + cal.getOaCalenderEnd().substring(5, 16) + "</font>&nbsp&nbsp<font color='#476074'>" + cal.getLibrary().getLibraryInfoName()
							+ ":</font>&nbsp&nbsp<font color='#0E75B7'>" + cal.getOaCalenderContent() + "</font><font color='#EC6907'> (未完成)</font></a><br/>";
				}
			}
			CalenderBean calbean = new CalenderBean(time[i], content, newdate, newyear, newmonth, newday);
			list.add(calbean);
		}

		logger.info("显示日程安排(按年显示)...");
		return WebUtilWork.WebResultPack(list);
	}

	/**
	 * 显示日程安排(按月显示)
	 * 
	 * @param context
	 * @param request
	 * @param year
	 * @param month
	 * @param index
	 * @return templist
	 */
	public ResultBean listOaCalenderMonth(ServletContext context, HttpServletRequest request, int year, int month, int index) {

		WorkCalenderMonth plan = new WorkCalenderMonth();

		GregorianCalendar calendar = new GregorianCalendar(year, month + index, 1);
		int newyear = calendar.get(Calendar.YEAR);
		int newmonth = calendar.get(Calendar.MONTH);

		SessionUser user = (SessionUser) LoginContext.getSessionValueByLogin(request);
		String dateStart = getYearAndMonthAndDay(newyear, newmonth + 1, 1);
		String dateEnd = getYearAndMonthAndDay(newyear, newmonth + 1, 31);

		OaCalender oaCalender = new OaCalender();
		oaCalender.setCompanyId((int) user.getCompanyId());
		oaCalender.setOaCalenderEmp(user.getEmployeeInfo().getPrimaryKey());
		oaCalender.setLastmodiId("1");// 给SQL日期做判断
		oaCalender.setOaCalenderStart(dateStart + "," + dateEnd);

		List<OaCalender> listoaCalender = workArrangeService.listOaCalender(oaCalender);

		for (OaCalender oc : listoaCalender) {
			oc.setLibrary(UtilTool.getLibraryInfoByPk(context, request, oc.getOaCalenderType()));
		}

		Map<Integer, List<OaCalender>> map = new HashMap<Integer, List<OaCalender>>();
		if (listoaCalender != null && listoaCalender.size() > 0) {
			for (OaCalender cal : listoaCalender) {
				int temp = Integer.valueOf(cal.getOaCalenderStart().substring(8, 10));// 生成主键
				Integer key = new Integer(temp);
				if (map.containsKey(key)) {// 判断键是否存在,存在拿出来放入集合，否则创建新集合
					List<OaCalender> calList = map.get(key);
					calList.add(cal);
				} else {
					ArrayList<OaCalender> tmpList = new ArrayList<OaCalender>();
					tmpList.add(cal);
					map.put(key, tmpList);
				}
			}
		}

		plan.setMap(map);
		plan.setReqeust(request);
		String caltable = new String();
		caltable = plan.newCalendar(newyear, newmonth,request);

		List<String> templist = new ArrayList<String>();
		templist.add(caltable);

		String newdate = getYearAndMonth(newyear, newmonth + 1);
		templist.add(String.valueOf(newyear));
		templist.add(String.valueOf(newmonth));
		templist.add(newdate);

		logger.info("显示日程安排(按月显示)...");
		return WebUtilWork.WebResultPack(templist);
	}

	private String getYearAndMonth(int year, int month) {
		String m = String.valueOf(month < 10 ? "0" + month : month);
		String tmp = year + "-" + m;
		return tmp;
	}

	/**
	 * 显示所有日程（表格显示）
	 * 
	 * @param context
	 * @param request
	 * @param oaCalender
	 * @param pager
	 * @return list, pager
	 */
	public ResultBean getOaCalenderList(ServletContext context, HttpServletRequest request, OaCalender oaCalender, Pager pager) {
		List<OaCalender> list = null;
		SessionUser user = (SessionUser) LoginContext.getSessionValueByLogin(request);
		oaCalender.setCompanyId((int) user.getCompanyId());
		oaCalender.setOaCalenderEmp(user.getEmployeeInfo().getPrimaryKey());
		pager = PagerHelper.getPager(pager, workArrangeService.listOaCalenderCount(oaCalender));
		list = workArrangeService.getAllOaCalender(oaCalender, pager);
		for (OaCalender oc : list) {
			oc.setLibrary(UtilTool.getLibraryInfoByPk(context, request, oc.getOaCalenderType()));
		}
		logger.info("显示所有日志（表格显示）...");
		return WebUtilWork.WebResultPack(list, pager);
	}

	/**
	 * 新增日程
	 * 
	 * @param context
	 * @param request
	 * @param oaCalender
	 * @return null
	 */
	public ResultBean saveOaCalender(ServletContext context, HttpServletRequest request, OaCalender oaCalender) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date starTime = null;
		Date enddate = null;
		try {
			starTime = sdf.parse(oaCalender.getOaCalenderStart());
			enddate = sdf.parse(oaCalender.getOaCalenderEnd());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (starTime.after(enddate)) {
			return new ResultBean(false, "开始时间大于结束时间,不能保存！");
		}

		String empid = UtilTool.getEmployeeId(request);
		String nowtime = UtilWork.getNowTime();

		if (oaCalender.getPrimaryKey() > 0) {
			OaCalender tmp = workArrangeService.getOaCalenderByPk(oaCalender.getPrimaryKey());
			oaCalender.setRecordId(tmp.getRecordId());
			oaCalender.setRecordDate(tmp.getRecordDate());
			oaCalender.setOaCalenderStatus(tmp.getOaCalenderStatus());
			oaCalender.setOaCalenderEmp(tmp.getOaCalenderEmp());

		} else {
			oaCalender.setRecordId(empid);
			oaCalender.setRecordDate(nowtime);
			oaCalender.setOaCalenderStatus(2); // 未完成
			oaCalender.setOaCalenderEmp(empid);
		}

		oaCalender.setCompanyId(UtilTool.getCompanyId(request));  
		oaCalender.setLastmodiId(empid);
		oaCalender.setLastmodiDate(nowtime);

		workArrangeService.saveOaCalender(oaCalender);
		logger.info("新增日程...");
		return WebUtilWork.WebResultPack(null);

	}

	/**
	 * 完成或未完成日程
	 * 
	 * @param context
	 * @param request
	 * @param pk
	 * @return null
	 */
	public ResultBean completeOaCalenderByPks(ServletContext context, HttpServletRequest request, long pk) {
		OaCalender oaCalender = workArrangeService.getOaCalenderByPk(pk);
		int status = oaCalender.getOaCalenderStatus();

		if (status == 1) {
			oaCalender.setOaCalenderStatus(2);
		} else {
			oaCalender.setOaCalenderStatus(1);
		}

		workArrangeService.saveOaCalender(oaCalender);
		logger.info("完成或未完成日程...");
		return WebUtilWork.WebResultPack(null);

	}

	/**
	 * id查找日程
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean getOaCalenderByPk(ServletContext context, HttpServletRequest request, long oaCalenderpk) {

		OaCalender oaCalender = workArrangeService.getOaCalenderByPk(oaCalenderpk);
		oaCalender.setLibrary(UtilTool.getLibraryInfoByPk(context, request, oaCalender.getOaCalenderType()));
		List<OaCalender> list = new ArrayList<OaCalender>();
		list.add(oaCalender);
		logger.info("根据ID查找日程...");
		return WebUtilWork.WebResultPack(list);
	}

	/**
	 * 删除工作日志
	 * 
	 * @param context
	 * @param request
	 * @param oaCalenderPKs[]
	 * @return
	 */

	public ResultBean deleteOaCalenderByPks(ServletContext context, HttpServletRequest request, long[] oaCalenderPKs) {

		workArrangeService.deleteOaCalenderByPks(oaCalenderPKs);
		return WebUtilWork.WebResultPack(null);
	}

}
