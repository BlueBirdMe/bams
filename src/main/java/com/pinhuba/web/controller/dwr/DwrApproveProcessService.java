package com.pinhuba.web.controller.dwr;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.GroupQuery;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.MembershipEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.pinhuba.common.activiti.ProcessDefinitionCache;
import com.pinhuba.common.module.ApproveProcessBean;
import com.pinhuba.common.module.HistoricProcessInstanceBean;
import com.pinhuba.common.module.ProcessInstanceBean;
import com.pinhuba.common.module.ProcessModelBean;
import com.pinhuba.common.module.ResultBean;
import com.pinhuba.common.pages.Pager;
import com.pinhuba.common.pages.PagerHelper;
import com.pinhuba.common.util.DateTimeTool;
import com.pinhuba.common.util.EnumUtil;
import com.pinhuba.common.util.UtilPrimaryKey;
import com.pinhuba.common.util.UtilTool;
import com.pinhuba.common.util.WebUtilWork;
import com.pinhuba.core.iservice.IApproveProcessService;
import com.pinhuba.core.iservice.IHrmEmployeeService;
import com.pinhuba.core.pojo.HrmEmployee;
import com.pinhuba.core.pojo.SysProcessConfig;
import com.pinhuba.core.pojo.SysProcessType;
import com.pinhuba.core.pojo.SysUserInfo;

/**********************************************
 * Class name: 流程处理dwr服务
 * Description:对DWR服务进行描述 
 * Others: // 其它内容的说明 History:
 **********************************************/
public class DwrApproveProcessService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private IApproveProcessService approveService;

	@Resource
	private RepositoryService repositoryService;
	
	@Resource
	private IdentityService identityService;

	@Resource
	private RuntimeService runtimeService;

	@Resource
	private HistoryService historyService;

	@Resource
	private IHrmEmployeeService employeeinfoService;

	/**
	 * 
	 * @param context
	 * @param request
	 * @param approveset
	 * @param pager
	 * @return
	 */
	public ResultBean listSysApproveProcessByPager(ServletContext context, HttpServletRequest request, Pager pager) {

		// 保存两个对象，一个是ProcessDefinition（流程定义），一个是Deployment（流程部署）
		List<ApproveProcessBean> processList = new ArrayList<ApproveProcessBean>();
		ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery().orderByDeploymentId().desc();

		pager = PagerHelper.getPager(pager, (int) processDefinitionQuery.count());
		List<ProcessDefinition> processDefinitionList = processDefinitionQuery.listPage(pager.getStartRow(), pager.getPageSize());

		for (ProcessDefinition processDefinition : processDefinitionList) {
			ApproveProcessBean processBean = new ApproveProcessBean();
			String deploymentId = processDefinition.getDeploymentId();
			Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
			SysProcessConfig config = approveService.getSysProcessConfigByPk(processDefinition.getId());
			processBean.setProcessDefinition(processDefinition);
			processBean.setDeploymentTime(DateTimeTool.getStringFromDate(deployment.getDeploymentTime(), "yyyy-MM-dd HH:mm:ss"));
			processBean.setConfig(config != null ? config : new SysProcessConfig());
			processList.add(processBean);
		}

		return WebUtilWork.WebResultPack(processList, pager);
	}

	/**
	 * 
	 * @param context
	 * @param request
	 * @param approveset
	 * @param pager
	 * @return
	 */
	public List<ApproveProcessBean> listSysApproveProcessAll(ServletContext context, HttpServletRequest request) {

		List<ApproveProcessBean> processList = new ArrayList<ApproveProcessBean>();
		ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery().orderByDeploymentId().desc();
		List<ProcessDefinition> processDefinitionList = processDefinitionQuery.list();

		for (ProcessDefinition processDefinition : processDefinitionList) {
			ApproveProcessBean processBean = new ApproveProcessBean();
			String deploymentId = processDefinition.getDeploymentId();
			Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
			SysProcessConfig config = approveService.getSysProcessConfigByPk(processDefinition.getId());
			processBean.setProcessDefinition(processDefinition);
			processBean.setDeploymentTime(DateTimeTool.getStringFromDate(deployment.getDeploymentTime(), "yyyy-MM-dd HH:mm:ss"));
			processBean.setConfig(config != null ? config : new SysProcessConfig());
			processList.add(processBean);
		}

		return processList;
	}

	/**
	 * 模型列表
	 * 
	 * @param context
	 * @param request
	 * @param pager
	 * @return
	 */
	public ResultBean listProcessModelByPager(ServletContext context, HttpServletRequest request, Pager pager) {
		ModelQuery modelQuery = repositoryService.createModelQuery().orderByCreateTime().desc();// 根据创建时间排序

		pager = PagerHelper.getPager(pager, (int) modelQuery.count());
		List<Model> list = modelQuery.listPage(pager.getStartRow(), pager.getPageSize());

		List<ProcessModelBean> beanList = new ArrayList<ProcessModelBean>();

		for (Model model : list) {
			ProcessModelBean bean = new ProcessModelBean();
			bean.setModel(model);
			bean.setCreateTime(DateTimeTool.getStringFromDate(model.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
			bean.setLastUpdateTime(DateTimeTool.getStringFromDate(model.getLastUpdateTime(), "yyyy-MM-dd HH:mm:ss"));
			beanList.add(bean);
		}

		return WebUtilWork.WebResultPack(beanList, pager);
	}

	/**
	 * 
	 * @param context
	 * @param request
	 * @param ids
	 * @return
	 */
	public ResultBean setApproveProcessActionById(ServletContext context, HttpServletRequest request, String id) {
		ProcessDefinition processDefinition = repositoryService.getProcessDefinition(id);
		if (processDefinition.isSuspended()) {
			repositoryService.activateProcessDefinitionById(id, true, null);
		} else {
			repositoryService.suspendProcessDefinitionById(id, true, null);
		}
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 
	 * @param context
	 * @param request
	 * @param ids
	 * @return
	 */
	public ResultBean deleteApproveProcessActionById(ServletContext context, HttpServletRequest request, String deploymentId) {
		List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().deploymentId(deploymentId).list();
		for (ProcessDefinition processDefinition : list) {
			approveService.deleteSysProcessConfigByPk(processDefinition.getId());
		}
		repositoryService.deleteDeployment(deploymentId, true);
		logger.info("删除流程定义及其设置");
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 流程用户组列表
	 * 
	 * @param context
	 * @param request
	 * @param pager
	 * @return
	 */
	public ResultBean listProcessGroupByPager(ServletContext context, HttpServletRequest request, Pager pager) {
		GroupQuery groupQuery = identityService.createGroupQuery();

		pager = PagerHelper.getPager(pager, (int) groupQuery.count());
		List<Group> list = groupQuery.listPage(pager.getStartRow(), pager.getPageSize());
		return WebUtilWork.WebResultPack(list, pager);
	}

	/**
	 * 删除用户组
	 * 
	 * @param context
	 * @param request
	 * @param groupId
	 * @return
	 */
	public ResultBean deleteProcessGroupById(ServletContext context, HttpServletRequest request, String groupId) {
		identityService.deleteGroup(groupId);
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 保存用户组
	 * 
	 * @param context
	 * @param request
	 * @param groupId
	 * @return
	 */
	public ResultBean saveProcessGroup(ServletContext context, HttpServletRequest request, GroupEntity group) {

		Group temp = identityService.createGroupQuery().groupId(group.getId()).singleResult();

		if (temp != null) {
			return new ResultBean(false, "用户组ID已经存在，不能添加！");
		}

		temp = identityService.createGroupQuery().groupName(group.getName()).singleResult();

		if (temp != null) {
			return new ResultBean(false, "用户组名称已经存在，不能添加！");
		}

		identityService.saveGroup(group);
		return WebUtilWork.WebResultPack(null);

	}

	/**
	 * 更新用户组，先删后插入，不完美，需要进一步处理
	 * 
	 * @param context
	 * @param request
	 * @param groupId
	 * @return
	 */
	public ResultBean updateProcessGroup(ServletContext context, HttpServletRequest request, GroupEntity group) {
		identityService.deleteGroup(group.getId());
		identityService.saveGroup(group);
		return WebUtilWork.WebResultPack(null);
	}

	public ResultBean getProcessGroupById(ServletContext context, HttpServletRequest request, String id) {
		Group group = identityService.createGroupQuery().groupId(id).singleResult();
		return WebUtilWork.WebObjectPack(group);
	}

	public ResultBean listSysUserForProcess(ServletContext context, HttpServletRequest request, SysUserInfo userinfo, String depids, Pager pager) {
		HrmEmployee employee = new HrmEmployee();
		employee.setHrmEmployeeDepidTree(depids);
		userinfo.setCompanyId(UtilTool.getCompanyId(request));
		userinfo.setEmployee(employee);

		pager = PagerHelper.getPager(pager, approveService.getSysUserInfoListCount(userinfo));
		List<SysUserInfo> userList = approveService.getSysUserInfoListByPager(userinfo, pager);

		for (SysUserInfo sysUserInfo : userList) {

			List<Group> groupList = approveService.getGroupListByUserId(sysUserInfo.getHrmEmployeeId());

			if (groupList != null && groupList.size() > 0) {
				String groupNames = "";
				for (Group group : groupList) {
					groupNames += group.getName() + "、";
				}
				sysUserInfo.setProcessGroup(groupNames.substring(0, groupNames.length() - 1));
			}
		}
		return WebUtilWork.WebResultPack(userList, pager);
	}

	/**
	 * 用户、组关系设置
	 * 
	 * @param context
	 * @param request
	 * @param pager
	 * @return
	 */
	public ResultBean saveMembership(ServletContext context, HttpServletRequest request, String empId, String[] groupIds) {
		
		User processUser = identityService.createUserQuery().userId(empId).singleResult();
		
		if(processUser != null){
			//删除现有的用户/用户组关系
			GroupQuery groupQuery = identityService.createGroupQuery();
			List<Group> list = groupQuery.list();
			for (Group group : list) {
				identityService.deleteMembership(empId, group.getId());
			}
		}else{
			processUser = new UserEntity();
			processUser.setId(empId);
			identityService.saveUser(processUser);
		}
		
		//创建新的用户/用户组关系
		for (String groupId : groupIds) {
			identityService.createMembership(empId, groupId);
		}

		return WebUtilWork.WebResultPack(null);
	}

	public String getMembership(ServletContext context, HttpServletRequest request, String empId) {

		List<MembershipEntity> list = approveService.getMembership(empId);
		if (list != null && list.size() > 0) {
			String str = "";
			for (MembershipEntity membership : list) {
				str = str + membership.getGroupId() + ",";
			}
			return str.substring(0, str.length() - 1);
		} else {
			return null;
		}
	}

	/**
	 * 运行中流程
	 * 
	 * @param context
	 * @param request
	 * @param pager
	 * @return
	 */
	public ResultBean listProcessRunningByPager(ServletContext context, HttpServletRequest request, Pager pager) {
		ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();

		List<ProcessInstanceBean> beanList = new ArrayList<ProcessInstanceBean>();
		ProcessDefinitionCache.setRepositoryService(repositoryService);

		pager = PagerHelper.getPager(pager, (int) processInstanceQuery.count());
		List<ProcessInstance> list = processInstanceQuery.listPage(pager.getStartRow(), pager.getPageSize());

		for (ProcessInstance proInstance : list) {
			ProcessInstanceBean bean = new ProcessInstanceBean();
			bean.setProcessInstance(proInstance);
			bean.setNodeName(ProcessDefinitionCache.getActivityName(proInstance.getProcessDefinitionId(), proInstance.getActivityId()));
			beanList.add(bean);
		}

		return WebUtilWork.WebResultPack(beanList, pager);
	}

	/**
	 * 运行中流程实例 挂起、激活
	 * 
	 * @param context
	 * @param request
	 * @param ids
	 * @return
	 */
	public ResultBean setSuspensionState(ServletContext context, HttpServletRequest request, String state, String processInstanceId) {
		if (state.equals("active")) {
			runtimeService.activateProcessInstanceById(processInstanceId);
			return new ResultBean(true, "已激活ID为[" + processInstanceId + "]的流程实例。");
		} else if (state.equals("suspend")) {
			runtimeService.suspendProcessInstanceById(processInstanceId);
			return new ResultBean(true, "已挂起ID为[" + processInstanceId + "]的流程实例。");
		}

		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 删除运行中流程实例
	 * 
	 * @param context
	 * @param request
	 * @param ids
	 * @return
	 */
	public ResultBean deleteProcessInstanceById(ServletContext context, HttpServletRequest request, String processInstanceId) {
		runtimeService.deleteProcessInstance(processInstanceId, "");
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 历史流程
	 * 
	 * @param context
	 * @param request
	 * @param pager
	 * @return
	 */
	public ResultBean listProcessHistoryByPager(ServletContext context, HttpServletRequest request, HistoricProcessInstanceBean tmpbean, Pager pager) {

		List<HistoricProcessInstanceBean> beanList = new ArrayList<HistoricProcessInstanceBean>();

		HistoricProcessInstanceQuery query = historyService.createHistoricProcessInstanceQuery();

		if (StringUtils.isNotBlank(tmpbean.getId()))
			query.processInstanceId(tmpbean.getId());// 流水号

		if (StringUtils.isNotBlank(tmpbean.getKey()) && !("-1".equals(tmpbean.getKey())))
			query.processDefinitionKey(tmpbean.getKey());// 类型

		if (tmpbean.getProcessStatus() != null && tmpbean.getProcessStatus() != -1) {
			if (tmpbean.getProcessStatus() == EnumUtil.PROCESS_STATUS.FINISH.value) {
				query.finished();
			} else if (tmpbean.getProcessStatus() == EnumUtil.PROCESS_STATUS.DOING.value) {
				query.unfinished();
			}
		}

		query.orderByProcessInstanceStartTime().desc();

		pager = PagerHelper.getPager(pager, (int) query.count());

		List<HistoricProcessInstance> list = query.listPage(pager.getStartRow(), pager.getPageSize());

		for (HistoricProcessInstance historicProcessInstance : list) {
			HistoricProcessInstanceBean bean = new HistoricProcessInstanceBean();
			ProcessDefinition processDefinition = repositoryService.getProcessDefinition(historicProcessInstance.getProcessDefinitionId());
			HrmEmployee employee = employeeinfoService.getEmployeeByPK(historicProcessInstance.getStartUserId());
			SysProcessConfig config = approveService.getSysProcessConfigByPk(historicProcessInstance.getProcessDefinitionId());
			bean.setHistoricProcessInstance(historicProcessInstance);
			bean.setProcessDefinition(processDefinition);
			bean.setEmployee(employee);
			bean.setConfig(config);
			beanList.add(bean);
		}
		return WebUtilWork.WebResultPack(beanList, pager);
	}

	/**
	 * 删除历史流程实例
	 * 
	 * @param context
	 * @param request
	 * @param ids
	 * @return
	 */
	public ResultBean deleteHistoricProcessInstanceById(ServletContext context, HttpServletRequest request, String processInstanceId) {
		try {
			historyService.deleteHistoricProcessInstance(processInstanceId);
			return WebUtilWork.WebResultPack(null);
		} catch (ActivitiException e) {
			return new ResultBean(false, "该流程正在运行，请先到运行中的流程中删除。");
		}
	}

	/**
	 * 根据ID获得 SysProcessConfig ID和流程定义ID相同
	 * 
	 * @param context
	 * @param request
	 * @param pk
	 */
	public ResultBean getSysProcessConfigByPk(ServletContext context, HttpServletRequest request, String pk) {

		ProcessDefinition definition = repositoryService.getProcessDefinition(pk);
		SysProcessConfig sysProcessConfig = approveService.getSysProcessConfigByPk(pk);
		if (sysProcessConfig != null) {
			sysProcessConfig.setProcessDefinition(definition);
			logger.info("根据ID获得 SysProcessConfig...{}", sysProcessConfig.getPrimaryKey());
		}

		return WebUtilWork.WebObjectPack(sysProcessConfig);
	}

	/**
	 * 更新 SysProcessConfig
	 * 
	 * @param context
	 * @param request
	 * @param sysProcessConfig
	 */
	public ResultBean updateSysProcessConfig(ServletContext context, HttpServletRequest request, SysProcessConfig sysProcessConfig) {
		approveService.saveSysProcessConfig(sysProcessConfig);
		logger.info("更新 SysProcessConfig...");
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 查询 SysProcessType 分页列表
	 * 
	 * @param context
	 * @param request
	 * @param sysProcessType
	 * @param pager
	 */
	public ResultBean listSysProcessType(ServletContext context, HttpServletRequest request, SysProcessType sysProcessType, Pager pager) {
		List<SysProcessType> list = null;
		pager = PagerHelper.getPager(pager, approveService.listSysProcessTypeCount(sysProcessType));
		list = approveService.listSysProcessType(sysProcessType, pager);
		logger.info("查询 SysProcessType 分页列表...");
		return WebUtilWork.WebResultPack(list, pager);
	}

	/**
	 * 查询所有 SysProcessType 列表
	 * 
	 * @param context
	 * @param request
	 * @param sysProcessType
	 * @param pager
	 */
	public ResultBean listSysProcessTypeAll(ServletContext context, HttpServletRequest request) {
		SysProcessType sysProcessType = new SysProcessType();
		List<SysProcessType> list = approveService.listSysProcessType(sysProcessType);

		for (SysProcessType processType : list) {
			List<SysProcessConfig> configList = approveService.listConfigByProcessTypeId(processType.getPrimaryKey());
			for (SysProcessConfig processConfig : configList) {
				processConfig.setProcessDefinition(repositoryService.getProcessDefinition(processConfig.getPrimaryKey()));
			}
			processType.setConfigList(configList);
		}

		logger.info("查询所有 SysProcessType 列表...");
		return WebUtilWork.WebResultPack(list);
	}

	public ResultBean listSysProcessTypeAllForSelect(ServletContext context, HttpServletRequest request) {
		SysProcessType sysProcessType = new SysProcessType();
		List<SysProcessType> list = approveService.listSysProcessType(sysProcessType);
		logger.info("查询所有 SysProcessType 列表...");
		return WebUtilWork.WebResultPack(list);
	}

	/**
	 * 保存 SysProcessType
	 * 
	 * @param context
	 * @param request
	 * @param sysProcessType
	 */
	public ResultBean saveSysProcessType(ServletContext context, HttpServletRequest request, SysProcessType sysProcessType) {
		String empid = UtilTool.getEmployeeId(request);
		sysProcessType.initSave(empid);
		sysProcessType.setPrimaryKey(UtilPrimaryKey.getPrimaryKey());
		approveService.saveSysProcessType(sysProcessType);
		logger.info("保存 SysProcessType...");
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 更新 SysProcessType
	 * 
	 * @param context
	 * @param request
	 * @param sysProcessType
	 */
	public ResultBean updateSysProcessType(ServletContext context, HttpServletRequest request, SysProcessType sysProcessType) {
		String empid = UtilTool.getEmployeeId(request);
		sysProcessType.initUpdate(empid);
		approveService.saveSysProcessType(sysProcessType);
		logger.info("更新 SysProcessType...");
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 根据ID获得 SysProcessType
	 * 
	 * @param context
	 * @param request
	 * @param pk
	 */
	public ResultBean getSysProcessTypeByPk(ServletContext context, HttpServletRequest request, String pk) {
		SysProcessType sysProcessType = approveService.getSysProcessTypeByPk(pk);
		logger.info("根据ID获得 SysProcessType...{}", sysProcessType.getPrimaryKey());
		return WebUtilWork.WebObjectPack(sysProcessType);
	}

	/**
	 * 删除 SysProcessType
	 * 
	 * @param context
	 * @param request
	 * @param pks
	 */
	public ResultBean deleteSysProcessTypeByPks(ServletContext context, HttpServletRequest request, String[] pks) {

		for (String pk : pks) {
			List<SysProcessConfig> configs = approveService.listConfigByProcessTypeId(pk);
			if (configs.size() > 0) {
				return new ResultBean(false, "该分类下已经有流程定义，不能删除！");
			}
		}
		approveService.deleteSysProcessTypeByPks(pks);
		return WebUtilWork.WebResultPack(null);
	}
}
