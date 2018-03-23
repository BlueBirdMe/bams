package com.pinhuba.core.pojo;

/**
 * 数据库表名：收件箱和人对应表
 */
public class OaMailEmp extends BaseBean implements java.io.Serializable {

	private static final long serialVersionUID = -3358056272577184834L;
	private OaMailInbox oaMailEmpInboxid; // 收件箱id
	private String oaMailEmpEmpid; // 收件人id
	private String oaMailEmpEmpname; // 收件人姓名
	private Integer oaMailEmpType; // 该字段暂未用到
	private Integer oaMailEmpIsread; // 1 已读 2 未读
	private Integer oaMailEmpStatus; // 1收件 2删除
	private String recordId;
	private String recordDate;
	private Integer companyId;

	// 默认构造方法
	public OaMailEmp() {
		super();
	}

	// get和set方法
	public String getOaMailEmpEmpid() {
		return oaMailEmpEmpid;
	}

	public void setOaMailEmpEmpid(String aOaMailEmpEmpid) {
		this.oaMailEmpEmpid = aOaMailEmpEmpid;
	}

	public String getOaMailEmpEmpname() {
		return oaMailEmpEmpname;
	}

	public void setOaMailEmpEmpname(String aOaMailEmpEmpname) {
		this.oaMailEmpEmpname = aOaMailEmpEmpname;
	}

	public Integer getOaMailEmpType() {
		return oaMailEmpType;
	}

	public void setOaMailEmpType(Integer aOaMailEmpType) {
		this.oaMailEmpType = aOaMailEmpType;
	}

	public Integer getOaMailEmpIsread() {
		return oaMailEmpIsread;
	}

	public void setOaMailEmpIsread(Integer aOaMailEmpIsread) {
		this.oaMailEmpIsread = aOaMailEmpIsread;
	}

	public Integer getOaMailEmpStatus() {
		return oaMailEmpStatus;
	}

	public void setOaMailEmpStatus(Integer aOaMailEmpStatus) {
		this.oaMailEmpStatus = aOaMailEmpStatus;
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
	 * @return the oaMailEmpInboxid
	 */
	public OaMailInbox getOaMailEmpInboxid() {
		return oaMailEmpInboxid;
	}

	/**
	 * @param oaMailEmpInboxid
	 *            the oaMailEmpInboxid to set
	 */
	public void setOaMailEmpInboxid(OaMailInbox oaMailEmpInboxid) {
		this.oaMailEmpInboxid = oaMailEmpInboxid;
	}

}