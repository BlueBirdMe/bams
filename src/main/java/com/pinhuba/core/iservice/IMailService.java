package com.pinhuba.core.iservice;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.pinhuba.common.module.SessionUser;
import com.pinhuba.common.pages.Pager;
import com.pinhuba.core.pojo.HrmEmployee;
import com.pinhuba.core.pojo.OaMailEmp;
import com.pinhuba.core.pojo.OaMailInbox;
import com.pinhuba.core.pojo.OaMailSend;
import com.pinhuba.core.pojo.OaNetmailInbox;
import com.pinhuba.core.pojo.OaNetmailPerson;
import com.pinhuba.core.pojo.OaNetmailSendbox;
import com.pinhuba.core.pojo.OaNetmailSet;
import com.pinhuba.core.pojo.OaNetmailUid;

public interface IMailService {

	int listOaMailInboxCount(OaMailEmp oaMailEmp, OaMailInbox oaMailInbox);

	List<OaMailEmp> getAllOaMailInbox(OaMailEmp oaMailEmp, OaMailInbox oaMailInbox, Pager pager);

	OaMailInbox getOaMailInboxByPk(Integer oaMailEmpInboxid);

	void saveOaMailSend(OaMailSend oaMailSend, String isSaveSendBox);

	int listOaMailSendCount(OaMailSend oaMailSend);

	List<OaMailSend> getAllOaMailSend(OaMailSend oaMailSend, Pager pager);

	OaMailEmp getOaMailEmpByPk(long mailEmpId);

	void saveOaMailEmp(OaMailEmp mailEmp);

	String deleteOaMailSendByPks(long oaMailSendIds);

	OaMailSend getOaMailSendByPk(long oaMailSendId);

	void updateOaMailInboxByPks(long[] oaMailInboxIds);

	String deleteOaMailEmpByPks(long oaMailInboxIds);

	HrmEmployee getEmployeeByPk(String id);

	void setMailRead(long[] ids);
	
	public List<OaNetmailSet> getOaNetMailSetList(String empId,int companyId);
	
	public void saveOaNetMailSet(String empId,int companyId,OaNetmailSet[] sets);
	
	public List<OaNetmailPerson> getOaNetMailPersonListByPager(OaNetmailPerson person,Pager pager);
	
	public int getOaNetMailPersonListByCount(OaNetmailPerson person);
	
	public OaNetmailPerson saveOaNetMailPerson(OaNetmailPerson person);
	
	public void deleteOaNetMailPersonByPks(long[] pks);
	
	public OaNetmailPerson getOaNetMailPersonByPk(long pk);
	
	public OaNetmailSet getOaNetmailSetByPk(long pk);
	
	public OaNetmailSendbox saveOaNetMailSendBoxBySketch(OaNetmailSendbox box);
	
	public int getOaNetMailSendBoxByCount(OaNetmailSendbox box);
	
	public List<OaNetmailSendbox> getOaNetMailSendBoxByPager(OaNetmailSendbox box,Pager pager);
	
	public boolean  saveNetmailSend(OaNetmailSendbox box,boolean issavesend,OaNetmailSet mailSet,SessionUser user,String files) throws Exception;
	
	public int saveNetMailInboxGetServer(ServletContext context,HttpServletRequest request,long[] setIds) throws Exception;

	public int getNetMailInboxCount(OaNetmailInbox inbox);
	
	public List<Object[]> getNetmailInboxByPager(OaNetmailInbox inbox,Pager pager);
	
	public void updateNetMailReadStatus(long[] pks);
	
	public OaNetmailInbox getOaNetmailInboxById(long pk);
	
	public void updateNetMailRepSign(long pk);
	
	public OaNetmailSendbox getOaNetMailSendBoxBypk(long pk);
	
	public void deleteOaNetMailSend(OaNetmailSendbox box);
	
	public void deleteOaNetMailInbox(OaNetmailInbox inbox);
	
	public String testNetMail(OaNetmailSet set);
	
	public List<OaNetmailInbox> getNetmailInboxBySetId(OaNetmailInbox inbox);
	
	public List<OaNetmailSendbox> getNetmailSendboxBySetId(OaNetmailSendbox sendbox);
	
	public void deleteOaNetMailSet(long pk);
	
	public void deleteOanetmailUid(OaNetmailUid uid);
	
	public int getOaNetMailSetCount(String empId,int companyId,int type);
}
