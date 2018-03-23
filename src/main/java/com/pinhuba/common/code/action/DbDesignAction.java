package com.pinhuba.common.code.action;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.pinhuba.common.code.bean.DbDatabase;
import com.pinhuba.common.code.bean.DbField;
import com.pinhuba.common.code.bean.DbTable;
import com.pinhuba.common.code.service.DbDesignService;
import com.pinhuba.common.code.util.Util;

@Controller
@RequestMapping("/erp/code_create/")
public class DbDesignAction {

	private DbDesignService designService = Util.getDesignService();
	
	
	/**
	 * 创建表
	 * @param table
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "createTable.do", method = RequestMethod.POST)
	public String createTable(DbTable table, HttpServletRequest request, ModelMap model) throws Exception{
		designService.createTable(table);
		return "redirect:listTables.do";
	}
	
	/**
	 * 修改表
	 * @param oldName
	 * @param table
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "updateTable.do", method = RequestMethod.POST)
	public String updateTable(String oldName, DbTable table, HttpServletRequest request, ModelMap model) throws Exception{
		designService.updateTable(oldName, table);
		return "redirect:listTables.do";
	}
	
	/**
	 * 删除表
	 * @param name
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "deleteTable.do")
	public String deleteTable(String name, HttpServletRequest request, ModelMap model) throws Exception{
		designService.deleteTable(name);
		return "redirect:listTables.do";
	}
	
	/**
	 * 预览表字段
	 * @param name
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "viewTable.do")
	public String viewTable(String name, HttpServletRequest request, ModelMap model) throws Exception{
		List<DbField> fields = designService.listField(name);
		model.addAttribute("fields", fields);
		model.addAttribute("tableName", name);
		return "db_table_view.jsp";
	}
	
	/**
	 * 展示数据库表
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "listTables.do")
	public String listTables(HttpServletRequest request, ModelMap model) throws Exception {
		
		DbDatabase db = designService.getDatabase();
		List<DbTable> tables = designService.listTables();
		model.addAttribute("db", db);
		model.addAttribute("tables", tables);
		
		return "db_tables.jsp";
	}
	
	/**
	 * 保存字段
	 * @param tableName
	 * @param name
	 * @param type
	 * @param size
	 * @param defaultValue
	 * @param remark
	 * @param showQuery
	 * @param showAdvanced
	 * @param componentType
	 * @param must
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "saveField.do")
	public String saveField(String tableName, String[] name, String[] type, String[] size,
			String[] defaultValue, String[] remark, String[] showQuery, String[] showAdvanced,
			Integer[] componentType, String[] must,
			HttpServletRequest request, ModelMap model) throws Exception{
			
		List<DbField> fields = new ArrayList<DbField>();
		if(name != null && name.length > 0){
			for (int i = 0; i < name.length; i++) {
				DbField field = new DbField();
				field.setName(name[i]);
				field.setType(type[i]);
				field.setSize(size[i]);
				field.setDefaultValue(defaultValue[i]);
				
				String remarks = remark[i] + "|" +
						Util.boolean2int(showQuery[i]) + "|" +
						Util.boolean2int(showAdvanced[i]) + "|" +
						componentType[i] + "|" +
						Util.boolean2int(must[i]);
				field.setRemarks(remarks);
				fields.add(field);
			}
		}
		designService.saveField(fields,tableName);
		return "redirect:viewTable.do?name="+tableName;
	}
	
	/**
	 * 删除字段
	 * @param tableName
	 * @param fieldName
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "deleteField.do")
	public String deleteField(String tableName, String fieldName,
			HttpServletRequest request, ModelMap model) throws Exception{
		designService.deleteField(tableName,fieldName);
		return "redirect:viewTable.do?name="+tableName;
	}
	
	/**
	 * 编辑字段
	 * @param tableName
	 * @param fieldName
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "editField.do")
	public String editField(String tableName, String fieldName,
			HttpServletRequest request, ModelMap model) throws Exception{
		
		DbField field = designService.getField(tableName,fieldName);
		model.addAttribute("field", field);
		model.addAttribute("tableName", tableName);
		return "db_field_edit.jsp";
	}

	/**
	 * 修改字段
	 * @param tableName
	 * @param field
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "updateField.do")
	public String updateField(String tableName, DbField field,
			HttpServletRequest request, ModelMap model) throws Exception{
		
		String remarks = field.getRemark() + "|" +
				Util.boolean2int(field.getShowQuery()) + "|" +
				Util.boolean2int(field.getShowAdvanced()) + "|" +
				field.getComponentType() + "|" +
				Util.boolean2int(field.getMust());
		field.setRemarks(remarks);
		
		designService.updateField(tableName, field);
		
		return "redirect:viewTable.do?name="+tableName;
	}

}
