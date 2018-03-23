package com.pinhuba.core.pojo;

/**
 * 数据库表名：OA_NOTEBOOK
 */
public class OaNotebook extends BaseBean implements java.io.Serializable {

	/**
	 * 便签数据表
	 */
	private static final long serialVersionUID = -7584775622225747516L;
	private String oaNotebookContext; // 便签内容
	private String oaNotebookCreattime; // 便签创建时间
	private String oaNotebookEmp; // 便签创建人
	private Integer companyId;

	// 默认构造方法
	public OaNotebook() {
		super();
	}

	// get和set方法
	public String getOaNotebookContext() {
		return oaNotebookContext;
	}

	public void setOaNotebookContext(String aOaNotebookContext) {
		this.oaNotebookContext = aOaNotebookContext;
	}

	public String getOaNotebookCreattime() {
		return oaNotebookCreattime;
	}

	public void setOaNotebookCreattime(String aOaNotebookCreattime) {
		this.oaNotebookCreattime = aOaNotebookCreattime;
	}

	public String getOaNotebookEmp() {
		return oaNotebookEmp;
	}

	public void setOaNotebookEmp(String aOaNotebookEmp) {
		this.oaNotebookEmp = aOaNotebookEmp;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer aCompanyId) {
		this.companyId = aCompanyId;
	}

}