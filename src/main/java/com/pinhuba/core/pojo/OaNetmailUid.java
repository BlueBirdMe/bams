package com.pinhuba.core.pojo;

/**
 * 数据库表名：OA_NETMAIL_UID
 */
public class OaNetmailUid extends BaseBean implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8173649003065470485L;
	private String oaMailUid;
	private String oaMailSetId;
	private String oaMailTime;
	private String oaMailEmpId;
	private Integer companyId;

	// 默认构造方法
	public OaNetmailUid() {
		super();
	}

	// 构造方法(手工生成)

	// get和set方法
	
	
	public String getOaMailUid() {
		return oaMailUid;
	}

	public void setOaMailUid(String aOaMailUid) {
		this.oaMailUid = aOaMailUid;
	}

	public String getOaMailSetId() {
		return oaMailSetId;
	}

	public void setOaMailSetId(String aOaMailSetId) {
		this.oaMailSetId = aOaMailSetId;
	}

	public String getOaMailTime() {
		return oaMailTime;
	}

	public void setOaMailTime(String aOaMailTime) {
		this.oaMailTime = aOaMailTime;
	}

	public String getOaMailEmpId() {
		return oaMailEmpId;
	}

	public void setOaMailEmpId(String oaMailEmpId) {
		this.oaMailEmpId = oaMailEmpId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

}