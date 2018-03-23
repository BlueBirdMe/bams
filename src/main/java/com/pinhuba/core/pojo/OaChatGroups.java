package com.pinhuba.core.pojo;

/**
 * 数据库表名：OA_CHAT_GROUPS
 */
public class OaChatGroups extends BaseBean implements java.io.Serializable {

	/**
	 * 通讯录分组
	 */
	private static final long serialVersionUID = 8399123293654081196L;
	private String oaChatgpName; // 分组名称
	private String oaChatgpDetail; // 分组描述
	private String recordId; // 修改人ID
	private String recordDate; // 修改时间
	private String lastmodiId; // 最后修改人ID
	private String lastmodiDate; // 最后修改时间
	private Integer companyId; // 公司ID
	private HrmEmployee employee; // 人员表
	private Integer communicationCount; // 计数

	// 默认构造方法
	public OaChatGroups() {
		super();
	}
	
	// get和set方法
	public String getOaChatgpName() {
		return oaChatgpName;
	}

	public void setOaChatgpName(String aOaChatgpName) {
		this.oaChatgpName = aOaChatgpName;
	}

	public String getOaChatgpDetail() {
		return oaChatgpDetail;
	}

	public void setOaChatgpDetail(String aOaChatgpDetail) {
		this.oaChatgpDetail = aOaChatgpDetail;
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

	public HrmEmployee getEmployee() {
		return employee;
	}

	public void setEmployee(HrmEmployee employee) {
		this.employee = employee;
	}

	public Integer getCommunicationCount() {
		return communicationCount;
	}

	public void setCommunicationCount(Integer communicationCount) {
		this.communicationCount = communicationCount;
	}

}