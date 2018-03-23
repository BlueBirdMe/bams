package com.pinhuba.web.taglib.table;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

public class SysTableTag extends TagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7165425650223950180L;

	private String width;

	private String tableTitle;

	private String id;
	
	public void setWidth(String width) {
		this.width = width;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setTableTitle(String tableTitle) {
		this.tableTitle = tableTitle;
	}

	public int doStartTag() throws JspTagException {
		String bar = getStartString();
		try {
			pageContext.getOut().write(bar);
		} catch (IOException e) {
			throw new JspTagException(e.getMessage());
		}
		return EVAL_BODY_INCLUDE;
	}

	/**
	 * 输出标记内的内容
	 */
	public int doEndTag() throws JspTagException {
		try {
			// 输出结束标记
			String end = "</thead><tbody></tbody></table></td></tr></table>\n";
			pageContext.getOut().write(end);
		} catch (IOException ex) {
			throw new JspTagException(ex.getMessage());
		}

		return EVAL_PAGE;
	}

	private String getStartString() {
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		StringBuffer paramBuf = new StringBuffer();
		String wd = "";
		if (width != null && width.length() > 0) {
			wd = "width ='" + width + "'";
		}
		paramBuf.append("<table cellpadding='0' cellspacing='0' border='0' align='center' " + wd + "/>\n");
		paramBuf.append("<tr style=\"BACKGROUND-IMAGE: url('" + request.getContextPath() + "/images/grid_images/wbg.gif');\" height=\"26px\">\n");
		
		//不需要标题的table，比如tab标签页中的table
		if(tableTitle != null && tableTitle.trim().length() > 0){
			paramBuf.append("<td align='left' style='padding-left:10px;font-weight: bold;' id='"+id+"_TitleName'>" + tableTitle + "</td><td align='right' id='systablebtntd'></td></tr>\n");
		}else{
			paramBuf.append("<td>&nbsp;</td></tr>");
		}
		
		paramBuf.append("<tr><td valign='top' colspan='2'>\n");
		paramBuf.append("<table  class='tablerowStyleColor'  cellSpacing='0' cellPadding='4' width='100%' align='center' border='1' id='"+id+"'>\n");
		//paramBuf.append("<thead style=\"BACKGROUND-IMAGE: url('" + request.getContextPath() + "/images/grid_images/fhbg2.gif');\">");
		paramBuf.append("<thead>");
		return paramBuf.toString();
	}

}
