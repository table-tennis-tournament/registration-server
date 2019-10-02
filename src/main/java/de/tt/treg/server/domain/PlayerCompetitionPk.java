package de.tt.treg.server.domain;

import java.io.Serializable;

public class PlayerCompetitionPk implements Serializable {

	private static final long serialVersionUID = 5055711079596065057L;
	private int playerId;
	private int competitionId;

	public PlayerCompetitionPk() {

	}

	public PlayerCompetitionPk(int competitionId, int playerId) {
		this.playerId = playerId;
		this.competitionId = competitionId;
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public int getCompetitionId() {
		return competitionId;
	}

	public void setCompetitionId(int competitionId) {
		this.competitionId = competitionId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + competitionId;
		result = prime * result + playerId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlayerCompetitionPk other = (PlayerCompetitionPk) obj;
		if (competitionId != other.competitionId)
			return false;
		if (playerId != other.playerId)
			return false;
		return true;
	}

}
