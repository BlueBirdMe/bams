package com.pinhuba.common.code.component;

public class UploadFile extends Component{
	
	
	@Override
	public String getCommponentSetValueStr(String fieldName, String fieldValue) {
		StringBuffer sb = new StringBuffer();
		sb.append("if(isNotBlank("+fieldValue+")){\n");
		sb.append("                dwrCommonService.getAttachmentInfoListToString("+fieldValue+",function(data){Sys.setFilevalue(\""+fieldName+"\",data);});\n");
		sb.append("            }");
		return sb.toString();
	}

	@Override
	public String getCommponentDefine(String fieldName, String requiredString, String requiredLabel) {
		String s = "<file:multifileupload width=\"90%\" acceptTextId=\""+fieldName+"\" height=\"100\" edit=\"<%=isedit %>\"></file:multifileupload>";
		return s;
	}
	
	@Override
	public String getCommponentSetDetailStr(String fieldName, String fieldValue) {
		StringBuffer sb = new StringBuffer();
		sb.append("//放入附件\n");
		sb.append("            if(isNotBlank("+fieldValue+")){\n");
		sb.append("                Sys.showDownload("+fieldValue+",\""+fieldName+"\");\n");
		sb.append("            }");
		return sb.toString();
	}
	
	
	
}
