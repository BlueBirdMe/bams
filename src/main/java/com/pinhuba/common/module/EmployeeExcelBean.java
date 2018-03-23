package com.pinhuba.common.module;

/**
 * 人员批量导入临时存储
 * @author JC
 * @date   May 24, 2011
 */
public class EmployeeExcelBean {
	//必填
	private String hrmEmployeeName;         //员工姓名
	private String hrmEmployeeSex;			//性别
	private String hrmEmployeeCode;         //员工工号
	private String hrmEmployeeBirthday;     //生日,出生日期
	private String hrmEmployeeStatus; 		//入职状态 1试用 2正常 3离职
	private String hrmEmployeeActive;     	//员工状态（1，有效 2，无效）
	//选填
	private String hrmEmployeeIdentitycard; //身份证号
	private String hrmEmployeeHouseAddress; //家庭地址
	private String hrmEmployeeMobileTele;   //移动电话
	private String hrmEmployeeWorkTele;     //工作电话
	private String hrmEmployeeInTime;       //入职日期
	private String hrmEmployeeWorkTime;     //转正日期
	
	private String impMsg;

	
	public String getHrmEmployeeHouseAddress() {
		return hrmEmployeeHouseAddress;
	}

	public void setHrmEmployeeHouseAddress(String hrmEmployeeHouseAddress) {
		this.hrmEmployeeHouseAddress = hrmEmployeeHouseAddress;
	}

	public String getHrmEmployeeMobileTele() {
		return hrmEmployeeMobileTele;
	}

	public void setHrmEmployeeMobileTele(String hrmEmployeeMobileTele) {
		this.hrmEmployeeMobileTele = hrmEmployeeMobileTele;
	}

	public String getHrmEmployeeWorkTele() {
		return hrmEmployeeWorkTele;
	}

	public void setHrmEmployeeWorkTele(String hrmEmployeeWorkTele) {
		this.hrmEmployeeWorkTele = hrmEmployeeWorkTele;
	}

	public String getHrmEmployeeInTime() {
		return hrmEmployeeInTime;
	}

	public void setHrmEmployeeInTime(String hrmEmployeeInTime) {
		this.hrmEmployeeInTime = hrmEmployeeInTime;
	}

	public String getHrmEmployeeWorkTime() {
		return hrmEmployeeWorkTime;
	}

	public void setHrmEmployeeWorkTime(String hrmEmployeeWorkTime) {
		this.hrmEmployeeWorkTime = hrmEmployeeWorkTime;
	}

	public String getHrmEmployeeActive() {
		return hrmEmployeeActive;
	}

	public void setHrmEmployeeActive(String hrmEmployeeActive) {
		this.hrmEmployeeActive = hrmEmployeeActive;
	}

	public String getHrmEmployeeSex() {
		return hrmEmployeeSex;
	}

	public void setHrmEmployeeSex(String hrmEmployeeSex) {
		this.hrmEmployeeSex = hrmEmployeeSex;
	}

	public String getHrmEmployeeName() {
		return hrmEmployeeName;
	}

	public void setHrmEmployeeName(String hrmEmployeeName) {
		this.hrmEmployeeName = hrmEmployeeName;
	}

	public String getHrmEmployeeBirthday() {
		return hrmEmployeeBirthday;
	}

	public void setHrmEmployeeBirthday(String hrmEmployeeBirthday) {
		this.hrmEmployeeBirthday = hrmEmployeeBirthday;
	}

	public String getHrmEmployeeIdentitycard() {
		return hrmEmployeeIdentitycard;
	}

	public void setHrmEmployeeIdentitycard(String hrmEmployeeIdentitycard) {
		this.hrmEmployeeIdentitycard = hrmEmployeeIdentitycard;
	}

	public String getHrmEmployeeCode() {
		return hrmEmployeeCode;
	}

	public void setHrmEmployeeCode(String hrmEmployeeCode) {
		this.hrmEmployeeCode = hrmEmployeeCode;
	}

	public String getHrmEmployeeStatus() {
		return hrmEmployeeStatus;
	}

	public void setHrmEmployeeStatus(String hrmEmployeeStatus) {
		this.hrmEmployeeStatus = hrmEmployeeStatus;
	}

	public String getImpMsg() {
		return impMsg;
	}

	public void setImpMsg(String impMsg) {
		this.impMsg = impMsg;
	}

}
