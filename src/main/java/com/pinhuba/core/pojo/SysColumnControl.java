package com.pinhuba.core.pojo;

/**
 * 数据库表名：Sys_Column_Control 控制列名和显示
 * 
 */
public class SysColumnControl extends BaseBean implements java.io.Serializable {

	private static final long serialVersionUID = -5753424412755496727L;
	private String columnCode; 		//列编码
	private String columnName;   	//列名称
	private String tableName;		//表名称
	private int isShow; // 是否显示在列中1，显示 2，不显示
	private int isshowSimple;// 是否显示到下拉列表 1，显示 2，不显示
	private int isshowAdvanced;// 是否显示到高级查询 1，显示 2，不显示
	private int columnStrcount;// 最多显示字符数 0代表不做限制
	private int priority;//排序

	public SysColumnControl(String columnCode, String columnName,int isShow, int isshowSimple, int isshowAdvanced, int columnStrcount) {
		super();
		this.columnCode = columnCode;
		this.columnName = columnName;
		this.isShow = isShow;
		this.isshowSimple = isshowSimple;
		this.isshowAdvanced = isshowAdvanced;
		this.columnStrcount = columnStrcount;
	}

	// 默认构造方法
	public SysColumnControl() {
		super();
	}

	public String getColumnCode() {
		return columnCode;
	}

	public void setColumnCode(String columnCode) {
		this.columnCode = columnCode;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public int getIsShow() {
		return isShow;
	}

	public void setIsShow(int isShow) {
		this.isShow = isShow;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public int getIsshowSimple() {
		return isshowSimple;
	}

	public void setIsshowSimple(int isshowSimple) {
		this.isshowSimple = isshowSimple;
	}

	public int getIsshowAdvanced() {
		return isshowAdvanced;
	}

	public void setIsshowAdvanced(int isshowAdvanced) {
		this.isshowAdvanced = isshowAdvanced;
	}

	public int getColumnStrcount() {
		return columnStrcount;
	}

	public void setColumnStrcount(int columnStrcount) {
		this.columnStrcount = columnStrcount;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
}