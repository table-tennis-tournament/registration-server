package de.tt.treg.server.domain.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.tt.treg.server.controller.helper.DateHelper;
import de.tt.treg.server.domain.Player;

public class PlayerMap {

	private Map<String, Player> playerMap;

	public PlayerMap(List<Player> playersToMap) {
		playerMap = new HashMap<String, Player>();
		for (Player player : playersToMap) {
			if(player.getId()> 0){
				continue;
			}
			playerMap.put(getPlayerKey(player), player);
		}
	}

	public Player getPlayerObject(Player player) {
		String playerKey = getPlayerKey(player);
		if (playerMap.containsKey(playerKey)) {
			return playerMap.get(playerKey);
		}
		return null;
	}

	public void updatePlayerObject(Player playerToUpdate) {
		playerMap.put(getPlayerKey(playerToUpdate), playerToUpdate);
	}

	public boolean contains(Player player) {
		return playerMap.containsKey(getPlayerKey(player));
	}

	private String getPlayerKey(Player player) {
		return player.getFirstName() + player.getLastName()
				+ DateHelper.getYearFromDate(player.getBirthDate());
	}

	public List<Player> getPlayersAsList() {
		return new ArrayList<Player>(playerMap.values());
	}
}
