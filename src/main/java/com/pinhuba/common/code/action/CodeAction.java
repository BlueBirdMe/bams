package com.pinhuba.common.code.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.pinhuba.common.code.CreateDwrAndService;
import com.pinhuba.common.code.CreatePage;
import com.pinhuba.common.code.CreatePojoAndDao;
import com.pinhuba.common.code.bean.DbTable;
import com.pinhuba.common.code.exception.ColumnRemarkException;
import com.pinhuba.common.code.service.DbDesignService;
import com.pinhuba.common.code.util.Util;
import com.pinhuba.common.util.ConstWords;

@Controller
@RequestMapping("/erp/code_create/")
public class CodeAction {

	private DbDesignService designService = Util.getDesignService();
	
	@RequestMapping(value = "createPojoAndDao.do", method = RequestMethod.GET)
	public String createPojoAndDaoGet(HttpServletRequest request, ModelMap model) throws Exception {
		List<DbTable> tables = designService.listTables();
		model.addAttribute("tableList", tables);
		return "code_create_one.jsp";
	}

	/**
	 * PojoAndDao
	 */
	@RequestMapping(value = "createPojoAndDao.do", method = RequestMethod.POST)
	public String createPojoAndDaoPost(String tables, HttpServletRequest request, ModelMap model) throws Exception {

		try {
			CreatePojoAndDao cp = new CreatePojoAndDao(tables);
			
			cp.getTablePojo();// 创建pojo
			cp.getTableDao();// 创建dao
			cp.getTableDaoImpl();// 创建daoImpl
			cp.getConfig();// 生成配置临时文件
			model.addAttribute(ConstWords.TempStringMsg, "代码生成成功，请查看！");
			
		} catch (ColumnRemarkException e) {
			model.addAttribute(ConstWords.TempStringMsg, e.getErrorMessage());
		} catch (Exception e) {
			model.addAttribute(ConstWords.TempStringMsg, "代码生成失败，请注意查看相关数据项！");
			e.printStackTrace();
		}
		
		List<DbTable> tableList = designService.listTables();
		model.addAttribute("tableList", tableList);
		model.addAttribute("tables", tables);

		return "code_create_one.jsp";
	}

	/**
	 * DwrAndService
	 */
	@RequestMapping(value = "createDwrAndService.do", method = RequestMethod.POST)
	public String createDwrAndServicePost(String pojoClass, String moduleName, Boolean istree, 
			HttpServletRequest request, ModelMap model) {
		try {
			CreateDwrAndService cp = new CreateDwrAndService(moduleName, pojoClass, istree);
			cp.getDwr();// 创建Dwr
			cp.getService();// 创建Service
			cp.getPack();
			cp.getConfig();// 生成配置临时文件
			model.addAttribute(ConstWords.TempStringMsg, "代码生成成功，请查看！");
		} catch (Exception e) {
			model.addAttribute(ConstWords.TempStringMsg, "代码生成失败，请注意查看相关数据项！");
			e.printStackTrace();
		}
		model.addAttribute("pojoClass", pojoClass);
		model.addAttribute("moduleName", moduleName);

		return "code_create_two.jsp";
	}

	/**
	 * Page
	 */
	@RequestMapping(value = "createPage.do", method = RequestMethod.POST)
	public String createPagePost(String pojoClass, String pojoShortName, String pojoName, String dwrName, 
			String columnCount, String folderName, Boolean istree, HttpServletRequest request, ModelMap model){

		try {
			CreatePage cp = new CreatePage(pojoClass, pojoShortName, pojoName, dwrName, columnCount, folderName);
			cp.getAddPage();// 创建新增/编辑页
			cp.getManagePage();// 创建列表页
			cp.getDetailPage();// 创建详情页
			if (istree)
				cp.getTreePage();// 创建树形结构相关页面
			model.addAttribute(ConstWords.TempStringMsg, "页面生成成功，请查看！");
		} catch (Exception e) {
			model.addAttribute(ConstWords.TempStringMsg, "页面生成失败，请注意查看相关数据项！");
			e.printStackTrace();
		}

		model.addAttribute("pojoClass", pojoClass);
		model.addAttribute("pojoShortName", pojoShortName);
		model.addAttribute("pojoName", pojoName);
		model.addAttribute("dwrName", dwrName);
		model.addAttribute("columnCount", columnCount);
		model.addAttribute("folderName", folderName);

		return "code_create_three.jsp";
	}

}
