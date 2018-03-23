package com.pinhuba.common.netdisk.json.bean;

import java.util.Date;
/**
 * File node module
 * @author Administrator
 *
 */
public class FileNode {
	
	/** 节点id */
	private String id;

	/** 节点名称 */
	private String text;

	/** 是否是叶子节点 */
	private boolean leaf;
	
	/** 是否共享 */
	private boolean share;

	/** 文件名 */
	private String fileName;

	/** 文件尺寸 */
	private String fileSize = "";

	/** 文件最后修改时间 */
	private Date lastModifyDate;

	
	public boolean isShare() {
		return share;
	}

	public void setShare(boolean share) {
		this.share = share;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public Date getLastModifyDate() {
		return lastModifyDate;
	}

	public void setLastModifyDate(Date lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
