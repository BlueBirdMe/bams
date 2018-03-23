package com.pinhuba.common.util;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

public class UtilWork {
	private static final Logger logger = Logger.getLogger(UtilWork.class);

	public static String[] dateMaker(String date) {
		String[] str = null;

		if (date == null) {
			return null;
		} else if (date != "all") {
			Calendar cal = Calendar.getInstance();
			str[1] = cal.get(cal.YEAR) + "-" + (cal.get(cal.MONTH) + 1) + "-"
					+ cal.get(cal.DATE) + " 23:59:59";

			if (date == "today") {
				str[0] = cal.get(cal.YEAR) + "-" + (cal.get(cal.MONTH) + 1)
						+ "-" + cal.get(cal.DATE) + " 00:00:01";
			} else if (date == "yesterday") {
				str[0] = cal.get(cal.YEAR) + "-" + (cal.get(cal.MONTH) + 1)
						+ "-" + (cal.get(cal.DATE) - 1) + " 00:00:01";
			} else if (date == "week") {
				str[0] = cal.get(cal.YEAR) + "-" + (cal.get(cal.MONTH) + 1)
						+ "-" + (cal.get(cal.DATE) - 1) + " 00:00:01";
			} else if (date == "month") {
			} else if (date == "year") {
			}
		}

		return str;
	}
	
	/**
	 * 获取日期和时间 yyyy-MM-dd HH:mm:ss
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String getNowTime(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}
	
	/**
	 * 获取日期和时间，没有秒 yyyy-MM-dd HH:mm
	 * @return yyyy-MM-dd HH:mm
	 */
	public static String getNowTimeNoSec(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return sdf.format(new Date());
	}
	
	/**
	 * 获取日期和时间
	 * @return 日期和时间
	 */
	public static String getCustomerDay(String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date());
	}
	
	/**
	 * 获取当前时间 HH:mm:ss
	 * @return HH:mm:ss
	 */
	public static String getSimpleTime(){
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		return sdf.format(new Date());
	}
	
	/**
	 * 获取当前日期 yyyy-MM-dd
	 * @return yyyy-MM-dd
	 */
	public static String getToday(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date());
	}

	public static String getTodayAdd(int year,int month,int day){
		String tmpstr="";
		GregorianCalendar calendar=new GregorianCalendar();
		int yearnow=calendar.get(Calendar.YEAR)+year;
		int monthnow = calendar.get(Calendar.MONTH)+month;
		int daynow = calendar.get(Calendar.DAY_OF_MONTH)+day;
		tmpstr+=yearnow+"-";
		if (monthnow<10) {
			tmpstr+="0"+monthnow+"-";
		}else{
			tmpstr+=monthnow+"-";
		}
		if (daynow<10) {
			tmpstr+="0"+daynow;
		}else{
			tmpstr+=daynow;
		}
		return tmpstr;
	}
	
	
	public static String changeEncode(String source) throws UnsupportedEncodingException{
		if(source != null){
			return new String(source.getBytes("ISO-8859-1"),"GBK");
		}else{
			return null;
		}
	}
	

	public static String changeEncode(String source, String fromEncode,
			String toEncode) throws UnsupportedEncodingException {
		if (source != null) {
			return new String(source.getBytes(fromEncode), toEncode);
		} else {
			return source;
		}
	}

	public static String buildString(String source)
			throws UnsupportedEncodingException {
		if (source != null) {
			return new String(source.trim().getBytes("ISO-8859-1"), "UTF-8");
		} else {
			return source;
		}
	}
	
	/**
	 * 验证时间前后
	 * @param time	标准时间
	 * @param timeCheck	比较时间
	 * @return	true timeCheck在time之前，false timeCheck在time之后
	 */
	public static boolean checkTime(String time,String timeCheck){
		if(time.length() == 5){
			time += ":00";
		}
		if(timeCheck.length() == 5){
			timeCheck += ":00";
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		try {
			return dateFormat.parse(timeCheck).before(dateFormat.parse(time));
		} catch (ParseException e) {
			logger.error("时间比较出错："+e.getMessage());
			return false;
		}
	}
	
	/**
	 * 验证日期前后
	 * @param time	标准时间
	 * @param timeCheck	比较时间
	 * @return	true timeCheck在time之前，false timeCheck在time之后
	 */
	public static boolean checkDayBySF(String time,String timeCheck,SimpleDateFormat sf){
		try {
			return sf.parse(timeCheck).before(sf.parse(time));
		} catch (ParseException e) {
			logger.error("时间比较出错："+e.getMessage());
			return false;
		}
	}
	
	
	/**
	 * 验证日期前后
	 * @param time	标准时间
	 * @param timeCheck	比较时间
	 * @return	true timeCheck在time之前，false timeCheck在time之后
	 */
	public static boolean checkDay(String time,String timeCheck){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return dateFormat.parse(timeCheck).before(dateFormat.parse(time));
		} catch (ParseException e) {
			logger.error("时间比较出错："+e.getMessage());
			return false;
		}
	}
	
	/**
	 * 验证日期时间前后
	 * @param time	标准时间
	 * @param timeCheck	比较时间
	 * @return	true timeCheck在time之前，false timeCheck在time之后
	 */
	public static boolean checkDate(String time,String timeCheck){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			return dateFormat.parse(timeCheck).before(dateFormat.parse(time));
		} catch (ParseException e) {
			logger.error("时间比较出错："+e.getMessage());
			return false;
		}
	}
	
	
	/**
	 * 验证日期时间前后
	 * @param time	标准时间
	 * @param timeCheck	比较时间
	 * @return	true timeCheck在time之前，false timeCheck在time之后
	 */
	public static String termChange(int term){
		switch(term){
			case 1 : return "<";
			case 2 : return ">";
			case 3 : return "<=";
			case 4 : return ">=";
			case 5 : return "<>";
			case 6 : return "=";
			default : return "=";
		}
	}
	/**
	 * 取当月的首个日期
	 * 
	 */
	public static String getFirstDateOfMonth(){
		Calendar currentCal=Calendar.getInstance();
		currentCal.set(Calendar.DATE, 1);//把日期设置为当月第一天
		int currentday = currentCal.get(Calendar.DAY_OF_MONTH);
		  int currentyear =currentCal.get(Calendar.YEAR);
		  int currentmonth = currentCal.get(Calendar.MONTH)+1;
		  String currentDate = getYearAndMonthAndDay(currentyear, currentmonth, currentday);
		return currentDate;
		
	}
	
	/**
	 * 取当月的末日期
	 * 
	 */
	public static String getEndDateOfMonth(){
		Calendar currentCal=Calendar.getInstance();
		currentCal.set(Calendar.DATE, 1);//把日期设置为当月第一天
		currentCal.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
		int MaxDate=currentCal.get(Calendar.DATE);
		  int currentyear =currentCal.get(Calendar.YEAR);
		  int currentmonth = currentCal.get(Calendar.MONTH)+1;
		  String currentDate = getYearAndMonthAndDay(currentyear, currentmonth, MaxDate);
		return currentDate;
		
	}
	
	private static String getYearAndMonthAndDay(int year,int month,int day){
		String m = String.valueOf(month<10?"0"+month:month);
		String d = String.valueOf(day<10?"0"+day:day);
		String tmp = year+"-"+m+"-"+d;
		return tmp;
	}
	
	
	public static String getTodayZeroPoint() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		return dateFormat.format(new Date());
	}
	
	@SuppressWarnings("deprecation")
	public static String getNextDayZeroPoint() {
		Date date = new Date();
		date.setDate(date.getDate()+1);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		return dateFormat.format(date);
	}
	
	public static void main(String[] args){
//		List<String> list = getDaysByYearMonth("1980-02");
//		for (String string : list) {
//			System.out.println(string);
//		}
		
		String[] times = {"23","31"};
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String time = sdf.format(new Date());

		GregorianCalendar calendar = new GregorianCalendar();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);

		calendar = new GregorianCalendar(year, month, day, Integer.parseInt(times[0]), Integer.parseInt(times[1]));
		int hours = calendar.get(Calendar.HOUR_OF_DAY);
		int minutes = calendar.get(Calendar.MINUTE);
		// 标准时间
		GregorianCalendar standerCal = new GregorianCalendar(year, month, day, hours, minutes);

		String standerDate = sdf.format(standerCal.getTime());

		boolean beforebl = UtilWork.checkDayBySF(time, standerDate, sdf) || time.equals(standerDate);
		System.out.println(beforebl+" 正常 "+time.equals(standerDate));
		
	}
	
	/**
	 * 根据传进来的年月（yyyy-MM）获取该月有多少天
	 * @param ym
	 * @return
	 */
	public static int getMaxDayByYearMonth(String ym){
		int day=0;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			Calendar calendar = new GregorianCalendar();
			Date date = sdf.parse(ym);
			calendar.setTime(date);
			day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		} catch (ParseException e) {
			day = 0;
		}
		return day;
	}
	
	/**
	 * 获取指定年月从开始至结束的日期
	 * @param ym
	 * @return
	 */
	public static List<String> getDaysByYearMonth(String ym){
		List<String> list = new ArrayList<String>();
		int maxDate = getMaxDayByYearMonth(ym);
		for (int i = 1;i<=maxDate; i++) {
			String d = String.valueOf(i<10?"0"+i:i);
			list.add(ym+"-"+d);
		}
		return list;
	}
	
	/**
	 * 获取当前日期至月底之间的日期
	 * @param iscurrentdate 是否包含当天
	 * @return
	 */
	public static List<String> getDayToMonthEnd(boolean iscurrentdate){
		List<String> list = new ArrayList<String>();
		Calendar calendar = new GregorianCalendar();
		int cday = calendar.get(Calendar.DAY_OF_MONTH);
		int currentyear =calendar.get(Calendar.YEAR);
		int currentmonth = calendar.get(Calendar.MONTH)+1;
		if (iscurrentdate) {
			list.add(getYearAndMonthAndDay(currentyear, currentmonth, cday));
		}
		int maxDate = getMaxDayByYearMonth(getCustomerDay("yyyy-MM"));
		for (int i = cday+1; i <=maxDate; i++) {
			list.add(getYearAndMonthAndDay(currentyear, currentmonth, i));
		}
		Collections.sort(list);
		return list;
	}
	
	/**
	 * 计算时间差
	 * @param a
	 * @param b
	 * @return
	 */
	public static String getBetweenDayNumber(String a,String b){
		double number = 0; 
		double DAY = 24L * 60L * 60L * 1000L;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			Date ad = df.parse(a);
			Date bd = df.parse(b);
			number = (bd.getTime()-ad.getTime())/DAY;
		} catch (ParseException e) {
			logger.error("时间格式化出错："+e.getMessage());
		}
		
		DecimalFormat nf = new DecimalFormat("#.##");
		
		return nf.format(number);
	}
}