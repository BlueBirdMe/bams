package com.pinhuba.core.pojo;

/**
 * 数据库表名：HRM_WORKAREA（工作地区）
 */
public class HrmWorkarea extends BaseBean implements java.io.Serializable {

	private static final long serialVersionUID = -2616574802397750680L;
	private String hrmAreaName;               //工作地区名称
	private String hrmAreaEngname;            //英文名
	private String hrmAreaDesc;               //描述
	private String recordId;                  //记录人
	private String recordDate;                //记录时间
	private String lastmodiId;                //最后修改人
	private String lastmodiDate;              //最后修改时间
	private Integer companyId;                //公司ID

	// 默认构造方法
	public HrmWorkarea() {
		super();
	}

	// 构造方法(手工生成)
	public HrmWorkarea(String hrmAreaName, String hrmAreaEngname, String hrmAreaDesc, String recordId, String recordDate, String lastmodiId, String lastmodiDate, Integer companyId) {
		super();
		this.hrmAreaName = hrmAreaName;
		this.hrmAreaEngname = hrmAreaEngname;
		this.hrmAreaDesc = hrmAreaDesc;
		this.recordId = recordId;
		this.recordDate = recordDate;
		this.lastmodiId = lastmodiId;
		this.lastmodiDate = lastmodiDate;
		this.companyId = companyId;
	}
	
	// get和set方法
	public String getHrmAreaName() {
		return hrmAreaName;
	}

	public void setHrmAreaName(String aHrmAreaName) {
		this.hrmAreaName = aHrmAreaName;
	}

	public String getHrmAreaEngname() {
		return hrmAreaEngname;
	}

	public void setHrmAreaEngname(String aHrmAreaEngname) {
		this.hrmAreaEngname = aHrmAreaEngname;
	}

	public String getHrmAreaDesc() {
		return hrmAreaDesc;
	}

	public void setHrmAreaDesc(String aHrmAreaDesc) {
		this.hrmAreaDesc = aHrmAreaDesc;
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