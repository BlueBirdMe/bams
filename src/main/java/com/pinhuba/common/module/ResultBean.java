package com.pinhuba.common.module;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.pinhuba.common.pages.Pager;

public class ResultBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5356601458854230789L;
	
	//true查询成功，false查询失败
	private boolean success;
	
	//封装给客户看的信息
	private String message;
	
	//查询的结果
	private List resultList;

	private Pager pager;

	public ResultBean(boolean success, List resultList, Pager pager) {
		super();
		this.pager = pager;
		this.resultList = resultList;
		this.success = success;
	}

	public ResultBean(boolean success, List resultList, Pager pager,
			String message) {
		super();
		this.message = message;
		this.pager = pager;
		this.resultList = resultList;
		this.success = success;
	}

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}

	public ResultBean(boolean success, List resultList) {
		super();
		this.success = success;
		this.resultList = resultList;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ResultBean() {
		super();
	}

	public ResultBean(boolean success, String message, ArrayList resultList,Pager pager) {
		super();
		this.success = success;
		this.message = message;
		this.resultList = resultList;
		this.pager = pager;
	}
	
	@SuppressWarnings("unchecked")
	public ResultBean(boolean success, String message, ArrayList resultList) {
		super();
		this.success = success;
		this.message = message;
		this.resultList = resultList;
	}

	public ResultBean(boolean success, String message) {
		super();
		this.success = success;
		this.message = message;
	}

	public List getResultList() {
		return resultList;
	}

	public void setResultList(List resultList) {
		this.resultList = resultList;
	}

}
