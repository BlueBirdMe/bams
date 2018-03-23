package com.pinhuba.web.taglib.table.cloumntype;

public class OtherType extends AbscolumnType {

	private String html;

	private String getValueMethod;
	
	@Override
	public int getColumType() {
		return GridColumnType.TYPE_OTHER;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public OtherType(String html) {
		super();
		this.html = html;
	}

	public String getGetValueMethod() {
		return getValueMethod;
	}

	public void setGetValueMethod(String getValueMethod) {
		this.getValueMethod = getValueMethod;
	}

	public OtherType(String html, String getValueMethod) {
		super();
		this.html = html;
		this.getValueMethod = getValueMethod;
	}

	@Override
	public String getTypeValue() {
		if(getGetValueMethod()!=null&&getGetValueMethod().length()>0){
			return getGetValueMethod();
		}else{
			return GridColumnType.TYPE_TEXT_GETVALUE;
		}
	}
}
