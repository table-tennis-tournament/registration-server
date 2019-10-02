package de.tt.treg.server.dao;

import de.tt.treg.server.domain.User;

public interface UserDao extends AbstractDao<User, Integer> {

	User getUserByUserNameAndPasswort(String username, String md5);

}
