package com.pinhuba.core.pojo;

/**
 * 数据库表名：OA_NETDISK_CONFIG
 */
public class OaNetdiskConfig extends BaseBean implements java.io.Serializable {
	/**
	 * 网络磁盘配置表
	 */
	private static final long serialVersionUID = -5825376988785300196L;
	private String hrmEmployeeId;// 人ID
	private Double usedSpace; // 使用空间
	private Integer totalSpace; // 总空间
	private String recordId;
	private String recordDate;
	private String lastmodiId;
	private String lastmodiDate;
	private Integer companyId;
	private HrmEmployee hrmEmployee;

	public HrmEmployee getHrmEmployee() {
		return hrmEmployee;
	}

	public void setHrmEmployee(HrmEmployee hrmEmployee) {
		this.hrmEmployee = hrmEmployee;
	}

	// 默认构造方法
	public OaNetdiskConfig() {
		super();
	}

	// get和set方法
	public String getHrmEmployeeId() {
		return hrmEmployeeId;
	}

	public void setHrmEmployeeId(String aHrmEmployeeId) {
		this.hrmEmployeeId = aHrmEmployeeId;
	}

	public Integer getTotalSpace() {
		return totalSpace;
	}

	public void setTotalSpace(Integer aTotalSpace) {
		this.totalSpace = aTotalSpace;
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

	public Double getUsedSpace() {
		return usedSpace;
	}

	public void setUsedSpace(Double usedSpace) {
		this.usedSpace = usedSpace;
	}

}