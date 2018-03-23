package com.pinhuba.common.util;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.pinhuba.core.pojo.OaCalender;

public class WorkCalenderMonth {

	private Map<Integer, List<OaCalender>> map = new HashMap<Integer, List<OaCalender>>();// 存放查询出来的日程

	private HttpServletRequest reqeust;

	/**
	 * @return the map
	 */

	String[] months = { "一　月", "二　月", "三　月", "四　月", "五　月", "六　月", "七　月", "八　月", "九　月", "十　月", "十一月", "十二月" };
	int[] daysInMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	int displayMonth;
	int displayYear;
	int todayYear;
	int todayMonth;
	int todayDay;

	public WorkCalenderMonth() {
		Calendar cal = Calendar.getInstance();
		this.displayYear = cal.get(1);
		this.displayMonth = cal.get(2);
		this.todayYear = this.displayYear;
		this.todayMonth = this.displayMonth;
		this.todayDay = cal.get(5);
	}

	public int getDays(int month, int year) {
		if (1 == month) {
			return ((((0 == year % 4) && (0 != year % 100)) || (0 == year % 400)) ? 29 : 28);
		}

		return this.daysInMonth[month];
	}

	public String newCalendar(int displayYear, int displayMonth,HttpServletRequest request) {
		this.displayYear = displayYear;
		this.displayMonth = displayMonth;
		Calendar newCal = Calendar.getInstance();
		newCal.set(displayYear, displayMonth, 1);
		int day = -1;
		int startDayOfWeek = newCal.get(7);
		if ((this.todayYear == newCal.get(1)) && (this.todayMonth == newCal.get(2))) {
			day = this.todayDay;
		}
		int intDaysInMonth = getDays(newCal.get(2), newCal.get(1));
		String daysGrid = makeDaysGrid(startDayOfWeek, day, intDaysInMonth, newCal);
		return daysGrid;
	}

	public String makeDaysGrid(int startDay, int day, int intDaysInMonth, Calendar newCal) {
		Calendar currentCal = Calendar.getInstance();
		int currentday = currentCal.get(Calendar.DAY_OF_MONTH);
		int currentyear = currentCal.get(Calendar.YEAR);
		int currentmonth = currentCal.get(Calendar.MONTH) + 1;
		String currentDate = this.getYearAndMonthAndDay(currentyear, currentmonth, currentday);

		int forwardmonth = this.displayMonth + 1;
		StringBuffer daysgrid = new StringBuffer();
		daysgrid.append("<table class='tablerowStyleColor' cellpadding='1' cellspacing='0'  border='1' style='width:100%;height:100%'/>");

		daysgrid.append("<tr style=\"font:12px '宋体';text-align:center;BACKGROUND-IMAGE: url('"+reqeust.getContextPath()+"/images/grid_images/wbg.gif');\" height=\"26px\">");
		daysgrid.append("<td>日</td><td>一</td><td>二</td><td>三</td><td>四</td><td>五</td><td>六</td>");
		daysgrid.append("</tr>");

		int dayOfMonthOfFirstSunday = 7 - startDay + 2;
		int count = 0;
		int dayOfMonth = 0;
		for (int intWeek = 0; intWeek < 6; ++intWeek) {
			daysgrid.append("<tr height='80'>");
			for (int intDay = 0; intDay < 7; ++intDay) {
				dayOfMonth = intWeek * 7 + intDay + dayOfMonthOfFirstSunday - 7;
				if (dayOfMonth <= 0) {
					daysgrid.append("<td>&nbsp;</td>");
				} else if (dayOfMonth <= intDaysInMonth) {
					++count;
					String color = "black";
					String bgcolor = "#ffffff";
					String mousecolor = "blue";
					String titlecolor = "#EDF5FA";
					String tmp = getYearAndMonthAndDay(this.displayYear, forwardmonth, dayOfMonth);
					String titletmp = "";

					if (tmp.equalsIgnoreCase(currentDate)) {
						mousecolor = "red";
						titlecolor = "#C2C9D1";
					} else {
						mousecolor = "blue";
						titlecolor = "#EDF5FA";
					}

					String dayString = String.valueOf(dayOfMonth);
					daysgrid.append("<td width='13%' title='" + titletmp + "' align='right' valign='top' id='" + tmp + "' value='"
							+ "' ondblclick='addCalender(this.id);' readonly style=\"font:12px '宋体';background-color:" + bgcolor + ";color:" + color
							+ ";\" onmouseover=\"changDateColorOver(this,'#d3dfee','" + mousecolor + "');\" onmouseout =\"changDateColorOut(this);\">");
					daysgrid.append("<div style=\"font:13px '宋体';background-color:" + titlecolor + ";padding:3px;border-bottom:1px solid #dddddd\" title='" + tmp + "'>" + dayString
							+ "</div><input type='hidden' id='ch" + dayOfMonth + "' value='" + dayOfMonth + "'/>");
					daysgrid.append("<div style='padding:5px;border:0px solid #214079;text-align:left;line-height:15px;word-break:break-all'>");
					// 内容显示
					if (getMap() != null && getMap().size() > 0) {
						if (map.containsKey(dayOfMonth)) {
							List<OaCalender> tmplist = map.get(dayOfMonth);
							for (OaCalender cal : tmplist) {
								int level = cal.getOaCalenderLevel();
								String img = "";
								if (level == EnumUtil.OA_CALENDER_LEVEL.one.value) {
									img = "<img title ='重要/紧急' src='" + getReqeust().getContextPath() + "/images/grid_images/zyjj.png' border='0'/>";
								} else if (level == EnumUtil.OA_CALENDER_LEVEL.two.value) {
									img = "<img title ='重要/不紧急' src='" + getReqeust().getContextPath() + "/images/grid_images/zybjj.png' border='0'/>";
								} else if (level == EnumUtil.OA_CALENDER_LEVEL.three.value) {
									img = "<img title ='不重要/紧急' src='" + getReqeust().getContextPath() + "/images/grid_images/bzyjj.png' border='0'/>";
								} else {
									img = "<img title ='不重要/不紧急' src='" + getReqeust().getContextPath() + "/images/grid_images/bzybjj.png' border='0'/>";
								}

								if (cal.getOaCalenderStatus() == EnumUtil.OA_CALENDER_STATUS.one.value) {
									daysgrid.append(img + "&nbsp;<a class='cal' href='javascript:void(0)' onmouseover=\"changeStatus('" + cal.getOaCalenderStatus() + "');\" id='" + cal.getPrimaryKey() + "'><font color='#00BD00'>"
											+ cal.getOaCalenderStart().substring(5, 16) + "--" + cal.getOaCalenderEnd().substring(5, 16) + "</font><br/><font color='#476074'>"
											+ cal.getLibrary().getLibraryInfoName() + ":</font><br/><font color='#0E75B7'>" + cal.getOaCalenderContent()
											+ "</font><font color='#00BD00'> (已完成)</font></a><br/><br/>");
								} else {
									daysgrid.append(img + "&nbsp;<a class='cal' href='javascript:void(0)' onmouseover=\"changeStatus('" + cal.getOaCalenderStatus() + "');\" id='" + cal.getPrimaryKey() + "'><font color='#EC6907'>"
											+ cal.getOaCalenderStart().substring(5, 16) + "--" + cal.getOaCalenderEnd().substring(5, 16) + "</font><br/><font color='#476074'>"
											+ cal.getLibrary().getLibraryInfoName() + ":</font><br/><font color='#0E75B7'>" + cal.getOaCalenderContent()
											+ "</font><font color='#EC6907'> (未完成)</font></a><br/><br/>");
								}
							}
						}
					}

					daysgrid.append("</div>");
					daysgrid.append("</td>");
				}
			}
			int dayspan = dayOfMonth - count;
			if (dayOfMonth < intDaysInMonth) {
				daysgrid.append("</tr>");
			} else if ((dayspan < 7) && (dayspan > 0)) {
				for (int k = 0; k < dayspan; ++k) {
					daysgrid.append("<td>&nbsp;</td>");
				}
				daysgrid.append("</tr>");
			}
		}
		daysgrid.append("</table>");
		return daysgrid.toString();
	}

	private String getYearAndMonthAndDay(int year, int month, int day) {
		String m = String.valueOf(month < 10 ? "0" + month : month);
		String d = String.valueOf(day < 10 ? "0" + day : day);
		String tmp = year + "-" + m + "-" + d;
		return tmp;
	}

	public WorkCalenderMonth(Map<Integer, List<OaCalender>> map) {
		super();
		this.map = map;
	}

	/**
	 * @return the map
	 */
	public Map<Integer, List<OaCalender>> getMap() {
		return map;
	}

	/**
	 * @param map
	 *            the map to set
	 */
	public void setMap(Map<Integer, List<OaCalender>> map) {
		this.map = map;
	}

	public void setReqeust(HttpServletRequest reqeust) {
		this.reqeust = reqeust;
	}

	public HttpServletRequest getReqeust() {
		return reqeust;
	}

}
