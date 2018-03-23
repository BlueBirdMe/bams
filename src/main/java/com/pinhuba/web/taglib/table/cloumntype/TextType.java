package com.pinhuba.web.taglib.table.cloumntype;

public class TextType extends AbscolumnType {

	private String defaultText;
	
	@Override
	public int getColumType() {
		return GridColumnType.TYPE_TEXT;
	}

	public String getDefaultText() {
		return defaultText;
	}

	public void setDefaultText(String defaultText) {
		this.defaultText = defaultText;
	}

	public TextType() {
		super();
	}

	public TextType(String defaultText) {
		super();
		this.defaultText = defaultText;
	}
	public TextType(String[] customerFunction){
		super();
		setCustomerFunction(customerFunction);
	}
	
	public TextType(String[] customerFunction,String[] customerAttribute){
		super();
		setCustomerFunction(customerFunction);
		setCustomerAttribute(customerAttribute);
	}
	
	public TextType(String defaultText,String[] customerFunction) {
		super();
		this.defaultText = defaultText;
		setCustomerFunction(customerFunction);
	}
	
	public TextType(String defaultText,String[] customerFunction,String[] customerAttribute) {
		super();
		this.defaultText = defaultText;
		setCustomerFunction(customerFunction);
		setCustomerAttribute(customerAttribute);
	}

	@Override
	public String getTypeValue() {
		return GridColumnType.TYPE_TEXT_GETVALUE;
	}
}
