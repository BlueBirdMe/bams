package com.pinhuba.common.util.os.command;

import java.io.Serializable;

public final class ResultSet implements Serializable {

	private static final long serialVersionUID = 1L;

	private int code = Integer.MAX_VALUE;

	private String outputString = null;

	private String errorString = null;

	private Throwable ex;

	public ResultSet() {
		//
	}

	public ResultSet(int code, String outputString, Throwable ex,
			String errorString) {
		this.code = code;
		if (outputString != null && outputString.trim().length() == 0) {
			this.outputString = null;
		} else {
			this.outputString = outputString;
		}
		this.outputString = outputString;
		this.ex = ex;
		if (errorString == null) {
			this.errorString = null;
		} else if (errorString != null && errorString.trim().length() == 0) {
			this.errorString = null;
		} else {
			this.errorString = errorString;
		}
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setOutputString(String outputString) {
		this.outputString = outputString;
	}

	public int getCode() {
		return this.code;
	}

	public String getOutputString() {
		return this.outputString;
	}

	public void setEx(Throwable ex) {
		this.ex = ex;
	}

	public Throwable getEx() {
		return this.ex;
	}

	public void setErrorString(String errorString) {
		this.errorString = errorString;
	}

	public String getErrorString() {
		return this.errorString;
	}
}
