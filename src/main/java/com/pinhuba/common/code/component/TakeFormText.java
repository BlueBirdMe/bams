package com.pinhuba.common.code.component;

public class TakeFormText extends Component{

	@Override
	public String getCommponentDefine(String fieldName, String requiredString, String requiredLabel) {
		String s = "<input type=\"text\" id=\""+fieldName+"\" class=\"takeform\" readonly=\"readonly\" linkclear=\"\" onClick=\"\" title=\"点击选择\"></input>"+requiredLabel;
		return s;
	}

}
