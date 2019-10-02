package de.tt.treg.server.controller.domain;

import java.util.ArrayList;
import java.util.List;

import de.tt.treg.server.domain.Player;

public class TournamentRegistrationObject {

	private List<Player> players;

	private List<TournamentRegistrationDoubleObject> doubles;

	public TournamentRegistrationObject() {

	}

	public TournamentRegistrationObject(
			List<TournamentRegistrationDoubleObject> doubles) {
		setPlayersFromDoubles(doubles);
		this.doubles = doubles;
	}
	
	public TournamentRegistrationObject(List<Player> players,
			List<TournamentRegistrationDoubleObject> doubles) {
		this.players = players;
		this.doubles = doubles;
	}

	private void setPlayersFromDoubles(List<TournamentRegistrationDoubleObject> doubles2) {
		players = new ArrayList<Player>();
		for(TournamentRegistrationDoubleObject doubleObject : doubles2){
			players.add(doubleObject.getFirstPlayer());
			players.add(doubleObject.getSecondPlayer());
		}
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public List<TournamentRegistrationDoubleObject> getDoubles() {
		return doubles;
	}

	public void setDoubles(List<TournamentRegistrationDoubleObject> doubles) {
		this.doubles = doubles;
	}

}
