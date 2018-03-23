package com.pinhuba.common.code.component;

public class TakeFormTextarea extends Component{

	@Override
	public String getCommponentDefine(String fieldName, String requiredString, String requiredLabel) {
		String str = "<textarea id=\""+fieldName+"\" readonly=\"readonly\" linkclear=\"\" onclick=\"\" title=\"点击选择\"></textarea>"+requiredLabel;
		return str;
	}

}
