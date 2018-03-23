package com.pinhuba.common.code.component;

public class NiceForm extends Component{

	@Override
	public String getCommponentDefine(String fieldName, String requiredString, String requiredLabel) {
		String s = "<input type=\"text\" id=\""+fieldName+"\"" + requiredString + "></input>" + requiredLabel;
		return s;
	}

}
