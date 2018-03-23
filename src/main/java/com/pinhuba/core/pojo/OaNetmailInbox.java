package com.pinhuba.core.pojo;

/**
 * 数据库表名：OA_NETMAIL_INBOX
 */
public class OaNetmailInbox extends BaseBean implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5726563615691436909L;
	private String oaNetmailInboxSender;
	private String oaNetmailInboxTime;
	private String oaNetmailInboxTitle;
	private String oaNetmailInboxContent;
	private String oaNetmailSetFrom;
	private String oaNetmailInboxAffix;
	private String oaNetmailInboxOther;
	private String oaNetmailInboxEmpid;
	private String recordId;
	private String recordDate;
	private Integer companyId;
	private Integer oaNetmailIsRead;
	private Integer oaNetmailUrgent;
	private Integer oaNetmailType;
	private String oaNetmailMessageId;
	private String oaNetmailSetId;
	private Integer oaNetmailReplySign;
	
	

	// 默认构造方法
	public OaNetmailInbox() {
		super();
	}

	// 构造方法(手工生成)

	// get和set方法
	
	
	
	public String getOaNetmailInboxSender() {
		return oaNetmailInboxSender;
	}

	public void setOaNetmailInboxSender(String aOaNetmailInboxSender) {
		this.oaNetmailInboxSender = aOaNetmailInboxSender;
	}

	public String getOaNetmailInboxTime() {
		return oaNetmailInboxTime;
	}

	public void setOaNetmailInboxTime(String aOaNetmailInboxTime) {
		this.oaNetmailInboxTime = aOaNetmailInboxTime;
	}

	public String getOaNetmailInboxTitle() {
		return oaNetmailInboxTitle;
	}

	public void setOaNetmailInboxTitle(String aOaNetmailInboxTitle) {
		this.oaNetmailInboxTitle = aOaNetmailInboxTitle;
	}

	public String getOaNetmailInboxContent() {
		return oaNetmailInboxContent;
	}

	public void setOaNetmailInboxContent(String aOaNetmailInboxContent) {
		this.oaNetmailInboxContent = aOaNetmailInboxContent;
	}

	public String getOaNetmailSetFrom() {
		return oaNetmailSetFrom;
	}

	public void setOaNetmailSetFrom(String aOaNetmailSetFrom) {
		this.oaNetmailSetFrom = aOaNetmailSetFrom;
	}

	public String getOaNetmailInboxAffix() {
		return oaNetmailInboxAffix;
	}

	public void setOaNetmailInboxAffix(String aOaNetmailInboxAffix) {
		this.oaNetmailInboxAffix = aOaNetmailInboxAffix;
	}

	public String getOaNetmailInboxOther() {
		return oaNetmailInboxOther;
	}

	public void setOaNetmailInboxOther(String aOaNetmailInboxOther) {
		this.oaNetmailInboxOther = aOaNetmailInboxOther;
	}

	public String getOaNetmailInboxEmpid() {
		return oaNetmailInboxEmpid;
	}

	public void setOaNetmailInboxEmpid(String aOaNetmailInboxEmpid) {
		this.oaNetmailInboxEmpid = aOaNetmailInboxEmpid;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String aRecordId) {
		this.recordId = aRecordId;
	}

	public String getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(String aRecordDate) {
		this.recordDate = aRecordDate;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer aCompanyId) {
		this.companyId = aCompanyId;
	}

	public Integer getOaNetmailIsRead() {
		return oaNetmailIsRead;
	}

	public void setOaNetmailIsRead(Integer oaNetmailIsRead) {
		this.oaNetmailIsRead = oaNetmailIsRead;
	}

	public Integer getOaNetmailUrgent() {
		return oaNetmailUrgent;
	}

	public void setOaNetmailUrgent(Integer oaNetmailUrgent) {
		this.oaNetmailUrgent = oaNetmailUrgent;
	}

	public Integer getOaNetmailType() {
		return oaNetmailType;
	}

	public void setOaNetmailType(Integer oaNetmailType) {
		this.oaNetmailType = oaNetmailType;
	}

	public String getOaNetmailMessageId() {
		return oaNetmailMessageId;
	}

	public void setOaNetmailMessageId(String oaNetmailMessageId) {
		this.oaNetmailMessageId = oaNetmailMessageId;
	}

	public String getOaNetmailSetId() {
		return oaNetmailSetId;
	}

	public void setOaNetmailSetId(String oaNetmailSetId) {
		this.oaNetmailSetId = oaNetmailSetId;
	}

	public Integer getOaNetmailReplySign() {
		return oaNetmailReplySign;
	}

	public void setOaNetmailReplySign(Integer oaNetmailReplySign) {
		this.oaNetmailReplySign = oaNetmailReplySign;
	}
	
}