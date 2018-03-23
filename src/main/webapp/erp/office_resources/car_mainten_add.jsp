<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader("Expires",0);
String cid =request.getParameter("pk");
String mid=request.getParameter("pkey");
String url =request.getParameter("url");
 %>
<title>车辆维护</title>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOfficeResourcesService.js"></script>
<script type="text/javascript">
window.onload = function(){
	useLoadingMassage();
	dwrOfficeResourcesService.getCarByPk(<%=cid%>,setpagevalue);
	if(<%=mid%>!=null){
	dwrOfficeResourcesService.getCarmaintenByPk(<%=mid%>,setcarupet);
	}
	 initInput("helpTitle","车辆维护，您可以在此处登记车辆报废，日常保养，配件更换等信息。");
	 
}

function setcarupet(data){
   if(data!=null){
     if(data.resultList.length>0){
     	var tmp = data.resultList[0];
			DWRUtil.setValue("maintainMoney",tmp.maintainMoney);
			DWRUtil.setValue("maintainDate",tmp.maintainDate);
			DWRUtil.setValue("maintainAppendnews",tmp.maintainAppendnews);
			DWRUtil.setValue("maintainUser",tmp.applyEmployee.hrmEmployeeName);
			DWRUtil.setValue("maintainUserid",tmp.maintainUser);
		
     }
   }
}
function setpagevalue(data){
	if(data!=null){
		if(data.resultList.length>0){
			var tmp = data.resultList[0];
			DWRUtil.setValue("oaCarCards",tmp.oaCarCards);
			DWRUtil.setValue("oaCarName",tmp.oaCarName);
			DWRUtil.setValue("oaCarModel",tmp.oaCarModel);
			DWRUtil.setValue("oaCarEngine",tmp.oaCarEngine);
			DWRUtil.setValue("oaCarType",tmp.library.libraryInfoName);
			DWRUtil.setValue("oaCarMete",tmp.oaCarMete);
			DWRUtil.setValue("oaCarMax",tmp.oaCarMax);
			DWRUtil.setValue("oaCarMotoeman",tmp.oaCarMotoeman);
			if(tmp.oaCarPrice != null){
					DWRUtil.setValue("oaCarPrice","￥"+FormatNumber(tmp.oaCarPrice,2));
				}
			//DWRUtil.setValue("oaCarPrice",tmp.oaCarPrice);
			DWRUtil.setValue("oaCarStatus",tmp.oaCarStatus);
			DWRUtil.setValue("oaCarBuydate",tmp.oaCarBuydate);
		
			document.getElementById("oaCarRemark").innerHTML = tmp.oaCarRemark;
			  var face = document.getElementById("oaCarPhoto");
			  face.src+="&imgId="+tmp.oaCarPhoto;
		}
	}
}	
function saveOrEdit(){
	dwrOfficeResourcesService.getCarmaintenByPk(carmaintenprimaryKey,setCarmainteninfo);
}

function FCKeditor_OnComplete(editorInstance) {
	fck= editorInstance;
	editorInstance.SetHTML(fckvalue);//初始赋值
	window.status = editorInstance.Description;

}
function getUser(){
	var box = SEL.getEmployeeIds("radio","maintainUser","maintainUserid");
	box.show();
}

function save(){ 
           var bl = validvalue('helpTitle');
	     if(bl){
	       var textmst=document.getElementById("maintainAppendnews").value;
            if(textmst!=null&& textmst.length<1){
              document.getElementById("title").innerHTML="请输入维修原因";
            return；
          }
	       dwrOfficeResourcesService.saveCarmainten(getmaintain(),saveCallback);
		 }
}

function saveCallback(data){
    if (data.success == true) {
		alertmsg(data,"closePage()");
	}else{
	   alertmsg(data);
	}
}

function reload() {
	if('<%=url%>'=='null'){
		window.parent.MoveDiv.close();
  		window.parent.queryData();
	}else{
		Sys.load("<%=contextPath%>/erp/"+'<%=url%>');
	}
}
function getmaintain(){
	 var maintain= new Object();
	 if(<%=mid%>!=null){
	  maintain.primaryKey=<%=mid%>;
	 }
	 maintain.carId=<%=cid %>;
	maintain.maintainUser=DWRUtil.getValue("maintainUserid");
	maintain.maintainMoney=DWRUtil.getValue("maintainMoney");
	maintain.maintainDate=DWRUtil.getValue("maintainDate");
	maintain.maintainType=DWRUtil.getValue("maintainType");
	maintain.maintainAppendnews=DWRUtil.getValue("maintainAppendnews");
	return maintain;
}

function closePage(){
	closeMDITab(<%=request.getParameter("tab")%>);
}
</script>
</head>

  <body class="inputcls">
	<div class="formdetail">
	<div class="requdivdetail"><label>查看帮助:&nbsp;可通过点击附件来进行下载。</label></div>
    <div class="detailtitle">车辆明细</div>
	<table class="detailtable" align="center">
	<tr>
		<th width="10%">车牌号</th>
		<td width="15%" id="oaCarCards" class="detailtabletd"></td>
		<td width="10%"></td>
		<td rowspan="4" colspan="2">
				<file:imgshow  id="oaCarPhoto" width="128" ></file:imgshow><br/>			
		</td>
	</tr>
	<tr>
	<th>车辆名称</th>
	<td id="oaCarName" class="detailtabletd"></td>
	</tr>
	<tr>
	<th>厂牌型号</th>
	<td id="oaCarModel" class="detailtabletd"></td>
	</tr>
	<tr>
	<th>排量</th>
	<td id="oaCarMete" class="detailtabletd"></td>
	</tr>
	<tr>
	<th>车辆类型</th>
	<td id="oaCarType" class="detailtabletd" colspan="3"></td>
	
	</tr>
	<tr>
	<th>最大载重</th>
	<td id="oaCarMax" class="detailtabletd" colspan="3"></td>
	</tr>
	<tr>
	<th>购买价格</th>
	<td id="oaCarPrice" class="detailtabletd" colspan="3"></td>
	</tr>
	<tr>
	<th>购买日期</th>
	<td id="oaCarBuydate" class="detailtabletd" colspan="3"></td>
	</tr>
	<tr>
	</tr>
	<tr>
	<th>备  注</th>
	<td colspan="3" id="oaCarRemark" class="detailtabletd"></td>
	</tr>
	</table>
	<br/>
</div>
	<br/>


	<div class="formDetail">
			<div class="requdiv"><label id="helpTitle"></label></div>
			<div class="formTitle">车辆维护</div>
		<div>
			<table class="inputtable" border="0">
			<tr>
			<th width="15%"><em>* </em>操作人</th>
			<td width="30%">
			   <input type="text" class="takeform" linkclear="maintainUserid" readonly="readonly" id="maintainUser" formust="maintainUserMust" title="点击选择人员" onclick="getUser()"  must="操作人不能为空" style="color: #999;">
		       <input type="hidden" id="maintainUserid" value="">
		       <label id="maintainUserMust"></label>
			</td>	
			<th><em>* </em>费用</th>
			<td style="text-align: left;"><input type="text" class="rmbform" id="maintainMoney" must="请填写费用" formust="maintainMoneyMust">
			<label id="maintainMoneyMust"></label>
			</td>
			</tr>
			<tr>
			<th><em>* </em>时间</th>
			<td style="text-align: left">
			<input type="text" id="maintainDate" readonly="readonly" class="Wdate" onClick="WdatePicker()" must="请填写时间" value="<%=UtilWork.getToday() %>" formust="maintainDateMust">
			<label id="maintainDateMust"></label>
			</td>
			<th>类型</th>
			<td> 
			<select id="maintainType" >
			<%=UtilTool.getSelectOptions(this.getServletContext(),request,"999,车辆报废","16") %>
			</select>
			</td>
			</tr>
			<tr>
			<th><em>* </em>原因</th>
			<td style="text-align: left;" colspan="3">
			<textarea style="height:80px;width: 70%" id="maintainAppendnews" must="原因不能为空" formust="maintainAppendnewsMust"></textarea>
			<label id="maintainAppendnewsMust"></label></td>
			</tr>
			</table>
	</div>
	</div>

	<br/>
	<table align="center">
   	<tr>
     <td><btn:btn onclick="save();" value="保 存 " imgsrc="../../images/png-1718.png" title="保存车辆维护信息" /></td>
     <td style="width: 10px;"></td>
     <td><btn:btn onclick="closePage();" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"/></td>
   	</tr>
	</table>

</body>
</html>