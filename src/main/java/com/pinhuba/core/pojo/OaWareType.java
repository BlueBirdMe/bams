package com.pinhuba.core.pojo;

/**
 * 数据库表名：OA_WARE_TYPE
 */
public class OaWareType extends BaseBean implements java.io.Serializable {

	/**
	 * 表格类型表
	 */
	private static final long serialVersionUID = -5937709804650133229L;
	private String oaTypeName;     //类型名称
	private String oaTypeText;		//类型描述
	private String recordId;		//修改人ID
	private String recordDate;		//修改人时间
	private String lastmodiId;		//最后修改人ID
	private String lastmodiDate;	//最后修改人时间
	private Integer companyId;		//公司ID
	private Integer formsorware;	//标示是表格类型 还是知识类型
	private Integer premCount;// 可查看人员限制数量

	// 默认构造方法
	public OaWareType() {
		super();
	}

	// get和set方法
	public String getOaTypeName() {
		return oaTypeName;
	}

	public void setOaTypeName(String aOaTypeName) {
		this.oaTypeName = aOaTypeName;
	}

	public String getOaTypeText() {
		return oaTypeText;
	}

	public void setOaTypeText(String aOaTypeText) {
		this.oaTypeText = aOaTypeText;
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

	public Integer getFormsorware() {
		return formsorware;
	}

	public void setFormsorware(Integer aFormsorware) {
		this.formsorware = aFormsorware;
	}

	public Integer getPremCount() {
		return premCount;
	}

	public void setPremCount(Integer premCount) {
		this.premCount = premCount;
	}

}