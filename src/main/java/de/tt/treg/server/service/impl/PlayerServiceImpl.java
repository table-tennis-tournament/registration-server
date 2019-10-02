package de.tt.treg.server.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.tt.treg.server.dao.AbstractDao;
import de.tt.treg.server.dao.PlayerCompetitionDao;
import de.tt.treg.server.dao.PlayerDao;
import de.tt.treg.server.dao.TeamDao;
import de.tt.treg.server.dao.UserDao;
import de.tt.treg.server.domain.Competition;
import de.tt.treg.server.domain.IIdentable;
import de.tt.treg.server.domain.Player;
import de.tt.treg.server.domain.PlayerCompetition;
import de.tt.treg.server.domain.PlayerCompetitionPk;
import de.tt.treg.server.domain.Team;
import de.tt.treg.server.domain.User;
import de.tt.treg.server.service.CompetitionService;
import de.tt.treg.server.service.PlayerService;
import de.tt.treg.server.service.impl.helper.CompetitionCache;
import de.tt.treg.server.service.impl.waitingList.IWaitingListCriteria;
import de.tt.treg.server.service.impl.waitingList.PerCompetitionWaitingListCriteria;

@Service("playerService")
@Transactional(readOnly = true)
public class PlayerServiceImpl implements PlayerService {

	@Autowired
	private PlayerDao playerDao;
	@Autowired
	private TeamDao teamDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private PlayerCompetitionDao playerCompetitionDao;

	@Autowired
	private CompetitionService competitionService;

	private IWaitingListCriteria waitingListCriteria;

	@Override
	@Transactional(readOnly = false)
	public Player getPlayerById(int id) {
		return playerDao.findById(id);
	}

	@Override
	@Transactional(readOnly = false)
	public void insertPlayer(Player playerToInsert) {
		Set<PlayerCompetition> existingCompetitions = getOnlyExistingCompetitionsInDatabase(playerToInsert
				.getCompetitions());
		if (existingCompetitions.size() == 0) {
			return;
		}
		playerToInsert.setCompetitions(existingCompetitions);
		Team team = playerToInsert.getTeam();
		if (team.getId() < 1) {
			Team newTeam = createTeamIfNecessary(team);
			playerToInsert.setTeam(newTeam);
		}
		setWaitingListCriteria();
		createUserIfNecessary(playerToInsert);
		playerDao.save(playerToInsert);
		savePlayerCompetitionsForPlayer(playerToInsert);
	}

	private void setWaitingListCriteria() {
		if (waitingListCriteria == null) {
			waitingListCriteria = new PerCompetitionWaitingListCriteria(
					playerCompetitionDao);
		}
	}

	private void savePlayerCompetitionsForPlayer(Player playerToInsert) {
		for (PlayerCompetition competition : playerToInsert.getCompetitions()) {
			PlayerCompetitionPk pk = competition.getPlayerCompetitionPk();
			pk.setPlayerId(playerToInsert.getId());
			competition.setPlayerCompetitionPk(pk);
			int isWaitingList = getCalculatedWaitingListValue(pk
					.getCompetitionId());
			competition.setIsWaitingList(isWaitingList);
			competition.setPaid(0);
			playerCompetitionDao.save(competition);
		}
	}

	private int getCalculatedWaitingListValue(int competitionId) {
		if (waitingListCriteria
				.isWaitingListActiveForCompetition(competitionId)) {
			return 1;
		}
		return 0;
	}

	@Override
	@Transactional(readOnly = false)
	public List<Player> getPlayersByUser(User user) {
		User completeUser = userDao.getUserByUserNameAndPasswort(
				user.getUserName(), MD5Generator.md5(user.getPasswort()));
		if (completeUser == null)
			return new ArrayList<Player>();
		return playerDao.getPlayersByUser(completeUser);
	}

	private Set<PlayerCompetition> getOnlyExistingCompetitionsInDatabase(
			Set<PlayerCompetition> set) {
		Set<PlayerCompetition> result = new HashSet<PlayerCompetition>();
		for (PlayerCompetition competition : set) {
			PlayerCompetitionPk pk = competition.getPlayerCompetitionPk();
			if (pk == null) {
				continue;
			}
			Competition databaseCompetition = CompetitionCache.getInstance()
					.getCompetitionById(pk.getCompetitionId());
			if (databaseCompetition != null) {
				result.add(competition);
			}
		}
		return result;
	}

	private void createUserIfNecessary(Player playerToInsert) {
		if (isObjectNOTinDatabase(playerToInsert.getUser().getId(), userDao)) {
			userDao.save(playerToInsert.getUser());
		}
	}

	private Team createTeamIfNecessary(Team teamToTest) {
		Team existingTeam = teamDao.findByName(teamToTest.getName());
		if (existingTeam == null) {
			teamDao.save(teamToTest);
			return teamToTest;
		}
		return existingTeam;
	}

	private boolean isObjectNOTinDatabase(int id,
			AbstractDao<? extends IIdentable, Integer> dao) {
		return dao.findById(id) == null;
	}

	@Override
	public List<Player> getRegisteredPlayersByCompetitionId(int competitionId) {
		return playerDao.getRegisteredPlayersByCompetitionId(competitionId);
	}

	@Override
	public List<Player> getWaitingListForCompetitionId(int competitionId) {
		return playerDao.getPlayersOnWaitingListByCompetitionId(competitionId);
	}

	@Override
	@Transactional(readOnly = false)
	public void deletePlayerById(int id) {
		Player player = playerDao.findById(id);
		if (player == null) {
			return;
		}
		PlayerCompetition playerComp = playerCompetitionDao
				.findByPlayer(player);
		playerCompetitionDao.delete(playerComp);
		playerDao.delete(id);
	}

	@Override
	public List<Player> getFreeDoublePlayersForCompetitionId(int doubleCompetitionId) {
		List<Competition> singleCompetitions = CompetitionCache.getInstance().getSingleCompetitionToDouble(doubleCompetitionId);
		return playerDao.findFreeDoublePlayers(singleCompetitions, doubleCompetitionId);
	}

	@Override
	public List<Player> getPlayersByInvoiceNumber(int invoiceNumber) {
		return playerDao.getPlayersByInvoiceNumber(invoiceNumber);
	}

	@Override
	public int setPayedByPlayerIds(List<Integer> _result) {
		return playerCompetitionDao.setPayedForPlayers(_result);
	}

	@Override
	public List<Player> getAllPlayersLight() {
		return playerDao.getAllPlayersLight();
	}

	@Override
	public List<Player> getPlayersByUserId(int userId) {
		return playerDao.getPlayersByUserId(userId);
	}

}
