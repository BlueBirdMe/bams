package com.pinhuba.core.pojo;

import java.io.Serializable;
import com.pinhuba.common.util.UtilWork;

/**
 * 主键为String类型基类
 * @author JC
 * @description
 */
public abstract class BaseStringBean implements Serializable {

	private String primaryKey;

	private String recordId;//记录人
	private String recordDate;//记录时间
	private String lastmodiId;//最近修改人
	private String lastmodiDate;//最近修改时间
	
	private String orderPriority;

	public BaseStringBean() {
		super();
	}

	public String getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
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

	public void initSave(String empid) {
		setRecordId(empid);
		setRecordDate(UtilWork.getNowTime());
		setLastmodiId(empid);
		setLastmodiDate(UtilWork.getNowTime());
	}

	public void initUpdate(String empid) {
		setLastmodiId(empid);
		setLastmodiDate(UtilWork.getNowTime());
	}
	
	public String getOrderPriority() {
		return orderPriority;
	}

	public void setOrderPriority(String orderPriority) {
		this.orderPriority = orderPriority;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((primaryKey == null) ? 0 : primaryKey.hashCode());
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
		final BaseStringBean other = (BaseStringBean) obj;
		if (primaryKey == null) {
			if (other.primaryKey != null)
				return false;
		} else if (!primaryKey.equals(other.primaryKey))
			return false;
		return true;
	}

}
