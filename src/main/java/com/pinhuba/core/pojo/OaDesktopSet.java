package com.pinhuba.core.pojo;

/**
 * 数据库表名：OA_DESKTOP_SET
 */
public class OaDesktopSet extends BaseBean implements java.io.Serializable {

	/**
	 * 桌面设置
	 */
	private static final long serialVersionUID = -3633385249277375817L;
	private String oaDesktopEmpid;   //人员主键
	private Integer oaDesktopType;   //类型
	private Integer oaDesktopIsshow; //是否显示
	private String oaDesktopValue;
	private Integer companyId;
	private String recordId;
	private String recordDate;
	private String lastmodiId;
	private String lastmodiDate;

	// 默认构造方法
	public OaDesktopSet() {
		super();
	}
	
	// get和set方法
	public String getOaDesktopEmpid() {
		return oaDesktopEmpid;
	}

	public void setOaDesktopEmpid(String aOaDesktopEmpid) {
		this.oaDesktopEmpid = aOaDesktopEmpid;
	}

	public Integer getOaDesktopType() {
		return oaDesktopType;
	}

	public void setOaDesktopType(Integer aOaDesktopType) {
		this.oaDesktopType = aOaDesktopType;
	}

	public Integer getOaDesktopIsshow() {
		return oaDesktopIsshow;
	}

	public void setOaDesktopIsshow(Integer aOaDesktopIsshow) {
		this.oaDesktopIsshow = aOaDesktopIsshow;
	}

	public String getOaDesktopValue() {
		return oaDesktopValue;
	}

	public void setOaDesktopValue(String aOaDesktopValue) {
		this.oaDesktopValue = aOaDesktopValue;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer aCompanyId) {
		this.companyId = aCompanyId;
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

}