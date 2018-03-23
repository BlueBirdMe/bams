package com.pinhuba.core.pojo;

import java.util.Map;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import com.pinhuba.common.util.EnumUtil;

/**
 * 数据库表名：OA_TRSVEL
 */
public class OaTrsvel extends BaseBean implements java.io.Serializable {

	/**
	 * 出差申请
	 */
	private static final long serialVersionUID = 5284643934232520307L;
	
	
	private String processInstanceId;
	
	private String trsvelArea; // 出差地方
	private String trsvelBegindata; // 开始时间
	private String trsvelEnddata; // 结束时间
	private String trsvelCause; // 原因
	private String trsvelApplyuser; // 申请人
	private String recordId;
	private String recordDate;
	private String lastmodiId;
	private String lastmodiDate;
	private Integer companyId;
	private String applydata; // 申请时间
	private Integer status;
	//临时变量
	private HrmEmployee applyEmployee;
	
	// 流程任务
	private Task task;
	
	private Map<String, Object> variables;
	
	// 运行中的流程实例
	private ProcessInstance processInstance;
	
	// 历史的流程实例
	private HistoricProcessInstance historicProcessInstance;
	
	// 流程定义
	private ProcessDefinition processDefinition;
	
	
	// 默认构造方法
	public OaTrsvel() {
		super();
	}

	// get和set方法
	public String getTrsvelArea() {
		return trsvelArea;
	}

	public void setTrsvelArea(String aTrsvelArea) {
		this.trsvelArea = aTrsvelArea;
	}

	public String getTrsvelBegindata() {
		return trsvelBegindata;
	}

	public void setTrsvelBegindata(String aTrsvelBegindata) {
		this.trsvelBegindata = aTrsvelBegindata;
	}

	public String getTrsvelEnddata() {
		return trsvelEnddata;
	}

	public void setTrsvelEnddata(String aTrsvelEnddata) {
		this.trsvelEnddata = aTrsvelEnddata;
	}

	public String getTrsvelCause() {
		return trsvelCause;
	}

	public void setTrsvelCause(String aTrsvelCause) {
		this.trsvelCause = aTrsvelCause;
	}

	public String getTrsvelApplyuser() {
		return trsvelApplyuser;
	}

	public void setTrsvelApplyuser(String trsvelApplyuser) {
		this.trsvelApplyuser = trsvelApplyuser;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String aRecordId) {
		this.recordId = aRecordId;
	}

	public String getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(String aRecordDate) {
		this.recordDate = aRecordDate;
	}

	public String getLastmodiId() {
		return lastmodiId;
	}

	public void setLastmodiId(String aLastmodiId) {
		this.lastmodiId = aLastmodiId;
	}

	public String getLastmodiDate() {
		return lastmodiDate;
	}

	public void setLastmodiDate(String aLastmodiDate) {
		this.lastmodiDate = aLastmodiDate;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer aCompanyId) {
		this.companyId = aCompanyId;
	}

	public String getApplydata() {
		return applydata;
	}

	public void setApplydata(String aApplydata) {
		this.applydata = aApplydata;
	}
	
	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public Map<String, Object> getVariables() {
		return variables;
	}

	public void setVariables(Map<String, Object> variables) {
		this.variables = variables;
	}

	public ProcessInstance getProcessInstance() {
		return processInstance;
	}

	public void setProcessInstance(ProcessInstance processInstance) {
		this.processInstance = processInstance;
	}

	public HistoricProcessInstance getHistoricProcessInstance() {
		return historicProcessInstance;
	}

	public void setHistoricProcessInstance(
			HistoricProcessInstance historicProcessInstance) {
		this.historicProcessInstance = historicProcessInstance;
	}

	public ProcessDefinition getProcessDefinition() {
		return processDefinition;
	}

	public void setProcessDefinition(ProcessDefinition processDefinition) {
		this.processDefinition = processDefinition;
	}

	public HrmEmployee getApplyEmployee() {
		return applyEmployee;
	}

	public void setApplyEmployee(HrmEmployee applyEmployee) {
		this.applyEmployee = applyEmployee;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getStatusName(){
		String statusName = "";
		Integer status = getStatus();
		if(status == EnumUtil.APPLY_STATUS.DRAFT.value){
			statusName = "<font color='brown'>"+EnumUtil.APPLY_STATUS.valueOf(status)+"</font>";
		}else if(status == EnumUtil.APPLY_STATUS.DOING.value){
			statusName = "<font color='red'>"+EnumUtil.APPLY_STATUS.valueOf(status)+"</font>";
		}else if(status == EnumUtil.APPLY_STATUS.FINISH.value){
			statusName = "<font color='green'>"+EnumUtil.APPLY_STATUS.valueOf(status)+"</font>";
		}
		return statusName;

	}
}