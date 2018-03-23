package com.pinhuba.core.pojo;

/**
 * 数据库表名：OA_WAREHOUSE
 */
public class OaWarehouse extends BaseBean implements java.io.Serializable {

	/**
	 * 知识数据表
	 */
	private static final long serialVersionUID = 4439846180630062843L;
	private String oaWareName;     		  //标题
	private Integer oaWareType;			  //知识类型id
	private OaWareType wareType;		  //知识类型表
	private String oaKeyword;			  //关键字
	private String oaWareEmp;			  //人员ID
	
	private HrmEmployee oaWareEmployee;   //人员表
	
	private String oaWareText;			  //内容
	private String oaWareTime;			  //发布时间
	private String oaWareAcce;			  //访问
	private String recordId;			  //修改人ID
	private String recordDate;			  //修改时间
	private String lastmodiId;			  //最后修改人id
	private String lastmodiDate;		  //最后修改时间
	private Integer companyId;			  //公司ID

	public HrmEmployee getOaWareEmployee() {
		return oaWareEmployee;
	}

	public void setOaWareEmployee(HrmEmployee oaWareEmployee) {
		this.oaWareEmployee = oaWareEmployee;
	}

	public OaWareType getWareType() {
		return wareType;
	}

	public void setWareType(OaWareType wareType) {
		this.wareType = wareType;
	}

	// 默认构造方法
	public OaWarehouse() {
		super();
	}

	// get和set方法
	public String getOaWareName() {
		return oaWareName;
	}

	public void setOaWareName(String aOaWareName) {
		this.oaWareName = aOaWareName;
	}

	public Integer getOaWareType() {
		return oaWareType;
	}

	public void setOaWareType(Integer aOaWareType) {
		this.oaWareType = aOaWareType;
	}

	public String getOaKeyword() {
		return oaKeyword;
	}

	public void setOaKeyword(String aOaKeyword) {
		this.oaKeyword = aOaKeyword;
	}

	public String getOaWareEmp() {
		return oaWareEmp;
	}

	public void setOaWareEmp(String aOaWareEmp) {
		this.oaWareEmp = aOaWareEmp;
	}

	public String getOaWareText() {
		return oaWareText;
	}

	public void setOaWareText(String aOaWareText) {
		this.oaWareText = aOaWareText;
	}

	public String getOaWareTime() {
		return oaWareTime;
	}

	public void setOaWareTime(String aOaWareTime) {
		this.oaWareTime = aOaWareTime;
	}

	public String getOaWareAcce() {
		return oaWareAcce;
	}

	public void setOaWareAcce(String aOaWareAcce) {
		this.oaWareAcce = aOaWareAcce;
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