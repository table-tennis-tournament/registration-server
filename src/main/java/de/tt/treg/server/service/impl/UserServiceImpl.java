package de.tt.treg.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.tt.treg.server.dao.UserDao;
import de.tt.treg.server.domain.User;
import de.tt.treg.server.service.UserService;

@Service("userService")
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	@Transactional(readOnly = false)
	public User getUserById(int id) {
		return userDao.findById(id);
	}

	@Override
	@Transactional(readOnly = false)
	public void createNewUser(User userToCreate) {
		userToCreate.setPasswort(MD5Generator.md5(userToCreate.getPasswort()));
		userDao.save(userToCreate);
	}

	@Override
	@Transactional(readOnly = false)
	public User getUserByNameAndPasswort(String username, String passwort) {
		return userDao.getUserByUserNameAndPasswort(username,
				MD5Generator.md5(passwort));
	}

	@Override
	public boolean isUserAdmin(String username, String password) {
		User myUser = userDao.getUserByUserNameAndPasswort(username, MD5Generator.md5(password));
		if(myUser == null) {
			return false;
		}
		if(myUser.getIsAdmin() == 1) {
			return true;
		}
		return false;
	}

}
