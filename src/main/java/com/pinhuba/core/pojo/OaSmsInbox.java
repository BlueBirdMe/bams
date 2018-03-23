package com.pinhuba.core.pojo;

/**
 * 数据库表名：OA_SMS_INBOX 
 */
public class OaSmsInbox extends BaseBean implements java.io.Serializable {

	/**
	 * 收件箱（短信）
	 */
	private static final long serialVersionUID = -4108282153753768711L;
	private String oaSmsInboxSenderid; // 发件人id
	private String oaSmsInboxSenderName; // 发件人姓名
	private String oaSmsInboxSendtime; // 发件时间
	private String oaSmsInboxContent; // 发件内容
	private String oaSmsInboxEmp; // 收件人id
	private Integer oaSmsInboxIsread; // 1 已读 2 未读
	private Integer oaSmsType; // 短信类型
	private String recordId;
	private String recordDate;
	private Integer companyId;

	// 默认构造方法
	public OaSmsInbox() {
		super();
	}

	// get和set方法
	public String getOaSmsInboxSenderid() {
		return oaSmsInboxSenderid;
	}

	public void setOaSmsInboxSenderid(String aOaSmsInboxSenderid) {
		this.oaSmsInboxSenderid = aOaSmsInboxSenderid;
	}

	public String getOaSmsInboxSendtime() {
		return oaSmsInboxSendtime;
	}

	public void setOaSmsInboxSendtime(String aOaSmsInboxSendtime) {
		this.oaSmsInboxSendtime = aOaSmsInboxSendtime;
	}

	public String getOaSmsInboxContent() {
		return oaSmsInboxContent;
	}

	public void setOaSmsInboxContent(String aOaSmsInboxContent) {
		this.oaSmsInboxContent = aOaSmsInboxContent;
	}

	public String getOaSmsInboxEmp() {
		return oaSmsInboxEmp;
	}

	public void setOaSmsInboxEmp(String aOaSmsInboxEmp) {
		this.oaSmsInboxEmp = aOaSmsInboxEmp;
	}

	public Integer getOaSmsInboxIsread() {
		return oaSmsInboxIsread;
	}

	public void setOaSmsInboxIsread(Integer aOaSmsInboxIsread) {
		this.oaSmsInboxIsread = aOaSmsInboxIsread;
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

	/**
	 * @return the oaSmsType
	 */
	public Integer getOaSmsType() {
		return oaSmsType;
	}

	/**
	 * @param oaSmsType
	 *            the oaSmsType to set
	 */
	public void setOaSmsType(Integer oaSmsType) {
		this.oaSmsType = oaSmsType;
	}

	/**
	 * @return the oaSmsInboxSenderName
	 */
	public String getOaSmsInboxSenderName() {
		return oaSmsInboxSenderName;
	}

	/**
	 * @param oaSmsInboxSenderName
	 *            the oaSmsInboxSenderName to set
	 */
	public void setOaSmsInboxSenderName(String oaSmsInboxSenderName) {
		this.oaSmsInboxSenderName = oaSmsInboxSenderName;
	}

}