package com.pinhuba.common.util;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class PlanBook {
	
	private Map<String, Integer> map = new HashMap<String, Integer>();//存放查询出来的日程
	/**
	 * @return the map
	 */
	public Map<String, Integer> getMap() {
		return map;
	}

	/**
	 * @param map the map to set
	 */
	public void setMap(Map<String, Integer> map) {
		this.map = map;
	}

	
	String[] months = { "一　月", "二　月", "三　月", "四　月", "五　月", "六　月", "七　月", "八　月", "九　月", "十　月", "十一月", "十二月" };
	  int[] daysInMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	  int displayMonth;
	  int displayYear;
	  int todayYear;
	  int todayMonth;
	  int todayDay;

	  public PlanBook()
	  {
	    Calendar cal = Calendar.getInstance();
	    this.displayYear = cal.get(1);
	    this.displayMonth = cal.get(2);

	    this.todayYear = this.displayYear;
	    this.todayMonth = this.displayMonth;
	    this.todayDay = cal.get(5);
	  }

	  public int getDays(int month, int year)
	  {
	    if (1 == month) {
	      return ((((0 == year % 4) && (0 != year % 100)) || (0 == year % 400)) ? 29 : 28);
	    }

	    return this.daysInMonth[month];
	  }

	  public String newCalendar(int displayYear, int displayMonth) {
	    this.displayYear = displayYear;
	    this.displayMonth = displayMonth;
	    Calendar newCal = Calendar.getInstance();
	    newCal.set(displayYear, displayMonth, 1);
	    int day = -1;
	    int startDayOfWeek = newCal.get(7);
	    if ((this.todayYear == newCal.get(1)) && (this.todayMonth == newCal.get(2)))
	    {
	      day = this.todayDay;
	    }
	    int intDaysInMonth = getDays(newCal.get(2), newCal.get(1));
	    String daysGrid = makeDaysGrid(startDayOfWeek, day, intDaysInMonth, newCal);
	    return daysGrid;
	  }

	  public String makeDaysGrid(int startDay, int day, int intDaysInMonth, Calendar newCal)
	  {
		  Calendar currentCal = Calendar.getInstance();
		  int currentday = currentCal.get(Calendar.DAY_OF_MONTH);
		  int currentyear =currentCal.get(Calendar.YEAR);
		  int currentmonth = currentCal.get(Calendar.MONTH)+1;
		  String currentDate = this.getYearAndMonthAndDay(currentyear, currentmonth, currentday);
		  
	    int forwardmonth = this.displayMonth + 1;
	    String daysGrid = "<table align=center cellpadding='2' cellspacing='0' borderColorDark='#ffffff' bgColor='white' borderColorLight='#A6D0E8' border='1' style='margin: 1px;width:99%'>";
	    daysGrid = daysGrid +"<tr><td colspan='7'><table cellpadding='0' cellspacing='2'>" +
	    				"<tr><td align='left' style='padding-left:2px;'><a title='上一月'  href='javascript:void(0)' onclick='init(-1)' style=\"cursor: pointer;font:12px '宋体'; \">&nbsp;<<&nbsp;</a></td><td align='center' width='100%' style=\"font:12px '宋体';cursor: default; \" nowrap='nowrap'>"+this.displayYear+" 年 "+forwardmonth+" 月 </td>" +
	    				"<td align='right' style='padding-right:2px;'><a title ='下一月' href='javascript:void(0)' onclick='init(1)' style=\"cursor: pointer;font:12px '宋体'; \">&nbsp;>>&nbsp;</a></td></tr></table></td></tr>";
	    daysGrid = daysGrid + "<tr height='20' style=\"font:12px '宋体'; background-color:#A6D0E8\"><td align=center>日</td><td align=center>一</td><td align=center>二</td>";
	    daysGrid = daysGrid + "<td align=center>三</td><td align=center>四</td><td align=center>五</td><td align=center>六</td></tr>";
	    int dayOfMonthOfFirstSunday = 7 - startDay + 2;
	    int count = 0;
	    int dayOfMonth = 0;
	    for (int intWeek = 0; intWeek < 6; ++intWeek) {
	      daysGrid = daysGrid + "<tr height=24 >";
	      for (int intDay = 0; intDay < 7; ++intDay) {
	        dayOfMonth = intWeek * 7 + intDay + dayOfMonthOfFirstSunday - 7;
	        if (dayOfMonth <= 0) {
	          daysGrid = daysGrid + "<td>&nbsp;</td>";
	        }
	        else if (dayOfMonth <= intDaysInMonth) {
	          ++count;
	          String color = "black";
	          String bgcolor = "#ffffff";
	          String mousecolor = "blue";
	          
	          String tmp =getYearAndMonthAndDay(this.displayYear,forwardmonth,dayOfMonth);
	          String titletmp = "";
	          
	          if (tmp.equalsIgnoreCase(currentDate)) { //等于当前日期的颜色
	        	  bgcolor ="#336699";
	        	  color = "#ffffff";
	        	  mousecolor = "red";
	          }else{
	        	  bgcolor = "#ffffff";
	        	  color = "black";
	        	  mousecolor = "blue";
	          }
	          int c=0;
	          if(getMap()!=null&&getMap().size()>0){
	        	  if (map.containsKey(tmp)) {
	        		   c = getMap().get(tmp);
		        	  if(c>0){
		        		  titletmp = tmp+" 日志数量:"+c;
		        		  bgcolor ="#7CD3DA";
			        	  color = "#ededed";
			        	  mousecolor = "blue";
		        	  }
	        	  }
	          }
	          
	          daysGrid = daysGrid + "<td title='"+titletmp+"' align=center id="+tmp+" value="+c+" onclick='addDayToCurrentShift(this.id,this.value);' readonly ";
	          daysGrid = daysGrid + "style=\"font:12px '宋体' ;cursor:pointer;background-color:"+bgcolor+";color:" + color + "\" onmouseover=\"changDateColorOver(this,'#d3dfee','"+mousecolor+"');\" onmouseout =\"changDateColorOut(this);\">";
	          String dayString = dayOfMonth + "</a></b>";
	          daysGrid = daysGrid + dayString + "<input type='hidden' id='ch"+dayOfMonth+"' value='"+dayOfMonth+",0'/></td>";
	        }
	      }
	      int dayspan = dayOfMonth - count;
	      if (dayOfMonth < intDaysInMonth) {
	        daysGrid = daysGrid + "</tr>";
	      }else if ((dayspan < 7) && (dayspan > 0)) {
	        for (int k = 0; k < dayspan; ++k)
	          daysGrid = daysGrid + "<td>&nbsp;</td>";
	        daysGrid = daysGrid + "</tr>";
	      }
	    }

	    return daysGrid + "</table>";
	  }

	private String getYearAndMonthAndDay(int year,int month,int day){
		String m = String.valueOf(month<10?"0"+month:month);
		String d = String.valueOf(day<10?"0"+day:day);
		String tmp = year+"-"+m+"-"+d;
		return tmp;
	}

	public PlanBook(Map<String, Integer> map) {
		super();
		this.map = map;
	}
}
