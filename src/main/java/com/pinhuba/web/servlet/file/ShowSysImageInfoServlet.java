package com.pinhuba.web.servlet.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pinhuba.common.util.file.FileTool;
import com.pinhuba.common.util.file.properties.SystemConfig;
import com.pinhuba.common.util.security.Base64;
import com.pinhuba.core.iservice.IFileProcessService;
import com.pinhuba.core.pojo.SysImageInfo;
import com.pinhuba.web.servlet.ServletServiceController;

/**
 * 加载图片
 * 
 * @author peng.ning
 * 
 */
public class ShowSysImageInfoServlet extends ServletServiceController {

	private static final long serialVersionUID = -3115721054966652096L;

	public ShowSysImageInfoServlet() {
		super();
	}

	public void destroy() {
		super.destroy();
	}

	/**
	 * 显示图片 imgPath imgId至少有一个(不要包含中文) imgPath图片绝对路径（优先使用） imgId 图片表主键
	 * 
	 * noImgPath 指定的无图片时去查找的图片绝对路径（可以不指定，系统将自动查找缺省图片),可以放入系统配置文件进行调用 imgCode
	 * 如果存在压缩图片，压缩图片与原图的标识（可以不指定），可以放入系统配置文件进行调用
	 * 
	 * 调用方法
	 * 
	 * <img src="<%=request.getContextPath() %>/showimg.do?imgId=1">
	 * 
	 * <img src="<%=request.getContextPath()
	 * %>/showimg.do?imgId=1&imgCode=_Max&noImgPath=d:/123.jpg" border="0">
	 * 
	 * <img src="<%=request.getContextPath()
	 * %>/showimg.do?imgPath=d:/2222.jpg&imgCode=_Max&noImgPath=d:/123.jpg"
	 * border="0">
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.reset();
		response.setHeader("Cache-Control","no-cache"); 
		response.setHeader("Pragma","no-cache"); 
		response.setDateHeader("Expires",0); 
		// 默认暂无图片路径
		String defaultNoImgPath = SystemConfig.getParam("erp.upload.imgSavePath") + "noimg.gif";

		String filePath = request.getParameter("imgPath");// 指定图片路径
		String imgId = request.getParameter("imgId");// 图片表编号
		String noImgPath = request.getParameter("noImgPath");// 指定暂无图片
		// 判断指定的暂无图片是否存在,不存在将使用系统默认
		if (noImgPath == null || noImgPath.trim().length() == 0 || FileTool.getIsFile(Base64.getStringFromBase64(noImgPath)) == false) {
			noImgPath = defaultNoImgPath;
		}else{
			noImgPath =Base64.getStringFromBase64(noImgPath);
		}
		String addCode = request.getParameter("imgCode");// 附加码（压缩时使用的附加吗，表示需要找哪个图片的压缩图）

		String path = "";

		if (filePath != null && filePath.trim().length() > 0) {
			boolean bl = FileTool.getIsFile(Base64.getStringFromBase64(filePath));
			if (bl) {
				path = Base64.getStringFromBase64(filePath);
			}
		} else if (imgId != null && imgId.trim().length() > 0) {
			IFileProcessService fileProcessService = this.getFileProcessService();
			long mpk = Long.parseLong(imgId);
			SysImageInfo sysImgInfo = fileProcessService.getImageInfoByPk(mpk);
			if (sysImgInfo != null) {
				String tmpPath = Base64.getStringFromBase64(sysImgInfo.getImageInfoFilename());
				if (tmpPath != null && tmpPath.trim().length() > 0 && FileTool.getIsFile(tmpPath)) {
					path = tmpPath;
				}
			}
		}

		if (addCode != null && addCode.length() > 0&&FileTool.getIsFile(path)) {// 指定附加码后转换图片路径
			String filePrex = path.substring(0, path.indexOf('.'));
			String tmp = filePrex + addCode + path.substring(filePrex.length());
			path = tmp;
		}

		if (path.length() == 0 || FileTool.getIsFile(path) == false) {
			path = noImgPath;
		}
		
		//获取文件扩展名决定ContentType
		
		int index = path.lastIndexOf(".");
		boolean bl = true;
		if (index != -1) {// 如果有后缀
			String ext = path.substring(index + 1).toLowerCase();
			if (ext.equalsIgnoreCase("gif")) {
				response.setContentType("image/gif;charset=utf-8");
				bl = false;
			}else if (ext.equalsIgnoreCase("fif")) {
				response.setContentType("image/fif;charset=utf-8");
				bl = false;
			}else if (ext.equalsIgnoreCase("tiff")||ext.equalsIgnoreCase("tif")) {
				response.setContentType("image/tiff;charset=utf-8");
				bl = false;
			}
		}
		if (bl) {
			response.setContentType("image/jpg;charset=utf-8");
		}
		// 根据最终路径输出图片
		ServletOutputStream output = null;
		try {
			output = response.getOutputStream();
			InputStream in = new FileInputStream(new File(path));
			byte[] tmp = new byte[1024 * 1024];
			int i = 0;
			while ((i = in.read(tmp)) != -1) {
				output.write(tmp, 0, i);
			}
			in.close();
			output.flush();
		} catch (IOException e) {
			throw e;
		} finally {
			if (output != null) {
				output.close();
			}
		}
	}

	

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void init() throws ServletException {
		super.init();
		this.setContext(this.getServletContext());
	}

}
