package com.pinhuba.core.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pinhuba.common.pack.HrmEmployeePack;
import com.pinhuba.common.pages.Pager;
import com.pinhuba.common.util.DateTimeTool;
import com.pinhuba.common.util.EnumUtil;
import com.pinhuba.common.util.UtilPrimaryKey;
import com.pinhuba.core.dao.IHrmDepartmentDao;
import com.pinhuba.core.dao.IHrmEmployeeDao;
import com.pinhuba.core.dao.IHrmPostDao;
import com.pinhuba.core.dao.IHrmWorkareaDao;
import com.pinhuba.core.dao.ISysUserInfoDao;
import com.pinhuba.core.iservice.IHrmEmployeeService;
import com.pinhuba.core.pojo.HrmDepartment;
import com.pinhuba.core.pojo.HrmEmployee;
import com.pinhuba.core.pojo.HrmPost;
import com.pinhuba.core.pojo.HrmWorkarea;
import com.pinhuba.core.pojo.SysUserInfo;

/**********************************************
Class name: 人力资源
Description:对Sevice服务进行描述
Others:         // 
History:        
liurunkai    2010.4.28     v3.0
peng.ning 2010.4.30 增加考勤查询
**********************************************/
@Service
@Transactional
public class HrmEmployeeService implements IHrmEmployeeService {
	private Logger log = Logger.getLogger(this.getClass());
	@Resource
	private IHrmEmployeeDao hrmEmployeeDao;
	@Resource
	private IHrmDepartmentDao hrmDepartmentDao;
	@Resource
	private IHrmPostDao hrmPostDao;
	@Resource
	private IHrmWorkareaDao hrmWorkareaDao;
	@Resource
	private ISysUserInfoDao sysUserInfoDao;
	
	public int listEmployeeByGroupIdCount(HrmEmployee employee, long companyId){
		int count = hrmEmployeeDao.findBySqlCount(HrmEmployeePack.packEmployeeQueryByGroupId(employee,companyId));
		return count;
	}

	public List<HrmEmployee> listEmployeeByGroupId(HrmEmployee employee, long companyId, Pager pager){
		List<HrmEmployee> list = hrmEmployeeDao.findBySqlPage(HrmEmployeePack.packEmployeeQueryByGroupId(employee,companyId), HrmEmployee.class, pager);
		return list;
	}
	
	public int listEmployeeByRoleIdCount(HrmEmployee employee, long companyId){
		int count = hrmEmployeeDao.findBySqlCount(HrmEmployeePack.packEmployeeQueryByRoleId(employee,companyId));
		return count;
	}

	public List<HrmEmployee> listEmployeeByRoleId(HrmEmployee employee, long companyId, Pager pager){
		List<HrmEmployee> list = hrmEmployeeDao.findBySqlPage(HrmEmployeePack.packEmployeeQueryByRoleId(employee,companyId), HrmEmployee.class, pager);
		return list;
	}
	
	/**
	 * 获取所有人员信息
	 */
	public List<HrmEmployee> getAllEmployee(HrmEmployee employee,long l, Pager pager) {
		List<HrmEmployee> employeeList = hrmEmployeeDao.findByHqlWherePage(HrmEmployeePack.packEmployeeQuery(employee,l)+" order by model.hrmEmployeeCode", pager);

		return employeeList;
	}

	/**
	 *  获取所有人员信息 不分页
	 */
	public List<HrmEmployee> getAllEmployeeNoPager(HrmEmployee employee, long l){
		List<HrmEmployee> employeeList = hrmEmployeeDao.findByHqlWhere(HrmEmployeePack.packEmployeeQuery(employee,l)+" order by model.hrmEmployeeCode");
		return employeeList;
	}
	/**
	 * 统计人员记录数量
	 */
	public int listEmployeeCount(HrmEmployee employee,long l) {
		int count = hrmEmployeeDao.findByHqlWhereCount(HrmEmployeePack.packEmployeeQuery(employee,l));
		return count;
	}

	/**
	 * 根据上级部门获取部门信息
	 */
	public List<HrmDepartment> getDownDeptartByCode(String code,long l){
		List<HrmDepartment> deptList = hrmDepartmentDao.findByHqlWhere(" and model.hrmDepUpid = '"+code+"' and model.companyId = "+l+" order by model.hrmDepShowRow");
		return deptList;
	}
	/**
	 * 根据编码查询自己及下级
	 */
	public List<HrmDepartment> getHrmDepartmetnListByDepId(String code,int companyId){
		List<HrmDepartment> deptList = hrmDepartmentDao.findByHqlWhere(" and model.hrmDepId like '"+code+"%' and model.companyId = "+companyId);
		return deptList;
	}
	
	/**
	 * 根据编码查询自己及下级
	 */
	public List<HrmPost> getHrmPostListByPostId(String code,int companyId){
		List<HrmPost> postList = hrmPostDao.findByHqlWhere(" and model.hrmPostId like '"+code+"%' and model.companyId = "+companyId);
		return postList;
	}
	
	/**
	 * 更新部门编码
	 */	
	public void updateBatchHrmDepartmentIdAndUpId(String oldId,String newId,int companyId){
		
		log.info("编码："+oldId+"更换为"+newId+"....");
		
		List<HrmDepartment> deptList = hrmDepartmentDao.findByHqlWhere(" and model.hrmDepId like '"+oldId+"%' and model.companyId = "+companyId+" and model.hrmDepId<>'"+oldId+"'");
		int oldIdLen = oldId.length();
		for (HrmDepartment hrmDepartment : deptList) {
			String tmp = hrmDepartment.getHrmDepId();
			String uptmp = hrmDepartment.getHrmDepUpid();
			String depCode =  newId+tmp.substring(oldIdLen,tmp.length());
			String depUpCode = newId+uptmp.substring(oldIdLen,uptmp.length());
			hrmDepartment.setHrmDepId(depCode);
			hrmDepartment.setHrmDepUpid(depUpCode);
			hrmDepartmentDao.save(hrmDepartment);
			log.info(tmp+"更换为"+depCode+"....");
		}
	}
	
	/**
	 * 更新岗位编码
	 */	
	public void updateBatchHrmPostIdAndUpId(String oldId,String newId,int companyId){
		
		log.info("岗位编码："+oldId+"更换为"+newId+"....");
		
		List<HrmPost> postList = hrmPostDao.findByHqlWhere(" and model.hrmPostId like '"+oldId+"%' and model.companyId = "+companyId+" and model.hrmPostId<>'"+oldId+"'");
		int oldIdLen = oldId.length();
		for (HrmPost hrmpost : postList) {
			String tmp = hrmpost.getHrmPostId();
			String uptmp = hrmpost.getHrmPostUpid();
			String depCode =  newId+tmp.substring(oldIdLen,tmp.length());
			String depUpCode = newId+uptmp.substring(oldIdLen,uptmp.length());
			hrmpost.setHrmPostId(depCode);
			hrmpost.setHrmPostUpid(depUpCode);
			hrmPostDao.save(hrmpost);
			log.info(tmp+"更换为"+depCode+"....");
		}
	}
	
	/**
	 * 根据上级部门节点统计部门数量
	 */
	public int getDownDeptartByCodeCount(String code,long l){
		int count = hrmDepartmentDao.findByHqlWhereCount(" and model.hrmDepUpid = '"+code+"' and model.companyId = "+l);
		return count;
	}

    /**
     * 根据id获取部门信息
     */
	public HrmDepartment getDepartmentByID(Integer hrmEmployeeDepid) {
		HrmDepartment hdp = hrmDepartmentDao.getByPK((long)hrmEmployeeDepid);
		return hdp;
	}

	/**
	 * 保存人员信息
	 */
	public HrmEmployee saveEmployee(HrmEmployee employee) {
		HrmEmployee employeeTemp = (HrmEmployee) hrmEmployeeDao.save(employee);
		return employeeTemp;
	}
	
	/**
	 * 导入人员时直接调用service层，并设置相应主键
	 * @param employee
	 * @param companyId
	 * @return
	 * @author JC
	 * @date   May 26, 2011
	 */
	public HrmEmployee saveEmployee(HrmEmployee employee,int companyId){
		employee.setPrimaryKey(UtilPrimaryKey.getPrimaryKey(String.valueOf(companyId)));
		HrmEmployee employeeTemp = (HrmEmployee) hrmEmployeeDao.save(employee);
		return employeeTemp;
	}

	/**
	 * 根据主键获取人员信息
	 */
    public HrmEmployee getEmployeeByPK(String employeePK) {
		HrmEmployee employee = hrmEmployeeDao.getByPK(employeePK);
		return employee;
	}
    
    /**根据名字获取相应的信息
     * 
     */
    public List<HrmEmployee> getEmployeeByName(String empname,int companyId){
    	List<HrmEmployee> list  = hrmEmployeeDao.findByHqlWhere(" and model.hrmEmployeeName = '" + empname + "'"+" and model.companyId = "+companyId);
    	return list;
    }

    /**
     * 根据主键s获取人员信息
     */
    public List<HrmEmployee> getEmployeeByPKs(String employeePKs){
    	List<HrmEmployee> list = new ArrayList<HrmEmployee>();
    	if(employeePKs == null || employeePKs.length() == 0){
    		return list;
    	}else{
			String[] employeePKArray = employeePKs.substring(0, employeePKs.length()-1).split(",");
			employeePKs = "";
			for (int i = 0 ; i < employeePKArray.length ;i++) {
				if(i ==  employeePKArray.length -1){
					employeePKs += "'" + employeePKArray[i] + "'";
				}else{
					employeePKs += "'" + employeePKArray[i] + "',";
				}
			}
	    	list = hrmEmployeeDao.findByHqlWhere(" and model.primaryKey in ( " + employeePKs + " ) ");
	    	return list;
    	}
    }
    
    /**
     * 根据多条主键获取部门信息
     */
    public List<HrmDepartment> getDepartmentByPKs(String ids){
    	List<HrmDepartment> list = new ArrayList<HrmDepartment>();
    	if(ids == null || ids.length() == 0){
    		return list;
    	}else{
			ids = ids.substring(0,ids.length()-1);
	    	list = hrmDepartmentDao.findByHqlWhere(" and model.primaryKey in ( " +ids + " ) ");
	    	return list;
    	}
    }
    
    
    /**
     * 获取所有部门信息
     */
	public List<HrmDepartment> getAllDepartment(HrmDepartment department,long c, Pager pager) {
		String tmproder = "model.hrmDepCode";
		if (department.getHrmDepUpid()!=null&&department.getHrmDepUpid().length()>0) {
			tmproder = "model.hrmDepShowRow";
		}
		
		
		List<HrmDepartment> hrmDepartment = hrmDepartmentDao.findByHqlWherePage(HrmEmployeePack.packDepartmentQuery(department,c)+" order by "+tmproder, pager);
		for (HrmDepartment dep : hrmDepartment) {
			
			List<HrmDepartment> parentDepartment = null;
			// 如果有上级单位
			if (dep.getHrmDepUpid() != null && !"00".equals(dep.getHrmDepUpid())) {
				 parentDepartment =  hrmDepartmentDao.findByHqlWhere(" and model.hrmDepId = '" + dep.getHrmDepUpid() + "'");
			     if(parentDepartment.size() > 0){
			    	 for(HrmDepartment kk :parentDepartment){
			    		 dep.setParentDepartment(kk);
			    	 }
			     }
			}
			
            //转换部门经理
			if(dep.getHrmEmpId() != null){
				HrmEmployee manager = hrmEmployeeDao.getByPK(dep.getHrmEmpId());
				dep.setEmployee(manager);
			}
			
		}
		return hrmDepartment;
	}

	/**
	 * 统计部门数量
	 */
	public int listDepartmentCount(HrmDepartment department,long c) {
		int count = hrmDepartmentDao.findByHqlWhereCount(HrmEmployeePack.packDepartmentQuery(department,c));
		return count;
	}
	
	/**
	 * 根据主键获取部门信息
	 */
    public HrmDepartment getDepartmentByPK(long id){
    	HrmDepartment department = hrmDepartmentDao.getByPK(id);
    	return department;
    }
    
    /**
     * 保存部门信息
     */
    public HrmDepartment saveDepartment(HrmDepartment department){
		HrmDepartment tmp = (HrmDepartment) hrmDepartmentDao.save(department);
    	return tmp;
    }

    /**
     * 删除部门信息
     */
	public void deleteDepartmentById(long[] ids) {
		for (long l : ids) {
			HrmDepartment department = hrmDepartmentDao.getByPK(l);
			hrmDepartmentDao.remove(department);
		}
	}
	
	/**
	 * 根据上级节点统计部门数量
	 */
	public int getdepartmentByUpCode(String upcode,int companyId){
		int row = hrmDepartmentDao.findByHqlWhereCount(" and model.hrmDepUpid ='"+upcode+"' and model.companyId="+companyId);
		return row;
	}

	/**
	 * 根据部门节点获取部门信息。
	 */
	public HrmDepartment getDepartmentByCode(String code,long com) {
		List<HrmDepartment> department  = hrmDepartmentDao.findByHqlWhere(" and model.hrmDepId = '" + code + "'"+" and model.companyId = "+com);
		HrmDepartment dep =null;
    	if (department.size()>0) {
			dep = department.get(0);
		}
    	return dep;
	}

	/**
	 * 分页显示所有岗位
	 */
	public List<HrmPost> getAllPost(HrmPost hrmPost, int companyId, Pager pager) {
		String tmproder = "model.hrmPostId";
		if (hrmPost.getHrmPostUpid()!=null&&hrmPost.getHrmPostUpid().length()>0) {
			tmproder = "model.hrmPostShowRow";
		}

		List<HrmPost> postList = hrmPostDao.findByHqlWherePage(HrmEmployeePack.packPostQuery(hrmPost,companyId)+" order by "+tmproder, pager);
		for (HrmPost post : postList) {
			if (post.getHrmPostUpid()!=null&&post.getHrmPostUpid().length()>0) {
				List<HrmPost> tmplist = hrmPostDao.findByHqlWhere(" and model.hrmPostId='"+post.getHrmPostUpid()+"' and model.companyId ="+companyId);
				if (tmplist.size()>0) {
					post.setHrmUpPost(tmplist.get(0));	
				}
			}
			if (post.getHrmPostMang()!=null&&post.getHrmPostMang().length()>0) {
				post.setMangerEmployee(hrmEmployeeDao.getByPK(post.getHrmPostMang()));
			}
			
		}
		return postList;
	}

	/**
	 * 统计岗位个数
	 */
	public int listPostCount(HrmPost hrmPost, int companyId) {
		int count = hrmPostDao.findByHqlWhereCount(HrmEmployeePack.packPostQuery(hrmPost,companyId));
		return count;
	}

	/**
	 * 设置离职状态，人员信息置于无效状态，如果存在用户的话，则设置其用户为无效状态
	 */
	public void setSeparationByPks(String[] employeePKs,long comid) {
         for(String l : employeePKs){
             List<SysUserInfo>  userList = sysUserInfoDao.findByHqlWhere(" and model.hrmEmployeeId='"+l+"' and model.companyId ="+comid);
             if(userList.isEmpty() == false){
            	 SysUserInfo  userInfo = userList.get(0);
            	 userInfo.setUserAction(EnumUtil.SYS_ISACTION.No_Vaild.value);
            	 sysUserInfoDao.save(userInfo);
             }
             HrmEmployee employee = hrmEmployeeDao.getByPK(l);
             employee.setHrmEmployeeStatus(EnumUtil.HRM_EMPLOYEE_STATUS.Separation.value);
             employee.setHrmEmployeeActive(EnumUtil.SYS_ISACTION.No_Vaild.value);
             
             hrmEmployeeDao.save(employee);
         }
	}

	/**
	 * 保存岗位
	 */
	public HrmPost savePost(HrmPost post) {
		HrmPost posttemp =  (HrmPost) hrmPostDao.save(post);
		return posttemp;
	}

	/**
	 * 根据岗位主键获取岗位
	 */
	public HrmPost getPostByPk(long postPk,int companyId) {
		HrmPost post = hrmPostDao.getByPK(postPk);
		if (companyId>0) {
			if ((post.getHrmPostUpid()!= null && post.getHrmPostUpid().length()>0) || post.getHrmPostUpid() != "00") {
				List<HrmPost> tmplist = hrmPostDao.findByHqlWhere(" and model.hrmPostId='"+post.getHrmPostUpid()+"' and model.companyId ="+companyId);
				if (tmplist.size()>0) {
					post.setHrmUpPost(tmplist.get(0));
				}
			}
			if (post.getHrmPostMang()!=null&&post.getHrmPostMang().length()>0) {
				post.setMangerEmployee(hrmEmployeeDao.getByPK(post.getHrmPostMang()));
			}
		}
		return post;
	}

	/**
	 *   删除岗位信息
	 */
	public void deletePostsByPks(long[] postPks) {
		for (long l : postPks) {
			HrmPost post = hrmPostDao.getByPK(l);
			hrmPostDao.remove(post);
		}
	}
	
	/**
	 * 根据上级岗位查询岗位信息
	 */
	public List<HrmPost> getHrmPostListByUpcode(String upcode,int companyId){
		List<HrmPost> postList = hrmPostDao.findByHqlWhere(" and model.hrmPostUpid='"+upcode+"' and model.companyId ="+companyId+" order by model.hrmPostShowRow");
		return postList;
	}
	
	/**
	 * 根据上级岗位统计岗位数量
	 */
	public int getHrmPostByUpcodeCount(String upcode,int companyId){
		return hrmPostDao.findByHqlWhereCount(" and model.hrmPostUpid='"+upcode+"' and model.companyId ="+companyId);
	}
	
	/**
	 * 检测岗位下是否包含人员
	 * @param postid
	 * @param companyId
	 * @return
	 */
	public int getEmployeeCountByPostId(long postid,int companyId){
		int row = hrmEmployeeDao.findByHqlWhereCount(" and model.hrmEmployeePostId="+postid+" and model.companyId="+companyId);
		int tmprow=0;
		if (row>0) {
			return row;
		}else{
			List<Object[]> objlist = hrmEmployeeDao.findBySqlObjList("select hrm_part_post from hrm_employee where company_id="+companyId+" and length(hrm_part_post)>0");
			for (Object[] obj : objlist) {
				if(obj!=null&&obj[0]!=null){
					String[] tmp = ((String) obj[0]).split(",");
					for (String str : tmp) {
						if (str!=null&&str.length()>0&&Long.parseLong(str)==postid) {
							tmprow++;
							break;
						}
					}
				}
				if (tmprow>0) {
					break;
				}
			}
		}
		return tmprow;
	}
	
	/**
	 * 统计主岗位为指定的人员信息数量
	 */
	public int getEmployeeCountByMainPostId(long postid,int companyId){
		int row = hrmEmployeeDao.findByHqlWhereCount(" and model.hrmEmployeePostId="+postid+" and model.companyId="+companyId);
		return row;
	}
	
	/**
	 * 获取主岗位为指定的人员信息
	 */
	public List<HrmEmployee> getEmployeeeByMainPostId(long postid,int companyId,Pager pager){
		List<HrmEmployee> employeelist= hrmEmployeeDao.findByHqlWherePage(" and model.hrmEmployeePostId="+postid+" and model.companyId="+companyId,pager);
		return employeelist;
	}

    /**
     * 根据主键ID获取岗位信息
     */
	public HrmPost getPostByPkUseEmp(long hrmEmployeePostId) {
		HrmPost post = hrmPostDao.getByPK(hrmEmployeePostId);
		return post;
	}

	/**
	 * 根据人员姓名查询人员，返回TRUE，或 FALSE
	 */
	public boolean checkEmployeeByEmployeeName(HrmEmployee employee) {

		String sql = " and model.hrmEmployeeName='"+employee.getHrmEmployeeName()+"' and model.companyId ="+employee.getCompanyId()+" and model.hrmEmployeeActive="+EnumUtil.SYS_ISACTION.Vaild.value;
		if (employee.getPrimaryKey()!=null&&employee.getPrimaryKey().trim().length()>0) {
			sql+=" and model.primaryKey <> '"+employee.getPrimaryKey()+"'";
		}
		int count= hrmEmployeeDao.findByHqlWhereCount(sql);

		if(count>0){
			return false;
		}else{
			return true;
		}
	}

	/**
	 * 查询员工编码为指定的人员 返回TRUE，FALSE
	 */
	public boolean checkEmployeeByEmployeeCardid(HrmEmployee employee) {
		String sql = " and model.hrmEmployeeCode='"+employee.getHrmEmployeeCode()+"' and model.companyId ="+employee.getCompanyId()+" and model.hrmEmployeeActive="+EnumUtil.SYS_ISACTION.Vaild.value;
		if (employee.getPrimaryKey()!=null&&employee.getPrimaryKey().trim().length()>0) {
			sql+=" and model.primaryKey <> '"+employee.getPrimaryKey()+"'";
		}
		int count= hrmEmployeeDao.findByHqlWhereCount(sql);

		if(count>0){
			return false;
		}else{
			return true;
		}
	}

	/**
	 * 根据主键获取工作地区信息
	 */
	public HrmWorkarea getEmployeeWorkareaByPk(long hrmEmployeeWorkareaid) {
		HrmWorkarea hrmWorkarea = hrmWorkareaDao.getByPK(hrmEmployeeWorkareaid);
		return hrmWorkarea;
	}

	/**
	 * 获取所有工作地区信息
	 */
	public List<HrmWorkarea> getAllWorkarea(HrmWorkarea workarea, Pager pager) {
		List<HrmWorkarea> workareaList = hrmWorkareaDao.findByHqlWherePage(HrmEmployeePack.packWorkareaQuery(workarea), pager);

		return workareaList;
	}

	/**
	 * 统计工作地区数量
	 */
	public int listWorkareaCount(HrmWorkarea workarea) {
		int count = hrmWorkareaDao.findByHqlWhereCount(HrmEmployeePack.packWorkareaQuery(workarea));
		return count;
	}

    /**
     * 保存工作地区
     */
	public void saveWorkarea(HrmWorkarea workarea) {
		hrmWorkareaDao.save(workarea);
		
	}

	/**
	 * 删除工作地区
	 */
	public HrmWorkarea deleteWorkareaBypk(long l) {
		HrmWorkarea hrmWorkarea = hrmWorkareaDao.getByPK(l);
		hrmWorkareaDao.remove(hrmWorkarea);
		return hrmWorkarea;
	}

	/**
	 * 根据工作地区获取人员数量
	 */
	public int getEmployeeByWorkareaPk(long l, int companyId) {
		return hrmEmployeeDao.findByHqlWhereCount(" and model.hrmEmployeeWorkareaid='"+l+"' and model.companyId ="+companyId);
	}

	/**
	 *  获取工作地区信息
	 */
	public List<HrmWorkarea> getWorkarea(HrmWorkarea workarea) {
		List<HrmWorkarea> list = hrmWorkareaDao.findByHqlWhere(HrmEmployeePack.packWorkareaQuery(workarea));
		return list;
	}

	/**
	 * 获取人员所有岗位
	 */
	public List<HrmEmployee> getAllEmployee_post(HrmEmployee employee,
			Pager pager) {
		List<HrmEmployee> employeelist = hrmEmployeeDao.findBySqlPage(HrmEmployeePack.getEmployeeSql(employee)+" order by emp.hrm_employee_code",HrmEmployee.class,pager);
		for (HrmEmployee hrmEmployee : employeelist) {
			//写入部门
			if(hrmEmployee.getHrmEmployeeDepid() != null && hrmEmployee.getHrmEmployeeDepid()>0){
				hrmEmployee.setHrmDepartment(hrmDepartmentDao.getByPK((long)hrmEmployee.getHrmEmployeeDepid()));	
			}
			//写入主岗位
			if(hrmEmployee.getHrmEmployeePostId() != null && hrmEmployee.getHrmEmployeePostId()>0){
				hrmEmployee.setHrmEmployeePost(hrmPostDao.getByPK((long)hrmEmployee.getHrmEmployeePostId()));
			}
			
			//写入兼职岗位
			if(hrmEmployee.getHrmPartPost() != null && hrmEmployee.getHrmPartPost().length()>0){
				String hrmPartPostNames = "";
				String pids = hrmEmployee.getHrmPartPost();
				if (pids.charAt(pids.length() - 1) == ',') {
					pids = pids.substring(0, pids.length() - 1);
				}
				List<HrmPost> postlist = hrmPostDao.findByHqlWhere(" and model.primaryKey in ( "+pids+" )");
				for (int i = 0; i < postlist.size(); i++) {
					HrmPost hrmPost = postlist.get(i);
					if (hrmPost.getHrmPostName()!=null&&hrmPost.getHrmPostName().length()>0) {
						if (i== postlist.size()-1) {
							hrmPartPostNames+=hrmPost.getHrmPostName();
						}else{
							hrmPartPostNames+=hrmPost.getHrmPostName()+",";
						}
					}
				}
				hrmEmployee.setHrmPartPostName(hrmPartPostNames);
			}
			
		}
		
		return employeelist;
	}

    /**
     * 统计人员岗位数量
     */
	public int listEmployeeCount_post(HrmEmployee employee) {
		return hrmEmployeeDao.findBySqlCount(HrmEmployeePack.getEmployeeSql(employee));
	}

	/**
	 * 批量设置人员岗位
	 */
	public List<HrmEmployee> getAllEmployee_post_set(String employeepks,
			Pager pager) {
		List<HrmEmployee> employeeList = null;
		String[] empids = employeepks.split(",");
		String empid = "";
		for (String str : empids) {
			if (str != null && str.length() > 0) {
				empid += "'"+str+"'"+",";
			}
		}
		if (empid.length() > 0) {
			empid = empid.substring(0, empid.length() - 1);
		
		   employeeList = hrmEmployeeDao.findBySqlPage("select * from hrm_employee where hrm_employee_id in ( " + empid + " )",HrmEmployee.class, pager);
		}
		
		for (HrmEmployee hrmEmployee : employeeList) {
			//写入部门
			if(hrmEmployee.getHrmEmployeeDepid() != null && hrmEmployee.getHrmEmployeeDepid()>0){
				hrmEmployee.setHrmDepartment(hrmDepartmentDao.getByPK((long)hrmEmployee.getHrmEmployeeDepid()));	
			}
			//写入主岗位
			if(hrmEmployee.getHrmEmployeePostId() != null && hrmEmployee.getHrmEmployeePostId()>0){
				hrmEmployee.setHrmEmployeePost(hrmPostDao.getByPK((long)hrmEmployee.getHrmEmployeePostId()));
			}
			
			//写入兼职岗位
			if(hrmEmployee.getHrmPartPost() != null && hrmEmployee.getHrmPartPost().length()>0){
				String hrmPartPostNames = "";
				String pids = hrmEmployee.getHrmPartPost();
				if (pids.charAt(pids.length() - 1) == ',') {
					pids = pids.substring(0, pids.length() - 1);
				}
				List<HrmPost> postlist = hrmPostDao.findByHqlWhere(" and model.primaryKey in ( "+pids+" )");
				for (int i = 0; i < postlist.size(); i++) {
					HrmPost hrmPost = postlist.get(i);
					if (hrmPost.getHrmPostName()!=null&&hrmPost.getHrmPostName().length()>0) {
						if (i== postlist.size()-1) {
							hrmPartPostNames+=hrmPost.getHrmPostName();
						}else{
							hrmPartPostNames+=hrmPost.getHrmPostName()+",";
						}
					}
				}
				hrmEmployee.setHrmPartPostName(hrmPartPostNames);
			}
			
		}
		return employeeList;
	}

	/**
	 * 根据人员主键统计人员岗位数
	 */
	public int listEmployeeCount_post_set(String employeepks) {
		String[] empids = employeepks.split(",");
		return empids.length;
	}

	/**
	 * 设置人员主岗位和兼职岗位
	 */
	public void setEmployeePostAndPartPost(String employeepks, String postid,
			String partpost) {
		String[] empids = employeepks.split(",");
		for (int i = 0; i < empids.length; i++) {
			HrmEmployee employee = hrmEmployeeDao.getByPK(empids[i]);
			int pid = Integer.parseInt("".equals(postid)?"0":postid);
			employee.setHrmEmployeePostId(pid);
			employee.setHrmPartPost(partpost);
			
			hrmEmployeeDao.save(employee);
			
			//检测人员是否为岗位负责人和调整后的主岗位
			List<HrmPost> postlist= hrmPostDao.findByHqlWhere(" and model.hrmPostMang ='"+empids[i]+"'");
			for (HrmPost hrmPost : postlist) {
				if ((int)hrmPost.getPrimaryKey()!=pid) {
					hrmPost.setHrmPostMang("");
					hrmPostDao.save(hrmPost);
				}
			}
		}
		
	}

	/**
	 * 不分页查询部门信息
	 */
	public List<HrmDepartment> getAllDepNopager(long com) {
		List<HrmDepartment> list = hrmDepartmentDao.findByHqlWhere(" and model.companyId = "+com);
		
		return list;
	}
	
	/**
	 * 获取考勤查询列数组
	 * @param sdate
	 * @param edate
	 * @return
	 */
	private String[] getWorkRecordDay(String sdate,String edate) throws Exception{
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		long oneday = 24*60*60*1000;
		Date sd = sf.parse(sdate);
		Date ed = sf.parse(edate);
		//计算两个日期相差天数
		long l = (ed.getTime() - sd.getTime())/oneday>0?(ed.getTime() - sd.getTime())/oneday+1:1;
		String[] cells = new String[(int)l];
		
		for (int i = 0; i <cells.length; i++) {
			cells[i] = sf.format(DateTimeTool.addDay(sd, i));
		}
		
		return cells;
	}

	/**
	 * 判断同一级别下重名部门
	 * @param name
	 * @param code
	 * @param com
	 * @return
	 */
	public int getDepartmentCountBynameAndCode(String name,String code,long com,long id){
		int count = 0;
		if(id>0){
			count = hrmDepartmentDao.findByHqlWhereCount(" and model.hrmDepName='"+name+"' and model.hrmDepUpid='"+code+"' and model.companyId="+com+" and model.primaryKey<>"+id);
		}else{
			count = hrmDepartmentDao.findByHqlWhereCount(" and model.hrmDepName='"+name+"' and model.hrmDepUpid='"+code+"' and model.companyId="+com);
		}
		return count;
	}
	
	/**
	 * 判断重名工作地区
	 * @param name
	 * @param com
	 * @return
	 */
	public int getworkAreaCountByname(String name,long com,long id){
		int count = 0;
		if(id>0){
			count = hrmWorkareaDao.findByHqlWhereCount(" and model.hrmAreaName='"+name+"' and model.companyId="+com+" and model.primaryKey<>"+id);
		}else{
			count = hrmWorkareaDao.findByHqlWhereCount(" and model.hrmAreaName='"+name+"' and model.companyId="+com);
		}
		return count;
	}
	
	/**
	 * 根据员工编码查询数量，用于excel导入人员时，判断人员编码是否重。
	 * @param code
	 * @param companyId
	 * @return
	 * @author JC
	 * @date   May 26, 2011
	 */
	public int getEmployeeByCodeCount(String code,int companyId){
		return hrmEmployeeDao.findByHqlWhereCount("and model.hrmEmployeeCode = '"+code+"' and model.companyId ="+companyId);
	}
	
	/**
	 * 根据员工姓名查询数量，用于excel导入人员时，判断人员姓名是否重复。
	 * @param name
	 * @param companyId
	 * @return
	 * @author JC
	 * @date   May 26, 2011
	 */
	public int getEmployeeByNameCount(String name,int companyId){
		return hrmEmployeeDao.findByHqlWhereCount("and model.hrmEmployeeName = '"+name+"' and model.companyId ="+companyId);
		
	}
}
