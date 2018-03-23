package com.pinhuba.core.pojo;

/**
 * 数据库表名：HRM_POST（工作岗位）
 */
public class HrmPost extends BaseBean implements java.io.Serializable {

	private static final long serialVersionUID = 1384631517138463523L;
	private String hrmPostId;             //岗位编号
	private String hrmPostName;           //岗位名称
	private String hrmPostEngname;        //英文名
	private String hrmPostUpid;           //上级岗位
	
	private HrmPost hrmUpPost;            //上级岗位对象
	
	private String hrmPostMang;           //岗位负责人
	
	private HrmEmployee mangerEmployee;   //负责人对象
	 
	private String hrmPostDesc;           //岗位描述
	private String recordId;              //记录人
	private String recordDate;            //记录时间
	private String lastmodiId;            //最后修改人
	private String lastmodiDate;          //最后修改时间
	private Integer companyId;            //公司ID
	private Integer hrmPostShowRow;			//显示顺序	

	// 默认构造方法
	public HrmPost() {
		super();
	}

	// get和set方法
	public String getHrmPostId() {
		return hrmPostId;
	}

	public void setHrmPostId(String aHrmPostId) {
		this.hrmPostId = aHrmPostId;
	}

	public String getHrmPostName() {
		return hrmPostName;
	}

	public void setHrmPostName(String aHrmPostName) {
		this.hrmPostName = aHrmPostName;
	}

	public String getHrmPostEngname() {
		return hrmPostEngname;
	}

	public void setHrmPostEngname(String aHrmPostEngname) {
		this.hrmPostEngname = aHrmPostEngname;
	}

	public String getHrmPostUpid() {
		return hrmPostUpid;
	}

	public void setHrmPostUpid(String aHrmPostUpid) {
		this.hrmPostUpid = aHrmPostUpid;
	}

	public String getHrmPostMang() {
		return hrmPostMang;
	}

	public void setHrmPostMang(String aHrmPostMang) {
		this.hrmPostMang = aHrmPostMang;
	}

	public String getHrmPostDesc() {
		return hrmPostDesc;
	}

	public void setHrmPostDesc(String aHrmPostDesc) {
		this.hrmPostDesc = aHrmPostDesc;
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

	public HrmEmployee getMangerEmployee() {
		return mangerEmployee;
	}

	public void setMangerEmployee(HrmEmployee mangerEmployee) {
		this.mangerEmployee = mangerEmployee;
	}

	public HrmPost getHrmUpPost() {
		return hrmUpPost;
	}

	public void setHrmUpPost(HrmPost hrmUpPost) {
		this.hrmUpPost = hrmUpPost;
	}

	public Integer getHrmPostShowRow() {
		return hrmPostShowRow;
	}

	public void setHrmPostShowRow(Integer hrmPostShowRow) {
		this.hrmPostShowRow = hrmPostShowRow;
	}

}