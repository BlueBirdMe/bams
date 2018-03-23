package com.pinhuba.common.code.component;

public class DateForm extends Component{

	@Override
	public String getCommponentDefine(String fieldName, String requiredString, String requiredLabel) {
		String s = "<input type=\"text\" id=\""+fieldName+"\" class=\"Wdate\" readonly=\"readonly\" onClick=\"WdatePicker()\""+requiredString+"></input>"+requiredLabel;
		return s;
	}

}
