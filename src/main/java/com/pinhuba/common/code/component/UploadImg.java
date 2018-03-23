package com.pinhuba.common.code.component;

public class UploadImg extends Component{
	@Override
	public String getCommponentSetValueStr(String fieldName, String fieldValue) {
		StringBuffer sb = new StringBuffer();
		sb.append("if(isNotBlank("+fieldValue+")){\n");
		sb.append("                dwrCommonService.getImageInfoListToString("+fieldValue+",function(data){Sys.setFilevalue(\""+fieldName+"\",data);});\n");
		sb.append("            }");
		return sb.toString();
	}

	@Override
	public String getCommponentDefine(String fieldName, String requiredString, String requiredLabel) {
		String s = "<file:imgupload width=\"120\" acceptTextId=\""+fieldName+"\" height=\"135\" edit=\"<%=isedit %>\"></file:imgupload>";
		return s;
	}
	
	public String getCommponentSetDetailStr(String fieldName, String fieldValue) {
		StringBuffer sb = new StringBuffer();
		sb.append("//放入图片\n");
		sb.append("            if(isNotBlank("+fieldValue+")){\n");
		sb.append("                var face = document.getElementById(\""+fieldName+"\");\n");
		sb.append("                face.src+=\"&imgId=\"+"+fieldValue+";\n");
		sb.append("            }");
		return sb.toString();
	}
	
	public String getCommponentDetailDefine(String fieldName) {
		String str = "<td><file:imgshow id=\"" + fieldName + "\" width=\"120\"></file:imgshow></td>";
		return str;
	}
	
}
