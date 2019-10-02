package de.tt.treg.server.controller.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.tt.treg.server.domain.Player;
import de.tt.treg.server.domain.PlayerCompetition;

public class WaitingListPlayerSeperator {

	public static final String PLAYER_LIST = "playerList";
	public static final String WAITING_LIST = "waitingList";

	public Map<String, List<Player>> getPlayersAndWaitingListLists(
			List<Player> players) {
		Map<String, List<Player>> resultList = new HashMap<String, List<Player>>();
		List<Player> playerList = new ArrayList<Player>();
		List<Player> waitingList = new ArrayList<Player>();
		for (Player player : players) {
			Set<PlayerCompetition> competitionPlayerList = new HashSet<PlayerCompetition>();
			Set<PlayerCompetition> competitionWaitingList = new HashSet<PlayerCompetition>();
			for (PlayerCompetition competition : player.getCompetitions()) {
				if (competition.getIsWaitingList() == 1) {
					competitionWaitingList.add(competition);
					continue;
				}
				competitionPlayerList.add(competition);
			}
			playerList = addPlayerToListIfCompetitionsNotEmpty(playerList,
					player, competitionPlayerList);
			waitingList = addPlayerToListIfCompetitionsNotEmpty(waitingList,
					player, competitionWaitingList);
		}
		resultList = addPlayerListToMapIfPlayersNotEmpty(resultList,
				playerList, PLAYER_LIST);
		resultList = addPlayerListToMapIfPlayersNotEmpty(resultList,
				waitingList, WAITING_LIST);
		return resultList;
	}

	private Map<String, List<Player>> addPlayerListToMapIfPlayersNotEmpty(
			Map<String, List<Player>> resultList, List<Player> players,
			String key) {
		if (players.size() > 0) {
			resultList.put(key, players);
		}
		return resultList;
	}

	private List<Player> addPlayerToListIfCompetitionsNotEmpty(
			List<Player> playerList, Player player,
			Set<PlayerCompetition> competitions) {

		if (competitions.size() > 0) {
			Player newPlayer = new Player(player.getFirstName(),
					player.getLastName(), player.getBirthDate(), competitions,
					player.getUser(), player.getTeam());
			newPlayer.setPayment(player.getPayment());
			newPlayer.setPrepayment(player.getPrepayment());
			playerList.add(newPlayer);
		}
		return playerList;
	}

}
