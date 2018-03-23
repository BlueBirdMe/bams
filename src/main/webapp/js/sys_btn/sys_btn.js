var Btn ={
	btnTableOver:function(btn) {
		var tds = btn.getElementsByTagName("td");
		for (var i = 0; i < tds.length; i++) {
			if (tds[i].className == "linetd") {
				tds[i].className = "linetd_hover";
			} else {
				if (tds[i].className == "inputtd") {
					tds[i].className = "inputtd_hover";
					var tmps = tds[i].childNodes;
					for (var j = 0; j < tmps.length; j++) {
						tmps[j].className = "btn_table_hover";
					}
				}
			}
		}
	},
	btnTableOut:function(btn) {
		var tds = btn.getElementsByTagName("td");
		for (var i = 0; i < tds.length; i++) {
			if (tds[i].className == "linetd_hover") {
				tds[i].className = "linetd";
			} else {
				if (tds[i].className == "inputtd_hover") {
					tds[i].className = "inputtd";
					var tmps = tds[i].childNodes;
					for (var j = 0; j < tmps.length; j++) {
						tmps[j].className = "btn_table";
					}
				}
			}
		}
	},
	open:function(id){
		if(id == null){
			id = "button_submit";
		}
		document.getElementById(id).disabled = false;
	},
	close:function(id){
		if(id == null){
			id = "button_submit";
		}
		document.getElementById(id).disabled = true;
	},
	hidden:function(id){
		document.getElementById(id).style.display="none";
	},
	show:function(id){
		document.getElementById(id).style.display="";
	}
};
