<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
    <%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户组拾取</title>
<%
String textid = request.getParameter("textid");
String valueid = request.getParameter("valueid");
 %>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrSysProcessService.js"></script>
<script type="text/javascript">
//查询方法
function queryData(){
	startQuery();
	var group = getQueryParam();
	var pager = getPager();
	dwrSysProcessService.listSysUserGroupBypager(group,pager,queryCallback);
}

function queryCallback(data){
	if(data.success == true){
		initGridData(data.resultList,data.pager);
	}else{
		alert(data.message);
	}
	endQuery();
}

function repleaDetailCount(rowObj){
	var str=rowObj.detailList.length;
	return str;
}
function groupclick(myfrmname){
     
    var win = Sys.getfrm();//获取index页面iframe window对象	
    if(myfrmname!=null&&myfrmname != "undefined" && myfrmname != undefined){
    	win = win.document.getElementById(myfrmname).contentWindow;	
    }
     
     var textid=win.document.getElementById("<%=textid%>"); 
     var valueid = win.document.getElementById("<%=valueid%>");
     
        var objs = getRowsObject();
        if(objs.length==0){
	    	alertmsg("请选择相应数据...");
	    	return;
		}
        var value="";
	    var text="";
        for(var i=0;i<objs.length;i++){
           value+=objs[i].primaryKey+",";
           text+=objs[i].groupName+",";
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

function groupclickcustomer(dialogId,myfrmname,method){
	groupclick(myfrmname);
	if(method != ""){
		var win = Sys.getfrm();//获取index页面iframe window对象
	    if(myfrmname!=null&&myfrmname != "undefined" && myfrmname != undefined){
	    	win = win.document.getElementById(myfrmname).contentWindow;	
	    }
		eval("win."+method);
	}
	Sys.close(dialogId);
}
</script>
</head>
<body>
<%
	SysGrid bg =new SysGrid(request);
bg.setShowView(SysGrid.SHOW_TABLE);
bg.setBorder(1);
bg.setTableTitle("用户编组列表");

//设置附加信息
bg.setQueryFunction("queryData");	//查询的方法名
bg.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
bg.setDblBundle("primaryKey");		//双击列的绑定的列值
bg.setIsshowProcessTool(false);
//放入列
ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(UtilTool.getColumnShow(this.getServletContext(),"人员编组"));
colList.add(ColumnUtil.getCusterShowColumn("count","用户数","repleaDetailCount",0,"text-align:center"));
bg.setColumnList(colList);


//开始创建
out.print(bg.createTable());
%>
</body>
</html>