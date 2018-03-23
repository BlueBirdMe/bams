package com.pinhuba.web.taglib;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
/**
 * 文件下载标签
 * @author peng.ning
 *
 */
public class FileDownloadTag extends TagSupport {
	
	private static final long serialVersionUID = -5045513955766446263L;

	private int fileId;
	
	private String filePath;
	
	private String saveName;
	
	private String title;
	
	private String id;
	
	private String name;
	
	private String onclick;
	
	private String onmouseover;
	
	private String style;
	
	private String onmouseout;
	
	private String className;
	
	private String value;

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	public void setOnmouseover(String onmouseover) {
		this.onmouseover = onmouseover;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public void setOnmouseout(String onmouseout) {
		this.onmouseout = onmouseout;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
	public void setValue(String value) {
		this.value = value;
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
		result.append("<a href='"+request.getContextPath()+"/download.do?type=filedown");
		if(filePath!=null&&filePath.length()>0){
			result.append("&filePath="+filePath);
		}
		if(fileId>0){
			result.append("&fileId="+fileId);
		}
		if (saveName!=null&&saveName.length()>0) {
			result.append("&saveName="+saveName);
		}
		result.append("' ");
		if (id != null && id.length() > 0) {
			result.append("id='" + id + "' ");
		}
		if (name != null && name.length() > 0) {
			result.append("name='" + name + "' ");
		}
		if (title != null && title.length() > 0) {
			result.append("title='" + title + "' ");
		}
		if (style!=null&&style.length()>0) {
			result.append("style='"+style+"' ");
		}
		if (className!=null&&className.length()>0) {
			result.append("className='"+className+"' ");
		}
		if (onclick!=null&&onclick.length()>0) {
			result.append("onclick='"+onclick+"' ");
		}
		if (onmouseover!=null&&onmouseover.length()>0) {
			result.append("onmouseover='"+onmouseover+"' ");
		}
		if (onmouseout!=null&&onmouseout.length()>0) {
			result.append("onmouseout='"+onmouseout+"' ");
		}
		result.append(">");
		if (value!=null&&value.length()>0) {
			result.append(value);
		}
		result.append("</a>\n");
		return result.toString();
	}
}
