package com.pinhuba.web.controller.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import com.pinhuba.common.module.EmployeeExcelBean;
import com.pinhuba.common.util.ConstWords;
import com.pinhuba.common.util.UtilTool;
import com.pinhuba.common.util.UtilWork;
import com.pinhuba.core.iservice.IHrmEmployeeService;
import com.pinhuba.core.pojo.HrmEmployee;

@Controller
public class ImportAction {
	private static final Logger logger = LoggerFactory.getLogger(ImportAction.class);

	@Resource
	private IHrmEmployeeService hrmEmployeeService;


	/**
	 * 导入人员
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/importEmployee.do")
	public String importEmployee(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException{
		ServletInputStream is = request.getInputStream();
		byte[] junk = new byte[1024]; 
		int bytesRead = 0; 
		//首先除去Http Head
		bytesRead = is.readLine(junk, 0, junk.length); 
		bytesRead = is.readLine(junk, 0, junk.length); 
		bytesRead = is.readLine(junk, 0, junk.length); 
		bytesRead = is.readLine(junk, 0, junk.length); 
		//读取excel文件获取填写不合格集合
		List<EmployeeExcelBean> list = new ArrayList<EmployeeExcelBean>();
		Workbook tExcelWorkbook = null;
		int companyId = UtilTool.getCompanyId(request);
		String empId = UtilTool.getEmployeeId(request);
		String time = UtilWork.getNowTime();
		int count = 0;
		int row=1;
        try {
            tExcelWorkbook = Workbook.getWorkbook(is);
            Sheet tExcelSheet = tExcelWorkbook.getSheet(0);
            if (tExcelSheet == null) {
            	model.addAttribute(ConstWords.TempStringMsg,"导入失败:文件格式不符合导入要求！");
            	return null;
            }
            count=tExcelSheet.getRows()-1;
            if (count==0) {
            	model.addAttribute(ConstWords.TempStringMsg,"没有需要导入的数据！");
            	return null;
			}
            for (int i = 1; i < tExcelSheet.getRows(); i++) {
            	boolean bl = true;
            	String msg = "";
            	row++;
                Cell tCells[] = tExcelSheet.getRow(i);
                int len = tCells.length;
                String[] values = new String[len<14?14:len];
                for (int j = 0; j < values.length; j++) {
                	values[j] = "";
				}
                
                for (int j = 0; j <len; j++) {
                	Cell tExcelCell = tCells[j];
                	values[j] = tExcelCell.getContents()==null?"":tExcelCell.getContents().trim();
				}
                
                /************人员必填项开始***************/
                String hrmEmployeeName = values[0];//姓名
                if (hrmEmployeeName.length()==0) {
					bl = false;
					msg = "姓名不能为空！";
				}else{//验证员工姓名是否重复
					int tmp = hrmEmployeeService.getEmployeeByNameCount(hrmEmployeeName, companyId);
					if (tmp>0) {
						bl = false;
						msg = "员工姓名已存在,不能重复！";
					}
				}
                
                String hrmEmployeeSex = values[1];//性别
                if (bl) {
	                if (hrmEmployeeSex.length()==0) {
	   					bl = false;
	   					msg = "性别不能为空！";
	   				}                                              
                }
                
                String hrmEmployeeCode = values[2];//员工工号
                if (bl) {
					if(hrmEmployeeCode.length()==0){
						bl = false;
    					msg = "员工工号不能为空！";
					}else{//验证员工工号是否重复
						int tmp = hrmEmployeeService.getEmployeeByCodeCount(hrmEmployeeCode, companyId);
						if (tmp>0) {
							bl = false;
							msg = "员工工号已存在,不能重复！";
						}
					}
				}
                
                String hrmEmployeeBirthday = values[3];//生日,出生日期
                if (bl) {
					if(hrmEmployeeBirthday.length()==0){
						bl = false;
    					msg = "出生日期不能为空！";
					}
				}
                
                String hrmEmployeeStatus = values[4];//入职状态 1试用 2正常 3离职
                if (bl) {
					if(hrmEmployeeStatus.length()==0){
						bl = false;
    					msg = "入职状态不能为空！";
					}
				}
                
                String hrmEmployeeActive = values[5];//员工状态（1，有效 2，无效）
                if (bl) {
					if(hrmEmployeeActive.length()==0){
						bl = false;
    					msg = "员工状态不能为空！";
					}
				}
                /************人员必填项结束***************/
                
                String hrmEmployeeIdentitycard = values[6];//身份证号
            	String hrmEmployeeHouseAddress = values[7]; //家庭地址
            	String hrmEmployeeMobileTele = values[8];   //移动电话
            	String hrmEmployeeWorkTele = values[9];     //工作电话
            	String hrmEmployeeInTime = values[10];       //入职日期
            	String hrmEmployeeWorkTime = values[11];     //转正日期
                //写入
                if (bl) {
                	HrmEmployee emp = new HrmEmployee();//封装对象写入
                
                	emp.setHrmEmployeeName(hrmEmployeeName);
                	emp.setHrmEmployeeSex(hrmEmployeeSex);
                	emp.setHrmEmployeeCode(hrmEmployeeCode);
                	emp.setHrmEmployeeBirthday(hrmEmployeeBirthday);
                	emp.setHrmEmployeeStatus(Integer.parseInt(hrmEmployeeStatus));
                	emp.setHrmEmployeeActive(Integer.parseInt(hrmEmployeeActive));
                	
                  	emp.setHrmEmployeeIdentitycard(hrmEmployeeIdentitycard);
                  	emp.setHrmEmployeeHouseAddress(hrmEmployeeHouseAddress);
                  	emp.setHrmEmployeeMobileTele(hrmEmployeeMobileTele);
                  	emp.setHrmEmployeeWorkTele(hrmEmployeeWorkTele);
                  	emp.setHrmEmployeeInTime(hrmEmployeeInTime);
                  	emp.setHrmEmployeeWorkTime(hrmEmployeeWorkTime);
                	
                	emp.setRecordId(empId);
                	emp.setRecordDate(time);
                	emp.setLastmodiId(empId);
                	emp.setLastmodiDate(time);
                	emp.setCompanyId(companyId);
                	
                	hrmEmployeeService.saveEmployee(emp,companyId);
				}else{
					//放入错误记录集合
					EmployeeExcelBean bean = new EmployeeExcelBean();
					
					bean.setHrmEmployeeName(hrmEmployeeName);
					bean.setHrmEmployeeSex(hrmEmployeeSex);
					bean.setHrmEmployeeCode(hrmEmployeeCode);
					bean.setHrmEmployeeBirthday(hrmEmployeeBirthday);
					bean.setHrmEmployeeStatus(hrmEmployeeStatus);
					bean.setHrmEmployeeActive(hrmEmployeeActive);
                	
					bean.setHrmEmployeeIdentitycard(hrmEmployeeIdentitycard);
					bean.setHrmEmployeeHouseAddress(hrmEmployeeHouseAddress);
					bean.setHrmEmployeeMobileTele(hrmEmployeeMobileTele);
					bean.setHrmEmployeeWorkTele(hrmEmployeeWorkTele);
					bean.setHrmEmployeeInTime(hrmEmployeeInTime);
					bean.setHrmEmployeeWorkTime(hrmEmployeeWorkTime);
                	
                	bean.setImpMsg(msg);
                	
					list.add(bean);
				}
            }
            String s ="共读取 "+count+" 条数据，成功写入 "+(count-list.size())+" 条，数据填写不规范 "+list.size()+" 条!";
            model.addAttribute(ConstWords.TempStringMsg,s);
            
        } catch (Exception e) {
        	model.addAttribute(ConstWords.TempStringMsg,"读取第 "+row+" 数据时发生异常，请检查！");
        	e.printStackTrace();
        	return null;
        } finally {
            if (tExcelWorkbook != null){
                tExcelWorkbook.close();
            }
            if(is!=null){
            	is.close();
            }
        }
		if (list!=null&&list.size()>0) {
			model.addAttribute("excellist", list);
		}
		return "erp/hrm/employee_import.jsp";
	}


}
