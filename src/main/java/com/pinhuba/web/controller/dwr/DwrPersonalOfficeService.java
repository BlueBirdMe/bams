package com.pinhuba.web.controller.dwr;

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
import org.quartz.SchedulerException;
import com.pinhuba.common.module.ResultBean;
import com.pinhuba.common.pages.Pager;
import com.pinhuba.common.pages.PagerHelper;
import com.pinhuba.common.util.UtilTool;
import com.pinhuba.common.util.UtilWork;
import com.pinhuba.common.util.WebUtilWork;
import com.pinhuba.core.iservice.IPersonalOfficeSerivce;
import com.pinhuba.core.pojo.HrmTimedrecord;
import com.pinhuba.core.pojo.OaNotebook;
import com.pinhuba.core.pojo.OaTools;
import com.pinhuba.core.pojo.SysLibraryInfo;
import com.pinhuba.core.scheduler.TimedScheduler;
import com.pinhuba.core.scheduler.job.TimedJob;
/**********************************************
Class name: 个人办公dwr服务
Description:提供个人办公模块的各种服务
Others:         
History:        
peng.ning    2010.4.27     v3.0
**********************************************/
public class DwrPersonalOfficeService {
	// 日志文件
	private final static Logger logger = Logger.getLogger(DwrPersonalOfficeService.class);
	@Resource
	private IPersonalOfficeSerivce personalOfficeSerivce;

	public IPersonalOfficeSerivce getPersonalOfficeSerivce() {
		return personalOfficeSerivce;
	}

	public void setPersonalOfficeSerivce(IPersonalOfficeSerivce personalOfficeSerivce) {
		this.personalOfficeSerivce = personalOfficeSerivce;
	}

	
	/**
	 * 显示所有工具
	 * 
	 * @param context
	 * @param request
	 * @param oaTools
	 * @param pager
	 * @return
	 */
	public Map<SysLibraryInfo, ArrayList<OaTools>> listOaTools(ServletContext context, HttpServletRequest request, OaTools oaTools) {
		oaTools.setCompanyId(UtilTool.getCompanyId(request));
		oaTools.setOaToolEmp(UtilTool.getEmployeeId(request));
		// 判断是否需要初始化
		personalOfficeSerivce.isHashToolsByEmpId(oaTools);
		// 查询并封装
		Map<SysLibraryInfo, ArrayList<OaTools>> toolsMap = new HashMap<SysLibraryInfo, ArrayList<OaTools>>();

		List<SysLibraryInfo> libList = personalOfficeSerivce.getSysLibraryInfoByCode();
		List<OaTools> tools = personalOfficeSerivce.getAllOaTools(oaTools);

		for (SysLibraryInfo lib : libList) {
			for (OaTools oaTools2 : tools) {
				if (lib.getPrimaryKey() == oaTools2.getOaToolType().longValue()) {
					if (toolsMap.containsKey(lib)) {
						ArrayList<OaTools> list = toolsMap.get(lib);
						list.add(oaTools2);
					} else {
						ArrayList<OaTools> newlist = new ArrayList<OaTools>();
						newlist.add(oaTools2);
						toolsMap.put(lib, newlist);
					}
				}
			}
		}

		return toolsMap;
	}

	/**
	 * 新增常用工具
	 * 
	 * @param context
	 * @param request
	 * @param oaTool
	 * @return
	 */
	public ResultBean addTool(ServletContext context, HttpServletRequest request, OaTools oaTool, String image) {
		// 编辑状态
		if (oaTool.getPrimaryKey() > 0) {
			OaTools oaTool2 = personalOfficeSerivce.getOaToolsByPk(oaTool.getPrimaryKey());
			if (image.length() <= 0) {
				oaTool.setOaToolImage(oaTool2.getOaToolImage());
				oaTool.setOaToolImageId(null);
			} else {
				if (oaTool2.getOaToolImageId() != null && oaTool2.getOaToolImageId().longValue() > 0) {
					UtilTool.deleteAttachmentsNoFile(context, request, oaTool2.getOaToolImageId().toString());
				}
				String[] imageStrings = image.split("\\|");
				oaTool.setOaToolImage(imageStrings[0]);

				String id = UtilTool.saveImages(context, request, image);
				oaTool.setOaToolImageId(Integer.parseInt(id));
			}
		} else {
			// 新增状态
			String[] imageStrings = image.split("\\|");
			oaTool.setOaToolImage(imageStrings[0]);
			String imageid = "";
			if (image.length() > 0) {
				imageid = UtilTool.saveImages(context, request, image);
			}
			int f = 0;
			if (imageid != null && imageid.length() > 0) {
				f = Integer.parseInt(imageid);
				oaTool.setOaToolImageId(f);
			}
		}
		oaTool.setRecordDate(UtilWork.getNowTime());
		oaTool.setRecordId(UtilTool.getEmployeeId(request));
		oaTool.setOaToolEmp(UtilTool.getEmployeeId(request));
		oaTool.setCompanyId(UtilTool.getCompanyId(request));
		oaTool.setLastmodiDate(UtilWork.getNowTime());
		oaTool.setLastmodiId(UtilTool.getEmployeeId(request));
		personalOfficeSerivce.saveOaTools(oaTool);
		return WebUtilWork.WebResultPack(null);
	}

	/*
	 * 根据类型获得工具集
	 * 
	 */
	public ResultBean getToolByType(ServletContext context, HttpServletRequest request, int type) {
		List<OaTools> list = personalOfficeSerivce.getOaToolsListByType(type);
		return WebUtilWork.WebResultPack(list);
	}

	/*
	 * 根据类型和主键获得工具
	 */
	public ResultBean getToolByTypeAndPk(ServletContext context, HttpServletRequest request, int type, int pk) {
		List<OaTools> list = personalOfficeSerivce.getOaToolByTypeAndPk(type, pk);
		return WebUtilWork.WebResultPack(list);
	}

	public ResultBean deleteOatoolByPk(ServletContext context, HttpServletRequest request, int pk) {
		personalOfficeSerivce.deleteOaTool(pk);
		return WebUtilWork.WebResultPack(null);
	}

	public boolean isregiserByTime(String prescribeTime, int addTime, int reduceTime) throws Exception{
		String[] times = prescribeTime.split(":");
		boolean bl = false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String time = sdf.format(new Date());

		GregorianCalendar calendar = new GregorianCalendar();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);

		calendar = new GregorianCalendar(year, month, day, Integer.parseInt(times[0]), Integer.parseInt(times[1]));
		int hours = calendar.get(Calendar.HOUR_OF_DAY);
		int minutes = calendar.get(Calendar.MINUTE);

		GregorianCalendar beforeCal = new GregorianCalendar(year, month, day, hours, minutes - reduceTime);
		GregorianCalendar afterCal = new GregorianCalendar(year, month, day, hours, minutes + addTime);

		String beforeDate = sdf.format(beforeCal.getTime());
		String afterDate = sdf.format(afterCal.getTime());

		boolean beforebl = sdf.parse(time).after(sdf.parse(beforeDate)) || time.equals(beforeDate);
		boolean afterbl =  sdf.parse(time).before(sdf.parse(afterDate)) || time.equals(afterDate);
		if (afterbl && beforebl) {
			bl = true;
		}

		return bl;
	}
	
	/**
	 * 标准时间和当前时间的比对
	 * 
	 * @param prescribeTime
	 * @param flag true为上班，false为下班
	 * @return false prescribeTime在当前时间之前，true prescribeTime在当前时间之后
	 */
	public boolean checkByPrescribeTime(String prescribeTime,boolean flag) {
		String[] times = prescribeTime.split(":");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String time = sdf.format(new Date());

		GregorianCalendar calendar = new GregorianCalendar();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);

		calendar = new GregorianCalendar(year, month, day, Integer.parseInt(times[0]), Integer.parseInt(times[1]));
		int hours = calendar.get(Calendar.HOUR_OF_DAY);
		int minutes = calendar.get(Calendar.MINUTE);
		// 标准时间
		GregorianCalendar standerCal = new GregorianCalendar(year, month, day, hours, minutes);

		String standerDate = sdf.format(standerCal.getTime());

		boolean beforebl = true;
		if(flag){
			//true为当前时间在规定时间前或等于规定时间，false为当前时间在规定时间后
			beforebl = UtilWork.checkDayBySF(standerDate, time, sdf) || time.equals(standerDate);
		}else{
			//true为当前时间在规定时间后或等于规定时间，false为当前时间在规定时间前
			beforebl = UtilWork.checkDayBySF(time, standerDate, sdf) || time.equals(standerDate);	
		}
		return beforebl;
	}

	/**
	 * 个人便签
	 */
	public ResultBean listNotebook(ServletContext context, HttpServletRequest request, OaNotebook notebook, Pager pager) {
		notebook.setOaNotebookEmp(UtilTool.getEmployeeId(request));
		notebook.setCompanyId(UtilTool.getCompanyId(request));
		pager = PagerHelper.getPager(pager, personalOfficeSerivce.getNotebookCount(notebook));
		List<OaNotebook> list = personalOfficeSerivce.getAllNotebook(notebook, pager);

		logger.info("显示个人便签...");
		return WebUtilWork.WebResultPack(list, pager);
	}

	/**
	 * 添加个人便签
	 */

	public ResultBean saveNotebook(ServletContext context, HttpServletRequest request, String conText) {

		OaNotebook notebook = new OaNotebook();
		notebook.setOaNotebookCreattime(UtilWork.getNowTime());
		notebook.setCompanyId(UtilTool.getCompanyId(request));
		notebook.setOaNotebookContext(conText);
		notebook.setOaNotebookEmp(UtilTool.getEmployeeId(request));
		personalOfficeSerivce.savePersonalNotebook(notebook);
		logger.info("保存个人便签...");
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 删除便签
	 */
	public ResultBean deleteNotebookById(ServletContext context, HttpServletRequest request, long[] ids) {

		try {
			personalOfficeSerivce.delectNotebookByid(ids);
			logger.info("删除新闻信息...");
		} catch (Exception e) {
			logger.error("删除新闻出错..." + e.getMessage());
			return new ResultBean(false, e.getMessage());
		}
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 查询便签根据ID
	 */
	public ResultBean getNotebookById(ServletContext context, HttpServletRequest request, long id) {

		OaNotebook notebook = personalOfficeSerivce.getNotebookById(id);

		logger.info("显示个人便签...");
		List<OaNotebook> list = new ArrayList<OaNotebook>();
		list.add(notebook);
		return WebUtilWork.WebResultPack(list);
	}
	
	/**
	 * 查询有效的定时设置， by 公司ID 和 雇员ID
	 */
	public ResultBean getTimedValidByCompanyAndEmpId(ServletContext context, HttpServletRequest request,HrmTimedrecord record, Pager pager) {
		HrmTimedrecord temp = new HrmTimedrecord();
		temp.setCompanyId(UtilTool.getCompanyId(request));
		temp.setRecordId(UtilTool.getEmployeeId(request));
		temp.setTimedDescription(record.getTimedDescription());
		pager = PagerHelper.getPager(pager, personalOfficeSerivce
				.getTimedValidCount(temp));
		List<HrmTimedrecord> list = personalOfficeSerivce
				.getTimedValidByCompanyAndEmpId(temp, pager);
		logger.info("查询有效的定时提醒...");
		return WebUtilWork.WebResultPack(list, pager);
	}
	
	/**
	 * 查询失效的定时设置， by 公司ID 和 雇员ID
	 */
	public ResultBean getTimedInValidByCompanyAndEmpId(ServletContext context, HttpServletRequest request, HrmTimedrecord record, Pager pager) {
		record.setRecordId(UtilTool.getEmployeeId(request));
		pager = PagerHelper.getPager(pager, personalOfficeSerivce.getTimedInValidCount(record));
		List<HrmTimedrecord> list = personalOfficeSerivce.getTimedInValidByCompanyAndEmpId(record, pager);
		logger.info("查询无效的定时提醒...");
		return WebUtilWork.WebResultPack(list, pager);
	}

	/**
	 * 清空无效的定时提醒
	 */
	public ResultBean deleteTimedInvalid(ServletContext context, HttpServletRequest request) {

		try {
			personalOfficeSerivce.deleteTimedInvalid(UtilTool.getCompanyId(request), UtilTool.getEmployeeId(request));
			logger.info("清空无效的定时提醒...");
		} catch (Exception e) {
			logger.error("清空无效的定时提醒出错..." + e.getMessage());
			return new ResultBean(false, e.getMessage());
		}
		return WebUtilWork.WebResultPack(null);
	}
	
	/**
	 * 查询便签根据ID
	 */
	public ResultBean getTimedRecordByPk(ServletContext context, HttpServletRequest request, long id) {

		HrmTimedrecord timedRecord = personalOfficeSerivce.getTimedRecordByPk(id);
 
		logger.info("显示个人便签...");
		List<HrmTimedrecord> list = new ArrayList<HrmTimedrecord>();
		list.add(timedRecord);
		return WebUtilWork.WebResultPack(list);
	}
	
	/**
	 * 根据主键删除
	 * @param context
	 * @param request
	 * @param ids
	 * @return
	 */
	public ResultBean deleteTimedRecordByPks(ServletContext context, HttpServletRequest request, long[] ids) {
		try {
			personalOfficeSerivce.deleteTimedRecordByPks(ids);
			logger.info("删除定时提醒...");
			for(long id: ids) {
				try {
					TimedScheduler.removeJob(TimedScheduler.JOB_PREFIX_NAME + id, context);
				} catch (SchedulerException e) {
					logger.info("移除定时队列出错...");
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			logger.error("删除定时提醒出错..." + e.getMessage());
			return new ResultBean(false, e.getMessage());
		}
		return WebUtilWork.WebResultPack(null);
	}
	
	/**
	 * 添加定时提醒
	 * @param context
	 * @param request
	 * @param conText
	 * @return
	 */
	public ResultBean saveTimedRecord(ServletContext context, HttpServletRequest request, HrmTimedrecord timedRecord) throws Exception{
		if(timedRecord.getPrimaryKey()>0 ) {
			HrmTimedrecord temp = personalOfficeSerivce.getTimedRecordByPk(timedRecord.getPrimaryKey());
			if(temp!=null) {
				timedRecord.setRecordId(temp.getRecordId());
				timedRecord.setRecordDate(temp.getRecordDate());
				timedRecord.setCompanyId(temp.getCompanyId());
			} else {
				return null;
			}
		} else {
			timedRecord.setRecordId(UtilTool.getEmployeeId(request));
			timedRecord.setRecordDate(UtilWork.getNowTime());
			timedRecord.setCompanyId(UtilTool.getCompanyId(request));
		}
		timedRecord.setLastmodiDate(UtilWork.getNowTime());
		timedRecord.setLastmodiId(UtilTool.getEmployeeId(request));
		HrmTimedrecord saved = personalOfficeSerivce.saveTimedRecord(timedRecord);
		logger.info("保存定时提醒...");
		//加入TimeJob
		String timedDate = saved.getTimedDate();
		
		String cronStr = UtilTool.getDateToQuartzStr(saved.getTimedType(), timedDate);
		
		TimedJob job = new TimedJob();
		if (timedRecord.getPrimaryKey() > 0) {
			TimedScheduler.removeJob(TimedScheduler.JOB_PREFIX_NAME + saved.getPrimaryKey(), context);
		}
		TimedScheduler.addJob(TimedScheduler.JOB_PREFIX_NAME + saved.getPrimaryKey(), job, cronStr, context);
		return WebUtilWork.WebResultPack(null);
	}
}
