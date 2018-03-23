package com.pinhuba.core.pojo;

/**
 * 数据库表名：SYS_COMPANY_METHODS
 */
public class SysCompanyMethods extends BaseBean implements java.io.Serializable {

	/**
	 * 公司对应功能表
	 */
	private static final long serialVersionUID = -6198723153675455827L;
	private String methodInfoId;	//功能编号
	private Integer companyId;		//公司编号


	// 默认构造方法
	public SysCompanyMethods() {
		super();
	}

	// 构造方法(手工生成)
	public SysCompanyMethods(String methodInfoId, Integer companyId) {
		super();
		this.methodInfoId = methodInfoId;
		this.companyId = companyId;
	}
	// get和set方法
	public String getMethodInfoId() {
		return methodInfoId;
	}

	public void setMethodInfoId(String aMethodInfoId) {
		this.methodInfoId = aMethodInfoId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer aCompanyId) {
		this.companyId = aCompanyId;
	}

}