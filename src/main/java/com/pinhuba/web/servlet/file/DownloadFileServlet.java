package com.pinhuba.web.servlet.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.pinhuba.common.util.file.FileTool;
import com.pinhuba.common.util.security.Base64;
import com.pinhuba.core.iservice.IFileProcessService;
import com.pinhuba.core.pojo.SysAttachmentInfo;
import com.pinhuba.web.servlet.ServletServiceController;

public class DownloadFileServlet extends ServletServiceController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5034554679172116602L;

	public DownloadFileServlet() {
		super();
	}

	public void destroy() {
		super.destroy();
	}

	/**
	 * 文件下载 fileId filePath至少需要一个 
	 * 
	 * fileId 附件表主键 
	 * 
	 * filePath 附件绝对路径(优先使用) 
	 * 
	 * saveName 下载显示的文件名(可以不指定，不指定filePath将采用系统默认，fileId将采用数据库存储名称)
	 * 
	 * 调用方法 
	 * 
	 * <a href='<%=request.getContextPath() %>/download.do?filePath=d:/2222.txt&saveName=测试文档'>文件</a>
	 * 
	 * <a href='<%=request.getContextPath() %>/download.do?fileId=1'>文件</a>
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		String path = "";
		String saveName = "";
		String saveNameExt="";
		String fid = request.getParameter("fileId");// 附件表编号
		String filePath = request.getParameter("filePath");// 下载的具体文件路径
		String name = request.getParameter("saveName");
		if (name != null && name.trim().length() > 0) {
			saveName = name;
		}
		if (filePath != null && filePath.trim().length() > 0 && FileTool.getIsFile(Base64.getStringFromBase64(filePath))) {
			filePath =Base64.getStringFromBase64(filePath);
			path = filePath;
			String fileext = filePath.substring(0, filePath.indexOf('.'));
			saveNameExt =filePath.substring(fileext.length());
			if (saveName.length() == 0) {// 截取文件名
				String filePrex = filePath.substring(0, filePath.lastIndexOf('/') == -1 ? filePath.lastIndexOf('\\') + 1 : filePath.lastIndexOf('/') + 1);
				saveName = filePath.substring(filePrex.length())+filePath.substring(fileext.length());
			}
		} else if (fid != null && fid.trim().length() > 0) {
			IFileProcessService fileProcessService = this.getFileProcessService();
			SysAttachmentInfo attachMentInfo = fileProcessService.getAttachmentInfoByPk(Long.parseLong(fid));
			if (attachMentInfo != null) {
				String tmpPath = Base64.getStringFromBase64(attachMentInfo.getAttachmentFilename());
				String fext = tmpPath.substring(0, tmpPath.indexOf('.'));
				saveNameExt = tmpPath.substring(fext.length());
				if (tmpPath != null && tmpPath.trim().length() > 0 && FileTool.getIsFile(tmpPath)) {
					path = tmpPath;
					if (saveName.length() == 0) {
						saveName = attachMentInfo.getAttachmentName();
					}
				}
			}
		}
		if (path.length() == 0 || FileTool.getIsFile(path) == false) {
			PrintWriter writer = response.getWriter();
			writer.println("<script type='text/javascript'>alert('附件不存在，请与管理员联系。');history.back();</script>");
			writer.close();
			return;
		}
		
		// 下载文件
		// 设置响应头和下载保存的文件名
		if (saveName != null && saveName.length() > 0) {
			if (saveName.indexOf('.')==-1) {
				saveName+=saveNameExt;
			}
			ServletOutputStream out = null;
			// 读取文件流
			try {
				response.setContentType("application/x-msdownload");
				response.setHeader("Content-Disposition", "attachment; filename="+new String(saveName.getBytes("gb2312"),"iso-8859-1"));
				response.setContentLength((int)new File(path).length());
				out = response.getOutputStream();
				FileInputStream fileInputStream = new FileInputStream(path);
				
				if (fileInputStream != null) {
					byte[] tmp = new byte[1024 * 1024];
					int i = 0;
					while ((i = fileInputStream.read(tmp)) != -1) {
						out.write(tmp, 0, i);
					}
				}
				fileInputStream.close();
				out.flush();
			} catch (IOException e) {
				throw e;
			} finally {
//				System.out.println(request.getRequestURI());
				if (out != null) {
					out.close();
				}
			}
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

	public void init() throws ServletException {
		super.init();
		this.setContext(this.getServletContext());
	}

}
