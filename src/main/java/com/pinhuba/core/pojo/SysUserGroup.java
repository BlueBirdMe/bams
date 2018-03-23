package com.pinhuba.core.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库表名：SYS_USER_GROUP
 */
public class SysUserGroup extends BaseBean implements java.io.Serializable {

	/**
	 * 用户分组表
	 */
	private static final long serialVersionUID = 8347711380923133874L;
	private String groupName;		//组名称
	private String groupDecp;		//组描述
	private String recordId;
	private String recordDate;
	private String lastmodiId;
	private String lastmodeDate;
	private Integer companyId;

	private List<SysUserGroupDetail> detailList = new ArrayList<SysUserGroupDetail>();

	public List<SysUserGroupDetail> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<SysUserGroupDetail> detailList) {
		this.detailList = detailList;
	}

	// 默认构造方法
	public SysUserGroup() {
		super();
	}

	// get和set方法
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String aGroupName) {
		this.groupName = aGroupName;
	}

	public String getGroupDecp() {
		return groupDecp;
	}

	public void setGroupDecp(String aGroupDecp) {
		this.groupDecp = aGroupDecp;
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

	public String getLastmodeDate() {
		return lastmodeDate;
	}

	public void setLastmodeDate(String aLastmodeDate) {
		this.lastmodeDate = aLastmodeDate;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer aCompanyId) {
		this.companyId = aCompanyId;
	}

}