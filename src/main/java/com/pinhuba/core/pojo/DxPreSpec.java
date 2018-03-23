package com.pinhuba.core.pojo;

import com.pinhuba.common.annotation.Remark;

/**
 * 数据库表名：dx_pre_spec
 */
public class DxPreSpec extends BaseStringBean implements java.io.Serializable {

    private String name;
    private String preSpec;
    private String empid;

    //默认构造方法
    public DxPreSpec(){
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

    public String getPreSpec(){
        return preSpec;
    }

    public void setPreSpec(String aPreSpec){
        this.preSpec = aPreSpec;
    }

    public String getEmpid(){
        return empid;
    }

    public void setEmpid(String aEmpid){
        this.empid = aEmpid;
    }

}