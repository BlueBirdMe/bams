package com.pinhuba.common.util.imgProcess;

public class ProcessImg {
	private int row;
	private int col;
	private long methodInfoId;
	private String showImgPath;
	private String invalidImgPath;
	private String showValue;
	private String arrow;
	private String minImgPath;
	private String minInvaildImgPath;
	private String customeEvent;
	
	
	public ProcessImg() {
		super();
	}
	
	
	public ProcessImg(int row, int col, long methodInfoId, String showImgPath, String invalidImgPath, String showValue) {
		super();
		this.row = row;
		this.col = col;
		this.methodInfoId = methodInfoId;
		this.showImgPath = showImgPath;
		this.invalidImgPath = invalidImgPath;
		this.showValue = showValue;
	}

	

	public String getCustomeEvent() {
		return customeEvent;
	}


	public void setCustomeEvent(String customeEvent) {
		this.customeEvent = customeEvent;
	}


	public ProcessImg(int row, int col, String arrow) {
		super();
		this.row = row;
		this.col = col;
		this.arrow = arrow;
	}

	

	public ProcessImg(long methodInfoId, String showValue, String minImgPath,String minInvaildImgPath) {
		super();
		this.methodInfoId = methodInfoId;
		this.showValue = showValue;
		this.minImgPath = minImgPath;
		this.minInvaildImgPath = minInvaildImgPath;
	}


	public String getMinInvaildImgPath() {
		return minInvaildImgPath;
	}


	public void setMinInvaildImgPath(String minInvaildImgPath) {
		this.minInvaildImgPath = minInvaildImgPath;
	}


	public String getMinImgPath() {
		return minImgPath;
	}


	public void setMinImgPath(String minImgPath) {
		this.minImgPath = minImgPath;
	}


	public String getArrow() {
		return arrow;
	}


	public void setArrow(String arrow) {
		this.arrow = arrow;
	}


	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public long getMethodInfoId() {
		return methodInfoId;
	}

	public void setMethodInfoId(long methodInfoId) {
		this.methodInfoId = methodInfoId;
	}

	public String getShowImgPath() {
		return showImgPath;
	}

	public void setShowImgPath(String showImgPath) {
		this.showImgPath = showImgPath;
	}

	public String getInvalidImgPath() {
		return invalidImgPath;
	}

	public void setInvalidImgPath(String invalidImgPath) {
		this.invalidImgPath = invalidImgPath;
	}

	public String getShowValue() {
		return showValue;
	}

	public void setShowValue(String showValue) {
		this.showValue = showValue;
	}

}
