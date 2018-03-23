package com.pinhuba.common.code;

import com.pinhuba.common.code.util.Util;

/**
 * 树形相关代码
 * 
 * @author JC
 */
public class CreateTree {

	public static StringBuffer createService(Boolean istree, StringBuffer sb, String pojo) {
		if (istree) {
			sb.append("    public " + pojo + " get" + pojo + "ByCode(String code);\n");
			sb.append("    public int listDown" + pojo + "ByCodeCount(String code);\n");
			sb.append("    public List<" + pojo + "> listDown" + pojo + "ByCode(String code);\n");
			sb.append("    public List<" + pojo + "> list" + pojo + "ByCode(String code);\n");
			sb.append("    public void updateBatch" + pojo + "CodeAndUpcode(String oldCode,String newCode);\n");
		}
		return sb;
	}

	public static StringBuffer createServiceImpl(Boolean istree, StringBuffer sb, String pojo) {
		String lowerPojo = Util.lowerCaseFirstLetter(pojo);
		String pojoDao = lowerPojo + "Dao";

		if (istree) {
			sb.append("    public " + pojo + " get" + pojo + "ByCode(String code){\n");
			sb.append("        List<" + pojo + "> list = " + pojoDao + ".findByHqlWhere(\" and model.code = '\"+code+\"'\");\n");
			sb.append("        return (list.size() > 0)?list.get(0):null;\n");
			sb.append("    }\n");

			sb.append("    public int listDown" + pojo + "ByCodeCount(String code){\n");
			sb.append("        int count = " + pojoDao + ".findByHqlWhereCount(\" and model.upcode = '\"+code+\"'\");\n");
			sb.append("        return count;\n");
			sb.append("    }\n");

			sb.append("    public List<" + pojo + "> listDown" + pojo + "ByCode(String code){\n");
			sb.append("        List<" + pojo + "> list = " + pojoDao + ".findByHqlWhere(\" and model.upcode = '\"+code+\"' order by model.priority asc\");\n");
			sb.append("        return list;\n");
			sb.append("    }\n");

			sb.append("    public List<" + pojo + "> list" + pojo + "ByCode(String code){\n");
			sb.append("        List<" + pojo + "> list = " + pojoDao + ".findByHqlWhere(\" and model.code like '\"+code+\"%'\");\n");
			sb.append("        return list;\n");
			sb.append("    }\n");

			sb.append("    public void updateBatch" + pojo + "CodeAndUpcode(String oldCode,String newCode){\n");
			sb.append("        List<" + pojo + "> list = " + pojoDao + ".findByHqlWhere(\" and model.code like '\"+oldCode+\"%' and model.code<>'\"+oldCode+\"'\");\n");
			sb.append("        int oldCodeLen = oldCode.length();\n");
			sb.append("        for (" + pojo + " " + lowerPojo + " : list) {\n");
			sb.append("            String tmp = " + lowerPojo + ".getCode();\n");
			sb.append("            String uptmp = " + lowerPojo + ".getUpcode();\n");
			sb.append("            " + lowerPojo + ".setCode(newCode+tmp.substring(oldCodeLen,tmp.length()));\n");
			sb.append("            " + lowerPojo + ".setUpcode(newCode+uptmp.substring(oldCodeLen,uptmp.length()));\n");
			sb.append("            " + pojoDao + ".save(" + lowerPojo + ");\n");
			sb.append("        }\n");
			sb.append("    }\n");
		}

		return sb;
	}

	public static StringBuffer createDwr(Boolean istree, StringBuffer sb, String pojo, String servicevar) {
		String lowerPojo = Util.lowerCaseFirstLetter(pojo);

		if (istree) {
			sb.append("    /**\n");
			sb.append("     * 保存 " + pojo + "\n");
			sb.append("     * @param context\n");
			sb.append("     * @param request\n");
			sb.append("     * @param " + lowerPojo + "\n");
			sb.append("     */\n");
			sb.append("    public ResultBean save" + pojo + "(ServletContext context, HttpServletRequest request, " + pojo + " " + lowerPojo + "){\n");
			sb.append("        String upcode = " + lowerPojo + ".getUpcode();\n");
			sb.append("        String tmpUpcode = \"00\";\n");

			sb.append("        if(StringUtils.isNotBlank(upcode)){\n");
			sb.append("            tmpUpcode = " + lowerPojo + ".getUpcode();\n");
			sb.append("        }\n");
			sb.append("        " + lowerPojo + ".setUpcode(tmpUpcode);\n");

			sb.append("        String code = UtilTool.getCodeByUpCode(context, request, tmpUpcode, \"tz_model_directory\", \"code\", \"upcode\");\n");
			sb.append("        " + lowerPojo + ".setCode(code);\n");

			sb.append("        " + lowerPojo + ".setPrimaryKey(UtilPrimaryKey.getPrimaryKey());\n");
			sb.append("        " + servicevar + ".save" + pojo + "(" + lowerPojo + ");\n");
			sb.append("        logger.info(\"保存 TzModelDirectory...\");\n");
			sb.append("        return WebUtilWork.WebResultPack(null);\n");
			sb.append("    }\n");

			sb.append("    /**\n");
			sb.append("     * 更新 " + pojo + "\n");
			sb.append("     * @param context\n");
			sb.append("     * @param request\n");
			sb.append("     * @param " + lowerPojo + "\n");
			sb.append("     */\n");
			sb.append("    public ResultBean update" + pojo + "(ServletContext context, HttpServletRequest request, " + pojo + " " + lowerPojo + ", String oldUpcode){\n");
			sb.append("        String upcode = " + lowerPojo + ".getUpcode();\n");
			sb.append("        String tmpUpcode = \"00\";\n");

			sb.append("        if(StringUtils.isNotBlank(upcode)){\n");
			sb.append("            tmpUpcode = " + lowerPojo + ".getUpcode();\n");
			sb.append("        }\n");

			sb.append("        " + pojo + " tmp = " + servicevar + ".get" + pojo + "ByPk(" + lowerPojo + ".getPrimaryKey());\n");
			sb.append("        String newCode = null;\n");

			sb.append("        if(tmpUpcode.equals(oldUpcode)){//上级编码未变动\n");
			sb.append("            " + lowerPojo + ".setCode(tmp.getCode());\n");
			sb.append("            " + lowerPojo + ".setUpcode(tmp.getUpcode());\n");
			sb.append("        }else{\n");
			sb.append("            boolean bl = false;\n");
			sb.append("            //判断上级编码是否为自己和自己的下级\n");
			sb.append("            List<" + pojo + "> tmpList = " + servicevar + ".list" + pojo + "ByCode(tmp.getCode());\n");
			sb.append("            for (" + pojo + " tmp" + pojo + " : tmpList) {\n");
			sb.append("                if (tmp" + pojo + ".getCode().equals(tmpUpcode)) {\n");
			sb.append("                    bl = true;\n");
			sb.append("                    break;\n");
			sb.append("                }\n");
			sb.append("            }\n");
			sb.append("            if (bl) {\n");
			sb.append("                return new ResultBean(false,\"上级目录不能为自己和自己的下级目录!\");\n");
			sb.append("            }\n");

			sb.append("            //重新计算目录编码\n");
			sb.append("            newCode = UtilTool.getCodeByUpCode(context, request, tmpUpcode, \"tz_model_directory\", \"code\", \"upcode\");\n");
			sb.append("            " + lowerPojo + ".setCode(newCode);\n");
			sb.append("        }\n");

			sb.append("        if(newCode != null){\n");
			sb.append("            " + servicevar + ".updateBatch" + pojo + "CodeAndUpcode(tmp.getCode(), newCode);\n");
			sb.append("        }\n");

			sb.append("        " + servicevar + ".save" + pojo + "(" + lowerPojo + ");\n");
			sb.append("        logger.info(\"更新 " + pojo + "...\");\n");
			sb.append("        return WebUtilWork.WebResultPack(null);\n");
			sb.append("    }\n\n");

			
			sb.append("    /**\n");
			sb.append("     * 根据编号获取所有下级目录(加载树使用)\n");
			sb.append("     * @param context\n");
			sb.append("     * @param request\n");
			sb.append("     * @param deptCode\n");
			sb.append("     * @return\n");
			sb.append("     */\n");
			sb.append("    public List<" + pojo + "> listDown" + pojo + "ByCode(HttpServletRequest request,String code){\n");
			sb.append("        List<" + pojo + "> list = " + servicevar + ".listDown" + pojo + "ByCode(code);\n");
			sb.append("        logger.info(\"获取编号为:\"+code+\"的下级目录...\");\n");
			sb.append("        return list;\n");
			sb.append("    }\n");
			
			sb.append("    /**\n");
			sb.append("     * 统计节点下的目录数量\n");
			sb.append("     * @param request\n");
			sb.append("     * @param code\n");
			sb.append("     * @return\n");
			sb.append("     */\n");
			sb.append("    public int listDown" + pojo + "ByCodeCount(HttpServletRequest request,String code){\n");
			sb.append("        return " + servicevar + ".listDown" + pojo + "ByCodeCount(code);\n");
			sb.append("    }\n");
		}
		return sb;
	}
	

	public static String getTreeSelectPageString() {
		// TODO Auto-generated method stub
		return "";
	}
	
	public static String getTreeSelectXmlPageString(String dwrName,String pojoClass,String folderName,String pojoShortName) {
		String dwrClassName = Util.upperCaseFirstLetter(dwrName);
		String lowerPojo = Util.lowerCaseFirstLetter(pojoClass);

		StringBuffer sb = new StringBuffer();
		sb.append("<%@page language=\"java\" contentType=\"text/html; charset=UTF-8\"  pageEncoding=\"UTF-8\"%>\n");
		sb.append("<%@page import=\"java.io.PrintWriter\"%>\n");
		sb.append("<%@page import=\"org.springframework.web.context.WebApplicationContext\"%>\n");
		sb.append("<%@page import=\"org.springframework.web.context.support.WebApplicationContextUtils\"%>\n");
		sb.append("<%@page import=\"java.util.List\"%>\n");
		sb.append("<%@page import=\"com.pinhuba.common.util.UtilTool\"%>\n");
		sb.append("<%@page import=\"com.pinhuba.core.pojo." + pojoClass + "\"%>\n");
		sb.append("<%@page import=\"com.pinhuba.web.controller.dwr." + dwrClassName + "\"%>\n");
		sb.append("<%\n");

		sb.append("String code = request.getParameter(\"code\");\n");
		sb.append("String treetype = request.getParameter(\"treetype\");\n");

		sb.append("WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());\n");
		sb.append(dwrClassName + " service = (" + dwrClassName + ")webAppContext.getBean(\"" + dwrName + "\");\n");
		sb.append("List<" + pojoClass + "> list = service.listDown" + pojoClass + "ByCode(request,code);\n");
		sb.append("if(list != null && list.size()>0){\n");
		sb.append("    StringBuffer sb = new StringBuffer();\n");
		sb.append("    sb.append(\"<?xml version=\\\"1.0\\\" encoding=\\\"utf-8\\\"?>\");\n");
		sb.append("    sb.append(\"<tree>\");\n");
		sb.append("    for(int i=0;i<list.size();i++){\n");
		sb.append("        " + pojoClass + " " + lowerPojo + " = list.get(i);\n");
		sb.append("        String tmp = \"\";\n");
		sb.append("        int row = service.listDown" + pojoClass + "ByCodeCount(request, " + lowerPojo + ".getCode());\n");
		sb.append("        if(row>0){\n");
		sb.append("            tmp =\"src=\\\"\"+request.getContextPath()+\"/erp/" + folderName + "/" + pojoShortName + "_select_xml.jsp?code=\"+" + lowerPojo + ".getCode()+\"&treetype=\"+treetype+\"\\\"\";\n");
		sb.append("        }\n");
		sb.append("        //输出树节点\n");
		sb.append("        sb.append(\"<tree type=\\\"\"+treetype+\"\\\" id =\\\"" + lowerPojo + "_\"+" + lowerPojo + ".getPrimaryKey()+\"\\\" text=\\\"\"+" + lowerPojo + ".getName()+\"\\\" value=\\\"\"+" + lowerPojo + ".getPrimaryKey()+\"\\\" \"+tmp+\"/>\");\n");
		sb.append("    }\n");
		sb.append("sb.append(\"</tree>\");\n");
		sb.append("UtilTool.writeTextXml(response,sb.toString());\n");
		sb.append("}\n");
		sb.append("%>\n");

		return sb.toString();
	}

	public static String getTreePageString(String dwrName,String pojoClass,String folderName,String pojoShortName,String pojoName) {
		StringBuffer sb = new StringBuffer();
		sb.append("<%@page language=\"java\" contentType=\"text/html; charset=UTF-8\"  pageEncoding=\"UTF-8\"%>\n");
		sb.append("<%\n");
		sb.append("String contextPath = request.getContextPath();\n");
		sb.append("String ischeck = request.getParameter(\"ischeck\");\n");
		sb.append("String tmp = \"\";\n");
		sb.append("if(ischeck !=null && ischeck.length() > 0){\n");
		sb.append("    tmp += \"&ischeck=true\";\n");
		sb.append("}\n");

		sb.append("%>\n");
		sb.append("<link type='text/css' rel='stylesheet' href='<%=contextPath%>/css/xtree.css' />\n");
		sb.append("<script type='text/javascript' src='<%=contextPath%>/js/treeJs/map.js' charset='UTF-8'></script>\n");
		sb.append("<script type='text/javascript' src='<%=contextPath%>/js/treeJs/xtree.js' charset='UTF-8'></script>\n");
		sb.append("<script type='text/javascript' src='<%=contextPath%>/js/treeJs/xloadtree.js' charset='UTF-8'></script>\n");
		sb.append("<script type='text/javascript' src='<%=contextPath%>/js/treeJs/checkboxTreeItem.js' charset='UTF-8'></script>\n");
		sb.append("<script type='text/javascript' src='<%=contextPath%>/js/treeJs/xmlextras.js' charset='UTF-8'></script>\n");
		sb.append("<script type='text/javascript' src='<%=contextPath%>/js/treeJs/checkboxXLoadTree.js' charset='UTF-8'></script>\n");
		sb.append("<script type='text/javascript' src='<%=contextPath%>/js/treeJs/radioTreeItem.js' charset='UTF-8'></script>\n");
		sb.append("<script type='text/javascript' src='<%=contextPath%>/js/treeJs/radioXLoadTree.js' charset='UTF-8'></script>\n");
		sb.append("<script type='text/javascript'>webFXTreeConfig.setImagePath('<%=contextPath%>/js/treeJs/images/default/');</script>\n");
		sb.append("<script type=\"text/javascript\">\n");

		sb.append("function setliandong(obj){\n");
		sb.append("    webFXTreeConfig.setCascadeCheck(obj.checked);\n");
		sb.append("}\n");
		sb.append("webFXTreeConfig.setCascadeCheck(true);\n");
		sb.append("var tree = new WebFXLoadTree(\""+pojoName+"树\",\"<%=contextPath%>/erp/" + folderName + "/" + pojoShortName + "_tree_xml.jsp?code=00<%=tmp%>\",\"treeclick('');\");\n");
		sb.append("</script>\n");

		sb.append("<div class=\"div_tree\">\n");
		sb.append("    <%if(ischeck!=null && ischeck.length()>0){%>\n");
		sb.append("    <input type=\"checkbox\" id=\"lidong\" onchange=\"setliandong(this)\" checked=\"checked\">\n");
		sb.append("    <label for=\"lidong\" style=\"color: #336699\">选择上级自动选中下级</label>\n");
		sb.append("    <%}%>\n");
		sb.append("    <script type=\"text/javascript\">\n");
		sb.append("    document.write(tree);\n");
		sb.append("    function getCheckedIds(){\n");
		sb.append("        document.getElementById(\"upcode\").value = getCheckValues();\n");
		sb.append("        queryData();\n");
		sb.append("    }\n");
		sb.append("    </script>\n");
		sb.append("</div>\n");
		sb.append("<input type=\"hidden\" id=\"upcode\">\n");

		return sb.toString();
	}

	public static String getTreeXmlPageString(String dwrName,String pojoClass,String folderName,String pojoShortName) {
		String dwrClassName = Util.upperCaseFirstLetter(dwrName);
		String lowerPojo = Util.lowerCaseFirstLetter(pojoClass);

		StringBuffer sb = new StringBuffer();
		sb.append("<%@page language=\"java\" contentType=\"text/html; charset=UTF-8\"  pageEncoding=\"UTF-8\"%>\n");
		sb.append("<%@page import=\"java.io.PrintWriter\"%>\n");
		sb.append("<%@page import=\"org.springframework.web.context.WebApplicationContext\"%>\n");
		sb.append("<%@page import=\"org.springframework.web.context.support.WebApplicationContextUtils\"%>\n");
		sb.append("<%@page import=\"java.util.List\"%>\n");
		sb.append("<%@page import=\"com.pinhuba.common.util.UtilTool\"%>\n");
		sb.append("<%@page import=\"com.pinhuba.core.pojo." + pojoClass + "\"%>\n");
		sb.append("<%@page import=\"com.pinhuba.web.controller.dwr." + dwrClassName + "\"%>\n");

		sb.append("<%\n");

		sb.append("String code = request.getParameter(\"code\");\n");
		sb.append("String ischeck = request.getParameter(\"ischeck\");\n");

		sb.append("WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());\n");
		sb.append(dwrClassName + " service = (" + dwrClassName + ")webAppContext.getBean(\"" + dwrName + "\");\n");
		sb.append("List<" + pojoClass + "> list = service.listDown" + pojoClass + "ByCode(request,code);\n");
		sb.append("if(list != null&& list.size()>0){\n");
		sb.append("    StringBuffer sb = new StringBuffer();\n");
		sb.append("    sb.append(\"<?xml version=\\\"1.0\\\" encoding=\\\"utf-8\\\"?>\");\n");
		sb.append("    sb.append(\"<tree>\");\n");
		sb.append("    for(int i=0;i<list.size();i++){\n");
		sb.append("        " + pojoClass + " " + lowerPojo + " = list.get(i);\n");
		sb.append("        String tmp = \"\";\n");
		sb.append("        String chstr = \"\";\n");
		sb.append("        String val = \"\";\n");
		sb.append("        int row = service.listDown" + pojoClass + "ByCodeCount(request, " + lowerPojo + ".getCode());\n");
		sb.append("        if(ischeck != null && ischeck.length() > 0){\n");
		sb.append("            if(row>0){\n");
		sb.append("                tmp =\"src=\\\"\"+request.getContextPath()+\"/erp/" + folderName + "/" + pojoShortName + "_tree_xml.jsp?code=\"+" + lowerPojo + ".getCode()+\"&ischeck=true\\\"\";\n");
		sb.append("            }\n");
		sb.append("            chstr =\"onchange=\\\"getCheckedIds();\\\"  type=\\\"check\\\"\";\n");
		sb.append("            val = " + lowerPojo + ".getPrimaryKey();\n");
		sb.append("        }else{\n");
		sb.append("            if(row>0){\n");
		sb.append("                tmp =\"src=\\\"\"+request.getContextPath()+\"/erp/" + folderName + "/" + pojoShortName + "_tree_xml.jsp?code=\"+" + lowerPojo + ".getCode()+\"\\\"  action=\\\"treeclick('\"+" + lowerPojo + ".getPrimaryKey()+\"','\"+" + lowerPojo + ".getName()+\"');\\\"\";\n");
		sb.append("            }else{\n");
		sb.append("                tmp =\"action=\\\"treeclick('\"+" + lowerPojo + ".getPrimaryKey()+\"','\"+" + lowerPojo + ".getName()+\"');\\\"\";\n");
		sb.append("            }\n");
		sb.append("        }\n");
		sb.append("        //输出树节点\n");
		sb.append("        sb.append(\"<tree \"+chstr+\" id =\\\"" + lowerPojo + "_\"+" + lowerPojo + ".getPrimaryKey()+\"\\\" text=\\\"\"+" + lowerPojo + ".getName()+\"\\\" value=\\\"\"+val+\"\\\" \"+tmp+\"/>\");\n");
		sb.append("    }\n");
		sb.append("sb.append(\"</tree>\");\n");
		sb.append("UtilTool.writeTextXml(response,sb.toString());\n");
		sb.append("}\n");
		sb.append("%>\n");

		return sb.toString();
	}
	
}
