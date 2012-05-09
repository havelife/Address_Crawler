package com.crawler.dao.base;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
   
/**
 * 封装Hibernate原生API的DAO泛型基类.
 * 可扩展泛型DAO子类使用
 * 参照SpringSide实现
 * 
 * @author luochao
 *
 * @param <T>
 * @param <PK>
 */
@SuppressWarnings("unchecked")
@Transactional(propagation = Propagation.SUPPORTS, readOnly=true)
public class BaseDao<T, PK extends Serializable>{  

	protected Logger logger = LoggerFactory.getLogger(getClass());  
	
	@Autowired
	@Qualifier("sessionFactory")
	protected SessionFactory sessionFactory;  
	
	protected Class<T> entityClass;  
	      
	public BaseDao() {  
	    
	}  
	
	public BaseDao(SessionFactory sessionFactory, Class<T> entityClass) {        
	    this.sessionFactory = sessionFactory;
	    this.entityClass = entityClass;  
	}  
	
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	

	public void setSessionFactory(SessionFactory sessionFactory){
		this.sessionFactory=sessionFactory;
	}
	
	public Criteria getCriteria() {
		Criteria criteria = getSession().createCriteria(entityClass);
		return criteria;
	}
	
	/**
	 * 根据Criterion条件创建Criteria. 
	 */
	public Criteria createCriteria(final Criterion... criterions) {  
		Criteria criteria = this.getCriteria();
	    for (Criterion c : criterions) {  
	        criteria.add(c);  
	    }  
	    return criteria;  
	}
	
	/**
	 * 根据Order排序条件创建Criteria. 
	 */
	public Criteria createCriteria(final Order... orders) {  
		Criteria criteria = this.getCriteria();
	    for (Order c : orders) {  
	        criteria.addOrder(c);  
	    }  
	    return criteria;  
	}
	
	/**
	 * 根据查询HQL与参数列表创建Query对象.
	 */
	public Query createQuery(final String queryString, final Map<String, Object> values) {
		Assert.hasText(queryString);
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			query.setProperties(values);
		}
		return query;
	}
	
	public Query createQuery(String queryString, Object... values) {  
	    Assert.hasText(queryString);  
	    Query query = getSession().createQuery(queryString);
	    if (values != null) {  
	        for (int i = 0; i < values.length; i++) {  
	        	query.setParameter(i, values[i]);  
	        }  
	    }  
	    return query;  
	}  
	
	@Transactional (propagation = Propagation.REQUIRED, readOnly=false)
	public T save(final T entity) {  
	    Assert.notNull(entity);  
	    logger.info("save entity: {}", entity);
	    return (T) getSession().merge(entity);		//此处使用merge
	}  
	
	@Transactional (propagation = Propagation.REQUIRED, readOnly=false)
	public void delete(final T entity) {  
	    Assert.notNull(entity);  
	    getSession().delete(entity);
	    logger.info("delete entity: {}", entity);  
	}
	
	@Transactional (propagation = Propagation.REQUIRED, readOnly=false)
	public void delete(final PK id) {  
	    Assert.notNull(id);  
	    delete(get(id));
	    logger.debug("delete entity {},id is {}", entityClass.getSimpleName(), id);
	}
	
	/**
	 * 按id获取对象.
	 */
	public T get(final PK id) {  
		return (T) getSession().get(entityClass, id);
	}
	
	public T load(final PK id) {  
		return (T) getSession().load(entityClass, id);
	}
	
	/**
	 * 获取全部对象
	 * 也可return find();
	 */
	public List<T> findAll() {  
	    return findByCriteria();
	}  
	
	/**
	 * 按HQL查询对象列表.
	 * 参数数量可变,按顺序绑定.
	 */
	public <X> List<X> find(final String hql, final Object... values) {
	    return createQuery(hql, values).list();  
	}
	
	public <X> List<X> find(final String hql, final Map<String, Object> values) {
		return createQuery(hql, values).list();
	}
	
	public List<T> find(final Criterion... criterions) {
		return createCriteria(criterions).list();
	}
	
	
	/**
	 * 查询唯一对象.
	 */
	public <X> X findUnique(final String hql, final Object... values) {  
	    return (X) createQuery(hql, values).uniqueResult();  
	}
	
	public <X> X findUnique(final String hql, final Map<String, Object> values) {
		return (X) createQuery(hql, values).uniqueResult();
	}

	public T findUnique(final Criterion... criterions) {
		return (T) createCriteria(criterions).uniqueResult();
	}
	
	/**
	 * 按SQL查询对象列表.
	 * 
	 * @param sql sql语句
	 * @param paramMap 参数Map
	 */
	public List findBySQL(String sql, Map<String,Object> paramMap) {
		return createSQLQuery(sql, paramMap).list();
	}

	public Integer findInt(String hql, Object... values) {  
	    return (Integer) findUnique(hql, values);  
	}  
	
	public Long findLong(String hql, Object... values) {  
	    return (Long) findUnique(hql, values);  
	}  
	
	public List<T> findByCriteria(Criterion... criterion) {  
	    return createCriteria(criterion).list();  
	}  
	
	/**  
	 * 按属性查找对象列表.  
	 */  
	public List<T> findByProperty(final String propertyName, final Object value) {  
	    Assert.hasText(propertyName);  
	    return createCriteria(Restrictions.eq(propertyName, value)).list();  
	}  
	
	public T findUniqueByProperty(final String propertyName, final Object value) {  
	    Assert.hasText(propertyName);  
	    return (T) createCriteria(Restrictions.eq(propertyName, value)).uniqueResult();  
	}  
	
	/**
	 * 根据查询函数与参数列表创建SQLQuery对象,后续可进行更多处理,辅助函数.
	 */
	public Query createSQLQuery(String querySQL, Map<String,Object> parameterMap) {
		Assert.hasText(querySQL);
		Query queryObject = getSession().createSQLQuery(querySQL);
		if (parameterMap != null && parameterMap.size()>0) {
			Iterator<String> keyIterator=parameterMap.keySet().iterator();
			while(keyIterator.hasNext()){
				String key=keyIterator.next();
				Object value=parameterMap.get(key);
				queryObject.setParameter(key, value);
			}
		}
		return queryObject;
	}

	/**
	 * 按id列表获取对象.
	 */
	public List<T> findByIds(List<PK> ids) {
		return find(Restrictions.in(getSessionFactory().getClassMetadata(entityClass).getIdentifierPropertyName(), ids));
	}
	
	/**
	 * 执行HQL进行批量修改/删除操作.
	 */
	public int execHQL(final String hql, final Object... values) {
		return createQuery(hql, values).executeUpdate();
	}

	/**
	 * 执行HQL进行批量修改/删除操作.
	 * @return 更新记录数.
	 */
	public int execHQL(final String hql, final Map<String, Object> values) {
		return createQuery(hql, values).executeUpdate();
	}
	
	/**
	 * 执行SQL进行批量修改/删除操作.
	 * @return 更新记录数.
	 */
	public int execSQL(final String sql, final Map<String, Object> values) {
		return createSQLQuery(sql, values).executeUpdate();
	}
	
}