package com.pinhuba.core.pojo;

/**
 * 数据库表名：OA_VOTE_OPTION
 */
public class OaVoteOption extends BaseBean implements java.io.Serializable {

	/**
	 * 投票选项表
	 */
	private static final long serialVersionUID = 4533728851603539295L;
	private Integer oaVoteId; // 投票主键
	private String oaOptionName; // 选项名称
	private Integer oaOptionCount; // 选项统计数
	private String recordId;
	private String recordDate;
	private String lastmodiId;
	private String lastmodiDate;
	private Integer companyId;

	// 默认构造方法
	public OaVoteOption() {
		super();
	}

	// get和set方法
	public String getOaOptionName() {
		return oaOptionName;
	}

	public void setOaOptionName(String aOaOptionName) {
		this.oaOptionName = aOaOptionName;
	}

	public Integer getOaOptionCount() {
		return oaOptionCount;
	}

	public void setOaOptionCount(Integer aOaOptionCount) {
		this.oaOptionCount = aOaOptionCount;
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

	public Integer getOaVoteId() {
		return oaVoteId;
	}

	public void setOaVoteId(Integer oaVoteId) {
		this.oaVoteId = oaVoteId;
	}

}