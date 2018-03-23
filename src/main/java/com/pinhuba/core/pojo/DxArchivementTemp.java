package com.pinhuba.core.pojo;

import com.pinhuba.common.annotation.Remark;

/**
 * 数据库表名：dx_archivement_temp
 */
public class DxArchivementTemp extends BaseStringBean implements java.io.Serializable {

    private String name;
    private String ability;
    private String type;
    private String archieve;
    private String performance;
    private String patent;
    private String compet;
    private String result;
    private String empid;
    private String flag;

    //默认构造方法
    public DxArchivementTemp(){
        super();
    }

    //构造方法(手工生成)


    //get和set方法
    public String getName(){
        return name;
    }

    public void setName(String aName){
        this.name = aName;
    }

    public String getAbility(){
        return ability;
    }

    public void setAbility(String aAbility){
        this.ability = aAbility;
    }

    public String getType(){
        return type;
    }

    public void setType(String aType){
        this.type = aType;
    }

    public String getArchieve(){
        return archieve;
    }

    public void setArchieve(String aArchieve){
        this.archieve = aArchieve;
    }

    public String getPerformance(){
        return performance;
    }

    public void setPerformance(String aPerformance){
        this.performance = aPerformance;
    }

    public String getPatent(){
        return patent;
    }

    public void setPatent(String aPatent){
        this.patent = aPatent;
    }

    public String getCompet(){
        return compet;
    }

    public void setCompet(String aCompet){
        this.compet = aCompet;
    }

    public String getResult(){
        return result;
    }

    public void setResult(String aResult){
        this.result = aResult;
    }

    public String getEmpid(){
        return empid;
    }

    public void setEmpid(String aEmpid){
        this.empid = aEmpid;
    }

    public String getFlag(){
        return flag;
    }

    public void setFlag(String aFlag){
        this.flag = aFlag;
    }

}