package com.pinhuba.common.code.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import com.pinhuba.common.code.bean.DbConfig;

public class OracleHandler implements DatabaseHandler {

	// oracle字段类型为NUMBER 并且长度为11的， 处理为java的Integer类型
	private static final int ORACLE_INTEGER_LENGTH = 11;
	private String schemaPattern = null;

	@Override
	public Connection getConn() throws Exception {
		DbConfig c = DbConfig.getInstance();
		Properties props = new Properties();
		props.put("user", c.getUser());
		props.put("password", c.getPassword());
		props.put("remarksReporting", "true");
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection conn = DriverManager.getConnection(c.getUrl(), props);
		schemaPattern = c.getUser().toUpperCase();// schemaPattern 大写用户名
		return conn;
	}

	@Override
	public List<String> getRemarks(Connection conn, String tableName) throws Exception {
		List<String> list = new ArrayList<String>();
		DatabaseMetaData dbmd = conn.getMetaData();
		ResultSet rs = dbmd.getColumns(conn.getCatalog(), schemaPattern, tableName.toUpperCase(), "%");
		while (rs.next()) {
			list.add(rs.getString("REMARKS"));
		}
		return list;
	}

	@Override
	public String columnTypeToFieldType(int columnType, int columnSize) {
		if (columnType == Types.NUMERIC) {

			if (columnSize == ORACLE_INTEGER_LENGTH)
				return "Integer";
			else
				return "Double";

		} else {
			return "String";
		}
	}

	@Override
	public String getName() {
		return "oracle";
	}
}
