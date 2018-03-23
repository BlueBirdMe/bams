package com.pinhuba.core.pojo;

/**
 * 数据库表名：HRM_TIMEDRECORD（定时提醒）
 */
public class HrmTimedrecord extends BaseBean implements java.io.Serializable {

	private static final long serialVersionUID = -694383536686375250L;
	//0: 每天  1：具体时间（年月日）
   private Integer timedType;	//提醒类型
   private String timedDate; 	//提醒日期
   private String timedDescription;	//提醒内容
   private String recordId;
   private String recordDate;
   private String lastmodiId;
   private String lastmodiDate;
   private Integer companyId;

	// 默认构造方法
	public HrmTimedrecord() {
		super();
	}

	// get和set方法
	public Integer getTimedType() {
		return timedType;
	}

	public void setTimedType(Integer aTimedType) {
		this.timedType = aTimedType;
	}

	public String getTimedDate() {
		return timedDate;
	}

	public void setTimedDate(String aTimedDate) {
		this.timedDate = aTimedDate;
	}

	public String getTimedDescription() {
		return timedDescription;
	}

	public void setTimedDescription(String aTimedDescription) {
		this.timedDescription = aTimedDescription;
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