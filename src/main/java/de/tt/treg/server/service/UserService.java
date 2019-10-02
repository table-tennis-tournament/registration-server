package de.tt.treg.server.service;

import de.tt.treg.server.domain.User;

public interface UserService {

	public User getUserById(int id);

	public void createNewUser(User userToCreate);

	public User getUserByNameAndPasswort(String username, String passwort);

	public boolean isUserAdmin(String username, String password);

}
