package com.pinhuba.core.pojo;

/**
 * 数据库表名：OA_NETMAIL_PERSON
 */
public class OaNetmailPerson extends BaseBean implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1800019260986728197L;
	private String oaNetmailEmpname;
	private String oaNetmailEmpmail;
	private String hrmEmployeeId;
	private Integer companyId;
	private String oaNetmailDate;

	// 默认构造方法
	public OaNetmailPerson() {
		super();
	}

	// 构造方法(手工生成)

	// get和set方法
	public String getOaNetmailEmpname() {
		return oaNetmailEmpname;
	}

	public void setOaNetmailEmpname(String aOaNetmailEmpname) {
		this.oaNetmailEmpname = aOaNetmailEmpname;
	}

	public String getOaNetmailEmpmail() {
		return oaNetmailEmpmail;
	}

	public void setOaNetmailEmpmail(String aOaNetmailEmpmail) {
		this.oaNetmailEmpmail = aOaNetmailEmpmail;
	}

	public String getHrmEmployeeId() {
		return hrmEmployeeId;
	}

	public void setHrmEmployeeId(String aHrmEmployeeId) {
		this.hrmEmployeeId = aHrmEmployeeId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer aCompanyId) {
		this.companyId = aCompanyId;
	}

	public String getOaNetmailDate() {
		return oaNetmailDate;
	}

	public void setOaNetmailDate(String oaNetmailDate) {
		this.oaNetmailDate = oaNetmailDate;
	}

}