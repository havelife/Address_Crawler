package com.crawler.dao.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.transform.ResultTransformer;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.crawler.util.ReflectionUtils;

/**
 * ��չ���ܰ�����ҳ��ѯ,�����Թ��������б��ѯ.
 * ����SpringSideʵ��
 * 
 * @param <T> DAO�����Ķ�������
 * @param <PK> ��������
 * 
 * @author luochao
 * @version 1.0.0
 */
@SuppressWarnings("unchecked")
@Transactional(propagation = Propagation.SUPPORTS, readOnly=true)
public class GenericDao<T, PK extends Serializable> extends BaseDao<T, PK> {
	
	public GenericDao() {
		super();
	}

	/**
	 * ����ʡ��Dao��, Service��ֱ��ʹ��ͨ��HibernateDao�Ĺ��캯��.
	 * �ڹ��캯���ж����������Class.
	 * eg.
	 * HibernateDao<User, Long> userDao = new HibernateDao<User, Long>(sessionFactory, User.class);
	 */
	public GenericDao(final SessionFactory sessionFactory, final Class<T> entityClass) {
		super(sessionFactory, entityClass);
	}

	//-- ��ҳ��ѯ���� --//
	/**
	 * ��ҳ��ȡȫ������.
	 */
	public Page<T> findAll(final Page<T> page) {
		return findPage(page);
	}

	
	
	
	/**
	 * ��HQL��ҳ��ѯ.
	 * 
	 * @param page ��ҳ����.��֧�����е�orderBy����.
	 * @param hql hql���.
	 * @param values �����ɱ�Ĳ�ѯ����,��˳���.
	 * 
	 * @return ��ҳ��ѯ���, �������б?���в�ѯʱ�Ĳ���.
	 */
	public Page<T> findPage(final Page<T> page, final String hql, final Object... values) {
		Assert.notNull(page);

		Query q = createQuery(hql, values);

		if (page.isAutoCount()) {
			long totalCount = countHqlResult(hql, values);
			page.setTotalCount(totalCount);
		}

		setPageParameter(q, page);
		List result = q.list();
		page.setResult(result);
		return page;
	}

	/**
	 * ��HQL��ҳ��ѯ.
	 * 
	 * @param page ��ҳ����.
	 * @param hql hql���.
	 * @param values �������,����ư�.
	 * 
	 * @return ��ҳ��ѯ���, �������б?���в�ѯʱ�Ĳ���.
	 */
	public Page<T> findPage(final Page<T> page, final String hql, final Map<String, Object> values) {
		Assert.notNull(page);

		Query q = createQuery(hql, values);

		if (page.isAutoCount()) {
			long totalCount = countHqlResult(hql, values);
			page.setTotalCount(totalCount);
		}

		setPageParameter(q, page);
		
	    page.setResult(q.list());
	    
		return page;
	}

	/**
	 * ��Criteria��ҳ��ѯ.
	 * 
	 * @param page ��ҳ����.
	 * @param criterions �����ɱ��Criterion.
	 * 
	 * @return ��ҳ��ѯ���.�������б?���в�ѯʱ�Ĳ���.
	 */
	public Page<T> findPage(final Page<T> page, final Criterion... criterions) {
		Assert.notNull(page);

		Criteria c = createCriteria(criterions);

		if (page.isAutoCount()) {
			int totalCount = countCriteriaResult(c);
			page.setTotalCount(totalCount);
		}

		setPageParameter(c, page);
		List result = c.list();
		page.setResult(result);
		return page;
	}

	/**
	 * ��Criteria��ҳ��ѯ.
	 * ����Criteria������ѯ
	 *
	 * @author luochao
	 * @version 1.0.0
	 */
	public Page<T> findPage(final Page<T> page, final Criteria criteria) {
		Assert.notNull(page);
		if (page.isAutoCount()) {
			int totalCount = countCriteriaResult(criteria);
			page.setTotalCount(totalCount);
		}

		setPageParameter(criteria, page);
		List<T> result = criteria.list();
		page.setResult(result);
		return page;
	}
	
	
	
	/**
	 * ���÷�ҳ����Query����,������.
	 */
	protected Query setPageParameter(final Query q, final Page<T> page) {
		//hibernate��firstResult����Ŵ�0��ʼ
		q.setFirstResult(page.getFirst() - 1);
		q.setMaxResults(page.getPageSize());
		return q;
	}

	/**
	 * ���÷�ҳ����Criteria����,������.
	 */
	protected Criteria setPageParameter(final Criteria c, final Page<T> page) {
		//hibernate��firstResult����Ŵ�0��ʼ
		c.setFirstResult(page.getFirst() - 1);
		c.setMaxResults(page.getPageSize());

		if (page.isOrderBySetted()) {
			String[] orderByArray = StringUtils.split(page.getOrderBy(), ',');
			String[] orderArray = StringUtils.split(page.getOrder(), ',');

			Assert.isTrue(orderByArray.length == orderArray.length, "��ҳ�������������,�����ֶ���������ĸ������");

			for (int i = 0; i < orderByArray.length; i++) {
				if (Page.ASC.equals(orderArray[i])) {
					c.addOrder(Order.asc(orderByArray[i]));
				} else {
					c.addOrder(Order.desc(orderByArray[i]));
				}
			}
		}
		return c;
	}

	
	
	//-- ���������� ���ڷ�ҳ --//
	
	/**
	 * ִ��count��ѯ��ñ���Hql��ѯ���ܻ�õĶ�������.
	 * 
	 * ������ֻ���Զ�����򵥵�hql���,���ӵ�hql��ѯ�����б�дcount����ѯ.
	 */
	public long countHqlResult(final String hql, final Object... values) {
		String fromHql = hql;
		//select�Ӿ���order by�Ӿ��Ӱ��count��ѯ,���м򵥵��ų�.
		fromHql = "from " + StringUtils.substringAfter(fromHql, "from");
		fromHql = StringUtils.substringBefore(fromHql, "order by");

		String countHql = "select count(*) " + fromHql;

		try {
			Long count = findUnique(countHql, values);
			return count;
		} catch (Exception e) {
			throw new RuntimeException("hql can't be auto count, hql is:" + countHql, e);
		}
	}

	/**
	 * ִ��count��ѯ��ñ���Hql��ѯ���ܻ�õĶ�������.
	 * 
	 * ������ֻ���Զ�����򵥵�hql���,���ӵ�hql��ѯ�����б�дcount����ѯ.
	 */
	public long countHqlResult(final String hql, final Map<String, Object> values) {
		String fromHql = hql;
		//select�Ӿ���order by�Ӿ��Ӱ��count��ѯ,���м򵥵��ų�.
		fromHql = "from " + StringUtils.substringAfter(fromHql, "from");
		fromHql = StringUtils.substringBefore(fromHql, "order by");

		String countHql = "select count(*) " + fromHql;

		try {
			Long count = findUnique(countHql, values);
			return count;
		} catch (Exception e) {
			throw new RuntimeException("hql can't be auto count, hql is:" + countHql, e);
		}
	}

	/**
	 * ִ��count��ѯ��ñ���Criteria��ѯ���ܻ�õĶ�������.
	 */
	public int countCriteriaResult(final Criteria c) {
		CriteriaImpl impl = (CriteriaImpl) c;

		// �Ȱ�Projection��ResultTransformer��OrderByȡ����,������ߺ���ִ��Count����
		Projection projection = impl.getProjection();
		ResultTransformer transformer = impl.getResultTransformer();

		List<CriteriaImpl.OrderEntry> orderEntries = null;
		try {
			orderEntries = (List) ReflectionUtils.getFieldValue(impl, "orderEntries");
			ReflectionUtils.setFieldValue(impl, "orderEntries", new ArrayList());
		} catch (Exception e) {
			logger.error("�������׳����쳣:{}", e.getMessage());
		}

		// ִ��Count��ѯ
		int totalCount = (Integer) c.setProjection(Projections.rowCount()).uniqueResult();

		// ��֮ǰ��Projection,ResultTransformer��OrderBy�����������ȥ
		c.setProjection(projection);

		if (projection == null) {
			c.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}
		if (transformer != null) {
			c.setResultTransformer(transformer);
		}
		try {
			ReflectionUtils.setFieldValue(impl, "orderEntries", orderEntries);
		} catch (Exception e) {
			logger.error("�������׳����쳣:{}", e.getMessage());
		}

		return totalCount;
	}

	
	//-- ���Թ�������(PropertyFilter)��ѯ���� ��--//

	/**
	 * �ж϶��������ֵ����ݿ����Ƿ�Ψһ.
	 * 
	 * ���޸Ķ�����龰��,����������޸ĵ�ֵ(value)��������ԭ����ֵ(orgValue)�����Ƚ�.
	 */
	public boolean isPropertyUnique(final String propertyName, final Object newValue, final Object oldValue) {
		if (newValue == null || newValue.equals(oldValue))
			return true;
		Object object = findUniqueByProperty(propertyName, newValue);
		return (object == null);
	}
}