package com.pinhuba.core.pojo;

/**
 * 数据库表名：OA_CAR_USE
 */
public class OaCarUse extends BaseBean implements java.io.Serializable {

	/**
	 * 车辆外出
	 */
	private static final long serialVersionUID = -4541722018610481668L;
	private String oaCaruseName; // 车辆名称
	private String oaCaruseCards; // 车牌号
	private String oaCaruseUser; // 用车人
	private String oaCaruseStar; // 起始时间
	private String oaCaruseEnd; // 结束时间
	private String oaCaruseDestination; // 出车地点
	private String oaCaruseMileage; // 出车里程数
	private String oaCaruseCause; // 出车事由
	private String oaCaruseRemark; // 出车记录
	private String recordId;
	private String recordDate;
	private String lastmodiId;
	private String lastmodiDate;
	private Integer companyId;

	// 默认构造方法
	public OaCarUse() {
		super();
	}

	// get和set方法
	public String getOaCaruseName() {
		return oaCaruseName;
	}

	public void setOaCaruseName(String aOaCaruseName) {
		this.oaCaruseName = aOaCaruseName;
	}

	public String getOaCaruseCards() {
		return oaCaruseCards;
	}

	public void setOaCaruseCards(String aOaCaruseCards) {
		this.oaCaruseCards = aOaCaruseCards;
	}

	public String getOaCaruseUser() {
		return oaCaruseUser;
	}

	public void setOaCaruseUser(String aOaCaruseUser) {
		this.oaCaruseUser = aOaCaruseUser;
	}

	public String getOaCaruseStar() {
		return oaCaruseStar;
	}

	public void setOaCaruseStar(String aOaCaruseStar) {
		this.oaCaruseStar = aOaCaruseStar;
	}

	public String getOaCaruseEnd() {
		return oaCaruseEnd;
	}

	public void setOaCaruseEnd(String aOaCaruseEnd) {
		this.oaCaruseEnd = aOaCaruseEnd;
	}

	public String getOaCaruseDestination() {
		return oaCaruseDestination;
	}

	public void setOaCaruseDestination(String aOaCaruseDestination) {
		this.oaCaruseDestination = aOaCaruseDestination;
	}

	public String getOaCaruseMileage() {
		return oaCaruseMileage;
	}

	public void setOaCaruseMileage(String aOaCaruseMileage) {
		this.oaCaruseMileage = aOaCaruseMileage;
	}

	public String getOaCaruseCause() {
		return oaCaruseCause;
	}

	public void setOaCaruseCause(String aOaCaruseCause) {
		this.oaCaruseCause = aOaCaruseCause;
	}

	public String getOaCaruseRemark() {
		return oaCaruseRemark;
	}

	public void setOaCaruseRemark(String aOaCaruseRemark) {
		this.oaCaruseRemark = aOaCaruseRemark;
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