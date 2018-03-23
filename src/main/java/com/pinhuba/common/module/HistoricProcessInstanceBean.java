package com.pinhuba.common.module;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.ProcessDefinition;

import com.pinhuba.common.util.DateTimeTool;
import com.pinhuba.core.pojo.HrmEmployee;
import com.pinhuba.core.pojo.SysProcessConfig;

public class HistoricProcessInstanceBean {
	private HistoricProcessInstance historicProcessInstance;
	private ProcessDefinition processDefinition;
	private HrmEmployee employee;
	private Integer scope;// 范围
	private String key;
	private String id;
	private Integer processStatus;
	private SysProcessConfig config;

	public String getWorkName() {
		return getProcessDefinition().getName() + "(" + getInstanceStartTime() + ")";
	}

	public HistoricProcessInstance getHistoricProcessInstance() {
		return historicProcessInstance;
	}

	public void setHistoricProcessInstance(HistoricProcessInstance historicProcessInstance) {
		this.historicProcessInstance = historicProcessInstance;
	}

	public ProcessDefinition getProcessDefinition() {
		return processDefinition;
	}

	public void setProcessDefinition(ProcessDefinition processDefinition) {
		this.processDefinition = processDefinition;
	}

	public HrmEmployee getEmployee() {
		return employee;
	}

	public void setEmployee(HrmEmployee employee) {
		this.employee = employee;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getScope() {
		return scope;
	}

	public void setScope(Integer scope) {
		this.scope = scope;
	}

	public Integer getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(Integer processStatus) {
		this.processStatus = processStatus;
	}

	public String getInstanceStartTime() {
		return DateTimeTool.getStringFromDate(getHistoricProcessInstance().getStartTime(), "yyyy-MM-dd HH:mm:ss");
	}

	public String getInstanceEndTime() {
		return DateTimeTool.getStringFromDate(getHistoricProcessInstance().getEndTime(), "yyyy-MM-dd HH:mm:ss");
	}

	public SysProcessConfig getConfig() {
		return config;
	}

	public void setConfig(SysProcessConfig config) {
		this.config = config;
	}

}
