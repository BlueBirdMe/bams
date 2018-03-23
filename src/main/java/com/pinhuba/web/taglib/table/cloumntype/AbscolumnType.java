package com.pinhuba.web.taglib.table.cloumntype;

public abstract class AbscolumnType {
	
	private String[] customerFunction;// 自定义事件 {"onclick ='alert(123);'",
	  									//"onchange = 'alert(222);'"}

	private String[] customerAttribute;// 自定义属性 {"ads ='1111'","aaa ='2'"};
	
	public abstract int getColumType();

	public abstract String getTypeValue();
	
	public String[] getCustomerFunction() {
		return customerFunction;
	}

	public void setCustomerFunction(String[] customerFunction) {
		this.customerFunction = customerFunction;
	}

	public String[] getCustomerAttribute() {
		return customerAttribute;
	}

	public void setCustomerAttribute(String[] customerAttribute) {
		this.customerAttribute = customerAttribute;
	}
	
	
}
