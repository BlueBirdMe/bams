<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common.jsp" %>
<%
    String tab = request.getParameter("tab");
    String pk = request.getParameter("pk");
    String isedit = "false";
    String saveOrEdit = "新增";
    String helpTitle = "您可以在此处添加您想新增的个人信息！";
    if(pk != null){
        isedit = "true";
        saveOrEdit = "编辑";
        helpTitle = "您可以在此处编辑个人信息信息！";
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=saveOrEdit%>个人信息</title>
<script type="text/javascript" src="<%=contextPath%>/dwr/interface/dwrDxUserinfoService.js"></script>
<script type="text/javascript">
window.onload = function(){
    useLoadingMassage();
    initInput("helpTitle","<%=helpTitle%>");
    saveOrEdit();
}
function saveOrEdit(){
    if(<%=isedit%>){
        var pk = '<%=pk%>';
        dwrDxUserinfoService.getDxUserinfoByPk(pk,setDxuserinfo);
    }
}

function setDxuserinfo(data){
    if(data.success == true){
        if(data.resultList.length > 0){
            var dxuserinfo = data.resultList[0];
        }else{
            alert(data.message);
        }
    }else{
        alert(data.message);
    }
}
function save(){
    var warnArr = new Array();
    //清空所有信息提示
    warnInit(warnArr);
    var bl = validvalue('helpTitle');
    if(bl){
        //此处可编写js代码进一步验证数据项

        //Btn.close();
        if(<%=isedit%>){
            dwrDxUserinfoService.updateDxUserinfo(getDxuserinfo(),updateCallback);
        }else{
            dwrDxUserinfoService.saveDxUserinfo(getDxuserinfo(),saveCallback);
        }
    }
}
function getDxuserinfo(){
    var dxuserinfo = new Object();
    if(<%=isedit%>){
        dxuserinfo.primaryKey = '<%=pk%>';
    }
    return dxuserinfo;
}
function saveCallback(data){
    //Btn.open();
    if(data.success){
        confirmmsgAndTitle("添加个人信息成功！是否想继续添加个人信息？","reset();","继续添加","closePage();","关闭页面");
    }else{
        alertmsg(data);
    }
}
function updateCallback(data){
    //Btn.open();
    if(data.success){
        alertmsg(data,"closePage();");
    }else{
        alertmsg(data);
    }
}
function reset(){
    Sys.reload();
}
function closePage(){
    closeMDITab(<%=tab%>);
}
</script>
</head>
<body class="inputcls">
    <div class="formDetail">
        <div class="requdiv"><label id="helpTitle"></label></div>
        <div class="formTitle"><%=saveOrEdit%>个人信息</div>
        <table class="inputtable">
        </table>
    </div>
    <table align="center">
        <tr>
            <td><btn:btn onclick="save();" value="保 存 " imgsrc="../../images/png-1718.png" title="保存个人信息信息" /></td>
            <td style="width:20px;"></td>
            <td><btn:btn onclick="closePage();" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"/></td>
        </tr>
    </table>
</body>
</html>
