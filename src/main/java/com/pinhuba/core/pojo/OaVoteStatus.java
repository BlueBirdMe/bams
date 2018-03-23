package com.pinhuba.core.pojo;

/**
 * 数据库表名：OA_VOTE_STATUS
 */
public class OaVoteStatus extends BaseBean implements java.io.Serializable {

	/**
	 * 投票状态表
	 */
	private static final long serialVersionUID = -2398217997884079009L;
	private String oaVoteRec; // 投票记录主键
	private String oaVoteEmp; // 已投票人员主键
	private Integer oaIsAnonymous; // 是否匿名投票
	private String recordId;
	private String recordDate;
	private String lastmodiId;
	private String lastmodiDate;
	private Integer companyId;
	private HrmEmployee employee;

	// 默认构造方法
	public OaVoteStatus() {
		super();
	}

	// get和set方法
	public String getOaVoteRec() {
		return oaVoteRec;
	}

	public void setOaVoteRec(String aOaVoteRec) {
		this.oaVoteRec = aOaVoteRec;
	}

	public String getOaVoteEmp() {
		return oaVoteEmp;
	}

	public void setOaVoteEmp(String aOaVoteEmp) {
		this.oaVoteEmp = aOaVoteEmp;
	}

	public Integer getOaIsAnonymous() {
		return oaIsAnonymous;
	}

	public void setOaIsAnonymous(Integer aOaIsAnonymous) {
		this.oaIsAnonymous = aOaIsAnonymous;
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

}