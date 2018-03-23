<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@ include file="../common.jsp"%>
<%@ include file="../editmsgbox.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath%>/dwr/interface/dwrPersonalOfficeService.js"></script>
<title>常用工具</title>
<%
WebApplicationContext webContext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
DwrPersonalOfficeService perService = (DwrPersonalOfficeService) webContext.getBean("dwrPersonalOfficeService");
Map<SysLibraryInfo, ArrayList<OaTools>> toolmap = perService.listOaTools(this.getServletContext(), request, new OaTools());
%>
<script type="text/javascript">
$(function(){
	
	$('.show_table_detail img').contextMenu('functiondiv', {
		bindings : {
			'edit' : function(t) {
				edit(t.name,t.id)
			},
			'del' : function(t) {
				deleteTool(t.id)
			},
			'refresh' : function(t) {
				reback()
			}
		},
		menuStyle : {
			width : '60px'
		}
	});
	
})

function go(url){
	openMDITab(url);
}

function tooladd(pk,type){
	MoveDiv.show(type,'<%=contextPath%>/erp/personal_work/tool_add.jsp?typePk='+pk);
}

function edit(typePk,toolid){
	MoveDiv.show("修改常用工具",'<%=contextPath%>/erp/personal_work/tool_add.jsp?typePk='+typePk+'&toolid='+toolid);
}

function deleteTool(toolid){
	confirmmsg("确定要删除工具吗?","delok("+toolid+")");
}	

function delok(toolid){
    var id;
	id = toolid;
    dwrPersonalOfficeService.deleteOatoolByPk(id,deleteCallback);
}
function deleteCallback(data){
	alertmsg(data,"reback()");
}
function reback(){
	Sys.href('<%=contextPath%>/erp/personal_work/commentools.jsp');
}

</script>
</head>
<body style="overflow: hidden;">
	<div style="display:none;" id='functiondiv'>
		<ul>
			<li id='edit'><img src="<%=contextPath%>/images/grid_images/edit.png"> 编辑</li>
			<li id='del'><img src="<%=contextPath%>/images/grid_images/close.png"> 删除</li>
			<li id='refresh'><img src="<%=contextPath%>/images/grid_images/refresh.png"> 刷新</li>
		</ul>
	</div>
	<div style="overflow: auto;height: 100%">
		<%
			if (toolmap != null && toolmap.size() > 0) {
				Set<SysLibraryInfo> keys = toolmap.keySet();
				Iterator<SysLibraryInfo> it = keys.iterator();
		%>
		<table width="100%" border="0" align="center" cellpadding="3" cellspacing="1">
			<%
				while (it.hasNext()) {
						SysLibraryInfo lib = it.next();
						ArrayList<OaTools> list = new ArrayList<OaTools>();
						if (toolmap.containsKey(lib)) {
							list = toolmap.get(lib);
						}
			%>
			<tr>
				<td height="24px" colspan="7" style="background-image: url('<%=contextPath %>/images/toolsbg.png')" align="left"><label style="padding-left: 10px;"><%=lib.getLibraryInfoName()%></label> <a href="javascript:void(0)" onclick="tooladd(<%=lib.getPrimaryKey()%>,'<%=lib.getLibraryInfoName()%>')" title="添加工具" style="padding-left: 5px"> <img src="<%=contextPath%>/images/grid_images/add.png" border="0" alt=添加 <%=lib.getLibraryInfoName()%> style="vertical-align: bottom;" />
				</a></td>
			</tr>
			<%
				int td = 0;
						if (list.size() > 0) {
							for (int i = 0; i < list.size(); i++) {
								OaTools tl = list.get(i);
								if (td % 5 == 0) {
			%>
			<tr>
				<%
					}
				%>

				<td width="20%" class="show_table_detail">
					<%
						if (tl.getOaToolImageId() != null && tl.getOaToolImageId() > 0) {
					%> <img id="<%=tl.getPrimaryKey()%>" name="<%=tl.getOaToolType()%>" src="<%=contextPath%>/showimg.do?type=img&imgId=<%=tl.getOaToolImageId()%>" width="64" height="64" style="cursor: pointer;" onmousedown="showmenu(,);" title="右击修改，删除,刷新" /> <br /><%=tl.getOaToolText()%> <%
						} else {
					%> <img id="<%=tl.getPrimaryKey()%>" name="<%=tl.getOaToolType()%>" src="<%=contextPath%>/images/commentoolsimage/<%=tl.getOaToolImage()%>" style="cursor: pointer;" onclick="go('<%=tl.getOaToolPath()%>')" width="64" height="64" title="右击修改，删除,刷新" /> <br /><%=tl.getOaToolText()%> <%
						}
					%>
				</td>
				<%
					td++;
									if (td > 0 && td % 5 == 0) {
				%>
			</tr>
			<%
				}
			%>
			<%
				}
						}
			%>
			<%
				}
			%>
		</table>
		<%
			}
		%>
	</div>
</body>
</html>