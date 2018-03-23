package com.pinhuba.web.taglib;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
/**
 * 图片打印显示
 * @author peng.ning
 *
 */
public class ImageShowTag extends TagSupport {
	
	private static final long serialVersionUID = 3073704476963456024L;

	private int imageId;
	
	private String imageCode;
	
	private String noImagePath;
	
	private String imagePath;
	
	private String title;
	
	private String id;
	
	private String name;
	
	private String alt;
	
	private String onclick;
	
	private String onmouseover;
	
	private String style;
	
	private String width;
	
	private String height;
	
	private String onmouseout;
	
	private String className;

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public void setImageCode(String imageCode) {
		this.imageCode = imageCode;
	}

	public void setNoImagePath(String noImagePath) {
		this.noImagePath = noImagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
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

	public void setAlt(String alt) {
		this.alt = alt;
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

	public void setWidth(String width) {
		this.width = width;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public void setOnmouseout(String onmouseout) {
		this.onmouseout = onmouseout;
	}

	public void setClassName(String className) {
		this.className = className;
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
		result.append("<img border='0' src='"+request.getContextPath()+"/showimg.do?type=img");
		if(imagePath!=null&&imagePath.length()>0){
			result.append("&imgPath="+imagePath);
		}
		if(imageId>0){
			result.append("&imgId="+imageId);
		}
		if (imageCode!=null&&imageCode.length()>0) {
			result.append("&imgCode="+imageCode);
		}
		if (noImagePath!=null&&noImagePath.length()>0) {
			result.append("&noImgPath="+noImagePath);
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
		if (alt != null && alt.length() > 0) {
			result.append("alt='" + alt + "' ");
		}
		if (width!=null&&width.length()>0) {
			result.append("width='"+width+"' ");
		}
		if (height!=null&&height.length()>0) {
			result.append("height='"+height+"' ");
		}
		if (style!=null&&style.length()>0) {
			result.append("style='"+style+"' ");
		}
		if (className!=null&&className.length()>0) {
			result.append("className='"+className+"' ");
		}
		if (onclick!=null&&onclick.length()>0) {
			result.append("onclick=\""+onclick+"\" ");
		}
		if (onmouseover!=null&&onmouseover.length()>0) {
			result.append("onmouseover=\""+onmouseover+"\" ");
		}
		if (onmouseout!=null&&onmouseout.length()>0) {
			result.append("onmouseout=\""+onmouseout+"\" ");
		}
		result.append("/>\n");
		return result.toString();
	}
}
