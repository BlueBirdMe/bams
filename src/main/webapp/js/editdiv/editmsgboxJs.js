/**动画显示编辑等页面**/
var MoveDivConfig = {
	isShield:true,
	defaultShowTop:100,
	defaultCloseTop:50,
	showTimerSplit:3,
	closeTimerSplit:3,
	moveStep:20,
	defaultHeight:50
};

var MoveDiv ={
	frmname:"processloadfrm",
	titlename:"editmsgtitle",
	parentdivname:"processdivObj",
	maxbtnname:"editmsgdivmaxbtn",
	shielddivname:"shielddiv",
	showTimer:null,
	closeTimer:null,
	showFilter:20,
	show:function(title,url,wp){
		if(wp==null){
			wp =window;
		}
		wp.document.getElementById(this.titlename).innerHTML = title;
		wp.document.getElementById(this.frmname).src=url;
		var objfrmdiv = wp.document.getElementById(this.parentdivname);
		objfrmdiv.style.display="block";
		objfrmdiv.style.opacity = parseInt(this.showFilter)/100;
		objfrmdiv.style.filter = "alpha(opacity="+this.showFilter+")";
		if(MoveDivConfig.isShield){
			var sd = wp.document.getElementById(this.shielddivname);
			sd.style.display="block";
		}
		this.showTimer = window.setInterval(function(){try{
			MoveDiv.showFilter = parseInt(MoveDiv.showFilter)+parseInt(MoveDivConfig.moveStep);
			if(parseInt(MoveDiv.showFilter)>=100){
				if(MoveDiv.showTimer!=null){
					window.clearInterval(MoveDiv.showTimer);
				}
				objfrmdiv.style.opacity = 1;
				objfrmdiv.style.filter = "alpha(opacity=100)";
			}else{
				objfrmdiv.style.opacity = parseInt(MoveDiv.showFilter)/100;
				objfrmdiv.style.filter = "alpha(opacity="+MoveDiv.showFilter+")";
			}
		}catch(e){}},MoveDivConfig.showTimerSplit);
	},
	close:function(wp){
		if(wp==null){
			wp =window;
		}
		wp.document.getElementById(this.titlename).innerHTML = "";
		wp.document.getElementById(this.frmname).src="";
		var objfrmdiv = wp.document.getElementById(this.parentdivname);
		objfrmdiv.style.height=MoveDivConfig.defaultHeight+"%";
		this.showTop = MoveDivConfig.defaultShowTop;
		objfrmdiv.style.opacity = parseInt(this.showFilter)/100;
		objfrmdiv.style.filter = "alpha(opacity="+this.showFilter+")";
		var mbtn = wp.document.getElementById(this.maxbtnname);
		objfrmdiv.style.top="50%";
		mbtn.className = "maxbtn";
		mbtn.title="全屏";
		var sd = null;
		if(MoveDivConfig.isShield){
			sd = wp.document.getElementById(this.shielddivname);
		}
		this.closeTimer = window.setInterval(function(){try{
			MoveDiv.showFilter =parseInt(MoveDiv.showFilter)- parseInt(MoveDivConfig.moveStep);
			if(parseInt(MoveDiv.showFilter)<=10){
				if(MoveDiv.closeTimer!=null){
					window.clearInterval(MoveDiv.closeTimer);
				}
				objfrmdiv.style.opacity = 0.1;
				objfrmdiv.style.filter = "alpha(opacity=10)";
				if(sd!=null){
					sd.style.display="none";
				}
				objfrmdiv.style.display="none";
			}else{
				objfrmdiv.style.opacity = parseInt(MoveDiv.showFilter)/100;
				objfrmdiv.style.filter = "alpha(opacity="+MoveDiv.showFilter+")";
			}
		}catch(e){}},MoveDivConfig.closeTimerSplit);
		
		
	},
	max:function(wp){
		if(wp==null){
			wp =window;
		}
		var objfrmdiv = wp.document.getElementById(this.parentdivname);
		var mbtn = wp.document.getElementById(this.maxbtnname);
		if(objfrmdiv.style.top=="5%"){
			mbtn.className = "maxbtn";
			mbtn.title="全屏";
			objfrmdiv.style.top=(100-MoveDivConfig.defaultHeight)+"%";
			objfrmdiv.style.height=MoveDivConfig.defaultHeight+"%";
		}else{
			mbtn.className = "maxbtn_nor";
			mbtn.title="恢复";
			objfrmdiv.style.top="5%";
			objfrmdiv.style.height="94%";
		}
	}
};