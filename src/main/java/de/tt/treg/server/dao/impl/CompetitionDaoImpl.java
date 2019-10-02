package de.tt.treg.server.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import de.tt.treg.server.dao.CompetitionDao;
import de.tt.treg.server.domain.Competition;
import de.tt.treg.server.domain.CompetitionInformation;

@Repository
public class CompetitionDaoImpl extends AbstractDaoImpl<Competition, Integer>
		implements CompetitionDao {

	protected CompetitionDaoImpl() {
		super(Competition.class);
	}

	public List<Competition> readAll() {
		return getCurrentSession().createCriteria(Competition.class)
				.addOrder(Order.asc("name")).list();
	}

	@Override
	protected String getTableName() {
		return "type";
	}

	@Override
	public List<Competition> getCompetitionByKind(int i) {
		return findByCriteria(Restrictions.eq("kind", i));
	}

	@Override
	protected Criteria beforeLoadCriteria(Criteria criteria) {
		return criteria.addOrder(Order.asc("name"));
	}

	@Override
	public List<CompetitionInformation> getAllCompetitionInformation() {
		Criteria criteria = getCurrentSession().createCriteria(CompetitionInformation.class);
		criteria.add(Restrictions.eq("kind", 1));
		return criteria.list();
	}

}
