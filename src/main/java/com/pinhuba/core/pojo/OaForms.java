package com.pinhuba.core.pojo;

/**
 * 数据库表名：OA_FORMS
 */
public class OaForms extends BaseBean implements java.io.Serializable {

	/**
	 * 电子表格
	 */
	private static final long serialVersionUID = -1375438439905272153L;
	private String oaFormName; // 表格名称
	private Integer oaFormType; // 表格类型ID
	private OaWareType wareType; // 类型表
	private String oaFormText; // 表格描述
	private String oaFormEmp; // 人员ID
	private HrmEmployee formEmployee;// 人员表
	private String oaFormTime; // 发布时间
	private String oaFormAcce; // 访问
	private String recordId; // 修改人
	private String recordDate; // 修改时间
	private String lastmodiId; // 最后修改人ID
	private String lastmodiDate; // 最后修改人时间
	private Integer companyId; // 公司ID

	// 默认构造方法
	public OaForms() {
		super();
	}

	// get和set方法
	public String getOaFormName() {
		return oaFormName;
	}

	public void setOaFormName(String aOaFormName) {
		this.oaFormName = aOaFormName;
	}

	public Integer getOaFormType() {
		return oaFormType;
	}

	public void setOaFormType(Integer aOaFormType) {
		this.oaFormType = aOaFormType;
	}

	public String getOaFormText() {
		return oaFormText;
	}

	public void setOaFormText(String aOaFormText) {
		this.oaFormText = aOaFormText;
	}

	public String getOaFormEmp() {
		return oaFormEmp;
	}

	public void setOaFormEmp(String aOaFormEmp) {
		this.oaFormEmp = aOaFormEmp;
	}

	public String getOaFormTime() {
		return oaFormTime;
	}

	public void setOaFormTime(String aOaFormTime) {
		this.oaFormTime = aOaFormTime;
	}

	public String getOaFormAcce() {
		return oaFormAcce;
	}

	public void setOaFormAcce(String aOaFormAcce) {
		this.oaFormAcce = aOaFormAcce;
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

	public OaWareType getWareType() {
		return wareType;
	}

	public void setWareType(OaWareType wareType) {
		this.wareType = wareType;
	}

	public HrmEmployee getFormEmployee() {
		return formEmployee;
	}

	public void setFormEmployee(HrmEmployee formEmployee) {
		this.formEmployee = formEmployee;
	}

}