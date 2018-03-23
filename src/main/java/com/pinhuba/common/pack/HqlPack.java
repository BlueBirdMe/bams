package com.pinhuba.common.pack;

import com.pinhuba.common.util.UtilWork;
import com.pinhuba.core.pojo.OaAdversaria;
import com.pinhuba.core.pojo.OaAnnouncement;
import com.pinhuba.core.pojo.OaNetdiskConfig;
import com.pinhuba.core.pojo.OaNetdiskShare;
import com.pinhuba.core.pojo.OaNotice;

public class HqlPack {
	
	/**
	 * 封装日期
	 * @param date 开始日期和结束日期，以逗号分隔（分为开始时间和结束时间）
	 * @param columnName HQL里对应的时间字段
	 * @param result 封装的HQL
	 * @param showTaday 如果没有开始时间和结束时间，是否查询当天时间，还是查询所有时间。true:查询当天时间，false:查询所有
	 * @param isShowTime 是否包含时分秒
	 */
	public static void timeBuilder( String date, String columnName, StringBuffer result, Boolean showTaday,boolean isShowTime) {
		if(date != null && date.trim().length() > 0){
			String[] time = date.split(",");
			if(time.length == 1){
				//只有开始日期，没有结束日期
				result.append(" and model." + columnName + " like '%" + time[0] + "%'");
			}else if (time.length == 2 && ((time[0] != null && time[0].trim().length() > 0) || (time[1] != null & time[1].trim().length() > 0))) {
				if (time[0] == null || time[0].trim().length() == 0) {
					time[0] = "1900-01-01";
				} else {
					time[0] = time[0].trim();
				}
				if (isShowTime && time[0].trim().length()<=11) {
					time[0]+= " 00:00:00";
				}
				if (time[1] == null || time[1].trim().length() == 0) {
					time[1] = "2100-01-01";
				} else {
					time[1] = time[1].trim();
				}
				if (isShowTime&& time[1].trim().length()<=11) {
					time[1]+= " 23:59:59";
				}
				result.append(" and model." + columnName + " between '" + time[0] + "' and '" + time[1] + "'");
			} else {
				if (showTaday) {
					if (isShowTime) {
						result.append(" and model." + columnName + " between '" + UtilWork.getToday() + " 00:00:00' and '" + UtilWork.getToday() + " 23:59:59'");
					}else{
						result.append(" and model." + columnName + " between '" + UtilWork.getToday() + "' and '" + UtilWork.getToday() + "'");
					}
					
				}
			}
		}
	}
	

	/**
	 * 封装String对象成like语句
	 * @param str 对象值
	 * @param columnName 列名
	 * @param result 
	 */
	public static void getStringLikerPack(String str,String columnName, StringBuffer result) {
		if (str != null && str.trim().length() > 0) {
			result.append(" and model."+columnName+" like '%" + str + "%'");
		}
	}
	
	/**
	 * 封装String对象成like语句(右侧模糊)
	 * @param str 对象值
	 * @param columnName 列名
	 * @param result 
	 */
	public static void getStringRightLikerPack(String str,String columnName, StringBuffer result) {
		if (str != null && str.trim().length() > 0) {
			result.append(" and model."+columnName+" like '" + str + "%'");
		}
	}
	
	/**
	 * 封装String对象成like语句(左侧模糊)
	 * @param str 对象值
	 * @param columnName 列名
	 * @param result 
	 */
	public static void getStringLeftLikerPack(String str,String columnName, StringBuffer result) {
		if (str != null && str.trim().length() > 0) {
			result.append(" and model."+columnName+" like '%" + str + "'");
		}
	}
	
	/**
	 * 封装String对象成equal语句
	 * @param str 对象值
	 * @param columnName 列名
	 * @param result 
	 */
	public static void getStringEqualPack(String data,String columnName, StringBuffer result) {
		if(data != null && data.trim().length() > 0){
			result.append(" and model."+columnName+" = '" + data + "'");
		}
	}
	
	/**
	 * 封装long或者int的整数对象成equal语句
	 * @param str 对象值
	 * @param columnName 列名
	 * @param result 
	 */
	public static void getNumEqualPack(Object data,String columnName, StringBuffer result) {
		if(data!=null&&Long.parseLong(data.toString()) > 0){
			result.append(" and model."+columnName+" = " + data + "");
		}
	}
	
	/**
	 * 封装long或者int的整数对象成equal语句
	 * @param str 对象值
	 * @param columnName 列名
	 * @param result 
	 */
	public static void getNumEqualPack(Object data,String columnName, StringBuffer result,Integer expvalue) {
		if(data!=null&&Long.parseLong(data.toString()) > (long)expvalue){
			result.append(" and model."+columnName+" = " + data + "");
		}
	}
	
	/**
	 * 封装long或者int的整数对象成equal语句(不等于)
	 * @param str 对象值
	 * @param columnName 列名
	 * @param result 
	 */
	public static void getNumNOEqualPack(Object data,String columnName, StringBuffer result) {
		if(data!=null){
			result.append(" and model."+columnName+" <> " + data + "");
		}
	}
	
	/**
	 * 封装in查询语句
	 * @param data
	 * @param columnName
	 * @param result
	 */
	public static void getInPack(String data,String columnName, StringBuffer result){
		if (data!=null&&data.trim().length()>0) {
			result.append(" and model."+columnName+" in ( "+ data+ " )");
		}
	}
	
	/**
	 * 封装in String查询语句
	 * @param data
	 * @param columnName
	 * @param result
	 */
	public static void getInPackString(String data,String columnName, StringBuffer result){
		if (data!=null&&data.trim().length()>0) {
		String[] dataArray = data.substring(0, data.length()-1).split(",");
		data="";
		for (int i = 0 ; i < dataArray.length ;i++) {
			if(i ==  dataArray.length -1){
				data += "'" + dataArray[i] + "'";
			}else{
				data += "'" + dataArray[i] + "',";
			}
		}
		result.append(" and model."+columnName+" in ( "+ data+ " )");
		}
	}
	
	/**
	 * 封装not in查询语句
	 * @param data
	 * @param columnName
	 * @param result
	 */
	public static void getNotInPack(String data,String columnName, StringBuffer result){
		if (data!=null&&data.trim().length()>0) {
			result.append(" and model."+columnName+" not in ( "+ data+ " )");
		}
	}
	
	public static void getCheckStrInArr(String data,String columnName,StringBuffer result){
		if (data!=null&&data.trim().length()>0) {
//			result.append(" and CheckStrInArr('"+data+"',model."+columnName+")>0 ");
			result.append(" and INSTR(model."+columnName+",'"+data+"')>0 ");
		}
	}
	
	public static void getOrderPack(String orderPriority, StringBuffer result) {
		if(orderPriority!=null && orderPriority.trim().length()>0)
			result.append(" order by model."+orderPriority);
	}
    
	/**
	 * 查询公告条件
	 * @param announcement
	 * @param companyId      公司主键
	 * @return 
	 */
	public static String packAnnouncementQuery(OaAnnouncement announcement, long companyId) {
		StringBuffer result = new StringBuffer();
		result.append("select announcement.* from oa_announcement announcement,hrm_employee emp where announcement.oa_anno_emp = emp.hrm_employee_id and announcement.company_id = emp.company_id");
		
		SqlPack.getStringLikerPack(announcement.getOaAnnoName(), "announcement.oa_anno_name", result);
		SqlPack.getNumEqualPack(announcement.getOaAnnoType(), "announcement.oa_anno_type", result);
		if(announcement.getEmployee() != null){
			SqlPack.getStringLikerPack(announcement.getEmployee().getHrmEmployeeName(), "emp.hrm_employee_name", result);
		}
		if(announcement.getOaAnnoEmp() != null && announcement.getOaAnnoEmp().length() > 0){
			SqlPack.getStringEqualPack(announcement.getOaAnnoEmp(), "announcement.oa_anno_emp", result);
		}
		SqlPack.timeBuilder(announcement.getOaAnnoTime(),"announcement.oa_anno_time",result,false,false);
		SqlPack.getNumEqualPack(announcement.getOaAnnoType(), "announcement.oa_anno_type",result);
		SqlPack.getNumEqualPack(announcement.getOaAnnoStatus(), "announcement.oa_anno_status",result);
		SqlPack.getNumEqualPack(announcement.getOaAnnoLevel(), "announcement.oa_anno_level",result);
		SqlPack.getNumEqualPack(companyId, "announcement.company_id",result);
		return result.toString();
	}
	
	/**
	 * 查询通知条件
	 * @param notice
	 * @param companyId   公司主键
	 * @return
	 */
	public static String packNoticeQuery(OaNotice notice, long companyId) {
		StringBuffer result = new StringBuffer();
		result.append("select notice.* from oa_notice notice,hrm_employee emp where notice.oa_noti_emp = emp.hrm_employee_id and notice.company_id = emp.company_id");
		
		SqlPack.getStringLikerPack(notice.getOaNotiName(), "notice.oa_noti_name", result);
		SqlPack.getNumEqualPack(notice.getOaNotiType(), "notice.oa_noti_type", result);
		
		if(notice.getEmployee() != null){
			SqlPack.getStringLikerPack(notice.getEmployee().getHrmEmployeeName(), "emp.hrm_employee_name", result);
		}
		if(notice.getOaNotiEmp() != null && notice.getOaNotiEmp().length() > 0){
			SqlPack.getStringEqualPack(notice.getOaNotiEmp(), "notice.oa_noti_emp", result);
		}
		
		if(notice.getOaObjDep() != null || notice.getOaObjEmp() != null){
//			result.append(" and (CheckStrInArr('"+notice.getOaObjDep()+"',notice.oa_obj_dep)>0 or notice.oa_obj_emp like '%"+notice.getOaObjEmp()+"%')");
			result.append(" and (INSTR(notice.oa_obj_dep,'"+notice.getOaObjDep()+"')>0 or notice.oa_obj_emp like '%"+notice.getOaObjEmp()+"%')");
	
		}
		
		SqlPack.timeBuilder(notice.getOaNotiTime(),"notice.oa_noti_time",result,false,false);
		SqlPack.getNumEqualPack(notice.getOaNotiStatus(), "notice.oa_noti_status",result);
		SqlPack.getNumEqualPack(companyId, "notice.company_id",result);
		return result.toString();
	}
	
	/**
	 * 查询公司记事条件
	 * @param adversaria
	 * @param companyId   公司主键
	 * @return
	 */
	public static String packAdversariaQuery(OaAdversaria adversaria, long companyId) {
		StringBuffer result = new StringBuffer();
		result.append("select adversaria.* from oa_adversaria adversaria,hrm_employee emp where adversaria.oa_adver_emp = emp.hrm_employee_id and adversaria.company_id = emp.company_id");
		
		SqlPack.getStringLikerPack(adversaria.getOaAdverTitle(), "adversaria.oa_adver_title", result);
		SqlPack.getNumEqualPack(adversaria.getOaAdverLevel(), "adversaria.oa_adver_level", result);
		if(adversaria.getEmployee() != null){
		        SqlPack.getStringLikerPack(adversaria.getEmployee().getHrmEmployeeName(), "emp.hrm_employee_name", result);
		}
		if(adversaria.getOaAdverEmp() != null && adversaria.getOaAdverEmp().length()>0){
			SqlPack.getStringEqualPack(adversaria.getOaAdverEmp(), "adversaria.oa_adver_emp", result);
		}
		SqlPack.timeBuilder(adversaria.getOaAdverTime(),"adversaria.oa_adver_time",result,false,false);
		SqlPack.getNumEqualPack(adversaria.getOaAdverStatus(), "adversaria.oa_adver_status",result);
		SqlPack.getNumEqualPack(companyId, "adversaria.company_id",result);
		return result.toString();
	}
    
	
	/**
	 * 查询网络磁盘共享
	 * @param msg   
	 * @return
	 */
	public static String packNetdiskShare(OaNetdiskShare oaNetdiskShare,String empid,String deptid) {
		StringBuffer result = new StringBuffer();
		getNumEqualPack(oaNetdiskShare.getCompanyId(), "companyId", result);
		getStringEqualPack(oaNetdiskShare.getFolderPath(), "folderPath", result);
		getStringEqualPack(oaNetdiskShare.getHrmEmployeeId(), "hrmEmployeeId", result);
//		result.append(" and model.folderPath like '"+oaNetdiskShare.getFolderPath()+"%'");
//		result.append(" and ( model.netdiskEmps like '%"+empid+"%' or CheckStrInArr('"+deptid+"', model.netdiskDeps)>0) ");
		result.append(" and ( model.netdiskEmps like '%"+empid+"%' or INSTR(model.netdiskDeps,'"+deptid+"')>0) ");

		return result.toString();
	}
	
	/**
	 * 查询网络磁盘共享, by folder path
	 * @param msg   
	 * @return
	 */
	public static String packNetdiskShareByFolderPath(OaNetdiskShare oaNetdiskShare) {
		StringBuffer result = new StringBuffer();
		getNumEqualPack(oaNetdiskShare.getCompanyId(), "companyId", result);
		getStringEqualPack(oaNetdiskShare.getHrmEmployeeId(), "hrmEmployeeId", result);
		result.append(" and model.folderPath like '"+oaNetdiskShare.getFolderPath()+"%'");
		return result.toString();
	}
	
	/**
	 *  查询磁盘使用人
	 * @param OaNetdisk
	 * @return
	 */
	public static String packOaNetdiskQuery(OaNetdiskConfig OaNetdisk) {
		StringBuffer result = new StringBuffer();
		result.append("select  distinct hrm_employee_id from hrm_employee where hrm_employee_id not in(select hrm_employee_id from oa_netdisk_config)");
		SqlPack.getNumEqualPack(OaNetdisk.getCompanyId(), "company_id", result);
		return result.toString();
	}

	/**
	 * 网络磁盘
	 * @param OaNetdisk
	 * @return
	 */
	public static String packNetdisksQuery(OaNetdiskConfig OaNetdisk) {
		StringBuffer result = new StringBuffer();
		result.append("select distinct employee.hrm_employee_code,netdisk.* from hrm_employee employee,oa_netdisk_config netdisk,hrm_department department where netdisk.hrm_employee_id=employee.hrm_employee_id and employee.hrm_employee_depid =department.dep_id");
		if (OaNetdisk.getHrmEmployee()!=null) {
			SqlPack.getInPack(OaNetdisk.getHrmEmployee().getHrmEmployeeDepidTree(), "department.dep_id", result);
			SqlPack.getStringLikerPack(OaNetdisk.getHrmEmployee().getHrmEmployeeName(), "employee.hrm_employee_name", result);
			SqlPack.getStringLikerPack(OaNetdisk.getHrmEmployee().getHrmEmployeeCode(), "employee.hrm_employee_code", result);
		}
		SqlPack.getNumEqualPack(OaNetdisk.getCompanyId(),"netdisk.company_id",result);
		
		return result.toString();
	}
	
	/**
	 * 网络磁盘配置
	 * @param oaNetdisk
	 * @return
	 */
	public static String packNetdiskConfigQuery(OaNetdiskConfig oaNetdisk) {
		StringBuffer result = new StringBuffer();
		result
				.append("select distinct model.* from oa_netdisk_config model where model.hrm_employee_id='"
						+ oaNetdisk.getHrmEmployeeId()
						+ "' and model.company_id='" + oaNetdisk.getCompanyId()+"'");
		return result.toString();
	}
}