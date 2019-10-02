package de.tt.treg.server.domain;

import javax.persistence.Column;

public class Team implements IIdentable {

	private int id;
	private String name;
	private String shortName;
	private String organization;

	public Team() {

	}

	public Team(String name, String shortName, String organization) {
		super();
		this.name = name;
		this.shortName = shortName;
		this.organization = organization;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "Club_Name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "Club_ShortName")
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	@Column(name = "Club_Verband")
	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

}
