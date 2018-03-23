package com.pinhuba.core.pojo;

/**
 * 数据库表名：SYS_USER_INFO
 */
public class SysUserInfo extends BaseBean implements java.io.Serializable {

	/**
	 * 用户信息表
	 */
	private static final long serialVersionUID = 6892756052328913578L;
	private String hrmEmployeeId;	//人员ID
	
	private HrmEmployee employee;		
	
	private String userName;		//登录名
	private String userpassword;	//登录密码
	private Integer userAction;		//是否有效
	private String recordId;
	private String recordDate;
	private String lastmodiId;
	private String lastmodiDate;
	private Integer companyId;
	private Integer userType;//用户类型 1普通用户 2系统管理-测试用户 3系统管理-系统管理员账户
	
	
	//临时流程用户组名称
	private String  processGroup;
	
	public HrmEmployee getEmployee() {
		return employee;
	}

	public void setEmployee(HrmEmployee employee) {
		this.employee = employee;
	}

	// 默认构造方法
	public SysUserInfo() {
		super();
	}

	// get和set方法
	public String getHrmEmployeeId() {
		return hrmEmployeeId;
	}

	public void setHrmEmployeeId(String aHrmEmployeeId) {
		this.hrmEmployeeId = aHrmEmployeeId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String aUserName) {
		this.userName = aUserName;
	}

	public String getUserpassword() {
		return userpassword;
	}

	public void setUserpassword(String aUserpassword) {
		this.userpassword = aUserpassword;
	}

	public Integer getUserAction() {
		return userAction;
	}

	public void setUserAction(Integer aUserAction) {
		this.userAction = aUserAction;
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

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public String getProcessGroup() {
		return processGroup;
	}

	public void setProcessGroup(String processGroup) {
		this.processGroup = processGroup;
	}
}