package com.pinhuba.common.activiti.listener;

import javax.annotation.Resource;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.runtime.ProcessInstance;

import com.pinhuba.common.util.EnumUtil;
import com.pinhuba.core.iservice.IPersonalOfficeSerivce;
import com.pinhuba.core.pojo.OaLeaveregistration;

/**
 * 请假流程结束处理器
 */
public class LeaveFinishProcessor implements ExecutionListener{
	
	private static final long serialVersionUID = 1L;

	@Resource
	private IPersonalOfficeSerivce personalOfficeSerivce;
	@Resource
	private RuntimeService runtimeService;

	public void notify(DelegateExecution execution) {
		String processInstanceId = execution.getProcessInstanceId();
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		OaLeaveregistration leave = personalOfficeSerivce.getOaLeaveregistrationByPk(new Long(processInstance.getBusinessKey()));
		leave.setStatus(EnumUtil.APPLY_STATUS.FINISH.value);
		personalOfficeSerivce.saveOaLeaveregistration(leave);
	}
}
