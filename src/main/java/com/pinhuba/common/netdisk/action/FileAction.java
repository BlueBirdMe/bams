package com.pinhuba.common.netdisk.action;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pinhuba.common.netdisk.core.Page;
import com.pinhuba.common.netdisk.json.bean.FileNode;
import com.pinhuba.common.netdisk.json.bean.JsonDateValueProcessor;
import com.pinhuba.common.pages.Pager;
import com.pinhuba.common.util.ResponseUtils;
import com.pinhuba.common.util.UtilTool;
import com.pinhuba.common.util.UtilWork;
import com.pinhuba.common.util.file.properties.SystemConfig;
import com.pinhuba.core.iservice.INetdiskService;
import com.pinhuba.core.pojo.OaNetdiskConfig;
import com.pinhuba.core.pojo.OaNetdiskShare;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.oreilly.servlet.multipart.FileRenamePolicy;


@Controller
@RequestMapping("/erp/netdisk/")
public class FileAction{
	
	public static String ROOTPATH = null;
	
	@Resource
	private FileService fileService;
	@Resource
	private INetdiskService netdiskService;
	
	public static String getROOTPATH(){
		if(ROOTPATH == null) {
		    try {
				ROOTPATH = SystemConfig.getParam("erp.netdisk.path") ;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return ROOTPATH; 
	}
	
	/**
	 * 功能:得到个人磁盘空间
	 * @throws IOException 
	 */
	@RequestMapping(value = "getPersonalSpace.do")
	public void getPersonalSpace(HttpServletRequest request, HttpServletResponse response) throws IOException{
		OaNetdiskConfig oaNetdiskConfig = new OaNetdiskConfig();
		oaNetdiskConfig.setCompanyId(getCompanyId(request));
		oaNetdiskConfig.setHrmEmployeeId(getHrmEmpId(request));
		Pager page = new Pager();
		page.setStartRow(0);
		page.setTotalPages(1);
		OaNetdiskConfig temp = netdiskService.getOaNetdiskConfigByHrmEmpId(oaNetdiskConfig);
        PrintWriter out = response.getWriter();   
		if(temp != null) {
			out.print("{usedSpace:'"+temp.getUsedSpace()+"',totalSpace:'"+temp.getTotalSpace()+"'}");  
		} else {
			netdiskService.getHrmEmployee(oaNetdiskConfig, request);
			out.print("{usedSpace:'0',totalSpace:'"+UtilTool.getSysParamByIndex(request, "erp.Net.Disk")+"'}");
		}
		out.close();
	}
	
	/**
	 * 功能:获得共享的所有目录信息
	 * 
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	@RequestMapping(value = "getShareDirectories.do")
	public void getShareDirectories(HttpServletRequest request, HttpServletResponse response ,String node) throws FileNotFoundException, IOException {
		if(node.equals("*")) node = "";
		
		List<FileNode> shareNodes;
		
		String empid = UtilTool.getEmployeeId(request)+",";
		String deptid = UtilTool.getDeptId(request)+",";
		if(node.equals("")) {
			OaNetdiskShare oaNetdiskShare = new OaNetdiskShare();
			oaNetdiskShare.setCompanyId(getCompanyId(request));
			List<OaNetdiskShare> list = netdiskService.getSharePathByCompanyId(oaNetdiskShare,empid,deptid);
			shareNodes = fileService.listShareFiles(getROOTPATH() + File.separator + getCompanyId(request) + File.separator, list);
		} else {
			shareNodes = fileService.listFiles(getROOTPATH() + File.separator + getCompanyId(request) + File.separator,node,getHrmEmpId(request), true, true);
		}
		
		JSONArray json = JSONArray.fromObject(shareNodes);
		ResponseUtils.renderJson(response, json.toString());
	}
	
	/**
	 * 功能:获得指定目录下的所有目录信息
	 * 
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	@RequestMapping(value = "getDirectories.do")
	public void getDirectories(HttpServletRequest request, HttpServletResponse response, String node) throws FileNotFoundException, IOException{
		if(node.equals("*")) node = "";
		
		List<FileNode> nodes = fileService.listFiles(getROOTPATH() + File.separator + getCompanyId(request) + File.separator + getHrmEmpId(request), node, getHrmEmpId(request), true, false);
		
		JSONArray json = JSONArray.fromObject(nodes);
		ResponseUtils.renderJson(response, json.toString());
	}
	
	/**
	 * 功能:获得指定文件夹下面的所有文件和文件夹信息
	 * 
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	@RequestMapping(value = "getFiles.do")
	public void getFiles(HttpServletRequest request, HttpServletResponse response, String isShare, String node) throws FileNotFoundException, IOException {
		if(node.equals("*")) node = "";
		List<FileNode> nodes;
		
		Page page = new Page();
		if(isShare.equals("false")) {
			nodes = fileService.listFiles(getROOTPATH() + File.separator + getCompanyId(request) + File.separator + getHrmEmpId(request), node, getHrmEmpId(request), false, false);
		} else {
			nodes = fileService.listFiles(getROOTPATH() + File.separator + getCompanyId(request) + File.separator, node,getHrmEmpId(request), false, true);
		}
		page.setRoot(nodes);

		int length = 0;
		try {
			length = new File(node).list().length;
		} catch (Exception e) {
		}
		page.setTotalProperty(length);
		
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		config.setExcludes(new String[]{"conditions","limit","start","success","objCondition"});
		JSONArray json = JSONArray.fromObject(page, config);
		ResponseUtils.renderJson(response,  handlerJsonStr(json.toString()));
	}
	
	
	/**
	 * 重命名
	 * 
	 * @return String
	 * @throws Exception 
	 */
	@RequestMapping(value = "renameFolder.do")
	public void rename(HttpServletRequest request, HttpServletResponse response, String name, String[] paths) throws Exception{
		String midName = paths[0].substring(0, paths[0].lastIndexOf(File.separator));
		StringBuffer bf = new StringBuffer();
		bf.append(getROOTPATH() + File.separator + getCompanyId(request) + File.separator + getHrmEmpId(request));
		bf.append(midName);
		bf.append(File.separator);
		bf.append(name);
		
		OaNetdiskShare oaNetdiskShare = new OaNetdiskShare();
		oaNetdiskShare.setCompanyId(getCompanyId(request));
		oaNetdiskShare.setHrmEmployeeId(getHrmEmpId(request));
		oaNetdiskShare.setFolderPath(paths[0]);
		netdiskService.saveOaNetdiskShareWhenRenamePath(oaNetdiskShare, midName + File.separator + name, name);
		
		JSONObject json = new JSONObject();
		json.put("success", fileService.rename(getROOTPATH() + File.separator + getCompanyId(request) + File.separator + getHrmEmpId(request) + paths[0], bf.toString()));
		ResponseUtils.renderJson(response, json.toString());
	}
	
	@RequestMapping(value = "download.do")
	public void download(HttpServletRequest request, HttpServletResponse response, String path, String name, String isShare) throws Exception{
		
		if(path.lastIndexOf(File.separator) != path.length()){
			path += File.separator;
		}
		
		String downloadFilePath = null;
		if(isShare.equals("false")) {
			downloadFilePath = getROOTPATH() + File.separator + getCompanyId(request) + File.separator + getHrmEmpId(request) + path + name;
		} else {
			downloadFilePath = getROOTPATH() + File.separator + getCompanyId(request) + File.separator  + path + name;
		}
		
		// 获取服务其上的文件名称
		File file = new File(downloadFilePath);
		response.setHeader("Expires", "0");
		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Pragma", "public");
		response.setContentType("application/x-msdownload;charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=" + new String(name.getBytes(), "iso-8859-1"));

		// 将此文件流写入到response输出流中
		FileInputStream inputStream = new FileInputStream(file);
		OutputStream outputStream = response.getOutputStream();
		byte[] buffer = new byte[1024];
		int i = -1;
		while ((i = inputStream.read(buffer)) != -1) {
			outputStream.write(buffer, 0, i);
		}
		outputStream.flush();
		outputStream.close();
		inputStream.close();
	}
	
	/**
	 * 多文件删除
	 * 
	 * @return
	 * @throws IOException 
	 * @throws Exception 
	 */
	@RequestMapping(value = "deleteFiles.do")
	public void deleteFiles(HttpServletRequest request, HttpServletResponse response, String[] paths) throws IOException{
		boolean flag = false;
		double usedSpace = 0;
		try {
			for (String path : paths) {
				String temp = delFiles(getROOTPATH() + File.separator + getCompanyId(request) + File.separator + getHrmEmpId(request) + path, request);
				String[] tmp = temp.split(",");
				flag = Boolean.valueOf(tmp[0]);
				usedSpace = Double.valueOf(tmp[1]);
				if (!flag) {
					break;
				} else {
					//删除共享
					OaNetdiskShare oaNetdiskShare = new OaNetdiskShare();
					oaNetdiskShare.setCompanyId(getCompanyId(request));
					oaNetdiskShare.setHrmEmployeeId(getHrmEmpId(request));
					oaNetdiskShare.setFolderPath(path);
					netdiskService.deleteShareByHrmEmpIDandPath(oaNetdiskShare);
				}
			}
			
	        PrintWriter out = response.getWriter();
			out.print("{usedSpace:'"+usedSpace+"'}"); 
			out.close();
			out = null;
		} catch (RuntimeException e) {
			flag = false;
			e.printStackTrace();
		}
		JSONObject json = new JSONObject();
		json.put("success", flag);
		ResponseUtils.renderJson(response, json.toString());
	}
	
	private boolean forceDelete(File f) {
		boolean result = false;
		int tryCount = 0;
		while (!result && tryCount++ < 10) {
			System.gc();
			result = f.delete();
		}
		return result;
	}
	
	/**
	 * 删除指定文件路径下面的所有文件和文件夹
	 * 
	 * @param file
	 */
	private String delFiles(String fileName, HttpServletRequest request) {
		double usedSpace = 0;
		boolean flag = false;
		File file = null;
		int size = 0;
		boolean isFile = false;
		try {
			file = new File(fileName);
			if (file.exists()) {
				if (file.isDirectory()) {
					String[] contents = file.list();
					for (int i = 0; i < contents.length; i++) {
						delFiles(file.getAbsolutePath() + "/" + contents[i], request);
					}
				}else {
					FileInputStream fis = new FileInputStream(file);
					size = fis.available();
					fis.close();
					fis = null;
					isFile = true;
				}
				
				//磁盘空间更新
				flag = forceDelete(file);
				if(flag && isFile) {
						//修改磁盘空间
						OaNetdiskConfig oaNetdiskConfig = new OaNetdiskConfig();
						oaNetdiskConfig.setHrmEmployeeId(getHrmEmpId(request));
						oaNetdiskConfig.setCompanyId(getCompanyId(request));
						OaNetdiskConfig temp = netdiskService.getOaNetdiskConfigByHrmEmpId(oaNetdiskConfig);
						if(temp != null) {
							double totalUsed = temp.getUsedSpace()-((double)size/1024/1024);
							if(0>totalUsed){
								totalUsed = 0;
							}
							usedSpace = totalUsed;
							temp.setUsedSpace(totalUsed);
							temp.setLastmodiId(getHrmEmpId(request));
							temp.setLastmodiDate(UtilWork.getNowTime());
							
							netdiskService.SeveOaNetdisk(temp);
						}
				} else if(flag && !isFile) {
					//如果是文件夹
					OaNetdiskConfig oaNetdiskConfig = new OaNetdiskConfig();
					oaNetdiskConfig.setHrmEmployeeId(getHrmEmpId(request));
					oaNetdiskConfig.setCompanyId(getCompanyId(request));
					OaNetdiskConfig temp = netdiskService.getOaNetdiskConfigByHrmEmpId(oaNetdiskConfig);
					if(temp != null) {
						usedSpace = temp.getUsedSpace()	;
					}
					
				}
			} else {
				throw new RuntimeException("File not exist!");
			}
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		finally {
			file = null;
			
		}
		return flag + "," + usedSpace;
	}
	
	/**
	 * 创建文件夹
	 * 
	 * @returns
	 */
	@RequestMapping(value = "createFolder.do")
	public void createFolder(HttpServletRequest request, HttpServletResponse response, String path, String name) {
		
		if(path.equals("/*")) path = "/";
		
		String createPath = getROOTPATH() + File.separator + getCompanyId(request) + File.separator + getHrmEmpId(request) + path + File.separator;
		
		JSONObject json = new JSONObject();
		json.put("success", fileService.createDirectory(createPath + name));
		ResponseUtils.renderJson(response, json.toString());
	}
 
	/**
	 * 上传文件
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "uploadFiles.do", method = RequestMethod.POST)
	public void uploadFiles(HttpServletRequest request, HttpServletResponse response, String path) throws Exception{
		
		path = URLDecoder.decode(path, "UTF-8");
		
		if(path.equals("/*")) path = "/";
		
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		String sp = getROOTPATH() + File.separator + getCompanyId(request) + File.separator + getHrmEmpId(request) + path + File.separator;
		fileService.createDirectory(sp);
		
		FileRenamePolicy rename = new DefaultFileRenamePolicy();
		MultipartRequest multi = new MultipartRequest(request, sp, 1024*1024*1024, "utf-8", rename);
		
		Enumeration files = multi.getFileNames();
		File myUpload = null;
		int size = 0;
		while (files.hasMoreElements()){
			String name = (String) files.nextElement();
			myUpload = multi.getFile(name);
			if(myUpload != null){
				FileInputStream fis = new FileInputStream(myUpload);
				size += fis.available();
			}
		}
		OaNetdiskConfig oaNetdiskConfig = new OaNetdiskConfig();
		oaNetdiskConfig.setHrmEmployeeId(getHrmEmpId(request));
		oaNetdiskConfig.setCompanyId(getCompanyId(request));
		OaNetdiskConfig temp = netdiskService.getOaNetdiskConfigByHrmEmpId(oaNetdiskConfig);
		if(temp != null) {
			double totalUsed = temp.getUsedSpace()+((double)size/1024/1024);
			if(temp.getTotalSpace()<totalUsed){
				throw new Exception("超过磁盘空间大小");
			}
			temp.setUsedSpace(totalUsed);
			temp.setLastmodiId(getHrmEmpId(request));
			temp.setLastmodiDate(UtilWork.getNowTime());
			
			netdiskService.SeveOaNetdisk(temp);
		}
		out.print("<script type='text/javascript'>alert('上传文件成功!');window.parent.Ext.getCmp('netdiskframepanel').close();</script>"); 
		out.close();
		out = null;
	}
	
	/**
	 * 创建缩略图
	 * 
	 * @param file
	 *          上传的文件流
	 * @param height
	 *          最小的尺寸
	 * @throws IOException
	 */
	@RequestMapping(value = "createMiniPic.do")
	public void createMiniPic(HttpServletRequest request, HttpServletResponse response, String path, String isShare) throws IOException {
		path = URLDecoder.decode(path, "UTF-8");
		String filename = null;
		if(isShare.equals("false")) {
			filename = getROOTPATH() + File.separator + getCompanyId(request) + File.separator + getHrmEmpId(request) + path ;
		} else {
			filename = getROOTPATH() + File.separator + getCompanyId(request) + File.separator + path;
		}
		File file = new File(filename);
		Image src = javax.imageio.ImageIO.read(file); 
		int new_w = src.getWidth(null);
		int new_h = src.getHeight(null); 

		BufferedImage tag = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_RGB);
		tag.getGraphics().drawImage(src, 0, 0, new_w, new_h, null); // 绘制缩小后的图
		OutputStream os = response.getOutputStream();
		ImageIO.write(tag,   "JPEG",   os);   
		os.flush();
		os.close();
		os = null;
	}
	
	private String getHrmEmpId(HttpServletRequest request) {
		return UtilTool.getEmployeeId(request);
	}
	
	private int getCompanyId(HttpServletRequest request) {
		return UtilTool.getCompanyId(request);
	}
	
	private String handlerJsonStr(String jsonStr){
		return jsonStr.substring(1,jsonStr.length()-1); 
	}
	
}
