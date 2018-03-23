package com.pinhuba.common.module;


public class ProcessEmployeeTemp {
	private int level;
	private String employeeId;
	private String employeeName;

	
	
	public ProcessEmployeeTemp() {
		super();
	}

	
	
	public ProcessEmployeeTemp(int level, String employeeId) {
		super();
		this.level = level;
		this.employeeId = employeeId;
	}

	

	public ProcessEmployeeTemp(int level, String employeeId, String employeeName) {
		super();
		this.level = level;
		this.employeeId = employeeId;
		this.employeeName = employeeName;
	}



	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((employeeId == null) ? 0 : employeeId.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ProcessEmployeeTemp other = (ProcessEmployeeTemp) obj;
		if (employeeId == null) {
			if (other.employeeId != null)
				return false;
		} else if (!employeeId.equals(other.employeeId))
			return false;
		return true;
	}
}
