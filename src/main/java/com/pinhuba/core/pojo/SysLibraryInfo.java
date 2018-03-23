package com.pinhuba.core.pojo;

/**
 * 数据库表名：SYS_LIBRARY_INFO
 */
public class SysLibraryInfo extends BaseBean implements java.io.Serializable {

	/**
	 * 数据字典表 
	 */
	private static final long serialVersionUID = -1242810784237421542L;
	private String libraryInfoCode;		//业务编码
	private String libraryInfoName;		//业务名称
	private String libraryInfoEngname;	//英文名称
	private String libraryInfoUpcode;	//上级编码
	
	private SysLibraryInfo upSysLibraryInfo;
	
	private Integer libraryInfoIsedit;	//是否可修改
	private Integer libraryInfoIsvalid;	//是否有效
	private String libraryInfoDesc;		//备注
	

	// 默认构造方法
	public SysLibraryInfo() {
		super();
	}

	// 构造方法(手工生成)
	public SysLibraryInfo(String libraryInfoName) {
		super();
		this.libraryInfoName = libraryInfoName;
	}

	public SysLibraryInfo(Integer primaryKey) {
		super.setPrimaryKey(primaryKey);
	}

	public SysLibraryInfo getUpSysLibraryInfo() {
		return upSysLibraryInfo;
	}

	public void setUpSysLibraryInfo(SysLibraryInfo upSysLibraryInfo) {
		this.upSysLibraryInfo = upSysLibraryInfo;
	}

	// get和set方法
	public String getLibraryInfoCode() {
		return libraryInfoCode;
	}

	public void setLibraryInfoCode(String aLibraryInfoCode) {
		this.libraryInfoCode = aLibraryInfoCode;
	}

	public String getLibraryInfoName() {
		return libraryInfoName;
	}

	public void setLibraryInfoName(String aLibraryInfoName) {
		this.libraryInfoName = aLibraryInfoName;
	}

	public String getLibraryInfoEngname() {
		return libraryInfoEngname;
	}

	public void setLibraryInfoEngname(String aLibraryInfoEngname) {
		this.libraryInfoEngname = aLibraryInfoEngname;
	}

	public String getLibraryInfoUpcode() {
		return libraryInfoUpcode;
	}

	public void setLibraryInfoUpcode(String aLibraryInfoUpcode) {
		this.libraryInfoUpcode = aLibraryInfoUpcode;
	}

	public Integer getLibraryInfoIsedit() {
		return libraryInfoIsedit;
	}

	public void setLibraryInfoIsedit(Integer aLibraryInfoIsedit) {
		this.libraryInfoIsedit = aLibraryInfoIsedit;
	}

	public String getLibraryInfoDesc() {
		return libraryInfoDesc;
	}

	public void setLibraryInfoDesc(String aLibraryInfoDesc) {
		this.libraryInfoDesc = aLibraryInfoDesc;
	}

	public Integer getLibraryInfoIsvalid() {
		return libraryInfoIsvalid;
	}

	public void setLibraryInfoIsvalid(Integer aLibraryInfoIsvalid) {
		this.libraryInfoIsvalid = aLibraryInfoIsvalid;
	}

}