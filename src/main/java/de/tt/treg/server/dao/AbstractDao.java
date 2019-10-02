package de.tt.treg.server.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.Criterion;

import de.tt.treg.server.domain.IIdentable;

public interface AbstractDao<E extends IIdentable, I extends Serializable> {

	E findById(I id);

	void save(E e);

	void delete(E e);

	void delete(int id);

	List<E> findByCriteria(Criterion criterion);
}
