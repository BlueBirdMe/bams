package com.pinhuba.web.taglib.table.cloumntype;

public class TimeType extends AbscolumnType {

	private boolean isStratAndEnd = true;//是否显示为开始和结束
	
	private String defaultTime;//默认显示实践;
	
	private String timeFmt ="H:mm:ss";//时间格式
	
	@Override
	public int getColumType() {
		return GridColumnType.TYPE_TIME;
	}


	public boolean isStratAndEnd() {
		return isStratAndEnd;
	}


	public String getTimeFmt() {
		return timeFmt;
	}


	public void setTimeFmt(String timeFmt) {
		this.timeFmt = timeFmt;
	}


	public void setStratAndEnd(boolean isStratAndEnd) {
		this.isStratAndEnd = isStratAndEnd;
	}


	public String getDefaultTime() {
		return defaultTime;
	}


	public void setDefaultTime(String defaultTime) {
		this.defaultTime = defaultTime;
	}


	public TimeType() {
		super();
	}


	public TimeType(boolean isStratAndEnd) {
		super();
		this.isStratAndEnd = isStratAndEnd;
	}


	public TimeType(boolean isStratAndEnd, String defaultTime) {
		super();
		this.isStratAndEnd = isStratAndEnd;
		this.defaultTime = defaultTime;
	}
	public TimeType(String[] customerFunction){
		super();
		setCustomerFunction(customerFunction);
	}
	
	public TimeType(String[] customerFunction,String[] customerAttribute){
		super();
		setCustomerFunction(customerFunction);
		setCustomerAttribute(customerAttribute);
	}
	
	public TimeType(boolean isStratAndEnd,String[] customerFunction) {
		super();
		this.isStratAndEnd = isStratAndEnd;
		setCustomerFunction(customerFunction);
	}
	
	public TimeType(boolean isStratAndEnd,String[] customerFunction,String[] customerAttribute) {
		super();
		this.isStratAndEnd = isStratAndEnd;
		setCustomerFunction(customerFunction);
		setCustomerAttribute(customerAttribute);
	}


	@Override
	public String getTypeValue() {
		return GridColumnType.TYPE_TIME_GETVALUE;
	}
}
