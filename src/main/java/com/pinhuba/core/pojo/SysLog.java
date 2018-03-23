package com.pinhuba.core.pojo;

/**
 * 数据库表名：SYS_LOG
 */
public class SysLog extends BaseBean implements java.io.Serializable {

	/**
	 * 系统日志表
	 */
	private static final long serialVersionUID = -4794431000689932373L;
	private Integer userId;			//用户主键
	private String logDate;			//操作时间
	private String logDetail;		//日志详细
	private Integer companyId;		//所属公司
	private String requestAddr;		//IP地址
	
	private SysUserInfo sysUserInfo; 
    private HrmEmployee hrmEmployee; 

	public String getRequestAddr() {
		return requestAddr;
	}

	public void setRequestAddr(String requestAddr) {
		this.requestAddr = requestAddr;
	}

	// 默认构造方法
	public SysLog() {
		super();
	}

	// get和set方法
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer aUserId) {
		this.userId = aUserId;
	}

	public String getLogDate() {
		return logDate;
	}

	public void setLogDate(String aLogDate) {
		this.logDate = aLogDate;
	}

	public String getLogDetail() {
		return logDetail;
	}

	public void setLogDetail(String aLogDetail) {
		this.logDetail = aLogDetail;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer aCompanyId) {
		this.companyId = aCompanyId;
	}

	public SysUserInfo getSysUserInfo() {
		return sysUserInfo;
	}

	public void setSysUserInfo(SysUserInfo sysUserInfo) {
		this.sysUserInfo = sysUserInfo;
	}

	public HrmEmployee getHrmEmployee() {
		return hrmEmployee;
	}

	public void setHrmEmployee(HrmEmployee hrmEmployee) {
		this.hrmEmployee = hrmEmployee;
	}

	

}