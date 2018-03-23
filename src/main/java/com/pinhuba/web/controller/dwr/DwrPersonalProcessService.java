package com.pinhuba.web.controller.dwr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import com.pinhuba.common.activiti.ProcessConstants;
import com.pinhuba.common.activiti.cmd.WithdrawTaskCmd;
import com.pinhuba.common.module.HistoricTaskInstanceBean;
import com.pinhuba.common.module.HistoricProcessInstanceBean;
import com.pinhuba.common.module.ResultBean;
import com.pinhuba.common.module.TaskTodoBean;
import com.pinhuba.common.pages.Pager;
import com.pinhuba.common.pages.PagerHelper;
import com.pinhuba.common.util.DateTimeTool;
import com.pinhuba.common.util.EnumUtil;
import com.pinhuba.common.util.UtilTool;
import com.pinhuba.common.util.UtilWork;
import com.pinhuba.common.util.WebUtilWork;
import com.pinhuba.core.iservice.IApproveProcessService;
import com.pinhuba.core.iservice.IHrmEmployeeService;
import com.pinhuba.core.iservice.IPersonalOfficeSerivce;
import com.pinhuba.core.pojo.HrmEmployee;
import com.pinhuba.core.pojo.OaLeaveregistration;
import com.pinhuba.core.pojo.OaTrsvel;
import com.pinhuba.core.pojo.SysProcessConfig;
/**********************************************
Class name: 个人日常工作流程dwr服务
Description:
Others:         
History:        
JC    2014.1.27
**********************************************/
public class DwrPersonalProcessService {
	// 日志文件
	private final static Logger logger = Logger.getLogger(DwrPersonalProcessService.class);
	@Resource
	private IPersonalOfficeSerivce personalOfficeSerivce;
	@Resource
	private IHrmEmployeeService employeeinfoService;
	@Resource
	private IApproveProcessService approveService;
	@Resource
	private TaskService taskService;
	@Resource
	private RuntimeService runtimeService;
	@Resource
	private RepositoryService repositoryService;
	@Resource
	private HistoryService historyService;
	@Resource
	private ManagementService managementService;
	
	public void setApproveService(IApproveProcessService approveService) {
		this.approveService = approveService;
	}

	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}

	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	public RuntimeService getRuntimeService() {
		return runtimeService;
	}

	public void setRuntimeService(RuntimeService runtimeService) {
		this.runtimeService = runtimeService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	public void setEmployeeinfoService(IHrmEmployeeService employeeinfoService) {
		this.employeeinfoService = employeeinfoService;
	}

	public IPersonalOfficeSerivce getPersonalOfficeSerivce() {
		return personalOfficeSerivce;
	}

	public void setPersonalOfficeSerivce(IPersonalOfficeSerivce personalOfficeSerivce) {
		this.personalOfficeSerivce = personalOfficeSerivce;
	}
	
	/**
	 * desktop历史流程
	 * @param context
	 * @param request
	 * @param key
	 * @param row
	 * @return
	 */
	public List<HistoricProcessInstanceBean> listHistoricProcessUnfinished(ServletContext context, HttpServletRequest request, String key, int row) {
		
		String empId = UtilTool.getEmployeeId(request);
		
		//involvedUser 当前用户相关的
		HistoricProcessInstanceQuery query = historyService.createHistoricProcessInstanceQuery().startedBy(empId);
		
		if(StringUtils.isNotBlank(key))
			query.processDefinitionKey(key);//类型
		
		query.unfinished();
		query.orderByProcessInstanceStartTime().desc();
		
		List<HistoricProcessInstance> list = query.listPage(0,  row);
		List<HistoricProcessInstanceBean> beanList = convertHistoricProcessInstanceBean(list);
		
		return beanList;
	}
	
	
	/**
	 * desktop待办任务
	 * @param context
	 * @param request
	 * @param row
	 * @return
	 */
	public List<TaskTodoBean> listTaskTodo(ServletContext context,HttpServletRequest request, Integer row){
		String empId = UtilTool.getEmployeeId(request);
		TaskQuery query = taskService.createTaskQuery().taskCandidateOrAssigned(empId).active();
		
		List<Task> todoList = null;
		if(row != null)
			todoList = query.listPage(0, row);
		else
			todoList = query.list();
		
		List<TaskTodoBean> list = convertTasktodoBean(todoList);
		return list;
	}
	
	
	/**
	 * 所有待办工作
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean getTaskTodo(ServletContext context,HttpServletRequest request, Pager pager){
		String empId = UtilTool.getEmployeeId(request);
		TaskQuery query = taskService.createTaskQuery().taskCandidateOrAssigned(empId).active();
		pager = PagerHelper.getPager(pager, (int)query.count());
	    List<Task> todoList = query.listPage(pager.getStartRow(), pager.getPageSize());
	    List<TaskTodoBean> list = convertTasktodoBean(todoList);
		return WebUtilWork.WebResultPack(list, pager);
	}

	/**
	 * 转化成自定义的bean
	 * @param todoList
	 * @return
	 */
	private List<TaskTodoBean> convertTasktodoBean(List<Task> todoList) {
		List<TaskTodoBean> list = new ArrayList<TaskTodoBean>();
		
		for (Task task : todoList) {
	      String processDefinitionId = task.getProcessDefinitionId();
	      ProcessDefinition processDefinition = repositoryService.getProcessDefinition(processDefinitionId);
	      ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
	      
	      String startUserId = (String) taskService.getVariables(task.getId()).get(ProcessConstants.KEY_APPLY_USER_ID);
	      HrmEmployee employee = employeeinfoService.getEmployeeByPK(startUserId);
			
	      SysProcessConfig config = approveService.getSysProcessConfigByPk(processDefinitionId);
	      TaskTodoBean bean = new TaskTodoBean();
	      bean.setTask(task);
	      bean.setProcessDefinition(processDefinition);
	      bean.setProcessInstance(processInstance);
	      bean.setConfig(config);
	      bean.setEmployee(employee);
	      list.add(bean);
	    }
		return list;
	}
	
	
	/**
	 * 签收任务
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean claimTask(ServletContext context, HttpServletRequest request, String taskId) {
		String empId = UtilTool.getEmployeeId(request);
	    taskService.claim(taskId, empId);
	    return WebUtilWork.WebResultPack(null);
	}
	
	
	/**
	 * 部门领导办理请假任务
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean completeLeaveTaskForDeptLeader(ServletContext context, HttpServletRequest request, String taskId, Boolean isPass, String deptLeaderTxt) {
		//设置流程变量
		Map<String, Object> variables = taskService.getVariables(taskId);
		variables.put(ProcessConstants.KEY_DEPT_LEADER_PASS, isPass);
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		taskService.addComment(taskId, task.getProcessInstanceId(), deptLeaderTxt);
		taskService.complete(taskId, variables);
		
		return WebUtilWork.WebResultPack(null);
	}
	
	/**
	 * 人事办理请假任务
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean completeLeaveTaskForHr(ServletContext context, HttpServletRequest request, String taskId, Boolean isPass, String hrTxt) {
		//设置流程变量
		Map<String, Object> variables = taskService.getVariables(taskId);
		variables.put(ProcessConstants.KEY_HR_PASS, isPass);
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		taskService.addComment(taskId, task.getProcessInstanceId(), hrTxt);
		taskService.complete(taskId, variables);
		
		return WebUtilWork.WebResultPack(null);
	}
	
	/**
	 * 调整请假信息
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean completeLeaveTaskForApplyer(ServletContext context, HttpServletRequest request, String taskId, String applyTxt, OaLeaveregistration temp) {
		
		OaLeaveregistration leave = personalOfficeSerivce.getOaLeaveregistrationByPk(temp.getPrimaryKey());
		leave.setLeavetype(temp.getLeavetype());
		leave.setStartdata(temp.getStartdata());
		leave.setEnddata(temp.getEnddata());
		leave.setLeavereason(temp.getLeavereason());
		leave.setLastmodiId(UtilTool.getEmployeeId(request));
		leave.setLastmodiDate(UtilWork.getNowTime());
		personalOfficeSerivce.saveOaLeaveregistration(leave);
		
		//设置流程变量
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		taskService.addComment(taskId, task.getProcessInstanceId(), applyTxt);
		taskService.complete(taskId);
		return WebUtilWork.WebResultPack(null);
	}
	
	
	/**
	 * 部门领导办理出差任务
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean completeTrsvelTaskForDeptLeader(ServletContext context, HttpServletRequest request, String taskId, Boolean isPass, String deptLeaderTxt) {
		//设置流程变量
		Map<String, Object> variables = taskService.getVariables(taskId);
		variables.put(ProcessConstants.KEY_DEPT_LEADER_PASS, isPass);
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		taskService.addComment(taskId, task.getProcessInstanceId(), deptLeaderTxt);
		taskService.complete(taskId, variables);
		
		return WebUtilWork.WebResultPack(null);
	}
	
	/**
	 * 人事办理出差任务
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean completeTrsvelTaskForHr(ServletContext context, HttpServletRequest request, String taskId) {
		taskService.complete(taskId);
		return WebUtilWork.WebResultPack(null);
	}
	
	/**
	 * 调整出差信息
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean completeTrsvelTaskForApplyer(ServletContext context, HttpServletRequest request, String taskId, OaTrsvel temp) {
		OaTrsvel trsvel = personalOfficeSerivce.getOaTrsvelByPk(temp.getPrimaryKey());
		trsvel.setTrsvelArea(temp.getTrsvelArea());
		trsvel.setTrsvelBegindata(temp.getTrsvelBegindata());
		trsvel.setTrsvelEnddata(temp.getTrsvelEnddata());
		trsvel.setTrsvelCause(temp.getTrsvelCause());
		trsvel.setLastmodiId(UtilTool.getEmployeeId(request));
		trsvel.setLastmodiDate(UtilWork.getNowTime());
		personalOfficeSerivce.saveOaTrsvel(trsvel);
		taskService.complete(taskId);
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 启动请假流程
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean addOaLeaver(ServletContext context,HttpServletRequest request, String employeeId, OaLeaveregistration oaLeaver) {
		String time = UtilWork.getNowTime();
		String empId = UtilTool.getEmployeeId(request);
		try {
			oaLeaver.setApplyuser(empId);
			oaLeaver.setApplydata(time);
			oaLeaver.setCompanyId(UtilTool.getCompanyId(request));
			oaLeaver.setLastmodiId(empId);
			oaLeaver.setLastmodiDate(time);
			oaLeaver.setRecordDate(time);
			oaLeaver.setRecordId(empId);
			oaLeaver.setStatus(EnumUtil.APPLY_STATUS.DOING.value);
			
			//设置流程变量
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put(ProcessConstants.KEY_DEPT_LEADER_ID, employeeId);
			//启动流程
			ProcessInstance processInstance = personalOfficeSerivce.startOaLeaveWorkflow(oaLeaver, variables);
			return new ResultBean(true, "流程已启动，流程ID：" + processInstance.getId());
		} catch (Exception e) {
			logger.error("启动请假流程失败：", e);
			return new ResultBean(false, "系统内部错误！");
		}
	}
	public ResultBean saveOaLeaver(ServletContext context,HttpServletRequest request, OaLeaveregistration oaLeaver) {
		String time = UtilWork.getNowTime();
		oaLeaver.setApplyuser(UtilTool.getEmployeeId(request));
		oaLeaver.setApplydata(time);
		oaLeaver.setCompanyId(UtilTool.getCompanyId(request));
		oaLeaver.setLastmodiId(UtilTool.getEmployeeId(request));
		oaLeaver.setLastmodiDate(time);
		oaLeaver.setRecordDate(time);
		oaLeaver.setRecordId(UtilTool.getEmployeeId(request));
		oaLeaver.setStatus(EnumUtil.APPLY_STATUS.DRAFT.value);
		personalOfficeSerivce.saveOaLeaveregistration(oaLeaver);
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 请假登记根据ID取值
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean getOaLeaverByPk(ServletContext context, HttpServletRequest request, long OaLeaverpk) {
		OaLeaveregistration leave = personalOfficeSerivce.getOaLeaveregistrationByPk(OaLeaverpk);
		leave.setApplyEmployee(employeeinfoService.getEmployeeByPK(leave.getApplyuser()));
		leave.setLibrary(UtilTool.getLibraryInfoByPk(context, request, leave.getLeavetype()));
		return WebUtilWork.WebObjectPack(leave);
	}
	
	/**
	 * 历史流程
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean findHistoryTasks(ServletContext context, HttpServletRequest request, HistoricProcessInstanceBean tmpbean, Pager pager) {
		
		String empId = UtilTool.getEmployeeId(request);
		
		//involvedUser 当前用户相关的
		HistoricProcessInstanceQuery query = historyService.createHistoricProcessInstanceQuery().involvedUser(empId);
		
		if(tmpbean.getScope() != null && tmpbean.getScope() != -1){
			if(tmpbean.getScope() == EnumUtil.WORKFLOW_SCOPE.MYJOIN.value){
				query.involvedUser(empId);
			}else if(tmpbean.getScope() == EnumUtil.WORKFLOW_SCOPE.MYSTART.value){
				query.startedBy(empId);
			}
		}
		
		if(StringUtils.isNotBlank(tmpbean.getId()))
			query.processInstanceId(tmpbean.getId());//流水号
		
		if(StringUtils.isNotBlank(tmpbean.getKey()) && !("-1".equals(tmpbean.getKey())))
			query.processDefinitionKey(tmpbean.getKey());//类型
		
		if(tmpbean.getProcessStatus() != null && tmpbean.getProcessStatus() != -1){
			if(tmpbean.getProcessStatus() == EnumUtil.PROCESS_STATUS.FINISH.value){
				query.finished();
			}else if(tmpbean.getProcessStatus() == EnumUtil.PROCESS_STATUS.DOING.value){
				query.unfinished();
			}
		}
		
		query.orderByProcessInstanceStartTime().desc();
		
		pager = PagerHelper.getPager(pager, (int)query.count());
		
		List<HistoricProcessInstance>  list = query.listPage(pager.getStartRow(),  pager.getPageSize());
		
		List<HistoricProcessInstanceBean> beanList = convertHistoricProcessInstanceBean(list);
	    return WebUtilWork.WebResultPack(beanList, pager);
	}

	private List<HistoricProcessInstanceBean> convertHistoricProcessInstanceBean(List<HistoricProcessInstance> list) {
		
		List<HistoricProcessInstanceBean> beanList = new ArrayList<HistoricProcessInstanceBean>();
		
		for (HistoricProcessInstance historicProcessInstance : list) {
			HistoricProcessInstanceBean bean = new HistoricProcessInstanceBean();
			ProcessDefinition processDefinition = repositoryService.getProcessDefinition(historicProcessInstance.getProcessDefinitionId());
			SysProcessConfig config = approveService.getSysProcessConfigByPk(historicProcessInstance.getProcessDefinitionId());
			HrmEmployee employee = employeeinfoService.getEmployeeByPK(historicProcessInstance.getStartUserId());
			
			bean.setHistoricProcessInstance(historicProcessInstance);
			bean.setProcessDefinition(processDefinition);
			bean.setEmployee(employee);
			bean.setConfig(config);
			beanList.add(bean);
		}
		
		return beanList;
	}
	
	
	/**
	 * 流程实例历史任务详情
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean findHistoryDetail(ServletContext context, HttpServletRequest request, String id) {
		List<HistoricTaskInstance> instances = historyService.createHistoricTaskInstanceQuery().processInstanceId(id).list();
		List<HistoricTaskInstanceBean> beans = convertHistoricTaskInstanceBean(instances);
	    return WebUtilWork.WebResultPack(beans);
	}
	
	/**
	 * 所有历史任务详情
	 * @param context
	 * @param request
	 * @param pager
	 * @return
	 */
	public ResultBean getTaskDone(ServletContext context,HttpServletRequest request, Pager pager){
		String empId = UtilTool.getEmployeeId(request);
		HistoricTaskInstanceQuery query = historyService.createHistoricTaskInstanceQuery().taskAssignee(empId).finished().orderByHistoricTaskInstanceStartTime().desc();
		pager = PagerHelper.getPager(pager, (int)query.count());
		List<HistoricTaskInstance> list = query.listPage(pager.getStartRow(), pager.getPageSize());
		List<HistoricTaskInstanceBean> beans = convertHistoricTaskInstanceBean(list);
		return WebUtilWork.WebResultPack(beans, pager);
	}
	

	private List<HistoricTaskInstanceBean> convertHistoricTaskInstanceBean(List<HistoricTaskInstance> instances) {
		
		List<HistoricTaskInstanceBean> beans = new ArrayList<HistoricTaskInstanceBean>();
		
		for (HistoricTaskInstance historicTaskInstance : instances) {
			HistoricTaskInstanceBean bean = new HistoricTaskInstanceBean();
			
			ProcessDefinition processDefinition = repositoryService.getProcessDefinition(historicTaskInstance.getProcessDefinitionId());
			HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(historicTaskInstance.getProcessInstanceId()).singleResult();
			SysProcessConfig config = approveService.getSysProcessConfigByPk(historicTaskInstance.getProcessDefinitionId());
			
			HrmEmployee employee = new HrmEmployee();
			if(StringUtils.isNotBlank(historicTaskInstance.getAssignee()))
				employee = employeeinfoService.getEmployeeByPK(historicTaskInstance.getAssignee());
			
			bean.setHistoricTaskInstance(historicTaskInstance);
			bean.setEmployee(employee);
			bean.setProcessDefinition(processDefinition);
			bean.setHistoricProcessInstance(historicProcessInstance);
			bean.setConfig(config);
			
			List<Comment> commentList = taskService.getTaskComments(historicTaskInstance.getId());
			
			if(commentList != null && commentList.size() > 0){
				bean.setCommentList(commentList);
			}else{
				bean.setCommentList(new ArrayList<Comment>());
			}
			
			beans.add(bean);
		}
		
		return beans;
	}
	
	/**
	 * 撤销已办任务
	 * @param context
	 * @param request
	 * @param taskId
	 * @return
	 */
	public ResultBean withdrawTask(ServletContext context,HttpServletRequest request, String taskId){
		Command<Integer> cmd = new WithdrawTaskCmd(taskId);
		Integer result = managementService.executeCommand(cmd);
		if(result == 2)
			return new ResultBean(false, "下一任务已经通过,不能撤销");
	
		return WebUtilWork.WebResultPack(null);
	}
	

	/**
	 * 启动出差流程
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean addoaTrsvel(ServletContext context, HttpServletRequest request, OaTrsvel oaTrsvel, String employeeId) {
		String time = UtilWork.getNowTime();
		
		try {
			oaTrsvel.setTrsvelApplyuser(UtilTool.getEmployeeId(request));
			oaTrsvel.setApplydata(time);
			oaTrsvel.setCompanyId(UtilTool.getCompanyId(request));
			oaTrsvel.setRecordDate(time);
			oaTrsvel.setRecordId(UtilTool.getEmployeeId(request));
			oaTrsvel.setLastmodiId(UtilTool.getEmployeeId(request));
			oaTrsvel.setLastmodiDate(time);
			oaTrsvel.setStatus(EnumUtil.APPLY_STATUS.DOING.value);
			//设置流程变量
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put(ProcessConstants.KEY_DEPT_LEADER_ID, employeeId);

			//启动流程
			ProcessInstance processInstance = personalOfficeSerivce.startOaTrsvelWorkflow(oaTrsvel, variables);
			
			return new ResultBean(true, "流程已启动，流程ID：" + processInstance.getId());
		} catch (Exception e) {
			logger.error("启动请假流程失败：", e);
			return new ResultBean(false, "系统内部错误！");
		}
	}
	
	public ResultBean saveOaTrsvel(ServletContext context,HttpServletRequest request, OaTrsvel oaTrsvel) {
		String time = UtilWork.getNowTime();
		oaTrsvel.setTrsvelApplyuser(UtilTool.getEmployeeId(request));
		oaTrsvel.setApplydata(time);
		oaTrsvel.setCompanyId(UtilTool.getCompanyId(request));
		oaTrsvel.setLastmodiId(UtilTool.getEmployeeId(request));
		oaTrsvel.setLastmodiDate(time);
		oaTrsvel.setRecordDate(time);
		oaTrsvel.setRecordId(UtilTool.getEmployeeId(request));
		oaTrsvel.setStatus(EnumUtil.APPLY_STATUS.DRAFT.value);
		personalOfficeSerivce.saveOaTrsvel(oaTrsvel);
		return WebUtilWork.WebResultPack(null);
	}
	

	/**
	 * 出差ID取值
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean getOaTrsvelByPk(ServletContext context, HttpServletRequest request, long OaLeaverpk) throws Exception {
		OaTrsvel oaTrsvel = personalOfficeSerivce.getOaTrsvelByPk(OaLeaverpk);
		oaTrsvel.setApplyEmployee(employeeinfoService.getEmployeeByPK(oaTrsvel.getTrsvelApplyuser()));
		return WebUtilWork.WebObjectPack(oaTrsvel);
	}

	/**
	 * 显示所有请假登记
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean listAllOaLeaver(ServletContext context, HttpServletRequest request, OaLeaveregistration oaLeaveregistration, Pager paer) {
		List<OaLeaveregistration> Leavelist = null;
		oaLeaveregistration.setCompanyId(UtilTool.getCompanyId(request));
		paer = PagerHelper.getPager(paer, personalOfficeSerivce.listAllOaLeaveregistrationCount(oaLeaveregistration));
		Leavelist = personalOfficeSerivce.getAllOaLeaveregistration(oaLeaveregistration, paer);
		for (OaLeaveregistration leaver : Leavelist) {
			leaver.setLibrary(UtilTool.getLibraryInfoByPk(context, request, leaver.getLeavetype()));
			leaver.setApplyEmployee(employeeinfoService.getEmployeeByPK(leaver.getApplyuser()));
		}
		return WebUtilWork.WebResultPack(Leavelist, paer);
	}
	

	/**
	 * 个人请假管理
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean listOaLeaver(ServletContext context, HttpServletRequest request, OaLeaveregistration oaLeaveregistration, Pager paer) {
		List<OaLeaveregistration> Leavelist = null;
		oaLeaveregistration.setCompanyId(UtilTool.getCompanyId(request));
		oaLeaveregistration.setApplyuser(UtilTool.getEmployeeId(request));
		paer = PagerHelper.getPager(paer, personalOfficeSerivce.listAllOaLeaveregistrationCount(oaLeaveregistration));
		Leavelist = personalOfficeSerivce.getAllOaLeaveregistration(oaLeaveregistration, paer);
		for (OaLeaveregistration leaver : Leavelist) {
			leaver.setLibrary(UtilTool.getLibraryInfoByPk(context, request, leaver.getLeavetype()));
		}
		return WebUtilWork.WebResultPack(Leavelist, paer);
	}
	
	
	/**
    * 删除个人请假
    * @param context
    * @param request
    * @param pks
    */
	public ResultBean deleteOaLeaverByPks(ServletContext context, HttpServletRequest request, long[] pks) {
		for (long pk : pks) {
			OaLeaveregistration oaLeave = personalOfficeSerivce.getOaLeaveregistrationByPk(pk);
			if (oaLeave.getStatus() != EnumUtil.APPLY_STATUS.DRAFT.value) {
				return new ResultBean(false, "存在流程数据，请联系统系管理员删除！");
			}
		}
		personalOfficeSerivce.deleteOaLeaveregistration(pks);
		return WebUtilWork.WebResultPack(null);
	}
	
	/**
    * 管理员删除请假
    * @param context
    * @param request
    * @param pks
    */
	public ResultBean deleteOaLeaverByPksSuper(ServletContext context, HttpServletRequest request, long[] pks) {
		personalOfficeSerivce.deleteOaLeave(pks);
		return WebUtilWork.WebResultPack(null);
	}
	
	/**
	 * 出差显示
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean listAllOaTrsvel(ServletContext context, HttpServletRequest request, OaTrsvel oaTrsvel, Pager paer) {
		List<OaTrsvel> oaTrsvellist = null;
		try {
			oaTrsvel.setCompanyId(UtilTool.getCompanyId(request));
		
			paer = PagerHelper.getPager(paer, personalOfficeSerivce.listOaTrsvelCount(oaTrsvel));
			oaTrsvellist = personalOfficeSerivce.getOaTrsvel(oaTrsvel, paer);
			for (OaTrsvel oaTrsvel2 : oaTrsvellist) {
				oaTrsvel2.setApplyEmployee(employeeinfoService.getEmployeeByPK(oaTrsvel2.getTrsvelApplyuser()));
			}
			
			logger.info("出差显示取值");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return WebUtilWork.WebResultPack(oaTrsvellist, paer);
	}
	
	
	/**
	 * 个人出差管理
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean listOaTrsvel(ServletContext context, HttpServletRequest request, OaTrsvel oaTrsvel, Pager paer) {
		List<OaTrsvel> oaTrsvellist = null;
		try {
			oaTrsvel.setCompanyId(UtilTool.getCompanyId(request));
			oaTrsvel.setTrsvelApplyuser(UtilTool.getEmployeeId(request));
			paer = PagerHelper.getPager(paer, personalOfficeSerivce.listOaTrsvelCount(oaTrsvel));
			oaTrsvellist = personalOfficeSerivce.getOaTrsvel(oaTrsvel, paer);
			logger.info("出差显示取值");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return WebUtilWork.WebResultPack(oaTrsvellist, paer);
	}
	
	

	public ResultBean deleteOaTrsvelByPks(ServletContext context, HttpServletRequest request, long[] pks) {
		for (long pk : pks) {
			OaTrsvel oaTrsvel = personalOfficeSerivce.getOaTrsvelByPk(pk);
			if (oaTrsvel.getStatus() != EnumUtil.APPLY_STATUS.DRAFT.value) {
				return new ResultBean(false, "存在流程数据，不能删除！");
			}
		}
		personalOfficeSerivce.deleteOaTrsvel(pks);
		return WebUtilWork.WebResultPack(null);
	}
	
	/**
    * 管理员删除出差信息
    * @param context
    * @param request
    * @param pks
    */
	public ResultBean deleteOaTrsvelByPksSuper(ServletContext context, HttpServletRequest request, long[] pks) {
		personalOfficeSerivce.deleteOaTrsvelByPksSuper(pks);
		return WebUtilWork.WebResultPack(null);
	}
	
	/**
	 * 判读流程实例是否结束
	 * @param id
	 * @return
	 */
	public Boolean checkProcessFinish(String id){
		ProcessInstance instance = runtimeService.createProcessInstanceQuery().processInstanceId(id).singleResult();
		return instance == null?true:false;
	}
}
