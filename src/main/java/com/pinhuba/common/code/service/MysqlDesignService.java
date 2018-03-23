package com.pinhuba.common.code.service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pinhuba.common.code.bean.DbDatabase;
import com.pinhuba.common.code.bean.DbField;
import com.pinhuba.common.code.bean.DbTable;
import com.pinhuba.common.code.database.MysqlHandler;

public class MysqlDesignService implements DbDesignService {

	private final static Logger logger = LoggerFactory.getLogger(MysqlDesignService.class);

	private Connection getConn() throws Exception {
		return new MysqlHandler().getConn();
	}

	@Override
	public DbDatabase getDatabase() throws Exception {
		Connection conn = getConn();
		DatabaseMetaData dbmd = conn.getMetaData();
		DbDatabase db = new DbDatabase();
		db.setName(dbmd.getDatabaseProductName());
		db.setVersion(dbmd.getDatabaseProductVersion());
		db.setDriverName(dbmd.getDriverName());
		db.setDriverVersion(dbmd.getDriverVersion());
		return db;
	}

	@Override
	public List<DbTable> listTables() throws Exception {

		String sql = "show table status";
		PreparedStatement stmt = getConn().prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();

		logger.info("sql语句：{}", sql);

		List<DbTable> tables = new ArrayList<DbTable>();

		while (rs.next()) {
			DbTable table = new DbTable();
			table.setName(rs.getString("name"));
			table.setRows(rs.getInt("rows"));
			table.setComment(rs.getString("comment"));
			tables.add(table);
		}
		return tables;
	}

	@Override
	public void createTable(DbTable table) throws Exception {

		String sql = "create table " + table.getName() + " (id varchar(50),primary key (id)) engine=innodb default charset=utf8 comment='" + table.getComment() + "'";

		PreparedStatement ps = getConn().prepareStatement(sql);
		ps.executeUpdate();

		logger.info("sql语句：{}", sql);
	}

	@Override
	public void updateTable(String oldName, DbTable table) throws Exception {
		String sql = "";
		Statement smt = getConn().createStatement();

		// 如果新、旧表名相同，只更新表注释
		if (!oldName.equals(table.getName())) {
			sql = "alter table " + oldName + " rename to " + table.getName();
			smt.addBatch(sql);

			logger.info("sql语句：{}", sql);
		}

		sql = "alter table " + table.getName() + " comment '" + table.getComment() + "'";
		smt.addBatch(sql);
		smt.executeBatch();

		logger.info("sql语句：{}", sql);
	}

	@Override
	public void deleteTable(String name) throws Exception {

		String sql = "drop table " + name;

		PreparedStatement ps = getConn().prepareStatement(sql);
		ps.executeUpdate();

		logger.info("sql语句：{}", sql);
	}

	@Override
	public List<DbField> listField(String tableName) throws Exception {
		Connection conn = getConn();
		DatabaseMetaData dbmd = conn.getMetaData();
		ResultSet rs = dbmd.getColumns(null, "%", tableName, "%");
		List<DbField> fileds = new ArrayList<DbField>();

		while (rs.next()) {
			DbField field = new DbField();

			field = field.setRemarks(field, rs.getString("REMARKS"));
			field.setName(rs.getString("COLUMN_NAME"));
			field.setType(rs.getString("TYPE_NAME"));
			field.setSize(rs.getString("COLUMN_SIZE"));
			field.setDefaultValue(rs.getString("COLUMN_DEF"));
			fileds.add(field);
		}
		return fileds;
	}

	@Override
	public void saveField(List<DbField> fields, String tableName) throws Exception {

		Statement smt = getConn().createStatement();

		for (int i = 0; i < fields.size(); i++) {
			DbField field = fields.get(i);
			StringBuffer buffer = new StringBuffer();
			buffer.append("alter table " + tableName + " add ");
			buffer.append(field.getName() + " " + field.getType());
			buffer.append(getSizeStr(field.getSize()));
			buffer.append(getDefaultValueStr(field.getDefaultValue()));
			buffer.append(" comment '" + field.getRemarks() + "'");
			smt.addBatch(buffer.toString());

			logger.info("sql语句：{}", buffer.toString());
		}
		smt.executeBatch();
	}

	@Override
	public void deleteField(String tableName, String fieldName) throws Exception {

		String sql = "alter table " + tableName + " drop column " + fieldName;

		PreparedStatement stmt = getConn().prepareStatement(sql);
		stmt.executeUpdate();

		logger.info("sql语句：{}", sql);
	}

	@Override
	public DbField getField(String tableName, String fieldName) throws Exception {
		Connection conn = getConn();
		DatabaseMetaData dbmd = conn.getMetaData();
		ResultSet rs = dbmd.getColumns(null, "%", tableName, "%");
		DbField field = new DbField();
		while (rs.next()) {
			if (fieldName.equals(rs.getString("COLUMN_NAME"))) {
				field = field.setRemarks(field, rs.getString("REMARKS"));
				field.setName(rs.getString("COLUMN_NAME"));
				field.setType(rs.getString("TYPE_NAME"));
				field.setSize(rs.getString("COLUMN_SIZE"));
				field.setDefaultValue(rs.getString("COLUMN_DEF"));
			}
		}
		return field;
	}

	@Override
	public void updateField(String tableName, DbField field) throws Exception {

		Statement smt = getConn().createStatement();

		StringBuffer buffer = new StringBuffer();

		buffer.append("alter table " + tableName + " change ");
		buffer.append(field.getName() + " " + field.getName() + " " + field.getType());
		buffer.append(getSizeStr(field.getSize()));
		buffer.append(getDefaultValueStr(field.getDefaultValue()));
		buffer.append(" comment '" + field.getRemarks() + "'");

		smt.addBatch(buffer.toString());

		logger.info("sql语句：{}", buffer.toString());

		smt.executeBatch();
	}

	private String getSizeStr(String size) {
		if (StringUtils.isNotBlank(size)) {
			return "(" + size + ")";
		}
		return "";
	}

	private String getDefaultValueStr(String defaultValue) {
		if (StringUtils.isNotBlank(defaultValue)) {
			return " default " + defaultValue;
		}
		return "";
	}

}
