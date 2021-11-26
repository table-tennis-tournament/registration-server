package de.tt.treg.server.service.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.tt.treg.server.controller.domain.TournamentRegistrationDoubleObject;
import de.tt.treg.server.controller.domain.TournamentRegistrationObject;
import de.tt.treg.server.dao.DoubleDao;
import de.tt.treg.server.dao.PlayerCompetitionDao;
import de.tt.treg.server.dao.PlayerDao;
import de.tt.treg.server.domain.Competition;
import de.tt.treg.server.domain.Player;
import de.tt.treg.server.domain.PlayerCompetition;
import de.tt.treg.server.domain.TTDouble;
import de.tt.treg.server.service.DoubleService;
import de.tt.treg.server.service.impl.helper.CompetitionCache;

@Service("doubleService")
@Transactional(readOnly = true)
public class DoubleServiceImpl implements DoubleService {

	@Autowired
	private DoubleDao doubleDao;

	@Autowired
	private PlayerCompetitionDao playerCompetitionDao;

	@Autowired
	private PlayerDao playerDao;


	@Override
	@Transactional(readOnly = false)
	public TTDouble getDoubleById(int doubleId) {
		return doubleDao.findById(doubleId);
	}

	@Override
	@Transactional(readOnly = false)
	public void insertDouble(TTDouble doubleToInsert) {
		doubleDao.save(doubleToInsert);

	}

	@Override
	@Transactional(readOnly = false)
	public void deleteDouble(TTDouble doubleToDelete) {
		doubleDao.delete(doubleToDelete);
	}

	@Override
	@Transactional(readOnly = false)
	public List<TTDouble> getDoublesByUserId(int userId) {
		return doubleDao.getDoublesByUserId(userId);
	}

	@Override
	@Transactional(readOnly = false)
	public TournamentRegistrationObject insertDoubles(
			List<TournamentRegistrationDoubleObject> doubles,
			List<Player> players) {
		if(doubles.size() == 0){
			return new TournamentRegistrationObject(players, new ArrayList<TournamentRegistrationDoubleObject>());
		}
		List<TournamentRegistrationDoubleObject> resultList = new ArrayList<TournamentRegistrationDoubleObject>();
		for (TournamentRegistrationDoubleObject ttDouble : doubles) {
			TournamentRegistrationDoubleObject newDoubleObject = insertDisciplineForEveryPlayer(ttDouble);
			if (newDoubleObject.getFirstPlayer() == null
					|| newDoubleObject.getSecondPlayer() == null) {
				continue;
			}
			createDoubleObjectAndInsertToDb(newDoubleObject);
			newDoubleObject = setDisciplineNameOnDoubleObject(newDoubleObject);
			resultList.add(newDoubleObject);
		}
		return new TournamentRegistrationObject(
				resultList);
	}

	private TournamentRegistrationDoubleObject setDisciplineNameOnDoubleObject(
			TournamentRegistrationDoubleObject newDoubleObject) {
		Competition doubleCompetition = CompetitionCache.getInstance()
				.getCompetitionById(newDoubleObject.getDoubleId());
		if (doubleCompetition != null) {
			newDoubleObject.setDisciplineName(doubleCompetition.getName());
		}
		return newDoubleObject;

	}

	private void createDoubleObjectAndInsertToDb(
			TournamentRegistrationDoubleObject ttDouble) {
		Player firstPlayer = ttDouble.getFirstPlayer();
		Player secondPlayer = ttDouble.getSecondPlayer();
		if (secondPlayer.getId() == TTDouble.SEARCH_PLAYER_CONST) {
			return;
		}
		TTDouble ttdouble = new TTDouble(ttDouble.getDoubleId(),
				firstPlayer.getId(), secondPlayer.getId(),
				firstPlayer.getFirstName(), secondPlayer.getFirstName(),
				firstPlayer.getLastName(), secondPlayer.getLastName(),
				firstPlayer.getUser());
		insertDouble(ttdouble);
	}

	private TournamentRegistrationDoubleObject insertDisciplineForEveryPlayer(
			TournamentRegistrationDoubleObject ttDouble) {
		ttDouble.setFirstPlayer(insertDoubleInTypePerPlayer(
				ttDouble.getFirstPlayer(), ttDouble.getDoubleId()));
		ttDouble.setSecondPlayer(insertDoubleInTypePerPlayer(
				ttDouble.getSecondPlayer(), ttDouble.getDoubleId()));
		return ttDouble;
	}

	private Player insertDoubleInTypePerPlayer(Player playerToInsert,
			int doubleId) {
		if (playerToInsert.getId() > 0) {
			insertInTypePerPlayer(
					playerToInsert.getId(), doubleId);
			return playerDao.findById(playerToInsert.getId());
		}

		List<Player> players = playerDao.findPlayerByNameAndBirthyear(
				playerToInsert.getFirstName(), playerToInsert.getLastName(),
				playerToInsert.getBirthDate());
		if (players.size() > 0) {
			Player playerToCheck = players.get(0);
			PlayerCompetition competition = insertInTypePerPlayer(
					playerToCheck.getId(), doubleId);
			playerToCheck.addCompetition(competition);
			return playerToCheck;
		}
		return null;
	}

	private PlayerCompetition insertInTypePerPlayer(int id, int doubleId) {
		PlayerCompetition playerCompetition = new PlayerCompetition(0,
				Timestamp.from(Instant.now()), doubleId, id);
		playerCompetitionDao.save(playerCompetition);
		return playerCompetition;

	}

}
