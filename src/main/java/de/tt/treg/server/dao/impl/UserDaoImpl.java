package de.tt.treg.server.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import de.tt.treg.server.dao.UserDao;
import de.tt.treg.server.domain.User;
import de.tt.treg.server.service.impl.MD5Generator;

@Repository
public class UserDaoImpl extends AbstractDaoImpl<User, Integer> implements
		UserDao {

	protected UserDaoImpl() {
		super(User.class);
	}

	@Override
	public User getUserByUserNameAndPasswort(String username, String md5Passwort) {
		List<Criterion> criterias = new ArrayList<Criterion>();
		criterias.add(Restrictions.like("userName", username));
		criterias.add(Restrictions.like("passwort", md5Passwort));
		List<User> result = findByCriterias(criterias);
		if (result.size() == 0)
			return null;
		return result.get(0);
	}

	@Override
	public void save(User e) {
		e.setPasswort(MD5Generator.md5(e.getPasswort()));
		super.save(e);
	}

	@Override
	protected String getTableName() {
		return "user";
	}

}
