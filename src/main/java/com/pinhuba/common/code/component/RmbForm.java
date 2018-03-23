package com.pinhuba.common.code.component;

public class RmbForm extends Component{
	@Override
	public String getCommponentDefine(String fieldName, String requiredString, String requiredLabel) {
		String s = "<input type=\"text\" id=\""+ fieldName +"\" class=\"rmbform\"" + requiredString + "></input>" + requiredLabel;
		return s;
	}
}
