package com.pinhuba.core.pojo;

import com.pinhuba.core.pojo.BaseStringBean;
import com.pinhuba.core.pojo.HrmDepartment;
import com.pinhuba.core.pojo.HrmPost;
import com.pinhuba.core.pojo.HrmWorkarea;
import com.pinhuba.core.pojo.SysLibraryInfo;

/**
 * 数据库表名：HRM_EMPLOYEE（人员信息）
 */
public class HrmEmployee extends BaseStringBean implements java.io.Serializable {
	
	private static final long serialVersionUID = 8672492822060941704L;
	private String hrmEmployeeName;  //员工姓名
	private String hrmEmployeeEngname;  //英文名称
	private String hrmEmployeeSimple;  //姓名简写 自动生成
	private String hrmEmployeeCode;  //员工工号
	private String hrmEmployeePatternId;  //用工形式

	private SysLibraryInfo hrmEmployeePatternName;  //用工形式字典

	private String hrmEmployeeBirthday;  //生日
	private String hrmEmployeeSex;  //性别
	private String hrmEmployeeMarriage;  //婚姻状况

	private SysLibraryInfo hrmEmployeeMarriageName;  //婚姻状况字典

	private String hrmEmployeePolitics;  // 政治面貌
	private SysLibraryInfo hrmEmployeePoliticsName;  //政治面貌字典
	
	private String hrmEmployeeNationality;  //民族

	private SysLibraryInfo hrmEmployeeNationalityName;  //民族字典

	private String hrmEmployeeBloodType;  //血型

	private SysLibraryInfo hrmEmployeeBloodTypeName;  //血型字典

	private String hrmEmployeeHeight;  //身高
	private String hrmEmployeeWeight;  //体重
	private Integer hrmEmployeeImageInfoId = 0;  //照片
	private String hrmEmployeeSchool;  //毕业学校
	private String hrmEmployeeProfessional;  //专业

	private String hrmEmployeeDegree;  //学历

	private SysLibraryInfo hrmEmployeeDegreeName;  //学历字典

	private String hrmEmployeeIdentitycard;  //身份证号
	private String hrmEmployeeHometown;  //籍贯
	private String hrmEmployeeHousePhone;  //家庭电话
	private String hrmEmployeeHouseAddress;  //家庭地址
	private String hrmEmployeeMobileTele;  //移动电话
	private String hrmEmployeeWorkTele;  //工作电话
	private String hrmEmployeeInTime;  //入职日期
	private Integer hrmEmployeeDepid;  //所属部门

	private String hrmEmployeeDepidTree;
	
	
	private HrmDepartment hrmDepartment;  //

	private Integer hrmEmployeeWorkareaid;  //工作区域

	private HrmWorkarea hrmEmployeeWorkarea;  //工作区域表

	private Integer hrmEmployeePostId;  //工作岗位

	private HrmPost hrmEmployeePost;  //岗位表
	
	private Integer hrmEmployeeSquadId;  //工作班次
	
	private String hrmEmployeeEmail;  //邮件地址
	private String hrmEmployeeUrgentPreson;  //紧急联系人
	private String hrmEmployeeUrgentPhone;  //紧急电话
	private String hrmEmployeeAdderss;  //当前住址
	private String hrmEmployeeAppendid;  //附加描述
	private String recordId;  //
	private String recordDate;  //
	private String lastmodiId;
	private String lastmodiDate;
	private Integer companyId;
	private String hrmEmployeeWorkTime;  //转正日期
	private String hrmPartPost;  //兼职岗位
	private String hrmPartPostName;//兼职岗位名字
	private Integer hrmEmployeeStatus;  //入职状态 1试用 2正常 3离职
	private String hrmOtherAttachmen;  //附件
	private Integer hrmEmployeeActive;    //员工状态（1，有效 2，无效）
	private int[] count;//考勤数量 1,正常出勤2,迟到3,早退4,未考勤5,其他
	//临时变量
	private Integer isOnline;//是否在线
	
	private String employeeIds;
	
	private long groupId;
	private long roleId;
	
	public String getEmployeeIds() {
		return employeeIds;
	}

	public void setEmployeeIds(String employeeIds) {
		this.employeeIds = employeeIds;
	}

	public Integer getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(Integer isOnline) {
		this.isOnline = isOnline;
	}

	// 默认构造方法
	public HrmEmployee() {
		super();
	}
    
	//带参数构造方法
	public HrmEmployee(String hrmEmployeeName, String hrmEmployeeEngname,
			String hrmEmployeeSimple, String hrmEmployeeCode,
			String hrmEmployeePatternId, SysLibraryInfo hrmEmployeePatternName,
			String hrmEmployeeBirthday, String hrmEmployeeSex,
			String hrmEmployeeMarriage, SysLibraryInfo hrmEmployeeMarriageName,
			String hrmEmployeePolitics, SysLibraryInfo hrmEmployeePoliticsName,
			String hrmEmployeeNationality,
			SysLibraryInfo hrmEmployeeNationalityName,
			String hrmEmployeeBloodType,
			SysLibraryInfo hrmEmployeeBloodTypeName, String hrmEmployeeHeight,
			String hrmEmployeeWeight, Integer hrmEmployeeImageInfoId,
			String hrmEmployeeSchool, String hrmEmployeeProfessional,
			String hrmEmployeeDegree, SysLibraryInfo hrmEmployeeDegreeName,
			String hrmEmployeeIdentitycard, String hrmEmployeeHometown,
			String hrmEmployeeHousePhone, String hrmEmployeeHouseAddress,
			String hrmEmployeeMobileTele, String hrmEmployeeWorkTele,
			String hrmEmployeeInTime, Integer hrmEmployeeDepid,
			HrmDepartment hrmDepartment, Integer hrmEmployeeWorkareaid,
			HrmWorkarea hrmEmployeeWorkarea, Integer hrmEmployeePostId,
			HrmPost hrmEmployeePost, Integer hrmEmployeeSquadId,
			String hrmEmployeeEmail,
			String hrmEmployeeUrgentPreson, String hrmEmployeeUrgentPhone,
			String hrmEmployeeAdderss, String hrmEmployeeAppendid,
			String recordId, String recordDate, String lastmodiId,
			String lastmodiDate, Integer companyId, String hrmEmployeeWorkTime,
			String hrmPartPost, String hrmPartPostName,
			Integer hrmEmployeeStatus, String hrmOtherAttachmen,
			Integer hrmEmployeeActive) {
		super();
		this.hrmEmployeeName = hrmEmployeeName;
		this.hrmEmployeeEngname = hrmEmployeeEngname;
		this.hrmEmployeeSimple = hrmEmployeeSimple;
		this.hrmEmployeeCode = hrmEmployeeCode;
		this.hrmEmployeePatternId = hrmEmployeePatternId;
		this.hrmEmployeePatternName = hrmEmployeePatternName;
		this.hrmEmployeeBirthday = hrmEmployeeBirthday;
		this.hrmEmployeeSex = hrmEmployeeSex;
		this.hrmEmployeeMarriage = hrmEmployeeMarriage;
		this.hrmEmployeeMarriageName = hrmEmployeeMarriageName;
		this.hrmEmployeePolitics = hrmEmployeePolitics;
		this.hrmEmployeePoliticsName = hrmEmployeePoliticsName;
		this.hrmEmployeeNationality = hrmEmployeeNationality;
		this.hrmEmployeeNationalityName = hrmEmployeeNationalityName;
		this.hrmEmployeeBloodType = hrmEmployeeBloodType;
		this.hrmEmployeeBloodTypeName = hrmEmployeeBloodTypeName;
		this.hrmEmployeeHeight = hrmEmployeeHeight;
		this.hrmEmployeeWeight = hrmEmployeeWeight;
		this.hrmEmployeeImageInfoId = hrmEmployeeImageInfoId;
		this.hrmEmployeeSchool = hrmEmployeeSchool;
		this.hrmEmployeeProfessional = hrmEmployeeProfessional;
		this.hrmEmployeeDegree = hrmEmployeeDegree;
		this.hrmEmployeeDegreeName = hrmEmployeeDegreeName;
		this.hrmEmployeeIdentitycard = hrmEmployeeIdentitycard;
		this.hrmEmployeeHometown = hrmEmployeeHometown;
		this.hrmEmployeeHousePhone = hrmEmployeeHousePhone;
		this.hrmEmployeeHouseAddress = hrmEmployeeHouseAddress;
		this.hrmEmployeeMobileTele = hrmEmployeeMobileTele;
		this.hrmEmployeeWorkTele = hrmEmployeeWorkTele;
		this.hrmEmployeeInTime = hrmEmployeeInTime;
		this.hrmEmployeeDepid = hrmEmployeeDepid;
		this.hrmDepartment = hrmDepartment;
		this.hrmEmployeeWorkareaid = hrmEmployeeWorkareaid;
		this.hrmEmployeeWorkarea = hrmEmployeeWorkarea;
		this.hrmEmployeePostId = hrmEmployeePostId;
		this.hrmEmployeePost = hrmEmployeePost;
		this.hrmEmployeeSquadId = hrmEmployeeSquadId;
		this.hrmEmployeeEmail = hrmEmployeeEmail;
		this.hrmEmployeeUrgentPreson = hrmEmployeeUrgentPreson;
		this.hrmEmployeeUrgentPhone = hrmEmployeeUrgentPhone;
		this.hrmEmployeeAdderss = hrmEmployeeAdderss;
		this.hrmEmployeeAppendid = hrmEmployeeAppendid;
		this.recordId = recordId;
		this.recordDate = recordDate;
		this.lastmodiId = lastmodiId;
		this.lastmodiDate = lastmodiDate;
		this.companyId = companyId;
		this.hrmEmployeeWorkTime = hrmEmployeeWorkTime;
		this.hrmPartPost = hrmPartPost;
		this.hrmPartPostName = hrmPartPostName;
		this.hrmEmployeeStatus = hrmEmployeeStatus;
		this.hrmOtherAttachmen = hrmOtherAttachmen;
		this.hrmEmployeeActive = hrmEmployeeActive;
	}

	//get和set方法
	public Integer getHrmEmployeeSquadId() {
		return hrmEmployeeSquadId;
	}

	public String getHrmEmployeeDepidTree() {
		return hrmEmployeeDepidTree;
	}

	public void setHrmEmployeeDepidTree(String hrmEmployeeDepidTree) {
		this.hrmEmployeeDepidTree = hrmEmployeeDepidTree;
	}

	public void setHrmEmployeeSquadId(Integer hrmEmployeeSquadId) {
		this.hrmEmployeeSquadId = hrmEmployeeSquadId;
	}

	public String getHrmPartPostName() {
		return hrmPartPostName;
	}

	public void setHrmPartPostName(String hrmPartPostName) {
		this.hrmPartPostName = hrmPartPostName;
	}

	public Integer getHrmEmployeeWorkareaid() {
		return hrmEmployeeWorkareaid;
	}

	public void setHrmEmployeeWorkareaid(Integer hrmEmployeeWorkareaid) {
		this.hrmEmployeeWorkareaid = hrmEmployeeWorkareaid;
	}

	public HrmWorkarea getHrmEmployeeWorkarea() {
		return hrmEmployeeWorkarea;
	}

	public void setHrmEmployeeWorkarea(HrmWorkarea hrmEmployeeWorkarea) {
		this.hrmEmployeeWorkarea = hrmEmployeeWorkarea;
	}

	public SysLibraryInfo getHrmEmployeePoliticsName() {
		return hrmEmployeePoliticsName;
	}

	public void setHrmEmployeePoliticsName(SysLibraryInfo hrmEmployeePoliticsName) {
		this.hrmEmployeePoliticsName = hrmEmployeePoliticsName;
	}

	public String getHrmEmployeeName() {
		return hrmEmployeeName;
	}

	public void setHrmEmployeeName(String hrmEmployeeName) {
		this.hrmEmployeeName = hrmEmployeeName;
	}

	public String getHrmEmployeeEngname() {
		return hrmEmployeeEngname;
	}

	public void setHrmEmployeeEngname(String hrmEmployeeEngname) {
		this.hrmEmployeeEngname = hrmEmployeeEngname;
	}

	public String getHrmEmployeeSimple() {
		return hrmEmployeeSimple;
	}

	public void setHrmEmployeeSimple(String hrmEmployeeSimple) {
		this.hrmEmployeeSimple = hrmEmployeeSimple;
	}

	public String getHrmEmployeeCode() {
		return hrmEmployeeCode;
	}

	public void setHrmEmployeeCode(String hrmEmployeeCode) {
		this.hrmEmployeeCode = hrmEmployeeCode;
	}

	public String getHrmEmployeePatternId() {
		return hrmEmployeePatternId;
	}

	public void setHrmEmployeePatternId(String hrmEmployeePatternId) {
		this.hrmEmployeePatternId = hrmEmployeePatternId;
	}

	public SysLibraryInfo getHrmEmployeePatternName() {
		return hrmEmployeePatternName;
	}

	public void setHrmEmployeePatternName(SysLibraryInfo hrmEmployeePatternName) {
		this.hrmEmployeePatternName = hrmEmployeePatternName;
	}

	public String getHrmEmployeeBirthday() {
		return hrmEmployeeBirthday;
	}

	public void setHrmEmployeeBirthday(String hrmEmployeeBirthday) {
		this.hrmEmployeeBirthday = hrmEmployeeBirthday;
	}

	public String getHrmEmployeeSex() {
		return hrmEmployeeSex;
	}

	public void setHrmEmployeeSex(String hrmEmployeeSex) {
		this.hrmEmployeeSex = hrmEmployeeSex;
	}

	public String getHrmEmployeeMarriage() {
		return hrmEmployeeMarriage;
	}

	public void setHrmEmployeeMarriage(String hrmEmployeeMarriage) {
		this.hrmEmployeeMarriage = hrmEmployeeMarriage;
	}

	public SysLibraryInfo getHrmEmployeeMarriageName() {
		return hrmEmployeeMarriageName;
	}

	public void setHrmEmployeeMarriageName(SysLibraryInfo hrmEmployeeMarriageName) {
		this.hrmEmployeeMarriageName = hrmEmployeeMarriageName;
	}

	public String getHrmEmployeePolitics() {
		return hrmEmployeePolitics;
	}

	public void setHrmEmployeePolitics(String hrmEmployeePolitics) {
		this.hrmEmployeePolitics = hrmEmployeePolitics;
	}

	public String getHrmEmployeeNationality() {
		return hrmEmployeeNationality;
	}

	public void setHrmEmployeeNationality(String hrmEmployeeNationality) {
		this.hrmEmployeeNationality = hrmEmployeeNationality;
	}

	public SysLibraryInfo getHrmEmployeeNationalityName() {
		return hrmEmployeeNationalityName;
	}

	public void setHrmEmployeeNationalityName(SysLibraryInfo hrmEmployeeNationalityName) {
		this.hrmEmployeeNationalityName = hrmEmployeeNationalityName;
	}

	public String getHrmEmployeeBloodType() {
		return hrmEmployeeBloodType;
	}

	public void setHrmEmployeeBloodType(String hrmEmployeeBloodType) {
		this.hrmEmployeeBloodType = hrmEmployeeBloodType;
	}

	public SysLibraryInfo getHrmEmployeeBloodTypeName() {
		return hrmEmployeeBloodTypeName;
	}

	public void setHrmEmployeeBloodTypeName(SysLibraryInfo hrmEmployeeBloodTypeName) {
		this.hrmEmployeeBloodTypeName = hrmEmployeeBloodTypeName;
	}

	public String getHrmEmployeeHeight() {
		return hrmEmployeeHeight;
	}

	public void setHrmEmployeeHeight(String hrmEmployeeHeight) {
		this.hrmEmployeeHeight = hrmEmployeeHeight;
	}

	public String getHrmEmployeeWeight() {
		return hrmEmployeeWeight;
	}

	public void setHrmEmployeeWeight(String hrmEmployeeWeight) {
		this.hrmEmployeeWeight = hrmEmployeeWeight;
	}

	public Integer getHrmEmployeeImageInfoId() {
		return hrmEmployeeImageInfoId;
	}

	public void setHrmEmployeeImageInfoId(Integer hrmEmployeeImageInfoId) {
		this.hrmEmployeeImageInfoId = hrmEmployeeImageInfoId;
	}

	public String getHrmEmployeeSchool() {
		return hrmEmployeeSchool;
	}

	public void setHrmEmployeeSchool(String hrmEmployeeSchool) {
		this.hrmEmployeeSchool = hrmEmployeeSchool;
	}

	public String getHrmEmployeeProfessional() {
		return hrmEmployeeProfessional;
	}

	public void setHrmEmployeeProfessional(String hrmEmployeeProfessional) {
		this.hrmEmployeeProfessional = hrmEmployeeProfessional;
	}

	public String getHrmEmployeeDegree() {
		return hrmEmployeeDegree;
	}

	public void setHrmEmployeeDegree(String hrmEmployeeDegree) {
		this.hrmEmployeeDegree = hrmEmployeeDegree;
	}

	public SysLibraryInfo getHrmEmployeeDegreeName() {
		return hrmEmployeeDegreeName;
	}

	public void setHrmEmployeeDegreeName(SysLibraryInfo hrmEmployeeDegreeName) {
		this.hrmEmployeeDegreeName = hrmEmployeeDegreeName;
	}

	public String getHrmEmployeeIdentitycard() {
		return hrmEmployeeIdentitycard;
	}

	public void setHrmEmployeeIdentitycard(String hrmEmployeeIdentitycard) {
		this.hrmEmployeeIdentitycard = hrmEmployeeIdentitycard;
	}

	public String getHrmEmployeeHometown() {
		return hrmEmployeeHometown;
	}

	public void setHrmEmployeeHometown(String hrmEmployeeHometown) {
		this.hrmEmployeeHometown = hrmEmployeeHometown;
	}

	public String getHrmEmployeeHousePhone() {
		return hrmEmployeeHousePhone;
	}

	public void setHrmEmployeeHousePhone(String hrmEmployeeHousePhone) {
		this.hrmEmployeeHousePhone = hrmEmployeeHousePhone;
	}

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

	public Integer getHrmEmployeeDepid() {
		return hrmEmployeeDepid;
	}

	public void setHrmEmployeeDepid(Integer hrmEmployeeDepid) {
		this.hrmEmployeeDepid = hrmEmployeeDepid;
	}

	public HrmDepartment getHrmDepartment() {
		return hrmDepartment;
	}

	public void setHrmDepartment(HrmDepartment hrmDepartment) {
		this.hrmDepartment = hrmDepartment;
	}


	public Integer getHrmEmployeePostId() {
		return hrmEmployeePostId;
	}

	public void setHrmEmployeePostId(Integer hrmEmployeePostId) {
		this.hrmEmployeePostId = hrmEmployeePostId;
	}

	public HrmPost getHrmEmployeePost() {
		return hrmEmployeePost;
	}

	public void setHrmEmployeePost(HrmPost hrmEmployeePost) {
		this.hrmEmployeePost = hrmEmployeePost;
	}

	public String getHrmEmployeeEmail() {
		return hrmEmployeeEmail;
	}

	public void setHrmEmployeeEmail(String hrmEmployeeEmail) {
		this.hrmEmployeeEmail = hrmEmployeeEmail;
	}

	public String getHrmEmployeeUrgentPreson() {
		return hrmEmployeeUrgentPreson;
	}

	public void setHrmEmployeeUrgentPreson(String hrmEmployeeUrgentPreson) {
		this.hrmEmployeeUrgentPreson = hrmEmployeeUrgentPreson;
	}

	public String getHrmEmployeeUrgentPhone() {
		return hrmEmployeeUrgentPhone;
	}

	public void setHrmEmployeeUrgentPhone(String hrmEmployeeUrgentPhone) {
		this.hrmEmployeeUrgentPhone = hrmEmployeeUrgentPhone;
	}

	public String getHrmEmployeeAdderss() {
		return hrmEmployeeAdderss;
	}

	public void setHrmEmployeeAdderss(String hrmEmployeeAdderss) {
		this.hrmEmployeeAdderss = hrmEmployeeAdderss;
	}

	public String getHrmEmployeeAppendid() {
		return hrmEmployeeAppendid;
	}

	public void setHrmEmployeeAppendid(String hrmEmployeeAppendid) {
		this.hrmEmployeeAppendid = hrmEmployeeAppendid;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate;
	}

	public String getLastmodiId() {
		return lastmodiId;
	}

	public void setLastmodiId(String lastmodiId) {
		this.lastmodiId = lastmodiId;
	}

	public String getLastmodiDate() {
		return lastmodiDate;
	}

	public void setLastmodiDate(String lastmodiDate) {
		this.lastmodiDate = lastmodiDate;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getHrmEmployeeWorkTime() {
		return hrmEmployeeWorkTime;
	}

	public void setHrmEmployeeWorkTime(String hrmEmployeeWorkTime) {
		this.hrmEmployeeWorkTime = hrmEmployeeWorkTime;
	}

	public String getHrmPartPost() {
		return hrmPartPost;
	}

	public void setHrmPartPost(String hrmPartPost) {
		this.hrmPartPost = hrmPartPost;
	}

	public Integer getHrmEmployeeStatus() {
		return hrmEmployeeStatus;
	}

	public void setHrmEmployeeStatus(Integer hrmEmployeeStatus) {
		this.hrmEmployeeStatus = hrmEmployeeStatus;
	}

	public String getHrmOtherAttachmen() {
		return hrmOtherAttachmen;
	}

	public void setHrmOtherAttachmen(String hrmOtherAttachmen) {
		this.hrmOtherAttachmen = hrmOtherAttachmen;
	}

	public Integer getHrmEmployeeActive() {
		return hrmEmployeeActive;
	}

	public void setHrmEmployeeActive(Integer hrmEmployeeActive) {
		this.hrmEmployeeActive = hrmEmployeeActive;
	}

	public int[] getCount() {
		return count;
	}

	public void setCount(int[] count) {
		this.count = count;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
}