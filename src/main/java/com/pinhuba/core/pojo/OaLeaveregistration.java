package com.pinhuba.core.pojo;

import java.util.Map;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import com.pinhuba.common.util.EnumUtil;

/**
 * 数据库表名：OA_LEAVEREGISTRATION
 */
public class OaLeaveregistration extends BaseBean implements java.io.Serializable {

	/**
	 * 请假申请表
	 */
	private static final long serialVersionUID = 5654626783176152238L;
	
	private String processInstanceId;
	
	private String applyuser; 		// 申请人
	private String leavereason; 	// 请假原因
	private Integer leavetype; 		// 请假类型
	private String startdata; 		// 请假时间
	private String enddata; 		// 结束
	private String applydata;  		//申请时间
	
	private String realityStartTime;
	private String realityEndTime;
	
	private String recordId;
	private String recordDate;
	private String lastmodiId;
	private String lastmodiDate;
	private Integer companyId;
	private Integer status;

	
	//-- 临时属性 --//
	
	// 流程任务
	private Task task;
	
	private Map<String, Object> variables;
	
	// 运行中的流程实例
	private ProcessInstance processInstance;
	
	// 历史的流程实例
	private HistoricProcessInstance historicProcessInstance;
	
	// 流程定义
	private ProcessDefinition processDefinition;
	
	private HrmEmployee applyEmployee;

	private SysLibraryInfo library;

	public SysLibraryInfo getLibrary() {
		return library;
	}

	public void setLibrary(SysLibraryInfo library) {
		this.library = library;
	}

	// 默认构造方法
	public OaLeaveregistration() {
		super();
	}

	// get和set方法
	public String getApplyuser() {
		return applyuser;
	}

	public void setApplyuser(String aApplyuser) {
		this.applyuser = aApplyuser;
	}

	public String getLeavereason() {
		return leavereason;
	}

	public void setLeavereason(String aLeavereason) {
		this.leavereason = aLeavereason;
	}

	public Integer getLeavetype() {
		return leavetype;
	}

	public void setLeavetype(Integer aLeavetype) {
		this.leavetype = aLeavetype;
	}

	public String getStartdata() {
		return startdata;
	}

	public void setStartdata(String aStartdata) {
		this.startdata = aStartdata;
	}

	public String getEnddata() {
		return enddata;
	}

	public void setEnddata(String aEnddata) {
		this.enddata = aEnddata;
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

	public void setApplydata(String applydata) {
		this.applydata = applydata;
	}

	public HrmEmployee getApplyEmployee() {
		return applyEmployee;
	}

	public void setApplyEmployee(HrmEmployee applyEmployee) {
		this.applyEmployee = applyEmployee;
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

	public String getRealityStartTime() {
		return realityStartTime;
	}

	public void setRealityStartTime(String realityStartTime) {
		this.realityStartTime = realityStartTime;
	}

	public String getRealityEndTime() {
		return realityEndTime;
	}

	public void setRealityEndTime(String realityEndTime) {
		this.realityEndTime = realityEndTime;
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