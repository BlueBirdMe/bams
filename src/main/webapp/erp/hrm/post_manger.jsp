<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
    <%@ include file="../common.jsp" %>
<%@include  file="../editmsgbox.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>岗位管理</title>
<script type="text/javascript" src="<%=contextPath%>/dwr/interface/dwrHrmEmployeeService.js"></script>
<script type="text/javascript">
//查询方法
function queryData(){
	startQuery();
	var post = getQueryParam();
	post.hrmPostUpid = document.getElementById("postcode").value;
	var pager = getPager();
	dwrHrmEmployeeService.listPostByPager(post,pager,queryCallback);
}

function queryCallback(data){
	if(data.success == true){
		initGridData(data.resultList,data.pager);
	}else{
		alert(data.message);
	}
	endQuery();
}

//双击数据
function dblCallback(obj){
	var box = new Sys.msgbox('明细查看','<%=contextPath%>/erp/hrm/post_detail.jsp?pid='+obj.value,'700','450');
		box.msgtitle="<b>查看岗位信息明细</b>";
		box.show();
}

function edit(id){
	MoveDiv.show('编辑岗位信息','<%=contextPath%>/erp/hrm/post_add.jsp?pid='+id+'');
}

function del(pk){
	confirmmsg("确定要删除岗位吗?","delok("+pk+")");
}

function delok(pk){
	var pks = new Array();
	pks[0] = pk;
	dwrHrmEmployeeService.deletePostsByPks(pks,delcallback);
}

function delcallback(data){
    tree.reload();
	alertmsg(data,"queryData()");
}


function delbatch(){
	if(getAllRecordArray() != false){
		confirmmsg("确定要删除岗位吗?","delbatchok()");
	}else{
		alertmsg("请选择要删除的岗位...");
	}
}

function set(id){
	var box = new Sys.msgbox('岗位负责人设置',"<%=contextPath%>/erp/select_takepage/select_empbypostid.jsp?postid="+id+"&textid=tmptxt",'700','450');
	box.msgtitle="<b>岗位人员信息</b>";
	var butarray =new Array();
	butarray[0] = "ok|clickok(null,'setok()');";
	butarray[1] = "cancel";
	box.buttons = butarray;
	box.show();
	postid = id;
}

function setok(){
	var str = document.getElementById("tmptxt").value;
	if(postid!=null&&str.length>0){
		dwrHrmEmployeeService.updatePostManger(postid,str,setcallback);
	}
}

function setcallback(data){
	document.getElementById("tmptxt").value="";
	alertmsg(data,"queryData()");
}


function delbatchok(){
	var recordsPks = getAllRecordArray();
	dwrHrmEmployeeService.deletePostsByPks(recordsPks,delcallback);
}

function createProcessMethod(rowObj){
	var	str="<a href='javascript:void(0)' title='编辑' onclick=\"edit('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowedit.png' border='0'/></a>";
	str+="&nbsp;&nbsp;<a href='javascript:void(0)' title='负责人设置' onclick=\"set('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/seta.png' border='0'/></a>";
	str+="&nbsp;&nbsp;<a href='javascript:void(0)' title='删除' onclick=\"del('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowdel.png' border='0'/></a>";
	var coder = document.getElementById("postcode").value;
    if(coder != null && coder.length>0){
		str+="&nbsp;&nbsp;<a href='javascript:void(0)' title='向上移动' onclick=\"treeshowmove('"+rowObj.primaryKey+"',<%=EnumUtil.Tree_MOVE_Type.MOVE_UP.value%>)\"><img src='<%=contextPath%>/images/grid_images/treeup.jpg' border='0'/></a>";
		str+="&nbsp;&nbsp;<a href='javascript:void(0)' title='向下移动' onclick=\"treeshowmove('"+rowObj.primaryKey+"',<%=EnumUtil.Tree_MOVE_Type.MOVE_DOWN.value%>)\"><img src='<%=contextPath%>/images/grid_images/treedown.jpg' border='0'/></a>";
	}	
	return str;
}
function treeshowmove(id,type){
	dwrHrmEmployeeService.treeMoveShowRowByPost(id,type,movecallback);
}

function movecallback(data){
	alertmsg(data,"moveok()");
}

function moveok(){
	tree.reload();
	queryData();
}
function repcolor(rowObj){
	var str="";
	if(rowObj.mangerEmployee==null || rowObj.mangerEmployee.hrmEmployeeName==null){
		str ="<font color='red'><无></font>";
	}else{
		str = "<font color='green'/>"+rowObj.mangerEmployee.hrmEmployeeName+"</font>";
	}
	return str;
}
</script>
</head>
<body>
<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<tr>
<td id="split_l">

<div class="div_title">选择岗位</div>
<div class="div_content">
<jsp:include page="post_tree.jsp" flush="false"></jsp:include>
</div>
</td>
<td>	
<%
	SysGrid bg = new SysGrid(request);

	bg.setTableTitle("工作岗位列表");
	bg.setShowImg(false);

	//设置附加信息
	bg.setQueryFunction("queryData"); //查询的方法名
	bg.setDblFunction("dblCallback"); //双击列的方法名，又返回值，为列对象
	bg.setDblBundle("primaryKey"); //双击列的绑定的列值

	//放入按钮
	ArrayList<SysGridBtnBean> btnList = new ArrayList<SysGridBtnBean>();
	btnList.add(new SysGridBtnBean("批量删除", "delbatch()", "close.png"));
	bg.setBtnList(btnList);

	//放入列
	ArrayList<SysGridColumnBean> colList = UtilTool
			.getGridColumnList(UtilTool.getColumnShow(this
					.getServletContext(), "工作岗位"));

                        for (int i = 0; i < colList.size(); i++) {
		SysGridColumnBean bc = colList.get(i);
		if ("mangerEmployee.hrmEmployeeName".equalsIgnoreCase(bc
				.getDataName())) {
			bc.setColumnReplace("repcolor");
			bc.setColumnStyle("text-align:center");
		}
	}
	
	bg.setColumnList(colList);

	//设置列操作对象
	bg.setShowProcess(true);//默认为false 为true请设置processMethodName
	bg.setProcessMethodName("createProcessMethod");//生成该操作图标的js方法,系统默认放入数据行对象

	//开始创建
	out.print(bg.createTable());
%>
</td>
</tr>
</table>
<input type="hidden" id="tmptxt">
</body>
</html>