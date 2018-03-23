<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>邮箱设置</title>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrMailService.js"></script>
<script type="text/javascript">
	var count = 0;
	var sign = 1;
	window.onload = function(){
		useLoadingMassage();
		initInput("helpTitle","外部邮箱设置，最多支持5个邮箱！同时请确保设置邮箱支持SMTP及POP协议(暂不支持IMAP协议)。");
		//查询用户已设置邮箱
		loadset();
	}
	
	function loadset(){
		dwrMailService.getOaNetMailSetList(setpagevalue);
	}
	
	function setpagevalue(data){
		if(data.success){
			var tab = document.getElementById("mailset");
			var rlen = tab.rows.length;
			for(var i=rlen-1;i>=1;i--){
				tab.deleteRow(i);
			}
			if(data.resultList.length>0){
				for(var i=0;i<data.resultList.length;i++){
					var obj = data.resultList[i];
					setnetMail(obj);
				}
			}else{
				addnetmail();
			}
		}
	}
	
	function setnetMail(obj){
		sign++;
		count++;
		var tab = document.getElementById("mailset");
		var len = tab.rows.length;
		var otr = tab.insertRow(-1);
		
		var td1=document.createElement("td");
		td1.style.cssText ="text-align:center";
		td1.innerHTML = len;
		var td2 = document.createElement("td");
		
		var str = "<table class='inputtable'>";
		str	+= "<tr><th width='35%'><em>* </em>发送别名</th><td>";
		str += "<input type='hidden' name='primaryKey' value='"+obj.primaryKey+"'/>";
		str += "<input type='text' maxlength='15' style='width:60%' name='mailname' value='"+obj.oaNetmailName+"'/><label></labe></td></tr>";
		str	+= "<tr><th width='35%'><em>* </em>邮箱名称</th>";
		str += "<td>"+obj.oaNetmailFrom+"<input type='hidden' maxlength='50' style='width:60%' name='mailfrom' value='"+obj.oaNetmailFrom+"'/><label></labe></td></tr>";
		str	+= "<tr><th><em>* </em>邮箱SMTP</th>";
		str += "<td><input type='text' maxlength='50' style='width:60%' name='mailsmtp' value='"+obj.oaNetmailSmtp+"'/><label></labe></td></tr>";
		str	+= "<tr><th><em>* </em>邮箱POP</th>";
		str += "<td><input type='text' maxlength='50' style='width:60%' name='mailpop' value='"+obj.oaNetmailPop3+"'/><label></labe></td></tr>";
		str	+= "<tr><th><em>* </em>邮箱用户名</th>";
		str += "<td><input type='text' maxlength='50' style='width:60%' name='mailuser' value = '"+obj.oaNetmailUser+"'/><label></labe></td></tr>";
		str	+= "<tr><th><em>* </em>邮箱密码</th>";
		str += "<td><input type='password' maxlength='50' style='width:60%' name='mailpwd' value = '"+obj.oaNetmailPassword+"'/><label></labe></td></tr>";
		str	+= "<tr><th><em>* </em>接收最大数</th>";
		str += "<td><input type='text'  name='mailcount' class='numform' maxlength='2' value='"+obj.oaNetmailCount+"'/><label></labe>请根据实际网速设置10-99之间</td></tr>";
		str	+= "<tr><th><em>* </em>是否SMTP验证</th>";
		str += "<td><select name='mailvfy'>";
		if(obj.oaNetmailIsverify=='true'){
			str += "<option value='true' selected='selected'>是</option><option value='false'>否</option>";
		}else{
			str += "<option value='true'>是</option><option value='false'  selected='selected'>否</option>";
		}
		str += "</select></td></tr>";
		str	+= "<tr><th rowspan='2'><img src='<%=contextPath%>/images/maillantest.png' name='testImg' title='测试连接' alt='测试连接' style='cursor: pointer;' onclick='serverTest()'/>&nbsp;&nbsp;&nbsp;&nbsp;<br/><label id='lantest'></label></th>";
		var chk1 ="";
		var chk2 ="";
		var chk3 = "";
		if(obj.oaNetmailIssend==<%=EnumUtil.SYS_ISACTION.Vaild.value%>){
			chk1 = "checked='checked'";
		}
		if(obj.oaNetmailIsaccp==<%=EnumUtil.SYS_ISACTION.Vaild.value%>){
			chk2 = "checked='checked'";
		}
		if(obj.oaNetmailIsDefault==<%=EnumUtil.SYS_ISACTION.Vaild.value%>){
			chk3 = "checked='checked'";
		}
		str += "<td><input type='checkbox' name='mailissend' id='mailissend"+sign+"' "+chk1+"><label for='mailissend"+sign+"' >允许发送邮件</label>&nbsp;&nbsp;&nbsp;";
		str += "<input type='checkbox' name='mailisaccp' id='mailisaccp"+sign+"'  "+chk2+"><label for='mailisaccp"+sign+"'>允许接收邮件</label>&nbsp;&nbsp;&nbsp;";
		str += "<input type='radio' name='mailisdefault' id='mailisdefault"+sign+"' "+chk3+"><label for='mailisdefault"+sign+"' style='color:#336699'>设置为默认邮箱</label>";
		str += "</td></tr>";
		str	+= "<tr>";
		
		var chk4 = "";
		var pprot =obj.oaNetmailPop3Prot;
		var sprot = obj.oaNetmailSmtpProt;
		if(obj.oaNetmailIsSSL==<%=EnumUtil.SYS_ISACTION.Vaild.value%>){
			chk4 = "checked='checked'";
			if(pprot==null){
				pprot = "995";
			}
			if(sprot == null){
				sprot = "465";
			}
		}else{
			if(pprot==null){
				pprot = "110";
			}
			if(sprot == null){
				sprot = "25";
			}
		}
		
		str += "<td><input type='checkbox' name='mailisssl' id='mailisssl"+sign+"' onclick='changprot(this,"+sign+")' "+chk4+"><label for='mailisssl"+sign+"' >启用SSL安全连接</label><br/><br/>";
		str += "接收端口：<input type='text' name='popprot' id='popprot"+sign+"' class='numform' size='4' maxlength='4' value='"+pprot+"'>&nbsp;&nbsp;&nbsp;";
		str += "发送端口：<input type='text' name='smtpprot' id='smtpprot"+sign+"' class='numform' size='4' maxlength='4' value='"+sprot+"'>&nbsp;&nbsp;&nbsp;<span style='color:#808080'>请参考邮箱帮助进行设置</span>";
		str += "</td></tr>";
		str += "</table>";
		td2.innerHTML = str;
		
		
		var td3=document.createElement("td");
		td3.style.cssText ="text-align:center";
		td3.innerHTML="<a href='javascript:void(0)' onclick=\"deltablerow("+obj.primaryKey+")\">删除</a>";
		
		otr.appendChild(td1);
	    otr.appendChild(td2);
	    otr.appendChild(td3);
	}
	
	function deltablerow(pk){
		if(pk>0){
			confirmmsg("删除邮箱设置将清除相关邮件，确定吗？", "deltablerowOk("+pk+")");
		}else{
			deltablerowOk(pk);
		}
	}
	
	function deltablerowOk(pk){
		if(pk>0){
			//清除发件箱、收件箱、草稿箱
			dwrMailService.deleteOaNetMailBySetPk(pk,deletecallback);
		}
		var tab=document.getElementById("mailset");
		var rIndex = event.srcElement.parentElement.parentElement.rowIndex;
		tab.deleteRow(rIndex);
		var len = tab.rows.length
		for(var i=rIndex;i<len;i++){
		  	tab.rows[i].cells[0].innerHTML = i.toString();
		}
		count--;
	}
	
	function deletecallback(data){
		alertmsg(data,"loadset()");
	}
	
	function addnetmail(){
		if(count==5){
			alertmsg("最多可设置5个外部邮箱！");
			return;
		}
		sign++;
		count++;
		var tab = document.getElementById("mailset");
		var len = tab.rows.length;
		var otr = tab.insertRow(-1);
		
		var td1=document.createElement("td");
		td1.style.cssText ="text-align:center";
		td1.innerHTML = len;
		var td2 = document.createElement("td");
		
		var str = "<table class='inputtable'>";
		str	+= "<tr><th width='35%'><em>* </em>发送别名</th><td>";
		str += "<input type='hidden' name='primaryKey' value='0'/>";
		str += "<input type='text' maxlength='15' style='width:60%' name='mailname'/><label></labe></td></tr>";
		str	+= "<tr><th width='35%'><em>* </em>邮箱名称</th>";
		str += "<td><input type='text' maxlength='50' style='width:60%' name='mailfrom'/><label></labe></td></tr>";
		str	+= "<tr><th><em>* </em>邮箱SMTP</th>";
		str += "<td><input type='text' maxlength='50' style='width:60%' name='mailsmtp'/><label></labe></td></tr>";
		str	+= "<tr><th><em>* </em>邮箱POP</th>";
		str += "<td><input type='text' maxlength='50' style='width:60%' name='mailpop'/><label></labe></td></tr>";
		str	+= "<tr><th><em>* </em>邮箱用户名</th>";
		str += "<td><input type='text' maxlength='50' style='width:60%' name='mailuser'/><label></labe></td></tr>";
		str	+= "<tr><th><em>* </em>邮箱密码</th>";
		str += "<td><input type='password' maxlength='50' style='width:60%' name='mailpwd'/><label></labe></td></tr>";
		str	+= "<tr><th><em>* </em>是否SMTP验证</th>";
		str += "<td><select name='mailvfy'><option value='true'>是</option><option value='false'>否</option></select></td></tr>";
		str	+= "<tr><th><em>* </em>接收最大数</th>";
		str += "<td><input type='text'  name='mailcount' class='numform' maxlength='2' value='20'/><label></labe>请根据实际网速设置10-99之间</td></tr>";
		str	+= "<tr><th rowspan='2'><img src='<%=contextPath%>/images/maillantest.png' title='测试连接' alt='测试连接' style='cursor: pointer;' onclick='serverTest()' name='testImg'/>&nbsp;&nbsp;&nbsp;&nbsp;<br/><label id='lantest'></label></th>";
		str += "<td><input type='checkbox' name='mailissend' id='mailissend"+sign+"' checked='checked'><label for='mailissend"+sign+"' >允许发送邮件</label>&nbsp;&nbsp;&nbsp;";
		str += "<input type='checkbox' name='mailisaccp' id='mailisaccp"+sign+"'  checked='checked'><label for='mailisaccp"+sign+"'>允许接收邮件</label>&nbsp;&nbsp;&nbsp;";
		str += "<input type='radio' name='mailisdefault' id='mailisdefault"+sign+"'><label for='mailisdefault"+sign+"' style='color:#336699'>设置为默认邮箱</label>";
		str += "</td></tr>";
		str	+= "<tr>";
		str += "<td><input type='checkbox' name='mailisssl' id='mailisssl"+sign+"' onclick='changprot(this,"+sign+")'><label for='mailisssl"+sign+"'>启用SSL安全连接</label><br/><br/>";
		str += "接收端口：<input type='text' name='popprot' id='popprot"+sign+"' class='numform' size='4' maxlength='4' value='110'>&nbsp;&nbsp;&nbsp;";
		str += "发送端口：<input type='text' name='smtpprot' id='smtpprot"+sign+"' class='numform' size='4' maxlength='4' value='25'>&nbsp;&nbsp;&nbsp;<span style='color:#808080'>请参考邮箱帮助进行设置</span>";
		str += "</td></tr>";
		str += "</table>";
		td2.innerHTML = str;
		
		var td3=document.createElement("td");
		td3.style.cssText ="text-align:center";
		td3.innerHTML="<a href='javascript:void(0)' onclick=\"deltablerow(0)\">删除</a>";
		
		otr.appendChild(td1);
	    otr.appendChild(td2);
	    otr.appendChild(td3);

	}
	
	function savenetmail(){
		var pks = document.getElementsByName("primaryKey");
		var mns = document.getElementsByName("mailname");
		var fms = document.getElementsByName("mailfrom");
		var smtps = document.getElementsByName("mailsmtp");
		var pops = document.getElementsByName("mailpop");
		var users = document.getElementsByName("mailuser");
		var pwds = document.getElementsByName("mailpwd");
		var vfys = document.getElementsByName("mailvfy");
		var sends = document.getElementsByName("mailissend");
		var accps = document.getElementsByName("mailisaccp");
		var defaults = document.getElementsByName("mailisdefault");
		var ssls = document.getElementsByName("mailisssl");
		var popts = document.getElementsByName("popprot");
		var sps = document.getElementsByName("smtpprot");
		var nmcut = document.getElementsByName("mailcount");
		if(fms.length==0){
			setMustWarn("detailMust","请添加设置明细！");
			return;
		}else{
			document.getElementById("detailMust").innerHTML ="";
		}
		
		for(var i=0;i<mns.length;i++){
			var tmp = mns[i].value;
			var pnd = mns[i].nextSibling;
			if(tmp==""||tmp.length==0){
				if(pnd!=null){
					setMustWarnByObj(pnd,"请输入邮箱别名！");
				}
				mns[i].focus();
				return;
			}else{
				pnd.innerHTML ="";
			}
		}
		
		for(var i=0;i<fms.length;i++){
			var tmp = fms[i].value;
			var pnd = fms[i].nextSibling;
			if(tmp==""||tmp.length==0){
				if(pnd!=null){
					setMustWarnByObj(pnd,"请输入邮箱名称！");
				}
				fms[i].focus();
				return;
			}else{
				pnd.innerHTML ="";
			}
		}
		for(var i=0;i<smtps.length;i++){
			var tmp = smtps[i].value;
			var pnd = smtps[i].nextSibling;
			if(tmp==""||tmp.length==0){
				if(pnd!=null){
					setMustWarnByObj(pnd,"请输入邮箱smt协议！");
				}
				smtps[i].focus();
				return;
			}else{
				if(pnd!=null){
					pnd.innerHTML ="";
				}	
			}
		}
		for(var i=0;i<pops.length;i++){
			var tmp = pops[i].value;
			var pnd = pops[i].nextSibling;
			if(tmp==""||tmp.length==0){
				if(pnd!=null){
					setMustWarnByObj(pnd,"请输入邮箱pop3协议！");
				}
				pops[i].focus();
				return;
			}else{
				if(pnd!=null){
					pnd.innerHTML ="";
				}
			}
		}
		for(var i=0;i<users.length;i++){
			var tmp = users[i].value;
			var pnd = users[i].nextSibling;
			if(tmp==""||tmp.length==0){
				if(pnd!=null){
					setMustWarnByObj(pnd,"请输入邮箱用户名！");
				}
				users[i].focus();
				return;
			}else{
				if(pnd!=null){
					pnd.innerHTML ="";
				}
			}
		}
		for(var i=0;i<pwds.length;i++){
			var tmp = pwds[i].value;
			var pnd = pwds[i].nextSibling;
			if(tmp==""||tmp.length==0){
				if(pnd!=null){
					setMustWarnByObj(pnd,"请输入邮箱密码！");
				}
				pwds[i].focus();
				return;
			}else{
				if(pnd!=null){
					pnd.innerHTML ="";
				}
			}
		}
		
		for(var i=0;i<nmcut.length;i++){
			var tmp = nmcut[i].value;
			var pnd = nmcut[i].nextSibling;
			if(tmp==""||tmp.length==0||!isNumber(tmp)){
				if(pnd!=null){
					setMustWarnByObj(pnd,"接收最大数不能为空且必须为数字！");
				}
				nmcut[i].focus();
				return;
			}else if(parseInt(tmp)<10||parseInt(tmp)>99){
				if(pnd!=null){
					setMustWarnByObj(pnd,"接收最大数需在10-99之间！");
				}
				nmcut[i].focus();
				return;
			}else{
				if(pnd!=null){
					pnd.innerHTML ="";
				}
			}
		}
		
		
		if(getRadioValueByName("mailisdefault")==null){
			alertmsg("请设置默认邮箱！");
			return;
		}
		
		var mailArray = new Array();
		
		for(var i=0;i<fms.length;i++){
			var ma = new Object();
			var pk = pks[i].value;
			if(pk>0&&parseInt(pk)>0){
				ma.primaryKey = pk;
			}
			ma.oaNetmailName = mns[i].value;
			ma.oaNetmailFrom = fms[i].value;
			ma.oaNetmailSmtp = smtps[i].value;
			ma.oaNetmailPop3 = pops[i].value;
			ma.oaNetmailUser = users[i].value;
			ma.oaNetmailPassword = pwds[i].value;
			ma.oaNetmailIsverify = vfys[i].value;
			var sd = <%=EnumUtil.SYS_ISACTION.No_Vaild.value%>;
			if(sends[i].checked){
				sd = <%=EnumUtil.SYS_ISACTION.Vaild.value%>;
			}
			ma.oaNetmailIssend = sd;
			
			var ap = <%=EnumUtil.SYS_ISACTION.No_Vaild.value%>;
			if(accps[i].checked){
				ap = <%=EnumUtil.SYS_ISACTION.Vaild.value%>;
			}
			
			ma.oaNetmailIsaccp = ap;
			
			var df = <%=EnumUtil.SYS_ISACTION.No_Vaild.value%>;
			if(defaults[i].checked){
				df = <%=EnumUtil.SYS_ISACTION.Vaild.value%>;
			}
			ma.oaNetmailIsDefault = df;
			var sl = <%=EnumUtil.SYS_ISACTION.No_Vaild.value%>;
			if(ssls[i].checked){
				sl = <%=EnumUtil.SYS_ISACTION.Vaild.value%>;
			}
			ma.oaNetmailIsSSL = sl;
			
			ma.oaNetmailPop3Prot = popts[i].value;
			ma.oaNetmailSmtpProt = sps[i].value;
			ma.oaNetmailCount = nmcut[i].value;
			mailArray.push(ma);
		}
		
		dwrMailService.saveOaNetMailSet(mailArray,savecallback);
	}
	function savecallback(data){
		alertmsg(data,"loadset()");
	}
	
	function showtest(){
		var box = new Sys.msgbox('邮箱设置示例','<%=contextPath%>/erp/mobile_sms/netmail_settest.jsp','800','500');
		box.msgtitle="<b>邮箱设置示例</b>";
		box.show();
	}
	
	function changprot(obj,sign){
		var pobj = document.getElementById("popprot"+sign);
		var sobj = document.getElementById("smtpprot"+sign);
		if(obj.checked){
			pobj.value ='995';
			sobj.value = '465';
		}else{
			pobj.value ='110';
			sobj.value = '25';
		}
	}
	
	function serverTest(){
		var imgs = document.getElementsByName("testImg");
		var r=-1;
		
		for(var i=0;i<imgs.length;i++){
			if(imgs[i]==event.srcElement){
				r=i;
				break;
			}
		}
		if(r>-1){
			var mns = document.getElementsByName("mailname")[r];
			var fms = document.getElementsByName("mailfrom")[r];
			var smtps = document.getElementsByName("mailsmtp")[r];
			var pops = document.getElementsByName("mailpop")[r];
			var users = document.getElementsByName("mailuser")[r];
			var pwds = document.getElementsByName("mailpwd")[r];
			var vfys = document.getElementsByName("mailvfy")[r];
			var sends = document.getElementsByName("mailissend")[r];
			var accps = document.getElementsByName("mailisaccp")[r];
			var defaults = document.getElementsByName("mailisdefault")[r];
			var ssls = document.getElementsByName("mailisssl")[r];
			var popts = document.getElementsByName("popprot")[r];
			var sps = document.getElementsByName("smtpprot")[r];
			
			var tmp = mns.value;
			var pnd = mns.nextSibling;
			if(tmp==""||tmp.length==0){
				if(pnd!=null){
					setMustWarnByObj(pnd,"邮箱别名不能为空！");
				}
				mns.focus();
				return;
			}else{
				if(pnd!=null){
					pnd.innerHTML ="";
				}
			}
			tmp = fms.value;
			pnd = fms.nextSibling;
			if(tmp==""||tmp.length==0){
				if(pnd!=null){
					setMustWarnByObj(pnd,"邮箱名称不能为空！");
				}
				fms.focus();
				return;
			}else{
				if(pnd!=null){
					pnd.innerHTML ="";
				}
			}
			tmp = smtps.value;
			pnd = smtps.nextSibling;
			if(tmp==""||tmp.length==0){
				if(pnd!=null){
					setMustWarnByObj(pnd,"邮箱smtp不能为空！");
				}
				smtps.focus();
				return;
			}
			else{
				if(pnd!=null){
					pnd.innerHTML ="";
				}	
			}
			tmp = pops.value;
			pnd = pops.nextSibling;
			if(tmp==""||tmp.length==0){
				if(pnd!=null){
					setMustWarnByObj(pnd,"邮箱pop3不能为空！");
				}
				pops.focus();
				return;
			}else{
				if(pnd!=null){
					pnd.innerHTML ="";
				}
			}
			tmp = users.value;
			pnd = users.nextSibling;
			if(tmp==""||tmp.length==0){
				if(pnd!=null){
					setMustWarnByObj(pnd,"邮箱用户名不能为空！");
				}
				users.focus();
				return;
			}else{
				if(pnd!=null){
					pnd.innerHTML ="";
				}
			}
			tmp = pwds.value;
			pnd = pwds.nextSibling;
			if(tmp==""||tmp.length==0){
				if(pnd!=null){
					setMustWarnByObj(pnd,"邮箱密码不能为空！");
				}
				pwds.focus();
				return;
			}else{
				if(pnd!=null){
					pnd.innerHTML ="";
				}
			}
			
			var ma = new Object();
			ma.oaNetmailName = mns.value;
			ma.oaNetmailFrom = fms.value;
			ma.oaNetmailSmtp = smtps.value;
			ma.oaNetmailPop3 = pops.value;
			ma.oaNetmailUser = users.value;
			ma.oaNetmailPassword = pwds.value;
			ma.oaNetmailIsverify = vfys.value;
			var sd = <%=EnumUtil.SYS_ISACTION.No_Vaild.value%>;
			if(sends.checked){
				sd = <%=EnumUtil.SYS_ISACTION.Vaild.value%>;
			}
			ma.oaNetmailIssend = sd;
			
			var ap = <%=EnumUtil.SYS_ISACTION.No_Vaild.value%>;
			if(accps.checked){
				ap = <%=EnumUtil.SYS_ISACTION.Vaild.value%>;
			}
			
			ma.oaNetmailIsaccp = ap;
			
			var df = <%=EnumUtil.SYS_ISACTION.No_Vaild.value%>;
			if(defaults.checked){
				df = <%=EnumUtil.SYS_ISACTION.Vaild.value%>;
			}
			ma.oaNetmailIsDefault = df;
			var sl = <%=EnumUtil.SYS_ISACTION.No_Vaild.value%>;
			if(ssls.checked){
				sl = <%=EnumUtil.SYS_ISACTION.Vaild.value%>;
			}
			ma.oaNetmailIsSSL = sl;
			
			ma.oaNetmailPop3Prot = popts.value;
			ma.oaNetmailSmtpProt = sps.value;
			
			dwrMailService.netMailTest(ma,testcallback);
		}
	}
	
	function testcallback(data){
		alertmsg(data);
	}
</script>
</head>
<body class="inputcls">
<div class="formDetail">
<div class="requdiv"><label id="helpTitle"></label></div>
    <div class="formTitle">邮箱设置</div>
    <label id="detailMust" style="text-align: left;width: 95%;padding: 2px;"></label>
	<table cellpadding='0' cellspacing='0' border='0' align='center'/>
	  <tr style="BACKGROUND-IMAGE: url('<%=contextPath %>/images/grid_images/wbg.gif');" height="26px">
		<td align='left' style='padding-left:10px;font-weight: bold;'>设置明细</td>
		<td style="text-align: right;" nowrap="nowrap" align="right">
		<div class='div_btn' onmouseover ='this.className ="div_btn_hover"' onmouseout ='this.className ="div_btn"'   onclick="showtest()">&nbsp;设置示例&nbsp;</div>
		<div class='div_btn' onmouseover ='this.className ="div_btn_hover"' onmouseout ='this.className ="div_btn"'   onclick="addnetmail()">&nbsp;添加邮箱&nbsp;</div>
		</td>
		</tr>
		<tr><td valign='top' colspan="2">
		<div style="height:100%;vertical-align: top;" >
		<table  class='tablerowStyleColor'  cellSpacing='0' cellPadding='3' width='100%' align='center' border='1' id='mailset'>
			<tr style="BACKGROUND-IMAGE: url('<%=contextPath %>/images/grid_images/fhbg.gif');">
				<td  class='tableTitle1' style="text-align: center;" nowrap="nowrap" width="5%">序号</td>
				<td  class='tableTitle1' style="text-align: center;">详细信息</td>
				<td  class='tableTitle1' style="text-align: center;" width="10%">操  作</td>
			</tr>
		</table>
		</div>
		</td>
	  </tr>
	</table>
	<br/>
</div>
<br/>
<center>
<btn:btn onclick="savenetmail();" imgsrc="../../images/fileokico.png" title="保存信息" ></btn:btn>
</center>
</body>
</html>