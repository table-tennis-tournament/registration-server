package de.tt.treg.server.dao.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import de.tt.treg.server.dao.TeamDao;
import de.tt.treg.server.domain.Team;

@Repository
public class TeamDaoImpl extends AbstractDaoImpl<Team, Integer> implements
		TeamDao {

	protected TeamDaoImpl() {
		super(Team.class);
	}

	public List<Team> readAll() {
		return getCurrentSession().createCriteria(Team.class).list();
	}

	@Override
	public Team findByName(String name) {
		List<Team> result = findByCriteria(Restrictions.like("name", name));
		if (result.size() > 0) {
			return result.get(0);
		}
		return null;
	}

	@Override
	protected String getTableName() {
		return "club";
	}

}
