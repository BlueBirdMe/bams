package com.pinhuba.common.pack;

import com.pinhuba.common.util.UtilWork;

public class SqlPack {
	/**
	 * 封装日期
	 * @param date 开始日期和结束日期，以逗号分隔（分为开始时间和结束时间）
	 * @param columnName HQL里对应的时间字段
	 * @param result 封装的HQL
	 * @param showTaday 如果没有开始时间和结束时间，是否查询当天时间，还是查询所有时间。true:查询当天时间，false:查询所有
	 */
	public static void timeBuilder( String date, String columnName, StringBuffer result, Boolean showTaday,Boolean isshowTime) {
		if(date != null && date.trim().length() > 0){
			String[] time = date.split(",");
			if(time.length == 1){
				//只有开始日期，没有结束日期
				result.append(" and " + columnName + " like '%" + time[0] + "%'");
			}else if (time.length == 2 && ((time[0] != null && time[0].trim().length() > 0) || (time[1] != null & time[1].trim().length() > 0))) {
				if (time[0] == null || time[0].trim().length() == 0) {
					time[0] = "1900-01-01";
				} else{
					time[0] = time[0].trim();
				}
				if (isshowTime && time[0].trim().length()<=11) {
					time[0]+=" 00:00:00";
				}
				if (time[1] == null || time[1].trim().length() == 0) {
					time[1] = "2100-01-01";
				} else {
					time[1] = time[1].trim();
				}
				if (isshowTime && time[1].trim().length()<=11) {
					time[1]+=" 23:59:59";
				}
				result.append(" and " + columnName + " between '" + time[0] + "' and '" + time[1] + "'");
			} else {
				if (showTaday) {
					if (isshowTime) {
						result.append(" and " + columnName + " between '" + UtilWork.getToday() + " 00:00:00' and '" + UtilWork.getToday() + " 23:59:59'");
					}else{
						result.append(" and " + columnName + " between '" + UtilWork.getToday() + "' and '" + UtilWork.getToday() + "'");
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
			result.append("  and "+columnName+" like '%" + str + "%'");
		}
	}
	public static void getStringLikerPack(String str,String columnName, StringBuffer result,String andor) {
		if (str != null && str.trim().length() > 0) {
			result.append("  "+andor+" "+columnName+" like '%" + str + "%'");
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
			result.append(" and "+columnName+" like '" + str + "%'");
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
			result.append(" and "+columnName+" like '%" + str + "'");
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
			result.append(" and "+columnName+" = '" + data + "'");
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
			result.append(" and "+columnName+" = " + data + "");
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
			result.append(" and "+columnName+" = " + data + "");
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
			result.append(" and "+columnName+" <> " + data + "");
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
			result.append(" and "+columnName+" in ( "+ data+ " )");
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
			result.append(" and "+columnName+" not in ( "+ data+ " )");
		}
	}
	
	public static void getCheckStrInArr(String data,String columnName,StringBuffer result){
		if (data!=null&&data.trim().length()>0) {
//			result.append(" and CheckStrInArr('"+data+"',"+columnName+")>0 ");
			result.append(" and INSTR("+columnName+",'"+data+"')>0 ");
		}
	}
}
