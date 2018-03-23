package com.pinhuba.core.pojo;

/**
 * 数据库表名：SYS_IMAGE_INFO
 */
public class SysImageInfo extends BaseBean implements java.io.Serializable {

	/**
	 * 图片信息表
	 */
	private static final long serialVersionUID = -6479381051654919173L;
	private String imageInfoFilename;		//base64编码的图片存放路
	private String imageInfoFilePath;		//未经过base64编码的图片存放路，常用于报表打印
	private String imageInfoName;			//图片名称
	private String imageInfoRename;			//图片上传后的名称
	private String imageInfoDescription;	//图片信息描述
	private String imageInfoRemark;			//图片信息备注
	private String recordId;
	private String recordDate;
	private String lastmodiId;
	private String lastmodiDate;
	private Integer companyId;

	// 默认构造方法
	public SysImageInfo() {
		super();
	}

	// get和set方法
	public String getImageInfoFilename() {
		return imageInfoFilename;
	}

	public void setImageInfoFilename(String aImageInfoFilename) {
		this.imageInfoFilename = aImageInfoFilename;
	}

	public String getImageInfoName() {
		return imageInfoName;
	}

	public void setImageInfoName(String aImageInfoName) {
		this.imageInfoName = aImageInfoName;
	}

	public String getImageInfoDescription() {
		return imageInfoDescription;
	}

	public void setImageInfoDescription(String aImageInfoDescription) {
		this.imageInfoDescription = aImageInfoDescription;
	}

	public String getImageInfoRemark() {
		return imageInfoRemark;
	}

	public void setImageInfoRemark(String aImageInfoRemark) {
		this.imageInfoRemark = aImageInfoRemark;
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

	public String getImageInfoRename() {
		return imageInfoRename;
	}

	public void setImageInfoRename(String imageInfoRename) {
		this.imageInfoRename = imageInfoRename;
	}

	public String getImageInfoFilePath() {
		return imageInfoFilePath;
	}

	public void setImageInfoFilePath(String imageInfoFilePath) {
		this.imageInfoFilePath = imageInfoFilePath;
	}
}