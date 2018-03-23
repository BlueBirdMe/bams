package com.pinhuba.common.code.component;

public class Checkbox extends Component{
	@Override
	public String getCommponentSetValueStr(String fieldName, String fieldValue) {
		String str = "setCheckboxValueByName(\""+fieldName+"\","+fieldValue+");";
		return str;
	}
	
	@Override
	public String getCommponentGetValueStr(String pojoName, String fieldName) {
		String str = pojoName+"."+fieldName+" = getCheckboxValueByName(\""+fieldName+"\");";
		return str;
	}

	@Override
	public String getCommponentDefine(String fieldName, String requiredString, String requiredLabel) {
		String str = "<%=UtilTool.getCheckboxOptions(this.getServletContext(),request,null,\"06\",\""+fieldName+"\") %>";
		return str;
	}
}
