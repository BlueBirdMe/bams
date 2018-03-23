package com.pinhuba.web.taglib.table.cloumntype;

public class CheckBoxType extends AbscolumnType {

	private String value;// 值

	private String showText;// 显示文本

	private boolean ischecked = false;// 是否选中

	public CheckBoxType(String showText) {
		super();
		this.showText = showText;
	}

	public CheckBoxType(String value, String showText) {
		super();
		this.value = value;
		this.showText = showText;
	}

	public CheckBoxType(String showText, boolean ischecked) {
		super();
		this.showText = showText;
		this.ischecked = ischecked;
	}

	public CheckBoxType(String value, String showText, boolean ischecked) {
		super();
		this.value = value;
		this.showText = showText;
		this.ischecked = ischecked;
	}

	public CheckBoxType(String value, String showText, boolean ischecked, String[] customerFunction) {
		super();
		this.value = value;
		this.showText = showText;
		this.ischecked = ischecked;
		setCustomerFunction(customerFunction);
	}

	public CheckBoxType(String value, String showText, boolean ischecked, String[] customerFunction, String[] customerAttribute) {
		super();
		this.value = value;
		this.showText = showText;
		this.ischecked = ischecked;
		setCustomerFunction(customerFunction);
		setCustomerAttribute(customerAttribute);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getShowText() {
		return showText;
	}

	public void setShowText(String showText) {
		this.showText = showText;
	}

	public boolean isIschecked() {
		return ischecked;
	}

	public void setIschecked(boolean ischecked) {
		this.ischecked = ischecked;
	}
	@Override
	public int getColumType() {
		return GridColumnType.TYPE_CHECKBOX;
	}

	@Override
	public String getTypeValue() {
		return GridColumnType.TYPE_CHECKBOX_GETVALUE;
	}

}
