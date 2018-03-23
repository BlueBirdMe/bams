package com.pinhuba.core.service;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.MembershipEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pinhuba.common.pack.OaSysHqlPack;
import com.pinhuba.common.pages.Pager;
import com.pinhuba.core.dao.IHrmDepartmentDao;
import com.pinhuba.core.dao.IHrmEmployeeDao;
import com.pinhuba.core.dao.ISysProcessConfigDao;
import com.pinhuba.core.dao.ISysProcessTypeDao;
import com.pinhuba.core.dao.ISysUserInfoDao;
import com.pinhuba.core.iservice.IApproveProcessService;
import com.pinhuba.core.pojo.SysProcessConfig;
import com.pinhuba.core.pojo.SysProcessType;
import com.pinhuba.core.pojo.SysUserInfo;

/**
 * 审批流程业务类
 * @author peng.ning
 */
@Service
@Transactional
public class ApproveProcessService implements IApproveProcessService {
	@Resource
	private IHrmEmployeeDao hrmEmployeeDao;
	@Resource
	private IHrmDepartmentDao hrmDepartmentDao;
	@Resource
	private ISysUserInfoDao sysUserInfoDao;
	@Resource
	private ISysProcessConfigDao sysProcessConfigDao;
	@Resource
	private ISysProcessTypeDao sysProcessTypeDao;

	public List<MembershipEntity> getMembership(String empId) {
		List<MembershipEntity> entityList = new ArrayList<MembershipEntity>();
		String sql = "select USER_ID_,GROUP_ID_ from ACT_ID_MEMBERSHIP where USER_ID_='"+empId+"'";
		List<Object[]> list = sysUserInfoDao.findBySqlObjList(sql);
		
		for (Object[] object : list) {
			MembershipEntity entity = new MembershipEntity();
			entity.setUserId(object[0].toString());
			entity.setGroupId(object[1].toString());
			entityList.add(entity);
		}
		
		return entityList;
	}
	
	public int getSysUserInfoListCount(SysUserInfo userinfo) {
		return sysUserInfoDao.findBySqlCount(OaSysHqlPack.getSysUserInfoSql(userinfo));
	}

	public List<SysUserInfo> getSysUserInfoListByPager(SysUserInfo userinfo, Pager pager) {
		List<SysUserInfo> userinfoList = sysUserInfoDao.findBySqlPage(OaSysHqlPack.getSysUserInfoSql(userinfo) + " order by sysuser.user_action asc", SysUserInfo.class, pager);
		for (SysUserInfo sysUserInfo : userinfoList) {
			sysUserInfo.setEmployee(hrmEmployeeDao.getByPK(sysUserInfo.getHrmEmployeeId()));
			sysUserInfo.getEmployee().setHrmDepartment(hrmDepartmentDao.getByPK(sysUserInfo.getEmployee().getHrmEmployeeDepid().longValue()));
		}
		return userinfoList;
	}
	
	public List<Group> getGroupListByUserId(String hrmEmployeeId){
		String sql = "select NAME_ from ACT_ID_GROUP where ID_ in" +
				" ( select GROUP_ID_ from ACT_ID_MEMBERSHIP where USER_ID_ = '"+hrmEmployeeId+"')";
		
		List<Object[]> list = sysUserInfoDao.findBySqlObjList(sql);
		
		List<Group> groupList = new ArrayList<Group>();
		
		for (Object[] object : list) {
			Group group = new GroupEntity();
			group.setName(object[0].toString());
			groupList.add(group);
		}
		return groupList;
	}
	
	public SysProcessConfig saveSysProcessConfig(SysProcessConfig sysProcessConfig){
        SysProcessConfig temp = (SysProcessConfig)sysProcessConfigDao.save(sysProcessConfig);
        return temp;
    }
	
	public SysProcessConfig getSysProcessConfigByPk(String pk){
        SysProcessConfig sysProcessConfig = (SysProcessConfig)sysProcessConfigDao.getByPK(pk);
        return sysProcessConfig;
    }
	
	public List<SysProcessConfig> listConfigByProcessTypeId(String pk){
    	List<SysProcessConfig> list = sysProcessConfigDao.findByHqlWhere(" and model.processType.primaryKey = '"+pk+"'");
    	return list;
	}
	
	public void deleteSysProcessConfigByPk(String pk){
		SysProcessConfig config = sysProcessConfigDao.getByPK(pk);
		if(config != null)
			sysProcessConfigDao.remove(config);
	}

    public int listSysProcessTypeCount(SysProcessType sysProcessType){
        int count = sysProcessTypeDao.findByHqlWhereCount(packSysProcessTypeQuery(sysProcessType));
        return count;
    }
    

    public List<SysProcessType> listSysProcessType(SysProcessType sysProcessType, Pager pager){
        List<SysProcessType> list = sysProcessTypeDao.findByHqlWherePage(packSysProcessTypeQuery(sysProcessType), pager);
        return list;
    }

	public List<SysProcessType> listSysProcessType(SysProcessType sysProcessType){
        List<SysProcessType> list = sysProcessTypeDao.findByHqlWhere(packSysProcessTypeQuery(sysProcessType));
        return list;
    }

    public SysProcessType saveSysProcessType(SysProcessType sysProcessType){
        SysProcessType temp = (SysProcessType)sysProcessTypeDao.save(sysProcessType);
        return temp;
    }

    public SysProcessType getSysProcessTypeByPk(String pk){
        SysProcessType sysProcessType = (SysProcessType)sysProcessTypeDao.getByPK(pk);
        return sysProcessType;
    }

    public void deleteSysProcessTypeByPks(String[] pks){
        for (String pk : pks) {
            SysProcessType sysProcessType = sysProcessTypeDao.getByPK(pk);
            sysProcessTypeDao.remove(sysProcessType);
        }
    }
    
    private String packSysProcessTypeQuery(SysProcessType sysProcessType) {
    	StringBuffer result = new StringBuffer();
        result.append(" order by model.priority asc");
        return result.toString();
	}
}
