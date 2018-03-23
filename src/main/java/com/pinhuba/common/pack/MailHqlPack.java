package com.pinhuba.common.pack;

import com.pinhuba.core.pojo.OaMailEmp;
import com.pinhuba.core.pojo.OaMailInbox;
import com.pinhuba.core.pojo.OaMailSend;
import com.pinhuba.core.pojo.OaNetmailInbox;
import com.pinhuba.core.pojo.OaNetmailPerson;
import com.pinhuba.core.pojo.OaNetmailSendbox;

public class MailHqlPack {

	/**
	 * 内部邮件——查看收件箱
	 */
	public static String getOaMailInboxSql(OaMailEmp oaMailEmp, OaMailInbox oaMailInbox) {
		StringBuffer result = new StringBuffer();		
        HqlPack.getStringEqualPack(oaMailEmp.getOaMailEmpEmpid(),"oaMailEmpEmpid", result);
        HqlPack.getNumEqualPack(oaMailEmp.getOaMailEmpStatus(), "oaMailEmpStatus", result);
        HqlPack.getNumEqualPack(oaMailInbox.getOaMailInboxIsurgent(), "oaMailEmpInboxid.oaMailInboxIsurgent", result);
        HqlPack.timeBuilder(oaMailInbox.getOaMailInboxIntime(),"oaMailEmpInboxid.oaMailInboxIntime",result,false,true);
        HqlPack.getNumEqualPack(oaMailEmp.getOaMailEmpIsread(), "oaMailEmpIsread", result);
        HqlPack.getStringLikerPack(oaMailInbox.getOaMailInboxContent(),"oaMailEmpInboxid.oaMailInboxContent", result);
        HqlPack.getStringLikerPack(oaMailInbox.getOaMailInboxSendName(),"oaMailEmpInboxid.oaMailInboxSendName", result);
        HqlPack.getStringLikerPack(oaMailInbox.getOaMailInboxTitle(),"oaMailEmpInboxid.oaMailInboxTitle", result);
        HqlPack.getNumEqualPack(oaMailEmp.getCompanyId(), "companyId", result);
		return result.toString();
	}
	
	/**
	 * 内部邮件——查看发件箱
	 * @param oaMailSend
	 * @return
	 */
	public static String getOaMailSendSql(OaMailSend oaMailSend) {
		StringBuffer result = new StringBuffer();	
        HqlPack.getNumEqualPack(oaMailSend.getOaMailSendIsurgent(), "oaMailSendIsurgent", result);
		HqlPack.getStringEqualPack(oaMailSend.getOaMailSendSenderid(),"oaMailSendSenderid", result);
        HqlPack.getStringLikerPack(oaMailSend.getOaMailSendEmpNames(),"oaMailSendEmpNames", result);
        HqlPack.getStringLikerPack(oaMailSend.getOaMailSendTitle(),"oaMailSendTitle", result);
        HqlPack.getNumEqualPack(oaMailSend.getOaMailSendType(), "oaMailSendType", result);
        HqlPack.timeBuilder(oaMailSend.getOaMailSendTime(),"oaMailSendTime",result,false,true);
        HqlPack.getNumEqualPack(oaMailSend.getCompanyId(), "companyId", result);
		return result.toString();
	}

	
	public static String getOaNetMailPersonSql(OaNetmailPerson person){
		StringBuffer result = new StringBuffer();
		HqlPack.getStringLikerPack(person.getOaNetmailEmpname(), "oaNetmailEmpname", result);
		HqlPack.getStringLikerPack(person.getOaNetmailEmpmail(), "oaNetmailEmpmail", result);
		HqlPack.getStringEqualPack(person.getHrmEmployeeId(), "hrmEmployeeId", result);
		HqlPack.getNumEqualPack(person.getCompanyId(), "companyId", result);
		HqlPack.timeBuilder(person.getOaNetmailDate(), "oaNetmailDate", result, false, true);
		return result.toString();
	}
	
	public static String getOaNetMailSendBoxSql(OaNetmailSendbox box){
		StringBuffer result = new StringBuffer();
		HqlPack.getNumEqualPack(box.getOaNetmailSendIsurgent(), "oaNetmailSendIsurgent", result);
		HqlPack.getStringLikerPack(box.getOaNetmailSendTitle(), "oaNetmailSendTitle", result);
		HqlPack.getStringEqualPack(box.getOaNetmailSendEmpid(), "oaNetmailSendEmpid", result);
		HqlPack.getNumEqualPack(box.getCompanyId(), "companyId", result);
		HqlPack.timeBuilder(box.getOaNetmailSendTime(), "oaNetmailSendTime", result, false, true);
		HqlPack.getNumEqualPack(box.getOaNetmailSendType(), "oaNetmailSendType", result);
		HqlPack.getStringLikerPack(box.getOaNetmailSetFrom(), "oaNetmailSetFrom", result);
		return result.toString();
	}
	
	public static String getOaNetMailInboxSql(OaNetmailInbox inbox){
		StringBuffer result = new StringBuffer();
		result.append("select oa_netmail_inbox_id,oa_netmail_inbox_sender,oa_netmail_inbox_time,oa_netmail_inbox_title,oa_netmail_set_from,oa_netmail_inbox_affix,oa_netmail_isread,oa_netmail_urgent from oa_netmail_inbox where 1=1 ");
		SqlPack.getStringLikerPack(inbox.getOaNetmailSetId(), "oa_netmail_setid", result);
		SqlPack.getStringLikerPack(inbox.getOaNetmailInboxSender(), "oa_netmail_inbox_sender", result);
		SqlPack.getStringLikerPack(inbox.getOaNetmailInboxTitle(), "oa_netmail_inbox_title", result);
		SqlPack.getNumEqualPack(inbox.getOaNetmailIsRead(), "oa_netmail_isread", result);
		SqlPack.getNumEqualPack(inbox.getOaNetmailUrgent(), "oa_netmail_urgent", result);
		SqlPack.getStringEqualPack(inbox.getOaNetmailInboxEmpid(), "oa_netmail_inbox_empid", result);
		SqlPack.getNumEqualPack(inbox.getCompanyId(), "company_id", result);
		SqlPack.timeBuilder(inbox.getOaNetmailInboxTime(), "oa_netmail_inbox_time", result, false, true);
		return result.toString();
	}
}
