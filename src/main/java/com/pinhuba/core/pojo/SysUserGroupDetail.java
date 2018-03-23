package com.pinhuba.core.pojo;

/**
 * 数据库表名：SYS_USER_GROUP_DETAIL
 */
public class SysUserGroupDetail extends BaseBean implements java.io.Serializable {

	/**
	 * 用户分组明细表
	 */
	private static final long serialVersionUID = -3370517030597179291L;
	private Integer userId;    //用户主键
	
	private SysUserInfo userInfo;   //用户信息对象
	
	private Integer groupId; //用户组主键

	private SysUserGroup userGroup;    //用户组信息对象
	
	private Integer companyId; //公司主键

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public SysUserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(SysUserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public SysUserGroup getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(SysUserGroup userGroup) {
		this.userGroup = userGroup;
	}

	// 默认构造方法
	public SysUserGroupDetail() {
		super();
	}

	// get和set方法
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer aUserId) {
		this.userId = aUserId;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer aGroupId) {
		this.groupId = aGroupId;
	}

}