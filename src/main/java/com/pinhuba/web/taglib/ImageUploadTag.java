package com.pinhuba.web.taglib;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
/**
 * 上传图片调用标签
 * @author peng.ning
 *
 */
public class ImageUploadTag extends TagSupport {

	private static final long serialVersionUID = 4030265142262906636L;

	private String defaultImg;

	private String acceptTextId;

	private String height;

	private String width;

	private String style;

	private String className;

	private String id;

	private String name;

	private String title;

	private String alt;

	private String onclick;

	private String edit;
	
	public void setDefaultImg(String defaultImg) {
		this.defaultImg = defaultImg;
	}

	public void setAcceptTextId(String acceptTextId) {
		this.acceptTextId = acceptTextId;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public void setEdit(String edit) {
		this.edit = edit;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	public int doStartTag() throws JspException {

		try {
			String bar = this.getPageTag();
			pageContext.getOut().write(bar);
			return SKIP_BODY;
		} catch (IOException e) {
			throw new JspException(e.getMessage());
		}
	}

	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	private String getPageTag() {
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		StringBuffer result = new StringBuffer();
		if (acceptTextId != null & acceptTextId.length() > 0) {
			result.append("<input type = 'hidden' name='" + acceptTextId + "' id = '" + acceptTextId + "' />\n");
		}
		if ((style == null || style.length() == 0) && (className == null || className.length() == 0)) {
			style = "border: 1px solid #999;";
		}
		result.append("<iframe width='" + width + "' height='" + height + "' scrolling='no' marginheight='2' allowTransparency='true' frameborder='0' style='" + style + "' ");
		if (className != null && className.length() > 0) {
			result.append(" class = '" + className + "' ");
		}
		result.append("src = '" + request.getContextPath() + "/erp/imageupload.jsp?he=" + height);
		if (defaultImg != null && defaultImg.length() > 0) {
			result.append("&defaultImg=" + defaultImg);
		}
		if (acceptTextId != null & acceptTextId.length() > 0) {
			result.append("&AcceptText=" + acceptTextId);
		}
		if (edit != null && edit.length() > 0) {
			result.append("&edit=" + edit);
		}
		result.append("' ");
		if (id != null && id.length() > 0) {
			result.append("id='" + id + "' ");
		}else{
			result.append("id='"+acceptTextId+"__SysFrm'");
		}
		if (name != null && name.length() > 0) {
			result.append("name='" + name + "' ");
		}
		if (title != null && title.length() > 0) {
			result.append("title='" + title + "' ");
		}
		if (alt != null && alt.length() > 0) {
			result.append("alt='" + alt + "' ");
		}
		if (onclick != null && onclick.length() > 0) {
			result.append("onclick='" + onclick + "' ");
		}
		result.append("></iframe>\n");
		return result.toString();
	}
}
