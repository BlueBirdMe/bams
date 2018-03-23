package com.pinhuba.common.code.component;

public class Select extends Component{

	@Override
	public String getCommponentSetValueStr(String fieldName, String fieldValue) {
		String str = "setSelectValue(\""+fieldName+"\","+fieldValue+");";
		return str;
	}

	@Override
	public String getCommponentDefine(String fieldName, String requiredString, String requiredLabel) {
		String s = "<select id=\""+fieldName+"\"><%=UtilTool.getSelectOptions(this.getServletContext(),request,null,\"21\") %></select>";
		return s;
	}

}
