package com.pinhuba.core.iservice;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.pinhuba.common.pages.Pager;
import com.pinhuba.core.pojo.HrmDepartment;
import com.pinhuba.core.pojo.HrmEmployee;
import com.pinhuba.core.pojo.OaNetdiskConfig;
import com.pinhuba.core.pojo.OaNetdiskShare;

public interface INetdiskService {
	public List<OaNetdiskShare> getSharePathByCompanyId(OaNetdiskShare oaNetdiskShare,String empid,String deptid);
	public OaNetdiskShare getOaNetdiskShareByPk(long pk) ;
	public OaNetdiskShare saveOaNetdiskShare(OaNetdiskShare oaNetdiskShare) ;
	public void deleteShareByHrmEmpIDandPath(OaNetdiskShare oaNetdiskShare) ;
	public OaNetdiskShare getShareByHrmEmpIDandPath(OaNetdiskShare oaNetdiskShare) ;
	//获取人
	public void getHrmEmployee(OaNetdiskConfig OaNetdisk,HttpServletRequest request);
	//获取记录条数
	public int listOaNetdiskCount(OaNetdiskConfig OaNetdisk) ;
	//获得个人
	public HrmEmployee gethrmEmployeebyid(String id);
	
	public void saveOaNetdiskShareWhenRenamePath(OaNetdiskShare oaNetdiskShare, String newFolderPath, String newFolderName) ;

	//保存磁盘
	public void SeveOaNetdisk(OaNetdiskConfig OaNetdisk);
	//读取磁盘管理
	public List<OaNetdiskConfig> listOaNetdisk(OaNetdiskConfig OaNetdisk,Pager pager);
	//获取部门
	public HrmDepartment getHrmDeparTmentBy(long id);
	//获取磁盘ID
	public OaNetdiskConfig getOaNetdisk(long id);
	
	public OaNetdiskConfig getOaNetdiskConfigByHrmEmpId(OaNetdiskConfig OaNetdisk);
}
 