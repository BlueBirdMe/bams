package com.pinhuba.core.pojo;

/**
 * 数据库表名：OA_ALBUM
 */
public class OaAlbum extends BaseBean implements java.io.Serializable {

	/**
	 * （相册数据表）
	 */
	private static final long serialVersionUID = 6017883269332603026L;
	private String albumName;            //相册名称
	private String albumCreateEmployee;	 //创建人ID

	private HrmEmployee createEmployee;	 //人员表

	private String albumTime;			 //创建时间
	private Integer albumType;			 //相册类型

	private SysLibraryInfo libraryType;	 //类型表

	private Integer albumPhotoId;		 //相片ID

	private OaPhoto oaPhoto;			 //相片表

	private Integer albumPhotoCount;	 //相片数量
	private String albumDesc;			 //相册描述
	private String albumEmps;			 //浏览人员范围
	private String albumDeps;			 //浏览部门范围
	private String recordId;			 //修改人ID
	private String recordDate;			 //修改人时间
	private String lastmodiId;			 //最后修改人ID
	private String lastmodiDate;		 //最后修改时间
	private Integer companyId;			 //公司ID

	public HrmEmployee getCreateEmployee() {
		return createEmployee;
	}

	public void setCreateEmployee(HrmEmployee createEmployee) {
		this.createEmployee = createEmployee;
	}

	public SysLibraryInfo getLibraryType() {
		return libraryType;
	}

	public void setLibraryType(SysLibraryInfo libraryType) {
		this.libraryType = libraryType;
	}

	public OaPhoto getOaPhoto() {
		return oaPhoto;
	}

	public void setOaPhoto(OaPhoto oaPhoto) {
		this.oaPhoto = oaPhoto;
	}

	// 默认构造方法
	public OaAlbum() {
		super();
	}

	// get和set方法
	public String getAlbumName() {
		return albumName;
	}

	public void setAlbumName(String aAlbumName) {
		this.albumName = aAlbumName;
	}

	public String getAlbumCreateEmployee() {
		return albumCreateEmployee;
	}

	public void setAlbumCreateEmployee(String aAlbumCreateEmployee) {
		this.albumCreateEmployee = aAlbumCreateEmployee;
	}

	public String getAlbumTime() {
		return albumTime;
	}

	public void setAlbumTime(String aAlbumTime) {
		this.albumTime = aAlbumTime;
	}

	public Integer getAlbumType() {
		return albumType;
	}

	public void setAlbumType(Integer aAlbumType) {
		this.albumType = aAlbumType;
	}

	public Integer getAlbumPhotoId() {
		return albumPhotoId;
	}

	public void setAlbumPhotoId(Integer aAlbumPhotoId) {
		this.albumPhotoId = aAlbumPhotoId;
	}

	public Integer getAlbumPhotoCount() {
		return albumPhotoCount;
	}

	public void setAlbumPhotoCount(Integer aAlbumPhotoCount) {
		this.albumPhotoCount = aAlbumPhotoCount;
	}

	public String getAlbumDesc() {
		return albumDesc;
	}

	public void setAlbumDesc(String aAlbumDesc) {
		this.albumDesc = aAlbumDesc;
	}

	public String getAlbumEmps() {
		return albumEmps;
	}

	public void setAlbumEmps(String aAlbumEmps) {
		this.albumEmps = aAlbumEmps;
	}

	public String getAlbumDeps() {
		return albumDeps;
	}

	public void setAlbumDeps(String aAlbumDeps) {
		this.albumDeps = aAlbumDeps;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String aRecordId) {
		this.recordId = aRecordId;
	}

	public String getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(String aRecordDate) {
		this.recordDate = aRecordDate;
	}

	public String getLastmodiId() {
		return lastmodiId;
	}

	public void setLastmodiId(String aLastmodiId) {
		this.lastmodiId = aLastmodiId;
	}

	public String getLastmodiDate() {
		return lastmodiDate;
	}

	public void setLastmodiDate(String aLastmodiDate) {
		this.lastmodiDate = aLastmodiDate;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer aCompanyId) {
		this.companyId = aCompanyId;
	}

}