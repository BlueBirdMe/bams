	/*tips*/
	function showonlineDiv(){
		var pdiv=document.getElementById("onlineDesktopDiv");
		var pfrm = document.getElementById("onlinefrm");
		var thei = document.getElementById("onlineimg").clientHeight;
	    pdiv.style.bottom = thei+10;
	    pdiv.style.right = 18;
	    pdiv.style.display="";
	    pfrm.src = Sys.getProjectPath()+"/erp/desktop/desktop_online.jsp";
	}
	
	function hiddenlineDiv(){
		var pfrm = document.getElementById("onlinefrm");
		var pdiv=document.getElementById("onlineDesktopDiv");
		pdiv.style.display="none";
		pfrm.src ="";
	}
	
	function selectcmsgbox(type,obj){
		if(obj==null){
			return;
		}
		getdivTitleByType(type,obj);
		getdivContentByType(type);
		var pdiv=document.getElementById("msgboxtitdiv");
		var tt = obj;
	    var thei = tt.clientHeight;    //TT控件本身的高
	    pdiv.style.bottom = thei+1;
	    pdiv.style.right = 18;
	    
	    pdiv.style.display="";
	}
	
	var childArray = new Array();
	childArray[0] = "msgboxtitdiv";
	childArray[1] = "msgboxtitTable";
	childArray[2] = "msgboxtitimg";
	childArray[3] = "boxtitlediv";
	childArray[4] = "boxdivcontent";
	childArray[5] = "boxdivcontenttd";
	childArray[6] = "boxdivprocess";
	childArray[7] = "cancelshanshuo";
	childArray[8] = "showallmsgbox";
	
	function hiddenmsgbox(){
		var div = document.getElementById("msgboxtitdiv");
		var event  = window.event || event;
		var tobj = event.toElement ? event.toElement: event.relatedTarget;
		
		for(var i=0;i<childArray.length;i++){
			var cobj = document.getElementById(childArray[i]);
			cobj.onmouseout = function(){
				hiddenmsgbox();
			}
			if(cobj == tobj) return;
		}
		var tbrows = document.getElementById("msgboxtitTable").rows;
		for(var j=0;j<tbrows.length;j++){
			tbrows[j].onmouseout = function(){
				hiddenmsgbox();
			}
			if(tbrows[j] == tobj) return;
			var cls = tbrows[j].cells;
			for(var h=0;h<cls.length;h++){
				cls[h].onmouseout = function(){
					hiddenmsgbox();
				}
				if(cls[h] == tobj) return;
			}
		}
		var tdchilds = document.getElementById("boxdivcontenttd").childNodes;
		for(var i=0;i<tdchilds.length;i++){
			tdchilds[i].onmouseout = function(){
				hiddenmsgbox();
			}
			if(tdchilds[i] == tobj) return;
		}
		
		var contentChilds = document.getElementById("boxdivcontent").childNodes;
		for(var i=0;i<contentChilds.length;i++){
			contentChilds[i].onmouseout = function(){
				hiddenmsgbox();
			}
			if(contentChilds[i] == tobj) return;
		}
		
		div.style.display="none";;
	}
	
	function closemsgboxDivTips(){
		var div = document.getElementById("msgboxtitdiv");
		div.style.display = "none";
	}
	
	function getdivTitleByType(type,obj){
		var tit = document.getElementById("boxtitlediv");
		var img = document.getElementById("msgboxtitimg");
		img.src =obj.src;
		tit.innerHTML = obj.title;
	}
	
	function getdivContentByType(type){
		var content = document.getElementById("boxdivcontent");
		var sh = document.getElementById("cancelshanshuo");
		var fd = document.getElementById("showallmsgbox");
		if(type=='online'){
			var oimg = document.getElementById("onlineimg");
			fd.onclick = function(){
				closemsgboxDivTips();
				showonlineDiv();
			}
			oimg.onclick = function(){
				closemsgboxDivTips();
				showonlineDiv();
			}
			sh.style.display="none";
			fd.style.display="";
			
			jQuery.get(Sys.getProjectPath()+"/erp/desktop/getOnlineCount.jsp",
		    function(str){
				content.innerHTML = "<label style='float:left;margin-left: 5px'>当前系统在线人员</label><label style='float:right;margin-right: 5px'>["+str+"]</label>";
		  	});
			
		}else if(type == 'sms'){
			sh.style.display="";
			fd.style.display="";
			var simg = document.getElementById("smsimg");
			sh.onclick = function(){
				closemsgboxDivTips();
				cancelSms(simg);
			}
			fd.onclick = function(){
				closemsgboxDivTips();
				cancelSms(sms);
				showSms();
			}
		}else if(type == "approve"){
			sh.style.display="";
			fd.style.display="";
			var app = document.getElementById("approveimg");
			sh.onclick = function(){
				closemsgboxDivTips();
				cancelApprove(app);
			}
			fd.onclick = function(){
				closemsgboxDivTips();
				cancelApprove(app);
				showApprove();
			}
		}else if(type=='schtimer'){
			sh.style.display="";
			fd.style.display="";
			var timg = document.getElementById("timerimg");
			sh.onclick = function(){
				closemsgboxDivTips();
				cancelTimer(timg);
			}
			fd.onclick = function(){
				closemsgboxDivTips();
				cancelTimer(timg);
				showSchDetail();
			}
		}else if(type == 'mail'){
			sh.style.display="";
			fd.style.display="none";
			var mimg = document.getElementById("mailimg");
			sh.onclick = function(){
				closemsgboxDivTips();
				cancelMail(mimg);
			}
		}
	}
	
	function audioStart(id){
		var obj = document.getElementById(id);
		document.getElementById("audiodiv").innerHTML="<embed src='"+obj.href+"' hidden=true autostart=true loop=false>";
	}
	
	var smsTimer = null;
	
	function getTimerMsg(){
		getSmsMap();
		getApproveCount();
		getScherTimerCount();
		getMailCount();
		
		//收取邮件
		getNetMailByServer();
	}
	
	//注册事件
	if (document.all) {
		window.attachEvent("onload", getTimerMsg);
	} else {
		window.addEventListener("load", getTimerMsg, false);
	}
	//=======收取外部邮件===================
	function getNetMailByServer(){
		if(setDefaultPk!=null&&setDefaultPk>0){
			var ids = new Array();
			ids[0] = setDefaultPk;
			dwrMailService.getNetMailFormServer(ids,setNetMailServerTimer);
		}	
	}
	
	function setNetMailServerTimer(data){
		if(data.success){
			smsTimer = window.setTimeout("getNetMailByServer()", 10*1000*60);
		}
	}
	
	//========短信====================
	var sms = document.getElementById("smsimg");
	var tempstr = sms.src;
	var index = tempstr.lastIndexOf('.');
	var name = tempstr.substring(0, index);
	
	
	function getSmsMap(){
		dwrMoblieSmsService.getisreadSms(getresult);
	}
	
	//接受结果
	function getresult(data){
		if(data!=null&&data>0){
			sms.src = name+".gif";
			audioStart('smsaudio');
			sms.onclick=function(){
				closemsgboxDivTips();
				cancelSms(sms);
				showSms();
			};
			sms.onmouseover = function(){
				selectcmsgbox('sms',this);
				document.getElementById("boxdivcontent").innerHTML = "<label style='float:left;margin-left: 5px'>收到新的短信</label><label style='float:right;margin-right: 5px'>["+data+"]</label>";
			};
			sms.onmouseout = function(){
				hiddenmsgbox();
			}
		}else{
			cancelSms(sms);
		}
		smsTimer = window.setTimeout("getSmsMap()", 2*1000*60);
	}
	
	function cancelSms(smsObj){
		smsObj.src = name+".png";
		smsObj.onclick =function(){};
		smsObj.onmouseover = function(){};
		smsObj.onmouseout = function(){};
	}
	
	
	//======================审批=========================
	
	var app = document.getElementById("approveimg");
	var apptempstr = app.src;
	var appindex = apptempstr.lastIndexOf('.');
	var appname = apptempstr.substring(0, appindex);
	
	function getApproveCount(){
		dwrPersonalProcessService.listTaskTodo(null, setApproveResult);
	}
	
	function cancelApprove(appobj){
		appobj.src = appname+".png";
		appobj.onmouseover = function(){};
		appobj.onmouseout = function(){};
	}
	
	function setApproveResult(data){
		if(data.length>0){
			app.src = appname+".gif";
			audioStart('approveaudio');
			app.onmouseover = function(){
				selectcmsgbox('approve',this);
				createApproveRecord(data);
			};
			app.onmouseout = function(){
				hiddenmsgbox();
			}
		}else{
			cancelApprove(app);
		}
		smsTimer = window.setTimeout("getApproveCount()", calltimer);
	}
	
	function createApproveRecord(data){
		/**
		var str="";
		for(var i=0;i<data.length;i++){
			str+=data[i];
		}
		document.getElementById("boxdivcontent").innerHTML = str;
		**/
		
		document.getElementById("boxdivcontent").innerHTML = "<label style='float:left;margin-left: 5px'>待办任务</label><label style='float:right;margin-right: 5px'>["+data.length+"]</label>";
	}
	
	//===============定时提醒========================
	var schtimer = document.getElementById("timerimg");
	var schtempstr = schtimer.src;
	var schindex = schtempstr.lastIndexOf('.');
	var schname = schtempstr.substring(0, schindex);
	
	function getScherTimerCount(){
		dwrMoblieSmsService.getSchTimer(setTimerResult);
	}

	function setTimerResult(data){
		if(data!=null&&data>0){
			schtimer.src = schname+".gif";
			audioStart('timeraudio');
			schtimer.onclick=function(){
				closemsgboxDivTips();
				cancelTimer(schtimer);
				showSchDetail();
			};
			schtimer.onmouseover = function(){
				selectcmsgbox('schtimer',this);
				document.getElementById("boxdivcontent").innerHTML = "<label style='float:left;margin-left: 5px'>定时提醒</label><label style='float:right;margin-right: 5px'>["+data+"]</label>";
			};
			schtimer.onmouseout = function(){
				hiddenmsgbox();
			}
		}else{
			cancelTimer(schtimer);
		}
		smsTimer = window.setTimeout("getScherTimerCount()", 2*1000*60);
	}
	
	function cancelTimer(obj){
		obj.src = schname+".png";
		obj.onclick =function(){};
		obj.onmouseover = function(){};
		obj.onmouseout = function(){};
	}
	
	
	//=========================邮件==============================
	var mailimg = document.getElementById("mailimg");
	var mailtempstr = mailimg.src;
	var mailindex = mailtempstr.lastIndexOf('.');
	var mailname = mailtempstr.substring(0, mailindex);
	
	function getMailCount(){
		dwrMailService.getMailNoReadCountToString(setDefaultId,setMailResult);
	}
	
	//接受结果
	function setMailResult(data){
		if(data!=null&&data.length>0){
			mailimg.src = mailname+".gif";
			audioStart('mailaudio');
			mailimg.onmouseover = function(){
				selectcmsgbox('mail',this);
				createMail(data);
			};
			mailimg.onmouseout = function(){
				hiddenmsgbox();
			}
		}else{
			cancelMail(mailimg);
		}
		smsTimer = window.setTimeout("getMailCount()", 3*1000*60);
	}
	
	function cancelMail(obj){
		obj.src = mailname+".png";
		obj.onmouseover = function(){};
		obj.onmouseout = function(){};
	}
	
	function createMail(data){
		var str="";
		for(var i=0;i<data.length;i++){
			str+=data[i];
		}
		document.getElementById("boxdivcontent").innerHTML = str;
	}