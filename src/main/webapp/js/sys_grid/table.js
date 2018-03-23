
var highlightcolor = "#c0bebe";
//选中后颜色
var clickcolor = "#c8e6fa";
//存储选中结果集
var clickRecordList = "";
//存储结果集表格隔行换色旧颜色
var oldcname ="";
//存储图片表格隔行换色旧颜色
var oldbackcolor = "";
function changeto() {
	source = event.srcElement;
	if (source.tagName == "TR" || source.tagName == "TABLE") {
		return;
	}
	while (source.tagName != "TD") {
		source = source.parentElement;
	}
	source = source.parentElement;
	cs = source.children;
	if (cs[0].style.backgroundColor != highlightcolor && source.id != "nc" && cs[0].style.backgroundColor != clickcolor) {
		for (i = 0; i < cs.length; i++) {
			cs[i].style.backgroundColor = highlightcolor;
		}
	}
}
function changeback() {
	if (event.fromElement.contains(event.toElement) || source.contains(event.toElement) || source.id == "nc") {
		return;
	}
	if (event.toElement != source && cs[0].style.backgroundColor != clickcolor) {
	}
	for (i = 0; i < cs.length; i++) {
		cs[i].style.backgroundColor = "";
	}
}
function SetTRcolorOver(objTR, cssOnName){
	oldcname = objTR.className;
    objTR.className = cssOnName;
}

function SetTRcolorOut(objTR){
    objTR.className = oldcname;
}
//全选、取消按钮事件
function rowCountTableTdTopCheckFun(obj){
	var ck = obj.checked;
	setAllRowcountTableInput(ck);
	var tb = document.getElementById("rowCountTable");
	var tbcks = tb.getElementsByTagName("input");
	if(tbcks.length>0){
		for(var i=1;i<tbcks.length;i++){
			if(tbcks[i].getAttribute("type") == "checkbox"){
				var cid = tbcks[i].value;
				var ck = obj.checked;
				setbgColorByCheckboxStatus(cid,ck);
			}
		}
	}
}
//根据checkbox值及状态设置背景
function setbgColorByCheckboxStatus(cid,status){
	var recordcolor="";
	if (status) {
		recordcolor = clickcolor;
	} else {
		recordcolor = "";
	}
	tdbackcolor("record_", cid, recordcolor);
	tdbackcolor("img_", cid, recordcolor);
}

//2013-11-06 JC添加
//用于数据列表初始化时，高亮显示某行数据
function setbgColor(cid,color){
	var recordcolor="";
	if(color == null || color == undefined || color == "undefined"){
		color = "yellow";
	}
	recordcolor = color;
	tdbackcolor("record_", cid, recordcolor);
	tdbackcolor("img_", cid, recordcolor);
}

//checkbox事件
function sysgridchecked(obj){
	var trid = obj.value;
	var ck = obj.checked;
	setbgColorByCheckboxStatus(trid,ck);
	if(ck){
		if(getThCheckBoxIsAllChecked()){
			setRowCountTableTdTopCheckboxStatus(true);
		}else{
			setRowCountTableTdTopCheckboxStatus(false);
		}
	}else{
		setRowCountTableTdTopCheckboxStatus(false);
	}
}

//设置全选、取消checkbox状态
function setRowCountTableTdTopCheckboxStatus(ck){
	var rcbox = document.getElementById("rowCountTableTdTopCheck");
	if(rcbox == null || rcbox == undefined || rcbox == "undefined"){
		return;
	}
	rcbox.checked = ck;
}

//单击行事件
function clickto(event) {
	var event  = window.event || event;
    var source = event.srcElement ? event.srcElement : event.target;
	if (source.tagName == "TR" || source.tagName == "TABLE") {
		return;
	}
	while (source.tagName != "TD") {
		source = source.parentElement;
	}
	source = source.parentElement;
	var t = source.id;
	var ts = t.split("_");
	var imgcolor = "";
	var chk = false;
	if (source.tagName == "TR") {
		if (source.style.backgroundColor != clickcolor && source.id != "nc") {
			clearAllResultTableBgColor();
			
			source.style.backgroundColor = clickcolor;
			imgcolor = clickcolor;
			chk = true;
			//去除其他选中
			setAllRowcountTableInput(false);
			//选中checkbox
			setThCheckBoxChecked(ts[1],chk);
		} else {
			source.style.backgroundColor = "";
			imgcolor = "";
			//去除当前行选中状态
			setThCheckBoxChecked(ts[1],false);
		}
		tdbackcolor("img_", ts[1], imgcolor);
	}
}



//去除结果集行背景色
function clearAllResultTableBgColor(){
	var tab = document.getElementById("tableResult");
	for(var a = 1;a<tab.rows.length;a++){
		tab.rows[a].style.backgroundColor = "";
		tdbackcolor("img_", (tab.rows[a].id).split("_")[1], "");
	}
}

//去除图片背景色
function clearAllImgTableBgColor(){
	var tab = document.getElementById("imgResult");
	for(var a = 0;a<tab.rows.length;a++){
		var cell = tab.rows[a].cells;
		for(var b = 0;b<cell.length;b++){
			cell[b].style.backgroundColor = "";
			tdbackcolor("record_", (cell[b].id).split("_")[1], "");
		}
	}
}
//设置所有checkbox状态
function setAllRowcountTableInput(chk){
	var tb = document.getElementById("rowCountTable");
	var tbcks = tb.getElementsByTagName("input");
	if(tbcks.length>0){
		for(var i=1;i<tbcks.length;i++){
			if(tbcks[i].getAttribute("type") == "checkbox"){
				tbcks[i].checked = chk;
			}
		}
	}
}
//判断是否所有checkbox已选中
function getThCheckBoxIsAllChecked(){
	var ischecked = true;
	var tb = document.getElementById("rowCountTable");
	var tbcks = tb.getElementsByTagName("input");
	if(tbcks.length>0){
		for(var i=1;i<tbcks.length;i++){
			if(tbcks[i].getAttribute("type") == "checkbox"&&!tbcks[i].checked){
				ischecked = false;
				break;
			}
		}
	}else{
		ischecked = false;
	}
	return ischecked;
}

//取得所有被选中checkbox值
function getThCheckBoxChecked(){
	var chekcedvals =new Array();
	var tb = document.getElementById("rowCountTable");
	var tbcks = tb.getElementsByTagName("input");
	if(tbcks.length>0){
		var c=0;
		for(var i=1;i<tbcks.length;i++){
			if(tbcks[i].getAttribute("type") == "checkbox"&&tbcks[i].checked){
				chekcedvals[c] = tbcks[i].value; 
				c++;
			}
		}
	}
	return chekcedvals;
}

//设置单个checkbox状态
function setThCheckBoxChecked(sid,chk){
	var rowcounttable = document.getElementById("gridcheckbox_"+sid);
	var cks =rowcounttable.getElementsByTagName("input");
	if(cks.length>0&&cks[0].getAttribute("type") == "checkbox"){
		cks[0].checked = chk;
		if(chk){
			if(getThCheckBoxIsAllChecked()){
				setRowCountTableTdTopCheckboxStatus(true);
			}else{
				setRowCountTableTdTopCheckboxStatus(false);
			}
		}else{
			setRowCountTableTdTopCheckboxStatus(false);
		}
	}
}


//单击图片事件
function clickimg(event) {
	var event  = window.event || event;
    var source = event.srcElement ? event.srcElement : event.target;
	if (source.tagName == "TR" || source.tagName == "TABLE") {
		return;
	}
	
	if(source.tagName == "TD"){
		source = source;
	}
	if (source.tagName == "IMG" || source.tagName == "LABEL") {
		source = source.parentElement;
	}
	var t = source.id;
	var ts = t.split("_");
	var recordcolor = "";
	var chk = false;

	if (source.style.backgroundColor != clickcolor) {
		clearAllImgTableBgColor();
		
		source.style.backgroundColor = clickcolor;
		recordcolor = clickcolor;
		chk = true;
		
		//去除其他选中
		setAllRowcountTableInput(false);
		//选中checkbox及
		setThCheckBoxChecked(ts[1],chk);
	} else {
		source.style.backgroundColor = "";
		recordcolor = "";
		
		//去除当前行选中状态
		setThCheckBoxChecked(ts[1],false);
	}
	tdbackcolor("record_", ts[1], recordcolor);
}
function imgmouseover(img) {
	oldbackcolor = img.style.backgroundColor;
	if (oldbackcolor.length == 0) {
		img.style.backgroundColor = highlightcolor;
	}
}
function imgmouseout(img) {
	img.style.backgroundColor = oldbackcolor;
}

//处理是否选中背景色
function tdbackcolor(sign, id, color) {
	oldbackcolor = color;
	var timg = document.getElementById(sign + id);
	if (timg != null) {
		timg.style.backgroundColor = color;
	}
}

//获取记录数组
function getAllRecordArray() {
	var vals = getThCheckBoxChecked();
	if(vals.length>0){
		return vals;
	}else{
		return false;
	}
}

//获取记录数组
function getOneRecordArray() {
	var vals = getThCheckBoxChecked();
	if(vals.length>0){
		if(vals.length>1){
			return false;
		}else{
			return vals[0];
		}
	}else{
		return false;
	}
}
function clickToCheck(name, obj) {
	source = event.srcElement;
	if (source.tagName == "INPUT") {
		return;
	}
	if (source.tagName == "TR" || source.tagName == "TABLE") {
		return;
	}
	while (source.tagName != "TD") {
		source = source.parentElement;
	}
	source = source.parentElement;
	cs = source.children;
	if (cs[0].style.backgroundColor != clickcolor && source.id != "nc") {
		for (i = 0; i < cs.length; i++) {
			cs[i].style.backgroundColor = clickcolor;
		}
	} else {
		for (i = 0; i < cs.length; i++) {
			cs[i].style.backgroundColor = "";
		}
	}
	clickCheckBox(name + obj.id);
}

