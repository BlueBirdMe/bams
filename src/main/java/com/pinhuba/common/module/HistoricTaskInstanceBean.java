package com.pinhuba.common.module;

import java.util.List;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Comment;
import org.apache.commons.lang.StringUtils;
import com.pinhuba.common.util.DateTimeTool;
import com.pinhuba.common.util.EnumUtil;
import com.pinhuba.core.pojo.HrmEmployee;
import com.pinhuba.core.pojo.SysProcessConfig;

public class HistoricTaskInstanceBean {
	private HistoricTaskInstance historicTaskInstance;
	private ProcessDefinition processDefinition;
	private HistoricProcessInstance historicProcessInstance;
	private HrmEmployee employee;
	private List<Comment> commentList;
	private SysProcessConfig config;

	public String getWorkName() {
		return getProcessDefinition().getName() + "(" + getCreateTime() + ")";
	}

	public HistoricTaskInstance getHistoricTaskInstance() {
		return historicTaskInstance;
	}

	public void setHistoricTaskInstance(HistoricTaskInstance historicTaskInstance) {
		this.historicTaskInstance = historicTaskInstance;
	}

	public HrmEmployee getEmployee() {
		return employee;
	}

	public void setEmployee(HrmEmployee employee) {
		this.employee = employee;
	}

	public String getDurationTime() {
		if(historicTaskInstance.getDurationInMillis() != null){
			return DateTimeTool.periodToString(getHistoricTaskInstance().getDurationInMillis());
		}
		return "";
	}

	public String getInstanceStartTime() {
		return DateTimeTool.getStringFromDate(getHistoricTaskInstance().getStartTime(), "yyyy-MM-dd HH:mm:ss");
	}

	public String getInstanceEndTime() {
		return DateTimeTool.getStringFromDate(getHistoricTaskInstance().getEndTime(), "yyyy-MM-dd HH:mm:ss");
	}

	public List<Comment> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}

	public ProcessDefinition getProcessDefinition() {
		return processDefinition;
	}

	public void setProcessDefinition(ProcessDefinition processDefinition) {
		this.processDefinition = processDefinition;
	}

	public HistoricProcessInstance getHistoricProcessInstance() {
		return historicProcessInstance;
	}

	public void setHistoricProcessInstance(HistoricProcessInstance historicProcessInstance) {
		this.historicProcessInstance = historicProcessInstance;
	}

	public String getCreateTime() {
		return DateTimeTool.getStringFromDate(getHistoricProcessInstance().getStartTime(), "yyyy-MM-dd HH:mm:ss");
	}

	public SysProcessConfig getConfig() {
		return config;
	}

	public void setConfig(SysProcessConfig config) {
		this.config = config;
	}

	public String getTaskStatus() {
		if (StringUtils.isNotBlank(getInstanceEndTime())) {
			return EnumUtil.TASK_STATUS.valueOf(EnumUtil.TASK_STATUS.FINISH.value);
		} else {
			return EnumUtil.TASK_STATUS.valueOf(EnumUtil.TASK_STATUS.DOING.value);
		}
	}
}
