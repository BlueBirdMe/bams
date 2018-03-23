package com.pinhuba.core.pojo;

import org.activiti.engine.repository.ProcessDefinition;

/**
 * 数据库表名：sys_process_config
 */
public class SysProcessConfig extends BaseStringBean implements java.io.Serializable {

	
	private SysProcessType processType;
	private String processDesc;
    private String startPage;
    private String handlePage;
    private String detailPage;
    private ProcessDefinition processDefinition;

    //默认构造方法
    public SysProcessConfig(){
        super();
    }

    //构造方法(手工生成)


    //get和set方法
    public String getStartPage(){
        return startPage;
    }

    public void setStartPage(String aStartPage){
        this.startPage = aStartPage;
    }

    public String getHandlePage(){
        return handlePage;
    }

    public void setHandlePage(String aHandlePage){
        this.handlePage = aHandlePage;
    }

	public ProcessDefinition getProcessDefinition() {
		return processDefinition;
	}

	public void setProcessDefinition(ProcessDefinition processDefinition) {
		this.processDefinition = processDefinition;
	}

	public String getDetailPage() {
		return detailPage;
	}

	public void setDetailPage(String detailPage) {
		this.detailPage = detailPage;
	}

	public SysProcessType getProcessType() {
		return processType;
	}

	public void setProcessType(SysProcessType processType) {
		this.processType = processType;
	}

	public String getProcessDesc() {
		return processDesc;
	}

	public void setProcessDesc(String processDesc) {
		this.processDesc = processDesc;
	}
}