package com.pinhuba.core.pojo;

/**
 * 数据库表名：OA_CAR_APPLY
 */
public class OaCarApply extends BaseBean implements java.io.Serializable {

	/**
	 * 车辆申请表
	 */
	private static final long serialVersionUID = -853566023133619829L;
	private Integer applySection; 		// 部门ID
	private String applyUser; 			//申请人
	private HrmEmployee applyEmployee;  //申请雇员对象
	private String applyPhone; 			//电话
	private String applyTask; 			//任务
	private String applyBegindate; 		//时间
	private String applyEnddate; 		//时间
	private Integer applyNum; 			//人数
	private Integer carId;  			//车辆
	private OaCar oaCar;                //车辆对象
	private String recordId;
	private String recordDate;
	private String lastmodiId;
	private String lastmodiDate;
	private Integer companyId;   
	private String applyAppendnews;  	 //备注
	private Integer oaCarStatus; 		 //状态

	// 默认构造方法
	public OaCarApply() {
		super();
	}

	// get和set方法
	public Integer getApplySection() {
		return applySection;
	}

	public void setApplySection(Integer aApplySection) {
		this.applySection = aApplySection;
	}

	public String getApplyUser() {
		return applyUser;
	}

	public void setApplyUser(String aApplyUser) {
		this.applyUser = aApplyUser;
	}

	public String getApplyPhone() {
		return applyPhone;
	}

	public void setApplyPhone(String aApplyPhone) {
		this.applyPhone = aApplyPhone;
	}

	public String getApplyTask() {
		return applyTask;
	}

	public void setApplyTask(String aApplyTask) {
		this.applyTask = aApplyTask;
	}

	public String getApplyBegindate() {
		return applyBegindate;
	}

	public void setApplyBegindate(String aApplyBegindate) {
		this.applyBegindate = aApplyBegindate;
	}

	public String getApplyEnddate() {
		return applyEnddate;
	}

	public void setApplyEnddate(String aApplyEnddate) {
		this.applyEnddate = aApplyEnddate;
	}

	public Integer getApplyNum() {
		return applyNum;
	}

	public void setApplyNum(Integer aApplyNum) {
		this.applyNum = aApplyNum;
	}

	public Integer getCarId() {
		return carId;
	}

	public void setCarId(Integer aCarId) {
		this.carId = aCarId;
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

	public String getApplyAppendnews() {
		return applyAppendnews;
	}

	public void setApplyAppendnews(String aApplyAppendnews) {
		this.applyAppendnews = aApplyAppendnews;
	}

	

	public OaCar getOaCar() {
		return oaCar;
	}

	public void setOaCar(OaCar oaCar) {
		this.oaCar = oaCar;
	}

	public HrmEmployee getApplyEmployee() {
		return applyEmployee;
	}

	public void setApplyEmployee(HrmEmployee applyEmployee) {
		this.applyEmployee = applyEmployee;
	}

	public Integer getOaCarStatus() {
		return oaCarStatus;
	}

	public void setOaCarStatus(Integer oaCarStatus) {
		this.oaCarStatus = oaCarStatus;
	}

}