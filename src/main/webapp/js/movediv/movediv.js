var CoolDrag = {
	obj: null,
	//目标对象
	cloneobj: null,
	//拖动对象
	container: null,
	//容器
	dragged: false,
	//拖动标志
	shadow: null,
	//当前公司编码
	companycode:null,
	//当前用户
	userInfo:null,
	//阴影
	init: function(id,companyCode,userInfo) {
		CoolDrag.container = $(id);
		CoolDrag.companycode = companyCode;
		CoolDrag.userInfo = userInfo;
		var cooldrag = CoolDrag.read(companyCode+"_"+userInfo+"_cooldrag"+document.URL);
		if (cooldrag != "") { //读取cookie
			var subcontainer = cooldrag.split("|");
			for (var i = 0; i < subcontainer.length; i++) {
				var subcontainerItem = subcontainer[i].split(":");
				if ($(subcontainerItem[0])) {
					var items = subcontainerItem[1].split(",");
					for (var j = 0; j < items.length; j++) {
						if ($(items[j])) $(subcontainerItem[0]).appendChild($(items[j]));
					}
				}
			}
		}
		cleanWhitespace(CoolDrag.container) //清除空白节点
		var collection = CoolDrag.container.getElementsByTagName("DIV");
		for (var i = 0; i < collection.length; i++) {
			if (collection[i].className == "dragLayer") {
				var o = collection[i].firstChild;
				SavedObject.push([o.parentNode.id, 1, o.parentNode.style.height]);
				o.onmousedown = CoolDrag.start;
			}
		}
		document.onmousemove = CoolDrag.drag;
		document.onmouseup = CoolDrag.end;
	},
	start: function(e) {
		if (!e) e = window.event;
		var obj = getT(e);
		if (obj.className == "min") {
			CoolDrag.min(e);
			return;
		}else if(obj.className == "more"){
			return;
		}else if(obj.className == "dragHeader2"){
			return;
		}else if(obj.className == "add"){
			return;
		}else {
			if (obj.className != "dragHeader") obj = obj.parentNode;
		}
		CoolDrag.dragged = true;
		CoolDrag.obj = obj.parentNode;
		CoolDrag.cloneobj = CoolDrag.obj.cloneNode(true);
		document.body.appendChild(CoolDrag.cloneobj);
		CoolDrag.shadow = document.createElement("DIV");
		document.body.appendChild(CoolDrag.shadow);
		with(CoolDrag.cloneobj.style) {
			position = "absolute";
			zIndex = 1000;
			left = getRealLeft(CoolDrag.obj) + "px";
			top = getRealTop(CoolDrag.obj) + "px";
		}
		with(CoolDrag.shadow.style) {
			position = "absolute";
			zIndex = 999;
			left = getRealLeft(CoolDrag.obj) + 2 + "px";
			top = getRealTop(CoolDrag.obj) + 2 + "px";
			width = CoolDrag.obj.offsetWidth + "px";
			height = CoolDrag.obj.offsetHeight + "px";
			backgroundColor = "#ededed";
			if (navigator.userAgent.indexOf("Gecko") != -1) MozOpacity = "0.5";
			else if (navigator.userAgent.indexOf("MSIE") != -1) filter = "alpha(opacity=50)";
		}
		CoolDrag.cloneobj.initMouseX = getMouseX(e);
		CoolDrag.cloneobj.initMouseY = getMouseY(e);
		CoolDrag.cloneobj.initoffsetL = getRealLeft(CoolDrag.obj);
		CoolDrag.cloneobj.initoffsetY = getRealTop(CoolDrag.obj); //change style
		CoolDrag.cloneobj.firstChild.className = "dragHeader_over";
		CoolDrag.cloneobj.firstChild.style.width = CoolDrag.obj.offsetWidth + "px";
		CoolDrag.cloneobj.style.width = CoolDrag.obj.offsetWidth + "px";
		CoolDrag.cloneobj.className = "dragLayer_over";
		CoolDrag.obj.className = "clone_dragLayer_over";
	},
	drag: function(e) {  
		if (!CoolDrag.dragged || CoolDrag.obj == null) return;
		if (!e) e = window.event;
		var currenX = getMouseX(e);
		var currenY = getMouseY(e);
		if (CoolDrag.cloneobj.initoffsetL + currenX - CoolDrag.cloneobj.initMouseX > getRealLeft(CoolDrag.container)) CoolDrag.cloneobj.style.left = (CoolDrag.cloneobj.initoffsetL + currenX - CoolDrag.cloneobj.initMouseX) + "px";
		else CoolDrag.cloneobj.style.left = getRealLeft(CoolDrag.container) + "px";
		if (CoolDrag.cloneobj.initoffsetY + currenY - CoolDrag.cloneobj.initMouseY > getRealTop(CoolDrag.container)) CoolDrag.cloneobj.style.top = (CoolDrag.cloneobj.initoffsetY + currenY - CoolDrag.cloneobj.initMouseY) + "px";
		else CoolDrag.cloneobj.style.top = getRealTop(CoolDrag.container) + "px";
		var collection = CoolDrag.container.getElementsByTagName("DIV");
		var finded = false;
		for (var i = 0; i < collection.length; i++) {
			var o = collection[i];
			if (o.className == "dragLayer") {
				if (((getRealLeft(o) <= CoolDrag.cloneobj.offsetLeft && getRealLeft(o) + o.offsetWidth >= CoolDrag.cloneobj.offsetLeft) || (getRealLeft(o) <= CoolDrag.cloneobj.offsetLeft + CoolDrag.cloneobj.offsetWidth && getRealLeft(o) + o.offsetWidth >= CoolDrag.cloneobj.offsetLeft + CoolDrag.cloneobj.offsetWidth)) && getRealTop(o) <= CoolDrag.cloneobj.offsetTop && getRealTop(o) + o.offsetHeight >= CoolDrag.cloneobj.offsetTop) { //window.status = getRealTop(o.parentNode)+"|"+(CoolDrag.cloneobj.offsetTop - 8);
					if (getRealTop(o.parentNode) >= CoolDrag.cloneobj.offsetTop - 8) {
						o.parentNode.insertBefore(CoolDrag.obj, o);
					} else {
						if (o.nextSibling) o.parentNode.insertBefore(CoolDrag.obj, o.nextSibling);
						else o.parentNode.appendChild(CoolDrag.obj);
					}
					finded = true;
					break;
				}
			}
		}
		if (!finded) {
			for (var i = 0; i < CoolDrag.container.childNodes.length; i++) {
				var o = CoolDrag.container.childNodes[i];
				if (getRealLeft(o) <= CoolDrag.cloneobj.offsetLeft && getRealLeft(o) + o.offsetWidth >= CoolDrag.cloneobj.offsetLeft) o.appendChild(CoolDrag.obj);
			}
		}
		with(CoolDrag.shadow.style) {
			left = (CoolDrag.cloneobj.offsetLeft + 4) + "px";
			top = (CoolDrag.cloneobj.offsetTop + 4) + "px";
		} //document.title = CoolDrag.cloneobj.style.left + "|" + CoolDrag.shadow.style.left;
	},
	end: function(e) {
		if (!CoolDrag.dragged) return;
		CoolDrag.obj.className = "dragLayer";
		CoolDrag.dragged = false;
		CoolDrag.shadow.parentNode.removeChild(CoolDrag.shadow);
		CoolDrag.timer = CoolDrag.repos(150, 15); //保存cookie
		var str = "";
		for (var i = 0; i < CoolDrag.container.childNodes.length; i++) {
			var o = CoolDrag.container.childNodes[i];
			if (i > 0) str += "|";
			str += o.id + ":";
			for (var j = 0; j < o.childNodes.length; j++) {
				if (j > 0) str += ",";
				str += o.childNodes[j].id;
			}
		}
		var p = CoolDrag.companycode+"_"+CoolDrag.userInfo+"_cooldrag"+document.URL;
		CoolDrag.save(p, str, 24);
	},
	repos: function(aa, ab) { //var f=CoolDrag.obj.filters.alpha.opacity;
		var tl = getRealLeft(CoolDrag.cloneobj);
		var tt = getRealTop(CoolDrag.cloneobj);
		var kl = (tl - getRealLeft(CoolDrag.obj)) / ab;
		var kt = (tt - getRealTop(CoolDrag.obj)) / ab; //var kf=f/ab;
		return setInterval(function() {
			if (ab < 1) {
				clearInterval(CoolDrag.timer);
				CoolDrag.cloneobj.parentNode.removeChild(CoolDrag.cloneobj);
				CoolDrag.obj = null;
				return;
			}
			ab--;
			tl -= kl;
			tt -= kt; //f-=kf;
			CoolDrag.cloneobj.style.left = parseInt(tl) + "px";
			CoolDrag.cloneobj.style.top = parseInt(tt) + "px"; //CoolDrag.tdiv.filters.alpha.opacity=f;
		},
		aa / ab)
	},
	min: function(e) {
		if (!e) e = window.event;
		var obj = getT(e);
		var rootObj = obj.parentNode.parentNode.parentNode;
		var id = rootObj.id;
		if (SavedObject.getStatus(id)[1]) {
			SavedObject.setStatus(id, 0);
			rootObj.style.height = "20px";
			rootObj.childNodes[1].style.display = 'none';
		} else {
			SavedObject.setStatus(id, 1);
			rootObj.lastChild.style.display = '';
			rootObj.style.height = SavedObject.getStatus(id)[2];
		}
		//obj.innerHTML = obj.innerHTML == 5 ? 6 : 5;
	},
	close: function(e) {
		if (!e) e = window.event;
		var obj = getT(e);
		var rootObj = obj.parentNode.parentNode.parentNode;
		rootObj.parentNode.removeChild(rootObj);
	},
	save: function(name, value, hours) {
		var expire = "";
		if (hours != null) {
			expire = new Date((new Date()).getTime() + hours * 3600000);
			expire = "; expires=" + expire.toGMTString();
		}
		document.cookie = name + "=" + escape(value) + expire;
	},
	read: function(name) {
		var cookieValue = "";
		var search = name + "=";
		if (document.cookie.length > 0) {
			offset = document.cookie.indexOf(search);
			if (offset != -1) {
				offset += search.length;
				end = document.cookie.indexOf(";", offset);
				if (end == -1) end = document.cookie.length;
				cookieValue = unescape(document.cookie.substring(offset, end))
			}
		}
		return cookieValue;
	}
}
function $(id) {
	return document.getElementById(id);
}
function getT(e) {
	return e.target || e.srcElement;
}
function getMouseX(e) {
	return e.pageX ? e.pageX: e.clientX + document.body.scrollLeft - document.body.clientLeft;
}
function getMouseY(e) {
	return e.pageY ? e.pageY: e.clientY + document.body.scrollTop - document.body.clientTop;
}
function getRealLeft(o) {
	var l = 0;
	while (o) {
		l += o.offsetLeft - o.scrollLeft;
		o = o.offsetParent;
	}
	return (l);
}
function getRealTop(o) {
	var t = 0;
	while (o) {
		t += o.offsetTop - o.scrollTop;
		o = o.offsetParent;
	}
	return (t);
}
function cleanWhitespace(node) {
	var notWhitespace = /\S/;
	for (var i = 0; i < node.childNodes.length; i++) {
		var childNode = node.childNodes[i];
		if ((childNode.nodeType == 3) && (!notWhitespace.test(childNode.nodeValue))) {
			node.removeChild(node.childNodes[i]);
			i--;
		}
		if (childNode.nodeType == 1) {
			cleanWhitespace(childNode);
		}
	}
}
var SavedObject = {
	elements: new Array(),
	setStatus: function(id, s) {
		for (var i = 0; i < SavedObject.elements.length; i++) {
			if (SavedObject.elements[i][0] == id) {
				SavedObject.elements[i][1] = s;
				break;
			}
		}
	},
	getStatus: function(id) {
		for (var i = 0; i < SavedObject.elements.length; i++) {
			if (SavedObject.elements[i][0] == id) return SavedObject.elements[i];
		}
	},
	push: function(o) {
		SavedObject.elements[SavedObject.elements.length] = o;
	}
}
