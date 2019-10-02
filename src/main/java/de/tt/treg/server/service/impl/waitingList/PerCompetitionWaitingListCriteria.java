package de.tt.treg.server.service.impl.waitingList;

import java.util.List;

import de.tt.treg.server.dao.PlayerCompetitionDao;
import de.tt.treg.server.domain.Competition;
import de.tt.treg.server.domain.PlayerCompetition;
import de.tt.treg.server.service.impl.helper.CompetitionCache;

public class PerCompetitionWaitingListCriteria implements IWaitingListCriteria {

	private PlayerCompetitionDao playerCompetitionDao;

	public PerCompetitionWaitingListCriteria(PlayerCompetitionDao playerCompDao) {
		this.playerCompetitionDao = playerCompDao;
	}

	@Override
	public boolean isWaitingListActiveForCompetition(int competitionId) {
		Competition competition = CompetitionCache.getInstance()
				.getCompetitionById(competitionId);
		if (competition.getMaxParticipants() <= 0) {
			return false;
		}
		List<PlayerCompetition> playerCompetitionEntries = playerCompetitionDao
				.findByCompetition(competition);
		if (playerCompetitionEntries.size() >= competition.getMaxParticipants()) {
			return true;
		}
		return false;
	}
}
