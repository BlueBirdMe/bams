package com.pinhuba.core.pojo;

/**
 * 数据库表名：OA_REGULATIONS
 */
public class OaRegulations extends BaseBean implements java.io.Serializable {

	/**
	 * 规章制度表
	 */
	private static final long serialVersionUID = 4460506495595909218L;
	private String oaRegulationsTitle; // 标题
	private Integer oaRegulationsType; // 类型 ID

	private SysLibraryInfo regulationsType; // 类型表

	private String oaRegulationsEmp; // 人员ID

	private HrmEmployee regulationsEmployee; // 人员表

	private String oaRegulationsTime; // 创建时间
	private String oaRegulationsAttachs; // 附件
	private String recordId; // 修改人ID
	private String recordDate; // 修改时间
	private String lastmodiId; // 最后修改人ID
	private String lastmodiDate; // 最后修改时间
	private Integer companyId; // 公司ID

	private Integer regulationsStatus; // 状态

	private String regulatStratTime; // 生效日期

	private String regulatContext; // 内容

	private String tmpDatetime;// 临时使用

	public String getTmpDatetime() {
		return tmpDatetime;
	}

	public void setTmpDatetime(String tmpDatetime) {
		this.tmpDatetime = tmpDatetime;
	}

	public String getRegulatContext() {
		return regulatContext;
	}

	public void setRegulatContext(String regulatContext) {
		this.regulatContext = regulatContext;
	}

	public Integer getRegulationsStatus() {
		return regulationsStatus;
	}

	public void setRegulationsStatus(Integer regulationsStatus) {
		this.regulationsStatus = regulationsStatus;
	}

	public String getRegulatStratTime() {
		return regulatStratTime;
	}

	public void setRegulatStratTime(String regulatStratTime) {
		this.regulatStratTime = regulatStratTime;
	}

	// 默认构造方法
	public OaRegulations() {
		super();
	}

	// get和set方法
	public String getOaRegulationsTitle() {
		return oaRegulationsTitle;
	}

	public void setOaRegulationsTitle(String aOaRegulationsTitle) {
		this.oaRegulationsTitle = aOaRegulationsTitle;
	}

	public Integer getOaRegulationsType() {
		return oaRegulationsType;
	}

	public void setOaRegulationsType(Integer aOaRegulationsType) {
		this.oaRegulationsType = aOaRegulationsType;
	}

	public String getOaRegulationsEmp() {
		return oaRegulationsEmp;
	}

	public void setOaRegulationsEmp(String aOaRegulationsEmp) {
		this.oaRegulationsEmp = aOaRegulationsEmp;
	}

	public String getOaRegulationsTime() {
		return oaRegulationsTime;
	}

	public void setOaRegulationsTime(String aOaRegulationsTime) {
		this.oaRegulationsTime = aOaRegulationsTime;
	}

	public String getOaRegulationsAttachs() {
		return oaRegulationsAttachs;
	}

	public void setOaRegulationsAttachs(String aOaRegulationsAttachs) {
		this.oaRegulationsAttachs = aOaRegulationsAttachs;
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

	public SysLibraryInfo getRegulationsType() {
		return regulationsType;
	}

	public void setRegulationsType(SysLibraryInfo regulationsType) {
		this.regulationsType = regulationsType;
	}

	public HrmEmployee getRegulationsEmployee() {
		return regulationsEmployee;
	}

	public void setRegulationsEmployee(HrmEmployee regulationsEmployee) {
		this.regulationsEmployee = regulationsEmployee;
	}

}