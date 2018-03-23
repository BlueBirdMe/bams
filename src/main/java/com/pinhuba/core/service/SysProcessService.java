package com.pinhuba.core.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.sql.DataSource;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.pinhuba.common.module.OnlineHrmEmployeeBean;
import com.pinhuba.common.module.UserMethodsInfo;
import com.pinhuba.common.pack.HrmEmployeePack;
import com.pinhuba.common.pack.OaSysHqlPack;
import com.pinhuba.common.pack.SystemPack;
import com.pinhuba.common.pages.Pager;
import com.pinhuba.common.util.EnumUtil;
import com.pinhuba.common.util.StringTool;
import com.pinhuba.common.util.UtilWork;
import com.pinhuba.common.util.file.FileTool;
import com.pinhuba.common.util.file.properties.SystemConfig;
import com.pinhuba.common.util.security.Base64;
import com.pinhuba.core.dao.IHrmDepartmentDao;
import com.pinhuba.core.dao.IHrmEmployeeDao;
import com.pinhuba.core.dao.IHrmPostDao;
import com.pinhuba.core.dao.ISysAttachmentInfoDao;
import com.pinhuba.core.dao.ISysColumnControlDao;
import com.pinhuba.core.dao.ISysCompanyInfoDao;
import com.pinhuba.core.dao.ISysCompanyMethodsDao;
import com.pinhuba.core.dao.ISysConfigDao;
import com.pinhuba.core.dao.ISysExceptionDao;
import com.pinhuba.core.dao.ISysHelpDao;
import com.pinhuba.core.dao.ISysImageInfoDao;
import com.pinhuba.core.dao.ISysLibraryInfoDao;
import com.pinhuba.core.dao.ISysLibraryStandardDao;
import com.pinhuba.core.dao.ISysLogDao;
import com.pinhuba.core.dao.ISysLogRuntimeDao;
import com.pinhuba.core.dao.ISysMethodBtnDao;
import com.pinhuba.core.dao.ISysMethodHelpDao;
import com.pinhuba.core.dao.ISysMethodInfoDao;
import com.pinhuba.core.dao.ISysMethodShortcutDao;
import com.pinhuba.core.dao.ISysMsgDao;
import com.pinhuba.core.dao.ISysParamDao;
import com.pinhuba.core.dao.ISysRoleBindDao;
import com.pinhuba.core.dao.ISysRoleBtnDao;
import com.pinhuba.core.dao.ISysRoleDao;
import com.pinhuba.core.dao.ISysRoleDetailDao;
import com.pinhuba.core.dao.ISysUserBtnsDao;
import com.pinhuba.core.dao.ISysUserGroupDao;
import com.pinhuba.core.dao.ISysUserGroupDetailDao;
import com.pinhuba.core.dao.ISysUserInfoDao;
import com.pinhuba.core.dao.ISysUserMethodsDao;
import com.pinhuba.core.iservice.ISysProcessService;
import com.pinhuba.core.pojo.HrmDepartment;
import com.pinhuba.core.pojo.HrmEmployee;
import com.pinhuba.core.pojo.HrmPost;
import com.pinhuba.core.pojo.SysAttachmentInfo;
import com.pinhuba.core.pojo.SysColumnControl;
import com.pinhuba.core.pojo.SysCompanyInfo;
import com.pinhuba.core.pojo.SysCompanyMethods;
import com.pinhuba.core.pojo.SysConfig;
import com.pinhuba.core.pojo.SysException;
import com.pinhuba.core.pojo.SysHelp;
import com.pinhuba.core.pojo.SysImageInfo;
import com.pinhuba.core.pojo.SysLibraryInfo;
import com.pinhuba.core.pojo.SysLibraryStandard;
import com.pinhuba.core.pojo.SysLog;
import com.pinhuba.core.pojo.SysLogRuntime;
import com.pinhuba.core.pojo.SysMethodBtn;
import com.pinhuba.core.pojo.SysMethodHelp;
import com.pinhuba.core.pojo.SysMethodInfo;
import com.pinhuba.core.pojo.SysMethodShortcut;
import com.pinhuba.core.pojo.SysMsg;
import com.pinhuba.core.pojo.SysParam;
import com.pinhuba.core.pojo.SysRole;
import com.pinhuba.core.pojo.SysRoleBind;
import com.pinhuba.core.pojo.SysRoleBtn;
import com.pinhuba.core.pojo.SysRoleDetail;
import com.pinhuba.core.pojo.SysUserBtns;
import com.pinhuba.core.pojo.SysUserGroup;
import com.pinhuba.core.pojo.SysUserGroupDetail;
import com.pinhuba.core.pojo.SysUserInfo;
import com.pinhuba.core.pojo.SysUserMethods;

/**
 * 系统级服务
 * @author peng.ning
 */
@Service
@Transactional
public class SysProcessService implements ISysProcessService {
	private Logger log = Logger.getLogger(this.getClass());
	@Resource
	private ISysLogDao sysLogDao;
	@Resource
	private ISysExceptionDao exceptionDao;
	@Resource
	private ISysHelpDao sysHelpDao;
	@Resource
	private ISysMsgDao sysMsgDao;
	@Resource
	private ISysColumnControlDao sysColumnControlDao;
	@Resource
	private ISysConfigDao sysConfigDao;
	@Resource
	private ISysMethodInfoDao sysMethodInfoDao;
	@Resource
	private ISysLibraryInfoDao sysLibraryInfoDao;
	@Resource
	private ISysLibraryStandardDao sysLibraryStandardDao;
	@Resource
	private ISysAttachmentInfoDao sysAttachmentInfoDao;
	@Resource
	private ISysImageInfoDao sysImageInfoDao;
	@Resource
	private ISysUserInfoDao sysUserInfoDao;
	@Resource
	private ISysUserGroupDao sysUserGroupDao;
	@Resource
	private ISysUserGroupDetailDao sysUserGroupDetailDao;
	@Resource
	private ISysUserMethodsDao sysUserMethodsDao;
	@Resource
	private ISysUserBtnsDao sysUserBtnsDao;
	@Resource
	private IHrmEmployeeDao hrmEmployeeDao;
	@Resource
	private IHrmDepartmentDao hrmDeptDao;
	@Resource
	private IHrmPostDao hrmPostDao;
	@Resource
	private ISysRoleDao sysRoleDao;
	@Resource
	private ISysRoleDetailDao sysRoleDetailDao;
	@Resource
	private ISysRoleBtnDao sysRoleBtnDao;
	@Resource
	private ISysRoleBindDao sysRoleBindDao;
	@Resource
	private ISysCompanyInfoDao sysCompanyInfoDao;
	@Resource
	private ISysCompanyMethodsDao sysCompanyMethodsDao;
	@Resource
	private ISysParamDao sysParamDao;
	@Resource
	private ISysMethodHelpDao sysMethodHelpDao;
	@Resource
	private ISysMethodBtnDao sysMethodBtnDao;
	@Resource
	private ISysLogRuntimeDao sysLogRuntimeDao;
	@Resource
    private IdentityService identityService;
	@Resource
    private ISysMethodShortcutDao sysMethodShortcutDao;

	public SysLog saveSysLog(SysLog sysLog) {
		return (SysLog) sysLogDao.save(sysLog);
	}

	public SysException saveSysException(SysException sysException) {
		return (SysException) exceptionDao.save(sysException);
	}

	public SysColumnControl getSysColumnControl(long pk) {
		return sysColumnControlDao.getByPK(pk);
	}

	// 前台显示列
	public List<SysColumnControl> listAllSysColumnControl(SysColumnControl sysColumnControl) {
		return sysColumnControlDao.findByHqlWhere(SystemPack.packSysColumn(sysColumnControl));
	}

	public List<SysColumnControl> listAllSysColumnControlPager(SysColumnControl sysColumnControl, Pager pager) {
		return sysColumnControlDao.findByHqlWherePage(SystemPack.packSysColumnControlQuery(sysColumnControl), pager);
	}

	public int listAllSysColumnControlCount(SysColumnControl sysColumnControl) {
		int count = sysColumnControlDao.findByHqlWhereCount(SystemPack.packSysColumnControlQuery(sysColumnControl));
		return count;
	}

	public SysColumnControl saveSysColumnControl(SysColumnControl sysColumnControl) {
		return (SysColumnControl) sysColumnControlDao.save(sysColumnControl);
	}
	
	public List<SysMethodInfo> listAllSysMethodInfoPager(SysMethodInfo info,Pager pager) {
		return sysMethodInfoDao.findByHqlWherePage(SystemPack.packSysMethodInfo(info)+" order by model.primaryKey", pager);
	}

	public int listAllSysMethodInfoCount(SysMethodInfo sysMethodInfo) {
		return sysMethodInfoDao.findByHqlWhereCount(SystemPack.packSysMethodInfo(sysMethodInfo));
	}

	public SysConfig getSysconfigByCode(String code) {
		List<SysConfig> list = sysConfigDao.findByHqlWhere(" and methodId ='" + code + "'");
		SysConfig sysconfig = null;
		if (list.size() > 0) {
			sysconfig = list.get(0);
		}
		return sysconfig;
	}

	public List<SysMethodInfo> getSysMethodInfoList(ArrayList<String> ids) {
		StringBuffer sql = new StringBuffer(" and model.primaryKey in ( ");
		String tmp = "";
		for (String id : ids) {
			tmp += "'" + id + "',";
		}
		sql.append(StringTool.getLeftString(tmp, 1));
		sql.append(" ) order by model.methodLevel ");
		List<SysMethodInfo> list = sysMethodInfoDao.findByHqlWhere(sql.toString());
		return list;
	}

	/**
	 * 保存附件
	 * 
	 * @param fileNames
	 * @param empid
	 * @param companyid
	 * @return
	 */
	public List<SysAttachmentInfo> saveAttachmentInfo(String fileNames, String empid, int companyid) {
		List<SysAttachmentInfo> list = new ArrayList<SysAttachmentInfo>();
		if (fileNames == null || fileNames.length() == 0) {
			return list;
		} else {
			String files[] = fileNames.split(",");
			for (String str : files) {
				String[] tmps = str.split("\\|");
				SysAttachmentInfo attach = new SysAttachmentInfo();
				attach.setAttachmentRename(handlerName(tmps[1]));
				attach.setAttachmentName(tmps[0]);
				attach.setAttachmentFilename(tmps[1]);
				attach.setRecordId(empid);
				attach.setLastmodiId(empid);
				attach.setRecordDate(UtilWork.getNowTime());
				attach.setLastmodiDate(UtilWork.getNowTime());
				attach.setCompanyId(companyid);
				SysAttachmentInfo info = (SysAttachmentInfo) sysAttachmentInfoDao.save(attach);
				list.add(info);
			}
		}
		return list;
	}
	
	/**
	 * 根据文件路径名，获得重命名之后的文件名 2014-03-13 JC
	 * @param rename
	 * @return
	 */
	private String handlerName(String filePath) {
		String rename = "";
		try {
			if (filePath != null && filePath.trim().length() > 0 && FileTool.getIsFile(Base64.getStringFromBase64(filePath))) {
				filePath = Base64.getStringFromBase64(filePath);
				rename = filePath.substring(filePath.lastIndexOf('/') == -1 ? filePath.lastIndexOf('\\')+1 : filePath.lastIndexOf('/')+1,filePath.length());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rename;
	}

	/**
	 * 保存图片
	 * 
	 * @param fileNames
	 * @param empid
	 * @param companyid
	 * @return
	 */
	public List<SysImageInfo> saveImageInfo(String fileNames, String empid, int companyid) {
		List<SysImageInfo> list = new ArrayList<SysImageInfo>();
		if (fileNames == null || fileNames.length() == 0) {
			return list;
		} else {
			String files[] = fileNames.split(",");
			for (String str : files) {
				String[] tmps = str.split("\\|");
				SysImageInfo image = new SysImageInfo();
				image.setImageInfoRename(handlerName(tmps[1]));
				image.setImageInfoName(tmps[0]);
				image.setImageInfoFilename(tmps[1]);
				image.setImageInfoFilePath(Base64.getStringFromBase64(tmps[1]));
				image.setRecordId(empid);
				image.setLastmodiId(empid);
				image.setRecordDate(UtilWork.getNowTime());
				image.setLastmodiDate(UtilWork.getNowTime());
				image.setCompanyId(companyid);
				SysImageInfo info = (SysImageInfo) sysImageInfoDao.save(image);
				list.add(info);
			}
		}
		return list;
	}

	/**
	 * 根据id获取附件对象集合
	 * 
	 * @param ids
	 * @return
	 */
	public List<SysAttachmentInfo> getAttachmentInfoListByIds(String ids) {
		List<SysAttachmentInfo> list = new ArrayList<SysAttachmentInfo>();
		if (ids != null && ids.length() > 0) {
			if (ids.charAt(ids.length() - 1) == ',') {
				ids = ids.substring(0, ids.length() - 1);
			}
			list = sysAttachmentInfoDao.findByHqlWhere(" and model.primaryKey in ( " + ids + " )");
		}
		return list;
	}
	
	public SysAttachmentInfo getAttachmentInfoByPk(long id){
		return sysAttachmentInfoDao.getByPK(id);
	}

	/**
	 * 根据id删除附件
	 * 
	 * @param ids
	 * @param 是否移除附件
	 */
	public void deleteAttachmentInfoListByIds(String ids, boolean bl) {
		List<SysAttachmentInfo> list = this.getAttachmentInfoListByIds(ids);
		for (SysAttachmentInfo sysAttachmentInfo : list) {
			sysAttachmentInfoDao.remove(sysAttachmentInfo);
			// 移除文件
			if (bl) {
				FileTool.deleteFiles(new String[] { Base64.getStringFromBase64(sysAttachmentInfo.getAttachmentFilename()) });
			}
		}
	}

	/**
	 * 根据id获取图片对象集合
	 * 
	 * @param ids
	 * @return
	 */
	public List<SysImageInfo> getImageInfoListByIds(String ids) {
		List<SysImageInfo> list = new ArrayList<SysImageInfo>();
		if (ids != null && ids.length() > 0) {
			if (ids.charAt(ids.length() - 1) == ',') {
				ids = ids.substring(0, ids.length() - 1);
			}
			list = sysImageInfoDao.findByHqlWhere(" and model.primaryKey in ( " + ids + " )");
		}
		return list;
	}

	/**
	 * 根据id删除附件
	 * 
	 * @param ids
	 */
	public void deleteImageInfoListByIds(String ids, boolean bl) {
		List<SysImageInfo> list = this.getImageInfoListByIds(ids);
		for (SysImageInfo sysImageinfo : list) {
			sysImageInfoDao.remove(sysImageinfo);
			if (bl) {
				// 移除文件
				FileTool.deleteFiles(new String[] { Base64.getStringFromBase64(sysImageinfo.getImageInfoFilename()) });
			}
		}
	}

	public SysLog saveMethodLog(SysLog syslog) {
		return (SysLog) sysLogDao.save(syslog);
	}

	/**
	 * 查询上级节点为code的功能菜单
	 */
	public List<SysMethodInfo> getMethodInfoListByUpCode(String code) {
		List<SysMethodInfo> methodList = sysMethodInfoDao.findByHqlWhere(" and model.levelUnit ='" + code + "' order by model.methodNo asc");
		return methodList;
	}

	/**
	 * 统计以code为上级节点的功能菜单
	 */
	public int getMethodInfoListByUpCodeCount(String code) {
		return sysMethodInfoDao.findByHqlWhereCount(" and model.levelUnit ='" + code + "'");
	}

	public List<SysLibraryInfo> getSysLibraryInfoByInfo(SysLibraryInfo libraryInfo) {
		List<SysLibraryInfo> libraryList = sysLibraryInfoDao.findByHqlWhere(SystemPack.packSysLibraryInfo(libraryInfo));
		return libraryList;
	}

	public List<SysLibraryInfo> getSysLibraryInfoByInfoPager(SysLibraryInfo libraryInfo, Pager pager) {
		List<SysLibraryInfo> libraryList = sysLibraryInfoDao.findByHqlWherePage(SystemPack.packSysLibraryInfo(libraryInfo), pager);
		return libraryList;
	}

	public int getSysLibraryInfoCountByInfo(SysLibraryInfo libraryInfo) {
		int count = sysLibraryInfoDao.findByHqlWhereCount(SystemPack.packSysLibraryInfo(libraryInfo));
		return count;
	}

	public SysLibraryInfo getSysLibraryInfoByCodAndCompanyId(SysLibraryInfo libraryInfo) {
		SysLibraryInfo sysLibrary = null;

		List<SysLibraryInfo> list = sysLibraryInfoDao.findByHqlWhere(SystemPack.packSysLibraryInfo(libraryInfo));
		if (list.size() > 0) {
			sysLibrary = list.get(0);
		}
		return sysLibrary;
	}

	public SysLibraryInfo saveSysLibraryInfo(SysLibraryInfo libraryInfo) {
		SysLibraryInfo info = (SysLibraryInfo) sysLibraryInfoDao.save(libraryInfo);
		return info;
	}

	public List<SysLibraryInfo> getSysLibraryInfoByCode(String code) {
		List<SysLibraryInfo> list = sysLibraryInfoDao.findByHqlWhere(" and model.libraryInfoUpcode = '" + code + "' and model.libraryInfoIsvalid ="
				+ EnumUtil.SYS_ISACTION.Vaild.value + "  order by model.libraryInfoCode");
		return list;
	}

	public SysLibraryInfo getSysLibraryInfoByCodeAndCompanyId(String code) {
		SysLibraryInfo info = null;
		List<SysLibraryInfo> list = sysLibraryInfoDao.findByHqlWhere(" and model.libraryInfoCode = '" + code + "'");
		if (list.size() > 0) {
			info = list.get(0);
		}
		return info;
	}

	public SysLibraryInfo getSysLibraryInfoByPk(long pk) {
		return sysLibraryInfoDao.getByPK(pk);
	}

	
	public String getSysLibraryNamesByPks(String[] pks){
		String libraryNames = "";
		for (String l : pks) {
			SysLibraryInfo lib = sysLibraryInfoDao.getByPK(Long.valueOf(l));
			libraryNames += lib.getLibraryInfoName() + ",";
		}
		return StringTool.getLeftString(libraryNames, 1);
	}
	
	/**
	 * 根据名字得到相应的信息
	 */
	public List<SysLibraryInfo> getSysLibraryInfoByName(String name){
		List<SysLibraryInfo> list = sysLibraryInfoDao.findByHqlWhere(" and model.libraryInfoName = '" + name + "'");
		return list;
	}
	
	public SysLibraryInfo setLibraryInfoStatusByPk(long pk, int status, int type) {
		SysLibraryInfo library = sysLibraryInfoDao.getByPK(pk);
		if (type == 1) {
			library.setLibraryInfoIsedit(status);
		} else {
			library.setLibraryInfoIsvalid(status);
		}
		return (SysLibraryInfo) sysLibraryInfoDao.save(library);
	}

	public void setLibraryInfoBatchByPks(long[] pks, int type) {
		for (long l : pks) {
			SysLibraryInfo library = sysLibraryInfoDao.getByPK(l);
			int status;
			if (type == 1) {
				if (library.getLibraryInfoIsedit() == EnumUtil.SYS_ISEDIT.EDIT.value) {
					status = EnumUtil.SYS_ISEDIT.No_EDIT.value;
				} else {
					status = EnumUtil.SYS_ISEDIT.EDIT.value;
				}
				library.setLibraryInfoIsedit(status);
			} else {
				if (library.getLibraryInfoIsvalid() == EnumUtil.SYS_ISACTION.Vaild.value) {
					status = EnumUtil.SYS_ISACTION.No_Vaild.value;
				} else {
					status = EnumUtil.SYS_ISACTION.Vaild.value;
				}
				library.setLibraryInfoIsvalid(status);
			}
			sysLibraryInfoDao.save(library);
		}
	}

	public void deleteLibraryInfoBatchByPks(long[] pks) {
		for (long l : pks) {
			SysLibraryInfo library = sysLibraryInfoDao.getByPK(l);
			sysLibraryInfoDao.remove(library);
		}
	}

	/**
	 * 根据上级编码及表名计算下级编号
	 */
	public String getCode(String upcode, String table, String colname, String upcol, int companyId) {
		String code = "";
		if (upcode == null || upcode.length() == 0) {
			code = "01";
		} else {
			String sql = "select max(" + colname + ") from " + table + 
						" where " + upcol + " = '" + upcode.trim() + "'";
			
			if(companyId != 0){
				sql += " and company_id = " + companyId;
			}
			
			List<String> list = sysLogDao.findBySql(sql);
			String tmp = list.get(0);
			
			if ("00".equals(upcode) || "-1".equals(upcode)) {
				if (tmp == null) {
					code = "01";
				} else {
					int inttmp = Integer.parseInt(tmp) + 1;
					if (inttmp < 10) {
						code = "0" + inttmp;
					} else if (inttmp >99) {
						code = String.valueOf(inttmp-1);
					}else {
						code = String.valueOf(inttmp);
					}
				}
			} else {
				if (tmp == null) {
					code = upcode + "01";
				} else if (tmp.length() >= 2) {
					String t1 = tmp.substring(0, tmp.length() - 2);
					String t2 = tmp.substring(tmp.length() - 2, tmp.length());
					int inttmp = Integer.parseInt(t2) + 1;
					if (inttmp < 10) {
						code = t1 + "0" + inttmp;
					} else if (inttmp >99) {
						code = t1 + String.valueOf(inttmp-1);
					}else {
						code = t1 + inttmp;
					}
				}
			}

		}
		return code;
	}

	/**
	 * 根据人员id加载人员名称
	 */
	public List<String> getEmployeeNamesByids(String ids) {
		List<String> empnames = new ArrayList<String>();
		if (ids == null || ids.length() == 0) {
			return empnames;
		}
		Map<String, String> tmpMap = new HashMap<String, String>();
		String[] empids = ids.split(",");
		String empid = "";
		for (String str : empids) {
			if (str != null && str.length() > 0) {
				empid += "'" + str + "',";
			}
		}
		if (empid.length() > 0) {
			empid = empid.substring(0, empid.length() - 1);
			List<Object[]> list = sysLogDao.findBySqlObjList("select hrm_employee_id,hrm_employee_name from hrm_employee where hrm_employee_id in ( " + empid + " )");
			for (Object[] obj : list) {
				tmpMap.put(obj[0].toString(), obj[1].toString());
			}
		}
		for (String str : empids) {
			if (str != null && str.length() > 0) {
				if(tmpMap.containsKey(str)){
					empnames.add(tmpMap.get(str));
				}else{
					empnames.add("");
				}
			}
		}
		
		return empnames;
	}

	/**
	 * 根据用户id加载人员名称
	 */
	public List<String> getEmployeeNamesByUserIds(String ids) {
		List<String> empnames = new ArrayList<String>();
		if (ids == null || ids.length() == 0) {
			return empnames;
		}
		if (ids.charAt(ids.length() - 1) == ',') {
			ids = ids.substring(0, ids.length() - 1);
		}
		Map<String, String> tmpMap = new HashMap<String, String>();
		String[] userIds = ids.split(",");
		if (ids.length() > 0) {
			List<Object[]> list = sysLogDao
					.findBySqlObjList("select userinfo.user_id,hrm.hrm_employee_name from sys_user_info userinfo,hrm_employee hrm where hrm.hrm_employee_id = userinfo.hrm_employee_id and  userinfo.user_id in ( "
							+ ids + " )");
			for (Object[] obj : list) {
				tmpMap.put(obj[0].toString(), obj[1].toString());
			}
		}
		for (String str : userIds) {
			if (str != null && str.length() > 0) {
				if(tmpMap.containsKey(str)){
					empnames.add(tmpMap.get(str));
				}else{
					empnames.add("");
				}
			}
		}
		return empnames;
	}

	/**
	 * 根据编组id加载编组名称
	 */
	public List<String> getGroupNamesByIds(String ids) {
		List<String> groupnames = new ArrayList<String>();
		if (ids == null || ids.length() == 0) {
			return groupnames;
		}
		if (ids.charAt(ids.length() - 1) == ',') {
			ids = ids.substring(0, ids.length() - 1);
		}
		Map<String, String> tmpMap = new HashMap<String, String>();
		String[] guids = ids.split(",");
		if (ids.length() > 0) {
			List<Object[]> list = sysLogDao.findBySqlObjList("select group_id,group_name from sys_user_group where group_id in ( " + ids + " )");
			for (Object[] obj : list) {
				tmpMap.put(obj[0].toString(), obj[1].toString());
			}
		}
		for (String str : guids) {
			if (str != null && str.length() > 0) {
				if(tmpMap.containsKey(str)){
					groupnames.add(tmpMap.get(str));
				}else{
					groupnames.add("");
				}
			}
		}
		return groupnames;
	}

	/**
	 * 根据岗位id加载编组名称
	 */
	public List<String> getPostNamesByIds(String ids) {
		List<String> postnames = new ArrayList<String>();
		if (ids == null || ids.length() == 0) {
			return postnames;
		}
		if (ids.charAt(ids.length() - 1) == ',') {
			ids = ids.substring(0, ids.length() - 1);
		}
		Map<String, String> tmpMap = new HashMap<String, String>();
		String[] pids = ids.split(",");
		if (ids.length() > 0) {
			List<Object[]> list = sysLogDao.findBySqlObjList("select post_id,hrm_post_name from hrm_post where post_id in ( " + ids + " )");
			for (Object[] obj : list) {
				tmpMap.put(obj[0].toString(), obj[1].toString());
			}
		}
		for (String str : pids) {
			if (str != null && str.length() > 0) {
				if(tmpMap.containsKey(str)){
					postnames.add(tmpMap.get(str));
				}else{
					postnames.add("");
				}
			}
		}
		return postnames;
	}

	/**
	 * 根据部门id加载部门名称
	 * 
	 * @param ids
	 * @return
	 */
	public List<String> getDeptNamesByIds(String ids) {
		List<String> deptnames = new ArrayList<String>();
		if (ids == null || ids.length() == 0) {
			return deptnames;
		}
		if (ids.charAt(ids.length() - 1) == ',') {
			ids = ids.substring(0, ids.length() - 1);
		}
		Map<String, String> tmpMap = new HashMap<String, String>();
		String[] dids = ids.split(",");
		if (ids.length() > 0) {
			List<Object[]> list = sysLogDao.findBySqlObjList("select dep_id,hrm_dep_name from hrm_department where dep_id in ( " + ids + " )");
			for (Object[] obj : list) {
				tmpMap.put(obj[0].toString(), obj[1].toString());
			}
		}
		for (String str : dids) {
			if (str != null && str.length() > 0) {
				if(tmpMap.containsKey(str)){
					deptnames.add(tmpMap.get(str));
				}else{
					deptnames.add("");
				}
			}
		}
		return deptnames;
	}

	public SysUserInfo saveSysUserInfo(SysUserInfo user) {
		SysUserInfo tmp = (SysUserInfo) sysUserInfoDao.save(user);
		
		if (user.getPrimaryKey() <= 0) {
			// 写入用户权限表
			SysUserMethods methods = new SysUserMethods();
			methods.setUserId((int) tmp.getPrimaryKey());
			methods.setCompanyId(tmp.getCompanyId());
			sysUserMethodsDao.save(methods);
			
			synchronousToActiviti(tmp);//同步至activiti用户
		}
		return tmp;
	}

	public ArrayList<SysUserInfo> saveSysUserInfo(ArrayList<SysUserInfo> list) {
		ArrayList<SysUserInfo> userlist = new ArrayList<SysUserInfo>();
		for (SysUserInfo sysUserInfo : list) {
			SysUserInfo tmp = (SysUserInfo) sysUserInfoDao.save(sysUserInfo);
			userlist.add(tmp);
			if (sysUserInfo.getPrimaryKey() <= 0) {
				// 写入用户权限表
				SysUserMethods methods = new SysUserMethods();
				methods.setUserId((int) tmp.getPrimaryKey());
				methods.setCompanyId(tmp.getCompanyId());
				sysUserMethodsDao.save(methods);
			}
			
			synchronousToActiviti(tmp);//同步至activiti用户
			
		}
		return userlist;
	}

	private void synchronousToActiviti(SysUserInfo tmp) {
		User processUser = new UserEntity();
		processUser.setId(tmp.getHrmEmployeeId());
		identityService.saveUser(processUser);
	}

	public SysUserInfo getSysUserInfoByPk(long pk, boolean bl) {
		SysUserInfo user = sysUserInfoDao.getByPK(pk);
		if (bl) {
			user.setEmployee(hrmEmployeeDao.getByPK(user.getHrmEmployeeId()));
		}
		return user;
	}

	public int getSysUserInfoByEmpId(String empid, int companyId) {
		return sysUserInfoDao.findByHqlWhereCount(" and model.hrmEmployeeId = '" + empid + "' and model.companyId=" + companyId);
	}
	
	public SysUserInfo getSysUserInfoByEmpId(String empid) {
		List<SysUserInfo> list = sysUserInfoDao.findByHqlWhere(" and model.hrmEmployeeId = '" + empid +"'");
		if(list.size() > 0){
			return list.get(0);
		}else {
			return null;
		}
	}

	public int getSysUserInfoByUserName(String username, int companyId) {
		return sysUserInfoDao.findByHqlWhereCount(" and model.userName = '" + username + "' and model.companyId=" + companyId);
	}

	public int getSysUserInfoByUserNameEdit(String newusername, long userid, int companyId) {
		return sysUserInfoDao.findByHqlWhereCount(" and model.userName = '" + newusername + "' and model.primaryKey <> " + userid + " and model.companyId=" + companyId);
	}

	public int getSysUserInfoListCount(SysUserInfo userinfo) {
		return sysUserInfoDao.findBySqlCount(OaSysHqlPack.getSysUserInfoSql(userinfo));
	}

	public List<SysUserInfo> getSysUserInfoListByPager(SysUserInfo userinfo, Pager pager) {
		List<SysUserInfo> userinfoList = sysUserInfoDao.findBySqlPage(OaSysHqlPack.getSysUserInfoSql(userinfo) + " order by sysuser.user_action asc", SysUserInfo.class, pager);
		for (SysUserInfo sysUserInfo : userinfoList) {
			sysUserInfo.setEmployee(hrmEmployeeDao.getByPK(sysUserInfo.getHrmEmployeeId()));
			sysUserInfo.getEmployee().setHrmDepartment(hrmDeptDao.getByPK(sysUserInfo.getEmployee().getHrmEmployeeDepid().longValue()));
		}
		return userinfoList;
	}

	public void updateSysUserInfoIsaction(long[] ids, int isaction, String empid) {
		for (long l : ids) {
			SysUserInfo userinfo = sysUserInfoDao.getByPK(l);
			if (isaction==0) {
				if(userinfo.getUserAction().intValue()==EnumUtil.SYS_ISACTION.Vaild.value){
					isaction = EnumUtil.SYS_ISACTION.No_Vaild.value;
				}else{
					isaction=EnumUtil.SYS_ISACTION.Vaild.value;
				}
			}
			userinfo.setUserAction(isaction);
			userinfo.setLastmodiId(empid);
			userinfo.setLastmodiDate(UtilWork.getNowTime());
			sysUserInfoDao.save(userinfo);
		}
	}

	public List<SysUserGroupDetail> getSysUserGroupDetailList(int userId) {
		List<SysUserGroupDetail> detailList = sysUserGroupDetailDao.findByHqlWhere(" and model.userId = " + userId);
		for (SysUserGroupDetail sysUserGroupDetail : detailList) {
			sysUserGroupDetail.setUserGroup(sysUserGroupDao.getByPK(sysUserGroupDetail.getGroupId().longValue()));
		}
		return detailList;
	}

	public void updateSysUsergroupAndDetail(SysUserGroup group, String[] userIds, String empid, String nowtime) {
		// 删除原明细记录
		SysUserGroup tmp = sysUserGroupDao.getByPK(group.getPrimaryKey());
		List<SysUserGroupDetail> detailList = sysUserGroupDetailDao.findByHqlWhere(" and model.groupId = " + group.getPrimaryKey());
		for (SysUserGroupDetail sysUserGroupDetail : detailList) {
			sysUserGroupDetailDao.remove(sysUserGroupDetail);
		}
		group.setRecordId(tmp.getRecordId());
		group.setRecordDate(tmp.getRecordDate());
		group.setCompanyId(tmp.getCompanyId());
		group.setLastmodiId(empid);
		group.setLastmodeDate(nowtime);
		sysUserGroupDao.save(group);
		for (String id : userIds) {
			SysUserGroupDetail detail = new SysUserGroupDetail();
			detail.setGroupId((int) tmp.getPrimaryKey());
			detail.setUserId(Integer.parseInt(id));
			sysUserGroupDetailDao.save(detail);
		}
	}

	public SysUserGroup saveSysUsergroupAndDetail(SysUserGroup group, String[] userIds) {
		SysUserGroup tmp = (SysUserGroup) sysUserGroupDao.save(group);
		for (String id : userIds) {
			SysUserGroupDetail detail = new SysUserGroupDetail();
			detail.setGroupId((int) tmp.getPrimaryKey());
			detail.setUserId(Integer.parseInt(id));
			detail.setCompanyId(tmp.getCompanyId());
			sysUserGroupDetailDao.save(detail);
		}
		return tmp;
	}

	public List<SysUserGroup> listSysUserGroupBypager(SysUserGroup usergroup, Pager pager) {
		List<SysUserGroup> list = sysUserGroupDao.findByHqlWherePage(OaSysHqlPack.getSysUserGroupSql(usergroup), pager);
		for (SysUserGroup sysUserGroup : list) {
			sysUserGroup.setDetailList(sysUserGroupDetailDao.findByHqlWhere(" and model.groupId = " + sysUserGroup.getPrimaryKey()));
		}
		return list;
	}
	
	public List<SysUserGroup> listSysUserGroupAll(SysUserGroup usergroup){
		List<SysUserGroup> list = sysUserGroupDao.findByHqlWhere(OaSysHqlPack.getSysUserGroupSql(usergroup));
		return list;
	}
	

	public int listSysUserGroupCount(SysUserGroup usergroup) {
		return sysUserGroupDao.findByHqlWhereCount(OaSysHqlPack.getSysUserGroupSql(usergroup));
	}

	public SysUserGroup getSysUserGroupByPk(long pk) {
		SysUserGroup group = sysUserGroupDao.getByPK(pk);
		group.setDetailList(sysUserGroupDetailDao.findByHqlWhere(" and model.groupId = " + group.getPrimaryKey()));
		return group;
	}

	public void deleteSysUserGroupByIds(long[] ids) {
		for (long l : ids) {
			SysUserGroup group = this.getSysUserGroupByPk(l);
			for (SysUserGroupDetail detail : group.getDetailList()) {
				sysUserGroupDetailDao.remove(detail);
			}
			sysUserGroupDao.remove(group);
		}
	}

	public List<Object[]> getEmployeeNameByuserIds(String userIds, int companyId) {
		List<Object[]> list = new ArrayList<Object[]>();
		if (userIds != null && userIds.length() > 0) {
			if (userIds.charAt(userIds.length() - 1) == ',') {
				userIds = userIds.substring(0, userIds.length() - 1);
			}
			list = sysImageInfoDao.findBySqlObjList(OaSysHqlPack.getEmployeeNameByUserIdsSql(userIds, companyId));
		}
		return list;
	}

	public List<SysMethodInfo> getSysMethodInfoListByCode(String code, int level) {
		List<SysMethodInfo> methodList = sysMethodInfoDao.findByHqlWhere(" and model.methodLevel =" + level + " and model.primaryKey <> '" + code + "' and model.primaryKey like '" + code
				+ "%' and model.isAction = " + EnumUtil.SYS_ISACTION.Vaild.value);
		return methodList;
	}

	public SysRole saveRoleAndDetailBind(int companyId, String empId, SysRole role, SysRoleBind[] binds, String[] methodIds, String[] btnIds) {
		if (role.getPrimaryKey() > 0) {
			SysRole roleold = sysRoleDao.getByPK(role.getPrimaryKey());
			// 清除原明细
			List<SysRoleDetail> detailList = sysRoleDetailDao.findByHqlWhere(" and model.roleId=" + role.getPrimaryKey());
			for (SysRoleDetail sysRoleDetail : detailList) {
				sysRoleDetailDao.remove(sysRoleDetail);
			}
			
			// 清除原按钮
			List<SysRoleBtn> btnList = sysRoleBtnDao.findByHqlWhere(" and model.roleId=" + role.getPrimaryKey());
			for (SysRoleBtn sysRoleBtn : btnList) {
				sysRoleBtnDao.remove(sysRoleBtn);
			}
			
			// 清除原bind
			List<SysRoleBind> bindList = sysRoleBindDao.findByHqlWhere(" and model.roleId=" + role.getPrimaryKey());
			for (SysRoleBind sysRoleBind : bindList) {
				sysRoleBindDao.remove(sysRoleBind);
			}
			role.setRecordId(roleold.getRecordId());
			role.setRecordDate(roleold.getRecordDate());
		} else {
			role.setRecordId(empId);
			role.setRecordDate(UtilWork.getNowTime());
		}
		role.setCompanyId(companyId);
		role.setLastmodiId(empId);
		role.setLastmodiDate(UtilWork.getNowTime());
		SysRole sysrole = (SysRole) sysRoleDao.save(role);

		// 保存明细
		for (String method : methodIds) {
			SysRoleDetail detail = new SysRoleDetail();
			detail.setRoleId((int) sysrole.getPrimaryKey());
			detail.setMethodId(method);
			detail.setCompanyId(companyId);
			sysRoleDetailDao.save(detail);
		}

		// 保存按钮
		for (String btn : btnIds) {
			SysRoleBtn roleBtn = new SysRoleBtn();
			roleBtn.setRoleId((int) sysrole.getPrimaryKey());
			roleBtn.setBtnId(Integer.valueOf(btn));
			roleBtn.setCompanyId(companyId);
			sysRoleBtnDao.save(roleBtn);
		}
		
		// 保存绑定
		for (SysRoleBind rolebind : binds) {
			rolebind.setCompanyId(companyId);
			rolebind.setRoleId((int) sysrole.getPrimaryKey());
			sysRoleBindDao.save(rolebind);
		}
		return sysrole;
	}

	public int getSysRoleCount(SysRole role) {
		return sysRoleDao.findByHqlWhereCount(OaSysHqlPack.getSysRoleSql(role));
	}

	public List<SysRole> getSysRoleByPager(SysRole role, Pager pager) {
		List<SysRole> rolelist = sysRoleDao.findByHqlWherePage(OaSysHqlPack.getSysRoleSql(role), pager);
		for (SysRole sysRole : rolelist) {
			sysRole.setDetailCount(sysRoleDetailDao.findByHqlWhereCount(" and model.roleId=" + sysRole.getPrimaryKey()));
			sysRole.setBindUserCount(sysRoleBindDao.findByHqlWhereCount(" and model.roleId=" + sysRole.getPrimaryKey() + " and model.bindType =" + EnumUtil.SYS_ROLE_BIND_TYPE.BIND_USER.value));
			sysRole.setBindGroupCount(sysRoleBindDao.findByHqlWhereCount(" and model.roleId=" + sysRole.getPrimaryKey() + " and model.bindType =" + EnumUtil.SYS_ROLE_BIND_TYPE.BIND_GROUP.value));
			sysRole.setBindDeptCount(sysRoleBindDao.findByHqlWhereCount(" and model.roleId=" + sysRole.getPrimaryKey() + " and model.bindType =" + EnumUtil.SYS_ROLE_BIND_TYPE.BIND_DEPT.value));
			sysRole.setBindPostCount(sysRoleBindDao.findByHqlWhereCount(" and model.roleId=" + sysRole.getPrimaryKey() + " and model.bindType =" + EnumUtil.SYS_ROLE_BIND_TYPE.BIND_POST.value));
		}
		return rolelist;
	}
	
	public List<SysRole> getSysRoleAll(SysRole role){
		List<SysRole> rolelist = sysRoleDao.findByHqlWhere(OaSysHqlPack.getSysRoleSql(role));
		return rolelist;
	}

	public void deleteSysRoleByIds(long[] ids) {
		for (long l : ids) {
			SysRole role = sysRoleDao.getByPK(l);
			List<SysRoleDetail> detailList = sysRoleDetailDao.findByHqlWhere(" and model.roleId=" + l);
			for (SysRoleDetail sysRoleDetail : detailList) {
				sysRoleDetailDao.remove(sysRoleDetail);
			}
			
			List<SysRoleBtn> btnList = sysRoleBtnDao.findByHqlWhere(" and model.roleId=" + l);
			for (SysRoleBtn sysRoleBtn : btnList) {
				sysRoleBtnDao.remove(sysRoleBtn);
			}
			
			List<SysRoleBind> bindList = sysRoleBindDao.findByHqlWhere(" and model.roleId=" + l);
			for (SysRoleBind sysRoleBind : bindList) {
				sysRoleBindDao.remove(sysRoleBind);
			}
			sysRoleDao.remove(role);
		}
	}

	public List<SysRoleDetail> getSysRoleDetailList(int roleId) {
		List<SysRoleDetail> detailList = sysRoleDetailDao.findByHqlWhere(" and model.roleId=" + roleId);
		return detailList;
	}
	
	public List<SysRoleBtn> getSysRoleBtnList(int roleId) {
		List<SysRoleBtn> btnList = sysRoleBtnDao.findByHqlWhere(" and model.roleId=" + roleId);
		return btnList;
	}

	public SysRole getSysRoleById(long id) {
		return sysRoleDao.getByPK(id);
	}

	public List<SysRoleBind> getSysRoleBindList(int roleId) {
		List<SysRoleBind> bindList = sysRoleBindDao.findByHqlWhere(" and model.roleId=" + roleId);
		return bindList;
	}

	public List<UserMethodsInfo> getUserMethodsInfoByPager(UserMethodsInfo info, Pager pager) {
		List<Object[]> objlist = sysUserMethodsDao.findBySqlObjListByPager(OaSysHqlPack.getSysUserMethodSql(info)+" order by userinfo.user_id", pager);
		List<UserMethodsInfo> methodsinfoList = new ArrayList<UserMethodsInfo>();
		for (int i = 0; i < objlist.size(); i++) {
			Object[] obj = objlist.get(i);
			UserMethodsInfo method = new UserMethodsInfo();
			method.setPrimaryKey(Long.parseLong(obj[0].toString()));
			method.setEmployeeId(obj[1].toString());
			method.setEmployeeName(obj[2].toString());
			method.setMainPostId(obj[3] == null ? "" : obj[3].toString());
			method.setPartPostIds(obj[4] == null ? "" : obj[4].toString());
			method.setDeptId(obj[5].toString());
			method.setDeptName(obj[6].toString());
			// 查询绑定角色
			// 用户绑定角色数
			method.setEmployeeRole(String.valueOf(this.getRoleBindCountListBytype(EnumUtil.SYS_ROLE_BIND_TYPE.BIND_USER.value, method.getPrimaryKey().toString(), info.getCompanyId())));
			// 部门绑定角色数
			method.setDeptRole(String.valueOf(this.getRoleBindCountListBytype(EnumUtil.SYS_ROLE_BIND_TYPE.BIND_DEPT.value, method.getDeptId().toString(), info.getCompanyId())));

			// 主岗位绑定角色数
			if (method.getMainPostId().length() > 0) {
				HrmPost tmp = hrmPostDao.getByPK(Long.parseLong(method.getMainPostId()));
				if (tmp != null) {
					method.setMainPostName(tmp.getHrmPostName());
					method.setMainPostRole(String.valueOf(this.getRoleBindCountListBytype(EnumUtil.SYS_ROLE_BIND_TYPE.BIND_POST.value, method.getMainPostId(), info.getCompanyId())));
				}
			}

			// 兼职岗位绑定角色数
			if (method.getPartPostIds().length() > 0) {
				String ids = method.getPartPostIds();
				if (ids.charAt(ids.length() - 1) == ',') {
					ids = ids.substring(0, ids.length() - 1);
				}
				List<Object[]> countlist = sysRoleBindDao
						.findBySqlObjList("select post.hrm_post_name,count(bind.bind_id) from hrm_post post,sys_role_bind bind where post.post_id = bind.bind_value and post.company_id="
								+ info.getCompanyId() + " and bind.bind_value in( " + ids + " ) and bind.bind_type=" + EnumUtil.SYS_ROLE_BIND_TYPE.BIND_POST.value + " group by post.hrm_post_name");
				String tmp = "";
				String countstr = "";
				for (int j = 0; j < countlist.size(); j++) {
					if (j == countlist.size() - 1) {
						tmp += countlist.get(j)[0];
						countstr += countlist.get(j)[1];
					} else {
						tmp += countlist.get(j)[0] + ",";
						countstr += countlist.get(j)[1] + ",";
					}
				}
				method.setPartPostName(tmp);
				method.setPartPostRole(countstr);
			}

			// 所在组绑定角色数
			List<Object[]> groupcountlist = sysRoleBindDao
					.findBySqlObjList("select  ug.group_name,count(bind.bind_id) from sys_user_group ug,sys_user_group_detail ugd,sys_role_bind bind where ug.group_id = ugd.group_id and ug.company_id ="
							+ info.getCompanyId()
							+ " and ugd.user_id = "
							+ method.getPrimaryKey()
							+ " and ug.group_id = bind.bind_value and bind.bind_type="
							+ EnumUtil.SYS_ROLE_BIND_TYPE.BIND_GROUP.value + " group by  ug.group_name");
			String tmpgroup = "";
			String countgroupstr = "";
			for (int j = 0; j < groupcountlist.size(); j++) {
				if (j == groupcountlist.size() - 1) {
					tmpgroup += groupcountlist.get(j)[0];
					countgroupstr += groupcountlist.get(j)[1];
				} else {
					tmpgroup += groupcountlist.get(j)[0] + ",";
					countgroupstr += groupcountlist.get(j)[1] + ",";
				}
			}
			method.setGroupName(tmpgroup);
			method.setGroupRole(countgroupstr);
			// 附件权限数
			int umcount = 0;
			SysUserMethods ms = this.getSysUserMethodsByUid(method.getPrimaryKey().intValue(), info.getCompanyId());
			if (ms != null && ms.getUserMethodDetail() != null && ms.getUserMethodDetail().length() > 0) {
				String tmp = ms.getUserMethodDetail();
				if (tmp.charAt(tmp.length() - 1) == ',') {
					tmp = tmp.substring(0, tmp.length() - 1);
				}
				umcount = tmp.split(",").length;
			}
			method.setUserMethods(String.valueOf(umcount));

			methodsinfoList.add(method);
		}
		return methodsinfoList;
	}

	public int getUserMethodsInfoCount(UserMethodsInfo info) {
		return sysUserMethodsDao.findBySqlCount(OaSysHqlPack.getSysUserMethodSql(info));
	}

	public UserMethodsInfo getUserMethodsInfoByUid(int uid,int companyId){
		UserMethodsInfo tmp = new UserMethodsInfo();
		UserMethodsInfo methodinfo =new UserMethodsInfo();
		tmp.setPrimaryKey((long)uid);
		tmp.setCompanyId(companyId);
		
		SysUserInfo info = sysUserInfoDao.getByPK((long)uid);
		
		List<Object[]> objlist = sysUserMethodsDao.findBySqlObjList(OaSysHqlPack.getSysUserMethodSql(tmp));
		if (objlist.size()>0) {
			Object[] obj = objlist.get(0);
			methodinfo.setPrimaryKey(Long.parseLong(obj[0].toString()));
			methodinfo.setEmployeeId(obj[1].toString());
			methodinfo.setEmployeeName(obj[2].toString());
			methodinfo.setMainPostId(obj[3] == null ? "" : obj[3].toString());
			methodinfo.setPartPostIds(obj[4] == null ? "" : obj[4].toString());
			methodinfo.setDeptId(obj[5].toString());
			methodinfo.setDeptName(obj[6].toString());
			// 查询绑定角色
			// 用户绑定角色数
			methodinfo.setEmployeeRole(String.valueOf(this.getRoleBindNameListBytype(EnumUtil.SYS_ROLE_BIND_TYPE.BIND_USER.value, methodinfo.getPrimaryKey().toString(), info.getCompanyId())));
			// 部门绑定角色数
			methodinfo.setDeptRole(String.valueOf(this.getRoleBindNameListBytype(EnumUtil.SYS_ROLE_BIND_TYPE.BIND_DEPT.value, methodinfo.getDeptId().toString(), info.getCompanyId())));

			// 主岗位绑定角色数
			if (methodinfo.getMainPostId().length() > 0) {
				HrmPost mp = hrmPostDao.getByPK(Long.parseLong(methodinfo.getMainPostId()));
				if (mp != null) {
					methodinfo.setMainPostName(mp.getHrmPostName());
					methodinfo.setMainPostRole(String.valueOf(this.getRoleBindNameListBytype(EnumUtil.SYS_ROLE_BIND_TYPE.BIND_POST.value, methodinfo.getMainPostId(), info.getCompanyId())));
				}
			}

			// 兼职岗位绑定角色数
			if (methodinfo.getPartPostIds().length() > 0) {
				String ids = methodinfo.getPartPostIds();
				if (ids.charAt(ids.length() - 1) == ',') {
					ids = ids.substring(0, ids.length() - 1);
				}
				Set<Integer> tmpset = new HashSet<Integer>();
				List<Object[]> countlist = sysRoleBindDao
						.findBySqlObjList("select distinct post.post_id,post.hrm_post_name,sr.role_name from hrm_post post,sys_role_bind bind,sys_role sr where post.post_id = bind.bind_value and sr.role_id=bind.role_id and post.company_id="
								+ info.getCompanyId() + " and bind.bind_value in( " + ids + " ) and bind.bind_type=" + EnumUtil.SYS_ROLE_BIND_TYPE.BIND_POST.value);
				String str = "";
				String countstr = "";
				for (int i = 0; i < countlist.size(); i++) {
					Object[] objs= countlist.get(i);
					int pk = Integer.parseInt(objs[0].toString());
					if (tmpset.contains(pk)) {
						countstr+=objs[2].toString()+"&nbsp;,&nbsp;";
					}else{
						str+=objs[1].toString()+"&nbsp;,&nbsp;";
						countstr+=objs[2].toString()+"&nbsp;,&nbsp;";
						tmpset.add(pk);
					}
				}
				
				methodinfo.setPartPostName(str);
				methodinfo.setPartPostRole(countstr);
			}
			if (methodinfo.getPrimaryKey()!=null&&methodinfo.getPrimaryKey()>0) {
				Set<Integer> tmpset = new HashSet<Integer>();
				// 所在组绑定角色数
				List<Object[]> groupcountlist = sysRoleBindDao
						.findBySqlObjList("select distinct ug.group_id,ug.group_name,sr.role_name from sys_user_group ug,sys_user_group_detail ugd,sys_role_bind bind,sys_role sr where ug.group_id = ugd.group_id and sr.role_id=bind.role_id and ug.company_id ="
								+ info.getCompanyId()
								+ " and ugd.user_id = "
								+ methodinfo.getPrimaryKey()
								+ " and ug.group_id = bind.bind_value and bind.bind_type="
								+ EnumUtil.SYS_ROLE_BIND_TYPE.BIND_GROUP.value);
				
				String tmpgroup = "";
				String countgroupstr = "";
				for (int j = 0; j < groupcountlist.size(); j++) {
					Object[] objs= groupcountlist.get(j);
					int pk = Integer.parseInt(objs[0].toString());
					if (tmpset.contains(pk)) {
						countgroupstr+=objs[2].toString()+"&nbsp;,&nbsp;";
					}else{
						tmpgroup+=objs[1].toString()+"&nbsp;,&nbsp;";
						countgroupstr+=objs[2].toString()+"&nbsp;,&nbsp;";
						tmpset.add(pk);
					}
				}
				methodinfo.setGroupName(tmpgroup);
				methodinfo.setGroupRole(countgroupstr);
			}
		}
		return methodinfo;
	}
	
	private int getRoleBindCountListBytype(int type, String val, int companyId) {
		int count = 0;
		if (val != null && val.length() > 0) {
			count = sysRoleBindDao.findByHqlWhereCount(" and model.bindType =" + type + " and model.companyId =" + companyId + " and model.bindValue='" + val + "'");
		}

		return count;
	}

	private String getRoleBindNameListBytype(int type, String val, int companyId) {
		String rolenames="";
		List<Object[]> list =null;
		if (val != null && val.length() > 0) {
			list = sysRoleBindDao.findBySqlObjList("select sr.role_name from sys_role sr,sys_role_bind bind where sr.role_id=bind.role_id and bind.bind_type="+type+" and sr.company_id = "+companyId+" and bind.bind_value = '"+val+"'");
			for (int i = 0; i < list.size(); i++) {
				if (i==list.size()-1) {
					rolenames+=list.get(i)[0].toString();
				}else{
					rolenames+=list.get(i)[0].toString()+"&nbsp;,&nbsp;";
				}
			}
		}
		return rolenames;
	}
	
	public SysUserMethods getSysUserMethodsByUid(int uid, int companyId) {
		List<SysUserMethods> list = sysUserMethodsDao.findByHqlWhere(" and model.userId=" + uid + " and model.companyId=" + companyId);
		SysUserMethods methods = null;
		if (list.size() > 0) {
			methods = list.get(0);
		}
		return methods;
	}

	public void saveSysUserMethods(ArrayList<SysUserMethods> list, ArrayList<SysUserBtns> btns) {
		for (SysUserMethods sysUserMethods : list) {
			sysUserMethodsDao.save(sysUserMethods);
		}
		for (SysUserBtns sysUserBtns : btns) {
			sysUserBtnsDao.save(sysUserBtns);
		}
	}
	
	
	public SysUserBtns getSysUserBtnsByUid(int uid, int companyId) {
		List<SysUserBtns> list = sysUserBtnsDao.findByHqlWhere(" and model.userId=" + uid + " and model.companyId=" + companyId);
		SysUserBtns methods = null;
		if (list.size() > 0) {
			methods = list.get(0);
		}
		return methods;
	}
	
	public SysCompanyInfo saveCompanyInfo(SysCompanyInfo company,String[] methods){
		SysCompanyInfo companyinfo = (SysCompanyInfo) sysCompanyInfoDao.save(company);
		for (String string : methods) {
			if (string!=null&&string.length()>0) {
				SysCompanyMethods med = new SysCompanyMethods();
				med.setCompanyId((int)companyinfo.getPrimaryKey());
				med.setMethodInfoId(string);
				sysCompanyMethodsDao.save(med);
			}
		}
		return companyinfo;
	}
	
	public SysCompanyInfo updateCompanyInfo(SysCompanyInfo company){
		return (SysCompanyInfo) sysCompanyInfoDao.save(company);
	}
	
	public int getSysCompanyInfoCount(SysCompanyInfo companyInfo){
		return sysCompanyInfoDao.findByHqlWhereCount(SystemPack.getCompanySql(companyInfo));
	}
	
	public List<SysCompanyInfo> getSysCompanyInfoByPager(SysCompanyInfo companyInfo,Pager pager){
		List<SysCompanyInfo> list = sysCompanyInfoDao.findByHqlWherePage(SystemPack.getCompanySql(companyInfo),pager);
		return list;
	}
	
	public int getSysCompanyInfoCountForParam(SysCompanyInfo companyInfo){
		return sysCompanyInfoDao.findByHqlWhereCount(SystemPack.getCompanySqlForParam(companyInfo));
	}
	
	public List<SysCompanyInfo> getSysCompanyInfoByPagerForParam(SysCompanyInfo companyInfo,Pager pager){
		List<SysCompanyInfo> list = sysCompanyInfoDao.findByHqlWherePage(SystemPack.getCompanySqlForParam(companyInfo),pager);
		return list;
	}
	
	public List<SysMethodInfo> getSysCompanyMethodsByPk(long companyid){
		List<SysMethodInfo> methodlist = new ArrayList<SysMethodInfo>();
		List<SysCompanyMethods> list = sysCompanyMethodsDao.findByHqlWhere(" and model.companyId="+companyid);
		for (SysCompanyMethods sysCompanyMethods : list) {
			SysMethodInfo info = sysMethodInfoDao.getByPK(sysCompanyMethods.getMethodInfoId());
			methodlist.add(info);
		}
		return methodlist;
	}
	
	public SysCompanyInfo getSysCompanyInfoByPk(long pk){
		return sysCompanyInfoDao.getByPK(pk);
	}

	/**
	 * 删除系统公告
	 */
	public void delectSystemMsgByid(long[] id) {
		for (long l : id) {
			SysMsg msg =  sysMsgDao.getByPK(l);
			
			sysMsgDao.remove(msg);
			
		}
	}

	/**
	 * 获取所有系统公告
	 */
	public List<SysMsg> getAllSystemMsg(SysMsg msg, Pager pager) {
		List<SysMsg>  msgList = sysMsgDao.findByHqlWherePage(SystemPack.packSysMsgQuery(msg), pager);
		return msgList;
	}

	public List<SysMsg> listAllSystemMsg(){
		return sysMsgDao.list();
	}
	
	public List<SysMsg> listSystemMsgOrderBy(Pager pager){
		return sysMsgDao.findByHqlWherePage(" order by model.msgDate desc", pager);
	}
	
	/**
	 * 根据主键获取系统公告
	 */
	public  SysMsg getSystemMsgByid(long id) {
		SysMsg msg = sysMsgDao.getByPK(id);
		return msg;
	}

	/**
	 * 统计系统公告数量
	 */
	public int getSystemMsgCount(SysMsg msg) {
		int count = sysMsgDao.findByHqlWhereCount(SystemPack.packSysMsgQuery(msg));
		return count;
	}

	/**
	 * 保存系统公告
	 */
	public SysMsg saveSystemMsg(SysMsg msg) {
		SysMsg sysMsg = (SysMsg) sysMsgDao.save(msg);
		return sysMsg;
	}
//////////////////////////////////////////////////
	/**
	 * 删除系统帮助
	 */
	public void delectSystemHelpByid(long[] id) {
		for (long l : id) {
			SysHelp msg =  sysHelpDao.getByPK(l);
			
			sysHelpDao.remove(msg);
			
		}
		
	}

	/**
	 * 获得所有系统帮助
	 */
	public List<SysHelp> getAllSystemHelp(SysHelp help, Pager pager) {
		List<SysHelp>  helpList = sysHelpDao.findByHqlWherePage(SystemPack.packSysHelpQuery(help), pager);
		for (SysHelp sysHelp : helpList) {
			sysHelp.setMethodInfo(sysMethodInfoDao.getByPK(sysHelp.getMethodCode()));
		}
		return helpList;
	}
	
	
	public List<SysHelp> listSystemHelpOrderBy(Pager pager){
		return sysHelpDao.findByHqlWherePage(" order by model.helpDate desc", pager);
	}

	/**
	 * 通过ID查询系统帮助
	 */
	public SysHelp getSystemHelpByid(long id) {
		SysHelp helpmsg = sysHelpDao.getByPK(id);
		helpmsg.setMethodInfo(sysMethodInfoDao.getByPK(helpmsg.getMethodCode()));
		return helpmsg;
	}

	/**
	 * 获得系统帮助数量
	 */
	public int getSystemHelpCount(SysHelp help) {
		int count = sysHelpDao.findByHqlWhereCount(SystemPack.packSysHelpQuery(help));
		return count;
	}

	/**
	 * 保存系统帮助
	 */
	public SysHelp saveSystemHelp(SysHelp help) {
		SysHelp sysHelp = (SysHelp) sysHelpDao.save(help);
		return sysHelp;
	}
    
    public List<SysCompanyInfo> getSysCompanyInfoByCode(String code,Long companyId){
    	String hql = " and model.companyInfoCode ='"+code+"'";
    	if(companyId!=null&&companyId>0){
    		hql+=" and model.primaryKey <> "+companyId;
    	}
		List<SysCompanyInfo> list = sysCompanyInfoDao.findByHqlWhere(hql);
		return list;
	}
    
    /**
     * 获取系统异常数量
     * @param sysException
     *               SysException
     * @return int
     */
	public int listSysExceptionCount(SysException sysException) {
		int count = exceptionDao.findBySqlCount(OaSysHqlPack.getSysExceptionSql(sysException));
		return count;
	}
	
	/**
	 * 列出所有异常
	 * @param sysException 
	 *              SysException
	 * @param pager
	 *              Pager
	 * @return List<SysException>
	 */
	public List<SysException> listAllSysException(SysException exception, Pager pager) {
		List<SysException> list = exceptionDao.findBySqlPage(OaSysHqlPack.getSysExceptionSql(exception), SysException.class, pager);
		for (SysException sysException : list) {
			//写入User info
			if(sysException.getUserId() != null && sysException.getUserId()>0){
				sysException.setUserInfo(sysUserInfoDao.getByPK((long)sysException.getUserId()));
			}
			//写入Company Info
			if(sysException.getCompanyId() != null && sysException.getCompanyId()>0){
				sysException.setCompanyInfo(sysCompanyInfoDao.getByPK((long)sysException.getCompanyId()));
			}
		}
		return list;
	}
	
	/**
	 * 通过ID查询系统帮助
	 * @param  id 
	 *           long
	 * @return SysException
	 */
	public SysException getSysExceptionByid(long id) {
		SysException exception = exceptionDao.getByPK(id);
		if (exception != null) {
			exception.setCompanyInfo(getSysCompanyInfoByPk(exception.getCompanyId()));
			exception.setUserInfo(getSysUserInfoByPk(exception.getUserId(), false));
		}
		
		return exception;
	}
	
	/**
	 * 删除系统异常
	 * @param id
	 *         long[]
	 */
	public void delectSysExceptionsByid(long[] id) {
		for (long l : id) {
			SysException exception =  exceptionDao.getByPK(l);
			exceptionDao.remove(exception);
		}
	}
	
	/**
	 * 更新系统异常
	 * @param sysException
	 *           SysException
	 */
	public void updateSysException(SysException sysException) {
		SysException exception = exceptionDao.getByPK(sysException.getPrimaryKey());
		exception.setProcessName(sysException.getProcessName());
		exception.setProcessContext(sysException.getProcessContext());
		exception.setExceptionStatus(new Integer(2));
		exception.setProcessDate(UtilWork.getNowTime());
		exceptionDao.save(exception);
	}
	
	
    
	/**
	 * 更新公司及初始化公司运行参数
	 * @param companyInfo 公司
	 * @param systemInfo 管理公司
	 */
	public void updateSysCompanyInfo(SysCompanyInfo companyInfo,SysCompanyInfo systemInfo){
		//1.更新公司信息
		sysCompanyInfoDao.save(companyInfo);
		
		//2.2 生成超级用户
		SysUserInfo userinfo = new SysUserInfo();
		userinfo.setHrmEmployeeId("-1");
		userinfo.setUserName("systemadmin");
		userinfo.setUserpassword(Base64.getBase64FromString(UtilWork.getCustomerDay("yyyyMMddHHmmss")));
		userinfo.setUserAction(EnumUtil.SYS_ISACTION.Vaild.value);
		userinfo.setRecordId("-1");
		userinfo.setRecordDate(UtilWork.getNowTime());
		userinfo.setLastmodiId("-1");
		userinfo.setLastmodiDate(UtilWork.getNowTime());
		userinfo.setCompanyId((int)companyInfo.getPrimaryKey());
		userinfo.setUserType(EnumUtil.SYS_USER_TYPE.SYSTEM.value);
		
		sysUserInfoDao.save(userinfo);
		//2.3生成运行参数
		List<SysParam> paramList = sysParamDao.findByHqlWhere(" and model.companyId = "+systemInfo.getPrimaryKey());
		for (SysParam sysParam : paramList) {
			SysParam param = new SysParam();
			param.setParamIndex(sysParam.getParamIndex());
			param.setParamTitle(sysParam.getParamTitle());
			param.setParamValue(sysParam.getParamValue());
			param.setParamRemark(sysParam.getParamRemark());
			param.setParamType(sysParam.getParamType());
			param.setParamTypeValue(sysParam.getParamTypeValue());
			param.setRecordId(sysParam.getRecordId());
			param.setRecordDate(sysParam.getRecordDate());
			param.setLastmodiId(sysParam.getLastmodiId());
			param.setLastmodiDate(companyInfo.getCompanyInfoLastDate());
			param.setCompanyId((int)companyInfo.getPrimaryKey());
			sysParamDao.save(param);
		}
	}
	
	/**
	 * 管理人员调整公司信息
	 * @param info
	 * @param methods
	 * @return
	 */
	public SysCompanyInfo editSysCompanyInfo(SysCompanyInfo info,String[] methods){
		//清除原功能菜单
		List<SysCompanyMethods> methodslist = sysCompanyMethodsDao.findByHqlWhere(" and model.companyId = "+info.getPrimaryKey());
		for (SysCompanyMethods sysCompanyMethods : methodslist) {
			sysCompanyMethodsDao.remove(sysCompanyMethods);
		}
		//保存
		SysCompanyInfo companyinfo = (SysCompanyInfo) sysCompanyInfoDao.save(info);
		for (String string : methods) {
			SysCompanyMethods med = new SysCompanyMethods();
			med.setCompanyId((int)info.getPrimaryKey());
			med.setMethodInfoId(string);
			sysCompanyMethodsDao.save(med);
		}
		return companyinfo;
	}
	
	
	public List<SysCompanyInfo> getSysCompanyInfoByType(int type){
		List<SysCompanyInfo> list = sysCompanyInfoDao.findByHqlWhere(" and model.companyInfoType = "+type);
		return list;
	}
	
	public SysUserInfo getSysUserInfoByCompanyId(int cid){
		SysUserInfo sysuser = null;
		List<SysUserInfo> list = sysUserInfoDao.findByHqlWhere(" and model.companyId = "+cid+" and model.userType = "+EnumUtil.SYS_USER_TYPE.SYSTEM.value);
		if (list.size()>0) {
			sysuser = list.get(0);
		}
		return sysuser;
	}
	/**
	 * 删除公司
	 * @param pk
	 */
	public void deleteSysCompanyInfoByPk(ServletContext context,long pk) throws Exception{
		String companySign ="company_id";
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
		Connection conn = null;
		PreparedStatement ps= null;
		ResultSet rs =null;
		ResultSetMetaData rm =null;
		ArrayList<String> tmpList =new ArrayList<String>();
		ArrayList<String> deltableList = new ArrayList<String>();
		ArrayList<String> delsqlList =new ArrayList<String>();
		try {
			DataSource ds = (DataSource) ctx.getBean(SystemConfig.getParam("erp.proxool.dsname"));
			conn=ds.getConnection();
			conn.setAutoCommit(false);
			//特定表删除
			delsqlList.add("delete from sys_company_info where company_info_id ="+pk);			
			//1.获取所有表
			//ps = conn.prepareStatement("select table_name from user_tables");//oracle
			ps = conn.prepareStatement("show tables");//mysql
			
			rs = ps.executeQuery();
			while (rs.next()) {
				tmpList.add(rs.getString(1));
			}
			//2.确认是否存在公司字段
			for (int i = 0; i < tmpList.size(); i++) {
				String sql = "select * from "+tmpList.get(i);
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				rm = rs.getMetaData();
				for (int j = 1; j <= rm.getColumnCount(); j++) {
					String cname = rm.getColumnName(j);
					if (cname.equalsIgnoreCase(companySign)) {
						deltableList.add(tmpList.get(i));
						break;
					}
				}
			}
			//3.生成删除语句
			for (int i = 0; i < deltableList.size(); i++) {
				String sql="delete from "+deltableList.get(i)+" where "+companySign+" = "+pk;
				delsqlList.add(sql);
			}
			//4.执行
			for (int i = 0; i < delsqlList.size(); i++) {
				log.info(delsqlList.get(i));
				ps = conn.prepareStatement(delsqlList.get(i));
				System.out.println(delsqlList.get(i));
				ps.execute();
			}
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			throw new Exception("获取数据库连接对象错误!");
		}finally{
			if (rs!= null) {
				rs.close();
				rs=null;
			}
			if (ps!= null) {
				ps.close();
				ps=null;
			}
			if (conn!= null) {
				conn.close();
				conn = null;
			}
		}
	}
	
	public int getSysParamCount(SysParam param){
		return sysParamDao.findByHqlWhereCount(SystemPack.getSysParamSlq(param));
	}
	
	public List<SysParam> getSysParamByPager(SysParam param,Pager pager){
		List<SysParam> list = sysParamDao.findByHqlWherePage(SystemPack.getSysParamSlq(param), pager);
		return list;
	}
	
	
	public List<SysParam> getAllSysParamByCompanyId(int companyId){
		return sysParamDao.findByHqlWhere(" and model.companyId = "+companyId);
	}
	
	
	public SysParam saveSysParam(SysParam param){
		SysParam tmp = (SysParam) sysParamDao.save(param);
//		//写入其他公司
//		if (param.getPrimaryKey()==0) {
//			//所有已处理公司添加参数
//			List<SysCompanyInfo> companylist =  sysCompanyInfoDao.findByHqlWhere(" and model.companyInfoStatus ="+EnumUtil.SYS_COMPANY_STATUS.TAKE.value+" and model.companyInfoCode is not null and model.primaryKey <> "+param.getCompanyId());
//			for (SysCompanyInfo sysCompanyInfo : companylist) {
//				SysParam sp = new SysParam();
//				sp.setCompanyId((int)sysCompanyInfo.getPrimaryKey());
//				sp.setParamIndex(param.getParamIndex());
//				sp.setParamTitle(param.getParamTitle());
//				sp.setParamValue(param.getParamValue());
//				sp.setParamRemark(param.getParamRemark());
//				sp.setParamType(param.getParamType());
//				sp.setParamTypeValue(param.getParamTypeValue());
//				sp.setRecordId(tmp.getRecordId());
//				sp.setRecordDate(tmp.getRecordDate());
//				sp.setLastmodiId(param.getLastmodiId());
//				sp.setLastmodiDate(param.getLastmodiDate());
//				sysParamDao.save(sp);
//			}
//		}
		return tmp;
	}
	
	public SysParam getSysParamByPk(long pk){
		return sysParamDao.getByPK(pk);
	}
	
	public SysParam getSysParamByIndex(String index,int companyId){
		SysParam tmp = null;
		List<SysParam> list = sysParamDao.findByHqlWhere(" and model.paramIndex ='"+index+"' and model.companyId="+companyId);
		if (list.size()==0) {
			tmp = list.get(0);
		}
		return tmp;
	}
	
	public void updateSysparams(ArrayList<SysParam> list){
		for (SysParam sysParam : list) {
			sysParamDao.save(sysParam);
		}
	}
	public void deleteSysParamByPk(long pk){
		SysParam tmp = sysParamDao.getByPK(pk);
		sysParamDao.remove(tmp);
	}
	
	/**
	 * 获得系统所有日志
	 */
	public List<SysLog> getAllLog(SysLog sysLog, Pager pager) {
		List<SysLog> sysLogList = sysLogDao.findBySqlPage(OaSysHqlPack.packSysLogQuery(sysLog),SysLog.class,pager);	
		return sysLogList;
	}

	/**
	 * 删除系统公告
	 */
	public void deleteSysLogByCommpanyid(long companyId) {
		String sql=	"delete from sys_log where company_id="+companyId+" ";
		sysLogDao.executeSql(sql);
	}
	
	public int listLogCount(SysLog sysLog) {
		int coout =sysLogDao.findBySqlCount(OaSysHqlPack.packSysLogQuery(sysLog));
		return coout;
	}

	public HrmEmployee getEmployeeByPK(String employeePK) {
		HrmEmployee sysEmployee= hrmEmployeeDao.getByPK(employeePK);
		return sysEmployee;
		
	}

	public int getCompanyUserMaxCount(long companyId){
		return sysCompanyInfoDao.getByPK(companyId).getCompanyInfoUsercount().intValue();
	}
	
	public int getSysUserInfoCountByCompanyId(int companyId){
		return sysUserInfoDao.findByHqlWhereCount(" and model.companyId = "+companyId+" and model.userType = "+EnumUtil.SYS_USER_TYPE.DEFAULT.value);
	}


   public List<SysParam> getSysparmByIdex(String task_name) {
		List<SysParam> list = sysParamDao.findBySql("select * from sys_param where param_index='"+task_name+"' and company_id in (select company_info_id from sys_company_info where company_info_status=2 and company_info_type in (2,3) )", SysParam.class);
		return list;
	}

	public List<HrmEmployee> getEmployeeBycompanyId(Integer companyId) {
		List<HrmEmployee> list = hrmEmployeeDao.findBySql("select * from hrm_employee where company_id="+companyId+" and hrm_employee_status in (1,2) and hrm_employee_squad_id is not null", HrmEmployee.class);
		return list;
	}


	public List<SysParam> getSysparmByIdexAndcompanyid(int companyId,
			String task_name) {
		List<SysParam> list = sysParamDao.findBySql("select * from sys_param where param_index='"+task_name+"' and company_id = "+companyId+" ", SysParam.class);
		return list;
	}

	
	public List<OnlineHrmEmployeeBean> getOnlineEmployee(HrmEmployee employee,long companyId, Pager pager){
		List<OnlineHrmEmployeeBean> list = new ArrayList<OnlineHrmEmployeeBean>();
		List<Object[]> objlist = hrmEmployeeDao.findBySqlObjListByPager(HrmEmployeePack.packOnLineEmployeeQuery(employee, companyId)+" order by emp.hrm_employee_code", pager);
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
	
	public int getOnlineEmployeeCount(HrmEmployee employee,long companyId){
		return hrmEmployeeDao.findBySqlCount(HrmEmployeePack.packOnLineEmployeeQuery(employee, companyId));
	}
	/**
	 * 获得所有在线人员
	 */
	public List<HrmEmployee> getAllEmployee(HrmEmployee employee,long companyId, Pager pager) {
		List<HrmEmployee> employeeList = hrmEmployeeDao.findByHqlWherePage(HrmEmployeePack.packEmployeeQuery(employee,companyId), pager);

		return employeeList;
	}




	/**
	 * 获得所有在线人员  数量
	 */
	public int listEmployeeCount(HrmEmployee employee, long companyId) {
		int count = hrmEmployeeDao.findByHqlWhereCount(HrmEmployeePack.packEmployeeQuery(employee,companyId));
		return count;
	}




	/**
	 * 获得所有在线人员  部门
	 */
	public HrmDepartment getDepartmentByID(Integer hrmEmployeeDepid) {
		HrmDepartment hdp = hrmDeptDao.getByPK((long)hrmEmployeeDepid);
		return hdp;
	}




	/**
	 * 获得所有在线人员  岗位
	 */
	public HrmPost getEmployeeByPK(Integer hrmEmployeePostId) {
	   
		HrmPost  hrmPost=hrmPostDao.getByPK((long)hrmEmployeePostId);
		
		return hrmPost;
	}
	

	/**
	 * 删除功能菜单
	 */
	public void delectMethodByids(String[] ids) {
		for (String l : ids) {
			SysMethodInfo method = sysMethodInfoDao.getByPK(l);
			//如果为顶级，删除公司已经分配信息
			if(method.getMethodLevel() == -1){
				List<SysCompanyMethods> list = sysCompanyMethodsDao.findByProperty("methodInfoId", method.getPrimaryKey());
				for (SysCompanyMethods o : list) {
					sysCompanyMethodsDao.remove(o);
				}
			}

			//删除功能相关按钮
			List<SysMethodBtn> list = sysMethodBtnDao.findByProperty("methodId", method.getPrimaryKey());
			for (SysMethodBtn btn : list) {
				sysMethodBtnDao.remove(btn);
			}
			
			//删除功能相关提示信息
			List<SysMethodHelp> helpList = sysMethodHelpDao.findByProperty("methodId", method.getPrimaryKey());
			for (SysMethodHelp help : helpList) {
				sysMethodHelpDao.remove(help);
			}
			
			sysMethodInfoDao.remove(method);
		}
	}

	/**
	 * 启用/禁用功能菜单
	 */
	public void setMethodStatus(String[] ids) {
		for (String s : ids) {
			SysMethodInfo method = sysMethodInfoDao.getByPK(s);
			if(method.getIsAction() == EnumUtil.SYS_ISACTION.Vaild.value){
				method.setIsAction(EnumUtil.SYS_ISACTION.No_Vaild.value);
			}else{
				method.setIsAction(EnumUtil.SYS_ISACTION.Vaild.value);
			}
			sysMethodInfoDao.save(method);
		}
	}

	public List<SysConfig> listSysConfig() {
	    List<Object[]> list = sysConfigDao.findBySqlObjList("select mt.method_info_id,mt.method_info_name,cf.config_id,cf.project_name,cf.project_egname,cf.project_view from sys_method_info  mt left join sys_config cf on mt.method_info_id = cf.method_id where mt.level_unit='-1' ");
	    List<SysConfig> confingList = new ArrayList<SysConfig>();
		
	    for (Object[] objects : list) {
			SysConfig sc = new SysConfig();
			sc.setPrimaryKey(objects[2]==null?0:Long.parseLong(objects[2].toString()));
			sc.setMethodId(objects[0]==null?"":objects[0].toString());
			sc.setMethodInfoName(objects[1]==null?"":objects[1].toString());
			sc.setProjectName(objects[3]==null?"":objects[3].toString());
			sc.setProjectEgName(objects[4]==null?"":objects[4].toString());
			sc.setProjectView(objects[5]==null?"":objects[5].toString());	
			confingList.add(sc);
		}
	    
	    return confingList;
	}

	/**
	 * 新增系统配置
	 */
	public void addSysConfig(SysConfig[] cons) {
		for(int i = 0;i<cons.length;i++){
	        sysConfigDao.save(cons[i]);			
		}
	}

	/**
	 * 根据主键获取功能目录相关信息
	 */
	public SysMethodInfo getSysMethodInfoByPK(String sid) {
		SysMethodInfo info = sysMethodInfoDao.getByPK(sid);
		if(info != null){
			return info;
		}
		return null;
	}

	public SysMethodInfo getSysMethodInfoByUri(String uri){
		List<SysMethodInfo> list = sysMethodInfoDao.findByPropertyFuzzy("methodUri", uri);
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	public SysMethodInfo saveSysMethodInfo(SysMethodInfo info) {
		SysMethodInfo methodinfo = (SysMethodInfo) sysMethodInfoDao.save(info);
		return methodinfo;
	}

	/**
	 * 根据上级节点统计上级目录有多少下级目录
	 */
	public int getSysMethodInfoByLevelUnit(String levelUnit) {
		int count = sysMethodInfoDao.findByHqlWhereCount(" and model.levelUnit = '"+levelUnit+"'");
		return count;
	}

	public void deleteSysMethodInfoById(String sid) {
		SysMethodInfo info = sysMethodInfoDao.getByPK(sid);
		
		//删除功能相关按钮
		List<SysMethodBtn> list = sysMethodBtnDao.findByProperty("methodId", info.getPrimaryKey());
		for (SysMethodBtn btn : list) {
			sysMethodBtnDao.remove(btn);
		}
		
		//删除功能相关提示信息
		List<SysMethodHelp> helpList = sysMethodHelpDao.findByProperty("methodId", info.getPrimaryKey());
		for (SysMethodHelp help : helpList) {
			sysMethodHelpDao.remove(help);
		}
		
		sysMethodInfoDao.remove(info);
	}

	/**
	 * 判断功能目录主键是否重复
	 */
	public boolean getCountBySysMethodInfoPK(String pk) {
		int count = sysMethodInfoDao.findByHqlWhereCount(" and model.primaryKey = '" + pk + "' ");
		if (count >= 1) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 根据上级目录和目录名称统计功能目录数量
	 */
	public int getSysMethodInfoByNameAndLevelUnit(String name,String levelUnit, String PK) {
		int count = 0;
		if(PK != null && PK.length() > 0){
			count = sysMethodInfoDao.findByHqlWhereCount(" and model.methodInfoName='"+name+"' and model.levelUnit='"+levelUnit+"' and model.primaryKey<> '"+PK+"'");
		}else{
			count = sysMethodInfoDao.findByHqlWhereCount(" and model.methodInfoName='"+name+"' and model.levelUnit='"+levelUnit+"'");
		}
		return count;
	}

	/**
	 * 根据节点查询下级信息
	 */
	public List<SysMethodInfo> getSysMethodInfoListByPK(String pk) {
		List<SysMethodInfo> list = sysMethodInfoDao.findByHqlWhere(" and model.primaryKey like '"+pk+"%'");
		return list;
	}

	public void deleteColumnControlById(long[] pks) {
		for (long l : pks) {
			SysColumnControl column = sysColumnControlDao.getByPK(l);
			sysColumnControlDao.remove(column);
		}
	}

	public SysColumnControl saveColumnControl(SysColumnControl column) {
		return (SysColumnControl) sysColumnControlDao.save(column);
	}
	
	
	public SysLibraryStandard saveSysLibraryStandard(SysLibraryStandard libraryInfo) {
		SysLibraryStandard info = (SysLibraryStandard) sysLibraryStandardDao.save(libraryInfo);
		return info;
	}
	
	public List<SysLibraryStandard> getSysLibraryStandardInfoPager(SysLibraryStandard libraryInfo, Pager pager) {
		List<SysLibraryStandard> libraryList = sysLibraryStandardDao.findByHqlWherePage(SystemPack.packSysLibraryStandardInfo(libraryInfo), pager);
		return libraryList;
	}
	
	public int getSysLibraryStandardCount(SysLibraryStandard libraryInfo) {
		int count = sysLibraryStandardDao.findByHqlWhereCount(SystemPack.packSysLibraryStandardInfo(libraryInfo));
		return count;
	}
	
	public void deleteSysLibraryStandardByPks(long[] pks) {
		for (long l : pks) {
			SysLibraryStandard library = sysLibraryStandardDao.getByPK(l);
			sysLibraryStandardDao.remove(library);
		}
	}
	
	public SysLibraryStandard getSysLibraryStandardByPk(long pk) {
		return sysLibraryStandardDao.getByPK(pk);
	}
	
	public SysLibraryStandard getSysLibraryStandardByCode(String code) {
		SysLibraryStandard info = null;
		List<SysLibraryStandard> list = sysLibraryStandardDao.findByHqlWhere(" and model.libraryCode = '" + code + "'");
		if (list.size() > 0) {
			info = list.get(0);
		}
		return info;
	}
	
	public SysLibraryStandard getSysLibraryStandardByCode(String code, Integer value) {
		SysLibraryStandard info = null;
		List<SysLibraryStandard> list = sysLibraryStandardDao.findByHqlWhere(" and model.libraryCode = '" + code + "%' and model.libraryStandCode = "+value);
		if (list.size() > 0) {
			info = list.get(0);
		}
		return info;
	}
	
	public List<SysLibraryStandard> getSysLibraryStandardInfo(SysLibraryStandard libraryInfo) {
		List<SysLibraryStandard> libraryList = sysLibraryStandardDao.findByHqlWhere(SystemPack.packSysLibraryStandardInfo(libraryInfo));
		return libraryList;
	}

	public List<SysLibraryStandard> getDownSysLibraryStandardByCode(String libraryCode) {
		List<SysLibraryStandard> list = sysLibraryStandardDao.findByHqlWhere(" and model.libraryUpcode = '"+libraryCode+"' order by model.libraryStandCode asc");
		return list;
	}
	
	public List<SysLibraryStandard> getDownSysLibraryStandardByCodeAll(String libraryCode) {
		List<SysLibraryStandard> list = sysLibraryStandardDao.findByHqlWhere(" and model.libraryUpcode like '"+libraryCode+"%' order by model.libraryStandCode asc");
		return list;
	}

	public int getDownSysLibraryStandardByCodeCount(String libraryCode) {
		int count = sysLibraryStandardDao.findByHqlWhereCount(" and model.libraryUpcode = '"+libraryCode+"'");
		return count;
	}
	
	public int listSysMethodHelpCount(SysMethodHelp methodHelp){
		int count = sysMethodHelpDao.findBySqlCount(SystemPack.packSysMethodHelp(methodHelp));
		return count;
	}
	
	public List<SysMethodHelp> listSysMethodHelpByPager(SysMethodHelp methodHelp, Pager pager){
		List<SysMethodHelp> list = sysMethodHelpDao.findBySqlPage(SystemPack.packSysMethodHelp(methodHelp),SysMethodHelp.class,pager);
		return list;
	}
	
	public void deleteMethodHelpByIds(long[] pks){
		for (long l : pks) {
			SysMethodHelp methodHelp = sysMethodHelpDao.getByPK(l);
			sysMethodHelpDao.remove(methodHelp);
		}
	}
	
	public SysMethodHelp saveSysMethodHelp(SysMethodHelp methodHelp){
		return (SysMethodHelp) sysMethodHelpDao.save(methodHelp);
	}
	
	public SysMethodHelp getSysMethodHelpByPk(long pk){
		return sysMethodHelpDao.getByPK(pk);
	}
	
	public List<SysMethodHelp> listSysMethodHelpByMethodId(String methodId){
		return sysMethodHelpDao.findByHqlWhere(" and model.methodId = '"+methodId+"' order by model.priority asc");
	}
	
	
	
	public int listSysMethodBtnCount(SysMethodBtn methodBtn){
		int count = sysMethodBtnDao.findBySqlCount(SystemPack.packSysMethodBtn(methodBtn));
		return count;
	}
	
	public List<SysMethodBtn> listSysMethodBtnByPager(SysMethodBtn methodBtn, Pager pager){
		List<SysMethodBtn> list = sysMethodBtnDao.findBySqlPage(SystemPack.packSysMethodBtn(methodBtn),SysMethodHelp.class,pager);
		return list;
	}
	
	public void deleteMethodBtnByIds(long[] pks){
		for (long l : pks) {
			SysMethodBtn methodBtn = sysMethodBtnDao.getByPK(l);
			sysMethodBtnDao.remove(methodBtn);
		}
	}
	
	public SysMethodBtn saveSysMethodBtn(SysMethodBtn methodBtn){
		return (SysMethodBtn) sysMethodBtnDao.save(methodBtn);
	}
	
	public SysMethodBtn getSysMethodBtnByPk(long pk){
		return sysMethodBtnDao.getByPK(pk);
	}
	
	public List<SysMethodBtn> listSysMethodBtnByMethodId(String methodId){
		return sysMethodBtnDao.findByHqlWhere(" and model.methodId = '"+methodId+"' order by model.priority asc");
	}
	
	
	public int listSysMethodShortcutCount(SysMethodShortcut sysMethodShortcut){
      int count = sysMethodShortcutDao.findByHqlWhereCount(SystemPack.packSysMethodShortcutQuery(sysMethodShortcut));
      return count;
   }

   public List<SysMethodShortcut> listSysMethodShortcut(SysMethodShortcut sysMethodShortcut, Pager pager){
      List<SysMethodShortcut> list = sysMethodShortcutDao.findByHqlWherePage(SystemPack.packSysMethodShortcutQuery(sysMethodShortcut), pager);
      return list;
   }
   
   public List<SysMethodShortcut> listAllSysMethodShortcut(String employeeId){
	   return sysMethodShortcutDao.findByHqlWhere(" and model.empId = '"+employeeId+"' order by model.primaryKey desc");
   }
   public List<SysMethodShortcut> listSysMethodAutoOpen(String employeeId, Integer autoOpen){
	   return sysMethodShortcutDao.findByHqlWhere(" and model.empId = '"+employeeId+"' and model.autoOpen = "+autoOpen);
   }

   public SysMethodShortcut saveSysMethodShortcut(SysMethodShortcut sysMethodShortcut){
      SysMethodShortcut temp = (SysMethodShortcut)sysMethodShortcutDao.save(sysMethodShortcut);
      return temp;
   }

   public SysMethodShortcut getSysMethodShortcutByPk(long pk){
      SysMethodShortcut sysMethodShortcut = (SysMethodShortcut)sysMethodShortcutDao.getByPK(pk);
      return sysMethodShortcut;
   }

   public void deleteSysMethodShortcutByPks(long[] pks){
      for (long l : pks) {
         SysMethodShortcut sysMethodShortcut = sysMethodShortcutDao.getByPK(l);
         sysMethodShortcutDao.remove(sysMethodShortcut);
      }
   }
   public int checkSysMethodShortcut(String empId, String methodId){
	   int count = sysMethodShortcutDao.findByHqlWhereCount(" and model.empId = '"+empId+"' and model.methodId = '"+methodId+"'");
	   return count;
   }
   
   public int listSysLogRuntimeCount(SysLogRuntime sysLogRuntime){
      int count = sysLogRuntimeDao.findByHqlWhereCount(SystemPack.packSysLogRuntimeQuery(sysLogRuntime));
      return count;
   }

   public List<SysLogRuntime> listSysLogRuntime(SysLogRuntime sysLogRuntime, Pager pager){
      List<SysLogRuntime> list = sysLogRuntimeDao.findByHqlWherePage(SystemPack.packSysLogRuntimeQuery(sysLogRuntime), pager);
      return list;
   }
   
   public SysLogRuntime getSysLogRuntimeByPk(long pk){
      SysLogRuntime sysLogRuntime = (SysLogRuntime)sysLogRuntimeDao.getByPK(pk);
      return sysLogRuntime;
   }
   
   public void deleteSysLogRuntimeByPks(long[] pks){
      for (long l : pks) {
         SysLogRuntime sysLogRuntime = sysLogRuntimeDao.getByPK(l);
         sysLogRuntimeDao.remove(sysLogRuntime);
      }
   }
   
   public List<SysMethodInfo> listSysmethodInfoByPage(String page){
	   return sysMethodInfoDao.findByHqlWhere(" and model.methodPages like '%"+page+"%'");
   }
   
}
