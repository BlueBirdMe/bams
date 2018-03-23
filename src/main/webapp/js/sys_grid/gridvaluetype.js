//根据类型获取值
//文本框 下拉框 其他
function getTextValue(id){
	return DWRUtil.getValue(id);
}
function getSelectValue(id){
	return getTextValue(id);
}

function getCheckValue(id){
	if(document.getElementById(id).checked == true){
		return document.getElementById(id).value;
	}else{
		return -1;
	}
}

//单选框
function getRadioValue(RadioName){
    var obj;    
    obj=document.getElementsByName(RadioName);
    if(obj!=null){
        var i;
        for(i=0;i<obj.length;i++){
            if(obj[i].checked){
                return obj[i].value;            
            }
        }
    }
    return null;
}

//日期
function getDateValue(id){
	var str="";
	if(document.getElementById(id+"StartDate")!=null){
		if(document.getElementById(id+"StartDate").value.length>0){
			str+=DWRUtil.getValue(id+"StartDate")+",";
		}
	}
	if(document.getElementById(id+"EndDate")!=null){
		if(document.getElementById(id+"EndDate").value.length>0){
			str+=DWRUtil.getValue(id+"EndDate")+",";
		}
	}
	if(str.length>0){
		str = str.substring(0,str.length-1);
	}
	return str;
}

//时间
function getTimeValue(id){
	var str="";
	if(document.getElementById(id+"StartTime")!=null&&document.getElementById(id+"StartTime").value.length>0){
		str+=DWRUtil.getValue(id+"StartTime")+",";
	}
	if(document.getElementById(id+"EndTime")!=null&&document.getElementById(id+"EndTime").value.length>0){
		str+=DWRUtil.getValue(id+"EndTime")+",";
	}
	if(str.length>0){
		str = str.substring(0,str.length-1);
	}
	return str;
}
