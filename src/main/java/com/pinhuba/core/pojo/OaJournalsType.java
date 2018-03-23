package com.pinhuba.core.pojo;

/**
 * 数据库表名：OA_JOURNALS_TYPE
 */
public class OaJournalsType extends BaseBean implements java.io.Serializable {

	/**
	 * 期刊类型表
	 */
	private static final long serialVersionUID = 7353816400838449604L;
	private String journalsTypeName;		//类型名称
	private String journalsTypeProper;		//出版社
	private String journalsTypePress;		//内容描述
	private String recordId;				//修改人ID
	private String recordDate;				//修改时间
	private String lastmodiId;				//最后修改人ID
	private String lastmodiDate;			//最后修改时间
	private Integer companyId;				//公司ID

	// 默认构造方法
	public OaJournalsType() {
		super();
	}

	// get和set方法
	public String getJournalsTypeName() {
		return journalsTypeName;
	}

	public void setJournalsTypeName(String aJournalsTypeName) {
		this.journalsTypeName = aJournalsTypeName;
	}

	public String getJournalsTypeProper() {
		return journalsTypeProper;
	}

	public void setJournalsTypeProper(String aJournalsTypeProper) {
		this.journalsTypeProper = aJournalsTypeProper;
	}

	public String getJournalsTypePress() {
		return journalsTypePress;
	}

	public void setJournalsTypePress(String aJournalsTypePress) {
		this.journalsTypePress = aJournalsTypePress;
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