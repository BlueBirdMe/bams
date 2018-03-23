package com.pinhuba.core.service;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pinhuba.common.pack.HqlPack;
import com.pinhuba.common.pages.Pager;
import com.pinhuba.common.util.UtilTool;
import com.pinhuba.common.util.UtilWork;
import com.pinhuba.core.dao.IHrmDepartmentDao;
import com.pinhuba.core.dao.IHrmEmployeeDao;
import com.pinhuba.core.dao.IOaNetdiskConfigDao;
import com.pinhuba.core.dao.IOaNetdiskShareDao;
import com.pinhuba.core.iservice.INetdiskService;
import com.pinhuba.core.pojo.HrmDepartment;
import com.pinhuba.core.pojo.HrmEmployee;
import com.pinhuba.core.pojo.OaNetdiskConfig;
import com.pinhuba.core.pojo.OaNetdiskShare;
@Service
@Transactional
public class NetdiskService implements INetdiskService {
	@Resource
	private IOaNetdiskShareDao oaNetdiskShareDao;
	@Resource
	private IOaNetdiskConfigDao oaNetdiskConfigDao;
	@Resource
	private IHrmEmployeeDao hrmEmployeeDao;
	@Resource
	private IHrmDepartmentDao hrmDepartmentDao;
	
	/**
	 * 得到共享信息, by path and companyId
	 */
	public List<OaNetdiskShare> getSharePathByCompanyId(OaNetdiskShare oaNetdiskShare,String empid,String deptid) {
		List<OaNetdiskShare> list = oaNetdiskShareDao.findByHqlWhere(HqlPack.packNetdiskShare(oaNetdiskShare,empid,deptid));
		return list;
	}
	
	/**
	 * 得到共享信息, by pk
	 * 
	 */
	public OaNetdiskShare getOaNetdiskShareByPk(long pk) {
		OaNetdiskShare tmp = oaNetdiskShareDao.getByPK(pk);
		return tmp;
	}
	
	/**
	 * 根据人员ID和路径查询共享信息
	 */
	public OaNetdiskShare getShareByHrmEmpIDandPath(OaNetdiskShare oaNetdiskShare) {
		Pager page = new Pager();
		page.setStartRow(0);
		page.setPageSize(1);
		page.setPageSize(1);
		List<OaNetdiskShare> list = oaNetdiskShareDao.findByHqlWherePage(HqlPack.packNetdiskShare(oaNetdiskShare,"",""),page);
		if ( list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 保存磁盘共享
	 */
	public OaNetdiskShare saveOaNetdiskShare(OaNetdiskShare oaNetdiskShare) {
		return (OaNetdiskShare) oaNetdiskShareDao.save(oaNetdiskShare);
	}
	
	/**
	 * 删除共享, by empID and path
	 */
	public void deleteShareByHrmEmpIDandPath(OaNetdiskShare oaNetdiskShare) {
		List<OaNetdiskShare> list = oaNetdiskShareDao.findByHqlWhere(HqlPack.packNetdiskShareByFolderPath(oaNetdiskShare));
		for(OaNetdiskShare temp : list) {
			oaNetdiskShareDao.remove(temp);
		}
	}
	/**
	 * 重命名时, 修改共享文件夹名
	 */
	public void saveOaNetdiskShareWhenRenamePath(OaNetdiskShare oaNetdiskShare, String newFolderPath, String newFolderName) {
		List<OaNetdiskShare> list = oaNetdiskShareDao.findByHqlWhere(HqlPack.packNetdiskShareByFolderPath(oaNetdiskShare));
		for(OaNetdiskShare temp : list) {
			oaNetdiskShareDao.remove(temp);
		}
	}
	/**
	 * 磁盘配置的数量
	 */
	public int listOaNetdiskCount(OaNetdiskConfig oaNetdisk) {
		int count = oaNetdiskConfigDao.findBySqlCount(HqlPack.packNetdisksQuery(oaNetdisk));
		return count;
	   
	}

	/**查看公司磁盘表没有的人就添加进去
	 * 
	 * (non-Javadoc)
	 * @see com.pinhuba.core.iservice.INetdiskService#getHrmEmployee(com.pinhuba.core.pojo.OaNetdiskConfig, javax.servlet.http.HttpServletRequest)
	 */
	public void getHrmEmployee(OaNetdiskConfig oaNetdiskConfig, HttpServletRequest request) {
		String time = UtilWork.getNowTime();
		List<Object[]> list = hrmEmployeeDao.findBySqlObjList(HqlPack.packOaNetdiskQuery(oaNetdiskConfig));
		for (Object[] empid : list) {
			oaNetdiskConfig.setUsedSpace(0.0);
			oaNetdiskConfig.setTotalSpace(Integer.parseInt(UtilTool.getSysParamByIndex(request, "erp.Net.Disk")));
			oaNetdiskConfig.setLastmodiId(UtilTool.getEmployeeId(request));
			oaNetdiskConfig.setLastmodiDate(time);
			oaNetdiskConfig.setRecordId(UtilTool.getEmployeeId(request));
			oaNetdiskConfig.setRecordDate(time);
			oaNetdiskConfig.setHrmEmployeeId((String) empid[0]);
			SeveOaNetdisk(oaNetdiskConfig);
		}

	}

	// 获取人员
	public HrmEmployee gethrmEmployeebyid(String id) {
		return hrmEmployeeDao.getByPK(id);

	}
   
	// 获取部门
	public HrmDepartment getHrmDeparTmentBy(long id) {
		return hrmDepartmentDao.getByPK(id);

	}

	// 保存磁盘设置大小
	public void SeveOaNetdisk(OaNetdiskConfig oaNetdisk) {
		oaNetdiskConfigDao.save(oaNetdisk);

	}
	
	public OaNetdiskConfig getOaNetdiskConfigByHrmEmpId(OaNetdiskConfig oaNetdisk) {
		Pager pager = new Pager();
		pager.setStartRow(0);
		pager.setTotalPages(1);
		pager.setPageSize(1);
		List<OaNetdiskConfig> list = oaNetdiskConfigDao.findBySqlPage(HqlPack.packNetdiskConfigQuery(oaNetdisk), OaNetdiskConfig.class, pager);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	// 获得磁盘 数据
	public List<OaNetdiskConfig> listOaNetdisk(OaNetdiskConfig oaNetdisk, Pager pager) {
		return oaNetdiskConfigDao.findBySqlPage(HqlPack.packNetdisksQuery(oaNetdisk)+" order by employee.hrm_employee_code", OaNetdiskConfig.class, pager);

	}

	// 获取磁盘ID
	public OaNetdiskConfig getOaNetdisk(long id) {
		return oaNetdiskConfigDao.getByPK(id);

	}
}
