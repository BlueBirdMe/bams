package com.pinhuba.core.iservice;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.pinhuba.common.module.SessionUser;
import com.pinhuba.core.pojo.HrmPost;
import com.pinhuba.core.pojo.SysCompanyInfo;
import com.pinhuba.core.pojo.SysConfig;
import com.pinhuba.core.pojo.SysMethodInfo;
import com.pinhuba.core.pojo.SysRoleBind;
import com.pinhuba.core.pojo.SysUserGroupDetail;
import com.pinhuba.core.pojo.SysUserInfo;
import com.pinhuba.core.pojo.SysUserMethods;

public interface IUserLoginService {
	public List<SysMethodInfo> getAllMethodInfoByLevel();
	
	public SysMethodInfo getMethodInfoByPk(String pk);
	
	public SysCompanyInfo getCompanyInfoByPk(long id);
	
	public List<SysUserInfo> getUserByCompanyPk(int cid);
	
	public SysCompanyInfo getCompanyInfoByCode(String companyCode);
	
	public SysCompanyInfo vaildityCompany(String companyCode);
	
	public SysUserInfo vaildityUserInfo(SysCompanyInfo companyInfo,String userName,String userPwd);
	
	public SysUserInfo vaildityUserInfo(SysCompanyInfo companyInfo,String userName);
	
	public List<SysMethodInfo> getCompanyMethodsByCPk(int cpk);
	
	public SessionUser packageUserInfo(String companyCode,String userName,String projectCode);
	
	public SysUserMethods getSysUserMethodsByUid(long uid);
	
	public Set<String> getUserCompanyMethods(SessionUser user);
	
	public SysUserInfo getUserInfoByCompanyIdAndUserName(String userName,int cid);
	
	public List<HrmPost> getPartPostsByPostIds(String[] postIds);
	
	public List<SysUserGroupDetail> getGroupListByUserId(int userId);
	
	public Set<Integer> getRoleIdsByUser(SessionUser user);
	
	public List<SysRoleBind> getRoleBingListByType(String values, int type);
	
	public Set<String> getMethodIdsByRoleIds(Set<Integer> roleSet,SysUserMethods userMethodDetail);
	
	public Set<String> getUserCompanyMethods(long userId,int companyType);
	
	public SysCompanyInfo getCompanyInfoByUserId(long userId);
	
	public SysConfig getSysconfigByCode(String code);
	
	public List<SysMethodInfo> getSysMethodInfoByCodeUnit(String upCode);
	
	public int getSysMethodInfoByCodeUnitCount(String upCode);
	
	public List<SysMethodInfo> getSysmethodInfoListByIds(Set<String> methodIds);
	
	public List<SysMethodInfo> getSysMethodInfoListByCode(String code,HttpServletRequest request);
	
	public int getSysMethodInfoListByCodeCount(String code,HttpServletRequest request);
}
