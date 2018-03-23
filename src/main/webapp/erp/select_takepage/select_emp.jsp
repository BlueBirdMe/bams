<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<%@page import="com.pinhuba.web.controller.dwr.DwrSysProcessService"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.pinhuba.core.pojo.SysRole"%>
<%@page import="com.pinhuba.core.pojo.SysUserGroup"%>
<%
WebApplicationContext webcontext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
DwrSysProcessService service = (DwrSysProcessService)webcontext.getBean("dwrSysProcessService");
List<SysUserGroup> groupList = service.listSysUserGroupAll(this.getServletContext(), request);
List<SysRole> roleList = service.listSysRoleAll(this.getServletContext(), request);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>选择人员</title>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrHrmEmployeeService.js"></script>
<script type="text/javascript" src="<%=contextPath %>/js/jquery.accordion.js"></script>

<%
String textid = request.getParameter("textid");
String valueid = request.getParameter("valueid");
String treetype = request.getParameter("treetype");
%>
<script type="text/javascript">
//查询方法
function queryData(){
	startQuery();
	var employee = getQueryParam();
    employee.hrmEmployeeDepidTree = document.getElementById("upcode").value;
    employee.groupId = document.getElementById("groupId").value;
    employee.roleId = document.getElementById("roleId").value;
	var pager = getPager();
	dwrHrmEmployeeService.listEmployees(employee,pager,queryCallback);
}

function queryCallback(data){
	if(data.success == true){
		initGridData(data.resultList,data.pager);
	}else{
		alertmsg(data,null,null,window);
	}
	endQuery();
}

//双击数据
function dblCallback(obj){
	var box = new Sys.msgbox('明细查看','<%=contextPath%>/erp/hrm/employee_detail.jsp?employeepk='+obj.value,'800','500');
	box.msgtitle="<b>查看人员信息明细</b>";
	box.show();
}

//部门树选择
function treeclick(code){
	document.getElementById("upcode").value = code;
	document.getElementById("groupId").value = "";
	queryData();
}

function employeeclick(myfrmname){
    var win = Sys.getfrm();//获取index页面iframe window对象
    if(myfrmname!=null&&myfrmname != "undefined" && myfrmname != undefined){
    	var myfrmnames = myfrmname.split("@@");
    	if(myfrmnames.length>1){
    		for(var i=0;i<myfrmnames.length;i++){
    			win = win.document.getElementById(myfrmnames[i]).contentWindow;
    		}
    	}else{
    		win = win.document.getElementById(myfrmname).contentWindow;
    	}
    }
     var textid = win.document.getElementById("<%=textid%>"); 
     var valueid = win.document.getElementById("<%=valueid%>");
  
     var treetype= '<%=treetype%>';
     if (treetype == "radio"){
       if(getOneRecordArray() != false){
		var obj=getObjectByPk(getOneRecordArray());
		textid.value = obj.hrmEmployeeName;
		valueid.value = obj.primaryKey;
	   }else{
			alertmsg("请选择相应数据记录...");
			return;
	   }
     
     }else{
        var objs = getRowsObject();
        var value="";
	    var text="";
	    if(objs.length==0){
			alertmsg("请选择相应数据记录...");
			return;
		}
        for(var i=0;i<objs.length;i++){
           value+=objs[i].primaryKey+",";
           text+=objs[i].hrmEmployeeName+",";
	    } 
	   if(valueid.value == "" ||valueid.value == null){
        valueid.value = value;
        textid.value = text;
        }else{
       		var tmps = removerepeat(valueid.value+value,textid.value+text);
        	textid.value = tmps[1];
        	valueid.value = tmps[0];
        }
     }
}

function employeeclickcustomer(dialogId,myfrmname,method){
	employeeclick(myfrmname);
	if(method != null && method.length > 0){
		var win = Sys.getfrm();//获取index页面iframe window对象
	    if(myfrmname!=null&&myfrmname != "undefined" && myfrmname != undefined){
	    	var myfrmnames = myfrmname.split("@@");
	    	if(myfrmnames.length>1){
	    		for(var i=0;i<myfrmnames.length;i++){
	    			win = win.document.getElementById(myfrmnames[i]).contentWindow;
	    		}
	    	}else{
	    		win = win.document.getElementById(myfrmname).contentWindow;
	    	}
    	}
		eval("win."+method);
	}
	Sys.close(dialogId);
}

function groupClick(groupId){
	document.getElementById("groupId").value = groupId;
	document.getElementById("roleId").value = "";
	document.getElementById("upcode").value = "";
	queryData();
}

function roleClick(roleId){
	document.getElementById("roleId").value = roleId;
	document.getElementById("groupId").value = "";
	document.getElementById("upcode").value = "";
	queryData();
}
</script>
</head>
<body>
<input type="hidden" id="upcode">
<input type="hidden" id="groupId">
<input type="hidden" id="roleId">
<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<tr>
<td id="split_l">
<div id="accord">
<div class="div_title">按部门选择</div>
<div class="div_content" style="width:170px;">
	<jsp:include page="emp_tree.jsp" flush="false">
	<jsp:param name="ischeck" value="true"/>
	</jsp:include>
</div>

<div class="div_title">按角色选择</div>
<div class="div_content">
<ul style="padding:10px;line-height:22px;">
<%
for(SysRole role : roleList){
%>
<li style="background:url(<%=contextPath%>/images/projectimg/file.png) 2px no-repeat;padding-left:25px;">
<a style="color:black;" href="javascript:roleClick(<%=role.getPrimaryKey() %>)">
<%=role.getRoleName() %>
</a>
</li>
<%
}
%>
</ul>
</div>
  
<div class="div_title">按用户组选择</div>
<div class="div_content">
<ul style="padding:10px;line-height:22px;">
<%
for(SysUserGroup group : groupList){
%>
<li style="background:url(<%=contextPath%>/images/projectimg/file.png) 2px no-repeat;padding-left:25px;">
<a style="color:black;" href="javascript:groupClick(<%=group.getPrimaryKey() %>)">
<%=group.getGroupName() %>
</a>
</li>
<%
}
%>
</ul>
</div>

</div>
</td>
<td>
<%
SysGrid bg =new SysGrid(request);
bg.setTableHeight("100%");//可以不指定,默认为100%
bg.setTableTitle("人员列表");
bg.setQueryFunction("queryData");	//查询的方法名
bg.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
bg.setDblBundle("primaryKey");		//双击列的绑定的列值
bg.setShowImg(false);
if(treetype.equals("radio")) bg.setCheckboxOrNum(false);

ArrayList<SysColumnControl> sccList = new ArrayList<SysColumnControl>();
sccList.add(new SysColumnControl("hrmEmployeeName","姓名",1,1,1,0));
sccList.add(new SysColumnControl("hrmEmployeeCode","工号",1,2,2,0));
sccList.add(new SysColumnControl("hrmDepartment.hrmDepName","部门",1,2,2,0));
ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(sccList);

bg.setColumnList(colList);
out.print(bg.createTable());
%>
</td>
</tr>
</table>
<script type="text/javascript">
$('#accord').accordion({
	header:"div.div_title",
	animated:false,
	fillSpace:true
});
</script>
</body>
</html>