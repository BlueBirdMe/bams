package com.pinhuba.web.controller.dwr;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.pinhuba.common.util.security.MD5;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import com.pinhuba.common.module.OnlineHrmEmployeeBean;
import com.pinhuba.common.module.ResultBean;
import com.pinhuba.common.module.SessionUser;
import com.pinhuba.common.module.UserMethodsInfo;
import com.pinhuba.common.pages.Pager;
import com.pinhuba.common.pages.PagerHelper;
import com.pinhuba.common.util.ConvertPinyin;
import com.pinhuba.common.util.EnumUtil;
import com.pinhuba.common.util.LoginContext;
import com.pinhuba.common.util.UtilTool;
import com.pinhuba.common.util.UtilWork;
import com.pinhuba.common.util.WebUtilWork;
import com.pinhuba.common.util.security.Base64;
import com.pinhuba.core.iservice.ISysProcessService;
import com.pinhuba.core.iservice.IUserLoginService;
import com.pinhuba.core.pojo.HrmEmployee;
import com.pinhuba.core.pojo.SysColumnControl;
import com.pinhuba.core.pojo.SysCompanyInfo;
import com.pinhuba.core.pojo.SysConfig;
import com.pinhuba.core.pojo.SysException;
import com.pinhuba.core.pojo.SysHelp;
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
import com.pinhuba.web.listener.OnlineUserBindingListener;

/**
 * 系统管理
 */
public class DwrSysProcessService {
	private final static Logger logger = Logger.getLogger(DwrSysProcessService.class);
	@Resource
	private ISysProcessService sysProcessService;
	@Resource
	private IUserLoginService userLoginService;

	public ResultBean saveColumnControl(ServletContext context, HttpServletRequest request, SysColumnControl column) {
		sysProcessService.saveColumnControl(column);
		return WebUtilWork.WebResultPack(null);
	}

	public ResultBean getColumnControlByPK(ServletContext context, HttpServletRequest request, long id) {
		SysColumnControl column = sysProcessService.getSysColumnControl(id);
		return WebUtilWork.WebObjectPack(column);
	}

	/**
	 * 显示所有列
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean listSysColumnControl(ServletContext context, HttpServletRequest request, SysColumnControl sysColumnControl, Pager pager) {
		List<SysColumnControl> list = null;
		pager = PagerHelper.getPager(pager, sysProcessService.listAllSysColumnControlCount(sysColumnControl));
		list = sysProcessService.listAllSysColumnControlPager(sysColumnControl, pager);
		logger.info("显示所有列...");
		return WebUtilWork.WebResultPack(list, pager);
	}

	public ResultBean deleteColumnControlById(ServletContext context, HttpServletRequest request, long[] pks) {
		sysProcessService.deleteColumnControlById(pks);
		return WebUtilWork.WebResultPack(null);
	}

	public ResultBean setColumnInfo(ServletContext context, HttpServletRequest request, long[] ids) {
		for (long id : ids) {
			SysColumnControl column = sysProcessService.getSysColumnControl(id);
			if (column.getIsShow() == EnumUtil.SYS_ISEDIT.EDIT.value) {
				column.setIsShow(EnumUtil.SYS_ISEDIT.No_EDIT.value);
			} else {
				column.setIsShow(EnumUtil.SYS_ISEDIT.EDIT.value);
			}
			sysProcessService.saveColumnControl(column);
		}
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 设置排序
	 * 
	 * @param context
	 * @param request
	 * @param id
	 * @param flag
	 * @return
	 */
	public ResultBean setPriority(ServletContext context, HttpServletRequest request, long id, int flag) {
		SysColumnControl column = sysProcessService.getSysColumnControl(id);
		if (flag == EnumUtil.Tree_MOVE_Type.MOVE_DOWN.value) {
			column.setPriority(column.getPriority() + 1);
		} else if (flag == EnumUtil.Tree_MOVE_Type.MOVE_UP.value && column.getPriority() > 0) {
			column.setPriority(column.getPriority() - 1);
		}
		sysProcessService.saveColumnControl(column);
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 根据表名显示列
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public List<SysColumnControl> listColumn(SysColumnControl sysColumnControl) {
		List<SysColumnControl> list = sysProcessService.listAllSysColumnControl(sysColumnControl);
		logger.info("显示所有列...");
		return list;
	}

	/**
	 * 分页查询所有功能目录
	 * 
	 * @param context
	 * @param request
	 * @param methodinfo
	 * @param pager
	 * @return
	 */
	public ResultBean getSysMethodList(ServletContext context, HttpServletRequest request, SysMethodInfo methodinfo, Pager pager) {
		pager = PagerHelper.getPager(pager, sysProcessService.listAllSysMethodInfoCount(methodinfo));
		List<SysMethodInfo> list = sysProcessService.listAllSysMethodInfoPager(methodinfo, pager);
		for (SysMethodInfo method : list) {
			method.setUpSysMethodInfo(sysProcessService.getSysMethodInfoByPK(method.getLevelUnit()));
		}
		logger.info("显示所有目录信息...");
		return WebUtilWork.WebResultPack(list, pager);
	}

	public int getSysUserInfoCountByCompanyId(ServletContext context, HttpServletRequest request) {
		return sysProcessService.getSysUserInfoCountByCompanyId(UtilTool.getCompanyId(request));
	}

	public int getCompanyUserMaxCount(ServletContext context, HttpServletRequest request) {
		return sysProcessService.getCompanyUserMaxCount(UtilTool.getCompanyId(request));
	}

	/**
	 * 根据编码获取工程信息
	 * 
	 * @param code
	 * @return
	 */
	public SysConfig getSysconfigByCode(String code) {
		return sysProcessService.getSysconfigByCode(code);
	}

	public SysMethodInfo getMethodInfoByPk(String pk) {
		return userLoginService.getMethodInfoByPk(pk);
	}

	public List<SysMethodInfo> getSysMethodInfoByUpCode(String code) {
		List<SysMethodInfo> methodList = null;

		// 限制目录层级，和权限分配有关
		SysMethodInfo tmp = sysProcessService.getSysMethodInfoByPK(code);
		if (tmp != null && tmp.getMethodLevel() == EnumUtil.SYS_METHOD_LEVEL.TWO.value) {
			methodList = new ArrayList<SysMethodInfo>();
		} else {
			methodList = sysProcessService.getMethodInfoListByUpCode(code);
		}
		return methodList;
	}

	public int getSysMethodInfoByUpCodeCount(String code) {
		int count = 0;
		SysMethodInfo tmp = sysProcessService.getSysMethodInfoByPK(code);
		if (tmp != null && tmp.getMethodLevel() == EnumUtil.SYS_METHOD_LEVEL.TWO.value) {
			return count;
		}
		return sysProcessService.getMethodInfoListByUpCodeCount(code);
	}

	public List<SysLibraryInfo> getSysLibraryInfoListBytree(HttpServletRequest request, SysLibraryInfo libraryInfo) {
		List<SysLibraryInfo> list = sysProcessService.getSysLibraryInfoByInfo(libraryInfo);
		return list;
	}

	public int getSysLibraryInfoCountListBytree(HttpServletRequest request, SysLibraryInfo libraryInfo) {
		int count = sysProcessService.getSysLibraryInfoCountByInfo(libraryInfo);
		return count;
	}

	public ResultBean getSysLibraryInfoListByPager(ServletContext context, HttpServletRequest request, SysLibraryInfo libraryInfo, Pager pager) {
		pager = PagerHelper.getPager(pager, sysProcessService.getSysLibraryInfoCountByInfo(libraryInfo));
		List<SysLibraryInfo> list = sysProcessService.getSysLibraryInfoByInfoPager(libraryInfo, pager);
		return WebUtilWork.WebResultPack(list, pager);
	}

	public ResultBean saveSysLibrayInfo(ServletContext context, HttpServletRequest request, SysLibraryInfo libraryInfo) {
		// 上级编码
		String code = UtilTool.getCodeByUpCode(context, request, libraryInfo.getLibraryInfoUpcode(), "sys_library_info", "library_info_code", "library_info_upcode");

		libraryInfo.setLibraryInfoCode(code);
		sysProcessService.saveSysLibraryInfo(libraryInfo);

		return WebUtilWork.WebResultPack(null);
	}

	public ResultBean updateSysLibraryInfo(ServletContext context, HttpServletRequest request, SysLibraryInfo libraryInfo) {
		sysProcessService.saveSysLibraryInfo(libraryInfo);
		return WebUtilWork.WebResultPack(null);
	}

	public ResultBean setSysLibraryInfoBypk(ServletContext context, HttpServletRequest request, long pk, int status, int type) {
		sysProcessService.setLibraryInfoStatusByPk(pk, status, type);
		return WebUtilWork.WebResultPack(null);
	}

	public ResultBean setSysLibraryInfoByPks(ServletContext context, HttpServletRequest request, long[] pks, int type) {
		sysProcessService.setLibraryInfoBatchByPks(pks, type);
		return WebUtilWork.WebResultPack(null);
	}

	public ResultBean deleteSysLibraryInfoByPks(ServletContext context, HttpServletRequest request, long[] pks) {
		sysProcessService.deleteLibraryInfoBatchByPks(pks);
		return WebUtilWork.WebResultPack(null);
	}

	public ResultBean getSysLibraryInfoByPk(ServletContext context, HttpServletRequest request, long pk) {
		SysLibraryInfo libraryInfo = sysProcessService.getSysLibraryInfoByPk(pk);
		if (libraryInfo.getLibraryInfoUpcode() != null && libraryInfo.getLibraryInfoUpcode().length() > 0) {
			SysLibraryInfo tmp = new SysLibraryInfo();
			tmp.setLibraryInfoCode(libraryInfo.getLibraryInfoUpcode());
			libraryInfo.setUpSysLibraryInfo(sysProcessService.getSysLibraryInfoByCodAndCompanyId(tmp));
		}
		List<SysLibraryInfo> list = new ArrayList<SysLibraryInfo>();
		list.add(libraryInfo);
		return WebUtilWork.WebResultPack(list);
	}

	public ResultBean saveSysUsers(ServletContext context, HttpServletRequest request, String[] empids, String[] usernames, String[] userpwds) {
		String empid = UtilTool.getEmployeeId(request);
		String nowtime = UtilWork.getNowTime();
		int companyId = UtilTool.getCompanyId(request);
		// 是否包含用户数限制
		int max = sysProcessService.getCompanyUserMaxCount(companyId);
		if (max != 0) {
			int cuser = sysProcessService.getSysUserInfoCountByCompanyId(companyId);
			if (max <= cuser) {
				return new ResultBean(false, "允许的最大用户数为:" + max + ",不能添加!");
			} else if (max <= cuser + empids.length) {
				return new ResultBean(false, "允许的最大用户数为:" + max + ",已添加用户数为:" + cuser + ",<br/>能够添加的用户数为:" + String.valueOf(max - cuser));
			}
		}
		ArrayList<SysUserInfo> userlist = new ArrayList<SysUserInfo>();
		for (int i = 0; i < empids.length; i++) {
			SysUserInfo user = new SysUserInfo();
			user.setHrmEmployeeId(empids[i]);
			user.setUserName(usernames[i]);
//			user.setUserpassword(Base64.getBase64FromString(userpwds[i]));
			user.setUserpassword(MD5.encrypt(userpwds[i]).toLowerCase());
			user.setUserAction(EnumUtil.SYS_ISACTION.Vaild.value);
			user.setUserType(EnumUtil.SYS_USER_TYPE.DEFAULT.value);
			user.setRecordId(empid);
			user.setRecordDate(nowtime);
			user.setLastmodiId(empid);
			user.setLastmodiDate(nowtime);
			user.setCompanyId(companyId);
			userlist.add(user);
		}
		sysProcessService.saveSysUserInfo(userlist);
		return WebUtilWork.WebResultPack(null);
	}

	public ResultBean saveSysUser(ServletContext context, HttpServletRequest request, SysUserInfo user) {
		// 判断用户名是否已用
		String empid = UtilTool.getEmployeeId(request);
		String nowtime = UtilWork.getNowTime();
		int companyId = UtilTool.getCompanyId(request);
		if (user.getPrimaryKey() > 0) {
			SysUserInfo tmp = sysProcessService.getSysUserInfoByPk(user.getPrimaryKey(), false);
			int usercount = sysProcessService.getSysUserInfoByUserNameEdit(user.getUserName().trim(), user.getPrimaryKey(), companyId);
			if (usercount > 0) {
				return new ResultBean(false, "该用户名已存在!");
			}
			if (user.getUserName() != null && user.getUserName().trim().length() > 0) {
				tmp.setUserName(user.getUserName().trim());
			}
			if (user.getUserpassword() != null && user.getUserpassword().trim().length() > 0) {
//				tmp.setUserpassword(Base64.getBase64FromString(user.getUserpassword().trim()));
				tmp.setUserpassword(MD5.encrypt(user.getUserpassword().trim()).toLowerCase());
			}
			if (user.getUserAction() != null && user.getUserAction().intValue() > 0) {
				tmp.setUserAction(user.getUserAction());
			}
			tmp.setLastmodiId(empid);
			tmp.setLastmodiDate(nowtime);
			sysProcessService.saveSysUserInfo(tmp);
		} else {
			// 是否包含用户数限制
			int max = sysProcessService.getCompanyUserMaxCount(companyId);
			if (max != 0) {
				int cuser = sysProcessService.getSysUserInfoCountByCompanyId(companyId);
				if (max <= cuser) {
					return new ResultBean(false, "允许的最大用户数为:" + max + ",不能添加!");
				}
			}
			int empcount = sysProcessService.getSysUserInfoByEmpId(user.getHrmEmployeeId(), companyId);
			if (empcount > 0) {
				return new ResultBean(false, "该人员已注册为用户!");
			}
			int usercount = sysProcessService.getSysUserInfoByUserName(user.getUserName().trim(), companyId);
			if (usercount > 0) {
				return new ResultBean(false, "该用户名已存在!");
			}
//			String newpwd = Base64.getBase64FromString(user.getUserpassword().trim());
			String newpwd = MD5.encrypt(user.getUserpassword().trim()).toLowerCase();
			user.setUserpassword(newpwd);
			user.setRecordId(empid);
			user.setRecordDate(nowtime);
			user.setLastmodiId(empid);
			user.setLastmodiDate(nowtime);
			user.setCompanyId(companyId);
			sysProcessService.saveSysUserInfo(user);
		}
		return WebUtilWork.WebResultPack(null);
	}

	public ResultBean updateSysUserPassword(ServletContext context, HttpServletRequest request, String oldpwd, String newpwd) {
		SessionUser user = (SessionUser) LoginContext.getSessionValueByLogin(request);
		SysUserInfo userinfo = user.getUserInfo();
		SysUserInfo tmp = sysProcessService.getSysUserInfoByPk(userinfo.getPrimaryKey(), false);
		if (MD5.encrypt(oldpwd.trim()).toLowerCase().equals(tmp.getUserpassword())) {
			tmp.setUserpassword(MD5.encrypt(newpwd.trim()).toLowerCase());
			tmp.setLastmodiId(UtilTool.getEmployeeId(request));
			tmp.setLastmodiDate(UtilWork.getNowTime());
			sysProcessService.saveSysUserInfo(tmp);
		} else {
			return new ResultBean(false, "旧密码输入错误!");
		}
		return WebUtilWork.WebResultPack(null);
	}

	public ResultBean resetSysUserPassword(ServletContext context, HttpServletRequest request, long[] ids) {
		for (long l : ids) {
			SysUserInfo userInfo = sysProcessService.getSysUserInfoByPk(l, false);
			// 根据配置表中，获取初始密码
			String defaultPassword = UtilTool.getSysParamByIndex(request, "erp.user.initPwd");
			userInfo.setUserpassword(MD5.encrypt(defaultPassword).toLowerCase());
			sysProcessService.saveSysUserInfo(userInfo);
		}
		return WebUtilWork.WebResultPack(null);
	}

	public int vaildSysUserInfoByEmpId(ServletContext context, HttpServletRequest request, String empid) {
		return sysProcessService.getSysUserInfoByEmpId(empid, UtilTool.getCompanyId(request));
	}

	public ResultBean vaildSysUserInfoByEmpIds(ServletContext context, HttpServletRequest request, String ids, String names, boolean convertpingyin, boolean showname) throws Exception {
		List<String[]> list = new ArrayList<String[]>();
		String[] empids = ids.split(",");
		String[] empnames = names.split(",");
		for (int i = 0; i < empids.length; i++) {
			if (empids[i] != null && empids[i].length() > 0) {
				int count = sysProcessService.getSysUserInfoByEmpId(empids[i], UtilTool.getCompanyId(request));
				if (showname && count > 0) {
					continue;
				}
				String[] tmps = new String[5];
				tmps[0] = empids[i];
				tmps[1] = empnames[i];
				String str = "";
				if (convertpingyin) {
					str = ConvertPinyin.getPinyin(empnames[i]);
				}
				tmps[2] = str;
				tmps[3] = UtilTool.getSysParamByIndex(request, "erp.user.initPwd");
				tmps[4] = String.valueOf(count);
				list.add(tmps);
			}
		}
		return WebUtilWork.WebResultPack(list);
	}

	public int vaildSysUserInfoByUserName(ServletContext context, HttpServletRequest request, String username) {
		return sysProcessService.getSysUserInfoByUserName(username, UtilTool.getCompanyId(request));
	}

	public Integer[] vaildSysUserInfoByUserNames(ServletContext context, HttpServletRequest request, String[] usernames) {
		Integer[] count = new Integer[usernames.length];
		for (int i = 0; i < usernames.length; i++) {
			int c = sysProcessService.getSysUserInfoByUserName(usernames[i], UtilTool.getCompanyId(request));
			count[i] = c;
			if (c > 0) {
				break;
			}
		}
		return count;
	}

	public int vaildSysUserInfoByUserNameEdit(ServletContext context, HttpServletRequest request, String username) {
		SessionUser user = (SessionUser) LoginContext.getSessionValueByLogin(request);
		return sysProcessService.getSysUserInfoByUserNameEdit(username.trim(), user.getUserInfo().getPrimaryKey(), UtilTool.getCompanyId(request));
	}

	public ResultBean updateSysUserName(ServletContext context, HttpServletRequest request, String userpwd, String username) {
		SessionUser user = (SessionUser) LoginContext.getSessionValueByLogin(request);
		SysUserInfo userinfo = user.getUserInfo();
		SysUserInfo tmp = sysProcessService.getSysUserInfoByPk(userinfo.getPrimaryKey(), false);
		if (MD5.encrypt(userpwd.trim()).toLowerCase().equals(tmp.getUserpassword())) {
			int usercount = sysProcessService.getSysUserInfoByUserNameEdit(username.trim(), userinfo.getPrimaryKey(), UtilTool.getCompanyId(request));
			if (usercount > 0) {
				return new ResultBean(false, "该用户名已存在!");
			}
			tmp.setUserName(username.trim());
			tmp.setLastmodiId(UtilTool.getEmployeeId(request));
			tmp.setLastmodiDate(UtilWork.getNowTime());
			sysProcessService.saveSysUserInfo(tmp);
		} else {
			return new ResultBean(false, "密码输入错误!");
		}
		return WebUtilWork.WebResultPack(null);
	}

	public boolean vaildSysUserInfoOldPwd(ServletContext context, HttpServletRequest request, String oldpwd) {
		SessionUser user = (SessionUser) LoginContext.getSessionValueByLogin(request);
		SysUserInfo tmp = sysProcessService.getSysUserInfoByPk(user.getUserInfo().getPrimaryKey(), false);
		boolean bl = false;
		if (MD5.encrypt(oldpwd.trim()).toLowerCase().equals(tmp.getUserpassword())) {
			bl = true;
		}
		return bl;
	}

	public ResultBean listSysUserInfo(ServletContext context, HttpServletRequest request, SysUserInfo userinfo, Pager pager) {
		userinfo.setCompanyId(UtilTool.getCompanyId(request));
		pager = PagerHelper.getPager(pager, sysProcessService.getSysUserInfoListCount(userinfo));
		List<SysUserInfo> userList = sysProcessService.getSysUserInfoListByPager(userinfo, pager);
		for (SysUserInfo sysUserInfo : userList) {
			sysUserInfo.setUserpassword(MD5.encrypt(sysUserInfo.getUserpassword()).toLowerCase());
		}
		return WebUtilWork.WebResultPack(userList, pager);
	}

	/**
	 * 设置用户有效性
	 * 
	 * @param context
	 * @param request
	 * @param ids
	 *            主键
	 * @param isaction
	 * @return
	 */
	public ResultBean setSysUserInfoIsAction(ServletContext context, HttpServletRequest request, long[] ids, int isaction) {
		for (long l : ids) {
			SysUserInfo userInfo = sysProcessService.getSysUserInfoByPk(l, true);
			if (userInfo.getEmployee().getHrmEmployeeActive() == EnumUtil.SYS_ISACTION.No_Vaild.value) {
				return new ResultBean(false, userInfo.getUserName() + "用户人员已离职或人员信息被删除，不能进行有效性设置!");
			}
		}

		sysProcessService.updateSysUserInfoIsaction(ids, isaction, UtilTool.getEmployeeId(request));
		return WebUtilWork.WebResultPack(null);
	}

	public ResultBean getUserGroupDetailList(ServletContext context, HttpServletRequest request, String ids, String names, boolean isshow) {
		List<String[]> list = new ArrayList<String[]>();
		String[] userids = ids.split(",");
		String[] empnames = names.split(",");
		for (int i = 0; i < userids.length; i++) {
			if (userids[i] != null && userids[i].length() > 0) {
				// 查询用户是否已编组
				List<SysUserGroupDetail> detailList = sysProcessService.getSysUserGroupDetailList(Integer.parseInt(userids[i]));
				if (isshow && detailList.size() > 0) {
					continue;
				}
				String[] tmps = new String[3];
				tmps[0] = userids[i];
				tmps[1] = empnames[i];
				String str = "";
				for (SysUserGroupDetail detail : detailList) {
					str += detail.getUserGroup().getGroupName() + "<br/>";
				}
				tmps[2] = str;
				list.add(tmps);
			}
		}
		return WebUtilWork.WebResultPack(list);
	}

	public ResultBean saveSysUserGroupAndDetail(ServletContext context, HttpServletRequest request, SysUserGroup userGroup, String[] userIds) {
		String empid = UtilTool.getEmployeeId(request);
		String nowtime = UtilWork.getNowTime();
		if (userGroup.getPrimaryKey() > 0) {
			// 删除原明细
			sysProcessService.updateSysUsergroupAndDetail(userGroup, userIds, empid, nowtime);
		} else {
			userGroup.setCompanyId(UtilTool.getCompanyId(request));
			userGroup.setRecordId(empid);
			userGroup.setRecordDate(nowtime);
			userGroup.setLastmodiId(empid);
			userGroup.setLastmodeDate(nowtime);
			sysProcessService.saveSysUsergroupAndDetail(userGroup, userIds);
		}
		return WebUtilWork.WebResultPack(null);
	}

	public ResultBean listSysUserGroupBypager(ServletContext context, HttpServletRequest request, SysUserGroup group, Pager pager) {
		group.setCompanyId(UtilTool.getCompanyId(request));
		pager = PagerHelper.getPager(pager, sysProcessService.listSysUserGroupCount(group));
		List<SysUserGroup> list = sysProcessService.listSysUserGroupBypager(group, pager);
		return WebUtilWork.WebResultPack(list, pager);
	}

	public List<SysUserGroup> listSysUserGroupAll(ServletContext context, HttpServletRequest request) {
		SysUserGroup group = new SysUserGroup();
		group.setCompanyId(UtilTool.getCompanyId(request));
		List<SysUserGroup> list = sysProcessService.listSysUserGroupAll(group);
		return list;
	}

	public ResultBean deleteSysUserGroupByIds(ServletContext context, HttpServletRequest requset, long[] ids) {
		sysProcessService.deleteSysUserGroupByIds(ids);
		return WebUtilWork.WebResultPack(null);
	}

	public ResultBean getSysUserGroupByPk(long pk) {
		return WebUtilWork.WebObjectPack(sysProcessService.getSysUserGroupByPk(pk));
	}

	public ResultBean getEmployeeNameByUserIds(ServletContext context, HttpServletRequest request, String userids) {
		List<Object[]> list = sysProcessService.getEmployeeNameByuserIds(userids, UtilTool.getCompanyId(request));
		List<String> nameList = new ArrayList<String>();
		for (Object[] obj : list) {
			nameList.add((String) obj[0]);
		}
		return WebUtilWork.WebResultPack(nameList);
	}

	public ResultBean getSysMethodInfoList(ServletContext context, HttpServletRequest request, String upcode, int level) {
		List<SysMethodInfo> list = sysProcessService.getSysMethodInfoListByCode(upcode, level);
		for (SysMethodInfo sysMethodInfo : list) {
			List<SysMethodBtn> btns = sysProcessService.listSysMethodBtnByMethodId(sysMethodInfo.getPrimaryKey());
			sysMethodInfo.setBtns(btns);
		}
		return WebUtilWork.WebResultPack(list);
	}

	public ResultBean saveRoleAndDetailBind(ServletContext context, HttpServletRequest request, SysRole role, SysRoleBind[] binds, String[] methodIds, String[] btnIds) {
		int companyId = UtilTool.getCompanyId(request);
		String empId = UtilTool.getEmployeeId(request);
		role = sysProcessService.saveRoleAndDetailBind(companyId, empId, role, binds, methodIds, btnIds);
		return WebUtilWork.WebObjectPack(role);
	}

	public ResultBean listSysRoledetailByRoleId(int roleId) {
		List<SysRoleDetail> detailList = sysProcessService.getSysRoleDetailList(roleId);
		return WebUtilWork.WebResultPack(detailList);
	}
	
	public ResultBean listSysRoleBtnByRoleId(int roleId) {
		List<SysRoleBtn> btnList = sysProcessService.getSysRoleBtnList(roleId);
		return WebUtilWork.WebResultPack(btnList);
	}

	public ResultBean listsysRoleByPager(ServletContext context, HttpServletRequest request, SysRole role, Pager pager) {
		role.setCompanyId(UtilTool.getCompanyId(request));
		pager = PagerHelper.getPager(pager, sysProcessService.getSysRoleCount(role));
		List<SysRole> roleList = sysProcessService.getSysRoleByPager(role, pager);
		return WebUtilWork.WebResultPack(roleList, pager);
	}

	public List<SysRole> listSysRoleAll(ServletContext context, HttpServletRequest request) {
		SysRole role = new SysRole();
		role.setCompanyId(UtilTool.getCompanyId(request));
		List<SysRole> roleList = sysProcessService.getSysRoleAll(role);
		return roleList;
	}

	public ResultBean deleteSysRoleByIds(ServletContext context, HttpServletRequest request, long[] ids) {
		sysProcessService.deleteSysRoleByIds(ids);
		return WebUtilWork.WebResultPack(null);
	}

	public ResultBean getSysRoleById(ServletContext context, HttpServletRequest request, long id) {
		SysRole role = sysProcessService.getSysRoleById(id);
		return WebUtilWork.WebObjectPack(role);
	}

	public ResultBean getSysRoleBindByRoleId(ServletContext context, HttpServletRequest request, int roleId) {
		List<SysRoleBind> bindList = sysProcessService.getSysRoleBindList(roleId);
		return WebUtilWork.WebResultPack(bindList);
	}

	public ResultBean getUserMethodsByPager(ServletContext context, HttpServletRequest request, UserMethodsInfo methodinfo, Pager pager) {
		methodinfo.setCompanyId(UtilTool.getCompanyId(request));
		pager = PagerHelper.getPager(pager, sysProcessService.getUserMethodsInfoCount(methodinfo));
		List<UserMethodsInfo> list = sysProcessService.getUserMethodsInfoByPager(methodinfo, pager);
		return WebUtilWork.WebResultPack(list, pager);
	}

	public SysUserMethods getSysUserMethodsByUserId(ServletContext context, HttpServletRequest request, String userids) {
		SysUserMethods method = null;
		if (userids.length() > 0) {
			if (userids.charAt(userids.length() - 1) == ',') {
				userids = userids.substring(0, userids.length() - 1);
			}
			String[] ids = userids.trim().split(",");
			if (ids.length == 1) {
				method = sysProcessService.getSysUserMethodsByUid(Integer.parseInt(ids[0]), UtilTool.getCompanyId(request));
			}
		}
		return method;
	}
	
	
	public SysUserBtns getSysUserBtnsByUserId(ServletContext context, HttpServletRequest request, String userids) {
		SysUserBtns btn = null;
		if (userids.length() > 0) {
			if (userids.charAt(userids.length() - 1) == ',') {
				userids = userids.substring(0, userids.length() - 1);
			}
			String[] ids = userids.trim().split(",");
			if (ids.length == 1) {
				btn = sysProcessService.getSysUserBtnsByUid(Integer.parseInt(ids[0]), UtilTool.getCompanyId(request));
			}
		}
		return btn;
	}

	public Set<String> getSysUserMethodsAllByUserId(ServletContext context, HttpServletRequest request, int userids, String show) {
		Set<String> methodset = new HashSet<String>();
		if (show == null || show.length() == 0) {
			SysUserMethods method = sysProcessService.getSysUserMethodsByUid(userids, UtilTool.getCompanyId(request));
			if (method != null && method.getUserMethodDetail() != null && method.getUserMethodDetail().length() > 0) {
				String[] tmps = method.getUserMethodDetail().split(",");
				for (String string : tmps) {
					if (string != null && string.length() > 0) {
						methodset.add(string);
					}
				}
			}
		} else {
			methodset = userLoginService.getUserCompanyMethods((long) userids, EnumUtil.SYS_COMPANY_TYPE.OFFICIAL.value);
		}
		return methodset;
	}

	public ResultBean getUserMethodsInfoByUid(ServletContext context, HttpServletRequest request, int userids) {
		UserMethodsInfo info = sysProcessService.getUserMethodsInfoByUid(userids, UtilTool.getCompanyId(request));
		return WebUtilWork.WebObjectPack(info);
	}

	public ResultBean updateSysUserMethods(ServletContext context, HttpServletRequest request, String userids, String[] methodIds, String[] btnIds) {
		ArrayList<SysUserMethods> methodslist = new ArrayList<SysUserMethods>();
		ArrayList<SysUserBtns> btnslist = new ArrayList<SysUserBtns>();
		
		String ms = "";
		if (methodIds.length > 0) {
			for (int i = 0; i < methodIds.length; i++) {
				if (methodIds[i].length() > 0) {
					ms += methodIds[i] + ",";
				}
			}
		}
		
		String bs = "";
		if (btnIds.length > 0) {
			for (int i = 0; i < btnIds.length; i++) {
				if (btnIds[i].length() > 0) {
					bs += btnIds[i] + ",";
				}
			}
		}
		
		if (userids.length() > 0) {
			if (userids.charAt(userids.length() - 1) == ',') {
				userids = userids.substring(0, userids.length() - 1);
			}
			String[] ids = userids.trim().split(",");
			for (String str : ids) {
				if (str != null && str.length() > 0) {
					SysUserMethods umthos = sysProcessService.getSysUserMethodsByUid(Integer.parseInt(str), UtilTool.getCompanyId(request));
					
					if(umthos == null) umthos = new SysUserMethods();
					
					umthos.setCompanyId(UtilTool.getCompanyId(request));
					umthos.setUserId(Integer.parseInt(str));
					umthos.setUserMethodDetail(ms);
					methodslist.add(umthos);
					
					
					SysUserBtns ubtns = sysProcessService.getSysUserBtnsByUid(Integer.parseInt(str), UtilTool.getCompanyId(request));
					
					if(ubtns == null) ubtns = new SysUserBtns();
					
					ubtns.setCompanyId(UtilTool.getCompanyId(request));
					ubtns.setUserId(Integer.parseInt(str));
					ubtns.setUserBtnDetail(bs);
					btnslist.add(ubtns);
				}
			}
		}
		sysProcessService.saveSysUserMethods(methodslist, btnslist);
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 公司注册写入
	 * 
	 * @param context
	 * @param request
	 * @param company
	 * @param methods
	 * @return
	 */
	public ResultBean saveSysCompanyInfo(ServletContext context, HttpServletRequest request, SysCompanyInfo company, String[] methods) {
		company.setCompanyInfoStatus(EnumUtil.SYS_COMPANY_STATUS.APPROVE.value);
		company.setCompanyInfoType(EnumUtil.SYS_COMPANY_TYPE.APPROVE.value);
		company.setCompanyInfoRegDate(UtilWork.getNowTime());
		company.setCompanyInfoLastDate(UtilWork.getNowTime());
		SysCompanyInfo tmp = sysProcessService.saveCompanyInfo(company, methods);
		return WebUtilWork.WebObjectPack(tmp);
	}

	/**
	 * 修改公司标志
	 * 
	 * @param context
	 * @param request
	 * @param imgpath
	 * @return
	 */
	public ResultBean saveCompanyInfoImage(ServletContext context, HttpServletRequest request, String tit, String entit, String imgpath) {
		int companyId = UtilTool.getCompanyId(request);
		SysCompanyInfo companyinfo = (SysCompanyInfo) sysProcessService.getSysCompanyInfoByPk((long) companyId);

		companyinfo.setCompanyInfoTitle(tit);
		companyinfo.setCompanyInfoEnTitle(entit);

		String ids = UtilTool.saveImages(context, request, imgpath);
		companyinfo.setCompanyInfoLogin(Integer.parseInt(ids));
		sysProcessService.updateCompanyInfo(companyinfo);
		return WebUtilWork.WebResultPack(null);
	}

	public ResultBean listSysCompanyInfoByPager(ServletContext context, HttpServletRequest request, SysCompanyInfo info, Pager pager) {
		pager = PagerHelper.getPager(pager, sysProcessService.getSysCompanyInfoCount(info));
		List<SysCompanyInfo> list = sysProcessService.getSysCompanyInfoByPager(info, pager);
		return WebUtilWork.WebResultPack(list, pager);
	}

	public ResultBean listSysCompanyInfoByPagerForParam(ServletContext context, HttpServletRequest request, SysCompanyInfo info, Pager pager) {
		pager = PagerHelper.getPager(pager, sysProcessService.getSysCompanyInfoCountForParam(info));
		List<SysCompanyInfo> list = sysProcessService.getSysCompanyInfoByPagerForParam(info, pager);
		return WebUtilWork.WebResultPack(list, pager);
	}

	public List<SysMethodInfo> getCompanyMethodsByPk(ServletContext context, HttpServletRequest request, long companyid) {
		return sysProcessService.getSysCompanyMethodsByPk(companyid);
	}

	public ResultBean getSysCompanyInfoByPk(ServletContext context, HttpServletRequest request, long pk) {
		return WebUtilWork.WebObjectPack(sysProcessService.getSysCompanyInfoByPk(pk));
	}

	public String getSysCompanyInfoCodeByShortName(ServletContext context, HttpServletRequest request, long pk) {
		SysCompanyInfo company = sysProcessService.getSysCompanyInfoByPk(pk);
		String code = "";
		if (company.getCompanyInfoShortname() != null && company.getCompanyInfoShortname().length() > 0) {
			code = ConvertPinyin.getPinYinHeadChar(company.getCompanyInfoShortname());
		}
		if (code.length() > 10) {
			code = code.substring(0, 9);
		}
		return code;
	}

	// 查询系统公告
	public ResultBean listSysMsg(ServletContext context, HttpServletRequest request, SysMsg msg, Pager pager) throws Exception {

		pager = PagerHelper.getPager(pager, sysProcessService.getSystemMsgCount(msg));
		List<SysMsg> list = sysProcessService.getAllSystemMsg(msg, pager);

		SysMsg sysmsg = new SysMsg();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		String nowtime = UtilWork.getNowTime();
		Date nowTime = sdf.parse(nowtime);
		Date starTime = null;
		Date enddate = null;

		for (int i = 0; i < list.size(); i++) {
			sysmsg = list.get(i);
			starTime = sdf.parse(sysmsg.getMsgVsdate());
			enddate = sdf.parse(sysmsg.getMsgVedate());
			if (starTime.after(nowTime) == false && nowTime.after(enddate) == false) {
				sysmsg.setMsgIsEffective(EnumUtil.SYS_ISACTION.Vaild.value);
			} else {
				sysmsg.setMsgIsEffective(EnumUtil.SYS_ISACTION.No_Vaild.value);
			}

		}

		logger.info("显示系统公告...");
		return WebUtilWork.WebResultPack(list, pager);
	}

	/**
	 * s 删除系统公告信息
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean deleteNewsById(ServletContext context, HttpServletRequest request, long[] ids) {

		try {
			sysProcessService.delectSystemMsgByid(ids);
			logger.info("删除新闻信息...");
		} catch (Exception e) {
			logger.error("删除新闻出错..." + e.getMessage());
			return new ResultBean(false, e.getMessage());
		}
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * s 新增系统公告
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean saveSysMsg(ServletContext context, HttpServletRequest request, SysMsg msg) {
		getSysMsgInfo(context, request, msg);
		logger.info("新增系统公告...");
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * s 编辑系统公告
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean updataSysMsg(ServletContext context, HttpServletRequest request, SysMsg msg) {
		msg.setPrimaryKey(msg.getPrimaryKey());
		getSysMsgInfo(context, request, msg);
		logger.info("修改系统公告...");
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * s 获得系统公告参数值
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	private ResultBean getSysMsgInfo(ServletContext context, HttpServletRequest request, SysMsg msg) {
		msg.setMsgTitle(msg.getMsgTitle());
		msg.setMsgDate(UtilWork.getToday());
		msg.setMsgVsdate(msg.getMsgVsdate());
		msg.setMsgVedate(msg.getMsgVedate());
		msg.setMsgPerson(UtilTool.getEmployeeId(request));
		msg.setMsgContext(msg.getMsgContext());
		sysProcessService.saveSystemMsg(msg);
		logger.info("新增系统公告...");
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * s 根据ID获取公告信息
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean getSysMsgByPk(ServletContext context, HttpServletRequest request, long id) throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		String nowtime = UtilWork.getNowTime();
		Date nowTime = sdf.parse(nowtime);
		Date starTime = null;
		Date enddate = null;

		SysMsg sysmsg = sysProcessService.getSystemMsgByid(id);

		starTime = sdf.parse(sysmsg.getMsgVsdate());
		enddate = sdf.parse(sysmsg.getMsgVedate());
		if (starTime.after(nowTime) == false && nowTime.after(enddate) == false) {
			sysmsg.setMsgIsEffective(1);
		} else {
			sysmsg.setMsgIsEffective(2);
		}

		logger.info("根据id获取公告信息...");

		List<SysMsg> list = new ArrayList<SysMsg>();
		list.add(sysmsg);
		return WebUtilWork.WebResultPack(list);
	}

	// 查询系统公告
	public ResultBean listHelpMsg(ServletContext context, HttpServletRequest request, SysHelp help, Pager pager) {
		pager = PagerHelper.getPager(pager, sysProcessService.getSystemHelpCount(help));
		List<SysHelp> list = sysProcessService.getAllSystemHelp(help, pager);
		logger.info("显示系统公告...");
		return WebUtilWork.WebResultPack(list, pager);
	}

	public List<SysHelp> listAllSysHelp(ServletContext context, HttpServletRequest request, int row) throws Exception {
		List<SysHelp> list = sysProcessService.listSystemHelpOrderBy(getOtherPager(row));
		return list;
	}

	/**
	 * 查询系统异常
	 * 
	 * @param context
	 * @param request
	 * @return ResultBean
	 */
	public ResultBean listSysException(ServletContext context, HttpServletRequest request, SysException sysException, Pager pager) {
		pager = PagerHelper.getPager(pager, sysProcessService.listSysExceptionCount(sysException));
		List<SysException> list = sysProcessService.listAllSysException(sysException, pager);

		logger.info("显示系统异常...");
		return WebUtilWork.WebResultPack(list, pager);
	}

	/**
	 * 删除系统异常
	 * 
	 * @param context
	 * @param request
	 * @return ResultBean
	 */
	public ResultBean delectSysExceptionsByid(ServletContext context, HttpServletRequest request, long[] ids) {

		try {
			sysProcessService.delectSysExceptionsByid(ids);
			logger.info("删除系统异常...");
		} catch (Exception e) {
			logger.error("删除系统异常出错..." + e.getMessage());
			return new ResultBean(false, e.getMessage());
		}
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 更新系统异常
	 * 
	 * @param context
	 * @param request
	 * @return ResultBean
	 */
	public ResultBean updateSysException(ServletContext context, HttpServletRequest request, SysException sysException) {

		try {
			sysProcessService.updateSysException(sysException);
			logger.info("处理系统异常成功...");
		} catch (Exception e) {
			logger.error("处理系统异常出错..." + e.getMessage());
			return new ResultBean(false, e.getMessage());
		}
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 根据ID获取系统异常
	 * 
	 * @param context
	 * @param request
	 * @return ResultBean
	 */
	public ResultBean getSysExceptionByPk(ServletContext context, HttpServletRequest request, long id) throws Exception {
		SysException sysException = sysProcessService.getSysExceptionByid(id);

		logger.info("根据ID获取系统异常...");
		List<SysException> list = new ArrayList<SysException>();
		list.add(sysException);
		return WebUtilWork.WebResultPack(list);
	}

	/**
	 * 删除系统帮助
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean deleteHelpsById(ServletContext context, HttpServletRequest request, long[] ids) {
		try {
			sysProcessService.delectSystemHelpByid(ids);
			logger.info("删除系统帮助...");
		} catch (Exception e) {
			logger.error("删除系统帮助出错..." + e.getMessage());
			return new ResultBean(false, e.getMessage());
		}
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 新增系统帮助
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean saveSysHelp(ServletContext context, HttpServletRequest request, SysHelp help) {
		getSysHelpInfo(context, request, help);
		logger.info("新增系统帮助...");
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 编辑系统帮助
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean updataSysHelp(ServletContext context, HttpServletRequest request, SysHelp help) {
		help.setPrimaryKey(help.getPrimaryKey());
		getSysHelpInfo(context, request, help);
		logger.info("编辑系统帮助..");
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 获得系统帮助参数值
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	private ResultBean getSysHelpInfo(ServletContext context, HttpServletRequest request, SysHelp help) {
		help.setHelpTitle(help.getHelpTitle());
		help.setHelpKeyword(help.getHelpKeyword());
		help.setHelpDate(UtilWork.getToday());
		help.setHelpContext(help.getHelpContext());
		sysProcessService.saveSystemHelp(help);
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 根据ID获取系统帮助
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean getSysHelpByPk(ServletContext context, HttpServletRequest request, long id) {
		SysHelp sysHelp = sysProcessService.getSystemHelpByid(id);
		List<SysHelp> list = new ArrayList<SysHelp>();
		list.add(sysHelp);
		logger.info("根据ID获取系统帮助...");
		return WebUtilWork.WebResultPack(list);
	}

	public boolean isHashCompanyInfoCode(ServletContext context, HttpServletRequest request, String code) {
		List<SysCompanyInfo> list = sysProcessService.getSysCompanyInfoByCode(code, null);
		boolean bl = false;
		if (list.size() > 0) {
			bl = true;
		}
		return bl;
	}

	public boolean isHashCompanyInfoCodeByEdit(ServletContext context, HttpServletRequest request, String code, long companyId) {
		List<SysCompanyInfo> list = sysProcessService.getSysCompanyInfoByCode(code, companyId);
		boolean bl = false;
		if (list.size() > 0) {
			bl = true;
		}
		return bl;
	}

	/**
	 * 注册公司转为正式或试用公司
	 * 
	 * @param context
	 * @param request
	 * @param info
	 * @return
	 */
	public ResultBean updateSysCompanyInfo(ServletContext context, HttpServletRequest request, SysCompanyInfo info) {
		List<SysCompanyInfo> list = sysProcessService.getSysCompanyInfoByType(EnumUtil.SYS_COMPANY_TYPE.SYSTEM.value);
		SysCompanyInfo systemcompanyinfo = null;
		if (list.size() == 1) {
			systemcompanyinfo = list.get(0);
		} else {
			return new ResultBean(false, "管理公司设置错误,无法确认管理数据字典！");
		}

		SysCompanyInfo oldinfo = sysProcessService.getSysCompanyInfoByPk(info.getPrimaryKey());
		oldinfo.setCompanyInfoStatus(EnumUtil.SYS_COMPANY_STATUS.TAKE.value);
		oldinfo.setCompanyInfoCode(info.getCompanyInfoCode());
		oldinfo.setCompanyInfoType(info.getCompanyInfoType());
		oldinfo.setCompanyInfoSdate(info.getCompanyInfoSdate());
		oldinfo.setCompanyInfoEdate(info.getCompanyInfoEdate());
		oldinfo.setCompanyInfoUsercount(info.getCompanyInfoUsercount());
		oldinfo.setCompanyInfoWarehousecount(info.getCompanyInfoWarehousecount());
		oldinfo.setCompanyInfoContext(info.getCompanyInfoContext());
		oldinfo.setCompanyInfoLastDate(UtilWork.getNowTime());

		sysProcessService.updateSysCompanyInfo(oldinfo, systemcompanyinfo);
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 管理人员调整公司信息
	 * 
	 * @param context
	 * @param request
	 * @param info
	 * @param methods
	 * @return
	 */
	public ResultBean editSysCompanyInfo(ServletContext context, HttpServletRequest request, SysCompanyInfo info, String[] methods) {
		SysCompanyInfo oldinfo = sysProcessService.getSysCompanyInfoByPk(info.getPrimaryKey());
		info.setCompanyInfoStatus(oldinfo.getCompanyInfoStatus());
		info.setCompanyInfoRegDate(oldinfo.getCompanyInfoRegDate());
		info.setCompanyInfoLastDate(UtilWork.getNowTime());
		info.setCompanyInfoLogin(oldinfo.getCompanyInfoLogin());
		info.setCompanyInfoTitle(oldinfo.getCompanyInfoTitle());
		info.setCompanyInfoEnTitle(oldinfo.getCompanyInfoEnTitle());
		sysProcessService.editSysCompanyInfo(info, methods);
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 根据公司主键查询公司超级用户
	 * 
	 * @param context
	 * @param request
	 * @param cid
	 * @return
	 */
	public SysUserInfo getSysUserInfoByCompanyId(ServletContext context, HttpServletRequest request, int cid) {
		return sysProcessService.getSysUserInfoByCompanyId(cid);
	}

	public ResultBean deleteSysCompanyInfoByPk(ServletContext context, HttpServletRequest request, long pk) {
		try {
			sysProcessService.deleteSysCompanyInfoByPk(context, pk);
		} catch (Exception e) {
			logger.info("删除公司错误," + e.getMessage());
			return new ResultBean(false, e.getMessage());
		}
		return WebUtilWork.WebResultPack(null);
	}

	public ResultBean getSysParamBypager(ServletContext context, HttpServletRequest request, SysParam param, Pager pager) {
		// param.setCompanyId(UtilTool.getCompanyId(request));
		pager = PagerHelper.getPager(pager, sysProcessService.getSysParamCount(param));
		List<SysParam> list = sysProcessService.getSysParamByPager(param, pager);
		return WebUtilWork.WebResultPack(list, pager);
	}

	public ResultBean saveSysparam(ServletContext context, HttpServletRequest request, SysParam param) {
		if (param.getPrimaryKey() > 0) {
			SysParam tmp = sysProcessService.getSysParamByPk(param.getPrimaryKey());
			param.setRecordId(tmp.getRecordId());
			param.setRecordDate(tmp.getRecordDate());
		} else {
			param.setRecordId(UtilTool.getEmployeeId(request));
			param.setRecordDate(UtilWork.getNowTime());
		}
		// param.setCompanyId(UtilTool.getCompanyId(request));
		param.setLastmodiId(UtilTool.getEmployeeId(request));
		param.setLastmodiDate(UtilWork.getNowTime());
		sysProcessService.saveSysParam(param);
		return WebUtilWork.WebResultPack(null);
	}

	public ResultBean getSysParamByPk(ServletContext context, HttpServletRequest request, long pk) {
		SysParam tmp = sysProcessService.getSysParamByPk(pk);
		return WebUtilWork.WebObjectPack(tmp);
	}

	public ResultBean deleteSysParamByPk(ServletContext context, HttpServletRequest request, long pk) {
		sysProcessService.deleteSysParamByPk(pk);
		return WebUtilWork.WebResultPack(null);
	}

	public List<SysParam> getAllSysParamByCompanyId(ServletContext context, HttpServletRequest request) {
		return sysProcessService.getAllSysParamByCompanyId(UtilTool.getCompanyId(request));
	}

	public ResultBean updateSysParams(ServletContext context, HttpServletRequest request, SysParam[] params) {
		ArrayList<SysParam> list = new ArrayList<SysParam>();
		for (SysParam sysParam : params) {
			SysParam tmp = sysProcessService.getSysParamByPk(sysParam.getPrimaryKey());
			tmp.setParamValue(sysParam.getParamValue());
			tmp.setLastmodiId(UtilTool.getEmployeeId(request));
			tmp.setLastmodiDate(UtilWork.getNowTime());
			list.add(tmp);
		}
		sysProcessService.updateSysparams(list);

		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 获取所有有效公告
	 * 
	 * @param context
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List<SysMsg> listSysMsgByVaild(ServletContext context, HttpServletRequest request) throws Exception {
		List<SysMsg> vaildmsg = new ArrayList<SysMsg>();
		List<SysMsg> list = sysProcessService.listAllSystemMsg();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		String nowtime = UtilWork.getNowTime();
		Date nowTime = sdf.parse(nowtime);
		Date starTime = null;
		Date enddate = null;
		for (SysMsg sm : list) {
			if (sm.getMsgVsdate() != null && sm.getMsgVedate() != null) {
				starTime = sdf.parse(sm.getMsgVsdate());
				enddate = sdf.parse(sm.getMsgVedate());
				if (starTime.after(nowTime) == false && nowTime.after(enddate) == false) {
					vaildmsg.add(sm);
				}
			}
		}
		return vaildmsg;
	}

	/**
	 * 帮助页面获取
	 * 
	 * @param context
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List<SysMsg> listAllSysMsg(ServletContext context, HttpServletRequest request, int row) throws Exception {
		List<SysMsg> list = sysProcessService.listSystemMsgOrderBy(getOtherPager(row));
		String nowtime = UtilWork.getNowTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date nowTime = sdf.parse(nowtime);
		Date starTime = null;
		Date enddate = null;
		for (SysMsg sm : list) {
			starTime = sdf.parse(sm.getMsgVsdate());
			enddate = sdf.parse(sm.getMsgVedate());
			if (starTime.after(nowTime) == false && nowTime.after(enddate) == false) {
				sm.setMsgIsEffective(EnumUtil.SYS_ISACTION.Vaild.value);
			} else {
				sm.setMsgIsEffective(EnumUtil.SYS_ISACTION.No_Vaild.value);
			}
		}
		return list;
	}

	private Pager getOtherPager(int row) {
		Pager pager = new Pager();
		pager.setStartRow(0);
		pager.setPageSize(row);
		return pager;
	}

	/**
	 * 系统日志查询
	 */
	/**
	 * 所有操作日志查询
	 */
	public ResultBean listSysLog(ServletContext context, HttpServletRequest request, SysLog sysLog, Pager pager) {

		List<SysLog> list = null;
		sysLog.setCompanyId(UtilTool.getCompanyId(request));
		pager = PagerHelper.getPager(pager, sysProcessService.listLogCount(sysLog));

		list = sysProcessService.getAllLog(sysLog, pager);

		for (SysLog syslog : list) {

			SysUserInfo user = sysProcessService.getSysUserInfoByPk(syslog.getUserId(), false);

			if (user.getHrmEmployeeId().equals("-1")) {
				HrmEmployee hrmEmployee = new HrmEmployee();
				hrmEmployee.setHrmEmployeeName("系统管理员");
				syslog.setHrmEmployee(hrmEmployee);
			} else {
				syslog.setHrmEmployee(sysProcessService.getEmployeeByPK(user.getHrmEmployeeId()));
			}

		}

		return WebUtilWork.WebResultPack(list, pager);
	}

	/**
	 * 根据公司ID 删除系统日志
	 */
	public ResultBean deleteSysLog(ServletContext context, HttpServletRequest request) {
		sysProcessService.deleteSysLogByCommpanyid(UtilTool.getCompanyId(request));
		logger.info("删除系统公告...");
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 根据公司ID查询在线人员
	 */
	@SuppressWarnings("unchecked")
	public ResultBean listOnline(ServletContext context, HttpServletRequest request, HrmEmployee employee, boolean blonline, Pager pager) {
		List<OnlineHrmEmployeeBean> list = null;
		int companyId = UtilTool.getCompanyId(request);
		// 从application中取出在线人员列表
		Set<String> employeeIds = OnlineUserBindingListener.getOnlineList(context, companyId);
		employee.setHrmEmployeeStatus(EnumUtil.HRM_EMPLOYEE_STATUS.Separation.value);
		employee.setHrmEmployeeActive(EnumUtil.SYS_ISACTION.Vaild.value);
		// employee.setPrimaryKey(UtilTool.getEmployeeId(request));

		if (blonline) {
			String ids = UtilTool.getStringFormSetIsString(employeeIds);
			if (ids.length() > 0) {
				employee.setEmployeeIds(ids);
				pager = PagerHelper.getPager(pager, sysProcessService.getOnlineEmployeeCount(employee, (long) UtilTool.getCompanyId(request)));
				list = sysProcessService.getOnlineEmployee(employee, (long) UtilTool.getCompanyId(request), pager);
				for (OnlineHrmEmployeeBean bean : list) {
					bean.setIsOnLine(1);
					bean.setOtherHtml("在线");
				}
			} else {
				list = new ArrayList<OnlineHrmEmployeeBean>();
			}
		} else {
			pager = PagerHelper.getPager(pager, sysProcessService.getOnlineEmployeeCount(employee, (long) UtilTool.getCompanyId(request)));
			list = sysProcessService.getOnlineEmployee(employee, (long) UtilTool.getCompanyId(request), pager);

			for (OnlineHrmEmployeeBean Employee : list) {
				boolean bl = false;
				Iterator<String> it = employeeIds.iterator();
				while (it.hasNext()) {
					String empid = (String) it.next();
					if (Employee.getPrimaryKey().equalsIgnoreCase(empid)) {
						bl = true;
						break;
					}
				}
				if (bl) {
					Employee.setIsOnLine(1);
					Employee.setOtherHtml("在线");
				} else {
					Employee.setIsOnLine(2);
					Employee.setOtherHtml("离线");
				}
			}
			CaseInsensitiveComparator comp = new CaseInsensitiveComparator();
			Collections.sort(list, comp);
		}
		return WebUtilWork.WebResultPack(list, pager);
	}

	@SuppressWarnings("rawtypes")
	class CaseInsensitiveComparator implements Comparator {

		public int compare(Object arg0, Object arg1) {
			OnlineHrmEmployeeBean employee1 = (OnlineHrmEmployeeBean) arg0;
			OnlineHrmEmployeeBean employee2 = (OnlineHrmEmployeeBean) arg1;
			if (employee1.getIsOnLine() != null && employee2.getIsOnLine() != null && employee1.getIsOnLine() != employee2.getIsOnLine()) {
				int m1 = employee1.getIsOnLine();
				int m2 = employee2.getIsOnLine();
				if (m1 < m2) {
					return -1;
				} else {
					return 1;
				}
			} else {
				return 0;
			}
		}
	}

	/**
	 * 根据编号获取功能目录
	 * 
	 * @param context
	 * @param request
	 * @param id
	 * @return
	 */
	public ResultBean getSysMethodInfoByPK(ServletContext context, HttpServletRequest request, String id) {
		List<SysMethodInfo> list = new ArrayList<SysMethodInfo>();
		SysMethodInfo info = sysProcessService.getSysMethodInfoByPK(id);
		
//		List<SysMethodBtn> btns = sysProcessService.listSysMethodBtnByMethodId(id);
//		List<SysMethodHelp> helps = sysProcessService.listSysMethodHelpByMethodId(id);
//		
//		info.setBtns(btns);
//		info.setHelps(helps);

		// 加载上级功能菜单信息
		if (info.getLevelUnit() != null && info.getLevelUnit().length() > 0) {
			SysMethodInfo me = sysProcessService.getSysMethodInfoByPK(info.getLevelUnit());
			if (me != null) {
				info.setUpSysMethodInfo(me);
			}
		}

		list.add(info);

		logger.info("根据主键获取功能目录...");
		return WebUtilWork.WebResultPack(list);
	}

	/**
	 * 删除功能目录
	 * 
	 * @param context
	 * @param request
	 * @param ids
	 *            主键
	 * @return
	 */
	public ResultBean deleteSysMethodInfoById(ServletContext context, HttpServletRequest request, String[] ids) {
		for (String s : ids) {
			SysMethodInfo info = sysProcessService.getSysMethodInfoByPK(s);

			// if(info.getIsAction() == EnumUtil.SYS_ISACTION.Vaild.value){
			// return new
			// ResultBean(false,info.getMethodInfoName()+"为有效状态，不能删除！");
			// }

			int count = sysProcessService.getMethodInfoListByUpCodeCount(info.getPrimaryKey());
			if (count > 0) {
				return new ResultBean(false, info.getMethodInfoName() + "有下级功能目录，不能删除！");
			}
		}
		sysProcessService.delectMethodByids(ids);

		logger.info("删除功能目录...");
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 启用/禁用 功能菜单
	 * 
	 * @param context
	 * @param request
	 * @param ids
	 * @return
	 */
	public ResultBean setMethodInfoStatus(ServletContext context, HttpServletRequest request, String[] ids) {
		sysProcessService.setMethodStatus(ids);
		logger.info("设置功能菜单状态...");
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 新增功能目录
	 * 
	 * @param context
	 * @param request
	 * @param method
	 * @return
	 */
	public ResultBean saveMethod(ServletContext context, HttpServletRequest request, SysMethodInfo method) {

		// 判断上级节点是否为空，不为空表示有上级节点。为空表示没有上级节点，则为顶级节点。
		if (StringUtils.isNotBlank(method.getLevelUnit())) {

			SysMethodInfo info = sysProcessService.getSysMethodInfoByPK(method.getLevelUnit());
			int upMethodLevel = info.getMethodLevel();
			if (upMethodLevel == -1)
				method.setMethodLevel(1);// 如果上级为-1，设置为第一级
			else
				method.setMethodLevel(upMethodLevel + 1);

		} else {
			method.setLevelUnit("-1");// 上级节点为空时
			method.setMethodLevel(-1);
		}

		int c = sysProcessService.getSysMethodInfoByNameAndLevelUnit(method.getMethodInfoName(), method.getLevelUnit(), method.getPrimaryKey());
		if (c > 0) {
			return new ResultBean(false, "同一级别下，功能目录名称不能重名！");
		}

		String code = UtilTool.getCodeByUpCode(context, request, method.getLevelUnit(), "sys_method_info", "method_info_id", "level_unit");
		method.setPrimaryKey(code);// 设置主键（编号）
		int count = sysProcessService.getMethodInfoListByUpCodeCount(method.getLevelUnit());
		method.setMethodNo(count + 1);// 设置默认排序值
		method.setIsDefault(0);

		sysProcessService.saveSysMethodInfo(method);
		logger.info("新增功能目录...");
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 编辑功能目录信息
	 * 
	 * @param context
	 * @param request
	 * @param info
	 * @return
	 */
	public ResultBean updateSysMethodInfo(ServletContext context, HttpServletRequest request, SysMethodInfo method) {
		if (StringUtils.isBlank(method.getLevelUnit())) {
			method.setLevelUnit("-1");
		}

		SysMethodInfo methodinfo = sysProcessService.getSysMethodInfoByPK(method.getPrimaryKey());
		int count = sysProcessService.getSysMethodInfoByNameAndLevelUnit(method.getMethodInfoName(), method.getLevelUnit(), method.getPrimaryKey());
		if (count > 0) {
			return new ResultBean(false, "同一级别下，功能目录名称不能重名！");
		}

		if (!method.getLevelUnit().equals(methodinfo.getLevelUnit())) {// 上级编码未变动
			boolean bl = false;
			// 判断上级目录是否为自己和自己的下级目录
			List<SysMethodInfo> list = sysProcessService.getSysMethodInfoListByPK(methodinfo.getPrimaryKey());
			for (SysMethodInfo smi : list) {
				if (smi.getPrimaryKey().equals(method.getLevelUnit())) {
					bl = true;
					break;
				}
			}
			if (bl) {
				return new ResultBean(false, "上级目录不能为自己和自己的下级目录!");
			}
		}
		sysProcessService.saveSysMethodInfo(method);

		logger.info("编辑功能目录信息...");
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 根据uri获得功能目录
	 * 
	 * @param context
	 * @param request
	 * @param uri
	 * @return
	 */
	public SysMethodInfo getSysMethodInfoByUri(ServletContext context, HttpServletRequest request, String uri) {
		return sysProcessService.getSysMethodInfoByUri(uri);
	}

	/**
	 * 系统配置
	 */
	public ResultBean listSysConfig(ServletContext context, HttpServletRequest request, SysConfig sysConfig, Pager pager) {

		List<SysConfig> list = sysProcessService.listSysConfig();
		pager = PagerHelper.getPager(pager, list.size());

		logger.info("系统配置...");
		return WebUtilWork.WebResultPack(list, pager);
	}

	/**
	 * 新增系统配置
	 */
	public ResultBean addSysConfig(ServletContext context, HttpServletRequest request, SysConfig[] cons) {
		sysProcessService.addSysConfig(cons);
		logger.info("新增系统配置...");
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 根据empId获取用户名 2014-02-06 JC
	 * 
	 * @param context
	 * @param request
	 * @param empId
	 * @return
	 */
	public SysUserInfo getSysUserInfoByEmpId(ServletContext context, HttpServletRequest request, String empId) {
		return sysProcessService.getSysUserInfoByEmpId(empId);
	}

	/********************* 标准代码开始 2014-02-14 JC *********************/

	public ResultBean getSysLibraryStandardListByPager(ServletContext context, HttpServletRequest request, SysLibraryStandard libraryInfo, Pager pager) {
		pager = PagerHelper.getPager(pager, sysProcessService.getSysLibraryStandardCount(libraryInfo));
		List<SysLibraryStandard> list = sysProcessService.getSysLibraryStandardInfoPager(libraryInfo, pager);
		return WebUtilWork.WebResultPack(list, pager);
	}

	public ResultBean getSysLibraryStandardByPk(ServletContext context, HttpServletRequest request, long pk) {
		SysLibraryStandard libraryInfo = sysProcessService.getSysLibraryStandardByPk(pk);

		if (StringUtils.isNotBlank(libraryInfo.getLibraryUpcode())) {
			libraryInfo.setUpSysLibrary(sysProcessService.getSysLibraryStandardByCode(libraryInfo.getLibraryUpcode()));
		}

		return WebUtilWork.WebObjectPack(libraryInfo);
	}

	public ResultBean saveSysLibrayStandard(ServletContext context, HttpServletRequest request, SysLibraryStandard libraryInfo) {
		// 上级编码
		String code = UtilTool.getCodeByUpCode(context, request, libraryInfo.getLibraryUpcode(), "sys_library_standard", "library_code", "library_upcode");
		libraryInfo.setLibraryCode(code);
		sysProcessService.saveSysLibraryStandard(libraryInfo);
		return WebUtilWork.WebResultPack(null);
	}

	public ResultBean updateSysLibraryStandard(ServletContext context, HttpServletRequest request, SysLibraryStandard libraryInfo) {
		sysProcessService.saveSysLibraryStandard(libraryInfo);
		return WebUtilWork.WebResultPack(null);
	}

	public ResultBean deleteSysLibraryStandardByPks(ServletContext context, HttpServletRequest request, long[] pks) {
		sysProcessService.deleteSysLibraryStandardByPks(pks);
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 根据编号获取下级(加载树使用)
	 * 
	 * @param context
	 * @param request
	 * @param libraryCode
	 * @return
	 */
	public List<SysLibraryStandard> listDownSysLibraryStandardByCode(HttpServletRequest request, String libraryCode) {
		List<SysLibraryStandard> list = sysProcessService.getDownSysLibraryStandardByCode(libraryCode);
		return list;
	}

	public List<SysLibraryStandard> listDownSysLibraryStandardByCodeAll(ServletContext context, HttpServletRequest request, String libraryCode) {
		List<SysLibraryStandard> list = sysProcessService.getDownSysLibraryStandardByCodeAll(libraryCode);
		return list;
	}

	/**
	 * 统计节点下的数量
	 * 
	 * @param request
	 * @param libraryCode
	 * @return
	 */
	public int listDownSysLibraryStandardByCodeCount(HttpServletRequest request, String libraryCode) {
		return sysProcessService.getDownSysLibraryStandardByCodeCount(libraryCode);
	}

	/********************* 标准代码结束 2014-02-14 JC *********************/

	public ResultBean listSysMethodHelpByPager(ServletContext context, HttpServletRequest request, SysMethodHelp methodHelp, Pager pager) {
		pager = PagerHelper.getPager(pager, sysProcessService.listSysMethodHelpCount(methodHelp));
		List<SysMethodHelp> list = sysProcessService.listSysMethodHelpByPager(methodHelp, pager);

		for (SysMethodHelp sysMethodHelp : list) {
			sysMethodHelp.setMethodInfo(sysProcessService.getSysMethodInfoByPK(sysMethodHelp.getMethodId()));
		}

		return WebUtilWork.WebResultPack(list, pager);
	}

	public ResultBean getSysMethodHelpByPk(ServletContext context, HttpServletRequest request, long pk) {
		SysMethodHelp methodHelp = sysProcessService.getSysMethodHelpByPk(pk);

		if (StringUtils.isNotBlank(methodHelp.getMethodId())) {
			methodHelp.setMethodInfo(sysProcessService.getSysMethodInfoByPK(methodHelp.getMethodId()));
		}

		return WebUtilWork.WebObjectPack(methodHelp);
	}

	public ResultBean deleteMethodHelpByIds(ServletContext context, HttpServletRequest request, long[] pks) {
		sysProcessService.deleteMethodHelpByIds(pks);
		return WebUtilWork.WebResultPack(null);
	}

	public ResultBean saveSysMethodHelp(ServletContext context, HttpServletRequest request, SysMethodHelp methodHelp) {
		sysProcessService.saveSysMethodHelp(methodHelp);
		return WebUtilWork.WebResultPack(null);
	}

	public ResultBean updateSysMethodHelp(ServletContext context, HttpServletRequest request, SysMethodHelp methodHelp) {
		sysProcessService.saveSysMethodHelp(methodHelp);
		return WebUtilWork.WebResultPack(null);
	}

	public List<SysMethodHelp> listSysMethodHelpByMethodId(ServletContext context, HttpServletRequest request, String methodId) {
		List<SysMethodHelp> helpList = sysProcessService.listSysMethodHelpByMethodId(methodId);
		return helpList;
	}
	
	public ResultBean listSysMethodHelpByMethodIdResultBean(ServletContext context, HttpServletRequest request, String methodId) {
		List<SysMethodHelp> helpList = sysProcessService.listSysMethodHelpByMethodId(methodId);
		return WebUtilWork.WebResultPack(helpList);
	}
	

	public ResultBean getSysMethodBtnByPk(ServletContext context, HttpServletRequest request, long pk) {
		SysMethodBtn methodBtn = sysProcessService.getSysMethodBtnByPk(pk);

		if (StringUtils.isNotBlank(methodBtn.getMethodId())) {
			methodBtn.setMethodInfo(sysProcessService.getSysMethodInfoByPK(methodBtn.getMethodId()));
		}

		return WebUtilWork.WebObjectPack(methodBtn);
	}

	public ResultBean deleteMethodBtnByIds(ServletContext context, HttpServletRequest request, long[] pks) {
		sysProcessService.deleteMethodBtnByIds(pks);
		return WebUtilWork.WebResultPack(null);
	}

	public ResultBean saveSysMethodBtn(ServletContext context, HttpServletRequest request, SysMethodBtn methodBtn) {
		sysProcessService.saveSysMethodBtn(methodBtn);
		return WebUtilWork.WebResultPack(null);
	}

	public ResultBean updateSysMethodBtn(ServletContext context, HttpServletRequest request, SysMethodBtn methodBtn) {
		sysProcessService.saveSysMethodBtn(methodBtn);
		return WebUtilWork.WebResultPack(null);
	}

	public List<SysMethodBtn> listSysMethodBtnByMethodId(ServletContext context, HttpServletRequest request, String methodId) {
		List<SysMethodBtn> helpList = sysProcessService.listSysMethodBtnByMethodId(methodId);
		return helpList;
	}
	
	public ResultBean listSysMethodBtnByMethodIdResultBean(ServletContext context, HttpServletRequest request, String methodId) {
		List<SysMethodBtn> helpList = sysProcessService.listSysMethodBtnByMethodId(methodId);
		return WebUtilWork.WebResultPack(helpList);
	}
	
	
	

	/**
	 * 查询 SysMethodShortcut 列表
	 * 
	 * @param context
	 * @param request
	 * @param sysMethodShortcut
	 * @param pager
	 */
	public ResultBean listSysMethodShortcut(ServletContext context, HttpServletRequest request, SysMethodShortcut sysMethodShortcut, Pager pager) {
		List<SysMethodShortcut> list = null;
		pager = PagerHelper.getPager(pager, sysProcessService.listSysMethodShortcutCount(sysMethodShortcut));
		list = sysProcessService.listSysMethodShortcut(sysMethodShortcut, pager);
		return WebUtilWork.WebResultPack(list, pager);
	}

	public List<SysMethodShortcut> listAllSysMethodShortcut(ServletContext context, HttpServletRequest request) {
		List<SysMethodShortcut> list = sysProcessService.listAllSysMethodShortcut(UtilTool.getEmployeeId(request));
		for (SysMethodShortcut sysMethodShortcut : list) {
			SysMethodInfo method = sysProcessService.getSysMethodInfoByPK(sysMethodShortcut.getMethodId());
			sysMethodShortcut.setMethod(method);
		}
		return list;
	}

	public ResultBean listSysMethodAutoOpen(ServletContext context, HttpServletRequest request) {
		List<SysMethodShortcut> list = sysProcessService.listSysMethodAutoOpen(UtilTool.getEmployeeId(request), EnumUtil.SYS_ISEDIT.EDIT.value);

		List<SysMethodInfo> methodList = new ArrayList<SysMethodInfo>();
		for (SysMethodShortcut sysMethodShortcut : list) {
			SysMethodInfo method = sysProcessService.getSysMethodInfoByPK(sysMethodShortcut.getMethodId());
			if (method != null)
				methodList.add(method);
		}
		return WebUtilWork.WebResultPack(methodList);
	}

	/**
	 * 保存 SysMethodShortcut
	 * 
	 * @param context
	 * @param request
	 * @param sysMethodShortcut
	 */
	public ResultBean saveSysMethodShortcut(ServletContext context, HttpServletRequest request, String methodId) {

		SysMethodInfo methodInfo = sysProcessService.getSysMethodInfoByPK(methodId);

		int count = 0;
		count = sysProcessService.getMethodInfoListByUpCodeCount(methodId);

		if (count > 0 || methodInfo.getMethodLevel() == -1) {
			return new ResultBean(false, "请选择末级功能菜单。");
		}

		String empId = UtilTool.getEmployeeId(request);
		count = sysProcessService.checkSysMethodShortcut(empId, methodId);
		if (count > 0) {
			return new ResultBean(false, "已经添加过该功能菜单了。");
		}
		SysMethodShortcut shortcut = new SysMethodShortcut();
		shortcut.setEmpId(empId);
		shortcut.setMethodId(methodId);
		shortcut.setCompanyId(UtilTool.getCompanyId(request));
		shortcut.setAutoOpen(EnumUtil.SYS_ISEDIT.No_EDIT.value);
		sysProcessService.saveSysMethodShortcut(shortcut);
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 更新 SysMethodShortcut
	 * 
	 * @param context
	 * @param request
	 * @param sysMethodShortcut
	 */
	public ResultBean updateSysMethodShortcut(ServletContext context, HttpServletRequest request, SysMethodShortcut sysMethodShortcut) {
		sysProcessService.saveSysMethodShortcut(sysMethodShortcut);
		return WebUtilWork.WebResultPack(null);
	}

	public ResultBean updateSysMethodShortcutByPk(ServletContext context, HttpServletRequest request, long pk) {
		SysMethodShortcut shortcut = sysProcessService.getSysMethodShortcutByPk(pk);

		if (shortcut.getAutoOpen() == EnumUtil.SYS_ISEDIT.EDIT.value)
			shortcut.setAutoOpen(EnumUtil.SYS_ISEDIT.No_EDIT.value);
		else
			shortcut.setAutoOpen(EnumUtil.SYS_ISEDIT.EDIT.value);

		sysProcessService.saveSysMethodShortcut(shortcut);
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 根据ID获得 SysMethodShortcut
	 * 
	 * @param context
	 * @param request
	 * @param pk
	 */
	public ResultBean getSysMethodShortcutByPk(ServletContext context, HttpServletRequest request, long pk) {
		SysMethodShortcut sysMethodShortcut = sysProcessService.getSysMethodShortcutByPk(pk);
		return WebUtilWork.WebObjectPack(sysMethodShortcut);
	}

	/**
	 * 删除 SysMethodShortcut
	 * 
	 * @param context
	 * @param request
	 * @param pks
	 */
	public ResultBean deleteSysMethodShortcutByPks(ServletContext context, HttpServletRequest request, long[] pks) {
		sysProcessService.deleteSysMethodShortcutByPks(pks);
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 查询 SysLogRuntime 列表
	 * 
	 * @param context
	 * @param request
	 * @param sysLogRuntime
	 * @param pager
	 */
	public ResultBean listSysLogRuntime(ServletContext context, HttpServletRequest request, SysLogRuntime sysLogRuntime, Pager pager) {
		List<SysLogRuntime> list = null;
		pager = PagerHelper.getPager(pager, sysProcessService.listSysLogRuntimeCount(sysLogRuntime));
		list = sysProcessService.listSysLogRuntime(sysLogRuntime, pager);
		logger.info("查询 SysLogRuntime 列表...");
		return WebUtilWork.WebResultPack(list, pager);
	}

	/**
	 * 删除 SysLogRuntime
	 * 
	 * @param context
	 * @param request
	 * @param pks
	 */
	public ResultBean deleteSysLogRuntimeByPks(ServletContext context, HttpServletRequest request, long[] pks) {
		sysProcessService.deleteSysLogRuntimeByPks(pks);
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 根据ID获得 SysLogRuntime
	 * 
	 * @param context
	 * @param request
	 * @param pk
	 */
	public ResultBean getSysLogRuntimeByPk(ServletContext context, HttpServletRequest request, long pk) {
		SysLogRuntime sysLogRuntime = sysProcessService.getSysLogRuntimeByPk(pk);
		logger.info("根据ID获得 SysLogRuntime...");
		return WebUtilWork.WebObjectPack(sysLogRuntime);
	}

	public List<SysMethodInfo> listSysmethodInfoByPage(ServletContext context, HttpServletRequest request, String page) {
		List<SysMethodInfo> list = sysProcessService.listSysmethodInfoByPage(page);
		return list;
	}
}
