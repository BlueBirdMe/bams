package com.pinhuba.core.dao;

import java.io.Serializable;
import java.util.List;

import com.pinhuba.common.pages.Pager;

/**
 * 
 * @author Frin
 * 
 * @param <Obj>
 *            范型，对象引用
 * @param <PK>
 *            范型，对象主键
 */
public interface BaseDao<Obj, PK extends Serializable> {

	public Object save(Obj o);

	public void remove(Obj o);

	public Obj getByPK(PK id);

	public List<Obj> list();
	
	public List<Obj> listPager(Pager pager);
	
	public List<Obj> findNoPage(final String hql);

	public List<Obj> findByPage(final String hql, final int offset,
			final int pageSize);

	public List<Obj> findByPage(final String hql, final Object value,
			final int offset, final int pageSize);

	public List<Obj> findByPage(final String hql, final Object[] values,
			final int offset, final int pageSize);

	public List<Obj> findByProperty(String propertyName, Object value);

	public List<Obj> findByProperty(String[] propertyNames, Object[] values);

	public List<Obj> findByPropertyFuzzy(String propertyName, Object value);

	public List<Obj> findByPropertyFuzzy(String[] propertyNames, Object[] values);
	
	public List<Obj> findByPropertyFuzzyRight(String propertyName, Object value);

	public List<Obj> findByPropertyFuzzyRight(String[] propertyNames, Object[] values);
	
	
	public int listCount();

	public int findByPropertyCount(String propertyName, Object value);

	public int findByPropertyCount(String[] propertyNames, Object[] values);

	public int findByPropertyFuzzyCount(String propertyName, Object value);

	public int findByPropertyFuzzyCount(String[] propertyNames, Object[] values);
	
	public int findByPropertyFuzzyRightCount(String propertyName, Object value);

	public int findByPropertyFuzzyRightCount(String[] propertyNames, Object[] values);
	

	public List<Obj> findByPropertyPage(String propertyName, Object value,Pager pager);
	
	public List<Obj> findByPropertyPage(String[] propertyNames, Object[] values,Pager pager);
	
	public List<Obj> findByPropertyFuzzyPage(String propertyName, Object value,Pager pager);
	
	public List<Obj> findByPropertyFuzzyPage(String[] propertyNames, Object[] values,Pager pager);

	public List<Obj> findByPropertyFuzzyRightPage(String propertyName, Object value,Pager pager);

	public List<Obj> findByPropertyFuzzyRightPage(String[] propertyNames, Object[] values,Pager pager);
	
	public List<Obj> findByHqlWhere(String hqlWhere);
	
	public List<Obj> findByHqlWherePage(String hqlWhere, Pager pager);
	
	public int findByHqlWhereCount(String hqlWhere);
	
	public void flushSession();
	
	public List findBySql(String sql);
	
	public List<Obj> findBySql(String sql,Class entity);
	
	public List findBySqlPage(String sql,Pager pager);
	
	public List<Obj> findBySqlPage(String sql,Class entity,Pager pager);
	
	public int executeSql(String sql);
	
	public List<Object[]> findBySqlObjList(String sql);
	
	public List<Object[]> findBySqlObjListByPager(String sql,Pager pager);
	
	public int findBySqlCount(String sql);
}
