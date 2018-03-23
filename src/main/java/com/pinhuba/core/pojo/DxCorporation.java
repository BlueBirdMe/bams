package com.pinhuba.core.pojo;

import com.pinhuba.common.annotation.Remark;

/**
 * 数据库表名：dx_corporation
 */
public class DxCorporation extends BaseStringBean implements java.io.Serializable {

    private String description;
    private String flag;
    private String name;
    private String parentid;
    private Integer gradea;
    private Integer gradeb;
    private Integer gradec;
    private Integer graded;
    private Integer total;
    private String depttype;
    private String corpid;

    //默认构造方法
    public DxCorporation(){
        super();
    }

    //构造方法(手工生成)


    //get和set方法
    public String getDescription(){
        return description;
    }

    public void setDescription(String aDescription){
        this.description = aDescription;
    }

    public String getFlag(){
        return flag;
    }

    public void setFlag(String aFlag){
        this.flag = aFlag;
    }

    public String getName(){
        return name;
    }

    public void setName(String aName){
        this.name = aName;
    }

    public String getParentid(){
        return parentid;
    }

    public void setParentid(String aParentid){
        this.parentid = aParentid;
    }

    public Integer getGradea(){
        return gradea;
    }

    public void setGradea(Integer aGradea){
        this.gradea = aGradea;
    }

    public Integer getGradeb(){
        return gradeb;
    }

    public void setGradeb(Integer aGradeb){
        this.gradeb = aGradeb;
    }

    public Integer getGradec(){
        return gradec;
    }

    public void setGradec(Integer aGradec){
        this.gradec = aGradec;
    }

    public Integer getGraded(){
        return graded;
    }

    public void setGraded(Integer aGraded){
        this.graded = aGraded;
    }

    public Integer getTotal(){
        return total;
    }

    public void setTotal(Integer aTotal){
        this.total = aTotal;
    }

    public String getDepttype(){
        return depttype;
    }

    public void setDepttype(String aDepttype){
        this.depttype = aDepttype;
    }

    public String getCorpid(){
        return corpid;
    }

    public void setCorpid(String aCorpid){
        this.corpid = aCorpid;
    }

}