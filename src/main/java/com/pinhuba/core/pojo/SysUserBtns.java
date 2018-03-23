package com.pinhuba.core.pojo;


public class SysUserBtns extends BaseBean implements java.io.Serializable {

	/**
	 * 用户按钮对应表
	 */
	private static final long serialVersionUID = -7368426963442026719L;
	private Integer userId;				//用户ID
	private String userBtnDetail;		//按钮编号
	private Integer companyId;			//所属公司

	// 默认构造方法
	public SysUserBtns() {
		super();
	}
	
	// get和set方法
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer aUserId) {
		this.userId = aUserId;
	}

	public String getUserBtnDetail() {
		return userBtnDetail;
	}

	public void setUserBtnDetail(String userBtnDetail) {
		this.userBtnDetail = userBtnDetail;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer aCompanyId) {
		this.companyId = aCompanyId;
	}

}