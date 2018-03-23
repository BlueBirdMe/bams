package com.pinhuba.web.servlet.file;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.pinhuba.common.util.ConstWords;
import com.pinhuba.common.util.UtilTool;
import com.pinhuba.common.util.file.FileTool;
import com.pinhuba.common.util.file.properties.SystemConfig;
import com.pinhuba.common.util.imgProcess.MyFileRenamePolicy;
import com.pinhuba.common.util.security.Base64;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.FileRenamePolicy;

public class UploadFileServlet extends HttpServlet {

	private static final long serialVersionUID = -6583277827845475836L;

	public UploadFileServlet() {
		super();
	}

	public void destroy() {
		super.destroy();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String filesize = request.getParameter("fileSize");
		String savetype = request.getParameter("saveType");
		String savedir = request.getParameter("savedir") == null ? "" : request.getParameter("savedir");
		String he = request.getParameter("he");
		String count = request.getParameter("count");
		String aname = request.getParameter("aname");
		String forward = request.getParameter("forward");
		String oldimg = request.getParameter("oldimg");
		// 符合要求开始上传
		String saveDirectory = SystemConfig.getParam("erp.upload.imgSavePath");
		if (savetype.equalsIgnoreCase("file")) {
			saveDirectory = SystemConfig.getParam("erp.upload.fileSavePath");
		}
		if (savetype.equalsIgnoreCase("customer") && savedir != null && savedir.length() > 0) {
			saveDirectory = Base64.getStringFromBase64(savedir);
		}

		if (savedir.length() <= 0) {
			saveDirectory += UtilTool.getCompanyAndUserPath(request, false) + ConstWords.septor;
		}
		FileTool.checkDirAndCreate(saveDirectory);

		int cu = Integer.parseInt(count);
		long fileSize = Long.parseLong(filesize);

		long maxPostSize = cu * fileSize * 1024 * 1024;

		FileRenamePolicy rename = new MyFileRenamePolicy();
		// response的编码为"utf-8",同时采用缺省的文件名冲突解决策略,实现上传

		MultipartRequest multi;
		try {
			multi = new MultipartRequest(request, saveDirectory, (int) maxPostSize, "utf-8", rename);
		} catch (Exception e) {
			e.printStackTrace();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter writer = response.getWriter();
			String msg = "上传文件出错，文件不能超过" + fileSize + "M或系统异常，请重新上传。";
			if (forward.equalsIgnoreCase("img") && oldimg != null && oldimg.length() > 0) {
				String tmp = "<input type='hidden' id='tmp' value ='" + oldimg + "'/>";
				String back = "var tmpv = document.getElementById('tmp').value;\nwindow.location.href = '" + request.getContextPath() + "/erp/imageupload.jsp?defaultImg='+tmpv+'&he=" + he + "&AcceptText=" + aname + "&edit=true';";
				writer.print(tmp + "<script type='text/javascript'>alert('" + msg + "');\n" + back + "</script>");
			} else {
				writer.println("<script type='text/javascript'>alert('" + msg + "');history.back();</script>");
			}
			writer.close();
			return;
		}
		// 清除原图片信息
		if (oldimg != null && oldimg.length() > 0) {
			FileTool.deleteFiles(new String[] { Base64.getStringFromBase64(oldimg) });
		}
		// 输出反馈信息
		Enumeration files = multi.getFileNames();
		TreeMap<String, String> lastMap = new TreeMap<String, String>();
		lastMap.descendingKeySet();
		Map<String, String> otherMap = new HashMap<String, String>();
		while (files.hasMoreElements()) {
			String name = (String) files.nextElement();
			File f = multi.getFile(name);
			if (f != null) {
				String fileName = multi.getFilesystemName(name).replaceAll(",", "");
				String oldFileName = multi.getOriginalFileName(name).replaceAll(",", "");
				String lastFileName = Base64.getBase64FromString(saveDirectory + fileName);
				lastMap.put(oldFileName, lastFileName);
			}
		}
		otherMap.put("aname", aname);
		otherMap.put("he", he);
		String url = null;
		if (forward.equalsIgnoreCase("img")) {
			url = "erp/imageupload.jsp";
		} else if (forward.equalsIgnoreCase("file")) {
			url = "erp/fileupload.jsp";
		}
		request.setAttribute(ConstWords.TempStringRequest2, otherMap);
		request.setAttribute(ConstWords.TempStringRequest, lastMap);
		request.getRequestDispatcher(url).forward(request, response);
	}

	public void init() throws ServletException {
	}
}
