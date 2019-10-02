package de.tt.treg.server.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import de.tt.treg.server.dao.PlayerCompetitionDao;
import de.tt.treg.server.domain.Competition;
import de.tt.treg.server.domain.Player;
import de.tt.treg.server.domain.PlayerCompetition;

@Repository
public class PlayerCompetitionDaoImpl extends
		AbstractDaoImpl<PlayerCompetition, Integer> implements
		PlayerCompetitionDao {

	protected PlayerCompetitionDaoImpl() {
		super(PlayerCompetition.class);
	}

	@Override
	public List<PlayerCompetition> findByCompetition(Competition competition) {
		return findByCriteria(Restrictions.like(
				"playerCompetitionPk.competitionId", competition.getId()));
	}

	@Override
	protected Criteria beforeLoadCriteria(Criteria criteria) {
		criteria.addOrder(Order.asc("registrationDate"));
		return criteria;
	}

	@Override
	protected String getTableName() {
		return "typeperplayer";
	}

	@Override
	public PlayerCompetition findByPlayer(Player player) {
		return findByCriteria(
				Restrictions.like("playerCompetitionPk.playerId",
						player.getId())).get(0);
	}

	public long getCountOfRegisteredParticipants() {
		return (long) getCurrentSession()
				.createCriteria(PlayerCompetition.class)
				.setProjection(Projections.rowCount()).uniqueResult();
	}

	@Override
	public int setPayedForPlayers(List<Integer> _result) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String resultList = "0";
		for (Integer value : _result) {
			resultList+= ", "+value.toString();
		}
		String hqlUpdate = "update PlayerCompetition set paid = 1 where playerCompetitionPk.playerId IN ("+resultList+")";
		int updatedEntities = session.createQuery(hqlUpdate)
		        .executeUpdate();
		tx.commit();
		session.close();
		return updatedEntities;
	}

	
}
