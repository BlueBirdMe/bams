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
String carpk =request.getParameter("carpk");
String url =request.getParameter("url");
String iedit= "false";
    if(carpk != null){
         iedit = "true";
    }
 %>
<title>增加车辆</title>
<style type="text/css">
	body {
	background-color: #EDF5FA;
}

</style>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOfficeResourcesService.js"></script>
<script type="text/javascript">
window.onload = function() {
	useLoadingMassage();
	initInput("helpTitle","新增车辆，您可以在此处登记公司新采购的车辆信息。");
	if ( <%=carpk %>!=null) {
		dwrOfficeResourcesService.getCarByPk(<%=carpk %>, setCarinfo);
	}
	document.getElementById("oaCarCards").focus();
}

	function save() {
		var bl = validvalue('helpTitle');
		if (bl) {
			if ( <%=carpk %>!=null) {
			dwrOfficeResourcesService.updateCar(getCarinfo(), updateCallback);
		} else {
			dwrOfficeResourcesService.saveCar(getCarinfo(), saveCallback);
		}
	}
}
function saveCallback(data) {
	if (data.success) {
		confirmmsgAndTitle("车辆添加成功！是否想继续添加？","reset();","继续添加","closePage();","关闭页面");
	}else{
		alertmsg(data);
	}
}

function canse(){
	 window.parent.MoveDiv.close();
	 window.parent.queryData();
}

function reset(){
  	DWRUtil.setValue("oaCarCards", "");
	DWRUtil.setValue("oaCarName", ""); //放入图片
	Sys.setFilevalue("oaCarPhoto","");
	DWRUtil.setValue("oaCarModel", "");
	setSelectValue("oaCarType", "");
	DWRUtil.setValue("oaCarMete", "");
	DWRUtil.setValue("oaCarMax", "");
	DWRUtil.setValue("oaCarPrice", "");
	DWRUtil.setValue("oaCarBuydate", "");
	DWRUtil.setValue("oaCarRemark", "");
	document.getElementById("oaCarCards").focus();
	refreshMDITab(<%=request.getParameter("tab")%>);
}
function updateCallback(data) {
	if (data.success == true) {
		alertmsg(data, "reloadpager()");
	} else {
		alert(data.message);
	}
}
function reloadpager() {
	if ( <%=carpk %>!=null) {
		window.parent.MoveDiv.close();
   		window.parent.queryData();
	}
}
function getCarinfo() {
	var car = new Object();
	if ( <%=carpk %>!=null) {
		car.primaryKey = <%=carpk %>;
	}
	car.oaCarCards = DWRUtil.getValue("oaCarCards");
	car.oaCarName = DWRUtil.getValue("oaCarName");
	car.oaCarPhoto = DWRUtil.getValue("oaCarPhoto");
	car.oaCarModel = DWRUtil.getValue("oaCarModel");
	car.oaCarType = DWRUtil.getValue("oaCarType");
	car.oaCarMete = DWRUtil.getValue("oaCarMete");
	car.oaCarMax = DWRUtil.getValue("oaCarMax");
	car.oaCarPrice = DWRUtil.getValue("oaCarPrice");
	car.oaCarBuydate = DWRUtil.getValue("oaCarBuydate");
	car.oaCarRemark = DWRUtil.getValue("oaCarRemark");
	return car;
}
var fckvalue = "";
function setCarinfo(data) {
	if (data.success == true) {
		if (data.resultList.length > 0) {
			var car = data.resultList[0];
			DWRUtil.setValue("oaCarCards", car.oaCarCards);
			DWRUtil.setValue("oaCarName", car.oaCarName); //放入图片
			if (car.oaCarPhoto != null && car.oaCarPhoto != undefined && car.oaCarPhoto > 0) {
				dwrCommonService.getImageInfoListToString(car.oaCarPhoto, setImage);
			}
			DWRUtil.setValue("oaCarModel", car.oaCarModel);
			setSelectValue("oaCarType", car.oaCarType);
			DWRUtil.setValue("oaCarMete", car.oaCarMete);
			DWRUtil.setValue("oaCarMax", car.oaCarMax);
			DWRUtil.setValue("oaCarPrice", car.oaCarPrice);
			DWRUtil.setValue("oaCarBuydate", car.oaCarBuydate);
			DWRUtil.setValue("oaCarRemark", car.oaCarRemark);
			fckvalue = car.oaCarRemark;
		} else {
			alert(data.message);
		}
	} else {
		alert(data.message);
	}
}
function setImage(data) {
	Sys.setFilevalue("oaCarPhoto", data);
}
var fck;
function FCKeditor_OnComplete(editorInstance) {
	fck = editorInstance;
	editorInstance.SetHTML(fckvalue); //初始赋值
	window.status = editorInstance.Description;
}
function reload() {
	if('<%=url%>'=='null'){
		
	   Sys.load("<%=contextPath%>/erp/office_resources/car_add.jsp");
	}else{
		Sys.load("<%=contextPath%>/erp/"+'<%=url%>');
	}
	
}

function closePage(){
	closeMDITab(<%=request.getParameter("tab")%>);
}	
</script>
</head>
<body class="inputcls">
	<div class="formDetail">
			<div class="requdiv"><label id="helpTitle"></label></div>
			<div class="formTitle">新增车辆</div>
		<div>
			<table class="inputtable" border="0">
					<tr>
				<th width="15%"><em>* </em>车牌号</th>
				<td width="30%"  style="text-align: left;"><input type="text" id="oaCarCards" must="车牌号不能为空" formust="oaCarCardsMust">
				<label id="oaCarCardsMust"></label>
				</td>
				<th rowspan="9" >车辆图片</th>
				<td rowspan="9">
				<file:imgupload width="320" acceptTextId="oaCarPhoto" height="200" edit="<%=iedit%>"></file:imgupload><br/>
				</td>
				</tr>
				<tr>
				<th><em>* </em>车辆名称</th>
				<td style="text-align: left;"><input type="text" id="oaCarName" must="车辆名称不能为空"  formust="oaCarNameMust">
				<label id="oaCarNameMust"></label>
				</td>
				</tr>
				<tr>
				<th><em></em>厂牌型号</th>
				<td style="text-align: left;"><input type="text" id="oaCarModel" ></td>
				</tr>
				<tr>
				<th>车辆类型</th>
				<td> 
				<select must="请选车辆类型" id="oaCarType" >
				<%=UtilTool.getSelectOptions(this.getServletContext(),request,null,"13") %>
				</select>
				</td>
				</tr>
				<tr>
				<th><em></em>排量</th>
				<td style="text-align: left;"><input type="text" id="oaCarMete"  class="numform"></td>
				<tr>
				<th><em></em>最大载重</th>
				<td style="text-align: left;"><input type="text" id="oaCarMax"  class="numform"></td>
				</tr>
				<tr>
				<th><em></em>购买价格</th>
				<td style="text-align: left;"><input type="text" class="rmbform" id="oaCarPrice" maxlength="7">&nbsp;元</td>
				</tr>
				<tr>
				<th><em></em>购买时间</th>
				<td style="text-align: left">
				<input type="text" id="oaCarBuydate" readonly="readonly" class="Wdate" onClick="WdatePicker({maxDate:'%y-%M-%d'})" value="<%=UtilWork.getToday() %>">
				</td>
				</tr>
				<tr>
				<th></th>
				<td style="text-align: left;"></td>
				</tr>
				
				<tr>
				<th>备注</th>
					<td style="text-align: left;" colspan="3">
				<textarea rows="6" cols="3"  id = "oaCarRemark"></textarea> </td>
				</tr>
			</table>
		</div>
	</div>
	
		<br/>
		<table align="center">
	   	<tr>
	     	<td><btn:btn onclick="save()" value="保 存 " imgsrc="../../images/png-1718.png" title="保存信息"></btn:btn></td>
	     	<td style="width: 10px;"></td>
			<td>
			<%if (carpk == null){ %>
			<btn:btn onclick="closePage()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn>
			<%}else{ %>
			<btn:btn onclick="window.parent.MoveDiv.close()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn>
			<%} %>
			</td>	   	
	</tr>
		</table>
</body>
</html>