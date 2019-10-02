package de.tt.treg.server.controller.helper;

import java.sql.Date;

import de.tt.treg.server.domain.User;

public class UserBuilder {

	public User getCreatedUserFromUsername(String userName) {
		return new User(userName, getRandomPasswort(), new Date(
				System.currentTimeMillis()));
	}

	private String getRandomPasswort() {
		StringBuffer buffer = new StringBuffer();
		String characters = "";

		characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

		int charactersLength = characters.length();

		for (int i = 0; i < 9; i++) {
			double index = Math.random() * charactersLength;
			buffer.append(characters.charAt((int) index));
		}
		return buffer.toString();
	}

}
