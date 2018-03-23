package com.pinhuba.core.pojo;

/**
 * 数据库表名：SYS_USER_VIEW
 */
public class SysUserView extends BaseBean implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6826306531565118106L;
	private Integer userId;			//用户主键
	private Integer viewIsdown;		//是否向下兼容
	private Integer deptId;			//部门主键
	private Integer partPostIsview;	//是否显示兼容岗位
	private String addDeptView;		//附加显示部门
	private String recordId;
	private String recordDate;
	private String lastmodiId;
	private String lastmodiDate;
	private Integer companyId;

	// 默认构造方法
	public SysUserView() {
		super();
	}

	// get和set方法
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer aUserId) {
		this.userId = aUserId;
	}

	public Integer getViewIsdown() {
		return viewIsdown;
	}

	public void setViewIsdown(Integer aViewIsdown) {
		this.viewIsdown = aViewIsdown;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer aDeptId) {
		this.deptId = aDeptId;
	}

	public Integer getPartPostIsview() {
		return partPostIsview;
	}

	public void setPartPostIsview(Integer aPartPostIsview) {
		this.partPostIsview = aPartPostIsview;
	}

	public String getAddDeptView() {
		return addDeptView;
	}

	public void setAddDeptView(String aAddDeptView) {
		this.addDeptView = aAddDeptView;
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