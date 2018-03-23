package com.pinhuba.core.pojo;

/**
 * 数据库表名：OA_FORUMS
 */
public class OaForums extends BaseBean implements java.io.Serializable {

	/**
	 * 帖子版块表
	 */
	private static final long serialVersionUID = 8287967590382721080L;
	private String oaForumName; // 版块名称
	private String oaForumText; // 版块说明
	private String oaForumEmp; // 创建人
	private String oaForumAdmin; // 版主
	private String oaForumTime; // 创建时间
	private String oaForumImage; // 版块图标（图片）
	private String recordId; // 修改人ID
	private String recordDate; // 修改时间
	private String lastmodiId; // 最后修改人ID
	private String lastmodiDate; // 最后修改时间
	private Integer companyId; // 公司ID
	private HrmEmployee employee; // 创建人的人员对象
	private Integer oaPostsCount; // 此版块中的帖子数
	private String forumAdminName; // 用于保存版主名字
	private Integer todayPostCount; // 今天帖子数
	private Integer topicCount; // 主题帖子数
	private Integer articleCount; // 文章数
	private String lastReplyTitle; // 最后发布标题
	private String lastReplyAuthor; // 最后发布作者
	private String lastReplyDate; // 最后发布日期
	private Long lastReplyID; // 最后发布ID
	private Integer listReplyForumID; // 最后发布板块ID

	// 默认构造方法
	public OaForums() {
		super();
	}

	// get和set方法
	public String getOaForumName() {
		return oaForumName;
	}

	public void setOaForumName(String aOaForumName) {
		this.oaForumName = aOaForumName;
	}

	public String getOaForumText() {
		return oaForumText;
	}

	public void setOaForumText(String aOaForumText) {
		this.oaForumText = aOaForumText;
	}

	public String getOaForumEmp() {
		return oaForumEmp;
	}

	public void setOaForumEmp(String aOaForumEmp) {
		this.oaForumEmp = aOaForumEmp;
	}

	public String getOaForumTime() {
		return oaForumTime;
	}

	public void setOaForumTime(String aOaForumTime) {
		this.oaForumTime = aOaForumTime;
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

	public HrmEmployee getEmployee() {
		return employee;
	}

	public void setEmployee(HrmEmployee employee) {
		this.employee = employee;
	}

	public Integer getOaPostsCount() {
		return oaPostsCount;
	}

	public void setOaPostsCount(Integer oaPostsCount) {
		this.oaPostsCount = oaPostsCount;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getOaForumAdmin() {
		return oaForumAdmin;
	}

	public void setOaForumAdmin(String oaForumAdmin) {
		this.oaForumAdmin = oaForumAdmin;
	}

	public String getOaForumImage() {
		return oaForumImage;
	}

	public void setOaForumImage(String oaForumImage) {
		this.oaForumImage = oaForumImage;
	}

	public String getForumAdminName() {
		return forumAdminName;
	}

	public void setForumAdminName(String forumAdminName) {
		this.forumAdminName = forumAdminName;
	}

	/**
	 * @return the todayPostCount
	 */
	public Integer getTodayPostCount() {
		return todayPostCount;
	}

	/**
	 * @param todayPostCount
	 *            the todayPostCount to set
	 */
	public void setTodayPostCount(Integer todayPostCount) {
		this.todayPostCount = todayPostCount;
	}

	/**
	 * @return the topicCount
	 */
	public Integer getTopicCount() {
		return topicCount;
	}

	/**
	 * @param topicCount
	 *            the topicCount to set
	 */
	public void setTopicCount(Integer topicCount) {
		this.topicCount = topicCount;
	}

	/**
	 * @return the articleCount
	 */
	public Integer getArticleCount() {
		return articleCount;
	}

	/**
	 * @param articleCount
	 *            the articleCount to set
	 */
	public void setArticleCount(Integer articleCount) {
		this.articleCount = articleCount;
	}

	/**
	 * @return the lastReplyTitle
	 */
	public String getLastReplyTitle() {
		return lastReplyTitle;
	}

	/**
	 * @param lastReplyTitle
	 *            the lastReplyTitle to set
	 */
	public void setLastReplyTitle(String lastReplyTitle) {
		this.lastReplyTitle = lastReplyTitle;
	}

	/**
	 * @return the lastReplyAuthor
	 */
	public String getLastReplyAuthor() {
		return lastReplyAuthor;
	}

	/**
	 * @param lastReplyAuthor
	 *            the lastReplyAuthor to set
	 */
	public void setLastReplyAuthor(String lastReplyAuthor) {
		this.lastReplyAuthor = lastReplyAuthor;
	}

	/**
	 * @return the lastReplyDate
	 */
	public String getLastReplyDate() {
		return lastReplyDate;
	}

	/**
	 * @param lastReplyDate
	 *            the lastReplyDate to set
	 */
	public void setLastReplyDate(String lastReplyDate) {
		this.lastReplyDate = lastReplyDate;
	}

	/**
	 * @return the lastReplyID
	 */
	public Long getLastReplyID() {
		return lastReplyID;
	}

	/**
	 * @param lastReplyID
	 *            the lastReplyID to set
	 */
	public void setLastReplyID(Long lastReplyID) {
		this.lastReplyID = lastReplyID;
	}

	/**
	 * @return the listReplyForumID
	 */
	public Integer getListReplyForumID() {
		return listReplyForumID;
	}

	/**
	 * @param listReplyForumID
	 *            the listReplyForumID to set
	 */
	public void setListReplyForumID(Integer listReplyForumID) {
		this.listReplyForumID = listReplyForumID;
	}

}