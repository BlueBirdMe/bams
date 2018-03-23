jQuery(document).ready(function() {
	initHeight();
	
	var qm = document.getElementById("queryMore");
	if (qm != null && qm != "undefined" && qm != undefined) {
		$("#queryMore").toggle(function() {
			$("#queryParam").show();
			document.getElementById("queryImg").src = formStylePath.getImagePath() + "grid_images/uup.png";
			document.getElementById('searchType').value = "1";
			var queryvalue = document.getElementById("simpleQueryValue");
			if (queryvalue != null && queryvalue != "undefined" && queryvalue != undefined) {
				queryvalue.value = "";
				queryvalue.disabled = true;
				queryvalue.style.backgroundColor = "#EEEEEE";
			}
			subHeight($("#queryParam").height());
		}, function() {
			addHeight($("#queryParam").height());
			$("#queryParam").hide();
			document.getElementById("queryImg").src = formStylePath.getImagePath() + "grid_images/ddn.png";
			document.getElementById('searchType').value = "0";
			var queryvalue = document.getElementById("simpleQueryValue");
			if (queryvalue != null && queryvalue != "undefined" && queryvalue != undefined) {
				queryvalue.disabled = false;
				queryvalue.style.backgroundColor = "#FFFFFF";
			}
		});
	}
	
	var tit = document.getElementById("helpProcessTitle");
	if (tit != null && tit != "undefined" && tit != undefined) {
		$("#helpProcessTitle").toggle(function() {
			$("#processTitleTable").show();
			document.getElementById("helptitleImg").src = formStylePath.getImagePath() + "grid_images/uup.png";
			subHeight($("#processTitleTable").height());
		}, function() {
			addHeight($("#processTitleTable").height());
			$("#processTitleTable").hide();
			document.getElementById("helptitleImg").src = formStylePath.getImagePath() + "grid_images/ddn.png";
		});

	}
	
	var sm = document.getElementById("showMore");
	if (sm != null && sm != "undefined" && sm != undefined) {
		$("#showMore").toggle(function() {
			$("#showParam").show();
			document.getElementById("showImg").src = formStylePath.getImagePath() + "grid_images/uup.png";
			subHeight($("#showParam").height());
		}, function() {
			addHeight($("#showParam").height());
			$("#showParam").hide();
			document.getElementById("showImg").src = formStylePath.getImagePath() + "grid_images/ddn.png";
		});
	}

	document.getElementById('searchType').value = "0";
});

function initHeight(){
	$("#allshowdiv").css("height", $("#allshowtd").height());
}

function addHeight(h){
	$("#allshowdiv").css("height", $("#allshowtd").height() + h);
}

function subHeight(h){
	$("#allshowdiv").css("height", $("#allshowtd").height() - h);
}

function colsControl(table, num, isShow) {
	for (i = 0; i < table.rows.length; i++) {
		var td = table.rows[i].cells[num];
		var str = isShow ? "block" : "none";
		td.style.display = str;
	}
}

function showQueryParam(){
	if ($("#queryParam").is(":hidden")) {
		$("#queryMore").trigger("click");//模拟click事件
	}
}
