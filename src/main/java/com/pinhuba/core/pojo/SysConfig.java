package com.pinhuba.core.pojo;

/**
 * 
 * 数据对应表：SysConfig （系统配置表）
 *
 */
public class SysConfig extends BaseBean {
	
	private static final long serialVersionUID = -7288497181187994358L;

	private String methodId;//项目编码
	
	private String projectName;	//项目首页名称
	
	private String projectEgName; //项目首页英文名称
	
	private String projectView;//项目默认打开默认载入视图

	private String methodInfoName; //项目名称
	

	
	// 默认构造方法
	public SysConfig() {
		super();
	}
	
	public String getMethodInfoName() {
		return methodInfoName;
	}

	public void setMethodInfoName(String methodInfoName) {
		this.methodInfoName = methodInfoName;
	}

	
	public void setMethodId(String methodId) {
		this.methodId = methodId;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public void setProjectEgName(String projectEgName) {
		this.projectEgName = projectEgName;
	}

	public String getMethodId() {
		return methodId;
	}

	public String getProjectName() {
		return projectName;
	}

	public String getProjectEgName() {
		return projectEgName;
	}

	public String getProjectView() {
		return projectView;
	}

	public void setProjectView(String projectView) {
		this.projectView = projectView;
	}
    
}
