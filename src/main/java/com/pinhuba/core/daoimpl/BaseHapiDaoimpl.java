package com.pinhuba.core.daoimpl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import com.pinhuba.common.pages.Pager;
import com.pinhuba.core.dao.BaseDao;

/**
 * 
 * @author Frin
 * 
 * @param <Obj>
 *            范型，对象引用
 * @param <PK>
 *            范型，对象主键
 * @description Hibernate实现基本数据库交互
 */
public class BaseHapiDaoimpl<Obj, PK extends Serializable> extends
		HibernateDaoSupport implements BaseDao<Obj, PK> {
	Logger logger = Logger.getLogger(BaseHapiDaoimpl.class);

	private Class<Obj> persistentClass;

	public BaseHapiDaoimpl(Class<Obj> persistentClass) {
		this.persistentClass = persistentClass;
	}
	
	@Resource
	public void setSuperSessionFactory(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}

	/**
	 *
	 * @description 通过主键获取对象，getHibernateTemplate().load会检索缓存，如果查不到信息会抛出异常。
	 * @references getHibernateTemplate().get，直接从数据库去，查不到信息返回null，不会检索缓存。
	 */
	@SuppressWarnings("unchecked")
	public Obj getByPK(PK id) {
		logger.debug("get:" + id);
		return (Obj) this.getHibernateTemplate().get(persistentClass, id);
	}

	/**
	 * 查处所有对象
	 */
	@SuppressWarnings("unchecked")
	public List<Obj> list() {
		logger.debug("list:" + persistentClass.getName());
		return this.getHibernateTemplate().find(
				"from " + persistentClass.getName());
	}

	public int listCount() {
		logger.debug("list:" + persistentClass.getName());
		long count = (Long) getSession().createQuery(
				"select count(distinct model) from "
						+ persistentClass.getName() + " as model").list()
				.iterator().next();
		
		return Integer.parseInt(count + "");
	}

	@SuppressWarnings("unchecked")
	public List<Obj> listPager(Pager pager) {
		logger.debug("listPage:" + persistentClass.getName());
		return this.getSession().createQuery(
				"from " + persistentClass.getName()).setFirstResult(
				pager.getStartRow()).setMaxResults(pager.getPageSize()).list();
	}

	/**
	 * 删除对象
	 */
	public void remove(Obj o) {
		logger.debug("list:" + persistentClass.getName());
		this.getHibernateTemplate().delete(o);
	}
	
	/**
	 * 保存或更新对象
	 */
	public Object save(Obj o) {
		logger.debug("save:" + persistentClass.getName());
		Object obj= this.getHibernateTemplate().merge(o);
		return obj;
	}

	/**
	 * 使用hql 语句进行分页查询操作
	 * 
	 * @param hql
	 *            需要查询的hql语句
	 * @param offset
	 *            第一条记录索引
	 * @param pageSize
	 *            每页需要显示的记录数
	 * @return 当前页的所有记录
	 */

	@SuppressWarnings("unchecked")
	public List<Obj> findByPage(final String hql, final int offset,
			final int pageSize) {

		List<Obj> list = getHibernateTemplate().executeFind(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						List<Obj> result = session.createQuery(hql)
								.setFirstResult(offset).setMaxResults(pageSize)
								.list();
						return result;
					}
				});
		return list;
	}
	
	/**
	 * 使用hql 语句查询操作，不分页
	 * 
	 * @param hql
	 *            需要查询的hql语句
	 * @param value
	 *            如果hql有一个参数需要传入，value就是传入的参数
	 * @param offset
	 *            第一条记录索引
	 * @param pageSize
	 *            每页需要显示的记录数
	 * @return 当前页的所有记录
	 */
	@SuppressWarnings("unchecked")
	public List<Obj> findNoPage(final String hql) {

		List<Obj> list = getHibernateTemplate().executeFind(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						List<Obj> result = session.createQuery(hql).list();
						return result;
					}
				});
		return list;
	}

	/**
	 * 使用hql 语句进行分页查询操作
	 * 
	 * @param hql
	 *            需要查询的hql语句
	 * @param value
	 *            如果hql有一个参数需要传入，value就是传入的参数
	 * @param offset
	 *            第一条记录索引
	 * @param pageSize
	 *            每页需要显示的记录数
	 * @return 当前页的所有记录
	 */
	@SuppressWarnings("unchecked")
	public List<Obj> findByPage(final String hql, final Object value,
			final int offset, final int pageSize) {

		List<Obj> list = getHibernateTemplate().executeFind(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						List<Obj> result = session.createQuery(hql)
								.setParameter(0, value).setFirstResult(offset)
								.setMaxResults(pageSize).list();
						return result;
					}
				});
		return list;
	}

	/**
	 * 使用hql 语句进行分页查询操作
	 * 
	 * @param hql
	 *            需要查询的hql语句
	 * @param values
	 *            如果hql有多个个参数需要传入，values就是传入的参数数组
	 * @param offset
	 *            第一条记录索引
	 * @param pageSize
	 *            每页需要显示的记录数
	 * @return 当前页的所有记录
	 */
	@SuppressWarnings("unchecked")
	public List<Obj> findByPage(final String hql, final Object[] values,
			final int offset, final int pageSize) {

		List<Obj> list = getHibernateTemplate().executeFind(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createQuery(hql);
						for (int i = 0; i < values.length; i++) {
							query.setParameter(i, values[i]);
						}
						List<Obj> result = query.setFirstResult(offset)
								.setMaxResults(pageSize).list();
						return result;
					}
				});
		return list;
	}

	/**
	 * 通过单个对象属性查找对象
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Obj> findByProperty(String propertyName, Object value) {
		String queryString = "from " + persistentClass.getSimpleName()
				+ " as model where model." + propertyName + "= ?";
		Query queryObject = getSession().createQuery(queryString);
		queryObject.setParameter(0, value);
		return queryObject.list();
	}

	public int findByPropertyCount(String propertyName, Object value) {
		String queryString = "select count(distinct model) from "
				+ persistentClass.getName() + " as model where model."
				+ propertyName + "= ?";
		long count = (Long) getSession().createQuery(queryString).setParameter(
				0, value).list().iterator().next();
		return Integer.parseInt(count + "");
	}

	/**
	 * 通过多个对象属性查找对象，封装成数组
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Obj> findByProperty(String[] propertyNames, Object[] values) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("from " + persistentClass.getSimpleName()
				+ " as model ");

		int size = propertyNames.length;
		if (size > 0) {
			queryString.append("where 1=1 ");
		}

		for (int i = 0; i < size; i++) {
			queryString.append("and model." + propertyNames[i] + "= ? ");
		}

		Query queryObject = getSession().createQuery(queryString.toString());
		for (int i = 0; i < size; i++) {
			queryObject.setParameter(i, values[i]);
		}

		return queryObject.list();
	}

	public int findByPropertyCount(String[] propertyNames, Object[] values) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("select count(distinct model) from "
				+ persistentClass.getName() + " as model");

		int size = propertyNames.length;
		if (size > 0) {
			queryString.append("where 1=1 ");
		}

		for (int i = 0; i < size; i++) {
			queryString.append("and model." + propertyNames[i] + "= ? ");
		}

		Query queryObject = getSession().createQuery(queryString.toString());
		for (int i = 0; i < size; i++) {
			queryObject.setParameter(i, values[i]);
		}

		long count = (Long) queryObject.list().iterator().next();
		return Integer.parseInt(count + "");
	}

	/**
	 * 通过单个对象属性模糊查找
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Obj> findByPropertyFuzzy(String propertyName, Object value) {
		String queryString = "from " + persistentClass.getSimpleName()
				+ " as model where model." + propertyName + " like ?";
		Query queryObject = getSession().createQuery(queryString);
		queryObject.setParameter(0, "%" + value + "%");
		return queryObject.list();
	}

	public int findByPropertyFuzzyCount(String propertyName, Object value) {
		String queryString = "select count(distinct model) from "
				+ persistentClass.getName() + " as model where model."
				+ propertyName + " like ?";
		Query queryObject = getSession().createQuery(queryString);
		queryObject.setParameter(0, "%" + value + "%");
		long count = (Long) queryObject.list().iterator().next();
		return Integer.parseInt(count + "");
	}

	/**
	 * 通过多个对象属性模糊查找，封装成数组
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Obj> findByPropertyFuzzy(String[] propertyNames, Object[] values) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("from " + persistentClass.getSimpleName()
				+ " as model ");

		int size = propertyNames.length;
		if (size > 0) {
			queryString.append("where 1=1 ");
		}

		for (int i = 0; i < size; i++) {
			queryString.append("and model." + propertyNames[i] + " like ? ");
		}

		Query queryObject = getSession().createQuery(queryString.toString());
		for (int i = 0; i < size; i++) {
			queryObject.setParameter(i, "%" + values[i] + "%");
		}

		return queryObject.list();
	}

	public int findByPropertyFuzzyCount(String[] propertyNames, Object[] values) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("select count(distinct model) from "
				+ persistentClass.getSimpleName() + " as model ");

		int size = propertyNames.length;
		if (size > 0) {
			queryString.append("where 1=1 ");
		}

		for (int i = 0; i < size; i++) {
			queryString.append("and model." + propertyNames[i] + " like ? ");
		}

		Query queryObject = getSession().createQuery(queryString.toString());
		for (int i = 0; i < size; i++) {
			queryObject.setParameter(i, "%" + values[i] + "%");
		}

		long count = (Long) queryObject.list().iterator().next();
		return Integer.parseInt(count + "");
	}

	/**
	 * 通过单个对象属性右模糊查找
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Obj> findByPropertyFuzzyRight(String propertyName, Object value) {
		String queryString = "from " + persistentClass.getSimpleName()
				+ " as model where model." + propertyName + " like ?";
		Query queryObject = getSession().createQuery(queryString);
		queryObject.setParameter(0, value + "%");
		return queryObject.list();
	}

	public int findByPropertyFuzzyRightCount(String propertyName, Object value) {
		String queryString = "select count(distinct model) from "
				+ persistentClass.getSimpleName() + " as model where model."
				+ propertyName + " like ?";
		Query queryObject = getSession().createQuery(queryString);
		queryObject.setParameter(0, value + "%");
		long count = (Long) queryObject.list().iterator().next();
		return Integer.parseInt(count + "");
	}

	/**
	 * 通过多个对象属性右模糊查找，封装成数组
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Obj> findByPropertyFuzzyRight(String[] propertyNames,
			Object[] values) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("from " + persistentClass.getSimpleName()
				+ " as model ");

		int size = propertyNames.length;
		if (size > 0) {
			queryString.append("where 1=1 ");
		}

		for (int i = 0; i < size; i++) {
			queryString.append("and model." + propertyNames[i] + " like ? ");
		}

		Query queryObject = getSession().createQuery(queryString.toString());
		for (int i = 0; i < size; i++) {
			queryObject.setParameter(i, values[i] + "%");
		}

		return queryObject.list();
	}

	public int findByPropertyFuzzyRightCount(String[] propertyNames,
			Object[] values) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("select count(distinct model) from "
				+ persistentClass.getSimpleName() + " as model ");

		int size = propertyNames.length;
		if (size > 0) {
			queryString.append("where 1=1 ");
		}

		for (int i = 0; i < size; i++) {
			queryString.append("and model." + propertyNames[i] + " like ? ");
		}

		Query queryObject = getSession().createQuery(queryString.toString());
		for (int i = 0; i < size; i++) {
			queryObject.setParameter(i, values[i] + "%");
		}

		long count = (Long) queryObject.list().iterator().next();
		return Integer.parseInt(count + "");
	}

	/**
	 * 分页-通过单个对象属性查找对象
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Obj> findByPropertyPage(String propertyName, Object value,
			Pager pager) {
		String queryString = "from " + persistentClass.getSimpleName()
				+ " as model where model." + propertyName + "= ?";
		Query queryObject = getSession().createQuery(queryString);
		queryObject.setParameter(0, value);
		return queryObject.setFirstResult(pager.getStartRow()).setMaxResults(
				pager.getPageSize()).list();
	}

	/**
	 * 分页-通过多个对象属性查找对象，封装成数组
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Obj> findByPropertyPage(String[] propertyNames,
			Object[] values, Pager pager) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("from " + persistentClass.getSimpleName()
				+ " as model ");

		int size = propertyNames.length;
		if (size > 0) {
			queryString.append("where 1=1 ");
		}

		for (int i = 0; i < size; i++) {
			queryString.append("and model." + propertyNames[i] + "= ? ");
		}

		Query queryObject = getSession().createQuery(queryString.toString());
		for (int i = 0; i < size; i++) {
			queryObject.setParameter(i, values[i]);
		}

		return queryObject.setFirstResult(pager.getStartRow()).setMaxResults(
				pager.getPageSize()).list();
	}

	/**
	 * 分页-通过单个对象属性模糊查找
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Obj> findByPropertyFuzzyPage(String propertyName, Object value,
			Pager pager) {
		String queryString = "from " + persistentClass.getSimpleName()
				+ " as model where model." + propertyName + " like ?";
		Query queryObject = getSession().createQuery(queryString);
		queryObject.setParameter(0, "%" + value + "%");
		return queryObject.setFirstResult(pager.getStartRow()).setMaxResults(
				pager.getPageSize()).list();
	}

	/**
	 * 分页-通过多个对象属性模糊查找，封装成数组
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Obj> findByPropertyFuzzyPage(String[] propertyNames,
			Object[] values, Pager pager) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("from " + persistentClass.getSimpleName()
				+ " as model ");

		int size = propertyNames.length;
		if (size > 0) {
			queryString.append("where 1=1 ");
		}

		for (int i = 0; i < size; i++) {
			queryString.append("and model." + propertyNames[i] + " like ? ");
		}

		Query queryObject = getSession().createQuery(queryString.toString());
		for (int i = 0; i < size; i++) {
			queryObject.setParameter(i, "%" + values[i] + "%");
		}

		return queryObject.setFirstResult(pager.getStartRow()).setMaxResults(
				pager.getPageSize()).list();
	}

	/**
	 * 分页-通过单个对象属性右模糊查找
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Obj> findByPropertyFuzzyRightPage(String propertyName,
			Object value, Pager pager) {
		String queryString = "from " + persistentClass.getSimpleName()
				+ " as model where model." + propertyName + " like ?";
		Query queryObject = getSession().createQuery(queryString);
		queryObject.setParameter(0, value + "%");
		return queryObject.setFirstResult(pager.getStartRow()).setMaxResults(
				pager.getPageSize()).list();
	}

	/**
	 * 分页-通过多个对象属性右模糊查找，封装成数组
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Obj> findByPropertyFuzzyRightPage(String[] propertyNames,
			Object[] values, Pager pager) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("from " + persistentClass.getSimpleName()
				+ " as model ");

		int size = propertyNames.length;
		if (size > 0) {
			queryString.append("where 1=1 ");
		}

		for (int i = 0; i < size; i++) {
			queryString.append("and model." + propertyNames[i] + " like ? ");
		}

		Query queryObject = getSession().createQuery(queryString.toString());
		for (int i = 0; i < size; i++) {
			queryObject.setParameter(i, values[i] + "%");
		}

		return queryObject.setFirstResult(pager.getStartRow()).setMaxResults(
				pager.getPageSize()).list();
	}

	/**
	 * 分页-通过多个对象属性右模糊查找，封装成数组 需要先验证likeNames = likeValues和equalNames和equalValues
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Obj> findByPropertyFuzzyRightPage(List<String> likeNames,
			List<Object> likeValues, List<String> equalNames,
			List<Integer> equalValues, Pager pager) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("from " + persistentClass.getSimpleName()
				+ " as model ");

		int totalSize = likeNames.size() + equalNames.size();
		if (totalSize > 0) {
			queryString.append("where 1=1 ");
		}

		// 附加非主键模糊查询
		for (String lnames : likeNames) {
			queryString.append("and model." + lnames + " like ? ");
		}
		// 附加主键查询
		for (String eqnames : equalNames) {
			queryString.append("and model." + eqnames + " = ? ");
		}

		Query queryObject = getSession().createQuery(queryString.toString());
		int index = 0;
		for (Object valueObj : likeValues) {
			queryObject.setParameter(index,
					(valueObj instanceof String) ? valueObj + "%" : valueObj);
			index++;
		}
		for (Integer valueObj : equalValues) {
			queryObject.setParameter(index, valueObj);
			index++;
		}

		return queryObject.setFirstResult(pager.getStartRow()).setMaxResults(
				pager.getPageSize()).list();
	}

	public int findByPropertyFuzzyRightCount(List<String> likeNames,
			List<Object> likeValues, List<String> equalNames,
			List<Integer> equalValues, String otherHql) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("select count(distinct model) from "
				+ persistentClass.getSimpleName() + " as model ");

		int totalSize = likeNames.size() + equalNames.size();
		if (totalSize > 0) {
			queryString.append("where 1=1 ");
		}
		if (otherHql != null && otherHql.length() > 0) {
			queryString.append(otherHql + " ");
		}

		// 附加非主键模糊查询
		for (String lnames : likeNames) {
			queryString.append("and model." + lnames + " like ? ");
		}
		// 附加主键查询
		for (String eqnames : equalNames) {
			queryString.append("and model." + eqnames + " = ? ");
		}

		Query queryObject = getSession().createQuery(queryString.toString());
		int index = 0;
		for (Object valueObj : likeValues) {
			queryObject.setParameter(index,
					(valueObj instanceof String) ? valueObj + "%" : valueObj);
			index++;
		}
		for (Integer valueObj : equalValues) {
			queryObject.setParameter(index, valueObj);
			index++;
		}

		long count = (Long) queryObject.list().iterator().next();
		return Integer.parseInt(count + "");
	}

	/**
	 * 分页-通过多个对象属性右模糊查找，封装成数组 需要先验证likeNames = likeValues和equalNames和equalValues
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Obj> findByPropertyFuzzyPage(List<String> likeNames,
			List<Object> likeValues, List<String> equalNames,
			List<Integer> equalValues, String otherHql, Pager pager) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("from " + persistentClass.getSimpleName()
				+ " as model ");

		int totalSize = likeNames.size() + equalNames.size();
		if (totalSize > 0) {
			queryString.append("where 1=1 ");
		}
		if (otherHql != null && otherHql.length() > 0) {
			queryString.append(otherHql + " ");
		}

		// 附加非主键模糊查询
		for (String lnames : likeNames) {
			queryString.append("and model." + lnames + " like ? ");
		}
		// 附加主键查询
		for (String eqnames : equalNames) {
			queryString.append("and model." + eqnames + " = ? ");
		}

		Query queryObject = getSession().createQuery(queryString.toString());
		int index = 0;
		for (Object valueObj : likeValues) {
			queryObject.setParameter(index, (valueObj instanceof String) ? "%"
					+ valueObj + "%" : valueObj);
			index++;
		}
		for (Integer valueObj : equalValues) {
			queryObject.setParameter(index, valueObj);
			index++;
		}

		return queryObject.setFirstResult(pager.getStartRow()).setMaxResults(
				pager.getPageSize()).list();
	}

	public int findByPropertyFuzzyPageCount(List<String> likeNames,
			List<Object> likeValues, List<String> equalNames,
			List<Integer> equalValues) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("select count(distinct model) from "
				+ persistentClass.getSimpleName() + " as model ");

		int totalSize = likeNames.size() + equalNames.size();
		if (totalSize > 0) {
			queryString.append("where 1=1 ");
		}

		// 附加非主键模糊查询
		for (String lnames : likeNames) {
			queryString.append("and model." + lnames + " like ? ");
		}
		// 附加主键查询
		for (String eqnames : equalNames) {
			queryString.append("and model." + eqnames + " = ? ");
		}

		Query queryObject = getSession().createQuery(queryString.toString());
		int index = 0;
		for (Object valueObj : likeValues) {
			queryObject.setParameter(index, (valueObj instanceof String) ? "%"
					+ valueObj + "%" : valueObj);
			index++;
		}
		for (Integer valueObj : equalValues) {
			queryObject.setParameter(index, valueObj);
			index++;
		}

		long count = (Long) queryObject.list().iterator().next();
		return Integer.parseInt(count + "");
	}

	/**
	 * 获取数据总数
	 * 
	 * @param hqlWhere
	 *            ＨＱＬ后的ｗｈｅｒｅ约束，不需要加ｗｈｅｒｅ，直接加　ｍｏｄｅｌ．什么　＝　什么
	 * @param pager
	 *            翻页控件
	 * @return
	 */
	public int findByHqlWhereCount(String hqlWhere) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("select count(distinct model) from "
				+ persistentClass.getSimpleName() + " as model where 1=1 ");

		if (hqlWhere != null && hqlWhere.length() > 0) {
			queryString.append(hqlWhere);
		}

		long count = (Long) getSession().createQuery(queryString.toString())
				.list().iterator().next();
		return Integer.parseInt(count + "");
	}

	/**
	 * 获取数据
	 * 
	 * @param hqlWhere
	 *            ＨＱＬ后的ｗｈｅｒｅ约束，不需要加ｗｈｅｒｅ，直接加　ｍｏｄｅｌ．什么　＝　什么
	 * @param pager
	 *            翻页控件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Obj> findByHqlWherePage(String hqlWhere, Pager pager) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("from " + persistentClass.getSimpleName()
				+ " as model where 1=1 ");

		if (hqlWhere != null && hqlWhere.length() > 0) {
			queryString.append(hqlWhere);
		}

		return getSession().createQuery(queryString.toString()).setFirstResult(
				pager.getStartRow()).setMaxResults(pager.getPageSize()).list();
	}
	
	/**
	 * 获取数据
	 * 
	 * @param hqlWhere
	 *            ＨＱＬ后的ｗｈｅｒｅ约束，不需要加ｗｈｅｒｅ，直接加　ｍｏｄｅｌ．什么　＝　什么
	 * @param pager
	 *            翻页控件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Obj> findByHqlWhere(String hqlWhere) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("from " + persistentClass.getSimpleName()
				+ " as model where 1=1 ");

		if (hqlWhere != null && hqlWhere.length() > 0) {
			queryString.append(hqlWhere);
		}
		
		return getSession().createQuery(queryString.toString()).list();
	}
	
	public void flushSession(){
		this.getHibernateTemplate().flush();
	}
	
	public List findBySql(String sql) {
		SQLQuery queryObject = getSession().createSQLQuery(sql);
		
		return queryObject.list();
	}
	
	public List findBySqlPage(String sql,Pager pager){
		SQLQuery queryObject = getSession().createSQLQuery(sql);
		queryObject.setFirstResult(pager.getStartRow()).setMaxResults(pager.getPageSize());
		return queryObject.list();
	}
	
	public List<Obj> findBySql(String sql,Class entity) {
		SQLQuery queryObject = getSession().createSQLQuery(sql);
		return queryObject.addEntity(entity).list();
	}
	
	public List<Object[]> findBySqlObjList(String sql) {
		List<Object[]> objList = new ArrayList<Object[]>();
		SQLQuery queryObject = getSession().createSQLQuery(sql);
		ScrollableResults  r = (ScrollableResults) queryObject.scroll();
		while(r.next()){
			Object[] obj = r.get();
			objList.add(obj);
		}
		
		if(r!=null){
			r.close();
		}
		return objList;
	}
	
	public List<Object[]> findBySqlObjListByPager(String sql,Pager pager) {
		List<Object[]> objList = new ArrayList<Object[]>();
		SQLQuery queryObject = getSession().createSQLQuery(sql);
		queryObject.setFirstResult(pager.getStartRow()).setMaxResults(pager.getPageSize());
		ScrollableResults  r = (ScrollableResults) queryObject.scroll();
		while(r.next()){
			Object[] obj = r.get();
			objList.add(obj);
		}
		
		if(r!=null){
			r.close();
		}
		return objList;
	}
	
	public List<Obj> findBySqlPage(String sql,Class entity,Pager pager){
		SQLQuery queryObject = getSession().createSQLQuery(sql);
		queryObject.addEntity(entity).setFirstResult(pager.getStartRow()).setMaxResults(pager.getPageSize());
		return queryObject.list();
	}
	
	
	public int findBySqlCount(String sql){
		//加  as usertable oracle报错
		//不加  as usertable mysql报错，mysql子查询需要别名
		SQLQuery queryObject = getSession().createSQLQuery("select count(*) from ( "+sql+" ) usertable");
		String count = queryObject.list().iterator().next().toString();
		return Integer.valueOf(count);
	}
	
	
	//执行sql,返回执行成功条数，只适用于update和delete
	public int executeSql(String sql){
		SQLQuery queryObject = getSession().createSQLQuery(sql);
		return queryObject.executeUpdate();
	}

}
