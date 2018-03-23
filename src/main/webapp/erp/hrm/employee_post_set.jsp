<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrHrmEmployeeService.js"></script>
<title>岗位设置</title>
<script type="text/javascript">
window.onload = function(){
	initInput('title','岗位设置，您可以在此处设置人员岗位！');
}

function queryData(){
	startQuery();
	var employee = getQueryParam();
	var employeepks = tempUrlParameter.getTmpvalue();
	var pager = getPager();
	dwrHrmEmployeeService.listEmployees_post_set(employeepks,pager,queryCallback);
}

function queryCallback(data){
	if(data.success == true){
		initGridData(data.resultList,data.pager);
		if(data.resultList.length==1){
			var tmp = data.resultList[0];
			
			if(tmp.hrmEmployeePost!=null){
				document.getElementById("hrmEmployeePostId").value = tmp.hrmEmployeePostId;
				document.getElementById("hrmEmployeePostIdtext").value = tmp.hrmEmployeePost.hrmPostName;
			}
			
			if(tmp.hrmPartPost != null && tmp.hrmPartPost.length>0 ){
				document.getElementById("hrmPartPost").value = tmp.hrmPartPost;
				document.getElementById("hrmPartPosttext").value = tmp.hrmPartPostName;
			}	
		}
	}else{
		alert(data.message);
	}
	endQuery();
}

function save(){
	 var postid = document.getElementById("hrmEmployeePostId").value;
	 var partpostid = document.getElementById("hrmPartPost").value;
	 var employeepks = tempUrlParameter.getTmpvalue();
	 dwrHrmEmployeeService.listEmployees_post_setpost(employeepks,postid,partpostid,saveCallback);
}
	
function saveCallback(data){
	alertmsg(data,"returnload()");
}
    
function returnload(){
	closeMDITab(<%=request.getParameter("tab")%>);
}

function getPostUpid(){
    var box = SEL.getPostIds("radio","hrmEmployeePostIdtext","hrmEmployeePostId");
    box.show();
}

function getPartPostUpid(){
    var box = SEL.getPostIds("check","hrmPartPosttext","hrmPartPost");
    box.show();
}
</script>
</head>
<body class="inputcls">
<%
	SysGrid bg = new SysGrid(request);
	bg.setBodyScroll("auto");
	bg.setTableTitle("已选择人员");
	bg.setIsshowSimpleTool(false);
	bg.setIsshowProcessTool(false);
	bg.setShowImg(false);
	bg.setTableHeight("320px");
	bg.setCheckboxOrNum(false);
	bg.setTableRowSize(10);

	//设置附加信息
	bg.setQueryFunction("queryData"); //查询的方法名

	//放入列
	ArrayList<SysColumnControl> sccList = new ArrayList<SysColumnControl>();
	sccList.add(new SysColumnControl("hrmEmployeeName", "姓名", 1, 1, 1, 0));
	sccList.add(new SysColumnControl("hrmDepartment.hrmDepName", "部门", 1, 2, 2, 0));
	sccList.add(new SysColumnControl("hrmEmployeePost.hrmPostName", "岗位", 1, 2, 2, 0));
	sccList.add(new SysColumnControl("hrmPartPostName", "兼职岗位", 1, 2, 2, 0));

	ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(sccList);

	bg.setColumnList(colList);

	//开始创建
	out.print(bg.createTable());
%>
<div class="formDetail">
	<div class="requdiv">
		<label id="title"></label>
	</div>
	<div class="formTitle">
		设置岗位
	</div>
	   <table class="inputtable">
	      <tr>
		    <th><font color="black">主岗位</font></th>
			<td>
			<textarea  id="hrmEmployeePostIdtext" linkclear="hrmEmployeePostId" title="点击选择岗位" readonly="readonly" onclick="getPostUpid();" style="color: #999;"></textarea>
			<input type="hidden" id="hrmEmployeePostId" value="">
			</td>
		 </tr>
		 <tr>
			<th><font color="black">兼职岗位</font></th>
			<td>
			<textarea  id="hrmPartPosttext" linkclear="hrmPartPost" title="点击选择岗位" readonly="readonly" onclick="getPartPostUpid();" style="color: #999;"></textarea>
			<input type="hidden" id="hrmPartPost" value="">
			</td>
		</tr>	
		<tr>
		   <th></th>
		   <td>
		   <ul style="color: #808080;">
		   	<li>若主岗位或者兼职岗位为空，则所选人员的主岗位或者兼职岗位设置为空</li>
		   	<li>调整人员主岗位，将会影响<font color="green">岗位负责人</font>设置，调整正完成请修正岗位负责人</li>
		   </ul>
		   </td>
		</tr> 
	   </table>
	</div>
	<table align="center">
		<tr>
			<td>
				<btn:btn onclick="save();" value="保 存 " imgsrc="../../images/png-1718.png" title="保存岗位设置" />
			</td>
			<td style="width: 20px;"></td>
			<td id="btncancel">
				<btn:btn onclick="returnload()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面" />
			</td>
		</tr>
	</table>
</body>
</html>