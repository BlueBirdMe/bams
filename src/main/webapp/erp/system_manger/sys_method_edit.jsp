<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>编辑功能目录</title>
		<%
			String sid = request.getParameter("sid");
		%>
		<script type="text/javascript" src="<%=contextPath%>/dwr/interface/dwrSysProcessService.js"></script>
		<script type="text/javascript">

window.onload = function(){
	useLoadingMassage();
	initInput('helpTitle','您可以在此处编辑功能目录信息！');
	dwrSysProcessService.getSysMethodInfoByPK('<%=sid%>',setpagevalue);
	initBtnTable();
	initHelpTable();
}

function initBtnTable(){
	dwrSysProcessService.listSysMethodBtnByMethodIdResultBean('<%=sid%>',addBtnTable);
}

function initHelpTable(){
	dwrSysProcessService.listSysMethodHelpByMethodIdResultBean('<%=sid%>',addHelpTable);
}

	
function saveSysMethodInfo(){
	var warnArr = new Array();
	warnArr[0] ="methodInfoNameMust";
	warnArr[1] ="methodNoMust";
	warnInit(warnArr);
	var bl = validvalue('helpTitle');
	
	if(bl){
	    Btn.close();
		dwrSysProcessService.updateSysMethodInfo(getSysMethodInfo(), savecallback);
	}
}

function savecallback(data){
    Btn.open();//开放按钮
	if(data.success){
		alertmsg(data,"reload()");
	}else{
		alertmsg(data);
	}
}

function reload(){
    closeMDITab(<%=request.getParameter("tab")%>,"","tree");
}
	
function getSysMethodInfo(){
	 var info = new Object();
     info.primaryKey = DWRUtil.getValue("primaryKey");
	 info.methodInfoName = DWRUtil.getValue("methodInfoName");
	 info.methodInfoEngname =  DWRUtil.getValue("methodInfoEngname");
	 info.levelUnit = DWRUtil.getValue("levelUnitId");
	 info.imageSrc = DWRUtil.getValue("imageSrc");
	 info.methodNo = DWRUtil.getValue("methodNo");
	 info.methodLevel =  DWRUtil.getValue("methodLevel");
	 info.isAction = getRadioValueByName("isAction");
	 info.isDefault = getRadioValueByName("isDefault");
	 info.methodSign =  DWRUtil.getValue("methodSign");
	 info.defaultPage = DWRUtil.getValue("defaultPage");
	 info.methodUri = DWRUtil.getValue("methodUri");
	 info.methodMsg =  DWRUtil.getValue("methodMsg");
	 info.methodPages =  DWRUtil.getValue("methodPages");
	 return info;
}

function getBtn(){
    var btn = new Object();
    btn.primaryKey = DWRUtil.getValue("btnId");
    btn.priority = DWRUtil.getValue("btnPriority");
    btn.btnName = DWRUtil.getValue("btnName");
    btn.btnImg = DWRUtil.getValue("btnImg");
    btn.btnFun = DWRUtil.getValue("btnFun");
    btn.btnDesc = DWRUtil.getValue("btnDesc");
    btn.methodId = '<%=sid%>';
    return btn;
}

function getHelp(){
    var help = new Object();
    help.primaryKey = DWRUtil.getValue("helpId");
    help.priority = DWRUtil.getValue("helpPriority");
    help.helpImg = DWRUtil.getValue("helpImg");
    help.helpDesc = DWRUtil.getValue("helpDesc");
    help.methodId = '<%=sid%>';
    return help;
}

function setpagevalue(data){
	if(data != null && data.resultList.length > 0){
 			var info = data.resultList[0];
 			DWRUtil.setValue("primaryKey",info.primaryKey);
 			DWRUtil.setValue("methodInfoName",info.methodInfoName);
		    DWRUtil.setValue("methodInfoEngname",info.methodInfoEngname);
		    if(info.upSysMethodInfo != null){
		    	DWRUtil.setValue("levelUnitName",info.upSysMethodInfo.methodInfoName);
		    }
		    DWRUtil.setValue("levelUnitId",info.levelUnit);
	        DWRUtil.setValue("imageSrc",info.imageSrc);
	        DWRUtil.setValue("methodNo",info.methodNo);
	        DWRUtil.setValue("methodLevel",info.methodLevel);
	        setRadioValueByName("isAction",info.isAction);
	       	setRadioValueByName("isDefault",info.isDefault);
		    DWRUtil.setValue("methodSign",info.methodSign);
		    DWRUtil.setValue("defaultPage",info.defaultPage);
		    DWRUtil.setValue("methodUri",info.methodUri);
            DWRUtil.setValue("methodMsg",info.methodMsg);
            DWRUtil.setValue("methodPages",info.methodPages);
 		}
}


function addBtnTable(data){
	var tab = document.getElementById("btnlist");
	var rlen = tab.rows.length;
	for(var i=rlen-1;i>=1;i--){
		tab.deleteRow(i);
	}
	
	var dlist = data.resultList;
	if(dlist != null && dlist.length>0){
		for (var j = 0; j < dlist.length; j++) {
		var btn = dlist[j];
		
		var otr = tab.insertRow(-1);
		
		var td0=document.createElement("td");
		td0.style.cssText ="text-align:center";
	    td0.innerHTML = btn.primaryKey;
		var td01=document.createElement("td");
		td01.style.cssText ="text-align:center";
	    td01.innerHTML = btn.priority;
		var td1=document.createElement("td");
		td1.style.cssText ="text-align:center";
	    td1.innerHTML = btn.btnName;
	   	var td2=document.createElement("td");
	   	td2.style.cssText ="text-align:center";
	   	var imgurl = "<%=contextPath%>/images/grid_images/"+btn.btnImg;
	   	td2.innerHTML= "<img src='"+imgurl+"'/>";
	    var td3=document.createElement("td");
	    td3.style.cssText ="text-align:center";
	    td3.innerHTML = btn.btnFun;
	   	var td4=document.createElement("td");
	   	td4.innerHTML= btn.btnDesc;
	    var td5=document.createElement("td");
	    td5.style.cssText ="text-align:center";
		td5.innerHTML="<a href='javascript:void(0)' onclick=\"addOrEditBtn("+btn.primaryKey+")\">编辑</a>&nbsp;&nbsp;"+
						"<a href='javascript:void(0)' onclick=\"delBtn("+btn.primaryKey+")\">删除</a>";
	    
	    otr.appendChild(td0);
	    otr.appendChild(td01);
	    otr.appendChild(td1);
	    otr.appendChild(td2);
	    otr.appendChild(td3);
	    otr.appendChild(td4);
	    otr.appendChild(td5);
		}
	}
}

function addHelpTable(data){
	var tab = document.getElementById("helplist");
	var rlen = tab.rows.length;
	for(var i=rlen-1;i>=1;i--){
		tab.deleteRow(i);
	}
	
	var dlist = data.resultList;
	if(dlist != null && dlist.length>0){
		for (var j = 0; j < dlist.length; j++) {
			var help = dlist[j];
			
			var otr = tab.insertRow(-1);
			
			var td0=document.createElement("td");
			td0.style.cssText ="text-align:center";
		    td0.innerHTML = help.primaryKey;
		    var td01=document.createElement("td");
			td01.style.cssText ="text-align:center";
		    td01.innerHTML = help.priority;
			var td1=document.createElement("td");
			td1.style.cssText ="text-align:center";
			var imgurl = "<%=contextPath%>/images/grid_images/"+help.helpImg;
		    td1.innerHTML = "<img src='"+imgurl+"'/>";
		   	var td2=document.createElement("td");
		   	td2.innerHTML= help.helpDesc;
		   	var td3=document.createElement("td");
		   	td3.style.cssText ="text-align:center";
		   	td3.innerHTML="<a href='javascript:void(0)' onclick=\"addOrEditHelp("+help.primaryKey+")\">编辑</a>&nbsp;&nbsp;"+
							"<a href='javascript:void(0)' onclick=\"delHelp("+help.primaryKey+")\">删除</a>";
		    
			otr.appendChild(td0);
			otr.appendChild(td01);
		    otr.appendChild(td1);
		    otr.appendChild(td2);
		    otr.appendChild(td3);
		}
	}
}


function getImg(path, id){
	var box = SEL.getImg(path, id);
    box.show();
}

function addOrEditBtn(pk){
    var title = "";
	if(isNotBlank(pk)){
		title = "编辑功能按钮";
		dwrSysProcessService.getSysMethodBtnByPk(pk,setBtn);
	}else{
		title = "新增功能按钮";
		DWRUtil.setValue("btnPriority","");
		DWRUtil.setValue("btnId","");
		DWRUtil.setValue("btnName","");
	    DWRUtil.setValue("btnImg","");
	    DWRUtil.setValue("btnFun","");
	    DWRUtil.setValue("btnDesc","");
	}
	addOrEditDialog = art.dialog({
		title:title,
		width:"500px",
	    content: document.getElementById("btnFrom"),
    	ok: function () {
    		
    		if(isNotBlank(pk))
    			dwrSysProcessService.updateSysMethodBtn(getBtn(),callback);
    		else
    			dwrSysProcessService.saveSysMethodBtn(getBtn(),callback);
            return false;
        },
        cancelVal: '关闭',
        cancel: true //为true等价于function(){}
	});
}


function setBtn(data){
    if(data.success == true){
        if(data.resultList.length > 0){
            var btn = data.resultList[0];
            DWRUtil.setValue("btnPriority",btn.priority);
            DWRUtil.setValue("btnId",btn.primaryKey);
            DWRUtil.setValue("btnName",btn.btnName);
            DWRUtil.setValue("btnImg",btn.btnImg);
            DWRUtil.setValue("btnFun",btn.btnFun);
            DWRUtil.setValue("btnDesc",btn.btnDesc);
        }
    }
}


function addOrEditHelp(pk){
    var title = "";
	if(isNotBlank(pk)){
		title = "编辑操作提示";
		dwrSysProcessService.getSysMethodHelpByPk(pk,setHelp);
	}else{
		title = "新增操作提示";
		DWRUtil.setValue("helpPriority","");
		DWRUtil.setValue("helpId","");
		DWRUtil.setValue("helpImg","");
	    DWRUtil.setValue("helpDesc","");
	}
	addOrEditDialog = art.dialog({
		title:title,
		width:"500px",
	    content: document.getElementById("helpFrom"),
    	ok: function () {
    		
    		if(isNotBlank(pk))
    			dwrSysProcessService.updateSysMethodHelp(getHelp(),callback);
    		else
    			dwrSysProcessService.saveSysMethodHelp(getHelp(),callback);
            return false;
        },
        cancelVal: '关闭',
        cancel: true //为true等价于function(){}
	});
}

function setHelp(data){
    if(data.success == true){
        if(data.resultList.length > 0){
            var help = data.resultList[0];
            DWRUtil.setValue("helpPriority",help.priority);
            DWRUtil.setValue("helpId",help.primaryKey);
            DWRUtil.setValue("helpImg",help.helpImg);
            DWRUtil.setValue("helpDesc",help.helpDesc);
        }
    }
}


function callback(data){
    addOrEditDialog.close();
    initBtnTable();
	initHelpTable();
}

function delBtn(id){
	var ids = new Array();
	ids[0] = id;
	dwrSysProcessService.deleteMethodBtnByIds(ids,function(data){initBtnTable();});
}

function delHelp(id){
	var ids = new Array();
	ids[0] = id;
	dwrSysProcessService.deleteMethodHelpByIds(ids,function(data){initHelpTable();});
}


</script>
	</head>
	<body class="inputcls">
	<div style="display:none;" id="btnFrom">
		<input type="hidden" id="btnId"></input>
	    <table class="inputtable">
	        <tr>
	            <th><em>*</em>&nbsp;&nbsp;排序</th>
	            <td><input type="text" id="btnPriority"></input></td>
	        </tr>
	        <tr>
	            <th><em>*</em>&nbsp;&nbsp;名称</th>
	            <td><input type="text" id="btnName"></input></td>
	        </tr>
	        <tr>
	            <th><em>*</em>&nbsp;&nbsp;图标</th>
	            <td><input type="text" id="btnImg" onclick="getImg('grid_images','btnImg')" class="takeform"></input></td>
	        </tr>
	        <tr>
	            <th><em>*</em>&nbsp;&nbsp;方法名</th>
	            <td><input type="text" id="btnFun"></input></td>
	        </tr>
	        <tr>
	            <th>描述</th>
	            <td><textarea id="btnDesc" style="width:90%;"></textarea></td>
	        </tr>
	    </table>
	</div>
	
	<div style="display:none;" id="helpFrom">
		<input type="hidden" id="helpId"></input>
	    <table class="inputtable">
	    	<tr>
	            <th><em>*</em>&nbsp;&nbsp;排序</th>
	            <td><input type="text" id="helpPriority"></input></td>
	        </tr>
	        <tr>
	            <th><em>*</em>&nbsp;&nbsp;图标</th>
	            <td><input type="text" id="helpImg" onclick="getImg('grid_images','helpImg')" class="takeform"></input></td>
	        </tr>
	        <tr>
	            <th>描述</th>
	            <td><textarea id="helpDesc" style="width:90%;"></textarea></td>
	        </tr>
	    </table>
	</div>


	
		<div class="formDetail">
			<div class="requdiv">
				<label id="helpTitle"></label>
			</div>
			<div class="formTitle">
				编辑功能信息
			</div>
			<table class="inputtable">
				<tr>
					<th width="15%">
						<em>* </em>功能编号
					</th>
					<td>
						<input type="text" id="primaryKey" readonly="readonly">
					</td>
					<th width="15%">
						<em>* </em>功能名称
					</th>
					<td>
						<input type="text" id="methodInfoName" maxlength="50" must="功能名称不能为空。" formust="methodInfoNameMust">
						<label id="methodInfoNameMust"></label>
					</td>
				</tr>
				
				<tr>
					<th>
						功能层次
					</th>
					<td>
						<input type="text" id="methodLevel" readonly="readonly">
					</td>
					<th>
						<em>* </em>显示顺序
					</th>
					<td>
						<input type="text" id="methodNo" maxlength="9" class="numform" formust="methodNoMust" must="显示顺序不能为空">
						<label id="methodNoMust"></label>
					</td>
				</tr>
				
				<tr>
					<th>
						上级功能
					</th>
					<td>
						<input type="text" id="levelUnitName" readonly="readonly">
						<input type="hidden" id="levelUnitId" >
					</td>
					
					<th>
						英文名称
					</th>
					<td>
						<input type="text" id="methodInfoEngname" maxlength="50">
					</td>
				</tr>
				<tr>
					<th>
						是否有效
					</th>
					<td colspan="3">
						 <%=UtilTool.getRadioOptionsByEnum(EnumUtil.SYS_ISACTION.getSelectAndText(""),"isAction")%>
					</td>
				</tr>
				<tr>
					<th>
						是否默认
					</th>
					<td>
						 <%=UtilTool.getRadioOptionsByEnum(EnumUtil.SYS_IS_DEFAULT.getSelectAndText(""),"isDefault")%>
					</td>
					<th>
						默 认 页
					</th>
					<td>
						<input type="text" id="defaultPage" maxlength="100" size="116">
					</td>
				</tr>
				<tr>
					<th>
						功能标识
					</th>
					<td>
						<input type="text" id="methodSign" maxlength="50">
					</td>
					<th>
						功能图标
					</th>
					<td>
						<input type="text" id="imageSrc" maxlength="50" class="takeform" ondblclick="getImg('projectimg','imageSrc')"/>
						<font class="tip">双击选择，新图片放到images/projectimg目录</font>
					</td>
				</tr>
				
				<tr>
					<th>
						跳转路径
					</th>
					<td colspan="3">
						<input type="text" id="methodUri" style="width:50%">
					</td>
				</tr>
				<tr>
					<th>
						功能描述
					</th>
					<td colspan="3">
						<textarea id="methodMsg" style="width:50%"></textarea>
					</td>
				</tr>
				<tr>
					<th>
						功能相关页面
					</th>
					<td colspan="3">
						<textarea id="methodPages" style="width:50%"></textarea>
					</td>
				</tr>
				
			</table>
			
		<table cellpadding='0' cellspacing='0' border='0'  width ='90%' align="center"/>
		<tr style="BACKGROUND-IMAGE: url('<%=contextPath %>/images/grid_images/wbg.gif');" height="24px">	
			<td align='left' style='padding-left:10px;font-weight: bold;'>设置功能按钮</td>
			<td style="text-align: right;padding-right: 10px;" nowrap="nowrap" align="right">
			<div class='div_btn' onmouseover ='this.className ="div_btn_hover"' onmouseout ='this.className ="div_btn"'   onclick="addOrEditBtn()">&nbsp;添加按钮&nbsp;</div>
			</td>
		</tr>
		<tr>
			<td valign='top' colspan="2">
			<table  class='tablerowStyleColor'  cellSpacing='1' cellPadding='5' width='100%' align='center' border='1' id='btnlist'>
				<tr style="BACKGROUND-IMAGE: url('<%=contextPath %>/images/grid_images/fhbg.gif');">
					<td class='tableTitle1'>ID</td>
					<td class='tableTitle1'>排序</td>
					<td class='tableTitle1'>名称</td>
					<td class='tableTitle1'>图标</td>
					<td class='tableTitle1'>功能方法名</td>
					<td class='tableTitle1'>备注</td>
					<td class='tableTitle1' width="40">操作</td>
				</tr>
			</table>
			</td>
		</tr>
		</table>
		<br/>
		<table cellpadding='0' cellspacing='0' border='0'  width ='90%' align="center"/>
		<tr style="BACKGROUND-IMAGE: url('<%=contextPath %>/images/grid_images/wbg.gif');" height="24px">	
			<td align='left' style='padding-left:10px;font-weight: bold;'>设置功能提示</td>
			<td style="text-align: right;padding-right: 10px;" nowrap="nowrap" align="right">
			<div class='div_btn' onmouseover ='this.className ="div_btn_hover"' onmouseout ='this.className ="div_btn"' onclick="addOrEditHelp()">&nbsp;添加提示&nbsp;</div>
			</td>
		</tr>
		<tr>
			<td valign='top' colspan="2">
			<table  class='tablerowStyleColor'  cellSpacing='1' cellPadding='5' width='100%' align='center' border='1' id='helplist'>
				<tr style="BACKGROUND-IMAGE: url('<%=contextPath %>/images/grid_images/fhbg.gif');">
					<td class='tableTitle1'>ID</td>
					<td class='tableTitle1'>排序</td>
					<td class='tableTitle1'>图标</td>
					<td class='tableTitle1'>提示内容</td>
					<td class='tableTitle1' width="40">操作</td>
				</tr>
			</table>
			</td>
		</tr>
		</table>
		
		</div>
		<table align="center">
			<tr>
				<td>
					<btn:btn onclick="saveSysMethodInfo();" value="保 存 "
						imgsrc="../../images/png-1718.png" title="保存功能目录"></btn:btn>
				</td>
				<td style="width: 10px;"></td>
				<td>
					<btn:btn onclick="reload()" value="关 闭 "
						imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn>
				</td>
			</tr>
		</table>
	</body>
</html>