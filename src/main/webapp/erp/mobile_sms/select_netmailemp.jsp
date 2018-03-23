<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>外部邮箱联系人</title>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrMailService.js"></script>
<%
String valueid = request.getParameter("valueid");
String treetype = request.getParameter("treetype");
%>
<script type="text/javascript">
//查询方法
function queryData(){
	startQuery();
	var person = getQueryParam();
	var pager = getPager();
	dwrMailService.getOaNetmailPersonByPager(person,pager,queryCallback);
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
     var valueid = win.document.getElementById("<%=valueid%>");
  
     var treetype= '<%=treetype%>';
     if (treetype == "radio"){
		if(getOneRecordArray() != false){
			var obj=getObjectByPk(getOneRecordArray());
			valueid.value = obj.oaNetmailEmpmail;
	   	}else{
			alertmsg("请选择相应数据记录...");
			return;
	   	}
     }else{
        var objs = getRowsObject();
        var value="";
	    if(objs.length==0){
			alertmsg("请选择相应数据记录...");
			return;
		}
        for(var i=0;i<objs.length;i++){
           value+=objs[i].oaNetmailEmpmail+";";
	    } 
		if(valueid.value == "" ||valueid.value == null){
			valueid.value = value;
		}else{
       		var tmps = removerepeatIds(valueid.value+value);
        	valueid.value = tmps;
        }
     }
}

function employeeclickcustomer(dialogId,myfrmname,method){
	employeeclick(myfrmname);
	if(method!=null&&method != "undefined" && method != undefined){
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
</script>
</head>
<body>
<%
	SysGrid bg = new SysGrid(request);

bg.setTableTitle("邮箱联系人列表");
bg.setShowImg(false);

//设置附加信息
bg.setQueryFunction("queryData"); //查询的方法名
bg.setDblFunction("dblCallback"); //双击列的方法名，又返回值，为列对象
bg.setDblBundle("primaryKey"); //双击列的绑定的列值


//放入列
ArrayList<SysColumnControl> sccList = new ArrayList<SysColumnControl>();
sccList.add(new SysColumnControl("oaNetmailEmpname","人员名称",1,1,1,30));
sccList.add(new SysColumnControl("oaNetmailEmpmail","邮箱地址",1,1,1,50));
sccList.add(new SysColumnControl("oaNetmailDate","创建时间",1,2,1,0));
ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(sccList); 
for(int i=0;i<colList.size();i++){
	SysGridColumnBean bc =colList.get(i);
	if("oaNetmailDate".equalsIgnoreCase(bc.getDataName())){
		DateType date = new DateType();
		bc.setColumnTypeClass(date);
	}
}
bg.setColumnList(colList);

//开始创建
out.print(bg.createTable());
%>
</body>
</html>