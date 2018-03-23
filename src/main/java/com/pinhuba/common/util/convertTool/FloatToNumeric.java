package com.pinhuba.common.util.convertTool;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.sql.DataSource;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.pinhuba.common.util.UtilWork;
import com.pinhuba.common.util.file.properties.SystemConfig;

public class FloatToNumeric {
	
	public static String convertToNumeric(ServletContext context,ArrayList<FloatToNumBean> list) throws Exception{
		String str = "更新完成：";
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
		Connection conn = null;
		PreparedStatement ps= null;
		ResultSet rs =null;
		try {
			DataSource ds = (DataSource) ctx.getBean(SystemConfig.getParam("erp.proxool.dsname"));
			conn=ds.getConnection();
			conn.setAutoCommit(false);
			for (int i = 0; i < list.size(); i++) {
				String sql ="select ";
				int colcount = 1;	
				Map<String, ArrayList<Object>> tmpMap = new HashMap<String, ArrayList<Object>>();
				FloatToNumBean bean = list.get(i);
				sql+=bean.getTabelPkName()+",";
				tmpMap.put(bean.getTabelPkName(), new ArrayList<Object>());
				Set<String> cols = bean.getColsName();
				Iterator<String> it = cols.iterator();
				while (it.hasNext()) {
					String elem = (String) it.next();
					sql+=elem+",";
					colcount++;
					tmpMap.put(elem, new ArrayList<Object>());
				}
				
				if (sql.charAt(sql.length() - 1) == ',') {
					sql = sql.substring(0, sql.length() - 1);
				}
				sql+=" from "+bean.getTableName();
				System.out.println(sql);
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				Set<String> keyset = tmpMap.keySet();
				while(rs.next()){
					Iterator<String> mapit = keyset.iterator();
					while (mapit.hasNext()) {
						String key = (String) mapit.next();
						tmpMap.get(key).add(rs.getObject(key));
					}
				}
				List<String> sqllist = isScienceCountToSql(bean.getTabelPkName(), bean.getTableName(), tmpMap);
				for (String string : sqllist) {
					System.out.println(string);
					ps = conn.prepareStatement(string);
					ps.execute();
				}
				System.out.println("=============="+bean.getTableName());
			}
			String sql = "alter table AST_PRODUCT_PROVIDER_SET alter column ast_unit_price numeric(18,2) null";
			System.out.println(sql);
			ps = conn.prepareStatement(sql);
			ps.execute();
			sql = "alter table ast_order_detail alter column ast_unit_price numeric(18,2) null";
			System.out.println(sql);
			ps = conn.prepareStatement(sql);
			ps.execute();
			
			sql = "alter table ast_order_detail alter column ast_detail_subtotal numeric(18,2) null";
			System.out.println(sql);
			ps = conn.prepareStatement(sql);
			ps.execute();
			
			sql = "alter table AST_PAYMENT alter column AST_PAYMENT_MEET numeric(18,2) null";
			System.out.println(sql);
			ps = conn.prepareStatement(sql);
			ps.execute();
			
			sql = "alter table AST_SELL_DETAIL alter column ast_sell_goods_price numeric(18,2) null";
			System.out.println(sql);
			ps = conn.prepareStatement(sql);
			ps.execute();
			
			sql = "alter table AST_SELL_GOODS_PRICE alter column ast_sell_goods_price numeric(18,2) null";
			System.out.println(sql);
			ps = conn.prepareStatement(sql);
			ps.execute();
			
			sql = "alter table AST_SELL_GOODS_PRICE alter column ast_sell_goods_percent numeric(18,2) null";
			System.out.println(sql);
			ps = conn.prepareStatement(sql);
			ps.execute();
			str+=UtilWork.getNowTime();
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			str = "更新失败："+e.getMessage();
			conn.rollback();
		}finally{
			if (rs!= null) {
				rs.close();
				rs=null;
			}
			if (ps!= null) {
				ps.close();
				ps=null;
			}
			if (conn!= null) {
				conn.close();
				conn = null;
			}
		}
		return str;
	}
	
	
	private static List<String> isScienceCountToSql(String pkname,String tablename,Map<String, ArrayList<Object>> tmpMap){
		DecimalFormat df = new DecimalFormat("#########################.############");
		List<String> sqllist = new ArrayList<String>();
		Set<String> keyset = tmpMap.keySet();
		Iterator<String> mapit = keyset.iterator();
		
		List<Object> pklist = tmpMap.get(pkname);
		
		while (mapit.hasNext()) {
			String key = (String) mapit.next();
			
			if (!key.equalsIgnoreCase(pkname)) {
				List<Object> list = tmpMap.get(key);
				boolean bl = false;
				for (Object val : list) {
					if (val instanceof BigDecimal) {
						break;
					}else if (val!=null) {
						String tmpval = val.toString().toLowerCase();
						if (tmpval.indexOf("e")>0) {//是否包含科学计数
							bl = true;
							break;
						}
					}
				}
				if (bl) {
					sqllist.add("update "+tablename+" set "+key+"=0");
				}
				sqllist.add("alter table "+tablename+" alter column "+key+" numeric(25,12) null");
				//生成更新语句
				
				if (bl&&pklist.size()>0) {
					for (int i = 0; i < list.size(); i++) {
						if(list.get(i)!=null){
							String tmpval =df.format((Double)list.get(i));
							
							Object obj = pklist.get(i);
							if(obj instanceof Integer){
								sqllist.add("update "+tablename+" set "+key+" = "+tmpval+" where "+pkname+" = "+obj.toString());
							}else{
								sqllist.add("update "+tablename+" set "+key+" = "+tmpval+" where "+pkname+" = '"+obj.toString()+"'");
							}
						}
					}
				}
			}
			
		}
		
		return sqllist;
	}
}
