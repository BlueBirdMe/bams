$(document).ready(function() {
	var sl = $("#split_l");
	//isNotBlank 只用于DOM对象，JQ对象需要转化一下
	if (sl != null && isNotBlank(sl[0])) {
		sl.after("<td id='split_c'><div class='cd' title='点击收缩'><div class='cb'></div></div></td>");
		$("#split_c").click(function() {
			// 普通效果
			if (sl.is(":hidden")) {
				sl.show();
				$("#split_c .cb").css("background-position", "-1px 0px");
			} else {
				sl.hide();
				$("#split_c .cb").css("background-position", "-18px 0px");
			}
		});

		//高度动态设置
		var slHeight = sl.height();

		var i = $("#split_l .div_title").size();
		
		//左侧分一栏 涉及 div_content
		if (i == 1) {
			var titleHeight = $("#split_l .div_title").height();
			$("#split_l .div_content").css("height", slHeight - titleHeight).show();
		}
		//左侧分两栏 涉及 div_content_top、div_content_bottom
		if (i == 2) {
			var titleHeight = $("#split_l .div_title").height();
			var bottomHeight = $("#split_l .div_content_bottom").height();
			$("#split_l .div_content_top").css("height", slHeight - titleHeight * 2 - bottomHeight).show();
		}
	}
});