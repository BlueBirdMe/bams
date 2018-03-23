package com.pinhuba.core.pojo;

import java.util.List;

import com.pinhuba.common.annotation.Remark;

/**
 * 数据库表名：sys_process_type
 */
public class SysProcessType extends BaseStringBean implements java.io.Serializable {

    @Remark("分类名称|1|2|1|1")
    private String typeName;
    @Remark("分类描述|2|2|10|2")
    private String typeDesc;
    private int priority;//排序
    
    List<SysProcessConfig> configList;

    //默认构造方法
    public SysProcessType(){
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

    public String getTypeDesc(){
        return typeDesc;
    }

    public void setTypeDesc(String aTypeDesc){
        this.typeDesc = aTypeDesc;
    }

	public List<SysProcessConfig> getConfigList() {
		return configList;
	}

	public void setConfigList(List<SysProcessConfig> configList) {
		this.configList = configList;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

}