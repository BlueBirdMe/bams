package com.pinhuba.web.controller.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import com.pinhuba.common.util.UtilTool;
import com.pinhuba.common.util.file.office.ExcelExport;
import com.pinhuba.common.util.file.office.WordExport;
import com.pinhuba.core.iservice.IHrmEmployeeService;
import com.pinhuba.core.iservice.IOaNewsService;
import com.pinhuba.core.pojo.HrmEmployee;
import com.pinhuba.core.pojo.OaAnnouncement;
import freemarker.template.TemplateException;

@Controller
public class ExportAction {
	private static final Logger logger = LoggerFactory.getLogger(ExportAction.class);
	
	@Resource
	private IOaNewsService oaNewsService;

	@Resource
	private IHrmEmployeeService employeeinfoService;
	
	@Resource
	private WordExport wordExport;
	
	@Resource
	private ExcelExport excelExport;

	/**
	 * 导出公告WORD
	 * 
	 * @param id
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = "/exportAnnounce.do")
	public void exportAnnounce(long id, HttpServletRequest request, HttpServletResponse response, ModelMap model){
		try {
			wordExport.setFontSize("18");
			wordExport.setFontFamily("微软雅黑");
			wordExport.setSpacingLine("300");

			OaAnnouncement announcement = oaNewsService.getAnnouncementByPK(id);
			announcement.setOaAnnoText(wordExport.changeToWordXml(announcement.getOaAnnoText()));
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("an", announcement);
			String fileName = wordExport.export(map, "gonggao.xml", "gonggao.doc");
			downloadFile(response, fileName, announcement.getOaAnnoName() + ".doc");
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("向WORD中导入图片时出错");
		} catch (TemplateException e) {
			e.printStackTrace();
			logger.error("公告模板未定义");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出文件出错");
		}
		
	}

	/**
	 * 导出人员EXCEL
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = "/exportEmployee.do")
	public void exportEmployee(HrmEmployee employee, HttpServletRequest request, HttpServletResponse response, ModelMap model){
		List<HrmEmployee> list = employeeinfoService.getAllEmployeeNoPager(employee, UtilTool.getCompanyId(request));
		// 处理数据字典
		for (HrmEmployee emp : list) {
			System.out.println(emp.getHrmEmployeeName());
		}
		try {
			String fileName = excelExport.exportEmployeeExcel(list);
			downloadFile(response, fileName, "人员列表.xls");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public static void downloadFile(HttpServletResponse response, String fileName, String bookName) throws IOException {
		// 获取服务其上的文件名称
		File file = new File(fileName);
		response.setHeader("Expires", "0");
		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Pragma", "public");
		response.setContentType("application/x-msdownload;charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=" + new String(bookName.getBytes(), "iso-8859-1"));

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

}
