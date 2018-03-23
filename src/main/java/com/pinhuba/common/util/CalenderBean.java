package com.pinhuba.common.util;


public class CalenderBean {
	private String time;
	private String value;
	private String newdate;
	private int year;
	private int month;
	private int day;
	
	
	
	
	
	
	
	
	public CalenderBean(String time, String value, String newdate, int year,
			int month, int day) {
		super();
		this.time = time;
		this.value = value;
		this.newdate = newdate;
		this.year = year;
		this.month = month;
		this.day = day;
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


	/**
	 * @return the newdate
	 */
	public String getNewdate() {
		return newdate;
	}
	/**
	 * @param newdate the newdate to set
	 */
	public void setNewdate(String newdate) {
		this.newdate = newdate;
	}
	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	

}
