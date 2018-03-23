package com.pinhuba.common.pack;

import org.apache.commons.lang.StringUtils;

import com.pinhuba.common.util.EnumUtil;
import com.pinhuba.common.util.StringTool;
import com.pinhuba.core.pojo.HrmDepartment;
import com.pinhuba.core.pojo.HrmEmployee;
import com.pinhuba.core.pojo.HrmPost;
import com.pinhuba.core.pojo.HrmWorkarea;

public class HrmEmployeePack{
	
	/**
	 * 查询人员信息
	 * @param employee 
	 * @param l   公司主键
	 * @return
	 */
	public static String packEmployeeQuery(HrmEmployee employee, long l) {
		StringBuffer result = new StringBuffer();
		
		String name = employee.getHrmEmployeeName();
		if(StringUtils.isNotBlank(name)){
			if(StringTool.isChinese(name))
				HqlPack.getStringLikerPack(name,"hrmEmployeeName", result);
			else
				HqlPack.getStringLikerPack(name.toUpperCase(),"hrmEmployeeSimple", result);
		}
		
		HqlPack.getStringEqualPack(employee.getPrimaryKey(),"primaryKey", result);
		HqlPack.getStringLikerPack(employee.getHrmEmployeeCode(),"hrmEmployeeCode", result);
		HqlPack.timeBuilder(employee.getHrmEmployeeBirthday(),"hrmEmployeeBirthday",result,false,false);
		HqlPack.timeBuilder(employee.getHrmEmployeeInTime(),"hrmEmployeeInTime",result,false,false);
		HqlPack.getNumEqualPack(employee.getHrmEmployeeSex(), "hrmEmployeeSex", result);
		HqlPack.getNumEqualPack(employee.getHrmEmployeeDepid(), "hrmEmployeeDepid", result);
		HqlPack.getNumEqualPack(employee.getHrmEmployeeActive(), "hrmEmployeeActive", result);
		HqlPack.getNumEqualPack(l, "companyId", result);
		HqlPack.getInPackString(employee.getEmployeeIds(), "primaryKey", result);
		HqlPack.getInPack(employee.getHrmEmployeeDepidTree(), "hrmEmployeeDepid", result);
		return result.toString();
	}
	
	/**
	 * 在线人员
	 * @param employee
	 * @param l
	 * @return
	 */
	public static String packOnLineEmployeeQuery(HrmEmployee employee, long l){
		StringBuffer result = new StringBuffer();
		result.append("select emp.hrm_employee_id,emp.hrm_employee_code,emp.hrm_employee_name,dept.hrm_dep_name,emp.hrm_employee_sex,emp.hrm_employee_image_info_id from hrm_employee emp,hrm_department dept where emp.hrm_employee_depid = dept.dep_id");
		SqlPack.getNumEqualPack(l, "emp.company_id", result);
		SqlPack.getInPack(employee.getHrmEmployeeDepidTree(), "dept.dep_id", result);
		SqlPack.getStringLikerPack(employee.getHrmEmployeeName(), "emp.hrm_employee_name", result);
		SqlPack.getStringLikerPack(employee.getHrmEmployeeCode(), "emp.hrm_employee_code", result);
		SqlPack.getNumEqualPack(employee.getHrmEmployeeSex(), "emp.hrm_employee_sex", result);
		SqlPack.getInPack(employee.getEmployeeIds(), "emp.hrm_employee_id", result);
		SqlPack.getNumNOEqualPack(employee.getHrmEmployeeStatus(), "emp.hrm_employee_status", result);
		SqlPack.getNumEqualPack(employee.getHrmEmployeeActive(), "emp.hrm_employee_active", result);
		if (employee.getPrimaryKey()!=null&&employee.getPrimaryKey().length()>0) {
			result.append(" and emp.hrm_employee_id <>'"+employee.getPrimaryKey()+"'");
		}
		return result.toString();
	}
	
	 /**
     * 查询人员（sql查询）
     * @param employee
     * @return
     */
	public static String getEmployeeSql(HrmEmployee employee) {
		StringBuffer result = new StringBuffer();
		result.append("select emp.* from hrm_employee emp ");
		if(employee.getHrmEmployeeDepidTree() != null && employee.getHrmEmployeeDepidTree().length()>0){
			  result.append(" , hrm_department dep");
           if(employee.getHrmEmployeePostId() != null && employee.getHrmEmployeePostId()>0){
        	   result.append(" , hrm_post post"); 
        	   result.append(" where emp.hrm_employee_depid = dep.dep_id and dep.dep_id in ("+employee.getHrmEmployeeDepidTree()+") ");
        	   result.append(" and post.post_id = emp.hrm_employee_post_id and emp.hrm_employee_post_id ="+employee.getHrmEmployeePostId());  
        	   if (employee.getHrmPartPost()!=null&&employee.getHrmPartPost().length()>0) {
        		   result.append(" or emp.hrm_part_post like '%"+employee.getHrmPartPost()+"%' ");
        		   SqlPack.getCheckStrInArr(employee.getHrmPartPost(), "emp.hrm_part_post", result);
        	   }
        	   
           }else{
        	   result.append(" where emp.hrm_employee_depid = dep.dep_id and dep.dep_id  in ("+employee.getHrmEmployeeDepidTree()+") ");
           }
		
		}else{
			if(employee.getHrmEmployeePostId() != null && employee.getHrmEmployeePostId()>0 ){
        	   result.append(" , hrm_post post"); 
        	   result.append(" where post.post_id = emp.hrm_employee_post_id and emp.hrm_employee_post_id ="+employee.getHrmEmployeePostId()); 
        	   if (employee.getHrmPartPost()!=null&&employee.getHrmPartPost().length()>0) {
        		   result.append(" or emp.hrm_part_post like '%"+employee.getHrmPartPost()+"%' ");
        		   SqlPack.getCheckStrInArr(employee.getHrmPartPost(), "emp.hrm_part_post", result);
        	   }
			}else{
        	   result.append(" where 1=1 ");
           }
		}
		
		if(employee.getHrmEmployeeName() != null && employee.getHrmEmployeeName().length()>0){
			result.append(" and emp.hrm_employee_name like '%"+employee.getHrmEmployeeName()+"%' ");
		}
		if(employee.getHrmEmployeeCode() != null && employee.getHrmEmployeeCode().length()>0){
			result.append(" and emp.hrm_employee_code like '%"+employee.getHrmEmployeeCode()+"%' ");
		}
		if (employee.getCompanyId()!=null && employee.getCompanyId()>0) {
			result.append(" and emp.company_id = "+employee.getCompanyId()+"");
		}
		result.append(" and emp.hrm_employee_active ="+EnumUtil.SYS_ISACTION.Vaild.value);
		return result.toString();
	}
	
	/**
     * 查询工作区域
     * @param workarea
     * @return
     */
	public static String packWorkareaQuery(HrmWorkarea workarea) {
        StringBuffer result = new StringBuffer();
		HqlPack.getStringLikerPack(workarea.getHrmAreaName(), "hrmAreaName", result);
		HqlPack.getStringLikerPack(workarea.getHrmAreaEngname(), "hrmAreaEngname", result);
		HqlPack.getNumEqualPack(workarea.getCompanyId(), "companyId", result);
		
		return result.toString();
	}
	
	/**
	 *  查询部门信息
	 * @param department
	 * @param c 公司主键
	 * @return
	 */
	public static String packDepartmentQuery(HrmDepartment department,long c) {
		StringBuffer result = new StringBuffer();
		HqlPack.getStringLikerPack(department.getHrmDepCode(),"hrmDepCode", result);
		HqlPack.getStringLikerPack(department.getHrmDepName(),"hrmDepName", result);
		HqlPack.getStringLikerPack(department.getHrmDepEngname(),"hrmDepEngname", result);
		HqlPack.getStringEqualPack(department.getHrmDepUpid(), "hrmDepUpid", result);
		HqlPack.getNumEqualPack(department.getHrmEmpId(), "hrmEmpId", result);
		HqlPack.getNumEqualPack(c, "companyId", result);
		return result.toString();
	}
	
    /**
     * 岗位查询条件
     * @param hrmPost
     * @param companyId      公司主键
     * @return
     */
	public static String packPostQuery(HrmPost hrmPost, int companyId) {
		StringBuffer result = new StringBuffer();
		HqlPack.getStringEqualPack(hrmPost.getHrmPostId(), "hrmPostId", result);
		HqlPack.getStringLikerPack(hrmPost.getHrmPostName(), "hrmPostName", result);
		HqlPack.getStringLikerPack(hrmPost.getHrmPostEngname(), "hrmPostEngname", result);
		HqlPack.getStringEqualPack(hrmPost.getHrmPostUpid(), "hrmPostUpid", result);
		HqlPack.getNumEqualPack(hrmPost.getCompanyId(), "companyId",result);
		return result.toString();
	}
	
	public static String packEmployeeQueryByGroupId(HrmEmployee employee, long companyId) {
		StringBuffer result = new StringBuffer();
		
		result.append("select emp.* from hrm_employee emp where emp.hrm_employee_id in " +
				"(select userinfo.hrm_employee_id FROM sys_user_group_detail ugd  " +
				"left join sys_user_info userinfo on ugd.user_id = userinfo.user_id  " +
				"where ugd.group_id = "+employee.getGroupId()+")  ");
		
		SqlPack.getStringLikerPack(employee.getHrmEmployeeName(), "emp.hrm_employee_name", result);
		SqlPack.getNumEqualPack(employee.getHrmEmployeeActive(), "emp.hrm_employee_active", result);
		SqlPack.getNumEqualPack(companyId, "emp.company_id", result);
		
		return result.toString();
	}
	
	public static String packEmployeeQueryByRoleId(HrmEmployee employee, long companyId) {
		StringBuffer result = new StringBuffer();
		long rid = employee.getRoleId();
		
		result.append("select * from (");
		result.append("select * from hrm_employee where hrm_employee_id in ");
		result.append("(select hrm_employee_id from sys_user_info where user_id in ");
		result.append("(select bind_value from sys_role_bind where bind_type=1 and role_id = "+rid+")) ");
		result.append("union ");
		result.append("select * from hrm_employee where hrm_employee_depid in ");
		result.append("(select dep_id from hrm_department where dep_id in ");
		result.append("(select bind_value from sys_role_bind where bind_type=2 and role_id = "+rid+")) ");
		result.append("union ");
		result.append("select * from hrm_employee where hrm_employee_id in ");
		result.append("(select hrm_employee_id from sys_user_info where user_id in ");
		result.append("(select user_id from sys_user_group_detail where group_id in  ");
		result.append("(select bind_value from sys_role_bind where bind_type=4 and role_id = "+rid+"))) ");
		result.append(") s where 1=1 ");
		
		SqlPack.getStringLikerPack(employee.getHrmEmployeeName(), "hrm_employee_name", result);
		SqlPack.getNumEqualPack(employee.getHrmEmployeeActive(), "hrm_employee_active", result);
		SqlPack.getNumEqualPack(companyId, "company_id", result);
		
		return result.toString();
	}

}