package com.pinhuba.common.code.component;

public class Radio extends Component{
	@Override
	public String getCommponentSetValueStr(String fieldName, String fieldValue) {
		String str = "setRadioValueByName(\""+fieldName+"\","+fieldValue+");";
		return str;
	}
	
	@Override
	public String getCommponentGetValueStr(String pojoName, String fieldName) {
		String str = pojoName+"."+fieldName+" = getRadioValueByName(\""+fieldName+"\");";
		return str;
	}

	@Override
	public String getCommponentDefine(String fieldName, String requiredString, String requiredLabel) {
		String s = "<%=UtilTool.getRadioOptionsByEnum(EnumUtil.HRM_EMPLOYEE_SEX.getSelectAndText(\"\"),\""+fieldName+"\")%>";
		return s;
	}
}
