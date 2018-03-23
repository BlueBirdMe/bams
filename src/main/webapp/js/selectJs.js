/*拾取js*/

var SEL = {
	projectPath:"/",
	setProjectPath:function(path){
		this.projectPath = path;
	},
	getProjectPath:function(){
		return this.projectPath;
	},
	
	getEmployeeIds:function(type,txtid,valid,frmname,method,wp){
		var box = new Sys.msgbox("人员拾取",this.projectPath+"/erp/select_takepage/select_emp.jsp?treetype="+type+"&textid="+txtid+"&valueid="+valid,'900','550',wp);
		box.msgtitle="<b>人员拾取</b><br/>选择拾取人员，不拾取请‘取消’";
		var butarray = new Array();
		butarray[0] = "ok|employeeclickcustomer('"+box.dialogId+"','"+frmname+"','"+method+"');";
		butarray[1] = "cancel";
		box.buttons = butarray;
		return box;
	},
	
	getDeptIds:function(type,txtid,valid,frmname,method,wp){
		var box = new Sys.msgbox("部门拾取",this.projectPath+"/erp/select_takepage/select_dep.jsp?treetype="+type+"&textid="+txtid+"&valueid="+valid,'300','500',wp);
		box.msgtitle="<b>部门拾取</b><br/>选择拾取部门编码，不拾取请‘取消’";
		var butarray = new Array();
		butarray[0] = "ok|deptclickcustomer('"+box.dialogId+"','"+frmname+"','"+method+"');";
		butarray[1] = "cancel";
		box.buttons = butarray;
		return box;
	},
	
	getPostIds:function(type,txtid,valid,frmname,method,wp){
		var box = new Sys.msgbox("岗位拾取",this.projectPath+"/erp/select_takepage/select_post.jsp?type="+type+"&textid="+txtid+"&valueid="+valid,'300','500',wp);
		box.msgtitle="<b>岗位拾取</b><br/>选择拾取岗位，不拾取请‘取消’";
		var butarray = new Array();
		butarray[0] = "ok|postclickcustomer('"+box.dialogId+"','"+frmname+"','"+method+"');";
		butarray[1] = "cancel";
		box.buttons = butarray;
		return box;
	},

	//创建角色、用户编组时使用到
	getUserIds:function(type,txtid,valid,frmname,method,wp){
		var box = new Sys.msgbox("人员拾取",this.projectPath+"/erp/select_takepage/select_user.jsp?treetype="+type+"&textid="+txtid+"&valueid="+valid,'900','550',wp);
		box.msgtitle="<b>人员拾取</b><br/>选择拾取人员，不拾取请‘取消’";
		var butarray = new Array();
		butarray[0] = "ok|userclickcustomer('"+box.dialogId+"','"+frmname+"','"+method+"');";
		butarray[1] = "cancel";
		box.buttons = butarray;
		return box;
	},

	getLibraryIds:function(libupname,libupcode,frmname,method,wp) {
		var box = new Sys.msgbox('拾取上级编码',this.projectPath+"/erp/select_takepage/select_library.jsp?name="+libupname+"&code="+libupcode,'300','500',wp);
		box.msgtitle="<b>业务字典上级编码拾取</b><br/>选择拾取上级编码，不拾取请‘取消’";
		var butarray = new Array();
		butarray[0] = "ok|gettreecheckcustom('"+box.dialogId+"','"+frmname+"','"+method+"');";
		butarray[1] = "cancel";
		box.buttons = butarray;
		return box;
	},
	
	getLibraryStandard:function(libupname,libupcode,frmname,method,wp) {
		var box = new Sys.msgbox('拾取上级编码',this.projectPath+"/erp/select_takepage/select_library_standard.jsp?name="+libupname+"&code="+libupcode,'300','500',wp);
		box.msgtitle="<b>标准代码上级拾取</b>";
		var butarray = new Array();
		butarray[0] = "ok|gettreecheckcustom('"+box.dialogId+"','"+frmname+"','"+method+"');";
		butarray[1] = "cancel";
		box.buttons = butarray;
		return box;
	},

	getSysMethodInfoIds:function(type,txtid,valid,frmname,method,wp){
		var box = new Sys.msgbox("上级目录拾取",this.projectPath+"/erp/select_takepage/select_level_methodinfo.jsp?treetype="+type+"&textid="+txtid+"&valueid="+valid,'300','500',wp);
		box.msgtitle="<b>上级目录拾取</b><br/>选择拾取上级目录，不拾取请‘取消’";
		var butarray = new Array();
		butarray[0] = "ok|methodInfoIdclick('"+box.dialogId+"','"+frmname+"','"+method+"');";
		butarray[1] = "cancel";
		box.buttons = butarray;
		return box;
	},
	
	getUserSysMethodInfoIds:function(type,method,wp){
		var box = new Sys.msgbox("选择快捷菜单",this.projectPath+"/erp/select_takepage/select_user_method.jsp?treetype="+type,'300','500',wp);
		var butarray = new Array();
		butarray[0] = "ok|methodInfoIdclick('"+box.dialogId+"','"+method+"');";
		butarray[1] = "cancel";
		box.buttons = butarray;
		return box;
	},
	
	getUserGroup:function(type,txtid,valid,frmname,method,wp){
		var box = new Sys.msgbox("用户组拾取",this.projectPath+"/erp/select_takepage/select_usergroup.jsp?treetype="+type+"&textid="+txtid+"&valueid="+valid,'800','500',wp);
		box.msgtitle="<b>用户组拾取</b><br/>选择拾取组，不拾取请‘取消’";
		var butarray = new Array();
		butarray[0] = "ok|groupclickcustomer('"+box.dialogId+"','"+frmname+"','"+method+"');";
		butarray[1] = "cancel";
		box.buttons = butarray;
		return box;
	},

	getNetMailAdder:function(type,valid,frmname,method,wp){
		var box = new Sys.msgbox("邮件地址拾取",this.projectPath+"/erp/mobile_sms/select_netmailemp.jsp?treetype="+type+"&valueid="+valid,'800','500',wp);
		box.msgtitle="<b>邮件地址拾取</b><br/>选择拾取联系人，不拾取请‘取消’";
		var butarray = new Array();
		butarray[0] = "ok|employeeclickcustomer('"+box.dialogId+"','"+frmname+"','"+method+"');";
		butarray[1] = "cancel";
		box.buttons = butarray;
		return box;
	},
	
	getCarIds:function(type,txtid,valid,frmname,method,wp){
		var box = new Sys.msgbox("车辆拾取",this.projectPath+"/erp/office_resources/take_car.jsp?treetype="+type+"&textid="+txtid+"&valueid="+valid,'800','500',wp);
		box.msgtitle="<b>车辆拾取</b><br/>选择拾取车辆编码，不拾取请‘取消’";
		var butarray = new Array();
		butarray[0] = "ok|roomclickcustomer('"+box.dialogId+"','"+frmname+"','"+method+"');";
		butarray[1] = "cancel";
		box.buttons = butarray;
		return box;
	},

	getBoardIds:function(type,txtid,valid,frmname,method,wp){
		var box = new Sys.msgbox("会议室拾取",this.projectPath+"/erp/office_resources/take_boadroom.jsp?treetype="+type+"&textid="+txtid+"&valueid="+valid,'800','500',wp);
		box.msgtitle="<b>会议室拾取</b><br/>选择拾取会议室，不拾取请‘取消’";
		var butarray = new Array();
		butarray[0] = "ok|roomclickcustomer('"+box.dialogId+"','"+frmname+"','"+method+"');";
		butarray[1] = "cancel";
		box.buttons = butarray;
		return box;
	},
	
	getBookIds:function(type,txtid,valid,frmname,method,wp){
		var box = new Sys.msgbox("拾取图书信息",this.projectPath+"/erp/select_takepage/select_book.jsp?treetype="+type+"&textid="+txtid+"&valueid="+valid,'800','500',wp);
		box.msgtitle="<b>拾取图书信息</b><br/>拾取图书信息，不拾取请‘取消’";
		var butarray = new Array();
		butarray[0] = "ok|bookclickcustomer('"+box.dialogId+"','"+frmname+"','"+method+"');";
		butarray[1] = "cancel";
		box.buttons = butarray;
		return box;
	},
	
	getImg:function(path,txtid,frmname,wp){
		var box = new Sys.msgbox("拾取图标",this.projectPath+"/erp/select_takepage/select_img.jsp?path="+path+"&textid="+txtid+"&frmname="+frmname,'800','500',wp);
		box.dialogId = "imgDialog";
		return box;
	}
}