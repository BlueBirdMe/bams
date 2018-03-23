package com.pinhuba.common.code.exception;

public class ColumnRemarkException extends Exception {

	private static final long serialVersionUID = 57310819821993949L;
	
	private String message;
	private Throwable cause;
	
	
	public ColumnRemarkException() {
		super();
	}

	public ColumnRemarkException(String message, Throwable cause) {
		this.message = message;
		this.cause = cause;
	}

	public ColumnRemarkException(String message) {
		this.message = message;
	}

	public ColumnRemarkException(Throwable cause) {
		this.cause = cause;
	}

	public String getErrorMessage(){
		return this.getMessage();
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
