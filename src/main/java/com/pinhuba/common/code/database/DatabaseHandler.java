package com.pinhuba.common.code.database;

import java.sql.Connection;
import java.util.List;

public interface DatabaseHandler {
	
	public String getName();

	public Connection getConn() throws Exception;

	public List<String> getRemarks(Connection conn, String tableName) throws Exception;

	public String columnTypeToFieldType(int columnType, int columnSize);
}
