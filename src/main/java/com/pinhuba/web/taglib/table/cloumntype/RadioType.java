package com.pinhuba.web.taglib.table.cloumntype;

public class RadioType extends AbscolumnType {
	
	private String[] value;//与showText个数匹配
	
	private String[] showText;//2个以上
	
	private String checkedValue;//默认选中值
	
	@Override
	public int getColumType() {
		return GridColumnType.TYPE_RADIO;
	}


	public RadioType(String[] value, String[] showText) {
		super();
		this.value = value;
		this.showText = showText;
	}

	public RadioType( String[] value, String[] showText,String[] customerFunction) {
		super();
		this.value = value;
		this.showText = showText;
		setCustomerFunction(customerFunction);
	}

	public RadioType(String[] value, String[] showText,String[] customerFunction,String[]customerAttribute) {
		super();
		this.value = value;
		this.showText = showText;
		setCustomerFunction(customerFunction);
		setCustomerAttribute(customerAttribute);
	}
	
	public RadioType(String[] showText) {
		super();
		this.showText = showText;
	}


	public RadioType( String[] value, String[] showText, String checkedValue) {
		super();
		this.value = value;
		this.showText = showText;
		this.checkedValue = checkedValue;
	}

	public RadioType( String[] value, String[] showText, String checkedValue,String[] customerFunction) {
		super();
		this.value = value;
		this.showText = showText;
		this.checkedValue = checkedValue;
		setCustomerFunction(customerFunction);
	}
	
	public RadioType( String[] value, String[] showText, String checkedValue,String[] customerFunction,String[] customerAttribute) {
		super();
		this.value = value;
		this.showText = showText;
		this.checkedValue = checkedValue;
		setCustomerFunction(customerFunction);
		setCustomerAttribute(customerAttribute);
	}


	public String[] getValue() {
		return value;
	}


	public void setValue(String[] value) {
		this.value = value;
	}


	public String[] getShowText() {
		return showText;
	}


	public void setShowText(String[] showText) {
		this.showText = showText;
	}


	public String getCheckedValue() {
		return checkedValue;
	}


	public void setCheckedValue(String checkedValue) {
		this.checkedValue = checkedValue;
	}


	@Override
	public String getTypeValue() {
		return GridColumnType.TYPE_RADIO_GETVALUE;
	}

	
}
