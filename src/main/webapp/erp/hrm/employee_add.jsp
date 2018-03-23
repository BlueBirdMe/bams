<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<%
	String tab = request.getParameter("tab");
	String employeepk = request.getParameter("employeepk");
	String saveOrEdit = "新增";
    String helpTitle = "您可以在此处添加您想新增的人员！";
	String isedit = "false";
	if(employeepk!=null){//编辑时使用
		isedit = "true";
		saveOrEdit = "编辑";
        helpTitle = "您可以在此处编辑人员信息！";
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=saveOrEdit %>人员</title>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrHrmEmployeeService.js"></script>
<script type="text/javascript">
window.onload = function(){
    useLoadingMassage();
	initInput('helpTitle','<%=helpTitle %>');
	saveOrEdit();

	//第一个输入框获取焦点
    document.getElementById("hrmEmployeeName").focus();
}
	
function backToEmployeeList(){
    window.location = "employee.jsp";
}

function saveOrEdit(){
     if(<%=isedit%>){
	      var employeeprimaryKey = '<%=employeepk%>';
	      dwrHrmEmployeeService.getEmployeeByPK(employeeprimaryKey,setEmployeeinfo);
     }
}
    
var fckvalue="";
var fck;

function setEmployeeinfo(data){
    if(data.success == true){
		if(data.resultList.length > 0){
			var employee = data.resultList[0];
			DWRUtil.setValue("hrmEmployeeName",employee.hrmEmployeeName);
		    DWRUtil.setValue("hrmEmployeeEngname",employee.hrmEmployeeEngname);
		    DWRUtil.setValue("hrmEmployeeCode",employee.hrmEmployeeCode);
		    DWRUtil.setValue("hrmEmployeeBirthday",employee.hrmEmployeeBirthday);
		    setRadioValueByName("hrmEmployeeSex",employee.hrmEmployeeSex);	//设置radio的值
		    setSelectValue("hrmEmployeeMarriage",employee.hrmEmployeeMarriage);
		    setSelectValue("hrmEmployeePolitics",employee.hrmEmployeePolitics);
		    setSelectValue("hrmEmployeeNationality",employee.hrmEmployeeNationality);
		    setSelectValue("hrmEmployeeBloodType",employee.hrmEmployeeBloodType);
		    DWRUtil.setValue("hrmEmployeeHeight",employee.hrmEmployeeHeight);
		    DWRUtil.setValue("hrmEmployeeWeight",employee.hrmEmployeeWeight);
		    DWRUtil.setValue("hrmEmployeeSchool",employee.hrmEmployeeSchool);
		    DWRUtil.setValue("hrmEmployeeProfessional",employee.hrmEmployeeProfessional);
		    setSelectValue("hrmEmployeeDegree",employee.hrmEmployeeDegree);
		    DWRUtil.setValue("hrmEmployeeIdentitycard",employee.hrmEmployeeIdentitycard);
		    DWRUtil.setValue("hrmEmployeeHometown",employee.hrmEmployeeHometown);
		    DWRUtil.setValue("hrmEmployeeHousePhone",employee.hrmEmployeeHousePhone);
		    DWRUtil.setValue("hrmEmployeeHouseAddress",employee.hrmEmployeeHouseAddress);
		    DWRUtil.setValue("hrmEmployeeMobileTele",employee.hrmEmployeeMobileTele);
		    DWRUtil.setValue("hrmEmployeeWorkTele",employee.hrmEmployeeWorkTele);
		    DWRUtil.setValue("hrmEmployeeInTime",employee.hrmEmployeeInTime);
		    setSelectValue("hrmEmployeeWorkareaid",employee.hrmEmployeeWorkareaid);
		    DWRUtil.setValue("hrmEmployeeEmail",employee.hrmEmployeeEmail);
		    DWRUtil.setValue("hrmEmployeeUrgentPreson",employee.hrmEmployeeUrgentPreson);
		    DWRUtil.setValue("hrmEmployeeUrgentPhone",employee.hrmEmployeeUrgentPhone);
		    DWRUtil.setValue("hrmEmployeeAdderss",employee.hrmEmployeeAdderss);
	        setSelectValue("hrmEmployeePatternId",employee.hrmEmployeePatternId); 
		    DWRUtil.setValue("hrmEmployeeDepid",employee.hrmEmployeeDepid);
		    DWRUtil.setValue("hrmEmployeeDeptext",employee.hrmDepartment.hrmDepName);
		    DWRUtil.setValue("hrmEmployeePostId",employee.hrmEmployeePostId);
		    DWRUtil.setValue("hrmEmployeePostIdtext",employee.hrmEmployeePost.hrmPostName);
		    DWRUtil.setValue("hrmPartPost",employee.hrmPartPost);
		    DWRUtil.setValue("hrmPartPosttext",employee.hrmPartPostName);
		    DWRUtil.setValue("hrmEmployeeWorkTime",employee.hrmEmployeeWorkTime);
		    DWRUtil.setValue("hrmEmployeeAppendid",employee.hrmEmployeeAppendid);
		    setRadioValueByName("hrmEmployeeStatus",employee.hrmEmployeeStatus);	//设置radio的值
			//附件
			if(employee.hrmOtherAttachmen!=null && employee.hrmOtherAttachmen!=undefined && employee.hrmOtherAttachmen!= "undefined" && employee.hrmOtherAttachmen.length>0){
				dwrCommonService.getAttachmentInfoListToString(employee.hrmOtherAttachmen,setAttachaccept);
			}	
			//照片
			if(employee.hrmEmployeeImageInfoId!=null && employee.hrmEmployeeImageInfoId!=undefined && employee.hrmEmployeeImageInfoId!= "undefined" && employee.hrmEmployeeImageInfoId > 0){
				dwrCommonService.getImageInfoListToString(employee.hrmEmployeeImageInfoId,setImageaccept);
			}	
		}
	}
}

//放入附件
function setAttachaccept(data){
    Sys.setFilevalue("hrmOtherAttachmen",data);
}

//放入照片
function setImageaccept(data){
	Sys.setFilevalue("hrmEmployeeImageInfoId",data);
}
	
function FCKeditor_OnComplete(editorInstance) {
	fck= editorInstance;
	editorInstance.SetHTML(fckvalue);
	window.status = editorInstance.Description;
}
	
function getEmployeeinfo(){
     var employee = new Object();
   	 if(<%=isedit%>){
	 	  employee.primaryKey = '<%=employeepk%>';
	 }
	 employee.hrmEmployeeWorkTime = DWRUtil.getValue("hrmEmployeeWorkTime");
	 employee.hrmEmployeeName = DWRUtil.getValue("hrmEmployeeName");
	 employee.hrmEmployeeEngname = DWRUtil.getValue("hrmEmployeeEngname");
	 employee.hrmEmployeeCode = DWRUtil.getValue("hrmEmployeeCode");
	 employee.hrmEmployeeBirthday = DWRUtil.getValue("hrmEmployeeBirthday");
	 employee.hrmEmployeeSex = getRadioValueByName("hrmEmployeeSex");	//获取radio的值
	 employee.hrmEmployeeMarriage = DWRUtil.getValue("hrmEmployeeMarriage");
	 employee.hrmEmployeePolitics = DWRUtil.getValue("hrmEmployeePolitics");
	 employee.hrmEmployeeNationality = DWRUtil.getValue("hrmEmployeeNationality");
	 employee.hrmEmployeeBloodType = DWRUtil.getValue("hrmEmployeeBloodType");
	 employee.hrmEmployeeHeight = DWRUtil.getValue("hrmEmployeeHeight");
	 employee.hrmEmployeeWeight = DWRUtil.getValue("hrmEmployeeWeight");
	 employee.hrmEmployeeSchool = DWRUtil.getValue("hrmEmployeeSchool");
	 employee.hrmEmployeeProfessional = DWRUtil.getValue("hrmEmployeeProfessional");
	 employee.hrmEmployeeDegree = DWRUtil.getValue("hrmEmployeeDegree");
	 employee.hrmEmployeeIdentitycard = DWRUtil.getValue("hrmEmployeeIdentitycard");
	 employee.hrmEmployeeHometown = DWRUtil.getValue("hrmEmployeeHometown");
	 employee.hrmEmployeeHousePhone = DWRUtil.getValue("hrmEmployeeHousePhone");
	 employee.hrmEmployeeHouseAddress = DWRUtil.getValue("hrmEmployeeHouseAddress");
	 employee.hrmEmployeeMobileTele = DWRUtil.getValue("hrmEmployeeMobileTele");
	 employee.hrmEmployeeWorkTele = DWRUtil.getValue("hrmEmployeeWorkTele");
	 employee.hrmEmployeeInTime = DWRUtil.getValue("hrmEmployeeInTime");
	 employee.hrmEmployeeDepid = DWRUtil.getValue("hrmEmployeeDepid");
	 employee.hrmEmployeeWorkareaid = DWRUtil.getValue("hrmEmployeeWorkareaid");
	 employee.hrmEmployeePostId = DWRUtil.getValue("hrmEmployeePostId");
	 employee.hrmPartPost = DWRUtil.getValue("hrmPartPost");
	 employee.hrmEmployeeEmail = DWRUtil.getValue("hrmEmployeeEmail");
	 employee.hrmEmployeeUrgentPreson = DWRUtil.getValue("hrmEmployeeUrgentPreson");
	 employee.hrmEmployeeUrgentPhone = DWRUtil.getValue("hrmEmployeeUrgentPhone");
	 employee.hrmEmployeeAdderss = DWRUtil.getValue("hrmEmployeeAdderss");
	 employee.hrmEmployeePatternId = DWRUtil.getValue("hrmEmployeePatternId");
	 employee.hrmEmployeeStatus = getRadioValueByName("hrmEmployeeStatus");	//获取radio的值
	 //employee.hrmEmployeeAppendid =  fck.GetXHTML();
	 employee.hrmEmployeeAppendid = DWRUtil.getValue("hrmEmployeeAppendid");
	 
	 return employee;
}

function save(){
    //定义信息提示数组
	var warnArr = new Array();
	warnArr[0] = "hrmEmployeeCodeMust";
	warnArr[1] = "hrmEmployeeDeptextMust";
	warnArr[2] = "hrmEmployeeBirthdayMust";
	warnArr[3] = "hrmEmployeeInTimeMust";
	warnArr[4] = "hrmEmployeeAppendidMsg";
	//清空所有信息提示
	warnInit(warnArr);
	
	 var bl = validvalue('helpTitle');
     if(bl){  
           //tab.setSelectTag(2);
           var empCode = DWRUtil.getValue("hrmEmployeeCode");//员工编号
           var dept = DWRUtil.getValue("hrmEmployeeDepid"); //部门
           
           if(trim(empCode) == "" || trim(empCode) == null){
                tab.setSelectTag(2);
                setMustWarn("hrmEmployeeCodeMust","员工编号不能为空！");
                   //document.getElementById("hrmEmployeeCode").Focus();
                   return ;
           }
           
           if(trim(dept) == "" || trim(dept) == null){
               tab.setSelectTag(2);
                setMustWarn("hrmEmployeeDeptextMust","员工所属部门不能为空！");
                   //document.getElementById("hrmEmployeeDeptext").Focus();
                   return ;
           }
           
	       var attach = DWRUtil.getValue("hrmOtherAttachmen");//附件
	       var imgfile = DWRUtil.getValue("hrmEmployeeImageInfoId");//图片
	       var birthday = document.getElementById("hrmEmployeeBirthday").value;//出生日期
	       var tryTime = document.getElementById("hrmEmployeeInTime").value;	//入职
	       var realTime = document.getElementById("hrmEmployeeWorkTime").value;//转正
	       
	       //比较时间
	       if(tryTime != "" && realTime != "" ){
	            if(tryTime > realTime){
	                 tab.setSelectTag(2);
	                 setMustWarn("hrmEmployeeInTimeMust","入职日期不能大于转正日期!");
	       		     return;
	            }
	       }
	       
	       if(tryTime != ""){
                if(birthday >= tryTime){
                     tab.setSelectTag(1);
                     setMustWarn("hrmEmployeeBirthdayMust","出生日期不能大于或等于入职日期!");
	       		     return;
                }
	       }
	       
	       if(realTime != ""){
                if(birthday >= realTime){
                     tab.setSelectTag(1);
                     setMustWarn("hrmEmployeeBirthdayMust","出生日期不能大于或等于转正日期!");
	       		     return;
                }
	       }  
	       var tmpval = DWRUtil.getValue("hrmEmployeeAppendid");
	       if(tmpval.length>1000){
	       		tab.setSelectTag(3);
                setMustWarn("hrmEmployeeAppendidMsg","备注不能超过1000个字符，建议您上传附件！");
	       		return;
	       }
	          
           Btn.close();
   		   dwrHrmEmployeeService.saveEmployee(getEmployeeinfo(),attach,imgfile,saveCallback);
	  }
}
	
function saveCallback(data){
   Btn.open(); 
   if(<%=isedit%>){
        if(data.success){
        	alertmsg(data,"closePage();");
        }else{
       		alertmsg(data);
        }
   }else{
   		if(data.success){
        	confirmmsgAndTitle("添加人员信息成功！是否想继续添加人员信息？","repload();","继续添加","closePage();","关闭页面");
  		}else{
  			alertmsg(data);
  		}
   }
}

function repload(){
    DWRUtil.setValue("hrmEmployeeName","");
    DWRUtil.setValue("hrmEmployeeEngname","");
    DWRUtil.setValue("hrmEmployeeCode","");
    DWRUtil.setValue("hrmEmployeeBirthday","");
    //setSelectValue("hrmEmployeeSex",0);
    setSelectValue("hrmEmployeeMarriage",0);
    //setSelectValue("hrmEmployeeStatus",0);
    setSelectValue("hrmEmployeePolitics",0);
    setSelectValue("hrmEmployeeNationality",0);
    setSelectValue("hrmEmployeeBloodType",0);
    DWRUtil.setValue("hrmEmployeeHeight","");
    DWRUtil.setValue("hrmEmployeeWeight","");
    DWRUtil.setValue("hrmEmployeeSchool","");
    DWRUtil.setValue("hrmEmployeeProfessional","");
    setSelectValue("hrmEmployeeDegree",0);
    DWRUtil.setValue("hrmEmployeeIdentitycard","");
    DWRUtil.setValue("hrmEmployeeHometown","");
    DWRUtil.setValue("hrmEmployeeHousePhone","");
    DWRUtil.setValue("hrmEmployeeHouseAddress","");
    DWRUtil.setValue("hrmEmployeeMobileTele","");
    DWRUtil.setValue("hrmEmployeeWorkTele","");
    DWRUtil.setValue("hrmEmployeeInTime","");
    setSelectValue("hrmEmployeeWorkareaid",0);
    DWRUtil.setValue("hrmEmployeeEmail","");
    DWRUtil.setValue("hrmEmployeeUrgentPreson","");
    DWRUtil.setValue("hrmEmployeeUrgentPhone","");
    DWRUtil.setValue("hrmEmployeeAdderss","");
    setSelectValue("hrmEmployeePatternId",0);
         
    DWRUtil.setValue("hrmEmployeeDepid","");
    DWRUtil.setValue("hrmEmployeeDeptext","");
    
    DWRUtil.setValue("hrmEmployeePostId","");
    DWRUtil.setValue("hrmEmployeePostIdtext","");
    
    DWRUtil.setValue("hrmPartPost","");
    DWRUtil.setValue("hrmPartPosttext","");
	DWRUtil.setValue("hrmEmployeeAppendid","");
	//刷新附件
	Sys.setFilevalue("hrmOtherAttachmen","");
	//刷新照片
	Sys.setFilevalue("hrmEmployeeImageInfoId","");
	
	tab.setSelectTag(1);
	document.getElementById("hrmEmployeeName").focus();
}

function getupcode(){
	var box = SEL.getDeptIds("radio","hrmEmployeeDeptext","hrmEmployeeDepid");
	box.show();
}

function getPostUpid(){
    var box = SEL.getPostIds("radio","hrmEmployeePostIdtext","hrmEmployeePostId");
    box.show();
}

function getPartPostUpid(){
    var box = SEL.getPostIds("check","hrmPartPosttext","hrmPartPost");
    box.show();
}	

function closePage(){
    closeMDITab(<%=tab%>);
}
</script>
</head>
<body class="inputcls">
<div class="formDetail">
<div class="requdiv"><label id="helpTitle"></label></div>
<div class="formTitle">人员信息</div>
<table class="inputtable">
	<tr>
	<td colspan="3">
		<DIV class="tabdiv">
		<UL class="tags">
		<LI><A onClick="tab.selectTag(this);" href="javascript:void(0)">基本信息</A></LI>
		<LI><A onClick="tab.selectTag(this)" href="javascript:void(0)" id="seltg2">工作信息</A> </LI>
		<LI><A onClick="tab.selectTag(this);" href="javascript:void(0)">附加信息</A></LI>
		</UL>
		<DIV class="tagContentdiv">
			<DIV class="tagContent" id="tag0" style="height: 400px;">
			   <div style="overflow: hidden;" >
			    <table  border='0' width ='98%'>
			           <tr>
						<th><em>*</em>&nbsp;&nbsp;姓名</th>
						  <td>
						    <input type="text" id="hrmEmployeeName" must="员工姓名不能为空!" formust="hrmEmployeeNameMust" maxlength="10">
						    <label id="hrmEmployeeNameMust"></label>
						  </td>
						<th  rowspan="5" width="10%" valign="bottom">上传照片</th>
						<td  rowspan="5"><file:imgupload width="120" acceptTextId="hrmEmployeeImageInfoId" height="135" edit="<%=isedit %>"></file:imgupload></td>
						</tr>
						<tr>
						    <th>英文名</th>
						    <td>
						    <input type="text" id="hrmEmployeeEngname" maxlength="20">
						    </td>
						</tr>
						<tr>
						    <th>性别</th>
						    <td><%=UtilTool.getRadioOptionsByEnum(EnumUtil.HRM_EMPLOYEE_SEX.getSelectAndText(""),"hrmEmployeeSex")%></td>
						</tr>
						<tr>
						    <th><em>*</em>&nbsp;&nbsp;出生日期</th>
						    <td>
						    <input type="text" must="出生日期不能为空！" readonly="readonly" id="hrmEmployeeBirthday" formust="hrmEmployeeBirthdayMust" class="Wdate" onClick="WdatePicker({maxDate:'%y-%M-%d'})" value="">
						    <label id="hrmEmployeeBirthdayMust"></label>
						    </td>
						</tr>
						<tr>
						    <th><em>*</em>&nbsp;&nbsp;身份证号码</th>
							<td>
								 <input type="text" id="hrmEmployeeIdentitycard" must="身份证号码不能为空!"  formust="hrmEmployeeIdentitycardMust" maxlength="18">
							     <label id="hrmEmployeeIdentitycardMust"></label>
							</td>
						</tr>
						<tr>
						<th>政治面貌</th>
							  <td>
							      <select must="请选择政治面貌" id="hrmEmployeePolitics">
							      <%=UtilTool.getSelectOptions(this.getServletContext(),request,null,"18") %>
							      </select>
							  </td>	
						</tr>
						<tr>
								<th>籍贯</th>
								<td>
								  <input type="text" id="hrmEmployeeHometown" maxlength="30">
								</td>
							  <th>移动电话</th>
							    <td>
							      <input type="text" id="hrmEmployeeMobileTele" maxlength="20" >
							    </td>
							  
						</tr>
						<tr>
						<th>民族</th>
						<td>
						  <select must="请选择民族" id="hrmEmployeeNationality">
						  <%=UtilTool.getSelectOptions(this.getServletContext(),request,null,"20") %>
						  </select>
						</td>
						<th>当前住址</th>
						    <td>
						       <input type="text" id="hrmEmployeeAdderss" maxlength="30">
						    </td>
						</tr>
						<tr>
						    <th>学历</th>
						    <td>
						      <select must="请选择学历" id="hrmEmployeeDegree">
						       <%=UtilTool.getSelectOptions(this.getServletContext(),request,null,"21") %>
						      </select>
						    </td>
						    <th>家庭电话</th>
						    <td>
						      <input type="text" id="hrmEmployeeHousePhone"  maxlength="20">
						    </td>
	 					</tr>
						<tr>
						    <th>家庭地址</th>
							<td>
							  <input type="text" id="hrmEmployeeHouseAddress" maxlength="30">
							</td>
						    <th>婚姻状况</th>
						    <td>
						     <select must="请选择性别" id="hrmEmployeeMarriage">
						     <%=UtilTool.getSelectOptions(this.getServletContext(),request,null,"02") %>
						     </select>
						    </td>
							</tr>
						<tr>
						    <th>紧急联系人</th>
					        <td>
					        <input type="text" id="hrmEmployeeUrgentPreson" maxlength="30">
					        </td>
						    <th>紧急联系电话</th>
							<td>
							  <input type="text" id="hrmEmployeeUrgentPhone"  maxlength="20">
							</td>
						</tr>
			    </table>
			   </div>
			</DIV>
			<DIV class="tagContent" id="tag1" style="height: 400px;">
			   <div style="overflow: hidden;" >
			    <table  border='0' width ='98%'>
					<tr>	 
					<th><em>*</em>&nbsp;&nbsp;员工编号</th>
						<td>
						<input type="text" id="hrmEmployeeCode" maxlength="10">
						<label id="hrmEmployeeCodeMust"></label>
						</td>  
					<th><em>*</em>&nbsp;&nbsp;部门</th>
				    <td>
				      <input type="text" class="takeform" id="hrmEmployeeDeptext"  readonly="readonly" linkclear="hrmEmployeeDepid" title="点击选择部门" onclick="getupcode();">
				      <input type="hidden" id="hrmEmployeeDepid" value="">
				      <label id="hrmEmployeeDeptextMust"></label>
				    </td>
					</tr>
					<tr>
					<th>入职状态</th>
					<td style="text-align: left;">
	   				   <input  type="radio" name="hrmEmployeeStatus" value="2" checked="checked" id="status1"><label for="status1">正式</label>
	  				   <input type="radio" name="hrmEmployeeStatus" value="1"  id="status2"><label for="status2">试用</label>
	                </td>
					<th>工作电话</th>
					<td>
					  <input type="text" id="hrmEmployeeWorkTele" maxlength="20">
					</td>
					</tr>
					<tr>
						<th>工作岗位</th>
					    <td>
					      <input type="text" class="takeform" id="hrmEmployeePostIdtext" readonly="readonly" linkclear="hrmEmployeePostId" title="双击获取岗位" onclick="getPostUpid();">
					      <input type="hidden" id="hrmEmployeePostId" value="">
					    </td>
					    <th>入职日期</th>
					    <td>
					      <input type="text" readonly="readonly" id="hrmEmployeeInTime" class="Wdate" onClick="WdatePicker({maxDate:'%y-%M-%d'})" value="<%=UtilWork.getToday() %>">
					      <label id="hrmEmployeeInTimeMust"></label>
					    </td>
					</tr>
					<tr>
					    
						<th>兼职岗位</th>
					    <td>
					      <input type="text" class="takeform" id="hrmPartPosttext" readonly="readonly" linkclear="hrmPartPost" title="双击获取兼职岗位" onclick="getPartPostUpid();">
					      <input type="hidden" id="hrmPartPost" value="">
					    </td>
					    <th>转正日期</th>
						<td>
						  <input type="text" readonly="readonly" id="hrmEmployeeWorkTime" class="Wdate" onClick="WdatePicker({minDate:'#F{$dp.$D(\'hrmEmployeeInTime\')}'})" value="<%=UtilWork.getToday() %>">
						</td>
					    
					</tr>
					
					<tr>
					    <th>工作地区</th>
						<td>
						 <select must="请选择工作地区" id="hrmEmployeeWorkareaid">
						   <%=UtilTool.getWorkareaOptions(this.getServletContext(),request,null) %>
						 </select>
						</td>
					    <th>用工形式</th>
						<td>
						  <select must="请选择用工形式" id="hrmEmployeePatternId">
					       <%=UtilTool.getSelectOptions(this.getServletContext(),request,null,"01") %>
					      </select>
						</td>
					</tr>
			   </table>
			   </div>
			</DIV>
			<DIV class="tagContent" id="tag2" style="height: 400px;">
			    <div style="overflow: hidden;" >
			     <table  border='0' width ='98%'>
			      <tr>
					    <th>血型</th>
						<td>
						  <select must="请选择血型" id="hrmEmployeeBloodType">
						  <%=UtilTool.getSelectOptions(this.getServletContext(),request,null,"19") %>
						  </select>
						</td>
						<th>邮箱地址</th>
						<td>
						  <input type="text" id="hrmEmployeeEmail" maxlength="30">
						</td>
				 </tr>
				 <tr>
					    <th>体重</th>
						<td>
						  <input type="text" id="hrmEmployeeWeight" class="numform" maxlength="3">&nbsp;&nbsp;<font color="red">kg</font>
						</td>
					    <th>身高</th>
					    <td>
					      <input type="text" id="hrmEmployeeHeight" class="numform" maxlength="3">&nbsp;&nbsp;<font color="red">cm</font>
					    </td>
				</tr>
				<tr>
				    <th>毕业学校</th>
					<td>
					  <input type="text" id="hrmEmployeeSchool" maxlength="30">
					</td>
				    <th>专业</th>
				    <td>
				      <input type="text" id="hrmEmployeeProfessional" maxlength="20">
				    </td>
				</tr>
			    <tr>
				<th>附件</th>
				<td  colspan="3">
				<file:multifileupload width="90%" acceptTextId="hrmOtherAttachmen" height="100" saveType="file" edit="<%=isedit%>"></file:multifileupload>
				</td>
				</tr>
				<tr>
					<th></th>
					<td id="hrmEmployeeAppendidMsg" colspan="3"></td>
				</tr>
				<tr>	
				<th>备注</th>
				<td colspan="3">
				<textarea id="hrmEmployeeAppendid"  width="90%" style="height: 150px;" ></textarea>
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
     <td><btn:btn onclick="save();" value="保 存 " imgsrc="../../images/png-1718.png" title="保存人员信息" ></btn:btn></td>
     <td style="width: 20px;"></td>
     <td><btn:btn onclick="closePage();" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭页面"/></td>
   </tr>
</table>
</body>
<script type="text/javascript">
	var tab =new SysTab('<%=contextPath%>');
</script>
</html>