package com.pinhuba.common.code.bean;

public class ColumnProperty{

	public static final int PROPERTY_COUNT = 5;
	
	private String name;//字段中文含义
	private int isShowSimple;//是否显示到右上角下拉列表简单查询 1，显示 2，不显示
	private int isShowAdvanced;//是否显示到高级查询 1，显示 2，不显示
	private int componentType;//对应的组件类型
	private int isRequired;//是否必填项
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getIsShowSimple() {
		return isShowSimple;
	}
	public void setIsShowSimple(int isShowSimple) {
		this.isShowSimple = isShowSimple;
	}
	public int getIsShowAdvanced() {
		return isShowAdvanced;
	}
	public void setIsShowAdvanced(int isShowAdvanced) {
		this.isShowAdvanced = isShowAdvanced;
	}
	public int getComponentType() {
		return componentType;
	}
	public void setComponentType(int componentType) {
		this.componentType = componentType;
	}
	public int getIsRequired() {
		return isRequired;
	}
	public void setIsRequired(int isRequired) {
		this.isRequired = isRequired;
	}
	
}
