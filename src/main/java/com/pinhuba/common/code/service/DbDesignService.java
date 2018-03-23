package com.pinhuba.common.code.service;

import java.util.List;
import com.pinhuba.common.code.bean.DbDatabase;
import com.pinhuba.common.code.bean.DbField;
import com.pinhuba.common.code.bean.DbTable;

public interface DbDesignService {

	public DbDatabase getDatabase() throws Exception;
	
	public List<DbTable> listTables() throws Exception;

	public void createTable(DbTable table) throws Exception;
	
	public void updateTable(String oldName, DbTable table) throws Exception;

	public void deleteTable(String name) throws Exception;
	
	public List<DbField> listField(String tableName) throws Exception;

	public void saveField(List<DbField> fields, String tableName) throws Exception;

	public void deleteField(String tableName, String fieldName) throws Exception;

	public DbField getField(String tableName, String fieldName) throws Exception;

	public void updateField(String tableName, DbField field) throws Exception;
}
