package com.pinhuba.core.pojo;

/**
 * 数据库表名：SYS_LIBRARY_STANDARD
 */
public class SysLibraryStandard extends BaseBean implements java.io.Serializable {

	/**
	 * 标准代码，不区分companyId
	 */
	private static final long serialVersionUID = -1242810784237421542L;
	private String libraryCode;		//编码
	private String libraryName;		//名称
	private String libraryUpcode;	//上级编码
	private Integer libraryStandCode;	//国家标准代码
	private String libraryDesc;//备注
	private SysLibraryStandard upSysLibrary;

	// 默认构造方法
	public SysLibraryStandard() {
		super();
	}

	// 构造方法(手工生成)
	public SysLibraryStandard(String libraryName) {
		super();
		this.libraryName = libraryName;
	}

	public SysLibraryStandard getUpSysLibrary() {
		return upSysLibrary;
	}

	public void setUpSysLibrary(SysLibraryStandard upSysLibrary) {
		this.upSysLibrary = upSysLibrary;
	}

	// get和set方法
	public String getLibraryCode() {
		return libraryCode;
	}

	public void setLibraryCode(String aLibraryCode) {
		this.libraryCode = aLibraryCode;
	}

	public String getLibraryName() {
		return libraryName;
	}

	public void setLibraryName(String aLibraryName) {
		this.libraryName = aLibraryName;
	}

	public String getLibraryUpcode() {
		return libraryUpcode;
	}

	public void setLibraryUpcode(String aLibraryUpcode) {
		this.libraryUpcode = aLibraryUpcode;
	}

	public Integer getLibraryStandCode() {
		return libraryStandCode;
	}

	public void setLibraryStandCode(Integer libraryStandCode) {
		this.libraryStandCode = libraryStandCode;
	}

	public String getLibraryDesc() {
		return libraryDesc;
	}

	public void setLibraryDesc(String libraryDesc) {
		this.libraryDesc = libraryDesc;
	}
}