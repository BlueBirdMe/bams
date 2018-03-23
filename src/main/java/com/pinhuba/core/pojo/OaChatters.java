package com.pinhuba.core.pojo;

/**
 * 数据库表名：OA_CHATTERS
 */
public class OaChatters extends BaseBean implements java.io.Serializable {

	/**
	 * 通讯手册表
	 */
	private static final long serialVersionUID = 1311360319835826446L;
	private String oaChatEmp; // 人员姓名
	private Integer oaChatSex; // 性别
	private String oaChatCom; // 单位
	private Integer oaChatGroup; // 所属分组
	private String oaChatAddress; // 地址
	private String oaHomeTel; // 家庭地址
	private String oaChatMobile; // 移动电话
	private String oaWorkTel; // 工作电话
	private String oaChatQq; // QQ
	private String oaChatMsn; // MSN
	private String oaChatEmail; // EMAIL
	private String oaChatPhotos; // 相片
	private Integer oaIsShare; // 是否共享
	private String oaShareEmp; // 共享人员
	private Integer oaCheckId; // 签入id
	private String recordId; // 修改人ID
	private String recordDate; // 修改时间
	private String lastmodiId; // 最后修改人ID
	private String lastmodiDate; // 最后修改时间
	private Integer companyId; // 公司ID
	private OaChatGroups oaChatGroups; // 分组表
	private String shareEmpName; // 共享人员名称
	private String shareEmpId; // 共享人员ID

	private Integer oaIsChecked; // 临时变量，存储状态

	// 默认构造方法
	public OaChatters() {
		super();
	}

	// get和set方法
	public String getOaChatEmp() {
		return oaChatEmp;
	}

	public void setOaChatEmp(String aOaChatEmp) {
		this.oaChatEmp = aOaChatEmp;
	}

	public Integer getOaChatSex() {
		return oaChatSex;
	}

	public void setOaChatSex(Integer aOaChatSex) {
		this.oaChatSex = aOaChatSex;
	}

	public String getOaChatCom() {
		return oaChatCom;
	}

	public void setOaChatCom(String aOaChatCom) {
		this.oaChatCom = aOaChatCom;
	}

	public Integer getOaChatGroup() {
		return oaChatGroup;
	}

	public void setOaChatGroup(Integer aOaChatGroup) {
		this.oaChatGroup = aOaChatGroup;
	}

	public String getOaChatAddress() {
		return oaChatAddress;
	}

	public void setOaChatAddress(String aOaChatAddress) {
		this.oaChatAddress = aOaChatAddress;
	}

	public String getOaHomeTel() {
		return oaHomeTel;
	}

	public void setOaHomeTel(String aOaHomeTel) {
		this.oaHomeTel = aOaHomeTel;
	}

	public String getOaChatMobile() {
		return oaChatMobile;
	}

	public void setOaChatMobile(String aOaChatMobile) {
		this.oaChatMobile = aOaChatMobile;
	}

	public String getOaWorkTel() {
		return oaWorkTel;
	}

	public void setOaWorkTel(String aOaWorkTel) {
		this.oaWorkTel = aOaWorkTel;
	}

	public String getOaChatQq() {
		return oaChatQq;
	}

	public void setOaChatQq(String aOaChatQq) {
		this.oaChatQq = aOaChatQq;
	}

	public String getOaChatMsn() {
		return oaChatMsn;
	}

	public void setOaChatMsn(String aOaChatMsn) {
		this.oaChatMsn = aOaChatMsn;
	}

	public String getOaChatEmail() {
		return oaChatEmail;
	}

	public void setOaChatEmail(String aOaChatEmail) {
		this.oaChatEmail = aOaChatEmail;
	}

	public Integer getOaIsShare() {
		return oaIsShare;
	}

	public void setOaIsShare(Integer aOaIsShare) {
		this.oaIsShare = aOaIsShare;
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

	public OaChatGroups getOaChatGroups() {
		return oaChatGroups;
	}

	public void setOaChatGroups(OaChatGroups oaChatGroups) {
		this.oaChatGroups = oaChatGroups;
	}

	public String getShareEmpName() {
		return shareEmpName;
	}

	public void setShareEmpName(String shareEmpName) {
		this.shareEmpName = shareEmpName;
	}

	public String getShareEmpId() {
		return shareEmpId;
	}

	public void setShareEmpId(String shareEmpId) {
		this.shareEmpId = shareEmpId;
	}

	public String getOaChatPhotos() {
		return oaChatPhotos;
	}

	public void setOaChatPhotos(String oaChatPhotos) {
		this.oaChatPhotos = oaChatPhotos;
	}

	public String getOaShareEmp() {
		return oaShareEmp;
	}

	public void setOaShareEmp(String oaShareEmp) {
		this.oaShareEmp = oaShareEmp;
	}

	public Integer getOaCheckId() {
		return oaCheckId;
	}

	public void setOaCheckId(Integer oaCheckId) {
		this.oaCheckId = oaCheckId;
	}

	public Integer getOaIsChecked() {
		return oaIsChecked;
	}

	public void setOaIsChecked(Integer oaIsChecked) {
		this.oaIsChecked = oaIsChecked;
	}

}