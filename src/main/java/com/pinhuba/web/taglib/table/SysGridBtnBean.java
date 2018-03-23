package com.pinhuba.web.taglib.table;

public class SysGridBtnBean {
	private String btnTitle;
	
	private String btnMethod;
	
	private String btnImage;

	public String getBtnTitle() {
		return btnTitle;
	}

	public SysGridBtnBean() {
		super();
	}

	public SysGridBtnBean(String btnTitle, String btnMethod, String btnImage) {
		super();
		this.btnTitle = btnTitle;
		this.btnMethod = btnMethod;
		this.btnImage = btnImage;
	}

	public void setBtnTitle(String btnTitle) {
		this.btnTitle = btnTitle;
	}

	public String getBtnMethod() {
		return btnMethod;
	}

	public void setBtnMethod(String btnMethod) {
		this.btnMethod = btnMethod;
	}

	public String getBtnImage() {
		return btnImage;
	}

	public void setBtnImage(String btnImage) {
		this.btnImage = btnImage;
	}
	
	
}
