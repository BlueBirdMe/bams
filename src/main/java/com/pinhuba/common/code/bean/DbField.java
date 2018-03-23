package com.pinhuba.common.code.bean;

import com.pinhuba.common.code.util.Util;

public class DbField {

	private String name;
	private String type;
	private String size;
	private String defaultValue;
	private String remark;
	private Boolean showQuery;
	private Boolean showAdvanced;
	private Integer componentType;
	private Boolean must;
	private String remarks;

	public DbField() {
	}

	public DbField(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * 获得字段类型html列表
	 * @return
	 */
	public String getFieldTypesAsHtml() {
		return Util.getFieldTypesAsHtml(getType());
	}

	public String getSize() {
		return this.size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getDefaultValue() {
		if(defaultValue == null)
			defaultValue = "";
		return defaultValue;
	}

	public String getRemark() {
		if(remark == null)
			remark = "";
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Boolean getShowQuery() {
		return showQuery;
	}

	public void setShowQuery(Boolean showQuery) {
		this.showQuery = showQuery;
	}

	public Boolean getShowAdvanced() {
		return showAdvanced;
	}

	public void setShowAdvanced(Boolean showAdvanced) {
		this.showAdvanced = showAdvanced;
	}

	public Boolean getMust() {
		return must;
	}

	public void setMust(Boolean must) {
		this.must = must;
	}

	public Integer getComponentType() {
		return componentType;
	}

	public void setComponentType(Integer componentType) {
		this.componentType = componentType;
	}
	
	/**
	 * 获得组件名称
	 * @return
	 */
	public String getComponentTypeName() {
		return Util.getComponentTypeName(getComponentType());
	}
	
	/**
	 * 获得组件html列表
	 * @return
	 */
	public String getComponentTypesAsHtml() {
		return Util.getComponentTypesAsHtml(getComponentType());
	}
	
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public DbField setRemarks(DbField field, String remarks) {
		if(remarks != null && remarks.length() > 0){
    		String[] remarkArray = remarks.split("\\|");
    		if(remarkArray.length == Util.COUNT){
        		field.setRemark(remarkArray[0]);
        		field.setShowQuery(Util.tmpChange(remarkArray[1]));
        		field.setShowAdvanced(Util.tmpChange(remarkArray[2]));
        		field.setComponentType(Integer.valueOf(remarkArray[3]));
        		field.setMust(Util.tmpChange(remarkArray[4]));
    		}
		}
		return field;
	}


	
}