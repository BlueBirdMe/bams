package com.pinhuba.core.iservice;

import java.util.List;

import com.pinhuba.core.pojo.HrmDepartment;
import com.pinhuba.core.pojo.HrmEmployee;
import com.pinhuba.core.pojo.HrmPost;
import com.pinhuba.core.pojo.HrmWorkarea;
import com.pinhuba.common.pages.Pager;

/**
 * 人员相关
 * 
 * @author frin
 * 
 */
public interface IHrmEmployeeService {
	
	public int listEmployeeByGroupIdCount(HrmEmployee employee, long companyId);

	public List<HrmEmployee> listEmployeeByGroupId(HrmEmployee employee, long companyId, Pager pager);
	
	public int listEmployeeByRoleIdCount(HrmEmployee employee, long companyId);

	public List<HrmEmployee> listEmployeeByRoleId(HrmEmployee employee, long companyId, Pager pager);
	
	// 查找人员信息
	public List<HrmEmployee> getAllEmployee(HrmEmployee employee, long l, Pager pager);
	
	public List<HrmEmployee> getAllEmployeeNoPager(HrmEmployee employee, long l);
	
	public void updateBatchHrmDepartmentIdAndUpId(String oldId,String newId,int companyId);
	
	public List<HrmPost> getHrmPostListByPostId(String code,int companyId);
	
	public void updateBatchHrmPostIdAndUpId(String oldId,String newId,int companyId);
	
	public List<HrmDepartment> getHrmDepartmetnListByDepId(String code,int companyId);

	public int listEmployeeCount(HrmEmployee employee, long l);

	public List<HrmDepartment> getDownDeptartByCode(String code, long l);

	public int getDownDeptartByCodeCount(String code, long l);

	public HrmDepartment getDepartmentByID(Integer hrmEmployeeDepid);

	public HrmEmployee saveEmployee(HrmEmployee employee);

	public HrmEmployee getEmployeeByPK(String employeePK);
	
	public List<HrmEmployee> getEmployeeByName(String empname,int companyId);

	public List<HrmEmployee> getEmployeeByPKs(String employeePKs);
	
	public List<HrmDepartment> getDepartmentByPKs(String ids);
	
	// 分页显示所有部门
	public List<HrmDepartment> getAllDepartment(HrmDepartment department, long c, Pager pager);

	// 不分页获取部门
	public List<HrmDepartment> getAllDepNopager(long com);

	// 获取部门数
	public int listDepartmentCount(HrmDepartment department, long c);

	public HrmDepartment getDepartmentByPK(long id);

	public HrmDepartment saveDepartment(HrmDepartment department);

	public void deleteDepartmentById(long[] ids);

	// 根据部门编号获取
	public HrmDepartment getDepartmentByCode(String code, long com);

	public int listPostCount(HrmPost hrmPost, int companyId);

	public List<HrmPost> getAllPost(HrmPost hrmPost, int companyId, Pager pager);

	public void setSeparationByPks(String[] employeePKs, long comid);

	public HrmPost savePost(HrmPost post);

	public int getdepartmentByUpCode(String upcode, int companyId);

	public HrmPost getPostByPk(long postPk, int companyId);

	public void deletePostsByPks(long[] postPks);

	public List<HrmPost> getHrmPostListByUpcode(String upcode, int companyId);

	public int getHrmPostByUpcodeCount(String upcode, int companyId);

	public int getEmployeeCountByPostId(long postid, int companyId);

	public List<HrmEmployee> getEmployeeeByMainPostId(long postid, int companyId, Pager pager);

	public int getEmployeeCountByMainPostId(long postid, int companyId);

	public HrmPost getPostByPkUseEmp(long hrmEmployeePostId);

	public boolean checkEmployeeByEmployeeName(HrmEmployee employee);

	public boolean checkEmployeeByEmployeeCardid(HrmEmployee employee);

	public HrmWorkarea getEmployeeWorkareaByPk(long hrmEmployeeWorkareaid);

	public int listWorkareaCount(HrmWorkarea workarea);

	public List<HrmWorkarea> getAllWorkarea(HrmWorkarea workarea, Pager pager);

	public void saveWorkarea(HrmWorkarea workarea);

	public HrmWorkarea deleteWorkareaBypk(long l);

	public int getEmployeeByWorkareaPk(long l, int companyId);

	public List<HrmWorkarea> getWorkarea(HrmWorkarea workarea);

	public int listEmployeeCount_post(HrmEmployee employee);

	public List<HrmEmployee> getAllEmployee_post(HrmEmployee employee, Pager pager);

	public int listEmployeeCount_post_set(String employeepks);

	public List<HrmEmployee> getAllEmployee_post_set(String employeepks, Pager pager);

	public void setEmployeePostAndPartPost(String employeepks, String postid, String partpost);
	
	//统计考勤明细
	public int getDepartmentCountBynameAndCode(String name,String code,long com,long id);
	
	public int getworkAreaCountByname(String name,long com,long id);

	//根据员工编码查询数量，用于excel导入人员时，判断人员编码是否重。
	public int getEmployeeByCodeCount(String code,int companyId);
	
	//根据员工姓名查询数量，用于excel导入人员时，判断人员姓名是否重复。
	public int getEmployeeByNameCount(String name,int companyId);

	//导入人员时直接调用service层，并设置相应主键。
	public HrmEmployee saveEmployee(HrmEmployee emp, int companyId);
}
