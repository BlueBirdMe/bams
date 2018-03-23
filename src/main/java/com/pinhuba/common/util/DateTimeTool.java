package com.pinhuba.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 本类是日期时间工具类，主要是针对项目当中的一些常用日期时间处理方法进行封装，方便于开发中使用.
 * 
 * <p>
 * <b>Company:</b> ShangHai KOAL SoftWare CO.,LTD
 * </p>
 * <p>
 * <b>Author:</b> 邓文敏
 * </P>
 * <p>
 * <b>Date:</b> 2006/06/19
 * </p>
 */
public class DateTimeTool {

	/**
	 * 检查给定格式的字符串日期，是否符合指定格式，是否为空，并对日期的合法的都进行检验
	 * 
	 * @param dateString
	 *            要进行检查的日期字符串
	 * 
	 * @return boolean: 符合指定格式返回 true，否则为 false
	 * @throws ParseException
	 */
	public static boolean checkDateString(String dateString) {

		// 参数检验
		if (dateString == null) {
			return false;
		}

		String[] strs = dateString.split("-");
		if (strs == null || strs.length != 3) {
			return false;
		}
		dateString = "";
		dateString += strs[0];
		dateString += "-";
		if (strs[1].length() < 2) {
			dateString += "0" + strs[1];
		} else {
			dateString += strs[1];
		}
		dateString += "-";
		if (strs[2].length() < 2) {
			dateString += "0" + strs[2];
		} else {
			dateString += strs[2];
		}

		SimpleDateFormat simpleDF = new SimpleDateFormat("yyyy-MM-dd");

		try {
			Date date = simpleDF.parse(dateString);
			String newDateString = simpleDF.format(date);
			if (newDateString.equals(dateString))
				return true;
			else
				return false;
		} catch (ParseException e) {
			throw new RuntimeException("日期[" + dateString + "]格式无效.");
		}
	}

	/**
	 * 判断当前日期是否过期,日期格式必须 yyyy-MM-dd(注意)
	 * 
	 * @param nowDate
	 *            当前日期
	 * @param expireDate
	 *            过期日期
	 * @return true:已过期 false:未过期 参数非法:返回true
	 */
	public static boolean isDateExpire(Date nowDate, Date expireDate) {
		if (expireDate == null || nowDate == null) {
			return false;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(expireDate);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(nowDate);
		return cal.after(cal2);
	}

	private static boolean isDateExpire(String nowDate, String expireDate) throws ParseException {
		if (!checkDateString(nowDate))
			return true;
		if (!checkDateString(expireDate))
			return true;

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date myExpireDate = null;
		Date myNowDate = null;
		try {
			myExpireDate = dateFormat.parse(expireDate);
			myNowDate = dateFormat.parse(nowDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		return isDateExpire(myExpireDate, myNowDate);
	}

	/**
	 * 检查给定格式的字符串时间，是否符合指定格式，是否为空，并对时间的合法的都进行检验
	 * 
	 * @param timeString
	 *            要进行检查的日期字符串
	 * 
	 * @return boolean: 符合指定格式返回 true，否则为 false
	 */
	public static boolean checkTimeString(String timeString) {

		// 参数检验
		if (timeString == null) {
			return false;
		}

		SimpleDateFormat simpleDF = new SimpleDateFormat("HH:mm:SS");

		try {
			Date date = simpleDF.parse(timeString);
			String newDateString = simpleDF.format(date);
			if (newDateString.equals(timeString))
				return true;
			else
				return false;
		} catch (ParseException e) {
			return false;
		}
	}

	/**
	 * 将Date类型的时间转换为 java.sql.Date 类型的时间
	 * 
	 * @param date
	 *            Date 类型的时间
	 * 
	 * @return java.sql.Date 成功返回转换好的Date类型对象 null：出现异常
	 */
	public static java.sql.Date getSqlDateFromDate(Date date) {

		// 参数检验
		if (date == null) {
			return null;
		}
		java.sql.Date dateSql = new java.sql.Date(date.getTime());

		return dateSql;
	}

	/**
	 * 将java.sql.Date 类型的时间转换为 Date类型的时间
	 * 
	 * @param sqlDate
	 *            java.sql.Date类型的时间
	 * 
	 * @return Date 成功返回转换好的java.sql.Date类型对象 null：出现异常
	 */
	public static Date getDateFromSqlDate(java.sql.Date sqlDate) {

		// 参数检验
		if (sqlDate == null) {
			return null;
		}
		Date date = new java.sql.Date(sqlDate.getTime());

		return date;
	}

	/**
	 * 将指定格式为“yyyy-MM-dd”的日期字符串，转化为Date型对象
	 * 
	 * @param dateString
	 *            要转化的日期字符串，格式为“yyyy-MM-dd”
	 * 
	 * @return Date: 返回转化成功的Date型对象, null: 出现异常
	 * @throws ParseException
	 */
	public static Date getDateFromString(String dateString) {

		// 参数检验
		try {
			if (!checkDateString(dateString)) {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return getDateFromString(dateString, "yyyy-MM-dd");
	}

	/**
	 * 按指定的日期格式字符串格式化日期字符串为Date型对象,不对格式的 正确性进行检验，所以，格式错误的话，返回错误的日期对象。
	 * 
	 * 日期格式字符串的写法参照SimpleDateFormat类中的描述。
	 * 
	 * @param dateString
	 *            要转化的日期字符串
	 * @param pattern
	 *            指定的日期格式字符串
	 * 
	 * @return Date: 返回转化成功的Date型对象, null: 出现异常
	 */
	public static Date getDateFromString(String dateString, String pattern) {
		if (dateString == null || pattern == null) {
			return null;
		}

		SimpleDateFormat simpleDF = new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = simpleDF.parse(dateString);
		} catch (ParseException e) {
			throw new RuntimeException("日期[" + dateString + "]格式无效.");
		}

		return date;
	}

	/**
	 * 将指定格式为“HH:mm:SS”的时间字符串，转化为Date型对象
	 * 
	 * @param timeString
	 *            要转化的日期字符串，格式为"HH:mm:SS"
	 * 
	 * @return Date: 返回转化成功的Date型对象, null: 出现异常
	 */
	public static Date getDateFromTimeString(String timeString) {
		// 参数检验
		if (!checkTimeString(timeString)) {
			return null;
		}

		return getDateFromString(timeString, "HH:mm:SS");
	}

	/**
	 * 将指定的Date对象增加给定的<b>天数</b>后得到新的Date对象
	 * 
	 * @param date
	 *            要被增加的Date对象
	 * @param days
	 *            要增加的<b>天数</b>(可以为负数，当为负数时表示减少)
	 * 
	 * @return Date: 返回增加<b>天数</b>后的Date型对象, 出现异常返回 null
	 */
	public static Date addDay(Date date, int days) {
		if (date == null) {
			return null;
		}

		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.DAY_OF_YEAR, days);
		Date resultDate = ca.getTime();

		return resultDate;
	}

	/**
	 * 返回相隔指定工作日的日期
	 * 
	 * @param dateBegin
	 *            指定的日期
	 * @param days
	 *            正数:向后的工作日;负数:向前的工作日
	 * @return Date
	 */
	public static Date addWorkDay(Date dateBegin, int days) {
		if (dateBegin == null) {
			return null;
		}
		Calendar calBegin = Calendar.getInstance();

		// 设置日期
		calBegin.setTime(dateBegin);
		if (days >= 0) {
			for (; days > 0;) {
				calBegin.add(Calendar.DAY_OF_MONTH, 1);
				if ((calBegin.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) && (calBegin.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)) {
					days--;
				}
			}
		} else {
			for (; 0 > days;) {
				calBegin.add(Calendar.DAY_OF_MONTH, -1);
				if ((calBegin.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) && (calBegin.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)) {
					days++;
				}
			}
		}

		return calBegin.getTime();
	}

	/**
	 * 将指定的Date对象增加给定的<b>月数</b>后得到新的Date对象
	 * 
	 * @param date
	 *            要被增加的Date对象
	 * @param months
	 *            要增加的<b>月数</b>(可以为负数，当为负数时表示减少)
	 * 
	 * @return Date: 返回增加<b>月数</b>后的Date型对象, 出现异常返回 null
	 */
	public static Date addMonth(Date date, int months) {

		if (date == null) {
			return null;
		}

		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.MONTH, months);
		Date resultDate = ca.getTime();

		return resultDate;
	}

	/**
	 * 将指定的Date对象增加给定的<b>年数</b>后得到新的Date对象
	 * 
	 * @param date
	 *            要被增加的Date对象
	 * @param years
	 *            要增加的<b>年数</b>(可以为负数，当为负数时表示减少)
	 * 
	 * @return Date: 返回增加<b>年数</b>后的Date型对象, 出现异常返回 null
	 */
	public static Date addYear(Date date, int years) {

		if (date == null) {
			return null;
		}

		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.YEAR, years);
		Date resultDate = ca.getTime();

		return resultDate;
	}

	/**
	 * 将指定的Date对象增加给定的<b>时钟数</b>后得到新的Date对象
	 * 
	 * @param date
	 *            要被增加的Date对象
	 * @param hours
	 *            要增加的<b>时钟数</b>(可以为负数，当为负数时表示减少)
	 * 
	 * @return Date: 返回增加<b>时钟数</b>后的Date型对象, 出现异常返回 null
	 */
	public static Date addHour(Date date, int hours) {

		if (date == null) {
			return null;
		}

		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.HOUR, hours);
		Date resultDate = ca.getTime();

		return resultDate;
	}

	/**
	 * 将指定的Date对象增加给定的<b>分钟数</b>后得到新的Date对象
	 * 
	 * @param date
	 *            要被增加的Date对象
	 * @param minutes
	 *            要增加的<b>分钟数</b>(可以为负数，当为负数时表示减少)
	 * 
	 * @return Date: 返回增加<b>分钟数</b>后的Date型对象, 出现异常返回 null
	 */
	public static Date addMinute(Date date, int minutes) {
		if (date == null) {
			return null;
		}

		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.MINUTE, minutes);
		Date resultDate = ca.getTime();

		return resultDate;
	}

	/**
	 * 将指定的String增加给定的<b>分钟数</b>后得到新的String。time的格式为 小时:分钟，增加分钟
	 * 
	 * @param date
	 *            要被增加的String
	 * @param minutes
	 *            要增加的<b>分钟数</b>(可以为负数，当为负数时表示减少)
	 * 
	 * @return Date: 返回增加<b>分钟数</b>后的String, 出现异常返回 null
	 */
	public static String addMinute(String time, int minutes) {
		if (time == null || time.trim().length() == 0) {
			return null;
		}

		String result = null;
		try {
			Date date = new SimpleDateFormat("HH:mm").parse(time);

			Calendar ca = Calendar.getInstance();
			ca.setTime(date);
			ca.add(Calendar.MINUTE, minutes);
			Date resultDate = ca.getTime();

			result = new SimpleDateFormat("HH:mm").format(resultDate);
		} catch (Exception e) {
			result = null;
		}

		return result;
	}

	/**
	 * 将指定的Date对象增加给定的<b>秒钟数</b>后得到新的Date对象
	 * 
	 * @param date
	 *            要被增加的Date对象
	 * @param seconds
	 *            要增加的<b>秒钟数</b>(可以为负数，当为负数时表示减少)
	 * 
	 * @return Date: 返回增加<b>秒钟数</b>后的Date型对象, 出现异常返回 null
	 */
	public static Date addSecond(Date date, int seconds) {
		if (date == null) {
			return null;
		}

		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.SECOND, seconds);
		Date resultDate = ca.getTime();

		return resultDate;
	}

	/**
	 * 检验给出的Date对象是否为闰年
	 * 
	 * @param date
	 *            用于检验的Date对象
	 * 
	 * @return true Date对象为闰年，否则为 false
	 */
	public static boolean isLeapYear(Date date) {
		if (date == null) {
			return false;
		}
		boolean result = false;

		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.get(Calendar.YEAR);
		int year = ca.get(Calendar.YEAR);

		if (((year % 4) == 0 && (year % 100 != 0)) || ((year % 400) == 0)) {
			result = true;
		}

		return result;
	}

	// 获取本月第一天
	public static String getMonthFirstDate() {
		Calendar cal = Calendar.getInstance();

		SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
		
		// 当前月的第一天
		cal.set(GregorianCalendar.DAY_OF_MONTH, 1);
		Date beginTime = cal.getTime();

		return datef.format(beginTime);
	}
	
	// 获取本月第一天和最后一天
	public String getMonthFirstAndEndDate() {
		Calendar cal = Calendar.getInstance();

		SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
		// 当前月的最后一天
		cal.set(Calendar.DATE, 1);
		cal.roll(Calendar.DATE, -1);
		Date endTime = cal.getTime();
		String endTime1 = datef.format(endTime) + " 23:59:59";
		// 当前月的第一天
		cal.set(GregorianCalendar.DAY_OF_MONTH, 1);
		Date beginTime = cal.getTime();
		String beginTime1 = datef.format(beginTime) + " 00:00:00";

		return beginTime1 + "," + endTime1;
	}

	public long getNumBetweenTwoDate(String startDate, String endDate) {
		try {
			Date date1 = new SimpleDateFormat("yyyy-mm-dd").parse(startDate);
			Date date2 = new SimpleDateFormat("yyyy-mm-dd").parse(endDate);

			// 日期相减得到相差的日期
			long day = (date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000) > 0 ? (date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000) : (date2.getTime() - date1.getTime())
					/ (24 * 60 * 60 * 1000);

			return day + 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 将yyyy-MM-dd日期形式转换为 yyyy年MM月dd日 星期X
	 * @param date 日期
	 * @param htmlFlag 是否已html形式显示
	 * @return
	 * @throws ParseException 
	 */
	public static String parseDateShow(String date,boolean htmlFlag) throws ParseException{
		String showDate = "";
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Date sd = sf.parse(date);

		Calendar c = Calendar.getInstance();
		c.setTime(sd);
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		
		if(htmlFlag){
			switch (dayOfWeek) {
			case 1:
				showDate = "&nbsp;&nbsp;<font color='orange'>星期日</font>";
				break;
			case 2:
				showDate = "&nbsp;&nbsp;星期一";
				break;
			case 3:
				showDate = "&nbsp;&nbsp;星期二";
				break;
			case 4:
				showDate = "&nbsp;&nbsp;星期三";
				break;
			case 5:
				showDate = "&nbsp;&nbsp;星期四";
				break;
			case 6:
				showDate = "&nbsp;&nbsp;星期五";
				break;
			case 7:
				showDate = "&nbsp;&nbsp;<font color='orange'>星期六</font>";
				break;
			}
		}else{
			switch (dayOfWeek) {
			case 1:
				showDate = "  星期日";
				break;
			case 2:
				showDate = "  星期一";
				break;
			case 3:
				showDate = "  星期二";
				break;
			case 4:
				showDate = "  星期三";
				break;
			case 5:
				showDate = "  星期四";
				break;
			case 6:
				showDate = "  星期五";
				break;
			case 7:
				showDate = "  星期六";
				break;
			}
		}
		
		sf = new SimpleDateFormat("yyyy年MM月dd日"); 
		
		return sf.format(sd) + showDate;
	}
	
	public static void main(String[] args){
		try {
//			String sdate="2010-08-01";
//			String edate="2010-08-25";
//			
//			System.out.println(DateTimeTool.parseDateShow(sdate, true));
//			System.out.println(DateTimeTool.parseDateShow(edate, true));
//			System.out.println(DateTimeTool.parseDateShow(sdate, false));
//			System.out.println(DateTimeTool.parseDateShow(edate, false));
			
//			String h1 = "15:20";
//			String h2 = "19:50";
			
//			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
//			Date newDate = DateTimeTool.addMinute(sdf.parse(h1), 10);
//			System.out.println(sdf.format(newDate));
			
//			Date newDate2 = DateTimeTool.addMinute(sdf.parse(h1), 0-30);
//			System.out.println(sdf.format(newDate2));
			
//			Date newDate3 = DateTimeTool.addMinute(sdf.parse(h2), 20);
//			System.out.println(sdf.format(newDate3));
			
//			Date newDate4 = DateTimeTool.addMinute(sdf.parse(h2), 0-30);
//			System.out.println(sdf.format(newDate4));
			
//			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
//			SimpleDateFormat csf = new SimpleDateFormat("MM-dd");
//			long oneday = 24*60*60*1000;
//			Date sd = sf.parse(sdate);
//			Date ed = sf.parse(edate);
//			//计算两个日期相差天数
//			long l = (ed.getTime() - sd.getTime())/oneday>0?(ed.getTime() - sd.getTime())/oneday+1:1;
//			String[] cells = new String[(int)l];
//			
//			for (int i = 0; i <cells.length; i++) {
//				cells[i] = csf.format(DateTimeTool.addDay(sd, i));
//			}
//			
//			for (String string : cells) {
//				System.out.println(string);
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//获取一个月的第一天和最后一天
	public static String getFirstDataAndLastDate(String year,String month) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
		// 当前月的第一天
		cal.set(Integer.parseInt(year), Integer.parseInt(month)-1, 1);
		String beginTime = datef.format(cal.getTime());
		
		// 当前月的最后一天
		cal.roll(Calendar.DATE, -1);
		String endTime = datef.format(cal.getTime());
		return beginTime+","+endTime;
	}
	
	//将Date类型日期转换为字符串类型
	public static String getStringFromDate(Date date, String pattern) {
		if (date == null || pattern == null) {
			return null;
		}

		SimpleDateFormat simpleDF = new SimpleDateFormat(pattern);
		return simpleDF.format(date);
	}
	
	public static String periodToString(Long millisecond){
		String str = "";
		long day = millisecond /  86400000;
		long hour = (millisecond % 86400000) / 3600000 ;
		long minute = (millisecond %  86400000 % 3600000) / 60000;
		if(day > 0){
			str = String.valueOf(day) + "天";
		}
		if(hour > 0){
			str += String.valueOf(hour) + "小时";
		}
		if(minute > 0){
			str += String.valueOf(minute) + "分钟";
		}
		return str;
	}
}
