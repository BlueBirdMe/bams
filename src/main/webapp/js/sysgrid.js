var sysGridShowView = {
	showAll :1,//显示表格和图片列表
	showTable:2,//只显示表格列表
	showImage:3//只显示图片列表
};

var sysGridDefaultShow = {//只有在表格和图片列表都显示时起作用
	defaultTable:1,//默认打开表格视图
	defaultImage:2//默认打开图片视图
};

var sysGridConfing ={
	//===========生成表格所有需要变量==========
	conpath : "",//工程路径
	bodyscroll : "",
	queryFunction : "",
	dblbundle : "",
	dblfunction : "",
	autoquery : false,
	showview : 1,
	isCheckBox:true,
	defaultShow:1, 
	isshowBlank:true,
	//行操作图标
	isshowProcess:true,
	processMethodName:"",
	
	//图片信息
	imgnum : "",
	imgtext : "",
	imgurl : "",
	imgwidth:"",
	imgheight:"",
	noimgpath:"",
	imgdivWidth:"",
	imgcode :"",
	imgtxtlen : 8,
	imgMethod : false,
	//高级查询区域
	advancedlist:{},
	//列设置
	collist:{},
	//列样式
	colstyle:{},
	//滚动条对象
	sildertmp:null,
	sliderpagetmp:null,
	
	//结果集合临时存放对象
	resultTempList : null,
	
	gridIdPk:"primaryKey",
	orderPriority:"",
	rowHeight:"",
	//=================end======================
	init:function(sysGrid){
		this.conpath = sysGrid.contextPath;//工程路径
		
		this.bodyscroll = sysGrid.bodyscroll;
		//处理窗口滚动条
		initTableHeight();
		this.queryFunction = sysGrid.queryFunction;
		this.dblbundle = sysGrid.dblBundle;
		this.dblfunction = sysGrid.dblFunction;
		this.autoquery = sysGrid.isAutoQuery;
		this.showview = sysGrid.showView;
		this.isCheckBox = sysGrid.isCheckBox;
		this.isshowProcess = sysGrid.isShowProcess;
		this.processMethodName = sysGrid.processMethodName;
		this.defaultShow = sysGrid.defaultShow;
		this.isshowBlank = sysGrid.isshowBlank;
		this.rowHeight = "height:"+sysGrid.rowHeight+"px;";
		if(this.showview==sysGridShowView.showAll ||this.showview ==sysGridShowView.showImage){
			//图片信息
			this.imgnum = sysGrid.imgShowNum;
			this.imgtext = sysGrid.imgShowText;
			this.imgurl = sysGrid.imgShowUrl;
			this.imgwidth = sysGrid.imgWidth;
			this.imgheight = sysGrid.imgHeight;
			this.noimgpath = document.getElementById("noimgpath").value;
			this.imgdivWidth = sysGrid.imgdivWidth;
			this.imgcode = sysGrid.imgShowCode;
			this.imgtxtlen = sysGrid.imgTextLen;
			this.imgMethod = sysGrid.imgMethod;
			
			var sliderbl = false;
			if(this.imgwidth!=null&&this.imgwidth.length>0&&this.imgwidth != "null"){
				if(this.imgwidth.toLowerCase()!="auto"){
					sliderbl = true;
				}
			}
			if(this.imgheight!=null&&this.imgheight.length>0&&this.imgheight != "null"){
				if(this.imgheight.toLowerCase() != "auto"){
					sliderbl = true;
				}
			}
			
			if(sliderbl){
				//创建拖动条
				this.sildertmp = createslider();
				document.getElementById("imgviewsize").style.display ="";
			}else{
				document.getElementById("imgviewsize").style.display ="none";
				document.getElementById("imgchktitle").width ="100%";
			}
		}
		//快速跳转拖动条
		this.sliderpagetmp = createsliderByPager();
		
		//高级查询区域
		this.advancedlist = sysGrid.advancedlist;
		//列设置
		this.collist = sysGrid.collist;
		this.colstyle = sysGrid.colstyle;

		if(this.autoquery){
			eval(this.queryFunction+"();");
		}
		
		if(this.showview==sysGridShowView.showAll && this.defaultShow == sysGridDefaultShow.defaultImage){
			changeShow(sysGridDefaultShow.defaultImage);
		}else if(this.showview==sysGridShowView.showImage){
			changeShow(sysGridDefaultShow.defaultImage);
		}
	}
};

function initTableHeight(){
	document.body.style.overflow =sysGridConfing.bodyscroll;
}
function initGridData(resultList,pager){
	sysGridConfing.resultTempList = resultList;
	//加载数据
	try{
		clearGridResult();
		if(sysGridConfing.showview==sysGridShowView.showAll || sysGridConfing.showview == sysGridShowView.showTable){
			
			var rlen = resultList.length;//列表数据行数
			var roundlen = pager.pageSize-rlen;//列表空白行数
			
			//显示数据行
			for(var i=0;i<rlen;i++){
				var obj = resultList[i];
				var rid = eval("obj."+sysGridConfing.gridIdPk);
				//显示行数或复选框
				var otrCount = document.getElementById("rowCountTable").insertRow(-1);
				//otrCount.style.cssText = "background:url("+sysGridConfing.conpath+"/images/grid_images/fhbg2.gif);";
				otrCount.style.cssText = "background:#F5F5F5;";
				otrCount.id = "gridcheckbox_"+rid;
				var tdCount = document.createElement("td");
				tdCount.className = "tabStyletd";
				tdCount.style.cssText = sysGridConfing.rowHeight;
				if(sysGridConfing.isCheckBox){
					tdCount.innerHTML = "<div class='tabimgdiv'><input style='margin:0px;padding:0px;height:13px;width:13px;' type='checkbox' value='"+rid+"' onclick=\"sysgridchecked(this);\"/></div>";
				}else{
					var pc =1;
					if(pager.currentPage>=1){
						pc = pager.currentPage-1;
					}
					tdCount.innerHTML = "<nobr>"+(pc*pager.pageSize+i+1)+"<input type='checkbox' value='"+rid+"' class ='nonice' style='display:none;'/></nobr>";
				}
				otrCount.appendChild(tdCount);
				//显示数据
				var otr = document.getElementById("tableResult").insertRow(-1);
				if(document.getElementById("tableResult").rows.length % 2 >0){
					otr.className = "tableContentAlternating001";
				}else{
					otr.className = "tableContent001";
				}
				
				otr.onmouseover=function(){
					SetTRcolorOver(this,'tableContentOn001');
				};
				
				otr.onmouseout=function(){
					SetTRcolorOut(this);
				};
				
				otr.onclick=function(event){
					clickto(event);
				};
				
				otr.id = "record_"+rid;
				var vl = eval("obj."+sysGridConfing.dblbundle);
				otr.value = vl;
				
				otr.ondblclick=function(){
					try{eval(sysGridConfing.dblfunction+"(this)")}catch(err){};
				};
				
				for(var j=0;j<sysGridConfing.collist.length;j++){
				
					var td = document.createElement("td");
					var pars = sysGridConfing.collist[j].split("@@");
					var columnCode = pars[0];//列表id
					//进行对象为空判断
					var data = vaildityColumnData(obj,columnCode,pars);
					var cs = sysGridConfing.rowHeight;
					if(parseInt(pars[2])>0){//显示字数
						cs += "text-align:left;padding-left:3px;";
					}else{
						cs += "text-align:center;";
					}
					if(sysGridConfing.colstyle!=null&&sysGridConfing.colstyle.length>0&&sysGridConfing.colstyle!="null"){
						for(var a=0;a<sysGridConfing.colstyle.length;a++){
							var styles = sysGridConfing.colstyle[a].split("@@");
							if(styles[0] == columnCode){
								cs+=styles[1];
							}
						}
					}
					td.style.cssText = cs;
					//判断是否需要列转换
					td.innerHTML = "<nobr>"+changeColumnData(obj,data,pars[1],pars[2])+"</nobr>";
					otr.appendChild(td);
				}
				//显示操作
				if(sysGridConfing.isshowProcess){
					var processtr = document.getElementById("dataProcessTable").insertRow(-1);
					//processtr.style.cssText = "background:url("+sysGridConfing.conpath+"/images/grid_images/fhbg2.gif);";
					processtr.style.cssText = "background:#F5F5F5;";
					processtr.id = "gridprocess_"+rid;
					var processtd = document.createElement("td");
					processtd.className = "tabStyletd";
					processtd.style.cssText = sysGridConfing.rowHeight;
					var method = sysGridConfing.processMethodName;
					var methodstr ="";
					if(method!=null && method.length>0){
						methodstr =eval(method+"(obj)");
					}
					processtd.innerHTML = "<nobr><div class='tabimgdiv'>"+methodstr+"</div><nobr>";
					processtr.appendChild(processtd);
				}
			}
			
			
			//显示空白行
			if(sysGridConfing.isshowBlank){
				for(var i=0;i<roundlen;i++){
					var otrCount = document.getElementById("rowCountTableBlank").insertRow(-1);
					var tdCount = document.createElement("td");
					tdCount.className = "tabStyletd";
					tdCount.style.cssText = sysGridConfing.rowHeight;
					if(i==0){
						tdCount.style.borderTop="0px solid #cccccc";
					}
					tdCount.innerHTML = "&nbsp;";
					otrCount.appendChild(tdCount);
					
					var otr = document.getElementById("tableResult").insertRow(-1);
					for(var j=0;j<sysGridConfing.collist.length;j++){
						var td = document.createElement("td");
						td.style.cssText = sysGridConfing.rowHeight;
						td.innerHTML = "&nbsp;";
						otr.appendChild(td);
					}
					if(sysGridConfing.isshowProcess){
						var processtr = document.getElementById("dataProcessTableBlank").insertRow(-1);
						var processtd = document.createElement("td");
						processtd.className = "tabStyletd";
						processtd.style.cssText = sysGridConfing.rowHeight;
						if(i==0){
							processtd.style.borderTop="0px solid #cccccc";
						}
						processtd.innerHTML = "&nbsp;";
						processtr.appendChild(processtd);
					}
				}
			}
		}
		if(sysGridConfing.showview==sysGridShowView.showAll || sysGridConfing.showview ==sysGridShowView.showImage){
			/****图片形式显示****/
			var colspan=0;
			var tdw=100/sysGridConfing.imgnum+"%";
			if(resultList.length%sysGridConfing.imgnum!=0){
				colspan=resultList.length%5+1;
			}
			var imgstyle ="margin:5px;";
			var tmpimgwidth = sysGridConfing.imgwidth;
			var tmpimgheight = sysGridConfing.imgheight;
			var sildervalue = 100;
			if(sysGridConfing.sildertmp!=null){
				sildervalue = sysGridConfing.sildertmp.getValue();
			}
			if(sysGridConfing.imgwidth!=null&&sysGridConfing.imgwidth.length>0&&sysGridConfing.imgwidth != "null"){
				if(sysGridConfing.imgwidth.toLowerCase()!="auto"){
					tmpimgwidth = sysGridConfing.imgwidth*sildervalue/100+"px";
				}
				imgstyle+="width:"+tmpimgwidth+";";
			}
			if(sysGridConfing.imgheight!=null&&sysGridConfing.imgheight.length>0&&sysGridConfing.imgheight != "null"){
				if(sysGridConfing.imgheight.toLowerCase() != "auto"){
					tmpimgheight = sysGridConfing.imgheight*sildervalue/100+"px";
				}
				imgstyle+="height:"+tmpimgheight+";";
			}
			for(var i=0;i<resultList.length;i++){
				if(i%sysGridConfing.imgnum == 0){
					var otr = document.getElementById("imgResult").insertRow(-1);
				}
				var td = document.createElement("td");
				var obj = resultList[i];
				var rid = eval("obj."+sysGridConfing.gridIdPk);
				var imgid = "img_"+rid;
				//只显示图片时选择使用
				if(sysGridConfing.showview ==sysGridShowView.showImage){
					var otrCount = document.getElementById("rowCountTable").insertRow(-1);
					otrCount.id = "gridcheckbox_"+rid;
					var tdCount = document.createElement("td");
					tdCount.innerHTML = "<input type='checkbox' value='"+rid+"' class ='nonice' style='display:none;'/>";
					otrCount.appendChild(tdCount);
				}
				
				td.id = imgid;
				var vl = eval("obj."+sysGridConfing.dblbundle);
				td.value = vl;
				
				td.ondblclick=function(){
					try{eval(sysGridConfing.dblfunction+"(this)")}catch(err){};
				};
				
				td.style.cssText = "text-align:center;";
				td.className = 'tableStyleSolidLine';
				
				td.onclick=function(event){
					clickimg(event);
				};
				
				td.onmouseover=function(){
					imgmouseover(this);imgshowdetail(this);
				};
				
				td.onmouseout=function(){
					imgmouseout(this);imghiddendetail(this);
				};
				
				td.width ="20%";
				var url = changeImgColumnData(vaildityColumnData(obj,sysGridConfing.imgurl));
				var txt="";
				if(sysGridConfing.imgMethod){
					txt = eval(sysGridConfing.imgtext+"(obj)");
				}else{
					var ts = (sysGridConfing.imgtext).split(",");
					if(ts.length>0){
						for(var t=0;t<ts.length;t++){
							if(ts[t].length>0){
								if(ts[t].charAt(0)=="&"){
									txt+=ts[t].replace("&","");
								}else{
									var vc = vaildityColumnData(obj,ts[t]);
									if(vc!=null&&vc.length>0){
										txt+=vc;
									}
								}
							}
						}
					}
				}
				var tmp = txt;
				if(sysGridConfing.imgtxtlen!=0&&txt!=null&&txt.length>sysGridConfing.imgtxtlen&&txt!="null"){
					tmp = txt.substring(0,sysGridConfing.imgtxtlen)+"..";
				}
				var imgsrc =sysGridConfing.conpath+"/showimg.do?imgId="+url;
				if(sysGridConfing.noimgpath!=null&&sysGridConfing.noimgpath.length>0 && sysGridConfing.noimgpath!="null"){
					imgsrc+= "&noImgPath="+sysGridConfing.noimgpath;
				}
				if(sysGridConfing.imgcode!=null&&sysGridConfing.imgcode.length>0 && sysGridConfing.imgcode!="null"){
					imgsrc+= "&imgCode="+sysGridConfing.imgcode;
				}
				td.innerHTML = "<img src='"+imgsrc+"' style ='"+imgstyle+"' border='0' name='imageshitu'/><br/><label style ='padding:5px;'>"+tmp+"</label>";
				otr.appendChild(td);
				if( i == resultList.length-1 && colspan>0){
					var td2 = document.createElement("td");
		        	td2.colSpan =colspan;
		        	otr.appendChild(td2);
		        }
			}
		}
		//分页
		document.getElementById("currentPage").innerHTML = pager.currentPage;
		document.getElementById("pageCount").innerHTML = pager.totalPages;
		if(pager.totalPages==0){
			document.getElementById("pageCount").innerHTML= "1";
		}
		document.getElementById("rowCount").innerHTML = pager.totalRows;
		var firstPage = document.getElementById("firstPage");
		var prevPage = document.getElementById("prevPage");
		var lastPage = document.getElementById("lastPage");
		var nextPage = document.getElementById("nextPage");
		var currentPage = document.getElementById("currentPage");
		if(parseInt(pager.totalPages) <= 1){
			firstPage.src = ""+sysGridConfing.conpath+"/images/grid_images/first_.gif";	//灰色
			firstPage.className = null;
			firstPage.onclick = function(){};
			
			prevPage.src = ""+sysGridConfing.conpath+"/images/grid_images/prev_.gif";	//灰色
			prevPage.className = null;
			prevPage.onclick=function(){};
			
			nextPage.src = ""+sysGridConfing.conpath+"/images/grid_images/next_.gif";	//灰色
			nextPage.className = null;
			nextPage.onclick=function(){};
			
			lastPage.src = ""+sysGridConfing.conpath+"/images/grid_images/last_.gif";	//灰色
			lastPage.className = null;
			lastPage.onclick = function(){};
			
		}else if(parseInt(pager.currentPage) == 1){
			firstPage.src = ""+sysGridConfing.conpath+"/images/grid_images/first_.gif";	//灰色
			firstPage.className = null;
			firstPage.onclick = function(){};
			
			prevPage.src = ""+sysGridConfing.conpath+"/images/grid_images/prev_.gif";	//灰色
			prevPage.className = null;
			prevPage.onclick=function(){};
			
			nextPage.src = ""+sysGridConfing.conpath+"/images/grid_images/next.gif";	
			nextPage.className = "grid_img";
			
			nextPage.onclick=function(){
				document.getElementById("pageMethod").value="next";
				eval(sysGridConfing.queryFunction+"()");
			};
			
			
			lastPage.src = ""+sysGridConfing.conpath+"/images/grid_images/last.gif";	
			lastPage.className = "grid_img";
		
			lastPage.onclick=function(){
				document.getElementById("pageMethod").value="last";
				eval(sysGridConfing.queryFunction+"()");
			};	
			
		}else if(parseInt(pager.currentPage) == parseInt(pager.totalPages)){
			firstPage.src = ""+sysGridConfing.conpath+"/images/grid_images/first.gif";	
			firstPage.className = "grid_img";
			
			firstPage.onclick=function(){
				document.getElementById("pageMethod").value="first";
				eval(sysGridConfing.queryFunction+"()");
			};
			
			prevPage.src = ""+sysGridConfing.conpath+"/images/grid_images/prev.gif";
			prevPage.className = "grid_img";
			
			prevPage.onclick=function(){
				document.getElementById("pageMethod").value="previous";
				eval(sysGridConfing.queryFunction+"()");
			};
			
			nextPage.src = ""+sysGridConfing.conpath+"/images/grid_images/next_.gif";	//灰色
			nextPage.className = null;
			nextPage.onclick=function(){};
			
			lastPage.src = ""+sysGridConfing.conpath+"/images/grid_images/last_.gif";	//灰色
			lastPage.className = null;
			lastPage.onclick=function(){};
		}else{
			firstPage.src = ""+sysGridConfing.conpath+"/images/grid_images/first.gif";
			firstPage.className = "grid_img";
			
			firstPage.onclick=function(){
				document.getElementById("pageMethod").value="first";
				eval(sysGridConfing.queryFunction+"()");
			};
			
			prevPage.src = ""+sysGridConfing.conpath+"/images/grid_images/prev.gif";
			prevPage.className = "grid_img";
			
			prevPage.onclick=function(){
				document.getElementById("pageMethod").value="previous";
				eval(sysGridConfing.queryFunction+"()");
			};
			
			nextPage.src = ""+sysGridConfing.conpath+"/images/grid_images/next.gif";	
			nextPage.className = "grid_img";
			
			nextPage.onclick=function(){
				document.getElementById("pageMethod").value="next";
				eval(sysGridConfing.queryFunction+"()");
			};
			
			lastPage.src = ""+sysGridConfing.conpath+"/images/grid_images/last.gif";	
			lastPage.className = "grid_img";
			
			lastPage.onclick=function(){
				document.getElementById("pageMethod").value="last";
				eval(sysGridConfing.queryFunction+"()");
			};
		}
		//创建快速跳转
		if(sysGridConfing.sliderpagetmp!=null){
			sysGridConfing.sliderpagetmp.setMax(""+pager.totalPages);
			sysGridConfing.sliderpagetmp.setValue(""+pager.currentPage);
		}
		document.getElementById("pagerTool").innerHTML = pagerTool;
	}catch(err){
	
	}
	if(sysGridConfing.isCheckBox){
		setselectStyle();
		setRowCountTableTdTopCheckboxStatus(false);
	}
	
	document.getElementById("allshowdiv").scrollTop=0;	//滚动条置顶
}
function vaildityColumnData(obj,columnCode,columnarray){
	if(columnarray!=null && columnarray == undefined && columnarray == "undefined"&& columnarray.length > 1){
		var codes = columnCode.split("_");
		if(codes.length>1&&codes[0]== "customer_"){
			return eval(columnarray[1]+"(obj)");
		}
	}
	var tmp = columnCode.split(".");
	if(tmp.length==1){
		return eval("obj."+columnCode);
	}else{
		var bl = true;
		var tp = obj;
		for(var i=0;i<tmp.length-1;i++){
			var t = eval("tp."+tmp[i]);
			if(t == undefined || t == "undefined" || t == null){
				bl =false;
				break;
			}else{
				tp = t;
			}
		}
		if(bl){
			return eval("obj."+columnCode);
		}else{
			return null;
		}
	}
}

function goPage(){
	document.getElementById("pageMethod").value="go";
	eval(sysGridConfing.queryFunction+"()");
	document.getElementById("allshowdiv").scrollTop=0;
}

function getPager(){
	var pager = new Object();
	pager.pageSize = document.getElementById("showRows").value;
	pager.pageMethod = document.getElementById("pageMethod").value;;

	pager.currentPage = document.getElementById("currentPage").innerHTML;
	pager.totalPages = document.getElementById("pageCount").innerHTML;
	return pager;
}

//刷新数据
function startQuery(){
	document.getElementById("refreshDiv").style.display = "none";
	document.getElementById("waitingDiv").style.display = "";
}

function endQuery(){
	document.getElementById("refreshDiv").style.display = "";
	document.getElementById("waitingDiv").style.display = "none";
}

function getQueryParam(){
	var names = new Array();
	var values = new Array();
	if(document.getElementById('searchType').value == "0"){
		//简单查询
		var qptmp = document.getElementById("simpleQueryParam");
		if(qptmp!=null && qptmp!=undefined && qptmp != "undefined"){
			var name = DWRUtil.getValue("simpleQueryParam");
			var value = DWRUtil.getValue("simpleQueryValue");
			names[0] = name;
			values[0] = value;
		}
	}else{
		//复杂查询
		var c =0;
		for(var i=0;i<sysGridConfing.advancedlist.length;i++){
			var param = sysGridConfing.advancedlist[i].split("@@");
			if(eval(param[2])){
				names[c] = param[0];
				values[c] = eval(param[1]);
				c++;
			}
		}
	}
	return createObject(names,values);
}
//取得不封装入对象的属性，按顺序放入数组
function getCustomerParam(){
	var values = new Array();
	var c =0 ;
	for(var i=0;i<sysGridConfing.advancedlist.length;i++){
		var param = sysGridConfing.advancedlist[i].split("@@");
		if(!eval(param[2])){
			values[c] = eval(param[1]);
			c++;
		}
	}
	return values;
}
/**
 *属性和值封装为对象
 * names 属性集合
 * values 值集合
 **/
function createObject(names ,values){
	var obj = new Object();
	obj.orderPriority = sysGridConfing.orderPriority;
  	//开始循环处理
 	for(var c =0 ;c<names.length;c++){
  		var me = names[c];
  		var vl = values[c];
  		if(me!=null&&me.length>0){
	   		var tmp = me.split(".");
			if(tmp.length==1){
				eval("obj."+me+"='"+vl+"'");
			}else{
		   		var temp = new  Array(tmp.length);
		   		for(var i =0 ;i<tmp.length;i++){//复制对象
		   			temp[i] = tmp[i];
		   		}
		   		for(var a=0;a<temp.length-1;a++){//将最后一位以前转为对象
		   			//判断对象是否已存在,已存在取出，否则创建
		   			if(a==0){
		   				if(eval("obj."+temp[a]) instanceof Object){//判断是否为对象
		   					temp[a] = eval("obj."+temp[a]);//取出对象
		   				}else{
		   					temp[a] = new Object();
		   				}
		   			}else{
		   				if(eval("temp[a-1]."+temp[a]) instanceof Object){//判断是否为对象
		   					temp[a] = eval("temp[a-1]."+temp[a]);
		   				}else{
		   					temp[a] = new Object();
		   				}
		   			}
		   		}
		   		for(var b=temp.length-1;b>=0;b--){
		   			if(b-1>=0){//判断是否存在上级
		   				if(b==temp.length-1){//是否为最后一位	    						
		   					eval("temp[b-1]."+temp[b]+"='"+vl+"'");
		   				}else{
		   					eval("temp[b-1]."+tmp[b]+"=temp[b]");
		   				}
		   			}else{//不存在上级赋值给总体对象obj
		   				eval("obj."+tmp[b]+"=temp[b]");
		   			}
		   		}
	   		}
   		}
  	}
  	return obj;
}

//清空数据
function clearGridResult(){
	var retab = document.getElementById("tableResult");
	if(retab!=null&&retab != undefined&& retab != "undefined"){
		var rlen = retab.rows.length;	
		for(var i=rlen-1;i>=1;i--){
			retab.deleteRow(i);
		}
	}
	
	var rctab = document.getElementById("rowCountTable");
	if(rctab!=null&&rctab != undefined && rctab != "undefined"){
		var rlen = rctab.rows.length;	
		for(var i=rlen-1;i>=1;i--){
			rctab.deleteRow(i);
		}
	}
	
	var dptab = document.getElementById("dataProcessTable");
	if(dptab!=null&&dptab != undefined && dptab != "undefined"){
		var rlen = dptab.rows.length;	
		for(var i=rlen-1;i>=1;i--){
			dptab.deleteRow(i);
		}
	}
	
	if(sysGridConfing.isshowBlank){
		var rctabblank = document.getElementById("rowCountTableBlank");
		if(rctabblank!=null&&rctabblank != undefined && rctabblank != "undefined"){
			var rlen = rctabblank.rows.length;	
			for(var i=rlen-1;i>=0;i--){
				rctabblank.deleteRow(i);
			}
		}
		
		var dptabblank = document.getElementById("dataProcessTableBlank");
		if(dptabblank!=null&&dptabblank != undefined && dptabblank != "undefined"){
			var rlen = dptabblank.rows.length;	
			for(var i=rlen-1;i>=0;i--){
				dptabblank.deleteRow(i);
			}
		}
	}
	
	var imgre = document.getElementById("imgResult");
	if(imgre!=null&&imgre != undefined && imgre != "undefined"){
		var rlen = imgre.rows.length;	
		for(var i=rlen-1;i>=0;i--){
			imgre.deleteRow(i);
		}
	}

}

function resultToColumn(list){
	var columnArray = new Array();
	for(var i=0;i<list.length;i++){
		columnArray[i] = list[i].columnCode+","+list[i].columnName;
	}
}
function changeImgColumnData(data){
	//数据为空
	if(data == undefined || data == "undefined" || data == null){
		data = "-1";
	}
	return data;
}
/**将数据进行数字-字符串转换
 * obj 数据对象
 *data 需要转换的数据 repstr 转换方法
*/
function changeColumnData(obj,data,repstr,strcount){
	//数据为空
	if(data == undefined || data == "undefined" || data == null){
		data = "<无>";
	}
	if(repstr != null && repstr.length > 0 && repstr != "null"){
		data = eval(repstr+"(obj)");
	}
	var c = parseInt(strcount);
	if(c>0&&data.length>c){
		data = "<div title ='"+data+"'>"+data.substring(0,c)+"...</div>";
	}
	return data;
}

function changeShow(type){
	if(type == sysGridDefaultShow.defaultTable){
		if(vaildIsObj(document.getElementById("imgviewsize"))){
			document.getElementById("imgviewsize").style.display = "none";
		}
		
		if(vaildIsObj(document.getElementById("listimgprocess"))){
			document.getElementById("listimgprocess").style.display ="none";
		}
		
		if(vaildIsObj(document.getElementById("listtablecolumn"))){
			document.getElementById("listtablecolumn").style.display ="";
		}
		if(vaildIsObj(document.getElementById("listShow"))){
			document.getElementById("listShow").style.display = "";
		}
		
		if(vaildIsObj(document.getElementById("imgShow"))){
			document.getElementById("imgShow").style.display = "none";		
		}
		
		if(vaildIsObj(document.getElementById("dataimg"))){
			document.getElementById("dataimg").src =sysGridConfing.conpath+"/images/grid_images/datalist.png";		
		}
		
		if(vaildIsObj(document.getElementById("imglist"))){
			document.getElementById("imglist").src =sysGridConfing.conpath+"/images/grid_images/imglist_.png";
		}
	}else{
		if(vaildIsObj(document.getElementById("imgviewsize"))){
			document.getElementById("imgviewsize").style.display = "";
		}
		
		if(vaildIsObj(document.getElementById("listimgprocess"))){
			document.getElementById("listimgprocess").style.display ="";
		}
		
		if(vaildIsObj(document.getElementById("listtablecolumn"))){
			document.getElementById("listtablecolumn").style.display ="none";
		}
		
		if(vaildIsObj(document.getElementById("listShow"))){
			document.getElementById("listShow").style.display = "none";
		}
		
		if(vaildIsObj(document.getElementById("imgShow"))){
			document.getElementById("imgShow").style.display = "";
		}
		
		if(vaildIsObj(document.getElementById("dataimg"))){
			document.getElementById("dataimg").src =sysGridConfing.conpath+"/images/grid_images/datalist_.png";
		}
		
		if(vaildIsObj(document.getElementById("imglist"))){
			document.getElementById("imglist").src =sysGridConfing.conpath+"/images/grid_images/imglist.png";		
		}
		
	}
}

function vaildIsObj(obj){
	if(obj == undefined || obj == "undefined" || obj == null || typeof(obj) != "object"){
		return false;
	}
	return true;
}

function imgshowdetail(img){
	var imgshowck =document.getElementById("showorhiddenimgdiv");
	if(!imgshowck.checked) return;
	var imgdiv  = document.getElementById("imgdivshow");
	if(sysGridConfing.resultTempList==null) return;
	
	if(imgdiv.style.display == "block") return;
	
	var imgid = (img.id).split("_")[1];
	var obj = null;
	for(var i=0;i<sysGridConfing.resultTempList.length;i++){
		var tmp = sysGridConfing.resultTempList[i];
		var tmpid = eval("tmp."+sysGridConfing.gridIdPk);
		if(tmpid == imgid){
			obj = tmp;
			break;
		}
	}
	if(imgdiv!=null&& obj!=null){
		var row =0;
		
	    //创建内容
	    var table = document.createElement("table");
	    imgdiv.appendChild(table);
		table.style.cssText = "margin:0px;border:0px;padding:0px;";
		table.cellSpacing = 1;
		table.cellPadding=1;
		table.width ="100%";
		var th = table.insertRow(-1);
		row++;
		th.style.cssText = "height:22px;";
		var thd = th.insertCell(-1);
		thd.colSpan =2;
		thd.className = 'tableStyleSolidLine;';
		thd.style.cssText ="padding-left:5px;"; 
		thd.innerHTML = "<font color ='#336699'><b>详细信息</b></font> [双击查看更多明细]";
		for(var j=0;j<sysGridConfing.collist.length;j++){
			var tr = table.insertRow(-1);
			row++;
			tr.style.cssText = "height:22px;";
			var pars = sysGridConfing.collist[j].split("@@");
			var td1 = tr.insertCell(-1);
			td1.style.cssText ="text-align:center;background-color:#f6f6f6";
			td1.className = 'tableStyleSolidLine';
			td1.innerHTML = "<nobr>"+pars[3]+"</nobr>";
			var td2 = tr.insertCell(-1);
			td2.style.cssText ="padding-left:5px;"; 
			td2.className = 'tableStyleSolidLine';
			var columnCode = pars[0];//列表id
			var data = vaildityColumnData(obj,columnCode,pars);
			//判断是否需要列转换
			td2.innerHTML =changeColumnData(obj,data,pars[1],0);
		}
		
		//跟随鼠标移动显示
		var tp = event.clientY;
		var lf = event.clientX;
		var cw =document.body.clientWidth;//可用区域宽度
		var ch = document.body.clientHeight;//可见区域高度
		var zw = lf+sysGridConfing.imgdivWidth;
		var zh = tp +30*row;
		if(zw>cw){
			lf +=(cw-zw-3); 
		}
		if(zh>ch){
			tp +=(ch-zh-3); 
		}
		imgdiv.style.top = tp;
	    imgdiv.style.left = lf;
		
		imgdiv.style.display = "block";
	}
}

function imghiddendetail(img){
	var imgshowck =document.getElementById("showorhiddenimgdiv");
	if(!imgshowck.checked) return;
	var imgdiv  = document.getElementById("imgdivshow");
	if(imgdiv!=null){ 
		imgdiv.innerHTML ="";
		imgdiv.style.display = "none";
	}
}

//设置图片滚动条
function createslider(){
	var slider1 = new dhtmlxSlider("sliderdiv", {
        skin: "arrowgreen",
        min:25,
        max:200,
        step:25,
        size:180,
        value:100,
        vertical:false
    });
    slider1.attachEvent("onChange",changeimgsize);
    slider1.init();
    return slider1;
}


//根据pager创建滚动条
function createsliderByPager(){
	var slider2 = new dhtmlxSlider("pagesendsliderdiv", {
        skin: "arrowgreen",
        min:1,
        step:1,
        size:100,
        vertical:false
    });
    slider2.linkTo("culabel");
    slider2.init();
    return slider2;
}

//快速跳转页
function selectcpage(obj){
	if(sysGridConfing.sliderpagetmp==null){
		return;
	}
	if(obj.innerHTML == "" || obj.innerHTML == "0"){
		return;
	}
	var totle = document.getElementById("pageCount").innerHTML;
	if(totle==""||totle=="0"||totle=="1"){
		return;
	}
	sysGridConfing.sliderpagetmp.setValue(obj.innerHTML);
	var pdiv=document.getElementById("pagesenddiv");
    pdiv.style.display = "block";
    pdiv.style.right = 0;
    pdiv.style.bottom = 24;
}

function pagetosend(){
	if(sysGridConfing.sliderpagetmp!=null){
		var pz=sysGridConfing.sliderpagetmp.getValue();
		var cp = document.getElementById("currentPage");
		if(pz != cp.innerHTML){
			cp.innerHTML = pz;
			goPage();
		}
	}
	document.getElementById("pagesenddiv").style.display = "none";
}
function changeimgsize(pos,slider1){
	var imgs =document.getElementsByName("imageshitu");
	var cwidth= null;
	var cheight = null;
	if(sysGridConfing.imgwidth!=null&&sysGridConfing.imgwidth.length>0&&sysGridConfing.imgwidth != "null"){
		if(sysGridConfing.imgwidth.toLowerCase()!="auto"){
			cwidth = sysGridConfing.imgwidth*pos/100+"px";
		}
	}
	if(sysGridConfing.imgheight!=null&&sysGridConfing.imgheight.length>0&&sysGridConfing.imgheight != "null"){
		if(sysGridConfing.imgheight.toLowerCase() != "auto"){
			cheight = sysGridConfing.imgheight*pos/100+"px";
		}
	}
	for(var a=0;a<imgs.length;a++){
		if(cwidth !=null){
			imgs[a].style.width = cwidth;
		}
		if(cheight!=null){
			imgs[a].style.height = cheight;
		}
	}
}

function changimg(a){
	if(sysGridConfing.sildertmp!=null){
		var val = sysGridConfing.sildertmp.getValue();
		if(a ==1){
			if(val-25>=25){
				sysGridConfing.sildertmp.setValue(val-25);
				changeimgsize(val-25,sysGridConfing.sildertmp);
			}
		}else if(a==2){
			if(val+25<=200){
				sysGridConfing.sildertmp.setValue(val+25);
				changeimgsize(val+25,sysGridConfing.sildertmp);
			}
		}
	}
}

//获取选中行数据对象
function getRowsObject(){
	var dataArray = new Array();
	var records= getAllRecordArray();
	var resultList = sysGridConfing.resultTempList;
	var c=0;
	if(records!=false&&resultList!=null){
		for(var i=0;i<resultList.length;i++){
			var tmp = resultList[i];
			var tmpid = eval("tmp."+sysGridConfing.gridIdPk);
			for(var j=0;j<records.length;j++){
				if(tmpid == records[j]){
					dataArray[c] = tmp;
					c++;
					break;
				}
			}
		}
	}
	return dataArray;
}

//根据主键获取数据对象
function getObjectByPk(pk){
	var obj =null;
	var resultList = sysGridConfing.resultTempList;
	if(resultList!=null){
		for(var i=0;i<resultList.length;i++){
			var tmp = resultList[i];
			var tmpid = eval("tmp."+sysGridConfing.gridIdPk);
			if(tmpid == pk){
				obj = tmp;
				break;
			}
		}
	}
	return obj;
}
var idstemparray = new Array();
idstemparray[0] = "currentPage";
idstemparray[1] = "pagesendtable";
idstemparray[2] = "pagesendsliderdiv";
idstemparray[3] = "gridpagesendbtn";
idstemparray[4] = "pagesenddiv";
idstemparray[5] = "labelshowtd";

//关闭快速跳转
document.onclick=function(event){
	try{
		var event  = window.event || event;
	    var obj = event.srcElement ? event.srcElement : event.target;
	    if(sysGridConfing.sliderpagetmp!=null){
	    	if(obj.parentNode!=null&&obj.parentNode.tagName=="DIV"){
	    		var pcname = obj.parentNode;
	    		if(pcname.className == 'dhtmlxSlider_arrowgreen' || pcname.id == 'pagesendsliderdiv'){
	    			return;
	    		}
	    	}
	    }
		for(var i=0;i<idstemparray.length;i++){
			var tmp = document.getElementById(idstemparray[i]);
			if(tmp == undefined || tmp == "undefined" || tmp ==null || obj == tmp){
				return;
			}
		}
       var pdiv = document.getElementById("pagesenddiv");
       if(pdiv!=null){
       		pdiv.style.display = "none";
       }
   }catch(err){}
}


function ts_resortTable2(lnk, column) {
	var span;
	var str;
	var orderType;
	for (var ci=0;ci<lnk.childNodes.length;ci++) {
		if (lnk.childNodes[ci].tagName && lnk.childNodes[ci].tagName.toLowerCase() == 'span') 
			span = lnk.childNodes[ci];
	}
	
	if (span.getAttribute("sortdir") == 'asc') {
		str = "<font color='red' title='从高至低排序'>↑</font>";
		span.setAttribute('sortdir','desc');
		orderType = "desc";
	} else {
		str = "<font color='green' title='从低至高排序'>↓</font>";
		span.setAttribute('sortdir','asc');
		orderType = "asc";
	}
	
	var allspans = document.getElementsByTagName("span");
	for (var ci=0;ci<allspans.length;ci++) {
		if (allspans[ci].className == 'sortarrow') {
			allspans[ci].innerHTML = ' ';
		}
	}
	
	sysGridConfing.orderPriority = column + " " + orderType;
	span.innerHTML = str;
	
	eval(sysGridConfing.queryFunction+"()");
}