package com.pinhuba.common.code.bean;

public class DbTable {

	private String name;
	private int rows;
	private String comment;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getComment() {
		if(comment == null){
			comment = "";
		}
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}