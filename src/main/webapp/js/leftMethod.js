var leftConfig = {
	defaultsel		:1,
	outBgColor		:"#C1DFF8",
	outBorderColorL	:"#ffffff",
	outBorderColorR	:"#808080",
	outFontColor	:"#888888",
	overBgColor		:"#AED9ED",
	overBorderColorL:"#CCE8FC",
	overBorderColorR:"#808080",
	overFontColor	:"#ffffff",
	setDefaultSel:function(sel){
		if(sel==null||sel<1) sel =1;
		this.defaultsel = sel;
	}
};

function setMethodSel(index){
	leftConfig.setDefaultSel(index);
	var divs = document.getElementsByTagName("div");
	var dl = divs.length;
	for(var i=0;i<dl;i++){
		if(divs[i].className=="div_leftmethod"){
			var btns = divs[i].getElementsByTagName("div");
			var c=0;
			var leftbtnarray = new Array();
			for(var a=0;a<btns.length;a++){
				if(btns[a].className == "leftbut"){
					leftbtnarray[c] = btns[a];
					c++;
				}
			}
			var tmpsel = leftConfig.defaultsel;
			if(tmpsel>leftbtnarray.length){
				tmpsel =1;
			}
			for(var j=0;j<leftbtnarray.length;j++){
				if(j+1 == tmpsel){
					leftbtnarray[j].style.borderWidth = "1px";
					leftbtnarray[j].style.borderStyle = "solid";
					leftbtnarray[j].style.backgroundColor = leftConfig.overBgColor;
					leftbtnarray[j].style.borderTopColor = leftConfig.overBorderColorL;
					leftbtnarray[j].style.borderLeftColor = leftConfig.overBorderColorL;
					leftbtnarray[j].style.borderBottomColor = leftConfig.overBorderColorR;
					leftbtnarray[j].style.borderRightColor = leftConfig.overBorderColorR;
					leftbtnarray[j].style.color =leftConfig.overFontColor;
				}else{
					leftbtnarray[j].style.backgroundColor = "";
					leftbtnarray[j].style.borderTopColor = "";
					leftbtnarray[j].style.borderLeftColor = "";
					leftbtnarray[j].style.borderBottomColor = "";
					leftbtnarray[j].style.borderRightColor = "";
					leftbtnarray[j].style.color ="";
				}
			}
		}
	}
}


function initLeftMethod(){
	//左侧功能按钮事件绑定
	
	var divs = document.getElementsByTagName("div");
	var dl = divs.length;
	for(var i=0;i<dl;i++){
		if(divs[i].className=="div_leftmethod"){
			var btns = divs[i].getElementsByTagName("div");
			var c=0;
			var leftbtnarray = new Array();
			for(var a=0;a<btns.length;a++){
				if(btns[a].className == "leftbut"){
					leftbtnarray[c] = btns[a];
					c++;
				}
			}
			var tmpsel = leftConfig.defaultsel;
			if(tmpsel>leftbtnarray.length){
				tmpsel =1;
			}
			for(var j=0;j<leftbtnarray.length;j++){
				if(j+1 == tmpsel){
					leftbtnarray[j].style.borderWidth = "1px";
					leftbtnarray[j].style.borderStyle = "solid";
					leftbtnarray[j].style.backgroundColor = leftConfig.overBgColor;
					leftbtnarray[j].style.borderTopColor = leftConfig.overBorderColorL;
					leftbtnarray[j].style.borderLeftColor = leftConfig.overBorderColorL;
					leftbtnarray[j].style.borderBottomColor = leftConfig.overBorderColorR;
					leftbtnarray[j].style.borderRightColor = leftConfig.overBorderColorR;
					leftbtnarray[j].style.color =leftConfig.overFontColor;
				}
				leftbtnarray[j].onmouseover = function (){
					div_leftmethod_function_over(this);
				};
				leftbtnarray[j].onmouseout = function (){
					div_leftmethod_function_out(this);
				};
				var old = leftbtnarray[j].onclick;
				leftbtnarray[j].onclick = function (){
					div_leftmethod_function_click(this,leftbtnarray);
				};
				if (typeof (old) == "function") {
					if (window.addEventListener) {
						leftbtnarray[j].addEventListener("click", bindAsEvt(leftbtnarray[j],old), false);
					} else {
						leftbtnarray[j].attachEvent("onclick", bindAsEvt(leftbtnarray[j],old));
					}
				}
			}
		}
	}
}

//================左侧功能使用============
var old_div_leftmethod_bgColor="";
var old_div_leftmethod_borderColorL="";
var old_div_leftmethod_borderColorR="";
var old_div_leftmethod_fontColor="";

function div_leftmethod_function_over(obj){
	old_div_leftmethod_bgColor = obj.style.backgroundColor;
	old_div_leftmethod_borderColorL = obj.style.borderLeftColor;
	old_div_leftmethod_borderColorR= obj.style.borderRightColor;
	old_div_leftmethod_fontColor =obj.style.color;
	if(old_div_leftmethod_bgColor==leftConfig.outBgColor){
		obj.style.backgroundColor = leftConfig.overBgColor;
		obj.style.borderTopColor = leftConfig.overBorderColorL;
		obj.style.borderLeftColor = leftConfig.overBorderColorL;
		obj.style.borderBottomColor = leftConfig.overBorderColorR;
		obj.style.borderRightColor = leftConfig.overBorderColorR;
		obj.style.color =leftConfig.overFontColor;
	}
}

function div_leftmethod_function_out(obj){
	obj.style.backgroundColor= old_div_leftmethod_bgColor;
	obj.style.borderTopColor = old_div_leftmethod_borderColorL;
	obj.style.borderLeftColor = old_div_leftmethod_borderColorL;
	obj.style.borderBottomColor = old_div_leftmethod_borderColorR;
	obj.style.borderRightColor = old_div_leftmethod_borderColorR;
	obj.style.color =old_div_leftmethod_fontColor;
}

function div_leftmethod_function_click(obj,array){
	for(var j=0;j<array.length;j++){
		array[j].style.backgroundColor = "";
		array[j].style.borderTopColor = "";
		array[j].style.borderLeftColor = "";
		array[j].style.borderBottomColor = "";
		array[j].style.borderRightColor = "";
		array[j].style.color ="";
	}
	old_div_leftmethod_bgColor = leftConfig.overBgColor;
	old_div_leftmethod_borderColorL = leftConfig.overBorderColorL;
	old_div_leftmethod_borderColorR= leftConfig.overBorderColorR;
	old_div_leftmethod_fontColor =leftConfig.overFontColor;
	
	
	obj.style.backgroundColor = leftConfig.overBgColor;
	obj.style.borderTopColor = leftConfig.overBorderColorL;
	obj.style.borderLeftColor = leftConfig.overBorderColorL;
	obj.style.borderBottomColor = leftConfig.overBorderColorR;
	obj.style.borderRightColor = leftConfig.overBorderColorR;
	obj.style.color =leftConfig.overFontColor;
	obj.style.borderWidth = "1px";
	obj.style.borderStyle = "solid";
	
}

//转换ie下this指向window
function bindAsEvt() {
	var args = Array.prototype.slice.call(arguments, 0), obj = args.shift(), fn = args.shift();
	return function (event) {
		fn.apply(obj, [event || window.event].concat(args));
	};
}

//注册事件
if (document.all) {
	window.attachEvent("onload", initLeftMethod);
} else {
	window.addEventListener("load", initLeftMethod, false);
}