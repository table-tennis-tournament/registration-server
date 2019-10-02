package de.tt.treg.server.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import de.tt.treg.server.dao.AbstractDao;
import de.tt.treg.server.domain.IIdentable;

public abstract class AbstractDaoImpl<E extends IIdentable, I extends Serializable>
		implements AbstractDao<E, I> {

	protected Class<E> entityClass;

	protected AbstractDaoImpl(Class<E> entityClass) {
		this.entityClass = entityClass;
	}

	@Autowired
	protected SessionFactory sessionFactory;

	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	public E findById(I id) {
		return (E) getCurrentSession().get(entityClass, id);
	}

	public void save(E e) {
		getCurrentSession().saveOrUpdate(e);
	}

	public void update(E e) {
		getCurrentSession().update(e);
	}

	@Transactional(readOnly = false)
	public void delete(E e) {
		getCurrentSession().delete(e);
	}

	public List<E> findByCriteria(Criterion criterion) {
		Criteria criteria = getCurrentSession().createCriteria(entityClass);
		criteria.add(criterion);
		criteria = beforeLoadCriteria(criteria);
		return criteria.list();

	}

	protected Criteria beforeLoadCriteria(Criteria criteria) {
		return criteria;
	}

	public List<E> findByCriterias(List<Criterion> criterion) {
		Criteria criteria = getCurrentSession().createCriteria(entityClass);
		for (Criterion singleCriteria : criterion) {
			criteria.add(singleCriteria);
		}
		return criteria.list();
	}

	@Override
	public void delete(int id) {
		getCurrentSession().createQuery(
				String.format("Delete FROM %s where id = %d", getTableName(),
						id)).executeUpdate();
	}

	protected abstract String getTableName();
}
