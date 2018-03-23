<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@ include file="common.jsp" %>
<%
String mid = request.getParameter("mid");	//跳转项目代码

SessionUser sessionUser = (SessionUser)LoginContext.getSessionValueByLogin(request);
List<SysMethodInfo> userModuleMethods = sessionUser.getUserModuleMethods();//个人功能模块
int size = userModuleMethods.size();

SysCompanyInfo company = sessionUser.getCompanyInfo();
String titname = "";
int clogin = -1;
if(company != null){
	clogin = company.getCompanyInfoLogin();
	titname = company.getCompanyInfoTitle();
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title><%=titname %></title>
		<link rel='stylesheet' type='text/css' href='<%=contextPath%>/js/tabs/tabs.css' />
		<script type="text/javascript" src="<%=contextPath%>/js/tabs/tabs.js"></script>
		<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrSysProcessService.js"></script>
		<script type="text/javascript">
		window.onload = function(){
			document.getElementById("methodleftfrm").src="<%=contextPath %>/erp/index_left.jsp?mid=<%=mid%>";
			autoOpen();
			showtime();
		}
		
		function autoOpen(){
			MDIOpen("<%=contextPath %>/erp/center.jsp");
			dwrSysProcessService.listSysMethodAutoOpen(function(data){
				for(var i=0;i<data.resultList.length;i++){
					var url = '<%=contextPath %>/erp/' + data.resultList[i].methodUri;
					autoOpenTmp(url,i);
				}
			});
		}
		function autoOpenTmp(url,i){
			setTimeout(function(){MDIOpen(url);},(i+1)*1500);
		}
		
		function showtime() {
		  var tm = getServerDate();
		  var year = tm[0];
		  var month = tm[1];
		  var date = tm[2];
		  var hours = tm[3];
		  var minutes =tm[4];
		  var seconds =tm[5];
		  var day = tm[6];
		  var tp = tm[7];
		  var timeValue = "";
		  timeValue += year + "年";
		  timeValue += month + "月";
		  timeValue += date + "日  ";
		  timeValue += day + "  ";
		  //timeValue += tp+" ";

		  timeValue += hours;
		  timeValue +=  ":" + minutes;
		  timeValue += ":" + seconds;
		  document.getElementById("systime").innerHTML = timeValue;
		  timerID = setTimeout("showtime()",1000);
		  timerRunning = true;
		}
		</script>
	</head>

	<body style="overflow:hidden;">
		
		<table width="100%" cellpadding="0" cellspacing="0" border="0" height="100%">
		<tr>
		<td height="55" style="background:url('<%=contextPath %>/images/top-5.png') repeat-x;">
		
			<!-- 首页顶部开始 -->
			<table width="100%" cellpadding="0" cellspacing="0" border="0" height="100%">
			<tr>
			<td style="padding-left:5px;padding-right:5px" valign="center">
				<a target="_blank" href="http://www.pinhuba.com/bams/index.htm" title="吉林电信人才管理系统">
				<%if(clogin>0){ %>
				<img border="0" alt="公司logo" src="<%=contextPath %>/showimg.do?imgId=<%=clogin %>&noImgPath=<%=Base64.getBase64FromString(this.getServletContext().getRealPath("/images/syslogin.png"))%>" style="height:35px;">
				<%}else{ %>
				<img border="0" alt="公司logo" src="<%=contextPath %>/images/syslogin.png" style="height: 35px;">
				<%} %>
				</a>
			</td>
			<td valign="top" width="100%" align="right">
				<%@include file="index_top.jsp"%> 
			</td>
			</tr>
			</table>
			<!-- 首页顶部结束 -->
		</td>
		</tr>
		<tr>
		<td>
		<!-- 首页中间开始 -->
		<table width="100%" cellpadding="0" cellspacing="0" border="0" height="100%">
			<tr>
				<td id="split_l" style="width:200px;">
					<div class="div_title">功能菜单</div>
					<div class="div_content_top">
						<iframe frameborder="0" allowTransparency="true" id="methodleftfrm" width="100%" height="100%" scrolling="no" marginheight="0"></iframe>
					</div>
					<div class="div_title">模块切换</div>
					<div class="div_content_bottom" style="height:<%=ConstWords.getProjectChangeHeight(size)%>px">
						<!-- 个人模块切换开始 -->
						<%
						if(userModuleMethods != null && userModuleMethods.size()>0){
						 %>
						<table cellspacing="0" cellpadding="5" align="center" border="0">
						<%
						String imgs="";
						String tit="";
						String style = "";
							int row =Integer.parseInt(SystemConfig.getParam("erp.show.projectchange"));
							for(int i=0;i<size;i++){
								SysMethodInfo sysMethod =userModuleMethods.get(i);
								int counttd=0;
								if(i%row==0){
								%>
								<tr>
								<%}%>
								<td valign="middle" align="center">
								<%
								String click = "onclick=\"projectOpen('"+sysMethod.getPrimaryKey()+"',"+i+",'"+sysMethod.getMethodInfoName()+"');\"";
								if(mid.equals(sysMethod.getPrimaryKey())){
									imgs = FileTool.getRepFileName(sysMethod.getImageSrc(),"_");
									tit = "当前模块:"+sysMethod.getMethodInfoName();
									style = "border: 1px solid #89B4D3;";
								}else{
									imgs = sysMethod.getImageSrc();
									tit = "切换到:"+sysMethod.getMethodInfoName();
									style = "border: 1px solid #cccccc;background-color: #fbfbfb;cursor: pointer;";
								}
								%>
								<img id="img<%=i%>" src="<%=contextPath+"/images/projectimg/"+imgs %>"  <%=click %>  title="<%=tit %>"  style="<%=style %>"/>
								<br/><%= StringTool.textCut(sysMethod.getMethodInfoName(), 4, "") %>
								</td>
								<%
								counttd++;
								if((i%row ==0&&counttd%row==0)||i==userModuleMethods.size()-1){
								%>
								</tr>
								<%}%>
							<%} %>
						</table>
						<%} %>
						<script type="text/javascript">
						function projectOpen(id,num,title){
							var count = <%=userModuleMethods.size()%>;
							for(var i=0;i<count;i++){
								var img = document.getElementById("img"+i);
								var imgSrc = img.src;
								if(i != num){//未选中
									if(imgSrc.substring(imgSrc.length-5,imgSrc.length-4) == "_"){
										img.src = imgSrc.substring(0,imgSrc.length-5) + imgSrc.substring(imgSrc.length-4,imgSrc.length);
										img.style.cssText = "border: 1px solid #cccccc;background-color: #fbfbfb;cursor: pointer;";
										var t = img.title.split(":");
										img.title = "切换到:"+t[1];
									}
								}else{//选中
									if(imgSrc.substring(imgSrc.length-5,imgSrc.length-4) != "_"){//不是重复点击
										img.src = imgSrc.substring(0,imgSrc.length-4) + "_" + imgSrc.substring(imgSrc.length-4,imgSrc.length);
										img.style.border = "1px solid #89B4D3";
										img.title = "当前模块:"+title;
										document.getElementById("methodleftfrm").src = "<%=contextPath %>/erp/index_left.jsp?mid="+id;
									}
								}
							}
						}
						</script>
						<!-- 个人模块切换结束 -->
					</div>
				</td>
				<td>
			        <iframe height="100%" width="100%" marginHeight="0" frameBorder="0"
			            name="mainframe" id="mainframe" marginWidth="0" scrolling="no"></iframe>
				</td>
			</tr>
		</table>
		<!-- 首页中间结束 -->
		</td>
		</tr>
		
		
		<tr>
		<td height="30" style="background:#054376;">
			<!-- 首页底部开始 -->	
			<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
				<tr>
					<td style="padding-left:10px;">
						<font color="#ffffff">
						(c)2018-2099 ChinaNet BAMS Version 2.0
						</font>
					</td>
					<td style="padding-left:15px;padding-right:15px;" valign="middle">
					
					</td>
										
					<td align="right">
	                	<span id="systime" style="color:#FFFFFF;vertical-align: middle;"></span>
	                </td>					
					
					<td align="right" style="padding-right:5px;" width="240" valign="middle">
						<%@include file="index_bottom.jsp"%> 
					</td>
				</tr>
			</table>					
			<!-- 首页底部结束 -->	
		</td>
		</tr>
		</table>
	<!-- 	
	<%@include file="desktop/serverTimer.jsp"%>
	 -->
	</body>
</html>