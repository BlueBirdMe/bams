package com.pinhuba.core.pojo;

/**
 * 数据库表名：sys_method_btn
 */
public class SysMethodBtn extends BaseBean implements java.io.Serializable {

	private String btnName;
	private String btnImg;
	private String btnFun;
	private String btnDesc;
	private String methodId;
	private int priority;//排序
	private SysMethodInfo methodInfo;
	
	private boolean checked = false;//权限设置时是否选中
	

	// 默认构造方法
	public SysMethodBtn() {
		super();
	}

	public String getBtnName() {
		return btnName;
	}

	public void setBtnName(String btnName) {
		this.btnName = btnName;
	}

	public String getBtnImg() {
		return btnImg;
	}

	public void setBtnImg(String btnImg) {
		this.btnImg = btnImg;
	}

	public String getBtnFun() {
		return btnFun;
	}

	public void setBtnFun(String btnFun) {
		this.btnFun = btnFun;
	}

	public String getBtnDesc() {
		return btnDesc;
	}

	public void setBtnDesc(String btnDesc) {
		this.btnDesc = btnDesc;
	}

	public String getMethodId() {
		return methodId;
	}

	public void setMethodId(String methodId) {
		this.methodId = methodId;
	}

	public SysMethodInfo getMethodInfo() {
		return methodInfo;
	}

	public void setMethodInfo(SysMethodInfo methodInfo) {
		this.methodInfo = methodInfo;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
}