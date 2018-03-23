<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../common.jsp" %>
<%
    String tab = request.getParameter("tab");
    String pk = request.getParameter("pk");
    String isedit = "false";
    String saveOrEdit = "新增";
    String helpTitle = "您可以在此处添加您想新增的培训信息！";
    if (pk != null) {
        isedit = "true";
        saveOrEdit = "编辑";
        helpTitle = "您可以在此处编辑培训信息信息！";
    } else {
        SessionUser user = (SessionUser) LoginContext.getSessionValueByLogin(request);
        HrmEmployee empinfo = user.getEmployeeInfo();
        pk = empinfo.getHrmEmployeeCode();
        isedit = "true";
        saveOrEdit = "编辑";
        helpTitle = "您可以在此处编辑培训信息信息！";
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><%=saveOrEdit%>培训信息</title>
    <script type="text/javascript" src="<%=contextPath%>/dwr/interface/dwrDxEducateService.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/dwr/interface/dwrDxEducateTempService.js"></script>
    <script type="text/javascript">
        window.onload = function () {
            useLoadingMassage();
            initInput("helpTitle", "<%=helpTitle%>");
            saveOrEdit();
        }
        function saveOrEdit() {
            if (<%=isedit%>) {
                var pk = '<%=pk%>';
                dwrDxEducateService.getModDxEducateByPk(pk, setDxeducate);
            }
        }

        function setDxeducate(data) {
            if (data.success == true) {
                if (data.resultList.length > 0) {
                    var dxeducate = data.resultList[0];
                    DWRUtil.setValue("e_name", dxeducate.name);
                    DWRUtil.setValue("e_experience", dxeducate.experience);
                    DWRUtil.setValue("e_certificate", dxeducate.certificate);
                    DWRUtil.setValue("e_specialty", dxeducate.specialty);
                    DWRUtil.setValue("e_date", dxeducate.date);
                    DWRUtil.setValue("e_year", dxeducate.year);
                    DWRUtil.setValue("e_pastspecialty", dxeducate.pastspecialty);
                    DWRUtil.setValue("e_pastdate", dxeducate.pastdate);
                    DWRUtil.setValue("e_pstyear", dxeducate.pstyear);
                    DWRUtil.setValue("e_id", dxeducate.empid);
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
                //Btn.close();
                dwrDxEducateTempService.saveDxEducateTemp(getDxeducateTemp(), saveCallback);
                <%--if (<%=isedit%>) {--%>
                    <%--DwrDxEducateTempService.updateDxEducateTemp(getDxeducate(), updateCallback);--%>
                <%--} else {--%>
                    <%--DwrDxEducateTempService.saveDxEducateTemp(getDxeducate(), saveCallback);--%>
                <%--}--%>
            }
        }

        function getDxeducateTemp() {
            var educateTemp = new Object();
            if (<%=isedit%>) {
                educateTemp.primaryKey = '<%=pk%>';
                educateTemp.empid = '<%=pk%>';
            }
            educateTemp.name = DWRUtil.getValue("e_name");
            educateTemp.experience = DWRUtil.getValue("e_experience");
            educateTemp.certificate = DWRUtil.getValue("e_certificate");
            educateTemp.specialty = DWRUtil.getValue("e_specialty");
            educateTemp.date = DWRUtil.getValue("e_date");
            educateTemp.year = DWRUtil.getValue("e_year");
            educateTemp.pastspecialty = DWRUtil.getValue("e_pastspecialty");
            educateTemp.pastdate = DWRUtil.getValue("e_pastdate");
            educateTemp.pstyear = DWRUtil.getValue("e_pstyear");
            educateTemp.flag = "0";
            return educateTemp;
        }

        function getDxeducate() {
            var dxeducate = new Object();
            if (<%=isedit%>) {
                dxeducate.primaryKey = '<%=pk%>';
                dxeducate.empid = '<%=pk%>';
            }
            dxeducate.name = DWRUtil.getValue("e_name");
            dxeducate.experience = DWRUtil.getValue("e_experience");
            dxeducate.certificate = DWRUtil.getValue("e_certificate");
            dxeducate.specialty = DWRUtil.getValue("e_specialty");
            dxeducate.date = DWRUtil.getValue("e_date");
            dxeducate.year = DWRUtil.getValue("e_year");
            dxeducate.pastspecialty = DWRUtil.getValue("e_pastspecialty");
            dxeducate.pastdate = DWRUtil.getValue("e_pastdate");
            dxeducate.pstyear = DWRUtil.getValue("e_pstyear");
            return dxeducate;
        }
        function saveCallback(data) {
            //Btn.open();
            if (data.success) {
                confirmmsgAndTitle("添加培训信息成功！是否想继续添加培训信息？", "reset();", "继续添加", "closePage();", "关闭页面");
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
    <div class="formTitle"><%=saveOrEdit%>培训信息</div>
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
                                        <th>现从事专业</th>
                                        <td>
                                            <input type="text" id="e_specialty" maxlength="20" disabled>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>现专业从事时间</th>
                                        <td>
                                            <input type="text" id="e_date" maxlength="20" disabled>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>现专业年从事年限</th>
                                        <td>
                                            <input type="text" id="e_year" maxlength="20" disabled>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>曾从事专业</th>
                                        <td>
                                            <input type="text" id="e_pastspecialty" maxlength="20" disabled>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>曾从事专业时间</th>
                                        <td>
                                            <input type="text" id="e_pastdate" maxlength="20" disabled>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>曾专业年从事年限</th>
                                        <td>
                                            <input type="text" id="e_pstyear" maxlength="20" disabled>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>&nbsp;&nbsp;培训经历</th>
                                        <td>
                                            <textarea id="e_experience"></textarea>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>认证证书</th>
                                        <td>
                                            <textarea id="e_certificate"></textarea>
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
        <td><btn:btn onclick="save();" value="保 存 " imgsrc="../../images/png-1718.png" title="保存培训信息信息"/></td>
        <td style="width:20px;"></td>
        <td><btn:btn onclick="closePage();" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"/></td>
    </tr>
</table>
</body>
</html>
