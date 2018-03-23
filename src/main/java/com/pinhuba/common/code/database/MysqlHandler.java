package com.pinhuba.common.code.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import com.pinhuba.common.code.bean.DbConfig;

public class MysqlHandler implements DatabaseHandler {

	@Override
	public Connection getConn() throws Exception {
		DbConfig c = DbConfig.getInstance();
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection(c.getUrl(), c.getUser(), c.getPassword());
		return conn;
	}

	@Override
	public List<String> getRemarks(Connection conn, String tableName) throws Exception {
		List<String> list = new ArrayList<String>();
		DatabaseMetaData dbmd = conn.getMetaData();
		ResultSet rs = dbmd.getColumns(null, "%", tableName, "%");
		while (rs.next()) {
			list.add(rs.getString("REMARKS"));
		}
		return list;
	}

	@Override
	public String columnTypeToFieldType(int columnType, int columnSize) {
		if (columnType == Types.INTEGER) {
			return "Integer";
		} else if (columnType == Types.DOUBLE || columnType == Types.FLOAT) {
			return "Double";
		} else {
			return "String";
		}
	}

	@Override
	public String getName() {
		return "mysql";
	}

}
