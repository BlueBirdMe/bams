var cookie = new JSCookie();
var companyname ="companycode";
var uname ="urname";

window.onload=function(){
	initHotkeys();
	//加载cookie
	var cookcompany =cookie.GetCookie(companyname);
	var cookusername =cookie.GetCookie(uname);
	
	if(cookcompany!= null&& cookcompany!=""){
		document.getElementById("companycode").value=cookcompany;
	}
	if(cookusername!= null&& cookusername!=""){
		document.getElementById("username").value=cookusername;
	}
	if(document.getElementById("companycode").value == null ||document.getElementById("companycode").value==""){
		document.getElementById("companycode").focus();
	}else if(document.getElementById("username").value==null || document.getElementById("username").value==""){
		document.getElementById("username").focus();
	}else{
		document.getElementById("userpwd").focus();
	}
	
	changecode(document.getElementById("codeimg"));

	var txts = document.getElementsByTagName("input");
	for(var i=0;i<txts.length;i++){
		var txt = txts[i];
		if (txt.getAttribute("type") == "text" || txt.getAttribute("type") == "password") {
			txt.onfocus = function(){
				this.className='niceform_hover';
			};
			txt.onblur = function(){
				this.className='niceform';
			};
		}
	}
}

function initHotkeys(){
	$(document).bind('keydown.return',function (evt){loginCheck(); return false; });
}

function clearinput(){
	document.getElementById("username").value="";
	document.getElementById("userpwd").value="";
	document.getElementById("code").value = "";
	changecode(document.getElementById("codeimg"));
	document.getElementById("username").focus();
}

function loginCheck(path){
	var companycode =document.getElementById("companycode");
	var username = document.getElementById("username");
	var pwd = document.getElementById("userpwd");
	var ccode = document.getElementById("code");
	var lbtn = document.getElementById("loginbtndiv");
	var lod = document.getElementById("loginloadingdiv");
	var box = document.getElementById("msgbox");
	
	if(companycode.value.length == 0){
		box.innerHTML = "请输入公司码！";
		companycode.focus();
		return;
	}
	if(username.value.length == 0){
		box.innerHTML = "请输入用户名！";
		username.focus();
		return;
	}
	if(pwd.value.length == 0){
		box.innerHTML = "请输入用户密码！";
		pwd.focus();
		return;
	}
	if(ccode != null && ccode.value.length == 0){
		box.innerHTML = "请输入验证码！";
		ccode.focus();
		return;
	}
	
	box.innerHTML ="";
	lbtn.style.display="none";
	lod.style.display="block";
	
	var expire_time = new Date();
	expire_time.setFullYear(expire_time.getFullYear() + 1);
	
	if(document.getElementById("cmcode").checked){
		cookie.SetCookie(companyname,companycode.value,expire_time); 
	}
	if(document.getElementById("usname").checked){
		cookie.SetCookie(uname,username.value,expire_time); 
	}
	
	document.erpfrm.submit();
}

function hiddenTipsDiv(){
	document.getElementById('tipsdiv').style.display ='none';
}

function  detectCapsLock(event){
    var e = event||window.event;   
    var o = e.target||e.srcElement;   
    var oTip = document.getElementById("tipsdiv");   
    var keyCode  =  e.keyCode||e.which; // 按键的keyCode    
    var isShift  =  e.shiftKey ||(keyCode  ==   16 ) || false ; // shift键是否按住   
     if (((keyCode >=   65   &&  keyCode  <=   90 )  &&   !isShift) // Caps Lock 打开，且没有按住shift键    
     || ((keyCode >=   97   &&  keyCode  <=   122 )  &&  isShift)// Caps Lock 打开，且按住shift键   
     ){
	    oTip.style.display = "block";
     }else{
     	oTip.style.display = "none"; 
     }    
}

window.status ="品互网络 Pinhuba ©2008-2014";