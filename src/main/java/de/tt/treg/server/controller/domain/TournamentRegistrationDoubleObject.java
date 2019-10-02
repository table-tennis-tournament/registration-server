package de.tt.treg.server.controller.domain;

import de.tt.treg.server.domain.Player;

public class TournamentRegistrationDoubleObject {

	private Player firstPlayer;

	private Player secondPlayer;

	private int doubleId;

	private String disciplineName;

	public TournamentRegistrationDoubleObject() {

	}

	public TournamentRegistrationDoubleObject(Player firstPlayer,
			Player secondPlayer, int doubleId) {
		this.firstPlayer = firstPlayer;
		this.secondPlayer = secondPlayer;
		this.doubleId = doubleId;
	}

	public Player getFirstPlayer() {
		return firstPlayer;
	}

	public void setFirstPlayer(Player firstPlayer) {
		this.firstPlayer = firstPlayer;
	}

	public Player getSecondPlayer() {
		return secondPlayer;
	}

	public void setSecondPlayer(Player secondPlayer) {
		this.secondPlayer = secondPlayer;
	}

	public int getDoubleId() {
		return doubleId;
	}

	public void setDoubleId(int doubleId) {
		this.doubleId = doubleId;
	}

	public String getDisciplineName() {
		return disciplineName;
	}

	public void setDisciplineName(String disciplineName) {
		this.disciplineName = disciplineName;
	}

}
