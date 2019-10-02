package de.tt.treg.server.service.impl.waitingList;

import de.tt.treg.server.dao.PlayerCompetitionDao;

public class PerTournamentWaitingListCriteria implements IWaitingListCriteria {

	private PlayerCompetitionDao playerCompetitionDao;
	private int maximumRegistrationCount;

	public PerTournamentWaitingListCriteria(
			PlayerCompetitionDao playerCompetitionDao, int maxRegistrationCount) {
		this.playerCompetitionDao = playerCompetitionDao;
		this.maximumRegistrationCount = maxRegistrationCount;
	}

	@Override
	public boolean isWaitingListActiveForCompetition(int competitionId) {
		long currentCount = playerCompetitionDao
				.getCountOfRegisteredParticipants();
		if (currentCount >= maximumRegistrationCount) {
			return true;
		}
		return false;
	}
}
