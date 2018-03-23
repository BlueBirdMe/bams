<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.pinhuba.web.controller.dwr.DwrCommonService"%>
<%@ include file="common.jsp" %>
<%
String fileId = request.getParameter("fileId");
WebApplicationContext webcontext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
DwrCommonService service = (DwrCommonService)webcontext.getBean("dwrCommonService");
SysAttachmentInfo attach = service.getAttachmentInfoByPk(this.getServletContext(), request, Long.valueOf(fileId));
String fileName = attach.getAttachmentRename();
String filePath = Base64.getStringFromBase64(attach.getAttachmentFilename());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>编辑文档</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath%>/ntko/OfficeContorlFunctions.js"></script>
<script type="text/javascript">
window.onload = function(){
	var actionUrl = "<%=contextPath%>/erp/attach_office_update.jsp?filePath="+encodeURI('<%=filePath%>');
	document.getElementById("frmname").action = actionUrl;
	intializePage("<%=contextPath%>/download.do?fileId=<%=fileId%>");
	OFFICE_CONTROL_OBJ.Menubar=false;
}

</script>
</head>
<body onbeforeunload ="onPageClose()">
	<form id="frmname" enctype="multipart/form-data" style="padding:0px;margin:0px;">
	
	<input name="fileName" id="fileName" type="hidden" value="<%=fileName%>" />
	<input name="filePath" id="filePath" type="hidden" value="<%=filePath%>" />
	<table width="100%" height="100%">
		<tr>
			<td>
				<script type="text/javascript" src="<%=contextPath%>/ntko/ntkoofficecontrol.js"></script>
				<script language="JScript" for=TANGER_OCX event="OnDocumentClosed()">
					setFileOpenedOrClosed(false);
				</script>
				<script language="JScript" for="TANGER_OCX" event="OnDocumentOpened(TANGER_OCX_str,TANGER_OCX_obj)">
									
					OFFICE_CONTROL_OBJ.activeDocument.saved=true;//saved属性用来判断文档是否被修改过,文档打开的时候设置成ture,当文档被修改,自动被设置为false,该属性由office提供.
					//获取文档控件中打开的文档的文档类型
					switch (OFFICE_CONTROL_OBJ.doctype)
					{
						case 1:
							fileType = "Word.Document";
							fileTypeSimple = "wrod";
							break;
						case 2:
							fileType = "Excel.Sheet";
							fileTypeSimple="excel";
							break;
						case 3:
							fileType = "PowerPoint.Show";
							fileTypeSimple = "ppt";
							break;
						case 4:
							fileType = "Visio.Drawing";
							break;
						case 5:
							fileType = "MSProject.Project";
							break;
						case 6:
							fileType = "WPS Doc";
							fileTypeSimple="wps";
							break;
						case 7:
							fileType = "Kingsoft Sheet";
							fileTypeSimple="et";
							break;
						default :
							fileType = "unkownfiletype";
							fileTypeSimple="unkownfiletype";
					}
					setFileOpenedOrClosed(true);
				</script>
				<script language="JScript" for=TANGER_OCX event="BeforeOriginalMenuCommand(TANGER_OCX_str,TANGER_OCX_obj)">
					alert("BeforeOriginalMenuCommand事件被触发");
				</script>
				<script language="JScript" for=TANGER_OCX event="OnFileCommand(TANGER_OCX_str,TANGER_OCX_obj)">
					if (TANGER_OCX_str == 3) {
						alert("不能保存！请点击底部按钮保存！");
						CancelLastCommand = true;
					}
				</script>
			</td>
		</tr>
		<tr>
			<td height="40" align="right">
				<table align="center">
				   <tr>
				     <td><btn:btn onclick="saveFileToUrl();" value="保 存 " imgsrc="../images/png-1718.png" title="保存人员信息" ></btn:btn></td>
				     <td style="width: 20px;"></td>
				     <td><btn:btn onclick="javascript:window.close();" value="关 闭 " imgsrc="../images/winclose.png" title="关闭页面"/></td>
				   </tr>
				</table>
			</td>
		</tr>
	</table>
	</form>
</body>
</html>


