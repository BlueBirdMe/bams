package com.pinhuba.common.module;

public class CommonBean {

	private long id;
	private String name;
	private String en_name;//英文名
	
	public CommonBean() {
		super();
	}

	public CommonBean(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public CommonBean(long id, String name,String en_name) {
		super();
		this.id = id;
		this.name = name;
		this.en_name=en_name;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEn_name() {
		return en_name;
	}

	public void setEn_name(String en_name) {
		this.en_name = en_name;
	}
	
}
