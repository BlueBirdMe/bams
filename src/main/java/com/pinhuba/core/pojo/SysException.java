package com.pinhuba.core.pojo;

/**
 * 数据库表名：SYS_EXCEPTION
 */
public class SysException extends BaseBean implements java.io.Serializable {
	/**
	 * 系统异常数据表
	 */
	private static final long serialVersionUID = 2921074247509816691L;
	private Integer userId;				//用户编号
	
	private SysUserInfo userInfo;		//用户信息
	
	private Integer companyId;			//公司编码
	
	private SysCompanyInfo companyInfo;	//公司信息
	
	private String exceptionDate;		//异常日期
	private String exceptionMsg;		//异常信息
	private String exceptionClass;		//异常类
	private String exceptionContext;	//异常内容
	private String processName;			//处理人
	private String processDate;			//处理日期
	private String processContext;		//内容
	private Integer exceptionStatus;	//异常状态

	// 默认构造方法
	public SysException() {
		super();
	}

	// get和set方法
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer aUserId) {
		this.userId = aUserId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer aCompanyId) {
		this.companyId = aCompanyId;
	}

	public String getExceptionDate() {
		return exceptionDate;
	}

	public void setExceptionDate(String aExceptionDate) {
		this.exceptionDate = aExceptionDate;
	}

	public String getExceptionMsg() {
		return exceptionMsg;
	}

	public void setExceptionMsg(String aExceptionMsg) {
		this.exceptionMsg = aExceptionMsg;
	}

	public String getExceptionClass() {
		return exceptionClass;
	}

	public void setExceptionClass(String aExceptionClass) {
		this.exceptionClass = aExceptionClass;
	}

	public String getExceptionContext() {
		return exceptionContext;
	}

	public void setExceptionContext(String aExceptionContext) {
		this.exceptionContext = aExceptionContext;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String aProcessName) {
		this.processName = aProcessName;
	}

	public String getProcessDate() {
		return processDate;
	}

	public void setProcessDate(String aProcessDate) {
		this.processDate = aProcessDate;
	}

	public String getProcessContext() {
		return processContext;
	}

	public void setProcessContext(String aProcessContext) {
		this.processContext = aProcessContext;
	}

	public Integer getExceptionStatus() {
		return exceptionStatus;
	}

	public void setExceptionStatus(Integer aExceptionStatus) {
		this.exceptionStatus = aExceptionStatus;
	}

	public SysUserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(SysUserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public SysCompanyInfo getCompanyInfo() {
		return companyInfo;
	}

	public void setCompanyInfo(SysCompanyInfo companyInfo) {
		this.companyInfo = companyInfo;
	}

}