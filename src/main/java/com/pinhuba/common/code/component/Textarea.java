package com.pinhuba.common.code.component;

public class Textarea extends Component{

	@Override
	public String getCommponentDefine(String fieldName, String requiredString, String requiredLabel) {
		String s = requiredLabel+"<br/><textarea width=\"90%\" id=\""+fieldName+"\" style=\"height:150px;\"></textarea>";
		return s;
	}

}
