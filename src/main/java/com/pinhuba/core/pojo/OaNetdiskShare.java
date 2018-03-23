package com.pinhuba.core.pojo;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * 数据库表名：OA_NETDISK_SHARE
 */
public class OaNetdiskShare extends BaseBean implements java.io.Serializable {

	/**
	 *  网络文件共享
	 */
	private static final long serialVersionUID = -8416644497703037523L;

	private String hrmEmployeeId; // 人员ID
	private String folderName; // 文件夹名称
	private String folderPath; // 文件夹路径
	private String netdiskEmps; // 共享查看人员
	private String netdiskDeps; // 共享查看部门
	private String shareDesc; // 共享描述
	private String recordId;
	private String recordDate;
	private String lastmodiId;
	private String lastmodiDate;
	private Integer companyId;

	// 默认构造方法
	public OaNetdiskShare() {
		super();
	}

	// get和set方法
	public String getHrmEmployeeId() {
		return hrmEmployeeId;
	}

	public void setHrmEmployeeId(String aHrmEmployeeId) {
		this.hrmEmployeeId = aHrmEmployeeId;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String aFolderName) {
		this.folderName = aFolderName;
	}

	public String getFolderPath() {
		if (folderPath != null)
			try {
				return URLDecoder.decode(this.folderPath, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		return this.folderPath;
	}

	public void setFolderPath(String aFolderPath) {
		this.folderPath = aFolderPath;
	}

	public String getNetdiskEmps() {
		return netdiskEmps;
	}

	public void setNetdiskEmps(String aNetdiskEmps) {
		this.netdiskEmps = aNetdiskEmps;
	}

	public String getNetdiskDeps() {
		return netdiskDeps;
	}

	public void setNetdiskDeps(String aNetdiskDeps) {
		this.netdiskDeps = aNetdiskDeps;
	}

	public String getShareDesc() {
		return shareDesc;
	}

	public void setShareDesc(String aShareDesc) {
		this.shareDesc = aShareDesc;
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