<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../common.jsp" %>
<%
    String tab = request.getParameter("tab");
    String pk = request.getParameter("pk");
    String isedit = "false";
    String saveOrEdit = "新增";
    String helpTitle = "您可以在此处添加您想新增的认证信息！";
    if (pk != null) {
        isedit = "true";
        saveOrEdit = "编辑";
        helpTitle = "您可以在此处编辑认证信息信息！";
    } else {
        SessionUser user = (SessionUser) LoginContext.getSessionValueByLogin(request);
        HrmEmployee empinfo = user.getEmployeeInfo();
        pk = empinfo.getHrmEmployeeCode();
        isedit = "true";
        saveOrEdit = "编辑";
        helpTitle = "您可以在此处编辑认证信息信息！";
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><%=saveOrEdit%>认证信息</title>
    <script type="text/javascript" src="<%=contextPath%>/dwr/interface/dwrDxArchivementService.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/dwr/interface/dwrDxArchivementTempService.js"></script>
    <script type="text/javascript">
        window.onload = function () {
            useLoadingMassage();
            initInput("helpTitle", "<%=helpTitle%>");
            saveOrEdit();
        }
        function saveOrEdit() {
            if (<%=isedit%>) {
                var pk = '<%=pk%>';
                dwrDxArchivementService.getModDxArchivementByPk(pk, setDxarchivement);
            }
        }

        function setDxarchivement(data) {
            if (data.success == true) {
                if (data.resultList.length > 0) {
                    var dxarchivement = data.resultList[0];
                    DWRUtil.setValue("e_name", dxarchivement.name);
                    DWRUtil.setValue("e_id", dxarchivement.empid);
                    DWRUtil.setValue("e_ability", dxarchivement.ability);
                    DWRUtil.setValue("e_type", dxarchivement.type);
                    DWRUtil.setValue("e_archieve", dxarchivement.archieve);
                    DWRUtil.setValue("e_performance", dxarchivement.performance);
                    DWRUtil.setValue("e_patent", dxarchivement.patent);
                    DWRUtil.setValue("e_compet", dxarchivement.compet);
                    DWRUtil.setValue("e_result", dxarchivement.result);
                } else {
                    alert(data.message);
                }
            } else {
                alert(data.message);
            }
        }
        function save() {
            var warnArr = new Array();
            //清空所有信息提示
            warnInit(warnArr);
            var bl = validvalue('helpTitle');
            if (bl) {
                //此处可编写js代码进一步验证数据项

                //Btn.close();
                dwrDxArchivementTempService.saveDxArchivementTemp(getDxarchivementTemp(), saveCallback);
                <%--if(<%=isedit%>){--%>
                <%--dwrDxArchivementService.updateDxArchivement(getDxarchivement(),updateCallback);--%>
                <%--}else{--%>
                <%--dwrDxArchivementService.saveDxArchivement(getDxarchivement(),saveCallback);--%>
                <%--}--%>
            }
        }

        function getDxarchivementTemp() {
            var dxarchivementTemp = new Object();
            if (<%=isedit%>) {
                dxarchivementTemp.primaryKey = '<%=pk%>';
                dxarchivementTemp.empid = '<%=pk%>';
            }
            dxarchivementTemp.name = DWRUtil.getValue("e_name");
            dxarchivementTemp.ability = DWRUtil.getValue("e_ability");
            dxarchivementTemp.type = DWRUtil.getValue("e_type");
            dxarchivementTemp.archieve = DWRUtil.getValue("e_archieve");
            dxarchivementTemp.performance = DWRUtil.getValue("e_performance");
            dxarchivementTemp.patent = DWRUtil.getValue("e_patent");
            dxarchivementTemp.compet = DWRUtil.getValue("e_compet");
            dxarchivementTemp.result = DWRUtil.getValue("e_result");
            dxarchivementTemp.flag = "0";
            return dxarchivementTemp;
        }

        function getDxarchivement() {
            var dxarchivement = new Object();
            if (<%=isedit%>) {
                dxarchivement.primaryKey = '<%=pk%>';
                dxarchivement.empid = '<%=pk%>';
            }
            dxarchivement.name = DWRUtil.getValue("e_name");
            dxarchivement.ability = DWRUtil.getValue("e_ability");
            dxarchivement.type = DWRUtil.getValue("e_type");
            dxarchivement.archieve = DWRUtil.getValue("e_archieve");
            dxarchivement.performance = DWRUtil.getValue("e_performance");
            dxarchivement.patent = DWRUtil.getValue("e_patent");
            dxarchivement.compet = DWRUtil.getValue("e_compet");
            dxarchivement.result = DWRUtil.getValue("e_result");
            return dxarchivement;
        }
        function saveCallback(data) {
            //Btn.open();
            if (data.success) {
                confirmmsgAndTitle("添加认证信息成功！是否想继续添加认证信息？", "reset();", "继续添加", "closePage();", "关闭页面");
            } else {
                alertmsg(data);
            }
        }
        function updateCallback(data) {
            //Btn.open();
            if (data.success) {
                alertmsg(data, "closePage();");
            } else {
                alertmsg(data);
            }
        }
        function reset() {
            Sys.reload();
        }
        function closePage() {
            closeMDITab(<%=tab%>);
        }
    </script>
</head>
<body class="inputcls">
<div class="formDetail">
    <div class="requdiv"><label id="helpTitle"></label></div>
    <div class="formTitle"><%=saveOrEdit%>认证信息</div>
    <table class="inputtable">
        <tr>
            <td colspan="3">
                <DIV class="tabdiv">
                    <DIV class="tagContentdiv">
                        <DIV class="tagContent" id="tag0" style="height: 400px;">
                            <div style="overflow: hidden;">
                                <table border='0' width='98%'>
                                    <tr>
                                        <th>&nbsp;&nbsp;工号</th>
                                        <td>
                                            <input type="text" id="e_id" maxlength="10" disabled>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>&nbsp;&nbsp;姓名</th>
                                        <td>
                                            <input type="text" id="e_name" maxlength="20" disabled>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>B/C及人才</th>
                                        <td>
                                            <input type="text" id="e_ability" maxlength="20" disabled>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>类型</th>
                                        <td>
                                            <input type="text" id="e_type" maxlength="20" disabled>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>竞赛</th>
                                        <td>
                                            <textarea id="e_compet" maxlength="50"></textarea>
                                        </td>
                                        <th>成绩</th>
                                        <td>
                                            <textarea id="e_result" maxlength="50"></textarea>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>获奖情况</th>
                                        <td>
                                            <textarea id="e_archieve" maxlength="50"></textarea>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>重大业绩</th>
                                        <td>
                                            <textarea id="e_performance" maxlength="50"></textarea>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>专利情况</th>
                                        <td>
                                            <textarea id="e_patent" maxlength="50"></textarea>
                                        </td>
                                    </tr>

                                </table>
                            </div>
                        </DIV>
                    </DIV>
                </DIV>
            </td>
        </tr>
    </table>
</div>
<table align="center">
    <tr>
        <td><btn:btn onclick="save();" value="保 存 " imgsrc="../../images/png-1718.png" title="保存认证信息信息"/></td>
        <td style="width:20px;"></td>
        <td><btn:btn onclick="closePage();" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"/></td>
    </tr>
</table>
</body>
</html>
