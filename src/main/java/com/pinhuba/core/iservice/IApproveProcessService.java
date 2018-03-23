package com.pinhuba.core.iservice;

import java.util.List;
import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.persistence.entity.MembershipEntity;
import com.pinhuba.common.pages.Pager;
import com.pinhuba.core.pojo.SysProcessConfig;
import com.pinhuba.core.pojo.SysProcessType;
import com.pinhuba.core.pojo.SysUserInfo;

public interface IApproveProcessService {

	public List<MembershipEntity> getMembership(String empId);

	public int getSysUserInfoListCount(SysUserInfo userinfo);

	public List<SysUserInfo> getSysUserInfoListByPager(SysUserInfo userinfo,
			Pager pager);

	public List<Group> getGroupListByUserId(String hrmEmployeeId);

	public SysProcessConfig getSysProcessConfigByPk(String pk);
	public SysProcessConfig saveSysProcessConfig(SysProcessConfig sysProcessConfig);
	public List<SysProcessConfig> listConfigByProcessTypeId(String pk);
	public void deleteSysProcessConfigByPk(String pk);
	
	public int listSysProcessTypeCount(SysProcessType sysProcessType);
    public List<SysProcessType> listSysProcessType(SysProcessType sysProcessType, Pager pager);
    public List<SysProcessType> listSysProcessType(SysProcessType sysProcessType);
    public SysProcessType saveSysProcessType(SysProcessType sysProcessType);
    public SysProcessType getSysProcessTypeByPk(String pk);
    public void deleteSysProcessTypeByPks(String[] pks);

	

}
