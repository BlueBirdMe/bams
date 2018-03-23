<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="<%=contextPath %>/js/weather.js"></script>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOADesktopService.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人设置</title>
<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader("Expires",0);
WebApplicationContext webctx = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
DwrOADesktopService deskService = (DwrOADesktopService)webctx.getBean("dwrOADesktopService");
List<OaDesktopSet> list = deskService.getOaDeskTopList(this.getServletContext(),request).getResultList();
String min = SystemConfig.getParam("erp.desktop.showMinRow");
String max = SystemConfig.getParam("erp.desktop.showMaxRow");
String isopen = SystemConfig.getParam("erp.center.weather");

boolean bl = "true".equalsIgnoreCase(isopen);
String[] cityids = null;
if(bl){
	OaDesktopSet depset = UtilTool.getDeskTopByType(this.getServletContext(),request,EnumUtil.OA_DESKTOP_TYPE.Weather.value);
	if(depset != null && depset.getOaDesktopValue() != null && depset.getOaDesktopValue().length()>0){
		cityids = depset.getOaDesktopValue().split(",");
	}
}


SessionUser usermsg = (SessionUser)LoginContext.getSessionValueByLogin(request);
SysConfig sysconfig =usermsg.getSysconfig();
if(sysconfig==null){
	out.print("不能加载配置系统信息..");
	return;
}
 %>
<script type="text/javascript">
window.onload = function(){
	useLoadingMassage();
	initInput("helpTitle","个人设置，你可以在此处设置桌面显示的相关信息。");
}

function savedeskset(){
	var vals = document.getElementsByName("val");
	for(var i=0;i<vals.length;i++){
		var vl = vals[i].value;
		if(!vals[i].disabled){
			var tdid = vals[i].getAttribute("fortd");
			var tdobj = document.getElementById(tdid);
			if(vl!=null&&trim(vl).length>0){
				if(!isNumber(vl)){
					if(tdobj!=null){
						tdobj.innerHTML = "<img src=\"<%=contextPath%>/images/grid_images/rowdel.png\">&nbsp;<font color='red'>请输入显示行数,且只能为数字<font>。";
					}
					vals[i].focus();
					return false;
				}else{
					tdobj.innerHTML = "<img src=\"<%=contextPath%>/images/grid_images/rowok.png\">";
				}
			}
			var tmpvl = parseInt(vl);
			if(tmpvl<<%=min%> || tmpvl><%=max%>){
				if(tdobj!=null){
					tdobj.innerHTML = "<img src=\"<%=contextPath%>/images/grid_images/rowdel.png\">&nbsp;<font color='red'>显示行数范围<%=min %>-<%=max %>之间</font>。";
				}
				vals[i].focus();
				return false;
			}else{
				tdobj.innerHTML = "<img src=\"<%=contextPath%>/images/grid_images/rowok.png\">";
			}
		}
	}
	Btn.close();
	var array = new Array();
	
	var chks = document.getElementsByName("chk");
	var pks = document.getElementsByName("primaryKey");
	var types = document.getElementsByName("deskType");
	
	for(var i=0;i<chks.length;i++){
		var obj = new Object();
		var type = types[i].value;
		var pk = pks[i].value;
		
		obj.primaryKey = pk;
		if(chks[i].checked){
			obj.oaDesktopIsshow = <%=EnumUtil.SYS_ISACTION.Vaild.value%>;
			obj.oaDesktopValue = getValueBytype(type,pk);
		}else{
			obj.oaDesktopIsshow = <%=EnumUtil.SYS_ISACTION.No_Vaild.value%>;
		}
		array.push(obj);
	}
	dwrOADesktopService.saveOaDeskTop(array,savecallback);
}

function savecallback(data){
	Btn.open();
	alertmsg(data,"repdesk()");
}

function getValueBytype(type,i){
	var val="";
	if(type == <%=EnumUtil.OA_DESKTOP_TYPE.Weather.value%>){
		val = document.getElementById("city").value+","+document.getElementById("area").value;
	}else{
		val = document.getElementById("rowval"+i).value;
	}
	return val;
}

function setobjdisByWeather(obj){
	var ct = document.getElementById("city");
	var ae = document.getElementById("area");
	if(obj.checked){
		ct.disabled = false;
		ae.disabled = false;
	}else{
		ct.disabled = true;
		ae.disabled = true;
	}
}

function setobjdis(obj,i){
	var rowobj = document.getElementById("rowval"+i);
	if(obj.checked){
		rowobj.disabled = false;
	}else{
		rowobj.disabled = true;
	}
}

var CitySwitcher = {
	setCityList:function(){
		var citysel= document.getElementById("city");
		for(var i=0;i<data.length;i++){
			var name = data[i][0];
			var val = data[i][1];
			var option = new Option(name,val);
			citysel.options.add(option);
		}
	},
	setAreaList:function(){
		var areasel = document.getElementById("area");
		var citysel= document.getElementById("city");
		if(areasel!=null){
			areasel.options.length=0;
		}
		var cval = citysel.value;
		if(cval==null||cval.length==0){
			cval = data[0][1];
		}
		var tmpcval = cval.substring(0,5);
		var c = 0;
		for(var i=0;i<dataarea.length;i++){
			var name = dataarea[i][0];
			var val = dataarea[i][1]+"";
			var tmp = val.substring(0,5);
			if(tmpcval == tmp){
				c++;
				var option = new Option(name,val);
				areasel.options.add(option);
			}
		}
		if(c==0){
			var index = citysel.selectedIndex;
			var option = new Option(citysel.options[index].text,citysel.value);
			areasel.options.add(option);
		}
	}
};
</script>
</head>
<body class="inputcls">
<%
if(list!=null&&list.size()>0){
 %>
<div class="formDetail">
		<div class="requdiv"><label id="helpTitle"></label></div>
		<div class="formTitle">个人设置</div>
<table class="inputtable" border="0">
<%
for(int i=0;i<list.size();i++){
 OaDesktopSet tmp = list.get(i);
 String chk="";
 String dis="";
 String sy = "";
 String cdk = "";
 if(tmp.getOaDesktopIsshow() == EnumUtil.SYS_ISACTION.Vaild.value){
 	chk ="checked ='checked'";
 }else{
 	cdk = " disabled='disabled'";
 }
 boolean must = EnumUtil.OA_DESKTOP_TYPE_ISMUST.valueOf(tmp.getOaDesktopType());
 
 if(must){
 	dis=" disabled='disabled' title='必须显示项'";
 	sy="style='color:#336699' title='必须显示项'";
 }
 
if(tmp.getOaDesktopType() == EnumUtil.OA_DESKTOP_TYPE.Weather.value){
 	if(bl){
 %>
<tr>
<td width="15%">
<input type="hidden" name="primaryKey" value="<%=tmp.getPrimaryKey() %>">
<input type="hidden" name="deskType" value="<%=tmp.getOaDesktopType() %>">
<input type="checkbox" <%=chk %> id="chk_<%=tmp.getOaDesktopType() %>" onclick="setobjdisByWeather(this);"  <%=dis %>  name="chk"><label for="chk_<%=tmp.getOaDesktopType() %>" <%=sy %>>显示<%=EnumUtil.OA_DESKTOP_TYPE.valueOf(tmp.getOaDesktopType()) %></label>
</td>
<td width="50%">
<div class="selectdiv"  style="float: left;margin-right: 5px;"><SELECT name=city id="city" <%=cdk %> onchange="CitySwitcher.setAreaList()"></SELECT></div>
<div class="selectdiv"  style="float: left"><SELECT name=area id="area" <%=cdk %>></SELECT></div>
</td>
<td width="35%" style="color: #808080">
选择默认显示天气城市
</td>
</tr>
<%
	}
}else{ %>
<tr>
<td width="15%">
<input type="hidden" name="primaryKey" value="<%=tmp.getPrimaryKey() %>">
<input type="hidden" name="deskType" value="<%=tmp.getOaDesktopType() %>">
<input type="checkbox" <%=chk %> onclick="setobjdis(this,<%=tmp.getPrimaryKey() %>);" id="chk_<%=tmp.getOaDesktopType() %>" <%=dis %>  name="chk"><label for="chk_<%=tmp.getOaDesktopType() %>" <%=sy %>>显示<%=EnumUtil.OA_DESKTOP_TYPE.valueOf(tmp.getOaDesktopType()) %></label>
</td>
<td  width="50%">
<input type="text" class="numform" <%=cdk %> id="rowval<%=tmp.getPrimaryKey() %>" value="<%=tmp.getOaDesktopValue()==null?min:tmp.getOaDesktopValue() %>" name="val" fortd ="msg<%=i %>">
</td>
<td  width="35%" style="color: #808080" id="msg<%=i %>">
显示行数范围<font color="red"><%=min %></font>-<font color="red"><%=max %></font>。
</td>
</tr>
<%} %>
<% }%>
</table>
</div>
<br/>
<table align="center" cellpadding="0" cellspacing="0">
<tr>
<td>
<btn:btn onclick="savedeskset();" imgsrc="../../images/png-1718.png" title="保存桌面设置" value="保 存 "></btn:btn>
</td>
<td style="width: 15px;"></td>
<td id="cancelbtn">
<btn:btn onclick="closeMDITab();" value="取 消 " imgsrc="../../images/winclose.png" title="取消桌面设置"></btn:btn>
</td>
</tr>
</table>
<%} %>
<script type="text/javascript">
<%
if(bl){
%>
CitySwitcher.setCityList();

//设置sel选中
var cityandarea = new Array();
<%
if(cityids!=null&&cityids.length>0){
%>
cityandarea[0] = "<%=cityids[0]%>";
cityandarea[1] = "<%=cityids[1]%>";
<%}%>
if(cityandarea.length>0){
	setSelectValue("city",cityandarea[0]);
}
CitySwitcher.setAreaList();
if(cityandarea.length>0){
	setSelectValue("area",cityandarea[1]);
}
<%}%>

function repdesk(){
	closeMDITab();
}
</script>
</body>
</html>