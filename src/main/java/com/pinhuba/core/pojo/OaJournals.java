package com.pinhuba.core.pojo;

/**
 * 数据库表名：OA_JOURNALS
 */
public class OaJournals extends BaseBean implements java.io.Serializable {

	/**
	 * 电子期刊
	 */
	private static final long serialVersionUID = -5014989004720584837L;

	private Integer journalsTypeId; // 期刊类型ID
	private OaJournalsType journalsType; // 期刊类型表
	private String journalsCount; // 期刊期数
	private String journalsCode; // 期刊号
	private String journalsContext; // 描述
	private Integer journalsFace; // 封面
	private String journalsAffix; // 附件
	private String recordId; // 修改人ID
	private String recordDate; // 修改时间
	private String lastmodiId; // 最后修改人ID
	private String lastmodiDate; // 最后修改时间
	private Integer companyId; // 公司ID

	// 默认构造方法
	public OaJournals() {
		super();
	}

	// get和set方法
	public Integer getJournalsTypeId() {
		return journalsTypeId;
	}

	public void setJournalsTypeId(Integer aJournalsTypeId) {
		this.journalsTypeId = aJournalsTypeId;
	}

	public String getJournalsCount() {
		return journalsCount;
	}

	public void setJournalsCount(String journalsCount) {
		this.journalsCount = journalsCount;
	}

	public String getJournalsCode() {
		return journalsCode;
	}

	public void setJournalsCode(String aJournalsCode) {
		this.journalsCode = aJournalsCode;
	}

	public String getJournalsContext() {
		return journalsContext;
	}

	public void setJournalsContext(String aJournalsContext) {
		this.journalsContext = aJournalsContext;
	}

	public Integer getJournalsFace() {
		return journalsFace;
	}

	public void setJournalsFace(Integer aJournalsFace) {
		this.journalsFace = aJournalsFace;
	}

	public String getJournalsAffix() {
		return journalsAffix;
	}

	public void setJournalsAffix(String aJournalsAffix) {
		this.journalsAffix = aJournalsAffix;
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

	public OaJournalsType getJournalsType() {
		return journalsType;
	}

	public void setJournalsType(OaJournalsType journalsType) {
		this.journalsType = journalsType;
	}

}