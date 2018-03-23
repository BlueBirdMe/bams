package com.pinhuba.core.pojo;

/**
 * 数据库表名：sys_method_btn
 */
public class SysRoleBtn extends BaseBean implements java.io.Serializable {

	private Integer roleId;// 角色编号
	private Integer btnId;// 功能编号
	private Integer companyId;// 公司

	// 默认构造方法
	public SysRoleBtn() {
		super();
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	
	public Integer getBtnId() {
		return btnId;
	}

	public void setBtnId(Integer btnId) {
		this.btnId = btnId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

}