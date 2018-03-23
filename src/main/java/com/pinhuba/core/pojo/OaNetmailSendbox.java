package com.pinhuba.core.pojo;

/**
 * 数据库表名：OA_NETMAIL_SENDBOX
 */
public class OaNetmailSendbox extends BaseBean implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3605388576334338058L;
	private String oaNetmailSetFrom;
	private String oaNetmailSendTime;
	private String oaNetmailSendAdders;
	private String oaNetmailSendEmpids;
	private Integer oaNetmailSendIsurgent;
	private String oaNetmailSendTitle;
	private String oaNetmailSendContent;
	private String oaNetmailSendAffix;
	private Integer oaNetmailSendType;
	private String recordId;
	private String recordDate;
	private Integer companyId;
	private String oaNetmailSendEmpid;
	private Integer oaNetmailReceipt;//阅读回执
	
	//临时使用
	private String oaNetmailSendEmpNames;
	
	

	public Integer getOaNetmailReceipt() {
		return oaNetmailReceipt;
	}

	public void setOaNetmailReceipt(Integer oaNetmailReceipt) {
		this.oaNetmailReceipt = oaNetmailReceipt;
	}

	public String getOaNetmailSendEmpNames() {
		return oaNetmailSendEmpNames;
	}

	public void setOaNetmailSendEmpNames(String oaNetmailSendEmpNames) {
		this.oaNetmailSendEmpNames = oaNetmailSendEmpNames;
	}

	// 默认构造方法
	public OaNetmailSendbox() {
		super();
	}

	// 构造方法(手工生成)

	// get和set方法
	public String getOaNetmailSetFrom() {
		return oaNetmailSetFrom;
	}

	public void setOaNetmailSetFrom(String aOaNetmailSetFrom) {
		this.oaNetmailSetFrom = aOaNetmailSetFrom;
	}

	public String getOaNetmailSendTime() {
		return oaNetmailSendTime;
	}

	public void setOaNetmailSendTime(String aOaNetmailSendTime) {
		this.oaNetmailSendTime = aOaNetmailSendTime;
	}

	public String getOaNetmailSendAdders() {
		return oaNetmailSendAdders;
	}

	public void setOaNetmailSendAdders(String aOaNetmailSendAdders) {
		this.oaNetmailSendAdders = aOaNetmailSendAdders;
	}

	public String getOaNetmailSendEmpids() {
		return oaNetmailSendEmpids;
	}

	public void setOaNetmailSendEmpids(String aOaNetmailSendEmpids) {
		this.oaNetmailSendEmpids = aOaNetmailSendEmpids;
	}

	public Integer getOaNetmailSendIsurgent() {
		return oaNetmailSendIsurgent;
	}

	public void setOaNetmailSendIsurgent(Integer aOaNetmailSendIsurgent) {
		this.oaNetmailSendIsurgent = aOaNetmailSendIsurgent;
	}

	public String getOaNetmailSendTitle() {
		return oaNetmailSendTitle;
	}

	public void setOaNetmailSendTitle(String aOaNetmailSendTitle) {
		this.oaNetmailSendTitle = aOaNetmailSendTitle;
	}

	public String getOaNetmailSendContent() {
		return oaNetmailSendContent;
	}

	public void setOaNetmailSendContent(String aOaNetmailSendContent) {
		this.oaNetmailSendContent = aOaNetmailSendContent;
	}

	public String getOaNetmailSendAffix() {
		return oaNetmailSendAffix;
	}

	public void setOaNetmailSendAffix(String aOaNetmailSendAffix) {
		this.oaNetmailSendAffix = aOaNetmailSendAffix;
	}

	public Integer getOaNetmailSendType() {
		return oaNetmailSendType;
	}

	public void setOaNetmailSendType(Integer aOaNetmailSendType) {
		this.oaNetmailSendType = aOaNetmailSendType;
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

	public String getOaNetmailSendEmpid() {
		return oaNetmailSendEmpid;
	}

	public void setOaNetmailSendEmpid(String aOaNetmailSendEmpid) {
		this.oaNetmailSendEmpid = aOaNetmailSendEmpid;
	}

}