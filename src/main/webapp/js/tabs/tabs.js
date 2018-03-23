var bNewFeed = 0; bGetFeed = false;
if(top != window){top.location = location;}
String.prototype.Trim = function () { return this.replace(/(^\s*)|(\s*$)/g, ""); }

var strReferer = 'False';
var NewMail = 0;
var oldNum = NewMail;
var isMSIE = (navigator.appName == "Microsoft Internet Explorer");
var isOpera = navigator.userAgent.indexOf('Opera') != -1;
var fuckAlexa = false;
var bFirst = true;

var mainPage = "center.jsp";	//默认主页
var CSKey;
var xmlhttp = false;
var delayCount = 0;

var OnlineFriend = 0;
var lUserId = 0;
var sel = (lUserId > 0)?1:0;
var refblock;
function onClubClick(id, str, url, isNode){
	MDIOpen(url);
}

function onclickH(e){return onclickHook(e ? e : methodleftfrm);}
function onclickME(e){return onclickHook(e ? e : window);}

var nowWindow = -1;
var nowStyle = "max";
var starTab = 0;
var tempTab = "";
var maxWindow = 0;
var ocs = new Array();
var ocs_tab = new Array();
var wl = 107;

function onresizeH(e){
	if (nowWindow == -1) return;
	var w = tabs.document.body.offsetWidth;
	var tabLists = tabs.document.getElementById("tabs").childNodes;
	var nowStyle1 = "";

	if (w < tabLists.length * wl){
		nowStyle1 = "min";
	} else {
		if (w > tabLists.length * 107) {
			nowStyle1 = "max";
		} else {
			nowStyle1 = "";
		}
		document.getElementById("tab_left").firstChild.className = "hidden";
		document.getElementById("tab_right").firstChild.className = "hidden";
	}

	var n = Math.floor(w / wl);
	var n1 = 0;
	for (var i = 0; i < tabLists.length; i++) {
		if (tabLists[i].id == "tab_" + nowWindow) {
			n1 = i;
			break;
		}
	}

	var tabStyle = tabs.document.styleSheets[0][(tabs.document.all) ? 'rules' : 'cssRules'];
	try {
		tabStyle[tabStyle.length - 1].style.width = Math.floor(w / n)  + "px";
		tabStyle[tabStyle.length - 2].style.width = (Math.floor(w / n) + w % n) + "px";
	}catch(e){}

	if (nowStyle1 == "min") {
		tempTab = starTab;
		if (starTab + n < n1 + 1) {
			for (var i = starTab; i < n1 + 1 - n; i++) {
				tabLists[i].style.display = "none";
				tabLists[i].className = "min";
			}
			starTab = n1 + 1 - n;
		} else if (starTab > tabLists.length - n && starTab > 0) {
			for (var i = tabLists.length - n; i <= starTab; i++) {
				tabLists[i].style.display = "";
				tabLists[i].className = "min";
			}
			starTab = tabLists.length - n;
		} else if (starTab > n1) {
			for (var i = n1; i < starTab; i++) {
				tabLists[i].style.display = "";
				tabLists[i].className = "min";
			}
			starTab = n1;
		}

		if(starTab != tempTab){tabLists[tempTab].className = (tabLists[tempTab].id == "tab_" + nowWindow) ? "min here" : "min";}
		tabLists[starTab].className = (tabLists[starTab].id == "tab_" + nowWindow) ? "min1 here" : "min1";

		document.getElementById("tab_left").firstChild.className = (starTab > 0) ? "" : "stop";
		document.getElementById("tab_right").firstChild.className = (starTab + n < tabLists.length) ? "" : "stop";
	}

	if (nowStyle != nowStyle1) {
		nowStyle = nowStyle1;
		for (var i = 0; i < tabLists.length; i++) {
			if (nowStyle1 != "min") if (tabLists[i].style.display != "")
				tabLists[i].style.display = "";

			if (nowStyle1 != "min" || (nowStyle1 == "min" && i != starTab))
				tabLists[i].className = (tabLists[i].id == "tab_" + nowWindow) ? nowStyle + " here" : nowStyle;
		}
		if (nowStyle != "min") starTab = 0;
	}
}

onresize = onresizeH;

function onLefttab()
{
	var tabLists = tabs.document.getElementById("tabs").childNodes;
	MDISwitch(((starTab > 0) ? tabLists[starTab - 1].id : tabLists[0].id).substr(4));
}

function onRighttab()
{
	var n = Math.floor(tabs.document.body.offsetWidth / wl);
	var tabLists = tabs.document.getElementById("tabs").childNodes;
	MDISwitch(((starTab + n + 1 > tabLists.length) ? tabLists[tabLists.length - 1].id : tabLists[starTab + n].id).substr(4));
}

function onTabDblClick(){
	setTimeout("MDIClose();", 0);
}

function MDISwitch(i)
{
	if(nowWindow == i)return false;

	var ow, nw;

	if(nowWindow != -1)
	{
		tabs.document.getElementById("tab_" + nowWindow).className = nowStyle;
		ow = mainframe.document.getElementById("mdi_" + nowWindow);

		ow.scrollPos = 0;
		if(ow.Attached && ow.contentWindow)if(ow.contentWindow.document)if(ow.contentWindow.document.body)
			ow.scrollPos = ow.contentWindow.document.body.scrollTop;

		ow.LastAccess = (new Date()).getTime();
		ow.style.display = "none";
	}

	nw = mainframe.document.getElementById("mdi_" + i);

	nw.style.display = "";

	try
	{
		if(nw.scrollPos)
			nw.contentWindow.scrollTo(0, nw.scrollPos);
	}catch(e){};

	nowWindow = i;
	tabs.document.getElementById("tab_" + nowWindow).className = nowStyle + " here";

	onresizeH();
	
	return false;
}

function MDIClose(){
	var i, f, la = 0, lw;

	if(nowWindow != -1)
	{
		tabs.document.getElementById("tabs").removeChild(tabs.document.getElementById("tab_" + nowWindow));
		f = mainframe.document.getElementById("mdi_" + nowWindow);
		try
		{
			if(f){
				if(f.contentDocument){
					f.contentDocument.location = 'about:blank';
				}else{
					eval("mainframe.mdi_" + nowWindow).document.location = 'about:blank';
				}
			}
		}catch(e){}
		mainframe.document.body.removeChild(mainframe.document.getElementById("mdi_" + nowWindow));

		for(i = 0; i < maxWindow; i ++)
		{
			if(nowWindow != i)
			{
				f = mainframe.document.getElementById("mdi_" + i)
				if(f)
				{
					if(la < f.LastAccess)
					{
						la = f.LastAccess;
						lw = i;
					}
				}
			}
		}

		nowWindow = -1;
		if(la){
			MDISwitch(lw);
		}
	}
	return false;
}

function closeOtherTab(){
	for(i = 0; i < maxWindow; i ++){
		if(nowWindow != i){
			MDICloseOther(i);
		}
	}
	hideTabMenu();
	onresizeH();
}

function MDICloseOther(windowID){
	if(tabs.document.getElementById("tab_" + windowID) != null)
	{
		tabs.document.getElementById("tabs").removeChild(tabs.document.getElementById("tab_" + windowID));
		var f = mainframe.document.getElementById("mdi_" + windowID);
		try
		{
			if(f){
				if(f.contentDocument){
					f.contentDocument.location = 'about:blank';
				}else{
					eval("mainframe.mdi_" + windowID).document.location = 'about:blank';
				}
			}
		}catch(e){}
		mainframe.document.body.removeChild(mainframe.document.getElementById("mdi_" + windowID));

	}
	return false;
}

function MDIRefresh()
{
	var f;
	var l;

	f = mainframe.document.getElementById("mdi_" + nowWindow);
	l = "";
	try
	{
		if(f)
			if(f.contentDocument)
				f.contentDocument.location = f.contentDocument.location;
			else eval("mainframe.mdi_" + nowWindow).document.location = eval("mainframe.mdi_" + nowWindow).document.location;
	}catch(e){}
}

function MDILoad(n, url){
	var f;
	var l;

	f = mainframe.document.getElementById("mdi_" + n);
	l = "";
	try{
		if(f)
			if(f.contentDocument){
				l = f.contentDocument.location = url;
			}else{
				l = eval("mainframe.mdi_" + n).document.location = url;
			}
	}catch(e){}
}

function MDIOpen(url, nActiveMode){
	if(mainframe.frames.length > 20){
		alert("提示：标签页面过多，请关闭部分页面！");
		return;
	}
	if(typeof nActiveMode == 'undefined'){
		nActiveMode = 1;
	}
	if(nowWindow == -1){
		nActiveMode = 1;
	}

	var i, f, l, l1, n;
	var url1 = url.toLowerCase();
	
	for(i = 0; i < maxWindow; i ++){
		f = mainframe.document.getElementById("mdi_" + i);
		l = "";
		l1 = "";
		try
		{
			if(f)
				if(f.contentDocument)
					l = '' + f.contentDocument.location;
				else l = '' + eval("mainframe.mdi_" + i).document.location;
		}catch(e){}

		n = l.indexOf('?');
		if(n!=-1)l1 = l.substr(0, n);

		// 新增tab和原有的tab地址是否相同，如果相同，则跳转到tab，不同，则新增tab
		if(l.toString().toLowerCase() == url1 || l1.toString().toLowerCase() == url1 || l.toString().toLowerCase().indexOf(url1) >0){
			if(nowWindow != i)
			{
				if(nActiveMode)
				{
					MDISwitch(i);
					if(nActiveMode == 2)
						setTimeout("mainframe.document.getElementById(\"mdi_" + i + "\").src=\"" + url + "\"",0);
				}
			}else{
				setTimeout("mainframe.document.getElementById(\"mdi_" + i + "\").src=\"" + url + "\"",0);
			}
			return;
		}
	}

	if(maxWindow > mainframe.frames.length){
		for(i = 0; i < maxWindow; i ++)
			if(!mainframe.document.getElementById("mdi_" + i))
				break;
	}else{
		i = maxWindow;
		maxWindow ++;

		ocs[i] = new Function("return onclickHook(mainframe.mdi_" + i + ");");
		ocs_tab[i] = new Function("MDISwitch(" + i + ");");
	}

	var nw = mainframe.document.createElement("iframe")
	nw.width = "100%"
	nw.height = "100%"
	nw.frameBorder = 0
	nw.scrolling = "auto"
	nw.id = "mdi_" + i;
	nw.LastAccess = (new Date()).getTime();
//	nw.Attached = true;
	nw.src = "about:blank";
	nw.style.display = "none";
	nw.refblock = refblock;

	if(nowWindow != -1){
		f = mainframe.document.getElementById("mdi_" + nowWindow);
		if(f.contentDocument)
			nw.referurl = f.contentDocument.location.href;
		else
			nw.referurl = eval("mainframe.mdi_" + nowWindow).document.location.href;
	}
	else{
		nw.referurl = '';
	}

	if(isMSIE){
		var ifHtml = nw.outerHTML;
		nw = mainframe.document.createElement("div")
		nw.id = "mdi_" + i;
		mainframe.document.body.appendChild(nw);

		mainframe.document.getElementById("mdi_" + i).outerHTML = ifHtml;
	}else{
		mainframe.document.body.appendChild(nw);
	}

	var td = tabs.document.createElement("li");
	td.className = nowStyle;
	//td.width = wl;
	td.id = "tab_" + i;
	td.onmousedown=ocs_tab[i];
	td.ondblclick = onTabDblClick;
	td.onmouseover = function () { this.className1 = this.className; if (this.className.indexOf("here") == -1) this.className += " over"; }
	td.onmouseout = function () { if (this.className.indexOf("here") == -1) { this.className = this.className1; } this.className1 = ""; }
	td.oncontextmenu = function (event){
		var e = event || this.ownerDocument.parentWindow.event;
		showTabMenu(e.clientX,e.clientY);
	}
	
	if (isOpera){
		td.innerHTML = "<a href='javascript:void(0);'>　加载中…</a>";
	}else{
		td.innerHTML = '<div class="ch_m_l"><div class="ch_m_r"><span>加载中…</span><a href="javascript:parent.MDIRefresh();void(0);" title="刷新" class="ch_f5">刷新</a><a href="javascript:parent.MDIClose();void(0);" title="关闭" class="ch_close">关闭</a></div></div>';
	}

	try {
		tabs.document.getElementById("tabs").insertBefore(td, tabs.document.getElementById("tab_" + nowWindow).nextSibling);
	} catch (e) {
		tabs.document.getElementById("tabs").appendChild(td);
	}

	if(nActiveMode){
		MDISwitch(i);
	}else{
		onresizeH();
	}
	setTimeout('MDILoad(' + i + ',"' + url + '");', 0);
}

function HtmlEncode(s){
	var s1;

	s1 = s.replace(new RegExp('&', 'g'), '&amp;');
	s1 = s1.replace(new RegExp('<', 'g'), '&lt;');
	s1 = s1.replace(new RegExp('>', 'g'), '&gt;');

	return s1;
}

function MDIonload(w, title){
	if(typeof title != 'undefined' && title){
//		var s = (new String(title)).replace(/(.*)_(.*博尔科技)$/ig, "$1");
		var s = (new String(title)).replace(/(.*)_(.*品互网络)$/ig, "$1");
		var l, n;

		l = 0;
		n = 0;
		while(l < 20 && n < s.length)
		{
			if(s.charCodeAt(n) > 128)
			{
				l ++;
				if(l == 20)break;
			}
			l ++;
			n ++;
		}

		if(n > 0){
			if(isOpera){
				tabs.document.getElementById("tab_" + w).innerHTML = "<a href='javascript:void(0);'>　" + HtmlEncode(s.substr(0, 8)) + "..</a>";
			}else{
				tabs.document.getElementById("tab_" + w).innerHTML = '<div class="ch_m_l"><div class="ch_m_r"><span>' + HtmlEncode(s.substr(0, 8)) + '</span><a href="javascript:parent.MDIRefresh();void(0);" title="刷新" class="ch_f5">刷新</a><a href="javascript:parent.MDIClose();void(0);" title="关闭" class="ch_close">关闭</a></div></div>';
			}
			tabs.document.getElementById("tab_" + w).title = s;
			mainframe.document.getElementById("mdi_" + w).Attached = true;
		};
	}
}

var oca = new Array();

function MDILoop(){
	if(fuckAlexa){
		try
		{
			var i, o, path;
	
			for(i = 0; i < oca.length; i ++)
			{
				o = oca[i];
				path = o.getAttribute("path");
				if(path)
				{
					o.href = path;
					o.target = "_blank";
					o.setAttribute("path", '');
				}
			}
	
			oca = new Array();
		}catch(e){}
	}else if(isMSIE && document.onclick && document.onclick!=onclickME){
		fuckAlexa = true;
	}

	var f, f1;

	for(i = 0; i < maxWindow; i ++)
	{
		try
		{
		f = mainframe.document.getElementById("mdi_" + i)
			if(f)
				if(f.contentDocument)
				{
					if(!f.contentDocument.clickhook)
					{
						f.contentDocument.onclick = onclickHook;
						f.contentDocument.clickhook = true;
						f.Attached = false;
					}
					if(!f.Attached)
						MDIonload(i, f.contentDocument.title);
				}else
				{
					f1 = eval("mainframe.mdi_" + i)
					if(f1)if(f1.document)
					{
						if(!f1.document.clickhook)
						{
							if(fuckAlexa && f1.document.onclick)
								f1.document.oldclick = f1.document.onclick;
							f1.document.onclick = ocs[i];
							f1.document.clickhook = true;
							f.Attached = false;
						}
						if(!f.Attached)
							MDIonload(i, f1.document.title);
					}
				}
		}catch(e){}
	}
}

setInterval("MDILoop()", 10);

function onclickStatHook(e, srcElement){
	var x = 0;
	var y = 0;
	var StatTag = "";
	var StatBlock = "";
	var CSKey,surl,elementtemp;
	var n = document.cookie.indexOf("SessionID");

	try {
		if((typeof(e.lATimes)=='undefined' && typeof(e.view.lATimes)=='undefined') || n == -1)
			return;
	}catch(e){return;}

	var IEorFF = (typeof(e.lATimes)!='undefined')?e.window.document:e.currentTarget;
	var Url =IEorFF.URL;

	CSKey = document.cookie.substr(n + 10, 16);

	if(srcElement.tagName.toLowerCase()=="input"){
		elementtemp = srcElement;
		while(elementtemp && elementtemp.tagName.toLowerCase()!="form" && elementtemp.parentNode != (e.document?e.document:e.currentTarget))
			elementtemp = elementtemp.parentNode;
		if(elementtemp.tagName.toLowerCase()=="form")
			surl = elementtemp.action ;
		else	surl  = "";
	}else{
		surl = srcElement.href.replace(";","").replace("#","");
	}
	while(srcElement && srcElement.parentNode != e.document && srcElement.tagName != "BODY"){
		if(srcElement.getAttribute("StatTag") && StatTag=="")
			StatTag = srcElement.getAttribute("StatTag");
		if(srcElement.getAttribute("StatBlock"))
			StatBlock += srcElement.getAttribute("StatBlock") + "_";
		srcElement = srcElement.parentNode;
	}
}

function onclickHook(e){
	return;
	try{
		var srcElement = e.target ? e.target : e.event.srcElement;
		while(srcElement && srcElement.tagName.toLowerCase() != "a" && srcElement.tagName.toLowerCase() != "area"  && srcElement.tagName.toLowerCase() != "input" && srcElement.parentNode != (e.document?e.document:e.currentTarget))
			srcElement = srcElement.parentNode;

		if((srcElement.tagName.toLowerCase() == "input" && srcElement.getAttribute("type")!="submit")||!srcElement||srcElement.parentNode==e.document)
		return true;

		if(srcElement.onclick){
		    if(srcElement.onclick.toString().indexOf("return") > -1){
		        return;
		    }
		}
		onclickStatHook(e, srcElement);
	
		var hrefUrl = srcElement.href;
		var path = srcElement.getAttribute("path");
		
		if(typeof hrefUrl != 'undefined'){
			if(!path){
				if(hrefUrl.substr(0, 11).toLowerCase() == "javascript:"){
					return true;
				}
				if(fuckAlexa && hrefUrl.search(/\.htm/i) > 0){
					if(e.document.oldclick){
						e.document.oldclick();
					}

					return true;
				}
				path = hrefUrl;
				hrefUrl = '';
			}else{
				if(path.substr(0, 7) != "http://" && path.substr(0, 1) != "/"){
					if(!e.location)
						path = this.location.pathname.substring(0, this.location.pathname.lastIndexOf("/") + 1) + path;
					else
						path = e.location.pathname.substring(0, e.location.pathname.lastIndexOf("/") + 1) + path;
				}
				if(path.substr(0, 7) != "http://" && path.substr(0, 1) == "/")
				{
					path = "http://" + location.host + path;
				}

			}

			if(fuckAlexa && hrefUrl == '')
			{
				srcElement.href = 'javascript:void(0);';
				srcElement.target = "_self";
				srcElement.setAttribute("path", path);
				oca.push(srcElement);
			}
			
			MDIOpen(path);
			return false;
		}
	}catch(e){}

	return true;
}

//获取当前tab标签页对象
function getMDI(){
	return eval("mainframe.mdi_" + nowWindow);
}

//获取当前tab标签页序列
function getNowWindow(){
	return nowWindow;
}

//获取当前tab标签页frame
function getNowWindowFrame(){
	var nowfrm = "mdi_" + nowWindow;
	return nowfrm;
}

//根据tab标签页序列获取对象
function getMDIFrame(frmname){
	return eval("mainframe.mdi_" + frmname);
}


//显示tab菜单
function showTabMenu(x,y){
	var obj = document.getElementById("tabFrame");
	var rect = obj.getBoundingClientRect();     
	$("#tabmenu").css({"left":x+rect.left-3,"top":y+rect.top-3}).show();
}

function hideTabMenu(){
	$("#tabmenu").hide();
}

$(function(){
	var str = '<div id="tabmenu" onmouseleave="hideTabMenu();">'
			+	'<ul>'
			+		'<li class="closeOtherTab" onclick="closeOtherTab();" title="关闭其他选项卡">'
			+			'关闭其他'
			+		'</li>'
			+	'</ul>'
			+  '</div>';
	$(str).appendTo("body");
})
