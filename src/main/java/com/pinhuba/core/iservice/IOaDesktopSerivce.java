package com.pinhuba.core.iservice;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.pinhuba.common.module.OnlineHrmEmployeeBean;
import com.pinhuba.core.pojo.HrmEmployee;
import com.pinhuba.core.pojo.OaDesktopSet;

/**
 * 个人桌面
 * @author peng.ning
 * @date   Mar 31, 2010
 */
public interface IOaDesktopSerivce {
	public List<OaDesktopSet> getOaDeskTopList(HttpServletRequest reqeust,int companyId,String empId);
	
	public void saveOaDeskTop(ArrayList<OaDesktopSet> list);
	
	public OaDesktopSet getOaDeskTopByType(int companyId,String empId,int type);
	
	public List<OnlineHrmEmployeeBean> getOnlineEmployee(HrmEmployee employee);
	
	public OaDesktopSet getOaDesktopSetByPk(long pk);
	
}
