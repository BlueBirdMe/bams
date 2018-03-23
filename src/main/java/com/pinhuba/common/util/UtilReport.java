package com.pinhuba.common.util;

import org.apache.log4j.Logger;

/**
 * 动态生成图表所需要的xml data
 * @author frin
 * 注：每个方法请把出入参写清楚，并对数组或列表类型进行举例
 */
public class UtilReport {
	Logger log = Logger.getLogger(UtilReport.class);
	
	private static final int PALETTE = 3;
	/*
	 * strXML += "<chart baseFont='宋体' baseFontSize='12' numberSuffix='人' palette='2' caption='客户分类统计柱状体' xAxisName='' yAxisName='客户数量' showValues='1' decimals='0' formatNumberScale='0' useRoundEdges='1'>";
	 * strXML += "<set label='未开发客户' value='15' color='fb0113' alpha='85' link='customer_add.jsp'/>";
	 * strXML += "<set label='开发中客户' value='10' color='ecfb01'  alpha='85' link='javaScript:alert(\"签约客户\");'/>";
	 * strXML += "<set label='成交客户' value='5' color='24fb01' alpha='85' link='javaScript:test(5);'/>";
	 * strXML += "</chart>";
	 */

	/**
	 * 动态生成 柱状图 + 曲线 图表
	 * @param caption	标题
	 * @param xAxisName	x轴标题，没有则不填
	 * @param yAxisName	y轴标题，没有则不填
	 * @param decimals	小数点位数
	 * @param showValues	是否在线或点上显示数值
	 * @param numberPrefix	鼠标移上显示前置内容
	 * @param numberSuffix	鼠标移上显示后置内容
	 * @param category	x轴类别	封装好的类别
	 * @param column	封装好的柱状图
	 * @param line	封装好的线状图
	 * @return
	 */
	public static String getColumnLineXml(String caption,String xAxisName,String yAxisName,int decimals,int showValues,String numberPrefix,String numberSuffix,String category,String column,String line) {
		StringBuffer strXML = new StringBuffer(); 
		strXML.append("<?xml version=\'1.0\' encoding=\'UTF-8\'?>");
	    strXML.append("<chart palette='"+UtilReport.PALETTE+"' caption='"+caption+"' xAxisName='"+xAxisName+"' yAxisName='"+yAxisName+"' decimals='"+decimals+"' numberPrefix='"+numberPrefix+"' numberSuffix='"+numberSuffix+"' baseFont='宋体' baseFontSize='12' shownames='1' showvalues='1' bgColor='99CCFF,FFFFFF' bgAlpha='40,100' bgRatio='0,100' showBorder='1'useRoundEdges='1' legendBorderAlpha='0'>");
		
		strXML.append(category);
		strXML.append(column);
		strXML.append(line);
		
		strXML.append("</chart>");
		
		return strXML.toString();
	}
	
	/**
	 * 动态生成 柱状图 图表
	 * @param caption	标题
	 * @param xAxisName	x轴标题，没有则不填
	 * @param yAxisName	y轴标题，没有则不填
	 * @param decimals	小数点位数
	 * @param showValues	是否在线或点上显示数值
	 * @param numberPrefix	鼠标移上显示前置内容
	 * @param numberSuffix	鼠标移上显示后置内容
	 * @param column	封装好的柱状图
	 * @return
	 */
	public static String getColumnXml(String caption,String subCaption,String xAxisName,String yAxisName,int decimals,int showValues,int showSum,String numberPrefix,String numberSuffix,String column) {
		StringBuffer strXML = new StringBuffer(); 
		strXML.append("<?xml version=\'1.0\' encoding=\'UTF-8\'?>");
		strXML.append("<chart palette='"+UtilReport.PALETTE+"' subCaption='"+subCaption+"' caption='"+caption+"' xAxisName='"+xAxisName+"' yAxisName='"+yAxisName+"' decimals='"+decimals+"' showSum='"+showSum+"' numberPrefix='"+numberPrefix+"' numberSuffix='"+numberSuffix+"' baseFont='宋体' baseFontSize='12' shownames='1' showvalues='1' bgColor='99CCFF,FFFFFF' bgAlpha='40,100' bgRatio='0,100' showBorder='1'useRoundEdges='1' legendBorderAlpha='0'>");
		
		strXML.append(column);
		
		strXML.append("</chart>");
		
		return strXML.toString();
	}
	
	/**
	 * 动态生成 柱状图 图表
	 * @param columnShow x轴标题显示方向 1 正常，2 垂直，3 45度斜角 4 交叉
	 * @param caption	标题
	 * @param xAxisName	x轴标题，没有则不填
	 * @param yAxisName	y轴标题，没有则不填
	 * @param decimals	小数点位数
	 * @param showValues	是否在线或点上显示数值
	 * @param numberPrefix	鼠标移上显示前置内容
	 * @param numberSuffix	鼠标移上显示后置内容
	 * @param column	封装好的柱状图
	 * @return
	 */
	public static String getColumnXml(int columnShow,String caption,String subCaption,String xAxisName,String yAxisName,int decimals,int showValues,int showSum,String numberPrefix,String numberSuffix,String column) {
		String columnStyle = "";
		if(columnShow == 2){
			columnStyle = "labelDisplay='ROTATE'";
		}else if(columnShow == 3){
			columnStyle = "labelDisplay='Rotate' slantLabels='1' ";
		}else if(columnShow == 4){
			columnStyle = "labelDisplay='Stagger'";
		}
		
		StringBuffer strXML = new StringBuffer(); 
		strXML.append("<?xml version=\'1.0\' encoding=\'UTF-8\'?>");
		strXML.append("<chart palette='"+UtilReport.PALETTE+"' subCaption='"+subCaption+"' caption='"+caption+"' xAxisName='"+xAxisName+"' yAxisName='"+yAxisName+"' decimals='"+decimals+"' showSum='"+showSum+"' numberPrefix='"+numberPrefix+"' numberSuffix='"+numberSuffix+"' baseFont='宋体' baseFontSize='12' shownames='1' showvalues='1' bgColor='99CCFF,FFFFFF' bgAlpha='40,100' bgRatio='0,100' showBorder='1'useRoundEdges='1' legendBorderAlpha='0' "+columnStyle+">");
		
		strXML.append(column);
		
		strXML.append("</chart>");
		
		return strXML.toString();
	}
}
