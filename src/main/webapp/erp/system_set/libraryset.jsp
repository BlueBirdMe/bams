<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common.jsp"%>
<%@include file="../editmsgbox.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>业务字典</title>
<script type="text/javascript" src="<%=contextPath%>/dwr/interface/dwrSysProcessService.js"></script>
<script type="text/javascript">

//查询方法
function queryData(){
	startQuery();
	var syslib = getQueryParam();
	syslib.libraryInfoIsvalid = <%=EnumUtil.SYS_ISACTION.Vaild.value%>;
	//取得树值
	syslib.libraryInfoUpcode = document.getElementById("upcode").value;
	var pager = getPager();
	dwrSysProcessService.getSysLibraryInfoListByPager(syslib,pager,queryCallback);
}

function queryCallback(data){
	if(data.success == true){
		initGridData(data.resultList,data.pager);
	}else{
		alertmsg(data);
	}
	endQuery();
}

//双击数据
function dblCallback(obj){
}
function deleteObject(id){
	confirmmsg("确定要删除记录吗?","del("+id+")");
}

function del(id){
	var recordsPks = new Array();
	recordsPks[0] = id;
	dwrSysProcessService.deleteSysLibraryInfoByPks(recordsPks,setcallback);
}

function edit(id){
	MoveDiv.show('编辑数据字典','<%=contextPath%>/erp/system_set/libraryset_add.jsp?lid='+id);
}

function repIsEdit(rowObj){
	var ed = rowObj.libraryInfoIsedit;
	var str="";
	if(ed == <%=EnumUtil.SYS_ISEDIT.EDIT.value%>){
		str ="<%=EnumUtil.SYS_ISEDIT.valueOf(EnumUtil.SYS_ISEDIT.EDIT.value)%>";
	}else{
		str ="<font color='red'><%=EnumUtil.SYS_ISEDIT.valueOf(EnumUtil.SYS_ISEDIT.No_EDIT.value)%></font>";
	}
	return str;
}
function add(){
	var url = '<%=contextPath%>/erp/system_set/libraryset_add.jsp';
	openMDITab(url + "?tab="+getMDITab());
}

function createMethod(rowObj){
	var	str="";
		if(rowObj.libraryInfoIsedit==<%=EnumUtil.SYS_ISEDIT.EDIT.value%>){
			str+="<a href='javascript:void(0)' title='编辑' onclick=\"edit('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowedit.png' border='0'/></a>";
			str+="&nbsp;&nbsp;<a href='javascript:void(0)' title='删除' onclick=\"deleteObject('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowdel.png' border='0'/></a>";
		}else{
			str+="<a href='javascript:void(0)' title='不可以编辑' ><img src='<%=contextPath%>/images/grid_images/rowedit_.png' border='0'/></a>";
			str+="&nbsp;&nbsp;<a href='javascript:void(0)' title='不能删除' ><img src='<%=contextPath%>/images/grid_images/rowdel_.png' border='0'/></a>";
		}
		return str;
	}

	function setcallback(data) {
		tree.reload();
		alertmsg(data, "queryData()");
	}
</script>
</head>
<body>
	<table width="100%" cellpadding="0" cellspacing="0" border="0" height="100%">
		<tr>
			<td id="split_l">
				<div class="div_title">选择上级</div>
				<div class="div_content">
					<input type="hidden" id="treejs" value="treeclick"> 
					<jsp:include page="../system_manger/sys_library_tree.jsp" flush="false"></jsp:include>
				</div>
			</td>
			<td>
				<%
					SysGrid grid = new SysGrid(request, "系统业务字典列表");
					grid.setShowImg(false);//不显示图片信息
					//放入按钮
					ArrayList<SysGridBtnBean> btnList = new ArrayList<SysGridBtnBean>();
					btnList.add(new SysGridBtnBean("新增字典", "add()", "add.png"));
					grid.setBtnList(btnList);

					//放入列
					ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(UtilTool.getColumnShow(this.getServletContext(), "业务字典列表"));

					//进行高级查询显示处理
					for (int i = 0; i < colList.size(); i++) {
						SysGridColumnBean bc = colList.get(i);
						if ("libraryInfoIsedit".equalsIgnoreCase(bc.getDataName())) {
							SelectType select = new SelectType(EnumUtil.SYS_ISEDIT.getSelectAndText("-1,-请选择是否允许编辑-"));
							select.setCustomerFunction(new String[] { "onchange=\"queryData();\"" });
							bc.setColumnTypeClass(select);
							bc.setColumnReplace("repIsEdit");
							bc.setColumnStyle("text-align:center;");
						}
					}

					grid.setColumnList(colList);

					grid.setShowProcess(true);
					grid.setProcessMethodName("createMethod");

					//设置附加信息
					grid.setQueryFunction("queryData"); //查询的方法名
					grid.setDblFunction("dblCallback"); //双击列的方法名，又返回值，为列对象
					grid.setDblBundle("primaryKey"); //双击列的绑定的列值

					//开始创建
					out.print(grid.createTable());
				%>
			</td>
		</tr>
	</table>
</body>
</html>