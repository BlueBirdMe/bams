package com.pinhuba.core.pojo;

/**
 * 数据库表名：OA_MEETAPPLY
 */
public class OaMeetapply extends BaseBean implements java.io.Serializable {

	/**
	 * 会议申请表
	 */
	private static final long serialVersionUID = -4721469721726552349L;
	private String oaMeetapplyName;		//会议名称					
	private String oaMeetapplyTheme;   	//会议主题
	private String oaMeetapplyEmp;	   	//会议申请人
	private String oaMeetapplyDate;	  	//会议申请时间
	private String oaMeetapplyDep;    	//主办部门
	private String oaMeetapplyEmpw;     //外部出席人员
	private String oaMeetapplyEmpn;		//内部出席人员
	private String oaMeetapplySummary;  //会议纪要人
	private Integer oaMeetapplyRoom;    //会议室
	private String oaMeetapplyStar;     //会议开始时间
	private String oaMeetapplyEnd;      //会议结束时间
	private String oaMeetapplyRes;
	private Integer oaMeetapplyType;    //会议类型
	private Integer oaMeetapplyStatus;  //会议状态
	private String oaMeetapplyDescribe; //会议描述
	private String oaMeetapplyAffix;	//会议附件
	private String oaMeetapplyAwoke;	//是否提醒
	private Integer oaMeetapplyDegree;  //重要程度
	private String recordId;
	private String recordDate;
	private String lastmodiId;
	private String lastmodiDate;
	private Integer companyId;
	private SysLibraryInfo library; //会议类型
	private HrmEmployee employee;   //申请人对象
	private String  jiyaoEmpNames;  //存储纪要人姓名
    private String  zhubanDep;      //主办部门名称集合
    private String  chuxiEmpName;   //内部出席人员
    private OaBoardroom meetApplyRoomObj;     //会议室对象
    private String summaryCount;     //纪要数量
   
	// 默认构造方法
	public OaMeetapply() {
		super();
	}

	// get和set方法
	public String getOaMeetapplyName() {
		return oaMeetapplyName;
	}

	public void setOaMeetapplyName(String aOaMeetapplyName) {
		this.oaMeetapplyName = aOaMeetapplyName;
	}

	public String getOaMeetapplyTheme() {
		return oaMeetapplyTheme;
	}

	public void setOaMeetapplyTheme(String aOaMeetapplyTheme) {
		this.oaMeetapplyTheme = aOaMeetapplyTheme;
	}

	public String getOaMeetapplyEmp() {
		return oaMeetapplyEmp;
	}

	public void setOaMeetapplyEmp(String aOaMeetapplyEmp) {
		this.oaMeetapplyEmp = aOaMeetapplyEmp;
	}

	public String getOaMeetapplyDate() {
		return oaMeetapplyDate;
	}

	public void setOaMeetapplyDate(String aOaMeetapplyDate) {
		this.oaMeetapplyDate = aOaMeetapplyDate;
	}

	public String getOaMeetapplyDep() {
		return oaMeetapplyDep;
	}

	public void setOaMeetapplyDep(String aOaMeetapplyDep) {
		this.oaMeetapplyDep = aOaMeetapplyDep;
	}

	public String getOaMeetapplyEmpw() {
		return oaMeetapplyEmpw;
	}

	public void setOaMeetapplyEmpw(String aOaMeetapplyEmpw) {
		this.oaMeetapplyEmpw = aOaMeetapplyEmpw;
	}

	public String getOaMeetapplyEmpn() {
		return oaMeetapplyEmpn;
	}

	public void setOaMeetapplyEmpn(String aOaMeetapplyEmpn) {
		this.oaMeetapplyEmpn = aOaMeetapplyEmpn;
	}

	public String getOaMeetapplySummary() {
		return oaMeetapplySummary;
	}

	public void setOaMeetapplySummary(String aOaMeetapplySummary) {
		this.oaMeetapplySummary = aOaMeetapplySummary;
	}

	public Integer getOaMeetapplyRoom() {
		return oaMeetapplyRoom;
	}

	public void setOaMeetapplyRoom(Integer oaMeetapplyRoom) {
		this.oaMeetapplyRoom = oaMeetapplyRoom;
	}

	public OaBoardroom getMeetApplyRoomObj() {
		return meetApplyRoomObj;
	}

	public void setMeetApplyRoomObj(OaBoardroom meetApplyRoomObj) {
		this.meetApplyRoomObj = meetApplyRoomObj;
	}

	public String getOaMeetapplyStar() {
		return oaMeetapplyStar;
	}

	public void setOaMeetapplyStar(String aOaMeetapplyStar) {
		this.oaMeetapplyStar = aOaMeetapplyStar;
	}

	public String getOaMeetapplyEnd() {
		return oaMeetapplyEnd;
	}

	public void setOaMeetapplyEnd(String aOaMeetapplyEnd) {
		this.oaMeetapplyEnd = aOaMeetapplyEnd;
	}

	public String getOaMeetapplyRes() {
		return oaMeetapplyRes;
	}

	public void setOaMeetapplyRes(String aOaMeetapplyRes) {
		this.oaMeetapplyRes = aOaMeetapplyRes;
	}

	public Integer getOaMeetapplyType() {
		return oaMeetapplyType;
	}

	public void setOaMeetapplyType(Integer aOaMeetapplyType) {
		this.oaMeetapplyType = aOaMeetapplyType;
	}

	public Integer getOaMeetapplyStatus() {
		return oaMeetapplyStatus;
	}

	public void setOaMeetapplyStatus(Integer aOaMeetapplyStatus) {
		this.oaMeetapplyStatus = aOaMeetapplyStatus;
	}

	public String getOaMeetapplyDescribe() {
		return oaMeetapplyDescribe;
	}

	public void setOaMeetapplyDescribe(String aOaMeetapplyDescribe) {
		this.oaMeetapplyDescribe = aOaMeetapplyDescribe;
	}

	public String getOaMeetapplyAffix() {
		return oaMeetapplyAffix;
	}

	public void setOaMeetapplyAffix(String aOaMeetapplyAffix) {
		this.oaMeetapplyAffix = aOaMeetapplyAffix;
	}

	public String getOaMeetapplyAwoke() {
		return oaMeetapplyAwoke;
	}

	public void setOaMeetapplyAwoke(String aOaMeetapplyAwoke) {
		this.oaMeetapplyAwoke = aOaMeetapplyAwoke;
	}

	public Integer getOaMeetapplyDegree() {
		return oaMeetapplyDegree;
	}

	public void setOaMeetapplyDegree(Integer aOaMeetapplyDegree) {
		this.oaMeetapplyDegree = aOaMeetapplyDegree;
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

	public SysLibraryInfo getLibrary() {
		return library;
	}

	public void setLibrary(SysLibraryInfo library) {
		this.library = library;
	}

	public HrmEmployee getEmployee() {
		return employee;
	}

	public void setEmployee(HrmEmployee employee) {
		this.employee = employee;
	}

	public String getJiyaoEmpNames() {
		return jiyaoEmpNames;
	}

	public void setJiyaoEmpNames(String jiyaoEmpNames) {
		this.jiyaoEmpNames = jiyaoEmpNames;
	}

	public String getZhubanDep() {
		return zhubanDep;
	}

	public void setZhubanDep(String zhubanDep) {
		this.zhubanDep = zhubanDep;
	}

	public String getChuxiEmpName() {
		return chuxiEmpName;
	}

	public void setChuxiEmpName(String chuxiEmpName) {
		this.chuxiEmpName = chuxiEmpName;
	}

	public String getSummaryCount() {
		return summaryCount;
	}

	public void setSummaryCount(String summaryCount) {
		this.summaryCount = summaryCount;
	}

	
	
}