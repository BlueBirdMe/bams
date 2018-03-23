package com.pinhuba.common.util.convertTool;

import java.util.HashSet;
import java.util.Set;

public class FloatToNumBean {
	private String tableName;//表名
	private String tabelPkName;//主键列名
	private Set<String> colsName = new HashSet<String>();//要修改的列名

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTabelPkName() {
		return tabelPkName;
	}

	public void setTabelPkName(String tabelPkName) {
		this.tabelPkName = tabelPkName;
	}

	public Set<String> getColsName() {
		return colsName;
	}

	public void setColsName(Set<String> colsName) {
		this.colsName = colsName;
	}

	

	public FloatToNumBean(String tableName, String tabelPkName, Set<String> colsName) {
		super();
		this.tableName = tableName;
		this.tabelPkName = tabelPkName;
		this.colsName = colsName;
	}

	public FloatToNumBean() {
		super();
	}
	
	public FloatToNumBean(String tableName,String tabelPkName,String colName) {
		super();
		this.tableName = tableName;
		this.tabelPkName = tabelPkName;
		this.colsName.add(colName);
	}
}
