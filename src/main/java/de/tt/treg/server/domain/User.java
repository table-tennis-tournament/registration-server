package de.tt.treg.server.domain;

import java.util.Date;

public class User implements IIdentable {

	private int id;
	private String userName;
	private String passwort;
	private Date registrationDate;
	private int isAdmin = 0;

	public User() {
	}

	public User(String userName, String passwort, Date registrationDate) {
		super();
		this.userName = userName;
		this.passwort = passwort;
		this.registrationDate = registrationDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPasswort() {
		return passwort;
	}

	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public int getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
	}

}
