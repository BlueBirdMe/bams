package com.pinhuba.core.pojo;

/**
 * 数据库表名：SYS_ATTACHMENT_INFO
 */
public class SysAttachmentInfo extends BaseBean implements java.io.Serializable {

	/**
	 * 附件信息表
	 */
	private static final long serialVersionUID = -8103454592266626403L;
	private String attachmentFilename; // 存放名称
	private String attachmentName; // 文件名称
	private String attachmentRename; //上传后的文件名称
	private String attachmentDescription; // 附件描述
	private String attachmentRemark; // 备注
	private String recordId;
	private String recordDate;
	private String lastmodiId;
	private String lastmodiDate;
	private Integer companyId;

	// 默认构造方法
	public SysAttachmentInfo() {
		super();
	}

	// get和set方法
	public String getAttachmentFilename() {
		return attachmentFilename;
	}

	public void setAttachmentFilename(String aAttachmentFilename) {
		this.attachmentFilename = aAttachmentFilename;
	}

	public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(String aAttachmentName) {
		this.attachmentName = aAttachmentName;
	}

	public String getAttachmentDescription() {
		return attachmentDescription;
	}

	public void setAttachmentDescription(String aAttachmentDescription) {
		this.attachmentDescription = aAttachmentDescription;
	}

	public String getAttachmentRemark() {
		return attachmentRemark;
	}

	public void setAttachmentRemark(String aAttachmentRemark) {
		this.attachmentRemark = aAttachmentRemark;
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

	public String getAttachmentRename() {
		return attachmentRename;
	}

	public void setAttachmentRename(String attachmentRename) {
		this.attachmentRename = attachmentRename;
	}
}