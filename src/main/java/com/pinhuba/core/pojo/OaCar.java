package com.pinhuba.core.pojo;

/**
 * 数据库表名：OA_CAR
 */
public class OaCar extends BaseBean implements java.io.Serializable {

	/**
	 * 车辆数据表
	 */
	private static final long serialVersionUID = 3287750036564128699L;
	private String oaCarName;      //名称
	private String oaCarCards;	   //车牌号
	private String oaCarModel;	   
	private Integer oaCarSta;	   //状态
	private Integer oaCarType;		//维修状态
	private String oaCarMete;		
	private String oaCarMax;		//最大载重
	private String oaCarMotorman;	//司机
	private Double oaCarPrice; 		//价格
	private String oaCarPhoto;		//图片
	private String oaCarBuydate;	//购买时间
	private String oaCarRemark;  	//备注
	private String recordId;
	private String recordDate;
	private String lastmodiId;
	private String lastmodiDate;
	private Integer companyId;
	private SysLibraryInfo library;

	private Integer oaCarStatus;

	public Integer getOaCarStatus() {
		return oaCarStatus;
	}

	public void setOaCarStatus(Integer oaCarStatus) {
		this.oaCarStatus = oaCarStatus;
	}

	public SysLibraryInfo getLibrary() {
		return library;
	}

	public void setLibrary(SysLibraryInfo library) {
		this.library = library;
	}

	// 默认构造方法
	public OaCar() {
		super();
	}

	// get和set方法
	public String getOaCarName() {
		return oaCarName;
	}

	public void setOaCarName(String aOaCarName) {
		this.oaCarName = aOaCarName;
	}

	public String getOaCarCards() {
		return oaCarCards;
	}

	public void setOaCarCards(String aOaCarCards) {
		this.oaCarCards = aOaCarCards;
	}

	public String getOaCarModel() {
		return oaCarModel;
	}

	public void setOaCarModel(String aOaCarModel) {
		this.oaCarModel = aOaCarModel;
	}

	public Integer getOaCarType() {
		return oaCarType;
	}

	public void setOaCarType(Integer aOaCarType) {
		this.oaCarType = aOaCarType;
	}

	public String getOaCarMete() {
		return oaCarMete;
	}

	public void setOaCarMete(String aOaCarMete) {
		this.oaCarMete = aOaCarMete;
	}

	public String getOaCarMax() {
		return oaCarMax;
	}

	public void setOaCarMax(String aOaCarMax) {
		this.oaCarMax = aOaCarMax;
	}

	public String getOaCarMotorman() {
		return oaCarMotorman;
	}

	public void setOaCarMotorman(String aOaCarMotorman) {
		this.oaCarMotorman = aOaCarMotorman;
	}

	public Double getOaCarPrice() {
		return oaCarPrice;
	}

	public void setOaCarPrice(Double aOaCarPrice) {
		this.oaCarPrice = aOaCarPrice;
	}

	public String getOaCarPhoto() {
		return oaCarPhoto;
	}

	public void setOaCarPhoto(String aOaCarPhoto) {
		this.oaCarPhoto = aOaCarPhoto;
	}

	public String getOaCarBuydate() {
		return oaCarBuydate;
	}

	public void setOaCarBuydate(String aOaCarBuydate) {
		this.oaCarBuydate = aOaCarBuydate;
	}

	public String getOaCarRemark() {
		return oaCarRemark;
	}

	public void setOaCarRemark(String aOaCarRemark) {
		this.oaCarRemark = aOaCarRemark;
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

	public Integer getOaCarSta() {
		return oaCarSta;
	}

	public void setOaCarSta(Integer oaCarSta) {
		this.oaCarSta = oaCarSta;
	}


}