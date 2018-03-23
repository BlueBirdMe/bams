package com.pinhuba.core.pojo;

import com.pinhuba.common.annotation.Remark;

/**
 * 数据库表名：hrm_contract
 */
public class HrmContract extends BaseStringBean implements java.io.Serializable {

    @Remark("合同编号|1|1|1|1")
    private String contractCode;
    @Remark("合同名称|1|1|1|1")
    private String contractName;
    
    private Integer contractLimitType;
    
    @Remark("合同状态|2|1|5|1")
    private Integer contractStatus;
  
    private HrmContractType contractType;
    @Remark("开始日期|2|1|4|1")
    private String contractBegindate;
    @Remark("结束日期|2|1|4|2")
    private String contractEnddate;
    @Remark("合同内容|2|2|11|1")
    private String contractContent;
    @Remark("合同附件|2|2|13|2")
    private String contractFile;
    
    private HrmEmployee employee;
    
    private Integer companyId;

    //默认构造方法
    public HrmContract(){
        super();
    }

    //构造方法(手工生成)


    //get和set方法
    public String getContractCode(){
        return contractCode;
    }

    public void setContractCode(String aContractCode){
        this.contractCode = aContractCode;
    }

    public String getContractName(){
        return contractName;
    }

    public void setContractName(String aContractName){
        this.contractName = aContractName;
    }

    public HrmContractType getContractType() {
		return contractType;
	}

	public void setContractType(HrmContractType contractType) {
		this.contractType = contractType;
	}

	public Integer getContractStatus(){
        return contractStatus;
    }

    public void setContractStatus(Integer aContractStatus){
        this.contractStatus = aContractStatus;
    }
 
    public String getContractBegindate(){
        return contractBegindate;
    }

    public void setContractBegindate(String aContractBegindate){
        this.contractBegindate = aContractBegindate;
    }

    public String getContractEnddate(){
        return contractEnddate;
    }

    public void setContractEnddate(String aContractEnddate){
        this.contractEnddate = aContractEnddate;
    }

    public String getContractContent(){
        return contractContent;
    }

    public void setContractContent(String aContractContent){
        this.contractContent = aContractContent;
    }

    public String getContractFile(){
        return contractFile;
    }

    public void setContractFile(String aContractFile){
        this.contractFile = aContractFile;
    }

    public HrmEmployee getEmployee() {
		return employee;
	}

	public void setEmployee(HrmEmployee employee) {
		this.employee = employee;
	}

	public Integer getCompanyId(){
        return companyId;
    }

    public void setCompanyId(Integer aCompanyId){
        this.companyId = aCompanyId;
    }

	public Integer getContractLimitType() {
		return contractLimitType;
	}

	public void setContractLimitType(Integer contractLimitType) {
		this.contractLimitType = contractLimitType;
	}

}