package com.pinhuba.web.taglib.table.cloumntype;

public class SelectType extends AbscolumnType {

	private String options;//请以1,222|2,33格式
	
	private String defaultChecked  = "-1";//默认选中的value值
	
	@Override
	public int getColumType() {
		return GridColumnType.TYPE_SELECT;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public String getDefaultChecked() {
		return defaultChecked;
	}

	public void setDefaultChecked(String defaultChecked) {
		this.defaultChecked = defaultChecked;
	}

	public SelectType(String options) {
		super();
		this.options = options;
	}

	public SelectType(String options, String defaultChecked) {
		super();
		this.options = options;
		this.defaultChecked = defaultChecked;
	}

	public SelectType(String options,String[] customerFunction) {
		super();
		this.options = options;
		setCustomerFunction(customerFunction);
	}
	public SelectType(String options,String[] customerFunction,String[] customerAttribute) {
		super();
		this.options = options;
		setCustomerFunction(customerFunction);
		setCustomerAttribute(customerAttribute);
	}
	
	public SelectType(String options, String defaultChecked,String[] customerFunction) {
		super();
		this.options = options;
		this.defaultChecked = defaultChecked;
		setCustomerFunction(customerFunction);
	}
	
	public SelectType(String options, String defaultChecked,String[] customerFunction,String[] customerAttribute) {
		super();
		this.options = options;
		this.defaultChecked = defaultChecked;
		setCustomerFunction(customerFunction);
		setCustomerAttribute(customerAttribute);
	}

	@Override
	public String getTypeValue() {
		return GridColumnType.TYPE_SELECT_GETVALUE;
	}
}
