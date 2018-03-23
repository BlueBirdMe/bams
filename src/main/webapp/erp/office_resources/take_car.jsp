<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="../common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script type="text/javascript"
			src="<%=contextPath%>/dwr/interface/dwrOfficeResourcesService.js"></script>
		<title>车辆拾取</title>
	    <%
			String textid = request.getParameter("textid");
			String valueid = request.getParameter("valueid");
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String cudate = sf.format(new Date());
			String id =request.getParameter("id");
		%>
	<script type="text/javascript">
//查询方法
function queryData(){
useLoadingMassage();
	var pager = getPager();
	startQuery();
	var sdate = document.getElementById("applyBegindate").value;
	var edate = document.getElementById("applyEnddate").value;	

	var car =getQueryParam();
	dwrOfficeResourcesService.listCarByDate(car,sdate,edate,pager,queryCallback);

}

function queryCallback(data){
	if(data.success == true){
		initGridData(data.resultList,data.pager);
	}else{
		alertmsg(data);
	}
	endQuery();
}

function repstatus(rowObj){
	var str="";
	if(rowObj.oaCarStatus == <%=EnumUtil.OA_CAR_STATUS.INUSE.value%>){
		str ="<font color='red'><%=EnumUtil.OA_CAR_STATUS.valueOf(EnumUtil.OA_CAR_STATUS.INUSE.value)%></font>";
	}else{
		str ="<font color='green'><%=EnumUtil.OA_CAR_STATUS.valueOf(EnumUtil.OA_CAR_STATUS.BOOKED.value)%></font>";
	}
	return str;
}

	function roomclick(myfrmname){
     
     	var win = Sys.getfrm();//获取index页面iframe window对象	
	    if(myfrmname!=null&&myfrmname != "undefined" && myfrmname != undefined){
	    	win = win.document.getElementById(myfrmname).contentWindow;	
	    }
     	var textid=win.document.getElementById("<%=textid%>"); 
     	var valueid = win.document.getElementById("<%=valueid%>");
     	//时间
     	var startdateobj = win.document.getElementById("applyBegindate");
     	var enddateobj = win.document.getElementById("applyEnddate");     	
		var sdate = document.getElementById("applyBegindate").value;
		var edate = document.getElementById("applyEnddate").value;		
		if(sdate.length==0||edate.length==0){
			alertmsg("请选择车辆开始和结束时间...",null,null,window);
			return;
		}
       if(getOneRecordArray() != false){
			var obj=getObjectByPk(getOneRecordArray());
			if(obj.oaCarStatus == <%=EnumUtil.OA_CAR_STATUS.INUSE.value%>){
				confirmmsg("确定要选择被占用的车辆吗?","clickok('"+myfrmname+"')",null,window);
			}else{
				textid.value = obj.oaCarName;
				valueid.value = obj.primaryKey;
				startdateobj.value = sdate;
				enddateobj.value = edate;
				Sys.close();
			}
	   }else{
			alertmsg("请选择相应数据记录...",null,null,window);
			return;
	   }
}
	function clickok(myfrmname){
	var win = Sys.getfrm();
		 if(myfrmname!=null&&myfrmname != "undefined" && myfrmname != undefined){
	    	win = win.document.getElementById(myfrmname).contentWindow;	
	    }
	var textid=win.document.getElementById("<%=textid%>"); 
    var valueid = win.document.getElementById("<%=valueid%>");
    var obj=getObjectByPk(getOneRecordArray());
    textid.value = obj.oaCarName;
	valueid.value = obj.primaryKey;
	win.document.getElementById("applyBegindate").value = document.getElementById("applyBegindate").value;
    win.document.getElementById("applyEnddate").value = document.getElementById("applyEnddate").value;
}

function roomclickcustomer(dialogId,myfrmname,method){
	roomclick(myfrmname);
	if(method!=null&&method != "undefined" && method != undefined){
		var win = Sys.getfrm();//获取index页面iframe window对象	
	    if(myfrmname!=null&&myfrmname != "undefined" && myfrmname != undefined){
	    	win = win.document.getElementById(myfrmname).contentWindow;	
	    }
		eval("win."+method);
	}
	Sys.close(dialogId);
}
function changestatr(obj){
	document.getElementById("applyEnddate").value = obj.value;
	queryData();
}

function modi(name,id){
//MoveDiv.show('维护车辆','<%=contextPath%>/erp/office_resources/car_mainten_add.jsp?pk='+id);
//Sys.load("<%=contextPath%>/erp/office_resources/car_use_add.jsp?pk="+id,"mainframe");
var begindate = document.getElementById("applyBegindate").value;
var enddate = document.getElementById("applyEnddate").value;
window.parent.parent.mainframe.location ="<%=contextPath%>/erp/office_resources/car_use_add.jsp?carname="+name+"&carid="+id+"&begindate="+begindate+"&enddate="+enddate;
}
	
</script>
	</head>
<body>
		<table width="100%" height="100%" cellspacing="0" cellpadding="0" border="0" >
		<tr height="30px">
		<td nowrap="nowrap" style="padding-right: 30px">
		<div class="formTitle" style="font-size: 13px;color:#1B579D">请先选择时间范围:</div>
		</td>
		<td  align="left">
		<input type="text" id="applyBegindate" readonly="readonly" class="Wdate" onchange="changestatr(this)" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:false})" value="<%=cudate %>">
		&nbsp;至&nbsp;
		<input type="text" id="applyEnddate" readonly="readonly" class="Wdate" onchange="queryData()" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:false,minDate:'#F{$dp.$D(\'applyBegindate\')}'})"  value="<%=cudate %>">
		</td>
		</tr>
		<tr style="padding-top: 1px">
		<td valign="top" style="HEIGHT: 100%" colspan="2">
			<%
				SysGrid grid = new SysGrid(request, "车辆列表");
				grid.setShowImg(false);//不显示图片信息.
				grid.setCheckboxOrNum(false);
				//放入列	
				ArrayList<SysColumnControl> sccList = new ArrayList<SysColumnControl>();
					    sccList.add(new SysColumnControl("oaCarCards","车牌号",1,1,1,20));//设置GRIV 列
			            sccList.add(new SysColumnControl("oaCarName","车辆名称",1,1,1,50));
				ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(sccList);
				colList.add(ColumnUtil.getCusterShowColumn("oaCarStatus","使用状态","repstatus",0,"text-align:center"));
				grid.setColumnList(colList);
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
