package com.pinhuba.core.pojo;

import com.pinhuba.common.annotation.Remark;

/**
 * 数据库表名：dx_educate_temp
 */
public class DxEducateTemp extends BaseStringBean implements java.io.Serializable {

    private String name;
    private String experience;
    private String certificate;
    private String specialty;
    private String date;
    private String year;
    private String pastspecialty;
    private String pastdate;
    private String pstyear;
    private String empid;
    private String flag;

    //默认构造方法
    public DxEducateTemp(){
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

    public String getExperience(){
        return experience;
    }

    public void setExperience(String aExperience){
        this.experience = aExperience;
    }

    public String getCertificate(){
        return certificate;
    }

    public void setCertificate(String aCertificate){
        this.certificate = aCertificate;
    }

    public String getSpecialty(){
        return specialty;
    }

    public void setSpecialty(String aSpecialty){
        this.specialty = aSpecialty;
    }

    public String getDate(){
        return date;
    }

    public void setDate(String aDate){
        this.date = aDate;
    }

    public String getYear(){
        return year;
    }

    public void setYear(String aYear){
        this.year = aYear;
    }

    public String getPastspecialty(){
        return pastspecialty;
    }

    public void setPastspecialty(String aPastspecialty){
        this.pastspecialty = aPastspecialty;
    }

    public String getPastdate(){
        return pastdate;
    }

    public void setPastdate(String aPastdate){
        this.pastdate = aPastdate;
    }

    public String getPstyear(){
        return pstyear;
    }

    public void setPstyear(String aPstyear){
        this.pstyear = aPstyear;
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