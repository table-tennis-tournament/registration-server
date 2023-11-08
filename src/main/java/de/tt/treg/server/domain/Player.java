package de.tt.treg.server.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import de.tt.treg.server.domain.json.JsonDateSerializer;

public class Player implements IIdentable {

	private int id;
	private String firstName;
	private String lastName;
	private Date birthDate;
	private Set<PlayerCompetition> competitions = new HashSet<PlayerCompetition>();
	private User user;
	private Team team;
	private double prepayment;
	private double payment;
	private String firstNameShort = "";

	public Player() {
	}

	public Player(String firstName, String lastName, Date birthDate,
			Set<PlayerCompetition> competitions, User user, Team team) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.competitions = competitions;
		this.user = user;
		this.team = team;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getBirthDate() {

		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Set<PlayerCompetition> getCompetitions() {
		return competitions;
	}

	public void setCompetitions(Set<PlayerCompetition> competitions) {
		this.competitions = competitions;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public double getPrepayment() {
		return prepayment;
	}

	public void setPrepayment(double prepayment) {
		this.prepayment = prepayment;
	}

	public double getPayment() {
		return payment;
	}

	public void setPayment(double payment) {
		this.payment = payment;
	}

	public void addCompetition(PlayerCompetition competitionToAdd) {
		competitions.add(competitionToAdd);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((birthDate == null) ? 0 : birthDate.hashCode());
		result = prime * result
				+ ((competitions == null) ? 0 : competitions.hashCode());
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + id;
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		long temp;
		temp = Double.doubleToLongBits(payment);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(prepayment);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((team == null) ? 0 : team.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		Player other = (Player) obj;
		if (birthDate == null) {
			if (other.birthDate != null)
				return false;
		} else if (!birthDate.equals(other.birthDate))
			return false;
		if (competitions == null) {
			if (other.competitions != null)
				return false;
		} else if (!competitions.equals(other.competitions))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id != other.id)
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (Double.doubleToLongBits(payment) != Double
				.doubleToLongBits(other.payment))
			return false;
		if (Double.doubleToLongBits(prepayment) != Double
				.doubleToLongBits(other.prepayment))
			return false;
		if (team == null) {
			if (other.team != null)
				return false;
		} else if (!team.equals(other.team))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	public String getFirstNameShort() {
		return firstName;
	}

	public void setFirstNameShort(String firstNameShort) {
		this.firstNameShort = firstNameShort;
	}

}
