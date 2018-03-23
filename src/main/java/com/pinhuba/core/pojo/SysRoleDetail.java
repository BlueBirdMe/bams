package com.pinhuba.core.pojo;

/**
 * 数据库表名：SYS_ROLE_DETAIL
 */
public class SysRoleDetail extends BaseBean implements java.io.Serializable {

	/**
	 * 角色明细表
	 */
	private static final long serialVersionUID = -930072576659924937L;
	private Integer roleId;//角色编号
	private String methodId;//功能编号
	private Integer companyId;//公司

	// 默认构造方法
	public SysRoleDetail() {
		super();
	}

	// get和set方法
	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer aRoleId) {
		this.roleId = aRoleId;
	}

	public String getMethodId() {
		return methodId;
	}

	public void setMethodId(String aMethodId) {
		this.methodId = aMethodId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer aCompanyId) {
		this.companyId = aCompanyId;
	}

}