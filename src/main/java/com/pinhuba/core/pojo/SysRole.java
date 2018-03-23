package com.pinhuba.core.pojo;

/**
 * 数据库表名：SYS_ROLE
 */
public class SysRole extends BaseBean implements java.io.Serializable {

	/**
	 * 角色表
	 */
	private static final long serialVersionUID = -6254786187552556060L;
	private String roleName;	//角色名称
	private String roleDesc;	//角色描述
	private String recordId;	//修改人
	private String recordDate;	//修改时间
	private String lastmodiId;	//最后修改人
	private String lastmodiDate;//最后修改时间
	private Integer companyId;	//公司
	
	//临时存放变量
	private Integer detailCount;
	private Integer bindUserCount;
	private Integer bindGroupCount;
	private Integer bindDeptCount;
	private Integer bindPostCount;

	public Integer getDetailCount() {
		return detailCount;
	}

	public void setDetailCount(Integer detailCount) {
		this.detailCount = detailCount;
	}

	public Integer getBindUserCount() {
		return bindUserCount;
	}

	public void setBindUserCount(Integer bindUserCount) {
		this.bindUserCount = bindUserCount;
	}

	public Integer getBindGroupCount() {
		return bindGroupCount;
	}

	public void setBindGroupCount(Integer bindGroupCount) {
		this.bindGroupCount = bindGroupCount;
	}

	public Integer getBindDeptCount() {
		return bindDeptCount;
	}

	public void setBindDeptCount(Integer bindDeptCount) {
		this.bindDeptCount = bindDeptCount;
	}

	public Integer getBindPostCount() {
		return bindPostCount;
	}

	public void setBindPostCount(Integer bindPostCount) {
		this.bindPostCount = bindPostCount;
	}

	// 默认构造方法
	public SysRole() {
		super();
	}

	// get和set方法
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String aRoleName) {
		this.roleName = aRoleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String aRoleDesc) {
		this.roleDesc = aRoleDesc;
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