package com.pinhuba.core.pojo;

/**
 * 数据库表名：OA_TOOLS
 */
public class OaTools extends BaseBean implements java.io.Serializable {

	/**
	 * 常用工具数据表
	 */
	private static final long serialVersionUID = -8586746474380256222L;
	private String oaToolText; // 工具文本
	private Integer oaToolType;// 工具类型

	private SysLibraryInfo toolTypeObjInfo;//工具类型

	private String oaToolEmp; // 人员id
	private String oaToolImage;// 图片
	private String oaToolPath;// 工具路径
	private String recordId;
	private String recordDate;
	private String lastmodiId;
	private String lastmodiDate;
	private Integer companyId;
	private Integer oaToolImageId;//用户自己的图片ID
	// 默认构造方法
	public OaTools() {
		super();
	}

	// 构造方法(手工生成)
	public OaTools(String oaToolText, String oaToolImage,
			String oaToolPath) {
		super();
		this.oaToolText = oaToolText;
		this.oaToolImage = oaToolImage;
		this.oaToolPath = oaToolPath;
	}

	public SysLibraryInfo getToolTypeObjInfo() {
		return toolTypeObjInfo;
	}

	public void setToolTypeObjInfo(SysLibraryInfo toolTypeObjInfo) {
		this.toolTypeObjInfo = toolTypeObjInfo;
	}

	// get和set方法
	public String getOaToolText() {
		return oaToolText;
	}

	public void setOaToolText(String aOaToolText) {
		this.oaToolText = aOaToolText;
	}

	public String getOaToolEmp() {
		return oaToolEmp;
	}

	public void setOaToolEmp(String aOaToolEmp) {
		this.oaToolEmp = aOaToolEmp;
	}

	public String getOaToolImage() {
		return oaToolImage;
	}

	public void setOaToolImage(String aOaToolImage) {
		this.oaToolImage = aOaToolImage;
	}

	public String getOaToolPath() {
		return oaToolPath;
	}

	public void setOaToolPath(String aOaToolPath) {
		this.oaToolPath = aOaToolPath;
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

	public Integer getOaToolType() {
		return oaToolType;
	}

	public void setOaToolType(Integer oaToolType) {
		this.oaToolType = oaToolType;
	}

	public Integer getOaToolImageId() {
		return oaToolImageId;
	}

	public void setOaToolImageId(Integer oaToolImageId) {
		this.oaToolImageId = oaToolImageId;
	}
	
	
}