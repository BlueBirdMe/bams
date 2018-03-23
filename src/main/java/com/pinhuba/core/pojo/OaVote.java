package com.pinhuba.core.pojo;

/**
 * 数据库表名：OA_VOTE
 */
public class OaVote extends BaseBean implements java.io.Serializable {

	/**
	 * 投票信息表
	 */
	private static final long serialVersionUID = -1551280706485291609L;
	private String oaVoteName; // 投票名称
	private String oaVoteEmp; // 发起人
	private String oaVoteText; // 投票说明
	private Integer oaVoteType; // 投票类型
	private Integer oaChooseType; // 选项类型
	private String oaVoteStart; // 开始时间
	private String oaVoteEnd; // 结束时间
	private String oaRangeDep; // 投票范围（部门）
	private String oaRangeEmp; // 投票范围（人员）
	private Integer oaIsAnonymous; // 是否允许匿名投票
	private Integer oaVoteStatus; // 投票状态
	private String recordId; // 修改人ID
	private String recordDate; // 修改时间
	private String lastmodiId; // 最后修改人ID
	private String lastmodiDate; // 最后修改时间
	private Integer companyId; // 公司ID
	// 临时使用
	private HrmEmployee employee; // 发起人对象
	private SysLibraryInfo votetypeLib; // 投票类型对象
	private String rangDepNames; // 存放投票范围部门名称
	private String rangEmpNames; // 存放投票范围人员名称
	private Integer voteCount; // 是否已投

	// 默认构造方法
	public OaVote() {
		super();
	}

	// get和set方法
	public String getOaVoteName() {
		return oaVoteName;
	}

	public void setOaVoteName(String aOaVoteName) {
		this.oaVoteName = aOaVoteName;
	}

	public String getOaVoteEmp() {
		return oaVoteEmp;
	}

	public void setOaVoteEmp(String aOaVoteEmp) {
		this.oaVoteEmp = aOaVoteEmp;
	}

	public String getOaVoteText() {
		return oaVoteText;
	}

	public void setOaVoteText(String aOaVoteText) {
		this.oaVoteText = aOaVoteText;
	}

	public Integer getOaVoteType() {
		return oaVoteType;
	}

	public void setOaVoteType(Integer aOaVoteType) {
		this.oaVoteType = aOaVoteType;
	}

	public Integer getOaChooseType() {
		return oaChooseType;
	}

	public void setOaChooseType(Integer aOaChooseType) {
		this.oaChooseType = aOaChooseType;
	}

	public String getOaVoteStart() {
		return oaVoteStart;
	}

	public void setOaVoteStart(String aOaVoteStart) {
		this.oaVoteStart = aOaVoteStart;
	}

	public String getOaVoteEnd() {
		return oaVoteEnd;
	}

	public void setOaVoteEnd(String aOaVoteEnd) {
		this.oaVoteEnd = aOaVoteEnd;
	}

	public String getOaRangeDep() {
		return oaRangeDep;
	}

	public void setOaRangeDep(String aOaRangeDep) {
		this.oaRangeDep = aOaRangeDep;
	}

	public String getOaRangeEmp() {
		return oaRangeEmp;
	}

	public void setOaRangeEmp(String aOaRangeEmp) {
		this.oaRangeEmp = aOaRangeEmp;
	}

	public Integer getOaIsAnonymous() {
		return oaIsAnonymous;
	}

	public void setOaIsAnonymous(Integer aOaIsAnonymous) {
		this.oaIsAnonymous = aOaIsAnonymous;
	}

	public Integer getOaVoteStatus() {
		return oaVoteStatus;
	}

	public void setOaVoteStatus(Integer aOaVoteStatus) {
		this.oaVoteStatus = aOaVoteStatus;
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

	public SysLibraryInfo getVotetypeLib() {
		return votetypeLib;
	}

	public void setVotetypeLib(SysLibraryInfo votetypeLib) {
		this.votetypeLib = votetypeLib;
	}

	public String getRangDepNames() {
		return rangDepNames;
	}

	public void setRangDepNames(String rangDepNames) {
		this.rangDepNames = rangDepNames;
	}

	public String getRangEmpNames() {
		return rangEmpNames;
	}

	public void setRangEmpNames(String rangEmpNames) {
		this.rangEmpNames = rangEmpNames;
	}

	public Integer getVoteCount() {
		return voteCount;
	}

	public void setVoteCount(Integer voteCount) {
		this.voteCount = voteCount;
	}

}