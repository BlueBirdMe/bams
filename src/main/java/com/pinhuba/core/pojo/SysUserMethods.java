package com.pinhuba.core.pojo;

/**
 * 数据库表名：SYS_USER_METHODS
 */
public class SysUserMethods extends BaseBean implements java.io.Serializable {

	/**
	 * 用户功能对应表
	 */
	private static final long serialVersionUID = -7368426963442026719L;
	private Integer userId;				//用户主键
		
	private String userMethodDetail;	//功能编号
	private Integer companyId;			//所属公司

	// 默认构造方法
	public SysUserMethods() {
		super();
	}
	
	// get和set方法
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer aUserId) {
		this.userId = aUserId;
	}

	public String getUserMethodDetail() {
		return userMethodDetail;
	}

	public void setUserMethodDetail(String aUserMethodDetail) {
		this.userMethodDetail = aUserMethodDetail;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer aCompanyId) {
		this.companyId = aCompanyId;
	}

}