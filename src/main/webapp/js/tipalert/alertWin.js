var alertConfig ={
	bordercolor:"#7D98B8",// 提示窗口的边框颜?
	bgcolor : "#333", //屏蔽背景?
	msgbgcolor : "#eeeeee", // 提示内容的背景色
	msgcolor : "#000000", //提示内容字符颜色
	msgheight : 115,
	msgwidth : 320,
	count :3,//几秒钟自动关闭
	borderwidth :0//弹出边框宽度
};

function alertmsg(data, successevent,errorevent, par) {
	if (isBlank(par)) {
		par = window.top;
	}
	if(data==null){
		return;
	}
	var iWidth = par.document.body.clientWidth;
	var iHeight = par.document.body.clientHeight;
	var count = alertConfig.count;
	var bgObj = null;
	if(par != window){
		bgObj = par.document.createElement("div");
		bgObj.style.cssText = "position:absolute;left:0px;top:0px;width:" + iWidth + "px;height:" + iHeight + "px;filter:Alpha(Opacity=30);opacity:0.3;background-color:" + alertConfig.bgcolor + ";z-index:999998;";
		bgObj.setAttribute("id", "divbg");
		par.document.body.appendChild(bgObj);
	}
	var msgObj = par.document.createElement("div");
	msgObj.style.cssText = "position:absolute;color:" + alertConfig.msgcolor + ";font:12px '宋体';top:" + (iHeight - alertConfig.msgheight) / 2 + "px;left:" + (iWidth - alertConfig.msgwidth) / 2 + "px;width:" + alertConfig.msgwidth + "px;height:" + alertConfig.msgheight + "px;text-align:center;border:"+alertConfig.borderwidth+"px solid " + alertConfig.bordercolor + ";padding:0px;z-index:999999;";
	msgObj.setAttribute("id", "divmsg");
	par.document.body.appendChild(msgObj);
	
	//边框
	
	var table0 = par.document.createElement("table");
	msgObj.appendChild(table0);
	table0.style.cssText = "margin:0px;border:0px;padding:0px;height:100%;width:100%;";
	table0.cellSpacing = 0;
	table0.cellPadding = 0;
	table0.border = 0;
	
	var tr0 = table0.insertRow( - 1);
	tr0.style.cssText ="height:25px;";
	var td0_1 = tr0.insertCell( - 1);
	td0_1.style.cssText ="width:4px;background:url("+formStylePath.getImagePath()+"title_bg_left.gif) no-repeat;";
	
	var td0_2 = tr0.insertCell( - 1);
	td0_2.style.cssText ="padding-left:10px;color:#ffffff;width:"+(alertConfig.msgwidth-8)+"px;background:url("+formStylePath.getImagePath()+"title_bg_center.gif) repeat-x;";
	td0_2.innerHTML ="<nobr><b style='float:left'>操作提示</b></nobr>";
	
	var closediv = par.document.createElement("div");
	td0_2.appendChild(closediv);
	closediv.style.cssText = "font-size:12px;float:right;padding-right:8px;";
	closediv.innerHTML = count;
	
	var td0_3 = tr0.insertCell( - 1);
	td0_3.style.cssText ="width:4px;background:url("+formStylePath.getImagePath()+"title_bg_right.gif) no-repeat;";
	var tr01 = table0.insertRow( - 1);
	var txtheight = alertConfig.msgheight-25-3;
	tr01.style.cssText ="height:"+txtheight+"px;";
	
	
	var td01_1 = tr01.insertCell( - 1);
	td01_1.style.cssText ="width:4px;background:url("+formStylePath.getImagePath()+"win_l.gif) repeat-y left;";
	
	var td01_2 = tr01.insertCell( - 1);
	//====内容======
	var table = par.document.createElement("table");
	td01_2.appendChild(table);
	table.style.cssText = "margin:0px;border:0px;padding:0px;height:100%;width:100%;";
	table.cellSpacing = 0;
	table.cellPadding = 0;
	table.border = 0;
	
	var tr = table.insertRow( - 1);
	var td1_1 = tr.insertCell( - 1);
	td1_1.style.cssText = "cursor: default;vertical-align: middle;width:60px;text-align:center;padding:0px;margin:0px;font:bold 12px '宋体';color:" + alertConfig.msgcolor + ";background-color:" + alertConfig.msgbgcolor + ";";
	var imgobj ="";
	var mess = "";
	
	if(typeof(data)=="string"){
		imgobj = "<img src='"+formStylePath.getImagePath()+"alertwarning.png' border='0'/>";
		mess = data;
	}else{
		mess = data.message;
		if(data.success){
			imgobj = "<img src='"+formStylePath.getImagePath()+"savesuccess.png' border='0'/>";
		}else{
			imgobj = "<img src='"+formStylePath.getImagePath()+"saveerror.png' border='0'/>";
		}
	}
	td1_1.innerHTML = imgobj;
	
	var td1_2 = tr.insertCell( - 1);
	td1_2.style.cssText = "line-height:20px;cursor: default;vertical-align: middle;text-align:left;padding-left:8px;margin:0px;font-size:12px '宋体';color:" + alertConfig.msgcolor + ";background-color:" + alertConfig.msgbgcolor + ";padding-right:8px;";
	td1_2.innerHTML = mess;
	
	var tr2 = table.insertRow( - 1);
	tr2.style.cssText ="height:26px";
	var td2_1 = tr2.insertCell( - 1);
	td2_1.colSpan = 2;
	td2_1.style.cssText = "border-top:1px solid #C4DDE9;cursor: default;width:100%;text-align:right;padding:0px;margin:0px;font:12px '宋体';color:#666;background-color:#EEF7FE;padding-right:8px;";
	td2_1.innerHTML = "<font color='#d65500'>"+count+"</font>&nbsp;秒后自动关闭,或点击任意处关闭";
	
	//=================
	var td01_3 = tr01.insertCell( - 1);
	td01_3.style.cssText ="width:4px;background:url("+formStylePath.getImagePath()+"win_r.gif) repeat-y right;";
	
	
	var tr02 = table0.insertRow( - 1);
	tr02.style.cssText ="height:3px;";
	var td02_1 = tr02.insertCell( - 1);
	td02_1.style.cssText ="width:4px;background:url("+formStylePath.getImagePath()+"win_lb.gif) no-repeat;";
	var td02_2 = tr02.insertCell( - 1);
	td02_2.style.cssText ="background:url("+formStylePath.getImagePath()+"win_b.gif) repeat-x;";
	var td02_3 = tr02.insertCell( - 1);
	td02_3.style.cssText ="width:4px;background:url("+formStylePath.getImagePath()+"win_rb.gif) no-repeat;";
	
	
	msgObj.onclick = function() {
		par.document.body.removeChild(msgObj);
		if(bgObj!=null){
			par.document.body.removeChild(bgObj);
		}
		if (isNotBlank(successevent)) {
			if(data.success||typeof(data)=="string"){
				eval(successevent);
			}
		}
		if (isNotBlank(errorevent)) {
			if(!data.success){
				eval(errorevent);
			}
		}
	}
	
	var t = window.setInterval(function(){try{
			if(count<=1){
				if(msgObj!=null){
					par.document.body.removeChild(msgObj);
				}
				if(bgObj!=null){
					par.document.body.removeChild(bgObj);
				}
				if (isNotBlank(successevent)) {
					if(data.success||typeof(data)=="string"){
						eval(successevent);
					}
				}
				if (isNotBlank(errorevent)) {
					if(!data.success){
						eval(errorevent);
					}
				}
				window.clearInterval(t);
			}else{
				count--;
				td2_1.innerHTML = "<font color='#d65500'>"+count+"</font>&nbsp;秒后自动关闭,或点击任意处关闭";
				closediv.innerHTML = count;
			}
		}catch(e){}},1000);
}


function confirmmsg(msg,okevent,cancelevent,par) {
	if (isBlank(par)) {
		par = window.top;	//将页面级别升至顶
	}
	if(msg==null||msg.length==0){
		return;
	}
	var iWidth = par.document.body.clientWidth;
	var iHeight = par.document.body.clientHeight;
	var bgObj = null;
	if(par!=window){
		bgObj = par.document.createElement("div");
		bgObj.style.cssText = "position:absolute;left:0px;top:0px;width:" + iWidth + "px;height:" + iHeight + "px;filter:Alpha(Opacity=30);opacity:0.3;background-color:" + alertConfig.bgcolor + ";z-index:999998;";
		bgObj.setAttribute("id", "divbg");
		par.document.body.appendChild(bgObj);
	}
	var msgObj = par.document.createElement("div");
	msgObj.style.cssText = "position:absolute;color:" + alertConfig.msgcolor + ";font:12px '宋体';top:" + (iHeight - alertConfig.msgheight) / 2 + "px;left:" + (iWidth - alertConfig.msgwidth) / 2 + "px;width:" + alertConfig.msgwidth + "px;height:" + alertConfig.msgheight + "px;text-align:center;border:"+alertConfig.borderwidth+"px solid " + alertConfig.bordercolor + ";padding:0px;z-index:999999;";
	msgObj.setAttribute("id", "divmsgcon");
	
	par.document.body.appendChild(msgObj);
	
	//边框
	var table0 = par.document.createElement("table");
	msgObj.appendChild(table0);
	table0.style.cssText = "margin:0px;border:0px;padding:0px;height:100%;width:100%;";
	table0.cellSpacing = 0;
	table0.cellPadding = 0;
	table0.border = 0;
	
	var tr0 = table0.insertRow( - 1);
	tr0.style.cssText ="height:25px;";
	var td0_1 = tr0.insertCell( - 1);
	td0_1.style.cssText ="width:4px;background:url("+formStylePath.getImagePath()+"title_bg_left.gif) no-repeat;";
	
	var td0_2 = tr0.insertCell( - 1);
	td0_2.style.cssText ="padding-left:10px;color:#ffffff;width:"+(alertConfig.msgwidth-8)+"px;background:url("+formStylePath.getImagePath()+"title_bg_center.gif) repeat-x;";
	td0_2.innerHTML ="<b style='float:left'>操作提示</b>";
	
	var closediv = par.document.createElement("div");
	td0_2.appendChild(closediv);
	
	closediv.style.cssText = "font-size:13px;width:10px;float:right;padding-right:5px;font-weight: bold;cursor: default;";
	closediv.innerHTML = "×";
	closediv.title="关闭";
	closediv.onmouseover=function(){
		this.style.color='#214079';
	}
	closediv.onmouseout=function(){
		this.style.color='#ffffff';
	}
	closediv.onclick =function(){
		par.document.body.removeChild(msgObj);
		if(bgObj!=null){
			par.document.body.removeChild(bgObj);
		}
		if (isNotBlank(cancelevent)) {
			eval(cancelevent);
		}
	}
	
	var td0_3 = tr0.insertCell( - 1);
	td0_3.style.cssText ="width:4px;background:url("+formStylePath.getImagePath()+"title_bg_right.gif) no-repeat;";
	
	var tr01 = table0.insertRow( - 1);
	var txtheight = alertConfig.msgheight-25-3;
	tr01.style.cssText ="height:"+txtheight+"px;";
	
	
	var td01_1 = tr01.insertCell( - 1);
	td01_1.style.cssText ="width:4px;background:url("+formStylePath.getImagePath()+"win_l.gif) repeat-y left;";
	
	var td01_2 = tr01.insertCell( - 1);
	//====内容======
	
	var table = par.document.createElement("table");
	td01_2.appendChild(table);
	table.style.cssText = "margin:0px;border:0px;padding:0px;height:100%;width:100%;";
	table.cellSpacing = 0;
	table.cellPadding = 0;
	table.border = 0;
	var tr = table.insertRow( - 1);
	var td1_1 = tr.insertCell( - 1);
	td1_1.style.cssText = "cursor: default;vertical-align: middle;width:60px;text-align:center;padding:0px;margin:0px;font:bold 12px '宋体';color:" + alertConfig.msgcolor + ";background-color:" + alertConfig.msgbgcolor + ";";
	
	var imgobj = "<img src='"+formStylePath.getImagePath()+"alertconfrim.png' border='0'/>";
	td1_1.innerHTML = imgobj;
	
	var td1_2 = tr.insertCell( - 1);
	td1_2.style.cssText = "line-height:20px;cursor: default;vertical-align: middle;text-align:left;padding-left:8px;margin:0px;font-size:12px '宋体';color:" + alertConfig.msgcolor + ";background-color:" + alertConfig.msgbgcolor + ";padding-right:8px;";
	td1_2.innerHTML = msg;
	
	var tr2 = table.insertRow( - 1);
	tr2.style.cssText ="height:26px";
	var td2_1 = tr2.insertCell( - 1);
	td2_1.colSpan = 2;
	td2_1.style.cssText = "border-top:1px solid #C4DDE9;cursor: default;width:100%;text-align:right;padding:3px;padding-right:8px;margin:0px;font:12px '宋体';color:#666;background-color:EEF7FE;";
	
	var oklink=par.document.createElement("a");
	var cancellink = par.document.createElement("a");
	var span = par.document.createElement("span");
	
	
	td2_1.appendChild(oklink);
	td2_1.appendChild(span);
	td2_1.appendChild(cancellink);
	oklink.onfocus = function(){
		oklink.className = "atFocus";
	}
	oklink.onblur = function(){
		oklink.className = "at";
	}
	oklink.id = "oklinkid";
	oklink.className = "at";
	oklink.style.cssText = "cursor: default;";
	oklink.href ="javascript:void(0)";
	oklink.innerHTML = "&nbsp;确 定&nbsp;";
	
	span.innerHTML ="&nbsp;&nbsp;";
	cancellink.onfocus = function(){
		cancellink.className = "atFocus";
	}
	cancellink.onblur = function(){
		cancellink.className = "at";
	}
	cancellink.id = "cancellinkid";
	cancellink.className = "at";
	cancellink.style.cssText = "cursor: default;";
	cancellink.href ="javascript:void(0)";
	cancellink.innerHTML ="&nbsp;取 消&nbsp;";
	cancellink.focus();
	oklink.onclick = function() {
		par.document.body.removeChild(msgObj);
		if(bgObj!=null){
			par.document.body.removeChild(bgObj);
		}
		if (isNotBlank(okevent)) {
			eval(okevent);
		}
	}
	
	cancellink.onclick = function() {
		par.document.body.removeChild(msgObj);
		if(bgObj!=null){
			par.document.body.removeChild(bgObj);
		}
		if (isNotBlank(cancelevent)) {
			eval(cancelevent);
		}
	}
	
	
	//绑定esc和左右快捷键
	$(msgObj).bind('keydown.esc',function (evt){
		par.document.body.removeChild(msgObj);
		if(bgObj!=null){
			par.document.body.removeChild(bgObj);
		}
		if (isNotBlank(cancelevent)) {
			eval(cancelevent);
		}
		return false; 
	});
	
	$(msgObj).bind('keydown.right',function (evt){
		cancellink.focus();
		return false; 
	});
	
	$(msgObj).bind('keydown.left',function (evt){
		oklink.focus();
		return false; 
	});
	
	
	//=================
	var td01_3 = tr01.insertCell( - 1);
	td01_3.style.cssText ="width:4px;background:url("+formStylePath.getImagePath()+"win_r.gif) repeat-y right;";
	
	
	var tr02 = table0.insertRow( - 1);
	tr02.style.cssText ="height:3px;";
	var td02_1 = tr02.insertCell( - 1);
	td02_1.style.cssText ="width:4px;background:url("+formStylePath.getImagePath()+"win_lb.gif) no-repeat;";
	var td02_2 = tr02.insertCell( - 1);
	td02_2.style.cssText ="background:url("+formStylePath.getImagePath()+"win_b.gif) repeat-x;";
	var td02_3 = tr02.insertCell( - 1);
	td02_3.style.cssText ="width:4px;background:url("+formStylePath.getImagePath()+"win_rb.gif) no-repeat;";
}

function confirmmsgAndTitle(msg,okevent,oktitle,cancelevent,canceltitle,par) {
	if (isBlank(par)) {
		par = window.top;
	}
	if(msg==null||msg.length==0){
		return;
	}
	var iWidth = par.document.body.clientWidth;
	var iHeight = par.document.body.clientHeight;
	var bgObj = null;
	if(par!=window){
		bgObj = par.document.createElement("div");
		bgObj.style.cssText = "position:absolute;left:0px;top:0px;width:" + iWidth + "px;height:" + iHeight + "px;filter:Alpha(Opacity=30);opacity:0.3;background-color:" + alertConfig.bgcolor + ";z-index:999998;";
		bgObj.setAttribute("id", "divbg");
		par.document.body.appendChild(bgObj);
	}
	var msgObj = par.document.createElement("div");
	msgObj.style.cssText = "position:absolute;color:" + alertConfig.msgcolor + ";font:12px '宋体';top:" + (iHeight - alertConfig.msgheight) / 2 + "px;left:" + (iWidth - alertConfig.msgwidth) / 2 + "px;width:" + alertConfig.msgwidth + "px;height:" + alertConfig.msgheight + "px;text-align:center;border:"+alertConfig.borderwidth+"px solid " + alertConfig.bordercolor + ";padding:0px;z-index:999999;";
	msgObj.setAttribute("id", "divmsgcon");
	par.document.body.appendChild(msgObj);
	
	//边框
	
	var table0 = par.document.createElement("table");
	msgObj.appendChild(table0);
	table0.style.cssText = "margin:0px;border:0px;padding:0px;height:100%;width:100%;";
	table0.cellSpacing = 0;
	table0.cellPadding = 0;
	table0.border = 0;
	
	var tr0 = table0.insertRow( - 1);
	tr0.style.cssText ="height:25px;";
	var td0_1 = tr0.insertCell( - 1);
	td0_1.style.cssText ="width:4px;background:url("+formStylePath.getImagePath()+"title_bg_left.gif) no-repeat;";
	
	var td0_2 = tr0.insertCell( - 1);
	td0_2.style.cssText ="padding-left:10px;color:#ffffff;width:"+(alertConfig.msgwidth-8)+"px;background:url("+formStylePath.getImagePath()+"title_bg_center.gif) repeat-x;";
	td0_2.innerHTML ="<b style='float:left'>操作提示</b>";
	
	var closediv = par.document.createElement("div");
	td0_2.appendChild(closediv);
	
	closediv.style.cssText = "font-size:13px;width:10px;float:right;padding-right:5px;font-weight: bold;cursor: default;";
	closediv.innerHTML = "×";
	closediv.title="关闭";
	closediv.onmouseover=function(){
		this.style.color='#214079';
	}
	closediv.onmouseout=function(){
		this.style.color='#ffffff';
	}
	closediv.onclick =function(){
		par.document.body.removeChild(msgObj);
		if(bgObj!=null){
			par.document.body.removeChild(bgObj);
		}
		if (isNotBlank(cancelevent)) {
			eval(cancelevent);
		}
	}
	
	var td0_3 = tr0.insertCell( - 1);
	td0_3.style.cssText ="width:4px;background:url("+formStylePath.getImagePath()+"title_bg_right.gif) no-repeat;";
	
	var tr01 = table0.insertRow( - 1);
	var txtheight = alertConfig.msgheight-25-3;
	tr01.style.cssText ="height:"+txtheight+"px;";
	
	
	var td01_1 = tr01.insertCell( - 1);
	td01_1.style.cssText ="width:4px;background:url("+formStylePath.getImagePath()+"win_l.gif) repeat-y left;";
	
	var td01_2 = tr01.insertCell( - 1);
	//====内容======
	
	var table = par.document.createElement("table");
	td01_2.appendChild(table);
	table.style.cssText = "margin:0px;border:0px;padding:0px;height:100%;width:100%;";
	table.cellSpacing = 0;
	table.cellPadding = 0;
	table.border = 0;
	var tr = table.insertRow( - 1);
	var td1_1 = tr.insertCell( - 1);
	td1_1.style.cssText = "cursor: default;vertical-align: middle;width:60px;text-align:center;padding:0px;margin:0px;font:bold 12px '宋体';color:" + alertConfig.msgcolor + ";background-color:" + alertConfig.msgbgcolor + ";";
	
	var imgobj = "<img src='"+formStylePath.getImagePath()+"alertconfrim.png' border='0'/>";
	td1_1.innerHTML = imgobj;
	
	var td1_2 = tr.insertCell( - 1);
	td1_2.style.cssText = "line-height:20px;cursor: default;vertical-align: middle;text-align:left;padding-left:8px;margin:0px;font-size:12px '宋体';color:" + alertConfig.msgcolor + ";background-color:" + alertConfig.msgbgcolor + ";padding-right:8px;";
	td1_2.innerHTML = msg;
	
	var tr2 = table.insertRow( - 1);
	tr2.style.cssText ="height:26px";
	var td2_1 = tr2.insertCell( - 1);
	td2_1.colSpan = 2;
	td2_1.style.cssText = "border-top:1px solid #C4DDE9;cursor: default;width:100%;text-align:right;padding:3px;padding-right:8px;margin:0px;font:12px '宋体';color:#666;background-color:EEF7FE;";
	
	var oklink=par.document.createElement("a");
	var cancellink = par.document.createElement("a");
	var span = par.document.createElement("span");
	
	
	td2_1.appendChild(oklink);
	td2_1.appendChild(span);
	td2_1.appendChild(cancellink);
	
	oklink.onfocus = function(){
		oklink.className = "atFocus";
	}
	oklink.onblur = function(){
		oklink.className = "at";
	}
	oklink.id = "oklinkid";
	oklink.className = "at";
	oklink.style.cssText = "cursor: pointer;";
	oklink.href ="javascript:void(0)";
	oklink.innerHTML = oktitle;
	oklink.focus();
	span.innerHTML ="&nbsp;&nbsp;";
	
	cancellink.onfocus = function(){
		cancellink.className = "atFocus";
	}
	cancellink.onblur = function(){
		cancellink.className = "at";
	}
	cancellink.id = "cancellinkid";	
	cancellink.className = "at";
	cancellink.style.cssText = "cursor: pointer;";
	cancellink.href ="javascript:void(0)";
	cancellink.innerHTML = canceltitle;
	
	oklink.onclick = function() {
		par.document.body.removeChild(msgObj);
		if(bgObj!=null){
			par.document.body.removeChild(bgObj);
		}
		if (isNotBlank(okevent)) {
			eval(okevent);
		}
	}
	
	cancellink.onclick = function() {
		par.document.body.removeChild(msgObj);
		if(bgObj!=null){
			par.document.body.removeChild(bgObj);
		}
		if (isNotBlank(cancelevent)) {
			eval(cancelevent);
		}
	}
	
	//绑定左右快捷键
	$(msgObj).bind('keydown.right',function (evt){
		cancellink.focus();
		return false; 
	});
	
	$(msgObj).bind('keydown.left',function (evt){
		oklink.focus();
		return false; 
	});
	
	
	//=================
	var td01_3 = tr01.insertCell( - 1);
	td01_3.style.cssText ="width:4px;background:url("+formStylePath.getImagePath()+"win_r.gif) repeat-y right;";
	
	
	var tr02 = table0.insertRow( - 1);
	tr02.style.cssText ="height:3px;";
	var td02_1 = tr02.insertCell( - 1);
	td02_1.style.cssText ="width:4px;background:url("+formStylePath.getImagePath()+"win_lb.gif) no-repeat;";
	var td02_2 = tr02.insertCell( - 1);
	td02_2.style.cssText ="background:url("+formStylePath.getImagePath()+"win_b.gif) repeat-x;";
	var td02_3 = tr02.insertCell( - 1);
	td02_3.style.cssText ="width:4px;background:url("+formStylePath.getImagePath()+"win_rb.gif) no-repeat;";
}