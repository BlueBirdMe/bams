package com.pinhuba.web.controller.dwr;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import com.pinhuba.common.module.OnlineHrmEmployeeBean;
import com.pinhuba.common.module.ResultBean;
import com.pinhuba.common.pages.Pager;
import com.pinhuba.common.util.EnumUtil;
import com.pinhuba.common.util.UtilTool;
import com.pinhuba.common.util.UtilWork;
import com.pinhuba.common.util.WebUtilWork;
import com.pinhuba.common.util.EnumUtil.OA_MAIL_STATUS;
import com.pinhuba.core.iservice.IHrmEmployeeService;
import com.pinhuba.core.iservice.IMailService;
import com.pinhuba.core.iservice.IMoblieSmsService;
import com.pinhuba.core.iservice.IOaChartterService;
import com.pinhuba.core.iservice.IOaCompanyResourcesService;
import com.pinhuba.core.iservice.IOaDesktopSerivce;
import com.pinhuba.core.iservice.IOaNewsService;
import com.pinhuba.core.iservice.IPersonalOfficeSerivce;
import com.pinhuba.core.iservice.IWorkArrangeService;
import com.pinhuba.core.pojo.HrmEmployee;
import com.pinhuba.core.pojo.HrmTimedrecord;
import com.pinhuba.core.pojo.OaAdversaria;
import com.pinhuba.core.pojo.OaAnnouncement;
import com.pinhuba.core.pojo.OaCalender;
import com.pinhuba.core.pojo.OaDesktopSet;
import com.pinhuba.core.pojo.OaLeaveregistration;
import com.pinhuba.core.pojo.OaMailEmp;
import com.pinhuba.core.pojo.OaMailInbox;
import com.pinhuba.core.pojo.OaNetmailInbox;
import com.pinhuba.core.pojo.OaNotebook;
import com.pinhuba.core.pojo.OaNotice;
import com.pinhuba.core.pojo.OaRegulations;
import com.pinhuba.core.pojo.OaSmsInbox;
import com.pinhuba.core.pojo.OaTrsvel;
import com.pinhuba.core.pojo.OaVote;
import com.pinhuba.core.pojo.OaVoteStatus;
import com.pinhuba.web.listener.OnlineUserBindingListener;

/**
 * 个人桌面dwr
 * @author peng.ning
 * @date   Mar 31, 2010
 */
public class DwrOADesktopService {
	private Logger logger = Logger.getLogger(this.getClass());
	@Resource
	private IOaDesktopSerivce oaDesktopService;
	@Resource
	private IOaNewsService oaNewsService;
	@Resource
	private IPersonalOfficeSerivce personalOfficeService;
	@Resource
	private IWorkArrangeService workArrangeService;
	@Resource
	private IOaChartterService oaChartterService;
	@Resource
	private IOaCompanyResourcesService cmpReService;
	@Resource
	private IMoblieSmsService moblieSmsService;
	@Resource
	private IMailService mailService;
	@Resource
	private IHrmEmployeeService employeeinfoService;
	
	public ResultBean createOaDesktop(ServletContext context,HttpServletRequest request){
		int companyId = UtilTool.getCompanyId(request);
		String empId = UtilTool.getEmployeeId(request);
		String nowtime = UtilWork.getNowTime();
		List<OaDesktopSet> list = oaDesktopService.getOaDeskTopList(request,companyId, empId);
		Map<Integer, String> map = EnumUtil.OA_DESKTOP_TYPE.getValuesMap();
		Set<Integer> keys = map.keySet();
		Iterator<Integer> it = keys.iterator();
		ArrayList<OaDesktopSet> desktopList = new ArrayList<OaDesktopSet>();
		while (it.hasNext()) {
			Integer elem = (Integer) it.next();
			boolean bl = true;
			if (list.size()>0) {
				for (OaDesktopSet desk : list) {
					if(desk.getOaDesktopType().intValue() == elem.intValue()){
						bl = false;
						break;
					}
				}
			}
			if (bl) {
				OaDesktopSet dk = new OaDesktopSet();
				dk.setCompanyId(companyId);
				dk.setOaDesktopEmpid(empId);
				dk.setOaDesktopType(elem);
				dk.setOaDesktopIsshow(EnumUtil.SYS_ISACTION.Vaild.value);
				dk.setRecordId(empId);
				dk.setLastmodiId(empId);
				dk.setLastmodiDate(nowtime);
				dk.setRecordDate(nowtime);
				desktopList.add(dk);
			}
		}
		oaDesktopService.saveOaDeskTop(desktopList);
		return WebUtilWork.WebResultPack(null);
	}
	
	public ResultBean saveOaDeskTop(ServletContext context,HttpServletRequest request,OaDesktopSet[] setlist){
		ArrayList<OaDesktopSet> desktopList = new ArrayList<OaDesktopSet>();
		for (OaDesktopSet oaDesktopSet : setlist) {
			OaDesktopSet tmp = oaDesktopService.getOaDesktopSetByPk(oaDesktopSet.getPrimaryKey());
			tmp.setLastmodiId(UtilTool.getEmployeeId(request));
			tmp.setLastmodiDate(UtilWork.getNowTime());
			tmp.setOaDesktopIsshow(oaDesktopSet.getOaDesktopIsshow());
			if(oaDesktopSet.getOaDesktopIsshow() == EnumUtil.SYS_ISACTION.Vaild.value && oaDesktopSet.getOaDesktopValue()!=null&&oaDesktopSet.getOaDesktopValue().length()>0){
				tmp.setOaDesktopValue(oaDesktopSet.getOaDesktopValue());
			}
			desktopList.add(tmp);
		}
		oaDesktopService.saveOaDeskTop(desktopList);
		return WebUtilWork.WebResultPack(null);
	}
	
	
	@SuppressWarnings("unchecked")
	public ResultBean getOaDeskTopList(ServletContext context,HttpServletRequest request){
		int companyId = UtilTool.getCompanyId(request);
		String empId = UtilTool.getEmployeeId(request);
		List<OaDesktopSet> list=null;
		list =oaDesktopService.getOaDeskTopList(request, companyId, empId);
		return WebUtilWork.WebResultPack(list);
	}
	
	private Pager getOtherPager(int row){
		Pager pager = new Pager();
		pager.setStartRow(0);
		pager.setPageSize(row);
		return pager;
	}
	
	/**
	 * 加载公司通知
	 * @param context
	 * @param request
	 * @param row
	 * @return
	 */
	public List<OaNotice> getOaNoticeListByEmpId(ServletContext context,HttpServletRequest request,int row){
		String empid = UtilTool.getEmployeeId(request)+",";
		String deptid = UtilTool.getDeptId(request)+",";
		int companyId = UtilTool.getCompanyId(request);
		OaNotice notice = new OaNotice();
		notice.setOaObjDep(deptid);
 		notice.setOaObjEmp(empid);
 		
 		notice.setOaNotiStatus(EnumUtil.OA_ISSUEINFO_STATUS.EFFECT.value);
		List<OaNotice> list = oaNewsService.getAllNotice(notice, companyId, getOtherPager(row));
		for(OaNotice nt : list){
			nt.setEmployee(employeeinfoService.getEmployeeByPK(nt.getOaNotiEmp()));
		}
		return list;
	}
	/**
	 * 加载个人标签
	 * @param context
	 * @param request
	 * @param row
	 * @return
	 */
	public List<OaNotebook> getOaNoteBookListByEmpId(ServletContext context,HttpServletRequest request,int row){
		String empid = UtilTool.getEmployeeId(request);
		int companyId = UtilTool.getCompanyId(request);
		OaNotebook nb = new OaNotebook();
		nb.setOaNotebookEmp(empid);
		nb.setCompanyId(companyId);
		nb.setOaNotebookCreattime(UtilWork.getToday()+","+UtilWork.getToday());
		List<OaNotebook> list = personalOfficeService.getAllNotebook(nb, getOtherPager(row));
		return list;
	}
	

	/**
	 * 公告
	 * @param context
	 * @param request
	 * @param row
	 * @return
	 */
	public List<OaAnnouncement> getOaAnnouncementBydesk(ServletContext context,HttpServletRequest request,int row){
		OaAnnouncement ann = new OaAnnouncement();
		ann.setOaAnnoStatus(EnumUtil.OA_ISSUEINFO_STATUS.EFFECT.value);

		List<OaAnnouncement> list =oaNewsService.getAllAnnouncement(ann, UtilTool.getCompanyId(request), getOtherPager(row));
		for(OaAnnouncement tmp : list){
			tmp.setEmployee(employeeinfoService.getEmployeeByPK(tmp.getOaAnnoEmp()));
		}
		return list;
	}
	/**
	 * 记事
	 * @param context
	 * @param request
	 * @param row
	 * @return
	 */
	public List<OaAdversaria> getOaAdversariaBydesk(ServletContext context,HttpServletRequest request,int row){
		OaAdversaria adv = new OaAdversaria();
		adv.setOaAdverStatus(EnumUtil.OA_ISSUEINFO_STATUS.EFFECT.value);
		List<OaAdversaria> list =oaNewsService.getAllAdversaria(adv, UtilTool.getCompanyId(request), getOtherPager(row));
		for(OaAdversaria tmp : list){
			tmp.setEmployee(employeeinfoService.getEmployeeByPK(tmp.getOaAdverEmp()));
		}
		return list;
	}
	
	/**
	 * 请假
	 * @param context
	 * @param request
	 * @param row
	 * @return
	 */
	public List<OaLeaveregistration>  getOaLeaveregistrationByEmpId(ServletContext context,HttpServletRequest request,int row){
		OaLeaveregistration oaLeaveregistration = new OaLeaveregistration();
		oaLeaveregistration.setCompanyId(UtilTool.getCompanyId(request));
		oaLeaveregistration.setApplyuser(UtilTool.getEmployeeId(request));
		List<OaLeaveregistration> Leavelist = personalOfficeService.getOaLeaveregistration(oaLeaveregistration, getOtherPager(row));
		return Leavelist;
	}
	/**
	 * 出差
	 * @param context
	 * @param request
	 * @param row
	 * @return
	 */
	public List<OaTrsvel> getOaTrsvelByEmpId(ServletContext context,HttpServletRequest request,int row){
		OaTrsvel oaTrsvel = new OaTrsvel();
		oaTrsvel.setCompanyId(UtilTool.getCompanyId(request));
		oaTrsvel.setTrsvelApplyuser(UtilTool.getEmployeeId(request));
		List<OaTrsvel> oaTrsvellist = personalOfficeService.getOaTrsvel(oaTrsvel,  getOtherPager(row));
		return oaTrsvellist;
	}

	/**
	 * 投票
	 * @param context
	 * @param request
	 * @param row
	 * @return
	 * @throws Exception
	 */
	public List<OaVote> getOaVoteByEmpId(ServletContext context,HttpServletRequest request,int row) throws Exception{
		String useId = UtilTool.getEmployeeId(request) +",";
	 	Long  dep = UtilTool.getDeptId(request);
	 	String depId = dep.toString()+",";
	 	OaVote vote = new OaVote();
	 	vote.setOaVoteStatus(EnumUtil.OA_VOTE_STATUS.VOTING.value);
	 	List<OaVote> list = oaChartterService.getAllVote(vote, UtilTool.getCompanyId(request), useId, depId, getOtherPager(row));
	 	//加载当前人员投票状态
		for (OaVote oaVote : list) {
			List<OaVoteStatus>  statusList = oaChartterService.getStatusByEmpAndVoteId(UtilTool.getEmployeeId(request), oaVote.getPrimaryKey(), UtilTool.getCompanyId(request));
		    if(statusList.isEmpty() == true){
		    	oaVote.setVoteCount(0);
		    }else{
		    	oaVote.setVoteCount(1111);
		    }
		    
		    oaVote.setEmployee(employeeinfoService.getEmployeeByPK(oaVote.getOaVoteEmp()));
		}
	 	return list;
	}
	
	
	/**
	 * 当天日程
	 * @param context
	 * @param request
	 * @param row
	 * @return
	 */
	public List<OaCalender> getOaCalenderByEmpId(ServletContext context,HttpServletRequest request,int row){
		OaCalender cal = new OaCalender();
		cal.setOaCalenderEmp(UtilTool.getEmployeeId(request));
		cal.setCompanyId(UtilTool.getCompanyId(request));
		cal.setOaCalenderStart(UtilWork.getToday());
		cal.setOaCalenderStatus(EnumUtil.OA_CALENDER_STATUS.two.value);
		List<OaCalender> listoaCalender =  workArrangeService.getAllOaCalender(cal,getOtherPager(row));
		for(OaCalender oc : listoaCalender){
			oc.setLibrary(UtilTool.getLibraryInfoByPk(context, request, oc.getOaCalenderType()));
		}
		return listoaCalender;
	}
	
	/**
	 * 定时提醒
	 * @param context
	 * @param request
	 * @param row
	 * @return
	 */
	public List<HrmTimedrecord> getHrmTimerecord(ServletContext context,HttpServletRequest request,int row){
		HrmTimedrecord temp = new HrmTimedrecord();
		temp.setCompanyId(UtilTool.getCompanyId(request));
		temp.setRecordId(UtilTool.getEmployeeId(request));
		List<HrmTimedrecord> list = personalOfficeService.getTimedValidByCompanyAndEmpId(temp, getOtherPager(row));
		return list;
	}
	
	
	/**
	 * 在线人员
	 * @param context
	 * @param request
	 * @return
	 */
	public List<OnlineHrmEmployeeBean> getOnlineEmployee(ServletContext context,HttpServletRequest request){
		HrmEmployee employee = new HrmEmployee();
		int companyId= UtilTool.getCompanyId(request);
		Set<String> employeeIds =  OnlineUserBindingListener.getOnlineList(context, companyId);
		if (employeeIds.size()==0) {
			return new ArrayList<OnlineHrmEmployeeBean>();
		}
		employee.setHrmEmployeeStatus(EnumUtil.HRM_EMPLOYEE_STATUS.Separation.value);
		employee.setHrmEmployeeActive(EnumUtil.SYS_ISACTION.Vaild.value);
		String ids = UtilTool.getStringFormSetIsString(employeeIds);
		employee.setEmployeeIds(ids);
		employee.setCompanyId(companyId);
		//去掉自己
		//employee.setPrimaryKey(UtilTool.getEmployeeId(request));
		List<OnlineHrmEmployeeBean> list = oaDesktopService.getOnlineEmployee(employee);
		return list;
	}
	
	/**
	 * 规章制度
	 * @param context
	 * @param request
	 * @param row
	 * @return
	 */
	public List<OaRegulations> getOaRegulationsByEmpId(ServletContext context,HttpServletRequest request,int row){
		OaRegulations regul = new OaRegulations();
		regul.setCompanyId(UtilTool.getCompanyId(request));
		regul.setRegulationsStatus(EnumUtil.SYS_ISACTION.Vaild.value);
		regul.setTmpDatetime(UtilWork.getToday());
		List<OaRegulations> list = cmpReService.getOaRegulationsByPager(regul, getOtherPager(row));
		return list;
	}
	
	/**
	 * 即时消息
	 * @param context
	 * @param request
	 * @param row
	 * @return
	 */
	public List<OaSmsInbox> getOaSmsByEmpId(ServletContext context,HttpServletRequest request,int row){
		OaSmsInbox oaSmsInbox = new OaSmsInbox();
		
		oaSmsInbox.setCompanyId(UtilTool.getCompanyId(request));
		oaSmsInbox.setOaSmsInboxEmp(UtilTool.getEmployeeId(request));
		
		List<OaSmsInbox> list = moblieSmsService.getAllOaSmsInbox(oaSmsInbox,  getOtherPager(row));
		return list;
	}
	
	/**
	 *	个人邮件(内部邮件)
	 * @param context
	 * @param request
	 * @param row
	 * @return
	 */
	public List<OaMailEmp> getOaMailByEmpId(ServletContext context,HttpServletRequest request,int row){
		OaMailEmp oaMailEmp = new OaMailEmp();
		oaMailEmp.setCompanyId(UtilTool.getCompanyId(request));
		oaMailEmp.setOaMailEmpEmpid(UtilTool.getEmployeeId(request));
		oaMailEmp.setOaMailEmpStatus(OA_MAIL_STATUS.APPECT.value);
		OaMailInbox oaMailInbox =new OaMailInbox();
		List<OaMailEmp> list = mailService.getAllOaMailInbox(oaMailEmp,oaMailInbox, getOtherPager(row));
		return list;
	}
	
	/**
	 * 外部邮箱
	 * @param context
	 * @param request
	 * @param row
	 * @param setId
	 * @return
	 */
	public List<OaNetmailInbox> getOaNetMailByEmpId(ServletContext context,HttpServletRequest request,int row,String setId){
		OaNetmailInbox inbox = new OaNetmailInbox();
		inbox.setOaNetmailInboxEmpid(UtilTool.getEmployeeId(request));
		inbox.setCompanyId(UtilTool.getCompanyId(request));
		inbox.setOaNetmailSetId(setId);
		List<OaNetmailInbox> inboxlist = new ArrayList<OaNetmailInbox>();
		List<Object[]> objlist = mailService.getNetmailInboxByPager(inbox, getOtherPager(row));
		for (Object[] objs : objlist) {
			OaNetmailInbox box = new OaNetmailInbox();
			box.setPrimaryKey(Long.parseLong(objs[0].toString()));
			int isread = Integer.parseInt(objs[6].toString());
			
			String sender = objs[1]==null?"":objs[1].toString();
			String sendmail = objs[4].toString();
			if (sender.length()==0) {
				sender = sendmail.split("@")[0];
			}
			box.setOaNetmailInboxSender(sender);
			box.setOaNetmailInboxTime(objs[2].toString());
			box.setOaNetmailInboxTitle(objs[3].toString());
			box.setOaNetmailSetFrom(sendmail);
			box.setOaNetmailInboxAffix(objs[5]==null?"":objs[5].toString());
			box.setOaNetmailIsRead(isread);
			box.setOaNetmailUrgent(Integer.parseInt(objs[7].toString()));
			inboxlist.add(box);
		}
		return inboxlist;
	}
}
