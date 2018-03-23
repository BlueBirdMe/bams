package com.pinhuba.core.pojo;

/**
 * 数据库表名：HRM_DEPARTMENT(组织机构)
 */
public class HrmDepartment extends BaseBean implements java.io.Serializable {

	private static final long serialVersionUID = -3038017052710840411L;
	private String hrmDepId;                 //部门编号
	private String hrmDepCode;               //部门显示编号
	private String hrmDepName;               //部门名称
	private String hrmDepEngname;            //部门英文名称
	private String hrmDepUpid;               //上级部门
	private String hrmEmpId;                 //部门经理
	private String hrmDepDesc;               //部门描述
	private String recordId;                 //记录人
	private String recordDate;               //记录时间
	private String lastmodiId;               //最后修改人
	private String lastmodiDate;             //最后修改时间
	private Integer companyId;               //公司ID
	private Integer hrmDepShowRow;			//显示顺序	
	
	//临时使用
	private HrmDepartment parentDepartment;  //上级部门对象
	private HrmEmployee employee;            //部门经理对象
	
	public HrmEmployee getEmployee() {
		return employee;
	}

	public void setEmployee(HrmEmployee employee) {
		this.employee = employee;
	}

	// 默认构造方法
	public HrmDepartment() {
		super();
	}

	// get和set方法
	
	
	
	public String getHrmDepId() {
		return hrmDepId;
	}

	public void setHrmDepId(String aHrmDepId) {
		this.hrmDepId = aHrmDepId;
	}

	public String getHrmDepCode() {
		return hrmDepCode;
	}

	public void setHrmDepCode(String aHrmDepCode) {
		this.hrmDepCode = aHrmDepCode;
	}

	public String getHrmDepName() {
		return hrmDepName;
	}

	public void setHrmDepName(String aHrmDepName) {
		this.hrmDepName = aHrmDepName;
	}

	public String getHrmDepEngname() {
		return hrmDepEngname;
	}

	public void setHrmDepEngname(String aHrmDepEngname) {
		this.hrmDepEngname = aHrmDepEngname;
	}

	public String getHrmDepUpid() {
		return hrmDepUpid;
	}

	public void setHrmDepUpid(String aHrmDepUpid) {
		this.hrmDepUpid = aHrmDepUpid;
	}

	public String getHrmEmpId() {
		return hrmEmpId;
	}

	public void setHrmEmpId(String aHrmEmpId) {
		this.hrmEmpId = aHrmEmpId;
	}

	public String getHrmDepDesc() {
		return hrmDepDesc;
	}

	public void setHrmDepDesc(String aHrmDepDesc) {
		this.hrmDepDesc = aHrmDepDesc;
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

	public HrmDepartment getParentDepartment() {
		return parentDepartment;
	}

	public void setParentDepartment(HrmDepartment parentDepartment) {
		this.parentDepartment = parentDepartment;
	}

	public Integer getHrmDepShowRow() {
		return hrmDepShowRow;
	}

	public void setHrmDepShowRow(Integer hrmDepShowRow) {
		this.hrmDepShowRow = hrmDepShowRow;
	}

}