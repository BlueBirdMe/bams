String.prototype.Trim = function(){ 
	return this.replace(/^\s+/g,"").replace(/\s+$/g,""); 
} 

function JSCookie(){ 
	this.GetCookie = function(key){ 
		var cookie = document.cookie; 
		var cookieArray = cookie.split(';'); 
		var getvalue = ""; 
		for(var i = 0;i<cookieArray.length;i++){ 
			if(cookieArray[i].Trim().substr(0,key.length) == key){ 
				getvalue = cookieArray[i].Trim().substr(key.length + 1); 
				break; 
			} 
		}
		return getvalue; 
	}; 
	this.GetChild = function(cookiekey,childkey){ 
		var child = this.GetCookie(cookiekey); 
		var childs = child.split('&'); 
		var getvalue = ""; 
		for(var i = 0;i < childs.length;i++){ 
			if(childs[i].Trim().substr(0,childkey.length) == childkey){ 
				getvalue = childs[i].Trim().substr(childkey.length + 1); 
				break;
			}
		}
		return getvalue; 
	}; 
	this.SetCookie = function(key,value,expire,domain,path){ 
		var cookie = ""; 
		if(key != null && value != null) 
		cookie += key + "=" + value + ";"; 
		if(expire != null) 
		cookie += "expires=" + expire.toGMTString() + ";"; 
		if(domain != null) 
		cookie += "domain=" + domain + ";"; 
		if(path != null) 
		cookie += "path=" + path + ";"; 
		document.cookie = cookie; 
	}; 
	this.Expire = function(key){ 
		expire_time = new Date(); 
		expire_time.setFullYear(expire_time.getFullYear() - 1); 
		var cookie = " " + key + "=e;expires=" + expire_time + ";" 
		document.cookie = cookie; 
	} 
} 
