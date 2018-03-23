package com.pinhuba.common.code;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import com.pinhuba.common.code.bean.CodeConfig;
import com.pinhuba.common.code.bean.ColumnProperty;
import com.pinhuba.common.code.database.DatabaseHandler;
import com.pinhuba.common.code.exception.ColumnRemarkException;
import com.pinhuba.common.code.util.SaveFile;
import com.pinhuba.common.code.util.Util;

/**
 * 适用于BAMS项目创建pojo类及dao类
 * 
 * @author JC
 * 
 */
public class CreatePojoAndDao {

	private Connection conn = Util.getHandler().getConn();
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	private ResultSetMetaData rm = null;
	private List<String> tables = new ArrayList<String>();// 生成的表名
	private String srcPath = CodeConfig.getInstance().getSrcPath();
	private DatabaseHandler handler = Util.getHandler();
	// 定义不要生成的成员变量，下面四个成员变量已经在父类里定义，不需要在业务实体类中生成
	private static final String IGNORE_STR = "recordId,recordDate,lastmodiId,lastmodiDate";

	public CreatePojoAndDao(String tablestr) throws Exception {
		// 检测表字段注释格式
		for (String table : tablestr.split(",")) {
			List<String> remarks = handler.getRemarks(conn, table);
			for (String remark : remarks) {
				if (StringUtils.isNotBlank(remark) && remark.split("\\|").length != ColumnProperty.PROPERTY_COUNT) {
					String message = "表字段注释格式错误！<br/>表名：" + table + "<br/>注释内容：" + remark;
					throw new ColumnRemarkException(message);
				}
			}
			tables.add(table);// 在此处放入表
		}
	}

	public void getTablePojo() throws Exception {
		for (int i = 0; i < tables.size(); i++) {
			String sql = "select * from " + tables.get(i);
			String reString = this.getTableString(sql, tables.get(i));
			// 写入文件
			SaveFile.writeFile(srcPath + "com/pinhuba/core/pojo/" + getTableOrColumn(tables.get(i), 1) + ".java", reString);
		}
	}

	public void getTableDao() throws Exception {
		for (int i = 0; i < tables.size(); i++) {
			String sql = "select * from " + tables.get(i);
			String reString = this.getTableDaoString(sql, tables.get(i));
			// 写入文件
			SaveFile.writeFile(srcPath + "com/pinhuba/core/dao/" + "I" + getTableOrColumn(tables.get(i), 1) + "Dao.java", reString);
		}
	}

	public void getTableDaoImpl() throws Exception {
		for (int i = 0; i < tables.size(); i++) {
			String sql = "select * from " + tables.get(i);
			String reString = this.getTableDaoImplString(sql, tables.get(i));
			// 写入文件
			SaveFile.writeFile(srcPath + "com/pinhuba/core/daoimpl/" + getTableOrColumn(tables.get(i), 1) + "DaoImpl.java", reString);
		}
	}

	private String getTableDaoImplString(String sql, String tableName) throws Exception {
		StringBuffer sb = new StringBuffer();
		String tname = getTableOrColumn(tableName, 1);
		sb.append("package com.pinhuba.core.daoimpl;\n\n");
		sb.append("import org.springframework.stereotype.Repository;\n");
		sb.append("import com.pinhuba.core.pojo.*;\n");
		sb.append("import com.pinhuba.core.dao.*;\n");
		sb.append("/**\n");
		sb.append(" * 表：" + tableName + " 对应daoImpl\n");
		sb.append(" */\n");
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		rm = rs.getMetaData();
		String tmp = "";
		if (rm.getColumnCount() > 0) {
			int type = rm.getColumnType(1);
			if (type == Types.INTEGER) {
				tmp = "Long";
			} else {
				tmp = "String";
			}
		}
		sb.append("@Repository\n");
		sb.append("public class " + tname + "DaoImpl extends BaseHapiDaoimpl<" + tname + ", " + tmp + "> implements I" + tname + "Dao {\n\n");
		sb.append("    public " + tname + "DaoImpl(){\n");
		sb.append("        super(" + tname + ".class);\n");
		sb.append("    }\n");
		sb.append("}");
		return sb.toString();
	}

	private String getTableDaoString(String sql, String tableName) throws Exception {
		StringBuffer sb = new StringBuffer();
		String tname = getTableOrColumn(tableName, 1);
		sb.append("package com.pinhuba.core.dao;\n\n");
		sb.append("import com.pinhuba.core.pojo.*;\n");
		sb.append("/**\n");
		sb.append(" * 表：" + tableName + " 对应dao\n");
		sb.append(" */\n");
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		rm = rs.getMetaData();
		String tmp = "";
		if (rm.getColumnCount() > 0) {
			int type = rm.getColumnType(1);
			if (type == Types.INTEGER) {
				tmp = "Long";
			} else {
				tmp = "String";
			}
		}
		sb.append("public interface I" + tname + "Dao extends BaseDao<" + tname + "," + tmp + ">{\n\n");
		sb.append("}");
		return sb.toString();
	}

	private String getTableString(String sql, String tableName) throws Exception {
		StringBuffer sb = new StringBuffer();
		String tname = getTableOrColumn(tableName, 1);
		sb.append("package com.pinhuba.core.pojo;\n\n");
		sb.append("import com.pinhuba.common.annotation.Remark;\n\n");
		sb.append("/**\n");
		sb.append(" * 数据库表名：" + tableName + "\n");
		sb.append(" */\n");
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		rm = rs.getMetaData();
		List<String> remarks = handler.getRemarks(conn, tableName);
		String tmp = "";
		if (rm.getColumnCount() > 0) {
			int type = rm.getColumnType(1);
			if (type == Types.INTEGER) {
				tmp = "BaseBean";
			} else {
				tmp = "BaseStringBean";
			}
		}
		sb.append("public class " + tname + " extends " + tmp + " implements java.io.Serializable {\n\n");
		for (int i = 2; i <= rm.getColumnCount(); i++) {
			String cname = getTableOrColumn(rm.getColumnName(i), 0);
			if (checkcname(cname)) {
				String remark = remarks.get(i - 1);// 字段注释
				int columnSize = rm.getPrecision(i);// 字段长度
				int columnType = rm.getColumnType(i);// 字段类型
				String fieldType = handler.columnTypeToFieldType(columnType, columnSize);
				// 如果字段有注释并且注释格式正确，加上@Remark注解
				if (StringUtils.isNotBlank(remark)) {
					sb.append("    @Remark(\"" + remark + "\")\n");
				}
				sb.append("    private " + fieldType + " " + cname + ";\n");
			}
		}
		sb.append("\n");
		sb.append("    //默认构造方法\n");
		sb.append("    public " + tname + "(){\n");
		sb.append("        super();\n");
		sb.append("    }\n");

		sb.append("\n");
		sb.append("    //构造方法(手工生成)\n\n");

		sb.append("\n");
		sb.append("    //get和set方法\n");
		for (int i = 2; i <= rm.getColumnCount(); i++) {

			String cname = getTableOrColumn(rm.getColumnName(i), 0);
			if (checkcname(cname)) {
				int columnSize = rm.getPrecision(i);// 字段长度
				int columnType = rm.getColumnType(i);
				String fieldType = handler.columnTypeToFieldType(columnType, columnSize);
				sb.append(this.createGetAndSetMethod(fieldType, cname));
			}
		}
		sb.append("}");
		return sb.toString();
	}

	private boolean checkcname(String cname) {
		if (IGNORE_STR.contains(cname)) {
			return false;
		}
		return true;
	}

	private String createGetAndSetMethod(String type, String colsName) {
		StringBuffer result = new StringBuffer();
		String tmp1 = colsName.substring(0, 1).toUpperCase();
		String tmp2 = colsName.substring(1, colsName.length());
		String tmp3 = "a" + tmp1 + tmp2;
		result.append("    public " + type + " get" + tmp1 + tmp2 + "(){\n");
		result.append("        return " + colsName + ";\n");
		result.append("    }\n");
		result.append("\n");
		result.append("    public void set" + tmp1 + tmp2 + "(" + type + " " + tmp3 + "){\n");
		result.append("        this." + colsName + " = " + tmp3 + ";\n");
		result.append("    }\n");
		result.append("\n");
		return result.toString();
	}

	// 对表名和列名进行转换 type==1为表名 否则为列名
	private String getTableOrColumn(String oldname, int type) {
		String tmp = oldname.toLowerCase();
		String newStr = "";
		if (type == 1) {
			String[] tbs = tmp.split("_");
			for (int i = 0; i < tbs.length; i++) {
				String strat = tbs[i].substring(0, 1).toUpperCase();
				String end = tbs[i].substring(1, tbs[i].length());
				newStr += strat + end;
			}
		} else {
			String[] cols = tmp.split("_");
			for (int i = 0; i < cols.length; i++) {
				if (i == 0) {
					newStr += cols[i];
				} else {
					String strat = cols[i].substring(0, 1).toUpperCase();
					String end = cols[i].substring(1, cols[i].length());
					newStr += strat + end;
				}
			}
		}
		return newStr;

	}

	public void getConfig() throws Exception {
		StringBuffer writeString = new StringBuffer();
		writeString.append("//复制到spring-service.xml文件 Dwr Base配置区\n");
		for (int i = 0; i < tables.size(); i++) {
			String dwrStr = this.getDwrString(tables.get(i));
			writeString.append(dwrStr);
		}
		
		writeString.append("\n\n");
		writeString.append("//复制到hbm." + handler.getName() + ".xml文件\n");
		for (int i = 0; i < tables.size(); i++) {
			String sql = "select * from " + tables.get(i);
			String hibString = this.getHibernateString(sql, tables.get(i));
			writeString.append(hibString);
		}
		SaveFile.writeFile(srcPath + "config.txt", writeString.toString());
	}

	private String getDwrString(String tableName) {
		StringBuffer sb = new StringBuffer();
		String tname = getTableOrColumn(tableName, 1);
		sb.append("<dwr:convert type=\"hibernate3\" class=\"com.pinhuba.core.pojo." + tname + "\"/>\n");
		return sb.toString();
	}

	private String getHibernateString(String sql, String tableName) throws Exception {
		StringBuffer sb = new StringBuffer();
		String tname = getTableOrColumn(tableName, 1);
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		rm = rs.getMetaData();
		String tmp = "";
		String tmp2 = null;
		String oneCol = "";
		if (rm.getColumnCount() > 0) {
			oneCol = rm.getColumnName(1);
			int type = rm.getColumnType(1);
			if (type == Types.INTEGER) {
				tmp = "long";
			} else {
				tmp = "java.lang.String";
			}
			if (rm.isAutoIncrement(1)) {
				tmp2 = "        <generator class=\"identity\" />";
			}
		}
		sb.append("<class name=\"com.pinhuba.core.pojo." + tname + "\" table=\"" + tableName + "\" >\n");
		sb.append("    <id name=\"primaryKey\" type=\"" + tmp + "\">\n");
		sb.append("        <column name=\"" + oneCol + "\" />\n");
		if (tmp2 != null) {
			sb.append(tmp2 + "\n");
		}
		sb.append("    </id>\n");
		for (int i = 2; i <= rm.getColumnCount(); i++) {
			String cname = getTableOrColumn(rm.getColumnName(i), 0);
			int columnSize = rm.getPrecision(i);// 字段长度
			int columnType = rm.getColumnType(i);
			String fieldType = handler.columnTypeToFieldType(columnType, columnSize);
			sb.append(this.createHib(fieldType, cname, rm.getColumnName(i)));
		}
		sb.append("</class>\n\n");
		return sb.toString();
	}

	private String createHib(String type, String colName, String baseColName) {
		StringBuffer sb = new StringBuffer();
		sb.append("    <property name=\"" + colName + "\" type=\"java.lang." + type + "\">\n");
		sb.append("        <column name=\"" + baseColName + "\"/>\n");
		sb.append("    </property>\n");
		return sb.toString();
	}
}
