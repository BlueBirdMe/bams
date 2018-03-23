package com.pinhuba.web.taglib.button;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
/**
 * 图片按钮
 * @author peng.ning
 *
 */
public class ButtonImageTag extends TagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 893929427899417404L;
	private String onclick;
	private String title;
	private String imgsrc;
	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setImgsrc(String imgsrc) {
		this.imgsrc = imgsrc;
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
		StringBuffer btn = new StringBuffer();
		btn.append("<table cellpadding='0' cellspacing='0' border='0' onmouseover='Btn.btnTableOver(this);' onmouseout='Btn.btnTableOut(this);'>\n");
		btn.append("<tr height='1px'><td class='jiaotd' style='padding:0px;margin:0px;'></td><td class='linetd' style='padding:0px;margin:0px;'></td><td class='jiaotd' style='padding:0px;margin:0px;'></td></tr>\n");
		btn.append("<tr><td width='1px' class='linetd' style='padding:0px;margin:0px;'></td>\n");
		btn.append("<td class='inputtd' style='padding:0px;margin:0px;'  align='center'");
		if (onclick != null && onclick.length() > 0) {
			btn.append(" onclick =\"" + onclick + "\"");
		}
		if (title != null && title.length() > 0) {
			btn.append(" title ='" + title + "'");
		}
		btn.append(">");
		if (imgsrc != null && imgsrc.length() > 0) {
			btn.append("<a href='javascript:void(0)' style='cursor: default;'><img src='" + imgsrc + "' style='width: 24px;height: 24px;' border='0'></a>");
		}
		btn.append("</td>\n");
		btn.append("<td width='1px' class='linetd' style='padding:0px;margin:0px;'></td></tr>\n");
		btn.append("<tr height='1px'><td class='jiaotd' style='padding:0px;margin:0px;'></td><td class='linetd' style='padding:0px;margin:0px;'></td><td class='jiaotd' style='padding:0px;margin:0px;'></td></tr></table>\n");
		return btn.toString();
	}
}
