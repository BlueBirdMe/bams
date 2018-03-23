function SysTab(path,index,tid){
	if(index==null||index=="undefined"){
		index =1;
	}
	var dc=document;
	if(tid != null && tid != "undefined"){
		dc = document.getElementById(tid);
	}
	
	this.TabStyle = "default";
	document.write("<link type='text/css' rel='stylesheet' href='"+path+"/js/tabJs/skin/"+this.TabStyle+"/tab.css' />");
	
	var Tabs = new Array();
	var TabsLi = new Array();
	var divs = dc.getElementsByTagName("div");
	var c =0;
	for(var t=0;t<divs.length;t++){
		if(divs[t].className == "tagContent"){
			Tabs[c] = divs[t];
			c++;
		}
	}
	if(index>1 && index>Tabs.length){
		index =1;
	}
	var uls = dc.getElementsByTagName("ul");
	for(var i=0;i<uls.length;i++){
		if(uls[i].className == "tags"){
			var lis = uls[i].getElementsByTagName("li");
			TabsLi = lis;
			for(var j=0;j<lis.length;j++){
				if(j+1 == index){
					lis[j].className = "selectTag";
				}
			}
		}
	}
	for(var i=0; i<Tabs.length;i++){
		if(i+1==index){
			Tabs[i].style.display = "block";
		}
	}
	
	this.selectTag=function(tli){
		var a =0;
		for(var j=0;j<TabsLi.length;j++){
			
			if(tli.parentNode == TabsLi[j]){
				TabsLi[j].className = "selectTag";
				a=j;
			}else{
				TabsLi[j].className = "";
			}
		}
		for(var i=0; i<Tabs.length;i++){
			if(a==i){
				Tabs[i].style.display = "block";
			}else{
				Tabs[i].style.display = "none";
			}
		}
	};
	this.setSelectTag = function(id){
		if(id>1 && i>Tabs.length){
			id =1;
		}
		var uls = dc.getElementsByTagName("ul");
		for(var i=0;i<uls.length;i++){
			if(uls[i].className == "tags"){
				var lis = uls[i].getElementsByTagName("li");
				TabsLi = lis;
				for(var j=0;j<lis.length;j++){
					if(j+1 == id){
						lis[j].className = "selectTag";
					}else{
						lis[j].className = "";
					}
				}
			}
		}
		for(var i=0; i<Tabs.length;i++){
			if(i+1==id){
				Tabs[i].style.display = "block";
			}else{
				Tabs[i].style.display = "none";
			}
		}
	}
}
