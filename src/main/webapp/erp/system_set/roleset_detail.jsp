<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrSysProcessService.js"></script>
<title>创建角色</title>
<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader("Expires",0);
String roleId = request.getParameter("rid");
SessionUser sessionUser = (SessionUser)LoginContext.getSessionValueByLogin(request);
List<SysMethodInfo> companyMethods = sessionUser.getCompanyMethodsList();
List<SysRoleDetail> detailList=null;
Integer rid =null;
if(roleId!=null){
	rid = Integer.parseInt(roleId);
	detailList = UtilTool.getSysRoleDetailList(this.getServletContext(),request,rid);
}
 %>
</head>
<body class="inputdetail">
<div class="requdivdetail">
	<label>
		查看帮助:&nbsp;角色详细信息。
	</label>
</div>
	<div class="detailtitle">角色明细</div>
	<table class="detailtable" align="center">
	<tr>
	<th  width="15%">角色名称</th>
	<td id="rolename" class="detailtabletd"></td>
	</tr>
	<tr>
	<th>角色描述</th>
	<td id="roletext" class="detailtabletd"></td>
	</tr>
	<tr>
	<th valign="middle" style="vertical-align: middle">适用范围</th>
	<td valign="top" style="padding: 5px">
	<DIV class="tabdiv" style="width: 100%" id="tabdiv1">
		<UL class="tags">
		<LI><A onClick="tab.selectTag(this);" href="javascript:void(0)">用户</A></LI>
		<LI><A onClick="tab.selectTag(this);" href="javascript:void(0)">用户组</A></LI>
		<LI><A onClick="tab.selectTag(this)" href="javascript:void(0)">部门</A> </LI>
		<LI><A onClick="tab.selectTag(this)" href="javascript:void(0)">岗位</A> </LI>
		</UL>
	
		<DIV class="tagContentdiv">
			<DIV class="tagContent" id="tag0">
			<div style="border: 1px solid #A6D0E8;width: 100%overflow: auto;height: 100px;" >
			<input type="hidden" id="employeename">
			<input type="hidden" id="userid">
			<table cellpadding='2' cellspacing='2' border='0' align='center' width ='99%' id="userlist"/>
			  <tr style="BACKGROUND-IMAGE: url('<%=contextPath %>/images/grid_images/wbg.gif');" height="24px">
				<td align='left' style='padding-left:10px;font-weight: bold;' colspan="5">用户列表</td>
				</tr>
			</table>
			</div>
			</DIV>
			<DIV class="tagContent" id="tag1">
			<div style="border: 1px solid #A6D0E8;width: 100%overflow: auto;height: 100px;" >
			<input type="hidden" id="groupname">
			<input type="hidden" id="groupid">
			<table cellpadding='2' cellspacing='2' border='0' align='center' width ='99%' id="usergrouplist"/>
			  <tr style="BACKGROUND-IMAGE: url('<%=contextPath %>/images/grid_images/wbg.gif');" height="24px">
				<td align='left' style='padding-left:10px;font-weight: bold;'  colspan="5">用户组列表</td>
				</tr>
			</table>
			</div>
			</DIV>
			<DIV class="tagContent" id="tag2">
			<div style="border: 1px solid #A6D0E8;width: 100%overflow: auto;height: 100px;" >
			<input type="hidden" id="deptname">
			<input type="hidden" id="deptid">
			<table cellpadding='2' cellspacing='2' border='0' align='center' width ='99%' id="deptlist"/>
			  <tr style="BACKGROUND-IMAGE: url('<%=contextPath %>/images/grid_images/wbg.gif');" height="24px">
				<td align='left' style='padding-left:10px;font-weight: bold;'  colspan="5">部门列表</td>
				</tr>
			</table>
			</div>
			</DIV>
			<DIV class="tagContent" id="tag3">
			<div style="border: 1px solid #A6D0E8;width: 100%overflow: auto;height: 100px;" >
			<input type="hidden" id="postname">
			<input type="hidden" id="postid">
			<table cellpadding='2' cellspacing='2' border='0' align='center' width ='99%' id="postlist"/>
			  <tr style="BACKGROUND-IMAGE: url('<%=contextPath %>/images/grid_images/wbg.gif');" height="24px">
				<td align='left' style='padding-left:10px;font-weight: bold;'  colspan="5">岗位列表</td>
				</tr>
			</table>
			</div>
			</DIV>
		</DIV>
	</DIV>
	</td>
	</tr>
	</table>
	</br>
		<center><div class="linediv"></div></center>
	</br>
	<div class="detailtitle">权限列表</div>
	<div style="text-align: center;">
	<%
	if(companyMethods!=null&&companyMethods.size()>0){
	 %>
	<DIV class="tabdiv" style="width: 98%;margin: 5px;" id="tabdiv2" >
		<UL class="tags">
		<%
	for(int i=0;i<companyMethods.size();i++){
		SysMethodInfo sysMethod =companyMethods.get(i);
		String tmpck="";
		if(UtilTool.sysMethodIsCheck(sysMethod.getPrimaryKey(),detailList)){
			tmpck="checked ='checked'";
		}
	 %>
		<LI><A onClick="tab2.selectTag(this);" href="javascript:void(0)" style="padding-top: 4px;">
		<input type="checkbox" <%=tmpck %> id="<%=sysMethod.getMethodSign() %>" name="ck_<%=sysMethod.getMethodSign()%>" value="<%=sysMethod.getPrimaryKey() %>" disabled="disabled"><%=sysMethod.getMethodInfoName() %>
		</A></LI>
		<%} %>
		</UL>
		<DIV class="tagContentdiv">
			<%
			for(int i=0;i<companyMethods.size();i++){
			SysMethodInfo sysMethod =companyMethods.get(i);
			 %>
			<DIV class="tagContent" id="tag<%=i %>" style="overflow: hidden;">
			<%
			List<SysMethodInfo> levelist1 = UtilTool.getSysMethodMap(this.getServletContext(),request,sysMethod.getPrimaryKey(),detailList,EnumUtil.SYS_METHOD_LEVEL.ONE.value);
			List<SysMethodInfo> levelist2 = UtilTool.getSysMethodMap(this.getServletContext(),request,sysMethod.getPrimaryKey(),detailList,EnumUtil.SYS_METHOD_LEVEL.TWO.value);
			List<SysMethodInfo> levelist3 = UtilTool.getSysMethodMap(this.getServletContext(),request,sysMethod.getPrimaryKey(),detailList,EnumUtil.SYS_METHOD_LEVEL.THREE.value);
			 if(levelist1!=null&&levelist1.size()>0){
			 %>
			<table width="99%" cellpadding="4" cellspacing="0" border="0"  style="line-height: 20px;" >
			<%
			for(int j=0;j<levelist1.size();j++){
			SysMethodInfo levlMethod =levelist1.get(j);
			String chk1 ="";
			if(levlMethod.isIschecked()){chk1="checked ='checked'";}
			%>
			<tr>
				<td style="width: 12%;border-bottom: 1px solid #A6D0E8;" nowrap="nowrap">
				<input type="checkbox"  <%=chk1 %> value="<%=levlMethod.getPrimaryKey() %>" upvalue ="<%=levlMethod.getLevelUnit() %>" name="ck_<%=sysMethod.getMethodSign()%>"  disabled="disabled" id="chk_<%=levlMethod.getPrimaryKey() %>"><label for="chk_<%=levlMethod.getPrimaryKey() %>"><%=levlMethod.getMethodInfoName() %></label>
				</td>
				<td style="border-bottom: 1px solid #A6D0E8;">
				<table width="100%" cellpadding="4" cellspacing="1" border="0"  style="line-height: 20px;" >
				 <%for(int a=0;a<levelist2.size();a++){ 
				 	SysMethodInfo levlMethod2 =levelist2.get(a);
					if(levlMethod2.getLevelUnit().trim().equals(levlMethod.getPrimaryKey())){
						String chk2 ="";
						if(levlMethod2.isIschecked()){chk2="checked ='checked'";}
				 	%>
				 	<tr>
				 	<td style="width: 12%" nowrap="nowrap">
						<input type="checkbox" <%=chk2 %> value="<%=levlMethod2.getPrimaryKey() %>" upvalue ="<%=levlMethod2.getLevelUnit() %>" name="ck_<%=sysMethod.getMethodSign() %>"  disabled="disabled" id="chk_<%=levlMethod2.getPrimaryKey() %>"><label for="chk_<%=levlMethod2.getPrimaryKey() %>"><%=levlMethod2.getMethodInfoName() %></label>
					</td>
					<td>
					<table cellpadding="3" cellspacing="1" border="0"  style="line-height: 20px;" >
					<%
					int td = 0;
					for(int b=0;b<levelist3.size();b++){ 
				 	SysMethodInfo levlMethod3 =levelist3.get(b);
					if(levlMethod3.getLevelUnit().trim().equals(levlMethod2.getPrimaryKey())){
						String chk3="";
						if(levlMethod3.isIschecked()){chk3="checked ='checked'";}
				 	%>
				 	<%
				 	if(td%5==0){
				 	 %>
				 	<tr>
				 	<%} %>
				 	<td nowrap="nowrap" >
						<input type="checkbox" <%=chk3 %>  value="<%=levlMethod3.getPrimaryKey() %>" upvalue ="<%=levlMethod3.getLevelUnit() %>" name="ck_<%=sysMethod.getMethodSign() %>"  disabled="disabled" id="chk_<%=levlMethod3.getPrimaryKey() %>"><label for="chk_<%=levlMethod3.getPrimaryKey() %>"><%=levlMethod3.getMethodInfoName() %></label>
					</td>
					<%
					td++;
					if (td > 0 && td % 5 == 0) {
					 %>
					 </tr>
					 <% } %>
				 	<%}
				 	} %>
				 	<!-- </tr> -->
				 	</table>
				 	</td>
				 <%}
				 } %>
				 </table>
				  </td>
			</tr>
			<%} %>
			</table>
			<%} %>
			</DIV>
			<%} %>
		</DIV>
	</DIV>
	<%} %>
	</div>
<script type="text/javascript">
var tab =new SysTab('<%=contextPath%>',null,"tabdiv1");
var tab2 = new SysTab('<%=contextPath%>',null,"tabdiv2");
var cellscount =5;

window.onload=function(){
	useLoadingMassage();
	if(<%=rid%>!=null){
		dwrSysProcessService.getSysRoleById(<%=rid%>,setpagevalue);
		dwrSysProcessService.getSysRoleBindByRoleId(<%=rid%>,setlistvalues);
	}
}
function setpagevalue(data){
	if(data!=null&&data.resultList.length > 0){
		DWRUtil.setValue("rolename",data.resultList[0].roleName);
		DWRUtil.setValue("roletext",data.resultList[0].roleDesc);
	}
}

function setlistvalues(data){
	if(data.success==true){
		var userids ="";
		var groupids ="";
		var deptids ="";
		var postids ="";
		if(data.resultList.length > 0){
			for(var i=0;i<data.resultList.length;i++){
				var type = data.resultList[i].bindType;
				var bv = data.resultList[i].bindValue;
				if(type==<%=EnumUtil.SYS_ROLE_BIND_TYPE.BIND_USER.value%>){
					userids+=bv+",";
				}
				if(type==<%=EnumUtil.SYS_ROLE_BIND_TYPE.BIND_GROUP.value%>){
					groupids+=bv+",";
				}
				if(type==<%=EnumUtil.SYS_ROLE_BIND_TYPE.BIND_DEPT.value%>){
					deptids+=bv+",";
				}
				if(type==<%=EnumUtil.SYS_ROLE_BIND_TYPE.BIND_POST.value%>){
					postids+=bv+",";
				}
			}
		}
		setinputnames(userids,groupids,deptids,postids);
	}else{
		alertmsg(data);
	}
}

function showidandnames(data,ids,listid,nameid){
	if(data.length > 0){
		var str="";
		for(var i=0;i<data.length;i++){
			str+=data[i]+",";
		}
		document.getElementById(nameid).value = str;
		createrow(ids,str,listid);
	}
}


function setinputnames(userids,groupids,deptids,postids){
	if(userids.length>0){
		document.getElementById("userid").value = userids;
		dwrCommonService.getHrmEmployeeNamesByUserIds(userids,function(data){
			showidandnames(data,userids,"userlist","employeename");
		});
	}
	if(groupids.length>0){
		document.getElementById("groupid").value = groupids;
		dwrCommonService.getGroupNamesByIds(groupids,function(data){
			showidandnames(data,groupids,"usergrouplist","groupname");
		});
	}
	if(deptids.length>0){
		document.getElementById("deptid").value = deptids;
		dwrCommonService.getHrmDeptNamesByIds(deptids,function(data){
			showidandnames(data,deptids,"deptlist","deptname");
		});
	}
	if(postids.length>0){
		document.getElementById("postid").value = postids;
		dwrCommonService.getHrmPostNamesByIds(postids,function(data){
			showidandnames(data,postids,"postlist","postname");
		});
	}
}
//===============将内容转换为checkbox================
function deletebatch(id,name,listobj){
	var cks = document.getElementsByName("ck_"+listobj);
	var ids ="";
	var names ="";
	for(var i=0;i<cks.length;i++){
		if(!cks[i].checked){
			ids += cks[i].value+",";
			names +=  cks[i].getAttribute("text")+",";
		}
	}
	document.getElementById(id).value = ids;
	document.getElementById(name).value = names;
	createrow(ids,names,listobj);
}

function createrow(ids,names,listid){
	var tab = document.getElementById(listid);
	var rlen = tab.rows.length;
	for(var i=rlen-1;i>=1;i--){
		tab.deleteRow(i);
	}
	var idlist = ids.split(",");
	var namelist = names.split(",");
	for ( var i = 0; i < idlist.length; i++) {
		if(idlist[i].length>0){
			if(i%cellscount==0 ||i == idlist.length-1){
				var otr = tab.insertRow(-1);
			}
			otr.id="tr"+idlist[i];
	        var td=document.createElement("td");
	        td.style.cssText="border-bottom:0px solid #ccc;white-space: nowrap;width:20%";
	        td.innerHTML = "<input type='checkbox' checked='checked' text='"+namelist[i]+"' name='ck_"+listid+"' value='"+idlist[i]+"' id='"+listid+"_"+idlist[i]+"'  disabled='disabled'><label for='"+listid+"_"+idlist[i]+"'>"+namelist[i]+"<label>";
	        otr.appendChild(td);
        }
    }
}

</script>
</body>
</html>