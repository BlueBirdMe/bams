package com.pinhuba.common.email;


/**
 * 邮件实体类
 * @author peng.ning
 * @date   Jul 14, 2010
 */
public class MailReceiveBean {
	private String mailFromPerson;//发件人
	
	private String mailFrom;//发件人地址

	private String mailTOAdder;//邮件收件人
	
	private String mailCCAdder;//邮件抄送人
	
	private String mailBCCAdder;//邮件密送人
	
	private String mailSubject;//主题
	
	private String sentDate;//发送日期
	
	private String bodyText;//邮件正文
	
	private Integer replySign;//回执 1、要求回执 2、不用回执
	
	private Integer urgent;//优先级 1、紧急 3、普通 5、缓慢 
	
	private String messageId;//邮件id
	
	private String mailUid;//uid
	
	private Integer isRead;// 1 已读 2未读
	
	private String attachMent;//附件

	public String getMailFromPerson() {
		return mailFromPerson;
	}

	public void setMailFromPerson(String mailFromPerson) {
		this.mailFromPerson = mailFromPerson;
	}

	public String getMailFrom() {
		return mailFrom;
	}

	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}

	public String getMailTOAdder() {
		return mailTOAdder;
	}

	public void setMailTOAdder(String mailTOAdder) {
		this.mailTOAdder = mailTOAdder;
	}

	public String getMailCCAdder() {
		return mailCCAdder;
	}

	public void setMailCCAdder(String mailCCAdder) {
		this.mailCCAdder = mailCCAdder;
	}

	public String getMailBCCAdder() {
		return mailBCCAdder;
	}

	public void setMailBCCAdder(String mailBCCAdder) {
		this.mailBCCAdder = mailBCCAdder;
	}

	public String getMailSubject() {
		return mailSubject;
	}

	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}

	public String getSentDate() {
		return sentDate;
	}

	public void setSentDate(String sentDate) {
		this.sentDate = sentDate;
	}

	public String getBodyText() {
		return bodyText;
	}

	public void setBodyText(String bodyText) {
		this.bodyText = bodyText;
	}

	public Integer getReplySign() {
		return replySign;
	}

	public void setReplySign(Integer replySign) {
		this.replySign = replySign;
	}

	public Integer getUrgent() {
		return urgent;
	}

	public void setUrgent(Integer urgent) {
		this.urgent = urgent;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getMailUid() {
		return mailUid;
	}

	public void setMailUid(String mailUid) {
		this.mailUid = mailUid;
	}

	public Integer getIsRead() {
		return isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

	public String getAttachMent() {
		return attachMent;
	}

	public void setAttachMent(String attachMent) {
		this.attachMent = attachMent;
	}
	
	
}
