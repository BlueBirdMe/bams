package com.pinhuba.core.pojo;

import java.util.List;

/**
 * 数据库表名：SYS_METHOD_INFO
 */
public class SysMethodInfo extends BaseStringBean implements java.io.Serializable {

	/**
	 * 功能菜单数据表
	 */
	private static final long serialVersionUID = 7330281680655465718L;
	private String methodInfoName;                       //功能名称
	private String methodInfoEngname;                    //功能英文名称
	private String methodUri;                            //跳转路径
	private Integer methodNo;                            //显示顺序
	private String imageSrc;                             //功能图标
	private Integer methodLevel;                         //层次
	private String levelUnit;                            //上级节点（顶级为-1）
	
	private SysMethodInfo upSysMethodInfo;               //上级对象
	
	private Integer isAction;                            //是否有效
	private String methodSign;                           //功能标识
	private Integer isDefault;							 //默认
	private String defaultPage;							 //默认页
	private String methodMsg;                            //功能描述
	private String methodPages;                          //功能涉及的相关页面
	
	//临时
	private boolean ischecked = false;//权限设置时是否选中
	
	private List<SysMethodBtn> btns;
	private List<SysMethodHelp> helps;
	
	public boolean isIschecked() {
		return ischecked;
	}

	public void setIschecked(boolean ischecked) {
		this.ischecked = ischecked;
	}

	// 默认构造方法
	public SysMethodInfo() {
		super();
	}

	// 构造方法(手工生成)
	public SysMethodInfo(String methodInfoName, String methodInfoEngname, String methodUri, Integer methodNo, String imageSrc, Integer methodLevel, String levelUnit, Integer isAction,
			String methodSign, Integer isDefault, String defaultPage) {
		super();
		this.methodInfoName = methodInfoName;
		this.methodInfoEngname = methodInfoEngname;
		this.methodUri = methodUri;
		this.methodNo = methodNo;
		this.imageSrc = imageSrc;
		this.methodLevel = methodLevel;
		this.levelUnit = levelUnit;
		this.isAction = isAction;
		this.methodSign = methodSign;
		this.isDefault = isDefault;
		this.defaultPage = defaultPage;
	}

	// get和set方法
	public String getMethodInfoName() {
		return methodInfoName;
	}

	public void setMethodInfoName(String aMethodInfoName) {
		this.methodInfoName = aMethodInfoName;
	}

	public String getMethodInfoEngname() {
		return methodInfoEngname;
	}

	public void setMethodInfoEngname(String aMethodInfoEngname) {
		this.methodInfoEngname = aMethodInfoEngname;
	}

	public String getMethodUri() {
		return methodUri;
	}

	public void setMethodUri(String aMethodUri) {
		this.methodUri = aMethodUri;
	}
	
	public String getMethodRealUri(String code){
		String uri = getMethodUri();
		if(uri.indexOf("?") < 0){
			uri += "?";
		} else {
			uri += "&";
		}
		return uri + "mid=" + code;
	}

	public Integer getMethodNo() {
		return methodNo;
	}

	public void setMethodNo(Integer aMethodNo) {
		this.methodNo = aMethodNo;
	}

	public String getImageSrc() {
		return imageSrc;
	}

	public void setImageSrc(String aImageSrc) {
		this.imageSrc = aImageSrc;
	}

	public Integer getMethodLevel() {
		return methodLevel;
	}

	public void setMethodLevel(Integer aMethodLevel) {
		this.methodLevel = aMethodLevel;
	}

	public String getLevelUnit() {
		return levelUnit;
	}

	public void setLevelUnit(String aLevelUnit) {
		this.levelUnit = aLevelUnit;
	}

	public Integer getIsAction() {
		return isAction;
	}

	public void setIsAction(Integer aIsAction) {
		this.isAction = aIsAction;
	}

	public String getMethodSign() {
		return methodSign;
	}

	public void setMethodSign(String aMethodSign) {
		this.methodSign = aMethodSign;
	}

	public Integer getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}

	public String getDefaultPage() {
		return defaultPage;
	}

	public void setDefaultPage(String defaultPage) {
		this.defaultPage = defaultPage;
	}

	public SysMethodInfo getUpSysMethodInfo() {
		return upSysMethodInfo;
	}

	public void setUpSysMethodInfo(SysMethodInfo upSysMethodInfo) {
		this.upSysMethodInfo = upSysMethodInfo;
	}

	public String getMethodMsg() {
		return methodMsg;
	}

	public void setMethodMsg(String methodMsg) {
		this.methodMsg = methodMsg;
	}
	
	public String getMethodMsgStr() {
		return getMethodMsg() == null ? "" : getMethodMsg();
	}

	public String getMethodPages() {
		return methodPages;
	}

	public void setMethodPages(String methodPages) {
		this.methodPages = methodPages;
	}

	public List<SysMethodBtn> getBtns() {
		return btns;
	}

	public void setBtns(List<SysMethodBtn> btns) {
		this.btns = btns;
	}

	public List<SysMethodHelp> getHelps() {
		return helps;
	}

	public void setHelps(List<SysMethodHelp> helps) {
		this.helps = helps;
	}
	
}