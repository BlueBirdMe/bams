package com.pinhuba.web.taglib.table.cloumntype;

public class DateType extends AbscolumnType {

	private boolean isStratAndEnd = true;// 是否显示为开始和结束

	private String defaultDate;// 默认显示日期;

	private String dateFmt = "yyyy-MM-dd";// 日期格式

	@Override
	public int getColumType() {
		return GridColumnType.TYPE_DATE;
	}

	public String getDateFmt() {
		return dateFmt;
	}

	public void setDateFmt(String dateFmt) {
		this.dateFmt = dateFmt;
	}

	public boolean isStratAndEnd() {
		return isStratAndEnd;
	}

	public void setStratAndEnd(boolean isStratAndEnd) {
		this.isStratAndEnd = isStratAndEnd;
	}

	public String getDefaultDate() {
		return defaultDate;
	}

	public void setDefaultDate(String defaultDate) {
		this.defaultDate = defaultDate;
	}

	public DateType() {
		super();
	}

	public DateType(boolean isStratAndEnd) {
		super();
		this.isStratAndEnd = isStratAndEnd;
	}

	public DateType(boolean isStratAndEnd, String defaultDate) {
		super();
		this.isStratAndEnd = isStratAndEnd;
		this.defaultDate = defaultDate;
	}

	public DateType(String[] customerFunction) {
		super();
		setCustomerFunction(customerFunction);
	}

	public DateType(String[] customerFunction, String[] customerAttribute) {
		super();
		setCustomerFunction(customerFunction);
		setCustomerAttribute(customerAttribute);
	}

	public DateType(boolean isStratAndEnd, String[] customerFunction) {
		super();
		this.isStratAndEnd = isStratAndEnd;
		setCustomerFunction(customerFunction);
	}

	public DateType(boolean isStratAndEnd, String[] customerFunction, String[] customerAttribute) {
		super();
		this.isStratAndEnd = isStratAndEnd;
		setCustomerFunction(customerFunction);
		setCustomerAttribute(customerAttribute);
	}

	@Override
	public String getTypeValue() {
		return GridColumnType.TYPE_DATE_GETVALUE;
	}
}
