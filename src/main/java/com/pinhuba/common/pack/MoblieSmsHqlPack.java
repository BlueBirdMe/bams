package com.pinhuba.common.pack;

import com.pinhuba.core.pojo.OaSmsInbox;
import com.pinhuba.core.pojo.OaSmsSend;

public class MoblieSmsHqlPack {

	/**
	 * 内部短信——发件箱
	 * @param oaSmsSend
	 * @return
	 */
	public static String getOaSmsSendSql(OaSmsSend oaSmsSend) {
        StringBuffer result = new StringBuffer();		
        HqlPack.getStringEqualPack(oaSmsSend.getOaSmsSendEmp(),"oaSmsSendEmp", result);
        HqlPack.timeBuilder(oaSmsSend.getOaSmsSendTime(),"oaSmsSendTime",result,false,true);
        HqlPack.getNumEqualPack(oaSmsSend.getOaSmsType(), "oaSmsType", result);
        HqlPack.getStringLikerPack(oaSmsSend.getOaSmsSendAcpempName(),"oaSmsSendAcpempName", result);
        HqlPack.getStringLikerPack(oaSmsSend.getOaSmsSendContent(),"oaSmsSendContent", result);
        HqlPack.getNumEqualPack(oaSmsSend.getCompanyId(), "companyId", result);
		return result.toString();
	}
	
	/**
	 * 内部短信-收件箱
	 */
	public static String getoaSmsInboxSql(OaSmsInbox oaSmsInbox) {
		StringBuffer result = new StringBuffer();		
        HqlPack.getStringEqualPack(oaSmsInbox.getOaSmsInboxEmp(),"oaSmsInboxEmp", result);
        HqlPack.timeBuilder(oaSmsInbox.getOaSmsInboxSendtime(),"oaSmsInboxSendtime",result,false,true);
        HqlPack.getNumEqualPack(oaSmsInbox.getOaSmsType(), "oaSmsType", result);
        HqlPack.getStringLikerPack(oaSmsInbox.getOaSmsInboxContent(),"oaSmsInboxContent", result);
        HqlPack.getStringLikerPack(oaSmsInbox.getOaSmsInboxSenderName(),"oaSmsInboxSenderName", result);
        HqlPack.getNumEqualPack(oaSmsInbox.getOaSmsInboxIsread(), "oaSmsInboxIsread", result);
        HqlPack.getNumEqualPack(oaSmsInbox.getCompanyId(), "companyId", result);
		return result.toString();
	}
}
