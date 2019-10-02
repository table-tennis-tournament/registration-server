package de.tt.treg.server.dao.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import de.tt.treg.server.dao.DoubleDao;
import de.tt.treg.server.domain.TTDouble;

@Repository
public class DoubleDaoImpl extends AbstractDaoImpl<TTDouble, Integer> implements
		DoubleDao {

	protected DoubleDaoImpl() {
		super(TTDouble.class);
	}

	@Override
	protected String getTableName() {
		return "doubles";
	}

	@Override
	public List<TTDouble> getDoublesByUserId(int userId) {
		return findByCriteria(Restrictions.like("user.id", userId));
	}

}
