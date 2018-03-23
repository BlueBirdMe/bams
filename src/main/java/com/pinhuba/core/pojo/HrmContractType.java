package com.pinhuba.core.pojo;

import com.pinhuba.common.annotation.Remark;

/**
 * 数据库表名：hrm_contract_type
 */
public class HrmContractType extends BaseStringBean implements java.io.Serializable {

    @Remark("类别名称|1|1|1|1")
    private String typeName;
    @Remark("合同模板|2|2|13|2")
    private String typeFile;
    @Remark("类别描述|2|2|10|2")
    private String typeDesc;
    private Integer companyId;

    //默认构造方法
    public HrmContractType(){
        super();
    }

    //构造方法(手工生成)


    //get和set方法
    public String getTypeName(){
        return typeName;
    }

    public void setTypeName(String aTypeName){
        this.typeName = aTypeName;
    }

    public String getTypeFile(){
        return typeFile;
    }

    public void setTypeFile(String aTypeFile){
        this.typeFile = aTypeFile;
    }

    public String getTypeDesc(){
        return typeDesc;
    }

    public void setTypeDesc(String aTypeDesc){
        this.typeDesc = aTypeDesc;
    }

    public Integer getCompanyId(){
        return companyId;
    }

    public void setCompanyId(Integer aCompanyId){
        this.companyId = aCompanyId;
    }

}