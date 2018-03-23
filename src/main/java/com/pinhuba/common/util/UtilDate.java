package com.pinhuba.common.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class UtilDate {

	/**
	 * @param args
	 */
	
	private int year;
	private int month;
	private int day;
	private int index;
	int[] daysInMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	

	public UtilDate(int year, int month, int day, int index) {
		super();
		this.year = year;
		this.month = month;
		this.day = day;
		this.index = index;
	}




	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}




	/**
	 * @param index the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}




	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}




	/**
	 * @param year the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}




	/**
	 * @return the month
	 */
	public int getMonth() {
		return month;
	}




	/**
	 * @param month the month to set
	 */
	public void setMonth(int month) {
		this.month = month;
	}




	/**
	 * @return the day
	 */
	public int getDay() {
		return day;
	}




	/**
	 * @param day the day to set
	 */
	public void setDay(int day) {
		this.day = day;
	}

    
	public String[] getWeek(){ 
		//取当前日期所在星期中的星期日的日期
        GregorianCalendar calendar=new GregorianCalendar(this.year,this.month,this.day+this.index);
		
		

        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        switch (w)
        {
         case (0):
        	calendar.add(Calendar.DATE, 0 );
          break;
         case (1):
        	 calendar.add( Calendar.DATE, -1 );
          break;
         case (2):
        	 calendar.add( Calendar.DATE, -2 );
          break;
         case (3):
        	 calendar.add( Calendar.DATE, -3 );
          break;
         case (4):
        	 calendar.add( Calendar.DATE, -4 );
          break;
         case (5):
        	 calendar.add( Calendar.DATE, -5 );
          break;
         case (6):
        	 calendar.add( Calendar.DATE, -6 );
          break;
        }
		int newyear =calendar.get(Calendar.YEAR);
		int newmonth = calendar.get(Calendar.MONTH);
		int newday = calendar.get(Calendar.DATE);
		
		//返回一个星期的日期
		String[] weekdate = new String[7];
		for (int i = 0; i < 7; i++) {
			weekdate[i] = getEachDayOfWeek(newyear,newmonth,newday,i);
		}
		
		return weekdate;
		
	}
	
	private String getEachDayOfWeek(int year,int month,int day,int count){
		
		GregorianCalendar calendar=new GregorianCalendar(year,month,day+count);
		int newyear =calendar.get(Calendar.YEAR);
		int newmonth = calendar.get(Calendar.MONTH);
		int newday = calendar.get(Calendar.DATE);
        
		String newdate = getYearAndMonthAndDay(newyear,newmonth+1,newday);
		
		return newdate;
		
	}
	
	private String getYearAndMonthAndDay(int year,int month,int day){
		String m = String.valueOf(month<10?"0"+month:month);
		String d = String.valueOf(day<10?"0"+day:day);
		String tmp = year+"-"+m+"-"+d;
		return tmp;
	}
	
	
	public String[] getEachMonthOfYear(){
		int year = this.year+this.index;
		String[] monthOfYear = new String[12];
		for (int i = 0; i < 12; i++) {
			int day = getDays(i,year);
			monthOfYear[i] =getYearAndMonthAndDay(year,i+1,1)+","+getYearAndMonthAndDay(year,i+1,day);
		}
		
		return monthOfYear;
	}
	
	public int getDays(int month, int year)
	  {
	    if (1 == month) {
	      return ((((0 == year % 4) && (0 != year % 100)) || (0 == year % 400)) ? 29 : 28);
	    }

	    return this.daysInMonth[month];
	  }

	public static void main(String[] args) {
		UtilDate ud = new UtilDate(2010,0,9,-2);
		String[] temp = ud.getEachMonthOfYear();
		
//		for (int i = 0; i < temp.length; i++) {
//			System.out.println(temp[i]);
//		}
		
//		String temp = "01";
//		int i = Integer.valueOf(temp);
//		System.out.println(i);

	}

}
