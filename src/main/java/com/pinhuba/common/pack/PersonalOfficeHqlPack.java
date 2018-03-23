package com.pinhuba.common.pack;

import com.pinhuba.common.util.UtilWork;
import com.pinhuba.core.pojo.HrmTimedrecord;
import com.pinhuba.core.pojo.OaLeaveregistration;
import com.pinhuba.core.pojo.OaNotebook;
import com.pinhuba.core.pojo.OaTools;
import com.pinhuba.core.pojo.OaTrsvel;

public class PersonalOfficeHqlPack {

	//请假HQL
	public static String packOaLeaveregistrationQuery(OaLeaveregistration oaLeaver){
		StringBuffer result = new StringBuffer();
		HqlPack.getNumEqualPack(oaLeaver.getCompanyId(), "companyId", result);
		HqlPack.getStringEqualPack(oaLeaver.getApplyuser(),"applyuser", result);
		HqlPack.getNumEqualPack(oaLeaver.getLeavetype(), "leavetype",result);
		HqlPack.timeBuilder(oaLeaver.getApplydata(), "applydata", result,false,true);
		HqlPack.getStringLikerPack(oaLeaver.getLeavereason(), "leavereason",
				result);
		return result.toString();
	}
	
	/**
	 * 请假登记-审批
	 * @param OaRefisterout 外出登记
	 * @return 
	 */
	public static String packOaLeaveregistrationApprove(OaLeaveregistration oaLeaver,String ids){
		StringBuffer result = new StringBuffer();
		result.append("select lea.* from oa_leaveregistration  lea,hrm_employee emp where lea.applyuser = emp.hrm_employee_id ");
		SqlPack.getStringLikerPack(oaLeaver.getLeavereason(), "lea.leavereason", result);
		if (oaLeaver.getApplyEmployee()!=null) {
			SqlPack.getStringLikerPack(oaLeaver.getApplyEmployee().getHrmEmployeeName(),"emp.hrm_employee_name", result);
		}
		SqlPack.timeBuilder(oaLeaver.getApplydata(), "lea.applydata", result,false,true);
		SqlPack.getNumEqualPack(oaLeaver.getLeavetype(), "lea.leavetype", result);
		SqlPack.getNumEqualPack(oaLeaver.getCompanyId(), "lea.company_id", result);
		SqlPack.getInPack(ids, "lea.id", result);
		return result.toString();
	} 
	//出差HQL
	public static String packoaTrsvelQuery(OaTrsvel oaTrsvel){
		StringBuffer result = new StringBuffer();
		HqlPack.getStringEqualPack(oaTrsvel.getTrsvelApplyuser(), "trsvelApplyuser", result);
		HqlPack.getNumEqualPack(oaTrsvel.getCompanyId(), "companyId", result);
		HqlPack.getStringLikerPack(oaTrsvel.getTrsvelArea(),"trsvelArea", result);
		HqlPack.timeBuilder(oaTrsvel.getApplydata(), "applydata", result,false,true);
	
		return result.toString();
	}
	//出差HQL
	public static String packsuperoaTrsvelQuery(OaTrsvel oaTrsvel){
		StringBuffer result = new StringBuffer();
		
		HqlPack.getNumEqualPack(oaTrsvel.getCompanyId(), "companyId", result);
		HqlPack.getStringLikerPack(oaTrsvel.getTrsvelArea(),"trsvelArea", result);
		HqlPack.timeBuilder(oaTrsvel.getApplydata(), "applydata", result,false,true);
	
		return result.toString();
	}
	public static String packOaTrsvelApprove(OaTrsvel oaTrsvel,String ids){
		StringBuffer result = new StringBuffer();
		result.append("select trs.* from oa_trsvel trs,hrm_employee emp where trs.trsvel_appalyuser = emp.hrm_employee_id ");
		SqlPack.getStringLikerPack(oaTrsvel.getTrsvelArea(), "trs.trsvel_area", result);
		SqlPack.getStringLikerPack(oaTrsvel.getTrsvelCause(), "trs.trsvel_cause", result);
		if (oaTrsvel.getApplyEmployee()!=null) {
			SqlPack.getStringEqualPack(oaTrsvel.getApplyEmployee().getHrmEmployeeName(),"emp.hrm_employee_name", result);
		}
		SqlPack.timeBuilder(oaTrsvel.getApplydata(), "trs.applydata", result,false,true);
		SqlPack.getNumEqualPack(oaTrsvel.getCompanyId(), "trs.company_id", result);
		SqlPack.getInPack(ids, "trs.id", result);
		return result.toString();
	}

	//显示所有工具
	public static String packoaToolsQuery(OaTools oaTools){
		StringBuffer result = new StringBuffer();
		HqlPack.getStringEqualPack(oaTools.getOaToolEmp(), "oaToolEmp", result);
		HqlPack.getNumEqualPack(oaTools.getCompanyId(), "companyId", result);
		return result.toString();
	}	
    //显示所有个人便签
	public static String packNotebookQuery(OaNotebook notebook) {
		StringBuffer result = new StringBuffer();
		HqlPack.getStringEqualPack(notebook.getOaNotebookEmp(), "oaNotebookEmp", result);
		HqlPack.getStringLikerPack(notebook.getOaNotebookContext(), "oaNotebookContext", result);
		HqlPack.timeBuilder(notebook.getOaNotebookCreattime(), "oaNotebookCreattime", result, false, true);
		HqlPack.getNumEqualPack(notebook.getCompanyId(), "companyId", result);
		return result.toString();		
	}
	
	//查询所有公司的有效提醒
	public static String packAllCompanyTimedValid() {
		StringBuffer result = new StringBuffer();
		result.append(" and model.timedDate > '"+UtilWork.getNowTime()+"' or model.timedType = 0");
		return result.toString();
	}
	
	//查询有效提醒，by companyId and hrmEmpId
	public static String packHrmTimedRecordQueryValid(HrmTimedrecord record) {
		StringBuffer result = new StringBuffer();
		HqlPack.getNumEqualPack(record.getCompanyId(), "companyId", result);
		HqlPack.getStringEqualPack(record.getRecordId(), "recordId", result);
		HqlPack.timeBuilder(record.getTimedDate(), "timedDate", result, false, false);
		HqlPack.getStringLikerPack(record.getTimedDescription(), "timedDescription", result);
		result.append(" and (model.timedDate > '"+UtilWork.getNowTime()+"' or model.timedType = 0)");
		return result.toString();
	}
	
	//查询无效提醒，by companyId and hrmEmpId
	public static String packHrmTimedRecordQueryInValid(HrmTimedrecord record) {
		StringBuffer result = new StringBuffer();
		HqlPack.getNumEqualPack(record.getCompanyId(), "companyId", result);
		HqlPack.getStringEqualPack(record.getRecordId(), "recordId", result);
		HqlPack.timeBuilder(record.getTimedDate(), "timedDate", result, false, false);
		HqlPack.getStringLikerPack(record.getTimedDescription(), "timedDescription", result);
		result.append(" and model.timedDate < '"+UtilWork.getNowTime()+"' and model.timedType = 1");
		return result.toString();
	}
	
	//请假管理HQL
	public static String packAllOaLeaveregistrationQuery(OaLeaveregistration oaLeaver){
		StringBuffer result = new StringBuffer();
		result.append("select le.* from oa_leaveregistration le,hrm_employee hr where le.applyuser=hr.hrm_employee_id");
		SqlPack.getNumEqualPack(oaLeaver.getCompanyId(), "le.company_id", result);
		SqlPack.getStringEqualPack(oaLeaver.getApplyuser(),"le.applyuser", result);
		SqlPack.getNumEqualPack(oaLeaver.getLeavetype(), "le.leavetype",result);
		SqlPack.timeBuilder(oaLeaver.getApplydata(), "le.applydata", result,false,true);
		SqlPack.getStringLikerPack(oaLeaver.getLeavereason(), "le.leavereason",result);
		return result.toString();
	}
}
