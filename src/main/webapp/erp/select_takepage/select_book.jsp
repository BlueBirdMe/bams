<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>

<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOfficeResourcesService.js"></script>
<%
String textid = request.getParameter("textid");
String valueid = request.getParameter("valueid");
String treetype = request.getParameter("treetype");
%>
<title>图书信息</title>
<script>
//查询方法
function queryData(){
	startQuery();
	var book = getQueryParam();
	var type = document.getElementById("bookType.oaBooktypeName").value;
	if(type != null){
	    book.oaBookType = type;
	}
	var pager = getPager();
	dwrOfficeResourcesService.listBooks(book,pager,queryCallback);
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


function bookclick(myfrmname){
     
    var win = Sys.getfrm();//获取index页面iframe window对象
    if(myfrmname!=null&&myfrmname != "undefined" && myfrmname != undefined){
    	win = win.document.getElementById(myfrmname).contentWindow;	
    }
     
     var textid=win.document.getElementById("<%=textid%>"); 
     var valueid = win.document.getElementById("<%=valueid%>");
     
     var treetype= '<%=treetype%>';
     if (treetype == "radio"){
       if(getOneRecordArray() != false){
		var obj=getObjectByPk(getOneRecordArray());
		textid.value = obj.oaBookName;
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
           text+=objs[i].oaBookName+",";
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

function bookclickcustomer(dialogId,myfrmname,method){
	bookclick(myfrmname);
	if(method!=null&&method != "undefined" && method != undefined){
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

bg.setTableTitle("图书信息");
bg.setShowImg(false);//默认为true 显示切换视图 为true必须指定图片相关信息

//设置附加信息
bg.setQueryFunction("queryData");	//查询的方法名
bg.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
bg.setDblBundle("primaryKey");		//双击列的绑定的列值
bg.setCheckboxOrNum(false);

//放入列
//ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(UtilTool.getColumnShow(this.getServletContext(),"图书列表"));
    ArrayList<SysColumnControl> sccList = new ArrayList<SysColumnControl>();
    sccList.add(new SysColumnControl("oaBookCode","图书编号",1,1,1,20));
    sccList.add(new SysColumnControl("oaBookName","图书名称",1,1,1,20));
    sccList.add(new SysColumnControl("bookType.oaBooktypeName","图书类别",1,2,1,0));
    sccList.add(new SysColumnControl("oaBookRemain","剩余数量",1,2,2,0));
    ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(sccList);

//进行高级查询显示处理
for(int i=0;i<colList.size();i++){
	SysGridColumnBean bc =colList.get(i);
	if(bc.isShowAdvanced()||bc.isShowColumn()){
	
		if("bookType.oaBooktypeName".equalsIgnoreCase(bc.getDataName())){
	//设置高级查询显示样式
	
	SelectType select  = new SelectType(UtilTool.getBookTypeInfoList(this.getServletContext(),request,"-1,-请选择图书类型-"));
	select.setCustomerFunction(new String[]{"onchange=\"queryData();\""});
	bc.setColumnTypeClass(select);
	
	
	//设置列显示样式
	bc.setColumnStyle("text-align:center;");
		}
	}
}

bg.setColumnList(colList);

//开始创建
out.print(bg.createTable());
%>
</body>
</html>