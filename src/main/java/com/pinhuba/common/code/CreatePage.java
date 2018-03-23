package com.pinhuba.common.code;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import com.pinhuba.common.annotation.Remark;
import com.pinhuba.common.code.bean.CodeConfig;
import com.pinhuba.common.code.bean.ColumnProperty;
import com.pinhuba.common.code.component.Component;
import com.pinhuba.common.code.component.ComponentFactory;
import com.pinhuba.common.code.util.SaveFile;

/**
 * 适用于BAMS项目创建页面
 * 
 * @author JC
 */
public class CreatePage {
	private String pojoClass = null;
	private String pojoShortName = null;
	private String pojoShortName1 = null;
	private String pojoShortName2 = null;
	private String pojoName = null;
	private String pagePath = CodeConfig.getInstance().getPagePath();
	private String dwrName = null;
	private int columnCount = 2;// 新增页、明细页默认显示2列
	private String folderName = null;
	private Boolean hasFck = false;
	private Boolean hasUploadImg = false;
	private Boolean hasUploadFile = false;

	private List<Field> fields = new ArrayList<Field>();

	public CreatePage() {
		super();
	}

	public CreatePage(String pojoClass, String pojoShortName, String pojoName, String dwrName, String columnCount, String folderName) throws ClassNotFoundException {
		this.pojoClass = pojoClass;
		this.pojoName = pojoName;
		this.pojoShortName = pojoShortName;
		this.pojoShortName1 = handlerName(pojoShortName, 1);// 大写开头 驼峰标示
		this.pojoShortName2 = handlerName(pojoShortName, 2);// 小写开头 驼峰标示
		this.dwrName = dwrName;
		this.columnCount = Integer.valueOf(columnCount);
		this.folderName = folderName;

		Class<?> clazz = Class.forName("com.pinhuba.core.pojo." + this.pojoClass);
		Field[] tmp = clazz.getDeclaredFields();// 根据Class对象获得属性 私有的也可以获得

		for (Field field : tmp) {
			if (field.getAnnotations() != null && field.getAnnotations().length > 0) {
				int componentType = getColumnProperty(field).getComponentType();
				if (Component.RICHTEXTAREA == componentType)
					hasFck = true;// 是否存在FCK富文本
				if (Component.UPLOADIMG == componentType)
					hasUploadImg = true;
				if (Component.UPLOADFILE == componentType)
					hasUploadFile = true;
				fields.add(field);
			}
		}

	}

	public void getAddPage() throws Exception {
		String reString = this.getAddPageString();
		SaveFile.writeFile(pagePath + folderName + "/" + pojoShortName + "_add.jsp", reString);// 写入文件
	}

	private String getAddPageString() throws ClassNotFoundException {
		// 开始拼装页面
		StringBuffer sb = new StringBuffer();
		sb.append("<%@page language=\"java\" contentType=\"text/html; charset=UTF-8\" pageEncoding=\"UTF-8\"%>\n");
		sb.append("<%@include file=\"../common.jsp\" %>\n");

		sb.append("<%\n");
		sb.append("    String tab = request.getParameter(\"tab\");\n");
		sb.append("    String pk = request.getParameter(\"pk\");\n");
		sb.append("    String isedit = \"false\";\n");
		sb.append("    String saveOrEdit = \"新增\";\n");
		sb.append("    String helpTitle = \"您可以在此处添加您想新增的" + pojoName + "！\";\n");
		sb.append("    if(pk != null){\n");
		sb.append("        isedit = \"true\";\n");
		sb.append("        saveOrEdit = \"编辑\";\n");
		sb.append("        helpTitle = \"您可以在此处编辑" + pojoName + "信息！\";\n");
		sb.append("    }\n");
		sb.append("%>\n");

		sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n");
		sb.append("<html>\n");
		sb.append("<head>\n");
		sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
		sb.append("<title><%=saveOrEdit%>" + pojoName + "</title>\n");
		sb.append("<script type=\"text/javascript\" src=\"<%=contextPath%>/dwr/interface/" + dwrName + ".js\"></script>\n");
		sb.append("<script type=\"text/javascript\">\n");

		sb.append("window.onload = function(){\n");
		sb.append("    useLoadingMassage();\n");
		sb.append("    initInput(\"helpTitle\",\"<%=helpTitle%>\");\n");
		sb.append("    saveOrEdit();\n");
		sb.append("}\n");

		sb.append("function saveOrEdit(){\n");
		sb.append("    if(<%=isedit%>){\n");
		sb.append("        var pk = '<%=pk%>';\n");
		sb.append("        " + dwrName + ".get" + pojoClass + "ByPk(pk,set" + pojoShortName1 + ");\n");
		sb.append("    }\n");
		sb.append("}\n\n");

		if (hasFck) {
			sb.append("var fckvalue = \"\";\n");
			sb.append("var fck;\n");
			sb.append("function FCKeditor_OnComplete(editorInstance) {\n");
			sb.append("    fck = editorInstance;\n");
			sb.append("    editorInstance.SetHTML(fckvalue);\n");
			sb.append("    window.status = editorInstance.Description;\n");
			sb.append("}\n\n");
		}

		sb.append("function set" + pojoShortName1 + "(data){\n");
		sb.append("    if(data.success == true){\n");
		sb.append("        if(data.resultList.length > 0){\n");
		sb.append("            var " + pojoShortName2 + " = data.resultList[0];\n");

		for (Field f : fields) {
			String fieldName = f.getName();
			String fieldValue = pojoShortName2 + "." + fieldName;
			Component com = ComponentFactory.getComponent(getColumnProperty(f).getComponentType());
			sb.append("            " + com.getCommponentSetValueStr(fieldName, fieldValue) + "\n");
		}

		sb.append("        }else{\n");
		sb.append("            alert(data.message);\n");
		sb.append("        }\n");
		sb.append("    }else{\n");
		sb.append("        alert(data.message);\n");
		sb.append("    }\n");
		sb.append("}\n");

		sb.append("function save(){\n");
		sb.append("    var warnArr = new Array();\n");
		sb.append("    //清空所有信息提示\n");
		sb.append("    warnInit(warnArr);\n");
		sb.append("    var bl = validvalue('helpTitle');\n");
		sb.append("    if(bl){\n");
		sb.append("        //此处可编写js代码进一步验证数据项\n\n");
		if (hasUploadFile)
			sb.append("        var attach = DWRUtil.getValue(\"\");//附件\n");
		if (hasUploadImg)
			sb.append("        var imgfile = DWRUtil.getValue(\"\");//图片\n");
		sb.append("        //Btn.close();\n");
		sb.append("        if(<%=isedit%>){\n");
		sb.append("            " + dwrName + ".update" + pojoClass + "(get" + pojoShortName1 + "(),updateCallback);\n");
		sb.append("        }else{\n");
		sb.append("            " + dwrName + ".save" + pojoClass + "(get" + pojoShortName1 + "(),saveCallback);\n");
		sb.append("        }\n");
		sb.append("    }\n");
		sb.append("}\n");

		sb.append("function get" + pojoShortName1 + "(){\n");
		sb.append("    var " + pojoShortName2 + " = new Object();\n");
		sb.append("    if(<%=isedit%>){\n");
		sb.append("        " + pojoShortName2 + ".primaryKey = '<%=pk%>';\n");
		sb.append("    }\n");

		for (Field f : fields) {
			Component com = ComponentFactory.getComponent(getColumnProperty(f).getComponentType());
			sb.append("    " + com.getCommponentGetValueStr(pojoShortName2, f.getName()) + "\n");
		}

		sb.append("    return " + pojoShortName2 + ";\n");
		sb.append("}\n");

		sb.append("function saveCallback(data){\n");
		sb.append("    //Btn.open();\n");
		sb.append("    if(data.success){\n");
		sb.append("        confirmmsgAndTitle(\"添加" + pojoName + "成功！是否想继续添加" + pojoName + "？\",\"reset();\",\"继续添加\",\"closePage();\",\"关闭页面\");\n");
		sb.append("    }else{\n");
		sb.append("        alertmsg(data);\n");
		sb.append("    }\n");
		sb.append("}\n");

		sb.append("function updateCallback(data){\n");
		sb.append("    //Btn.open();\n");
		sb.append("    if(data.success){\n");
		sb.append("        alertmsg(data,\"closePage();\");\n");
		sb.append("    }else{\n");
		sb.append("        alertmsg(data);\n");
		sb.append("    }\n");
		sb.append("}\n");

		sb.append("function reset(){\n");
		sb.append("    Sys.reload();\n");
		sb.append("}\n");

		sb.append("function closePage(){\n");
		sb.append("    closeMDITab(<%=tab%>);\n");
		sb.append("}\n");

		sb.append("</script>\n");
		sb.append("</head>\n");
		sb.append("<body class=\"inputcls\">\n");
		sb.append("    <div class=\"formDetail\">\n");
		sb.append("        <div class=\"requdiv\"><label id=\"helpTitle\"></label></div>\n");
		sb.append("        <div class=\"formTitle\"><%=saveOrEdit%>" + pojoName + "</div>\n");
		sb.append("        <table class=\"inputtable\">\n");

		for (int i = 0; i < fields.size(); i++) {
			if (columnCount == 1) {
				sb.append("            <tr>\n");
				sb.append(createThtd(fields.get(i)));
				sb.append("            </tr>\n");
			} else if (columnCount == 2) {
				if ((i % 2) == 0) {
					sb.append("            <tr>\n");
					sb.append(createThtd(fields.get(i)));
					if ((i + 1) < fields.size()) {
						sb.append(createThtd(fields.get(i + 1)));
					} else {
						sb.append("                <th></th><td></td>\n");
					}
					sb.append("            </tr>\n");
				}
			} else if (columnCount == 3) {
				if ((i % 3) == 0) {
					sb.append("            <tr>\n");
					sb.append(createThtd(fields.get(i)));
					if ((i + 1) < fields.size()) {
						sb.append(createThtd(fields.get(i + 1)));
					} else {
						sb.append("                <th></th><td></td>\n");
					}
					if ((i + 2) < fields.size()) {
						sb.append(createThtd(fields.get(i + 2)));
					} else {
						sb.append("                <th></th><td></td>\n");
					}
					sb.append("            </tr>\n");
				}
			}
		}

		sb.append("        </table>\n");
		sb.append("    </div>\n");

		sb.append("    <table align=\"center\">\n");
		sb.append("        <tr>\n");
		sb.append("            <td><btn:btn onclick=\"save();\" value=\"保 存 \" imgsrc=\"../../images/png-1718.png\" title=\"保存" + pojoName + "信息\" /></td>\n");
		sb.append("            <td style=\"width:20px;\"></td>\n");
		sb.append("            <td><btn:btn onclick=\"closePage();\" value=\"关 闭 \" imgsrc=\"../../images/winclose.png\" title=\"关闭当前页面\"/></td>\n");
		sb.append("        </tr>\n");
		sb.append("    </table>\n");

		sb.append("</body>\n");
		sb.append("</html>\n");
		return sb.toString();
	}

	private String createThtd(Field f) {
		ColumnProperty property = getColumnProperty(f);
		String name = property.getName();
		String requiredString = "";
		String requiredLabel = "";
		String requiredFlag = "";
		if (property.getIsRequired() == 1) {
			requiredString = " must=\"" + name + "不能为空!\" formust=\"" + f.getName() + "Must\"";
			requiredLabel = "<label id=\"" + f.getName() + "Must\"></label>";
			requiredFlag = "<em>*</em>&nbsp;&nbsp;";
		}

		Component com = ComponentFactory.getComponent(property.getComponentType());
		StringBuffer sb = new StringBuffer();
		sb.append("                <th>" + requiredFlag + name + "</th>\n");
		sb.append("                <td>\n");
		sb.append("                    " + com.getCommponentDefine(f.getName(), requiredString, requiredLabel) + "\n");
		sb.append("                </td>\n");
		return sb.toString();
	}

	// 列表页
	public void getManagePage() throws Exception {
		String reString = this.getManagePageString();
		SaveFile.writeFile(pagePath + folderName + "/" + pojoShortName + "_manage.jsp", reString);// 写入文件
	}

	private String getManagePageString() throws ClassNotFoundException {

		StringBuffer sb = new StringBuffer();
		sb.append("<%@page language=\"java\" contentType=\"text/html; charset=UTF-8\"  pageEncoding=\"UTF-8\"%>\n");
		sb.append("<%@include file=\"../common.jsp\" %>\n");
		sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n");
		sb.append("<html>\n");
		sb.append("<head>\n");
		sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
		sb.append("<title>" + pojoName + "管理</title>\n");
		sb.append("<script type=\"text/javascript\" src=\"<%=contextPath%>/dwr/interface/" + dwrName + ".js\"></script>\n");
		sb.append("<script type=\"text/javascript\">\n");

		sb.append("//查询方法\n");
		sb.append("function queryData(){\n");
		sb.append("    startQuery();\n");
		sb.append("    var " + pojoShortName2 + " = getQueryParam();//java实体类相对应\n");
		sb.append("    var pager = getPager();\n");
		sb.append("    " + dwrName + ".list" + pojoClass + "(" + pojoShortName2 + ",pager,queryCallback);\n");
		sb.append("}\n");

		sb.append("function queryCallback(data){\n");
		sb.append("    if(data.success == true){\n");
		sb.append("        initGridData(data.resultList,data.pager);\n");
		sb.append("    }else{\n");
		sb.append("        alert(data.message);\n");
		sb.append("    }\n");
		sb.append("    endQuery();\n");
		sb.append("}\n");

		sb.append("//双击数据\n");
		sb.append("function dblCallback(obj){\n");
		sb.append("    var box = new Sys.msgbox(\"明细查看\",\"<%=contextPath%>/erp/" + folderName + "/" + pojoShortName + "_detail.jsp?pk=\"+obj.value);\n");
		sb.append("    box.show();\n");
		sb.append("}\n");

		sb.append("function edit(pk){\n");
		sb.append("    openMDITab(\"<%=contextPath%>/erp/" + folderName + "/" + pojoShortName + "_add.jsp?pk=\"+pk+\"&tab=\"+getMDITab());\n");
		sb.append("}\n");

		sb.append("function createProcessMethod(rowObj){\n");
		sb.append("    var str=\"<a href='javascript:void(0)' title='编辑' onclick=\\\"edit('\"+rowObj.primaryKey+\"')\\\"><img src='<%=contextPath%>/images/grid_images/rowedit.png' border='0'/></a>\";\n");
		sb.append("    str += \"&nbsp;&nbsp;<a href='javascript:void(0)' title='删除' onclick=\\\"del('\"+rowObj.primaryKey+\"')\\\"><img src='<%=contextPath%>/images/grid_images/rowdel.png' border='0'/></a>\";\n");
		sb.append("    return str;\n");
		sb.append("}\n");

		sb.append("function del(pk){\n");
		sb.append("    confirmmsg(\"确定要删除" + pojoName + "吗?\",\"delok('\"+pk+\"')\");\n");
		sb.append("}\n");

		sb.append("function delok(pk){\n");
		sb.append("    var pks = new Array();\n");
		sb.append("    pks[0] = pk;\n");
		sb.append("    " + dwrName + ".delete" + pojoClass + "ByPks(pks,delCallback);\n");
		sb.append("}\n");

		sb.append("function delCallback(data){\n");
		sb.append("    alertmsg(data,\"queryData()\");\n");
		sb.append("}\n");

		sb.append("function delbatch(){\n");
		sb.append("    if(getAllRecordArray() != false){\n");
		sb.append("        confirmmsg(\"确定要删除" + pojoName + "吗?\",\"delbatchok()\");\n");
		sb.append("    }else{\n");
		sb.append("        alertmsg(\"请选择要删除的" + pojoName + "...\");\n");
		sb.append("    }\n");
		sb.append("}\n");

		sb.append("function delbatchok(){\n");
		sb.append("    var pks = getAllRecordArray();\n");
		sb.append("    " + dwrName + ".delete" + pojoClass + "ByPks(pks,delCallback);\n");
		sb.append("}\n");

		sb.append("function add(){\n");
		sb.append("    openMDITab(\"<%=contextPath%>/erp/" + folderName + "/" + pojoShortName + "_add.jsp?tab=\"+getMDITab());\n");
		sb.append("}\n");

		sb.append("</script>\n");
		sb.append("</head>\n");
		sb.append("<body>\n");
		sb.append("<%\n");
		sb.append("SysGrid grid = new SysGrid(request,\"" + pojoName + "列表\");\n");

		sb.append("//放入按钮\n");
		sb.append("ArrayList<SysGridBtnBean> btnList = new ArrayList<SysGridBtnBean>();\n");
		sb.append("btnList.add(new SysGridBtnBean(\"新增\", \"add()\", \"add.png\"));\n");
		sb.append("btnList.add(new SysGridBtnBean(\"批量删除\", \"delbatch()\", \"close.png\"));\n");
		sb.append("grid.setBtnList(btnList);\n");
		sb.append("//放入操作提示，请在系统管理-帮助管理处添加\n");
		sb.append("grid.setHelpList(UtilTool.getGridTitleList(this.getServletContext(), request));\n");
		sb.append("ArrayList<SysColumnControl> sccList = new ArrayList<SysColumnControl>();\n");

		for (Field f : fields) {
			Remark ann = f.getAnnotation(Remark.class);
			String[] remarkArray = ann.value().split("\\|");
			sb.append("sccList.add(new SysColumnControl(\"" + f.getName() + "\",\"" + remarkArray[0] + "\",1," + remarkArray[1] + "," + remarkArray[2] + ",0));\n");
		}

		sb.append("ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(sccList);\n");

		sb.append("for(int i = 0; i < colList.size(); i++){\n");
		sb.append("    SysGridColumnBean bc = colList.get(i);\n");
		sb.append("    if (\"\".equalsIgnoreCase(bc.getDataName())){\n");
		sb.append("        bc.setColumnReplace(\"\");\n");
		sb.append("        bc.setColumnStyle(\"text-align:center\");\n");
		sb.append("    }\n");
		sb.append("}\n");

		sb.append("grid.setColumnList(colList);\n");

		sb.append("//设置附加信息\n");
		sb.append("grid.setShowImg(false);\n");
		sb.append("grid.setQueryFunction(\"queryData\");//查询的方法名\n");
		sb.append("grid.setDblFunction(\"dblCallback\");//双击列的方法名，有返回值，为列对象\n");
		sb.append("grid.setDblBundle(\"primaryKey\");//双击列的绑定的列值\n");
		sb.append("grid.setShowProcess(true);//默认为false 为true请设置processMethodName\n");
		sb.append("grid.setProcessMethodName(\"createProcessMethod\");//生成该操作图标的js方法,系统默认放入数据行对象\n");
		sb.append("out.print(grid.createTable());\n");
		sb.append("%>\n");
		sb.append("</body>\n");
		sb.append("</html>\n");

		return sb.toString();
	}

	public void getDetailPage() throws Exception {
		String reString = this.getDetailPageString();
		SaveFile.writeFile(pagePath + folderName + "/" + pojoShortName + "_detail.jsp", reString);// 写入文件
	}

	private String getDetailPageString() throws ClassNotFoundException {
		StringBuffer sb = new StringBuffer();
		sb.append("<%@page language=\"java\" contentType=\"text/html; charset=UTF-8\"  pageEncoding=\"UTF-8\"%>\n");
		sb.append("<%@include file=\"../common.jsp\" %>\n");
		sb.append("<%\n");
		sb.append("    String pk = request.getParameter(\"pk\");\n");
		sb.append("%>\n");
		sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n");
		sb.append("<html>\n");
		sb.append("<head>\n");
		sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
		sb.append("<title>" + pojoName + "明细</title>\n");
		sb.append("<script type=\"text/javascript\" src=\"<%=contextPath%>/dwr/interface/" + dwrName + ".js\"></script>\n");
		sb.append("<script type=\"text/javascript\">\n");

		sb.append("window.onload = function(){\n");
		sb.append("    " + dwrName + ".get" + pojoClass + "ByPk('<%=pk%>',setPageValue);\n");
		sb.append("}\n");

		sb.append("function setPageValue(data){\n");
		sb.append("    if(data.success == true){\n");
		sb.append("        if(data.resultList.length > 0){\n");
		sb.append("            var " + pojoShortName2 + " = data.resultList[0];\n");

		for (Field f : fields) {
			String fieldName = f.getName();
			String fieldValue = pojoShortName2 + "." + fieldName;
			Component com = ComponentFactory.getComponent(getColumnProperty(f).getComponentType());
			sb.append("            " + com.getCommponentSetDetailStr(fieldName, fieldValue) + "\n");
		}

		sb.append("        }else{\n");
		sb.append("            alert(data.message);\n");
		sb.append("        }\n");
		sb.append("    }else{\n");
		sb.append("        alert(data.message);\n");
		sb.append("    }\n");
		sb.append("}\n");

		sb.append("</script>\n");
		sb.append("</head>\n");
		sb.append("<body class=\"inputdetail\">\n");
		sb.append("    <div class=\"requdivdetail\"><label>查看帮助:&nbsp; 显示" + pojoName + "相关信息！</label></div>\n");
		sb.append("    <div class=\"detailtitle\">" + pojoName + "明细</div>\n");
		sb.append("    <table class=\"detailtable\">\n");

		for (int i = 0; i < fields.size(); i++) {
			if (columnCount == 1) {
				sb.append("        <tr>\n");
				sb.append(createDetailThtd(fields.get(i)));
				sb.append("        </tr>\n");
			} else if (columnCount == 2) {
				if ((i % 2) == 0) {
					sb.append("        <tr>\n");
					sb.append(createDetailThtd(fields.get(i)));
					if ((i + 1) < fields.size()) {
						sb.append(createDetailThtd(fields.get(i + 1)));
					} else {
						sb.append("            <th></th><td></td>\n");
					}
					sb.append("        </tr>\n");
				}
			} else if (columnCount == 3) {
				if ((i % 3) == 0) {
					sb.append("        <tr>\n");
					sb.append(createDetailThtd(fields.get(i)));
					if ((i + 1) < fields.size()) {
						sb.append(createDetailThtd(fields.get(i + 1)));
					} else {
						sb.append("            <th></th><td></td>\n");
					}
					if ((i + 2) < fields.size()) {
						sb.append(createDetailThtd(fields.get(i + 2)));
					} else {
						sb.append("            <th></th><td></td>\n");
					}
					sb.append("        </tr>\n");
				}
			}
		}

		sb.append("    </table>\n");
		sb.append("</body>\n");
		sb.append("</html>\n");
		return sb.toString();
	}

	private String createDetailThtd(Field f) {
		Component com = ComponentFactory.getComponent(getColumnProperty(f).getComponentType());
		StringBuffer sb = new StringBuffer();
		sb.append("            ");
		sb.append("<th>" + getColumnProperty(f).getName() + "</th>\n");
		sb.append("            ");
		sb.append(com.getCommponentDetailDefine(f.getName()) + "\n");
		return sb.toString();
	}

	private ColumnProperty getColumnProperty(Field f) {
		ColumnProperty p = new ColumnProperty();
		Remark remark = f.getAnnotation(Remark.class);
		String[] remarkArray = remark.value().split("\\|");
		p.setName(remarkArray[0]);
		p.setIsShowSimple(Integer.valueOf(remarkArray[1]));
		p.setIsShowAdvanced(Integer.valueOf(remarkArray[2]));
		p.setComponentType(Integer.valueOf(remarkArray[3]));
		p.setIsRequired(Integer.valueOf(remarkArray[4]));
		return p;
	}

	private String handlerName(String pojoShortName, int type) {
		String tmp = pojoShortName.toLowerCase();
		String newStr = "";
		if (type == 1) {
			String[] tbs = tmp.split("_");
			for (int i = 0; i < tbs.length; i++) {
				String strat = tbs[i].substring(0, 1).toUpperCase();
				String end = tbs[i].substring(1, tbs[i].length());
				newStr += strat + end;
			}
		} else {
			String[] cols = tmp.split("_");
			for (int i = 0; i < cols.length; i++) {
				if (i == 0) {
					newStr += cols[i];
				} else {
					String strat = cols[i].substring(0, 1).toUpperCase();
					String end = cols[i].substring(1, cols[i].length());
					newStr += strat + end;
				}
			}
		}
		return newStr;
	}

	public void getTreePage() throws Exception {
		String reString = CreateTree.getTreePageString(dwrName, pojoClass, folderName, pojoShortName, pojoName);
		SaveFile.writeFile(pagePath + folderName + "/" + pojoShortName + "_tree.jsp", reString);

		reString = CreateTree.getTreeXmlPageString(dwrName, pojoClass, folderName, pojoShortName);
		SaveFile.writeFile(pagePath + folderName + "/" + pojoShortName + "_tree_xml.jsp", reString);
		
		reString = CreateTree.getTreeSelectPageString();
		SaveFile.writeFile(pagePath + folderName + "/" + pojoShortName + "_select.jsp", reString);
		
		reString = CreateTree.getTreeSelectXmlPageString(dwrName, pojoClass, folderName, pojoShortName);
		SaveFile.writeFile(pagePath + folderName + "/" + pojoShortName + "_select_xml.jsp", reString);
	}

}
