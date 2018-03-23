package com.pinhuba.common.pack;

import com.pinhuba.common.util.EnumUtil;
import com.pinhuba.common.util.UtilWork;
import com.pinhuba.core.pojo.HrmEmployee;
import com.pinhuba.core.pojo.SysColumnControl;
import com.pinhuba.core.pojo.SysCompanyInfo;
import com.pinhuba.core.pojo.SysHelp;
import com.pinhuba.core.pojo.SysLibraryInfo;
import com.pinhuba.core.pojo.SysLibraryStandard;
import com.pinhuba.core.pojo.SysLogRuntime;
import com.pinhuba.core.pojo.SysMethodBtn;
import com.pinhuba.core.pojo.SysMethodHelp;
import com.pinhuba.core.pojo.SysMethodInfo;
import com.pinhuba.core.pojo.SysMethodShortcut;
import com.pinhuba.core.pojo.SysMsg;
import com.pinhuba.core.pojo.SysParam;

/**
 * 系统后台设置
 * @author peng.ning
 * @date   Mar 4, 2010
 */
public class SystemPack {
	
	public static String getCompanySql(SysCompanyInfo companyInfo){
		StringBuffer result = new StringBuffer();
		HqlPack.getStringLikerPack(companyInfo.getCompanyInfoName(), "companyInfoName", result);
		HqlPack.getStringLikerPack(companyInfo.getCompanyInfoShortname(), "companyInfoShortname", result);
		HqlPack.getStringLikerPack(companyInfo.getCompanyInfoEmployee(), "companyInfoEmployee", result);
		HqlPack.timeBuilder(companyInfo.getCompanyInfoRegDate(), "companyInfoRegDate", result, false, true);
		HqlPack.getStringLikerPack(companyInfo.getCompanyInfoCode(), "companyInfoCode", result);
		HqlPack.timeBuilder(companyInfo.getCompanyInfoSdate(), "companyInfoSdate",result, false, false);
		HqlPack.timeBuilder(companyInfo.getCompanyInfoEdate(), "companyInfoEdate", result, false, false);
		HqlPack.getNumEqualPack(companyInfo.getCompanyInfoStatus(), "companyInfoStatus", result);
		HqlPack.getNumEqualPack(companyInfo.getCompanyInfoType(), "companyInfoType", result);
		if ("end".equalsIgnoreCase(companyInfo.getCompanyInfoContext())) {
			result.append(" and model.companyInfoEdate<='"+UtilWork.getToday()+"'");
			HqlPack.getNumEqualPack(EnumUtil.SYS_COMPANY_STATUS.TAKE.value, "companyInfoStatus", result);
			HqlPack.getInPack(EnumUtil.SYS_COMPANY_TYPE.TRIAL.value+","+EnumUtil.SYS_COMPANY_TYPE.OFFICIAL.value, "companyInfoType", result);
		}
		return result.toString();
	}
	
	public static String getCompanySqlForParam(SysCompanyInfo companyInfo){
		StringBuffer result = new StringBuffer();
		HqlPack.getStringLikerPack(companyInfo.getCompanyInfoName(), "companyInfoName", result);
		HqlPack.getStringLikerPack(companyInfo.getCompanyInfoShortname(), "companyInfoShortname", result);
		HqlPack.getStringLikerPack(companyInfo.getCompanyInfoEmployee(), "companyInfoEmployee", result);
		HqlPack.timeBuilder(companyInfo.getCompanyInfoRegDate(), "companyInfoRegDate", result, false, true);
		HqlPack.getStringLikerPack(companyInfo.getCompanyInfoCode(), "companyInfoCode", result);
		HqlPack.timeBuilder(companyInfo.getCompanyInfoSdate(), "companyInfoSdate",result, false, false);
		HqlPack.timeBuilder(companyInfo.getCompanyInfoEdate(), "companyInfoEdate", result, false, false);
		HqlPack.getNumEqualPack(companyInfo.getCompanyInfoStatus(), "companyInfoStatus", result);
		HqlPack.getInPack(EnumUtil.SYS_COMPANY_TYPE.TRIAL.value+","+EnumUtil.SYS_COMPANY_TYPE.OFFICIAL.value + "," + EnumUtil.SYS_COMPANY_TYPE.SYSTEM.value , "companyInfoType", result);
		result.append(" and model.companyInfoEdate >= '"+UtilWork.getToday()+"'");
		return result.toString();
	}
	
	public static String getSysParamSlq(SysParam param){
		StringBuffer result = new StringBuffer();
		HqlPack.getStringLikerPack(param.getParamIndex(), "paramIndex", result);
		HqlPack.getStringLikerPack(param.getParamTitle(), "paramTitle", result);
		HqlPack.getNumEqualPack(param.getParamType(), "paramType", result);
		HqlPack.getNumEqualPack(param.getCompanyId(), "companyId", result);
		return result.toString();
	}
	
	public static String packEmployeeQuery(HrmEmployee employee) {
       StringBuffer result = new StringBuffer();
       HqlPack.getStringLikerPack(employee.getHrmEmployeeName(), "hrmEmployeeName", result);
       HqlPack.getStringLikerPack(employee.getHrmEmployeeCode(), "hrmEmployeeCode", result);
       HqlPack.getNumEqualPack(employee.getHrmEmployeeSquadId(), "hrmEmployeeSquadId", result);
       HqlPack.getNumEqualPack(employee.getHrmEmployeeDepid(), "hrmEmployeeDepid", result);
       HqlPack.getInPack(employee.getHrmEmployeeDepidTree(), "hrmEmployeeDepid", result);
       HqlPack.getNumEqualPack(EnumUtil.SYS_ISACTION.Vaild.value, "hrmEmployeeActive", result);
       HqlPack.getNumEqualPack(employee.getCompanyId(), "companyId", result);
		return result.toString();
	}
	
	
	/**
	 * 查询系统公告
	 * @param msg   
	 * @return
	 */
	public static String packSysMsgQuery(SysMsg msg) {
		StringBuffer result = new StringBuffer();
		
		HqlPack.getStringLikerPack(msg.getMsgTitle(), "msgTitle", result);
		HqlPack.timeBuilder(msg.getMsgDate(), "msgDate", result, false, false);
		HqlPack.timeBuilder(msg.getMsgVsdate(), "msgVsdate", result, false, false);
		HqlPack.timeBuilder(msg.getMsgVedate(), "msgVedate", result, false, false);
		return result.toString();
	}
	
	/**
	 * 查询系统帮助
	 * @param msg   
	 * @return
	 */
	public static String packSysHelpQuery(SysHelp help) {
		StringBuffer result = new StringBuffer();
		
		HqlPack.getStringLikerPack(help.getHelpKeyword(), "helpKeyword", result);
		HqlPack.getStringLikerPack(help.getHelpTitle(), "helpTitle", result);
		HqlPack.getStringLikerPack(help.getFindSign(), "findSign", result);
		HqlPack.getStringRightLikerPack(help.getMethodCode(), "methodCode", result);
		return result.toString();
	}
	
	
	/**
	 * 系统目录管理
	 * @param sysMethodInfo
	 * @return
	 */
	public static String packSysMethodInfo(SysMethodInfo info) {
		StringBuffer result = new StringBuffer();
		HqlPack.getStringLikerPack(info.getPrimaryKey(), "primaryKey", result);
		HqlPack.getStringLikerPack(info.getMethodInfoName(), "methodInfoName", result);
		HqlPack.getStringLikerPack(info.getMethodInfoEngname(), "methodInfoEngname", result);
		HqlPack.getNumEqualPack(info.getMethodLevel(), "methodLevel", result, -2);
		HqlPack.getNumEqualPack(info.getIsAction(), "isAction", result);
		HqlPack.getNumEqualPack(info.getIsDefault(), "isDefault", result);
		HqlPack.getNumEqualPack(info.getMethodNo(), "methodNo", result);
		HqlPack.getStringRightLikerPack(info.getLevelUnit(), "levelUnit", result);
		return result.toString();
	}
	
	/**
	 * 查询页面显示列查询
	 * @param sysColumnControl
	 * @return
	 */
	public static String packSysColumn(SysColumnControl sysColumnControl) {
		StringBuffer result = new StringBuffer();

		HqlPack.getNumEqualPack(sysColumnControl.getPrimaryKey(),"primaryKey",result);
		HqlPack.getNumEqualPack(sysColumnControl.getIsShow(),"isShow",result);
		HqlPack.getStringEqualPack(sysColumnControl.getTableName(),"tableName",result);
		HqlPack.getStringEqualPack(sysColumnControl.getColumnName(),"columnName",result);
		HqlPack.getStringEqualPack(sysColumnControl.getColumnCode(),"columnCode",result);
		result.append(" order by model.priority asc");	
		return result.toString();
	}
	
	/**
	 * 查询页面显示列查询
	 * @param sysColumnControl
	 * @return
	 */
	public static String packSysColumnControlQuery(SysColumnControl sysColumnControl) {
		StringBuffer result = new StringBuffer();
		
		HqlPack.getNumEqualPack(sysColumnControl.getPrimaryKey(),"primaryKey",result);
		HqlPack.getNumEqualPack(sysColumnControl.getIsShow(),"isShow",result);
		HqlPack.getStringLikerPack(sysColumnControl.getTableName(),"tableName",result);
		HqlPack.getStringLikerPack(sysColumnControl.getColumnName(),"columnName",result);
		HqlPack.getStringLikerPack(sysColumnControl.getColumnCode(),"columnCode",result);
		result.append(" order by model.tableName,model.priority asc");
		return result.toString();
	}
	
	
	/**
	 * 业务字典表查询
	 * @param library
	 * @return
	 */
	public static String packSysLibraryInfo(SysLibraryInfo library){
		StringBuffer result = new StringBuffer();
		HqlPack.getStringEqualPack(library.getLibraryInfoCode(), "libraryInfoCode", result);
		HqlPack.getStringLikerPack(library.getLibraryInfoName(), "libraryInfoName", result);
		HqlPack.getStringEqualPack(library.getLibraryInfoUpcode(), "libraryInfoUpcode", result);
		HqlPack.getNumEqualPack(library.getLibraryInfoIsedit(), "libraryInfoIsedit",result);
		HqlPack.getNumEqualPack(library.getLibraryInfoIsvalid(), "libraryInfoIsvalid",result);
		return result.toString();
	}
	
	
	/**
	 * 标准代码表查询
	 * @param library
	 * @return
	 */
	public static String packSysLibraryStandardInfo(SysLibraryStandard library){
		StringBuffer result = new StringBuffer();
		HqlPack.getStringEqualPack(library.getLibraryCode(), "libraryCode", result);
		HqlPack.getStringLikerPack(library.getLibraryName(), "libraryName", result);
		HqlPack.getStringEqualPack(library.getLibraryUpcode(), "libraryUpcode", result);
		HqlPack.getStringLikerPack(library.getLibraryDesc(), "libraryDesc", result);
		result.append(" order by model.libraryCode asc");
		return result.toString();
	}
	
	public static String packSysMethodHelp(SysMethodHelp methodHelp) {
		StringBuffer result = new StringBuffer();
		result.append("select help.* from sys_method_help help,sys_method_info info where help.method_id = info.method_info_id ");
		SqlPack.getStringLikerPack(methodHelp.getMethodName(), "info.method_info_name", result);
		return result.toString();
	}

	public static String packSysMethodBtn(SysMethodBtn methodBtn) {

		StringBuffer result = new StringBuffer();
		result.append(" order by model.priority asc");
		return result.toString();
	}

	public static String packSysMethodShortcutQuery(SysMethodShortcut sysMethodShortcut) {
		StringBuffer result = new StringBuffer();
		return result.toString();
	}

	public static String packSysLogRuntimeQuery(SysLogRuntime sysLogRuntime) {
		StringBuffer result = new StringBuffer();
		HqlPack.getStringEqualPack(sysLogRuntime.getLogLevel(), "logLevel", result);
		HqlPack.timeBuilder(sysLogRuntime.getCreateTime(), "createTime", result, false, false);
		result.append(" order by model.primaryKey desc");
		return result.toString();
	}
	
	/**
	 * 取得下级菜单
	 * @param code 上级编码
	 * @param level 等级
	 * @param bl 是否取得系统菜单
	 * @return
	 */
	public static String packSysMethodInfoByTree(String code){
		StringBuffer result = new StringBuffer();
		HqlPack.getStringEqualPack(code, "levelUnit", result);
		HqlPack.getNumNOEqualPack(-1, "methodNo", result);
		HqlPack.getNumEqualPack(EnumUtil.SYS_ISACTION.Vaild.value, "isAction", result);
		return result.toString();
	}
	
}
