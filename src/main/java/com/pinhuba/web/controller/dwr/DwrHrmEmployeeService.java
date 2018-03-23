package com.pinhuba.web.controller.dwr;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import com.pinhuba.common.module.ResultBean;
import com.pinhuba.common.module.SessionUser;
import com.pinhuba.common.pages.Pager;
import com.pinhuba.common.pages.PagerHelper;
import com.pinhuba.common.util.ConvertPinyin;
import com.pinhuba.common.util.EnumUtil;
import com.pinhuba.common.util.LoginContext;
import com.pinhuba.common.util.UtilPrimaryKey;
import com.pinhuba.common.util.UtilTool;
import com.pinhuba.common.util.UtilWork;
import com.pinhuba.common.util.WebUtilWork;
import com.pinhuba.core.iservice.IHrmEmployeeService;
import com.pinhuba.core.pojo.HrmDepartment;
import com.pinhuba.core.pojo.HrmEmployee;
import com.pinhuba.core.pojo.HrmPost;
import com.pinhuba.core.pojo.HrmWorkarea;

/**********************************************
Class name: 人力资源
Description:对DWR服务进行描述
Others:         // 
History:        
liurunkai    2010.4.28     v3.0
peng.ning	2010.4.30 添加考勤查询
**********************************************/
public class DwrHrmEmployeeService {
	private final static Logger logger = Logger.getLogger(DwrHrmEmployeeService.class);
	
	@Resource
	private IHrmEmployeeService employeeinfoService;

	/**
	 * 选择人员 显示在职人员
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean listEmployees(ServletContext context, HttpServletRequest request, HrmEmployee employee, Pager pager) {
		
		List<HrmEmployee> list = null;
		employee.setHrmEmployeeActive(EnumUtil.SYS_ISACTION.Vaild.value);
		
		if(employee.getGroupId() > 0){
			pager = PagerHelper.getPager(pager,employeeinfoService.listEmployeeByGroupIdCount(employee,(long)UtilTool.getCompanyId(request)));
			list = employeeinfoService.listEmployeeByGroupId(employee,(long)UtilTool.getCompanyId(request), pager);
		}else if(employee.getRoleId() > 0){
			pager = PagerHelper.getPager(pager,employeeinfoService.listEmployeeByRoleIdCount(employee,(long)UtilTool.getCompanyId(request)));
			list = employeeinfoService.listEmployeeByRoleId(employee,(long)UtilTool.getCompanyId(request), pager);
		}else{
			pager = PagerHelper.getPager(pager,employeeinfoService.listEmployeeCount(employee,(long)UtilTool.getCompanyId(request)));
			list = employeeinfoService.getAllEmployee(employee,(long)UtilTool.getCompanyId(request), pager);
		}
		
		for (HrmEmployee hrmEmployee : list) {
			hrmEmployee.setHrmDepartment(employeeinfoService.getDepartmentByID(hrmEmployee.getHrmEmployeeDepid()));	
		}
		logger.info("显示所有人员...");
		return WebUtilWork.WebResultPack(list, pager);
	}
	
	/**
	 * 显示所有人员
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean listAllEmployees(ServletContext context, HttpServletRequest request, HrmEmployee employee, Pager pager) {
	
		List<HrmEmployee> list = null;
		pager = PagerHelper.getPager(pager,employeeinfoService.listEmployeeCount(employee,(long)UtilTool.getCompanyId(request)));
		list = employeeinfoService.getAllEmployee(employee,(long)UtilTool.getCompanyId(request), pager);
		for (HrmEmployee hrmEmployee : list) {
			if(hrmEmployee.getHrmEmployeeDepid() != null){
				hrmEmployee.setHrmDepartment(employeeinfoService.getDepartmentByID(hrmEmployee.getHrmEmployeeDepid()));	
			}else{
				hrmEmployee.setHrmDepartment(new HrmDepartment());
			}
		}
		logger.info("显示所有人员...");
		return WebUtilWork.WebResultPack(list, pager);
	}
	
	/**
	 * 根据编号获取所有下级部门(加载树使用)
	 * @param context
	 * @param request
	 * @param deptCode
	 * @return
	 */
	public List<HrmDepartment> listDownDepartByCode(HttpServletRequest request,String deptCode){
		SessionUser user = (SessionUser)LoginContext.getSessionValueByLogin(request);		
		List<HrmDepartment> deptList = employeeinfoService.getDownDeptartByCode(deptCode,user.getCompanyId());
		logger.info("获取编号为:"+deptCode+"的下级部门...");
		return deptList;
	}
	
	/**
	 * 统计节点下的部门数量
	 * @param request
	 * @param deptCode
	 * @return
	 */
	public int listDownDepartByCodeCount(HttpServletRequest request,String deptCode){
		SessionUser user = (SessionUser)LoginContext.getSessionValueByLogin(request);
		return employeeinfoService.getDownDeptartByCodeCount(deptCode,user.getCompanyId());
	}
	/**********************************************
	 * 以上代码由BAMS代码生成工具自动生成，一般情况下无需修改。
	 * 开发人员在此注释以下编写业务逻辑代码，并将自己写的代码框起来，例如：
	 **********************************************/

	/**********************JC-begin**********************/
	   public void method(){
	      System.out.println("JC's code here");
	   }
	/**********************JC-end**********************/

	/**********************Jacy-begin**********************/
	   public void method2(){
	      System.out.println("Jacy's code here");
	   }
	/**********************Jacy-end**********************/	
	/**
	 * 新增人员
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean saveEmployee(ServletContext context, HttpServletRequest request, HrmEmployee employee, String attach, String imgfile) {
		String empid = UtilTool.getEmployeeId(request);
		String nowtime = UtilWork.getNowTime();
		employee.setCompanyId(UtilTool.getCompanyId(request));
		//判断编号和姓名是否重复
		if (employeeinfoService.checkEmployeeByEmployeeCardid(employee) == false) {
			return new ResultBean(false, "员工编号 [ " + employee.getHrmEmployeeCode()
					+ " ] 已存在,请重新输入。");
		}
		if (employeeinfoService.checkEmployeeByEmployeeName(employee) == false) {
			return new ResultBean(false, "员工姓名 [ " + employee.getHrmEmployeeName()
					+ " ] 已存在,请重新输入。");
		}
        
		if (employee.getPrimaryKey() != null && employee.getPrimaryKey().length() > 0) {  //编辑
			HrmEmployee temp = employeeinfoService.getEmployeeByPK(employee.getPrimaryKey());
			
			// 删除原附件
			UtilTool.deleteAttachmentsNoFile(context, request, temp.getHrmOtherAttachmen());
			// 删除原照片
			if (temp.getHrmEmployeeImageInfoId() != null && temp.getHrmEmployeeImageInfoId().intValue() > 0) {
				UtilTool.deleteImagesNoFile(context, request, temp.getHrmEmployeeImageInfoId().toString());
			}
			employee.setRecordId(temp.getRecordId());
			employee.setRecordDate(temp.getRecordDate());
			employee.setHrmEmployeeSquadId(temp.getHrmEmployeeSquadId());
		}else{//新增
			
			employee.setRecordId(empid);
			employee.setRecordDate(nowtime);	
			//生成人员主键
			
			employee.setPrimaryKey(UtilPrimaryKey.getPrimaryKey(String.valueOf(UtilTool.getCompanyId(request))));
		}
		
		String smpleName=ConvertPinyin.getPinYinHeadChar(employee.getHrmEmployeeName());
		employee.setHrmEmployeeSimple(smpleName);
		// 保存附
		String ids = UtilTool.saveAttachments(context, request, attach);
		employee.setHrmOtherAttachmen(ids);
		// 保存照片
		String imgid = UtilTool.saveImages(context, request, imgfile);
		int f = 0;
		if (imgid != null && imgid.length() > 0) {
			f = Integer.parseInt(imgid);
		}
		employee.setHrmEmployeeImageInfoId(f);
		employee.setHrmEmployeeActive(EnumUtil.SYS_ISACTION.Vaild.value);
		employee.setLastmodiId(empid);
		employee.setLastmodiDate(nowtime);

		employeeinfoService.saveEmployee(employee);
		logger.info("新增人员...");
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 根据ID获取人员
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean getEmployeeByPK(ServletContext context, HttpServletRequest request, String employeePK) {

		HrmEmployee employee = employeeinfoService.getEmployeeByPK(employeePK);
		HrmDepartment dep = new HrmDepartment();
		//加载部门信息 
		
		if(employee.getHrmEmployeeDepid() != null){
			dep = employeeinfoService.getDepartmentByID(employee.getHrmEmployeeDepid());
		}
		
		if(dep != null){
		  employee.setHrmDepartment(dep);
		}
		
		//加载岗位信息
		HrmPost post =  new HrmPost();
		if (employee.getHrmEmployeePostId() != null && employee.getHrmEmployeePostId().intValue() > 0) {
			 post = employeeinfoService.getPostByPkUseEmp(employee.getHrmEmployeePostId());
			
		}	
		if(post != null){
			 employee.setHrmEmployeePost(post);
		}
		
		//写入兼职岗位
		String hrmPartPostNames = "";
		String hrmPartPosts = "";
		if(employee.getHrmPartPost() != null && employee.getHrmPartPost().length()>0){
			String hrmPartPostsid[] = employee.getHrmPartPost().substring(0, employee.getHrmPartPost().length()-1).split(",");
			
			for(int i=0 ; i<hrmPartPostsid.length;i++){
				HrmPost postTep = employeeinfoService.getPostByPkUseEmp(Long.parseLong(hrmPartPostsid[i]));
				if (postTep != null){
					hrmPartPostNames += postTep.getHrmPostName()+",";
					hrmPartPosts += hrmPartPostsid[i]+",";
				}
			}	
		}
		employee.setHrmPartPostName(hrmPartPostNames);
		employee.setHrmPartPost(hrmPartPosts);
		
		employee.setHrmEmployeePatternName(UtilTool.getLibraryInfoByPk(context, request, Long.parseLong(employee.getHrmEmployeePatternId()==null?"-1":employee.getHrmEmployeePatternId())));
		employee.setHrmEmployeeMarriageName(UtilTool.getLibraryInfoByPk(context, request, Long.parseLong(employee.getHrmEmployeeMarriage()==null?"-1":employee.getHrmEmployeeMarriage())));
		employee.setHrmEmployeePoliticsName(UtilTool.getLibraryInfoByPk(context, request, Long.parseLong(employee.getHrmEmployeePolitics()==null?"-1":employee.getHrmEmployeePolitics())));
		employee.setHrmEmployeeNationalityName(UtilTool.getLibraryInfoByPk(context, request, Long.parseLong(employee.getHrmEmployeeNationality()==null?"-1":employee.getHrmEmployeeNationality())));
		employee.setHrmEmployeeBloodTypeName(UtilTool.getLibraryInfoByPk(context, request, Long.parseLong(employee.getHrmEmployeeBloodType()==null?"-1":employee.getHrmEmployeeBloodType())));
		employee.setHrmEmployeeDegreeName(UtilTool.getLibraryInfoByPk(context, request, Long.parseLong(employee.getHrmEmployeeDegree()==null?"-1":employee.getHrmEmployeeDegree())));
		
		if(employee.getHrmEmployeeWorkareaid() != null){
			employee.setHrmEmployeeWorkarea(employeeinfoService.getEmployeeWorkareaByPk(employee.getHrmEmployeeWorkareaid()));
		}
		List<HrmEmployee> list = new ArrayList<HrmEmployee>();
		list.add(employee);
		logger.info("根据ID获取人员...");
		return WebUtilWork.WebResultPack(list);
	}
	
	/**
	 * 
	 * 设置离职状态，只是改变人员信息状态【1，有效 2，无效】)
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean setSeparationByIds(ServletContext context, HttpServletRequest request, String[] employeePKs) {
	    employeeinfoService.setSeparationByPks(employeePKs,UtilTool.getCompanyId(request));	
		logger.info("设置离职人员...");
		return WebUtilWork.WebResultPack(null);
	}
	
	/**
	 * 
	 * 设置拼音码
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean setPinYinMaByIds(ServletContext context, HttpServletRequest request, String[] employeePKs) {
		String time = UtilWork.getNowTime();
		String empId = UtilTool.getEmployeeId(request);
		String pks = "";
		if(employeePKs != null && employeePKs.length > 0){
			for(int i = 0;i< employeePKs.length;i++){
				pks += employeePKs[i]+",";
			}
		}
	   List<HrmEmployee> list = employeeinfoService.getEmployeeByPKs(pks);
	   for(HrmEmployee emp:list){
		   String smpleName=ConvertPinyin.getPinYinHeadChar(emp.getHrmEmployeeName());
		   emp.setHrmEmployeeSimple(smpleName);
		   emp.setLastmodiDate(time);
		   emp.setLastmodiId(empId);
		   employeeinfoService.saveEmployee(emp);
	   }
		return WebUtilWork.WebResultPack(null);
	}
	/**
	 * 显示所有部门
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean listDepartments(ServletContext context, HttpServletRequest request, HrmDepartment department, Pager pager) {
		List<HrmDepartment> list = null;
		int companyId = UtilTool.getCompanyId(request);
		try {
			if(department.getHrmDepUpid() != null && department.getHrmDepUpid().length() > 0){
				HrmDepartment dept = employeeinfoService.getDepartmentByPK(Integer.parseInt(department.getHrmDepUpid()));
				department.setHrmDepUpid(dept.getHrmDepId());
			}
		    pager = PagerHelper.getPager(pager,employeeinfoService.listDepartmentCount(department,companyId));
		    list = employeeinfoService.getAllDepartment(department,companyId, pager);
		    
		    for(HrmDepartment oaDepartment : list){
		    	if(oaDepartment.getHrmEmpId() != null && oaDepartment.getHrmEmpId().length() > 0){
					HrmEmployee employee = employeeinfoService.getEmployeeByPK(oaDepartment.getHrmEmpId());
					oaDepartment.setEmployee(employee);
				}else{
					oaDepartment.setEmployee(new HrmEmployee());
				}
				if(oaDepartment.getHrmDepUpid() != null && oaDepartment.getHrmDepUpid().length() > 0){
					HrmDepartment updep = employeeinfoService.getDepartmentByCode(oaDepartment.getHrmDepUpid(),UtilTool.getCompanyId(request));
					oaDepartment.setParentDepartment(updep);
				}else{
					oaDepartment.setParentDepartment(new HrmDepartment());
				}
		    }
		    
		    logger.info("显示所有部门...");
		} catch (Exception e) {
			logger.error("显示所有部门出错..."+e.getMessage());
		}
		return WebUtilWork.WebResultPack(list, pager);
	}
	
	public ResultBean treeMoveShowRow(HttpServletRequest request,long pk,int type){
		HrmDepartment dept = employeeinfoService.getDepartmentByPK(pk);
		List<HrmDepartment> deptList = employeeinfoService.getDownDeptartByCode(dept.getHrmDepUpid(), UtilTool.getCompanyId(request));
		int deplen = deptList.size();
		if (deplen<=1) {
			dept.setHrmDepShowRow(1);
			employeeinfoService.saveDepartment(dept);
		}else{
			int tmpIndex = -1;			
			for (int i = 0; i < deptList.size(); i++) {
				HrmDepartment tmp = deptList.get(i);
				if (tmp.getPrimaryKey() == pk) {
					tmpIndex = i;
					break;
				}
			}
			if (type == EnumUtil.Tree_MOVE_Type.MOVE_UP.value) {//向上移动
				if (tmpIndex>0) {
					HrmDepartment dept1 = deptList.get(tmpIndex-1);
					HrmDepartment dept2 = deptList.get(tmpIndex);
					
					int row1 = dept2.getHrmDepShowRow();
					int row2 = dept1.getHrmDepShowRow();
					
					dept1.setHrmDepShowRow(row1);
					dept2.setHrmDepShowRow(row2);
					employeeinfoService.saveDepartment(dept1);
					employeeinfoService.saveDepartment(dept2);
				}
			}else if (type == EnumUtil.Tree_MOVE_Type.MOVE_DOWN.value) {//向下移动
				if (tmpIndex<(deplen-1)) {
					HrmDepartment dept1 = deptList.get(tmpIndex+1);
					HrmDepartment dept2 = deptList.get(tmpIndex);
					
					int row1 = dept2.getHrmDepShowRow();
					int row2 = dept1.getHrmDepShowRow();
					
					dept1.setHrmDepShowRow(row1);
					dept2.setHrmDepShowRow(row2);
					employeeinfoService.saveDepartment(dept1);
					employeeinfoService.saveDepartment(dept2);
				}
			}
		}
		return WebUtilWork.WebResultPack(null);
	}
	
	public ResultBean treeMoveShowRowByPost(HttpServletRequest request,long pk,int type){
		HrmPost post = employeeinfoService.getPostByPk(pk,0);
		List<HrmPost> postList = employeeinfoService.getHrmPostListByUpcode(post.getHrmPostUpid(), UtilTool.getCompanyId(request));
		int plen = postList.size();
		if (plen<=1) {
			post.setHrmPostShowRow(1);
			employeeinfoService.savePost(post);
		}else{
			int tmpIndex = -1;			
			for (int i = 0; i < postList.size(); i++) {
				HrmPost tmp = postList.get(i);
				if (tmp.getPrimaryKey() == pk) {
					tmpIndex = i;
					break;
				}
			}
			if (type == EnumUtil.Tree_MOVE_Type.MOVE_UP.value) {//向上移动
				if (tmpIndex>0) {
					HrmPost pt1 = postList.get(tmpIndex-1);
					HrmPost pt2 = postList.get(tmpIndex);
					
					int row1 = pt2.getHrmPostShowRow();
					int row2 = pt1.getHrmPostShowRow();
					
					pt1.setHrmPostShowRow(row1);
					pt2.setHrmPostShowRow(row2);
					employeeinfoService.savePost(pt1);
					employeeinfoService.savePost(pt2);
				}
			}else if (type == EnumUtil.Tree_MOVE_Type.MOVE_DOWN.value) {//向下移动
				if (tmpIndex<(plen-1)) {
					HrmPost pt1 = postList.get(tmpIndex+1);
					HrmPost pt2 = postList.get(tmpIndex);
					
					int row1 = pt2.getHrmPostShowRow();
					int row2 = pt1.getHrmPostShowRow();
					
					pt1.setHrmPostShowRow(row1);
					pt2.setHrmPostShowRow(row2);
					employeeinfoService.savePost(pt1);
					employeeinfoService.savePost(pt2);
				}
			}
		}
		return WebUtilWork.WebResultPack(null);
	}
	
	/**
	 * 不分页查询所有部门信息
	 * @param context
	 * @param requestR
	 * @param com   公司主键
	 * @return
	 */
	public ResultBean getAlldepartmentNopager(ServletContext context, HttpServletRequest request,long com){
		List<HrmDepartment> list = new ArrayList<HrmDepartment>();
		list = employeeinfoService.getAllDepNopager(UtilTool.getCompanyId(request));
		return WebUtilWork.WebResultPack(list);
	}
	
	/**
	 * 新增部门信息
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean saveDepartment(ServletContext context, HttpServletRequest request,HrmDepartment department) {
		String upPk = department.getHrmDepUpid();
		String time = UtilWork.getNowTime();
		String empId = UtilTool.getEmployeeId(request);
		String tmpUpCode="00";
		//判断重名（同一级别下，部门名称不能重名）
		int count = 0;
		if(upPk != null && upPk.length()>0){
			int upDepPk = Integer.parseInt(upPk);
			HrmDepartment hDept = employeeinfoService.getDepartmentByPK(upDepPk);
			// 存在上级部门
			tmpUpCode = hDept.getHrmDepId();
			count = employeeinfoService.getDepartmentCountBynameAndCode(department.getHrmDepName(),tmpUpCode,UtilTool.getCompanyId(request),0);
		}else{
			count = employeeinfoService.getDepartmentCountBynameAndCode(department.getHrmDepName(),tmpUpCode,UtilTool.getCompanyId(request),0);
		}
		if(count>0){
			return new ResultBean(false,"同一级别下，部门名称不能重名！");
		}
		department.setHrmDepUpid(tmpUpCode);
		String code = UtilTool.getCodeByUpCode(context, request, tmpUpCode, "hrm_department", "hrm_dep_id", "hrm_dep_upid");
		
		if (code!=null&&code.length()>=2) {//放入显示顺序
			department.setHrmDepShowRow(Integer.parseInt(code.substring(code.length()-2)));
		}else{
			department.setHrmDepShowRow(99);
		}
		
		department.setHrmDepId(code);
		department.setCompanyId(UtilTool.getCompanyId(request));

		department.setLastmodiDate(time);
		department.setLastmodiId(empId);
		department.setRecordId(empId);
		department.setRecordDate(time);

		employeeinfoService.saveDepartment(department);
		logger.info("新增部门信息...");
		return WebUtilWork.WebResultPack(null);
	}
	
	/**
	 * 删除部门信息
	 * @param context
	 * @param request
	 * @param id
	 * @return
	 */
	public ResultBean deleteDepartmentById(ServletContext context,HttpServletRequest request,long[] ids){
		for (long l : ids) {
			HrmDepartment dept = employeeinfoService.getDepartmentByPK(l);
			//判断是否包含下级部门
			if (dept.getHrmDepId()!=null&&dept.getHrmDepId().length()>0) {
				int row = employeeinfoService.getdepartmentByUpCode(dept.getHrmDepId(), UtilTool.getCompanyId(request));
				if (row>0) {
					return new ResultBean(false,dept.getHrmDepName()+" 部门有下级部门 "+row+" 个,不能删除！");
				}
			}
			//是否包含人员信息
			HrmEmployee employee = new HrmEmployee();
			employee.setHrmEmployeeDepid((int)l);
			int empcount = employeeinfoService.listEmployeeCount(employee,(long)UtilTool.getCompanyId(request));
			if (empcount>0) {
				return new ResultBean(false,dept.getHrmDepName()+" 部门有 "+empcount+" 个人员,不能删除！");
			}
		}
		employeeinfoService.deleteDepartmentById(ids);
		return WebUtilWork.WebResultPack(null);
	}
	
	/**
	 *    根据主键获取部门信息
	 * @param context
	 * @param request
	 * @param departmentPK
	 * @return
	 */
	public ResultBean getDepartmentByPK(ServletContext context, HttpServletRequest request, long departmentPK) {

		HrmDepartment department = employeeinfoService.getDepartmentByPK(departmentPK);
		//加载部门经理
		if(department.getHrmEmpId() != null && department.getHrmEmpId().length() > 0){
			HrmEmployee employee = employeeinfoService.getEmployeeByPK(department.getHrmEmpId());
			department.setEmployee(employee);
		}else{
			department.setEmployee(new HrmEmployee());
		}
		//加载上级部门
		if(department.getHrmDepUpid() != null && department.getHrmDepUpid().length() > 0){
			HrmDepartment updep = employeeinfoService.getDepartmentByCode(department.getHrmDepUpid(),UtilTool.getCompanyId(request));
			department.setParentDepartment(updep);
		}else{
			department.setParentDepartment(new HrmDepartment());
		}
		
		List<HrmDepartment> list = new ArrayList<HrmDepartment>();
		list.add(department);
		logger.info("根据ID获取部门...");
		return WebUtilWork.WebResultPack(list);
	}
	
	/**
	 * 设置部门负责人
	 * @param context
	 * @param request
	 * @param deptid
	 * @param empid
	 * @return
	 */
	public ResultBean updateDepartmentMangerById(ServletContext context,HttpServletRequest request,long deptid,String empid){
		HrmDepartment dept = employeeinfoService.getDepartmentByPK(deptid);
		dept.setHrmEmpId(empid);
		dept.setLastmodiId(UtilTool.getEmployeeId(request));
		dept.setLastmodiDate(UtilWork.getNowTime());
		employeeinfoService.saveDepartment(dept);
		return WebUtilWork.WebResultPack(null);
	}
	
	
	/**
	 * 编辑部门
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean updateDepartment(ServletContext context, HttpServletRequest request,HrmDepartment department,String oldDepUpId) {
		//获取当前修改部门信息
		String upPk = department.getHrmDepUpid();
		String tmpUpCode="00";
		
		//判断重名（同一级别下，部门名称不能重名）
		int count = 0;
		if(department.getHrmDepUpid() != null && department.getHrmDepUpid().length()>0){
			int upDepPk = Integer.parseInt(upPk);
			HrmDepartment hDept = employeeinfoService.getDepartmentByPK(upDepPk);
			// 存在上级部门
			tmpUpCode = hDept.getHrmDepId();
			count = employeeinfoService.getDepartmentCountBynameAndCode(department.getHrmDepName(),tmpUpCode,UtilTool.getCompanyId(request),department.getPrimaryKey());
		}else{
			count = employeeinfoService.getDepartmentCountBynameAndCode(department.getHrmDepName(),tmpUpCode,UtilTool.getCompanyId(request),department.getPrimaryKey());
		}
		if(count>0){
			return new ResultBean(false,"同一级别下，部门名称不能重名！");
		}
		if (upPk != null && upPk.length() > 0) {
			int upDepPk = Integer.parseInt(upPk);
			HrmDepartment hDept = employeeinfoService.getDepartmentByPK(upDepPk);
			tmpUpCode = hDept.getHrmDepId();
		}
		HrmDepartment dept = employeeinfoService.getDepartmentByPK(department.getPrimaryKey());
		String newDepId =null;
		if(tmpUpCode.equals(oldDepUpId)){//上级编码未变动
			department.setHrmDepId(dept.getHrmDepId());
			department.setHrmDepUpid(oldDepUpId);
			department.setHrmDepShowRow(dept.getHrmDepShowRow());
		}else{
			boolean bl = false;
			//判断上级编码是否为自己和自己的下级
			List<HrmDepartment> tmpList = employeeinfoService.getHrmDepartmetnListByDepId(dept.getHrmDepId(),dept.getCompanyId());
			for (HrmDepartment hrmDepartment : tmpList) {
				if (hrmDepartment.getHrmDepId().equals(tmpUpCode)) {
					bl = true;
					break;
				}
			}
			if (bl) {
				return new ResultBean(false,"上级部门不能为自己和自己的下级部门!");
			}
			
			department.setHrmDepUpid(tmpUpCode);
			//重新计算部门编码
			newDepId = UtilTool.getCodeByUpCode(context, request, tmpUpCode, "hrm_department", "hrm_dep_id", "hrm_dep_upid");
			
			department.setHrmDepId(newDepId);
			
			if (newDepId!=null&&newDepId.length()>=2) {//放入显示顺序
				department.setHrmDepShowRow(Integer.parseInt(newDepId.substring(newDepId.length()-2)));
			}else{
				department.setHrmDepShowRow(99);
			}			
		}
		department.setHrmEmpId(dept.getHrmEmpId());
		department.setCompanyId(dept.getCompanyId());
		
		String time =UtilWork.getNowTime();
		
		department.setLastmodiDate(time);
		department.setLastmodiId(UtilTool.getEmployeeId(request));
		department.setRecordId(dept.getRecordId());
		department.setRecordDate(dept.getRecordDate());
		employeeinfoService.saveDepartment(department);
		
		//更新下级部门编码
		if(newDepId!=null){
			employeeinfoService.updateBatchHrmDepartmentIdAndUpId(dept.getHrmDepId(), newDepId, dept.getCompanyId());
		}else{
			logger.info("编码未变动不用更新");
		}
		logger.info("编辑部门...");
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 显示所有岗位
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean listPostByPager(ServletContext context, HttpServletRequest request, HrmPost hrmPost, Pager pager) {
		hrmPost.setCompanyId(UtilTool.getCompanyId(request));
		pager = PagerHelper.getPager(pager,employeeinfoService.listPostCount(hrmPost,UtilTool.getCompanyId(request)));
		List<HrmPost>  list = employeeinfoService.getAllPost(hrmPost,UtilTool.getCompanyId(request), pager);
		return WebUtilWork.WebResultPack(list, pager);
	}
	
	/**
	 * 新增岗位
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean savePost(ServletContext context, HttpServletRequest request, HrmPost post) {
		String empid = UtilTool.getEmployeeId(request);
		String nowtime = UtilWork.getNowTime();
		String newPostId =null;
		HrmPost tmp = null;
		if (post.getPrimaryKey() > 0) {   //编辑
			tmp = employeeinfoService.getPostByPk(post.getPrimaryKey(),0);
			if(post.getHrmPostUpid().equals(tmp.getHrmPostUpid())){//上级编码未变动
				post.setHrmPostId(tmp.getHrmPostId());
				post.setHrmPostUpid(tmp.getHrmPostUpid());
				post.setHrmPostShowRow(tmp.getHrmPostShowRow());
			}else{
				boolean bl = false;
				//判断上级编码是否为自己和自己的下级
				List<HrmPost> tmpList = employeeinfoService.getHrmPostListByPostId(tmp.getHrmPostId(),tmp.getCompanyId());
				for (HrmPost hrmPost : tmpList) {
					if (hrmPost.getHrmPostId().equals(post.getHrmPostUpid())) {
						bl = true;
						break;
					}
				}
				if (bl) {
					return new ResultBean(false,"上级岗位不能为自己和自己的下级岗位!");
				}
				newPostId = UtilTool.getCodeByUpCode(context, request, post.getHrmPostUpid(), "hrm_post", "hrm_post_id", "hrm_post_upid");
				if (newPostId!=null&&newPostId.length()>=2) {//放入显示顺序
					post.setHrmPostShowRow(Integer.parseInt(newPostId.substring(newPostId.length()-2)));
				}else{
					post.setHrmPostShowRow(99);
				}
				post.setHrmPostId(newPostId);
			}
			post.setRecordId(tmp.getRecordId());
			post.setRecordDate(tmp.getRecordDate());
		}else{  //新增
			String code = UtilTool.getCodeByUpCode(context, request, post.getHrmPostUpid(), "hrm_post", "hrm_post_id", "hrm_post_upid");
			if (code!=null&&code.length()>=2) {//放入显示顺序
				post.setHrmPostShowRow(Integer.parseInt(code.substring(code.length()-2)));
			}else{
				post.setHrmPostShowRow(99);
			}
			
			post.setHrmPostId(code);
			post.setRecordId(empid);
			post.setRecordDate(nowtime);
		}
		post.setLastmodiId(empid);
		post.setLastmodiDate(nowtime);
		post.setCompanyId(UtilTool.getCompanyId(request));
		employeeinfoService.savePost(post);
		
		//更新下级岗位编码
		if(newPostId!=null&&tmp!=null){
			employeeinfoService.updateBatchHrmPostIdAndUpId(tmp.getHrmPostId(), newPostId, tmp.getCompanyId());
		}else{
			logger.info("编码未变动不用更新");
		}
		return WebUtilWork.WebResultPack(null);
	}
	
	/**
	 * 设置岗位负责人
	 * @param context
	 * @param request
	 * @param postid
	 * @param empid
	 * @return
	 */
	public ResultBean updatePostManger(ServletContext context, HttpServletRequest request,long postid,String empid){
		String employeeid = UtilTool.getEmployeeId(request);
		String nowtime = UtilWork.getNowTime();
		HrmPost tmp = employeeinfoService.getPostByPk(postid,0);
		tmp.setHrmPostMang(empid);
		tmp.setLastmodiId(employeeid);
		tmp.setLastmodiDate(nowtime);
		employeeinfoService.savePost(tmp);
		return WebUtilWork.WebResultPack(null);
	}
	
	/**
	 * 根据ID获取岗位
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean getPostByPk(ServletContext context, HttpServletRequest request, long postPk) {
		HrmPost post = employeeinfoService.getPostByPk(postPk,UtilTool.getCompanyId(request));
		logger.info("根据ID获取岗位...");
		return WebUtilWork.WebObjectPack(post);
	}
	
	/**
	 * 删除岗位
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean deletePostsByPks(ServletContext context, HttpServletRequest request, long[] postPks) {
		for (long l : postPks) {
			HrmPost post = employeeinfoService.getPostByPk(l,-1);
			int count = employeeinfoService.getHrmPostByUpcodeCount(post.getHrmPostId(), UtilTool.getCompanyId(request));
			if (count>0) {
				return new ResultBean(false,post.getHrmPostName()+" 岗位有下级岗位 "+count+" 个,不能删除！");
			}
			int row = employeeinfoService.getEmployeeCountByPostId(l, UtilTool.getCompanyId(request));
			if (row>0) {
				return new ResultBean(false,post.getHrmPostName()+" 岗位下设置有人员信息,不能删除！");
			}
		}
		employeeinfoService.deletePostsByPks(postPks);
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 根据上级编码获取岗位。
	 * @param request
	 * @param upcode
	 * @return
	 */
	public List<HrmPost> getHrmPostByUpCode(HttpServletRequest request,String upcode){
		return employeeinfoService.getHrmPostListByUpcode(upcode, UtilTool.getCompanyId(request));
	}
	
	/**
	 * 根据上级编码统计岗位数量
	 * @param request
	 * @param upcode
	 * @return
	 */
	public int getHrmPostByupcodeCount(HttpServletRequest request,String upcode){
		return employeeinfoService.getHrmPostByUpcodeCount(upcode, UtilTool.getCompanyId(request));
	}
	
	/**
	 * 根据主岗位获取岗位为该岗位的人员集合
	 * @param context
	 * @param request
	 * @param postid
	 * @param pager
	 * @return
	 */
	public ResultBean getHrmEmployeeListByMainPostId(ServletContext context,HttpServletRequest request,int postid,Pager pager){
		pager= PagerHelper.getPager(pager, employeeinfoService.getEmployeeCountByMainPostId(postid, UtilTool.getCompanyId(request)));
		List<HrmEmployee> employeeList = employeeinfoService.getEmployeeeByMainPostId(postid, UtilTool.getCompanyId(request),pager);
		return WebUtilWork.WebResultPack(employeeList,pager);
	}
	
	/**s
	 * 显示所有工作地区
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean listWorkarea(ServletContext context, HttpServletRequest request, HrmWorkarea workarea, Pager pager) {
		workarea.setCompanyId(UtilTool.getCompanyId(request));
		List<HrmWorkarea> list = null;
		pager = PagerHelper.getPager(pager,employeeinfoService.listWorkareaCount(workarea));
		list = employeeinfoService.getAllWorkarea(workarea,pager);
		
		logger.info("显示所有工作地区...");
		return WebUtilWork.WebResultPack(list, pager);
	}
	
	/**
	 * 新增/编辑工作地区
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean saveWorkarea(ServletContext context, HttpServletRequest request, HrmWorkarea workarea) {
		String empid = UtilTool.getEmployeeId(request);
		int com = UtilTool.getCompanyId(request);
		String nowtime = UtilWork.getNowTime();
		if (workarea.getPrimaryKey() > 0) {  //编辑
			int count = employeeinfoService.getworkAreaCountByname(workarea.getHrmAreaName(),com,workarea.getPrimaryKey());
			if(count>0){
				return new ResultBean(false,"工作地区名称不能重名！");
			}
			
			HrmWorkarea tmp = employeeinfoService.getEmployeeWorkareaByPk(workarea.getPrimaryKey());
			
			workarea.setRecordId(tmp.getRecordId());
			workarea.setRecordDate(tmp.getRecordDate());
		} else {    //新增
			int count = employeeinfoService.getworkAreaCountByname(workarea.getHrmAreaName(),com,0);
			if(count>0){
				return new ResultBean(false,"工作地区名称不能重名！");
			}
			
			workarea.setRecordId(empid);
			workarea.setRecordDate(nowtime);
		}

		workarea.setCompanyId(UtilTool.getCompanyId(request));
		workarea.setLastmodiId(empid);
		workarea.setLastmodiDate(nowtime);
		
		employeeinfoService.saveWorkarea(workarea);
		logger.info("新增/编辑工作地区...");
		return WebUtilWork.WebResultPack(null);
	}
	
	/**
	 * 根据ID获取工作地区
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean getWorkareaByPk(ServletContext context, HttpServletRequest request, long workareaPk) {
		HrmWorkarea workarea = employeeinfoService.getEmployeeWorkareaByPk(workareaPk);
		List<HrmWorkarea> list = new ArrayList<HrmWorkarea>();
		list.add(workarea);
		logger.info("根据ID获取工作地区...");
		return WebUtilWork.WebResultPack(list);
	}
	
	/**
	 * 工作地区
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean deleteWorkareaBypk(ServletContext context, HttpServletRequest request, long[] workareaPKs) {
		for (long l : workareaPKs) {
			HrmWorkarea workarea = employeeinfoService.getEmployeeWorkareaByPk(l);
			int countemp = employeeinfoService.getEmployeeByWorkareaPk(l,UtilTool.getCompanyId(request));
			if (countemp>0){
				return new ResultBean(false,workarea.getHrmAreaName()+" 下设置有人员信息,不能删除！");
			}
			employeeinfoService.deleteWorkareaBypk(l);
		}
		logger.info("删除工作地区...");
		return WebUtilWork.WebResultPack(null);
	}
	
	/**
	 * 工作地区下拉按钮
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean getWorkareaList(ServletContext context,HttpServletRequest request) {
		HrmWorkarea workarea = new HrmWorkarea();
		workarea.setCompanyId(UtilTool.getCompanyId(request));
		List<HrmWorkarea> list = employeeinfoService.getWorkarea(workarea);
		return WebUtilWork.WebResultPack(list);
	}
	
	/**
	 * 显示所有人员
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean listEmployees_post(ServletContext context, HttpServletRequest request, HrmEmployee employee, Pager pager) {
		List<HrmEmployee> list = null;
		employee.setCompanyId(UtilTool.getCompanyId(request));
		pager = PagerHelper.getPager(pager,employeeinfoService.listEmployeeCount_post(employee));
		list = employeeinfoService.getAllEmployee_post(employee, pager);
		
		logger.info("显示所有人员...");
		return WebUtilWork.WebResultPack(list, pager);
	}
	
	/**
	 * 显示已选择人员
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean listEmployees_post_set(ServletContext context, HttpServletRequest request, String employeepks, Pager pager) {
		List<HrmEmployee> list = null;
		pager = PagerHelper.getPager(pager,employeeinfoService.listEmployeeCount_post_set(employeepks));
		list = employeeinfoService.getAllEmployee_post_set(employeepks, pager);
		
		logger.info("显示已选择人员...");
		return WebUtilWork.WebResultPack(list, pager);
	}
	
	/**
	 * 设置已选择人员
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean listEmployees_post_setpost(ServletContext context, HttpServletRequest request, String employeepks, String postid, String partpost ) {
		employeeinfoService.setEmployeePostAndPartPost(employeepks,postid,partpost);
		logger.info("设置已选择人员...");
		return WebUtilWork.WebResultPack(null);
	}
	
	public ResultBean getEmployeeDetailByPK(ServletContext context, HttpServletRequest request, String employeePK) {

		HrmEmployee employee = employeeinfoService.getEmployeeByPK(employeePK);
		HrmDepartment dep = new HrmDepartment();
		// 加载部门信息
		dep = employeeinfoService.getDepartmentByID(employee.getHrmEmployeeDepid());
		if (dep != null) {
			employee.setHrmDepartment(dep);
		}
		return WebUtilWork.WebObjectPack(employee);
	}
}
