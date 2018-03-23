package com.pinhuba.web.taglib.table;

import com.pinhuba.web.taglib.table.cloumntype.AbscolumnType;
import com.pinhuba.web.taglib.table.cloumntype.TextType;

public class SysGridColumnBean {
	private String dataName;

	private String showName;

	private boolean isShowColumn;//是否显示在列中
	
	private boolean isShowQuerySelsect = true;//是否显示到下拉列表中
	
	private boolean isShowAdvanced = true;// 是否显示到高级查询中,默认为true,不指定高级查询类型，默认为文本框

	private AbscolumnType columnTypeClass; //对应的高级查询类型
	
	private String columnReplace;//进行列替换的js方法
	
	private int columnStrCount;//最大显示字符数 0表示不控制
	
	private String columnStyle;//列显示样式
	
	private boolean columnToObject = true;//查询时是否为对象属性

	public String getColumnReplace() {
		return columnReplace;
	}

	public void setColumnReplace(String columnReplace) {
		this.columnReplace = columnReplace;
	}

	public String getDataName() {
		return dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	public String getShowName() {
		return showName==null?"":showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public SysGridColumnBean(String dataName, String showName) {
		super();
		this.dataName = dataName;
		this.showName = showName;
	}

	public SysGridColumnBean(String dataName, String showName, boolean isShowQuerySelsect) {
		super();
		this.dataName = dataName;
		this.showName = showName;
		this.isShowQuerySelsect = isShowQuerySelsect;
	}

	public SysGridColumnBean() {
		super();
	}

	public boolean isShowAdvanced() {
		return isShowAdvanced;
	}

	public void setShowAdvanced(boolean isShowAdvanced) {
		this.isShowAdvanced = isShowAdvanced;
	}

	public SysGridColumnBean(String dataName, String showName, boolean isShowAdvanced, AbscolumnType columnTypeClass) {
		super();
		this.dataName = dataName;
		this.showName = showName;
		this.isShowAdvanced = isShowAdvanced;
		this.columnTypeClass = columnTypeClass;
	}

	public SysGridColumnBean(String dataName, String showName, boolean isShowQuerySelsect, boolean isShowAdvanced, AbscolumnType columnTypeClass) {
		super();
		this.dataName = dataName;
		this.showName = showName;
		this.isShowQuerySelsect = isShowQuerySelsect;
		this.isShowAdvanced = isShowAdvanced;
		this.columnTypeClass = columnTypeClass;
	}

	public AbscolumnType getColumnTypeClass() {
		return columnTypeClass;
	}

	public void setColumnTypeClass(AbscolumnType columnTypeClass) {
		this.columnTypeClass = columnTypeClass;
	}

	public boolean isShowQuerySelsect() {
		return isShowQuerySelsect;
	}

	public void setShowQuerySelsect(boolean isShowQuerySelsect) {
		this.isShowQuerySelsect = isShowQuerySelsect;
	}

	public SysGridColumnBean(String dataName, String showName, AbscolumnType columnTypeClass, String columnReplace) {
		super();
		this.dataName = dataName;
		this.showName = showName;
		this.columnTypeClass = columnTypeClass;
		this.columnReplace = columnReplace;
	}

	public SysGridColumnBean(String dataName, String showName, boolean isShowQuerySelsect, boolean isShowAdvanced, AbscolumnType columnTypeClass, String columnReplace) {
		super();
		this.dataName = dataName;
		this.showName = showName;
		this.isShowQuerySelsect = isShowQuerySelsect;
		this.isShowAdvanced = isShowAdvanced;
		this.columnTypeClass = columnTypeClass;
		this.columnReplace = columnReplace;
	}

	public SysGridColumnBean(String dataName, String showName, String columnReplace) {
		super();
		this.dataName = dataName;
		this.showName = showName;
		this.columnReplace = columnReplace;
	}

	public int getColumnStrCount() {
		return columnStrCount;
	}

	public void setColumnStrCount(int columnStrCount) {
		this.columnStrCount = columnStrCount;
	}

	public String getColumnStyle() {
		return columnStyle;
	}

	public void setColumnStyle(String columnStyle) {
		this.columnStyle = columnStyle;
	}

	public boolean isColumnToObject() {
		return columnToObject;
	}

	public void setColumnToObject(boolean columnToObject) {
		this.columnToObject = columnToObject;
	}

	public boolean isShowColumn() {
		return isShowColumn;
	}

	public void setShowColumn(boolean isShowColumn) {
		this.isShowColumn = isShowColumn;
	}

	
}
