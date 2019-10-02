package de.tt.treg.server.domain;

import java.io.Serializable;
import java.util.Date;

public class PlayerCompetition implements IIdentable, Serializable {

	private static final long serialVersionUID = 9059690284397543546L;
	private int id;
	private int paid;
	private int isWaitingList = 0;
	private Date registrationDate;
	private PlayerCompetitionPk playerCompetitionPk;
	private String name;

	public PlayerCompetition() {

	}

	public PlayerCompetition(int id, int paid, Date registrationDate) {
		this(paid, registrationDate, 0, 0);
		this.id = id;
	}

	public PlayerCompetition(int paid, Date registrationDate,
			int competitionId, int playerId) {
		super();
		this.paid = paid;
		this.registrationDate = registrationDate;
		this.playerCompetitionPk = new PlayerCompetitionPk(competitionId,
				playerId);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPaid() {
		return paid;
	}

	public void setPaid(int paid) {
		this.paid = paid;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public PlayerCompetitionPk getPlayerCompetitionPk() {
		return playerCompetitionPk;
	}

	public void setPlayerCompetitionPk(PlayerCompetitionPk playerCompetitionPk) {
		this.playerCompetitionPk = playerCompetitionPk;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIsWaitingList() {
		return isWaitingList;
	}

	public void setIsWaitingList(int isWaitingList) {
		this.isWaitingList = isWaitingList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + isWaitingList;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + paid;
		result = prime
				* result
				+ ((playerCompetitionPk == null) ? 0 : playerCompetitionPk
						.hashCode());
		result = prime
				* result
				+ ((registrationDate == null) ? 0 : registrationDate.hashCode());
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
		PlayerCompetition other = (PlayerCompetition) obj;
		if (id != other.id)
			return false;
		if (isWaitingList != other.isWaitingList)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (paid != other.paid)
			return false;
		if (playerCompetitionPk == null) {
			if (other.playerCompetitionPk != null)
				return false;
		} else if (!playerCompetitionPk.equals(other.playerCompetitionPk))
			return false;
		if (registrationDate == null) {
			if (other.registrationDate != null)
				return false;
		} else if (!registrationDate.equals(other.registrationDate))
			return false;
		return true;
	}

}
