package com.pinhuba.core.pojo;

import com.pinhuba.common.annotation.Remark;

/**
 * 数据库表名：dx_userinfo
 */
public class DxUserinfo extends BaseStringBean implements java.io.Serializable {

    private String name;
    private String birthday;
    private String minority;
    private String workdate;
    private String corpdate;
    private String technicalpost;
    private String fulltime;
    private String graduate;
    private String major;
    private String education;
    private String parttime;
    private String corpid;
    private String parttimemajor;
    private String deptid;
    private String level;
    private String empid;
    private String job;
    private String mobile;
    private String corpname;
    private String deptname;

    //默认构造方法
    public DxUserinfo(){
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

    public String getBirthday(){
        return birthday;
    }

    public void setBirthday(String aBirthday){
        this.birthday = aBirthday;
    }

    public String getMinority(){
        return minority;
    }

    public void setMinority(String aMinority){
        this.minority = aMinority;
    }

    public String getWorkdate(){
        return workdate;
    }

    public void setWorkdate(String aWorkdate){
        this.workdate = aWorkdate;
    }

    public String getCorpdate(){
        return corpdate;
    }

    public void setCorpdate(String aCorpdate){
        this.corpdate = aCorpdate;
    }

    public String getTechnicalpost(){
        return technicalpost;
    }

    public void setTechnicalpost(String aTechnicalpost){
        this.technicalpost = aTechnicalpost;
    }

    public String getFulltime(){
        return fulltime;
    }

    public void setFulltime(String aFulltime){
        this.fulltime = aFulltime;
    }

    public String getGraduate(){
        return graduate;
    }

    public void setGraduate(String aGraduate){
        this.graduate = aGraduate;
    }

    public String getMajor(){
        return major;
    }

    public void setMajor(String aMajor){
        this.major = aMajor;
    }

    public String getEducation(){
        return education;
    }

    public void setEducation(String aEducation){
        this.education = aEducation;
    }

    public String getParttime(){
        return parttime;
    }

    public void setParttime(String aParttime){
        this.parttime = aParttime;
    }

    public String getCorpid(){
        return corpid;
    }

    public void setCorpid(String aCorpid){
        this.corpid = aCorpid;
    }

    public String getParttimemajor(){
        return parttimemajor;
    }

    public void setParttimemajor(String aParttimemajor){
        this.parttimemajor = aParttimemajor;
    }

    public String getDeptid(){
        return deptid;
    }

    public void setDeptid(String aDeptid){
        this.deptid = aDeptid;
    }

    public String getLevel(){
        return level;
    }

    public void setLevel(String aLevel){
        this.level = aLevel;
    }

    public String getEmpid(){
        return empid;
    }

    public void setEmpid(String aEmpid){
        this.empid = aEmpid;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCorpname() {
        return corpname;
    }

    public void setCorpname(String corpname) {
        this.corpname = corpname;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }
}