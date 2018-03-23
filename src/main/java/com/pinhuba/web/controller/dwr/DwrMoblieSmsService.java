package com.pinhuba.web.controller.dwr;

import java.util.ArrayList;
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
import com.pinhuba.common.util.ConstWords;
import com.pinhuba.common.util.EnumUtil;
import com.pinhuba.common.util.LoginContext;
import com.pinhuba.common.util.UtilTool;
import com.pinhuba.common.util.UtilWork;
import com.pinhuba.common.util.WebUtilWork;
import com.pinhuba.core.iservice.IMoblieSmsService;
import com.pinhuba.core.pojo.HrmEmployee;
import com.pinhuba.core.pojo.HrmTimedrecord;
import com.pinhuba.core.pojo.OaSmsInbox;
import com.pinhuba.core.pojo.OaSmsSend;

public class DwrMoblieSmsService {
   
	private final static Logger logger = Logger.getLogger(DwrMoblieSmsService.class);
	
	@Resource
	private IMoblieSmsService moblieSmsService;
	
	/**
	 * 发送短信
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean saveSmsSend(ServletContext context, HttpServletRequest request, OaSmsSend oaSmsSend) {
		SessionUser user = (SessionUser)LoginContext.getSessionValueByLogin(request);
		oaSmsSend.setCompanyId(UtilTool.getCompanyId(request));
		oaSmsSend.setRecordId(user.getEmployeeInfo().getPrimaryKey());
		oaSmsSend.setOaSmsSendEmp(user.getEmployeeInfo().getPrimaryKey());
		oaSmsSend.setOaSmsSendEmpName(user.getEmployeeInfo().getHrmEmployeeName());
		oaSmsSend.setOaSmsSendTime(UtilWork.getNowTime());
		oaSmsSend.setRecordDate(UtilWork.getNowTime());
		oaSmsSend.setOaSmsType(1);
		moblieSmsService.saveSmsSend(context,oaSmsSend);
		logger.info("发送短信...");
		return WebUtilWork.WebResultPack(null);
	}
	
	/**
	 * 显示发件箱
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean listOaSmsSend(ServletContext context, HttpServletRequest request, OaSmsSend oaSmsSend, Pager pager) {
		List<OaSmsSend> list = null;
		SessionUser user = (SessionUser)LoginContext.getSessionValueByLogin(request);
		oaSmsSend.setCompanyId(UtilTool.getCompanyId(request));
		oaSmsSend.setOaSmsSendEmp(user.getEmployeeInfo().getPrimaryKey());
		
		pager = PagerHelper.getPager(pager,moblieSmsService.listOaSmsSendCount(oaSmsSend));
		list = moblieSmsService.getAllOaSmsSend(oaSmsSend, pager);

		logger.info("显示发件箱...");
		return WebUtilWork.WebResultPack(list, pager);
	}
	
	/**
	 * 显示收件箱
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean listOaSmsInbox(ServletContext context, HttpServletRequest request, OaSmsInbox oaSmsInbox, Pager pager) {
		List<OaSmsInbox> list = null;
		oaSmsInbox.setCompanyId(UtilTool.getCompanyId(request));
		oaSmsInbox.setOaSmsInboxEmp(UtilTool.getEmployeeId(request));
		
		pager = PagerHelper.getPager(pager,moblieSmsService.listOaSmsInboxCount(oaSmsInbox));
		list = moblieSmsService.getAllOaSmsInbox(oaSmsInbox, pager);

		logger.info("显示收件箱...");
		return WebUtilWork.WebResultPack(list, pager);
	}
	
	/**
	 * 发件箱重发
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean sendAgainOaSmsSend(ServletContext context, HttpServletRequest request, long oaSmsSendId) {

		OaSmsSend oaSmsSend = moblieSmsService.getOaSmsSendByPK(oaSmsSendId);
		SessionUser user = (SessionUser)LoginContext.getSessionValueByLogin(request);
		OaSmsSend newOaSmsSend = new OaSmsSend();
		newOaSmsSend.setCompanyId(oaSmsSend.getCompanyId());
		newOaSmsSend.setOaSmsSendAcpemp(oaSmsSend.getOaSmsSendAcpemp());
		newOaSmsSend.setOaSmsSendAcpempName(oaSmsSend.getOaSmsSendAcpempName());
		newOaSmsSend.setOaSmsSendContent(oaSmsSend.getOaSmsSendContent());
		newOaSmsSend.setOaSmsSendEmp(user.getEmployeeInfo().getPrimaryKey());
		newOaSmsSend.setOaSmsSendEmpName(user.getEmployeeInfo().getHrmEmployeeName());
		newOaSmsSend.setOaSmsSendTime(UtilWork.getNowTime());
		newOaSmsSend.setOaSmsType(oaSmsSend.getOaSmsType());
		newOaSmsSend.setRecordDate(UtilWork.getNowTime());
		newOaSmsSend.setRecordId(user.getEmployeeInfo().getPrimaryKey());
		
		moblieSmsService.saveSmsSend(context,newOaSmsSend);
		
		
		logger.info("发件箱重发...");
		return WebUtilWork.WebResultPack(null);
	}
	
	/**
	 * 通过id获取发件箱
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean getOaSmsSendByPk(ServletContext context, HttpServletRequest request, long oaSmsSendId) {

		OaSmsSend oaSmsSend = moblieSmsService.getOaSmsSendByPK(oaSmsSendId);
        List<OaSmsSend> list = new ArrayList<OaSmsSend>();
        list.add(oaSmsSend);
		logger.info("通过id获取发件箱...");
		return WebUtilWork.WebResultPack(list);
	}
	
	/**
	 * 通过人员id获取发件箱
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean listEmployee(ServletContext context, HttpServletRequest request, String id) {

		HrmEmployee employee = moblieSmsService.getEmployeeByPk(id);
        List<HrmEmployee> list = new ArrayList<HrmEmployee>();
        list.add(employee);
		logger.info("通过id获取发件箱...");
		return WebUtilWork.WebResultPack(list);
	}
	
	/**
	 * 删除发件箱
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	
	public ResultBean deleteOaSmsSendByPks(ServletContext context, HttpServletRequest request, long[] oaSmsSendPKs) {

		moblieSmsService.deleteOaSmsSendByPks(oaSmsSendPKs);
		return WebUtilWork.WebResultPack(null);
	}
	
	/**
	 * 通过id获取收件箱
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean getOaSmsInboxByPk(ServletContext context, HttpServletRequest request, long oaSmsInboxId) {

		OaSmsInbox oaSmsInbox = moblieSmsService.getOaSmsInboxByPK(oaSmsInboxId);
        List<OaSmsInbox> list = new ArrayList<OaSmsInbox>();
        list.add(oaSmsInbox);
       
		logger.info("通过id获取收件箱...");
		return WebUtilWork.WebResultPack(list);
	}
	
	/**
	 * 删除收件箱
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	
	public ResultBean deleteOaSmsInboxByPks(ServletContext context, HttpServletRequest request, long[] oaSmsInboxPKs) {

		moblieSmsService.deleteOaSmsInboxByPks(oaSmsInboxPKs);
		return WebUtilWork.WebResultPack(null);
	}
	
	/**
	 * 设置邮件为已读
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean setOaSmsInboxIsread(ServletContext context, HttpServletRequest request, long[] oaSmsInboxId) {
		OaSmsInbox oaSmsInbox=null;
		OaSmsInbox newoaSmsInbox=null;
		int tmpcount =0 ;
		for (long l : oaSmsInboxId) {
			oaSmsInbox = moblieSmsService.getOaSmsInboxByPK(l);
			if (oaSmsInbox.getOaSmsInboxIsread() == EnumUtil.OA_SMS_INBOX_ISREAD.two.value) {
				tmpcount++;
			}
			oaSmsInbox.setOaSmsInboxIsread(EnumUtil.OA_SMS_INBOX_ISREAD.one.value);
			newoaSmsInbox = moblieSmsService.saveOaSmsInbox(oaSmsInbox);
		}
		
		//将内存中的短信清掉
		if (context.getAttribute(ConstWords.servletContext_MSGBOX)!=null) {
			Map<String,Integer> map =(Map)context.getAttribute(ConstWords.servletContext_MSGBOX);
			String userid = UtilTool.getEmployeeId(request);
			if(map != null && map.size()>0){
				if(map.containsKey(userid)){
					int count = map.get(userid);
					if(count>tmpcount){
						map.put(userid, count-tmpcount);
					}else{
						map.remove(userid);
					}
				}
			}
		}
		logger.info("设置邮件为已读...");
		return WebUtilWork.WebResultPack(null);
	}
	
	/**
	 * 获取未读短信个数
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getisreadSms(ServletContext context, HttpServletRequest request) {
		int count=0;
		if (context.getAttribute(ConstWords.servletContext_MSGBOX)==null) {
			return count;
		}
		Map<String,Integer> map =(Map<String, Integer>)context.getAttribute(ConstWords.servletContext_MSGBOX);
		String userid = UtilTool.getEmployeeId(request);
		if(map != null && map.size()>0){
			if(map.containsKey(userid)){
				count = map.get(userid);
			}
		}
		logger.info("获取未读短信个数...");
		return count;
	}
	
	
	@SuppressWarnings("unchecked")
	public void resetSms(ServletContext context, HttpServletRequest request){
		if (context.getAttribute(ConstWords.servletContext_MSGBOX)!=null) {
			Map<String,Integer> map =(Map<String, Integer>)context.getAttribute(ConstWords.servletContext_MSGBOX);
			String userid = UtilTool.getEmployeeId(request);
			if(map != null && map.size()>0){
				if(map.containsKey(userid)){
					map.remove(userid);
				}
			}
		}
	}
	
	
	//==============================定时提醒===========================
	/**
	 * 获取未读定时提醒
	 */
	@SuppressWarnings("unchecked")
	public int getSchTimer(ServletContext context, HttpServletRequest request) {
		int count=0;
		if (context.getAttribute(ConstWords.servletContext_Timer)==null) {
			return count;
		}
		Map<String,List<HrmTimedrecord>> map =(Map<String, List<HrmTimedrecord>>)context.getAttribute(ConstWords.servletContext_Timer);
		String empId = UtilTool.getEmployeeId(request);
		if(map != null && map.size()>0){
			if(map.containsKey(empId)){
				count = map.get(empId).size();
			}
		}
		return count;
	}
	
	
	public List<HrmTimedrecord> getSchTimerByEmpId(ServletContext context, HttpServletRequest request){
		List<HrmTimedrecord> list = new ArrayList<HrmTimedrecord>();
		if (context.getAttribute(ConstWords.servletContext_Timer)==null) {
			return list;
		}
		Map<String,List<HrmTimedrecord>> map =(Map<String, List<HrmTimedrecord>>)context.getAttribute(ConstWords.servletContext_Timer);
		String empId = UtilTool.getEmployeeId(request);
		if(map != null && map.size()>0){
			if(map.containsKey(empId)){
				list = map.get(empId);
			}
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public void clearSchTimer(ServletContext context, HttpServletRequest request){
		if (context.getAttribute(ConstWords.servletContext_Timer)!=null) {
			Map<String,Integer> map =(Map<String, Integer>)context.getAttribute(ConstWords.servletContext_Timer);
			String empId = UtilTool.getEmployeeId(request);
			if(map != null && map.size()>0){
				if(map.containsKey(empId)){
					map.remove(empId);
				}
			}
		}
	}
	
}
