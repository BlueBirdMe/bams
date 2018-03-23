<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script type="text/javascript"
			src="<%=contextPath%>/dwr/interface/dwrSysProcessService.js"></script>
		<title>权限设置</title>
		<%
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			String userids = request.getParameter("uids");
			SessionUser sessionUser = (SessionUser) LoginContext.getSessionValueByLogin(request);
			List<SysMethodInfo> companyMethods = sessionUser.getCompanyMethodsList();
			SysUserMethods methods = null;
			SysUserBtns btns = null;
			if (userids != null) {
				methods = UtilTool.getSysUserMethodByUid(this.getServletContext(), request, userids);
				btns = UtilTool.getSysUserBtnByUid(this.getServletContext(), request, userids);
			}
		%>
	</head>
<body class="inputcls">
	<div class="formDetail">
		<div class="requdiv">
			<label id="helpTitle"></label>
		</div>
		<div class="formTitle">
			权限设置
		</div>
		<div>
			<table class="detailtable" align="center">
				<tr>
					<th style="width: 5%;">
						用户权限:
					</th>
					<td id="usersinfo" class="detailtabletd"></td>
				</tr>
			</table>
		</div>
		<br />
		<div style="text-align: center;">
			<%
			if (companyMethods != null && companyMethods.size() > 0) {
		%>
		<DIV class="tabdiv"
			style="width: 100%; margin: 5px; overflow: hidden;" id="tabdiv2">
			<UL class="tags">
				<%
					for (int i = 0; i < companyMethods.size(); i++) {
							SysMethodInfo sysMethod = companyMethods.get(i);
							String tmpck = "";
							if (UtilTool.sysMethodIsCheckByUserMethods(sysMethod.getPrimaryKey(), methods)) {
								tmpck = "checked ='checked'";
							}
				%>
				<LI>
					<A onClick="tab2.selectTag(this);" href="javascript:void(0)"
						style="padding-top: 4px;"> <input type="checkbox"
							<%=tmpck%> id="<%=sysMethod.getMethodSign()%>"
							name="ck_<%=sysMethod.getMethodSign()%>"
							value="<%=sysMethod.getPrimaryKey()%>"
							onclick="checkboxchange(this,'<%=sysMethod.getPrimaryKey()%>','<%=sysMethod.getMethodSign()%>','<%=sysMethod.getLevelUnit()%>')"
							title="全选/取消"><%=sysMethod.getMethodInfoName()%> </A>
				</LI>
				<%
					}
				%>
			</UL>
			<DIV class="tagContentdiv" style="overflow: hidden; width: 99%">
				<%
					for (int i = 0; i < companyMethods.size(); i++) {
							SysMethodInfo sysMethod = companyMethods.get(i);
				%>
				<DIV class="tagContent" id="tag<%=i%>" style="overflow: hidden;">
					<%
						List<SysMethodInfo> levelist1 = UtilTool.getSysMethodMapByUserMethods(this.getServletContext(), request, sysMethod.getPrimaryKey(), methods, EnumUtil.SYS_METHOD_LEVEL.ONE.value);
						List<SysMethodInfo> levelist2 = UtilTool.getSysMethodMapByUserMethods(this.getServletContext(), request, sysMethod.getPrimaryKey(), methods, EnumUtil.SYS_METHOD_LEVEL.TWO.value);
						List<SysMethodInfo> levelist3 = UtilTool.getSysMethodMapByUserMethods(this.getServletContext(), request, sysMethod.getPrimaryKey(), methods, EnumUtil.SYS_METHOD_LEVEL.THREE.value);
						if (levelist1 != null && levelist1.size() > 0) {
					%>
					<table width="99%" cellpadding="4" cellspacing="0" border="0"
						style="line-height: 20px;">
						<%
							for (int j = 0; j < levelist1.size(); j++) {
								SysMethodInfo levlMethod = levelist1.get(j);
								String chk1 = "";
								if (levlMethod.isIschecked()) {
									chk1 = "checked ='checked'";
								}
						%>
						<tr>
							<td style="width: 12%; border-bottom: 1px solid #BDBCBC;"
								nowrap="nowrap">
								
								<table width="100%" cellpadding="4" cellspacing="1" border="0">
								<tr>
								<td nowrap="nowrap">
									<input type="checkbox" <%=chk1%>
										value="<%=levlMethod.getPrimaryKey()%>"
										upvalue="<%=levlMethod.getLevelUnit()%>"
										name="ck_<%=sysMethod.getMethodSign()%>"
										onclick="checkboxchange(this,'<%=levlMethod.getPrimaryKey()%>','<%=sysMethod.getMethodSign()%>','<%=levlMethod.getLevelUnit()%>')"
										id="chk_<%=levlMethod.getPrimaryKey()%>">
									<label title="<%=levlMethod.getMethodMsgStr()%>" for="chk_<%=levlMethod.getPrimaryKey()%>"><%=levlMethod.getMethodInfoName()%></label>
								</td>
								<%if(levlMethod.getBtns().size() > 0) {%>
								<td nowrap="nowrap" style="background:#fbf1a4;" title="功能按钮">
									<!-- 功能按钮 -->
									<%for (int z = 0; z < levlMethod.getBtns().size(); z++) { 
										SysMethodBtn btn = levlMethod.getBtns().get(z);
										
										UtilTool.setBtnChecked(btn,btns);
										
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
							<td style="border-bottom: 1px solid #BDBCBC;">
								<table width="100%" cellpadding="4" cellspacing="1" border="0"
									style="line-height: 20px;">
									<%
										for (int a = 0; a < levelist2.size(); a++) {
											SysMethodInfo levlMethod2 = levelist2.get(a);
											if (levlMethod2.getLevelUnit().trim().equals(levlMethod.getPrimaryKey())) {
												String chk2 = "";
												if (levlMethod2.isIschecked()) {
													chk2 = "checked ='checked'";
												}
									%>
									<tr>
										<td style="width: 12%" nowrap="nowrap">
											<table width="100%" cellpadding="4" cellspacing="1" border="0">
											<tr>
											<td nowrap="nowrap">
												<input type="checkbox" <%=chk2%>
													value="<%=levlMethod2.getPrimaryKey()%>"
													upvalue="<%=levlMethod2.getLevelUnit()%>"
													name="ck_<%=sysMethod.getMethodSign()%>"
													onclick="checkboxchange(this,'<%=levlMethod2.getPrimaryKey()%>','<%=sysMethod.getMethodSign()%>','<%=levlMethod2.getLevelUnit()%>')"
													id="chk_<%=levlMethod2.getPrimaryKey()%>">
												<label title="<%=levlMethod2.getMethodMsgStr()%>" for="chk_<%=levlMethod2.getPrimaryKey()%>"><%=levlMethod2.getMethodInfoName()%></label>
											</td>
											<%if(levlMethod2.getBtns().size() > 0) {%>
											<td nowrap="nowrap" style="background:#fbf1a4;" title="功能按钮">
												<!-- 功能按钮 -->
												<%for (int z = 0; z < levlMethod2.getBtns().size(); z++) { 
													SysMethodBtn btn = levlMethod2.getBtns().get(z);
													UtilTool.setBtnChecked(btn,btns);
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
											<table cellpadding="3" cellspacing="1" border="0"
												style="line-height: 20px;">
												<%
													int td = 0;
													for (int b = 0; b < levelist3.size(); b++) {
														SysMethodInfo levlMethod3 = levelist3.get(b);
														if (levlMethod3.getLevelUnit().trim().equals(levlMethod2.getPrimaryKey())) {
															String chk3 = "";
															if (levlMethod3.isIschecked()) {
																chk3 = "checked ='checked'";
															}
												%>
												<%
													if (td % 5 == 0) {
												%>
												<tr>
													<%
														}
													%>
													<td nowrap="nowrap">
													
														<table width="100%" cellpadding="4" cellspacing="1" border="0">
														<tr>
														<td nowrap="nowrap">
															<input type="checkbox" <%=chk3%>
																value="<%=levlMethod3.getPrimaryKey()%>"
																upvalue="<%=levlMethod3.getLevelUnit()%>"
																name="ck_<%=sysMethod.getMethodSign()%>"
																onclick="checkboxchange(this,'<%=levlMethod3.getPrimaryKey()%>','<%=sysMethod.getMethodSign()%>','<%=levlMethod3.getLevelUnit()%>')"
																id="chk_<%=levlMethod3.getPrimaryKey()%>">
															<label title="<%=levlMethod3.getMethodMsgStr()%>" for="chk_<%=levlMethod3.getPrimaryKey()%>"><%=levlMethod3.getMethodInfoName()%></label>
														</td>
														<%if(levlMethod3.getBtns().size() > 0) {%>
														<td nowrap="nowrap" style="background:#fbf1a4;" title="功能按钮">
															<!-- 功能按钮 -->
															<%for (int z = 0; z < levlMethod3.getBtns().size(); z++) { 
																SysMethodBtn btn = levlMethod3.getBtns().get(z);
																UtilTool.setBtnChecked(btn,btns);
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
												<%
													}
												%>
												<%
													}
																			}
												%>

											</table>
										</td>
									</tr>
									<%
										}
														}
									%>
								</table>
							</td>
						</tr>
						<%
							}
						%>
					</table>
					<%
						}
					%>
				</DIV>
				<%
					}
				%>
			</DIV>
		</DIV>
		<%
			}
		%>
	</div>
	</br>
</div>
<br />
<table align="center">
	<tr>
		<td>
			<btn:btn onclick="saverole();" value="保 存 " imgsrc="../../images/png-1718.png" title="保存权限设置" />
		</td>
		<td style="width: 15px;"></td>
		<td id="backbtn">
			<btn:btn onclick="closePage();" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"/>
		</td>
	</tr>
</table>
<script type="text/javascript">
var tab2 = new SysTab('<%=contextPath%>',null,"tabdiv2");
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
	initInput("helpTitle","权限设置，您可以在此处设置人员权限。");
	dwrCommonService.getHrmEmployeeNamesByUserIds('<%=userids%>',setpagevalue);
}

function setpagevalue(data){
	if(data.length > 0){
		var tmp="";
		for(var i=0;i<data.length;i++){
			if(i==data.length-1){
				tmp+=data[i];
			}else{
				tmp+=data[i]+"&nbsp;,&nbsp;";
			}
		}
		document.getElementById("usersinfo").innerHTML = tmp;
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
			
			btncheckboxchange(val, check);//设置功能按钮状态
			
			var tmp = cks[i].value;
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

//==============保存================
function closePage(){
	closeMDITab(<%=request.getParameter("tab")%>);
}

function saverole(){
	var p=0;
	var parray = new Array();
	for(var i=0;i<methodArray.length;i++){
		var tmps = getCheckedValues("ck_"+methodArray[i]);
		for(var j=0;j<tmps.length;j++){
			parray[p] = tmps[j];
			p++;
		}
	}
	Btn.close();
	dwrSysProcessService.updateSysUserMethods('<%=userids%>',parray,getCheckedValues("btn"),methodcallback);
}
function methodcallback(data){
	Btn.open();
	alertmsg(data,"closePage()");
}

</script>
</body>
</html>