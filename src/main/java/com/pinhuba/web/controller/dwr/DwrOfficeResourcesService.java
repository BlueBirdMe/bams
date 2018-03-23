package com.pinhuba.web.controller.dwr;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

import com.pinhuba.common.module.ResultBean;
import com.pinhuba.common.pages.Pager;
import com.pinhuba.common.pages.PagerHelper;
import com.pinhuba.common.util.EnumUtil;
import com.pinhuba.common.util.UtilTool;
import com.pinhuba.common.util.UtilWork;
import com.pinhuba.common.util.WebUtilWork;
import com.pinhuba.core.iservice.IHrmEmployeeService;
import com.pinhuba.core.iservice.IOfficeResourcesService;
import com.pinhuba.core.pojo.HrmDepartment;
import com.pinhuba.core.pojo.HrmEmployee;
import com.pinhuba.core.pojo.OaBoardroom;
import com.pinhuba.core.pojo.OaBook;
import com.pinhuba.core.pojo.OaBookBr;
import com.pinhuba.core.pojo.OaBookType;
import com.pinhuba.core.pojo.OaCar;
import com.pinhuba.core.pojo.OaCarApply;
import com.pinhuba.core.pojo.OaCarMaintain;
import com.pinhuba.core.pojo.OaMeetapply;
import com.pinhuba.core.pojo.OaSummary;
import com.pinhuba.core.pojo.SysLibraryInfo;

/**********************************************
Class name: 办公资源dwr服务
Description:提供办公资源模块的各种服务
Others:         
History:        
peng.ning    2010.4.27     v3.0
**********************************************/
public class DwrOfficeResourcesService {

	private final static Logger logger = Logger.getLogger(DwrOfficeResourcesService.class);

	@Resource
	private IOfficeResourcesService officeResourcesService;
	@Resource
	private IHrmEmployeeService employeeinfoService;

	/**
	 * 显示所有会议室
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean listBoadrooms(ServletContext context, HttpServletRequest request, OaBoardroom oaBoardroom, Pager pager) {
		List<OaBoardroom> list = null;
		try {
			oaBoardroom.setCompanyId(UtilTool.getCompanyId(request));
			pager = PagerHelper.getPager(pager, officeResourcesService.listBoadroomCount(oaBoardroom));
			list = officeResourcesService.getAllBoadroom(oaBoardroom, pager);

			logger.info("显示所有会议室...");
		} catch (Exception e) {
			logger.error("显示所有会议室出错..." + e.getMessage());
		}
		return WebUtilWork.WebResultPack(list, pager);
	}

	/**
	 * 新增会议室
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean saveBoadroom(ServletContext context, HttpServletRequest request, OaBoardroom boardroom) {
		// 添加默认属性
		boardroom.setCompanyId(UtilTool.getCompanyId(request));
		boardroom.setLastmodiId(UtilTool.getEmployeeId(request));
		boardroom.setLastmodiDate(UtilWork.getNowTime());
		boardroom.setRecordId(UtilTool.getEmployeeId(request));

		officeResourcesService.saveBoadroom(boardroom);
		logger.info("新增会议室...");
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 编辑会议
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean updateBoadroom(ServletContext context, HttpServletRequest request, OaBoardroom boardroom) {
		// 添加默认属性
		boardroom.setCompanyId(UtilTool.getCompanyId(request));
		boardroom.setLastmodiId(UtilTool.getEmployeeId(request));
		boardroom.setLastmodiDate(UtilWork.getNowTime());

		officeResourcesService.saveBoadroom(boardroom);
		logger.info("编辑会议...");
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 根据id删除会议
	 * 
	 * @param context
	 * @param request
	 * @param pks 会议主键
	 * @return
	 */
	public ResultBean deleteMeetingById(ServletContext context, HttpServletRequest request, long[] pks) {
		for (long l : pks) {
			OaMeetapply meetapply = officeResourcesService.getMeetapplyByPk(l);
			if (meetapply.getOaMeetapplyAffix() != null
				&& meetapply.getOaMeetapplyAffix().length() > 0) {
				UtilTool.deleteAttachmentsAndFile(context, request, meetapply
						.getOaMeetapplyAffix());
			}
			List<OaSummary> list = officeResourcesService.getOaSummaryByPk(l);
			for (OaSummary s : list) {
				if (s.getOaSummaryContent() != null && s.getOaSummaryContent().length() > 0) {
					UtilTool.deleteAttachmentsAndFile(context, request, s.getOaSummaryContent());
				}
			}
		}
		officeResourcesService.deleteMeetapplysByPks(pks);
		logger.info("删除会议信息...");

		return WebUtilWork.WebResultPack(null);

	}

	public int summaryCount(ServletContext context, HttpServletRequest request, long pk) {
		List<OaSummary> sumList = officeResourcesService.getOaSummaryByPk(pk);
		return sumList.size();
	}

	/**
	 * 根据ID获取会议室
	 * 
	 * @param context
	 * @param request
	 * @return 会议室集合
	 */
	public ResultBean getBoadroomByPk(ServletContext context, HttpServletRequest request, long boadroomPk) {
		OaBoardroom boardroom = officeResourcesService.getBoadroomByPk(boadroomPk);
		List<OaBoardroom> list = new ArrayList<OaBoardroom>();
		list.add(boardroom);
		logger.info("根据ID获取会议室...");
		return WebUtilWork.WebResultPack(list);
	}

	/**
	 * 删除会议室
	 * 
	 * @param context
	 * @param request
	 * @return
	 */

	public ResultBean deleteBoadroomsByPks(ServletContext context, HttpServletRequest request, long[] boadroomPks) {
		officeResourcesService.deleteBoadroomsByPks(boadroomPks);
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 显示所有会议申请
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean listMeetapplys(ServletContext context,HttpServletRequest request, OaMeetapply oaMeetapply, Pager pager) {
		List<OaMeetapply> list = null;
		oaMeetapply.setCompanyId(UtilTool.getCompanyId(request));
		pager = PagerHelper.getPager(pager, officeResourcesService.listAllMeetapplyCount(oaMeetapply));
		list = officeResourcesService.getAllMeetapply(oaMeetapply, pager);
		for (OaMeetapply apply : list) {

			apply.setLibrary(UtilTool.getLibraryInfoByPk(context, request,apply.getOaMeetapplyType()));
			apply.setMeetApplyRoomObj(officeResourcesService.getBoadroomByPk(apply.getOaMeetapplyRoom().longValue()));
			apply.setEmployee(employeeinfoService.getEmployeeByPK(apply.getOaMeetapplyEmp()));

			OaSummary oaSummary = new OaSummary();
			oaSummary.setCompanyId(apply.getCompanyId());
			oaSummary.setOaSummaryMeetId((int) apply.getPrimaryKey());

			int count = officeResourcesService.listSummaryCount(oaSummary);
			apply.setSummaryCount(count + "");
		}
		logger.info("显示所有会议申请...");
		return WebUtilWork.WebResultPack(list, pager);
	}
	/**
	 * 
	 * @param context
	 * @param request
	 * @param oaMeetapply 会议
	 * @param pager 
	 * @return
	 */
	public ResultBean listMyMeetSummary(ServletContext context,HttpServletRequest request,OaMeetapply oaMeetapply,Pager pager){
		List<OaMeetapply> list = null;
		String empId = UtilTool.getEmployeeId(request)+",";
		oaMeetapply.setOaMeetapplyEmp(empId);
		oaMeetapply.setCompanyId(UtilTool.getCompanyId(request));
		pager = PagerHelper.getPager(pager, officeResourcesService.listMySummaryMeetCount(oaMeetapply,empId));
		list = officeResourcesService.getMySummaryMeet(oaMeetapply, empId, pager);
		for (OaMeetapply apply : list) {

			apply.setLibrary(UtilTool.getLibraryInfoByPk(context, request,apply.getOaMeetapplyType()));
			apply.setMeetApplyRoomObj(officeResourcesService.getBoadroomByPk(apply.getOaMeetapplyRoom().longValue()));
			apply.setEmployee(employeeinfoService.getEmployeeByPK(apply.getOaMeetapplyEmp()));

			OaSummary oaSummary = new OaSummary();
			oaSummary.setCompanyId(apply.getCompanyId());
			oaSummary.setOaSummaryMeetId((int) apply.getPrimaryKey());

			int count = officeResourcesService.listSummaryCount(oaSummary);
			apply.setSummaryCount(count + "");
		}
		return WebUtilWork.WebResultPack(list, pager);
	}
	/**
	 * 列出待参加会议
	 * @param context
	 * @param request
	 * @param oaMeetapply 会议
	 * @param pager	
	 * @return
	 */
	public ResultBean listWillAttendMeeting(ServletContext context,HttpServletRequest request, OaMeetapply oaMeetapply, Pager pager){
		List<OaMeetapply> list = null;
		String empId = UtilTool.getEmployeeId(request)+",";
		oaMeetapply.setOaMeetapplyEmp(empId);
		Long depId = UtilTool.getDeptId(request);
		String dep = depId.toString()+",";
		oaMeetapply.setOaMeetapplyDep(dep);
		oaMeetapply.setOaMeetapplyEmpn(empId);
		
		oaMeetapply.setCompanyId(UtilTool.getCompanyId(request));
		pager = PagerHelper.getPager(pager, officeResourcesService.listWillAttendMeetCount(oaMeetapply,empId));
		list = officeResourcesService.getWillAttendMeet(oaMeetapply, empId, pager);
		for (OaMeetapply apply : list) {
			apply.setLibrary(UtilTool.getLibraryInfoByPk(context, request,apply.getOaMeetapplyType()));
			apply.setMeetApplyRoomObj(officeResourcesService.getBoadroomByPk(apply.getOaMeetapplyRoom().longValue()));
			apply.setEmployee(employeeinfoService.getEmployeeByPK(apply.getOaMeetapplyEmp()));
			OaSummary oaSummary = new OaSummary();
			oaSummary.setCompanyId(apply.getCompanyId());
			oaSummary.setOaSummaryMeetId((int) apply.getPrimaryKey());

			int count = officeResourcesService.listSummaryCount(oaSummary);
			apply.setSummaryCount(count + "");
		}
		logger.info("显示待参加会议...");
		return WebUtilWork.WebResultPack(list, pager);
	}
	
	
	
	/**
	 * 显示我申请的会议
	 * @param context
	 * @param request
	 * @param oaMeetapply 会议
	 * @param pager
	 * @return
	 */
	public ResultBean listMyMeetapply(ServletContext context,HttpServletRequest request,OaMeetapply oaMeetapply,Pager pager){
		List<OaMeetapply> list = null;
		String empId = UtilTool.getEmployeeId(request);
		oaMeetapply.setOaMeetapplyEmp(empId);
		pager = PagerHelper.getPager(pager,officeResourcesService.listMeetapplyCount(oaMeetapply,empId));
		list = officeResourcesService.getMyMeetapply(oaMeetapply, oaMeetapply.getOaMeetapplyEmp(), pager);
		for (OaMeetapply apply : list) {

			apply.setLibrary(UtilTool.getLibraryInfoByPk(context, request,apply.getOaMeetapplyType()));
			apply.setMeetApplyRoomObj(officeResourcesService.getBoadroomByPk(apply.getOaMeetapplyRoom().longValue()));
			apply.setEmployee(employeeinfoService.getEmployeeByPK(apply.getOaMeetapplyEmp()));

			OaSummary oaSummary = new OaSummary();
			oaSummary.setCompanyId(apply.getCompanyId());
			oaSummary.setOaSummaryMeetId((int) apply.getPrimaryKey());

			int count = officeResourcesService.listSummaryCount(oaSummary);
			apply.setSummaryCount(count + "");
		}
		return WebUtilWork.WebResultPack(list, pager);
	}
	/**
	 * 列出我已参加的会议
	 * @param context
	 * @param request
	 * @param oaMeetapply	会议
	 * @param pager
	 * @return
	 */
	public ResultBean listAttendedMeeting(ServletContext context,HttpServletRequest request,OaMeetapply oaMeetapply,Pager pager){
		List<OaMeetapply> list = null;
		String empId = UtilTool.getEmployeeId(request);
		oaMeetapply.setOaMeetapplyEmp(empId);
		pager = PagerHelper.getPager(pager,officeResourcesService.listAttendedCount(oaMeetapply,empId));
		list = officeResourcesService.getAttendedMeet(oaMeetapply, oaMeetapply.getOaMeetapplyEmp(), pager);
		for (OaMeetapply apply : list) {

			apply.setLibrary(UtilTool.getLibraryInfoByPk(context, request,apply.getOaMeetapplyType()));
			apply.setMeetApplyRoomObj(officeResourcesService.getBoadroomByPk(apply.getOaMeetapplyRoom().longValue()));
			apply.setEmployee(employeeinfoService.getEmployeeByPK(apply.getOaMeetapplyEmp()));

			OaSummary oaSummary = new OaSummary();
			oaSummary.setCompanyId(apply.getCompanyId());
			oaSummary.setOaSummaryMeetId((int) apply.getPrimaryKey());

			int count = officeResourcesService.listSummaryCount(oaSummary);
			apply.setSummaryCount(count + "");
		}
		return WebUtilWork.WebResultPack(list, pager);
	}
	/**
	 * 新增会议申请
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean saveMeetapply(ServletContext context, HttpServletRequest request, OaMeetapply meetapply) {
		// 存储附件
		if (meetapply.getOaMeetapplyAffix() != null && meetapply.getOaMeetapplyAffix().length() > 0) {
			String ids = UtilTool.saveAttachments(context, request, meetapply.getOaMeetapplyAffix());
			meetapply.setOaMeetapplyAffix(ids);
		}
		meetapply.setOaMeetapplyStatus(EnumUtil.OA_MEETAPPLY_STATUS.APPLYING.value);
		// 添加默认属性
		// meetapply.setOaMeetapplyEmp(UtilTool.getEmployeeId(request));
		// //设置申请人为当前操作人员主键
		meetapply.setCompanyId(UtilTool.getCompanyId(request));
		meetapply.setLastmodiId(UtilTool.getEmployeeId(request));
		meetapply.setLastmodiDate(UtilWork.getNowTime());
		meetapply.setOaMeetapplyDate(UtilWork.getNowTime());
		meetapply.setRecordDate(UtilWork.getNowTime());
		meetapply.setRecordId(UtilTool.getEmployeeId(request));
		meetapply.setOaMeetapplyEmp(UtilTool.getEmployeeId(request));
		officeResourcesService.saveMeetapply(meetapply);
		logger.info("新增会议申请...");
		return WebUtilWork.WebResultPack(null);
	}
	
	public ResultBean endMeeting(ServletContext context,HttpServletRequest request,long pk){
		OaMeetapply meetapply = officeResourcesService.getMeetapplyByPk(pk);
		meetapply.setOaMeetapplyStatus(EnumUtil.OA_MEETAPPLY_STATUS.COMPLETE.value);
		officeResourcesService.saveMeetapply(meetapply);
		return WebUtilWork.WebResultPack(null);
	}
	
	/**
	 * 编辑会议申请
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean updateMeetapply(ServletContext context, HttpServletRequest request, OaMeetapply meetapply) {
		OaMeetapply oaMeetapply = officeResourcesService.getMeetapplyByPk(meetapply.getPrimaryKey());
		// 删除原有附件
		if (oaMeetapply.getOaMeetapplyAffix() != null && oaMeetapply.getOaMeetapplyAffix().length() > 0) {
			UtilTool.deleteAttachmentsNoFile(context, request, oaMeetapply.getOaMeetapplyAffix());
		}
		// 保存新附件
		String affixId = UtilTool.saveAttachments(context, request, meetapply.getOaMeetapplyAffix());
		meetapply.setOaMeetapplyAffix(affixId);
		// 添加默认属性
		meetapply.setOaMeetapplyStatus(oaMeetapply.getOaMeetapplyStatus());
		meetapply.setCompanyId(oaMeetapply.getCompanyId());
		meetapply.setLastmodiId(UtilTool.getEmployeeId(request));
		meetapply.setLastmodiDate(UtilWork.getNowTime());
		meetapply.setOaMeetapplyDate(oaMeetapply.getOaMeetapplyDate());
		meetapply.setRecordDate(oaMeetapply.getRecordDate());
		meetapply.setRecordId(oaMeetapply.getRecordId());
		meetapply.setOaMeetapplyEmp(oaMeetapply.getOaMeetapplyEmp());
		officeResourcesService.saveMeetapply(meetapply);
		logger.info("编辑会议申请...");
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 根据ID获取会议申请
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean getMeetapplyByPk(ServletContext context, HttpServletRequest request, long meetapplyPk) {
		OaMeetapply meetapply = officeResourcesService.getMeetapplyByPk(meetapplyPk);
		// 加载纪要人员名字
		if (meetapply.getOaMeetapplySummary() != null && meetapply.getOaMeetapplySummary().length() > 0) {
			String empids[] = meetapply.getOaMeetapplySummary().substring(0, meetapply.getOaMeetapplySummary().length() - 1).split(",");
			String jiyao = "";
			for (String s : empids) {
				HrmEmployee employee = employeeinfoService.getEmployeeByPK(s);
				if (employee != null) {
					jiyao = jiyao + employee.getHrmEmployeeName() + ",";
				}
			}
			meetapply.setJiyaoEmpNames(jiyao);

		}

		// 加载主办部门名称
		if (meetapply.getOaMeetapplyDep() != null && meetapply.getOaMeetapplyDep().length() > 0) {
			String depid[] = meetapply.getOaMeetapplyDep().substring(0, meetapply.getOaMeetapplyDep().length() - 1).split(",");
			String zhuban = "";
			for (String t : depid) {
				HrmDepartment dep = employeeinfoService.getDepartmentByPK(Integer.parseInt(t));
				if (dep != null) {
					zhuban = zhuban + dep.getHrmDepName() + ",";
				}
			}
			meetapply.setZhubanDep(zhuban);
		}

		// 加载出席内部人员名字
		if (meetapply.getOaMeetapplyEmpn() != null && meetapply.getOaMeetapplyEmpn().length() > 0) {
			String nempids[] = meetapply.getOaMeetapplyEmpn().substring(0, meetapply.getOaMeetapplyEmpn().length() - 1).split(",");
			String chuxi = "";
			for (String k : nempids) {
				HrmEmployee employee = employeeinfoService.getEmployeeByPK(k);
				if (employee != null) {
					chuxi = chuxi + employee.getHrmEmployeeName() + ",";
				}
			}
			meetapply.setChuxiEmpName(chuxi);
		}

		// 加载申请人信息
		if (meetapply.getOaMeetapplyEmp() != null && meetapply.getOaMeetapplyEmp().length() > 0) {
			HrmEmployee employee = employeeinfoService.getEmployeeByPK(meetapply.getOaMeetapplyEmp());
			if (employee != null) {
				meetapply.setEmployee(employee);
			}
		}

		// 加载会议类型信息
		if (meetapply.getOaMeetapplyType() != null) {
			SysLibraryInfo lib = officeResourcesService.getLibraryInfoBypk(meetapply.getOaMeetapplyType());
			if (lib != null) {
				meetapply.setLibrary(lib);
			}
		}

		// 加载会议室信息
		if (meetapply.getOaMeetapplyRoom() != null) {
			OaBoardroom room = officeResourcesService.getBoadroomByPk(meetapply.getOaMeetapplyRoom());
			if (room != null) {
				meetapply.setMeetApplyRoomObj(room);
			}
		}
		// 加载附件

		List<OaMeetapply> list = new ArrayList<OaMeetapply>();
		list.add(meetapply);
		logger.info("根据ID获取会议申请...");
		return WebUtilWork.WebResultPack(list);
	}

	/**
	 * 删除会议申请
	 * 
	 * @param context
	 * @param request
	 * @return
	 */

	public ResultBean deleteMeetapplysByPks(ServletContext context, HttpServletRequest request, long[] meetapplyPks) {
		for (long l : meetapplyPks) {
			OaMeetapply meetapply = officeResourcesService.getMeetapplyByPk(l);
			if (meetapply.getOaMeetapplyAffix() != null
					&& meetapply.getOaMeetapplyAffix().length() > 0) {
				UtilTool.deleteAttachmentsAndFile(context, request, meetapply
						.getOaMeetapplyAffix());
			}

		}

		officeResourcesService.deleteMeetapplysByPks(meetapplyPks);
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 显示所有纪要
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean listSummarys(ServletContext context, HttpServletRequest request, OaSummary oaSummary, Pager pager) {
		List<OaSummary> list = null;
		try {
			oaSummary.setCompanyId(UtilTool.getCompanyId(request));
			pager = PagerHelper.getPager(pager, officeResourcesService.listSummaryCount(oaSummary));
			list = officeResourcesService.getAllSummary(oaSummary, UtilTool.getCompanyId(request), pager);
			for (OaSummary o2 : list) {
				if (o2.getOaSummaryMeetId() != null) {
					OaMeetapply my = officeResourcesService.getMeetapplyByPk(o2.getOaSummaryMeetId());
					if (my != null) {
						o2.setOaMeetapply(my);
					}
				}
				if (o2.getSummaryRecorder() != null && o2.getSummaryRecorder().length() > 0) {
					HrmEmployee employee = employeeinfoService.getEmployeeByPK(o2.getSummaryRecorder());
					if (employee != null) {
						o2.setEmployee(employee);
					}
				}

			}
			logger.info("显示所有纪要...");
		} catch (Exception e) {
			logger.error("显示所有纪要出错..." + e.getMessage());
		}
		return WebUtilWork.WebResultPack(list, pager);
	}

	/**
	 * 新增纪要
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean saveSummary(ServletContext context, HttpServletRequest request, OaSummary summary) {
		// 存储附件
		if (summary.getOaSummaryContent() != null && summary.getOaSummaryContent().length() > 0) {
			String ids = UtilTool.saveAttachments(context, request, summary.getOaSummaryContent());
			summary.setOaSummaryContent(ids);
		}
		// 添加默认属性
		summary.setCompanyId(UtilTool.getCompanyId(request));
		summary.setRecordDate(UtilWork.getNowTime());
		summary.setRecordId(UtilTool.getEmployeeId(request));
		summary.setLastmodiId(UtilTool.getEmployeeId(request));
		summary.setLastmodiDate(UtilWork.getNowTime());

		summary.setOaSummaryDate(UtilWork.getNowTime());
		summary.setSummaryRecorder(UtilTool.getEmployeeId(request));

		officeResourcesService.saveSummary(summary);
		logger.info("新增纪要...");
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 编辑纪要
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean updateSummary(ServletContext context, HttpServletRequest request, OaSummary summary) {
		// 添加默认属性
		summary.setCompanyId(UtilTool.getCompanyId(request));
		summary.setLastmodiId(UtilTool.getEmployeeId(request));
		summary.setLastmodiDate(UtilWork.getNowTime());

		officeResourcesService.saveSummary(summary);
		logger.info("编辑纪要...");
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 根据ID获取纪要
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean getSummaryByPk(ServletContext context, HttpServletRequest request, long summaryPk) {
		OaSummary summary = officeResourcesService.getSummaryByPk(summaryPk);
		// 加载阅读人员
		if (summary.getOaSummaryReader() != null && summary.getOaSummaryReader().length() > 0) {
			String readers[] = summary.getOaSummaryReader().substring(0, summary.getOaSummaryReader().length() - 1).split(",");
			String summaryReader = "";
			for (String str : readers) {
				HrmEmployee employee = employeeinfoService.getEmployeeByPK(str);
				if (employee != null) {
					summaryReader = summaryReader + employee.getHrmEmployeeName() + ",";
				}
			}
			summary.setSummaryReaderName(summaryReader);
		}

		List<OaSummary> list = new ArrayList<OaSummary>();
		list.add(summary);
		logger.info("根据ID获取纪要...");
		return WebUtilWork.WebResultPack(list);
	}

	/**
	 * 删除纪要
	 * 
	 * @param context
	 * @param request
	 * @return
	 */

	public ResultBean deleteSummarysByPks(ServletContext context, HttpServletRequest request, long[] summaryPks) {
		// 删除纪要时先删附件
		for (long l : summaryPks) {
			OaSummary oaSummary = officeResourcesService.getSummaryByPk(l);
			if (oaSummary.getOaSummaryContent() != null && oaSummary.getOaSummaryContent().length() > 0) {
				UtilTool.deleteAttachmentsAndFile(context, request, oaSummary.getOaSummaryContent());
			}
		}
		officeResourcesService.deleteSummarysByPks(summaryPks);
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 显示所有车辆
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean listCars(ServletContext context, HttpServletRequest request, OaCar oaCar, Pager pager) {
		List<OaCar> list = null;
		try {
			oaCar.setCompanyId(UtilTool.getCompanyId(request));
			pager = PagerHelper.getPager(pager, officeResourcesService.listCarCount(oaCar));
			list = officeResourcesService.getAllCar(oaCar, pager);
			for (OaCar oacar : list) {
				oacar.setLibrary(UtilTool.getLibraryInfoByPk(context, request, oacar.getOaCarType()));
			}

			logger.info("显示所有车辆...");
		} catch (Exception e) {
			logger.error("显示所有车辆出错..." + e.getMessage());
		}
		return WebUtilWork.WebResultPack(list, pager);
	}

	/**
	 * 新增车辆
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean saveCar(ServletContext context, HttpServletRequest request, OaCar car) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(new Date());
		car.setOaCarSta(EnumUtil.OA_CAR_STA.GOOD.value);
		if (car.getOaCarPhoto() != null && car.getOaCarPhoto().length() > 0) {
			String imageId = UtilTool.saveImages(context, request, car.getOaCarPhoto());
			car.setOaCarPhoto(imageId);
		}

		// 添加默认属性
		car.setCompanyId(UtilTool.getCompanyId(request));
		car.setLastmodiId(UtilTool.getEmployeeId(request));
		car.setLastmodiDate(time);
		car.setRecordDate(time);
		car.setRecordId(UtilTool.getEmployeeId(request));

		officeResourcesService.saveCar(car);
		logger.info("新增车辆...");
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 编辑车辆
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean updateCar(ServletContext context, HttpServletRequest request, OaCar car) {
		String time = UtilWork.getNowTime();
		OaCar oaCar = officeResourcesService.getCarByPk(car.getPrimaryKey());
		car.setOaCarSta(EnumUtil.OA_CAR_STA.GOOD.value);
		if (oaCar.getOaCarPhoto() != null && oaCar.getOaCarPhoto().length() > 0) {
			UtilTool.deleteImagesNoFile(context, request, oaCar.getOaCarPhoto());
		}
		String imageId = UtilTool.saveImages(context, request, car.getOaCarPhoto());
		car.setOaCarPhoto(imageId);
		// 添加默认属性
		car.setRecordDate(oaCar.getRecordDate());
		car.setRecordId(oaCar.getRecordId());
		car.setCompanyId(oaCar.getCompanyId());
		car.setLastmodiId(UtilTool.getEmployeeId(request));
		car.setLastmodiDate(time);

		officeResourcesService.saveCar(car);
		logger.info("编辑车辆...");
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 根据ID获取车辆
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean getCarByPk(ServletContext context, HttpServletRequest request, long carPk) {

		OaCar car = officeResourcesService.getCarByPk(carPk);

		car.setLibrary(UtilTool.getLibraryInfoByPk(context, request, car.getOaCarType()));
	
		logger.info("根据ID获取车辆...");
		
		return WebUtilWork.WebObjectPack(car);
	}

	/**
	 * 删除车辆
	 * 
	 * @param context
	 * @param request
	 * @return
	 */

	public ResultBean deleteCarsByPks(ServletContext context, HttpServletRequest request, long[] carPks) {
		for (long l : carPks) {
			int row = officeResourcesService.getappy(l);
			OaCar car = officeResourcesService.getCarByPk(l);
			if (row > 0) {
				return new ResultBean(false, car.getOaCarName() + "包含 " + row
						+ "条申请信息，不能删除");
			}

			int mrow = officeResourcesService.getmaintain(l);
			if (mrow > 0) {
				return new ResultBean(false, car.getOaCarName() + "包含 " + mrow + "条维修信息，不能删除");
			}
		}
		
		for (long m : carPks) {
			OaCar car = officeResourcesService.getCarByPk(m);
			if (car.getOaCarPhoto() != null && car.getOaCarPhoto().length() > 0) {
				UtilTool.deleteImagesAndFile(context, request, car.getOaCarPhoto());
			}
			
		}
		officeResourcesService.deleteCarsByPks(carPks);
		return WebUtilWork.WebResultPack(null);
	}
	/**
	 * 删除车辆
	 * 
	 * @param context
	 * @param request
	 * @return
	 */

	public ResultBean deleteSuperCarsByPks(ServletContext context, HttpServletRequest request, long[] carPks) {
		for (long l : carPks) {
			 officeResourcesService.deletecarapply(l);
			 officeResourcesService.deletemaintain(l);
			OaCar car = officeResourcesService.getCarByPk(l);
			if (car.getOaCarPhoto() != null && car.getOaCarPhoto().length() > 0) {
				UtilTool.deleteImagesAndFile(context, request, car.getOaCarPhoto());
			}
		}
		officeResourcesService.deleteCarsByPks(carPks);
		
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 显示所有车辆申请
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean listCaruses(ServletContext context, HttpServletRequest request, OaCarApply oaCarApply, Pager pager) {
		List<OaCarApply> list = null;
		try {
			oaCarApply.setCompanyId(UtilTool.getCompanyId(request));
			pager = PagerHelper.getPager(pager, officeResourcesService.listCaruseCount(oaCarApply));
			list = officeResourcesService.getAllCaruse(oaCarApply, pager);
			for (OaCarApply oacar : list) {
				oacar.setApplyEmployee(employeeinfoService.getEmployeeByPK(oacar.getApplyUser()));
				oacar.setOaCar(officeResourcesService.getCarByPk(oacar.getCarId().longValue()));
			}
			logger.info("显示所有车辆申请...");
		} catch (Exception e) {
			logger.error("显示所有车辆申请出错..." + e.getMessage());
		}
		return WebUtilWork.WebResultPack(list, pager);
	}
	public ResultBean listCarapp(ServletContext context, HttpServletRequest request, OaCarApply oaCarApply, Pager pager) {
		List<OaCarApply> list = null;
		try {
		
			oaCarApply.setCompanyId(UtilTool.getCompanyId(request));
			pager = PagerHelper.getPager(pager, officeResourcesService.listCaruseCount(oaCarApply));
			list = officeResourcesService.getAllCaruse(oaCarApply, pager);
			for (OaCarApply oacar : list) {
				oacar.setApplyEmployee(employeeinfoService.getEmployeeByPK(oacar.getApplyUser()));
				oacar.setOaCar(officeResourcesService.getCarByPk(oacar.getCarId().longValue()));
			}
			logger.info("显示所有车辆申请...");
		} catch (Exception e) {
			logger.error("显示所有车辆申请出错..." + e.getMessage());
		}
		return WebUtilWork.WebResultPack(list, pager);
	}

	/**
	 * 新增车辆申请
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean saveCaruse(ServletContext context, HttpServletRequest request, OaCarApply caruse) {
		String empid = UtilTool.getEmployeeId(request);
		String nowtime = UtilWork.getNowTime();
		// 添加默认属性
		if (caruse.getPrimaryKey() > 0) {
			OaCarApply tmp = officeResourcesService.getCaruseByPk(caruse.getPrimaryKey());
			caruse.setRecordId(tmp.getRecordId());
			caruse.setRecordDate(tmp.getRecordDate());
		} else {
			caruse.setRecordId(empid);
			caruse.setRecordDate(nowtime);
		}
		caruse.setCompanyId(UtilTool.getCompanyId(request));
		caruse.setLastmodiId(empid);
		caruse.setLastmodiDate(nowtime);
		caruse.setOaCarStatus(EnumUtil.OA_MEETAPPLY_STATUS.APPLYING.value);
		officeResourcesService.saveCaruse(caruse);
		return WebUtilWork.WebResultPack(null);
	}

	public ResultBean listCarByDate(ServletContext context, HttpServletRequest request,OaCar car, String starttime, String endtime, Pager pager) {
		car.setCompanyId(UtilTool.getCompanyId(request));
		pager = PagerHelper.getPager(pager, officeResourcesService.getAllCarByDateCount(car));
		List<OaCar> list = officeResourcesService.getAllCarByDate(starttime, endtime,car, car.getCompanyId(), pager);

		CaseInsensitiveComparator comp =new CaseInsensitiveComparator();
		Collections.sort(list,comp);
		return WebUtilWork.WebResultPack(list, pager);
	}
	@SuppressWarnings("unchecked")
	class CaseInsensitiveComparator implements Comparator {

		public int compare(Object arg0, Object arg1) {
			OaCar OaCar1 = (OaCar) arg0;
			OaCar OaCar2 = (OaCar) arg1;
			if (OaCar1.getOaCarStatus()!=null &&OaCar2.getOaCarStatus()!=null&&OaCar1.getOaCarStatus()!=OaCar2.getOaCarStatus()) {
				int m1 = OaCar1.getOaCarStatus();
				int m2 = OaCar2.getOaCarStatus();
				if (m1 < m2) {
					return -1;
				} else {
					return 1;
				}
			} else {
				return 0;
			}
		}
	}

	public ResultBean listuseCarByDate(ServletContext context, HttpServletRequest request,OaCar car, String starttime, String endtime, Pager pager,int type) {

		pager = PagerHelper.getPager(pager, officeResourcesService.getUseCarCount(starttime, endtime,car, UtilTool.getCompanyId(request), pager,type));
		List<OaCar> list = officeResourcesService.getUseCar(starttime, endtime,car, UtilTool.getCompanyId(request), pager,type);
	
		return WebUtilWork.WebResultPack(list, pager);
	}

	/**
	 * 根据时间列出会议室状态
	 * 
	 * @param context
	 * @param request
	 * @param starttime	会议开始时间
	 * @param endtime	会议结束时间
	 * @param pager
	 * @return
	 */
	public ResultBean listBoadRoomsStatusByDate(ServletContext context, HttpServletRequest request, String starttime, String endtime, Pager pager) {
		pager = PagerHelper.getPager(pager, officeResourcesService.getAllBoadRoomCount());
		List<OaBoardroom> list = officeResourcesService.getAllBoadRoomByDate(starttime, endtime, UtilTool.getCompanyId(request), pager);
		return WebUtilWork.WebResultPack(list, pager);
	}

	/**
	 * 根据ID获取车辆申请
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean getCaruseByPk(ServletContext context, HttpServletRequest request, long carusePk) {

		OaCarApply caruse = officeResourcesService.getCaruseByPk(carusePk);
		caruse.setApplyEmployee(employeeinfoService.getEmployeeByPK(caruse.getApplyUser()));
		caruse.setOaCar(officeResourcesService.getCarByPk(caruse.getCarId().longValue()));
		return WebUtilWork.WebObjectPack(caruse);
	}

	/**
	 * 删除车辆申请
	 * 
	 * @param context
	 * @param request
	 * @return
	 */

	public ResultBean deleteCarusesByPks(ServletContext context, HttpServletRequest request, long[] carusePks) {

		if (officeResourcesService.deleteCarusesByPks(carusePks)) {
			return new ResultBean(true, "已删除");
		} else {
			return new ResultBean(false, "您选择中包含 正在进行中或已经结束的车辆不能删除");
		}

	}
	public ResultBean deleteSuperCarusesByPks(ServletContext context, HttpServletRequest request, long[] carusePks) {

		officeResourcesService.SuperdeleteCarusesByPks(carusePks);
		return WebUtilWork.WebResultPack(null);
		 

	} 

	/**
	 * 显示所有维护车辆
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean listCarmaintens(ServletContext context, HttpServletRequest request, OaCarMaintain oaCarMaintain, Pager pager) {
		List<OaCarMaintain> list = null;
		try {

			oaCarMaintain.setCompanyId(UtilTool.getCompanyId(request));
			pager = PagerHelper.getPager(pager, officeResourcesService.listCarmaintenCount(oaCarMaintain));
			list = officeResourcesService.getAllCarmainten(oaCarMaintain, pager);
			for (OaCarMaintain oacarmaintain : list) {
				oacarmaintain.setApplyEmployee(employeeinfoService.getEmployeeByPK(oacarmaintain.getMaintainUser()));
				if (oacarmaintain.getMaintainType() != 999) {
					SysLibraryInfo info = UtilTool.getLibraryInfoByPk(context, request, oacarmaintain.getMaintainType());
					oacarmaintain.setLibraryName(info == null ? "" : info.getLibraryInfoName());
				} else {
					oacarmaintain.setLibraryName("车辆报废");
				}

			}

			logger.info("显示所有维护车辆...");
		} catch (Exception e) {
			logger.error("显示所有维护车辆出错..." + e.getMessage());
		}
		return WebUtilWork.WebResultPack(list, pager);
	}

	/**
	 * 新增维护车辆
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean saveCarmainten(ServletContext context, HttpServletRequest request, OaCarMaintain carmainten) {
		// 添加默认属性
		if (carmainten.getPrimaryKey() > 0) {
			OaCarMaintain tmp = officeResourcesService.getCarmaintenByPk(carmainten.getPrimaryKey());
			carmainten.setRecordId(tmp.getRecordId());
			carmainten.setRecordDate(tmp.getRecordDate());
		
		} else {

			carmainten.setRecordId(UtilTool.getEmployeeId(request));
			carmainten.setRecordDate(UtilWork.getNowTime());
		}
		carmainten.setCompanyId(UtilTool.getCompanyId(request));
		carmainten.setLastmodiId(UtilTool.getEmployeeId(request));
		carmainten.setLastmodiDate(UtilWork.getNowTime());
		
		if (carmainten.getMaintainType() == 999) {
			OaCar car = officeResourcesService.getCarByPk(carmainten.getCarId());
			if(car.getOaCarSta()==EnumUtil.OA_CAR_STA.FAIL.value){
				return new ResultBean(false, "已经有人同时将车辆报废，您的此次不能操作失败,");
			}
			car.setRecordId(car.getRecordId());
			car.setRecordDate(car.getRecordDate());
			car.setCompanyId(car.getCompanyId());
			car.setLastmodiId(UtilTool.getEmployeeId(request));
			car.setLastmodiDate(UtilWork.getNowTime());
			car.setOaCarSta(EnumUtil.OA_CAR_STA.FAIL.value);
			officeResourcesService.saveCar(car);
		}

		logger.info("新增维护车辆...");
		return WebUtilWork.WebResultPack(officeResourcesService.saveCarmainten(carmainten));
	}

	/**
	 * 编辑维护车辆
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean updateCarmainten(ServletContext context, HttpServletRequest request, OaCarMaintain carmainten) {
		// 添加默认属性
		carmainten.setCompanyId(UtilTool.getCompanyId(request));
		carmainten.setLastmodiId(UtilTool.getEmployeeId(request));
		carmainten.setLastmodiDate(UtilWork.getNowTime());
		officeResourcesService.saveCarmainten(carmainten);
		logger.info("编辑维护车辆...");
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 根据ID获取维护车辆
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean getCarmaintenByPk(ServletContext context, HttpServletRequest request, long carmaintenPk) {
		OaCarMaintain carmainten = officeResourcesService.getCarmaintenByPk(carmaintenPk);
		carmainten.setApplyEmployee(employeeinfoService.getEmployeeByPK(carmainten.getMaintainUser()));
		if (carmainten.getMaintainType() != 999) {
			SysLibraryInfo info = UtilTool.getLibraryInfoByPk(context, request, carmainten.getMaintainType());
			carmainten.setLibraryName(info == null ? "" : info.getLibraryInfoName());
		} else {
			carmainten.setLibraryName("车辆报废");
		}
		carmainten.setApplyEmployee(employeeinfoService.getEmployeeByPK(carmainten.getMaintainUser()));
		List<OaCarMaintain> list = new ArrayList<OaCarMaintain>();
		list.add(carmainten);
		logger.info("根据ID获取维护车辆...");
		return WebUtilWork.WebResultPack(list);
	}

	/**
	 * 删除维护车辆
	 * 
	 * @param context
	 * @param request
	 * @return
	 */

	public ResultBean deleteCarmaintensByPks(ServletContext context, HttpServletRequest request, long[] carmaintenPks) {
		officeResourcesService.deleteCarmaintensByPks(carmaintenPks);
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 显示所有图书
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean listBooks(ServletContext context, HttpServletRequest request, OaBook oaBook, Pager pager) {
		List<OaBook> list = new ArrayList<OaBook>();

		oaBook.setCompanyId(UtilTool.getCompanyId(request));

		pager = PagerHelper.getPager(pager, officeResourcesService.listBookCount(oaBook));
		list = officeResourcesService.getAllBook(oaBook, pager);

		for (OaBook bk : list) {
			if (bk.getOaBookDep() != null) {
				HrmDepartment dep = employeeinfoService.getDepartmentByPK(bk.getOaBookDep());
				if (dep != null) {
					bk.setDepartment(dep);
				}
			}
			if (bk.getOaBookBooker() != null && bk.getOaBookBooker().length() > 0) {
				HrmEmployee emp = employeeinfoService.getEmployeeByPK(bk.getOaBookBooker());
				if (emp != null) {
					bk.setEmployee(emp);
				}
			}
			if (bk.getOaBookType() != null) {
				OaBookType type = officeResourcesService.getBooktypeByPk(bk.getOaBookType());
				if (type != null) {
					bk.setBookType(type);
				}
			}
		}

		logger.info("显示所有图书...");
		return WebUtilWork.WebResultPack(list, pager);
	}

	/**
	 * 不分页获取图书类别信息
	 * 
	 * @param context
	 * @param request
	 * @param com	公司主键
	 * @return
	 */
	public ResultBean getAllBookType(ServletContext context, HttpServletRequest request, long com) {
		List<OaBookType> list = new ArrayList<OaBookType>();

		list = officeResourcesService.getAllBooktypeNopager(UtilTool.getCompanyId(request));

		logger.info("获取图书类别...");
		return WebUtilWork.WebResultPack(list);
	}

	/**
	 * 新增图书
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean saveBook(ServletContext context, HttpServletRequest request, OaBook book) {
		SimpleDateFormat sdf = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss ");
		String time = sdf.format(new Date());

		// 存储附件
		if (book.getOaBookAcce() != null && book.getOaBookAcce().length() > 0) {
			String ids = UtilTool.saveAttachments(context, request, book.getOaBookAcce());
			book.setOaBookAcce(ids);
		}

		book.setOaBookRemain(book.getOaBookCount());
		book.setOaBookBooker(UtilTool.getEmployeeId(request));
		book.setOaRegistyDate(time);
		// 添加默认属性
		book.setCompanyId(UtilTool.getCompanyId(request));
		book.setLastmodiId(UtilTool.getEmployeeId(request));
		book.setLastmodiDate(time);
		book.setRecordDate(time);
		book.setRecordId(UtilTool.getEmployeeId(request));

		officeResourcesService.saveBook(book);
		logger.info("新增图书...");
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 编辑图书
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean updateBook(ServletContext context, HttpServletRequest request, OaBook book) {
		SimpleDateFormat sdf = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss ");
		String time = sdf.format(new Date());

		OaBook bk = officeResourcesService.getBookByPk(book.getPrimaryKey());

		// 删除原有附件记录
		if (bk.getOaBookAcce() != null && bk.getOaBookAcce().length() > 0) {
			UtilTool.deleteAttachmentsNoFile(context, request, bk.getOaBookAcce());
		}

		// 保存新附件
		if (book.getOaBookAcce() != null && book.getOaBookAcce().length() > 0) {
			String ids = UtilTool.saveAttachments(context, request, book.getOaBookAcce());
			book.setOaBookAcce(ids);
		}

		book.setOaBookBooker(bk.getOaBookBooker());
		book.setOaBookRemain(book.getOaBookCount());
		book.setRecordDate(bk.getRecordDate());
		book.setRecordId(bk.getRecordId());
		book.setOaRegistyDate(bk.getOaRegistyDate());

		// 添加默认属性
		book.setCompanyId(bk.getCompanyId());
		book.setLastmodiId(UtilTool.getEmployeeId(request));
		book.setLastmodiDate(time);

		officeResourcesService.saveBook(book);
		logger.info("编辑图书...");
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 根据ID获取图书信息
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean getBookByPk(ServletContext context, HttpServletRequest request, long bookPk) {
		OaBook book = officeResourcesService.getBookByPk(bookPk);

		// 加载所属部门信息
		if (book.getOaBookDep() != null) {
			HrmDepartment dep = employeeinfoService.getDepartmentByPK(book.getOaBookDep());
			if (dep != null) {
				book.setDepartment(dep);
			}
		}

		// 加载图书类型信息
		if (book.getOaBookType() != null) {
			OaBookType type = officeResourcesService.getBooktypeByPk(book.getOaBookType());
			if (type != null) {
				book.setBookType(type);
			}
		}

		// 加载图书登记人信息
		if (book.getOaBookBooker() != null && book.getOaBookBooker().length() > 0) {
			HrmEmployee emp = employeeinfoService.getEmployeeByPK(book.getOaBookBooker());
			if (emp != null) {
				book.setEmployee(emp);
			}
		}

		List<OaBook> list = new ArrayList<OaBook>();
		list.add(book);
		logger.info("根据ID获取图书信息...");
		return WebUtilWork.WebResultPack(list);
	}

	/**
	 * 删除图书信息
	 * 
	 * @param context
	 * @param request
	 * @return
	 */

	public ResultBean deleteBooksByPks(ServletContext context, HttpServletRequest request, long[] bookPks) {

		// 删除附件
		for (long l : bookPks) {
			OaBook bk = officeResourcesService.getBookByPk(l);
			if (bk.getOaBookAcce() != null && bk.getOaBookAcce().length() > 0) {
				UtilTool.deleteAttachmentsAndFile(context, request, bk.getOaBookAcce());
			}
		}

		officeResourcesService.deleteBooksByPks(bookPks, UtilTool.getCompanyId(request));

		logger.info("删除图书信息...");
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 显示所有出借归还信息
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean listBookbrs(ServletContext context, HttpServletRequest request, OaBookBr oaBookBr, Pager pager) {
		List<OaBookBr> list = null;

		oaBookBr.setCompanyId(UtilTool.getCompanyId(request));
		pager = PagerHelper.getPager(pager, officeResourcesService.listBookbrCount(oaBookBr));
		list = officeResourcesService.getAllBookbr(oaBookBr, pager);

		for (OaBookBr br : list) {
			// 加载图书信息
			if (br.getOaBrBookid() != null) {
				OaBook bk = officeResourcesService.getBookByPk(br.getOaBrBookid());
				if (bk != null) {
					br.setBookInfo(bk);
				}
			}
			// 加载借书人（内部）人员信息
			if (br.getOaBrLendn() != null && br.getOaBrLendn().length() > 0) {
				HrmEmployee emp = employeeinfoService.getEmployeeByPK(br.getOaBrLendn());
				if (emp != null) {
					br.setLendnEmp(emp);
				}
			}

		}
		logger.info("显示所有出借归还信息	...");
		return WebUtilWork.WebResultPack(list, pager);
	}

	/**
	 * 新增出借归还信息
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean saveBookbr(ServletContext context, HttpServletRequest request, OaBookBr bookbr) {
		SimpleDateFormat sdf = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss ");
		String time = sdf.format(new Date());
		OaBook book = officeResourcesService.getBookByPk(bookbr.getOaBrBookid());

		if (bookbr.getOaBrCount() <= 0) {
			return new ResultBean(false, "请输入有意义的借书数量！");
		}

		if (bookbr.getOaBrCount() > book.getOaBookRemain()) {
			return new ResultBean(false, "借书数量不能大于图书剩余数量！");
		}

		bookbr.setOaBrStatus(EnumUtil.OA_BOOKBR_STATUS.LEND.value);
		bookbr.setOaBrBooker(UtilTool.getEmployeeId(request));

		// 添加默认属性
		bookbr.setCompanyId(UtilTool.getCompanyId(request));
		bookbr.setLastmodiId(UtilTool.getEmployeeId(request));
		bookbr.setLastmodiDate(time);
		bookbr.setRecordDate(time);
		bookbr.setRecordId(UtilTool.getEmployeeId(request));

		officeResourcesService.saveBookbr(bookbr);
		logger.info("新增出借归还信息...");
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 根据ID获取出借归还信息
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean getBookbrByPk(ServletContext context, HttpServletRequest request, long bookbrPk) {
		OaBookBr bookbr = officeResourcesService.getBookbrByPk(bookbrPk);

		// 加载图书信息
		if (bookbr.getOaBrBookid() != null) {
			OaBook bk = officeResourcesService.getBookByPk(bookbr.getOaBrBookid());
			if (bk != null) {
				bookbr.setBookInfo(bk);
			}
		}

		// 加载登记人信息
		if (bookbr.getOaBrBooker() != null && bookbr.getOaBrBooker().length() > 0) {
			HrmEmployee emp = employeeinfoService.getEmployeeByPK(bookbr.getOaBrBooker());
			if (emp != null) {
				bookbr.setBookerEmp(emp);
			}
		}

		// 加载借书人（内部）人员信息
		if (bookbr.getOaBrLendn() != null && bookbr.getOaBrLendn().length() > 0) {
			HrmEmployee employee = employeeinfoService.getEmployeeByPK(bookbr.getOaBrLendn());
			if (employee != null) {
				bookbr.setLendnEmp(employee);
			}
		}

		List<OaBookBr> list = new ArrayList<OaBookBr>();
		list.add(bookbr);
		logger.info("根据ID获取出借归还信息...");
		return WebUtilWork.WebResultPack(list);
	}

	/**
	 * 归还图书
	 * 
	 * @param context
	 * @param request
	 * @return
	 */

	public ResultBean giveBackBookByPks(ServletContext context, HttpServletRequest request, long[] bookbrPks) {
		officeResourcesService.setBookbrsByPks(bookbrPks);
		logger.info("归还图书...");
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 获取所有图书类型信息
	 * 
	 * @param context
	 * @param request
	 * @param oaBookType
	 *            封装的查询条件
	 * @param pager
	 *            分页对象
	 * @return
	 */
	public ResultBean listBooktypes(ServletContext context, HttpServletRequest request, OaBookType oaBookType, Pager pager) {
		List<OaBookType> list = null;
		try {
			oaBookType.setCompanyId(UtilTool.getCompanyId(request));
			pager = PagerHelper.getPager(pager, officeResourcesService.listBooktypeCount(oaBookType, UtilTool.getCompanyId(request)));
			list = officeResourcesService.getAllBooktype(oaBookType, UtilTool.getCompanyId(request), pager);

			for (OaBookType obt : list) {
				OaBook book = new OaBook();
				book.setCompanyId(UtilTool.getCompanyId(request));
				book.setOaBookType((int) obt.getPrimaryKey());
				int count = officeResourcesService.listBookCount(book);
				obt.setBookCount(count + "");
			}

			logger.info("显示所有图书类别...");
		} catch (Exception e) {
			logger.error("显示所有图书类别出错..." + e.getMessage());
		}
		return WebUtilWork.WebResultPack(list, pager);
	}

	/**
	 * 新增图书类别
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean saveBooktype(ServletContext context, HttpServletRequest request, OaBookType booktype) {
		SimpleDateFormat sdf = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss ");
		String time = sdf.format(new Date());

		// 添加默认属性
		booktype.setCompanyId(UtilTool.getCompanyId(request));
		booktype.setLastmodiId(UtilTool.getEmployeeId(request));
		booktype.setLastmodiDate(time);
		booktype.setRecordDate(time);
		booktype.setRecordId(UtilTool.getEmployeeId(request));

		officeResourcesService.saveBooktype(booktype);
		logger.info("新增图书类别...");
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 编辑图书类别
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean updateBooktype(ServletContext context, HttpServletRequest request, OaBookType booktype) {
		SimpleDateFormat sdf = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss ");
		String time = sdf.format(new Date());

		OaBookType bType = officeResourcesService.getBooktypeByPk(booktype.getPrimaryKey());

		// 添加默认属性
		booktype.setCompanyId(UtilTool.getCompanyId(request));
		booktype.setLastmodiId(UtilTool.getEmployeeId(request));
		booktype.setLastmodiDate(time);
		booktype.setRecordDate(bType.getRecordDate());
		booktype.setRecordId(bType.getRecordId());

		officeResourcesService.saveBooktype(booktype);
		logger.info("编辑图书类别...");
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 根据ID获取图书类别信息
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean getBooktypeByPk(ServletContext context, HttpServletRequest request, long booktypePk) {

		OaBookType booktype = officeResourcesService.getBooktypeByPk(booktypePk);

		List<OaBookType> list = new ArrayList<OaBookType>();
		list.add(booktype);
		logger.info("根据ID获取图书类别信息...");
		return WebUtilWork.WebResultPack(list);
	}

	/**
	 * 删除图书类别
	 * 
	 * @param context 
	 * @param request
	 * @return
	 */
	public ResultBean deleteBooktypesByPks(ServletContext context, HttpServletRequest request, long[] booktypePks) {

		for (long l : booktypePks) {
			OaBookType type = officeResourcesService.getBooktypeByPk(l);

			OaBook book = new OaBook();
			book.setCompanyId(UtilTool.getCompanyId(request));
			book.setOaBookType((int) l);
			int bookCount = officeResourcesService.listBookCount(book);
			if (bookCount > 0) {
				return new ResultBean(false, type.getOaBooktypeName() + " 图书类型中有图书信息 " + bookCount + " 个,不能删除！");
			}
		}

		officeResourcesService.deleteBooktypesByPks(booktypePks);
		logger.info("删除图书类别信息...");
		return WebUtilWork.WebResultPack(null);
	}

}
