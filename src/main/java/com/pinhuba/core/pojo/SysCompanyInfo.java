package com.pinhuba.core.pojo;

/**
 * 数据库表名：SYS_COMPANY_INFO
 */
public class SysCompanyInfo extends BaseBean implements java.io.Serializable {

	/**
	 * 公司信息表
	 */
	private static final long serialVersionUID = 7202944412754932083L;
	private String provinceCode;					//所在省
	private String districtCode;					//所在市
	private String companyArea;						//所在区
	private String companyInfoName;					//公司全称
	private String companyInfoShortname;			//公司简称
	private String companyInfoAdder;				//公司地址
	private String companyInfoPost;					//公司地址
	private String companyInfoEmployee;				//联系人
	private String companyInfoEmployeePosition;		//联系人职务
	private String companyInfoEmail;				//电子邮箱
	private String companyInfoPhone;				//联系电话
	private String companyInfoFax;					//传真
	private Integer companyInfoStatus;				//状态
	private Integer companyInfoType;				//类型
	private String companyInfoCode;					//公司编码
	private String companyInfoSdate;				//有效期开始日期
	private String companyInfoEdate;				//有效期结束日期
	private Integer companyInfoUsercount;			//限定用户数量
	private Integer companyInfoWarehousecount;		//限定仓库数量
	private String companyInfoContext;				//附加说明
	private String companyInfoRegDate;				//注册时间
	private String companyInfoLastDate;				//最后修改时间
	private Integer companyInfoLogin;				//公司login
	private String companyInfoTitle;				//公司系统标题
	private String companyInfoEnTitle;				//公司系统英文标题
	

	public String getCompanyInfoTitle() {
		return companyInfoTitle;
	}

	public String getCompanyInfoEnTitle() {
		return companyInfoEnTitle;
	}

	public void setCompanyInfoTitle(String companyInfoTitle) {
		this.companyInfoTitle = companyInfoTitle;
	}

	public void setCompanyInfoEnTitle(String companyInfoEnTitle) {
		this.companyInfoEnTitle = companyInfoEnTitle;
	}

	public String getCompanyInfoLastDate() {
		return companyInfoLastDate;
	}

	public void setCompanyInfoLastDate(String companyInfoLastDate) {
		this.companyInfoLastDate = companyInfoLastDate;
	}

	public String getCompanyInfoRegDate() {
		return companyInfoRegDate;
	}

	public void setCompanyInfoRegDate(String companyInfoRegDate) {
		this.companyInfoRegDate = companyInfoRegDate;
	}

	// 默认构造方法
	public SysCompanyInfo() {
		super();
	}

	// get和set方法
	
	
	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String aProvinceCode) {
		this.provinceCode = aProvinceCode;
	}

	

	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}

	public String getCompanyArea() {
		return companyArea;
	}

	public void setCompanyArea(String companyArea) {
		this.companyArea = companyArea;
	}

	public String getCompanyInfoName() {
		return companyInfoName;
	}

	public void setCompanyInfoName(String aCompanyInfoName) {
		this.companyInfoName = aCompanyInfoName;
	}

	public String getCompanyInfoShortname() {
		return companyInfoShortname;
	}

	public void setCompanyInfoShortname(String aCompanyInfoShortname) {
		this.companyInfoShortname = aCompanyInfoShortname;
	}

	public String getCompanyInfoAdder() {
		return companyInfoAdder;
	}

	public void setCompanyInfoAdder(String aCompanyInfoAdder) {
		this.companyInfoAdder = aCompanyInfoAdder;
	}

	public String getCompanyInfoPost() {
		return companyInfoPost;
	}

	public void setCompanyInfoPost(String aCompanyInfoPost) {
		this.companyInfoPost = aCompanyInfoPost;
	}

	public String getCompanyInfoEmployee() {
		return companyInfoEmployee;
	}

	public void setCompanyInfoEmployee(String aCompanyInfoEmployee) {
		this.companyInfoEmployee = aCompanyInfoEmployee;
	}

	public String getCompanyInfoEmployeePosition() {
		return companyInfoEmployeePosition;
	}

	public void setCompanyInfoEmployeePosition(String aCompanyInfoEmployeePosition) {
		this.companyInfoEmployeePosition = aCompanyInfoEmployeePosition;
	}

	public String getCompanyInfoEmail() {
		return companyInfoEmail;
	}

	public void setCompanyInfoEmail(String aCompanyInfoEmail) {
		this.companyInfoEmail = aCompanyInfoEmail;
	}

	public String getCompanyInfoPhone() {
		return companyInfoPhone;
	}

	public void setCompanyInfoPhone(String aCompanyInfoPhone) {
		this.companyInfoPhone = aCompanyInfoPhone;
	}

	public String getCompanyInfoFax() {
		return companyInfoFax;
	}

	public void setCompanyInfoFax(String aCompanyInfoFax) {
		this.companyInfoFax = aCompanyInfoFax;
	}

	public Integer getCompanyInfoStatus() {
		return companyInfoStatus;
	}

	public void setCompanyInfoStatus(Integer aCompanyInfoStatus) {
		this.companyInfoStatus = aCompanyInfoStatus;
	}

	public Integer getCompanyInfoType() {
		return companyInfoType;
	}

	public void setCompanyInfoType(Integer aCompanyInfoType) {
		this.companyInfoType = aCompanyInfoType;
	}

	public String getCompanyInfoCode() {
		return companyInfoCode;
	}

	public void setCompanyInfoCode(String aCompanyInfoCode) {
		this.companyInfoCode = aCompanyInfoCode;
	}

	public String getCompanyInfoSdate() {
		return companyInfoSdate;
	}

	public void setCompanyInfoSdate(String aCompanyInfoSdate) {
		this.companyInfoSdate = aCompanyInfoSdate;
	}

	public String getCompanyInfoEdate() {
		return companyInfoEdate;
	}

	public void setCompanyInfoEdate(String aCompanyInfoEdate) {
		this.companyInfoEdate = aCompanyInfoEdate;
	}

	public Integer getCompanyInfoUsercount() {
		return companyInfoUsercount;
	}

	public void setCompanyInfoUsercount(Integer aCompanyInfoUsercount) {
		this.companyInfoUsercount = aCompanyInfoUsercount;
	}

	public Integer getCompanyInfoWarehousecount() {
		return companyInfoWarehousecount;
	}

	public void setCompanyInfoWarehousecount(Integer aCompanyInfoWarehousecount) {
		this.companyInfoWarehousecount = aCompanyInfoWarehousecount;
	}

	public String getCompanyInfoContext() {
		return companyInfoContext;
	}

	public void setCompanyInfoContext(String aCompanyInfoContext) {
		this.companyInfoContext = aCompanyInfoContext;
	}

	public Integer getCompanyInfoLogin() {
		return companyInfoLogin;
	}

	public void setCompanyInfoLogin(Integer companyInfoLogin) {
		this.companyInfoLogin = companyInfoLogin;
	}

}