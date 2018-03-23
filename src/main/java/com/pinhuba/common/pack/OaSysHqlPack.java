package com.pinhuba.common.pack;

import com.pinhuba.common.module.UserMethodsInfo;
import com.pinhuba.common.util.EnumUtil;
import com.pinhuba.core.pojo.SysException;
import com.pinhuba.core.pojo.SysLog;
import com.pinhuba.core.pojo.SysRole;
import com.pinhuba.core.pojo.SysUserGroup;
import com.pinhuba.core.pojo.SysUserInfo;

/**
 * 封装系统设置查询sql
 * @author peng.ning
 *
 */
public class OaSysHqlPack {
	
	//==========用户管理 ======================
	
	public static String getSysUserInfoSql(SysUserInfo userinfo){
		StringBuffer result = new StringBuffer();
		result.append("select distinct sysuser.* from sys_user_info sysuser,hrm_employee emp,hrm_department dep where sysuser.hrm_employee_id = emp.hrm_employee_id and emp.hrm_employee_depid = dep.dep_id ");
		if (userinfo.getEmployee()!=null) {
			if (userinfo.getEmployee().getHrmEmployeeName()!=null&&userinfo.getEmployee().getHrmEmployeeName().length()>0) {
				result.append(" and emp.hrm_employee_name like '%"+userinfo.getEmployee().getHrmEmployeeName()+"%'");
			}
			if (userinfo.getEmployee().getHrmEmployeeDepidTree()!=null&&userinfo.getEmployee().getHrmEmployeeDepidTree().length()>0) {
				result.append(" and dep.dep_id  in ("+userinfo.getEmployee().getHrmEmployeeDepidTree()+") ");
			}
		}
		if (userinfo.getUserName()!=null&&userinfo.getUserName().length()>0) {
			result.append(" and sysuser.user_name like '%"+userinfo.getUserName()+"%'");
		}
		if (userinfo.getUserAction()!=null&&userinfo.getUserAction()>0) {
			result.append(" and sysuser.user_action = "+userinfo.getUserAction()+"");
		}
		
		if (userinfo.getCompanyId()!=null&&userinfo.getCompanyId()>0) {
			result.append(" and sysuser.company_id = "+userinfo.getCompanyId()+"");
		}
		
		return result.toString();
	}
	
	public static String getSysUserGroupSql(SysUserGroup group){
		StringBuffer result = new StringBuffer();
		HqlPack.getStringLikerPack(group.getGroupName(), "groupName", result);
		HqlPack.getNumEqualPack(group.getCompanyId(), "companyId", result);
		return result.toString();
	}
	
	public static String getEmployeeNameByUserIdsSql(String userIds,int companyId){
		StringBuffer result = new StringBuffer();
		result.append("select distinct hrm.hrm_employee_name from sys_user_info suser,hrm_employee hrm where  hrm.hrm_employee_id = suser.hrm_employee_id");
		result.append(" and hrm.company_id = "+companyId+"");
		if (userIds!=null&&userIds.length()>0) {
			result.append(" and suser.user_id in ( "+userIds+" )");
		}
		return result.toString();
	}
	
	//=============角色=================
	public static String getSysRoleSql(SysRole role){
		StringBuffer result = new StringBuffer();
		HqlPack.getStringLikerPack(role.getRoleName(), "roleName", result);
		HqlPack.getNumEqualPack(role.getCompanyId(), "companyId", result);
		return result.toString();
	}
	
	//=========权限==================
	
	public static String getSysUserMethodSql(UserMethodsInfo info){
		StringBuffer result = new StringBuffer();
		result.append("select  userinfo.user_id,emp.hrm_employee_id,emp.hrm_employee_name,emp.hrm_employee_post_id,emp.hrm_part_post,dept.dep_id,dept.hrm_dep_name ");
		result.append("from sys_user_info userinfo,hrm_employee emp,hrm_department dept,sys_user_methods usermethod ");
		result.append("where userinfo.hrm_employee_id =emp.hrm_employee_id and emp.hrm_employee_depid = dept.dep_id and usermethod.user_id = userinfo.user_id ");
		result.append("and userinfo.user_action="+EnumUtil.SYS_ISACTION.Vaild.value+" and userinfo.company_id ="+info.getCompanyId()+"");
		if (info.getEmployeeName()!=null&&info.getEmployeeName().length()>0) {
			result.append(" and emp.hrm_employee_name like '%"+info.getEmployeeName()+"%'");
		}
		if (info.getUpcode()!=null&&info.getUpcode().length()>0) {
			result.append(" and emp.hrm_employee_depid in ("+info.getUpcode()+")");
		}
		if (info.getPrimaryKey()!=null&&info.getPrimaryKey()>0) {
			result.append(" and userinfo.user_id = "+info.getPrimaryKey());
		}
		return result.toString();
	}
	
	//==============系统操作日志================
	public static String packSysLogQuery(SysLog sysLog) {
		StringBuffer result = new StringBuffer();
		result.append("select logger.* from sys_log logger,sys_user_info userinfo ");
		if(sysLog.getHrmEmployee() != null && sysLog.getHrmEmployee().getHrmEmployeeName().length()>0){ 
			result.append(",hrm_employee emp where logger.user_id = userinfo.user_id and userinfo.hrm_employee_id=emp.hrm_employee_id and emp.hrm_employee_name like '%"+sysLog.getHrmEmployee().getHrmEmployeeName()+"%'");
			
		}else{
			result.append(" where logger.user_id = userinfo.user_id ");
		}
		
		if(sysLog.getCompanyId() != null && sysLog.getCompanyId()>0){
			result.append(" and userinfo.company_id = "+sysLog.getCompanyId()+"");
		}
		SqlPack.timeBuilder(sysLog.getLogDate(),"logger.log_date",result,false,true);
		
		result.append(" order by logger.log_date desc");
		
		return result.toString();
	}
	//==============系统操作日志删除================
	public static String packSysLogDelete(String sql) {
		StringBuffer result = new StringBuffer();
		result.append(sql);
		return result.toString();
	}

	
	/**
	 * pack system exception sql
	 * 
	 */
	public static String getSysExceptionSql(SysException sysException) {
		StringBuffer result = new StringBuffer();
		result.append("select exc.*,users.user_name,comp.company_info_name from sys_exception exc,sys_user_info users,sys_company_info comp where exc.user_id=users.user_id and exc.company_id=company_info_id ");
		if (sysException.getUserInfo() != null) {
			SqlPack.getStringLikerPack(sysException.getUserInfo().getUserName(), "users.user_name", result);
		}
		if (sysException.getCompanyInfo() != null) {
			SqlPack.getStringLikerPack(sysException.getCompanyInfo().getCompanyInfoCode(), "comp.company_info_code", result);
		}
		SqlPack.getStringLikerPack(sysException.getExceptionClass(), "exc.exception_class", result);
		SqlPack.timeBuilder(sysException.getExceptionDate(), "exc.exception_date", result,false,false);
		SqlPack.getStringLikerPack(sysException.getExceptionMsg(), "exc.exception_msg", result);
		SqlPack.getNumEqualPack(sysException.getExceptionStatus(), "exc.exception_status",result);
		
		result.append(" order by exc.exception_date desc");
		
		return result.toString();
	}
	
	

}
