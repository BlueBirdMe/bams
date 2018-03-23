package com.pinhuba.web.taglib.button;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 取消按钮
 * 
 * @author peng.ning
 * 
 */
public class ButtonCancelTag extends TagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5958580064034985670L;
	private String value;
	private String onclick;
	private String title;
	private String imgshow = "false";
	private String width;
	private Integer tabindex;

	public void setValue(String value) {
		this.value = value;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setImgshow(String imgshow) {
		this.imgshow = imgshow;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public void setTabindex(Integer tabindex) {
		this.tabindex = tabindex;
	}

	public int doStartTag() throws JspException {

		try {
			String bar = this.createButton();
			pageContext.getOut().write(bar);
			return SKIP_BODY;
		} catch (IOException e) {
			throw new JspException(e.getMessage());
		}
	}

	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	private String createButton() {
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		StringBuffer btn = new StringBuffer();
		String tmp =" 取 消 ";
		btn.append("<table cellpadding='0' cellspacing='0' border='0' onmouseover='Btn.btnTableOver(this);' onmouseout='Btn.btnTableOut(this);'>\n");
		btn.append("<tr height='1px'><td class='jiaotd' style='padding:0px;margin:0px;'></td><td class='linetd' style='padding:0px;margin:0px;'></td><td class='jiaotd' style='padding:0px;margin:0px;'></td></tr>\n");
		btn.append("<tr><td width='1px' class='linetd' style='padding:0px;margin:0px;'></td>\n");
		btn.append("<td class='inputtd' style='padding:0px;margin:0px;'  align='center'");
		if (width != null && width.length() > 0) {
			btn.append(" width ='" + width + "'");
		}
		if (title != null && title.length() > 0) {
			btn.append(" title ='" + title + "'");
		}
		btn.append(">");
		if (imgshow.equalsIgnoreCase("true")) {
			btn.append("<img src='" + request.getContextPath() + "/images/winclose.png' style='width: 16px;height: 16px;margin-left: 3px;margin-right: 3px;vertical-align:middle !important; *vertical-align: baseline;' border='0'>");
			tmp ="取 消";
		}
		btn.append("<input type='button' id='button_cancel' class='btn_table'");
		if (value != null && value.length() > 0) {
			tmp =value;
		}
		if (onclick != null && onclick.length() > 0) {
			btn.append(" onclick =\"" + onclick + "\"");
		} else {
			btn.append(" onclick ='Sys.close();'");
		}
		btn.append(" value ='"+tmp+"'");
		if (tabindex != null) {
			btn.append(" tabindex ='" + tabindex.intValue() + "'");
		}
		btn.append("/></td>\n");
		btn.append("<td width='1px' class='linetd' style='padding:0px;margin:0px;'></td></tr>\n");
		btn.append("<tr height='1px'><td class='jiaotd' style='padding:0px;margin:0px;'></td><td class='linetd' style='padding:0px;margin:0px;'></td><td class='jiaotd' style='padding:0px;margin:0px;'></td></tr></table>\n");
		return btn.toString();
	}
}
