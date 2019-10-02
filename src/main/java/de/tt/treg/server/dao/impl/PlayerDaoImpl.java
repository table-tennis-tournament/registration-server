package de.tt.treg.server.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import de.tt.treg.server.controller.helper.DateHelper;
import de.tt.treg.server.dao.PlayerDao;
import de.tt.treg.server.domain.Competition;
import de.tt.treg.server.domain.Player;
import de.tt.treg.server.domain.User;

@Repository
public class PlayerDaoImpl extends AbstractDaoImpl<Player, Integer> implements
		PlayerDao {

	protected PlayerDaoImpl() {
		super(Player.class);
	}

	@Override
	public List<Player> getPlayersByUser(User completeUser) {
		return findByCriteria(Restrictions.like("user", completeUser));
	}

	@Override
	protected String getTableName() {
		return "Player";
	}

	@Override
	public List<Player> getAllPlayersByCompetitionId(int id) {
		Criteria criteria = getPlayersByCompetitionIdCriteria(id);
		return criteria.list();
	}

	@Override
	public List<Player> getRegisteredPlayersByCompetitionId(int id) {
		return getPlayersByCompetitionAndWithWaitingListCriteria(id, 0);
	}

	@Override
	public List<Player> getPlayersOnWaitingListByCompetitionId(int id) {
		return getPlayersByCompetitionAndWithWaitingListCriteria(id, 1);
	}

	private Criteria getPlayersByCompetitionIdCriteria(int id) {
		Criteria criteria = getCurrentSession().createCriteria(Player.class);
		criteria = criteria.createCriteria("competitions");
		criteria.add(Restrictions.not(Restrictions.eq("paid", 100)));
		Criterion competitionIdCriteria = Restrictions.eq(
				"playerCompetitionPk.competitionId", id);
		criteria.add(competitionIdCriteria);
		criteria.addOrder(Order.asc("registrationDate"));
		return criteria;
	}

	private List<Player> getPlayersByCompetitionAndWithWaitingListCriteria(
			int id, int waitingList) {
		Criteria criteria = getPlayersByCompetitionIdCriteria(id);
		Criterion registeredCriterion = Restrictions.eq("isWaitingList",
				waitingList);
		criteria.add(registeredCriterion);
		return criteria.list();
	}

	@Override
	public List<Player> findPlayerByNameAndBirthyear(String firstName,
			String lastName, Date birthDate) {
		Criteria criteria = getCurrentSession().createCriteria(Player.class);
		Criterion firstNameCriteria = Restrictions.ilike("firstName",
				firstName, MatchMode.END);
		Criterion lastNameCriteria = Restrictions.ilike("lastName", lastName,
				MatchMode.END);
		String birthyearCriteriaString = String.format(
				"YEAR(Play_BirthDate)=%s",
				DateHelper.getYearFromDate(birthDate));
		Criterion birthyearCriteria = Restrictions
				.sqlRestriction(birthyearCriteriaString);
		criteria.add(firstNameCriteria);
		criteria.add(lastNameCriteria);
		criteria.add(birthyearCriteria);
		return criteria.list();
	}

	@Override
	public List<Player> findFreeDoublePlayers(
			List<Competition> singleCompetitions, int doubleCompetitionId) {
		Criteria criteria = getCurrentSession().createCriteria(Player.class);
		criteria = criteria.createCriteria("competitions");
		Disjunction or = Restrictions.disjunction();
		for (Competition singleCompetition : singleCompetitions) {
			or.add(Restrictions.eq("playerCompetitionPk.competitionId",
					singleCompetition.getId()));
		}
		criteria.add(or);

		return criteria.list();
	}

	@Override
	public List<Player> getPlayersByInvoiceNumber(int invoiceNumber) {
		return findByCriteria(Restrictions.eqOrIsNull("user.id", invoiceNumber));
	}


	@Override
	public List<Player> getAllPlayersLight() {
		return findByCriteria(Restrictions.isNotNull("user.id"));
	}

	@Override
	public List<Player> getPlayersByUserId(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

}
