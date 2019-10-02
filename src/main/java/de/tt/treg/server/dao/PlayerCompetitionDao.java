package de.tt.treg.server.dao;

import java.util.List;

import de.tt.treg.server.domain.Competition;
import de.tt.treg.server.domain.Player;
import de.tt.treg.server.domain.PlayerCompetition;

public interface PlayerCompetitionDao extends
		AbstractDao<PlayerCompetition, Integer> {

	List<PlayerCompetition> findByCompetition(Competition competition);

	PlayerCompetition findByPlayer(Player player);

	long getCountOfRegisteredParticipants();

	int setPayedForPlayers(List<Integer> _result);

}
