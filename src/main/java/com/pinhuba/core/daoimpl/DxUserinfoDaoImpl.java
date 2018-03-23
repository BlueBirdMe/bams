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
                if (s_age != null && e_age != null && !"".equals(s_age)&& !"".equals(e_age)) {
                    sql.append(" AND ROUND(DATEDIFF(CURDATE(), f.birthday)/365.2422) >=" + s_age +
                            " AND ROUND(DATEDIFF(CURDATE(), f.birthday)/365.2422) <=" + e_age);
                }
                if (deptName != null&& !"".equals(deptName)) {
                    sql.append(" AND f.deptname = '" + deptName+"'");
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
}