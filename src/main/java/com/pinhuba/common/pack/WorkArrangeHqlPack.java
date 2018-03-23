package com.pinhuba.common.pack;

import com.pinhuba.core.pojo.OaCalender;
import com.pinhuba.core.pojo.OaWorkLog;

public class WorkArrangeHqlPack {

	// ==============工作安排-工作日志============
	public static String getOaWorkLogSql(OaWorkLog oaWorkLog) {
		StringBuffer result = new StringBuffer();
		HqlPack.getStringLikerPack(oaWorkLog.getOaWorklogTitle(), "oaWorklogTitle", result);
		HqlPack.getStringLikerPack(oaWorkLog.getOaWorklogLogger(), "oaWorklogLogger", result);
		HqlPack.getStringLikerPack(oaWorkLog.getOaWorklogContent(), "oaWorklogContent", result);
		HqlPack.timeBuilder(oaWorkLog.getOaWorklogDate(), "oaWorklogDate", result, false, false);
		HqlPack.getNumEqualPack(oaWorkLog.getOaWorklogType(), "oaWorklogType", result);
		HqlPack.getNumEqualPack(oaWorkLog.getOaWorklogRange(), "oaWorklogRange", result);
		HqlPack.getNumEqualPack(oaWorkLog.getCompanyId(), "companyId", result);
		return result.toString();
	}

	// ==============工作安排-共享日志============
	public static String getOaShareWorkLogSql(OaWorkLog oaWorkLog) {
		StringBuffer result = new StringBuffer();
		result.append("select  worklog.* from oa_work_log worklog,hrm_employee emp,hrm_department dept" +
				" where worklog.oa_worklog_logger = emp.hrm_employee_id" +
				" and emp.hrm_employee_depid = dept.dep_id" +
				" and worklog.oa_worklog_range=2 ");
		if (oaWorkLog.getCompanyId() != null && oaWorkLog.getCompanyId() > 0) {
			result.append(" and worklog.company_id = " + oaWorkLog.getCompanyId() + "");
		}
		if (oaWorkLog.getOaWorklogTitle() != null && oaWorkLog.getOaWorklogTitle().length() > 0) {
			result.append(" and worklog.oa_worklog_title like '%" + oaWorkLog.getOaWorklogTitle() + "%'");
		}
		if (oaWorkLog.getOaWorklogType() != null && oaWorkLog.getOaWorklogType() > 0) {
			result.append(" and worklog.oa_worklog_type = " + oaWorkLog.getOaWorklogType() + "");
		}
		if (oaWorkLog.getOaWorklogDeps() != null && oaWorkLog.getOaWorklogEmps() != null) {
//			result.append(" and (CheckStrInArr('"+oaWorkLog.getOaWorklogDeps()+"',worklog.oa_worklog_deps )>0  or worklog.oa_worklog_emps like '%" + oaWorkLog.getOaWorklogEmps() + "%')");
			result.append(" and (INSTR(worklog.oa_worklog_deps,'"+oaWorkLog.getOaWorklogDeps()+"' )>0  or worklog.oa_worklog_emps like '%" + oaWorkLog.getOaWorklogEmps() + "%')");
			
		}
		if (oaWorkLog.getOaWorklogDate() != null && oaWorkLog.getOaWorklogDate().length() > 0) {
			String[] date = oaWorkLog.getOaWorklogDate().split(",");
			if (date.length == 1) {
				result.append(" and worklog.oa_worklog_date like '%" + date[0] + "%'");
			
			} else {
				result.append(" and worklog.oa_worklog_date between '" + date[0] + "' and '" + date[1] + "' ");
			}
		}
		if (oaWorkLog.getHrmEmployee() != null) {
			if (oaWorkLog.getHrmEmployee().getHrmEmployeeName() != null && oaWorkLog.getHrmEmployee().getHrmEmployeeName().length() > 0 && oaWorkLog.getCompanyId() != null
					&& oaWorkLog.getCompanyId() > 0) {
				result.append(" and worklog.oa_worklog_logger in (select hrm_employee_id from hrm_employee emp where emp.company_id = " + oaWorkLog.getCompanyId()
						+ " and emp.hrm_employee_name like '%" + oaWorkLog.getHrmEmployee().getHrmEmployeeName() + "%')");
			}
			if (oaWorkLog.getHrmEmployee().getHrmEmployeeDepidTree() != null && oaWorkLog.getHrmEmployee().getHrmEmployeeDepidTree().length() > 0){
				result.append(" and dept.dep_id in ("+oaWorkLog.getHrmEmployee().getHrmEmployeeDepidTree()+")");
			}
		}

		return result.toString();
	}
	
	public static String getOaCalenderSql(OaCalender oaCalender) {
		StringBuffer result = new StringBuffer();
		HqlPack.getStringEqualPack(oaCalender.getOaCalenderEmp(), "oaCalenderEmp", result);
		HqlPack.getNumEqualPack(oaCalender.getCompanyId(), "companyId", result);
		HqlPack.getNumEqualPack(oaCalender.getOaCalenderLevel(), "oaCalenderLevel", result);
		HqlPack.getNumEqualPack(oaCalender.getOaCalenderStatus(), "oaCalenderStatus", result);
		HqlPack.getNumEqualPack(oaCalender.getOaCalenderType(), "oaCalenderType", result);
		HqlPack.getStringLikerPack(oaCalender.getOaCalenderContent(), "oaCalenderContent", result);
		HqlPack.timeBuilder(oaCalender.getOaCalenderStart(), "oaCalenderStart", result, true, true);
		return result.toString();
	}
}
