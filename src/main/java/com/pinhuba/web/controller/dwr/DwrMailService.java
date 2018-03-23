package com.pinhuba.web.controller.dwr;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
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
import com.pinhuba.common.util.EnumUtil.OA_MAIL_STATUS;
import com.pinhuba.common.util.file.FileTool;
import com.pinhuba.common.util.security.Base64;
import com.pinhuba.core.iservice.IMailService;
import com.pinhuba.core.pojo.HrmEmployee;
import com.pinhuba.core.pojo.OaMailEmp;
import com.pinhuba.core.pojo.OaMailInbox;
import com.pinhuba.core.pojo.OaMailSend;
import com.pinhuba.core.pojo.OaNetmailInbox;
import com.pinhuba.core.pojo.OaNetmailPerson;
import com.pinhuba.core.pojo.OaNetmailSendbox;
import com.pinhuba.core.pojo.OaNetmailSet;
import com.pinhuba.core.pojo.OaNetmailUid;

public class DwrMailService {

	private final static Logger logger = Logger.getLogger(DwrMailService.class);
	
	@Resource
	private IMailService mailService;
	
	/**
	 * 显示邮件收件箱
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean listOaMailInbox(ServletContext context, HttpServletRequest request, OaMailEmp oaMailEmp, Pager pager) {
		List<OaMailEmp> list = null;
		oaMailEmp.setCompanyId(UtilTool.getCompanyId(request));
		oaMailEmp.setOaMailEmpEmpid(UtilTool.getEmployeeId(request));
		oaMailEmp.setOaMailEmpStatus(OA_MAIL_STATUS.APPECT.value);
		OaMailInbox oaMailInbox =oaMailEmp.getOaMailEmpInboxid(); 
		pager = PagerHelper.getPager(pager,mailService.listOaMailInboxCount(oaMailEmp,oaMailInbox));
		list = mailService.getAllOaMailInbox(oaMailEmp,oaMailInbox, pager);

		logger.info("显示邮件收件箱...");
		return WebUtilWork.WebResultPack(list, pager);
	}
	
	/**
	 * 获取未读邮件数量
	 * @param context
	 * @param request
	 * @return
	 */
	public int getOaMailNoRead(ServletContext context, HttpServletRequest request){
		OaMailEmp oaMailEmp = new OaMailEmp();
		oaMailEmp.setCompanyId(UtilTool.getCompanyId(request));
		oaMailEmp.setOaMailEmpEmpid(UtilTool.getEmployeeId(request));
		oaMailEmp.setOaMailEmpStatus(OA_MAIL_STATUS.APPECT.value);
		OaMailInbox oaMailInbox =new OaMailInbox();
		oaMailEmp.setOaMailEmpIsread(EnumUtil.OA_SMS_INBOX_ISREAD.two.value);
		return mailService.listOaMailInboxCount(oaMailEmp,oaMailInbox);
	}
	
	
	/**
	 * 发送或者保存邮件
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean saveOaMailSend(ServletContext context, HttpServletRequest request, OaMailSend oaMailSend,String isSaveSendBox) {
		SessionUser user = (SessionUser)LoginContext.getSessionValueByLogin(request);
		// 保存附件记录
		if(oaMailSend.getOaMailSendAffix() != null && oaMailSend.getOaMailSendAffix().length()>0){
		   String affix = UtilTool.saveAttachments(context, request, oaMailSend.getOaMailSendAffix());
		   oaMailSend.setOaMailSendAffix(affix);
		}
		oaMailSend.setCompanyId(UtilTool.getCompanyId(request));
		oaMailSend.setOaMailSendSenderid(user.getEmployeeInfo().getPrimaryKey());
		oaMailSend.setOaMailSendSenderName(user.getEmployeeInfo().getHrmEmployeeName());
		oaMailSend.setOaMailSendSenddep(user.getEmployeeDeptName());
		oaMailSend.setOaMailSendSaveTime(UtilWork.getNowTime());
		oaMailSend.setOaMailSendTime(UtilWork.getNowTime());
		oaMailSend.setRecordDate(UtilWork.getNowTime());
		oaMailSend.setRecordId(user.getEmployeeInfo().getPrimaryKey());
		
		mailService.saveOaMailSend(oaMailSend,isSaveSendBox);
		logger.info("发送或者保存邮件...");
		return WebUtilWork.WebResultPack(null);
	}
	
	/**
	 * 显示邮件发件箱
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean listOaMailSend(ServletContext context, HttpServletRequest request, OaMailSend oaMailSend, Pager pager) {
		List<OaMailSend> list = null;
		SessionUser user = (SessionUser)LoginContext.getSessionValueByLogin(request);
		oaMailSend.setCompanyId(UtilTool.getCompanyId(request));
		oaMailSend.setOaMailSendSenderid(user.getEmployeeInfo().getPrimaryKey());
		oaMailSend.setOaMailSendType(1);
		
		pager = PagerHelper.getPager(pager,mailService.listOaMailSendCount(oaMailSend));
		list = mailService.getAllOaMailSend(oaMailSend, pager);

		logger.info("显示邮件发件箱...");
		return WebUtilWork.WebResultPack(list, pager);
	}
	
	/**
	 * 显示邮件草稿箱
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean listOaMailSend_draft(ServletContext context, HttpServletRequest request, OaMailSend oaMailSend, Pager pager) {
		List<OaMailSend> list = null;
		SessionUser user = (SessionUser)LoginContext.getSessionValueByLogin(request);
		oaMailSend.setCompanyId(UtilTool.getCompanyId(request));
		oaMailSend.setOaMailSendSenderid(user.getEmployeeInfo().getPrimaryKey());
		oaMailSend.setOaMailSendType(2);
		
		pager = PagerHelper.getPager(pager,mailService.listOaMailSendCount(oaMailSend));
		list = mailService.getAllOaMailSend(oaMailSend, pager);

		logger.info("显示邮件草稿箱...");
		return WebUtilWork.WebResultPack(list, pager);
	}
	
	/**
	 * 设置邮件为已读
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean setMailinboxIsread(ServletContext context, HttpServletRequest request,long mailEmpId) {
		OaMailEmp oMailEmp = mailService.getOaMailEmpByPk(mailEmpId);
		if(oMailEmp.getOaMailEmpIsread() != null && oMailEmp.getOaMailEmpIsread() == 2){ //未读邮件
			mailService.saveOaMailEmp(oMailEmp);
		}
		
		logger.info("设置邮件为已读...");
		return WebUtilWork.WebResultPack(null);
	}
	/**
	 * 获得收件通过关联表id
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean getMailInboxByMailEmpPk(ServletContext context, HttpServletRequest request,long mailEmpId) {
		OaMailEmp oMailEmp = mailService.getOaMailEmpByPk(mailEmpId);
		
		List<OaMailEmp> oMailEmpList = new ArrayList<OaMailEmp>();
		oMailEmpList.add(oMailEmp);
		
		logger.info("设置邮件为已读...");
		return WebUtilWork.WebResultPack(oMailEmpList);
	}
	
	/**
	 * 从在线人员 获得 收件人ID
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean listEmployee(ServletContext context, HttpServletRequest request, String id) {
		HrmEmployee employee = mailService.getEmployeeByPk(id);
        List<HrmEmployee> list = new ArrayList<HrmEmployee>();
        list.add(employee);
		logger.info("通过id获取发件箱...");
		return WebUtilWork.WebResultPack(list);
	}
	
	/**
	 * 删除已发送的邮件
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean deleteOaMailSendByPks(ServletContext context, HttpServletRequest request,long[] oaMailSendIds) {
		
		for (long l : oaMailSendIds) {
			String affixid = mailService.deleteOaMailSendByPks(l);
			if(affixid != null && affixid.length()>0){ //删除附件
				UtilTool.deleteAttachmentsAndFile(context, request, affixid);
			}
		}

		logger.info("删除已发送的邮件...");
		return WebUtilWork.WebResultPack(null);
	}
	/**
	 * 通过id查询OaMailSend
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean getMailOutboxByPk(ServletContext context, HttpServletRequest request,long oaMailSendId) {
		OaMailSend oaMailSend = mailService.getOaMailSendByPk(oaMailSendId);
		
		List<OaMailSend> oaMailSendList = new ArrayList<OaMailSend>();
		oaMailSendList.add(oaMailSend);
		
		logger.info("通过id查询OaMailSend...");
		return WebUtilWork.WebResultPack(oaMailSendList);
	}

	/**
	 * 显示已删除
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean listOaMailInbox_delbox(ServletContext context, HttpServletRequest request, OaMailEmp oaMailEmp, Pager pager) {
		List<OaMailEmp> list = null;
		SessionUser user = (SessionUser)LoginContext.getSessionValueByLogin(request);
		oaMailEmp.setCompanyId(UtilTool.getCompanyId(request));
		oaMailEmp.setOaMailEmpEmpid(user.getEmployeeInfo().getPrimaryKey());
		oaMailEmp.setOaMailEmpStatus(2);
		OaMailInbox oaMailInbox =oaMailEmp.getOaMailEmpInboxid();
		pager = PagerHelper.getPager(pager,mailService.listOaMailInboxCount(oaMailEmp,oaMailInbox));
		list = mailService.getAllOaMailInbox(oaMailEmp,oaMailInbox, pager);

		logger.info("显示已删除...");
		return WebUtilWork.WebResultPack(list, pager);
	}
	/**
	 * 删除收件箱的邮件
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean deleteOaMailInboxByPks(ServletContext context, HttpServletRequest request,long[] oaMailInboxIds) {
		mailService.updateOaMailInboxByPks(oaMailInboxIds);
		
		logger.info("删除收件箱的邮件...");
		return WebUtilWork.WebResultPack(null);
	}
	/**
	 * 删除已删除的邮件
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean deleteOaMailEmpByPks(ServletContext context, HttpServletRequest request,long[] oaMailInboxIds) {
		
		for (long l : oaMailInboxIds) {
			String affixid = mailService.deleteOaMailEmpByPks(l);
			if(affixid != null && affixid.length()>0){ //删除附件
				UtilTool.deleteAttachmentsAndFile(context, request, affixid);
			}
		}
		
		logger.info("删除已删除的邮件...");
		return WebUtilWork.WebResultPack(null);
	}
	
	/**
	 * 设置邮件已读
	 * @param context
	 * @param request
	 * @param ids
	 * @return
	 */
	public ResultBean setMailReaded(ServletContext context, HttpServletRequest request,long[] ids){
		mailService.setMailRead(ids);
		
		logger.info("设置邮件已读...");
		return WebUtilWork.WebResultPack(null);
	}
	
	public ResultBean getOaNetMailSetList(ServletContext context,HttpServletRequest request){
		List<OaNetmailSet> list = mailService.getOaNetMailSetList(UtilTool.getEmployeeId(request), UtilTool.getCompanyId(request));
		return WebUtilWork.WebResultPack(list);
	}
	
	public int getOaNetMailSetListByCount(ServletContext context,HttpServletRequest request,int type){
		return mailService.getOaNetMailSetCount(UtilTool.getEmployeeId(request), UtilTool.getCompanyId(request), type);
	}
	
	public ResultBean saveOaNetMailSet(ServletContext context,HttpServletRequest request,OaNetmailSet[] mailsets){
		String empId = UtilTool.getEmployeeId(request);
		int companyId = UtilTool.getCompanyId(request);
		mailService.saveOaNetMailSet(empId, companyId, mailsets);
		return WebUtilWork.WebResultPack(null);
	}
	
	/**
	 * 删除邮箱设置
	 * @param context
	 * @param request
	 * @param oaNetmailId
	 * @return
	 */
	public ResultBean deleteOaNetMailBySetPk(ServletContext context,HttpServletRequest request,long oaNetmailId){
		OaNetmailSet set = mailService.getOaNetmailSetByPk(oaNetmailId);
		
		String empId = UtilTool.getEmployeeId(request);
		int companyId = UtilTool.getCompanyId(request);
		//清除收件箱
		OaNetmailInbox inbox = new OaNetmailInbox();
		inbox.setOaNetmailSetId(set.getOaNetmailFrom());
		inbox.setCompanyId(companyId);
		inbox.setOaNetmailInboxEmpid(empId);
		
		List<OaNetmailInbox> inboxlist = mailService.getNetmailInboxBySetId(inbox);
		for (OaNetmailInbox box : inboxlist) {
			if (box.getOaNetmailInboxAffix()!=null&&box.getOaNetmailInboxAffix().length()>0) {
				UtilTool.deleteAttachmentsAndFile(context, request, box.getOaNetmailInboxAffix());
			}
			mailService.deleteOaNetMailInbox(box);
		}
		//清除发件箱.
		OaNetmailSendbox sendbox = new OaNetmailSendbox();
		sendbox.setOaNetmailSendEmpid(empId);
		sendbox.setCompanyId(companyId);
		sendbox.setOaNetmailSetFrom(set.getOaNetmailFrom());
		
		List<OaNetmailSendbox> sendlist = mailService.getNetmailSendboxBySetId(sendbox);
		
		for (OaNetmailSendbox tmp : sendlist) {
			if (tmp.getOaNetmailSendType()==EnumUtil.Net_Mail_SendBox_Type.Sketch.value) {
				if (tmp.getOaNetmailSendAffix()!=null&&tmp.getOaNetmailSendAffix().trim().length()>0) {
					UtilTool.deleteAttachmentsAndFile(context, request, tmp.getOaNetmailSendAffix());
				}
			}else{
				if (tmp.getOaNetmailSendEmpids()==null||tmp.getOaNetmailSendEmpids().trim().length()==0) {
					if (tmp.getOaNetmailSendAffix()!=null&&tmp.getOaNetmailSendAffix().trim().length()>0) {
						UtilTool.deleteAttachmentsAndFile(context, request, tmp.getOaNetmailSendAffix());
					}
				}
			}
			mailService.deleteOaNetMailSend(tmp);
		}
		
		//清除uid
		OaNetmailUid uidobj = new OaNetmailUid();
		uidobj.setOaMailSetId(set.getOaNetmailFrom());
		uidobj.setOaMailEmpId(empId);
		uidobj.setCompanyId(companyId);
		mailService.deleteOanetmailUid(uidobj);
		
		
		//清除邮箱
		mailService.deleteOaNetMailSet(oaNetmailId);
		return WebUtilWork.WebResultPack(null);
	}
	
	public ResultBean getOaNetmailPersonByPager(ServletContext context,HttpServletRequest request,OaNetmailPerson person,Pager pager){
		person.setCompanyId(UtilTool.getCompanyId(request));
		person.setHrmEmployeeId(UtilTool.getEmployeeId(request));
		pager = PagerHelper.getPager(pager, mailService.getOaNetMailPersonListByCount(person));
		List<OaNetmailPerson> list = mailService.getOaNetMailPersonListByPager(person, pager);
		return WebUtilWork.WebResultPack(list,pager);
	}
	
	public ResultBean saveNetmailPerson(ServletContext context,HttpServletRequest request,OaNetmailPerson person){
		person.setOaNetmailDate(UtilWork.getNowTime());
		person.setCompanyId(UtilTool.getCompanyId(request));
		person.setHrmEmployeeId(UtilTool.getEmployeeId(request));		
		String mail = person.getOaNetmailEmpmail();
		if (!Pattern.matches("\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*", mail)) {
			return new ResultBean(false,"邮箱地址不合法！");
		}
		OaNetmailPerson tmp = mailService.saveOaNetMailPerson(person);
		return WebUtilWork.WebObjectPack(tmp);
	}
	
	public ResultBean deletePersonByPks(ServletContext context,HttpServletRequest request,long[] pks){
		mailService.deleteOaNetMailPersonByPks(pks);
		return WebUtilWork.WebResultPack(null);
	}
	
	public ResultBean getOaNetMailPersonByPk(ServletContext context,HttpServletRequest request,long pk){
		OaNetmailPerson tmp = mailService.getOaNetMailPersonByPk(pk);
		return WebUtilWork.WebObjectPack(tmp);
	}
	
	/**
	 * 保存草稿箱
	 * @param context
	 * @param request
	 * @param sendbox
	 * @return
	 */
	public ResultBean saveNetmailSketch(ServletContext context,HttpServletRequest request,OaNetmailSendbox sendbox){
		String time = UtilWork.getNowTime();
		String empId = UtilTool.getEmployeeId(request);
		if(sendbox.getPrimaryKey()>0){//清除原附件
			OaNetmailSendbox tmp = mailService.getOaNetMailSendBoxBypk(sendbox.getPrimaryKey());
			UtilTool.deleteAttachmentsNoFile(context, request, tmp.getOaNetmailSendAffix());
			sendbox.setRecordId(tmp.getRecordId());
			sendbox.setRecordDate(tmp.getRecordDate());
		}else{
			sendbox.setRecordId(empId);
			sendbox.setRecordDate(time);
		}
		
		// 保存附件记录
		if(sendbox.getOaNetmailSendAffix() != null && sendbox.getOaNetmailSendAffix().length()>0){
		   String affix = UtilTool.saveAttachments(context, request, sendbox.getOaNetmailSendAffix());
		   sendbox.setOaNetmailSendAffix(affix);
		}
		sendbox.setOaNetmailSendTime(time);
		OaNetmailSet netset = mailService.getOaNetmailSetByPk(Long.parseLong(sendbox.getOaNetmailSetFrom()) );
		if (netset!=null) {
			sendbox.setOaNetmailSetFrom(netset.getOaNetmailFrom());
		}
		sendbox.setOaNetmailSendType(EnumUtil.Net_Mail_SendBox_Type.Sketch.value);
		sendbox.setCompanyId(UtilTool.getCompanyId(request));
				
		sendbox.setOaNetmailSendEmpid(empId);
		
		mailService.saveOaNetMailSendBoxBySketch(sendbox);
		return WebUtilWork.WebResultPack(null);
	}
	
	public ResultBean getNetMailListByPager(ServletContext context,HttpServletRequest request,OaNetmailSendbox sendbox,Pager pager){
		sendbox.setOaNetmailSendEmpid(UtilTool.getEmployeeId(request));
		sendbox.setCompanyId(UtilTool.getCompanyId(request));
		
		pager = PagerHelper.getPager(pager, mailService.getOaNetMailSendBoxByCount(sendbox));
		List<OaNetmailSendbox> list = mailService.getOaNetMailSendBoxByPager(sendbox, pager);
		
		return WebUtilWork.WebResultPack(list,pager);
	}
	
	/**
	 * 发送邮件
	 * @param context
	 * @param request
	 * @param sendbox
	 * @param issavesend
	 * @return
	 * @throws Exception
	 */
	public ResultBean saveNetmailSend(ServletContext context,HttpServletRequest request,OaNetmailSendbox sendbox,boolean issavesend) throws Exception{
		String time = UtilWork.getNowTime();
		String empId = UtilTool.getEmployeeId(request);
		if(sendbox.getPrimaryKey()>0){//清除原附件
			OaNetmailSendbox tmp = mailService.getOaNetMailSendBoxBypk(sendbox.getPrimaryKey());
			UtilTool.deleteAttachmentsNoFile(context, request, tmp.getOaNetmailSendAffix());
		}
		
		// 保存附件记录
		SessionUser user = (SessionUser) LoginContext.getSessionValueByLogin(request);
		
		String files = sendbox.getOaNetmailSendAffix();
		String affix="";
		if(files != null && files.length()>0){
		   affix = UtilTool.saveAttachments(context, request, files);
		   sendbox.setOaNetmailSendAffix(affix);
		}
		
		
		OaNetmailSet netset = mailService.getOaNetmailSetByPk(Long.parseLong(sendbox.getOaNetmailSetFrom()) );
		if (netset!=null) {
			sendbox.setOaNetmailSetFrom(netset.getOaNetmailFrom());
		}
		sendbox.setOaNetmailSendType(EnumUtil.Net_Mail_SendBox_Type.Send.value);
		sendbox.setCompanyId(UtilTool.getCompanyId(request));
		sendbox.setOaNetmailSendTime(time);
		sendbox.setRecordId(empId);
		sendbox.setRecordDate(time);
		sendbox.setOaNetmailSendEmpid(empId);
		
		boolean bl = mailService.saveNetmailSend(sendbox, issavesend, netset, user, files);
		
		if (bl) {
			//清除附件
			String sendempIds = sendbox.getOaNetmailSendEmpids();
			if (!issavesend&&(sendempIds==null||sendempIds.length()==0)) {
				UtilTool.deleteAttachmentsAndFile(context, request, affix);
			}
			logger.info("清除附件...");
		}
		
		return WebUtilWork.WebResultPack(null);
	}
	
	/**
	 * 收取邮件
	 * @param context
	 * @param request
	 * @param setIds
	 * @return
	 * @throws Exception
	 */
	public ResultBean getNetMailFormServer(ServletContext context,HttpServletRequest request,long[] setIds) throws Exception{
		boolean bl=false;
		String empId = UtilTool.getEmployeeId(request);
		if(ConstWords.mailIsAccpMap.containsKey(empId)){
			bl = ConstWords.mailIsAccpMap.get(empId);
		}
		int count=0;
		if(!bl){
			count = mailService.saveNetMailInboxGetServer(context, request, setIds);
		}else{
			logger.info("邮件正在接受中...");
		}
		return WebUtilWork.WebObjectPack(count);
	}
	
	public ResultBean getNetMailSendDetailById(ServletContext context,HttpServletRequest request,long pk){
		OaNetmailSendbox sendbox = mailService.getOaNetMailSendBoxBypk(pk);
		//复制附件
		sendbox.setOaNetmailSendAffix(this.copyFileByoldFile(context, request, sendbox.getOaNetmailSendAffix()));
		return WebUtilWork.WebObjectPack(sendbox);
	}
	
	public ResultBean getNetMailSendDetailByIdDetail(ServletContext context,HttpServletRequest request,long pk){
		OaNetmailSendbox sendbox = mailService.getOaNetMailSendBoxBypk(pk);
		return WebUtilWork.WebObjectPack(sendbox);
	}
	
	
	/**
	 * 回复邮件
	 * @param context
	 * @param request
	 * @param pk
	 * @return
	 */
	public ResultBean getNetMailInboxDetailBackById(ServletContext context,HttpServletRequest request,long pk){
		OaNetmailInbox inbox = mailService.getOaNetmailInboxById(pk);
		
		OaNetmailSendbox sendbox = new OaNetmailSendbox();
		
		sendbox.setOaNetmailSendTitle("Re(回复):"+inbox.getOaNetmailInboxTitle());
		sendbox.setOaNetmailSendAdders(inbox.getOaNetmailSetFrom()+";");
		sendbox.setOaNetmailSendEmpids("");
		sendbox.setOaNetmailSetFrom(inbox.getOaNetmailSetId());
		sendbox.setOaNetmailReceipt(EnumUtil.OA_MAIL_RECEIPT.TWO.value);
		sendbox.setOaNetmailSendIsurgent(EnumUtil.OA_CALENDER_LEVEL.four.value);
		sendbox.setOaNetmailSendAffix("");
		
		StringBuffer result = new StringBuffer();
		result.append("<br/><br/><br/>");
		result.append("------------------ 原始邮件 ------------------<br/>");
		result.append("<label style='background-color:#ededed;width:100%;line-height:22px;padding-left:5px;border-left:1px solid #d1d1d1'>");
		String sender = inbox.getOaNetmailInboxSender();
		String sendmail = inbox.getOaNetmailSetFrom();
		
		if(sender==null||sender.trim().length()==0){
			sender = sendmail.split("@")[0];
		}
		
		result.append("<b>发件人：</b>\""+sender+"\"<"+sendmail+"><br/>");
		result.append("<b>发送时间：</b>"+inbox.getOaNetmailInboxTime()+"<br/>");
		result.append("<b>主题：</b>"+inbox.getOaNetmailInboxTitle());
		result.append("</label>");
		result.append("<br/><br/>");
		result.append(inbox.getOaNetmailInboxContent());
		sendbox.setOaNetmailSendContent(result.toString());
		return WebUtilWork.WebObjectPack(sendbox);
	}
	
	/**
	 * 转发时复制附件
	 * @param fileIds
	 */
	private String copyFileByoldFile(ServletContext context, HttpServletRequest request, String fileIds) {
			
		StringBuffer tmpstr = new StringBuffer();
		if (fileIds == null || fileIds.length() == 0) {
			return tmpstr.toString();
		}
		String files = UtilTool.getAttachments(context, request, fileIds);
		if (files.length() > 0) {
			String[] oldfiles = files.split(",");
			logger.info("复制新附件。。。");
			for (String str : oldfiles) {
				String[] tmps = str.split("\\|");
				String fname = tmps[0];
				String fpath = Base64.getStringFromBase64(tmps[1]);
				String fnpath = addInNameExt(fpath, "_CP");

				File srcFile = new File(fpath);
				if (srcFile.isFile()&&srcFile.exists()) {
					try {
						FileInputStream inputStream = new FileInputStream(srcFile);
						FileOutputStream outputStream = new FileOutputStream(new File(fnpath));
						byte[] readBytes = new byte[1024];
						int readbyte = inputStream.read(readBytes);
						while (readbyte != -1) {
							outputStream.write(readBytes, 0, readbyte);
							outputStream.flush();
							readbyte = inputStream.read(readBytes);
						}
						inputStream.close();
						outputStream.close();
						tmpstr.append(fname + "|" + Base64.getBase64FromString(fnpath) + ",");
					} catch (Exception e) {
						logger.error("转发邮件时复制附件异常:" + e.getMessage());
					}
				}
			}
			String tmp = tmpstr.toString();

			if (tmp.length() > 1) {
				tmp = tmp.substring(0, tmp.length() - 1);
			}
			return tmp;
		}
		return tmpstr.toString();
	}
	
	
	
	// 分离完整文件名的文件名和后缀,并在中间加入字符串后返回
	private String addInNameExt(String fullName, String add) {
		String name = "";// 文件名
		String ext = ""; // 后缀
		char point = '.';
		int index = fullName.lastIndexOf(point);

		if (index != -1) {// 如果有后缀
			name = fullName.substring(0, index);
			ext = fullName.substring(index + 1);
		} else {
			name = fullName;
		}

		return name.trim() + "_" + add + point + ext;
	}
	
	/**
	 * 删除没有用到的复制文件
	 * @param context
	 * @param request
	 * @param fileNames
	 */
	public void deleteCopyFiles(ServletContext context,HttpServletRequest request,String fileNames){
				
		ArrayList<String> list = new ArrayList<String>();	
		String files[] = fileNames.split(",");
		for (String str : files) {
			String[] tmps = str.split("\\|");
			if(tmps[1].length()>0){
				list.add(Base64.getStringFromBase64(tmps[1]));
			}
		}
		if (list.size()>0) {
			String[] delfiles = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				delfiles[i] = list.get(i);
			}
			FileTool.deleteFiles(delfiles);
			logger.info("删除附件。。。。");
		}
	}
	
	/**
	 * 转发
	 * @param context
	 * @param request
	 * @param pk
	 * @return
	 */
	public ResultBean getNetMailInboxDetailForwardById(ServletContext context,HttpServletRequest request,long pk){
		OaNetmailInbox inbox = mailService.getOaNetmailInboxById(pk);
		
		OaNetmailSendbox sendbox = new OaNetmailSendbox();
		
		sendbox.setOaNetmailSendTitle("Fw(转发):"+inbox.getOaNetmailInboxTitle());
		sendbox.setOaNetmailSendAdders("");
		sendbox.setOaNetmailSendEmpids("");
		sendbox.setOaNetmailSetFrom(inbox.getOaNetmailSetId());
		sendbox.setOaNetmailReceipt(EnumUtil.OA_MAIL_RECEIPT.TWO.value);
		sendbox.setOaNetmailSendIsurgent(EnumUtil.OA_CALENDER_LEVEL.four.value);
		//复制新附件
		sendbox.setOaNetmailSendAffix(this.copyFileByoldFile(context, request, inbox.getOaNetmailInboxAffix()));
		
		StringBuffer result = new StringBuffer();
		result.append("<br/><br/><br/>");
		result.append("------------------ 原始邮件 ------------------<br/>");
		result.append("<label style='background-color:#ededed;width:100%;line-height:22px;padding-left:5px;border-left:1px solid #d1d1d1'>");
		String sender = inbox.getOaNetmailInboxSender();
		String sendmail = inbox.getOaNetmailSetFrom();
		
		if(sender==null||sender.trim().length()==0){
			sender = sendmail.split("@")[0];
		}
		
		result.append("<b>发件人：</b>\""+sender+"\"<"+sendmail+"><br/>");
		result.append("<b>发送时间：</b>"+inbox.getOaNetmailInboxTime()+"<br/>");
		result.append("<b>主题：</b>"+inbox.getOaNetmailInboxTitle());
		result.append("</label>");
		result.append("<br/><br/>");
		result.append(inbox.getOaNetmailInboxContent());
		sendbox.setOaNetmailSendContent(result.toString());
		return WebUtilWork.WebObjectPack(sendbox);
	}
	
	public ResultBean getNetMailInboxByPager(ServletContext context,HttpServletRequest request,OaNetmailInbox inbox,Pager pager){
		List<OaNetmailInbox> inboxlist = new ArrayList<OaNetmailInbox>();
		inbox.setOaNetmailInboxEmpid(UtilTool.getEmployeeId(request));
		inbox.setCompanyId(UtilTool.getCompanyId(request));
		pager =  PagerHelper.getPager(pager, mailService.getNetMailInboxCount(inbox));
		List<Object[]> objlist = mailService.getNetmailInboxByPager(inbox, pager);
		for (Object[] objs : objlist) {
			OaNetmailInbox box = new OaNetmailInbox();
			box.setPrimaryKey(Long.parseLong(objs[0].toString()));
			box.setOaNetmailInboxSender(objs[1]==null?"":objs[1].toString());
			box.setOaNetmailInboxTime(objs[2]==null?"":objs[2].toString());
			box.setOaNetmailInboxTitle(objs[3]==null?"":objs[3].toString());
			box.setOaNetmailSetFrom(objs[4].toString());
			box.setOaNetmailInboxAffix(objs[5]==null?"":objs[5].toString());
			int t = EnumUtil.OA_SMS_INBOX_ISREAD.two.value;//未读
			box.setOaNetmailIsRead(objs[6]==null?t:Integer.parseInt(objs[6].toString()));
			int l = EnumUtil.OA_CALENDER_LEVEL.four.value;//一般
			box.setOaNetmailUrgent(objs[7]==null?l:Integer.parseInt(objs[7].toString()));
			inboxlist.add(box);
		}
		
		return WebUtilWork.WebResultPack(inboxlist,pager);
	}
	
	public int getNetMailInboxNoRead(ServletContext context,HttpServletRequest request,String setId){
		OaNetmailInbox inbox = new OaNetmailInbox();
		inbox.setOaNetmailInboxEmpid(UtilTool.getEmployeeId(request));
		inbox.setCompanyId(UtilTool.getCompanyId(request));
		inbox.setOaNetmailSetId(setId);
		inbox.setOaNetmailIsRead(EnumUtil.OA_SMS_INBOX_ISREAD.two.value);
		return mailService.getNetMailInboxCount(inbox);
	}
	
	public List<String> getMailNoReadCountToString(ServletContext context,HttpServletRequest request,String setId){
		List<String> tmplist = new ArrayList<String>();
		int count = this.getOaMailNoRead(context, request);
		int netmailcount=0;
		if(setId!=null&&setId.length()>0){
			netmailcount = this.getNetMailInboxNoRead(context, request, setId);
		}
		if (count>0) {
			tmplist.add("<a href='javascript:void(0)' onclick='showMailDetail(1)' style='color:green;float:left;margin-left: 5px' title='点击查看'>内部未读邮件</a><label style='float:right;margin-right: 5px'>["+count+"]</label><br/>");
		}
		if (netmailcount>0) {
			tmplist.add("<a href='javascript:void(0)' onclick='showMailDetail(2)' style='color:green;float:left;margin-left: 5px' title='点击查看'>默认邮箱未读邮件</a><label style='float:right;margin-right: 5px'>["+netmailcount+"]</label><br/>");
		}
		return tmplist;
	}
	
	public boolean getMailAccpIsReady(ServletContext context,HttpServletRequest request,String empId){
		return ConstWords.mailIsAccpMap.get(empId);
	}
	
	public ResultBean updateMailReadStatus(ServletContext context,HttpServletRequest request,long[] pks){
		mailService.updateNetMailReadStatus(pks);
		return WebUtilWork.WebResultPack(null);
	}
	
	public ResultBean getNetmailDetailById(ServletContext context,HttpServletRequest request,long pk){
		return WebUtilWork.WebObjectPack(mailService.getOaNetmailInboxById(pk));
	}
	
	/**
	 * 发送阅读收条
	 * @param context
	 * @param request
	 * @param pk
	 * @param setid
	 * @return
	 */
	public ResultBean sendRepByMailId(ServletContext context,HttpServletRequest request,long pk,long setid,boolean bl) throws Exception{
		if (bl) {
			SessionUser user = (SessionUser) LoginContext.getSessionValueByLogin(request);
			
			OaNetmailSet netset = mailService.getOaNetmailSetByPk(setid);
			
			OaNetmailInbox box = mailService.getOaNetmailInboxById(pk);
			
			OaNetmailSendbox sendbox = new OaNetmailSendbox();
			sendbox.setOaNetmailSendTitle("已读："+box.getOaNetmailInboxTitle());
			sendbox.setOaNetmailSendIsurgent(EnumUtil.OA_CALENDER_LEVEL.four.value);
			sendbox.setOaNetmailReceipt(EnumUtil.OA_MAIL_RECEIPT.TWO.value);
			sendbox.setOaNetmailSendContent("邮件收条<br/><br/>此收条表明收件人的电脑上曾显示过此邮件，显示时间:"+UtilWork.getNowTime());
			sendbox.setOaNetmailSendAdders(box.getOaNetmailSetFrom());
			
			mailService.saveNetmailSend(sendbox, false, netset, user, null);
		}

		//更新回执状态
		mailService.updateNetMailRepSign(pk);
		return WebUtilWork.WebResultPack(null);
	}
	
	/**
	 * 草稿箱删除
	 * @param context
	 * @param request
	 * @param pks
	 * @return
	 */
	public ResultBean deleteMailTempByPks(ServletContext context,HttpServletRequest request,long[] pks){
		for (long l : pks) {
			OaNetmailSendbox tmp = mailService.getOaNetMailSendBoxBypk(l);
			if (tmp.getOaNetmailSendAffix()!=null&&tmp.getOaNetmailSendAffix().trim().length()>0) {
				UtilTool.deleteAttachmentsAndFile(context, request, tmp.getOaNetmailSendAffix());
			}
			mailService.deleteOaNetMailSend(tmp);
		}
		return WebUtilWork.WebResultPack(null);
	}
	
	/**
	 * 发件箱删除
	 * @param context
	 * @param request
	 * @param pks
	 * @return
	 */
	public ResultBean deleteSendboxBySendPks(ServletContext context,HttpServletRequest request,long[] pks){
		for (long l : pks) {
			OaNetmailSendbox tmp = mailService.getOaNetMailSendBoxBypk(l);
			//如果未发送内部人员删除附件记录
			if (tmp.getOaNetmailSendEmpids()==null||tmp.getOaNetmailSendEmpids().trim().length()==0) {
				if (tmp.getOaNetmailSendAffix()!=null&&tmp.getOaNetmailSendAffix().trim().length()>0) {
					UtilTool.deleteAttachmentsAndFile(context, request, tmp.getOaNetmailSendAffix());
				}
			}
			mailService.deleteOaNetMailSend(tmp);
		}
		return WebUtilWork.WebResultPack(null);
	}
	
	/**
	 * 收件箱删除
	 * @param context
	 * @param request
	 * @param pks
	 * @return
	 */
	public ResultBean deleteOaNetmailInboxByPks(ServletContext context,HttpServletRequest request,long[] pks){
		for (long l : pks) {
			OaNetmailInbox box = mailService.getOaNetmailInboxById(l);
			if (box.getOaNetmailInboxAffix()!=null&&box.getOaNetmailInboxAffix().length()>0) {
				UtilTool.deleteAttachmentsAndFile(context, request, box.getOaNetmailInboxAffix());
			}
			mailService.deleteOaNetMailInbox(box);
		}
		return WebUtilWork.WebResultPack(null);
	}
	
	/**
	 * 测试邮件
	 * @param context
	 * @param reqeust
	 * @param set
	 * @return
	 */
	public ResultBean netMailTest(ServletContext context,HttpServletRequest reqeust,OaNetmailSet set){
		return new ResultBean(true,mailService.testNetMail(set));
	}
}
