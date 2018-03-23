package com.pinhuba.common.exception;

import com.pinhuba.common.util.UtilWork;

public class DataException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 57310819821993949L;
	
	private String message;
	private Throwable cause;
	
	
	public DataException() {
		super();
	}

	public DataException(String message, Throwable cause) {
		this.message = message;
		this.cause = cause;
	}

	public DataException(String message) {
		this.message = message;
	}

	public DataException(Throwable cause) {
		this.cause = cause;
	}

	public String getErrorMessage(){
		return UtilWork.getNowTime() + " 操作错误："+this.getMessage();
	}
	
	public String getStackMessage(){
		StackTraceElement[] stackTraceElements = this.getStackTrace();
		StringBuffer sb = new StringBuffer();
		sb.append(this.getMessage() + "\n");
		for (StackTraceElement element : stackTraceElements) {
			sb.append(element + "\n\t");
		}
		return sb.toString();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Throwable getCause() {
		return cause;
	}

	public void setCause(Throwable cause) {
		this.cause = cause;
	}
	
	
}
