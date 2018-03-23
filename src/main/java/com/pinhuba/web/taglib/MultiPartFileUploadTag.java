package com.pinhuba.web.taglib;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
/**
 * 多文件上传
 * @author peng.ning
 *
 */
public class MultiPartFileUploadTag extends TagSupport {

	private static final long serialVersionUID = 6624260535148482381L;

	private String height;

	private String width;

	private String style;

	private String className;

	private String id;

	private String name;

	private String title;

	private String alt;
	
	private String acceptTextId;
	
	private String edit;
	
	private int fileCount=0;
	
	private int fileSize=0;
	
	private String type;
	
	private String ext;
	
	private String saveType;
	
	private String saveDir;
	

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

	public void setAcceptTextId(String acceptTextId) {
		this.acceptTextId = acceptTextId;
	}

	public void setEdit(String edit) {
		this.edit = edit;
	}

	public void setFileCount(int fileCount) {
		this.fileCount = fileCount;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public void setSaveType(String saveType) {
		this.saveType = saveType;
	}

	public void setSaveDir(String saveDir) {
		this.saveDir = saveDir;
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

	private String getPageTag() throws JspException{
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		StringBuffer result = new StringBuffer();
		if (acceptTextId != null & acceptTextId.length() > 0) {
			result.append("<input type = 'hidden' name='" + acceptTextId + "' id = '" + acceptTextId + "' />\n");
		}
		if ((style == null || style.length() == 0) && (className == null || className.length() == 0)) {
			style = "border: 1px solid #D4D4D4;";
		}
		result.append("<iframe width='" + width + "' height='" + height + "' scrolling='no' marginheight='4' allowTransparency='true' frameborder='0' style='" + style + "' ");
		if (className != null && className.length() > 0) {
			result.append(" class = '" + className + "' ");
		}
		result.append("src = '" + request.getContextPath() + "/erp/fileupload.jsp?he=" + height);
		if (acceptTextId != null & acceptTextId.length() > 0) {
			result.append("&AcceptText=" + acceptTextId);
		}
		if (fileSize>0) {
			result.append("&fileSize=" + fileSize);
		}
		if (fileCount>0) {
			result.append("&fileCount=" + fileCount);
		}
		if (type != null && type.length()>0) {
			result.append("&type=" + type);
		}
		if (ext!=null&&ext.length()>0) {
			result.append("&ext="+ext);
		}
		if (saveType!=null&&saveType.length()>0) {
			result.append("&saveType="+saveType);
			if (saveType.equalsIgnoreCase("customer")) {
				if (saveDir==null||saveDir.length()<=0) {
					throw new JspException("saveType=customer必须指定存放位置！");
				}else{
					result.append("&saveDir="+saveDir);
				}
			}
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
		result.append("></iframe>\n");
		return result.toString();
	}
}
