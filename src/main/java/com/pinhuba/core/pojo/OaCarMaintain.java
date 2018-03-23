package com.pinhuba.core.pojo;

/**
 * 数据库表名：OA_CAR_MAINTAIN
 */
public class OaCarMaintain extends BaseBean implements java.io.Serializable {

	/**
	 * 车辆维修表
	 */
	private static final long serialVersionUID = 8727955966742518414L;
	private Integer carId;					//车辆ID
	private String maintainUser;			//维修人
	private HrmEmployee applyEmployee;	//申请人对象
	private String maintainMoney;			//维修金额
	private String maintainDate;			//维修时间
	private Integer maintainType;			//维修类型
	private String maintainAppendnews;		//维修原因
	private String recordId;
	private String recordDate;
	private String lastmodiId;
	private String lastmodiDate;
	private Integer companyId;
	private SysLibraryInfo library;

	private String libraryName;

	public String getLibraryName() {
		return libraryName;
	}

	public void setLibraryName(String libraryName) {
		this.libraryName = libraryName;
	}

	public SysLibraryInfo getLibrary() {
		return library;
	}

	public void setLibrary(SysLibraryInfo library) {
		this.library = library;
	}

	// 默认构造方法
	public OaCarMaintain() {
		super();
	}

	// get和set方法
	public Integer getCarId() {
		return carId;
	}

	public void setCarId(Integer aCarId) {
		this.carId = aCarId;
	}

	public String getMaintainMoney() {
		return maintainMoney;
	}

	public void setMaintainMoney(String aMaintainMoney) {
		this.maintainMoney = aMaintainMoney;
	}

	public String getMaintainDate() {
		return maintainDate;
	}

	public void setMaintainDate(String aMaintainDate) {
		this.maintainDate = aMaintainDate;
	}

	public Integer getMaintainType() {
		return maintainType;
	}

	public void setMaintainType(Integer aMaintainType) {
		this.maintainType = aMaintainType;
	}

	public String getMaintainAppendnews() {
		return maintainAppendnews;
	}

	public void setMaintainAppendnews(String aMaintainAppendnews) {
		this.maintainAppendnews = aMaintainAppendnews;
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

	public String getMaintainUser() {
		return maintainUser;
	}

	public void setMaintainUser(String maintainUser) {
		this.maintainUser = maintainUser;
	}

	public HrmEmployee getApplyEmployee() {
		return applyEmployee;
	}

	public void setApplyEmployee(HrmEmployee applyEmployee) {
		this.applyEmployee = applyEmployee;
	}

}