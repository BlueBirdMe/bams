package com.pinhuba.common.code.component;

public class RichTextarea extends Component{
	@Override
	public String getCommponentSetValueStr(String fieldName, String fieldValue) {
		return "fckvalue = "+fieldValue+";";
	}
	
	@Override
	public String getCommponentGetValueStr(String pojoName, String fieldName) {
		String str = pojoName+"."+fieldName+" = fck.GetXHTML();";
		return str;
	}

	@Override
	public String getCommponentDefine(String fieldName, String requiredString, String requiredLabel) {
		String str = requiredLabel+"<FCK:editor instanceName=\""+fieldName+"\" width=\"90%\" height=\"150\"></FCK:editor>";
		return str;
	}
	
	@Override
	public String getCommponentSetDetailStr(String fieldName, String fieldValue) {
		String str = "DWRUtil.setValue(\""+fieldName+"\","+fieldValue+",{escapeHtml:false});";
		return str;
	}
}
