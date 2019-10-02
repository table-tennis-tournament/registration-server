package de.tt.treg.server.domain;

import java.util.Set;

public class Competition implements IIdentable {

	private int id;

	private String name;

	private int maxAge;

	private int minAge;

	private double price;

	private int maxParticipants;

	private int kind;

	private Set<PlayerCompetition> players;

	public Competition() {
	}

	public Competition(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMaxAge() {
		return maxAge;
	}

	public void setMaxAge(int maxAge) {
		this.maxAge = maxAge;
	}

	public int getMinAge() {
		return minAge;
	}

	public void setMinAge(int minAge) {
		this.minAge = minAge;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getMaxParticipants() {
		return maxParticipants;
	}

	public void setMaxParticipants(int maxParticipants) {
		this.maxParticipants = maxParticipants;
	}

	public Set<PlayerCompetition> getPlayers() {
		return players;
	}

	public void setPlayers(Set<PlayerCompetition> players) {
		this.players = players;
	}

	public int getKind() {
		return kind;
	}

	public void setKind(int kind) {
		this.kind = kind;
	}

}
