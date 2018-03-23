package com.pinhuba.core.pojo;

/**
 * 数据库表名：OA_PHOTO
 */
public class OaPhoto extends BaseBean implements java.io.Serializable {

	/**
	 * 公司相片表
	 */
	private static final long serialVersionUID = -6049063455957517844L;
	private String photoName;		//相片名称
	private String photoTime;		//发布时间
	private String photoDesc;		//相片描述
	private Integer albumId;		//相册ID
	
	private OaAlbum album;			//相册表
	
	private Integer imageId;		//图片ID
	private String recordId;		//修改人ID
	private String recordDate;		//修改时间
	private String lastmodiId;		//最后修改人ID
	private String lastmodiDate;	//最后修改人时间
	private Integer companyId;		//公司ID
	private String isAlubmFace;		//封面标示

	public String getPhotoTime() {
		return photoTime;
	}

	public OaAlbum getAlbum() {
		return album;
	}

	public void setAlbum(OaAlbum album) {
		this.album = album;
	}

	public void setPhotoTime(String photoTime) {
		this.photoTime = photoTime;
	}

	public String getPhotoDesc() {
		return photoDesc;
	}

	public void setPhotoDesc(String photoDesc) {
		this.photoDesc = photoDesc;
	}

	public String getIsAlubmFace() {
		return isAlubmFace;
	}

	public void setIsAlubmFace(String isAlubmFace) {
		this.isAlubmFace = isAlubmFace;
	}

	// 默认构造方法
	public OaPhoto() {
		super();
	}

	// get和set方法
	public String getPhotoName() {
		return photoName;
	}

	public void setPhotoName(String aPhotoName) {
		this.photoName = aPhotoName;
	}

	public Integer getAlbumId() {
		return albumId;
	}

	public void setAlbumId(Integer aAlbumId) {
		this.albumId = aAlbumId;
	}

	public Integer getImageId() {
		return imageId;
	}

	public void setImageId(Integer aImageId) {
		this.imageId = aImageId;
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