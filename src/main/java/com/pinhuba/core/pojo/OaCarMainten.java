package com.pinhuba.core.pojo;

/**
 * 数据库表名：OA_CAR_MAINTEN
 */
public class OaCarMainten extends BaseBean implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9106637073398534995L;
	private String oaMaintenCards;
	private String oaMaintenName;
	private String oaMaintenEmp;
	private String oaMaintenDate;
	private Integer oaMaintenType;
	private String oaMaintenCause;
	private Double oaMaintenCharge;
	private String oaMaintenRemark;
	private String recordId;
	private String recordDate;
	private String lastmodiId;
	private String lastmodiDate;
	private Integer companyId;
	private SysLibraryInfo library;

	// 默认构造方法
	public OaCarMainten() {
		super();
	}

	// 构造方法(手工生成)

	// get和set方法
	public String getOaMaintenCards() {
		return oaMaintenCards;
	}

	public void setOaMaintenCards(String aOaMaintenCards) {
		this.oaMaintenCards = aOaMaintenCards;
	}

	public String getOaMaintenName() {
		return oaMaintenName;
	}

	public void setOaMaintenName(String aOaMaintenName) {
		this.oaMaintenName = aOaMaintenName;
	}

	public String getOaMaintenEmp() {
		return oaMaintenEmp;
	}

	public void setOaMaintenEmp(String aOaMaintenEmp) {
		this.oaMaintenEmp = aOaMaintenEmp;
	}

	public String getOaMaintenDate() {
		return oaMaintenDate;
	}

	public void setOaMaintenDate(String aOaMaintenDate) {
		this.oaMaintenDate = aOaMaintenDate;
	}

	public Integer getOaMaintenType() {
		return oaMaintenType;
	}

	public void setOaMaintenType(Integer aOaMaintenType) {
		this.oaMaintenType = aOaMaintenType;
	}

	public String getOaMaintenCause() {
		return oaMaintenCause;
	}

	public void setOaMaintenCause(String aOaMaintenCause) {
		this.oaMaintenCause = aOaMaintenCause;
	}

	public Double getOaMaintenCharge() {
		return oaMaintenCharge;
	}

	public void setOaMaintenCharge(Double aOaMaintenCharge) {
		this.oaMaintenCharge = aOaMaintenCharge;
	}

	public String getOaMaintenRemark() {
		return oaMaintenRemark;
	}

	public void setOaMaintenRemark(String aOaMaintenRemark) {
		this.oaMaintenRemark = aOaMaintenRemark;
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

	public String getLastmodiId() {
		return lastmodiId;
	}

	public void setLastmodiId(String aLastmodiId) {
		this.lastmodiId = aLastmodiId;
	}

	public String getLastmodiDate() {
		return lastmodiDate;
	}

	public void setLastmodiDate(String aLastmodiDate) {
		this.lastmodiDate = aLastmodiDate;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer aCompanyId) {
		this.companyId = aCompanyId;
	}

	public void setLibrary(SysLibraryInfo library) {
		this.library = library;

	}

	public SysLibraryInfo getLibrary() {
		return library;
	}

}