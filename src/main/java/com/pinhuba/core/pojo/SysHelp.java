package com.pinhuba.core.pojo;

/**
 * 数据库表名：SYS_HELP
 */
public class SysHelp extends BaseBean implements java.io.Serializable {

	/**
	 * 系统帮助信息表
	 */
	private static final long serialVersionUID = 8481379198559747252L;
	private String helpKeyword;		//关键字
	private String helpTitle;		//帮助主题
	private String helpContext;		//帮助内容
	private String helpDate;		//时间
	
	private String methodCode; 		//所属模块
	private String findSign; 		//检索标识

	private SysMethodInfo methodInfo;
	
	
	public SysMethodInfo getMethodInfo() {
		return methodInfo;
	}

	public void setMethodInfo(SysMethodInfo methodInfo) {
		this.methodInfo = methodInfo;
	}

	public String getMethodCode() {
		return methodCode;
	}

	public void setMethodCode(String methodCode) {
		this.methodCode = methodCode;
	}

	public String getFindSign() {
		return findSign;
	}

	public void setFindSign(String findSign) {
		this.findSign = findSign;
	}

	// 默认构造方法
	public SysHelp() {
		super();
	}

	// get和set方法
	public String getHelpKeyword() {
		return helpKeyword;
	}

	public void setHelpKeyword(String aHelpKeyword) {
		this.helpKeyword = aHelpKeyword;
	}

	public String getHelpTitle() {
		return helpTitle;
	}

	public void setHelpTitle(String aHelpTitle) {
		this.helpTitle = aHelpTitle;
	}

	public String getHelpContext() {
		return helpContext;
	}

	public void setHelpContext(String aHelpContext) {
		this.helpContext = aHelpContext;
	}

	public String getHelpDate() {
		return helpDate;
	}

	public void setHelpDate(String aHelpDate) {
		this.helpDate = aHelpDate;
	}

}