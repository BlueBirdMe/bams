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
List<SysRoleBtn> btnList=null;
Integer rid =null;
if(roleId!=null){
	rid = Integer.parseInt(roleId);
	detailList = UtilTool.getSysRoleDetailList(this.getServletContext(),request,rid);
	btnList = UtilTool.getSysRoleBtnList(this.getServletContext(),request,rid);
}
 %>
</head>
<body  class="inputcls">
	<div class="formDetail">
	<div class="requdiv">
		<label id="helpTitle"></label>
	</div>
	<div class="formTitle">
		角色信息
	</div>
	<div>
	<table class="inputtable">
	<tr>
		<th><em>* </em>角色名称</th>
		<td>
		<input type="text" id="rolename" maxlength="20" must="请输入角色名称" size="40" formust="rolenamemust">
		</td>
		<td align="left">
		<label id="rolenamemust"></label>
		</td>
	</tr>
	<tr>
		<th></th>
		<td>
		<label id="roletextmust"></label>
		 </td>
	</tr>
	<tr>
		<th>角色描述</th>
		<td colspan="2">
		<textarea id="roletext" style="height: 50px;">
		</textarea>
		</td>
	</tr>
	<tr>
		<th></th>
		<td id="rangmust" colspan="2"></td>
	</tr>
	<tr>
		<th><em>* </em>适用范围</th>
		<td valign="top" colspan="2">
		<DIV class="tabdiv" style="width: 90%" id="tabdiv1">
		<UL class="tags">
		<LI><A onClick="tab.selectTag(this);" href="javascript:void(0)">用户</A></LI>
		<LI><A onClick="tab.selectTag(this);" href="javascript:void(0)">用户组</A></LI>
		<LI><A onClick="tab.selectTag(this)" href="javascript:void(0)">部门</A> </LI>
		<LI><A onClick="tab.selectTag(this)" href="javascript:void(0)">岗位</A> </LI>
		</UL>
	
		<DIV class="tagContentdiv">
			<DIV class="tagContent" id="tag0">
			<div style="border: 1px solid #D4D4D4;" >
			<input type="hidden" id="employeename">
			<input type="hidden" id="userid">
			<table cellpadding='0' cellspacing='0' border='0' align='center' width ='100%'/>
			  <tr style="BACKGROUND-IMAGE: url('<%=contextPath %>/images/grid_images/wbg.gif');" height="24px">
				<td align='left' style='padding-left:10px;font-weight: bold;'>用户列表</td>
				<td style="text-align: right;" nowrap="nowrap" align="right">
				<div class='div_btn' onmouseover ='this.className ="div_btn_hover"' onmouseout ='this.className ="div_btn"'   onclick="deletebatch('userid','employeename','userlist');">&nbsp;删除选中用户&nbsp;</div>
				<div class='div_btn' onmouseover ='this.className ="div_btn_hover"' onmouseout ='this.className ="div_btn"'   onclick="getupcode()">&nbsp;选择用户&nbsp;</div>
				</td>
				</tr>
				<tr>
				<td colspan="2" valign="top">
				<div style="overflow: auto;height: 80px;width: 100%;vertical-align: top;">
					<table cellpadding='0' cellspacing='0' border='0' align='center' width ='100%' id="userlist"/>
					</table>
				</div>
				</td>
				</tr>
			</table>
			</div>
			</DIV>
			<DIV class="tagContent" id="tag1">
			<div style="border: 1px solid #D4D4D4;" >
			<input type="hidden" id="groupname">
			<input type="hidden" id="groupid">
			<table cellpadding='0' cellspacing='0' border='0' align='center' width ='100%'/>
			  <tr style="BACKGROUND-IMAGE: url('<%=contextPath %>/images/grid_images/wbg.gif');" height="24px">
				<td align='left' style='padding-left:10px;font-weight: bold;'>用户组列表</td>
				<td style="text-align: right;" nowrap="nowrap" align="right">
				<div class='div_btn' onmouseover ='this.className ="div_btn_hover"' onmouseout ='this.className ="div_btn"'   onclick="deletebatch('groupid','groupname','usergrouplist');">&nbsp;删除选中组&nbsp;</div>
				<div class='div_btn' onmouseover ='this.className ="div_btn_hover"' onmouseout ='this.className ="div_btn"'   onclick="getgroup()">&nbsp;选择组&nbsp;</div>
				</td>
				</tr>
				<tr>
				<td colspan="2" valign="top">
				<div style="overflow: auto;height: 80px;width: 100%;vertical-align: top;">
					<table cellpadding='0' cellspacing='0' border='0' align='center' width ='100%' id="usergrouplist"/>
					</table>
				</div>
				</td>
				</tr>
			</table>
			</div>
			</DIV>
			<DIV class="tagContent" id="tag2">
			<div style="border: 1px solid #D4D4D4;" >
			<input type="hidden" id="deptname">
			<input type="hidden" id="deptid">
			<table cellpadding='0' cellspacing='0' border='0' align='center' width ='100%'/>
			  <tr style="BACKGROUND-IMAGE: url('<%=contextPath %>/images/grid_images/wbg.gif');" height="24px">
				<td align='left' style='padding-left:10px;font-weight: bold;'>部门列表</td>
				<td style="text-align: right;" nowrap="nowrap" align="right">
				<div class='div_btn' onmouseover ='this.className ="div_btn_hover"' onmouseout ='this.className ="div_btn"'   onclick="deletebatch('deptid','deptname','deptlist');">&nbsp;删除选中部门&nbsp;</div>
				<div class='div_btn' onmouseover ='this.className ="div_btn_hover"' onmouseout ='this.className ="div_btn"'   onclick="getdept()">&nbsp;选择部门&nbsp;</div>
				</td>
				</tr>
				<tr>
				<td colspan="2" valign="top">
				<div style="overflow: auto;height: 80px;width: 100%;vertical-align: top;">
					<table cellpadding='0' cellspacing='0' border='0' align='center' width ='100%' id="deptlist"/>
					</table>
				</div>
				</td>
				</tr>
			</table>
			</div>
			</DIV>
			<DIV class="tagContent" id="tag3">
			<div style="border: 1px solid #D4D4D4;" >
			<input type="hidden" id="postname">
			<input type="hidden" id="postid">
			<table cellpadding='0' cellspacing='0' border='0' align='center' width ='100%'/>
			  <tr style="BACKGROUND-IMAGE: url('<%=contextPath %>/images/grid_images/wbg.gif');" height="24px">
				<td align='left' style='padding-left:10px;font-weight: bold;'>岗位列表</td>
				<td style="text-align: right;" nowrap="nowrap" align="right">
				<div class='div_btn' onmouseover ='this.className ="div_btn_hover"' onmouseout ='this.className ="div_btn"'   onclick="deletebatch('postid','postname','postlist')">&nbsp;删除选中岗位&nbsp;</div>
				<div class='div_btn' onmouseover ='this.className ="div_btn_hover"' onmouseout ='this.className ="div_btn"'   onclick="getpost()">&nbsp;选择岗位&nbsp;</div>
				</td>
				</tr>
				<tr>
				<td colspan="2" valign="top">
				<div style="overflow: auto;height: 80px;width: 100%;vertical-align: top;">
					<table cellpadding='0' cellspacing='0' border='0' align='center' width ='100%' id="postlist"/>
					</table>
				</div>
				</td>
				</tr>
			</table>
			</div>
			</DIV>
		</DIV>
	</DIV>
	</td>
	</tr>
	</table>
	</div>
	</br>
		<center><div class="linediv"></div></center>
	</br>
	<div class="formTitle">
		权限列表
	</div>
	
	<label id="methodmust" style="width: 100%;text-align: left;padding-left: 15px"></label>
	<div style="text-align: center;">
	<%
	if(companyMethods!=null&&companyMethods.size()>0){
	 %>
	<DIV class="tabdiv" style="width:100%;margin:0 auto;overflow: hidden;" id="tabdiv2" >
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
		<input type="checkbox" <%=tmpck %> id="<%=sysMethod.getMethodSign() %>" name="ck_<%=sysMethod.getMethodSign()%>" value="<%=sysMethod.getPrimaryKey() %>" onclick="checkboxchange(this,'<%=sysMethod.getPrimaryKey() %>','<%=sysMethod.getMethodSign()  %>','<%=sysMethod.getLevelUnit()%>')" title="全选/取消"><%=sysMethod.getMethodInfoName() %>
		</A></LI>
		<%} %>
		</UL>
		<DIV class="tagContentdiv" style="overflow: hidden;width: 99%">
			<%
			for(int i=0;i<companyMethods.size();i++){
			SysMethodInfo sysMethod =companyMethods.get(i);
			 %>
			<DIV class="tagContent" id="tag<%=i %>" style="overflow: auto;">
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
				<td style="width: 12%;border-bottom: 1px dotted #d4d4d4;;" nowrap="nowrap">
				
				<table width="100%" cellpadding="4" cellspacing="1" border="0">
				<tr>
				<td nowrap="nowrap">
				
					<input type="checkbox"  <%=chk1 %> 
						value="<%=levlMethod.getPrimaryKey() %>" 
						upvalue ="<%=levlMethod.getLevelUnit() %>" 
						name="ck_<%=sysMethod.getMethodSign()%>" 
						onclick="checkboxchange(this,'<%=levlMethod.getPrimaryKey() %>','<%=sysMethod.getMethodSign()  %>','<%=levlMethod.getLevelUnit()%>')" 
						id="chk_<%=levlMethod.getPrimaryKey() %>">
					<label title="<%=levlMethod.getMethodMsgStr()%>" for="chk_<%=levlMethod.getPrimaryKey() %>"><%=levlMethod.getMethodInfoName() %></label>
				</td>
				<%if(levlMethod.getBtns().size() > 0) {%>
				<td nowrap="nowrap" style="background:#fbf1a4;" title="功能按钮">
					<!-- 功能按钮 -->
					<%for (int z = 0; z < levlMethod.getBtns().size(); z++) { 
						SysMethodBtn btn = levlMethod.getBtns().get(z);
						
						UtilTool.setBtnChecked(btn,btnList);
						
						String btnchk1 = "";
						if (btn.isChecked()) {
							btnchk1 = "checked ='checked'";
						}
					%>
						<input type="checkbox" <%=btnchk1%> id="btn_<%=btn.getPrimaryKey()%>"
						upvalue="<%=levlMethod.getPrimaryKey()%>" name="btn" value="<%=btn.getPrimaryKey()%>"/>
						<label for="btn_<%=btn.getPrimaryKey()%>"><%=btn.getBtnName()%></label><br/>
					<%} %>
				</td>
				<%} %>
				</tr>
				</table>
				
				</td>
				<td style="border-bottom: 1px dotted  #d4d4d4;">
				<table width="100%" cellpadding="4" cellspacing="1" border="0"  style="line-height: 20px;" >
				 <%for(int a=0;a<levelist2.size();a++){ 
				 	SysMethodInfo levlMethod2 =levelist2.get(a);
					if(levlMethod2.getLevelUnit().trim().equals(levlMethod.getPrimaryKey())){
						String chk2 ="";
						if(levlMethod2.isIschecked()){chk2="checked ='checked'";}
				 	%>
				 	<tr>
				 	<td style="width: 12%" nowrap="nowrap">
				 	
				 		<table width="100%" cellpadding="4" cellspacing="1" border="0">
						<tr>
						<td nowrap="nowrap">
							<input type="checkbox" <%=chk2 %> 
							value="<%=levlMethod2.getPrimaryKey() %>" 
							upvalue ="<%=levlMethod2.getLevelUnit() %>" 
							name="ck_<%=sysMethod.getMethodSign() %>" 
							onclick="checkboxchange(this,'<%=levlMethod2.getPrimaryKey() %>','<%=sysMethod.getMethodSign()  %>','<%=levlMethod2.getLevelUnit()%>')" 
							id="chk_<%=levlMethod2.getPrimaryKey() %>">
							
						<label title="<%=levlMethod2.getMethodMsgStr()%>" for="chk_<%=levlMethod2.getPrimaryKey() %>"><%=levlMethod2.getMethodInfoName() %></label>
						</td>
						<%if(levlMethod2.getBtns().size() > 0) {%>
						<td nowrap="nowrap" style="background:#fbf1a4;" title="功能按钮">
							<!-- 功能按钮 -->
							<%for (int z = 0; z < levlMethod2.getBtns().size(); z++) { 
								SysMethodBtn btn = levlMethod2.getBtns().get(z);
								
								UtilTool.setBtnChecked(btn,btnList);
								
								String btnchk2 = "";
								if (btn.isChecked()) {
									btnchk2 = "checked ='checked'";
								}
							%>
								<input type="checkbox" <%=btnchk2%> id="btn_<%=btn.getPrimaryKey()%>" 
								upvalue="<%=levlMethod2.getPrimaryKey()%>" name="btn" value="<%=btn.getPrimaryKey()%>"/>
								<label for="btn_<%=btn.getPrimaryKey()%>"><%=btn.getBtnName()%></label><br/>
							<%} %>
						</td>
						<%} %>
						</tr>
						</table>
					
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
				 	<td nowrap="nowrap" style="padding-left: 5px;padding-right: 5px">
				 		<table width="100%" cellpadding="4" cellspacing="1" border="0">
						<tr>
						<td nowrap="nowrap">
				 	
						<input type="checkbox" <%=chk3 %>  
							value="<%=levlMethod3.getPrimaryKey() %>" 
							upvalue ="<%=levlMethod3.getLevelUnit() %>" 
							name="ck_<%=sysMethod.getMethodSign() %>" 
							onclick="checkboxchange(this,'<%=levlMethod3.getPrimaryKey() %>','<%=sysMethod.getMethodSign()  %>','<%=levlMethod3.getLevelUnit()%>')" 
							id="chk_<%=levlMethod3.getPrimaryKey() %>">
						<label title="<%=levlMethod3.getMethodMsgStr()%>" for="chk_<%=levlMethod3.getPrimaryKey() %>"><%=levlMethod3.getMethodInfoName() %></label>
					
						</td>
						<%if(levlMethod3.getBtns().size() > 0) {%>
						<td nowrap="nowrap" style="background:#fbf1a4;" title="功能按钮">
							<!-- 功能按钮 -->
							<%for (int z = 0; z < levlMethod3.getBtns().size(); z++) { 
								SysMethodBtn btn = levlMethod3.getBtns().get(z);
								
								UtilTool.setBtnChecked(btn,btnList);
								
								String btnchk3 = "";
								if (btn.isChecked()) {
									btnchk3 = "checked ='checked'";
								}
							%>
								<input type="checkbox" <%=btnchk3%> id="btn_<%=btn.getPrimaryKey()%>" 
								upvalue="<%=levlMethod3.getPrimaryKey()%>" name="btn" value="<%=btn.getPrimaryKey()%>"/>
								<label for="btn_<%=btn.getPrimaryKey()%>"><%=btn.getBtnName()%></label><br/>
							<%} %>
						</td>
						<%} %>
						</tr>
						</table>
					</td>
					<%
					td++;
					if (td > 0 && td % 5 == 0) {
					 %>
					 </tr>
					 <% } %>
				 	<%}
				 	} %>
				 	</table>
				 	</td>
				 	</tr>
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
<br/>
</div>
<br/>
<table align="center">
<tr>
<td>
<btn:btn onclick="saverole();" value="保 存 " imgsrc="../../images/png-1718.png" title="保存角色信息" />
</td>
<td style="width: 15px;"></td>
<td id="backbtn"><btn:btn onclick="closePage();" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"/></td>
</tr>
</table>
<script type="text/javascript">
var tab =new SysTab('<%=contextPath%>',null,"tabdiv1");
var tab2 = new SysTab('<%=contextPath%>',null,"tabdiv2");
var cellscount =5;
//权限checkbox标识
var methodArray = new Array();
<%
for(int i=0;i<companyMethods.size();i++){
	SysMethodInfo sysMethod =companyMethods.get(i);
%>
	methodArray[<%=i%>] = '<%=sysMethod.getMethodSign()%>';
<%}%>



window.onload=function(){
	useLoadingMassage();
	initInput('helpTitle',"创建角色信息，可以批量设置相同类型的用户权限。");
	if(<%=rid%>!=null){
		dwrSysProcessService.getSysRoleById(<%=rid%>,setpagevalue);
		dwrSysProcessService.getSysRoleBindByRoleId(<%=rid%>,setlistvalues);
	}
	document.getElementById("rolename").focus();
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
//=======权限=============
function checkboxchange(obj,val,tname,upunit){
	var check = obj.checked;
	var cks = document.getElementsByName("ck_"+tname);
	//存放上级
	var uparray = new Array();
	var upc=0;
	if(cks.length>0){
		for(var i=0;i<cks.length;i++){
			var tmp = cks[i].value;
			
			btncheckboxchange(val, check);//设置功能按钮状态
			
			//选中下级
			if(tmp.length>val.length){
				tmp = tmp.substring(0,val.length);
				if(tmp == val){
					cks[i].checked = check;
					btncheckboxchange(cks[i].value, cks[i].checked);//设置功能按钮状态
				} 
			}
			//取出上级
			if(tmp.length<val.length){
				uparray[upc] = cks[i];
				upc++;
			}
		}
		if(check){
			if(uparray.length>0){
				var len = val.length;
				for(var j=0;j<uparray.length;j++){
					var upval = uparray[j].value;
					var valtmp = val.substring(0,upval.length);
					if(upval == valtmp){
						uparray[j].checked = check;
					}					 
				}
			}
		}else{
			upchekedbox(val,tname,upunit);
		}
	}
}

function btncheckboxchange(val, check){
	var btncks = document.getElementsByName("btn");
	for(var j=0;j<btncks.length;j++){
		var tmpup = btncks[j].getAttribute("upvalue");
		if(tmpup == val)
			btncks[j].checked = check;
	}
}

function upchekedbox(val,tname,upunit){
	var check = false;
	var cks = document.getElementsByName("ck_"+tname);
	//存放同级
	var tmparray = new Array();
	var tmpc =0;
	
	var tmpobj = null;
	if(cks.length>0){
		for(var i=0;i<cks.length;i++){
			var tmp = cks[i].value;
			var tmpup = cks[i].getAttribute("upvalue");
			//取出同级
			if(tmpup==upunit){
				tmparray[tmpc] = cks[i];
				tmpc++;
			}
			
			if(upunit == tmp){
				tmpobj = cks[i];
			}
		}
	}
	var bl = true;
	//根据统计目录计算上级状态
	if(tmparray.length>0){
		for(var j=0;j<tmparray.length;j++){
			if(tmparray[j].checked){
				bl = false;
				break;
			}
		}
	}else{
		bl = false;
	}
	if(bl){
		if(tmpobj!=null){
			tmpobj.checked = check;
			upchekedbox(tmpobj.value,tname,tmpobj.getAttribute("upvalue"));
		}
	}
}
//=========用户=========
function getupcode(){
	var box = SEL.getUserIds("check","employeename","userid","undefined","getusermsg()");
	box.show();
}
function getusermsg(){
	var userid =document.getElementById("userid").value;
	var empname =document.getElementById("employeename").value;
	createrow(userid,empname,"userlist");
}

//========用户组============
function getgroup(){
	var box = SEL.getUserGroup("check","groupname","groupid","undefined","getgroupmsg()");
	box.show();
}
function getgroupmsg(){
	var ids =document.getElementById("groupid").value;
	var names =document.getElementById("groupname").value;
	createrow(ids,names,"usergrouplist");
}
//===========部门===============
function getdept(){
	var box = SEL.getDeptIds("check","deptname","deptid","undefined","getdeptmsg()");
	box.show();
}
function getdeptmsg(){
	var ids =document.getElementById("deptid").value;
	var names =document.getElementById("deptname").value;
	createrow(ids,names,"deptlist");
}
//=============岗位================
function getpost(){
	var box = SEL.getPostIds("check","postname","postid","undefined","postmsg()");
	box.show();
}
function postmsg(){
	var ids =document.getElementById("postid").value;
	var names =document.getElementById("postname").value;
	createrow(ids,names,"postlist");
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
	for(var i=rlen-1;i>=0;i--){
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
	        td.innerHTML = "<input type='checkbox' text='"+namelist[i]+"' name='ck_"+listid+"' value='"+idlist[i]+"' id='"+listid+"_"+idlist[i]+"'><label for='"+listid+"_"+idlist[i]+"'>"+namelist[i]+"<label>";
	        otr.appendChild(td);
        }
    }
}
//==============保存================

function saverole(){
	var tit = document.getElementById("rangmust");
	var mm = document.getElementById("methodmust");
	var warnArr = new Array();
	warnArr[0]="rolenamemust";
	warnArr[1]="rangmust";
	warnArr[2]="methodmust";
	warnInit(warnArr); //验证常用组件
	var bl = validvalue('helpTitle');
	if(bl){	
		var users = getCheckBoxValues("ck_userlist");
		var groups = getCheckBoxValues("ck_usergrouplist");
		var depts = getCheckBoxValues("ck_deptlist");
		var posts = getCheckBoxValues("ck_postlist");
		var count = users.length+groups.length+depts.length+posts.length;
		
		
		    var tmpval = DWRUtil.getValue("rolename");
	       if(tmpval.length>1000){
	           setMustWarn("roletextmust","备注不能超过500个字符");
	           document.getElementById("roletext").focus();
	       		return;
	       }
		
		if(count==0){
			tit.innerHTML =" <img src=\"<%=contextPath%>/images/grid_images/rowdel.png\">&nbsp;<font color=\"red\">请选择适用范围!</font>"; 
			window.scrollTo(0,0);
			return false;
		}
		var pc=0;
		for(var i=0;i<methodArray.length;i++){
			pc+=getCheckedCount("ck_"+methodArray[i]);
		}
		if(pc==0){
			mm.innerHTML ="<img src=\"<%=contextPath%>/images/grid_images/rowdel.png\">&nbsp;<font color=\"red\">请选择权限!</font>"; 
			window.scrollTo(0,0);
			return false;
		}
		Btn.close();
		var role = new Object();
		if(<%=rid%>!=null){
			role.primaryKey = <%=rid%>;
		}
		role.roleName = DWRUtil.getValue("rolename");
		role.roleDesc = DWRUtil.getValue("roletext");
		
		var c=0;
		var binding = new Array();
		for(var i=0;i<users.length;i++){
			var bind = new Object();
			bind.bindType = <%=EnumUtil.SYS_ROLE_BIND_TYPE.BIND_USER.value%>;
			bind.bindValue = users[i];
			binding[c] = bind;
			c++;
		}
		for(var i=0;i<groups.length;i++){
			var bind = new Object();
			bind.bindType = <%=EnumUtil.SYS_ROLE_BIND_TYPE.BIND_GROUP.value%>;
			bind.bindValue = groups[i];
			binding[c] = bind;
			c++;
		}
		for(var i=0;i<depts.length;i++){
			var bind = new Object();
			bind.bindType = <%=EnumUtil.SYS_ROLE_BIND_TYPE.BIND_DEPT.value%>;
			bind.bindValue = depts[i];
			binding[c] = bind;
			c++;
		}
		for(var i=0;i<posts.length;i++){
			var bind = new Object();
			bind.bindType = <%=EnumUtil.SYS_ROLE_BIND_TYPE.BIND_POST.value%>;
			bind.bindValue = posts[i];
			binding[c] = bind;
			c++;
		}
		var p=0;
		var parray = new Array();
		for(var i=0;i<methodArray.length;i++){
			var tmps = getCheckedValues("ck_"+methodArray[i]);
			for(var j=0;j<tmps.length;j++){
				parray[p] = tmps[j];
				p++;
			}
		}
		dwrSysProcessService.saveRoleAndDetailBind(role,binding,parray,getCheckedValues("btn"),methodcallback);
	}
}
function methodcallback(data){
	Btn.open();
	if(<%=rid%>!=null){
		//confirmmsgAndTitle("编辑角色信息成功！是否想继续添加？","reset();","继续添加","closePage();","关闭页面");
		alertmsg(data,"closePage()");
	}else{
		//alertmsg(data,"reset()");
		confirmmsgAndTitle("编辑角色信息成功！是否想继续添加？","reset();","继续添加","closePage();","关闭页面");
	}
}

function reset(){
	DWRUtil.setValue("rolename","");
	DWRUtil.setValue("roletext","");
	var listarray = new Array();
	listarray[0] = "userlist";
	listarray[1] = "usergrouplist";
	listarray[2] = "deptlist";
	listarray[3] = "postlist";
	for(var a=0;a<listarray.length;a++){
		var listid = listarray[a];
		var tab = document.getElementById(listid);
		var rlen = tab.rows.length;
		for(var i=rlen-1;i>=0;i--){
			tab.deleteRow(i);
		}
	}
	for(var i=0;i<methodArray.length;i++){
		var cks = document.getElementsByName("ck_"+methodArray[i]);
		for(var j=0;j<cks.length;j++){
			cks[j].checked = false;
		}
	}
	
	document.getElementById("userid").value="";
	document.getElementById("employeename").value="";
	
	document.getElementById("deptid").value="";
	document.getElementById("deptname").value="";
	
	document.getElementById("groupid").value="";
	document.getElementById("groupname").value="";
	
	document.getElementById("postid").value="";
	document.getElementById("postname").value="";
}

function closePage(){
	closeMDITab(<%=request.getParameter("tab")%>);
}
</script>
</body>
</html>