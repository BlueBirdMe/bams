package com.pinhuba.core.pojo;

/**
 * 数据库表名：OA_POSTS
 */
public class OaPosts extends BaseBean implements java.io.Serializable {

	/**
	 * 帖子信息表
	 */
	private static final long serialVersionUID = 7051516524873681057L;
	private String oaPostName; // 帖子名称
	private String oaPostText; // 发帖/回复内容
	private Integer oaPostForum; // 所属版块
	private String oaPostEmp; // 发帖/回复人
	private String oaPostTime; // 发帖/回复时间
	private String oaPostLastregter; // 最后回复时间
	private String oaLastRegEmp; // 最后回复人
	private Integer oaReadCount; // 阅读数
	private Integer oaIsPost; // 是否帖子
	private Integer oaPostReg; // 存储帖子主键
	private Integer oaIsBoutique; // 是否加精
	private String recordId; // 修改人ID
	private String recordDate; // 修改时间
	private String lastModiId; // 最后修改人ID
	private String lastModiDate; // 最后修改时间
	private Integer companyId; // 公司ID
	private HrmEmployee employee; // 人员对象
	private OaForums forums; // 版块对象

	// 默认构造方法
	public OaPosts() {
		super();
	}

	// get和set方法
	public String getOaPostName() {
		return oaPostName;
	}

	public void setOaPostName(String aOaPostName) {
		this.oaPostName = aOaPostName;
	}

	public String getOaPostText() {
		return oaPostText;
	}

	public void setOaPostText(String aOaPostText) {
		this.oaPostText = aOaPostText;
	}

	public Integer getOaPostForum() {
		return oaPostForum;
	}

	public void setOaPostForum(Integer aOaPostForum) {
		this.oaPostForum = aOaPostForum;
	}

	public String getOaPostEmp() {
		return oaPostEmp;
	}

	public void setOaPostEmp(String aOaPostEmp) {
		this.oaPostEmp = aOaPostEmp;
	}

	public String getOaPostTime() {
		return oaPostTime;
	}

	public void setOaPostTime(String aOaPostTime) {
		this.oaPostTime = aOaPostTime;
	}

	public String getOaPostLastregter() {
		return oaPostLastregter;
	}

	public void setOaPostLastregter(String aOaPostLastregter) {
		this.oaPostLastregter = aOaPostLastregter;
	}

	public Integer getOaReadCount() {
		return oaReadCount;
	}

	public void setOaReadCount(Integer aOaReadCount) {
		this.oaReadCount = aOaReadCount;
	}

	public Integer getOaIsPost() {
		return oaIsPost;
	}

	public void setOaIsPost(Integer aOaIsPost) {
		this.oaIsPost = aOaIsPost;
	}

	public Integer getOaPostReg() {
		return oaPostReg;
	}

	public void setOaPostReg(Integer aOaPostReg) {
		this.oaPostReg = aOaPostReg;
	}

	public Integer getOaIsBoutique() {
		return oaIsBoutique;
	}

	public void setOaIsBoutique(Integer aOaIsBoutique) {
		this.oaIsBoutique = aOaIsBoutique;
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

	public String getLastModiId() {
		return lastModiId;
	}

	public void setLastModiId(String aLastModiId) {
		this.lastModiId = aLastModiId;
	}

	public String getLastModiDate() {
		return lastModiDate;
	}

	public void setLastModiDate(String aLastModiDate) {
		this.lastModiDate = aLastModiDate;
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

	public OaForums getForums() {
		return forums;
	}

	public void setForums(OaForums forums) {
		this.forums = forums;
	}

	public String getOaLastRegEmp() {
		return oaLastRegEmp;
	}

	public void setOaLastRegEmp(String oaLastRegEmp) {
		this.oaLastRegEmp = oaLastRegEmp;
	}

}