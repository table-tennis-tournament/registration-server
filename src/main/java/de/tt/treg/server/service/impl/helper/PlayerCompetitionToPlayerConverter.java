package de.tt.treg.server.service.impl.helper;

import java.util.ArrayList;
import java.util.List;

import de.tt.treg.server.domain.Player;

public class PlayerCompetitionToPlayerConverter {

	public List<Player> getWaitingListPlayers(List<Player> result,
			int maxCountOfPlayers) {
		if (waitListIsNOTActive(maxCountOfPlayers, result)) {
			return new ArrayList<Player>();
		}
		List<Player> players = new ArrayList<Player>();
		for (int index = maxCountOfPlayers; index < result.size(); index++) {
			Player player = result.get(index);
			players.add(player);
		}
		return players;
	}

	private boolean waitListIsNOTActive(int maxCountOfPlayers,
			List<Player> result) {
		return maxCountOfPlayers <= 0 || result.size() == 0;
	}
}
