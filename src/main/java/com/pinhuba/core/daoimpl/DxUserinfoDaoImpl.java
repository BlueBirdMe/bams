package com.pinhuba.core.daoimpl;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;
import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 表：dx_userinfo 对应daoImpl
 */
@Repository
public class DxUserinfoDaoImpl extends BaseHapiDaoimpl<DxUserinfo, String> implements IDxUserinfoDao {

    public DxUserinfoDaoImpl() {
        super(DxUserinfo.class);
    }

    @Override
    public List<Object[]> countByHqlWhere(String s_age, String e_age, String deptName) {
        return this.getHibernateTemplate().execute(new HibernateCallback<List<Object[]>>() {
            @SuppressWarnings("unchecked")
            @Override
            public List<Object[]> doInHibernate(Session session)
                    throws HibernateException, SQLException {
                StringBuffer sql = new StringBuffer("SELECT t.name,COUNT(f.id) FROM dx_corporation t LEFT JOIN dx_userinfo f ON t.id =f.corpid ");
                if (s_age != null && e_age != null && !"".equals(s_age) && !"".equals(e_age)) {
                    sql.append(" AND ROUND(DATEDIFF(CURDATE(), f.birthday)/365.2422) >=" + s_age +
                            " AND ROUND(DATEDIFF(CURDATE(), f.birthday)/365.2422) <=" + e_age);
                }
                if (deptName != null && !"".equals(deptName)) {
                    sql.append(" AND f.deptname = '" + deptName + "'");
                }
                sql.append(" WHERE t.parentid IS NULL AND t.flag='b\\'0\\'' GROUP BY t.name");
                SQLQuery query = session.createSQLQuery(sql.toString());
//                query.addScalar("num", Hibernate.STRING);
//                query.addScalar("corpname", Hibernate.STRING);
                List results = query.list();  //有多个列，返回的是List<Object[]>
                return (List<Object[]>) results;
            }
        });
    }

    @Override
    public List<Object[]> computeUserLev(String pk) {
        return this.getHibernateTemplate().execute(new HibernateCallback<List<Object[]>>() {
            @SuppressWarnings("unchecked")
            @Override
            public List<Object[]> doInHibernate(Session session)
                    throws HibernateException, SQLException {
                String sql = "SELECT  " +
                        "CASE t.fulltime WHEN '博士' THEN '100'  WHEN '硕士' THEN '90' WHEN '本科' THEN '80' ELSE '70' END AS e_educate, " +
                        "CASE WHEN ROUND(DATEDIFF(CURDATE(), t.birthday)/365.2422)>=30 AND ROUND(DATEDIFF(CURDATE(), t.birthday)/365.2422)<=40 THEN '100' WHEN ((ROUND(DATEDIFF(CURDATE(), t.birthday)/365.2422)>=20 AND ROUND(DATEDIFF(CURDATE(), t.birthday)/365.2422)<30)) OR (( ROUND(DATEDIFF(CURDATE(), t.birthday)/365.2422)>40 AND ROUND(DATEDIFF(CURDATE(), t.birthday)/365.2422)<50)) THEN '90' WHEN ROUND(DATEDIFF(CURDATE(), t.birthday)/365.2422)>=50 THEN '80' ELSE '80' END AS e_age, " +
                        "CASE WHEN ROUND((LENGTH(f.experience) - LENGTH(REPLACE(f.experience,\"、\", \"\"))) / LENGTH(\"、\"),0)>10 THEN '100' WHEN ROUND((LENGTH(f.experience) - LENGTH(REPLACE(f.experience,\"、\", \"\"))) / LENGTH(\"、\"),0)>=9 AND ROUND((LENGTH(f.experience) - LENGTH(REPLACE(f.experience,\"、\", \"\"))) / LENGTH(\"、\"),0)<=10 THEN '90' WHEN ROUND((LENGTH(f.experience) - LENGTH(REPLACE(f.experience,\"、\", \"\"))) / LENGTH(\"、\"),0)>=7 AND ROUND((LENGTH(f.experience) - LENGTH(REPLACE(f.experience,\"、\", \"\"))) / LENGTH(\"、\"),0)<=8 THEN '80' WHEN ROUND((LENGTH(f.experience) - LENGTH(REPLACE(f.experience,\"、\", \"\"))) / LENGTH(\"、\"),0)>=3 AND ROUND((LENGTH(f.experience) - LENGTH(REPLACE(f.experience,\"、\", \"\"))) / LENGTH(\"、\"),0)<=6 THEN '70' ELSE '60' END AS e_experience, " +
                        "CASE WHEN ROUND((LENGTH(g.archieve) - LENGTH(REPLACE(g.archieve,\"、\", \"\"))) / LENGTH(\"、\"),0)>=7 THEN '100' WHEN ROUND((LENGTH(g.archieve) - LENGTH(REPLACE(g.archieve,\"、\", \"\"))) / LENGTH(\"、\"),0)>=4 AND ROUND((LENGTH(g.archieve) - LENGTH(REPLACE(g.archieve,\"、\", \"\"))) / LENGTH(\"、\"),0)<=6 THEN '90' WHEN ROUND((LENGTH(g.archieve) - LENGTH(REPLACE(g.archieve,\"、\", \"\"))) / LENGTH(\"、\"),0)>=2 AND ROUND((LENGTH(g.archieve) - LENGTH(REPLACE(g.archieve,\"、\", \"\"))) / LENGTH(\"、\"),0)<=3 THEN '80' ELSE '60' END AS e_archieve, " +
                        "CASE WHEN ROUND(DATEDIFF(CURDATE(), t.workdate)/365.2422)>15 THEN '100' WHEN ROUND(DATEDIFF(CURDATE(), t.workdate)/365.2422)>=10 AND ROUND(DATEDIFF(CURDATE(), t.workdate)/365.2422)<=15 THEN '90' WHEN ROUND(DATEDIFF(CURDATE(), t.workdate)/365.2422)>=5 AND ROUND(DATEDIFF(CURDATE(), t.workdate)/365.2422)<10 THEN '80' ELSE '70' END AS e_workdate, " +
                        "CASE g.ability WHEN 'B级' THEN '100'  WHEN 'C级' THEN '80' ELSE '0' END AS e_ability " +
                        "FROM dx_userinfo t " +
                        "LEFT JOIN dx_educate f ON t.empid=f.empid " +
                        "LEFT JOIN dx_archivement g ON t.empid=g.empid " +
                        "WHERE t.empid= '" + pk + "'";
                SQLQuery query = session.createSQLQuery(sql.toString());
//                query.addScalar("num", Hibernate.STRING);
//                query.addScalar("corpname", Hibernate.STRING);
                List results = query.list();  //有多个列，返回的是List<Object[]>
                return (List<Object[]>) results;
            }
        });
    }
}