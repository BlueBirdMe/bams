package com.pinhuba.web.taglib.button;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
/**
 * 创建默认button
 * @author peng.ning
 *
 */
public class ButtonTag extends TagSupport {

	private static final long serialVersionUID = -57117444709133422L;
	private String value;
	private String onclick;
	private String title;
	private String imgsrc;
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

	public void setImgsrc(String imgsrc) {
		this.imgsrc = imgsrc;
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
		StringBuffer btn = new StringBuffer();
		String tmp=" 确 定 ";
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
		if (imgsrc != null && imgsrc.length() > 0) {
			btn.append("<img src='" + imgsrc + "' style='width: 16px;height: 16px; margin-left: 3px;margin-right: 3px;vertical-align:middle !important; *vertical-align: baseline;' border='0'>");
			tmp ="确 定";
		}
		btn.append("<input type='button' id='button_submit' class='btn_table'");
		if (onclick != null && onclick.length() > 0) {
			btn.append(" onclick =\"" + onclick + "\"");
		}
		if (value != null && value.length() > 0) {
			tmp = value;
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
