package com.pinhuba.core.pojo;

/**
 * 数据库表名：SYS_PARAM
 */
public class SysParam extends BaseBean implements java.io.Serializable {

	
	/**
	 * 系统参数表
	 */
	private static final long serialVersionUID = 7722359034774900967L;
	private String paramIndex;  //参数索引
	private String paramTitle;  //参数标题
	private String paramValue;  //参数值
	private String paramRemark; //参数备注
	
	private Integer paramType;  //参数类型
	private String paramTypeValue;  //参数类型值
	
	private String recordId;
	private String recordDate;
	private String lastmodiId;
	private String lastmodiDate;
	private Integer companyId;

	
	
	public Integer getParamType() {
		return paramType;
	}

	public void setParamType(Integer paramType) {
		this.paramType = paramType;
	}

	public String getParamTypeValue() {
		return paramTypeValue;
	}

	public void setParamTypeValue(String paramTypeValue) {
		this.paramTypeValue = paramTypeValue;
	}

	// 默认构造方法
	public SysParam() {
		super();
	}

	// get和set方法
	public String getParamIndex() {
		return paramIndex;
	}

	public void setParamIndex(String aParamIndex) {
		this.paramIndex = aParamIndex;
	}

	public String getParamTitle() {
		return paramTitle;
	}

	public void setParamTitle(String aParamTitle) {
		this.paramTitle = aParamTitle;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String aParamValue) {
		this.paramValue = aParamValue;
	}

	public String getParamRemark() {
		return paramRemark;
	}

	public void setParamRemark(String aParamRemark) {
		this.paramRemark = aParamRemark;
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

}