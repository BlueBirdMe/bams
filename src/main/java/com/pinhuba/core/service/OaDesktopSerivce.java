package com.pinhuba.core.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pinhuba.common.module.OnlineHrmEmployeeBean;
import com.pinhuba.common.pack.HrmEmployeePack;
import com.pinhuba.core.dao.IHrmEmployeeDao;
import com.pinhuba.core.dao.IOaDesktopSetDao;
import com.pinhuba.core.iservice.IOaDesktopSerivce;
import com.pinhuba.core.pojo.HrmEmployee;
import com.pinhuba.core.pojo.OaDesktopSet;

/**
 * 个人桌面
 * @author peng.ning
 * @date   Mar 31, 2010
 */
@Service
@Transactional
public class OaDesktopSerivce implements IOaDesktopSerivce {
	@Resource
	private IOaDesktopSetDao oaDesktopSetdao;
	@Resource
	private IHrmEmployeeDao hrmEmployeeDao;

	@SuppressWarnings("unchecked")
	public List<OaDesktopSet> getOaDeskTopList(HttpServletRequest request,int companyId,String empId){
		List<OaDesktopSet> list = null;
		list = oaDesktopSetdao.findByHqlWhere(" and model.companyId="+companyId+" and model.oaDesktopEmpid='"+empId+"'");
		return list;
	}
	
	public void saveOaDeskTop(ArrayList<OaDesktopSet> list){
		for (OaDesktopSet oaDesktopSet : list) {
			oaDesktopSetdao.save(oaDesktopSet);
		}
	}
	
	public OaDesktopSet getOaDesktopSetByPk(long pk){
		return oaDesktopSetdao.getByPK(pk);
	}
	

	public OaDesktopSet getOaDeskTopByType(int companyId,String empId,int type){
		OaDesktopSet tmp = null;
		List<OaDesktopSet> list = oaDesktopSetdao.findByHqlWhere(" and model.companyId="+companyId+" and model.oaDesktopEmpid='"+empId+"' and model.oaDesktopType ="+type);
		if (list.size()==1) {
			tmp = list.get(0);
		}
		return tmp;
	}
	
	public List<OnlineHrmEmployeeBean> getOnlineEmployee(HrmEmployee employee){
		List<OnlineHrmEmployeeBean> list = new ArrayList<OnlineHrmEmployeeBean>();
		List<Object[]> objlist = hrmEmployeeDao.findBySqlObjList(HrmEmployeePack.packOnLineEmployeeQuery(employee, employee.getCompanyId())+" order by emp.hrm_employee_id");
		for (Object[] objects : objlist) {
			OnlineHrmEmployeeBean linebean = new OnlineHrmEmployeeBean();
			linebean.setPrimaryKey(objects[0].toString());
			linebean.setEmployeeCode(objects[1].toString());
			linebean.setEmployeeName(objects[2].toString());
			linebean.setEmployeeDeptName(objects[3].toString());
			linebean.setEmployeeSex(Integer.parseInt(objects[4].toString()));
			linebean.setImageId(Integer.parseInt(objects[5]==null?"0":objects[5].toString()));
			list.add(linebean);
		}
		return list;
	}
}
